package com.van.modules.tw.web.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.utils.StandardManage;
import com.thinkgem.jeesite.common.utils.OrderNumUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jr.utils.FormBean;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.van.modules.tw.service.order.OrderManageService;
/**
* 订单管理
* @author yiw
*
*/
@Controller
@RequestMapping(value = "${adminPath}/tw/order/orderManage")
public class OrderManageController extends BaseController {

@Autowired
private OrderManageService orderManageService ;

@RequestMapping(value = { "list", "" })
@RequiresPermissions("order:orderManage:view")
public String list(FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
	Page<Map<String, Object>> page = orderManageService.list(new Page<Map<String, Object>>(request, response), formbean);
	model.addAttribute("map", formbean);
	model.addAttribute("page", page);
	return "modules/tw/order/orderManageList";
}

@RequestMapping(value = { "form"})
@RequiresPermissions("order:orderManage:view")
public String form(String id,FormBean formbean, Model model) {
	Map<String, Object> bean = Maps.newHashMap();
	if(StringUtils.isNotEmpty(id)){
		bean = orderManageService.get(id);
	}
	//生成订单编号
	if(StringUtils.isBlank(MapUtils.getString(bean,"ORDER_NUM"))){
	   bean.put("ORDER_NUM", OrderNumUtils.getOrderNum());
	}

	model.addAttribute("map", new FormBean(bean));
	return "modules/tw/order/orderManageForm";
}


@RequestMapping(value = "save")
@RequiresPermissions("order:orderManage:edit")
public @ResponseBody String save(FormBean formbean,HttpServletRequest request, HttpServletResponse response){
	Map<String, Object> bean = formbean.getBean();
	String result = StandardManage.OPERATE_SUCCESS;
	try {
		orderManageService.save(bean);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
	return result;
}

@RequestMapping(value = "delete")
@RequiresPermissions("order:orderManage:edit")
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
		orderManageService.delete(map);
	} catch (Exception e) {
		e.printStackTrace();
		result = StandardManage.OPERATE_FAIL;
	}
		return result;
	}


	@RequestMapping(value = "getDetailByOrderId")
	@RequiresPermissions("order:orderManage:edit")
	public @ResponseBody List<Map<String,Object>> getDetailByOrderId(String orderId){
		return orderManageService.getDetailByOrderId(orderId);
	}

}
