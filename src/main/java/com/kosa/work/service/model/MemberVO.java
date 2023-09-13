package com.kosa.work.service.model;


import com.kosa.work.service.model.general.GeneralModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberVO implements GeneralModel {
	private static final long serialVersionUID = -3326403453610257995L;
	private int membernum;
	private String memberid;
	private String name;
	private String pwd;
	private String phone;
	
	public boolean isEqualPwd(MemberVO member) {
		return pwd.equals(member.getPwd());
	}
}
