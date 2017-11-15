package com.thinkgem.jeesite.modules.sys.dao;


import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
@MyBatisDao
	
public interface StfBaseDAO {
		
	void insert(User user);
	
	Map<String, Object> getUser(String userId); 
	
	void modify(Map<String, Object> userMap);
	    
}
