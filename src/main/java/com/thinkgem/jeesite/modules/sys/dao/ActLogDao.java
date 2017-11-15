package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jr.utils.PaginationMap;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 操作日志DAO接口
 * @version 2013-8-23
 */
@MyBatisDao
public interface ActLogDao{

	List<Map<String, Object>> getColumnByTable(Map<String, Object> param);

	Map<String, Object> getDataByKey(Map<String, Object> param);

	String getCurSeq(Map<String, Object> param);

	int insert(Map<String, Object> log);

	void insertField(List<Map<String, Object>> fieldLog);

	void insertFieldClob(List<Map<String, Object>> fieldLog);

	List<Map<String, Object>> getPubColumn();

	Map<String, Object> getPub(Map<String, Object> map);

	Map<String, Object> getTableName(String logTableAlias);

	void insertFieldClobEX(Map<String, Object> field);
	
	List<Map<String,Object>> list(Map<String,Object> map);
	
	Map<String,Object> selectList(PaginationMap map);
	
	List<Map<String,Object>> typeList(PaginationMap map);
}
