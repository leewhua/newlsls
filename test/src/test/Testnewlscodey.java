package test;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Testnewlscodey {

	public static void main2(String[] s){
		System.out.println(new Date(1468384421909l));
	}
	public static void main(String[] s) throws Exception{
		
		//quanjude
		//String apikey = "3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
		//String encryptsecret = "8uEb#Lw*R_6gHdfe";
		for(int i=10;i<11;i++){
		//beibingyang
		String apikey="qM12yg$hbapscuv9!82gqw@kbjpX_cvyjq";
		String encryptsecret = "8Twu@OPen06g!8tr";
		
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        List<String> raws = Files.readAllLines(Paths.get("finalbeibingyangdata20160720234102.txt"), Charset.forName("UTF-8"));
        int total = 0;
        Form f = Form.form();
	    

        /*
    		String params = "user=y&active=a1&t="+System.currentTimeMillis();
		
		    String sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的值
		    //formatter.close();
	        Form f = Form.form();
		
		    	
		    	//对原始数据进行加密
	        for (int i=10;i<50;i++){
		    	String encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/y/testactive2016080620"+i).getBytes("UTF-8")));
		    	f.add("data", encline);
	        }
		    	//String encline = new BASE64Encoder().encode(cipher.doFinal("http://0k6.cn/p/BND8Y".getBytes("UTF-8")));
		    	//f.add("data", encline);
		    //production
		    System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
*/
/*
    		 params = "user=p&active=na1&t="+System.currentTimeMillis();
    		
		    sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的值
	        f = Form.form();
	    	encline = new BASE64Encoder().encode(cipher.doFinal("http://0k6.cn/p/BND8Y".getBytes("UTF-8")));
	    	f.add("data", encline);
	    //production
	    System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
*/
	        //test
		    //System.out.println(Request.Post("http://106.75.16.21/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://192.168.11.15:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://localhost:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
        
        String encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/y/testactive2017010619"+i).getBytes("UTF-8")));
    	f.add("data", encline);
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    	System.out.println(cipher.doFinal(new BASE64Decoder().decodeBuffer(encline)));
    		String params = "user=y&active=a1&t="+System.currentTimeMillis();
    		
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
