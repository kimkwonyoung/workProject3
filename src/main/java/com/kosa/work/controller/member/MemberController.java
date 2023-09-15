package com.kosa.work.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.work.controller.AdminController;
import com.kosa.work.controller.PrtController;
import com.kosa.work.service.dao.GeneralDAOImpl;
import com.kosa.work.service.impl.MemberServiceImpl;
import com.kosa.work.service.model.MemberVO;
import com.kosa.work.service.model.common.SearchVO;
import com.kosa.work.service.model.search.MemberSearchVO;


/**회원 컨트롤러
 * @author kky
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends PrtController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	private MemberServiceImpl _memberService;
	
	//회원 로그인
	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public Map<String, Object> memberLogin(@RequestBody MemberVO member) throws Exception {
		Map<String, Object> map = _memberService.login(member);
		
		if ((boolean) map.get("status")) {
			getSession().setAttribute("loginMember", map.get("member"));
		}
		
		return map;
	}
	
	//회원 로그아웃
	@ResponseBody
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public Map<String, Object> memberLogout(@RequestBody MemberVO member) throws ServletException, IOException {
		getSession().invalidate();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		return map;
	}
	
	//아이디 찾기, 비밀번호 찾기 폼 이동
	@RequestMapping(value = "/memberSearchMove.do")
	public String memberSearchMove(MemberSearchVO search, Model model) throws Exception {
		model.addAttribute("search", search);
		
		return "noLayout/member/member_search";
	}
	
	//회원가입 폼 이동
	@RequestMapping(value = "/memberWrite.do")
	public String memberWrite(Model model) throws Exception {
		return "noLayout/member/member_write";
	}
	
	//회원 상세 정보 이동
	@RequestMapping(value = "/memberInfo.do")
	public String memberInfo(Model model) throws Exception {
		return "member/member_info";
	}
	
	//정보 수정 폼 이동
	@RequestMapping(value = "/memberUpdateMove.do")
	public String memberUpdateMove(MemberSearchVO search, Model model) throws Exception {
		model.addAttribute("search", search);
		return "noLayout/member/member_update";
	}
	
	//회원 탈퇴 폼 이동
	@RequestMapping(value = "/memberWithdrawMove.do")
	public String memberWithdrawMove(Model model) throws Exception {
		return "noLayout/member/member_withdraw";
	}
	
	//회원 정보 수정 수행
	@ResponseBody
	@RequestMapping(value = "/memberUpdate.do", method = RequestMethod.POST)
	public Map<String, Object> memberUpdate(@RequestBody MemberVO member, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", _memberService.update(member));
		
		
		HttpSession session = request.getSession();
		request.getSession().removeAttribute("loginMember");
		session.setAttribute("loginMember", _memberService.selectByMemberNum(member.getMembernum()));
		
//		getSession().removeAttribute("loginMember");
//		getSession().setAttribute("logingMember", _memberService.selectByMemberNum(member.getMembernum()));
//		
		return map;
	}
	
	//회원 탈퇴 수행
	@ResponseBody
	@RequestMapping(value = "/memberWithdraw.do", method = RequestMethod.POST)
	public Map<String, Object> memberWithdraw(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", _memberService.delete(member));
		
		return map;
	}
	
	//회원 아이디 찾기, 비밀번호 찾기
	@RequestMapping(value = "/memberSearch.do")
	public String memberSearch(MemberVO member, MemberSearchVO search, Model model) throws Exception {
		String message = _memberService.selectBySearch(member, search);
		
		model.addAttribute("search", search);
		model.addAttribute("message", message);
		return "noLayout/member/complete";
	}
	
	//아이디 중복 체크
	@ResponseBody
	@RequestMapping(value = "/memberExist.do" , method = RequestMethod.POST)
	public Map<String, Object> memberExist(@RequestBody MemberVO member) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", _memberService.existMember(member.getMemberid()));
		
		return map;
	}

	
	//회원 가입 수행
	@ResponseBody
	@RequestMapping(value = "/memberInsert.do" , method = RequestMethod.POST)
	public Map<String, Object> memberInsert(@RequestBody MemberVO member) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", _memberService.insert(member));
		
		return map;
	}
	

	
	/*		
	
	//사진 보기 이동 (로직 존재X)
	public String memberFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/favorite.jsp";
	}
	
	
	*/
}
