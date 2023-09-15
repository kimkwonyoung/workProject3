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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.work.controller.PrtController;
import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.model.BoardVO;
import com.kosa.work.service.model.search.BoardSearchVO;

/**게시판 컨트롤러
 * @author kky
 * 
 *
 */
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
		
		model.addAttribute("result", _boardService.noticeList(search));
		model.addAttribute("search", search);
		
		return "board/board_list";
	}
	
	
	
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
	
	//게시판 글 작성
	public String boardInsert(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		_boardService.insert(board);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		
		String url = "";
		if (board.getBoard_code() == 10) { // 일반 게시판
			url = "board/board_list.jsp";
		} else if (board.getBoard_code() == 20) { // 공지사항 게시판
			url = "board/notice_list.jsp";
		}
		return url;
	}
	
	//게시판 글 업데이트
	public String boardUpdate(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
						
		_boardService.update(board);
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("infoBoard", _boardService.selectKeyNum(board.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(board.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(board.getBoard_num()));

		return "board/board_info.jsp";
		
	}
	
	//게시판 글 삭제
	public String boardDelete(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject jsonResult = _boardService.delete(board);
		return jsonResult.toString();
	}
	
	//게시판 체크한 글 삭제
	public String boardDeleteChkbox(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		search.setsBNumStr(request.getParameter("bnumStr"));
		System.out.println("넘어온 글번호 값 : " + request.getParameter("bnumStr"));
		
		_boardService.deleteChkbox(search);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		
		String url = "";
		if (board.getBoard_code() == 10) { // 일반 게시판
			url = "board/board_list.jsp";
		} else if (board.getBoard_code() == 20) { // 공지사항 게시판
			url = "board/notice_list.jsp";
		}
		return url;
	}
	

	
	//일반게시판 목록
	public String boardList(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "";
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		
		if (board.getBoard_code() == 10) { // 일반 게시판
			url = "board/board_list.jsp";
		} else if (board.getBoard_code() == 20) { // 공지사항 게시판
			url = "board/notice_list.jsp";
		}
		return url;
	}
	
	public String ajaxDeleteChkOne(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteAjax(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxDeleteChkAll(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteCheckBox(board);
		
		return jsonResult.toString();
		
	}
	
	//게시판 글 정보
	public String boardInfo(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(board.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(board.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(board.getBoard_num()));
		
		return "board/board_info.jsp";
	}
	
	//게시판 글 수정 페이지 이동(글쓰기 공통)
	public String boardUpdateInfo(BoardVO board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("chk", CommonProperty.getUpdate());
		request.setAttribute("infoBoard", _boardService.selectKeyNum(board.getBoard_num()));
		
		return "board/board_write.jsp";
	}
	
	//게시판 글 쓰기 페이지 이동(글쓰기 공통)
	public String boardWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("chk", CommonProperty.getWrite());

		return "board/board_write.jsp";
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
	@ResponseBody
	@RequestMapping(value = "/boardList3.do")
	public Map<String, List<BoardVO>> boardList3(@RequestBody BoardSearchVO search, Model model) throws Exception {
		Map<String, List<BoardVO>> map = new HashMap<>();
		
		map.put("boardList", (List<BoardVO>) _boardService.boardList3(search));
		
		return map;
	}
}
