package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class Testnewlscodex {
	
			
	public static void main2(String[] s){
		System.out.println(new Date(1468384421909l));
	}
	public static void main(String[] s) throws Exception{
		
		//quanjude
		//String apikey = "3c684C4a*Be6c!4r2w_9c78#8dH8fe32uFdi";
		//String encryptsecret = "8uEb#Lw*R_6gHdfe";
		
		//rijiaman
		//String apikey="qM12yg%hfghsvxs6@24tyf*kbrpX#xdyqw";
		//clientsecret
		//String encryptsecret = "9Tr!RXvrn83g$7yr";
		
		//tezhongbing  34位 
		//String apikey="mQ24xc@dfwsxgrt7#78bnd^lgyuP&lprgk";
		//clientsecret
		//String encryptsecret = "3fY#WVgkp27c&9lf";
		
		//xiaoguancha
		String apikey="xF68dj%dghjbsay0*24mhd!wdgjV&xgjlp";
		String encryptsecret = "6cW$HPcfh37f^24s";
		byte[] raw = encryptsecret.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        Form f = Form.form();
        List<String> raws = Files.readAllLines(Paths.get("D:\\小罐茶\\finalxiaoguanchadata20160909100855.txt\\finalxiaoguanchadata20160909100855.txt"), Charset.forName("UTF-8"));
        //String str="http://0k6.cn/x/";
        int total = 0;
        int size = raws.size();
        System.out.println(size);
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("d:\\test3.text"), StandardCharsets.UTF_8);
        for (int i=0;i<size;i++){
        	int count = i+1;
    		total++;
    		System.out.println(raws.get(i));
    		String encline = new BASE64Encoder().encode(cipher.doFinal((raws.get(i)).getBytes("UTF-8")));
	    	f.add("data", encline);
	    	if (total==5000||i==(size-1)){
	    		String params = "user=x&active=a1&t="+System.currentTimeMillis();
    		    String sign = sign(params+"&key="+apikey);
    		    //production
    		    String res = Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString();
    		    System.out.println(i+"="+res);
	    		f = Form.form();
			    total=0;
			    writer.write(i+":"+raws.get(i)+"="+res+"\r\n");
	    	}
   		   
	       

	  }
        writer.flush();
        writer.close();
        		//r ��ҵ���20160819���� 10�Ǳ�� 10��11��12   testactive201608311919  testestestestestest201608301313
//        		String encline = new BASE64Encoder().encode(cipher.doFinal(("http://0k6.cn/x/md838r8y4v").getBytes("UTF-8")));
//		    	f.add("data", encline);
//		    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//		    	System.out.println(cipher.doFinal(new BASE64Decoder().decodeBuffer(encline)));
//		    		String params = "user=x&active=a1&t="+System.currentTimeMillis();
//	        		
//	    		    String sign = sign(params+"&key="+apikey);//formatter.toString();//获得sign的�?
//	    		    //production
//	    		    String res = Request.Post("http://106.75.5.15/newlscode?"+params+"&sign="+sign).bodyForm(f.build()).execute().returnContent().asString();
//	    		    System.out.println("="+res);
	    		

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
