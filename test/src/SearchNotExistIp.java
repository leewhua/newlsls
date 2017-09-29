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


public class SearchNotExistIp {
	public static void main(String[] s) throws Exception{
		final String ip = s[0];
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
				if (file.toString().endsWith("notexist")){
					BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
						String line = br.readLine();
						while (line!=null){
							if (line.contains(ip)){
								System.out.println(line);
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
