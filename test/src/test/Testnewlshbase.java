package test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsid.hbase.util.HBaseModelCfColVal;
import com.lsid.hbase.util.HBaseModelJson;
import com.lsid.hbase.util.HBaseModelOneRow;

public class Testnewlshbase {
	public static void run(final int i){
		final int a = i;
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Form f = Form.form().add("namespace", "q").add("table", "cachenow").add("action", "").add("cache", "now");
					List<HBaseModelOneRow> rows = new ArrayList<HBaseModelOneRow>();
						HBaseModelOneRow onerow = new HBaseModelOneRow();
						try {
							onerow.setRowkey("rowkey"+a);
							onerow.setRowkeyhash(null);
							List<HBaseModelCfColVal> columns = new ArrayList<HBaseModelCfColVal>();
							for (int k=0;k<2;k++){
								HBaseModelCfColVal col = new HBaseModelCfColVal();
								col.setColumn("col");
								col.setColumnfamily("cf");
								col.setValue("1234"+k);
								columns.add(col);
							}
							onerow.setColumns(columns);
							rows.add(onerow);
							//if (j%2==0){
								f.add("json", new ObjectMapper().writeValueAsString(onerow));
							//} else {
							//	f.add("json", "{\"rowkey\":\"\",\"rowkeyhash\":\"rowkey264_1\",\"columns\":[{\"columnfamily\":\"cf\",\"column\":\"testcol\",\"value\":\"testval264_1\"}],\"hashedrowkey\":\"5rowkey264_1\"}");
						//	}
							} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					
					System.out.println((a+1)+"="+Request.Post("http://localhost:8081/newlsbigstore/w").
							bodyForm(f.build()).execute().returnContent().asString());
					//Testnewlshbase.run(a+1);
				//} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	public static void main(String[] s) throws Exception{
		HBaseModelOneRow onerow = new HBaseModelOneRow();
		onerow.setRowkey("192.168");
		onerow.setRowkeyhash("192.168");
		List<HBaseModelCfColVal> columns = new ArrayList<HBaseModelCfColVal>();
		for (int k=0;k<1;k++){
			HBaseModelCfColVal col = new HBaseModelCfColVal();
			col.setColumn("col");
			col.setColumnfamily("cf");
			col.setValue("");
			columns.add(col);
		}
		onerow.setColumns(columns);
		System.out.println(new ObjectMapper().writeValueAsString(onerow));
		/*
		for (int i=0;i<8;i++){
			increment(i);
		}
		*/
	}
	
	public static void increment(final int i){
		final int a = i;
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Form f = Form.form().add("namespace", "q").add("table", "incre").add("action", "increment").add("cache", "");
					List<HBaseModelOneRow> rows = new ArrayList<HBaseModelOneRow>();
						HBaseModelOneRow onerow = new HBaseModelOneRow();
						try {
							onerow.setRowkey("rowkey2");
							onerow.setRowkeyhash(null);
							List<HBaseModelCfColVal> columns = new ArrayList<HBaseModelCfColVal>();
							for (int k=0;k<1;k++){
								HBaseModelCfColVal col = new HBaseModelCfColVal();
								col.setColumn("col");
								col.setColumnfamily("cf");
								col.setValue("1");
								columns.add(col);
							}
							onerow.setColumns(columns);
							rows.add(onerow);
							//if (j%2==0){
								f.add("json", new ObjectMapper().writeValueAsString(onerow));
							//} else {
							//	f.add("json", "{\"rowkey\":\"\",\"rowkeyhash\":\"rowkey264_1\",\"columns\":[{\"columnfamily\":\"cf\",\"column\":\"testcol\",\"value\":\"testval264_1\"}],\"hashedrowkey\":\"5rowkey264_1\"}");
						//	}
							} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					
					System.out.println((a+1)+"="+Request.Post("http://localhost:8081/newlsbigstore/w").
							bodyForm(f.build()).execute().returnContent().asString());
					//Testnewlshbase.run(a+1);
				//} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
}
