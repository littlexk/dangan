package com.jr.component.cache;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/** 
 * @author Changjielai
 * @version 2017年7月3日 下午3:25:27 
 * spring容器初始化完毕加载指定数据到缓存
 */
@Component("CacheDefineConfigue")
public class CacheDefineConfigue implements ApplicationListener<ContextRefreshedEvent>{
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		System.out.println("默认缓存初始化开始(CacheDefineConfigue.java)================================");
		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
			//加载单位列表
			UserUtils.getAllOfficeList();
		}
		System.out.println("默认缓存初始化完毕(CacheDefineConfigue.java)================================");
	}
}
