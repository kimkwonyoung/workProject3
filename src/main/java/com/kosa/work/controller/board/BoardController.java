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
		map.put("status", _boardService.updateViewcount(board));
		map.put("info", _boardService.boardOne(board));
		
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
	
	
	/*	
	public String boardList2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception { 
		request.setAttribute("boardList2", _boardService.selectByAddList(board));
		return "board/board_list2.jsp";
	}
	public String boardAjaxList2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception { 
		JSONObject jsonResult = _boardService.selectByAjaxList(board);
		return jsonResult.toString();
	}
	
	public String ajaxDeleteChkOne2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _boardService.deleteAjax2(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxDeleteChkAll2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _boardService.deleteCheckBox2(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxInfo2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = new JSONObject();
		List<BoardVO> boardList = new ArrayList<>();
		boardList.add(_boardService.selectByBoardNum(board.getBoard_num()));		
		
		jsonResult.put("boardInfo", boardList);
		
		return jsonResult.toString();
	}
	
	public String ajaxWrite2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		_boardService.insert2(board);
		JSONObject jsonResult = _boardService.selectByAjaxOneRow(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxUpdate2(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		_boardService.update(board);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("boardUpdate", _boardService.selectByBoardNum(board.getBoard_num()));	
		
		return jsonResult.toString();
	}
	

	

	public String ajaxDeleteChkOne(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteAjax(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxDeleteChkAll(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteCheckBox(board);
		
		return jsonResult.toString();
		
	}
	

	//댓글 작성
	public String commentInsert(CommentVO comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		search.setsBoard_code(request.getParameter("board_code"));
		
		_boardService.insert(comment);
		
		request.setAttribute("board_code", search.getsBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(comment.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(comment.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(comment.getBoard_num()));
		
		return "board/board_info.jsp";
	}
//	public String commentInsert(Board_comment comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("댓글 = " + comment);
//		JSONObject jsonResult = _boardService.insert(comment);
//		System.out.println(jsonResult.get("board_comment"));
//		return jsonResult.toString();
//	}
	
	//댓글 삭제 
	public String commentDelete(CommentVO comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_code(request.getParameter("board_code"));
		
		_boardService.deleteComment(comment.getComment_num());

		request.setAttribute("board_code", search.getsBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(comment.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(comment.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(comment.getBoard_num()));

		return "board/board_info.jsp";
	}
	
	//게시판에 댓글 수정하기
	public String boardUpdateComment(CommentVO board_comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonResult = _boardService.update(board_comment);
		return jsonResult.toString();
	}
	*/
	
	
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
