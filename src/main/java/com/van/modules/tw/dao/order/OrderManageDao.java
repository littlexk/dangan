package com.van.modules.tw.dao.order;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrderManageDao {

 List<Map<String, Object>> list(Map<String,Object> map);

 void insert(Map<String, Object> bean);

 Map<String, Object> get(String id);

 void update(Map<String, Object> bean);

 void delete(Map<String, Object> map);


 List<Map<String, Object>> getDetailByOrderId(Map<String,Object> map);
 void insertDetail(Map<String, Object> bean);
 void deleteDetailByOrderId(Map<String, Object> map);
}
