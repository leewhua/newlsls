package com.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.http.client.fluent.Request;

public class TestZhifubao {
	public static void main(String[] s) throws Exception{
				
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", "batch_trans_notify");
		params.put("partner", "2088421804393451");
		params.put("_input_charset", "utf-8");
		//params.put("notify_url", "http://商户网关地址/batch_trans_notify-JAVA-UTF-8/notify_url.jsp");
		params.put("email", "971462925@qq.com");
		params.put("account_name", "日加满饮品（上海）有限公司");
		params.put("pay_date", "20160914");
		params.put("batch_no", "20160914001");
		params.put("batch_fee", "1");
		params.put("batch_num", "1");
		params.put("detail_data", "20160914001^13761167551^毛晨辉^1^抽中红包");
		String sign = sign(createLinkString(params), "iqp7m5n5ut78lcrhnv6vou0xogxh16z6","utf-8");
		System.out.println(sign);
		
		String zfburl = "https://mapi.alipay.com/gateway.do?sign_type=MD5&detail_data="+URLEncoder.encode(params.get("detail_data"),"UTF-8")+
				"&account_name="+URLEncoder.encode(params.get("account_name"),"UTF-8")+"&sign="+sign+"&batch_fee="+params.get("batch_fee")+
				"&_input_charset="+params.get("_input_charset")+"&email="+URLEncoder.encode(params.get("email"),"UTF-8")+"&service=batch_trans_notify_no_pwd&pay_date="+params.get("pay_date")+
				"&partner="+params.get("partner")+"&batch_num="+params.get("batch_num")+"&batch_no="+params.get("batch_no");
		System.out.println(zfburl);
		String result = Request.Get(zfburl).execute().returnContent().asString();
		System.out.println(result);
		System.out.println(result.contains("ILLEGAL_PARTNER_EXTERFACE"));
						
	}
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
