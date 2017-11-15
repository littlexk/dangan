package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.Map;

import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.DataInterfaceDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 接口服务
 * @author Changjielai
 *
 */
@Service
@Transactional(readOnly = true)
public class DataInterfaceService extends BaseService {

	@Autowired
	private DataInterfaceDao dataInterfaceDao;
	/**
	 * 获取接口服务列表
	 * @author Changjielai
	 * @param formbean 
	 * @param page 
	 * @return
	 */
	public Page<Map<String, Object>> getDataList(Page<Map<String, Object>> page, FormBean formbean) {
		PaginationMap map = (PaginationMap) formbean.getBean();
		map.setPage(page);
		page.setList(dataInterfaceDao.getDataList(map));
		return page;
	}
	/**
	 * 接口服务信息保存
	 * @author Changjielai
	 * @param bean
	 */
	public void dataInterfaceSave(Map<String, Object> bean) {
		bean.put("OPERATOR", UserUtils.getUser().getName());
		Map<String, Object> param = Maps.newHashMap();
		param.put("TYPE", MapUtils.getString(bean, "TYPE"));
		Map<String, Object> dataInfo = dataInterfaceDao.getDataInfo(param);
		if (dataInfo!=null) {
			dataInfo.putAll(bean);
			dataInterfaceDao.updateData(dataInfo);
		}else {
			dataInterfaceDao.insertData(bean);
		}
	}
	/**
	 * 执行接口服务
	 * @author Changjielai
	 * @param bean
	 */
	@Transactional(rollbackFor=Exception.class)
	public void dataInterfaceDo(Map<String, Object> bean) {
		StringBuffer sql = new StringBuffer(" call ");
		Map<String, Object> dataInfo = dataInterfaceDao.getDataInfo(bean);
		sql.append(MapUtils.getString(dataInfo, "EXCU_PRO"));
		sql.append("(");
		String paramStr = MapUtils.getString(bean, "param");
		if (StringUtils.isNotEmpty(paramStr)) {
			String[] paramArray = paramStr.split(",");
			for (int i=0;i<paramArray.length;i++) {
				if(i==paramArray.length-1){
					sql.append("'"+paramArray[i]+"'");
				}else {
					sql.append("'"+paramArray[i]+"',");
				}
			}
		}
		sql.append(") ");
		Map<String, Object> param = Maps.newHashMap();
		param.put("SQL", sql.toString());
		dataInterfaceDao.excuPro(param);
		dataInfo.put("LAST_TIME", new Date());
		dataInfo.put("OPERATOR", UserUtils.getUser().getName());
		if (StringUtils.isNotEmpty(MapUtils.getString(bean, "REMARKS"))) {
			dataInfo.put("REMARKS", MapUtils.getString(bean, "REMARKS"));
		}
		dataInterfaceDao.updateData(dataInfo);
	}
	/**
	 * 根据type获取接口服务信息
	 * @author Changjielai
	 * @param type
	 * @return
	 */
	public Map<String, Object> getDataInfoByType(String type) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("TYPE",type);
		return dataInterfaceDao.getDataInfo(param);
	}
}
