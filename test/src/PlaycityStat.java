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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlaycityStat {
	public static void main(String[] s) throws Exception{
		
		final Path output = Paths.get("statplaycity"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".txt");
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
				if (file.toString().endsWith("playcity")){
					BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
						String line = br.readLine();
						while (line!=null){
							String[] temp = line.split(",");
							if (temp.length>7){
								String ip = temp[1];
								String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(temp[2])));
								String openid = temp[3];
								String prize = URLDecoder.decode(temp[7],"UTF-8");
								String newline = openid+","+time+","+prize+","+ip;
								List<String> lines = new ArrayList<String>(1);
								lines.add(newline);
								Files.write(output, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
