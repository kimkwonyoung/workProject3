package com.kosa.work.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kosa.work.controller.AdminController;
import com.kosa.work.service.model.MemberVO;
import com.kosa.work.service.model.common.SearchVO;
import com.kosa.work.service.model.search.MemberSearchVO;

import lombok.extern.slf4j.Slf4j;

/** 회원 비즈니스 로직
 * @author kky
 *
 */
@Slf4j
@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl {
	
	//회원 전체 목록
	public List<MemberVO> memberList(MemberSearchVO search) {
		return (List<MemberVO>) getDAO().selectList("member.selectMemberList", search);
	}
	
	//관리자에서 회원 추가
	public Map<String, MemberVO> insertAjax(MemberVO mem) throws Exception {
		//프로시저 적용 로직
		getDAO().insert("member.callMemberInsert", mem);
		Map<String, MemberVO> map = new HashMap<String, MemberVO>();
		map.put("mem", (MemberVO) getDAO().selectOne("member.selectMemberOneRow", mem));
		return map;
	}
	
	//로그인
	public Map<String, Object> login(MemberVO mem) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO loginMember = (MemberVO) getDAO().selectOne("member.selectMemberId", mem.getMemberid());
		if (loginMember != null) {
			if (loginMember.isEqualPwd(mem)) {
				map.put("status", true);
				map.put("member", loginMember);
			} else {
				map.put("status", false);
			}
		} else {
			map.put("status", false);
		}
		return map;
	}
	
	//회원 정보 수정
	public boolean update(MemberVO mem) throws Exception {
		boolean status = false;
//		log.info(">>>>>>>>>>>" + getDAO().update("member.callMemberUpdate", mem));
		if (getDAO().update("member.callMemberUpdate", mem) < 0) {
			status = true;
		}
		return status;
	}
	
	//회원 번호로 회원 조회
	public MemberVO selectByMemberNum(int num) {
		return (MemberVO) getDAO().selectOne("member.selectMemberNum", num);
	}
	
	//회원 탈퇴 
	public boolean delete(MemberVO mem) {
		boolean status = false;
		log.info(">>>입력회원정보 = " + mem);
		MemberVO loginMember = (MemberVO) getDAO().selectOne("member.selectMemberId", mem.getMemberid());
		log.info(">>>>찾은 회원 정보 = " + loginMember);
		if (loginMember != null) {
			if (loginMember.isEqualPwd(mem))  {
				getDAO().delete("member.deleteMember", mem);
				status = true;
			}
		}
		return status;
	}
	
	//아이디 찾기
	public Map<String, Object> searchId(MemberVO mem) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO member = new MemberVO();
		String message = "";
		boolean status = false;
		member = (MemberVO) getDAO().selectByKey("member.selectSearchId", mem);
		if (member != null) {
			message = "찾으려는 아이디 = " + member.getMemberid();
			status = true;
		} else {
			message = "이름 또는 휴대폰 번호가 다릅니다";
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	//비밀번호 찾기
	public Map<String, Object> searchPwd(MemberVO mem) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO member = new MemberVO();
		String message = "";
		boolean status = false;
		member = (MemberVO) getDAO().selectByKey("member.selectSearchPwd", mem);
		if (member != null) {
			message = "찾으려는 비밀번호= " + member.getPwd();
			status = true;
		} else {
			message = "아이디 또는 이름이 다릅니다";
		}
		map.put("status", status);
		map.put("message", message);
		
		return map;
	}
	
	
	//존재 하는 회원 카운트
	public boolean existMember(String memberid) throws Exception {
		boolean status = false;
		int row = (int) getDAO().selectOne("member.selectbyCount", memberid);
		if (row > 0) {
			status = true;
		}
		return status;
	}
	
	//회원 가입
	public boolean insert(MemberVO mem) throws Exception {
		boolean status = false;
//		log.info(">>>>>>>>>>>>>>>>>" + getDAO().insert("member.callMemberInsert", mem));
		int row = getDAO().insert("member.callMemberInsert", mem);
		if (row < 0) status = true;
		
		return status;
	}
	
}
