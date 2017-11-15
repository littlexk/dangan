package com.thinkgem.jeesite.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

@SuppressWarnings("deprecation")
public class Utils {
	
	private static HSSFCellStyle cellStyle = null;
	public static void copyRows(HSSFWorkbook wb, String pSourceSheetName,
			  String pTargetSheetName, int pStartRow, int pEndRow, int pPosition){
			  HSSFRow sourceRow = null;
			  HSSFRow targetRow = null;
			  HSSFCell sourceCell = null;
			  HSSFCell targetCell = null;
			  HSSFSheet sourceSheet = null;
			  HSSFSheet targetSheet = null;
			  Region region = null;
			  int cType;
			  int i;
			  short j;
			  int targetRowFrom;
			  int targetRowTo;

			  if ((pStartRow == -1) || (pEndRow == -1)){
			   return;
			  }
			  sourceSheet = wb.getSheet(pSourceSheetName);
			  targetSheet = wb.getSheet(pTargetSheetName);
			  // 拷贝合并的单元格
			  for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			   region = sourceSheet.getMergedRegionAt(i);
			   if ((region.getRowFrom() >= pStartRow)
			     && (region.getRowTo() <= pEndRow)) {
			    targetRowFrom = region.getRowFrom() - pStartRow + pPosition;
			    targetRowTo = region.getRowTo() - pStartRow + pPosition;
			    region.setRowFrom(targetRowFrom);
			    region.setRowTo(targetRowTo);
			    targetSheet.addMergedRegion(region);
			   }
			  }
			  // 设置列宽
			  for (i = pStartRow; i <= pEndRow; i++) {
			   sourceRow = sourceSheet.getRow(i);
			   if (sourceRow != null) {
			    for (j = sourceRow.getLastCellNum(); j > sourceRow
			      .getFirstCellNum(); j--) {
			     targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
			     //targetSheet.setColumnHidden(j, false);
			    }
			    break;
			   }
			  }
			  // 拷贝行并填充数据
			  for (; i <= pEndRow; i++) {
			   sourceRow = sourceSheet.getRow(i);
			   if (sourceRow == null) {
			    continue;
			   }
			   targetRow = targetSheet.createRow(i - pStartRow + pPosition);
			   targetRow.setHeight(sourceRow.getHeight());
			   for (j = sourceRow.getFirstCellNum(); j < sourceRow
			     .getPhysicalNumberOfCells(); j++) {
			    sourceCell = sourceRow.getCell(j);
			    if (sourceCell == null) {
			     continue;
			    }
			    targetCell = targetRow.createCell(j);
			    //targetCell.setEncoding(sourceCell.getEncoding());
			    targetCell.setCellStyle(sourceCell.getCellStyle());
			    cType = sourceCell.getCellType();
			    targetCell.setCellType(cType);
			    switch (cType) {
			    case HSSFCell.CELL_TYPE_BOOLEAN:
			     targetCell.setCellValue(sourceCell.getBooleanCellValue());
			     System.out.println("--------TYPE_BOOLEAN:"
			       + targetCell.getBooleanCellValue());
			     break;
			    case HSSFCell.CELL_TYPE_ERROR:
			     targetCell
			       .setCellErrorValue(sourceCell.getErrorCellValue());
			     System.out.println("--------TYPE_ERROR:"
			       + targetCell.getErrorCellValue());
			     break;
			    case HSSFCell.CELL_TYPE_FORMULA:
			     // parseFormula这个函数的用途在后面说明
			     targetCell.setCellFormula(parseFormula(sourceCell
			       .getCellFormula()));
			     System.out.println("--------TYPE_FORMULA:"
			       + targetCell.getCellFormula());
			     break;
			    case HSSFCell.CELL_TYPE_NUMERIC:
			     targetCell.setCellValue(sourceCell.getNumericCellValue());
			     System.out.println("--------TYPE_NUMERIC:"
			       + targetCell.getNumericCellValue());
			     break;
			    case HSSFCell.CELL_TYPE_STRING:
			     //targetCell.setCellValue(sourceCell.getRichStringCellValue());
			      targetCell.setCellValue(sourceCell.getStringCellValue());
			     //System.out.println("--------TYPE_STRING:" + i+ targetCell.getRichStringCellValue());
			     break;
			    }

			   }

			  }

			 }

			 private static String parseFormula(String pPOIFormula) {
			  final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
			  StringBuffer result = null;
			  int index;

			  result = new StringBuffer();
			  index = pPOIFormula.indexOf(cstReplaceString);
			  if (index >= 0) {
			   result.append(pPOIFormula.substring(0, index));
			   result.append(pPOIFormula.substring(index
			     + cstReplaceString.length()));
			  } else {
			   result.append(pPOIFormula);
			  }

			  return result.toString();
			 }
			 
			 public static void copyRows(HSSFWorkbook wb, HSSFSheet sourceSheet, int pStartRow, int pEndRow, int pPosition){
					  HSSFRow sourceRow = null;
					  HSSFRow targetRow = null;
					  HSSFCell sourceCell = null;
					  HSSFCell targetCell = null;
					  Region region = null;
					  HSSFSheet targetSheet = sourceSheet;
					  int cType;
					  int i;
					  short j;
					  int targetRowFrom;
					  int targetRowTo;

					  if ((pStartRow == -1) || (pEndRow == -1)){
					   return;
					  }
					  // 拷贝合并的单元格
					  for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
					   region = sourceSheet.getMergedRegionAt(i);
					   if ((region.getRowFrom() >= pStartRow)
					     && (region.getRowTo() <= pEndRow)) {
					    targetRowFrom = region.getRowFrom() - pStartRow + pPosition;
					    targetRowTo = region.getRowTo() - pStartRow + pPosition;
					    region.setRowFrom(targetRowFrom);
					    region.setRowTo(targetRowTo);
					    targetSheet.addMergedRegion(region);
					   }
					  }
					  // 设置列宽
					  for (i = pStartRow; i <= pEndRow; i++) {
					   sourceRow = sourceSheet.getRow(i);
					   if (sourceRow != null) {
					    for (j = sourceRow.getLastCellNum(); j > sourceRow
					      .getFirstCellNum(); j--) {
					     targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
					     //targetSheet.setColumnHidden(j, false);
					    }
					    break;
					   }
					  }
					  
					  // 拷贝行并填充数据
					  for (; i <= pEndRow; i++) {
					   sourceRow = sourceSheet.getRow(i);
					   if (sourceRow == null) {
					    continue;
					   }
					   targetRow = targetSheet.getRow(i - pStartRow + pPosition);
					   if(targetRow == null)targetRow = targetSheet.createRow(i - pStartRow + pPosition);
					   targetRow.setHeight(sourceRow.getHeight());
					   for (j = sourceRow.getFirstCellNum(); j < sourceRow
					     .getPhysicalNumberOfCells(); j++) {
					    sourceCell = sourceRow.getCell(j);
					    if (sourceCell == null) {
					     continue;
					    }
					    targetCell = targetRow.getCell(j);
					    if(targetCell == null)targetCell = targetRow.createCell(j);
					    //targetCell.setEncoding(sourceCell.getEncoding());
					    targetCell.setCellStyle(sourceCell.getCellStyle());
					    cType = sourceCell.getCellType();
					    targetCell.setCellType(cType);
					    /**
					    switch (cType) {
					    case HSSFCell.CELL_TYPE_BOOLEAN:
					     targetCell.setCellValue(sourceCell.getBooleanCellValue());
					     System.out.println("--------TYPE_BOOLEAN:"
					       + targetCell.getBooleanCellValue());
					     break;
					    case HSSFCell.CELL_TYPE_ERROR:
					     targetCell
					       .setCellErrorValue(sourceCell.getErrorCellValue());
					     System.out.println("--------TYPE_ERROR:"
					       + targetCell.getErrorCellValue());
					     break;
					    case HSSFCell.CELL_TYPE_FORMULA:
					     // parseFormula这个函数的用途在后面说明
					     targetCell.setCellFormula(parseFormula(sourceCell
					       .getCellFormula()));
					     System.out.println("--------TYPE_FORMULA:"
					       + targetCell.getCellFormula());
					     break;
					    case HSSFCell.CELL_TYPE_NUMERIC:
					     targetCell.setCellValue(sourceCell.getNumericCellValue());
					     System.out.println("--------TYPE_NUMERIC:"
					       + targetCell.getNumericCellValue());
					     break;
					    case HSSFCell.CELL_TYPE_STRING:
					     //targetCell.setCellValue(sourceCell.getRichStringCellValue());
					      targetCell.setCellValue(sourceCell.getStringCellValue());
					     //System.out.println("--------TYPE_STRING:" + i+ targetCell.getRichStringCellValue());
					     break;
					    }*/

					   }

					  }

					 }
			 
			 public static HSSFCellStyle copyCellStyle(HSSFCellStyle cStyle, HSSFCellStyle sourceCellStyle) {
					if(sourceCellStyle == null || cStyle == null){
						return cStyle;
					}
					//是否换行
					cStyle.setWrapText(sourceCellStyle.getWrapText());
					//字体拷贝
					//cStyle.setFont(sourceCellStyle.get);
//					 cStyle.cloneStyleFrom(sourceCellStyle);
					//拷贝对齐方式
					cStyle.setAlignment(sourceCellStyle.getAlignment());
					cStyle.setVerticalAlignment(sourceCellStyle.getVerticalAlignment());
					//边框拷贝
					cStyle.setBorderBottom(sourceCellStyle.getBorderBottom());
					cStyle.setBorderLeft(sourceCellStyle.getBorderLeft());
					cStyle.setBorderRight(sourceCellStyle.getBorderRight());
					cStyle.setBorderTop(sourceCellStyle.getBorderTop());
					cStyle.setBottomBorderColor(sourceCellStyle.getBottomBorderColor());
					cStyle.setLeftBorderColor(sourceCellStyle.getLeftBorderColor());
					cStyle.setRightBorderColor(sourceCellStyle.getRightBorderColor());
					cStyle.setTopBorderColor(sourceCellStyle.getTopBorderColor());
					//别的样式的拷贝
					return cStyle;
				}
			 
			 public static void removeRow(HSSFSheet sheet, int rowIndex) {  
				     int lastRowNum=sheet.getLastRowNum();  
				     if(rowIndex>=0&&rowIndex<lastRowNum)  
				         sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行  
				     if(rowIndex==lastRowNum){  
				         HSSFRow removingRow=sheet.getRow(rowIndex);  
				         if(removingRow!=null)  
				             sheet.removeRow(removingRow);  
				     }  
				 } 
			 
			 public static synchronized HSSFCellStyle getRedCellStyle(HSSFWorkbook wb){
				 if(cellStyle != null) return cellStyle;
				 else{
					 HSSFCellStyle borderStyle =wb.createCellStyle();
					 HSSFFont font = wb.createFont();
					 font.setColor(HSSFColor.RED.index);
					 //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
					 //font.setUnderline(HSSFFont.U_SINGLE); 
					 borderStyle.setFont(font);
					 cellStyle = borderStyle;
				 }
				 return cellStyle;
			 }


}

