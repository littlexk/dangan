package com.van.modules.tw.dao.pro;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SubProDao {

 List<Map<String, Object>> list(Map<String,Object> map);

 void insert(Map<String, Object> bean);

 Map<String, Object> get(String id);

 void update(Map<String, Object> bean);

 void delete(Map<String, Object> map);

}
