package com.thinkgem.jeesite.modules.sys.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.utils.StandardManage;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jr.utils.FormBean;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.DataInterfaceService;
import com.thinkgem.jeesite.modules.sys.service.SysService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 菜单Controller
 * @author JIN
 * @version 2016-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bus")
public class SysController extends BaseController{
	
	@Autowired
	private SysService sysService;
	
	@Autowired
	private DataInterfaceService dataInterfaceService;
	
	/*@RequiresPermissions("sys:menu:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(Model model, String id) {
		model.addAttribute("id", id);
		return "modules/sys/systemList";
	}
	/**
	 * 获取首页数据
	 * @author Changjielai

	 * @return
	 */
	@RequestMapping(value = {"indexData"})
	public @ResponseBody Map<String, Object> indexData(HttpServletRequest request,HttpServletResponse response) {
		String year = request.getParameter("year");
		String type = request.getParameter("type");
		String subType = request.getParameter("subType");
		String sub2Type = request.getParameter("sub2Type");
		String orgId = request.getParameter("orgId");
		Map<String, Object> param = Maps.newHashMap();
		if (StringUtils.isNotEmpty(year)) {
			param.put("YEAR", year);
		}
		if (StringUtils.isNotEmpty(type)) {
			param.put("TYPE", type);
		}
		if (StringUtils.isNotEmpty(subType)) {
			param.put("SUB_TYPE", subType);
		}
		if (StringUtils.isNotEmpty(sub2Type)) {
			param.put("SUB2_TYPE", sub2Type);
		}
		if (StringUtils.isNotEmpty(orgId)) {
			param.put("ORG_ID", orgId);
		}else {
			param.put("ORG_ID", UserUtils.getUser().getOffice().getParent().getId());
		}
		return sysService.getIndexData(param);
	}
	/**
	 * 首页统计执行页面
	 * @author Changjielai
	 * @param type
	 * @param formBean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"excuIndexForm"})
	public String excuIndexForm(String type,FormBean formBean,HttpServletRequest request,HttpServletResponse response,Model model) {
		if (StringUtils.isEmpty(type)) {
			addMessage(model, "服务信息为空");
		}else {
			Map<String, Object> dataInfo = dataInterfaceService.getDataInfoByType(type);
			if (dataInfo!=null) {
				formBean.setBean(dataInfo);
				formBean.getBean().put("YEAR", new Date());
			}
		}
		model.addAttribute("type", type);
		model.addAttribute("map", formBean);
		return "modules/sys/excuIndexForm";
	}
	/**
	 * 首页统计执行
	 * @author Changjielai
	 * @param formBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "excuIndex")
	public @ResponseBody String excuIndex(FormBean formBean,HttpServletRequest request, HttpServletResponse response){
		String type = MapUtils.getString(formBean.getBean(), "TYPE");
		String orgId = MapUtils.getString(formBean.getBean(), "ORG_ID");
		String year = MapUtils.getString(formBean.getBean(), "YEAR");
		String param = "";
		Map<String, Object> bean = Maps.newHashMap();
		bean.put("TYPE", type);
		if (StringUtils.isNotEmpty(orgId)) {
			param += orgId;
			Map<String, Object> orgInfo =Maps.newHashMap();
			bean.put("REMARKS", "更新:"+year+"年"+MapUtils.getString(orgInfo, "ORG_NAME")+"的统计信息；");
		}else {
			bean.put("REMARKS", "更新:"+year+"年所有党组织的统计信息；");
		}
		param += ","+year+","+UserUtils.getUser().getName();
		bean.put("param", param);
		
		String result = StandardManage.OPERATE_SUCCESS;
		try {
			dataInterfaceService.dataInterfaceDo(bean);
		} catch (Exception e) {
			e.printStackTrace();
			result = StandardManage.OPERATE_FAIL;
		}
		return result;
	}
}
