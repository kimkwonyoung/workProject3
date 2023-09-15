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
public class BoardVO implements GeneralModel {
	private static final long serialVersionUID = 5812978017275882923L;
	private int boardNum;
	private String memId;
	private String title;
	private String content;
	private String regDate;
	private String modDate;
	private int viewCount;
	private int pid;
	private int level;
	
//	private int nrow;
//	private int count;
//	private int lastBnum;
//	private String deleteStr;
//	
//	//검색
//	private String searchTitle = "";
//	
//	
//	//페이징
//	private int pageNo = 1;     //현재 페이지 번호
//	private int totalCount;     //전체 건수  
//	private int totalPageSize;  //전체 페이지수  
//	private int pageLength = 10;//한페이지의 길이 
//	private int navSize = 10;   //페이지 하단에 출력되는 페이지의 항목수 
//	private int navStart = 0;   //페이지 하단에 출력되는 페이지 시작 번호 : NavStart = (PageNo / NavSize) * NavSize + 1
//	private int navEnd = 0;     //페이지 하단에 출력되는 페이지 끝 번호 : NavEnd = (PageNo / NavSize + 1) * NavSize
//	
//	public void setTotalCount(int totalCount) {
//		this.totalCount = totalCount;
//
//		// 2. 전체 페이지 건수를 계산한다
//		totalPageSize = (int) Math.ceil((double) totalCount / pageLength);
//
//		// 3. 페이지 네비게이터 시작 페이지를 계산한다
//		navStart = ((pageNo - 1) / navSize) * navSize + 1;
//
//		// 4. 페이지 네비게이터 끝 페이지를 계산한다
//		navEnd = ((pageNo - 1) / navSize + 1) * navSize;
//
//		// 5. 전체 페이지 보다 크면 전체 페이지 값을 변경한다
//		if (navEnd >= totalPageSize) {
//			navEnd = totalPageSize;
//		}
//	}
//
//	public int getStartNo() {
//		return (pageNo - 1) * pageLength + 1;
//	}
//
//	public int getEndNo() {
//		return pageNo * pageLength;
//	}
//	
//	public JSONObject getJSONObject() throws Exception {
//        JSONObject json = new JSONObject();
//
//        Class cls = this.getClass();
//        for (Field field : cls.getDeclaredFields()) {
//            json.put(field.getName(), field.get(this));
//        }
//
//        return json;
//    }
//
//    public Map<String, Object> getMapObject() throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        Class cls = this.getClass();
//        for (Field field : cls.getDeclaredFields()) {
//            map.put(field.getName(), field.get(this));
//        }
//
//        return map;
//    }
	
	

}
