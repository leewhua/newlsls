package com.lsid.datanalysis;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Location {
	//key=ip,value=weidu,jingdu,sheng,shi,
	private static Map<String, String> locations = new HashMap<String, String>();
	private static Map<String, String> fails = new HashMap<String, String>();
	//http://api.map.baidu.com/location/ip?ip=123.153.60.199&ak=CEqrwMgvMICUzfmkj6OUeePG
	//{"address":"CN|\u6d59\u6c5f|\u53f0\u5dde|None|UNICOM|0|0","content":{"address_detail":{"province":"\u6d59\u6c5f\u7701","city":"\u53f0\u5dde\u5e02","district":"","street":"","street_number":"","city_code":244},"address":"\u6d59\u6c5f\u7701\u53f0\u5dde\u5e02","point":{"y":"3312807.61","x":"13518854.29"}},"status":0}
	
	//http://api.map.baidu.com/geocoder/v2/?location=39.983424,116.322987&output=json&ak=CEqrwMgvMICUzfmkj6OUeePG
	//{"status":0,"result":{"location":{"lng":116.32298703399,"lat":39.983424051248},"formatted_address":"北京市海淀区中关村大街27号1101-08室","business":"中关村,人民大学,苏州街","addressComponent":{"adcode":"110108","city":"北京市","country":"中国","direction":"附近","distance":"7","district":"海淀区","province":"北京市","street":"中关村大街","street_number":"27号1101-08室","country_code":0},"poiRegions":[],"sematic_description":"北京远景国际公寓(中关村店)内0米","cityCode":131}}
	
	public static void main(String[] s) throws JsonProcessingException, ClientProtocolException, IOException{
		JsonNode j = new ObjectMapper().readTree(Request.Get("http://api.map.baidu.com/location/ip?ak=CEqrwMgvMICUzfmkj6OUeePG&ip="+"123.153.60.199").execute().returnContent().asString());
		String province = URLDecoder.decode(j.get("content").get("address_detail").get("province").asText(), "UTF-8");
		String city = URLDecoder.decode(j.get("content").get("address_detail").get("city").asText(), "UTF-8");
		System.out.println(province);
		System.out.println(city);
		System.out.println(j);
	}
	public static void main1(String[] s) throws IOException{
		List<String> raw = new LinkedList<String>();
		for (int i = 1; i < 9; i++){
			raw.addAll(Files.readAllLines(Paths.get("E:/work/dataanalysis/QR_READ_HISTORY_BOX-"+i+".txt"), Charset.forName("UTF-8")));
		}
		int size = raw.size();
		int count = 0;
		
		String surfix = new SimpleDateFormat("MMddHHmmss").format(new Date())+".txt";
		String location = "E:/work/dataanalysis/locations"+surfix;
		Files.createFile(Paths.get(location));
		BufferedWriter w = Files.newBufferedWriter(Paths.get(location), Charset.forName("UTF-8"));
		
		String fail = "E:/work/dataanalysis/fails"+surfix;
		Files.createFile(Paths.get(fail));
		BufferedWriter w1 = Files.newBufferedWriter(Paths.get(fail), Charset.forName("UTF-8"));
		
		for (int i = 1; i <size; i++){
			if (i%2000==0){
				try {
					System.out.println("Processed ["+i+"] got ["+locations.size()+"] locations");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String[] line = raw.get(i).split(",");
			if (line.length>3){
				if (locations.get(line[3])==null){
					try{
						JsonNode j = new ObjectMapper().readTree(Request.Get("http://api.map.baidu.com/location/ip?ak=CEqrwMgvMICUzfmkj6OUeePG&ip="+line[3]).execute().returnContent().asString());
						String province = URLDecoder.decode(j.get("content").get("address_detail").get("province").asText(), "UTF-8");
						String city = URLDecoder.decode(j.get("content").get("address_detail").get("city").asText(), "UTF-8");
						if ((province==null||province.trim().isEmpty())&&(city==null||city.trim().isEmpty())){
							Integer.valueOf("throw exception");
						}
						if (province==null||province.trim().isEmpty()){
							province = line[3];
						}
						if (city==null||city.trim().isEmpty()){
							city = line[3];
						}
						
						String weidu = line[3];
						if (line.length>7&&line[7]!=null&&!line[7].trim().isEmpty()){
							weidu = line[7];
						}
						String jingdu = line[3];
						if (line.length>6&&line[6]!=null&&!line[6].trim().isEmpty()){
							jingdu = line[6];
						}
						
						String newLine = weidu+","+jingdu+","+province+","+city+",";
						w.write(line[3]+","+newLine);
						w.newLine();
						w.flush();
						locations.put(line[3], newLine);
					}catch(Exception e){
						if (line.length>7&&line[7]!=null&&!line[7].trim().isEmpty()&&line[6]!=null&&!line[6].trim().isEmpty()){
							count++;
							System.out.println(count+" gps");
							try{
								JsonNode j = new ObjectMapper().readTree(Request.Get("http://api.map.baidu.com/geocoder/v2/?output=json&ak=CEqrwMgvMICUzfmkj6OUeePG&location="+line[7]+","+line[6]).execute().returnContent().asString());
								String province = j.get("result").get("addressComponent").get("province").asText();
								String city = j.get("result").get("addressComponent").get("city").asText();
								if ((province==null||province.trim().isEmpty())&&(city==null||city.trim().isEmpty())){
									Integer.valueOf("throw exception");
								}
								if (province==null||province.trim().isEmpty()){
									province = line[3];
								}
								if (city==null||city.trim().isEmpty()){
									city = line[3];
								}
								
								String weidu = line[3];
								if (line.length>7&&line[7]!=null&&!line[7].trim().isEmpty()){
									weidu = line[7];
								}
								String jingdu = line[3];
								if (line.length>6&&line[6]!=null&&!line[6].trim().isEmpty()){
									jingdu = line[6];
								}
								
								String newLine = weidu+","+jingdu+","+province+","+city+",";
								w.write(line[3]+","+newLine);
								w.newLine();
								w.flush();
								locations.put(line[3], newLine);
							
							}catch(Exception e1){
								System.out.println("fail gps ["+fails.size()+"]"+raw.get(i));
								w1.write(raw.get(i));
								w1.newLine();
								w1.flush();
								fails.put(line[3], raw.get(i));
							}
						} else {
							System.out.println("fail gps length ["+fails.size()+"]"+raw.get(i));
							w1.write(raw.get(i));
							w1.newLine();
							w1.flush();
							fails.put(line[3], raw.get(i));
						}
					}
				}
			} else {
				System.out.println("fail length ["+fails.size()+"]"+raw.get(i));
				w1.write(raw.get(i));
				w1.newLine();
				w1.flush();
				fails.put(line[3], raw.get(i));
			}
		}
		System.out.println("ending "+count+" gps");
		
		w.close();
		
		w1.close();
	}
}
