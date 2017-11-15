package com.jr.component.cache;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/** 
 * @author Changjielai
 * @version 2017年7月3日 下午3:25:27 
 * spring容器初始化完毕加载指定数据到缓存
 */
@Service
public class CacheDefineConfigue2 implements InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("默认缓存初始化开始(CacheDefineConfigue2.java)================================");
			//加载单位列表
			UserUtils.getAllOfficeList();
		System.out.println("默认缓存初始化完毕(CacheDefineConfigue2.java)================================");
	}
}
