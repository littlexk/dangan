package com.van.modules.tw.service.pro;

import java.util.List;
import java.util.Map;

import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;

import com.van.modules.tw.dao.pro.SubProDao;

/**
* @author yiw
*/
@Service
public class SubProService extends BaseService {

@Autowired
private SubProDao subProDao;


public Page<Map<String, Object>> list(Page<Map<String, Object>> page,FormBean formbean) {
    PaginationMap map = (PaginationMap) formbean.getBean();
    map.setPage(page);
    page.setList(subProDao.list(map));
    return page;
}


public void save(Map<String, Object> bean) {
    String id = MapUtils.getString(bean, "ID");
    if (id==null||"".equals(id)) {
        subProDao.insert(bean);
    }else {
        Map<String, Object> _bean = subProDao.get(id);
        _bean.putAll(bean);
        subProDao.update(_bean);
    }
}

public Map<String, Object> get(String id) {
    return subProDao.get(id);
}

public void delete(Map<String, Object> map){
    subProDao.delete(map);
}

    public List<Map<String,Object>> proSelectList(FormBean formbean) {
        return  subProDao.list(formbean.getBean());
    }
}
