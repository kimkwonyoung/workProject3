package com.kosa.work.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.impl.MemberServiceImpl;
import com.kosa.work.service.model.BoardVO;
import com.kosa.work.service.model.MemberVO;
import com.kosa.work.service.model.NoticeVO;
import com.kosa.work.service.model.search.BoardSearchVO;
import com.kosa.work.service.model.search.MemberSearchVO;

import lombok.extern.slf4j.Slf4j;


/** 관리자 컨트롤러
 * @author kky
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends PrtController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private BoardServiceImpl _boardService;
	
	@Autowired
	private MemberServiceImpl _memberService;
	
	//회원 목록
	@RequestMapping("/memberList.do")
	public String memberList(MemberSearchVO search, BindingResult result, Model model) throws Exception {
		super.setPageSubTitle("관리자 회원 목록", model);
		if (result.hasErrors())
			return super.setBindingResult(result, model);
    
		model.addAttribute("memberlist", _memberService.memberList(search));
		
		return "admin/member_list";
	}
	
	//관리자가 회원 추가
	@ResponseBody
	@RequestMapping(value = "/memberAjaxInsert.do", method = RequestMethod.POST)
	public Map<String, MemberVO> memberAjaxInsert(@RequestBody MemberVO member) throws Exception {
		return _memberService.insertAjax(member);
		
	}
	//회원 목록 더보기 버튼
	@ResponseBody
	@RequestMapping(value = "/memberAjaxList.do", method = RequestMethod.POST)
	public Map<String, List<MemberVO>> memberAjaxList(@RequestBody MemberSearchVO search) throws Exception {
		Map<String, List<MemberVO>> map = new HashMap<String, List<MemberVO>>();
		map.put("memberlist", _memberService.memberList(search));
		
		return map;
	}
	
	//공지사항 게시판 목록
	@RequestMapping(value = "/noticeList.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String noticeList(BoardSearchVO search, Model model) throws Exception {
		if (search.getScRecodeCount() > 0)
			search.setRecordCount(search.getScRecodeCount());
		model.addAttribute("result", _boardService.noticeList(search));
		model.addAttribute("search", search);
		return "admin/notice_list";
	}
	
	//공지사항 수정
	@ResponseBody
	@RequestMapping(value = "/noticeUpdate.do", method = RequestMethod.POST)
	public Map<String, NoticeVO> noticeUpdate(@RequestBody NoticeVO notice) throws Exception {
		Map<String, NoticeVO> map = new HashMap<String, NoticeVO>();
		_boardService.update(notice);
		
		map.put("notice", _boardService.noticeOne(notice));
		
		return map;
	}
	
	//공지사항 수정탭 정보 띄우기
//	@ResponseBody
//	@RequestMapping(value = "/noticeUpdateInfo.do", method = RequestMethod.POST)
//	public Map<String, NoticeVO> noticeUpdateInfo(@RequestBody NoticeVO notice) throws Exception {
//		Map<String, NoticeVO> map = new HashMap<String, NoticeVO>();
//		
//		map.put("noticeInfo", _boardService.noticeOne(notice));
//		
//		return map;
//	}
	
	//공지사항 글 쓰기
	@ResponseBody
	@RequestMapping(value = "/noticeWrite.do", method = RequestMethod.POST)
	public Map<String, NoticeVO> noticeWrite(@RequestBody NoticeVO notice) throws Exception {
		Map<String, NoticeVO> map = new HashMap<String, NoticeVO>();
		_boardService.insert(notice);
		
		map.put("noticeRow", _boardService.noticeNewOne(notice));
		
		return map;
	}
	
	//공지사항 글 상세보기
	@ResponseBody
	@RequestMapping(value = "/noticeInfo.do", method = RequestMethod.POST)
	public Map<String, Object> noticeInfo(@RequestBody NoticeVO notice) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		map.put("status", _boardService.updateNoticeViewcount(notice));
		map.put("info", _boardService.noticeOne(notice));
		
		return map;
	}
	
	//공지시항 글 체크 된것 삭제
	@ResponseBody
	@RequestMapping(value = "/noticeDelete.do", method = RequestMethod.POST)
	public Map<String, List<NoticeVO>> noticeDeleteChk(@RequestBody BoardSearchVO search) throws Exception {
		Map<String, List<NoticeVO>> map = new HashMap<String, List<NoticeVO>>();
		_boardService.deleteChkbox(search);
		map.put("noticeList", _boardService.noticeAddList(search));
		return map;
		
	}
}
