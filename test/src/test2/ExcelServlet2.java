package test2; 
import java.io.FileOutputStream;  
  

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.util.CellRangeAddress;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

import test2.SpringJdbc4mysql;

  
/** 
 *  
 * 测试生成Excel文档 
 *  
 * @author Administrator 
 *  
 */  
public class ExcelServlet2 {  
  
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
      
      
    public static void createonesheet(Sheet sheet,HSSFWorkbook excel) throws Exception{  
    	 String headers[] = new String[]{"序号","月饼券类型","零售价","串码","识别码","经销商一级代码","经销商一级名称","经销商二级代码","经销商二级名称","付款/激活日期","实付金额","付款标识","兑换日期",
         		"收货人姓名","收货人电话","收货地址","省份","城市","县区","订单号","运单号"};           
         Row header_row = sheet.createRow(0);          
         HSSFCellStyle cellStyle = excel.createCellStyle();  
         //创建单元格的 显示样式  
         HSSFFont hssfFont = excel.createFont();
         //设置是否斜体
//         hssfFont.setItalic(true);
         //字体大小
         hssfFont.setFontHeightInPoints((short)12);
         hssfFont.setFontName("宋体");
         hssfFont.setUnderline((byte)0);
         cellStyle.setFont(hssfFont);
                   
         for(int i=0;i<headers.length;i++){  
             //设置列宽   基数为256  
             sheet.setColumnWidth(i, 10*256);  
             Cell cell = header_row.createCell(i);  
             //应用样式到  单元格上  
             cell.setCellStyle(cellStyle);  
             cell.setCellValue(headers[i]);  
         }  
                  
        String  sql="select * from hm_code44 where status='2' and paytime>=1503590400000 and discount=6.5";
        List <Map<String,Object>> codeinfo =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
        
         sql="SELECT * FROM hm_exchangeorder";
        List<Map<String,Object>> exchangeorder =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
        
        sql="select * from hm_user_account1";
        List <Map<String,Object>> useraccount1 =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
        
        sql = "select * from hm_agency_info"; 
        List<Map<String,Object>> agencyinfo =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
        
        sql = "select * from hm_ems_order"; 
        List<Map<String,Object>> emsorder =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
        
        List<List<String>> paperinfo =new ArrayList();
        
        int xuhao=1;
     	   for(int j=0;j<codeinfo.size();j++){
//     		   System.out.println("===1==="+codeinfo.get(j).get("exchangeorder").toString());
//     		  System.out.println("====2==="+exchangeorder.get(i).get("id").toString());     		 
     			  List row=new ArrayList();
     			  String type=codeinfo.get(j).get("type").toString();
     			  String pname=codeinfo.get(j).get("name").toString();
     			  Float price=Float.valueOf(codeinfo.get(j).get("price").toString());
     			  String seirnum=codeinfo.get(j).get("seirnum").toString();
     			  String batchcode1=codeinfo.get(j).get("batchcode1").toString();
     			  
     			  String oneuserid="";
     			  String onename="";
     			  String twouserid="";
     			  String twoname="";
     			  String openid=codeinfo.get(j).get("openid").toString();
     			  
     			  
     			  String payway="微信支付";
     			 
 					String isactive=codeinfo.get(j).get("isactive").toString();
 					if(isactive.equals("1")){
 						payway="手工激活";
 					}
 					 					
     			  //帐号
 					if(isactive.equals("0")){
 						 String userid=codeinfo.get(j).get("userid").toString();
 						 Map<String, Object> map = new HashMap<String, Object>();
 						 for (Map<String, Object> one:useraccount1) {
 							 if (userid.equalsIgnoreCase(one.get("userid").toString())) {
 								 map.putAll(one);
 								 break;
 							 }
 						 }
 		     			  //sql="select userid,user,parentid from hm_user_account1 where userid=?";
 		     			  //Map map= SpringJdbc4mysql.getJdbc("0").queryForMap(sql,userid);
 		     			 String parentid=map.get("parentid").toString();
 		     			String user=map.get("user").toString();
 		     			
 		     			String aname="";
 		     			int cnt = 0;
 		     			for (Map<String, Object> one:agencyinfo) {
 		     				 if (userid.equalsIgnoreCase(one.get("userid").toString())) {
 								 cnt++;
 							 }
 		     			}
 		     			 //sql="select count(*) from hm_agency_info where userid=?";
 		     			 //int cnt=SpringJdbc4mysql.getJdbc("0").queryForInt(sql,userid);
 		     			 if(cnt>0){
 		     				 map.clear();
 		     				 for (Map<String, Object> one:agencyinfo) {
	 							 if (userid.equalsIgnoreCase(one.get("userid").toString())) {
	 								 map.putAll(one);
	 								 break;
	 							 }
 		     				 }
 		     				 //sql="select aname from hm_agency_info where userid=?"; 
 		     				 //map= SpringJdbc4mysql.getJdbc("0").queryForMap(sql,userid);
 		     				 aname=map.get("aname").toString();       				 
 		     			 }
 		     			     		     			 
 		     			 if(!parentid.equals("0")){
 		     				map.clear();
		     				 for (Map<String, Object> one:useraccount1) {
	 							 if (parentid.equalsIgnoreCase(one.get("userid").toString())) {
	 								 map.putAll(one);
	 								 break;
	 							 }
		     				 }
 		     				 //sql="select user,parentid from hm_user_account1 where userid=?";
 		        			  	 //map= SpringJdbc4mysql.getJdbc("0").queryForMap(sql,parentid);
 		        			  	 oneuserid=map.get("user").toString();
 		        			  	 twouserid=user;
 		        			  	 twoname=aname;
 		        			  	 
 		        			  	 cnt = 0;
								for (Map<String, Object> one:agencyinfo) {
									 if (parentid.equalsIgnoreCase(one.get("userid").toString())) {
										 cnt++;
									 }
								}
 		        			  	 //sql="select count(*) from hm_agency_info where userid=?";
 		             			 //cnt=SpringJdbc4mysql.getJdbc("0").queryForInt(sql,parentid);
 		             			 if(cnt>0){
 		             				map.clear();
 				     				 for (Map<String, Object> one:agencyinfo) {
 			 							 if (parentid.equalsIgnoreCase(one.get("userid").toString())) {
 			 								 map.putAll(one);
 			 								 break;
 			 							 }
 				     				 }
 		             				 //sql="select aname from hm_agency_info where userid=?";
 		             				 //map= SpringJdbc4mysql.getJdbc("0").queryForMap(sql,parentid);
 		             				 onename=map.get("aname").toString();    	             				 
 		             			 }
 		     			 }else{
 		     				 oneuserid=user;
 		     				 onename=aname;
 		     			 }
 					}
     			
     			 
     			 long paytime1=Long.valueOf(codeinfo.get(j).get("paytime").toString());
     			 String paytime=transferLongToDate("yyyy-MM-dd HH:mm:ss",paytime1);
     			    			    			 
     			  Float discount=Float.valueOf(codeinfo.get(j).get("discount").toString());
     			  float disprice=price*discount/10;
 					 

 					
 					
 					String exchangetime="";
 					String name="";
 					String telephone="";
 					String address="";
 					String exchangeid="";
 					String emsid="";
 					 String province="";
 					 String city="";
 					 String county="";
 			  for(int i=0;i<exchangeorder.size();i++){
 				if(codeinfo.get(j).get("exchangeorder").toString().equals(exchangeorder.get(i).get("id").toString())){  
     			  //兑换信息
 					
 					//System.out.println("===="+exchangeorder.get(i).get("time").toString());
     			   exchangetime=exchangeorder.get(i).get("time").toString();
     			  // exchangetime=transferLongToDate("yyyy-MM-dd HH:mm:ss",exchangetime1);
     			  
     			   name=exchangeorder.get(i).get("name").toString();
     			   telephone=exchangeorder.get(i).get("telephone").toString();
     			   address=exchangeorder.get(i).get("address").toString();
     			   province=exchangeorder.get(i).get("province").toString();
     			   city=exchangeorder.get(i).get("city").toString();
     			   county=exchangeorder.get(i).get("county").toString();
     			  
     			   exchangeid=exchangeorder.get(i).get("id").toString();  
     			  int cnt = 0;
					for (Map<String, Object> one:emsorder) {
						 if (exchangeid.equalsIgnoreCase(one.get("exchangeid").toString())&&one.get("emsid")!=null&&!one.get("emsid").toString().trim().isEmpty()) {
							 cnt++;
						 }
					}
     			  //sql="select count(*) from hm_ems_order where exchangeid=? and emsid is not null";
     			 //int cnt=SpringJdbc4mysql.getJdbc("0").queryForInt(sql,exchangeid);
     			  if(cnt>0){
     				 Map<String, Object> map = new HashMap<String, Object>();
	     				 for (Map<String, Object> one:emsorder) {
							 if (exchangeid.equalsIgnoreCase(one.get("exchangeid").toString())) {
								 map.putAll(one);
								 break;
							 }
	     				 }
     				//sql= "select emsid from hm_ems_order where exchangeid=?";
     				//Map map=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,exchangeid);
     				emsid=map.get("emsid").toString();
     			  }
 				}
 			  }
     			  xuhao++;
     			  row.add(xuhao+"");
     			  row.add(pname);
     			  row.add(price+"");
     			  row.add(seirnum);
     			  row.add(batchcode1);
     			  
     			  row.add(oneuserid);
     			  row.add(onename);
     			  row.add(twouserid);
     			  row.add(twoname);
     			  
     			  row.add(paytime);
     			  row.add(disprice+"");
     			  row.add(payway);
     			  
     			  row.add(exchangetime);
     			  row.add(name);
     			  row.add(telephone);
     			  row.add(address);
     			  row.add(province);
     			  row.add(city);
     			  row.add(county);
     			  
     			  row.add(exchangeid);
     			  row.add(emsid);
     			 
 				paperinfo.add(row);
        }
        
 		for(int i=0;i<paperinfo.size();i++){
 			Row row  = sheet.createRow(i+1);
 			for(int j=0;j<paperinfo.get(i).size();j++){
 		        //往excel表格创建一行，excel的行号是从0开始的
 		        //第一行创建第一个单元格
 				Cell cell = row.createCell(j);
 		        //设置第一个单元格的值
 				cell.setCellValue(paperinfo.get(i).get(j));
 				cell.setCellStyle(cellStyle);
 			}
 		}
 		if (codeinfo!=null) {
 			codeinfo.clear();
 		}
 		codeinfo = null;
 		
 		if (exchangeorder!=null) {
 			exchangeorder.clear();
 		}
 		exchangeorder = null;
 		
 		if (useraccount1!=null) {
 			useraccount1.clear();
 		}
 		useraccount1 = null;
 		
 		if (agencyinfo!=null) {
 			agencyinfo.clear();
 		}
 		agencyinfo = null;
 		
 		if (emsorder!=null) {
 			emsorder.clear();
 		}
 		emsorder = null;
 		
    }  
    
