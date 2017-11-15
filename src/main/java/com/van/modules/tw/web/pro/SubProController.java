package com.van.modules.tw.web.pro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.utils.FormBean;
import com.jr.utils.StandardManage;
import org.apache.commons.collections.MapUtils;
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

import com.van.modules.tw.service.pro.SubProService;
/**
* 子产品
* @author yiw
*
*/
@Controller
@RequestMapping(value = "${adminPath}/tw/pro/subPro/")
public class SubProController extends BaseController {

@Autowired
private SubProService subProService ;

@RequestMapping(value = { "list", "" })
@RequiresPermissions("pro:subPro:view")
public String list(String priProId, FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
	if(StringUtils.isNotBlank(priProId)){
		formbean.getBean().put("PRI_PRO_ID",priProId);
	}
	Page<Map<String, Object>> page = subProService.list(new Page<Map<String, Object>>(request, response), formbean);
	model.addAttribute("map", formbean);
	model.addAttribute("page", page);
	return "modules/tw/pro/subProList";
}

@RequestMapping(value = { "form"})
@RequiresPermissions("pro:subPro:view")
public String form(String priProId,String id, Model model) {
	Map<String, Object> bean = Maps.newHashMap();
	if(StringUtils.isNotEmpty(id)){
		bean = subProService.get(id);
	}
	if(StringUtils.isNotBlank(priProId)){
		bean.put("PRI_PRO_ID",priProId);
	}
	model.addAttribute("map", new FormBean(bean));
	return "modules/tw/pro/subProForm";
}


@RequestMapping(value = "save")
@RequiresPermissions("pro:subPro:edit")
public @ResponseBody String save(FormBean formbean){
	Map<String, Object> bean = formbean.getBean();
	String result = StandardManage.OPERATE_SUCCESS;
	try {
		subProService.save(bean);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
	return result;
}

@RequestMapping(value = "delete")
@RequiresPermissions("pro:subPro:edit")
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
		subProService.delete(map);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
		return result;
	}


	@RequestMapping(value = { "proSelectList"})
	@RequiresPermissions("pro:subPro:view")
	public String proSelectList(FormBean formbean,  Model model) {
		Map<String,Object> bean = formbean.getBean();
		if(StringUtils.isBlank(MapUtils.getString(bean,"TYPE"))){
			bean.put("TYPE","01");
		}
		List<Map<String, Object>> list = subProService.proSelectList(formbean);
		model.addAttribute("map", formbean);
		model.addAttribute("list", list);
		return "modules/tw/pro/subProSelectList";
	}
}
