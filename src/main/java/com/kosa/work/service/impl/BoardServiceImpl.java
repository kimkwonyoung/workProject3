package com.kosa.work.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kosa.work.controller.AdminController;
import com.kosa.work.service.dao.GeneralDAOImpl;
import com.kosa.work.service.model.BoardVO;
import com.kosa.work.service.model.NoticeVO;
import com.kosa.work.service.model.common.SearchVO;
import com.kosa.work.service.model.search.BoardSearchVO;
import com.kosa.work.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
/** 게시판 비즈니스 로직
 * @author kky
 *
 */
@Service("boardService")
@Slf4j
public class BoardServiceImpl extends BaseServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	@Resource
	private GeneralDAOImpl _gDao;
	
	//메인페이지 공지사항, 일반 글 4개
	public Map<String, Object> mainList(SearchVO search) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("noticeList", (List<NoticeVO>) getDAO().selectList("notice.selectMainNotice", search));
		map.put("nomalList", (List<BoardVO>) getDAO().selectList("board.selectMainBoard", search));
		return map;
	}
	
	//공지사항 게시판 전체 목록
	public Map<String, List<NoticeVO>> noticeList(BoardSearchVO search) throws Exception {
		Map<String, List<NoticeVO>> map = new HashMap<String, List<NoticeVO>>();
		
		map.put("list",  (List<NoticeVO>) getDAO().selectBySearch("notice.selectNoticeList", search, "totalCount"));
		map.put("noticeList",  (List<NoticeVO>) getDAO().selectList("notice.selectMainNotice", search));
		
		return map;
	}
	
	//공지사항 게시판 글 한개 가져오기
	public NoticeVO noticeOne(NoticeVO notice) {
		return (NoticeVO) getDAO().selectOne("notice.selectOneRow", notice);
	}
	
	//공지사항 게시판 새로 추가된 글 가져오기
	public NoticeVO noticeNewOne(NoticeVO notice) throws JSONException, Exception {
		return (NoticeVO) getDAO().selectOne("notice.selectNewOneRow", notice);
	}
	
	//공지사항 게시판 글 쓰기
	public void insert(NoticeVO notice) {
		getDAO().insert("notice.insertNotice", notice);
	}
	
	//공지사항 게시판 글 수정
	public void update(NoticeVO notice) {
		if (StringUtil.isEmpty(notice.getFixedYn())) notice.setFixedYn("N");
		getDAO().update("notice.updateNotice", notice);
	}
	
	//공지사항 게시판 글 체크한 것 삭제
	public void deleteChkbox(BoardSearchVO search) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numlist", search.getScNoticeChkNum());
		getDAO().delete("notice.deleteNotice", params);
		
	}
	
	//공지사항 게시판 특정 글 로우(rownum) 갯수만큼 가져오기
	public List<NoticeVO> noticeAddList(BoardSearchVO search) {
		search.setScNrow(search.getScNoticeChkNum().length);
		
		return (List<NoticeVO>) getDAO().selectList("notice.selectAddNumList", search);
	}
	
	
	/*
	//아이디를 Key로 게시판 글 가져오기
	public List<BoardVO> selectByIdBoardList(SearchVO search) {
		return _boardDao.selectByIdBoardList(QueryProperty.getQuery("board.selectId"), search.getsMemid());
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기 + 조회수 1증가 업데이트
	public BoardVO selectByBoardNum(int board_num) {
		int row = _boardDao.updateViewCount(QueryProperty.getQuery("board.updateView"), board_num);
		Optional<BoardVO> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), board_num);
		if (row > 0) {
			System.out.println("조회수 반영된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		return optionalBoard.orElse(null);
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기
	public BoardVO selectKeyNum(int board_num) {
		Optional<BoardVO> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), board_num);
		return optionalBoard.orElse(null);
	}
	
	//댓글 전체 리스트 가져오기(글 한개에 대한)
	public List<CommentVO> selectCommentList(int board_num) {
		return _boardDao.selectByCommentList(QueryProperty.getQuery("board.selectCommentList"), board_num);
	}
	
	//댓글 카운트
	public int selectCommentCount(int board_num) {
		return _boardDao.selectByCommentCount(QueryProperty.getQuery("board.selectCommentCount"), board_num);
	}
	
	//게시판 글 작성
	public void insert(BoardVO board) {
		if (StringUtil.isEmpty(board.getFixed_yn())) board.setFixed_yn("N");
		
		int row = _boardDao.insert(QueryProperty.getQuery("board.insert"), board);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 댓글 작성
//	public JSONObject insert(Board_comment comment) {
//		JSONObject jsonResult = new JSONObject();
//		int row = _boardDao.insertComment(QueryProperty.getQuery("board.insertComment"), comment);
//		if (row > 0) {
//			jsonResult.put("status", true);
//			System.out.println("반영된 글 갯수 : " + row);
//			System.out.println("작성한 댓글 = " + comment);
//			jsonResult.put("board_comment", _boardDao.selectByComment(QueryProperty.getQuery("board.selectCommentRecent"), comment));
//		} else {
//			jsonResult.put("status", false);
//			System.out.println("반영 X");
//		}
//		return jsonResult;
//	}
	public void insert(CommentVO comment) {
		int row = _boardDao.insertComment(QueryProperty.getQuery("board.insertComment"), comment);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	*/
	
