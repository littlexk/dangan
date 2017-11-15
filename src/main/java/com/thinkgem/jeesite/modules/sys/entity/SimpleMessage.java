package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Map;

/**
 * 简单手机短信类
 * @ClassName: SimpleMessage 
 * @Description: TODO 简单手机短信类
 * @author: WangCong
 * @date: 2016年5月20日 上午9:04:24
 */
public class SimpleMessage {

	/**
	 * 短信接收者
	 */
	private Map<String,String> recipients;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	public SimpleMessage(){}
	
	/**
	 * 初始化所有参数的构造函数
	 * @Title:SimpleMessage
	 * @param recipients 短信接收者
	 * @param content 短信内容
	 */
	public SimpleMessage(Map<String,String> recipients, String content){
		this.recipients = recipients;
		this.content = content;
	}
	
	public Map<String,String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Map<String,String> recipients) {
		this.recipients = recipients;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
