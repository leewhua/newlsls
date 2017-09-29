package com.lsid.qrentry.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.fluent.Request;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsid.config.listener.AutoConfig;
import com.lsid.mysql.util.SpringJdbc4mysql;
import com.lsid.util.DefaultCipher;

public class ZfbFilter implements Filter {

    public void destroy() {

    }
    
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest)req;
    	HttpServletResponse response = (HttpServletResponse)res;
    	try{
    		if (request.getRequestURI().equals("/ls/info")||request.getRequestURI().equals("/ls/play2")||request.getRequestURI().equals("/ls/report")){
    			chain.doFilter(request, response);
    			return;
    		}
	    	if (request.getHeader("User-Agent")==null||!request.getHeader("User-Agent").contains("AlipayClient")){
	    		chain.doFilter(req, res);
	       	} else {
	       		String ip = getIpAddr(request);
	       		int codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_deniedip where remoteip=?",new Object[]{ip},Integer.class);
	       		if(codecnt>0){
	       			String sql="select scantime,erpiretime from t_deniedip where remoteip=?";
					Map<String, Object> mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{ip});
					long scantime = (long) mapinfo.get("scantime");
					long erpiretime = (long) mapinfo.get("erpiretime");
					if ((System.currentTimeMillis()-scantime)<=erpiretime){
						//ip被封的界面
						response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.denied"));
						return;
					}
	       		}
	       		//如果请求以ticket开头
				if (request.getRequestURI().startsWith("/ticket/")){
					getinfozfb(request, response, request.getRequestURI().substring(8));
				} else {
					//http://0k6.cn/t/testestestestestest201609291014
					String code = request.getRequestURI().substring(1);
					String namespace="";
					String namespace1="";
					String [] fixcode=new String[]{"y/eugvr7sd","t/6rdcjijh","a/sr478jgk","p/35rdfhuh"};
					if(code.indexOf("/")>0){
						String code1 = code.substring(code.indexOf("/")+1);
						namespace1=code.substring(0,code.indexOf("/"));
						if(code1.equals("abcdefg")){
							gozfb(request, response, "getinfozfb", namespace1, code1,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid"), "auth_userinfo");
							return;
						}
						for(int i=0;i<fixcode.length;i++){
							if(fixcode[i].equals(code)){
								gozfb(request, response, "getinfozfb", namespace1, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid"), "auth_userinfo");
								return;
							}
						}	
					}
					
					try{
	    				 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_code where code=? ",new Object[]{code},Integer.class);
	    				if (codecnt>0){
	    					String sql="select num from t_code where code=?";
	    					Map<String, Object> map=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,code);
	    					int num=(int) map.get("num");
	    					Path path = Paths.get("/data/tomcat7newdemo/webapps/ROOT/test.txt");
	    					List<String> list = Files.readAllLines(path, Charset.forName("UTF-8"));   					
	    					for (int i = 0; i < list.size(); i++) {
	    						String[] nums = list.get(i).split(",");
	    						if (num >= Integer.parseInt(nums[0]) && num <= Integer.parseInt(nums[1])) {
	    							namespace=nums[2];
	    							break;
	    						}
	    					}
	    					gozfb(request, response, "getinfozfb", namespace, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid"), "auth_userinfo");
	    					}else{
	    						System.out.println("=========namespace1===="+namespace1);
	    						if(namespace1.equals("h")){
	    							codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from hm_code44 where paycode=? or exchangecode=?",new String[]{code,code},Integer.class);
	    							if(codecnt>0){ 
	    								gozfb(request, response, "getinfozfb", namespace1, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid."+namespace1), "auth_userinfo");
	    							}else{
	    								//码不存在的界面
	    								response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist.h"));
	    								return;
	    							}
	    						}else{
	    						//内码是抽奖。外码是产品相关信息，没有内外码的放到内码字段中
	    						String temp=request.getRequestURI().substring(1);
	    						if(temp.indexOf("/")>0){
	    							namespace=temp.substring(0,temp.indexOf("/"));	
	    						}
								//code = temp.substring(temp.indexOf("/")+1);
	    						codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where outercode=? ",new String[]{temp},Integer.class);
	    						if(codecnt>0){
	    								gozfb(request, response, "getinfozfb", namespace, temp,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid"), "auth_userinfo");	
	    						}else{
	    							codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where innercode=? ",new String[]{temp},Integer.class);
	    							if(codecnt>0){
	        							gozfb(request, response, "getinfozfb", namespace, temp,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infozfb.appid"), "auth_userinfo");	
	        						}else{
	        							//码不存在界面
	        							 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_deniedip where remoteip=?",new Object[]{ip},Integer.class);
	        				       		if(codecnt>0){
	        								SpringJdbc4mysql.getJdbc("0").update("update t_deniedip set scantime =? where remoteip=?",System.currentTimeMillis(),ip);	
	        				       		}else{
	        				       			String ticket = UUID.randomUUID().toString().replaceAll("-", "");   
	        				       			String sql="insert into t_deniedip (id,namespace,code,entry,scantime,remoteip,erpiretime) values(?,?,?,?,?,?,?)";
	        				       			Object[] args = new Object[] { ticket,namespace,temp,"wx",System.currentTimeMillis(),ip,90000};
	        				       			SpringJdbc4mysql.getJdbc("0").update(sql, args);  
	        				       		}
	        				       		response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist"));	
	        				       		return;
	        						}
	    						}
	    					}
	    				}
						}catch(Exception ex){
						ex.printStackTrace();
						//response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist."+namespace)+request.getRequestURI());
    				}
				}
	       	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.zfbentry.error")+request.getRequestURI()+","+URLEncoder.encode(ex.getMessage(),"UTF-8"));
    	}	    	
    }

    
    private static void gozfb(HttpServletRequest request, HttpServletResponse response, String ticket4action, String namespace, String enc,  long intime, String remoteip, String zfbappid, String zfbscope) throws Exception{
	    	String ticket = UUID.randomUUID().toString().replaceAll("-", "");   
	    	String sql="insert into t_ticketinfo (ticketid,namespace,code,openid,entry,confirmtime,intime,remoteip,isprize,erpiretime) values(?,?,?,?,?,?,?,?,?,?)";
	    	try{
	    		Object[] args = new Object[] { ticket,namespace,enc,"","zfb",0l,intime,remoteip,"0",90000};
	    		SpringJdbc4mysql.getJdbc("0").update(sql, args); 
				response.sendRedirect("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="+zfbappid+"&scope="+zfbscope+"&redirect_uri="+URLEncoder.encode(AutoConfig.get("lsid.userentry.host")+"ticket/"+ticket, "UTF-8"));
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    		}
		}

	private static void getinfozfb(HttpServletRequest request, HttpServletResponse response, String ticket) throws Exception{
		String sql ="select count(*) from t_ticketinfo where ticketid=?";
		int codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
		if(codecnt>0){	
			sql="select isprize,namespace,code from t_ticketinfo where ticketid=?";
			Map<String, Object> mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{ticket});
			String namespace = (String) mapinfo.get("namespace");
			String isprize = (String) mapinfo.get("isprize");
			String code = (String) mapinfo.get("code");
			if(isprize.equals("0")){
				AlipayClient alipayClient;
				if(namespace.equals("h")){
					 alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AutoConfig.get("lsid.infozfb.appid."+namespace),
							AutoConfig.get("lsid.infozfb.private."+namespace),"json","GBK",AutoConfig.get("lsid.infozfb.public."+namespace),"RSA2");
				}else{
					 alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AutoConfig.get("lsid.infozfb.appid"),
							AutoConfig.get("lsid.infozfb.private"),"json","GBK",AutoConfig.get("lsid.infozfb.public"));
				}				
				AlipaySystemOauthTokenRequest zfbtokenrequest = new AlipaySystemOauthTokenRequest();
				zfbtokenrequest.setCode(request.getParameter("auth_code"));
				zfbtokenrequest.setGrantType("authorization_code");
			    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(zfbtokenrequest);
			    String infozfbid = oauthTokenResponse.getUserId();
			    System.out.println("====infozfbid==="+infozfbid);
				if (infozfbid!=null){ 
					 sql="select count(*) from t_ticketinfo where ticketid=?";
					 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
					 long confirmtime=System.currentTimeMillis();
					 if(codecnt==0){
						sql="select count(*) from t_ticketinfo where isprize<>'1' and openid=? and namespace=?";
						codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{infozfbid,namespace},Integer.class);
						if(codecnt>0){
							 mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap("select ticketid from t_ticketinfo where isprize<>'1' and  openid=? and namespace=?",new Object[]{infozfbid,namespace});
							 String oldticket=(String) mapinfo.get("ticketid");
							 SpringJdbc4mysql.getJdbc("0").update("delete from t_ticketinfo where ticketid=?",oldticket);
						}
					 }
						//更新ticketinfo 两个字段
						sql="update t_ticketinfo set confirmtime=? ,openid=? where ticketid=?";
						SpringJdbc4mysql.getJdbc("0").update(sql,confirmtime,infozfbid,ticket);	
						
					}else {
						 if(code.equals("abcdefg")){
							 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.report")+ticket);	
							 return;
						 }else if(code.equals("t/6rdcjijh")){
							 response.sendRedirect(AutoConfig.get("lsid.res.host")+"shoot/index.html?"+ticket);	
							 return; 
						 }else{
							 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where outercode=? ",new String[]{code},Integer.class);
							 if(codecnt>0){
								 //外码
								 sql="select innercode from t_codeother where outercode=?";
									Map<String, Object> codeinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{code});
									 String innnercode = (String) codeinfo.get("innercode");
									 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_ticketinfo where isprize='1' and code=? and namespace=?",new String[]{innnercode,namespace},Integer.class);
									 if(codecnt>0){
										 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
									 }else{
										 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.out"));	 	 
									 }
								 return;
							 }else{
								 //内码
								 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
								 return;
							   }
						    }	 
					    }
					  }
		 	if(namespace.equals("h")){					 							 				
		 			response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);		 		
		 	}else{
			     if(code.equals("abcdefg")){
					   response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.report")+ticket);	
					   return;
			     }else if(code.equals("t/6rdcjijh")){
					 response.sendRedirect(AutoConfig.get("lsid.res.host")+"shoot/index.html?"+ticket);	
					 return; 
				 }else{
			    	 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where outercode=? ",new String[]{code},Integer.class);
			    	 if(codecnt>0){
			    		 //外码
			    		 sql="select innercode from t_codeother where outercode=?";
							Map<String, Object> codeinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{code});
							 String innnercode = (String) codeinfo.get("innercode");
							 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_ticketinfo where isprize='1' and code=? and namespace=?",new String[]{innnercode,namespace},Integer.class);
							 if(codecnt>0){
								 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
							 }else{
								 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.out"));	 	 
							 }
			    		 return;
			    	 }else{
			    		 //内码
			    		 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
			    		 return;
			    	 }	 
			     }
		 	}
			}else{
			//码不存在界面
			response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist"));
			}
		}
    public void init(FilterConfig arg0) throws ServletException {

    }    
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)){
        	ip = "127.0.0.1";
        }
        return ip;
    }

}
