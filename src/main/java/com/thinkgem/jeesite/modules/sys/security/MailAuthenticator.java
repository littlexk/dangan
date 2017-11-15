package com.thinkgem.jeesite.modules.sys.security;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 服务器邮箱登录验证
 * @ClassName: MailAuthenticator 
 * @Description: TODO 服务器邮箱登录验证
 * @author: WangCong
 * @date: 2016年5月6日 下午3:54:24
 */
public class MailAuthenticator extends Authenticator{

	/**
	 * 邮箱帐号
	 */
	private String userName;
	
	/**
	 * 邮箱密码
	 */
	private String password;
	
	/**
	 * 无参数构造函数
	 */
	public MailAuthenticator(){
		
	}
	
	/**
	 * 初始化所有参数的构造函数
	 * @Title:MailAuthenticator
	 * @Description:TODO 初始化所有参数的构造函数
	 * @param userName 邮箱帐号
	 * @param password 邮箱密码
	 */
	public MailAuthenticator(String userName, String password){
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 构建一个PasswordAuthentication并返回
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
}
