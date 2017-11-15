package com.thinkgem.jeesite.modules.sys.security;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 邮件发送管理工具
 * @ClassName: MailSenderManager 
 * @Description: TODO 管理邮件发送功能
 * @author: WangCong
 * @date: 2016年5月6日 下午5:17:35
 */
public class MailSenderManager {

	/**
	 * 用于发送邮件时保存相关配置
	 */
	private final transient Properties properties = System.getProperties();
	
	/**
	 * 服务器邮箱登录验证
	 */
	private transient MailAuthenticator mailAuthenticator;
	
	/**
	 * 邮件发送会话
	 */
	private transient Session session;
	
	
	private static final String SMTP = "smtp";
	private static final String AT = "@";
	private static final String TRUE = "true";
	private static final String HOST = "mail.smtp.host";
	private static final String AUTH = "mail.smtp.auth";
	private static final String PORT = "mail.smtp.port";
	private static final String SSL = "mail.smtp.starttls.enable";
	private static final String CONNECTION_TIME_OUT = "mail.smtp.connectiontimeout";
	private static final String TIME_OUT = "mail.smtp.timeout";
	private static final String DEFAULT_PORT = "25";
	private static final String DEFAULT_TIME_OUT = "8000";
	private static final String DEBUG = "mail.debug";
	
	public MailSenderManager(){
		
	}
	
	/**
	 * 构造函数
	 * @Title:MailSenderManager
	 * @Description:TODO 设置邮箱帐号，邮箱密码，邮箱服务器
	 * @param userName 邮箱帐号
	 * @param password 邮箱密码
	 * @param smtpHost 邮箱服务器
	 * @param smtpPort 发送端口
	 * @param smtpSsl 是否使用SSL
	 */
	public MailSenderManager(final String userName, final String password,
			final String smtpHost, final String smtpPort, final String smtpSsl) {
		this.init(userName, password, smtpHost, smtpPort, smtpSsl);
	}

	/**
	 * 构造函数
	 * @Title:MailSenderManager
	 * @Description:TODO 设置邮箱帐号，邮箱密码
	 * @param userName 邮箱帐号
	 * @param password 邮箱密码
	 * @param smtpPort 发送端口
	 * @param smtpSsl 是否使用SSL
	 */
	public MailSenderManager(final String userName, final String password, final String smtpPort, final String smtpSsl){
		String smtpHost = SMTP + userName.split(AT)[1];
		this.init(userName, password, smtpHost, smtpPort, smtpSsl);
	}
	
	/**
	 * 构造函数(smtpPort默认设置为465,smtpSsl默认设置为true)
	 * @Title:MailSenderManager
	 * @Description:TODO 设置邮箱帐号，邮箱密码
	 * @param userName 邮箱帐号
	 * @param password 邮箱密码
	 * @param smtpPort 发送端口
	 */
	public MailSenderManager(final String userName, final String password){
		String smtpHost = SMTP + userName.split(AT)[1];
		String smtpPort = DEFAULT_PORT;
		String smtpSsl = TRUE;
		this.init(userName, password, smtpHost, smtpPort, smtpSsl);
	}
	
	/**
	 * 邮件发送/验证相关参数初始化
	 * @Title: init 
	 * @Description: TODO 邮件发送相关参数初始化
	 * @Date: 2016年5月6日 下午4:13:56
	 * @author: WangCong
	 * @param userName 邮箱帐号
	 * @param password 邮箱密码
	 * @param smtpHost 邮箱服务器
	 * @param smtpPort 发送端口
	 * @param smtpSsl 是否使用SSL
	 */
	public void init(String userName, String password, String smtpHost, String smtpPort, String smtpSsl){
		//设置邮件发送相关配置properties
		properties.put(HOST, smtpHost);//发件服务器
		properties.put(PORT, smtpPort);//端口
		properties.put(AUTH, TRUE);//使用验证
		properties.put(SSL, smtpSsl); //使用 STARTTLS安全连接，即表示SMTP需要使用SSL
		properties.put(CONNECTION_TIME_OUT, DEFAULT_TIME_OUT);//SMTP连接超时控制
		properties.put(TIME_OUT, DEFAULT_TIME_OUT);//邮件发送连接超时
		properties.put(DEBUG, TRUE);//打印调试信息
		
		//进行箱登录验证
		mailAuthenticator = new MailAuthenticator(userName, password);
		
		//创建邮件发送会话
		session = Session.getInstance(properties, mailAuthenticator);
	}
	
