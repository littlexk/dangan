/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.ShortcutMenuDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 用户工具类
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
public class UserUtils extends BaseService {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder
			.getBean(OfficeDao.class);

	private static ShortcutMenuDao shortcutMenuDao = SpringContextHolder.getBean(ShortcutMenuDao.class);

	public static final String CACHE_USER = "user";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	
	public static final String CACHE_LOGIN_ROLEID_LIST = "loginRoleIdList";

	public static final String CACHE_OFFICE_COLLEGE_LIST = "officeCollegeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

	public static final String CACHE_RULE_TYPE = "ruleType";
	public static final String CACHE_POST_TYPE = "postType";
	public static final String CACHE_COLLEGE_ID = "CACHE_COLLEGE_ID";
	public static final String CACHE_SHORTCUT_MENU_LIST = "shortcutMenuList";

	public static User getUser() {
		User user = (User) getCache(CACHE_USER);
		if (user == null) {
			try {
				Subject subject = SecurityUtils.getSubject();
				Principal principal = (Principal) subject.getPrincipal();
				if (principal != null) {
					user = userDao.get(principal.getId());
					putCache(CACHE_USER, user);
				}
			} catch (UnavailableSecurityManagerException e) {

			} catch (InvalidSessionException e) {

			}
		}
		if (user == null) {
			user = new User();
			try {
				SecurityUtils.getSubject().logout();
			} catch (UnavailableSecurityManagerException e) {

			} catch (InvalidSessionException e) {

			}
		}
		return user;
	}
	/*
	 * 获得用户 是否有人事处管理员权限
	 */
	public static boolean getStaffUser() {
		String staffRoleId =  Global.getStaffRoleId();
		User user = getUser();
		List<String> list = user.getRoleIdList();
		boolean isStaff =false;
		for (String role : list) {
			if(StringUtils.equals(role, staffRoleId)){
				isStaff = true;
			}
		}
		return isStaff;
	}

	
	public static String getCollegeId() {
		String collegeId = getCache(CACHE_COLLEGE_ID) == null ? ""
				: (String) getCache(CACHE_COLLEGE_ID);
		if (StringUtils.isEmpty(collegeId)) {
			User user = (User) getCache(CACHE_USER);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DEPT_ID", user.getOffice().getId());

			putCache(CACHE_COLLEGE_ID, collegeId);
			System.out.println();
		}
		if(StringUtils.isEmpty(collegeId)){
			collegeId = "-1";
		}
		return collegeId;

	}

	public static User getUser(boolean isRefresh) {
		if (isRefresh) {
			removeCache(CACHE_USER);
		}
		return getUser();
	}

