package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ShortcutMenuDao {
    List<Map<String,Object>> findByUserId(String id);
    void save(Map<String,Object> obj);
    void delMenuByUserId(String userId);
    
}
