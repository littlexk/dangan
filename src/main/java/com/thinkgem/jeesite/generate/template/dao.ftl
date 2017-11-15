package ${packageName}.${moduleName}.dao.${subModuleName};

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ${ClassName}Dao {

 List<Map<String, Object>> list(Map<String,Object> map);

 void insert(Map<String, Object> bean);

 Map<String, Object> get(String id);

 void update(Map<String, Object> bean);

 void delete(Map<String, Object> map);

}