    public static void createtwosheet(Sheet sheet,HSSFWorkbook excel) throws Exception{  
   	 String headers[] = new String[]{"序号","订单号","月饼券类型","零售价","付款日期","实付金额","付款标识"};           
        Row header_row = sheet.createRow(0);          
        HSSFCellStyle cellStyle = excel.createCellStyle();  
        //创建单元格的 显示样式  
        HSSFFont hssfFont = excel.createFont();
        //设置是否斜体
//        hssfFont.setItalic(true);
        //字体大小
        hssfFont.setFontHeightInPoints((short)12);
        hssfFont.setFontName("宋体");
        hssfFont.setUnderline((byte)0);
        cellStyle.setFont(hssfFont);
                  
        for(int i=0;i<headers.length;i++){  
            //设置列宽   基数为256  
            sheet.setColumnWidth(i, 10*256);  
            Cell cell = header_row.createCell(i);  
            //应用样式到  单元格上  
            cell.setCellStyle(cellStyle);  
            cell.setCellValue(headers[i]);  
        }  
                 
       String  sql="select * from hm_user_ecoupons_order where status='2'";
       List <Map<String,Object>>codeinfo =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
            
       List<List<String>> paperinfo =new ArrayList();
       
       int xuhao=1;
    	   for(int j=0;j<codeinfo.size();j++){
    			  List row=new ArrayList();
    			  String id=codeinfo.get(j).get("id").toString();
    			  int cnt1=Integer.valueOf(codeinfo.get(j).get("cnt1").toString());
    			  int cnt2=Integer.valueOf(codeinfo.get(j).get("cnt2").toString());
    			  int cnt3=Integer.valueOf(codeinfo.get(j).get("cnt3").toString());
    			  int cnt4=Integer.valueOf(codeinfo.get(j).get("cnt4").toString());
    			  long paytime1=Long.valueOf(codeinfo.get(j).get("time").toString());
    			  String paytime=transferLongToDate("yyyy-MM-dd HH:mm:ss",paytime1);   			    			    			 
    			  
    			  String payway="微信支付";
    			  if(cnt1>0){
    				  String product="流心奶黄";
    				  String price="288";
    				  row.add(xuhao+"");
    				  row.add(id);
    				  row.add(product);
    				  row.add(price+"");
    				  row.add(paytime);
    				  row.add(price+"");
    				  row.add(payway);
    				  xuhao++;
    			  }
     			  if(cnt2>0){
    				  String product="蛋黄白莲蓉";
    				  String price="88";
    				  row.add(xuhao+"");
    				  row.add(id);
    				  row.add(product);
    				  row.add(price+"");
    				  row.add(paytime);
    				  row.add(price+"");
    				  row.add(payway);
    				  xuhao++;
    			  }
     			  if(cnt3>0){
    				  String product="福运金秋";
    				  String price="288";
    				  row.add(xuhao+"");
    				  row.add(id);
    				  row.add(product);
    				  row.add(price+"");
    				  row.add(paytime);
    				  row.add(price+"");
    				  row.add(payway);
    				  xuhao++;
    			  }
     			  if(cnt4>0){
    				  String product="双黄白莲蓉";
    				  String price="188";
    				  row.add(xuhao+"");
    				  row.add(id);
    				  row.add(product);
    				  row.add(price+"");
    				  row.add(paytime);
    				  row.add(price+"");
    				  row.add(payway);
    				  xuhao++;
    			  }
    			 
    			  paperinfo.add(row);
    	   }
       
		for(int i=0;i<paperinfo.size();i++){
			Row row = sheet.createRow(i+1);
			for(int j=0;j< paperinfo.get(i).size();j++){
		        //往excel表格创建一行，excel的行号是从0开始的
		        //第一行创建第一个单元格
				Cell cell = row.createCell(j);
		        //设置第一个单元格的值
				cell.setCellValue(paperinfo.get(i).get(j));
				cell.setCellStyle(cellStyle);
			}
		}
              
   }  
      
