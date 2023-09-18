package com.kosa.work.controller.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.work.controller.PrtController;
import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.model.BoardVO;
import com.kosa.work.service.model.CommentVO;
import com.kosa.work.service.model.NoticeVO;
import com.kosa.work.service.model.search.BoardSearchVO;

import lombok.extern.slf4j.Slf4j;

/**게시판 컨트롤러
 * @author kky
 * 
 *
 */
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController extends PrtController {
	
	@Autowired
	private BoardServiceImpl _boardService;
	
	
	//공지사항 게시판 목록(일반회원 읽기만)
	@RequestMapping(value = "/noticeList.do")
	public String noticeList(BoardSearchVO search, Model model) throws Exception {
		if (search.getScRecodeCount() > 0)
			search.setRecordCount(search.getScRecodeCount());
		model.addAttribute("result", _boardService.noticeList(search));
		model.addAttribute("search", search);
		
		return "admin/notice_list";
	}
	
	//일반 게시판 목록
	@RequestMapping(value = "/boardList.do")
	public String boardList(BoardSearchVO search, Model model) throws Exception {
		if (search.getScRecodeCount() > 0)
			search.setRecordCount(search.getScRecodeCount());
		
		model.addAttribute("result", _boardService.boardList(search));
		model.addAttribute("search", search);
		
		return "board/board_list";
	}
	
	//일반 게시판 글 상세 보기
	@ResponseBody
	@RequestMapping(value = "/boardInfo.do", method = RequestMethod.POST)
	public Map<String, Object> boardInfo(@RequestBody BoardVO board) throws Exception {
		Map<String, Object> map = new HashMap<>();
		//log.info(">>>>>>>boolean" + _boardService.updateViewcount(board));
		
		CommentVO comment = new CommentVO();
		comment.setBoardNum(board.getBoardNum());
		
		map.put("status", _boardService.updateViewcount(board));
		map.put("info", _boardService.boardOne(board));
		map.put("comment", _boardService.commentList(comment));
		map.put("commentCount", _boardService.commentCount(board.getBoardNum()));
		
		return map;
	}
	
	//일반 게시판 글 수정 하기
	@ResponseBody
	@RequestMapping(value = "/boardUpdate.do", method = RequestMethod.POST)
	public Map<String, Object> boardUpdate(@RequestBody BoardVO board) throws Exception {
		Map<String, Object> map = new HashMap<>();
		//_boardService.update(board);
		
		map.put("status", _boardService.updateBoard(board));
		map.put("board", _boardService.boardOne(board));
		
		return map;
	}
	
	//일반 게시판 글 쓰기
	@ResponseBody
	@RequestMapping(value = "/boardWrite.do", method = RequestMethod.POST)
	public Map<String, Object> boardWrite(@RequestBody BoardVO board) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		map.put("status", _boardService.insertBoard(board));
		map.put("boardRow", _boardService.boardNewOne(board));
		
		return map;
	}
	
	//일반 게시판 답글 등록
	@ResponseBody
	@RequestMapping(value = "/boardReply.do", method = RequestMethod.POST)
	public Map<String, Object> boardReply(@RequestBody BoardVO board) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("status", _boardService.insertBoardReply(board));
		
		return map;
	}
	
	//일반 게시판 글  삭제
	@ResponseBody
	@RequestMapping(value = "/boardDelete.do", method = RequestMethod.POST)
	public Map<String, Object> boardDelete(@RequestBody BoardSearchVO search) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", _boardService.deleteBoard(search));
		return map;
	}
	
	//관리자가 일반 게시판 글 체크 박스로 삭제
	@ResponseBody
	@RequestMapping(value = "/boardDeletechkbox.do", method = RequestMethod.POST)
	public Map<String, Object> noticeDeleteChk(@RequestBody BoardSearchVO search) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		_boardService.deleteBoardchkbox(search);
		return map;
		
	}
	
	//댓글 더보기 추가 
	@ResponseBody
	@RequestMapping(value = "/commentAdd.do", method = RequestMethod.POST)
	public Map<String, Object> commentAdd(@RequestBody CommentVO comment) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comment", _boardService.commentList(comment));
		return map;
		
	}
	
	//댓글 수정하기
	@ResponseBody
	@RequestMapping(value = "/commentUpdate.do", method = RequestMethod.POST)
	public Map<String, Object> commentUpdate(@RequestBody CommentVO comment) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", _boardService.updateComment(comment));
		map.put("comment", _boardService.commentOne(comment.getCommentNum()));
		return map;
		
	}
	
	//댓글 작성하기
	@ResponseBody
	@RequestMapping(value = "/commentWrite.do", method = RequestMethod.POST)
	public Map<String, Object> commentWrite(@RequestBody CommentVO comment) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", _boardService.insertComment(comment));
		map.put("comment", _boardService.commentAll(comment));
		map.put("commentCount", _boardService.commentCount(comment.getBoardNum()));
		return map;
		
	}
	
	//댓글 삭제하기
	@ResponseBody
	@RequestMapping(value = "/commentDelete.do", method = RequestMethod.POST)
	public Map<String, Object> commentDelete(@RequestBody CommentVO comment) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", _boardService.deleteComment(comment.getCommentNum()));
		map.put("commentCount", _boardService.commentCount(comment.getBoardNum()));
		return map;
		
	}
	
	
	
//	public List<Member> getMemberList2(Member member) {
//	      Map<String, Object> map = new HashMap<>();
//	      map.put("searchTitle",member.getSearchTitle());
//	      map.put("startNo", member.getEndNo()-member.getDeleteCount()+1);
//	      map.put("endNo", member.getEndNo());
//	      sqlSession.selectList("mapper.member.getMemberList2",map);
//	      List<Member> list = (List<Member>)map.get("v_cursor");
//	      
//	      select rownum nrow, a.* 
//	         from(
//	            select memberid, name ,pwd, age, phone, address, gender ,email
//	            from member
//	            order by memberid
//	             ) a
//	             where rownum <= p_endNo
//	        ) b
//	     where nrow between p_startNo and  p_endNo
//	      return list;
	
	
	
	//jqGrid 테스트
//	@ResponseBody
//	@RequestMapping(value = "/boardList3.do")
//	public Map<String, List<BoardVO>> boardList3(@RequestBody BoardSearchVO search, Model model) throws Exception {
//		Map<String, List<BoardVO>> map = new HashMap<>();
//		
//		map.put("boardList", (List<BoardVO>) _boardService.boardList3(search));
//		
//		return map;
//	}
}
