package com.jr.utils;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.lang.reflect.Field;

/** 
 * @author Changjielai
 * @version 2017年5月22日 上午10:00:59 
 * 标准管理
 */
public class StandardManage {
	/***************************************************************************************************************/
	/***************************************************操作状态*******************************************************/
	/**
	 * 操作成功
	 */
	public static final String OPERATE_SUCCESS = "success";
	/**
	 * 操作失败
	 */
	public static final String OPERATE_FAIL = "error";
	/**
	 * 学生
	 */
	public static final String EMP_TYPE_STUDENT = "2";
	/**
	 * 老师
	 */
	public static final String EMP_TYPE_TEACHER = "1";
	
	
	
	
	/**
	 * 根据变量名获取值
	 * @author Changjielai
	 * @param key
	 * @return
	 */
	public static String getStandard(String key){
		String result = "";
		if (StringUtils.isEmpty(key)) {
			return result;
		}
		Field[] fields = StandardManage.class.getDeclaredFields();
        for (Field f : fields) {
            if (key.equals(f.getName())) {
                try {
					 result = f.get(f.getName()).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
                break;
            }
        }
        return result;
	}
}
