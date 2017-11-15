package com.jr.license;

import de.schlichtherle.license.*;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * VerifyLicense
 */
public class VerifyLicense {
	
	private static final String param="licenseParam.properties";
	
	//common param
	private static String PUBLICALIAS = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String pubPath = "";
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	private static Properties prop = null;
	public VerifyLicense(){
		// 获取参数
		prop = this.loadProperties(param);
		PUBLICALIAS = prop.getProperty("PUBLICALIAS");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
		licPath = prop.getProperty("licPath");
		pubPath = prop.getProperty("pubPath");
	}
	/**
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	private Properties loadProperties(String... resourcesPaths) {
		Properties props = new Properties();
		for (String location : resourcesPaths) {

//			logger.debug("Loading properties file from:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException ex) {
				System.out.println(("Could not load properties from path:" + location + ", " + ex.getMessage()));
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}
	public boolean verify() {		
		/************** 证书使用者端执行 ******************/
		LicenseManager licenseManager = LicenseManagerHolder
				.getLicenseManager(initLicenseParams());
		// 安装证书
		try {
			Resource resource = resourceLoader.getResource(licPath);
			licenseManager.install(resource.getFile());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// 验证证书
		try {
			licenseManager.verify();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 返回验证证书需要的参数
	private static LicenseParam initLicenseParams() {
		Preferences preference = Preferences
				.userNodeForPackage(VerifyLicense.class);
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				VerifyLicense.class, pubPath, PUBLICALIAS, STOREPWD, null);
		LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}
}