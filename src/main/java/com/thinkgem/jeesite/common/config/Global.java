/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.common.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2013-03-23
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("jeesite.properties");
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}
	
	/**
	 * 实时获取配置
	 */
	public static String getNewConfig(String key) {
		return loadNewProperties("version.properties").getProperty(key);
	}

	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 获取common静态资源文件路径
	 */
	public static String getStaticPath() {
		return getConfig("staticPath");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 获取文件上传最大值
	 */
	public static Long getWebMaxUploadSize() {
		return Long.parseLong(getConfig("web.maxUploadSize"));
	}
	
	/**
	 * 普通用户角色ID
	 */
	public static String getAverageRoleId() {
		String roleId = getConfig("averageRoleId");
		return roleId;
	}
	
	/**
	 * 人事处管理员角色ID
	 */
	public static String getStaffRoleId() {
		String roleId = getConfig("staffRoleId");
		return roleId;
	}

	/**
	 * 学院角色ID
	 */
	public static String getCollegeRoleId() {
		String roleId = getConfig("collegeRoleId");
		return roleId;
	}
	
	/**
	 * 学校资格审核权限
	 */
	public static String getSchMaterialRoleId() {
		String roleId = getConfig("schMaterialRoleId");
		return roleId;
	}
	/**
	 * 获得团队id
	 */
	public static String getTeamRoleId() {
		String roleId = getConfig("teamRoleId");
		return roleId;
	}
	/**
	 * 获得webservice服务url
	 */
	public static String getWebServiceUrl() {
		return getConfig("webservice.url");
	}
	
	/**
	 * 获取短信发送接口验证ID
	 */
	public static String getMsgAppId(){
		return getConfig("MS_AppId");
	}
	
	/**
	 * 获取短信发送接口验证密码
	 */
	public static String getMsgAppSecret(){
		return getConfig("MS_AppSecret");
	}
	
	/**
	 * 获取短信发送接口服务器地址
	 */
	public static String getMsgAppUrl(){
		return getConfig("MS_AppUrl");
	}
	
	/**
	 * 获取导出模版路径
	 * @return
	 */
	public static String getModelDir(String modelName) {
		String dir = System.getProperty("user.dir");
		if(StringUtils.isNotEmpty(modelName)){
			return dir+"\\WEB-INF\\model\\"+modelName;
		}
		return dir+"\\WEB-INF\\model\\";
	}
	public static void main(String[] args) {
		System.out.println(Global.getModelDir("staff"));
	}
	/**
	 * 获取CKFinder上传文件的根目录
	 * @return
	 */
	public static String getCkBaseDir() {
		String dir = getConfig("userfiles.basedir");
		Assert.hasText(dir, "配置文件里没有配置userfiles.basedir属性");
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}
	/**
	 * 获取虚拟文件系统构件
	 * @return
	 */
	public static Properties getVFS() {
		Properties prop = new Properties();
		Properties  props = propertiesLoader.getProperties();
		Set<Object> set  =  props.keySet();
		int i = 0 ;
		for (Iterator<Object> iterator = set.iterator(); iterator.hasNext();) {
			String name = ObjectUtils.toString(iterator.next());
			if(StringUtils.startsWith(name, "vfs.")){
				prop.setProperty("fileSystem." + i + ".name", StringUtils.substringAfter(name, "vfs."));
			    prop.setProperty("fileSystem." + i + ".url", Global.getConfig(name));
			    i++;
			}
		}
		return prop;
	}
	
	/**
	 * 实时读取配置文件
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	public static Properties loadNewProperties(String... resourcesPaths) {
		Properties props = new Properties();
		for (String location : resourcesPaths) {
			InputStream is = null;
			try {
				is = new BufferedInputStream(new FileInputStream(
						URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(location).getPath(),"utf-8")));
				props.load(is);
			} catch (IOException ex) {
				//logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
				System.out.println("获取实时配置文件错误：location。"+location);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}
}
