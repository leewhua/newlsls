import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class testclientsocket {
	public static void main(String[] str){
		//System.out.println(hexStringToBytes("ae")[0]);
		//System.out.println("ae".getBytes()[0]);
		int port = 0;
		try{
			port = Integer.parseInt(str[1]);
		}catch(Exception ex){
			//do nothing
		}
		if (str.length<2||port==0){
			System.out.println("Please run command: java -classpath . test [lasersystem ip] [lasersystem listen port] such as java -classpath . test 192.168.1.3 9999");
		} else {
			Socket s = null;
			InputStream is = null;
			OutputStream os = null;
			byte[] r = null;
			try{
				System.out.println("connecting to ip ["+str[0]+"] port["+port+"]");
				s = new Socket(str[0], port);
				
				is = s.getInputStream();
				r = new byte[200];
				is.read(r);
				System.out.println("connected with response ["+bytesToHexString(r)+"]");
				
				String code = "http://0k6.cn/a/test1234567890test1234567890";
				System.out.println("sending my code ["+code+"]");
				os = s.getOutputStream();
				os.write(send(code));
				os.flush();
				r = new byte[200];
				is.read(r);
				System.out.println("response my code ["+bytesToHexString(r)+"]");
				
				code = "http://0k6.cn/b/1234567890test1234567890test";
				System.out.println("sending my code 2 ["+code+"]");
				os.write(send(code));
				os.flush();
				r = new byte[200];
				is.read(r);
				System.out.println("response my code 2 ["+bytesToHexString(r)+"]");
			}catch(Exception ex){
				System.out.println("talking error");
				ex.printStackTrace();
			} finally {
				try{
					System.out.println("closing connection");
					os.write(sendclose());
					os.flush();
					r = new byte[200];
					is.read(r);
					System.out.println("response closing ["+bytesToHexString(r)+"]");
					is.close();
					os.close();
					s.close();
				}catch(Exception ex){
					System.out.println("closing error");
					ex.printStackTrace();
				}
			}
		}
	}
		   
	private static byte[] send(String code){
		String tosend = "02044101"+Integer.toHexString(code.length()+2)+"000000"+str2HexStr(code)+"03";
		System.out.println("====sending hex ["+tosend+"]");
		return hexStringToBytes(tosend);
	}
	
	private static byte[] sendclose(){
		String tosend = "0202F00003";
		System.out.println("====sending hex ["+tosend+"] to close connection");
		return hexStringToBytes(tosend);
	}
	
	private static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0){  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++){  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	
	private static byte[] hexStringToBytes(String hexString){  
	    if (hexString == null || hexString.equals("")){  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();
	    byte[] d = new byte[length];
	    for (int i = 0; i < length; i++){
	        int pos = i * 2;
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	private static byte charToByte(char c){  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	 
	private static String str2HexStr(String str){  
		char[] chars = "0123456789ABCDEF".toCharArray();      
		StringBuilder sb = new StringBuilder("");    
		byte[] bs = str.getBytes();      
		int bit;      
	         
		for (int i = 0; i < bs.length; i++){      
			bit = (bs[i] & 0x0f0) >> 4;      
	      	sb.append(chars[bit]);      
	      	bit = bs[i] & 0x0f;      
	      	sb.append(chars[bit]);    
	      	//sb.append(' ');    
		}      
		return sb.toString().trim();      
	}    
	 
	private static String hexStr2Str(String hexStr){      
		String str = "0123456789ABCDEF";      
		char[] hexs = hexStr.toCharArray();      
		byte[] bytes = new byte[hexStr.length() / 2];      
		int n;      
   
		for (int i = 0; i < bytes.length; i++){      
			n = str.indexOf(hexs[2 * i]) * 16;      
			n += str.indexOf(hexs[2 * i + 1]);      
			bytes[i] = (byte) (n & 0xff);      
		}      
		return new String(bytes);      
	}    
}
