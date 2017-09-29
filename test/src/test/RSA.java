package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;

public class RSA {
	private static final String ALGOGRITHM = "RSA";
	private static final String PUBLIC_KEY_PATH = "public.key";
	private static final String PRIVATE_KEY_PATH = "private.key";
	
	public static void main(String[] s) throws Exception {
		//KeyPairGenerator引擎类用于产生密钥对，JDK(7)默认支持的算法有，DiffieHellman、DSA、RSA、EC
		//KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGOGRITHM);
		//产生密钥对
		//KeyPair keyPair = generator.generateKeyPair();
		//获取公钥
		//PublicKey publicKey = keyPair.getPublic();
		//获取私钥
		//PrivateKey privateKey = keyPair.getPrivate();
		
		//System.out.println(new BASE64Encoder().encode(publicKey.getEncoded()));
		//System.out.println("#####################################");
		//System.out.println(new BASE64Encoder().encode(privateKey.getEncoded()));
		String pubkey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9V9x4bIhlHr6Ncq44J5rcUsOY7YSDroRwT7yi"+
"sgswMuY3rMKDswBgYFGIwTt5GUGLcN/XcY24HhjHRzfStS5JhJTex1OGuKgng65xLZZRwHTmKLvz"+
"lUuOBQPB4nI+QWS+pa0ELrQ0o0/rj+53+cmfK3544cx0Tw4MS1ULdS/0CwIDAQAB";
		String prikey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL1X3HhsiGUevo1yrjgnmtxSw5jt"+
"hIOuhHBPvKKyCzAy5jeswoOzAGBgUYjBO3kZQYtw39dxjbgeGMdHN9K1LkmElN7HU4a4qCeDrnEt"+
"llHAdOYou/OVS44FA8Hicj5BZL6lrQQutDSjT+uP7nf5yZ8rfnjhzHRPDgxLVQt1L/QLAgMBAAEC"+
"gYAc2n3DcC6c0kGRho9cCVUFDCu4a0GNW5PdFZmWZXFlB+yjQVVnAmklRqhGIcQxYRLm7+18hFZn"+
"X1bBT24HUwrZb+MtLbCkunvoxfsC3S+F+EL4kJq4xfbQpvWg35RnBWOYYFV1u/527V/z7NT/X/4C"+
"AIHedcBDYgZi6C4PS47I8QJBAO7MFqmO44y1Yx85YW63f0o6trtP/gtXnB7FuRh6tu1BQR8CqpCh"+
"jmkE8a1ZKm6UJmlteydb4EIsnBkj6JV9odMCQQDK+70zLS9bZ6YFDjl/SjI1MnLNfPHA0ZspeKl6"+
"mVyuHxZhHedBbc2ud1b3F4PPToqPXfF1rDKwBFpD2qmwc8npAkANe1A8Omj2ZLc36BFsIsf49N+k"+
"K5v4H/YcdqVAkI0LUAfRLGFc0QItQ64xTztqCsswYJH3YuSCV+poMPLMnc8pAkAeOD5hyR9cz2CT"+
"Lhv3THePHfSjjS980nf0Q6ePvhdjQPOW/9m3Dv7pM5E59kFEcuAEGithpoJhXDSIF5zdzU9JAkEA"+
"gJHqwT4+b6st1dCPF+VN2BNg8Gl0PXGPN1OgFBrd98XUXXSVTTsZIL+j3FhoqZTbNblJ0qzzbH3I"+
"9Sr+r6GCGA==";
		
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
		     new BASE64Decoder().decodeBuffer(prikey));
		   KeyFactory keyf = KeyFactory.getInstance("RSA");
		   PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
		   
		   
		   X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
				     new BASE64Decoder().decodeBuffer(pubkey));
				   // RSA对称加密算法
				   java.security.KeyFactory keyFactory;
				   keyFactory = java.security.KeyFactory.getInstance("RSA");
				   // 取公钥匙对象
				   PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
				   
				   Cipher cipher = Cipher.getInstance(ALGOGRITHM);
				   
				   cipher.init(Cipher.ENCRYPT_MODE, publicKey);
					//加密
					String sendInfo = "phfioeiferfq,5t2i3t[p[ertjw[r,13451435134513451,234523451435345";
					byte[] results = cipher.doFinal(sendInfo.getBytes());
					String enc = URLEncoder.encode(new BASE64Encoder().encode(results), "UTF-8");
					System.out.println(enc);
					cipher.init(Cipher.DECRYPT_MODE, privateKey);
					//解密
					byte[] deciphered = cipher.doFinal(new BASE64Decoder().decodeBuffer(URLDecoder.decode(enc,"UTF-8")));
					//得到明文
					String recvInfo = new String(deciphered);
					System.out.println(recvInfo);
		//将公钥与私钥写入文件，以备后用
		//writeKey(PUBLIC_KEY_PATH, publicKey);
		//writeKey(PRIVATE_KEY_PATH, privateKey);
	}
	
	public void testEncryptAndDecrypt() throws Exception {
		Cipher cipher = Cipher.getInstance(ALGOGRITHM);
		//读取私钥，进行加密
		PrivateKey privateKey = (PrivateKey) readKey(PRIVATE_KEY_PATH);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		//加密
		String sendInfo = "我的明文";
		byte[] results = cipher.doFinal(sendInfo.getBytes());
		
		//读取公钥，进行解密
		PublicKey publicKey = (PublicKey) readKey(PUBLIC_KEY_PATH);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		//解密
		byte[] deciphered = cipher.doFinal(results);
		//得到明文
		String recvInfo = new String(deciphered);
		System.out.println(recvInfo);
	}
	
	public static void writeKey(String path, Key key) throws Exception {
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(key);
		oos.close();
	}
	
	public static Key readKey(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream bis = new ObjectInputStream(fis);
		Object object = bis.readObject();
		bis.close();
		return (Key) object;
	}
}
