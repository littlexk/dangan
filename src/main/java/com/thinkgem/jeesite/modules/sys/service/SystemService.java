/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.ShortcutMenuDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ShortcutMenuDao shortcutMenuDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	@Autowired
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);

	// -- User Service --//

	public User getUser(String id) {
		return userDao.get(id);
	}

	public Page<User> findUser(Page<User> page, User user) {
		DetachedCriteria dc = userDao.createDetachedCriteria();
		User currentUser = UserUtils.getUser();
		dc.createAlias("company", "company");
		dc.createAlias("office", "office");
		if (user.getCompany() != null
				&& StringUtils.isNotBlank(user.getCompany().getId())) {
			/*dc.add(Restrictions.or(
					Restrictions.eq("office.id", user.getCompany().getId()),
					Restrictions.like("office.parentIds", "%."
							+ user.getOffice().getId() + ".%")));*/
			String[] ids = StringUtils.split(user.getCompany().getId(), ",");
			dc.add(Restrictions.or(
					Restrictions.in("office.id", ids),
					Restrictions.like("office.parentIds", "%."
							+ user.getCompany().getId() + ".%")));
		}
//		dc.createAlias("office", "office");
		if (user.getOffice() != null
				&& StringUtils.isNotBlank(user.getOffice().getId())) {
			String[] ids = StringUtils.split(user.getOffice().getId(), ",");
			dc.add(Restrictions.or(
					Restrictions.in("office.id", ids)/*,
					Restrictions.like("office.parentIds", "%."
							+ user.getOffice().getId() + ".%")*/));
		}

		if(StringUtils.isNotEmpty(user.getRolename())){
		dc.createAlias("roleList", "id");
		dc.add(Restrictions.like("id.name", "%"+user.getRolename()+"%"));
		}
		// 如果不是超级管理员，则不显示超级管理员用户
		if (!currentUser.isAdmin()) {
			dc.add(Restrictions.ne("id", "1"));
		}
		if(!StringUtils.equals(user.getUserType(), "2")){
			dc.add(dataScopeFilter(currentUser, "office", ""));
			if (StringUtils.equals(currentUser.getRoleType(), "3")) {
	
				dc.add(Restrictions.or(Restrictions.eq("office.id", currentUser
						.getOffice().getId()), Restrictions.like(
						"office.parentIds", currentUser.getOffice().getParentIds()
								+ currentUser.getOffice().getId() + ",%")));
			}
		}
		// System.out.println(dataScopeFilterString(currentUser, "office", ""));
		if (StringUtils.isNotEmpty(user.getLoginName())) {
			dc.add(Restrictions.like("loginName", "%" + user.getLoginName()
					+ "%"));
		}
		if (StringUtils.isNotEmpty(user.getName())) {
			dc.add(Restrictions.like("name", "%" + user.getName() + "%"));
		}
		if (StringUtils.isNotEmpty(user.getNo())) {
			dc.add(Restrictions.like("no", "%" + user.getNo() + "%"));
		}
		dc.add(Restrictions.eq(User.FIELD_DEL_FLAG, User.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.asc("company.code"))
					.addOrder(Order.asc("office.code"))
					.addOrder(Order.desc("name"));
		}
		return userDao.find(page, dc);
	}

	// 取用户的数据范围
	public String getDataScope(User user) {
		return dataScopeFilterString(user, "office", "");
	}

	public User getUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	public User getUserByEmployeeNo(String no) {
		return userDao.findByEmployeeNo(no);
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		userDao.clear();
		userDao.save(user);
		systemRealm.clearAllCachedAuthorizationInfo();
		// TODO 删除同步到Activiti
		// saveActiviti(user);
	}

	@Transactional(readOnly = false)
	public void deleteUser(String id) {
		userDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName,
			String newPassword) {
		userDao.updatePasswordById(entryptPassword(newPassword), id);
		systemRealm.clearCachedAuthorizationInfo(loginName);
	}

	@Transactional(readOnly = false)
	public void updateUserLoginInfo(String id) {
		userDao.updateLoginInfo(SecurityUtils.getSubject().getSession()
				.getHost(), new Date(), id);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)
				+ Encodes.encodeHex(hashPassword));
	}

	// -- Role Service --//

	public static Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role findRoleByName(String name) {
		return roleDao.findByName(name);
	}

	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}

	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		roleDao.clear();
		roleDao.save(role);
		systemRealm.clearAllCachedAuthorizationInfo();
		// 同步到Activiti
		// saveActiviti(role);
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
	}

	@Transactional(readOnly = false)
	public void deleteRole(String id) {
		roleDao.deleteById(id);
		systemRealm.clearAllCachedAuthorizationInfo();
		// 同步到Activiti
		// deleteActiviti(roleDao.get(id));
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, String userId) {
		User user = userDao.get(userId);
		List<String> roleIds = user.getRoleIdList();
		List<Role> roles = user.getRoleList();
		//
		if (roleIds.contains(role.getId())) {
			roles.remove(role);
			saveUser(user);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, String userId) {
		User user = userDao.get(userId);
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	// -- Menu Service --//

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		menu.setParent(this.getMenu(menu.getParent().getId()));
		String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		menu.setParentIds(menu.getParent().getParentIds()
				+ menu.getParent().getId() + ",");
		menuDao.clear();
		menuDao.save(menu);
		// 更新子节点 parentIds
		List<Menu> list = menuDao.findByParentIdsLike("%," + menu.getId()
				+ ",%");
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					menu.getParentIds()));
		}
		menuDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// saveActiviti(menu);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(String id) {
		menuDao.deleteById(id, "%," + id + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
	}

	@Transactional(readOnly = false)
	public void saveShortcutMenu(String menuIds) {
		String userId = UserUtils.getUser().getId();
		if (StringUtils.isEmpty(menuIds)) {
			shortcutMenuDao.delMenuByUserId(userId);
		} else {
			String[] _menuIds = StringUtils.split(menuIds, ",");
			shortcutMenuDao.delMenuByUserId(userId);
			int i = 0;
			for (String menuId : _menuIds) {
				Map<String, Object> obj = Maps.newHashMap();
				obj.put("USER_ID", userId);
				obj.put("MENU_ID", menuId);
				obj.put("SORT", i++);
				shortcutMenuDao.save(obj);
			}
		}
		UserUtils.removeCache(UserUtils.CACHE_SHORTCUT_MENU_LIST);
	}

}
