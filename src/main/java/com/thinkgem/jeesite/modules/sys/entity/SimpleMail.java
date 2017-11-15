package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Map;

/**
 * 简单邮件类
 * @ClassName: simpleMail 
 * @Description: TODO 简单邮件类，包括邮件接收者，邮件主题，邮件内容
 * @author: WangCong
 * @date: 2016年5月6日 下午4:42:49
 */
public class SimpleMail {
	
	/**
	 * 邮件接收者
	 */
	private Map<String,String> recipients;
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String content;
	
	/**
	 * 附件名称
	 */
	private String fileName;
	
	public SimpleMail(){}
	
	/**
	 * 初始化所有参数的构造函数
	 * @Title:SimpleMail
	 * @param recipients 邮件接收者
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	public SimpleMail(Map<String,String> recipients, String subject, String content){
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String,String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Map<String,String> recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
