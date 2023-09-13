package com.kosa.work.service.model.search;

import com.kosa.work.service.model.common.SearchVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberSearchVO extends SearchVO {
	private static final long serialVersionUID = 8860661778683988628L;
	
	private int scMemNum; //회원 번호

}
