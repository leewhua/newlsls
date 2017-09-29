package test2;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;

public class ExportExcel {
	
	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;
	private HSSFCellStyle style = null;

	public ExportExcel(HSSFWorkbook wb, HSSFSheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public HSSFCellStyle getStyle() {
		return style;
	}

	public void setStyle(HSSFCellStyle style) {
		this.style = style;
	}
	
	
	public void createNormalHead(String headString, int colSum){
		HSSFRow row = sheet.createRow(0);
		// 设置第一行   
		HSSFCell cell = row.createCell(0); 
		row.setHeight((short) 400); 
		
		// 定义单元格为字符串类型   
		cell.setCellType(HSSFCell.ENCODING_UTF_16);   
		cell.setCellValue(new HSSFRichTextString("南京城区各网点进件统计报表")); 		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,(short)colSum));		
		HSSFCellStyle cellStyle = wb.createCellStyle(); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐   
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐   
		cellStyle.setWrapText(true);// 指定单元格自动换行 
		// 设置单元格字体   
		HSSFFont font = wb.createFont();   
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
		font.setFontName("宋体");   
		font.setFontHeight((short) 300);   
		cellStyle.setFont(font);   		  
		cell.setCellStyle(cellStyle);
	}
	
}
