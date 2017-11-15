package com.jr.component.vfs.servlet;

import com.jr.component.vfs.wrap.WrappedResourceFileProvider;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.VFS;

import javax.servlet.ServletContext;

public class ServletContextFileProvider extends WrappedResourceFileProvider{
  public ServletContextFileProvider(ServletContext servletContext) throws FileSystemException{
    super(VFS.getManager(), servletContext.getRealPath("/"));
  }
}
