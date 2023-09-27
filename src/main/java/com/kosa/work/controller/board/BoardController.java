package com.kosa.work.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kosa.work.controller.PrtController;
import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.model.BoardVO;
import com.kosa.work.service.model.CommentVO;
import com.kosa.work.service.model.NoticeVO;
import com.kosa.work.service.model.common.AttacheFileVO;
import com.kosa.work.service.model.common.SearchVO;
import com.kosa.work.service.model.search.BoardSearchVO;
import com.kosa.work.util.StringUtil;

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
		
		
		map.put("filelist", _boardService.attacheFileList(board.getBoardNum()));
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
	public Map<String, Object> boardWrite(BoardVO board) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		List<AttacheFileVO> list = new ArrayList<AttacheFileVO>();
		if (board.getFile() != null) {
			for (MultipartFile file : board.getFile()) { 
				System.out.println("contentType = " + file.getContentType());
				System.out.println("size = " + file.getSize());
				System.out.println("name = " + file.getName());
				System.out.println("originalFilename = " + file.getOriginalFilename());
				
				//해당 위치에서 첨부 파일을 저장하면됨
				list.add(fileProcess(file));
			}
			board.setAttacheFileList(list);
		}
		//log.info(">>>>>>>>>파일 데이터 = " + board.getAttacheFileList().toString());
		
		map = _boardService.insertBoard(board);
//		map.put("boardRow", _boardService.boardNewOne(board));
//		map.put("filelist", board2.getAttacheFileList());
		
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
	
//	@ResponseBody
//	@RequestMapping(value = "/uploadFile.do", method = RequestMethod.POST) 
//	public Map<String, Object> uploadFile(AttacheFileVO uploadDTD, @RequestBody BoardVO board) throws Exception {
//		Map<String, Object> result = new HashMap();
//		BoardVO board2 = new BoardVO();
//		List<AttacheFileVO> list = new ArrayList<AttacheFileVO>();
//		result.put("status", false);
//		if (uploadDTD.getFile() != null) {
//			for (MultipartFile file : uploadDTD.getFile()) { 
//				System.out.println("contentType = " + file.getContentType());
//				System.out.println("size = " + file.getSize());
//				System.out.println("name = " + file.getName());
//				System.out.println("originalFilename = " + file.getOriginalFilename());
//				
//				//해당 위치에서 첨부 파일을 저장하면됨
//				list.add(fileProcess(file));
//			}
//			board2.setAttacheFileList(list);
//		}
//		result.put("filelist", board2.getAttacheFileList());
//		result.put("status", true);
//		return result;
//	}
	
	@RequestMapping("/download.do")
	protected void download(AttacheFileVO attach,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		
		AttacheFileVO attacheFile = _boardService.getAttacheFile(attach); 
		if (attacheFile != null) {
			String filePath = getConfig().getUploadPathPhysical() + getConfig().getUploadPathImage() + attacheFile.getFileNameReal();  
			File image = new File(filePath);
			
			
			if (!attacheFile.getContentType().contains("image")) {
				response.setHeader("Cache-Control", "no-cache");
				response.addHeader("Content-disposition", "attachment; fileName=" + attacheFile.getFileNameOrg());
				if (attacheFile.getContentType().contains("text")) {
					String fileNameOrg = attacheFile.getFileNameOrg();
					String encodedFileNameOrg = URLEncoder.encode(fileNameOrg, "UTF-8");
					response.setHeader("Content-disposition", "attachment; filename=\"" + encodedFileNameOrg + "\"");
				}
			}
			
			FileInputStream in = new FileInputStream(image);
			
			byte[] buffer = new byte[1024 * 8];
			while (true) {
				int count = in.read(buffer); 
				if (count == -1) 
					break;
				out.write(buffer, 0, count);
			}
			in.close();
		}
		out.close();
	}
	
	private AttacheFileVO fileProcess(MultipartFile file2) throws Exception{
		Calendar now = Calendar.getInstance();
		//SimpleDateFormat sdf = new SimpleDateFormat("\\yyyy\\MM\\dd");
		SimpleDateFormat sdf = SearchVO.SIMPLE_DATE_FORMAT;
		
		String fileNameOrg = file2.getOriginalFilename();
		String realFolder = sdf.format(now.getTime());
		
		File file = new File(getConfig().getUploadPathPhysical() + getConfig().getUploadPathImage() + realFolder);
		if (file.exists() == false) {
			file.mkdirs();
		}
	
		String fileNameReal = UUID.randomUUID().toString();
		
		//파일 저장 
		file2.transferTo(new File(file, fileNameReal)); //임시로 저장된 multipartFile을 실제 파일로 전송
	
		
		AttacheFileVO attache =	AttacheFileVO.builder()
			.fileNameOrg(fileNameOrg)
			.fileNameReal(realFolder + "\\" + fileNameReal)
			.length((int) file2.getSize())
			.contentType(file2.getContentType())
			.build();
				
		
		return attache;
}		
	
	
//	@RequestMapping(value="/board/reply.do", method = RequestMethod.POST)
//	public String reply(BoardDTO board, Model model,
//			MultipartHttpServletRequest multipartRequest
//			) throws Exception {
//		System.out.println("board.controller.detail() invoked.");
//		System.out.println("board " + board);
//		
//		multipartRequest.setCharacterEncoding("utf-8");
//		Map map = new HashMap();
//		Enumeration enu=multipartRequest.getParameterNames();
//		while(enu.hasMoreElements()){
//			String name=(String)enu.nextElement();
//			String value=multipartRequest.getParameter(name);
//			//System.out.println(name+", "+value);
//			map.put(name,value);
//		}
//		
//		board.setAttacheFileList(fileProcess(multipartRequest));
//		
//		try {
//			model.addAttribute("status", true);
//			board.setWriter_uid("bbb");
//			boardService.reply(board);
//        } catch (Exception e) { 
//        	model.addAttribute("status", false);
//        	model.addAttribute("message", "서버에 오류 발생");
//        	e.printStackTrace();
//        }
//
//		return "redirect:list.do";
//	} // detail
//	

	
	
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
