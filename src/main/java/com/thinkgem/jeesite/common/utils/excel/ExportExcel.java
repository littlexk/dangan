/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.common.utils.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.entity.HeaderCellEntity;
import com.thinkgem.jeesite.common.utils.excel.entity.HeaderEntity;
import com.thinkgem.jeesite.common.utils.excel.entity.SheetEntity;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcel {
	
	private static Logger log = LoggerFactory.getLogger(ExportExcel.class);
			
	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook wb;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;
	
	/**
	 * 当前行号
	 */
	private int rownum;
	/**
	 * 个人
	 */
	private int isPersonal = 0;
	
	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = Lists.newArrayList();
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 */
	public ExportExcel(String title, Class<?> cls){
		this(title, cls, 1);
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param type 导出类型（1:导出数据；2：导出模板）
	 * @param groups 导入分组
	 */
	public ExportExcel(String title, Class<?> cls, int type, int... groups){
		// Get annotation field 
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList){
			String t = ((ExcelField)os[0]).title();
			// 如果是导出，则去掉注释
			if (type==1){
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length==2){
					t = ss[0];
				}
			}
			headerList.add(t);
		}
		initialize(title, headerList);
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headers 表头数组
	 */
	public ExportExcel(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(String title, List<String> headerList) {
		initialize(title, headerList);
	}
	
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(List<SheetEntity> sheets) {
		this.initialize(sheets);
	}
	
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(SheetEntity sheets) {
		this.initialize(sheets);
	}
	private void initialize(SheetEntity sheets) {
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.styles = createStyles(wb);
		}
		int tSRow = 0;
		int mSRow = 1;
		int hSRow = 2;
		SheetEntity se = sheets;
			Sheet sheet = wb.createSheet(se.getSheetName());
			String[] colName= se.getColName();
			// Create title
			String title = se.getTitle();
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(tSRow);
				titleRow.setHeightInPoints(30);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("title2"));
				titleCell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), colName.length-1));
			}
			// Create header
			if (title == null){
				throw new RuntimeException("headerList not null!");
			}
			Row middleRow = null;
			Row headerRow = null;
			if(colName.length>0){
				headerRow = sheet.createRow(hSRow);
				headerRow.setHeightInPoints(40);
				
				middleRow = sheet.createRow(mSRow);
				middleRow.setHeightInPoints(24);
			}
			
			for(int i = 0,clen=colName.length; i < clen; i++){
				Cell cell = middleRow.createCell(i);
				cell.setCellStyle(styles.get("header2"));
				if(i==1){
					sheet.addMergedRegion(new CellRangeAddress(middleRow.getRowNum(),
							middleRow.getRowNum(), 1, 6));
					cell.setCellValue("单位：（签章）                 联系人：                 联系电话：           ");
					i=i+5;
				}else{
					cell.setCellValue("");
				}
				
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellStyle(styles.get("header3"));
				String[] ss = StringUtils.split(colName[i], "**", 2);
				if (ss.length==2){
					cell.setCellValue(ss[0]);
					Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					comment.setString(new XSSFRichTextString(ss[1]));
					cell.setCellComment(comment);
				}else{
					cell.setCellValue(colName[i]);
				}
				sheet.autoSizeColumn(i);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {  
				int colWidth = sheet.getColumnWidth(i)*2;
		        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
			}
			log.debug("Initialize success.");
			
			int hRow = 3;
			for (Map<String,Object> e : se.getData()){
				int colunm = 0;
				Row row = sheet.createRow(hRow++);
				row.setHeightInPoints(25);
				StringBuilder sb = new StringBuilder();	
				
				//暂时
				String[] coNameFrist = {"所在学院","所在单位（系）","姓    名","人事编号",
						"性别","最后学历","最后学位","毕业专业","毕业学校","毕业时间","来校时间","现从事专业","现职称",
						"现职称评定时间","申请晋升职称","申报类型"};
				
				if(Arrays.equals(colName, coNameFrist)){
					colName  =  new String[]{
							"COMPANY",
							"DEPT_NAME",
							"NAME",
							"EMPLOYEE_ID",
							"SEX",
							"END_EDU",
							"END_DEG",
							"GRA_SPECIALTY",
							"GRA_SCHOOL",
							"GRA_DATE",
							"INSCHOOL_DATE",
							"SPECIALITY",
							"TECHNIC",
							"NOW_TECH_DATE",
							"A_TECHNIC",
							"A_TEACHER_TYPE"
					};
				}
				
				for (String n : colName){
					Object val = e.get(n);
					this.addCell(row, colunm++, val, 4,String.class);
					sb.append(val + ", ");
				}
				log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
			}
			
			CellStyle style = null;
			Font footFont = null;
				Row footRow = null;
				footRow = sheet.createRow(hRow++);
				for(int j=0;j<colName.length;j++){
					Cell cell =footRow.createCell(j);
					if(j==1){
						style = wb.createCellStyle();
						footFont = wb.createFont();
						footFont.setFontName("宋体");
						footFont.setFontHeightInPoints((short) 11);
						style.setFont(footFont);
						cell.setCellStyle(style);
						sheet.addMergedRegion(new CellRangeAddress(footRow.getRowNum(),
								footRow.getRowNum(), 1, colName.length-1));
						cell.setCellValue("填表说明：请单位进行审核，并签字盖章。");
					}else{
						cell.setCellValue("");
					}
				}
				
				
				footRow = sheet.createRow(hRow++);
				for(int j=0;j<colName.length;j++){
					Cell cell =footRow.createCell(j);
					if(j==1){
						style = wb.createCellStyle();
						footFont = wb.createFont();
						footFont.setFontName("宋体");
						footFont.setFontHeightInPoints((short) 11);
						footFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
						style.setFont(footFont);
						cell.setCellStyle(style);
						sheet.addMergedRegion(new CellRangeAddress(footRow.getRowNum(),
								footRow.getRowNum(), 1, 3));
						cell.setCellValue("单位负责人签名：  ");
						j=j+2;
					}else if(j==5){
						style = wb.createCellStyle();
						footFont = wb.createFont();
						footFont.setFontName("宋体");
						footFont.setFontHeightInPoints((short) 11);
						style.setFont(footFont);
						cell.setCellStyle(style);
						sheet.addMergedRegion(new CellRangeAddress(footRow.getRowNum(),
								footRow.getRowNum(), 5, 11));
						cell.setCellValue("                       年     月       日");
						j=j+6;
					}else{
						cell.setCellValue("");
					}
				}
				
			
			
		
	}

	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(List<SheetEntity> sheets,String[] args1,String[] args2) {
		this.initialize(sheets,args1,args2);
	}
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(List<SheetEntity> sheets,HeaderEntity header,int exportType) {
		this.isPersonal=exportType;
		this.initialize(sheets,header,exportType);
	}
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(List<SheetEntity> sheets,int exportType) {
		this.isPersonal=exportType;
		this.initialize(sheets,exportType);
	}
	/**
	 */
	private void initialize(List<SheetEntity> sheets) {
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.styles = createStyles(wb);
		}
		int tSRow = 0;
		int hSRow = 1;
		for (int j = 0,len=sheets.size(); j < len; j++) {
			SheetEntity se = sheets.get(j);
			Sheet sheet = wb.createSheet(se.getSheetName());
			String[] colName= se.getColName();
			// Create title
			String title = se.getTitle();
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(tSRow);
				titleRow.setHeightInPoints(30);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("title"));
				titleCell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), colName.length-1));
			}
			// Create header
			if (title == null){
				throw new RuntimeException("headerList not null!");
			}
			Row headerRow = null;
			if(colName.length>0){
				headerRow = sheet.createRow(hSRow);
				headerRow.setHeightInPoints(16);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellStyle(styles.get("header"));
				String[] ss = StringUtils.split(colName[i], "**", 2);
				if (ss.length==2){
					cell.setCellValue(ss[0]);
					Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					comment.setString(new XSSFRichTextString(ss[1]));
					cell.setCellComment(comment);
				}else{
					cell.setCellValue(colName[i]);
				}
				sheet.autoSizeColumn(i);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {  
				int colWidth = sheet.getColumnWidth(i)*2;
		        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
			}
			log.debug("Initialize success.");
			
			int hRow = 2;
			for (Map<String,Object> e : se.getData()){
				int colunm = 0;
				Row row = sheet.createRow(hRow++);
				StringBuilder sb = new StringBuilder();	
				
				//暂时
				String[] coNameFrist = {"序号","编号","被评人单位","被评人","现专业技术职务","申报专业技术职务","申报等级","送审学校1","送审学校2","送审学校3","从事专业方向","建议投放学科","验证码"};
				String[] coNameFrists = {"序号","编号","被评人单位","被评人","现专业技术职务","申报专业技术职务","申报等级","送审学校","从事专业方向","建议投放学科","验证码","评审状态","评审结果"};
				String[] coName = {"序号","单位","姓名","性别","出生日期","最高学历及毕业时间","最高学位及授予时间","最后毕业院校及专业","参加工作时间","进校时间","系列",
						"岗训情况","是否博士后出站人员","申请认定专业技术职务","申请认定时间","备注", "人事处意见"};
				if(Arrays.equals(colName, coNameFrist)){
					colName  =  new String[]{
							"HX",
							"OUT_CODE",
							"DEPT_NAME",
							"NAME",
							"TECHNIC",
							"A_TECHNIC",
							"A_SCH_POST_LEVEL",
							"SCHOOL_1",
							"SCHOOL_2",
							"SCHOOL_3",
							"SPECIALITY",
							"FIRST_SUBJECT",
							"OUT_PASSWORD"
					};
				}else if(Arrays.equals(colName, coNameFrists)){
					colName  =  new String[]{
							"HX",
							"OUT_CODE",
							"DEPT_NAME",
							"NAME",
							"TECHNIC",
							"A_TECHNIC",
							"A_SCH_POST_LEVEL",
							"OUT_SCHOOL",
							"SPECIALITY",
							"FIRST_SUBJECT",
							"OUT_PASSWORD",
							"VERIFY_STATUS",
							"CONCLUSION"
					};
				}else if(Arrays.equals(colName, coName)){
					colName =  new String[]{
							"HX","DEPT_NAME","NAME","SEX","BIRTHDAY","EDUCATION_DATE","EDU_DEGREE_DATE",
							"SCHOOL_SPECIALITY","WORK_DATE","POLITY_DATE","A_SCH_POST_SORT1","POSITIVE_PT_STATUS","IS_PD_OUT",
							"A_TECHNIC","APPLY_DATE","REMARK","INVEST_RESULT_REMARK"
					};
				}
				
				for (String n : colName){
					Object val = e.get(n);
					this.addCell(row, colunm++, val, 2,String.class);
					sb.append(val + ", ");
				}
				log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
			}
			
		}
		
		
	}
	
	
	
	private void initialize(List<SheetEntity> sheets,String [] args1,String [] args2) {
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.styles = createStyles(wb);
		}
		int tSRow = 0;
		int hSRow = 1;
		for (int j = 0,len=sheets.size(); j < len; j++) {
			SheetEntity se = sheets.get(j);
			Sheet sheet = wb.createSheet(se.getSheetName());
			String[] colName= se.getColName();
			// Create title
			String title = se.getTitle();
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(tSRow);
				titleRow.setHeightInPoints(30);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("title"));
				titleCell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), colName.length-1));
			}
			// Create header
			if (title == null){
				throw new RuntimeException("headerList not null!");
			}
			Row headerRow = null;
			if(colName.length>0){
				headerRow = sheet.createRow(hSRow);
				headerRow.setHeightInPoints(16);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellStyle(styles.get("header"));
				String[] ss = StringUtils.split(colName[i], "**", 2);
				if (ss.length==2){
					cell.setCellValue(ss[0]);
					Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					comment.setString(new XSSFRichTextString(ss[1]));
					cell.setCellComment(comment);
				}else{
					cell.setCellValue(colName[i]);
				}
				sheet.autoSizeColumn(i);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {  
				int colWidth = sheet.getColumnWidth(i)*2;
		        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
			}
			log.debug("Initialize success.");
			
			int hRow = 2;
			for (Map<String,Object> e : se.getData()){
				int colunm = 0;
				Row row = sheet.createRow(hRow++);
				StringBuilder sb = new StringBuilder();	
				
				//暂时
				String[] coNameFrist = args1;
				String[] coNameFrists = {"序号","编号","被评人单位","被评人","现专业技术职务","申报职级","送审学校","从事专业方向","建议送审学科","验证码","评审状态","评审结果"};
				String[] coName = {"序号","单位","姓名","性别","出生日期","最高学历及毕业时间","最高学位及授予时间","最后毕业院校及专业","参加工作时间","进校时间","系列",
						"岗训情况","是否博士后出站人员","申请认定专业技术职务","申请认定时间","备注", "人事处意见"};
				if(Arrays.equals(colName, coNameFrist)){
					colName  = args2;
				}else if(Arrays.equals(colName, coNameFrists)){
					colName  =  new String[]{
							"HX",
							"OUT_CODE",
							"DEPT_NAME",
							"NAME",
							"TECHNIC",
							"A_SCH_POST_LEVEL",
							"OUT_SCHOOL",
							"SPECIALITY",
							"FIRST_SUBJECT",
							"OUT_PASSWORD",
							"VERIFY_STATUS",
							"CONCLUSION"
					};
				}else if(Arrays.equals(colName, coName)){
					colName =  new String[]{
							"HX","DEPT_NAME","NAME","SEX","BIRTHDAY","EDUCATION_DATE","EDU_DEGREE_DATE",
							"SCHOOL_SPECIALITY","WORK_DATE","POLITY_DATE","A_SCH_POST_SORT1","POSITIVE_PT_STATUS","IS_PD_OUT",
							"A_TECHNIC","APPLY_DATE","REMARK","INVEST_RESULT_REMARK"
					};
				}
				
				for (String n : colName){
					Object val = e.get(n);
					this.addCell(row, colunm++, val, 2,String.class);
					sb.append(val + ", ");
				}
				log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
			}
			
		}
		
		
	}
	
	/**
	 */
	@SuppressWarnings("unused")
	private void initialize(List<SheetEntity> sheets,int exportType) {
		String exportSort="";//区分不同业务导出
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.styles = createStyles(wb);
		}
		int tSRow = 0;
		int hSRow = 1;
		for (int j = 0,len=sheets.size(); j < len; j++) {
			SheetEntity se = sheets.get(j);
			Sheet sheet = wb.createSheet(se.getSheetName());
			String[] colName= se.getColName();
			// Create title
			String title = se.getTitle();
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(tSRow);
				titleRow.setHeightInPoints(30);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("title"));
				titleCell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), colName.length-1));
			}
			// Create header
			if (title == null){
				throw new RuntimeException("headerList not null!");
			}
			Row headerRow = null;
			if(colName.length>0){
				headerRow = sheet.createRow(hSRow);
				headerRow.setHeightInPoints(80);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {
				Cell cell = headerRow.createCell(i);
				CellStyle style =styles.get("header");
				style.setWrapText(true);
				cell.setCellStyle(style);
				String[] ss = StringUtils.split(colName[i], "**", 2);
				if (ss.length==2){
					cell.setCellValue(ss[0]);
					Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					comment.setString(new XSSFRichTextString(ss[1]));
					cell.setCellComment(comment);
				}else{
					cell.setCellValue(colName[i]);
				}
				sheet.autoSizeColumn(i);
			}
			for (int i = 0,clen=colName.length; i < clen; i++) {  
				int colWidth = sheet.getColumnWidth(i)*2;
				String columnName = colName[i];
				if(StringUtils.equals(columnName, "符合聘用条件具体情况")||StringUtils.equals(columnName, "符合学校聘用条件和本单位自行制定聘用条件的具体情况")){
					sheet.setColumnWidth(i, 8000);
				}else{
					sheet.setColumnWidth(i, 2500);  
				}
			}
			log.debug("Initialize success.");
			
			int hRow = 2;
			for (Map<String,Object> e : se.getData()){
				String businessSort = MapUtils.getString(e, "BUSINESS_SORT"); //业务类型
				exportSort=businessSort;
				String sort = MapUtils.getString(e, "A_SCH_POST_SORT"); //申报系列
				String level = MapUtils.getString(e, "A_SCH_POST_LEVEL");
				int colunm = 0;
				Row row = sheet.createRow(hRow++);
				row.setHeightInPoints(80);
				StringBuilder sb = new StringBuilder();	
				if(StringUtils.equals(businessSort, "3")){//工勤岗位晋级
					colName = new String[] { "ROWINDEX", "DEPT_NAME", "NAME",
							"SEX", "BIRTHDAY", "WORK_DATE", "INSCHOOL_DATE",
							"PERSON_STATUS", "A_SCH_POST_SORT_NAME",
							"EDUCATION_NAME", "TECHNIC_NAME",
							"F_TECHNIC_NAME", "WORK_AGE", "SCH_POST_LEVEL",
							"WORK_LEVEL_CRED",
							"REWARDS_PUNISH","SUBMIT_TECH_COND","SUMMARIZE",
							"COLLEGEREVIEW","REMARK" };
				}else{//专业技术岗位晋级
					if("0101".equals(sort)||"0102".equals(sort)){
						if ("01002".equals(level) || "01003".equals(level)) {
							colName = new String[] { "ROWINDEX", "DEPT_NAME", "NAME",
									"SEX", "BIRTHDAY_TECH", "WORK_DATE_TECH", "INSCHOOL_DATE_TECH",
									"PERSON_STATUS", "A_TECHNIC_NAME",
									"EDUCATION_NAME", "HIGHER_TEACHER", "TECHNIC_NAME",
									"F_TECHNIC_NAME", "JOB_AGE_LIMIT", "SUBJECT1",
									"EMPHASIS_SUBJECT", "IS_TUTOR", "SCH_POST_LEVEL",
									"HAVE_COND", "EXAM_COND", "SUBMIT_TECH_COND",
									"CONDITION_DETAILS", "SUMMARIZE", "COLLEGEREVIEW",
									"REMARK" };
						}else if("01005".equals(level)){
							colName = new String[] { "ROWINDEX", "DEPT_NAME", "NAME",
									"SEX", "BIRTHDAY_TECH", "WORK_DATE_TECH", "INSCHOOL_DATE_TECH",
									"PERSON_STATUS", "A_TECHNIC_NAME",
									"EDUCATION_NAME", "HIGHER_TEACHER", "TECHNIC_NAME",
									"F_TECHNIC_NAME", "JOB_AGE_LIMIT", "SUBJECT1",
									"EMPHASIS_SUBJECT", "IS_TUTOR", "SCH_POST_LEVEL",
									"HAVE_COND", "EXAM_COND", "SUBMIT_TECH_COND",
									"CONDITION_DETAILS", "DEPT_ENACT_CASE_EXPLAIN","SUMMARIZE", "COLLEGEREVIEW",
									"REMARK" };
						}else{
							colName = new String[] { "ROWINDEX", "DEPT_NAME", "NAME",
									"SEX", "BIRTHDAY_TECH", "WORK_DATE_TECH", "INSCHOOL_DATE_TECH",
									"PERSON_STATUS", "A_TECHNIC_NAME",
									"EDUCATION_NAME", "HIGHER_TEACHER", "TECHNIC_NAME",
									"F_TECHNIC_NAME", "JOB_AGE_LIMIT","SCH_POST_LEVEL",
									"HAVE_COND", "EXAM_COND", "DEPT_COND","SUMMARIZE", "COLLEGEREVIEW",
									"REMARK" };
						}
					}else{
						colName = new String[] { "ROWINDEX", "DEPT_NAME", "NAME",
								"SEX", "BIRTHDAY_TECH", "WORK_DATE_TECH", "INSCHOOL_DATE_TECH",
								"PERSON_STATUS", "ADMINIS_DUTY","A_TECHNIC_NAME",
								"EDUCATION_NAME",  "TECHNIC_NAME",
								"F_TECHNIC_NAME", "JOB_AGE_LIMIT","SCH_POST_LEVEL",
								"HAVE_COND", "EXAM_COND", "DEPT_COND","SUMMARIZE", "COLLEGEREVIEW",
								"REMARK" };
					}
				}
				for (String n : colName){
					Object val = e.get(n);
					if(StringUtils.equals(n, "REWARDS_PUNISH")||StringUtils.equals(n, "EXAM_COND")){//工勤个性 单位格
						this.addCell(row, colunm++, val, 2,String.class,3);
					}else{
						this.addCell(row, colunm++, val, 2,String.class,exportType);
					}
					sb.append(val + ", ");
				}
				log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
			}
			if(this.isPersonal==1||this.isPersonal==10){
			String signature="本人保证一览表内容真实、可靠、准确，如有不实之处，本人愿意承担责任。\n单位正职负责人签名（盖章）：                                      填表人签名：\n日期:                                            日期:";
			Row lastRow = sheet.createRow(3);
			lastRow.setHeightInPoints(30);
			Cell lastCell = lastRow.createCell(0);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 16);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			lastRow.setHeightInPoints(123);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			style.setWrapText(true);
			lastCell.setCellStyle(style);
			if(StringUtils.equals(exportSort, "2")&&this.isPersonal==1){
				signature="本人保证一览表内容真实、可靠、准确，如有不实之处，本人愿意承担责任。\n填表人签名：\n日期:";
			}
			lastCell.setCellValue(signature);
			sheet.addMergedRegion(new CellRangeAddress(lastRow.getRowNum(),
					lastRow.getRowNum(), 0, colName.length-1));
			}
		}
		
		
	}
	/**
	 */
	@SuppressWarnings("unused")
	private void initialize(List<SheetEntity> sheets,HeaderEntity header,int exportType) {
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.styles = createStyles(wb);
		}
		int cols = header.getCols();
		int tSRow = 0;
		for (int j = 0,len=sheets.size(); j < len; j++) {
			SheetEntity se = sheets.get(j);
			Sheet sheet = wb.createSheet(se.getSheetName());
			String[] colName= se.getColName();
			// Create title
			String title = se.getTitle();
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(0);
				titleRow.setHeightInPoints(30);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("title"));
				titleCell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), cols-1));
			}
			// Create header
			if (title == null){
				throw new RuntimeException("headerList not null!");
			}
			Row title1Row = sheet.createRow(1);
			title1Row.setHeightInPoints(25);
			Cell title1Cell = title1Row.createCell(1);
			CellStyle style1 = wb.createCellStyle();
			title1Cell.setCellStyle(style1);
			title1Cell.setCellValue("单位名称:(盖章)");
			sheet.addMergedRegion(new CellRangeAddress(title1Row.getRowNum(),
					title1Row.getRowNum(), 1, cols-1));
			//起始行号， 起始列号，
			int rs=2,cs=0;
			int nextCol = 0;
			Row headerRow=null;
			//判断是否换行
			boolean isNext = false;
			List<HeaderCellEntity>  headerList = header.getThead();
			for (int i = 0,hlen=headerList.size(); i < hlen; i++) {
					HeaderCellEntity hcell  =  headerList.get(i);
					if(isNext || headerRow==null){
						rownum++;
						headerRow = sheet.createRow(rs);
						headerRow.setHeightInPoints(40);
						//解决合并之后边框不能出来的问题
						if(nextCol >0){
							for(int hj=0;hj<nextCol;hj++){  
								Cell cell1 = headerRow.createCell((short)hj);  
								cell1.setCellValue("");  
								CellStyle style =styles.get("header1");
								style.setWrapText(true);
								cell1.setCellStyle(style);  
							}
						}
						isNext=false;
					}
					//合并行
					int rowspan = hcell.getRowspan();
					//合并列
					int colspan = hcell.getColspan();
					//终止行号
					int re = rs+rowspan;
					//终止列号
					int ce = cs+colspan;
					
					if(rowspan>0){
						nextCol++;
					}
					//headerRow.setHeightInPoints(16);
					Cell cell = headerRow.createCell(cs);
					CellStyle style =styles.get("header1");
					style.setWrapText(true);
					cell.setCellStyle(style);
					cell.setCellValue(hcell.getValue());
					sheet.addMergedRegion(new CellRangeAddress(rs,(short)(re),cs,(short)ce));
					//解决合并之后边框不能出来的问题
					for(int hj=cs+1;hj<=ce;hj++){  
						cell = headerRow.createCell((short)hj);  
						cell.setCellValue("");  
						cell.setCellStyle(styles.get("header1"));  
					}
					//设置下个单元格的起始行号
					cs=ce+1;
					//断送是否下个单元格超最大列数
					if(cs%cols == 0){
						isNext = true;
						rs ++;
						cs=nextCol;
						
					}
				sheet.autoSizeColumn(i);
			}
			for (int i = 0; i < headerList.size(); i++) {  
		        sheet.setColumnWidth(i, 1700);  
			}
			log.debug("Initialize success.");
			
			int hRow = 3;
			for (Map<String,Object> e : se.getData()){
				int colunm = 0;
				Row row = sheet.createRow(hRow++);
				row.setHeightInPoints(80);
				StringBuilder sb = new StringBuilder();	
				if(cols==23){
					colName = new String[] { "DEPT_NAME","DEPT_TYPE","NAME","SEX","BIRTHDAY","POST_APPLY_YEAR","A_SCH_POST_LEVEL","WORK_POST","SCH_POST_LEVEL",
						"WORK_AGE","AHEAD_PROMOTED","SUM_WORK_AGE","DUTY","DUTY_DATE","ADMINIS_DUTY_LEVEL","ADMINIS_LEVEL_DATE","TECHNIC",
						"TECHNIC_DATE","EDUCATION","EDUCATION_DATE","CONDITION","PRESENT_NUM","REMARK"};
				}else if(cols==20){
					colName = new String[] {"NAME","SEX","BIRTHDAY","POST_APPLY_YEAR","A_SCH_POST_LEVEL","WORK_POST","SCH_POST_LEVEL",
							"WORK_AGE","AHEAD_PROMOTED","SUM_WORK_AGE","DUTY","DUTY_DATE","ADMINIS_DUTY_LEVEL","ADMINIS_LEVEL_DATE","TECHNIC",
							"TECHNIC_DATE","EDUCATION","EDUCATION_DATE","CONDITION","REMARK"};
				}else{
					colName = new String[] { "DEPT_NAME","DEPT_TYPE","NAME","SEX","BIRTHDAY","POST_APPLY_YEAR","A_SCH_POST_LEVEL","WORK_POST","SCH_POST_LEVEL",
						"WORK_AGE","AHEAD_PROMOTED","SUM_WORK_AGE","DUTY","DUTY_DATE","ADMINIS_DUTY_LEVEL","ADMINIS_LEVEL_DATE","TECHNIC",
						"TECHNIC_DATE","EDUCATION","EDUCATION_DATE","CONDITION","REMARK"};
				}
				for (String n : colName){
					Object val = e.get(n);
					this.addCell(row, colunm++, val, 2,String.class,exportType);
					sb.append(val + ", ");
				}
				log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
			}
			if(this.isPersonal==1){
			Row lastRow = sheet.createRow(4);
			lastRow.setHeightInPoints(30);
			Cell lastCell = lastRow.createCell(3);
			Cell lastCell1 = lastRow.createCell(9);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			lastRow.setHeightInPoints(30);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			style.setWrapText(true);
			lastCell.setCellStyle(style);
			lastCell.setCellValue("审核人签名： ");
			sheet.addMergedRegion(new CellRangeAddress(lastRow.getRowNum(),
					lastRow.getRowNum(), 3, 5));
			lastCell1.setCellStyle(style);
			lastCell1.setCellValue("所在单位正职负责人签名：\n年    月    日 ");
			sheet.addMergedRegion(new CellRangeAddress(lastRow.getRowNum(),
					lastRow.getRowNum(), 9, 14));
			}
		}
		
		
	}
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(String title, HeaderEntity header) {
		initialize(title, header);
	}
	/**
	 * 构造函数,创建复杂的表头
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 * @param sheet 工作表名
	 */
	public ExportExcel(String title, HeaderEntity header,String sheet) {
		initialize(title, header,sheet);
	}
	/**
	 * 通过模板构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头数组
	 * @param modle 模板的上级目录
	 *  @param fileName EXCEL模板文件名
	 */
	public ExportExcel(String title, List<String> headerList,String modle,String fileName) {
		String dir = FileUtils.getAbsolutePath(modle+"/WEB-INF/model/"+modle+"/"+fileName);
		FileObject template = null;
		InputStream is = null;
		try {
			//获取默认的空白excel文档模板的文件对象
			FileSystemManager fsmWrapped=VFS.getManager();;
			 template = fsmWrapped.resolveFile(dir);
			 is = template.getContent().getInputStream();
			//读取模板文件，放入文件流，生成工作簿对象
			this.wb = new SXSSFWorkbook(new XSSFWorkbook(is));
			this.sheet = wb.getSheetAt(0);
			this.styles = createStyles(wb);
			
			template.close();
		} catch (Exception e) {
			log.error("模板获取失败，文件路径："+dir);
		}finally{
			IOUtils.closeQuietly(is);
		}
		initialize(title,headerList);
	}
	
	public ExportExcel(String title, HeaderEntity header,
			SheetEntity sheetEntity) {
		initalize(title,header,sheetEntity);
	}

	private void initalize(String title, HeaderEntity header,
			SheetEntity sheetEntity) {
		this.wb = new SXSSFWorkbook(500);
		if(StringUtils.isEmpty(sheetEntity.getSheetName())){
			this.sheet = wb.createSheet("Export");
		}else{
			this.sheet = wb.createSheet(sheetEntity.getSheetName());
		}
		
		this.styles = createStyles(wb);
		int cols = header.getCols();
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), cols-1));
		}
		if(StringUtils.isNotBlank(title)&&"青年教师破格晋升高级专业技术职务申报人员汇总表".equals(title)||sheetEntity.getSheetId()==88){
			Row title1Row = sheet.createRow(rownum++);
			title1Row.setHeightInPoints(25);
			Cell title1Cell = title1Row.createCell(0);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			title1Cell.setCellStyle(style);
			title1Cell.setCellValue("单位:  (盖章)");
			sheet.addMergedRegion(new CellRangeAddress(title1Row.getRowNum(),
					title1Row.getRowNum(), 0, cols-1));
	
		}
		//起始行号， 起始列号，
				int rs=2,cs=0;
				int nextCol = 0;
				Row headerRow=null;
				//判断是否换行
				boolean isNext = false;
				List<HeaderCellEntity>  headerList = header.getThead();
				for (int i = 0,len=headerList.size(); i < len; i++) {
						HeaderCellEntity hcell  =  headerList.get(i);
						if(isNext || headerRow==null){
							rownum++;
							headerRow = sheet.createRow(rs);
							//解决合并之后边框不能出来的问题
							if(nextCol >0){
								for(int j=0;j<nextCol;j++){  
									Cell cell1 = headerRow.createCell((short)j);  
									cell1.setCellValue("");  
									cell1.setCellStyle(styles.get("header1"));  
								}
							}
							isNext=false;
						}
						//合并行
						int rowspan = hcell.getRowspan();
						//合并列
						int colspan = hcell.getColspan();
						//终止行号
						int re = rs+rowspan;
						//终止列号
						int ce = cs+colspan;
						
						if(rowspan>0){
							nextCol++;
						}
						//headerRow.setHeightInPoints(16);
						Cell cell = headerRow.createCell(cs);
						cell.setCellStyle(styles.get("header1"));
						cell.setCellValue(hcell.getValue());
						sheet.addMergedRegion(new CellRangeAddress(rs,(short)(re),cs,(short)ce));
						//解决合并之后边框不能出来的问题
						for(int j=cs+1;j<=ce;j++){  
							cell = headerRow.createCell((short)j);  
							cell.setCellValue("");  
							cell.setCellStyle(styles.get("header1"));  
						}
						//设置下个单元格的起始行号
						cs=ce+1;
						//断送是否下个单元格超最大列数
						if(cs%cols == 0){
							isNext = true;
							rs ++;
							cs=nextCol;
							
						}
					sheet.autoSizeColumn(i);
				}
				//添加人员信息数据
				this.setDataList(sheetEntity.getData(),sheetEntity.getColName());
				//在后面的列中加内容
				if(StringUtils.isNotBlank(title)&&"青年教师破格晋升高级专业技术职务申报人员汇总表".equals(title)){
					
						this.addFoot("负责人签名：",cols,"2");
						this.addFoot("制表人签名：",cols,"2");
						this.addFoot("日  期：",cols,"2");
					
				}
				if(sheetEntity.getSheetId()==88){//青年破格送专家鉴定论文人员清单
				    this.addFoot("注：1.编号组成：学院代码+流水号，其中学院代码、流水号分别由人事处、学院确定。\n2.“现岗位”填写“教师”、“专职科研”中的一项。",cols,"1");
				    this.addFoot("负责人签名：",cols,"2");
					this.addFoot("制表人签名：",cols,"2");
					this.addFoot("日  期：",cols,"2");
				}
				for (int i = 0; i < headerList.size(); i++) {  
					int colWidth = sheet.getColumnWidth(i)*2;
					sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
				}
				log.debug("Initialize success.");
		
	}

	private void addFoot(String cellVal,int cols,String align) {
		Row title1Row = sheet.createRow(rownum++);
		title1Row.setHeightInPoints(25);
		Cell title1Cell = title1Row.createCell(0);
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 10);//设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setColor(HSSFColor.BLACK.index);
		if(StringUtils.equals(align, "1")){
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}else if(StringUtils.equals(align, "2")){
			style.setAlignment(CellStyle.ALIGN_CENTER);
		}else if(StringUtils.equals(align, "3")){
			style.setAlignment(CellStyle.ALIGN_RIGHT);
		}
		style.setFont(font);
		title1Cell.setCellStyle(style);
		title1Cell.setCellValue(cellVal);
		sheet.addMergedRegion(new CellRangeAddress(title1Row.getRowNum(),
				title1Row.getRowNum(), 0, cols-1));
		
	}

	private void initialize(String title,HeaderEntity header) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		int cols = header.getCols();
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, cols-1));
		}
		if(StringUtils.isNotBlank(title)&&("华南理工大学管理职员职级初次认定情况一览表".equals(title)||"华南理工大学教职工转正及初次认定职务人员情况一览表".equals(title))){
			Row title1Row = sheet.createRow(rownum++);
			title1Row.setHeightInPoints(25);
			Cell title1Cell = title1Row.createCell(0);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			title1Cell.setCellStyle(style);
			title1Cell.setCellValue("单位:  (盖章)                             单位负责人签字:                                  填表日期:          年          月          日");
			sheet.addMergedRegion(new CellRangeAddress(title1Row.getRowNum(),
					title1Row.getRowNum(), 0, cols-1));
	
		}
		
		if(StringUtils.isNotBlank(title)&&"青年教师破格晋升高级专业技术职务申报人员汇总表".equals(title)){
			Row title1Row = sheet.createRow(rownum++);
			title1Row.setHeightInPoints(25);
			Cell title1Cell = title1Row.createCell(0);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			title1Cell.setCellStyle(style);
			title1Cell.setCellValue("单位:  (盖章)");
			sheet.addMergedRegion(new CellRangeAddress(title1Row.getRowNum(),
					title1Row.getRowNum(), 0, cols-1));
	
		}
		//起始行号， 起始列号，
		int rs=1,cs=0;
		int nextCol = 0;
		Row headerRow=null;
		//判断是否换行
		boolean isNext = false;
		List<HeaderCellEntity>  headerList = header.getThead();
		for (int i = 0,len=headerList.size(); i < len; i++) {
				HeaderCellEntity hcell  =  headerList.get(i);
				if(isNext || headerRow==null){
					rownum++;
					if("华南理工大学管理职员职级初次认定情况一览表".equals(title)||"华南理工大学教职工转正及初次认定职务人员情况一览表".equals(title)||"青年教师破格晋升高级专业技术职务申报人员汇总表".equals(title)){
						rs=2;
						headerRow = sheet.createRow(rs);
						headerRow.setHeightInPoints(50);
					}else{
						headerRow = sheet.createRow(rs);
					}
					//解决合并之后边框不能出来的问题
					if(nextCol >0){
						for(int j=0;j<nextCol;j++){  
							Cell cell1 = headerRow.createCell((short)j);  
							cell1.setCellValue("");
							CellStyle style =styles.get("header1");
							if("华南理工大学管理职员职级初次认定情况一览表".equals(title)||"华南理工大学教职工转正及初次认定职务人员情况一览表".equals(title)){
								style.setWrapText(true);
							}
							cell1.setCellStyle(style);  
						}
					}
					isNext=false;
				}
				//合并行
				int rowspan = hcell.getRowspan();
				//合并列
				int colspan = hcell.getColspan();
				//终止行号
				int re = rs+rowspan;
				//终止列号
				int ce = cs+colspan;
				
				if(rowspan>0){
					nextCol++;
				}
				//headerRow.setHeightInPoints(16);
				Cell cell = headerRow.createCell(cs);
				CellStyle style =styles.get("header1");
				if("华南理工大学管理职员职级初次认定情况一览表".equals(title)||"华南理工大学教职工转正及初次认定职务人员情况一览表".equals(title)){
					style.setWrapText(true);
				}
				cell.setCellStyle(style);
				cell.setCellValue(hcell.getValue());
				sheet.addMergedRegion(new CellRangeAddress(rs,(short)(re),cs,(short)ce));
				//解决合并之后边框不能出来的问题
				for(int j=cs+1;j<=ce;j++){  
					cell = headerRow.createCell((short)j);  
					cell.setCellValue("");  
					cell.setCellStyle(styles.get("header1"));  
				}
				//设置下个单元格的起始行号
				cs=ce+1;
				//断送是否下个单元格超最大列数
				if(cs%cols == 0){
					isNext = true;
					rs ++;
					cs=nextCol;
					
				}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}
	private void initialize(String title,HeaderEntity header,String sheets) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet(sheets);
		this.styles = createStyles(wb);
		int cols = header.getCols();
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), cols-1));
		}
		//起始行号， 起始列号，
		int rs=1,cs=0;
		int nextCol = 0;
		Row headerRow=null;
		//判断是否换行
		boolean isNext = false;
		List<HeaderCellEntity>  headerList = header.getThead();
		for (int i = 0,len=headerList.size(); i < len; i++) {
				HeaderCellEntity hcell  =  headerList.get(i);
				if(isNext || headerRow==null){
					rownum++;
					headerRow = sheet.createRow(rs);
					//解决合并之后边框不能出来的问题
					if(nextCol >0){
						for(int j=0;j<nextCol;j++){  
							Cell cell1 = headerRow.createCell((short)j);  
							cell1.setCellValue("");  
							cell1.setCellStyle(styles.get("header1"));  
						}
					}
					isNext=false;
				}
				//合并行
				int rowspan = hcell.getRowspan();
				//合并列
				int colspan = hcell.getColspan();
				//终止行号
				int re = rs+rowspan;
				//终止列号
				int ce = cs+colspan;
				
				if(rowspan>0){
					nextCol++;
				}
				//headerRow.setHeightInPoints(16);
				Cell cell = headerRow.createCell(cs);
				cell.setCellStyle(styles.get("header1"));
				cell.setCellValue(hcell.getValue());
				sheet.addMergedRegion(new CellRangeAddress(rs,(short)(re),cs,(short)ce));
				//解决合并之后边框不能出来的问题
				for(int j=cs+1;j<=ce;j++){  
					cell = headerRow.createCell((short)j);  
					cell.setCellValue("");  
					cell.setCellStyle(styles.get("header1"));  
				}
				//设置下个单元格的起始行号
				cs=ce+1;
				//断送是否下个单元格超最大列数
				if(cs%cols == 0){
					isNext = true;
					rs ++;
					cs=nextCol;
					
				}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}
	/**
	 * 初始化函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		if(this.wb == null){
			this.wb = new SXSSFWorkbook(500);
			this.sheet = wb.createSheet("Export");
			this.styles = createStyles(wb);
		}
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// Create header
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = null;
		if(headerList.size()>0){
			headerRow = sheet.createRow(rownum++);
			headerRow.setHeightInPoints(16);
		}
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}

	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);
		
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		Font titleFont2 = wb.createFont();
		titleFont2.setFontName("宋体");
		titleFont2.setFontHeightInPoints((short) 18);
		//titleFont2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont2);
		styles.put("title2", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font headerFont4 = wb.createFont();
		headerFont4.setFontName("宋体");
		headerFont4.setFontHeightInPoints((short) 11);
		//headerFont3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(headerFont4);
		styles.put("data4", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		//headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font headerFont1 = wb.createFont();
		headerFont1.setFontName("Arial");
		headerFont1.setFontHeightInPoints((short) 10);
		headerFont1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(headerFont1);
		styles.put("header1", style);
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font headerFont2 = wb.createFont();
		headerFont2.setFontName("宋体");
		headerFont2.setFontHeightInPoints((short) 11);
		headerFont2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(headerFont2);
		styles.put("header2", style);
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font headerFont3 = wb.createFont();
		headerFont3.setFontName("宋体");
		headerFont3.setFontHeightInPoints((short) 11);
		//headerFont3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(headerFont3);
		styles.put("header3", style);
		return styles;
	}


	/**
	 * 添加一行
	 * @return 行对象
	 */
	public Row addRow(){
		return sheet.createRow(rownum++);
	}
	

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val){
		return this.addCell(row, column, val, 0, Class.class);
	}
	
	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"+(align>=1&&align<=4?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			log.info("Set cell value ["+row.getRowNum()+","+column+"] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}
	
	/**
	 * 专业技术岗位晋级
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType,int exportType){
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		style.setWrapText(true);
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			log.info("Set cell value ["+row.getRowNum()+","+column+"] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		if(StringUtils.equals(String.valueOf(exportType), "3")&&val!=null&&StringUtils.contains(val.toString(), "不合格")){//工勤。考核情况变色
			CellStyle stylerow =  wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(HSSFColor.RED.index);//单位格红色
			stylerow.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
			stylerow.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			stylerow.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框 
			stylerow.setWrapText(true);
			stylerow.setFont(font);
			cell.setCellStyle(stylerow);
		}else{
			cell.setCellStyle(style);
		}
		return cell;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list){
		for (E e : list){
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				// Get entity value
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}else if (os[1] instanceof Method){
							val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[] {}, new Object[] {});
						}
					}
					// If is dict, get dict label
					if (StringUtils.isNotBlank(ef.dictType())){
						val = DictUtils.getDictLabel(val==null?"":val.toString(), ef.dictType(), "");
					}
				}catch(Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
			log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}
	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public  ExportExcel setDataList(List<Map<String,Object>> list,String[] colName){
		for (Map<String,Object> e : list){
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (String n : colName){
				Object val = e.get(n);
				this.addCell(row, colunm++, val, 2,String.class);
				sb.append(val + ", ");
			}
			log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}
	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public  ExportExcel setDataList(List<Map<String,Object>> list,String[] colName,int exportType){
		this.isPersonal=exportType;
		for (Map<String,Object> e : list){
			int colunm = 0;
			Row row = this.addRow();
			row.setHeightInPoints(80);
			StringBuilder sb = new StringBuilder();
			for (String n : colName){
				Object val = e.get(n);
				this.addCell(row, colunm++, val, 2,String.class,exportType);
				sb.append(val + ", ");
			}
			log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
		}
		if(this.isPersonal==1){
			Row lastRow = sheet.createRow(rownum++);
			lastRow.setHeightInPoints(30);
			Cell lastCell = lastRow.createCell(0);
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);//设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			lastRow.setHeightInPoints(30);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			style.setWrapText(true);
			lastCell.setCellStyle(style);
			lastCell.setCellValue("填表人：                                      联系电话：");
			sheet.addMergedRegion(new CellRangeAddress(lastRow.getRowNum(),
					lastRow.getRowNum(), 0, colName.length-1));
		}
		if(this.isPersonal!=1){
			Row titleRow = sheet.createRow(0);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue("华南理工大学初次职员职级认定情况一览表（"+list.size()+"人）");
		}
		return this;
	}
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException{
		wb.write(os);
		return this;
	}
	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
		write(response.getOutputStream());
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param fileName 输出文件名
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}
	
	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose(){
		wb.dispose();
		return this;
	}
	
//	/**
//	 * 导出测试
//	 */
	public static void main(String[] args) throws Throwable {
		ExportExcel.testHeader();
	
	}
	public static void testHeader()   throws Throwable {
		HeaderEntity headerEntity = new HeaderEntity(26);
		//第一行
		headerEntity.addCol(new HeaderCellEntity("系列", 2, 0));
		headerEntity.addCol(new HeaderCellEntity("单位", 2, 0));
		headerEntity.addCol(new HeaderCellEntity("正高", 0, 5));
		headerEntity.addCol(new HeaderCellEntity("副 高", 0, 5));
		headerEntity.addCol(new HeaderCellEntity("中级", 0, 5));
		headerEntity.addCol(new HeaderCellEntity("初级", 0, 5));
		//第二行
		headerEntity.addCol(new HeaderCellEntity("晋级", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("转评", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("委托代评", 0, 1));
		
		headerEntity.addCol(new HeaderCellEntity("晋级", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("转评", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("委托代评", 0, 1));
		
		headerEntity.addCol(new HeaderCellEntity("晋级", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("转评", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("委托代评", 0, 1));
		
		headerEntity.addCol(new HeaderCellEntity("晋级", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("转评", 0, 1));
		headerEntity.addCol(new HeaderCellEntity("委托代评", 0, 1));
		
		//第三行
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("姓名", 0, 0));
		headerEntity.addCol(new HeaderCellEntity("人数", 0, 0));
		
		ExportExcel ee = new ExportExcel("申报情况", headerEntity);
		
		ee.writeFile("target/export.xlsx");

		ee.dispose();
	
		log.debug("Export success.");
	}
	public static void testExport()  throws Throwable {
		List<String> headerList = Lists.newArrayList();
		for (int i = 1; i <= 10; i++) {
			headerList.add("表头"+i+"**"+"sdfsdfs");
		}
		
		List<String> dataRowList = Lists.newArrayList();
		for (int i = 1; i <= headerList.size(); i++) {
			dataRowList.add("数据"+i);
		}
		
		List<List<String>> dataList = Lists.newArrayList();
		for (int i = 1; i <=100; i++) {
			dataList.add(dataRowList);
		}

		ExportExcel ee = new ExportExcel("表格标题", headerList);
		
		for (int i = 0; i < dataList.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < dataList.get(i).size(); j++) {
				ee.addCell(row, j, dataList.get(i).get(j));
			}
		}
		
		ee.writeFile("target/export.xlsx");

		ee.dispose();
	
		log.debug("Export success.");
	}

}
