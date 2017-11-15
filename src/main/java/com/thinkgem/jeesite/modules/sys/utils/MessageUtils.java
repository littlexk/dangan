package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.modules.sys.security.MessageSenderManager;

/**
 * 短信发送工具类
 * @ClassName: MessageUtils 
 * @Description: TODO 负责生成短信发送工具类
 * @author: WangCong
 * @date: 2016年5月20日 上午11:20:12
 */
public class MessageUtils {

	/**
     * 短信发送管理工具
     */
    private static MessageSenderManager messageSenderManager = null;
 
    /**
     * 获取短信发送管理工具
     * @Title: getMessageSenderManager 
     * @Description: TODO 获取短信发送管理工具
     * @Date: 2016年5月6日 下午5:20:05
     * @author: WangCong
     * @return 短信发送管理工具
     */
    public static MessageSenderManager getMessageSenderManager() {
    	messageSenderManager = new MessageSenderManager();
    	return messageSenderManager;
    }
}
