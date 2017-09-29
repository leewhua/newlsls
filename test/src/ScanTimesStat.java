
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Decoder;

public class ScanTimesStat {
	
	public static void main(String[] s){
		Path source = Paths.get("/data/lstat/a").resolve(s[0]+"scanned");
		Path target = Paths.get(s[0]+"scanstat"+s[1]);
		int mintimes = Integer.parseInt(s[1]);
		try{
			List<String> lines = Files.readAllLines(source, Charset.forName("UTF-8"));
			Map<String, Integer> times = new HashMap<String, Integer>();
			
			for (String line:lines){
				if (line!=null){
					String[] parts = line.split(",");
					String openid = parts[7];
					if (times.get(openid)==null){
						times.put(openid, 1);
					} else {
						times.put(openid, times.get(openid)+1);
					}
				}
			}
			
			for (String line:lines){
				if (line!=null){
					String[] parts = line.split(",");
					String code = parts[1];
					String ip = parts[3];
					String openid = parts[7];
					if (times.get(openid)>=mintimes){
						String intime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date(Long.valueOf(parts[4])));
						String nickname = getuserinfo("nickname",parts[8]).replaceAll(",", "");
						String type = parts[10];
						String count = "10".equals(parts[11])?parts[11]:parts[11].substring(0, parts[11].length()-2);
						List<String> outline = new ArrayList<String>();
						outline.add(code+","+openid+","+nickname+","+intime+","+ip+","+type+","+count);
						Files.write(target, outline, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					}
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public static Cipher deCipher = null;
	public static BASE64Decoder b64dec = new BASE64Decoder();
	static {
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		    random.setSeed("3ojhpoew8098y329hrq3hf923r9823uyr4".getBytes());
	        KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        kgen.init(128, random);
	        Key secretKey = kgen.generateKey();
	        byte[] enCodeFormat = secretKey.getEncoded();
	        Key key = new SecretKeySpec(enCodeFormat, "AES");
	        deCipher = Cipher.getInstance("AES");
	        deCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("===="+new Date()+"====System exited due to above exception "+e.getMessage());
			System.exit(1);
		}
	}
	private static String getuserinfo(String field, String encuserinfo){
		String returnValue = "unknown";
		try {
			String decuserinfo=new String(deCipher.doFinal(b64dec.decodeBuffer(URLDecoder.decode(encuserinfo, "UTF-8"))));
			JsonNode jn = new ObjectMapper().readTree(decuserinfo);
			returnValue=jn.get(field).asText();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return returnValue;
	}

}
