package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.ActLogDao;

/**
 * 日志Service
 * @author ThinkGem
 * @version 2013-6-2
 */
@Service
@Transactional(readOnly = true)
public class ActLogService extends BaseService {

	@Autowired
	private ActLogDao actLogDao;

	public List<Map<String, Object>> getColumnByTable(String logTableAlias) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("TABLE_ALIAS", logTableAlias);
		return actLogDao.getColumnByTable(param);
	}

	public Map<String, Object> getDataByKey(String logTableName, String key,
			String keyVal) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("TABLE_NAME", logTableName);
		param.put("KEY", key);
		param.put("KEY_VALUE", keyVal);
		return actLogDao.getDataByKey(param);
	}

	/**
	 * 通过表名和主键获得最大的流水号ID
	 * @param tableName
	 * @param key
	 * @return
	 */
	public String getCurSeq(String tableName,String key) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("TABLE_NAME", tableName);
		param.put("KEY", key);
		return actLogDao.getCurSeq(param);
	}
	public int insert(Map<String, Object> log) {
		 actLogDao.insert(log);
		return MapUtils.getIntValue(log, "NUM_ID");
	}

	public void insertField(List<Map<String, Object>> fieldLog) {
		actLogDao.insertField(fieldLog);
	}

	public void insertFieldClob(List<Map<String, Object>> fieldLog) {
		actLogDao.insertFieldClob(fieldLog);
	}
	
	public void insertFieldClobEX(Map<String, Object> field) {
		actLogDao.insertFieldClobEX(field);
	}
	/**
	 * 查询公用 列
	 * @return
	 */
	public List<Map<String, Object>> getPubColumn() {
		return actLogDao.getPubColumn();
	}
	/**
	 * 查询 被操作人的姓名、人事编号、方案
	 * @param logTableAlias 表名
	 * @param keyVal 流水号
	 * @return
	 */
	public Map<String, Object> pub(String logTableAlias, String keyVal) {
		Map<String, Object> map = Maps.newHashMap();
		if("T_PERSONAL_SUMMARY_G".equals(logTableAlias) || "T_PERSONAL_SUMMARY_P".equals(logTableAlias)){//个人总结，师德自评
			map.put("ID_NUM", keyVal);
			map.put("TABLE_NAME", "T_PERSONAL_SUMMARY");
		}else if("T_TEAM_SUMMARY".equals(logTableAlias)){//团队总结
			map.put("JC_TYPE", "0");
			map.put("ID_NUM", keyVal);
			map.put("TABLE_NAME", logTableAlias);
		}else if("T_PROJECT_INFORMATION".equals(logTableAlias)|| "T_QUAN_TEAM_MANAGE".equals(logTableAlias)){//公共功能(方案管理、团队管理)
			return null;
		}else{
			map.put("NUM_ID", keyVal);
			map.put("TABLE_NAME", logTableAlias);
		}
		return actLogDao.getPub(map);
	}
	/**
	 * 查询 表名
	 * @param logTableAlias 
	 * @return
	 */
	public Map<String, Object> getTableName(String logTableAlias) {
		Map<String, Object> map = Maps.newHashMap();
		if(StringUtils.isNotEmpty(logTableAlias)){
			map = actLogDao.getTableName(logTableAlias);
		}
		return map;
	}
	
	@SuppressWarnings("unused")
	public Page<Map<String,Object>> list(Page<Map<String,Object>> page,  FormBean formbean) {
		Map<String, Object> bean = formbean.getBean();
		PaginationMap map =(PaginationMap) formbean.getBean();
		map.setPage(page);
		page.setList(actLogDao.list(map));
		return page;
	}
	
	public Map<String, Object> selectList(FormBean formbean){
		Map<String, Object> list = actLogDao.selectList((PaginationMap) formbean.getBean());
		return list;
	}
	
	public List<Map<String, Object>> typeList(FormBean formbean){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = actLogDao.typeList((PaginationMap) formbean.getBean());
		return list;
	}
}
