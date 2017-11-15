package com.jr.component.vfs;

import org.apache.commons.vfs.FileObject;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载构件工厂
 */
public interface DownloadFactory {

	/**
	 * 文件下载
	 * @param file 用于下载的文件对象
	 * @param response HttpServletResponse对象
	 */
	void download(FileObject file, HttpServletResponse response);
}
