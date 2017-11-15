package com.thinkgem.jeesite.modules.sys.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.SysDao;

/**
 * 首页
 * @author Changjielai
 *
 */
@Service
@Transactional(readOnly = true)
public class SysService extends BaseService {

	@Autowired
	private SysDao sysDao;
	/**
	 * 
	 * @author Changjielai
	 * @param param
	 * @return
	 */
	public Map<String, Object> getIndexData(Map<String, Object> param) {
		Map<String, Object> data = sysDao.getIndexData(param);
		if (data!=null) {
		}
		return data;
	}
}
