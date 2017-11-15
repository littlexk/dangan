package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.SubjectDao;

@Service
@Transactional(readOnly = true)
public class SubjectService extends BaseService{
	
	@Autowired
	private SubjectDao subjectDao;
	
	public void getTreeDataList(String id,List<Map<String,Object>> list){
		List<Map<String,Object>> entityList = null;
		if(id == null || "".equals(id)){
			entityList = subjectDao.getParentTreeNodes();
		}else{
			entityList = subjectDao.getTreeNodesByParentId(id);
		}
		for(int i = 0; i < entityList.size();i++){
			Map<String,Object> e = entityList.get(i);
			String parentId = String.valueOf(e.get("PARENT_ID"));
			if(parentId == null || "".equals(parentId)){
				parentId = "0";
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", e.get("CODE"));
			map.put("name",e.get("NAME"));
			map.put("pId",parentId);
			boolean isLeaf =  "1".equals(e.get("IS_LEAF"))?true:false;
			map.put("isParent",!isLeaf);
			list.add(map);
		}
	}

}
