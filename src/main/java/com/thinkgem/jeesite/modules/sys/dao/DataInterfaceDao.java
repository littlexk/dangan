package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;


import com.jr.utils.PaginationMap;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 接口服务
 * @author Changjielai
 *
 */
@MyBatisDao
public interface DataInterfaceDao{

	List<Map<String, Object>> getDataList(PaginationMap map);

	void insertData(Map<String, Object> bean);

	void updateData(Map<String, Object> dataInfo);

	Map<String, Object> getDataInfo(Map<String, Object> bean);

	void excuPro(Map<String, Object> param);
}
