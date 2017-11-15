/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.sys.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 选择权限异常处理类
 * @author yiw
 * @version 2014-08-22
 */
public class PrivilegesException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public PrivilegesException() {
		super();
	}

	public PrivilegesException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrivilegesException(String message) {
		super(message);
	}

	public PrivilegesException(Throwable cause) {
		super(cause);
	}

}
