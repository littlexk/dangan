package com.van.modules.tw.service.order;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;

import com.van.modules.tw.dao.order.OrderManageDao;

/**
* @author yiw
*/
@Service
public class OrderManageService extends BaseService {

@Autowired
private OrderManageDao orderManageDao;


public Page<Map<String, Object>> list(Page<Map<String, Object>> page,FormBean formbean) {
    PaginationMap map = (PaginationMap) formbean.getBean();
    map.setPage(page);
    page.setList(orderManageDao.list(map));
    return page;
}


public void save(Map<String, Object> bean) {
    String id = MapUtils.getString(bean, "ID");
    if (id==null||"".equals(id)) {
        orderManageDao.insert(bean);
    }else {
        Map<String, Object> _bean = orderManageDao.get(id);
        _bean.putAll(bean);
        orderManageDao.update(_bean);
    }
}

public Map<String, Object> get(String id) {
    return orderManageDao.get(id);
}

public void delete(Map<String, Object> map){
    orderManageDao.delete(map);
}

    public List<Map<String, Object>> getDetailByOrderId(String orderId) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if(StringUtils.isNotBlank(orderId)) {
            Map qParams= Maps.newHashMap();
            qParams.put("ORDER_ID",orderId);
            list =  orderManageDao.getDetailByOrderId(qParams);
        }
        return list;
    }
}