/*	
	//게시판 댓글 수정
	public JSONObject update(CommentVO comment) {
		JSONObject jsonResult = new JSONObject();
		String nowtime = StringUtil.getDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		comment.setReg_date(nowtime);
		int row = _boardDao.updateComment(QueryProperty.getQuery("board.updateComment"), comment);
		if (row > 0) {
			
			jsonResult.put("status", true);
			jsonResult.put("comment_num", comment.getComment_num());
			jsonResult.put("detail", comment.getDetail());
			jsonResult.put("reg_date", nowtime);
			System.out.println("반영된 댓글 갯수 : " + row);
		} else {
			jsonResult.put("status", false);
			System.out.println("반영 X");
		}
		return jsonResult;
	}
	
	//게시판 글 삭제
	public JSONObject delete(BoardVO board) {
		JSONObject jsonResult = new JSONObject();
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), board.getBoard_num());
		if (row > 0) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageBoardDelete());
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessageBoardFail());
		}
		return jsonResult;
	}
	
	public JSONObject deleteAjax(BoardVO board) throws Exception {
		JSONObject jsonResult = new JSONObject();
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), board.getBoard_num());
		if (row > 0) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageBoardDelete());
			jsonResult.put("bod", _boardDao.selectByPageRow(board));
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessageBoardFail());
		}
		
		
		return jsonResult;
	}
	
	public JSONObject deleteAjax2(BoardVO board) throws Exception {
		JSONObject jsonResult = new JSONObject();
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), board.getBoard_num());
		if (row > 0) {
			
			board.setBoard_num(board.getLastBnum());
			board.setNrow(1);
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageBoardDelete());
			jsonResult.put("bod", _boardDao.selectByAddList(board));
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessageBoardFail());
		}
		
		
		return jsonResult;
	}
	
	
	public JSONObject deleteCheckBox(BoardVO board) throws JSONException, Exception {
		JSONObject jsonResult = new JSONObject();
		_boardDao.delete(QueryProperty.getQuery("board.deleteChk"), board.getDeleteStr());
		board.setBoard_num(board.getLastBnum());
		board.setNrow(board.getCount());
		jsonResult.put("bodChk", _boardDao.selectByAddList(board));
	
		return jsonResult;
	}
	
	public JSONObject deleteCheckBox2(BoardVO board) throws JSONException, Exception {
		JSONObject jsonResult = new JSONObject();
		_boardDao.delete(QueryProperty.getQuery("board.deleteChk"), board.getDeleteStr());
		
		jsonResult.put("bodChk", _boardDao.selectByPageRow2(board));
	
		return jsonResult;
	}
	
	
	
	//게시판 댓글 삭제
	public void deleteComment(int comment_num) {
		int row = _boardDao.delete(QueryProperty.getQuery("board.deleteComment"), comment_num);
		if (row > 0) {
			System.out.println("삭제된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		
	}
	
	//게시판 board_list2.jsp용
	public List<BoardVO> selectByAddList(BoardVO board) throws Exception {
		board.setNrow(10);
		return _boardDao.selectByAddList(board);
	}
	
	public JSONObject selectByAjaxList(BoardVO board) throws JSONException, Exception {
		JSONObject jsonResult = new JSONObject();
		board.setNrow(10);
		jsonResult.put("boardAjaxList", _boardDao.selectByAddList(board));
		
		return jsonResult;
	}
	
	
	
	
	

	
	*/
}
