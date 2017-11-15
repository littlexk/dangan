package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.modules.sys.security.MailSenderManager;

/**
 * 邮件发送工具类
 * @ClassName: MailUtils 
 * @Description: TODO 负责生成邮件发送管理工具
 * @author: WangCong
 * @date: 2016年5月6日 下午5:33:33
 */
public class MailUtils {
	/**
     * 邮件发送管理工具
     */
    private static MailSenderManager mailSenderManager = null;
 
    /**
     * 获取邮件发送管理工具
     * @Title: getMailSenderManager 
     * @Description: TODO 获取邮件发送管理工具
     * @Date: 2016年5月6日 下午5:20:05
     * @author: WangCong
     * @return 邮件发送管理工具
     */
    public static MailSenderManager getMailSenderManager() {
    	mailSenderManager = new MailSenderManager();
    	return mailSenderManager;
    }
}
