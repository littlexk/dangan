package com.thinkgem.jeesite.common.excel.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class TechColumnPositionMap {
	
	public static List<Map<String,Object>> columns = null;
	public static List<Map<String,Object>> columns1 = null;
	public static List<Map<String,Object>> columns2 = null;
	public static List<Map<String,Object>> columns3 = null;
	public static List<Map<String,Object>> columns4 = null;
	public static List<Map<String,Object>> columns5 = null;
	public static List<Map<String,Object>> columns6 = null;
	public static List<Map<String,Object>> columns7 = null;
	public static List<Map<String,Object>> columns8 = null;
	
	public static List<Map<String,Object>> pcolumns = null;
	public static List<Map<String,Object>> pcolumns1 = null;
	public static List<Map<String,Object>> pcolumns2 = null;
	public static List<Map<String,Object>> pcolumns3 = null;
	public static List<Map<String,Object>> pcolumns4 = null;
	public static List<Map<String,Object>> pcolumns5 = null;
	public static List<Map<String,Object>> pcolumns6 = null;
	
	public static List<Map<String,Object>> aftcolumns = null;
	public static List<Map<String,Object>> techColumns = null;
	
	public static List<Map<String,Object>> youthColumns = null;
	public static List<Map<String,Object>> youthPaperColumns = null;
	public static List<Map<String,Object>> youthAwardColumns = null;
	public static List<Map<String,Object>> youthProjectColumns = null;
	public static List<Map<String,Object>> youthPapentColumns = null;
	
	
	public static Map<String,Integer> sheetpos = null;//系列和sheet影射
	public static Map<String,Integer> errorpos = null;//系列和错误输出位置影射
	public static Map<String,Object> columnsMap = null;
	public static Map<String,Object> exportTypeSheet = new HashMap<String,Object>();//不同exportType对应不同sheetpos
	public static Map<String,Object> exportTypeError = new HashMap<String,Object>();//不同exportType对应不同error
	
	public static Map<String,Object> getColumnMap(List<Map<String,Object>> list){
		columnsMap = new HashMap<String,Object>();
		for(Map<String,Object> map:list){
			String columnName = (String)map.get("COLUMN_NAME");
			Object columnIndex = columnsMap.get(columnName);
			if(columnIndex != null&&!columnIndex.equals(map.get("COLUMN_INDEX"))){
				columnName = columnName + "_2";
			}
			columnsMap.put(columnName, map.get("COLUMN_INDEX"));
		}
		return columnsMap;
	}
	
	//先上岗
	public static Map<String,Integer> getSheetPos(Object exportType,int jobType){
		sheetpos = new HashMap<String,Integer>();
		//公共
		sheetpos.put("010101", 0);//教师系列(一般教师系统),教授
		sheetpos.put("010103", 0);//教师系列(设计类教师系列),
		sheetpos.put("010104", 0);//教师系列(音乐舞蹈类教师系列)
		sheetpos.put("010105", 0);//教师系列(体育类教师系列)
		
		if("1".equals(exportType)||"2".equals(exportType)){//正高和副高
			sheetpos.put("0101", 1);//教学岗位教师系列(参照申报类型),教学岗教授
			sheetpos.put("0102", 2);//专职科研系列,研究员
			sheetpos.put("010102",3);//学生思教教师系列
		}
		
		return sheetpos;
	}
	
	public static Map<String,Integer> getSheetPos(Object exportType){
		//sheetpos = (Map<String,Integer>)exportTypeSheet.get(exportType);
		//if(sheetpos == null){
		sheetpos = new HashMap<String,Integer>();
			//exportTypeSheet.put((String)exportType, sheetpos);
		//}else{
			//return sheetpos;
		//}
		
		//公共
		sheetpos.put("010101", 0);//教师系列(一般教师系统),教授
		sheetpos.put("010103", 0);//教师系列(设计类教师系列),
		sheetpos.put("010104", 0);//教师系列(音乐舞蹈类教师系列)
		sheetpos.put("010105", 0);//教师系列(体育类教师系列)
		
		if("1".equals(exportType)||"2".equals(exportType)){//正高和副高
			sheetpos.put("0101", 1);//教学岗位教师系列(参照申报类型),教学岗教授
			sheetpos.put("02", 2);//专职科研系列,研究员
			sheetpos.put("03", 3);//工程技术系列,教授级高级工程师
			sheetpos.put("04", 4);//实验技术系列,教授级高级实验师实验师
			sheetpos.put("05", 5);//高教管理系列,高教管理系列研究员
			sheetpos.put("010102",6);//学生思教教师系列
		}
		if("2".equals(exportType)){//副高才有
			sheetpos.put("06",7);//博士后副研究生(属其它系列)
		}
		
		if("3".equals(exportType)||"4".equals(exportType)||
				"3','4".equals(exportType)){//讲师或助教类
			sheetpos.put("02", 1);//专职科研系列,研究员
			sheetpos.put("03", 2);//工程技术系列,
			sheetpos.put("04", 3);//实验技术系列,实验师
			sheetpos.put("05", 4);//高教管理系列,高教管理系列研究员
			sheetpos.put("010102",5);//学生思教教师系列
			//sheetpos.put("0107", 6);//图书资料系列，馆员
			
			
			//tech
			sheetpos.put("07", 0);//图书资料系列，馆员
			sheetpos.put("09", 1);///卫生系列，
			sheetpos.put("10", 2);//幼儿园（小学）教师系列，
			sheetpos.put("11", 3);//中学教师 系列，
			sheetpos.put("12", 4);//统计专业系列，
			sheetpos.put("13", 5);//会计系列，
			sheetpos.put("14", 6);//审计专业 系列，
			sheetpos.put("15", 7);//科学研究 系列，
			sheetpos.put("16", 8);//出版专业系列，
			sheetpos.put("17", 9);//翻译 系列，
			sheetpos.put("19", 10);//新闻 系列，
			sheetpos.put("08", 10);
			sheetpos.put("20", 11);//经济专业系列，
			sheetpos.put("21", 12);//文物博物专业系列，
			sheetpos.put("22", 13);//档案专业系列，
			sheetpos.put("23", 14);//艺术专业系列，
		}
		return sheetpos;
	}
	
	/**
	 * 获取错误位置
	 * @param exportType 1正高、2副高、3初中级
	 * @return
	 */
	public static Map<String,Integer> getErrorPos(Object exportType){
		//errorpos = (Map<String,Integer>)exportTypeError.get(exportType);
		//if(errorpos == null){
		errorpos = new HashMap<String,Integer>();
			//exportTypeSheet.put((String)exportType, errorpos);
		//}else{
			//return errorpos;
		//}
		if("1".equals(exportType)||"2".equals(exportType)){//正高、副高
			errorpos.put("010101", 6);//必备条件
			errorpos.put("010101_", 31);//可选条件
			errorpos.put("010103", 6);
			errorpos.put("010103_", 31);
			errorpos.put("010104", 6);
			errorpos.put("010104_", 31);
			errorpos.put("010105", 6);
			errorpos.put("010105_", 31);
			errorpos.put("0101",   6);
			errorpos.put("0101_",   31);
			/*errorpos.put("0102",   5);
			errorpos.put("0102_",   21);
			errorpos.put("0103",   5);
			errorpos.put("0103_",   21);
			errorpos.put("0104",   5);
			errorpos.put("0104_",   23);
			errorpos.put("0105",   5);
			errorpos.put("0105_",   22);*/
			errorpos.put("010102", 6);
			errorpos.put("010102_", 32);
			errorpos.put("0106",   5);
			errorpos.put("0106_",   20);
			//tech

			errorpos.put("02", 5);//必备条件
			errorpos.put("02_", 21);//可选条件
			errorpos.put("03", 5);//必备条件
			errorpos.put("03_", 21);//可选条件
			errorpos.put("04", 5);//必备条件
			errorpos.put("04_", 23);//可选条件
			errorpos.put("05", 5);//必备条件
			errorpos.put("05_", 22);//可选条件
			
		}
		if("3".equals(exportType)||"4".equals(exportType)||
				"3','4".equals(exportType)){ //初中级
			errorpos.put("010101", 5);
			errorpos.put("010103", 5);
			errorpos.put("010104", 5);
			errorpos.put("010105", 5);
			errorpos.put("0101",   5);
			errorpos.put("02",   5);
			errorpos.put("03",   5);
			errorpos.put("04",   5);
			errorpos.put("05",   5);
			errorpos.put("010102", 5);
			errorpos.put("06",   5);
			errorpos.put("0107",   5);//图书资料系列
			
			errorpos.put("07", 5);//图书资料系列，馆员
			errorpos.put("09", 5);///卫生系列，
			errorpos.put("10", 5);//幼儿园（小学）教师系列，
			errorpos.put("11", 5);//中学教师 系列，
			errorpos.put("12", 5);//统计专业系列，
			errorpos.put("13", 5);//会计系列，
			errorpos.put("14", 5);//审计专业 系列，
			errorpos.put("15", 5);//科学研究 系列，
			errorpos.put("16", 5);//出版专业系列，
			errorpos.put("17", 5);//翻译 系列，
			errorpos.put("19", 5);//新闻 系列，
			errorpos.put("08", 5);
			errorpos.put("20", 5);//经济专业系列，
			errorpos.put("21", 5);//文物博物专业系列，
			errorpos.put("22", 5);//档案专业系列，
			errorpos.put("23", 5);//艺术专业系列，
		}
		return errorpos;
	}
	

/*
	//专业技术岗晋级一览表导出
	public static List<Map<String,Object>> getTechColumnPosMap(int type,String postSort,String after) throws Exception{
		if(techColumns ==null){
			techColumns = new ArrayList<Map<String,Object>>();
		}else{
			return techColumns;
		}
		techColumns.add(getColumn("ROWINDEX","0"));
		
		techColumns.add(getColumn("DEPT_NAME","1"));
		
		techColumns.add(getColumn("NAME_BIRTHDAY","2"));
		
		techColumns.add(getColumn("A_TECHNIC_NAME","3"));
		
		techColumns.add(getColumn("EXAM_COND","4"));
		
		techColumns.add(getColumn("TECHNIC_DATE_LIMIT","5"));
		
		techColumns.add(getColumn("TECH1","6"));
		
		techColumns.add(getColumn("TECH2","7"));
		
		techColumns.add(getColumn("TECH3","8"));
		
		techColumns.add(getColumn("TECH4","9"));
		
		techColumns.add(getColumn("TECH5","10"));
		
		techColumns.add(getColumn("TECH6","11"));
		
		techColumns.add(getColumn("TECH7","12"));
		
		techColumns.add(getColumn("HEAD_TEACHRE","13"));
		
		techColumns.add(getColumn("PAPER_1","14"));
		
		techColumns.add(getColumn("PAPER_2","15"));
		
		techColumns.add(getColumn("PROJECT_1","16"));
		
		techColumns.add(getColumn("PROJECT_2","17"));
		
		techColumns.add(getColumn("PROJECT_3","18"));
		
		techColumns.add(getColumn("PROJECT_4","19"));
		
		techColumns.add(getColumn("PROJECT_5","20"));
		
		techColumns.add(getColumn("PROJECT_6","21"));
		
		techColumns.add(getColumn("PROJECT_7","22"));
		
		techColumns.add(getColumn("PROJECT_8","23"));
		
		techColumns.add(getColumn("PROJECT_9","24"));
		
		techColumns.add(getColumn("PROJECT_10","25"));
		
		techColumns.add(getColumn("PROJECT_11","26"));
		
		techColumns.add(getColumn("PROJECT_12","27"));
		
		techColumns.add(getColumn("AWARD_1","28"));
		
		techColumns.add(getColumn("AWARD_2","29"));
		
		techColumns.add(getColumn("AWARD_3","30"));
		
		techColumns.add(getColumn("AWARD_4","31"));
		
		techColumns.add(getColumn("AWARD_5","32"));
		
		techColumns.add(getColumn("PAPER_1","33"));
		
		techColumns.add(getColumn("PAPER_2","34"));
		
		techColumns.add(getColumn("PAPER_3","35"));
		
		techColumns.add(getColumn("BOOK_1","36"));
		
		techColumns.add(getColumn("BOOK_2","37"));
		
		techColumns.add(getColumn("BOOK_3","38"));
		
		techColumns.add(getColumn("BOOK_4","39"));
		
		techColumns.add(getColumn("PROJECT_13","40"));
		
		techColumns.add(getColumn("PROJECT_1","41"));
		
		techColumns.add(getColumn("PROJECT_2","42"));
		
		techColumns.add(getColumn("PROJECT_3","43"));
		
		techColumns.add(getColumn("PROJECT_4","44"));
		
		techColumns.add(getColumn("PROJECT_5","45"));
		
		techColumns.add(getColumn("PROJECT_6","46"));
		
		techColumns.add(getColumn("PROJECT_7","47"));
		
		techColumns.add(getColumn("PROJECT_8","48"));
		
		techColumns.add(getColumn("PROJECT_9","49"));
		
		techColumns.add(getColumn("PROJECT_10","50"));
		
		techColumns.add(getColumn("PROJECT_11","51"));
		
		techColumns.add(getColumn("PROJECT_12","52"));
		
		techColumns.add(getColumn("QUALITY_1","53"));
		
		techColumns.add(getColumn("PATENT_1","54"));
		
		techColumns.add(getColumn("PATENT_2","55"));
		
		techColumns.add(getColumn("PATENT_3","56"));
		
		techColumns.add(getColumn("PATENT_4","57"));
		
		techColumns.add(getColumn("PATENT_5","58"));
		
		techColumns.add(getColumn("PATENT_6","59"));
		
		return techColumns;
	}*/
		

	/**
	 * 根据级别
	 * 及职务类型
	 * 确定系列
	 * @param technicType 职务类型
	 * @param exportType 级别
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> getColumnPosMap(String postSort,String technicType,Object exportType) throws Exception{
		if("1".equals(exportType)||"2".equals(exportType)){
			return getColumnPosMap(postSort,technicType);
		}else if("3".equals(exportType)||"4".equals(exportType)||
				"3','4".equals(exportType)){
			return getColumnPosMapPrimary(postSort,technicType);
		}else{
			throw new Exception("没有"+exportType+"类型一览表!");
		}
	}
	
	public static List<Map<String,Object>> getColumnPosMap(String postSort,String technicType) throws Exception{
		//if("01".equals(technicType)){//教师系列
			if("010102".equals(postSort)){//学生思教
				return getColumnPosMap6();
			}else if("010101".equals(postSort)){//一般教师
				return getColumnPosMap1();
			}else if("010103".equals(postSort)){//
				return getColumnPosMap1();
			}else if("010104".equals(postSort)){//
				return getColumnPosMap1();
			}else if("010105".equals(postSort)){//
				return getColumnPosMap1();
			}else if("0101".equals(postSort)){//
				return getColumnPosMap1();
		}else if("02".equals(technicType)){//专职科研 系列
			return getColumnPosMap2();
		}else if("03".equals(technicType)){//工程 系列
			return getColumnPosMap3();
		}else if("04".equals(technicType)){//实验 系列
			return getColumnPosMap4();
		}else if("05".equals(technicType)){//高教管理 系列
			return getColumnPosMap5();
		}else if("0106".equals(postSort)){
			return getColumnPosMap7();
		}else{
			throw new Exception("没有"+postSort+"系列!");
		}
	}
	/**
	 * 中初级
	 * 一览表
	 * @param postSort
	 * @param technicType
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> getColumnPosMapPrimary(String postSort, String technicType) throws Exception{
		//if("01".equals(technicType)){//教师系列	中初级
			if("010102".equals(postSort)){//学生思教 	中初级
				return getColumnPosMapPrimary6();
			}else if("010101".equals(postSort)){//一般教师	中初级
				return getColumnPosMapPrimary();
			}else if("010103".equals(postSort)){//
				return getColumnPosMapPrimary();
			}else if("010104".equals(postSort)){//
				return getColumnPosMapPrimary();
			}else if("010105".equals(postSort)){//
				return getColumnPosMapPrimary();
			}else if("0101".equals(postSort)){//
				return getColumnPosMapPrimary();
			
		}else if("02".equals(technicType)){//专职科研 系列	中初级
			return getColumnPosMapPrimary2();//专职科研	中初级
		}else if("03".equals(technicType)){//工程 系列	 	中初级
			return getColumnPosMapPrimary3();
		}else if("04".equals(technicType)){//实验 系列		中初级
			return getColumnPosMapPrimary4();
		}else if("05".equals(technicType)){//高教管理 系列	中初级
			return getColumnPosMapPrimary5();
		}/*else if("0107".equals(postSort)){//图书系列 
			return getColumnPosMap8();
		}*/else{
			return getColumnPosMapTechOther();//默认	使用其他 
		}

	}
	
	public static Map<String,Object> getColumn(String columnName,String columnIndex){
		Map<String, Object> zero = Maps.newHashMap();
		zero.put("COLUMN_NAME", columnName);
		zero.put("COLUMN_INDEX", columnIndex);
		return zero;
	}

			
	//教师系列
	public static  List<Map<String,Object>> getColumnPosMap1(){
		    columns = new ArrayList<Map<String,Object>>();
		columns.add(getColumn("ROWINDEX","0"));
		
		columns.add(getColumn("DEPT_NAME","1"));
		
		columns.add(getColumn("NAME","2"));
		
		columns.add(getColumn("A_TECHNIC_NAME","3"));
		
		columns.add(getColumn("TECH_TEAM","4"));
		
		columns.add(getColumn("EXAM_YEAR3","5"));
		
		columns.add(getColumn("EDUCATION_NAME","6"));
		
		columns.add(getColumn("TECHNIC_NAME","7"));
		
		columns.add(getColumn("TECH1","8"));
		
		columns.add(getColumn("TECH2","9"));
		
		columns.add(getColumn("TECH3","10"));
		
		columns.add(getColumn("TECH4","11"));
		
		columns.add(getColumn("TECH5","12"));
		
		columns.add(getColumn("TECH6","13"));
		
		columns.add(getColumn("TECH7","14"));
		
		columns.add(getColumn("HEAD_TEACHRE","15"));
		
		columns.add(getColumn("PAPER_1","16"));
		
		columns.add(getColumn("PAPER_2","17"));
		
		columns.add(getColumn("PROJECT_1","18"));
		
		columns.add(getColumn("PROJECT_2","19"));
		
		columns.add(getColumn("PROJECT_3","20"));
		
		columns.add(getColumn("PROJECT_4","21"));
		
		columns.add(getColumn("PROJECT_5","22"));
		
		columns.add(getColumn("PROJECT_6","23"));
		
		columns.add(getColumn("PROJECT_7","24"));
		
		columns.add(getColumn("PROJECT_8","25"));
		
		columns.add(getColumn("PROJECT_9","26"));
		
		columns.add(getColumn("PROJECT_10","27"));
		
		columns.add(getColumn("PROJECT_11","28"));
		
		columns.add(getColumn("PROJECT_12","29"));
		
		columns.add(getColumn("FOREIGN_LANG_F","30"));
		
		columns.add(getColumn("AWARD_1","31"));
		
		columns.add(getColumn("AWARD_2","32"));
		
		columns.add(getColumn("AWARD_3","33"));
		
		columns.add(getColumn("AWARD_4","34"));
		
		columns.add(getColumn("AWARD_5","35"));
		
		columns.add(getColumn("PAPER_1","36"));
		
		columns.add(getColumn("PAPER_2","37"));
		
		columns.add(getColumn("PAPER_3","38"));
		
		columns.add(getColumn("BOOK_1","39"));
		
		columns.add(getColumn("BOOK_2","40"));
		
		columns.add(getColumn("BOOK_3","41"));
		
		columns.add(getColumn("BOOK_4","42"));
		
		columns.add(getColumn("PROJECT_13","43"));
		
		columns.add(getColumn("PROJECT_1","44"));
		
		columns.add(getColumn("PROJECT_2","45"));
		
		columns.add(getColumn("PROJECT_3","46"));
		
		columns.add(getColumn("PROJECT_4","47"));
		
		columns.add(getColumn("PROJECT_5","48"));
		
		columns.add(getColumn("PROJECT_6","49"));
		
		columns.add(getColumn("PROJECT_7","50"));
		
		columns.add(getColumn("PROJECT_8","51"));
		
		columns.add(getColumn("PROJECT_9","52"));
		
		columns.add(getColumn("PROJECT_10","53"));
		
		columns.add(getColumn("PROJECT_11","54"));
		
		columns.add(getColumn("PROJECT_12","55"));
		
		columns.add(getColumn("QUALITY_1","56"));
		
		columns.add(getColumn("PATENT_1","57"));
		
		columns.add(getColumn("PATENT_2","58"));
		
		columns.add(getColumn("PATENT_3","59"));
		
		columns.add(getColumn("PATENT_4","60"));
		
		columns.add(getColumn("PATENT_5","61"));
		
		columns.add(getColumn("PATENT_6","62"));
		/*
		columns.add(getColumn("INVEST","63"));
		
		columns.add(getColumn("COLLEGEREVIEW","64"));*/

		return columns;
	}

	//教师系列，初中级
	public static List<Map<String,Object>> getColumnPosMapPrimary(){
		if(pcolumns ==null){
			pcolumns = new ArrayList<Map<String,Object>>();
		}else{
			return pcolumns;
		}
		pcolumns.add(getColumn("ROWINDEX","0"));
		
		pcolumns.add(getColumn("DEPT_NAME","1"));
		
		pcolumns.add(getColumn("NAME","2"));
		
		pcolumns.add(getColumn("A_TECHNIC_NAME","3"));
		
		pcolumns.add(getColumn("EXAM_YEAR3","4"));
		
		pcolumns.add(getColumn("EDUCATION_NAME","5"));
		
		pcolumns.add(getColumn("TECHNIC_NAME","6"));
		
		pcolumns.add(getColumn("TECH1","7"));
		
		pcolumns.add(getColumn("TECH2","8"));
		
		pcolumns.add(getColumn("TECH5","9"));
		
		pcolumns.add(getColumn("TECH6","10"));
		
		pcolumns.add(getColumn("TECH7","11"));
		
		pcolumns.add(getColumn("HEAD_TEACHRE","12"));
		
		pcolumns.add(getColumn("PAPER_4","13"));
		
		pcolumns.add(getColumn("FOREIGN_LANG_F","14"));
		
		pcolumns.add(getColumn("PROJECT_13","15"));
		
		pcolumns.add(getColumn("PROJECT_1","16"));
		
		pcolumns.add(getColumn("PROJECT_2","17"));
		
		pcolumns.add(getColumn("PROJECT_3","18"));
		
		pcolumns.add(getColumn("PROJECT_4","19"));
		
		pcolumns.add(getColumn("PROJECT_5","20"));
		
		pcolumns.add(getColumn("PROJECT_6","21"));
		
		pcolumns.add(getColumn("PROJECT_7","22"));
		
		pcolumns.add(getColumn("PROJECT_8","23"));
		
		pcolumns.add(getColumn("PROJECT_9","24"));
		
		pcolumns.add(getColumn("PROJECT_10","25"));
		
		pcolumns.add(getColumn("PROJECT_11","26"));
		
		pcolumns.add(getColumn("PROJECT_12","27"));
		
		pcolumns.add(getColumn("AWARD_1","28"));
		
		pcolumns.add(getColumn("AWARD_2","29"));
		
		pcolumns.add(getColumn("AWARD_3","30"));
		
		pcolumns.add(getColumn("AWARD_4","31"));		
		
		pcolumns.add(getColumn("AWARD_5","32"));
		
		pcolumns.add(getColumn("BOOK_1","33"));
		
		pcolumns.add(getColumn("BOOK_2","34"));
		
		pcolumns.add(getColumn("BOOK_3","35"));
		
		pcolumns.add(getColumn("BOOK_4","36"));		
		
		pcolumns.add(getColumn("PATENT_1","37"));
		
		pcolumns.add(getColumn("PATENT_2","38"));

		pcolumns.add(getColumn("PATENT_3","39"));
		
		pcolumns.add(getColumn("PATENT_4","40"));
		
		pcolumns.add(getColumn("PATENT_5","41"));
		
		pcolumns.add(getColumn("PATENT_6","42"));
		
		/*pcolumns.add(getColumn("COLLEGEREVIEW","43"));
		
		pcolumns.add(getColumn("REMARK","44"));*/
		
		return pcolumns;
	}
	
	//专职科研系列
		public static List<Map<String,Object>> getColumnPosMap2(){
			if(columns1 ==null){
				columns1 = new ArrayList<Map<String,Object>>();
			}else{
				return columns1;
			}
			columns1.add(getColumn("ROWINDEX","0"));
			
			columns1.add(getColumn("DEPT_NAME","1"));
			
			columns1.add(getColumn("NAME","2"));
			
			columns1.add(getColumn("A_TECHNIC_NAME","3"));
			
			columns1.add(getColumn("EXAM_YEAR3","4"));
			
			columns1.add(getColumn("EDUCATION_NAME","5"));
			
			columns1.add(getColumn("TECHNIC_NAME","6"));
			
			columns1.add(getColumn("PAPER_1","7"));
			
			columns1.add(getColumn("PROJECT_1","8"));
			
			columns1.add(getColumn("PROJECT_2","9"));
			
			columns1.add(getColumn("PROJECT_3","10"));
			
			columns1.add(getColumn("PROJECT_4","11"));
			
			columns1.add(getColumn("PROJECT_5","12"));
			
			columns1.add(getColumn("PROJECT_6","13"));
			
			columns1.add(getColumn("PROJECT_7","14"));
			
			columns1.add(getColumn("PROJECT_8","15"));
			
			columns1.add(getColumn("PROJECT_9","16"));
			
			columns1.add(getColumn("PROJECT_10","17"));
			
			columns1.add(getColumn("PROJECT_11","18"));
			
			columns1.add(getColumn("PROJECT_12","19"));
			
			columns1.add(getColumn("FOREIGN_LANG_F","20"));
			
			columns1.add(getColumn("AWARD_1","21"));
			
			columns1.add(getColumn("AWARD_2","22"));
			
			columns1.add(getColumn("AWARD_3","23"));
			
			columns1.add(getColumn("AWARD_4","24"));
			
			columns1.add(getColumn("PAPER_1","25"));
			
			columns1.add(getColumn("PAPER_3","26"));
			
			columns1.add(getColumn("BOOK_1","27"));
			
			columns1.add(getColumn("BOOK_2","28"));
			
			columns1.add(getColumn("BOOK_3","29"));
			
			columns1.add(getColumn("BOOK_4","30"));
			
			columns1.add(getColumn("PROJECT_13","31"));
			
			columns1.add(getColumn("PROJECT_1","32"));
			
			columns1.add(getColumn("PROJECT_2","33"));
			
			columns1.add(getColumn("PROJECT_3","34"));
			
			columns1.add(getColumn("PROJECT_4","35"));
			
			columns1.add(getColumn("PROJECT_5","36"));
			
			columns1.add(getColumn("PROJECT_6","37"));
			
			columns1.add(getColumn("PROJECT_7","38"));
			
			columns1.add(getColumn("PROJECT_8","39"));
			
			columns1.add(getColumn("PROJECT_9","40"));
			
			columns1.add(getColumn("PROJECT_10","41"));
			
			columns1.add(getColumn("PROJECT_11","42"));
			
			columns1.add(getColumn("PROJECT_12","43"));
			
			columns1.add(getColumn("PATENT_1","44"));
			
			columns1.add(getColumn("PATENT_2","45"));
			
			columns1.add(getColumn("PATENT_3","46"));
			
			columns1.add(getColumn("PATENT_4","47"));
			
			columns1.add(getColumn("PATENT_5","48"));
			
			columns1.add(getColumn("PATENT_6","49"));
			
			/*columns1.add(getColumn("INVEST","50"));
			
			columns1.add(getColumn("COLLEGEREVIEW","51"));
			
			columns1.add(getColumn("REMARK","52"));	*/		
			
			return columns1;
		}
		
		//专职，中初级
		public static List<Map<String,Object>> getColumnPosMapPrimary2(){
			if(pcolumns2 ==null){
				pcolumns2 = new ArrayList<Map<String,Object>>();
			}else{
				return pcolumns2;
			}
			pcolumns2.add(getColumn("ROWINDEX","0"));
			
			pcolumns2.add(getColumn("DEPT_NAME","1"));	
			
			pcolumns2.add(getColumn("NAME","2"));	
			
			pcolumns2.add(getColumn("A_TECHNIC_NAME","3"));	
			
			pcolumns2.add(getColumn("EXAM_YEAR3","4"));	
			
			pcolumns2.add(getColumn("EDUCATION_NAME","5"));	
			
			pcolumns2.add(getColumn("TECHNIC_NAME","6"));
			
			pcolumns2.add(getColumn("PAPER_4","7"));	
			
			pcolumns2.add(getColumn("FOREIGN_LANG_F","8"));	
			
			pcolumns2.add(getColumn("PROJECT_13","9"));	
			
			pcolumns2.add(getColumn("PROJECT_1","10"));	
			
			pcolumns2.add(getColumn("PROJECT_2","11"));	
			
			pcolumns2.add(getColumn("PROJECT_3","12"));	
			
			pcolumns2.add(getColumn("PROJECT_4","13"));
			
			pcolumns2.add(getColumn("PROJECT_5","14"));
			
			pcolumns2.add(getColumn("PROJECT_6","15"));
			
			pcolumns2.add(getColumn("PROJECT_7","16"));
			
			pcolumns2.add(getColumn("PROJECT_8","17"));
			
			pcolumns2.add(getColumn("PROJECT_9","18"));
			
			pcolumns2.add(getColumn("PROJECT_10","19"));
			
			pcolumns2.add(getColumn("PROJECT_11","20"));
			
			pcolumns2.add(getColumn("PROJECT_12","21"));
			
			pcolumns2.add(getColumn("BOOK_1","22"));
			
			pcolumns2.add(getColumn("BOOK_2","23"));
			
			pcolumns2.add(getColumn("BOOK_3","24"));
			
			pcolumns2.add(getColumn("BOOK_4","25"));
			
			pcolumns2.add(getColumn("PATENT_1","26"));
			
			pcolumns2.add(getColumn("PATENT_2","27"));
			
			pcolumns2.add(getColumn("PATENT_3","28"));
			
			pcolumns2.add(getColumn("PATENT_4","29"));
			
			pcolumns2.add(getColumn("PATENT_5","30"));
			/*
			pcolumns2.add(getColumn("INVEST","31"));	
			
			pcolumns2.add(getColumn("COLLEGEREVIEW","32"));	
			
			pcolumns2.add(getColumn("REMARK","33"));*/	
			
			return pcolumns2;
		}
		
		//工程技术系列
		public static List<Map<String,Object>> getColumnPosMap3(){
			if(columns2 ==null){
				columns2 = new ArrayList<Map<String,Object>>();
			}else{
				return columns2;
			}

			columns2.add(getColumn("ROWINDEX","0"));
			
			columns2.add(getColumn("DEPT_NAME","1"));	
			
			columns2.add(getColumn("NAME","2"));	
			
			columns2.add(getColumn("A_TECHNIC_NAME","3"));	
			
			columns2.add(getColumn("EXAM_YEAR3","4"));	
			
			columns2.add(getColumn("EDUCATION_NAME","5"));	
			
			columns2.add(getColumn("TECHNIC_NAME","6"));	
			
			columns2.add(getColumn("PAPER_1","7"));	
			
			columns2.add(getColumn("PROJECT_1","8"));	
			
			columns2.add(getColumn("PROJECT_2","9"));	
			
			columns2.add(getColumn("PROJECT_3","10"));	
			
			columns2.add(getColumn("PROJECT_4","11"));	
			
			columns2.add(getColumn("PROJECT_5","12"));	
			
			columns2.add(getColumn("PROJECT_6","13"));
			
			columns2.add(getColumn("PROJECT_7","14"));
			
			columns2.add(getColumn("PROJECT_8","15"));
			
			columns2.add(getColumn("PROJECT_9","16"));
			
			columns2.add(getColumn("PROJECT_10","17"));
			
			columns2.add(getColumn("PROJECT_11","18"));
			
			columns2.add(getColumn("PROJECT_12","19"));
			
			columns2.add(getColumn("FOREIGN_LANG_F","20"));
			
			columns2.add(getColumn("PROTEACH","21"));
			
			columns2.add(getColumn("AWARD_1","22"));
			
			columns2.add(getColumn("AWARD_2","23"));
			
			columns2.add(getColumn("AWARD_3","24"));
			
			columns2.add(getColumn("AWARD_4","25"));
			
			columns2.add(getColumn("PAPER_1","26"));
			
			columns2.add(getColumn("PAPER_3","27"));
			
			columns2.add(getColumn("BOOK_1","28"));
			
			columns2.add(getColumn("BOOK_2","29"));
			
			columns2.add(getColumn("BOOK_3","30"));
			
			columns2.add(getColumn("BOOK_4","31"));	
			
			columns2.add(getColumn("PROJECT_13","32"));	
			
			columns2.add(getColumn("PROJECT_1","33"));	
			
			columns2.add(getColumn("PROJECT_2","34"));	
			
			columns2.add(getColumn("PROJECT_3","35"));	
			
			columns2.add(getColumn("PROJECT_4","36"));	
			
			columns2.add(getColumn("PROJECT_5","37"));	
			
			columns2.add(getColumn("PROJECT_6","38"));	
			
			columns2.add(getColumn("PROJECT_7","39"));	
			
			columns2.add(getColumn("PROJECT_8","40"));

			columns2.add(getColumn("PROJECT_9","41"));	
			
			columns2.add(getColumn("PROJECT_10","42"));	
			
			columns2.add(getColumn("PROJECT_11","43"));
			
			columns2.add(getColumn("PROJECT_12","44"));
			
			columns2.add(getColumn("PROJECT_15","45"));
			
			columns2.add(getColumn("PATENT_1","46"));
			
			columns2.add(getColumn("PATENT_2","47"));
			
			columns2.add(getColumn("PATENT_3","48"));
			
			columns2.add(getColumn("PATENT_4","49"));
			
			columns2.add(getColumn("PATENT_5","50"));
			
			columns2.add(getColumn("PATENT_6","51"));
			
			/*columns2.add(getColumn("INVEST","52"));
			
			columns2.add(getColumn("COLLEGEREVIEW","53"));
			
			columns2.add(getColumn("RESULT","54"));
			
			columns2.add(getColumn("REMARK","55"));*/
			return columns2;
		}
		
		   //工程技术，中初级
				public static List<Map<String,Object>> getColumnPosMapPrimary3(){
					if(pcolumns3 ==null){
						pcolumns3 = new ArrayList<Map<String,Object>>();
					}else{
						return pcolumns3;
					}
					pcolumns3.add(getColumn("ROWINDEX","0"));
					pcolumns3.add(getColumn("DEPT_NAME","1"));	
					pcolumns3.add(getColumn("NAME","2"));	
					pcolumns3.add(getColumn("A_TECHNIC_NAME","3"));	
					pcolumns3.add(getColumn("EXAM_YEAR3","4"));	
					pcolumns3.add(getColumn("EDUCATION_NAME","5"));	
					pcolumns3.add(getColumn("TECHNIC_NAME","6"));	
					pcolumns3.add(getColumn("PAPER_4","7"));	
					pcolumns3.add(getColumn("FOREIGN_LANG_F","8"));	
					pcolumns3.add(getColumn("PROJECT_13","9"));	
					pcolumns3.add(getColumn("PROJECT_1","10"));
					
					pcolumns3.add(getColumn("PROJECT_2","11"));	
					pcolumns3.add(getColumn("PROJECT_3","12"));	
					pcolumns3.add(getColumn("PROJECT_4","13"));
					pcolumns3.add(getColumn("PROJECT_5","14"));
					pcolumns3.add(getColumn("PROJECT_6","15"));
					pcolumns3.add(getColumn("PROJECT_7","16"));
					pcolumns3.add(getColumn("PROJECT_8","17"));
					pcolumns3.add(getColumn("PROJECT_9","18"));
					pcolumns3.add(getColumn("PROJECT_10","19"));
					pcolumns3.add(getColumn("PROJECT_11","20"));
					
					pcolumns3.add(getColumn("PROJECT_12","21"));
					pcolumns3.add(getColumn("AWARD_1","22"));
					pcolumns3.add(getColumn("AWARD_2","23"));
					pcolumns3.add(getColumn("AWARD_3","24"));
					pcolumns3.add(getColumn("AWARD_4","25"));
					pcolumns3.add(getColumn("BOOK_1","26"));
					pcolumns3.add(getColumn("BOOK_2","27"));
					pcolumns3.add(getColumn("BOOK_3","28"));
					pcolumns3.add(getColumn("BOOK_4","29"));
					pcolumns3.add(getColumn("","30"));
					
					pcolumns3.add(getColumn("PATENT_1","31"));	
					pcolumns3.add(getColumn("PATENT_2","32"));	
					pcolumns3.add(getColumn("PATENT_3","33"));	
					pcolumns3.add(getColumn("PATENT_4","34"));	
					pcolumns3.add(getColumn("PATENT_5","35"));	
					pcolumns3.add(getColumn("PATENT_6","36"));	
				/*	pcolumns3.add(getColumn("COLLEGEREVIEW","37"));	
					pcolumns3.add(getColumn("","38"));	
					pcolumns3.add(getColumn("REMARK","39"));*/	

					return pcolumns3;
				}
	
				//实验技术，中初级
				public static List<Map<String,Object>> getColumnPosMapPrimary4(){
					if(pcolumns4 ==null){
						pcolumns4 = new ArrayList<Map<String,Object>>();
					}else{
						return pcolumns4;
					}
					pcolumns4.add(getColumn("ROWINDEX","0"));
					pcolumns4.add(getColumn("DEPT_NAME","1"));	
					pcolumns4.add(getColumn("NAME","2"));	
					pcolumns4.add(getColumn("A_TECHNIC_NAME","3"));	
					pcolumns4.add(getColumn("EXAM_YEAR3","4"));	
					pcolumns4.add(getColumn("EDUCATION_NAME","5"));	
					pcolumns4.add(getColumn("TECHNIC_NAME","6"));	
					pcolumns4.add(getColumn("TECH70","7"));	
					pcolumns4.add(getColumn("TECH71","8"));	
					pcolumns4.add(getColumn("PAPER_4","9"));	
					pcolumns4.add(getColumn("FOREIGN_LANG_F","10"));	
					pcolumns4.add(getColumn("PROJECT_13","11"));	
					pcolumns4.add(getColumn("PROJECT_1","12"));	
					pcolumns4.add(getColumn("PROJECT_2","13"));
					pcolumns4.add(getColumn("PROJECT_3","14"));
					pcolumns4.add(getColumn("PROJECT_4","15"));
					pcolumns4.add(getColumn("PROJECT_5","16"));
					pcolumns4.add(getColumn("PROJECT_6","17"));
					pcolumns4.add(getColumn("PROJECT_7","18"));
					pcolumns4.add(getColumn("PROJECT_8","19"));
					pcolumns4.add(getColumn("PROJECT_9","20"));
					
					pcolumns4.add(getColumn("PROJECT_10","21"));
					pcolumns4.add(getColumn("PROJECT_11","22"));
					pcolumns4.add(getColumn("PROJECT_12","23"));
					pcolumns4.add(getColumn("AWARD_1","24"));
					pcolumns4.add(getColumn("AWARD_2","25"));
					pcolumns4.add(getColumn("AWARD_3","26"));
					pcolumns4.add(getColumn("AWARD_4","27"));
					pcolumns4.add(getColumn("BOOK_1","28"));
					pcolumns4.add(getColumn("BOOK_2","29"));
					pcolumns4.add(getColumn("BOOK_3","30"));
					pcolumns4.add(getColumn("BOOK_4","31"));	
					pcolumns4.add(getColumn("PATENT_1","32"));	
					pcolumns4.add(getColumn("PATENT_2","33"));	
					pcolumns4.add(getColumn("PATENT_3","34"));	
					pcolumns4.add(getColumn("PATENT_4","35"));	
					pcolumns4.add(getColumn("PATENT_5","36"));	
					pcolumns4.add(getColumn("PATENT_6","37"));	
					/*pcolumns4.add(getColumn("COLLEGEREVIEW","38"));	
					pcolumns4.add(getColumn("RESULT","39"));	
					pcolumns4.add(getColumn("REMARK","40"));*/					
					return pcolumns4;
				}
	
		
				//高教管理，中初级
				public static List<Map<String,Object>> getColumnPosMapPrimary5(){
					if(pcolumns5 ==null){
						pcolumns5 = new ArrayList<Map<String,Object>>();
					}else{
						return pcolumns5;
					}
					pcolumns5.add(getColumn("ROWINDEX","0"));
					pcolumns5.add(getColumn("DEPT_NAME","1"));	
					pcolumns5.add(getColumn("NAME","2"));	
					pcolumns5.add(getColumn("A_TECHNIC_NAME","3"));	
					pcolumns5.add(getColumn("EXAM_YEAR3","4"));	
					pcolumns5.add(getColumn("EDUCATION_NAME","5"));	
					pcolumns5.add(getColumn("TECHNIC_NAME","6"));	
					pcolumns5.add(getColumn("PAPER_4","7"));	
					pcolumns5.add(getColumn("FILE_1","8"));	
					pcolumns5.add(getColumn("FOREIGN_LANG_F","9"));	
					pcolumns5.add(getColumn("PROJECT_13","10"));
					pcolumns5.add(getColumn("PROJECT_1","11"));	
					pcolumns5.add(getColumn("PROJECT_2","12"));
					pcolumns5.add(getColumn("PROJECT_3","13"));
					pcolumns5.add(getColumn("PROJECT_4","14"));
					pcolumns5.add(getColumn("PROJECT_5","15"));
					pcolumns5.add(getColumn("PROJECT_6","16"));
					pcolumns5.add(getColumn("PROJECT_7","17"));
					pcolumns5.add(getColumn("PROJECT_8","18"));
					pcolumns5.add(getColumn("PROJECT_9","19"));
					pcolumns5.add(getColumn("PROJECT_10","20"));
					
					pcolumns5.add(getColumn("PROJECT_11","21"));
					pcolumns5.add(getColumn("PROJECT_12","22"));
					pcolumns5.add(getColumn("AWARD_1","23"));
					pcolumns5.add(getColumn("AWARD_2","24"));
					pcolumns5.add(getColumn("AWARD_3","25"));
					pcolumns5.add(getColumn("AWARD_4","26"));
					pcolumns5.add(getColumn("BOOK_1","27"));
					pcolumns5.add(getColumn("BOOK_2","28"));
					pcolumns5.add(getColumn("BOOK_3","29"));
					pcolumns5.add(getColumn("BOOK_4","30"));
					/*pcolumns5.add(getColumn("RESULT","31"));	
					pcolumns5.add(getColumn("REMARK","32"));	*/
					return pcolumns5;
				}
				//学生思教系列 中初级
				public static List<Map<String,Object>> getColumnPosMapPrimary6(){
					if(pcolumns6 ==null){
						pcolumns6 = new ArrayList<Map<String,Object>>();
					}else{
						return pcolumns6;
					}
					pcolumns6.add(getColumn("ROWINDEX","0"));
					
					pcolumns6.add(getColumn("DEPT_NAME","1"));
					
					pcolumns6.add(getColumn("NAME","2"));	
					pcolumns6.add(getColumn("A_TECHNIC_NAME","3"));	
					pcolumns6.add(getColumn("EXAM_YEAR3","4"));	
					pcolumns6.add(getColumn("EDUCATION_NAME","5"));	
					pcolumns6.add(getColumn("TECHNIC_NAME","6"));	
					pcolumns6.add(getColumn("TECH8","7"));	
					pcolumns6.add(getColumn("TECH7","8"));	
					pcolumns6.add(getColumn("PAPER_4","9"));	
					pcolumns6.add(getColumn("FILE_1","10"));
					pcolumns6.add(getColumn("FOREIGN_LANG_F","11"));	
					pcolumns6.add(getColumn("PROJECT_13","12"));
					pcolumns6.add(getColumn("PROJECT_1","13"));
					pcolumns6.add(getColumn("PROJECT_2","14"));
					pcolumns6.add(getColumn("PROJECT_3","15"));
					pcolumns6.add(getColumn("PROJECT_4","16"));
					pcolumns6.add(getColumn("PROJECT_5","17"));
					pcolumns6.add(getColumn("PROJECT_6","18"));
					pcolumns6.add(getColumn("PROJECT_7","19"));
					pcolumns6.add(getColumn("PROJECT_8","20"));
					
					pcolumns6.add(getColumn("PROJECT_9","21"));
					pcolumns6.add(getColumn("PROJECT_10","22"));
					pcolumns6.add(getColumn("PROJECT_11","23"));
					pcolumns6.add(getColumn("PROJECT_12","24"));
					pcolumns6.add(getColumn("AWARD_1","25"));
					pcolumns6.add(getColumn("AWARD_2","26"));
					
					pcolumns6.add(getColumn("AWARD_3","27"));
					
					pcolumns6.add(getColumn("AWARD_4","28"));
					
					pcolumns6.add(getColumn("AWARD_5","29"));
					
					pcolumns6.add(getColumn("BOOK_1","30"));
					
					pcolumns6.add(getColumn("BOOK_2","31"));
					
					pcolumns6.add(getColumn("BOOK_3","32"));
					
					pcolumns6.add(getColumn("BOOK_4","33"));
					
					/*pcolumns6.add(getColumn("","34"));
					
					pcolumns6.add(getColumn("REMARK","35"));*/
					return pcolumns6;
				}	
			//实验系列 正副高
		public static List<Map<String,Object>> getColumnPosMap4(){
			if(columns3 ==null){
				columns3 = new ArrayList<Map<String,Object>>();
			}else{
				return columns3;
			}
			columns3.add(getColumn("ROWINDEX","0"));
			columns3.add(getColumn("DEPT_NAME","1"));	
			columns3.add(getColumn("NAME","2"));	
			columns3.add(getColumn("A_TECHNIC_NAME","3"));	
			columns3.add(getColumn("EXAM_YEAR3","4"));	
			columns3.add(getColumn("EDUCATION_NAME","5"));	
			columns3.add(getColumn("TECHNIC_NAME","6"));	
			columns3.add(getColumn("TECH70","7"));	
			columns3.add(getColumn("TECH71","8"));	
			columns3.add(getColumn("PAPER_4","9"));	
			columns3.add(getColumn("PROJECT_1","10"));
			columns3.add(getColumn("PROJECT_2","11"));	
			columns3.add(getColumn("PROJECT_3","12"));
			columns3.add(getColumn("PROJECT_4","13"));
			columns3.add(getColumn("PROJECT_5","14"));
			columns3.add(getColumn("PROJECT_6","15"));
			columns3.add(getColumn("PROJECT_7","16"));
			columns3.add(getColumn("PROJECT_8","17"));
			columns3.add(getColumn("PROJECT_9","18"));
			columns3.add(getColumn("PROJECT_10","19"));
			columns3.add(getColumn("PROJECT_11","20"));
			
			columns3.add(getColumn("PROJECT_12","21"));
			columns3.add(getColumn("FOREIGN_LANG_F","22"));
			columns3.add(getColumn("AWARD_1","23"));
			columns3.add(getColumn("AWARD_2","24"));
			columns3.add(getColumn("AWARD_3","25"));
			columns3.add(getColumn("AWARD_4","26"));
			columns3.add(getColumn("PAPER_4","27"));
			columns3.add(getColumn("PAPER_3","28"));
			columns3.add(getColumn("BOOK_1","29"));
			columns3.add(getColumn("BOOK_2","30"));
			columns3.add(getColumn("BOOK_3","31"));	
			columns3.add(getColumn("BOOK_4","32"));	
			columns3.add(getColumn("PROJECT_13","33"));	
			columns3.add(getColumn("PROJECT_1","34"));	
			columns3.add(getColumn("PROJECT_2","35"));	
			columns3.add(getColumn("PROJECT_3","36"));	
			columns3.add(getColumn("PROJECT_4","37"));	
			columns3.add(getColumn("PROJECT_5","38"));	
			columns3.add(getColumn("PROJECT_6","39"));	
			columns3.add(getColumn("PROJECT_7","40"));
			
			columns3.add(getColumn("PROJECT_8","41"));	
			columns3.add(getColumn("PROJECT_9","42"));	
			columns3.add(getColumn("PROJECT_10","43"));
			columns3.add(getColumn("PROJECT_11","44"));
			columns3.add(getColumn("PROJECT_12","45"));
			columns3.add(getColumn("PATENT_1","46"));
			columns3.add(getColumn("PATENT_2","47"));
			columns3.add(getColumn("PATENT_3","48"));
			columns3.add(getColumn("PATENT_4","49"));
			columns3.add(getColumn("PATENT_5","50"));
			
			columns3.add(getColumn("PATENT_6","51"));
			/*columns3.add(getColumn("INVEST","52"));
			columns3.add(getColumn("RESULT","53"));
			columns3.add(getColumn("COLLEGEREVIEW","54"));
			columns3.add(getColumn("","55"));

			columns3.add(getColumn("REMARK","56"));*/
			
			return columns3;
		}
		//高教管理 正副高
		public static List<Map<String,Object>> getColumnPosMap5(){
			if(columns4 ==null){
				columns4 = new ArrayList<Map<String,Object>>();
			}else{
				return columns4;
			}
			columns4.add(getColumn("ROWINDEX","0"));
			columns4.add(getColumn("DEPT_NAME","1"));	
			columns4.add(getColumn("NAME","2"));	
			columns4.add(getColumn("A_TECHNIC_NAME","3"));	
			columns4.add(getColumn("EXAM_YEAR3","4"));	
			columns4.add(getColumn("EDUCATION_NAME","5"));	
			columns4.add(getColumn("TECHNIC_NAME","6"));	
			columns4.add(getColumn("PAPER_1","7"));	
			columns4.add(getColumn("FILE_1","8"));	
			columns4.add(getColumn("PROJECT_1","9"));	
			columns4.add(getColumn("PROJECT_2","10"));
			columns4.add(getColumn("PROJECT_3","11"));	
			columns4.add(getColumn("PROJECT_4","12"));
			columns4.add(getColumn("PROJECT_5","13"));
			columns4.add(getColumn("PROJECT_6","14"));
			columns4.add(getColumn("PROJECT_7","15"));
			columns4.add(getColumn("PROJECT_8","16"));
			columns4.add(getColumn("PROJECT_9","17"));
			columns4.add(getColumn("PROJECT_10","18"));
			columns4.add(getColumn("PROJECT_11","19"));
			columns4.add(getColumn("PROJECT_12","20"));

			columns4.add(getColumn("FOREIGN_LANG_F","21"));
			columns4.add(getColumn("AWARD_1","22"));
			columns4.add(getColumn("AWARD_2","23"));
			columns4.add(getColumn("AWARD_3","24"));
			columns4.add(getColumn("AWARD_4","25"));
			columns4.add(getColumn("PAPER_1","26"));
			columns4.add(getColumn("PAPER_3","27"));
			columns4.add(getColumn("BOOK_1","28"));
			columns4.add(getColumn("BOOK_2","29"));
			columns4.add(getColumn("BOOK_3","30"));
			columns4.add(getColumn("BOOK_4","31"));	
			columns4.add(getColumn("PROJECT_13","32"));	
			columns4.add(getColumn("PROJECT_1","33"));	
			columns4.add(getColumn("PROJECT_2","34"));	
			columns4.add(getColumn("PROJECT_3","35"));	
			columns4.add(getColumn("PROJECT_4","36"));	
			columns4.add(getColumn("PROJECT_5","37"));	
			columns4.add(getColumn("PROJECT_6","38"));	
			columns4.add(getColumn("PROJECT_7","39"));	
			columns4.add(getColumn("PROJECT_8","40"));
			
			columns4.add(getColumn("PROJECT_9","41"));	
			columns4.add(getColumn("PROJECT_10","42"));	
			columns4.add(getColumn("PROJECT_11","43"));
			columns4.add(getColumn("PROJECT_12","44"));
			/*columns4.add(getColumn("INVEST","45"));
			columns4.add(getColumn("RESULT","46"));
			columns4.add(getColumn("REMARK","47"));*/
			return columns4;
		}
		//学生思教 正副高
		public static List<Map<String,Object>> getColumnPosMap6(){
			if(columns6 ==null){
			    columns6 = new ArrayList<Map<String,Object>>();
			}else{
				return columns6;
			}
			columns6.add(getColumn("ROWINDEX","0"));
			columns6.add(getColumn("DEPT_NAME","1"));	
			columns6.add(getColumn("NAME","2"));	
			columns6.add(getColumn("A_TECHNIC_NAME","3"));	
			columns6.add(getColumn("TECH_TEAM","4"));	
			columns6.add(getColumn("EXAM_YEAR3","5"));	
			columns6.add(getColumn("EDUCATION_NAME","6"));	
			columns6.add(getColumn("TECHNIC_NAME","7"));	
			columns6.add(getColumn("TECH1","8"));	
			columns6.add(getColumn("TECH2","9"));	
			columns6.add(getColumn("TECH3","10"));
			columns6.add(getColumn("TECH4","11"));	
			columns6.add(getColumn("TECH5","12"));
			columns6.add(getColumn("TECH6","13"));
			columns6.add(getColumn("TECH7","14"));
			columns6.add(getColumn("HEAD_TEACHRE","15"));
			columns6.add(getColumn("PAPER_1","16"));
			columns6.add(getColumn("PAPER_2","17"));
			columns6.add(getColumn("PROJECT_1","18"));
			columns6.add(getColumn("PROJECT_2","19"));
			columns6.add(getColumn("PROJECT_3","20"));
			
			columns6.add(getColumn("PROJECT_4","21"));
			columns6.add(getColumn("PROJECT_5","22"));
			columns6.add(getColumn("PROJECT_6","23"));
			columns6.add(getColumn("PROJECT_7","24"));
			columns6.add(getColumn("PROJECT_8","25"));
			columns6.add(getColumn("PROJECT_9","26"));
			columns6.add(getColumn("PROJECT_10","27"));
			columns6.add(getColumn("PROJECT_11","28"));
			columns6.add(getColumn("PROJECT_12","29"));
			columns6.add(getColumn("FOREIGN_LANG_F","30"));
			columns6.add(getColumn("AYEAR_STDWORK","31"));	
			columns6.add(getColumn("AWARD_1","32"));	
			columns6.add(getColumn("AWARD_2","33"));	
			columns6.add(getColumn("AWARD_3","34"));	
			columns6.add(getColumn("AWARD_4","35"));	
			columns6.add(getColumn("AWARD_5","36"));	
			columns6.add(getColumn("PAPER_1","37"));	
			columns6.add(getColumn("PAPER_2","38"));	
			columns6.add(getColumn("PAPER_3","39"));	
			columns6.add(getColumn("BOOK_1","40"));
			
			columns6.add(getColumn("BOOK_2","41"));	
			columns6.add(getColumn("BOOK_3","42"));	
			columns6.add(getColumn("BOOK_4","43"));
			columns6.add(getColumn("PROJECT_13","44"));
			columns6.add(getColumn("PROJECT_1","45"));
			columns6.add(getColumn("PROJECT_2","46"));
			columns6.add(getColumn("PROJECT_3","47"));
			columns6.add(getColumn("PROJECT_4","48"));
			columns6.add(getColumn("PROJECT_5","49"));
			columns6.add(getColumn("PROJECT_6","50"));
			columns6.add(getColumn("PROJECT_7","51"));
			columns6.add(getColumn("PROJECT_8","52"));
			columns6.add(getColumn("PROJECT_9","53"));
			columns6.add(getColumn("PROJECT_10","54"));
			columns6.add(getColumn("PROJECT_11","55"));
			columns6.add(getColumn("PROJECT_12","56"));
			columns6.add(getColumn("QUALITY_1","57"));
			columns6.add(getColumn("FIVEYEAR_STDWORK","58"));
			columns6.add(getColumn("STU_SOCIAL_PRACTICE","59"));
/*			columns6.add(getColumn("INVEST","60"));
			
			columns6.add(getColumn("RESULT","61"));
			columns6.add(getColumn("REMARK","62"));*/
			return columns6;
		}
		//博士后
		public static List<Map<String,Object>> getColumnPosMap7(){
			if(columns7 ==null){
				columns7 = new ArrayList<Map<String,Object>>();
			}else{
				return columns7;
			}
			columns7.add(getColumn("ROWINDEX","0"));
			columns7.add(getColumn("DEPT_NAME","1"));	
			columns7.add(getColumn("NAME","2"));	
			columns7.add(getColumn("A_TECHNIC_NAME","3"));	
			columns7.add(getColumn("PD_JOIN_DATE","4"));	
			columns7.add(getColumn("EDUCATION_NAME","5"));	
			columns7.add(getColumn("TECHNIC_NAME","6"));	
			columns7.add(getColumn("PAPER_1","7"));	
			columns7.add(getColumn("PROJECT_1","8"));	
			columns7.add(getColumn("PROJECT_2","9"));	
			columns7.add(getColumn("PROJECT_3","10"));
			columns7.add(getColumn("PROJECT_4","11"));	
			columns7.add(getColumn("PROJECT_5","12"));
			columns7.add(getColumn("PROJECT_6","13"));
			columns7.add(getColumn("PROJECT_7","14"));
			columns7.add(getColumn("PROJECT_8","15"));
			columns7.add(getColumn("PROJECT_9","16"));
			columns7.add(getColumn("PROJECT_10","17"));
			columns7.add(getColumn("PROJECT_11","18"));
			columns7.add(getColumn("PROJECT_12","19"));
			columns7.add(getColumn("AWARD_1","20"));
			
			columns7.add(getColumn("AWARD_2","21"));
			columns7.add(getColumn("AWARD_3","22"));
			columns7.add(getColumn("AWARD_4","23"));
			columns7.add(getColumn("PAPER_1","24"));
			columns7.add(getColumn("PAPER_3","25"));
			columns7.add(getColumn("BOOK_1","26"));
			columns7.add(getColumn("BOOK_2","27"));
			columns7.add(getColumn("BOOK_3","28"));
			columns7.add(getColumn("BOOK_4","29"));
			columns7.add(getColumn("PROJECT_13","30"));
			columns7.add(getColumn("PROJECT_1","31"));	
			columns7.add(getColumn("PROJECT_2","32"));	
			columns7.add(getColumn("PROJECT_3","33"));	
			columns7.add(getColumn("PROJECT_4","34"));	
			columns7.add(getColumn("PROJECT_5","35"));	
			columns7.add(getColumn("PROJECT_6","36"));	
			columns7.add(getColumn("PROJECT_7","37"));	
			columns7.add(getColumn("PROJECT_8","38"));	
			columns7.add(getColumn("PROJECT_9","39"));	
			columns7.add(getColumn("PROJECT_10","40"));
			
			columns7.add(getColumn("PROJECT_11","41"));	
			columns7.add(getColumn("PROJECT_12","42"));	
			columns7.add(getColumn("PATENT_1","43"));
			columns7.add(getColumn("PATENT_2","44"));
			columns7.add(getColumn("PATENT_3","45"));
			columns7.add(getColumn("PATENT_4","46"));
			columns7.add(getColumn("PATENT_5","47"));
			columns7.add(getColumn("PATENT_6","48"));
	/*		columns7.add(getColumn("INVEST","49"));
			columns7.add(getColumn("COLLEGEREVIEW","50"));
			columns7.add(getColumn("REMARK","51"));*/
			return columns7;
		}
		//图书资料系列中初级
		public static List<Map<String,Object>> getColumnPosMap8(){
			if(columns8 ==null){
				columns8 = new ArrayList<Map<String,Object>>();
			}else{
				return columns8;
			}
			columns8.add(getColumn("ROWINDEX","0"));
			columns8.add(getColumn("DEPT_NAME","1"));	
			columns8.add(getColumn("NAME","2"));	
			columns8.add(getColumn("A_TECHNIC_NAME","3"));	
			columns8.add(getColumn("EXAM_YEAR3","4"));	
			columns8.add(getColumn("EDUCATION_NAME","5"));	
			columns8.add(getColumn("TECHNIC_NAME","6"));	
			columns8.add(getColumn("FOREIGN_LANG_F","7"));	
			columns8.add(getColumn("CONEDU","8"));	
			columns8.add(getColumn("COMPUTEREXAM","9"));	
			columns8.add(getColumn("LIBPROJECT","10"));
			columns8.add(getColumn("FILE_1","11"));	
			columns8.add(getColumn("PAPER_4","12"));
			columns8.add(getColumn("","13"));
			columns8.add(getColumn("LIBAWARD","14"));
			/*columns8.add(getColumn("COLLEGEREVIEW","15"));
			columns8.add(getColumn("REMARK","16"));*/
			return columns8;
	
		}
		
		//新增 系列 中初级
		public static List<Map<String,Object>> getColumnPosMapTechOther(){
			if(techColumns ==null){
				techColumns = new ArrayList<Map<String,Object>>();
			}else{
				return techColumns;
			}
			techColumns.add(getColumn("ROWINDEX","0"));
			techColumns.add(getColumn("DEPT_NAME","1"));	
			techColumns.add(getColumn("NAME","2"));	
			techColumns.add(getColumn("A_TECHNIC_NAME","3"));	
			techColumns.add(getColumn("EXAM_YEAR3","4"));	
			techColumns.add(getColumn("EDUCATION_NAME","5"));	
			techColumns.add(getColumn("TECHNIC_NAME","6"));	
			techColumns.add(getColumn("LIBPROJECT","7"));
			techColumns.add(getColumn("FILE_1","8"));	
			techColumns.add(getColumn("PAPER_4","9"));
			techColumns.add(getColumn("","10"));
			techColumns.add(getColumn("LIBAWARD","11"));
			return techColumns;
	
		}
}
