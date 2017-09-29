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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsid.config.listener.AutoConfig;
import com.lsid.mysql.util.SpringJdbc4mysql;

public class WxFilter implements Filter {

    public void destroy() {

    }
    
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest)req;
    	HttpServletResponse response = (HttpServletResponse)res;
    	try{
    		if (request.getRequestURI().endsWith(".txt")){
    			chain.doFilter(req, res);
    			return;
    		}
    		if (request.getRequestURI().endsWith(".xls")){
    			chain.doFilter(req, res);
    			return;
    		}
    		if (request.getRequestURI().equals("/ls/info")||request.getRequestURI().equals("/ls/play2")||request.getRequestURI().equals("/ls/report")){
    			chain.doFilter(request, response);
    			return;
    		}
     		if (request.getRequestURI().equals("/newplay4msxq/info")||request.getRequestURI().equals("/newplay4msxq/play")){
    			chain.doFilter(request, response);
    			return;
    		}
     		if (request.getRequestURI().equals("/lsplay4tzb/info")||request.getRequestURI().equals("/lsplay4tzb/play")){
    			chain.doFilter(request, response);
    			return;
    		}
     		if (request.getRequestURI().equals("/newlsplay4hm/bg")){
    			chain.doFilter(request, response);
    			return;
    		}
     		if (request.getRequestURI().equals("/h/fixcode")){
    			chain.doFilter(request, response);
    			return;
    		}
     		if (request.getRequestURI().startsWith("/d/")){
     			response.sendRedirect("http://res.leasiondata.cn/star/index.html");
     			return;
    		}
	    	if (request.getHeader("User-Agent")==null||!request.getHeader("User-Agent").contains("MicroMessenger")){
	    		response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.nonwx")+request.getRequestURI());
	       	} else {
	       		String ip = getIpAddr(request);
	       		System.out.println("ip===="+ip);
	       		int codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_deniedip where remoteip=?",new Object[]{ip},Integer.class);
	       		if(codecnt>0){
	       			String sql="select scantime,erpiretime,namespace,code from t_deniedip where remoteip=?";
	       			
					Map<String, Object> mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{ip});
					long scantime = (long) mapinfo.get("scantime");
					long erpiretime = (long) mapinfo.get("erpiretime");
					String code11 = mapinfo.get("code").toString();
	       			
	       			System.out.println("code11===="+code11);
					//String namespace = (String) mapinfo.get("namespace");
					if ((System.currentTimeMillis()-scantime)<=erpiretime){
						//ip被封的界面
						response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.denied"));
						return;
					}
	       		}
	       		//如果请求以ticket开头
				if (request.getRequestURI().startsWith("/ticket/")){
					getinfowx(request, response, request.getRequestURI().substring(8));
				} else {
					//http://0k6.cn/t/testestestestestest201609291014
					String temp = request.getRequestURI().substring(1);
					String code=request.getRequestURI().substring(1);
					String isshared="0";
					String param="";
					System.out.println("temp==="+temp);
					if(temp.indexOf("$")!=-1){
						  code=temp.substring(0,temp.indexOf("$"));
						  isshared="1";
						  param=temp.substring(temp.indexOf("$")+1);						  
					}
					String [] fixcode=new String[]{"y/eugvr7sd","t/6rdcjijh","a/sr478jgk","p/35rdfhuh"};
					if(code.indexOf("/")>0){
						String code1 = code.substring(code.indexOf("/")+1);
						String namespace1=code.substring(0,code.indexOf("/"));
						if(code1.equals("abcdefg")){
							goWx(request, response, "getinfowx", namespace1, code1,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
							return;
						}
						for(int i=0;i<fixcode.length;i++){
							if(fixcode[i].equals(code)){
								goWx(request, response, "getinfowx", namespace1, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
								return;
							}
						}	
					}
					
				//	String namespace=temp.substring(0,temp.indexOf("/"));
					//String code = temp.substring(temp.indexOf("/")+1);
					String namespace="";
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
							goWx(request, response, "getinfowx", namespace, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
		    			//goWx(request, response, "getinfowx", namespace, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo");
    					}else{
    						//内码是抽奖。外码是产品相关信息，没有内外码的放到内码字段中
    						 temp=request.getRequestURI().substring(1);
    						if(temp.indexOf("/")>0){
    							namespace=temp.substring(0,temp.indexOf("/"));	
    						}
							//temp=temp.replace("/", "\\/");
							//code = temp.substring(temp.indexOf("/")+1);
							//外码
    						System.out.println("=========namespace===="+namespace);
    						if(namespace.equals("h")){
    							System.out.println("param==="+param);
    							if(code.equals("h/testcode")){
    								response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.beian.h"));
    								return;
    							}
    							codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from hm_code44 where paycode=? or exchangecode=?",new String[]{code,code},Integer.class);
    							if(codecnt>0){ 
    								goWx(request, response, "getinfowx", namespace, code,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid."+namespace), "snsapi_userinfo",isshared,param);
    							}else{
    								//码不存在的界面
    								response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist.h"));
    								return;
    							}
    						}else{						
    						codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where outercode=? ",new String[]{temp},Integer.class);
    						if(codecnt>0){ 
    								goWx(request, response, "getinfowx", namespace, temp,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
    						}else{
    							//内码
    							codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codeother where innercode=? ",new String[]{temp},Integer.class);
    							if(codecnt>0){
    									goWx(request, response, "getinfowx", namespace, temp,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
        						}else{
        							//特殊码
        							codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_codespecial where code=? ",new String[]{temp},Integer.class);
        							if(codecnt>0){
        								goWx(request, response, "getinfowx", namespace, temp,  System.currentTimeMillis(), ip, AutoConfig.get("lsid.infowx.appid"), "snsapi_userinfo",isshared,param);
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
        								//码不存在的界面
        								response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist"));
        								return;
        								}
        							}
    							}
    						}
    					}
					}catch(Exception ex){
						ex.printStackTrace();
						//response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.notexist"));
    				}
				}
	       	}
    	}catch(Exception ex){
    		ex.printStackTrace();
			response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.wxentry.error")+request.getRequestURI()+","+URLEncoder.encode(ex.getMessage(),"UTF-8"));
		}
    }

    private static void goWx(HttpServletRequest request, HttpServletResponse response, String ticket4action, String namespace, String enc, long intime, String remoteip, String wxappid, String wxscope,String isshared,String param) throws Exception{
    		String ticket = UUID.randomUUID().toString().replaceAll("-", "");   
    		String	sql="insert into t_ticketinfo (ticketid,namespace,code,openid,entry,confirmtime,intime,remoteip,isprize,erpiretime,isshared,param) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    		try{
    			Object[] args = new Object[] { ticket,namespace,enc,"","wx",0l,intime,remoteip,"0",90000,isshared,param};
    			SpringJdbc4mysql.getJdbc("0").update(sql, args);   		
    			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxappid+"&redirect_uri="+AutoConfig.get("lsid.userentry.host")+"ticket/"+ticket+"&response_type=code&scope="+wxscope+"&state="+new Random().nextInt(10000)+"#wechat_redirect");
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}	
    	}

    

	private static void getinfowx(HttpServletRequest request, HttpServletResponse response, String ticket) throws Exception{
		System.out.println("filter ticket======"+ticket);
			String sql ="select count(*) from t_ticketinfo where ticketid=?";
			int codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
			if(codecnt>0){	
				sql="select isprize,namespace,code,isshared,param from t_ticketinfo where ticketid=?";
				Map<String, Object> mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap(sql,new Object[]{ticket});
				String namespace = (String) mapinfo.get("namespace");
			    String isprize = (String) mapinfo.get("isprize");
				String code = (String) mapinfo.get("code");
				String isshared = (String) mapinfo.get("isshared");
				String param = (String) mapinfo.get("param");
				 sql ="select count(*) from t_codespecial where code=?";
				 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
				 String openid="";
				 if(codecnt>0){
					 //特殊ma
				 }else{
					 if(isprize.equals("0")){
						 JsonNode jn = null;
						 try{
							 String wxres="";
							 if(namespace.equals("h")){
								 
								  wxres= Request.Get(
										 "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
												 AutoConfig.get("lsid.infowx.appid."+namespace)+"&secret="+AutoConfig.get("lsid.infowx.secret."+namespace)+"&code="+request.getParameter("code")+"&grant_type=authorization_code"
										 ).execute().returnContent().asString();
							 }else{
								  wxres= Request.Get(
										 "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
												 AutoConfig.get("lsid.infowx.appid")+"&secret="+AutoConfig.get("lsid.infowx.secret")+"&code="+request.getParameter("code")+"&grant_type=authorization_code"
										 ).execute().returnContent().asString();								 
							 }
							
							  jn = new ObjectMapper().readTree(wxres);	 
						 }catch(Exception e){
							 e.printStackTrace();
						 }
						 if (jn.get("openid")!=null&&jn.get("access_token")!=null){
							 String infowxid = jn.get("openid").asText();
							 String accessToken = jn.get("access_token").asText();
								 jn = new ObjectMapper().readTree(
										 Request.Get("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken.trim()+"&openid="+infowxid.trim()+"&lang=zh_CN"
												 ).execute().returnContent().asString());
								 if (jn.get("openid")!=null&&jn.get("openid").asText().equals(infowxid.trim())){
								 sql="select count(*) from t_ticketinfo where ticketid=?";
								 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{ticket},Integer.class);
								 long confirmtime=System.currentTimeMillis();
								 if(codecnt==0){
									 sql="select count(*) from t_ticketinfo where isprize<>'1' and openid=? and namespace=?";
									 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject(sql,new Object[]{infowxid,namespace},Integer.class);
									 if(codecnt>0){
										 mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap("select ticketid from t_ticketinfo where isprize<>'1' and  openid=? and namespace=?",new Object[]{infowxid,namespace});
										 String oldticket=(String) mapinfo.get("ticketid");
										 SpringJdbc4mysql.getJdbc("0").update("delete from t_ticketinfo where ticketid=?",oldticket);
									 }	 
								 }
								 //更新ticketinfo 两个字段
								 sql="update t_ticketinfo set confirmtime=? ,openid=? where ticketid=?";
								 SpringJdbc4mysql.getJdbc("0").update(sql,confirmtime,infowxid,ticket);		 
								 openid= jn.get("openid").asText();
								 String nickname= new String(jn.get("nickname").asText().getBytes("iso-8859-1"), "UTF-8");
								 String sex= jn.get("sex").asText();
								 String province= new String(jn.get("province").asText().getBytes("iso-8859-1"), "UTF-8");
								 String city= new String(jn.get("city").asText().getBytes("iso-8859-1"), "UTF-8");
								 String country= new String(jn.get("country").asText().getBytes("iso-8859-1"), "UTF-8");
								 String headimgurl= jn.get("headimgurl").asText();
								 String privilege= jn.get("privilege").asText();
								 try{
									 codecnt=SpringJdbc4mysql.getJdbc("0").queryForObject("select count(*) from t_userinfo where openid=?",new Object[]{openid},Integer.class);
									 if(codecnt>0){
										 Object[] args = new Object[] {URLEncoder.encode(nickname,"utf-8"),sex,province,city,country,headimgurl,privilege,confirmtime,openid};
										 sql="update t_userinfo set nickname=?,sex=?,province=?,city=?,country=?,headimgurl=?,privilege=?,update_at=? where openid=?";
										 SpringJdbc4mysql.getJdbc("0").update(sql, args);
									 }else{
										 try{
											 Object[] args = new Object[] {nickname,sex,province,city,country,headimgurl,privilege,openid,confirmtime,confirmtime};
											 sql="insert into t_userinfo (nickname,sex,province,city,country,headimgurl,privilege,openid,create_at,update_at) values(?,?,?,?,?,?,?,?,?,?)";
											 SpringJdbc4mysql.getJdbc("0").update(sql, args);											 
										 }catch(Exception e){
											 Object[] args = new Object[] {nickname,sex,province,city,country,headimgurl,privilege,openid,confirmtime,confirmtime};
											 sql="insert into t_userinfo (nickname,sex,province,city,country,headimgurl,privilege,openid,create_at,update_at) values(?,?,?,?,?,?,?,?,?,?)";
											 SpringJdbc4mysql.getJdbc("0").update(sql, args);
										 }
									 }
								 }catch(Exception e){
									 e.printStackTrace();
								 }
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
//										 mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap("select isshared from t_ticketinfo where ticketid=?",new Object[]{ticket});
//										 String isshared=(String) mapinfo.get("isshared");
										 if(isshared.equals("0")){
											 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
											 return;
			 
										 }else{
											 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket+"$isshared=1");
											 return;
										 }
										 
									 }
								 	}		 
								 }
						 } else {
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
//									 mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap("select isshared from t_ticketinfo where ticketid=?",new Object[]{ticket});
//									 String isshared=(String) mapinfo.get("isshared");
									 if(isshared.equals("0")){
										 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
										 return;
		 
									 }else{
										 response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket+"$isshared=1");
										 return;
									 }
									 
								 
								 }
							 	}	 
							 }
					 	}
					 	if(namespace.equals("h")){					 		
					 		if(isshared.equals("1")){
					 			response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
					 		}else{					 				
					 			response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
					 		}
					 		
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
//					 				mapinfo=SpringJdbc4mysql.getJdbc("0").queryForMap("select isshared from t_ticketinfo where ticketid=?",new Object[]{ticket});
//					 				String isshared=(String) mapinfo.get("isshared");
					 				if(isshared.equals("0")){
					 					response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket);
					 					return;
					 					
					 				}else{
					 					response.sendRedirect(AutoConfig.get("lsid.res.host")+AutoConfig.get("lsid.active."+namespace)+ticket+"$isshared=1");
					 					return;
					 				}
					 				
					 			}
					 		}					 		
					 	}
				 }
			}else{
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
