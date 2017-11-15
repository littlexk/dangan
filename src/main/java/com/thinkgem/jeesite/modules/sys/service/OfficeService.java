/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends BaseService {

	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public Office get(String id) {
		return officeDao.get(id);
	}
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		office.setParent(this.get(office.getParent().getId()));
		String oldParentIds = office.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		office.setParentIds(office.getParent().getParentIds()+office.getParent().getId()+",");
		officeDao.clear();
		officeDao.save(office);
		// 更新子节点 parentIds
		List<Office> list = officeDao.findByParentIdsLike("%,"+office.getId()+",%");
		for (Office e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, office.getParentIds()));
		}
		officeDao.save(list);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		officeDao.deleteById(id, "%,"+id+",%");
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
	}
	
	/***
	 * 处理 	用户权限
	 * @param role 权限
	 * @param userId 用户ID
	 * @param type	类型“add增加”“delete删除”
	 * @return
	 */
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, String userId, String type) {
		User user = userDao.get(userId);
		List<String> roleIds = user.getRoleIdList();
		if(StringUtils.equals(type, "add")){///分配权限
			if (roleIds.contains(role.getId())) {
				return null;
			}
			user.getRoleList().add(role);
		}else if(StringUtils.equals(type, "delete")){//删除权限
			List<Role> roles = user.getRoleList();
			for (Role ro : roles) {
				String rid = ro.getId();
				String roid = role.getId();
					if(StringUtils.equals(rid, roid)){
						int i = roles.indexOf(ro);
						roles.remove(i);
					}
				}
		}
		userDao.save(user);
		systemRealm.clearAllCachedAuthorizationInfo();
		return user;
	}


	public Object getofficeCN(String data) {		
		return officeDao.findCN(data);
	}
	}
