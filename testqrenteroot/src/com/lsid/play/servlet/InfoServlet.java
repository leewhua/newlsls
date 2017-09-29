package com.lsid.play.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsid.config.listener.AutoConfig;
import com.lsid.mysql.util.SpringJdbc4mysql;
import com.lsid.util.DefaultCipher;

@SuppressWarnings("serial")
public class InfoServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InfoServlet() {
		super();
	}

	@Override
	public void destroy(){
	}
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("XDomainRequestAllowed","1");
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

    	String ticket = request.getParameter("ticket");
    	try{
	    	if (ticket==null||ticket.trim().isEmpty()||ticket.trim().length()!=32){
	    		throw new Exception("invalidticketparam");
	    	}
	    	String sql="select count(*) from t_ticketinfo where ticketid=?";
			int cnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
			if(cnt>0){
				sql="select t.namespace,t.entry,t.isprize,t1.nickname,t1.headimgurl,t1.province,t1.city from t_ticketinfo t,t_userinfo t1 where t.openid=t1.openid and ticketid=?";
				Map<String, Object> info= SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{ticket});
				String namespace = (String) info.get("namespace");
				//String purpose = ticketvalues[0];
				String entry = (String) info.get("entry");
				//String enc = ticketvalues[3];
				//String encinfo = ticketvalues[4];
				//String uuid = ticketvalues[5];
				//String playwxid = ticketvalues[6];
				//String openid = (String) info.get("openid");
				//String intime = ticketvalues[8];
				//String confirmtime = ticketvalues[9];
				//String remoteip = ticketvalues[10];
//				if (!"info".equals(purpose)){
//					throw new Exception("invalidticketvalue");
//				}
				
				String nickname=(String) info.get("nickname");
				String headimgurl=(String) info.get("headimgurl");
				String province=(String) info.get("province");
				String city = (String) info.get("city");
				String cashed = (String) info.get("isprize");
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("result", "success");
				result.put("entry", entry);
				result.put("nickname", nickname);
				result.put("headimgurl", headimgurl);
				result.put("province", province);
				result.put("city", city);
				result.put("cashed", cashed);
				result.put("namespace", namespace);
								
		    	if (!AutoConfig.get("lsid.playhost."+namespace).isEmpty()){
					Enumeration<String> params = request.getParameterNames();
					Form f = Form.form();
			    	while (params.hasMoreElements()){
			    		String param = params.nextElement();
			    		if ("ticket".equals(param)){
			    			f.add("ticket", ticket);
			    		} else {
			    			f.add(param, URLEncoder.encode(request.getParameter(param),"UTF-8"));
			    		}
			    	}
					String moresult = null;
			    	try{
			    		moresult = Request.Post(AutoConfig.get("lsid.playhost."+namespace)+"/info").bodyForm(f.build()).execute().returnContent().asString();
			    		result.put("more", new ObjectMapper().readTree(moresult));
			    	}catch(Exception ex){
			    		Map<String, Object> error = new HashMap<String, Object>();
			    		error.put("result", "fail");
			    		error.put("reason", ex.getMessage()+"["+moresult+"]");
			    		result.put("more", error);
			    	}
		    	} else {
		    		result.put("more", "");
		    	}
		    	PrintWriter p = response.getWriter();		
				p.write(new ObjectMapper().writeValueAsString(result));
				p.flush();
				p.close();
				p=null;
					
		 	} else {
		 		throw new Exception("invalidticket");
		 	}
    	}catch(Exception ex){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", "fail");
			result.put("uri", request.getRequestURI());
			result.put("reason", ex.getMessage());
			PrintWriter p = response.getWriter();		
			p.write(new ObjectMapper().writeValueAsString(result));
			p.flush();
			p.close();
			p=null;
    	}
	}
	
}
