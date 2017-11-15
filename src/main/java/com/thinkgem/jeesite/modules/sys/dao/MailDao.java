package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;


/**
 * 邮箱管理DAO接口
 * @ClassName: MailDao 
 * @Description: TODO 负责邮箱管理相关数据库操作
 * @author: WangCong
 * @date: 2016年5月5日 上午10:57:10
 */
@MyBatisDao
public interface MailDao {
	
	List<Map<String, Object>> searchMailByConditions(Map<String, Object> map);
	
	List<Map<String, Object>> getDefaultMail();
	
	int countMailByConditions(Map<String, Object> map);
	
	int countDefaultMail();
	
	String getPasswordById(Map<String, Object> map);
	
	void cancelDefault();
	
	void setDefaultMailRandomly();
	
	void updateMail(Map<String, Object> map);
	
	void insertMail(Map<String, Object> map);
	
	void deleteMail(Map<String, Object> map);
}
