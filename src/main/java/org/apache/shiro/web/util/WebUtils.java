package org.apache.shiro.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.WebEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils
{
  private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

  public static final String SERVLET_REQUEST_KEY = ServletRequest.class.getName() + "_SHIRO_THREAD_CONTEXT_KEY";
  public static final String SERVLET_RESPONSE_KEY = ServletResponse.class.getName() + "_SHIRO_THREAD_CONTEXT_KEY";
  public static final String SAVED_REQUEST_KEY = "shiroSavedRequest";
  public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
  public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
  public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
  public static final String INCLUDE_PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
  public static final String INCLUDE_QUERY_STRING_ATTRIBUTE = "javax.servlet.include.query_string";
  public static final String FORWARD_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
  public static final String FORWARD_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.forward.context_path";
  public static final String FORWARD_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";
  public static final String FORWARD_PATH_INFO_ATTRIBUTE = "javax.servlet.forward.path_info";
  public static final String FORWARD_QUERY_STRING_ATTRIBUTE = "javax.servlet.forward.query_string";
  public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";

  public static String getPathWithinApplication(HttpServletRequest request)
  {
    String contextPath = getContextPath(request);
    String requestUri = getRequestUri(request);
    if (StringUtils.startsWithIgnoreCase(requestUri, contextPath))
    {
      String path = requestUri.substring(contextPath.length());
      return StringUtils.hasText(path) ? path : "/";
    }

    return requestUri;
  }

  public static String getRequestUri(HttpServletRequest request)
  {
    String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
    if (uri == null) {
      uri = request.getRequestURI();
    }
    return normalize(decodeAndCleanUriString(request, uri));
  }

  public static String normalize(String path)
  {
    return normalize(path, true);
  }

  private static String normalize(String path, boolean replaceBackSlash)
  {
    if (path == null) {
      return null;
    }

    String normalized = path;

    if ((replaceBackSlash) && (normalized.indexOf('\\') >= 0)) {
      normalized = normalized.replace('\\', '/');
    }
    if (normalized.equals("/.")) {
      return "/";
    }

    if (!normalized.startsWith("/")) {
      normalized = "/" + normalized;
    }
    while (true)
    {
      int index = normalized.indexOf("//");
      if (index < 0)
        break;
      normalized = normalized.substring(0, index) + normalized.substring(index + 1);
    }

    while (true)
    {
      int index = normalized.indexOf("/./");
      if (index < 0)
        break;
      normalized = normalized.substring(0, index) + normalized.substring(index + 2);
    }

    while (true)
    {
      int index = normalized.indexOf("/../");
      if (index < 0)
        break;
      if (index == 0)
        return null;
      int index2 = normalized.lastIndexOf('/', index - 1);
      normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
    }

    return normalized;
  }

  private static String decodeAndCleanUriString(HttpServletRequest request, String uri)
  {
    uri = decodeRequestString(request, uri);
    int semicolonIndex = uri.indexOf(';');
    return semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri;
  }

  public static String getContextPath(HttpServletRequest request)
  {
    String contextPath = (String)request.getAttribute("javax.servlet.include.context_path");
    if (contextPath == null) {
      contextPath = request.getContextPath();
    }
    if ("/".equals(contextPath))
    {
      contextPath = "";
    }
    return decodeRequestString(request, contextPath);
  }

  public static WebEnvironment getRequiredWebEnvironment(ServletContext sc)
    throws IllegalStateException
  {
    WebEnvironment we = getWebEnvironment(sc);
    if (we == null) {
      throw new IllegalStateException("No WebEnvironment found: no EnvironmentLoaderListener registered?");
    }
    return we;
  }

  public static WebEnvironment getWebEnvironment(ServletContext sc)
  {
    return getWebEnvironment(sc, EnvironmentLoader.ENVIRONMENT_ATTRIBUTE_KEY);
  }

  public static WebEnvironment getWebEnvironment(ServletContext sc, String attrName)
  {
    if (sc == null) {
      throw new IllegalArgumentException("ServletContext argument must not be null.");
    }
    Object attr = sc.getAttribute(attrName);
    if (attr == null) {
      return null;
    }
    if ((attr instanceof RuntimeException)) {
      throw ((RuntimeException)attr);
    }
    if ((attr instanceof Error)) {
      throw ((Error)attr);
    }
    if ((attr instanceof Exception)) {
      throw new IllegalStateException((Exception)attr);
    }
    if (!(attr instanceof WebEnvironment)) {
      throw new IllegalStateException("Context attribute is not of type WebEnvironment: " + attr);
    }
    return (WebEnvironment)attr;
  }

  @SuppressWarnings("deprecation")
public static String decodeRequestString(HttpServletRequest request, String source)
  {
    String enc = determineEncoding(request);
    try {
      return URLDecoder.decode(source, enc);
    } catch (UnsupportedEncodingException ex) {
      if (log.isWarnEnabled()) {
        log.warn("Could not decode request string [" + source + "] with encoding '" + enc + "': falling back to platform default encoding; exception message: " + ex.getMessage());
      }
    }
    return URLDecoder.decode(source);
  }

  protected static String determineEncoding(HttpServletRequest request)
  {
    String enc = request.getCharacterEncoding();
    if (enc == null) {
      enc = "ISO-8859-1";
    }
    return enc;
  }

  public static boolean isWeb(Object requestPairSource)
  {
    return ((requestPairSource instanceof RequestPairSource)) && (isWeb((RequestPairSource)requestPairSource));
  }

  public static boolean isHttp(Object requestPairSource) {
    return ((requestPairSource instanceof RequestPairSource)) && (isHttp((RequestPairSource)requestPairSource));
  }

  public static ServletRequest getRequest(Object requestPairSource) {
    if ((requestPairSource instanceof RequestPairSource)) {
      return ((RequestPairSource)requestPairSource).getServletRequest();
    }
    return null;
  }

  public static ServletResponse getResponse(Object requestPairSource) {
    if ((requestPairSource instanceof RequestPairSource)) {
      return ((RequestPairSource)requestPairSource).getServletResponse();
    }
    return null;
  }

  public static HttpServletRequest getHttpRequest(Object requestPairSource) {
    ServletRequest request = getRequest(requestPairSource);
    if ((request instanceof HttpServletRequest)) {
      return (HttpServletRequest)request;
    }
    return null;
  }

  public static HttpServletResponse getHttpResponse(Object requestPairSource) {
    ServletResponse response = getResponse(requestPairSource);
    if ((response instanceof HttpServletResponse)) {
      return (HttpServletResponse)response;
    }
    return null;
  }

  private static boolean isWeb(RequestPairSource source) {
    ServletRequest request = source.getServletRequest();
    ServletResponse response = source.getServletResponse();
    return (request != null) && (response != null);
  }

  private static boolean isHttp(RequestPairSource source) {
    ServletRequest request = source.getServletRequest();
    ServletResponse response = source.getServletResponse();
    return ((request instanceof HttpServletRequest)) && ((response instanceof HttpServletResponse));
  }

  public static boolean _isSessionCreationEnabled(Object requestPairSource)
  {
    if ((requestPairSource instanceof RequestPairSource)) {
      RequestPairSource source = (RequestPairSource)requestPairSource;
      return _isSessionCreationEnabled(source.getServletRequest());
    }
    return true;
  }

  public static boolean _isSessionCreationEnabled(ServletRequest request)
  {
    if (request != null) {
      Object val = request.getAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED);
      if ((val != null) && ((val instanceof Boolean))) {
        return ((Boolean)val).booleanValue();
      }
    }
    return true;
  }

  public static HttpServletRequest toHttp(ServletRequest request)
  {
    return (HttpServletRequest)request;
  }

  public static HttpServletResponse toHttp(ServletResponse response)
  {
    return (HttpServletResponse)response;
  }

  @SuppressWarnings("rawtypes")
