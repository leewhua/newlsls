

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerSocket {
	public static void main(String[] str) throws IOException{
		ServerSocket ss = new ServerSocket(9999);
		Socket s = ss.accept();
		while( s!=null){
			System.out.println("connected");
			OutputStream os = s.getOutputStream();
			os.write(hexStringToBytes(str2HexStr("test")));
			os.flush();
			//s.close();
			//System.out.println("waiting");
			//s = ss.accept();
		}
		
	}
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	 public static String str2HexStr(String str)    
	 {      
	   
	     char[] chars = "0123456789ABCDEF".toCharArray();      
	     StringBuilder sb = new StringBuilder("");    
	     byte[] bs = str.getBytes();      
	     int bit;      
	         
	     for (int i = 0; i < bs.length; i++)    
	     {      
	         bit = (bs[i] & 0x0f0) >> 4;      
	         sb.append(chars[bit]);      
	         bit = bs[i] & 0x0f;      
	         sb.append(chars[bit]);    
	         //sb.append(' ');    
	     }      
	     return sb.toString().trim();      
	 }    
	 public static String hexStr2Str(String hexStr)    
	 {      
	     String str = "0123456789ABCDEF";      
	     char[] hexs = hexStr.toCharArray();      
	     byte[] bytes = new byte[hexStr.length() / 2];      
	     int n;      
	   
	     for (int i = 0; i < bytes.length; i++)    
	     {      
	         n = str.indexOf(hexs[2 * i]) * 16;      
	         n += str.indexOf(hexs[2 * i + 1]);      
	         bytes[i] = (byte) (n & 0xff);      
	     }      
	     return new String(bytes);      
	 }    
}
