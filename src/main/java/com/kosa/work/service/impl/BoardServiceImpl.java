package com.kosa.work.service.impl;
import java.io.File;
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
import com.kosa.work.service.model.CommentVO;
import com.kosa.work.service.model.NoticeVO;
import com.kosa.work.service.model.common.AttacheFileVO;
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
	public NoticeVO noticeNewOne(NoticeVO notice) throws Exception {
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
	
	//공지사항 조회수 증가
	public boolean updateNoticeViewcount(NoticeVO notice) {
		return 0 != getDAO().update("notice.updateViewcount", notice);
	}
	
	//공지사항 게시판 글 체크한 것 삭제
	public void deleteChkbox(BoardSearchVO search) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numlist", search.getScNoticeChkNum());
		getDAO().delete("notice.deleteNotice", params);
		
	}
	
	//일반 게시판 관리자가 글 체크한 것 삭제
	public void deleteBoardchkbox(BoardSearchVO search) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numlist", search.getScBoardChkNum());
		getDAO().delete("board.deleteBoardchkbox", params);
		
	}
	
	//공지사항 게시판 특정 글 로우(rownum) 갯수만큼 가져오기
	public List<NoticeVO> noticeAddList(BoardSearchVO search) {
		search.setScNrow(search.getScNoticeChkNum().length);
		
		return (List<NoticeVO>) getDAO().selectList("notice.selectAddNumList", search);
	}
	
	//일반 게시판 전체 목록
	public Map<String, Object> boardList(BoardSearchVO search) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		map.put("list",  (List<BoardVO>) getDAO().selectBySearch("board.selectBoardList", search, "totalCount"));
		map.put("noticeList",  (List<NoticeVO>) getDAO().selectList("notice.selectMainNotice", search));
		
		return map;
	}
	
	//일반 게시판 한건 가져 오기
	public BoardVO boardOne(BoardVO board) {
		return (BoardVO) getDAO().selectOne("board.selectOneRow", board);
	}
	
	//일반 게시판 조회수 증가
	public boolean updateViewcount(BoardVO board) {
		//log.info(">>>>>>>>>>" + getDAO().update("board.updateViewcount", board));
		return 0 != getDAO().update("board.updateViewcount", board);
	}
	
	//일반 게시판 글 수정
	public boolean updateBoard(BoardVO board) {
		return 0 != getDAO().update("board.updateBoard", board);
	}
	
	//일반 게시판 글 쓰기
	public Map<String, Object> insertBoard(BoardVO board) {
		Map<String, Object> map = new HashMap<>();
		getDAO().insert("board.insertBoard", board);
		//인설트 하고 파일업로드
		BoardVO boardNew = (BoardVO) getDAO().selectOne("board.selectNewOneRow", board);
		
		map.put("boardRow", boardNew);
		
		board.setBoardNum(boardNew.getBoardNum());
		if (board.getAttacheFileList() != null) {
			for (AttacheFileVO attacheFile : board.getAttacheFileList()) {
				attacheFile.setBoardid(board.getBoardNum());
				getDAO().insert("attacheFile.insert", attacheFile);
			}
		}
		
		return map;
	}
	
	//일반 게시판 답글 쓰기
	public boolean insertBoardReply(BoardVO board) {
		return 0 != getDAO().insert("board.insertBoardReply", board);
	}
	
	//일반 게시판 새로 추가된 글 가져오기
	public BoardVO boardNewOne(BoardVO board) throws Exception {
		return (BoardVO) getDAO().selectOne("board.selectNewOneRow", board);
	}
	
//	//일반 게시판 마지막 밑에 글 가져오기
//	public BoardVO boardLastRow(BoardSearchVO search) throws Exception {
//		return (BoardVO) getDAO().selectOne("board.selectLastrow", search);
//	}
	
	//일반 게시판 글 삭제
	public boolean deleteBoard(BoardSearchVO search) {
		List<AttacheFileVO> attacheList = (List<AttacheFileVO>) getDAO().selectList("attacheFile.getList", search.getScBoardNum());
		
		for (AttacheFileVO attache : attacheList) {
			String filePathToDelete = getConfig().getUploadPathPhysical() + getConfig().getUploadPathImage() + attache.getFileNameReal();
			File fileToDelete = new File(filePathToDelete);
			if (fileToDelete.exists()) { // 파일이 존재하는지 확인
				fileToDelete.delete();
			} 
		}
		return 0 != getDAO().delete("board.deleteBoard", search);
	}
	
	//댓글 전체 리스트 가져오기 5건(글 한개에 대한)
	public List<CommentVO> commentList(CommentVO comment) {
		return (List<CommentVO>) getDAO().selectList("comment.selectCommentlist", comment);
	}
	//댓글 전체 리스트 가져오기(글 한개에 대한)
	public List<CommentVO> commentAll(CommentVO comment) {
		return (List<CommentVO>) getDAO().selectList("comment.selectCommentAll", comment);
	}
	
	//댓글 갯수
	public int commentCount(int board_num) {
		return (int) getDAO().selectOne("comment.selectCommentcount", board_num);
	}
	
	//댓글 번호로 한건 가져오기
	public CommentVO commentOne(int comment_num) {
		return (CommentVO) getDAO().selectOne("comment.selectCommentNum", comment_num);
	}
	
	//댓글 번호로 한건 가져오기
	public CommentVO commentNew(int board_num) {
		return (CommentVO) getDAO().selectOne("comment.selectCommentNew", board_num);
	}
	
	//댓글 수정 하기
	public boolean updateComment(CommentVO comment) {
		return 0 != getDAO().update("comment.updateComment", comment);
	}
	
	//댓글 작성 하기
	public boolean insertComment(CommentVO comment) {
		return 0 != getDAO().insert("comment.insertComment", comment);
	}
	
	//댓글 삭제 하기
	public boolean deleteComment(int comment_num) {
		return 0 != getDAO().delete("comment.deleteComment", comment_num);
	}
	
	
	//첨부 파일 목록 가져오기
	public List<AttacheFileVO> attacheFileList(int boardid) {
		return (List<AttacheFileVO>) getDAO().selectList("attacheFile.getList", boardid);
	}
	
	//첨부 파일 한건 가져오기
	public AttacheFileVO getAttacheFile(AttacheFileVO attach) {
		return (AttacheFileVO) getDAO().selectOne("attacheFile.getAttacheFile", attach);
	}
	
	
	
	
}
