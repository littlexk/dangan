
package com.thinkgem.jeesite.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.JdbcUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * @author yiw
 * @version 2017-09-15
 */
public class Generate {
	
	private static Logger logger = LoggerFactory.getLogger(Generate.class);
	public static String packageName = "com.van.modules";

	public static String moduleName = "tw";			// 模块名，例：sys
	public static String subModuleName = "order";				// 子模块名（可选）
	public static String className = "orderManage";			// 类名，例：user
	public static String classAuthor = "yiw";		// 类作者，例：wengq
	public static String functionName = "订单管理";			// 功能名，例：用户
	public static String priColName = "ID" ;//主键列名
	public static String tableName = "T_TW_ORDER";			// 表名
	// 是否启用生成工具
	public static Boolean isEnable = true;

	public static void main(String[] args) throws Exception {

		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		// 主要提供基本功能模块代码生成。
		// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{subModuleName}/{className}
		
		// packageName 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。

		
		// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================
		
		if (!isEnable){
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}
		
		if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName) 
				|| StringUtils.isBlank(className) || StringUtils.isBlank(functionName)){
			logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
			return;
		}
		
		// 获取文件分隔符
		String separator = File.separator;
		
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/main/java/com/thinkgem/jeesite/generate/template", "/", separator);
		logger.info("Template Path: {}", tplPath);
		
		// Java文件路径
		String javaPath = StringUtils.replaceEach(projectPath+"/src/main/java/"+StringUtils.lowerCase(packageName), 
				new String[]{"/", "."}, new String[]{separator, separator});
		logger.info("Java Path: {}", javaPath);
		
		// 视图文件路径
		String viewPath = StringUtils.replace(projectPath+"/src/main/webapp/WEB-INF/views", "/", separator);
		logger.info("View Path: {}", viewPath);

		String xmlPath = StringUtils.replace(projectPath+"/src/main/resources/mappings/oracle", "/", separator);
		logger.info("View Path: {}", viewPath);

		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));
		model.put("moduleName", StringUtils.lowerCase(moduleName));
		model.put("subModuleName", StringUtils.lowerCase(subModuleName));
		model.put("className", StringUtils.uncapitalize(className));
		model.put("ClassName", StringUtils.capitalize(className));
		model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Tools");
		model.put("classVersion", DateUtils.getDate());
		model.put("functionName", functionName);
		model.put("tableName", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
				?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+model.get("className"));
		model.put("urlPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
				?"/"+StringUtils.lowerCase(subModuleName):"")+"/"+model.get("className"));
		model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
				model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
				?":"+StringUtils.lowerCase(subModuleName):"")+":"+model.get("className"));



		model.put("tableName", tableName);
		model.put("priColName", priColName);
		model.put("priColNameParams", "#{"+priColName+"}");
		model.put("insertCol", Generate.getInsertCol());
		model.put("insertColValue",  Generate.getInsertColValue());
		model.put("updateCol", Generate.getUpateCol());
		model.put("getColParams", "#{id}");

		model.put("colDetails",Generate.getColDetail());
		// 生成 Entity
		//Template template = cfg.getTemplate("entity.ftl");
		/*String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+separator+model.get("moduleName")+separator+"entity"
				+separator+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+".java";
		writeFile(content, filePath);
		logger.info("Entity: {}", filePath);*/
		
		// 生成 Dao
		Template template = cfg.getTemplate("dao.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+separator+model.get("moduleName")+separator+"dao"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Dao.java";
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);

		template = cfg.getTemplate("mybatisXml.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = xmlPath+separator+model.get("moduleName")+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Dao.xml";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 Service
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("moduleName")+separator+"service"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Service.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 Controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("moduleName")+separator+"web"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Controller.java";
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);
		
		// 生成 ViewForm
		template = cfg.getTemplate("viewForm.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName").toString(),".")
				+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
				+separator+model.get("className")+"Form.jsp";
		writeFile(content, filePath);
		logger.info("ViewForm: {}", filePath);
		
		// 生成 ViewList
		template = cfg.getTemplate("viewList.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName").toString(),".")
				+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
				+separator+model.get("className")+"List.jsp";
		writeFile(content, filePath);
		logger.info("ViewList: {}", filePath);
		
		logger.info("Generate Success.");
	}

	/**
	 * @return  数据库连接
	 */
	public static Connection getConnection(){
		Connection conn = null ;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver") ;

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "PARTY", "PARTY") ;
			return conn ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return null ;
	}
	public static  List<String>  getColInfo() {
		Connection  conn = Generate.getConnection();
		List<String> cols = null;
		try{
			String tableName= Generate.tableName;
			PreparedStatement ps = conn.prepareStatement("select * from user_tab_cols where table_name='"+tableName+"'");
			ResultSet rs = ps.executeQuery();
			cols = Lists.newArrayList();
			while (rs.next()){
				String colName = rs.getString("COLUMN_NAME");
				cols.add(colName);
			}
		}catch(Exception e ){
			e.printStackTrace();
		}finally {
			JdbcUtils.close(conn);
		}
		return cols;
	}

	public static  List<Map<String,String>>  getColDetail() {
		Connection  conn = Generate.getConnection();
		List<Map<String,String>> cols = null;
		try{
			String tableName= Generate.tableName;
			PreparedStatement ps = conn.prepareStatement("select t.TABLE_NAME, t.COLUMN_NAME,t.DATA_TYPE,a.COMMENTS,t.DATA_LENGTH,t.NULLABLE from user_tab_cols t " +
					"left outer join  all_col_comments a on t.TABLE_NAME= a.TABLE_NAME and t.COLUMN_NAME=a.COLUMN_NAME where t.table_name='"+tableName+"' order by column_id");
			ResultSet rs = ps.executeQuery();
			cols = Lists.newArrayList();
			while (rs.next()){
				Map<String,String> col = Maps.newHashMap();
				col.put("COLUMN_NAME",rs.getString("COLUMN_NAME"));
				col.put("COMMENTS",rs.getString("COMMENTS"));
				col.put("DATA_TYPE",rs.getString("DATA_TYPE"));
				col.put("DATA_LENGTH",rs.getString("DATA_LENGTH"));
				col.put("NULLABLE",rs.getString("NULLABLE"));
				cols.add(col);
			}
		}catch(Exception e ){
			e.printStackTrace();
		}finally {
			JdbcUtils.close(conn);
		}
		return cols;
	}

	public static  String getInsertCol(){
		List<String> cols = Generate.getColInfo();
		StringBuffer sb  = new StringBuffer();
		for (int i = 0; i < cols.size(); i++) {
			sb.append(cols.get(i));
			if(i!=(cols.size()-1)){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	public  static String getInsertColValue(){
		List<String> cols = Generate.getColInfo();
		StringBuffer sb  = new StringBuffer();
		for (int i = 0; i < cols.size(); i++) {
			if(cols.get(i).equals(priColName)){
				sb.append("SEQ_"+tableName+".Nextval");
			}else{
				sb.append("#{"+cols.get(i)+"}");
			}
			if(i!=(cols.size()-1)){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	public  static String getUpateCol(){
		List<String> cols = Generate.getColInfo();
		StringBuffer sb  = new StringBuffer();
		for (int i = 0; i < cols.size(); i++) {
			if(!StringUtils.equalsIgnoreCase(Generate.priColName,cols.get(i))){
				sb.append(cols.get(i) +" = #{"+cols.get(i)+"}");
				if(i!=(cols.size()-1)){
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)){
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
