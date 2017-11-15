package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.utils.FormBean;
import com.jr.utils.PaginationMap;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.sys.dao.MailDao;
import com.thinkgem.jeesite.modules.sys.security.MailSenderManager;
import com.thinkgem.jeesite.modules.sys.utils.MailUtils;


/**
 * 邮箱管理服务层
 * @ClassName: MailService
 * @Description: TODO 负责邮箱管理相关逻辑处理
 * @author: WangCong
 * @date: 2016年5月5日 上午11:02:34
 */
@Service
@Transactional(readOnly = true)
public class MailService {

	@Autowired
	private MailDao mailDao;
	
	
	/**
	 * 普通邮箱查询方法
	 * @Title: searchMailByConditions 
	 * @Description: TODO 通过指定条件查询邮箱记录
	 * @Date: 2016年5月5日 上午11:11:26
	 * @author: WangCong
	 * @param formBean 页面传入的查询条件
	 * @param page 分页类
	 * @return 邮箱记录
	 */
	public Page<Map<String, Object>> searchMailByConditions(FormBean formBean,Page<Map<String, Object>> page){
		PaginationMap map = (PaginationMap)formBean.getBean();
		map.setPage(page);
		List<Map<String, Object>> list = mailDao.searchMailByConditions(map);
		page.setList(list);
		return page;
	}
	
