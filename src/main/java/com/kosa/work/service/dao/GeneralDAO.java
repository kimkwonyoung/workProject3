package com.kosa.work.service.dao;

import java.util.List;

import com.kosa.work.service.model.general.GeneralModel;
import com.kosa.work.service.model.common.*;

/**
 * dao 클래스 일반화를 위한 인터페이스
 * 
 * @author kky
 *
 */
public interface GeneralDAO {

//	String test();
	int getCountBySearch(String sqlMapID, SearchVO search);

	Object selectByKey(String sqlMapID, Object key);

	// List<?> selectByObjects(String sqlMapID, Object...objects);

	List<?> selectBySearch(String sqlMapID, SearchVO search);

	int insert(String sqlMapID, GeneralModel model);

	int update(String sqlMapID, GeneralModel model);

	int delete(String sqlMapID, Object key);
}
