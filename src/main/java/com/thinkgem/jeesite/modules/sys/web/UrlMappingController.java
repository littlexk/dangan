package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 开发人员使用
 * 获取当前页面请求对应的Controller及方法
 * @ClassName: UrlMappingController 
 * @author: ChangjieLai
 * @date: 2016年5月11日 下午3:56:31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/mapping")
public class UrlMappingController extends BaseController{
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	private static List<Map<String, String>> urlList;//映射列表
	
	/**
	 * 初始化映射列表
	 * @Title: init 
	 * @author ChangjieLai
	 * @Date: 2016年5月11日 下午3:57:41
	 */
	private void init() {
		urlList = Lists.newArrayList();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();  
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {  
            HashMap<String, String> hashMap = new HashMap<String, String>();  
            RequestMappingInfo info = m.getKey();  
            HandlerMethod method = m.getValue();  
            PatternsRequestCondition p = info.getPatternsCondition();  
            for (String url : p.getPatterns()) {  
                hashMap.put("url", url);  
            }
            hashMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名  
            hashMap.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();  
            String type = methodsCondition.toString();  
            if (type != null && type.startsWith("[") && type.endsWith("]")) {  
                type = type.substring(1, type.length() - 1);  
                hashMap.put("type", type); // 方法名  
            }  
            urlList.add(hashMap);  
        }  
    }
	/**
	 * 获取url对应的Controller和方法
	 * @Title: list 
	 * @author ChangjieLai
	 * @Date: 2016年5月11日 下午3:57:55
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"mapping", ""})
	public @ResponseBody Map<String, Object> list(HttpServletRequest request) {
		//判断是否打开调试开关
		if (!"true".equals(Global.getConfig("gettingUrl"))) {
			return null;
		}
		
		//获取请求的url并做处理
		String url = request.getParameter("url");
		String adminPath = Global.getConfig("adminPath")+"/";
		int start = url.indexOf(adminPath);
		boolean b = url.substring(url.length()-1, url.length()).equals("/");
		if (b) {
			url = url.substring(0,url.length()-1);
		}
		url = url.substring(start);
		int last = url.lastIndexOf("/");
		String urlSort = url.substring(0,last);
		if (urlList==null) {
			init();
		}
		
		//遍历映射列表，找出映射关系
		Map<String, String> urlMap = Maps.newHashMap();
		String classNameString = "";
		String method = "";
		boolean hadFound = false;
		for (Map<String, String> map : urlList) {
			if (map.get("url").equals(url)) {
				urlMap = map;
				classNameString = urlMap.get("className");
				method = urlMap.get("method");
				hadFound = true;
				break;
			}
		}
		if (!hadFound) {
			for (Map<String, String> map : urlList) {
				if(map.get("url").equals(urlSort)){
					urlMap = map;
					classNameString = urlMap.get("className");
					method = urlMap.get("method");
					hadFound = true;
					break;
				}
			}
		}
		if (hadFound) {
			Map<String, Object> pathMap = Maps.newHashMap();
			pathMap.put("path", classNameString+"."+method+"()");
			return pathMap;
		}else {
			return null;
		}
		
	}
}
