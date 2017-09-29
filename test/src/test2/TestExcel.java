package test2;


  
import java.io.FileOutputStream;  
  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.util.CellRangeAddress;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

import com.lsid.mysql.util.SpringJdbc4mysql;
  
/** 
 *  
 * 测试生成Excel文档 
 *  
 * @author Administrator 
 *  
 */  
public class TestExcel {  
  
    /** 
     * Excel文档的构成 
     *  
     * 在工作簿(WorkBook)里面包含了工作表(Sheet) 在工作表里面包含了行(Row) 行里面包含了单元格(Cell) 
     *  
     *  
     * 创建一个工作簿的基本步骤 
     *  
     * 第一步 创建一个 工作簿 第二步 创建一个 工作表 第三步 创建一行 第四步 创建单元格 第五步 写数据 第六步 
     * 将内存中生成的workbook写到文件中 然后释放资源 
     *  
     */  
  
    public static void testCreateFirstExcel97() throws Exception {  
        Workbook wb = new HSSFWorkbook();  
        FileOutputStream fileOut = new FileOutputStream("C:/workbook.xls");  
        wb.write(fileOut);  
        fileOut.close();  
    }  
    public static void testCreateFirstExcel07() throws Exception {  
        Workbook wb = new XSSFWorkbook();  
        FileOutputStream fileOut = new FileOutputStream("C:/workbook.xlsx");  
        wb.write(fileOut);  
        fileOut.close();  
    }  
      
      
    public static void createExcelOfData() throws Exception{  
        Workbook wb = new HSSFWorkbook();  
          
        //创建工作表  
        Sheet sheet = wb.createSheet("测试Excel");  
          
        //创建单元格   单元格是隶属于行  
          
        Row row = sheet.createRow(0);   //起始从0开始  
          
        Cell cell = row.createCell(0);  
          
        cell.setCellValue("This is a test");  
        FileOutputStream fileOut = new FileOutputStream("D:/test.xls");  
        wb.write(fileOut);  
        fileOut.close();  
    }  
      
    public static void createExcelOfUsers() throws Exception{  
        Workbook wb = new HSSFWorkbook();  
          
        //创建工作表  
        Sheet sheet = wb.createSheet("纸券报表");  
                  
        Object [][] data = new Object[][]{  
                {'1',"2017/7/16","福运金秋","20","288","6","3456"},  
                {'1',"2017/7/17","福运金秋","20","288","6","3456"},
                {'2',"2017/7/18","福运金秋","20","288","6","3456"},
                {'2',"2017/7/19","福运金秋","20","288","6","3456"},
                {'3',"2017/7/21","福运金秋","20","288","6","3456"}
        }; 
                        
        String headers[] = new String[]{"月饼券类型","零售价","串码","识别码","经销商一级代码","经销商一级名称","经销商二级代码","付款/激活日期","实付金额","付款标识","兑换日期","收货人姓名	","收货人电话","收货地址","订单号","运单号"};  
          
        Row header_row = sheet.createRow(0);  
//        header_row.setHeight((short)(20*24));  
          
        //创建单元格的 显示样式  
        CellStyle style = wb.createCellStyle();  
        style.setAlignment(CellStyle.ALIGN_CENTER); //水平方向上的对其方式  
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
//        style.setBorderBottom(HSSFCellStyle.BORDER_THICK);//下边框
          
          
//        title_cell.setCellStyle(style);  
//        title_cell.setCellValue("用户详细信息");  
          
    //    sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));  
          
          
          
        for(int i=0;i<headers.length;i++){  
            //设置列宽   基数为256  
            sheet.setColumnWidth(i, 10*256);  
            Cell cell = header_row.createCell(i);  
            //应用样式到  单元格上  
            cell.setCellStyle(style);  
            cell.setCellValue(headers[i]);  
        }  
          
//          for(String s:map.keySet()){
//        	 ArrayList str= (ArrayList) map.get(s);
//        	 int cnt=str.size();
//        	 Row row = sheet.createRow(i+1); 
//        	 for(int j=0;j<data[i].length;j++){
//                 Cell cell = row.createCell(j+1);  
//                 cell.setCellValue(data[i][j].toString());  
//             }   
//          }
          
//        for(int i=0;i<data.length;i++){                
//            Row row = sheet.createRow(i+1);  
//            row.setHeight((short)(20*20)); //设置行高  基数为20  
//            for(int j=0;j<data[i].length;j++){
//            	if(i>=1&&j==0){
//            		if(data[i][j]==data[i-1][j]){
//            			System.out.println("--"+data[i][j]);
//            			System.out.println("--"+data[i-1][j]);
//            			CellRangeAddress cra=new CellRangeAddress(i, i+1, j+1, j+1);      
//            			//在sheet里增加合并单元格       
//            			sheet.addMergedRegion(cra);        
//            		}            		
//            	}
//                Cell cell = row.createCell(j+1);  
//                cell.setCellValue(data[i][j].toString());  
//            }               
//        }  
        
        List list=new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        
        HashMap<String, ArrayList> dis = new HashMap<String, ArrayList>();
        
        List list1=new ArrayList();
        Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			List list2=new ArrayList();
			list2.add("1");
			list2.add("2017/7/16");
			list2.add("福运金秋");
			list2.add("20");
			list2.add("288");
			list2.add("6");
			list2.add("3456");
			
			Row row = sheet.createRow(i+1); 
			for(int x=0;x<list2.size();x++){
              Cell cell = row.createCell(x+1); 
              cell.setCellStyle(style);  
              cell.setCellValue(list2.get(x).toString());  
			}
		
		}    
		CellRangeAddress cra=new CellRangeAddress(1, list.size()+1, 1, 1); 
		//在sheet里增加合并单元格       
		sheet.addMergedRegion(cra); 
		Row row = sheet.createRow(list.size()+1);
		Cell cell=row.createCell(2);
		cell.setCellValue("数量小计"); 
		
		cell=row.createCell(5);
		cell.setCellValue("付款小计"); 
		
		cra=new CellRangeAddress(list.size()+1, list.size()+1, 2, 3); 
		//在sheet里增加合并单元格       
		sheet.addMergedRegion(cra); 
		
		cra=new CellRangeAddress(list.size()+1, list.size()+1, 5, 6); 
		//在sheet里增加合并单元格       
		sheet.addMergedRegion(cra); 
        
        FileOutputStream fileOut = new FileOutputStream("d:/users.xls");  
        wb.write(fileOut);  
        fileOut.close();  
    }  
      
      
    public static void main(String[] args) throws Exception {  
//      testCreateFirstExcel97();  
//      testCreateFirstExcel07();  
          
//      createExcelOfData();  
        createExcelOfUsers();  
    }  
  
}  