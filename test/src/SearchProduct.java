

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SearchProduct {
	private static final Map<String, String> a = new HashMap<String, String>();
	public static void main(String[] s){
		try {
			Files.walkFileTree(Paths.get(s[0]), new FileVisitor<Object>(){

				@Override
				public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
					
				}
				
				@Override
				public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
					
				}

				@Override
				public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
					if (!Files.isDirectory(Paths.get(file.toString()))){
						try {
							SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
					        random.setSeed("096uy04234y31pgr12p3yrp12jfhpw9efy".getBytes());
				            KeyGenerator kgen = KeyGenerator.getInstance("AES");
				            kgen.init(128, random);
				            SecretKey secretKey = kgen.generateKey();
				            byte[] enCodeFormat = secretKey.getEncoded();
				            Key encKey = new SecretKeySpec(enCodeFormat, "AES");
				            kgen = null;
				            Cipher cipher = Cipher.getInstance("AES");
							cipher.init(Cipher.DECRYPT_MODE, encKey);
							byte[] old = Files.readAllBytes(Paths.get(file.toString()));
							byte[] dec = cipher.doFinal(old);
							BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(dec)));
							String line = reader.readLine();
							while (line!=null){
								String decpro = line;
								String product = null;
								if (decpro!=null){
									String[] infos = decpro.split(",");
									if (infos.length==5){
										if ("01".equals(infos[1])||"02".equals(infos[1])){
											product="盐典";
										} else if ("06".equals(infos[1])) {
											product="BV";
										} else {
											product="体可";
										}
									} else if (infos.length>2){
										if ("38".equals(infos[2])||"11".equals(infos[2])||"12".equals(infos[2])||"13".equals(infos[2])||"14".equals(infos[2])){
											product="体可";
										} else if ("22".equals(infos[2])){
											product="BV";
										} else if ("csdy01".equals(infos[3])){
											product="BV";
										} else {
											product="盐典";
										}
									} else {
										product="unknown";
									}
								}   
								
								if ("体可".equals(product)){
									a.put(decpro.split(",")[0], "");
								}
								line = reader.readLine();
							}
							reader.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					return FileVisitResult.CONTINUE;
					
				}

				@Override
				public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
					
				}  
				  
			});
			System.out.println(a.size());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
