package com.jr.component.vfs.wrap;

import org.apache.commons.vfs.*;
import org.apache.commons.vfs.provider.AbstractOriginatingFileProvider;
import org.apache.commons.vfs.provider.FileProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Restricted Resource File Provider
 */
public class WrappedResourceFileProvider extends AbstractOriginatingFileProvider implements FileProvider {

  private FileSystemManager fsmToWrap;
  private String resourceToWrap;

  @SuppressWarnings("rawtypes")
public final static Collection capabilities = Collections.unmodifiableCollection(Arrays.asList(new Capability[] {
      Capability.CREATE,
      Capability.DELETE,
      Capability.RENAME,
      Capability.GET_TYPE,
      Capability.GET_LAST_MODIFIED,
      Capability.SET_LAST_MODIFIED_FILE,
      Capability.SET_LAST_MODIFIED_FOLDER,
      Capability.LIST_CHILDREN,
      Capability.READ_CONTENT,
      Capability.URI,
      Capability.WRITE_CONTENT,
      Capability.APPEND_CONTENT,
      Capability.RANDOM_ACCESS_READ,
      Capability.RANDOM_ACCESS_WRITE }));

  /**
   * Constructor
   */
  public WrappedResourceFileProvider(FileSystemManager fsmToWrap, String resourceToWrap) {
    super();
    this.fsmToWrap = fsmToWrap;
    this.resourceToWrap = resourceToWrap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.vfs.provider.AbstractOriginatingFileProvider#doCreateFileSystem(org.apache.commons.vfs.FileName,
   *      org.apache.commons.vfs.FileSystemOptions)
   */
  protected FileSystem doCreateFileSystem(FileName name, FileSystemOptions fileSystemOptions)
      throws FileSystemException {

    return new WrappedResourceFileSystem(fsmToWrap, fileSystemOptions, name, resourceToWrap);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.vfs.provider.FileProvider#getCapabilities()
   */
  @SuppressWarnings("rawtypes")
public Collection getCapabilities() {
    return capabilities;
  }
}
