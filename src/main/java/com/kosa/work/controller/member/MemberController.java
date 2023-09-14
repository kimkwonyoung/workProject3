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
	
	/*	
	//회원 가입 수행
	public String memberInsert(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _memberService.insert(member);
		return jsonResult.toString();
	}
	
	public String memberAjaxInsert(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _memberService.insertAjax(member);
		
		return jsonResult.toString(); 
	}
	
	//아이디 중복 체크
	public String memberExist(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _memberService.existMember(member.getMemberid());
		return jsonResult.toString();
	}
	

	
	
	
	//회원 상세 정보
	public String memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/member_info.jsp";
	}
	
	public String memberList(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MemberVO> memberList = _memberService.selectByList(member);
		
		request.setAttribute("memberlist", memberList);
		
		return "member/member_list.jsp";
	}
	
	//회원 목록 더보기
	public String memberAjaxList(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		
		try { 
			List<MemberVO> memberList = _memberService.selectByList(member);
        	result.put("status", true);
        	result.put("memberlist", memberList);
        } catch (Exception e) { 
        	result.put("status", false);
        	result.put("message", "서버에 오류 발생");
        	e.printStackTrace();
        }
		return result.toString();
	}
	
	
	//회원 아이디 찾기, 비밀번호 찾기
	public String memberSearch(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchVO search = new SearchVO();
		search.setChkMem(request.getParameter("chkMem")); //아이디 찾기, 비밀번호 찾기 구분자 ex) value= findid, findpwd
		search.setsMemid(request.getParameter("memberid")); //파라미터 아이디
		String message = _memberService.selectBySearch(member, search);
		
		request.setAttribute("chkMem", search.getChkMem());
		request.setAttribute(CommonProperty.getAlertmessage(), message);
		
		return "member/complete.jsp";
	}
	
	//회원 정보 수정 수행
	public String memberUpdate(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _memberService.update(member);
		
		HttpSession session = request.getSession();
		request.getSession().removeAttribute("loginMember");
		
		session.setAttribute("loginMember", (MemberVO) jsonResult.get("member"));
		
		return jsonResult.toString();
	}
	
	//회원 탈퇴 수행
	public String memberWithdraw(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonResult = _memberService.delete(member);
		
		return jsonResult.toString();
	}
	
	//회원가입 폼 이동
	public String memberWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/member_write.jsp";
	}
	
	//아이디 찾기, 비밀번호 찾기 폼 이동
	public String memberSearchMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("chkMem", request.getParameter("chkMem"));
		
		return "member/member_search.jsp";
	}
	
	//사진 보기 이동 (로직 존재X)
	public String memberFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/favorite.jsp";
	}
	
	//정보 수정 폼 이동
	public String memberUpdateMove(SearchVO search, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("sMemid", search.getsMemid());
		return "member/member_update.jsp";
	}
	
	//회원 탈퇴 폼 이동
	public String memberWithdrawMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/member_withdraw.jsp";
	}
	*/
}