	public static List<Role> getRoleList() {
		@SuppressWarnings("unchecked")
		List<Role> list = (List<Role>) getCache(CACHE_ROLE_LIST);
		if (list == null) {
			User user = getUser();
			DetachedCriteria dc = roleDao.createDetachedCriteria();
			dc.createAlias("office", "office");
			dc.createAlias("userList", "userList", JoinType.LEFT_OUTER_JOIN);
			dc.add(dataScopeFilter(user, "office", "userList"));
			dc.add(Restrictions.eq(Role.FIELD_DEL_FLAG, Role.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("office.code")).addOrder(Order.asc("name"));
			list = roleDao.find(dc);
			putCache(CACHE_ROLE_LIST, list);
		}
		return list;
	}

	
	/**
	 * 获得登录的角色
	 * @return
	 */
	public static List<String> getLoginRoleId() {
			User user = getUser();
			String loginRole = user.getRoleType();
			String roleIds = "";
			// 人事处管理角色
			if (StringUtils.equals(loginRole, "2")) {
				roleIds = Global.getStaffRoleId();
			}
			// 院系管理员
			else if (StringUtils.equals(loginRole, "3")) {
				List<String> list = user.getRoleIdList();
				boolean isSchMaterial =false;
				for (String role : list) {
					if(StringUtils.equals(role, Global.getSchMaterialRoleId())){
						isSchMaterial = true;
					}
				}
				if(isSchMaterial){
					roleIds = Global.getSchMaterialRoleId();
				}else{
					roleIds = Global.getCollegeRoleId();
				}
				
			} // 个人
			else if (StringUtils.equals(loginRole, "1")) {
				roleIds = Global.getAverageRoleId();
			}else{
				roleIds= "";
			}
			String[] roleArray = roleIds.split(",");
			List<String> ids= Lists.newArrayList();
			for (String id : roleArray) {
				ids.add(id);
			}
		return ids;
	}
	
	public static List<Menu> getMenuList() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList();
			} else {
				menuList = menuDao.findByUserId(user.getId());
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	/*
	 * 不分选择的角色，获取登录用户 所有菜单
	 */
	public static List<Menu> getMenuAllUser() {
		List<Menu> menuList = Lists.newArrayList();
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList();
			}else{
				menuList = menuDao.findByUserId(user.getId());
			}
		return menuList;
	}
	public static List<Area> getAreaList() {
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
		if (areaList == null) {
			// User user = getUser();
			// if (user.isAdmin()){
			areaList = areaDao.findAllList();
			// }else{
			// areaList = areaDao.findAllChild(user.getArea().getId(),
			// "%,"+user.getArea().getId()+",%");
			// }
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	/**
	 * 获取组织机构树（按数据范围）
	 * @author Changjielai
	 * @return
	 */
	public static List<Office> getOfficeList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
		if (officeList == null) {
			DetachedCriteria dc = officeDao.createDetachedCriteria();
			dc.add(Restrictions.or(Restrictions.eq("delFlag", Office.DEL_FLAG_NORMAL),Restrictions.isNull("delFlag")));
			String dataScope = getDataScope();//数据范围过滤
			if (StringUtils.equals(dataScope, Role.DATA_SCOPE_OFFICE)) {//仅本单位
				dc.add(Restrictions.eq("id", UserUtils.getUser().getOffice().getId()));
			}else if (StringUtils.equals(dataScope, Role.DATA_SCOPE_OFFICE_AND_CHILD)) {//本单位及以下
				dc.add(Restrictions.like("parentIds", "%,"+UserUtils.getUser().getOffice().getParent().getId()+",%"));
			}
			dc.addOrder(Order.asc("orgCode")).addOrder(Order.asc("partyOrgCode"));
			officeList = officeDao.find(dc);
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}
	/**
	 * 获取组织机构树（所有）
	 * @author Changjielai
	 * @return
	 */
	public static List<Office> getAllOfficeList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)CacheUtils.get(CACHE_OFFICE_ALL_LIST);
		if (officeList == null) {
			DetachedCriteria dc = officeDao.createDetachedCriteria();
			dc.add(Restrictions.eq("delFlag", Office.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("code"));
			officeList = officeDao.find(dc);
			CacheUtils.put(CACHE_OFFICE_ALL_LIST, officeList);
		}
		return officeList;
	}
	public static List<Map<String,Object>> getShortcutMenuList() {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) getCache(CACHE_SHORTCUT_MENU_LIST);
		if (menuList == null) {
				User user = getUser();
				menuList = shortcutMenuDao.findByUserId(user.getId());
				putCache(CACHE_SHORTCUT_MENU_LIST, menuList);
		}
		return menuList;
	}
	/**
	 * 获得单位
	 * 
	 * @param type
	 *            [type=1]获取学院数据
	 * @return
	 */
	public static List<Office> getOfficeListByType(String type) {
		String cacheKey = StringUtils.equals(type, "1") ? CACHE_OFFICE_COLLEGE_LIST
				: CACHE_OFFICE_ALL_LIST;
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(cacheKey);
		if (officeList == null) {
			DetachedCriteria dc = officeDao.createDetachedCriteria();
			if (StringUtils.equals(type, "1")) {
				// 父ID为1的是学院
				dc.add(Restrictions.eq("parent", "1"));
			}
			dc.add(Restrictions.eq("delFlag", Office.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("code"));
			officeList = officeDao.find(dc);
			putCache(cacheKey, officeList);
		}
		return officeList;
	}

	public static User getUserById(String id) {
		if (StringUtils.isNotBlank(id)) {
			return userDao.get(id);
		} else {
			return null;
		}
	}
	public static boolean authExist(String roleStr) {
		List<String> list = UserUtils.getUser().getRoleIdList();
		roleStr=roleStr+",";
		for (String role : list) {
			if(roleStr.contains(role+",")){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取当前登录角色的数据范围(若无，即默认仅本单位)
	 * @author Changjielai
	 * @return
	 */
	public static String getDataScope() {
		List<String> roleIdList = getUser().getRoleIdList();
		String roleIdStr = Global.getConfig("roleIdSort");
		if (StringUtils.isEmpty(roleIdStr)||getUser().isAdmin()) {
			return Role.DATA_SCOPE_ALL;
		}
		String[] roleIds = roleIdStr.split(",");
		for (String roleId : roleIds) {
			if (roleIdList!=null&&roleIdList.contains(roleId)) {
				return SystemService.getRole(roleId).getDataScope();
			}
		}
		return Role.DATA_SCOPE_OFFICE;
	}
	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}

	public static Map<String, Object> getCacheMap() {
		Map<String, Object> map = Maps.newHashMap();
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			return principal != null ? principal.getCacheMap() : map;
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return map;
	}

}
