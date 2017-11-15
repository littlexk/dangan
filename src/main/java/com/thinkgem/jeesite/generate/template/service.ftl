package ${packageName}.${moduleName}.service.${subModuleName};

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jr.modules.common.empUtils.service.EmpUtilsService;
import com.jr.utils.FormBean;
import com.jr.utils.FormBeanUtils;
import com.jr.utils.PaginationMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;

import ${packageName}.${moduleName}.dao.${subModuleName}.${ClassName}Dao;

/**
* @author ${classAuthor}
*/
@Service
public class ${ClassName}Service extends BaseService {

@Autowired
private ${ClassName}Dao ${className}Dao;


public Page<Map<String, Object>> list(Page<Map<String, Object>> page,FormBean formbean) {
    PaginationMap map = (PaginationMap) formbean.getBean();
    map.setPage(page);
    page.setList(${className}Dao.list(map));
    return page;
}


public void save(Map<String, Object> bean) {
    String id = MapUtils.getString(bean, "${priColName}");
    if (id==null||"".equals(id)) {
        ${className}Dao.insert(bean);
    }else {
        Map<String, Object> _bean = ${className}Dao.get(id);
        FormBeanUtils.dealNullToString(bean);
        _bean.putAll(bean);
        ${className}Dao.update(_bean);
    }
}

public Map<String, Object> get(String id) {
    return ${className}Dao.get(id);
}

public void delete(Map<String, Object> map){
    ${className}Dao.delete(map);
}

}
