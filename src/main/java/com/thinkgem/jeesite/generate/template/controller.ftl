package ${packageName}.${moduleName}.web.${subModuleName};

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jr.utils.FormBean;
import com.google.common.collect.Maps;
import com.jr.modules.common.utils.StandardManage;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import ${packageName}.${moduleName}.service.${subModuleName}.${ClassName}Service;
/**
* ${functionName}
* @author ${classAuthor}
*
*/
@Controller
@RequestMapping(value = "${r"${adminPath}"}/${moduleName}/${subModuleName}/${className}")
public class ${ClassName}Controller extends BaseController {

@Autowired
private ${ClassName}Service ${className}Service ;

@RequestMapping(value = { "list", "" })
@RequiresPermissions("${subModuleName}:${className}:view")
public String list(FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
	Page<Map<String, Object>> page = ${className}Service.list(new Page<Map<String, Object>>(request, response), formbean);
	model.addAttribute("map", formbean);
	model.addAttribute("page", page);
	return "modules/${moduleName}/${subModuleName}/${className}List";
}

@RequestMapping(value = { "form"})
@RequiresPermissions("${subModuleName}:${className}:view")
public String form(String id,FormBean formbean, Model model) {
	Map<String, Object> bean = Maps.newHashMap();
	if(StringUtils.isNotEmpty(id)){
		bean = ${className}Service.get(id);
	}
	model.addAttribute("map", new FormBean(bean));
	return "modules/${moduleName}/${subModuleName}/${className}Form";
}


@RequestMapping(value = "save")
@RequiresPermissions("${subModuleName}:${className}:edit")
public @ResponseBody String save(FormBean formbean,HttpServletRequest request, HttpServletResponse response){
	Map<String, Object> bean = formbean.getBean();
	String result = StandardManage.OPERATE_SUCCESS;
	try {
		${className}Service.save(bean);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
	return result;
}

@RequestMapping(value = "delete")
@RequiresPermissions("${subModuleName}:${className}:edit")
public @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response){
	String result = StandardManage.OPERATE_SUCCESS;
	String ids = request.getParameter("ids");
	if (StringUtils.isEmpty(ids)) {
		return StandardManage.OPERATE_FAIL;
	}
	String[] idArray = ids.split(",");
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("ids", idArray);
	try {
		${className}Service.delete(map);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
		return result;
	}
}
