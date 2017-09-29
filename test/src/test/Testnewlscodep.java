package test;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsid.util.DefaultCipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Testnewlscodep {

	public static void main2(String[] s) throws Exception{
		System.out.println(new Date(1468384421909l));
		String apikey="5t684c3a_Be6c*4r2w_9c78!8dH8fe329ujy";
		String encryptsecret = "6Fet!Lw_R@6gHiUY";
		
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        List<String> raws = Files.readAllLines(Paths.get("filename"), Charset.forName("UTF-8"));
        int total = 0;
        Form f = Form.form();
	    
        for (int i=0;i<raws.size();i++){
    		total++;
    		String encline = new BASE64Encoder().encode(cipher.doFinal((DefaultCipher.dec(raws.get(i))).getBytes("UTF-8")));
	    	f.add("data", encline);
        
	    	if (total==5000||i==raws.size()-1){
	    		String params = "user=p&active=a1&t="+System.currentTimeMillis();
        		
    		    String sign = sign(params+"&key="+apikey);
    		    //production
    		    JsonNode jn = new ObjectMapper().readTree(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
    		    while (jn.get("fails")!=null&&jn.get("fails").size()!=0){
    		    	Thread.sleep(3000);
    		    	jn = new ObjectMapper().readTree(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
        		}
    		    total=0;
	    		f = Form.form();
		    	
	    	}
        }

	}
	
	public static void main1(String[] s) throws Exception{
//		String[] f = Paths.get("/Users/geopoe/work/productiondeploy/20160812/raw").toFile().list();
//		Files.createDirectories(Paths.get("/Users/geopoe/work/productiondeploy/20160812/"));
//		for (int i=0;i<f.length;i++){
//			if (f[i].startsWith("qp")){
//				List<String> lines = Files.readAllLines(Paths.get("/Users/geopoe/work/productiondeploy/20160812/raw").resolve(f[i]), Charset.forName("UTF-8"));
//				for (int j = 0; j<lines.size();j++){
//					if (j==lines.size()-1){
//						Files.write(Paths.get("/Users/geopoe/work/productiondeploy/20160812/").resolve(f[i]), 
//								(DefaultCipher.enc(lines.get(j))).getBytes("UTF-8"), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
//					} else {
//						Files.write(Paths.get("/Users/geopoe/work/productiondeploy/20160812/").resolve(f[i]), 
//								(DefaultCipher.enc(lines.get(j))+System.lineSeparator()).getBytes("UTF-8"), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
//					}
//				}
//			}
//		}
//		for (int i=90; i<95;i++){
//			if (i==94){
//				Files.write(Paths.get("/Users/geopoe/work/productiondeploy/20160812/").resolve("qptestt"), (DefaultCipher.enc("http://0k6.cn/p/testactive2016081222"+i)).getBytes("UTF-8"), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
//			} else {
//				Files.write(Paths.get("/Users/geopoe/work/productiondeploy/20160812/").resolve("qptestt"), (DefaultCipher.enc("http://0k6.cn/p/testactive2016081222"+i)+System.lineSeparator()).getBytes("UTF-8"), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
//			}
//		}
		//quanjude
		//String apikey = "3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
		//String encryptsecret = "8uEb#Lw*R_6gHdfe";
		
		//qingpi
		String apikey="5t684c3a_Be6c*4r2w_9c78!8dH8fe329ujy";
		String encryptsecret = "6Fet!Lw_R@6gHiUY";
		
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    		String params = "user=p&active=a1&t="+System.currentTimeMillis();
		
		    String sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的值
		    //formatter.close();
	        Form f = Form.form();
		
		    for (int i=10;i<20;i++){
		    	//对原始数据进行加密
		    	String encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/p/testactive2016120819"+i).getBytes("UTF-8")));
		    	f.add("data", encline);
		    }
		    	//production
		    System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());

//    		 params = "user=p&active=na1&t="+System.currentTimeMillis();
//    		
//		    sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的值
//	        f = Form.form();
//	        String encline = new BASE64Encoder().encode(cipher.doFinal("http://0k6.cn/p/BND8Y".getBytes("UTF-8")));
//	    	f.add("data", encline);
//	    //production
//	    System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());

	        //test
		    //System.out.println(Request.Post("http://106.75.16.21/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://192.168.11.15:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://localhost:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
		
	}
	
	public static void main(String[] s) throws Exception{
		for(int i=10;i<11;i++){
		//quanjude
		//String apikey = "3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
		//String encryptsecret = "8uEb#Lw*R_6gHdfe";
		
		//rijiaman
		//String apikey="qM12yg%hfghsvxs6@24tyf*kbrpX#xdyqw";
		//clientsecret
		//String encryptsecret = "9Tr!RXvrn83g$7yr";
		
		//qingpi
		String apikey="5t684c3a_Be6c*4r2w_9c78!8dH8fe329ujy";
		String encryptsecret = "6Fet!Lw_R@6gHiUY";
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
          Form f = Form.form();
//        List<String> raws = Files.readAllLines(Paths.get("D:\\1.text"), Charset.forName("UTF-8"));
//        //String str="http://0k6.cn/r/";
//        String str="";
//        int total = 0;
//        int size = raws.size();
//        System.out.println(size);
//        BufferedWriter writer = Files.newBufferedWriter(Paths.get("d:\\2.text"), StandardCharsets.UTF_8);
//        for (int i=0;i<size;i++){
//        	int count = i+1;
//    		total++;
//    		System.out.println(raws.get(i));
//    		String encline = new BASE64Encoder().encode(cipher.doFinal((str+raws.get(i)).getBytes("UTF-8")));
//	    	f.add("data", encline);
//	    	if (total==10||i==(size-1)){
//	    		String params = "user=t&active=a1&t="+System.currentTimeMillis();
//    		    String sign = sign(params+"&key="+apikey);
//    		    //production
//    		    String res = Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString();
//    		    System.out.println(i+"="+res);
//	    		f = Form.form();
//			    total=0;
//			    writer.write(i+":"+raws.get(i)+"="+res+"\r\n");
//	    	}


          
          
          
          
//	       
//
//	  }
//        writer.flush();
//        writer.close();
        		//r ��ҵ���20160819���� 10�Ǳ�� 10��11��12   testactive201608311919  testestestestestest201608301313
          
        	  
        	  String encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/p/testactive2017030119"+i).getBytes("UTF-8")));
        	  f.add("data", encline);
        	  cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        	  System.out.println(cipher.doFinal(new BASE64Decoder().decodeBuffer(encline)));
        	  String params = "user=p&active=a1&t="+System.currentTimeMillis();
        	  
        	  String sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的�?
        	  //production
        	  String res = Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString();
        	  System.out.println("="+res);
	}

}
    private static String sign(String raw){
    	MessageDigest crypt = null;
    	try {
    		crypt = MessageDigest.getInstance("MD5");
    	    crypt.reset();
    	   	crypt.update(raw.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return byteArrayToHexString(crypt.digest());
    }
    
	private static String[] HexCode = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }

    public static String byteArrayToHexString(byte[] b)
    {
        String result = "";
        for (int i = 0; i < b.length; i++)
        {
            result = result + byteToHexString(b[i]);
        }
        return result;
    }

	
}