public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams, boolean contextRelative, boolean http10Compatible)
    throws IOException
  {
    RedirectView view = new RedirectView(url, contextRelative, http10Compatible);
    view.renderMergedOutputModel(queryParams, toHttp(request), toHttp(response));
  }

  public static void issueRedirect(ServletRequest request, ServletResponse response, String url)
    throws IOException
  {
    issueRedirect(request, response, url, null, true, true);
  }

  @SuppressWarnings("rawtypes")
public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams)
    throws IOException
  {
    issueRedirect(request, response, url, queryParams, true, true);
  }

  public static void issueRedirect(ServletRequest request, ServletResponse response, String url, @SuppressWarnings("rawtypes") Map queryParams, boolean contextRelative)
    throws IOException
  {
    issueRedirect(request, response, url, queryParams, contextRelative, true);
  }

  public static boolean isTrue(ServletRequest request, String paramName)
  {
    String value = getCleanParam(request, paramName);
    return (value != null) && ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("t")) || (value.equalsIgnoreCase("1")) || (value.equalsIgnoreCase("enabled")) || (value.equalsIgnoreCase("y")) || (value.equalsIgnoreCase("yes")) || (value.equalsIgnoreCase("on")));
  }

  public static String getCleanParam(ServletRequest request, String paramName)
  {
    return StringUtils.clean(request.getParameter(paramName));
  }

  public static void saveRequest(ServletRequest request) {
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    HttpServletRequest httpRequest = toHttp(request);
    SavedRequest savedRequest = new SavedRequest(httpRequest);
    session.setAttribute("shiroSavedRequest", savedRequest);
  }

  public static SavedRequest getAndClearSavedRequest(ServletRequest request) {
    SavedRequest savedRequest = getSavedRequest(request);
    if (savedRequest != null) {
      Subject subject = SecurityUtils.getSubject();
      Session session = subject.getSession();
      session.removeAttribute("shiroSavedRequest");
    }
    return savedRequest;
  }

  public static SavedRequest getSavedRequest(ServletRequest request) {
    SavedRequest savedRequest = null;
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession(false);
    if (session != null) {
      savedRequest = (SavedRequest)session.getAttribute("shiroSavedRequest");
    }
    return savedRequest;
  }

  public static void redirectToSavedRequest(ServletRequest request, ServletResponse response, String fallbackUrl)
    throws IOException
  {
    String successUrl = null;
    boolean contextRelative = true;
    SavedRequest savedRequest = getAndClearSavedRequest(request);
    if ((savedRequest != null) && (savedRequest.getMethod().equalsIgnoreCase("GET"))) {
      successUrl = savedRequest.getRequestUrl();
      contextRelative = false;
    }

    if (successUrl == null) {
      successUrl = fallbackUrl;
    }

    if (successUrl == null) {
      throw new IllegalStateException("Success URL not available via saved request or via the successUrlFallback method parameter. One of these must be non-null for issueSuccessRedirect() to work.");
    }

    issueRedirect(request, response, successUrl, null, contextRelative);
  }
}