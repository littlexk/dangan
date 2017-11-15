/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
	
	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabelBySeparator(String value, String type,String separator, String defaultValue){
		
		StringBuffer label = new StringBuffer(100);
		
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			String[] values = StringUtils.split(value, separator);
			for (String val : values) {
				for (Dict dict : getDictList(type)){
					if (type.equals(dict.getType()) && val.equals(dict.getValue())){
						label.append(dict.getLabel()).append(",");
						break;
					}
				}
			}
		}
		
		if (label.length()>0) {
			label.deleteCharAt(label.length()-1);
		}
		
		return (label.toString().length() > 0 ? label.toString() : defaultValue);
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static String getDictRemarks(String type,String value){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getRemarks();
				}
			}
		}
		return "";
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList()){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	public static List<Dict> getDictFilterList(String type,String filter){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList()){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}else if (StringUtils.isNotEmpty(filter)) {
			List<Dict> tempList = Lists.newArrayList();
			for (Dict dict : dictList) {
				if (StringUtils.isNotEmpty(dict.getRemarks())&&dict.getRemarks().contains(filter)) {
					tempList.add(dict);
				}
			}
			dictList = tempList;
		}
		return dictList;
	}
	
	public static List<Dict> getDictListByValue(String type,String value){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(type+value);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findListByValue(type, value)){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(type+value, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	public static  List<Office>  getCollegeAll(){
		return UserUtils.getOfficeListByType("1");
	}
	
	public static  List<Office>  getOfficeAll(){
		return UserUtils.getOfficeListByType("");
	}
	public static String getOfficeLabelBySeparator(String value,String separator, String defaultValue){
		
		StringBuffer label = new StringBuffer(100);
		
		if (StringUtils.isNotBlank(value)){
			String[] values = StringUtils.split(value,(StringUtils.isEmpty(separator)?",":separator));
			List<Office>  office = getOfficeAll();
			for (String val : values) {
				for (Office dict : office){
					if (StringUtils.equals(dict.getId(), val)){
						label.append(dict.getName()).append(",");
						break;
					}
				}
			}
		}
		if (label.length()>0) {
			label.deleteCharAt(label.length()-1);
		}
		
		return (label.toString().length() > 0 ? label.toString() : defaultValue);
	}

}
