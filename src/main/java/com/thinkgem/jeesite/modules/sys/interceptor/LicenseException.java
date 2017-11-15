package com.thinkgem.jeesite.modules.sys.interceptor;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class LicenseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LicenseException() {
		super();
	}

	public LicenseException(String message) {
		super(message);
	}

	public LicenseException(Throwable cause) {
		super(cause);
	}

	public LicenseException(String message, Throwable cause) {
		super(message, cause);
	}
}
