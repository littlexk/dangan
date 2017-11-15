package com.van.modules.tw.service.pro;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;

import com.van.modules.tw.dao.pro.PriProDao;

/**
* @author yiw
*/
@Service
public class PriProService extends BaseService {

@Autowired
private PriProDao priProDao;


public Page<Map<String, Object>> list(Page<Map<String, Object>> page,FormBean formbean) {
    PaginationMap map = (PaginationMap) formbean.getBean();
    map.setPage(page);
    page.setList(priProDao.list(map));
    return page;
}


public void save(Map<String, Object> bean) {
    String id = MapUtils.getString(bean, "ID");
    if (id==null||"".equals(id)) {
        priProDao.insert(bean);
    }else {
        Map<String, Object> _bean = priProDao.get(id);
        _bean.putAll(bean);
        priProDao.update(_bean);
    }
}

public Map<String, Object> get(String id) {
    return priProDao.get(id);
}

public void delete(Map<String, Object> map){
    priProDao.delete(map);
}

    public List<Map<String,Object>> getPriProByType(String type) {
    Map<String,Object> bean = Maps.newHashMap();
    bean.put("TYPE",type);
     return priProDao.list(bean);
    }
}
