package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.security.Cryptos;
import com.thinkgem.jeesite.modules.sys.entity.SimpleMail;
import com.thinkgem.jeesite.modules.sys.security.MailSenderManager;
import com.thinkgem.jeesite.modules.sys.utils.MailUtils;

/**
 * 邮件发送服务层
 * @ClassName: MailSendService 
 * @Description: TODO 负责邮件发送相关逻辑处理
 * @author: WangCong
 * @date: 2016年5月6日 下午5:48:00
 */
@Service
@Transactional(readOnly = true)
public class MailSendService {

	//AES加密算法密钥
	private static final byte[] key = {-74,-61,-75,53,-104,122,-77,-100,-112,119,-66,75,123,86,44,60};
	
	private final static String FLAG_SUCCESS = "success";
	
	private final static String FLAG_FAILURE = "failure";
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 发送邮件
	 * @Title: sendMail 
	 * @Description: TODO 使用系统默认邮箱进行邮件发送
	 * @Date: 2016年5月6日 下午6:01:11
	 * @author: WangCong
	 * @param mail 简单邮件类
	 * @return 将邮件发送的结果信息封装到List集合中(List集合的数据类型为Map，该Map会有两条记录，一条的key为flag作为成功或失败标识，一条的key为remark作为失败信息记录)
	 */
	public List<Map<String, String>> sendMail(SimpleMail mail){
		//接收者
		Map<String, String> recipients = mail.getRecipients();
		//获取系统当前的默认邮箱
		List<Map<String, Object>> list = mailService.getDefaultMail();
		//邮件发送结果记录
		Map<String, String> tempMap = null;
		//将所有邮件发送结果记录添加到该List集合中，并作为最终结果返回
		List<Map<String,String>> result = Lists.newArrayList();
		
		String subject = mail.getSubject();
		String content = mail.getContent();
		Map<String, Object> defaultMail =  list.get(0);
		String loginName = (String)defaultMail.get("LOGIN_NAME");
		String password = (String)defaultMail.get("PASSWORD");
		//将密码进行AES算法解密
		password = Cryptos.aesDecrypt(Cryptos.parseHexStr2Byte(password), key);
		String smtpHost = (String)defaultMail.get("SMTP_HOST");
		String smtpPort = (String)defaultMail.get("SMTP_PORT");
		String smtpSsl = StringUtils.equals((String)defaultMail.get("SMTP_SSL"), "1") ? "true" : "false";
		//获取邮件发送管理工具
		MailSenderManager mailSenderManager = MailUtils.getMailSenderManager();
		//邮件发送相关参数初始化
		mailSenderManager.init(loginName, password, smtpHost, smtpPort, smtpSsl);
		//邮件发送
		for (Map.Entry<String, String> r : recipients.entrySet()) {
			tempMap = Maps.newHashMap();
			if (StringUtils.isNotBlank(r.getValue())) {
				try {
					mailSenderManager.singleSend(r.getValue(), subject, content, mail.getFileName());
					tempMap.put("flag", FLAG_SUCCESS);
					tempMap.put("remark", "邮件发送成功");
				} catch (MessagingException e) {
					tempMap.put("flag", FLAG_FAILURE);
					tempMap.put("remark", "邮件发送失败，错误信息为："+e.getLocalizedMessage());
					e.printStackTrace();
				}
			} else {
				tempMap.put("flag", FLAG_FAILURE);
				tempMap.put("remark", "邮件发送失败，邮箱帐号为空");
			}
			tempMap.put("emp", r.getKey());
			result.add(tempMap);
		}
		return result;
	}
}
