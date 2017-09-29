package com.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class SearchCode {
	public static void main1(String[] s){
		final String search = s[1];
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
								if (line.contains(search)){
									System.out.println(line);
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public static void main(String[] s) throws Exception{
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed("3ojhpoew8098y329hrq3hf923r9823uyr4".getBytes());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        Key encKey = new SecretKeySpec(enCodeFormat, "AES");
        kgen = null;
        Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, encKey);
		System.out.println(new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(URLDecoder.decode("5KeihAxFAJc4ysQLeoNpoLjJ%2BTcUV3m267BXHSNR0QisALYoBomoBYbLE0t4MEC%2F","UTF-8")))));
	}
}
