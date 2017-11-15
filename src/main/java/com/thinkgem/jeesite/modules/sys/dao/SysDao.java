package com.thinkgem.jeesite.modules.sys.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 首页
 * @author Changjielai
 *
 */
@MyBatisDao
public interface SysDao{

	Map<String, Object> getIndexData(Map<String, Object> param);
}