    public static void createthreesheet(Sheet sheet,HSSFWorkbook excel) throws Exception{  
      	 String headers[] = new String[]{"序号","兑换日期","收货人姓名","收货人电话","收货地址","订单号","运单号","月饼券类型","省份","城市","县区"};           
           Row header_row = sheet.createRow(0);          
           HSSFCellStyle cellStyle = excel.createCellStyle();  
           //创建单元格的 显示样式  
           HSSFFont hssfFont = excel.createFont();
           //设置是否斜体
//           hssfFont.setItalic(true);
           //字体大小
           hssfFont.setFontHeightInPoints((short)12);
           hssfFont.setFontName("宋体");
           hssfFont.setUnderline((byte)0);
           cellStyle.setFont(hssfFont);
                     
           for(int i=0;i<headers.length;i++){  
               //设置列宽   基数为256  
               sheet.setColumnWidth(i, 10*256);  
               Cell cell = header_row.createCell(i);  
               //应用样式到  单元格上  
               cell.setCellStyle(cellStyle);  
               cell.setCellValue(headers[i]);  
           }  
                    
          String  sql="select * from hm_exchangeorder where ptype='2'";
          List <Map<String,Object>>codeinfo =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
               
          sql="select * from hm_ems_order";
          List <Map<String,Object>>emsorder =SpringJdbc4mysql.getJdbc("0").queryForList(sql);
               
          List<List<String>> paperinfo =new ArrayList();
          
          int xuhao=1;
       	   for(int j=0;j<codeinfo.size();j++){
       			  List row=new ArrayList();
       			  
       			 //兑换信息
//     			  long exchangetime1=Long.valueOf(codeinfo.get(j).get("time").toString());
     			  String exchangetime=codeinfo.get(j).get("time").toString();
     			  
     			  
       			  String name=codeinfo.get(j).get("name").toString();
     			  String telephone=codeinfo.get(j).get("telephone").toString();
     			  String address=codeinfo.get(j).get("address").toString();
     			  String province=codeinfo.get(j).get("province").toString();
     			  String city=codeinfo.get(j).get("city").toString();
     			  String county=codeinfo.get(j).get("county").toString();
     			  
     			  String product="";
     			  String type=codeinfo.get(j).get("type").toString();
     			  if(type.equals("1")){
     				 product="流心奶黄";
     			  }else if(type.equals("2")){
     				 product="蛋黄白莲蓉";
     			  }else if(type.equals("3")){
     				 product="福运金秋";
     			  }else if(type.equals("4")){
     				 product="双黄白莲蓉";
     			  }
     			  
     			  String exchangeid=codeinfo.get(j).get("id").toString();
     			  String emsid="";
     			  
     			 int cnt = 0;
					for (Map<String, Object> one:emsorder) {
						 if (exchangeid.equalsIgnoreCase(one.get("exchangeid").toString())&&one.get("emsid")!=null&&!one.get("emsid").toString().trim().isEmpty()) {
							 cnt++;
						 }
					}
  			  	  
     			  //sql="select count(*) from hm_ems_order where exchangeid=? and emsid is not null";
     			  //int cnt=SpringJdbc4mysql.getJdbc("0").queryForInt(sql,exchangeid);
     			  if(cnt>0){
     				 Map<String, Object> map = new HashMap<String, Object>();
     				 for (Map<String, Object> one:emsorder) {
						 if (exchangeid.equalsIgnoreCase(one.get("exchangeid").toString())) {
							 map.putAll(one);
							 break;
						 }
     				 }
     				//sql= "select emsid from hm_ems_order where exchangeid=? ";
     				//Map map=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,exchangeid);
     				emsid=map.get("emsid").toString();
     			  }
       			 
     			  row.add(xuhao+"");
     			  xuhao++;
      			  row.add(exchangetime);
     			  row.add(name);
     			  row.add(telephone);
     			  row.add(address);
     			  
     			  row.add(exchangeid);
     			  row.add(emsid);
     			  row.add(product);
     			 
	     		row.add(product);
	     		row.add(city);
	     		row.add(county);
     			  paperinfo.add(row);
       	   }
          
   		for(int i=0;i<paperinfo.size();i++){
   			Row row  = sheet.createRow(i+1);
   			for(int j=0;j< paperinfo.get(i).size();j++){
   		        //往excel表格创建一行，excel的行号是从0开始的
   		        //第一行创建第一个单元格
   				Cell cell = row.createCell(j);
   		        //设置第一个单元格的值
   				cell.setCellValue(paperinfo.get(i).get(j));
   				cell.setCellStyle(cellStyle);
   			}
   		}
   		
   		if (codeinfo!=null) {
   			codeinfo.clear();
   		}
   		codeinfo = null;
   		
   		if (emsorder!=null) {
   			emsorder.clear();
   		}
   		emsorder = null;
   		         
      }  
    public static void  createExcelOfUsers() throws Exception{  
    	HSSFWorkbook excel = new HSSFWorkbook();          
        //创建工作表  
        Sheet sheet = excel.createSheet("纸券报表");                                  
        createonesheet(sheet,excel);
        
        //创建工作表  
        Sheet sheet2 = excel.createSheet("电子券购买报表");                                  
        createtwosheet(sheet2,excel);
        
        //创建工作表  
        Sheet sheet3 = excel.createSheet("电子券兑换报表");                                  
        createthreesheet(sheet3,excel);
        
        long currtime=System.currentTimeMillis();
		//当前时间加一周
		 Calendar calendar1 = Calendar.getInstance();
        Date date = new Date(currtime);
        calendar1.setTime(date);
        calendar1.add(Calendar.DAY_OF_MONTH, -1);
	     date = calendar1.getTime();
	     long date1=date.getTime();
	     String pretime=transferLongToDate("yyyyMMdd",date1);
	     
        String currday=transferLongToDate("yyyyMMdd",System.currentTimeMillis());
        
        Calendar  calendar= Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String reportname="hmreport20170825.xls";
//        if(hour<6){
//        	reportname="hmreport"+pretime+"16.xls";
//        }else if(hour>=6&&hour<12){
//        	reportname="hmreport"+currday+"06.xls";
//        }else if(hour>=12&&hour<16){
//        	reportname="hmreport"+currday+"12.xls";
//        }else if(hour>=16){
//        	reportname="hmreport"+currday+"16.xls";
//        }		 
        
        System.out.println("reprotname==="+reportname);
 //       String reportname="hmreport"+exchangetime+".xls";
        writeexcel(excel,"/data/tomcat7newdemo/webapps/hmconsole/report/"+reportname);
        //return reportname;
    }  
      
	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}
	
    public static void main(String[] args) throws Exception {   
        createExcelOfUsers();  
    }  
    
	public static void writeexcel(HSSFWorkbook excel,String path){
        FileOutputStream fout = null;
        try{
        	if(Files.exists(Paths.get(path))){
        		Files.delete(Paths.get(path));
        	}
            fout = new FileOutputStream(path,true);
            excel.write(fout);
            fout.close();
        }catch (Exception e){
            e.printStackTrace();
        }
	}
  
}  