	/**
	 * 单发邮件
	 * @Title: send 
	 * @Description: TODO 发送邮件给单个接收者
	 * @Date: 2016年5月6日 下午4:53:29
	 * @author: WangCong
	 * @param recipient 接收者
	 * @param subject 主题
	 * @param content 内容
	 * @throws MessagingException
	 */
	public void singleSend(String recipient, String subject, String content, String fileName) throws MessagingException{
		//设置邮件发送人、接收人
		InternetAddress fromAddress = new InternetAddress(mailAuthenticator.getUserName());
		InternetAddress toAddress = new InternetAddress(recipient);

		//编写邮件内容
		MimeMessage message = new MimeMessage(session);
		message.setFrom(fromAddress);
		message.addRecipient(RecipientType.TO, toAddress);
		message.setSentDate(Calendar.getInstance().getTime());
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=utf-8");
		
		//添加附件 默认路径 vfs.pr
		if(StringUtils.isNotEmpty(fileName)){
			String affixPath = Global.getConfig("vfs.pr");//附件地址
			String affixName = fileName;//附件名称
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
	        Multipart multipart = new MimeMultipart();         
	        BodyPart contentPart = new MimeBodyPart();
	        contentPart.setText(content);
	        multipart.addBodyPart(contentPart);
	        //添加附件
	        BodyPart messageBodyPart= new MimeBodyPart();
	        DataSource source = new FileDataSource(affixPath);
	        //添加附件的内容
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        //添加附件的标题 防止乱码
	        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	        messageBodyPart.setFileName("=?GBK?B?"+enc.encode(affixName.getBytes())+"?=");
	        multipart.addBodyPart(messageBodyPart);
	        //将multipart对象放到message中
	        message.setContent(multipart);
		}
		//发送消息
		Transport.send(message, message.getRecipients(RecipientType.TO));
	}
	
	/**
	 * 群发邮件
	 * @Title: groupSend 
	 * @Description: TODO 发送邮件给多个接收者
	 * @Date: 2016年5月6日 下午5:00:49
	 * @author: WangCong
	 * @param recipients 多个接收者
	 * @param subject 主题
	 * @param content 内容
	 * @throws MessagingException
	 */
	public void groupSend(String[] recipients, String subject, String content) throws MessagingException{
		//设置邮件发送人、接收人
		InternetAddress fromAddress = new InternetAddress(mailAuthenticator.getUserName());
		InternetAddress[] toAddress = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			toAddress[i] = new InternetAddress(recipients[i]);
		}

		//编写邮件内容
		MimeMessage message = new MimeMessage(session);
		message.setFrom(fromAddress);
		message.addRecipients(RecipientType.TO, toAddress);
		message.setSentDate(Calendar.getInstance().getTime());
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=utf-8");

		//发送消息
		Transport.send(message, message.getRecipients(RecipientType.TO));
	}
	
	/**
	 * 邮件发送
	 * @Title: send 
	 * @Description: TODO 邮件发送(能够通过判断recipients的大小来选择执行群发还是简单发送)
	 * @Date: 2016年5月6日 下午5:11:20
	 * @author: WangCong
	 * @param recipients 接收者
	 * @param content 内容
	 * @param subject 主题
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String content, String subject) throws MessagingException{
		int size = recipients.size();
		if (size == 1) {
			this.singleSend(recipients.get(0), subject, content, "");
		} else if (size > 1) {
			String[] receivers = new String[size];
			for (int i=0;i<size;i++) {
				receivers[i] = recipients.get(i);
			}
			this.groupSend(receivers, subject, content);
		}
	}
	
	/**
	 * 邮箱有效性验证
	 * @Title: validate 
	 * @Description: TODO 验证指定邮箱是否真实有效
	 * @Date: 2016年5月27日 下午2:54:20
	 * @author: WangCong
	 * @return 验证通过则返回true，否则返回false
	 * @throws MessagingException 
	 */
	public boolean validate() throws MessagingException{
		//验证结果
		boolean flag = true;
		//进行邮箱验证
		SMTPTransport smtpTransport = (SMTPTransport) session.getTransport("smtp");
		smtpTransport.connect();
		int code =smtpTransport.simpleCommand("MAIL FROM:<"+mailAuthenticator.getUserName()+">");
		if(code != 250 && code != 251) {
			flag = false;//验证失败
		}
		return flag;
	}
}
