import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Decoder;

public class ATotalScan {
/*
 * a,
 * D1yWfDsvCYeDVm5Te7NY%2BQ%3D%3D,
 * owErBs_iOPXd0_hwAfvRnVvq-bNM,
 * 223.104.20.38,
 * 1467388728516,
 * owErBs_iOPXd0_hwAfvRnVvq-bNM,
 * owErBs_iOPXd0_hwAfvRnVvq-bNM,
 * oErOcwVgZvUl5arJRs6W4xa9F4RE,
 * ck4W4aQM28uSpJ%2BL8ONgAEURfLx5DcyB483WY1TupRxWoxMSUypP0tkyL7HA%2BcWyT3mXbBDutBPg%0AMxp%2FgcplANE%2FYS7pLmaG%2FRDPYPKGCT6OEQ1dJ3UScRsKnRNp0OAsmzu81dohZIZ7ox4Tc%2Bmv5p1%2F%0A%2FIAyupdUtH0LJ5B2%2Bf9pS8WA88qvI1rXVYKTqgPBuqFpysOJZF9sQ5vw7nGIiOOlwviCurvyYHzM%0AdH4QRpV%2BOGit8udPlDzigH140TKMFy6zq14cn%2F0KtmrICJC%2BCDcWa51RV9H3r29Wk%2BjY72wXr6%2Bw%0AEKxwBUR%2FgfHPkg6uXUJaDX4i2Mye4UaHeTi7v513P%2FM5Qm2pbSwDP2YW3YPF%2F%2Ffs96CPSUqWnoXV%0AD5yxjirK4mB2q5EmZ%2F6gfwDGEg%3D%3D,
 * 1467388752596,
 * cash,
 * 100,
 * 1000018301201607010769091605,2016-07-01 23:59:12
a,Fkg6de%2B1Gf33wMX%2B51dQyQ%3D%3D,owErBs6L0yAgHw6hzESzmRhrgomg,223.104.20.126,1467388752877,owErBs6L0yAgHw6hzESzmRhrgomg,owErBs6L0yAgHw6hzESzmRhrgomg,oErOcwUH1dEadAsapiizXmmaX55k,ck4W4aQM28uSpJ%2BL8ONgAIAPeRtHzXZSz9v03NyBWBVRH8af5m3PaEvhdmebKpswJEmI0ekvbg4u%0A6z5ArqUhEqWrNSZTNmxPNMLmtfTo4E5xBeXt6dOgPKYvF8wFYFIjFlTek6X90b2Ry0vXKi105UWi%0A%2FmBPbU3%2BcruD6ik%2FEYwgpdNSl76E7dct2ea2mbfiovMFfmBMqqzEnijNJlZyyHz9Zj2KJ4xGBkD8%0AsFwzXTnXBPRnOHgIXQaOrzBsgztyw9b8bE%2FhE8uAtSnRHymHco5gKAfVbEWFunLVXzIHl8MWJUWC%0AyHtRxVTK1%2FA7BPA%2B%2BWm4%2BxyAxM3ueTe%2Bw9TCEua1ynO8Xv6Y%2BWcIiCgN65C88%2FTwsO95tK6%2FXpNA%0AS%2BR%2F7c1VCldJePyov4qtSCq1qDHkT1c6huegxosWqV12sZY%3D,1467388769165,cash,200,1000018301201607010769031757,2016-07-01 23:59:28
a,k7goLhQhZ6p2ZMmA1u324g%3D%3D,owErBswjZ5smrbKl7BvjXOplcNVk,182.144.39.219,1467388765355,owErBswjZ5smrbKl7BvjXOplcNVk,owErBswjZ5smrbKl7BvjXOplcNVk,oErOcwYcT7Yf8PfOQhv0WXJXukn4,ck4W4aQM28uSpJ%2BL8ONgAKc2IfMfajj%2BPNS%2FZSlizaEt3aaJj9bR6AUa1LLnnzV2eGeGTMMdxjEq%0Aj2%2FiAOAdJgZ2TN%2Bdt1u%2BvUAgBY3h%2BGGWpucDASheb4GpqdU8utZ3dD%2FBwX89DyvV4FDzdainPxFW%0AiPMEmGm9gKdx3zOt6T9I5D%2FDuRZTIcvbMeVBoD9numU5yo7e7Ofuwu8JOi5TMqTiaMWjIqov%2BMsD%0AocR%2Bp4bLPUbcFZdtzVNKDKG97jOo7BJQAKLeNvk9fcb61y%2B9BzDUkBtLKHIpt7CeLRaCNDRmXigD%0ANcoaCliT%2F5leJecMhgsd8xBS4EptMv3dpD0tCtDJqCRiMc1SSRj69qT7NuXcYfN3KhO43ODtCB2p%0A2XOxJqZQ7iGIud2Tuguq2S6xSg%3D%3D,1467388782738,jifen,10
a,0Mn8ybkmht%2BKHjcVlf%2FmtA%3D%3D,4V6Lg1d5rHeJOBI1t7yTydNoDSAntAb8JHdKliB%2BsmyAsoc1u7V8AcXOIHEKciRbAE7WhFeRORaY%0A6YIL2USEiw%3D%3D,117.136.75.65,1468943964125,owErBs_cHOKNq3SEHyL-xDJ9XE48,owErBs_cHOKNq3SEHyL-xDJ9XE48,oErOcwUG_avnoXYxIkmCqmBuBbPQ,ck4W4aQM28uSpJ%2BL8ONgAGWfNoxy5G%2B6jn28ZyxT5fTPwSRqBcE%2BOcxD%2BJOziIo%2FoQ3pSNDWcBWC%0Ak%2BG%2FDDw07SXq3k91pVO4vKh7Y9EUcIiWpucDASheb4GpqdU8utZ3VV0injOuWJ7b1gYwCDxZi9Yy%0AgbXfCn8%2Be9SVIoAriE9I5D%2FDuRZTIcvbMeVBoD9numU5yo7e7Ofuwu8JOi5TMqTiaMWjIqov%2BMsD%0AocR%2Bp4bWptZtHDvR8RS9c6gFyr7MV6ZfolvVr8pnaowUDeqtOYixmL5tKN5RiMX7QU19Zg0D4UIx%0A5fVXlAeDPSxoi%2F0foxUuW1ZQ3hbP4b%2BWbkzpJQW9MvrNjkB0Hjr3utuxRou%2Bcwvf2PSozEZ3FFG%2B%0AUe8idsEJYQCgfcQ2qb7zWm2D0Z1CTA6S0SmbjX8EnRJeYJY%3D,1468943979719,jifen,10
a,FSqRuV3AxUkaZRU%2FTcwafA%3D%3D,UcHc%2F2fzsScgC3CnHkIbbdNoDSAntAb8JHdKliB%2BsmzHL6rL3fte1ZBTCAGrIg54xwtGtL%2FYOCi9%0AB9Ek9qqrZw%3D%3D,117.136.75.248,1468943864909,owErBsz9hXZ48foQxQFMmFmpzYS0,owErBsz9hXZ48foQxQFMmFmpzYS0,oErOcwah-Vj4PUCjtPDRPIGfhmZA,ck4W4aQM28uSpJ%2BL8ONgALZZUSYvJzYGg39FTubkpmEySQOjVLnv9XKt717xjn85ZET1L1wnOEO9%0AqSRczuLnBvCpIT4dQR8DwWJwsci0%2BumV2h3AgPzPL90%2FVbSpC5ZY1VppLiTC3sFB1r4sFnuepenC%0AtpD5KVl5HYZL0fThXU%2Fuh3fGbcdNmYmCykJaMSmyjlxInOnOaa5LzmXv4O3%2Bx6wFFT7Lv%2FIY9qzM%0AklS8Jeu8nmrCKpyZmRDX5TtN6DWWLdBzO8AtGZDRD%2B198HT0Q2JavzyQ2aaX2DI7gw1WaVpSRsNR%0A%2BnKFw3z5VZLu%2Bp90T8Dmpgh4j5taDTtpCMsfKvZoDtyJF8CjbqJZmMyEQ2CiLrsy3MCB%2FIBY3gxF%0A2hmumyFv26PMbRVu0Ow0ARaMszHkT1c6huegxosWqV12sZY%3D,1468943980573,cash,100,1000018301201607190977186910,2016-07-19 23:59:40

 */
	private static final Map<String, Integer> openidcount = new HashMap<String, Integer>();
	private static Key decKey = null;
	private static Cipher cipher = null;
	private static BASE64Decoder b64dec = new BASE64Decoder();

