package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test1 {
	public static void main(String[] args) throws ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		long timeStart=sdf.parse("2018-01-01 00:00:00").getTime();
//		timeStart=System.currentTimeMillis();
//		System.out.println(timeStart);
//		long timeStart=sdf.parse("2017-07-20 06:00:00").getTime();
//		System.out.println(timeStart);
//		long timeStart=1501405978944l;
//		Date date1 = new Date(timeStart);
//		System.out.println(sdf.format(date1));
		
//		int i=1;
//		String s = String.format("%02d", 1);
//		System.out.println(s);// print 01
//		
		
		String ts="036541";
		int a=Integer.valueOf(ts);
		System.out.println(a);
		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long current=System.currentTimeMillis();
//		System.out.println(timeStart);
		long timeStart=sdf.parse("2017-08-25 00:00:00").getTime();
		long timeStart1=System.currentTimeMillis();
		System.out.println(timeStart);
	//System.out.println(timeStart1);
		
		
		//String ss="hm@admin2017";
		String ss="hmzl@0801";	
		System.out.println(EncoderByMd5(ss));
//		String s="f$r&x9!im4tpx5Ue";
//		System.out.println(s.length());
//		 s="9Tr!RXvrn83g$7yr";
//		System.out.println(s.length());
//		String  s="ftyu%csx9cse1iox@1zppm0!^axv03xr#p";
//			System.out.println(s.length());
//			
//			 Calendar calendar = Calendar.getInstance();
//		        Date date = new Date(System.currentTimeMillis());
//		        calendar.setTime(date);
//		        calendar.add(Calendar.WEEK_OF_YEAR, 1);
//		   //     calendar.add(Calendar.YEAR, -1);
//		        date = calendar.getTime();
//		        System.out.println(date.getTime());
//		        long date1=date.getTime();
//		        System.out.println(sdf.format(date1));
//			
//			
//			String temp="adasdas$shared=1";
//			String code=temp.substring(0,temp.indexOf("$"));
//			System.out.println(code);
//			
//			String data="lsid.ip.prize.limit.day.l:200;";
//			String data1=data.substring(0,data.length()-1);
//			System.out.println(data1);
//			String []param=data.split(";");
//			for(int i=0;i<param.length;i++){
//				String str=param[i];
//				int idx=str.indexOf(":");
//				String configkey=str.substring(0, idx);
//				String configvalue=str.substring(idx+1, str.length());
//				System.out.println("111"+configkey+":"+configvalue);
//			}
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
//		//Date date= new Date();
//		System.out.println(sdf.format(new Date()));
		
//		for(int i=0;i<20;i++){
//			int a = (int)(Math.random()*(9999-1000+1))+1000;
//			String s=a+"";
//			String sql="insert into ls_test_pwd(pwd,isused)values('"+s+"','0');";
//			System.out.println(sql);
//		}
//		for(int i=0;i<10;i++){
//			String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
//			System.out.println(id);
//		}
		
//    	String access_token = "nRWYnWzGh6FFmFlWtKeV20DAnqmsSk7YFNrwLCYzZIgT23LAADBN_IDkStFfZk4fHgOLPDPwOqqvWzswmubHEXSqbySpEihJ9YnUSOlO0SMJ1vsxXOKs-NDP4mVPc8V7XPYfAGAGYH";
//    	String code="636633426311";
//    	String card_id="pTzd4t_MiegfIxR7JmcSeNH9moAE";
//    	try{
//    		String params=params(code,card_id);
//    		 transfer(params,access_token);
//    		//System.out.println(res);
//    	}catch(Exception ex){
//    		ex.printStackTrace();
//    	}
		
		String ticket="123=";
		if(ticket.endsWith("=")){
    		ticket=ticket.substring(0,ticket.length()-1);
    	}
		System.out.println(ticket);
		
	}
	
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	MessageDigest md5=MessageDigest.getInstance("MD5");
    	BASE64Encoder base64en = new BASE64Encoder();	
    	String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
    	return newstr;
    	}
    
    	private static String params(String code, String card_id) throws JsonProcessingException{
            StringBuilder s = new StringBuilder();  
            Map<String, String> rettmp = new HashMap<String, String>();
            rettmp.put("code", code);
            rettmp.put("card_id", card_id);       
            return new ObjectMapper().writeValueAsString(rettmp);
    	}
    	
    	private static void transfer(String params,String access_token){
    		HttpPost httppost = new HttpPost("https://api.weixin.qq.com/card/code/consume?access_token="+access_token);
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
        	httppost.setConfig(requestConfig);
            CloseableHttpClient req = null;
    		CloseableHttpResponse res = null;
        	try {
    	   		req = HttpClients.custom().build();
    	   		StringEntity ss = new StringEntity(params,"UTF-8");
            	httppost.setEntity(ss);
            	res = req.execute(httppost);
            	HttpEntity entity = res.getEntity();
                StringBuffer s = new StringBuffer();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text=null;
                    while ((text = bufferedReader.readLine()) != null) {
                    	System.out.println(text);
                    	s.append(text);
                    	
                    }
                }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
