package com.lsid.mysql.imp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.lsid.mysql.util.SpringJdbc4mysql;

public class InnerOuterImp {
	public static void main1(String[] args) {
		String path="D:\\testdemon\\innerouter\\lq.text";
		try {
			String sql="insert into t_codeother(outercode,innercode) values(?,?)";
			List<String>files=Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			List<Object[]>batchArgs=new ArrayList<Object[]>();
			for(int i=0;i<files.size();i++){
				String [] lines=files.get(i).split(",");
				Object[] arg={lines[0],lines[1]};
				batchArgs.add(arg);
			}
			SpringJdbc4mysql.getJdbc("0").batchUpdate(sql, batchArgs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main2(String[] args) {
		String path="D:\\testdemon\\innerouter\\lq.text";
		
		try {
			List<String>files=Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			 BufferedWriter writer = Files.newBufferedWriter(Paths.get("D:\\testdemon\\innerouter\\lq.sql"), StandardCharsets.UTF_8);
			System.out.println(files.size());
			 for(int i=0;i<files.size();i++){
				String [] lines=files.get(i).split(",");
				//http://lsid.me/lq/H6DQR
				String sql="insert into t_codeother(outercode,innercode) values('"+lines[0].substring(15)+"','"+lines[1].substring(15)+"');"+"\r\n";
				writer.write(sql);
			}
			 writer.flush();
		     writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main3(String[] args) {
		String path="D:\\testdemon\\democode\\tezhongbin";
		
		try {
			List<String>files=Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			 BufferedWriter writer = Files.newBufferedWriter(Paths.get(path+".sql"), StandardCharsets.UTF_8);
			System.out.println(files.size());
			 for(int i=0;i<files.size();i++){
				String [] lines=files.get(i).split(",");
				//http://lsid.me/lq/H6DQR
				String sql="insert into t_codeother(outercode,innercode) values('"+lines[0].substring(15)+"','"+lines[1].substring(15)+"');"+"\r\n";
				writer.write(sql);
			}
			 writer.flush();
		     writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main6(String[] args) {
		String path="D:\\testdemon\\democode\\jincaidi";
		
		try {
			List<String>files=Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			 BufferedWriter writer = Files.newBufferedWriter(Paths.get(path+".sql"), StandardCharsets.UTF_8);
			System.out.println(files.size());
			 for(int i=0;i<files.size();i++){
				String [] lines=files.get(i).split(",");
				//http://lsid.me/lq/H6DQR
				String sql="insert into t_codeother(outercode,innercode) values(null,'"+lines[0].substring(15)+"');"+"\r\n";
				writer.write(sql);
			}
			 writer.flush();
		     writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String path="D:\\testdemon\\democode\\1000ma";
		
		try {
			List<String>files=Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			 BufferedWriter writer = Files.newBufferedWriter(Paths.get(path+".sql"), StandardCharsets.UTF_8);
			System.out.println(files.size());
			 for(int i=0;i<files.size();i++){
				String [] lines=files.get(i).split(",");
				//http://lsid.me/lq/H6DQR
				String sql="insert into t_code(code) values('"+lines[0]+"');"+"\r\n";
				writer.write(sql);
			}
			 writer.flush();
		     writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
