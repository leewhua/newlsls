package com.lsid.mysql.util;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	
	public static void main(String[] args) throws JsonProcessingException, ClientProtocolException, IOException {
//		JsonNode jn = new ObjectMapper().readTree(Request.Post("http://192.168.11.21:8080/lsconsole/test1019").bodyForm(
//		Form.form().add("actiontype", "showlist").build()).execute().returnContent().asString());
//		System.out.println(URLDecoder.decode(jn.get("records").get(0).get("rules").get(0).get("name").asText(),"UTF-8"));
//		System.out.println(URLDecoder.decode(jn.get("records").get(0).get("platform").asText(),"UTF-8"));
		String str="03e0749fcfa5450f9836faf3205bf75f";
		System.out.println(str.length());
	}

}
