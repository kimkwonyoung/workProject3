package com.kosa.work.service.model.search;

import com.kosa.work.service.model.common.SearchVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardSearchVO extends SearchVO {
	private static final long serialVersionUID = 8860661778683988628L;
	
	private int scBoardNum; // 일반 게시판 글 번호
	private int scNoticeNum; // 공지사항 게시판 글 번호
	private int scRecodeCount; // 게시판 레코드 갯수
	private int[] scNoticeChkNum; // 공지사항 게시판 체크된 글 번호 배열
	private int scNrow; // 게시판의 rownum
}
