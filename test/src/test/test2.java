package test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class test2 {
	public static void main(String[] args) throws ParseException, IOException{
//		Path path=Paths.get("D:\\1.txt"); 
//		try {
//			Files.write(path, "3".getBytes(), StandardOpenOption.APPEND);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		File file = new File("D:\\1.txt");
//		System.out.println(file.length());
		
//		int idx=1;
//		System.out.println((idx+1)+"");
//		
//		String namespace1="mx-a";
//		System.out.println(namespace1.substring(0,3));
//		
//		String name ="sHmo0IDO02VJilerUP0FL1M8zAbzeV9lttzTcKQ%2FIbw%3D_a_4";
//		System.out.println(name.hashCode()%2);
		
//		String s="shopcode10";
//		System.out.println(s.matches("^shopcode.*"));
		
//		String decpro ="testactive201707041010,06,KZR20170303A000,KZR20170303A000,2017-03-03 11:34:45.156";
		

//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		long timeStart=sdf.parse("2017-03-03 11:34:45.15").getTime();
//		
//		System.out.println(timeStart);
//		
//		String[] infos = decpro.split(",");
//		if (infos.length==5){
//			String time=infos[4];
//			System.out.println(time);
//			long timeStart1=sdf.parse(time).getTime();
//			System.out.println(timeStart1);
//		}
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("d:\\3.sql"), StandardCharsets.UTF_8);

		String A="A";
		String B="B";					
		for(int i=151;i<=200;i++){
			String num = String.format("%03d", i);
			String parentid=UUID.randomUUID().toString().replace("-", "");
			//String sql="insert into hm_user_account1(id,user,password,parentid)values('"+parentid+"','"+A+num+"','"+A+num+"',0);";
			String sql="INSERT INTO hm_user_account1 (id, user, password, parentid) VALUES('"+parentid+"','"+A+num+"','"+A+num+"','0');";

			//System.out.println(sql);  
			writer.write(sql+"\r\n");
			for(int j=1;j<=200;j++){
				String num1 = String.format("%03d", j);
				String userid=UUID.randomUUID().toString().replace("-", "");
				sql="INSERT INTO hm_user_account1 (id,user,password, parentid) VALUES('"+userid+"','"+A+num+B+num1+"','"+A+num+B+num1+"','"+parentid+"');";
				//System.out.println(sql);  
				writer.write(sql+"\r\n");
			}
		}
        writer.flush();
        writer.close();
	
		
//		String str = "abc12645d6saf";
//		System.out.println(str.matches("^abc.*$"));
//		String prizename="美团外卖";
//		String  prizename1=URLEncoder.encode(prizename,"utf-8");
//		String rowkey = URLEncoder.encode("c1_"+prizename1, "UTF-8");
//		System.out.println(rowkey);
//		for(int i=0;i<20;i++){			
//			String str = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
//			System.out.println(str);
//		}
		
//		String ids="1,2";
//		String[]id=ids.split(",");
//		for(int i=0;i<id.length;i++){
//			System.out.println(id[i]);
//		}
		
		
//		String sql3 = "update hm_code set status='1' where id in("+ids+")";
//		
//		StringBuilder sql1 = new StringBuilder("insert into hm_order(id,openid,codeid)values");
//		for(int i=0;i<id.length;i++){
////			sql="update hm_code set status='1' where id=?";
////			SpringJdbc4mysql.getJdbc("0").update(sql,id[i]);
//			String id1 = UUID.randomUUID().toString().replaceAll("-", "");
//			sql1.append("("+id1+","+22+","+id[i]+"),");
//		
//		}
//		final String sql2=sql1.toString().substring(0, sql1.length()-1);
//		System.out.println(sql3);

	}

}
