package com.kosa.work.service.model;



import com.kosa.work.service.model.general.GeneralModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeVO implements GeneralModel {
	private static final long serialVersionUID = 5812978017275882923L;
	private int noticeNum;
	private String memId;
	private String title;
	private String content;
	private String regDate;
	private String modDate;
	private int viewCount;
	private String fixedYn;
	
	
	private int nrow; // rownum 컬럼 사용(파라미터)
	
	
	

}
