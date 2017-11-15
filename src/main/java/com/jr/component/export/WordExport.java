package com.jr.component.export;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 便用freemarker导出word
 * @author yiw
 *
 */
public class WordExport {
	/**
	 * 需要转义的字符
	 */
	public static final String[] charArray = new String[] { "&", "'", "\"", ">", "<", "" };
	
	/**
	 * 转义后的字符
	 */
	public static final String[] convertArray = new String[] { "&amp;", "&apos;", "&quot;",
			"&gt;", "&lt;", "" };

	private static final String ecode = "UTF-8";

	/**
	 * 模版目录
	 */
	public static final String TEMPLATE_PATH = "/WEB-INF/template";

	/**
	 * 模版路径
	 */
	private String path;
	/**
	 * 模版文件名
	 */
	private String templateFileName;

	/**
	 * 导出文件名
	 */
	private String exportFileName;

	private HttpServletRequest request;

	/**
	 * freemarker数据对象
	 */
	private Map<String, Object> root = null;

	/**
	 * freemarker对象
	 */
	private Configuration config = null;

	/**
	 * 构造方法
	 * @param request
	 * @param templateFileName
	 *            模块文件名
	 * @param exportFileName
	 *            导出文件名
	 * @throws IOException
	 */
	public WordExport(HttpServletRequest request, String templateFileName,
			String exportFileName) throws IOException {
		root = new HashMap<String, Object>();
		this.request = request;
		this.path = getPathByRequest(request);
		this.templateFileName = templateFileName;
		this.exportFileName = exportFileName;
		config = new Configuration();
		config.setDirectoryForTemplateLoading(new File(this.path));
	}

	/**
	 * 获取模版存放目录
	 * @param request
	 * @return
	 */
	private String getPathByRequest(HttpServletRequest request) {
		return request.getSession().getServletContext()
				.getRealPath(TEMPLATE_PATH);
	}

	/**
	 * 增加导出数据
	 * 
	 * @param beanName
	 *            业务数据名称
	 * @param listData
	 *            导出数据
	 * @param keys
	 *            导出模版里包含的所有键
	 * @throws Exception
	 */
	public void addData(String beanName, List<Map<String, Object>> data,
			String[] keys) throws Exception {
		this.addData(beanName, data, keys, false);
	}

	/**
	 * 增加导出数据
	 * 
	 * @param beanName
	 *            业务数据名称
	 * @param listData
	 *            导出数据
	 * @param keys
	 *            导出模版里包含的所有键
	 * @param bo
	 *            如果没有数据是否插入空行
	 * @throws Exception
	 */
	public void addData(String beanName, List<Map<String, Object>> data,
			String[] keys, boolean bo) throws Exception {
		//如果没有记录,是否插入空行
		if (bo) {
			if (data == null || data.size() == 0) {
				Map<String, Object> entity = new HashMap<String, Object>(
						keys.length);
				for (String key : keys) {
					entity.put(key, "");
				}
				data = new ArrayList<Map<String, Object>>(1);
				data.add(entity);
			}
		}
		// 转换字符
		this.charConvert(data, keys);
		// 增加数据到导出对象
		this.root.put(beanName, data);
	}

	/**
	 * 增加导出数据
	 * 
	 * @param beanName
	 *            业务数据名称
	 * @param listData
	 *            导出数据
	 * @param keys
	 *            导出模版里包含的所有键
	 * @throws Exception
	 */
	public void addData(String beanName, Map<String, Object> data, String[] keys)
			throws Exception {

		// 转换字符
		this.charConvert(data, keys);
		// 增加数据到导出对象
		this.root.put(beanName, data);
	}

	/**
	 * 执行导出
	 * 
	 * @throws Exception
	 */
	public void export(HttpServletResponse response) throws Exception {
		String filename = this.encodeChineseDownloadFileName(this.request,
				this.exportFileName);
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment; filename="
				+ filename);
		Template template = this.config.getTemplate(this.templateFileName,
				ecode);
		template.process(root, response.getWriter());
	}

	/**
	 * 1)java插入到xml特殊字符的转换 字符 转义后 & &amp; ' &apos; " &quot; > &gt; < &lt;
	 * 2)设置不存在键值
	 * 
	 * @param map 转换的map
	 * @param keys 需要转换的所有键值,如为空,就直接转map所有值
	 * @return
	 */
	private void charConvert(Map<String, Object> map, String[] keys) {
		int leng = map.keySet().size();
		String[] mapKeys = new String[leng];
		if(keys==null){
			Set<String> set = map.keySet();
			int i=0;
			for (String str : set) {
				mapKeys[i++]= str;
			}
		}else{
			mapKeys = keys;
		}
		for (String key : mapKeys) {
			Object obj = map.get(key);
			if (obj == null) {
				//如为空,就设置为""
				map.put(key, "");
			} else if (obj instanceof String) {
				String str = ObjectUtils.toString(obj);
				//替换所有特殊字符
				map.put(key, org.apache.commons.lang3.StringUtils.replaceEach(
						str, charArray,  convertArray));
			}
		}
	}
	/**
	 * 1)java插入到xml特殊字符的转换 字符 转义后 & &amp; ' &apos; " &quot; > &gt; < &lt;
	 * 2)设置不存在键值
	 * @param list 转换的list
	 * @param keys 需要转换的所有键值,如为空,就直接转map所有值
	 * @return
	 */
	private void charConvert(List<Map<String, Object>> list, String[] keys) {
		for (Map<String, Object> map2 : list) {
			charConvert(map2, keys);
		}
	}

	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 * @param request
	 * @param pFileName 文件名
	 * @return 文件名
	 * @throws UnsupportedEncodingException
	 */
	public String encodeChineseDownloadFileName(HttpServletRequest request,
			String pFileName) throws Exception {

		String filename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (-1 != agent.indexOf("Firefox")) {// Firefox
				filename = "=?UTF-8?B?"
						+ (new String(
								org.apache.commons.codec.binary.Base64
										.encodeBase64(pFileName.getBytes(ecode))))
						+ "?=";
			} else if (-1 != agent.indexOf("Chrome")) {// Chrome
				filename = new String(pFileName.getBytes(), "ISO8859-1");
			} else {// IE7+
				filename = java.net.URLEncoder.encode(pFileName, ecode);
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}
		} else {
			filename = pFileName;
		}
		return filename;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}
}
