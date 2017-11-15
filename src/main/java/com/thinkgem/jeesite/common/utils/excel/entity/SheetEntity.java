package com.thinkgem.jeesite.common.utils.excel.entity;

import java.util.List;
import java.util.Map;

public class SheetEntity {
	private int sheetId;
	private String sheetName;
	private String title;
	private String[] colName;
	private List<Map<String, Object>> data;
	public int getSheetId() {
		return sheetId;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getColName() {
		return colName;
	}
	public void setColName(String[] colName) {
		this.colName = colName;
	}
	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}
	public String getSheetName() {
		return sheetName;
	}
	public String getTitle() {
		return title;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}
