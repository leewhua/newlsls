package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelOperation {
	public static void main(String[] args) throws IOException {
		for(int i=1;i<13;i++){
			String filename="KZR20170317B_"+i;
			readexcel(filename);
		}
		
	}
		
	public static void readexcel(String filename) throws IOException{
		String fileDir="D:\\迅雷下载\\康之味导出数据0319\\B\\K0317\\"+filename+".xls";
		HSSFWorkbook workbook = null;
		File file = new File(fileDir); 
		try {
			 workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		List result = new ArrayList(); 
		 HSSFSheet sheet = workbook.getSheet("数据表");  
		 // 获取表格的总行数  
         int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
         System.out.println("rowCount:"+rowCount);  

         // 获取表头的列数  
         int columnCount = sheet.getRow(0).getLastCellNum();  
         // 获得表头行对象  
         HSSFRow titleRow = sheet.getRow(0); 
         System.out.println(columnCount);
         // 逐行读取数据 从1开始 忽略表头  
         BufferedWriter writer = Files.newBufferedWriter(Paths.get("D:\\康之味\\码\\"+filename), StandardCharsets.UTF_8);
         for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {  
             // 获得行对象  
             HSSFRow row = sheet.getRow(rowIndex); 
             for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) { 
                 String data = row.getCell(columnIndex).toString(); 
                 if(columnIndex==1){
                	 int dex=data.lastIndexOf("/");
                	 data=data.substring(dex+1,data.length()); 
                 }
                 writer.write(data+",");
             }
             writer.write("\r\n");
         }
       writer.flush();
       writer.close();
	}

}
