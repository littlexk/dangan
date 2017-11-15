package com.jr.utils;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class FormBean extends BaseEntity<FormBean> {
	private static final long serialVersionUID = -6948393725334646497L;
	
	private Map<String,Object> bean = new PaginationMap();
	
	public FormBean() {
	}
	
	public FormBean(Map<String, Object> bean) {
		this.bean = bean;
	}

	public Map<String, Object> getBean() {
		return bean;
	}

	public void setBean(Map<String, Object> bean) {
		this.bean = bean;
	}
	
	/**
	 * 缓存查询条件
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void init(HttpServletRequest request, HttpServletResponse response){
		if (request.getParameter("repage")!=null){
			if(request!=null){
				Object obj  = UserUtils.getCache("queryBean");
				if(obj!=null){
					this.bean = (Map<String,Object>)obj;
				}
			}
		}else{
			UserUtils.putCache("queryBean",this.bean);
		}
	}
}
