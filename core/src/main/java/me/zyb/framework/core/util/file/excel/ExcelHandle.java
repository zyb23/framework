package me.zyb.framework.core.util.file.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author zhangyingbin
 */
@Slf4j
public class ExcelHandle {

	/**
	 * 获取单元格的值<br>
	 * 注：HSSFWorkbook是解析excel2007之前的版本（xls）;之后版本用户XSSFWorkbook(xlsx)
	 * @author zhangyingbin
	 * @param filePath      文件路径
	 * @param sheetIndex    工作表
	 * @param rowIndex      行
	 * @param cellIndex     列
	 * @return Object
	 */
	public static Object getCellValue(String filePath, int sheetIndex, int rowIndex, int cellIndex) throws Exception{
		log.debug("get cell value...");
		Object cellValue = null;
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(cellIndex);
		CellType cellType = cell.getCellType();
		switch(cellType){
			case NUMERIC: {
				cellValue = cell.getNumericCellValue();
				break;
			}
			case STRING: {
				cellValue = cell.getStringCellValue();
				break;
			}
			case FORMULA: {
				cellValue = cell.getCellFormula();
				break;
			}
			case BLANK:{
				cellValue = cell.getStringCellValue();
				break;
			}
			case BOOLEAN:{
				cellValue = cell.getBooleanCellValue();
				break;
			}
			case ERROR:{
				cellValue = cell.getErrorCellValue();
				break;
			}
			default:{
				cellValue = cell.getStringCellValue();
				break;
			}
		}
		
		return cellValue;
	}
	
	/**
	 * 设置单元格的值
	 * @author zhangyingbin
	 *
	 * @param row           行
	 * @param cellIndex     列
	 * @param cellType      类型
	 * @param cellValue     值
	 */
	public static void setCellValue(HSSFRow row, int cellIndex, CellType cellType, Object cellValue){
		HSSFCell cell = row.createCell(cellIndex, cellType);
		switch(cellType){
			case NUMERIC: {
				cell.setCellValue((Double)cellValue);
				break;
			}
			case STRING: {
				cell.setCellValue((String)cellValue);
				break;
			}
			case FORMULA: {
				cell.setCellValue((Double)cellValue);
				break;
			}
			case BLANK: {
				cell.setCellValue(cellValue.toString());
				break;
			}
			case BOOLEAN: {
				cell.setCellValue((Boolean)cellValue);
				break;
			}
			case ERROR: {
				cell.setCellErrorValue((Byte)cellValue);
				break;
			}
			default:{

			}
		}
	}
	
	/**
	 * 写Excel
	 * @author zhangyingbin
	 *
	 * @param filePath  文件路径
	 * @param wb        表格
	 */
	public static void writeExcel(String filePath, HSSFWorkbook wb) throws Exception{
		FileOutputStream os = new FileOutputStream(filePath);
		wb.write(os);
		os.close();
	}
}
