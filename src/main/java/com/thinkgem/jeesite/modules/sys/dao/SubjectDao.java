package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SubjectDao {
	
	public List<Map<String,Object>> getParentTreeNodes();
	public List<Map<String,Object>> getTreeNodesByParentId(String parentId);

}