	static {
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		    random.setSeed("3ojhpoew8098y329hrq3hf923r9823uyr4".getBytes());
		    KeyGenerator kgen = KeyGenerator.getInstance("AES");
		    kgen.init(128, random);
		    SecretKey secretKey = kgen.generateKey();
		    byte[] enCodeFormat = secretKey.getEncoded();
		    decKey = new SecretKeySpec(enCodeFormat, "AES");
		    kgen = null;
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, decKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] s) throws Exception{
		
		Files.walkFileTree(Paths.get("/data/lstat/a"), new FileVisitor<Object>(){

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
				if (file.toString().endsWith("scanned")){
					BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
						String line = br.readLine();
						while (line!=null){
							String[] temp = line.split(",");
							if (openidcount.get(temp[5])==null){
								openidcount.put(temp[5], 1);
							} else {
								openidcount.put(temp[5],openidcount.get(temp[5])+1);
							}
							line = br.readLine();
						}
						br.close();
						br = null;
					}
				
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}  
			  
		});

		final Path output = Paths.get("atotalscan"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				
		Files.walkFileTree(Paths.get("/data/lstat/a"), new FileVisitor<Object>(){

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
				if (file.toString().endsWith("scanned")){
					BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
						String line = br.readLine();
						while (line!=null){
							String[] temp = line.split(",");
							String enc=temp[1];
							String nickname="unknown";
							String ip = temp[3];
							String product="unknown";
							String day=new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(temp[4])));
							String second=new SimpleDateFormat("HH:mm:ss").format(new Date(Long.valueOf(temp[4])));
							String prize=temp[11];
							
							if (temp==null){
								System.out.println(temp);
							}
							
							if (temp[5]==null){
								System.out.println("["+temp[5]+"]");
							}
							if (openidcount==null){
								System.out.println("==["+openidcount+"]");
							}
							if (temp[5]!=null&&openidcount!=null&&openidcount.get(temp[5])==null){
								System.out.println("====["+temp[5]+"]");
							}
							if (temp[5]!=null&&openidcount!=null&&openidcount.get(temp[5])!=null&&openidcount.get(temp[5])>=50){
								String decpro = null;
								try{
									decpro = new String(cipher.doFinal(b64dec.decodeBuffer(URLDecoder.decode(temp[2],"UTF-8"))));
								}catch(Exception ex){
									String jsontopost = "{\"rowkey\":\""+enc+"\",\"rowkeyhash\":\""+enc+"\",\"columns\":[{\"columnfamily\":\"cf\",\"column\":\"cc\",\"value\":\"\"}]}";
									String result = Request.Post("http://10.19.9.216:9999/newlsbigstore/r").bodyForm(
											Form.form().add("namespace", "a").add("table", "c3").add(
													"json", jsontopost).build()).execute().returnContent().asString();
									JsonNode jn = new ObjectMapper().readTree(result);
									if (jn.get("result")!=null&&!jn.get("result").asText().equals("success")){
										String returnvalue = jn.get("value").asText();
										try {
											decpro = new String(cipher.doFinal(b64dec.decodeBuffer(URLDecoder.decode(returnvalue,"UTF-8"))));
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
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
								
						   		try{
						   			JsonNode jn = new ObjectMapper().readTree(new String(cipher.doFinal(b64dec.decodeBuffer(URLDecoder.decode(temp[8],"UTF-8")))));
						   			nickname=jn.get("nickname").asText().replaceAll(",", "");
						   		}catch(Exception ex){
									ex.printStackTrace();
								}
						   		Files.write(output, (enc+","+nickname+","+ip+","+product+","+day+","+second+","+prize+System.lineSeparator()).getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
							}

							line = br.readLine();
						}
						br.close();
						br = null;
					}
				
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}  
			  
		});


	}
}
