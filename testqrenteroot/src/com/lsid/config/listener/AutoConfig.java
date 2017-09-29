package com.lsid.config.listener;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lsid.mysql.util.SpringJdbc4mysql;
import com.lsid.util.DefaultCipher;

public class AutoConfig implements ServletContextListener{
	
	private static Map<String, String> AUTOCONFIG = new Hashtable<String, String>();
	private static boolean running = true;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		running = false;
		SpringJdbc4mysql.release();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
    	new Thread(new Runnable(){
			@Override
			public void run() {
				while(running){
		    		try {
						Thread.sleep(60*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		if (running){
		    			Set<String> keys = AUTOCONFIG.keySet();
			    		for (String key: keys){
			    			if (running){
			    				try{
				    				String newvalue = getfromdb(key);
				    				if (newvalue!=null&&!newvalue.trim().isEmpty()){
			    						AUTOCONFIG.put(key, newvalue.trim());
			    					} else {
			    						AUTOCONFIG.put(key, "");
					    			}
			    				}catch(Exception ex){
			    					//do nothing
			    				}
			    			}
			    		}
		    		}
		    	}
			}
    		
    	}).start();
    	System.out.println("===="+new Date()+"====started autoconfig");
	}
	
	public static String get(String key){
		String value = AUTOCONFIG.get(key);
		if (value==null){
			try{
				value = getfromdb(key);
			}catch(Exception ex){
				//do nothing
			}
			if (value!=null&&!value.trim().isEmpty()){
				AUTOCONFIG.put(key, value.trim());
			} else {
				AUTOCONFIG.put(key, "");
			}
		}
		return AUTOCONFIG.get(key);
	}
	
	private static String getfromdb(String key) throws Exception{
		String value = SpringJdbc4mysql.getJdbc("0").queryForObject("select configvalue from t_autoconfig where configkey=?", String.class, key);
		if (key.toLowerCase().contains("secret")||key.toLowerCase().contains("key")){
			try{
				value = DefaultCipher.dec(value);
			}catch(Exception ex){
				//do nothing
			}
		}
		return value;
	}
	
}
