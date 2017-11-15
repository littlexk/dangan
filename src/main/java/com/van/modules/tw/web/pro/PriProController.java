package com.van.modules.tw.web.pro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.utils.FormBean;
import com.jr.utils.StandardManage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.van.modules.tw.service.pro.PriProService;
/**
* 主产品
* @author yiw
*
*/
@Controller
@RequestMapping(value = "${adminPath}/tw/pro/priPro/")
public class PriProController extends BaseController {

@Autowired
private PriProService priProService ;

@RequestMapping(value = { "list", "" })
@RequiresPermissions("pro:priPro:view")
public String list(FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
	Page<Map<String, Object>> page = priProService.list(new Page<Map<String, Object>>(request, response), formbean);
	model.addAttribute("map", formbean);
	model.addAttribute("page", page);
	return "modules/tw/pro/priProList";
}

@RequestMapping(value = { "form"})
@RequiresPermissions("pro:priPro:view")
public String form(String id,FormBean formbean, Model model) {
	Map<String, Object> bean = Maps.newHashMap();
	if(StringUtils.isNotEmpty(id)){
		bean = priProService.get(id);
	}
	model.addAttribute("map", new FormBean(bean));
	return "modules/tw/pro/priProForm";
}


@RequestMapping(value = "save")
@RequiresPermissions("pro:priPro:edit")
public @ResponseBody String save(FormBean formbean,HttpServletRequest request, HttpServletResponse response){
	Map<String, Object> bean = formbean.getBean();
	String result = StandardManage.OPERATE_SUCCESS;
	try {
		priProService.save(bean);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
	return result;
}

@RequestMapping(value = "delete")
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
		priProService.delete(map);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
		return result;
	}
	@RequestMapping(value = { "getPriProByType"})
	@RequiresPermissions("pro:priPro:view")
	public @ResponseBody List<Map<String, Object>>  getPriProByType(String type) {
		List<Map<String, Object>> list = priProService.getPriProByType(type);
		return list;
	}
}
