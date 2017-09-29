package test;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

public class Testnewlscodeq {
	public static void main(String[] s) throws Exception{
		
//		for(int i=10;i<20;i++){
		//quanjude
		String apikey = "3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
		String encryptsecret = "8uEb#Lw*R_6gHdfe";
		
		//qingpi
//		String apikey="3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
//		String encryptsecret = "8uEb#Lw*R_6gHdfe";
		
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"绠楁硶/妯″紡/琛ョ爜鏂瑰紡"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    		String params = "user=q&active=a4&t="+System.currentTimeMillis();
		
		    String sign = sign(params+"&key="+apikey);//formatter.toString();//鑾峰緱sign鐨勫�
		    //formatter.close();
	        Form f = Form.form();
		
		    	
		    	//瀵瑰師濮嬫暟鎹繘琛屽姞瀵�	http://0k6.cn/q/54865631379434  testactive201609061802
	        String encline = new BASE64Encoder().encode(cipher.doFinal("http://0k6.cn/q/54865631379436,004,2016-12-06 20:05:10,01".getBytes("UTF-8")));
		    	f.add("data", encline);
		    	//String encline = new BASE64Encoder().encode(cipher.doFinal("http://0k6.cn/p/BND8Y".getBytes("UTF-8")));
		    	//f.add("data", encline);
		    //production
		    //System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());

//    		 params = "user=q&active=na2&t="+System.currentTimeMillis();
//    		
//		    sign = sign(params+"&key="+apikey);//formatter.toString();//鑾峰緱sign鐨勫�
//	        f = Form.form();
//	        for (int i = 0; i < 5000;i++){
//		    	encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/q/testnotactive20160715200"+i+",2016-07-15 20:05:10").getBytes("UTF-8")));
//		    	f.add("data", encline);
//	        }
	    //production
	    System.out.println(Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
	    	
	    //test
	    	//System.out.println(Request.Post("http://106.75.16.21/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());

	        //test
		    //System.out.println(Request.Post("http://106.75.16.21/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://192.168.11.15:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
			//System.out.println(Request.Post("http://localhost:8080/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString());
		
//	}
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
