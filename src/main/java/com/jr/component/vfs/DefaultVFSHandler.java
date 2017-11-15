package com.jr.component.vfs;

import com.thinkgem.jeesite.common.config.Global;
import org.apache.commons.vfs.FileSystemException;

import java.util.Properties;

/**
 * 虚拟文件系统构件工厂初始化处理器
 */
public class DefaultVFSHandler{
	public DefaultVFSHandler(){
		 Properties  prop = Global.getVFS();
	    //  prop.setProperty("internalProvider." + 0 + ".name", "servlet");
	     // prop.setProperty("internalProvider." + 0 + ".class", "com.jr.component.vfs.servlet.ServletContextFileProvider");
	    
	    //初始化VFSFactory
	    DefaultVFSFactory vfsFactory = new DefaultVFSFactory();
	    vfsFactory.init(prop);
	    

	    //初始化CommonFileUploadFactory
	    CommonFileUploadFactory uploadFactory = new CommonFileUploadFactory();
	    uploadFactory.init(prop);
	    

	    //初始化DefaultDownloadFactory
	    DefaultDownloadFactory downloadFactory = new DefaultDownloadFactory();
	    downloadFactory.init(prop);
	    
	    //初始化VFSManager
	    try {
			VFSManager.init(vfsFactory, uploadFactory, downloadFactory);
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  /**
   * 依赖服务
   */
  public String[] getDependComponents() {
    return new String[] {};
  }

}