	/**
	 * 通过邮箱主键ID来查询指定邮箱
	 * @Title: searchMailById 
	 * @Description: TODO 通过邮箱主键ID来查询指定邮箱
	 * @Date: 2016年5月5日 下午3:58:01
	 * @author: WangCong
	 * @param id 邮箱主键ID
	 * @return 指定邮箱
	 */
	public Map<String, Object> searchMailById(String id){
		Map<String, Object> map = Maps.newHashMap();
		map.put("ID", id);
		List<Map<String, Object>> list = mailDao.searchMailByConditions(map);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return new HashMap<String, Object>();
		}
	}
	
	/**
	 * 检查某个邮箱是否已经存在
	 * @Title: countMailByName 
	 * @Description: TODO 通过帐号来查询某个邮箱是否已经存在
	 * @Date: 2016年5月6日 上午11:20:12
	 * @author: WangCong
	 * @param loginName 帐号
	 * @return 数量为0则返回true，否则返回false
	 */
	public String checkIfMailExist(String loginName){
		Map<String, Object> map = Maps.newHashMap();
		map.put("LOGIN_NAME", loginName);
		int count = mailDao.countMailByConditions(map);
		return count>0 ? "false":"true";
	}
	
	/**
	 * 检查系统目前是否已经存在默认邮箱
	 * @Title: countDefaultMail 
	 * @Description: TODO 检查系统目前是否已经存在默认邮箱
	 * @Date: 2016年5月6日 下午2:33:29
	 * @author: WangCong
	 * @return 是则返回true，否则返回false
	 */
	public boolean checkIfDefaultMailExist(){
		int count = mailDao.countDefaultMail();
		return count>0 ? true:false;
	}
	
	/**
	 * 设置为默认邮箱
	 * @Title: setAsDefault 
	 * @Description: TODO 将当前操作邮箱设置为默认邮箱
	 * @Date: 2016年5月5日 下午2:13:39
	 * @author: WangCong
	 * @param id 当前所操作邮箱的ID
	 */
	public void setAsDefault(String id){
		Map<String, Object> map = Maps.newHashMap();
		map.put("ID", id);
		map.put("DEFAULT_FLAG", "1");
		mailDao.updateMail(map);
	}
	
	/**
	 * 随机将其中一个邮箱设置为默认邮箱
	 * @Title: setDefaultMailRandomly 
	 * @Description: TODO 随机将其中一个邮箱设置为默认邮箱
	 * @Date: 2016年5月6日 下午2:44:06
	 * @author: WangCong
	 */
	public void setDefaultMailRandomly(){
		mailDao.setDefaultMailRandomly();
	}
	
	/**
	 * 取消当前默认邮箱
	 * @Title: cancelDefault 
	 * @Description: TODO 将当前系统的默认邮箱设置为非默认
	 * @Date: 2016年5月5日 下午2:40:59
	 * @author: WangCong
	 */
	public void cancelDefault(){
		mailDao.cancelDefault();
	}
	
	/**
	 * 修改邮箱信息
	 * @Title: saveMail 
	 * @Description: TODO 修改邮箱信息
	 * @Date: 2016年5月5日 下午5:09:26
	 * @author: WangCong
	 * @param map 页面传入的邮箱信息
	 */
	public void updateMail(Map<String, Object> map){
		mailDao.updateMail(map);
	}
	
	/**
	 * 插入一条新的邮箱记录
	 * @Title: insertMail 
	 * @Description: TODO 插入一条新的邮箱记录
	 * @Date: 2016年5月6日 上午9:02:41
	 * @author: WangCong
	 * @param map 页面传入的邮箱信息
	 * @return: void
	 */
	public void insertMail(Map<String, Object> map){
		mailDao.insertMail(map);
	}
	
	
	/**
	 * 删除邮箱记录
	 * @Title: deleteMail 
	 * @Description: TODO 根据主键ID来删除邮箱记录
	 * @Date: 2016年5月6日 上午9:34:49
	 * @author: WangCong
	 * @param map 主键ID所构成的map
	 * @return: void
	 */
	public void deleteMail(Map<String, Object> map){
		mailDao.deleteMail(map);
	}
	
	/**
	 * 获取系统当前的默认邮箱
	 * @Title: getDefaultMail 
	 * @Description: TODO 获取系统当前的默认邮箱
	 * @Date: 2016年5月6日 下午5:44:44
	 * @author: WangCong
	 * @return 系统当前的默认邮箱
	 */
	public List<Map<String, Object>> getDefaultMail(){
		return mailDao.getDefaultMail();
	}
	
	
	/**
	 * 获取系统当前的默认邮箱的登录名 
	 * @Title: getDefaultMailLoginName 
	 * @Description: TODO 获取系统当前的默认邮箱的登录名 
	 * @Date: 2016年5月20日 下午3:55:07
	 * @author: WangCong
	 * @return 系统当前的默认邮箱的登录名
	 */
	public String getDefaultMailLoginName(){
		List<Map<String, Object>> temp = mailDao.getDefaultMail();
		if (temp!=null && temp.size()>0) {
			return (String)temp.get(0).get("LOGIN_NAME");
		}else {
			return null;
		}
	}
	
	/**
	 * 获取指定邮箱的密码
	 * @Title: getPasswordById 
	 * @Description: TODO 根据主键ID获取指定邮箱的密码
	 * @Date: 2016年5月31日 下午3:02:14
	 * @author: WangCong
	 * @param id 邮箱主键ID
	 * @return 指定邮箱密码
	 */
	public String getPasswordById(String id){
		Map<String, Object> param = Maps.newHashMap();
		param.put("ID", id);
		return mailDao.getPasswordById(param);
	}
	
	/**
	 * 邮箱有效性验证
	 * @Title: validate 
	 * @Description: TODO 验证指定邮箱是否真实有效
	 * @Date: 2016年5月27日 下午3:35:56
	 * @author: WangCong
	 * @param userName 帐号
	 * @param password 密码
	 * @param smtpPort 端口
	 * @param smtpHost 发件服务器
	 * @param smtpSsl 是否启动SSL
	 * @return 验证通过则返回true，否则返回false
	 */
	public boolean validate(String userName,String password,String smtpPort,String smtpHost,String smtpSsl){
		MailSenderManager mailSenderManager = MailUtils.getMailSenderManager();
		mailSenderManager.init(userName, password, smtpHost, smtpPort, smtpSsl);
		try {
			return mailSenderManager.validate();
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
