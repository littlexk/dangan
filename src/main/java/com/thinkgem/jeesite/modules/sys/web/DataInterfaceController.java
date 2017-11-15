package com.thinkgem.jeesite.modules.sys.web;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.utils.StandardManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jr.utils.FormBean;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.DataInterfaceService;


/**
 * 接口服务
 * @author Changjielai
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dataInterface")
public class DataInterfaceController extends BaseController {

	@Autowired
	private DataInterfaceService dataInterfaceService;
	
	/**
	 * 接口服务页面
	 * @author Changjielai
	 * @param formbean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "dataInterfaceList"})
	public String dataInterfaceList(FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String, Object>> page = dataInterfaceService.getDataList(new Page<Map<String, Object>>(request, response), formbean);
		model.addAttribute("page", page);
		model.addAttribute("map", formbean);
		return "modules/sys/dataInterfaceList";
	}
	/**
	 * 接口服务维护页面
	 * @author Changjielai
	 * @param type
	 * @param formbean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "dataInterfaceForm"})
	public String dataInterfaceForm(String type,String opType,FormBean formbean, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotEmpty(type)) {
			Map<String, Object> bean = dataInterfaceService.getDataInfoByType(type);
			if (bean!=null) {
				formbean.setBean(bean);
			}
		}
		model.addAttribute("map", formbean);
		model.addAttribute("opType", opType);
		return "modules/sys/dataInterfaceForm";
	}
	/**
	 * 接口服务信息保存
	 * @author Changjielai
	 * @param formBean
	 * @return
	 */
	@RequestMapping(value = "dataInterfaceSave")
	public @ResponseBody String dataInterfaceSave(FormBean formBean){
		Map<String, Object> bean = formBean.getBean();
		String result = StandardManage.OPERATE_SUCCESS;
		try {
			dataInterfaceService.dataInterfaceSave(bean);
		} catch (Exception e) {
			e.printStackTrace();
			result = StandardManage.OPERATE_FAIL;
		}
		return result;
	}
	/**
	 * 开启/关闭接口服务
	 * @author Changjielai
	 * @param formbean
	 * @param type
	 * @param status
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = { "dataInterfaceTurn"})
	public String dataInterfaceTurn(FormBean formbean,String type,String status, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("TYPE", type);
		param.put("STATUS", status);
		try {
			dataInterfaceService.dataInterfaceSave(param);
			addMessage(redirectAttributes, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "操作失败");
		}
		
		return "redirect:" + Global.getAdminPath() + "/sys/dataInterface/dataInterfaceList";
	}
	/**
	 * 接口服务执行
	 * @author Changjielai
	 * @param formBean
	 * @return
	 */
	@RequestMapping(value = "excuDo")
	public @ResponseBody String excuDo(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		String param = request.getParameter("param");
		Map<String, Object> bean = Maps.newHashMap();
		bean.put("TYPE", type);
		if (StringUtils.isNotEmpty(param)) {
			bean.put("param", param);
		}
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
