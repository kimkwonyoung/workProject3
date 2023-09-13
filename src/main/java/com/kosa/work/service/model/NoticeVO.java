package com.kosa.work.service.model;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

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
	
	
	
	

}
