package com.wanpg.together.net;


import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequest {

	public static final String API_URL = "http://121.40.190.228/";
//    public static final String API_URL = "http://192.168.0.104:8080/PikeReaderServer/api";
	
	public static final String API_SECRET_KEY = "U25vd1BlYXJNb25leUdvdA==";
	
	public static final String API_TRAVEL_LIST = "post/search";

	public static final String API_NEW_TRAVEL = "post/insert";

    public static final String API_MESSAGE_LIST = "message/search";

	public static final String API_COMMENT= "comment/insert";

	public static final String API_USER_INFO = "user/search";

	public static final String API_JOIN_TRAVEL = "post/join";
	public static final String API_EXIT_TRAVEL = "post/unjoin";

	public static final String API_GET_MY_TRAVEL = "post/find_my_posts";
	
	public static String getApi(String method, HashMap<String, Object> param){

		Iterator iterator = param.entrySet().iterator();
		String paramStr = "";
		while (iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			String key = entry.getKey();
			Object obj = entry.getValue();
			if(obj!=null){
				String objStr = obj.toString();
				if(!TextUtils.isEmpty(objStr)){
					paramStr =  paramStr + key + "=" + obj.toString() + "&";
				}
			}
		}
		
//		String secret = MD5Util.string2MD5(MD5Util.string2MD5(paramStr) + API_SECRET_KEY);
		
		return API_URL + method + "?" + paramStr;
	}
	
	public static HttpResult get(String urlStr){
		HttpResult hr = new HttpResult();
		InputStream ins = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        // Post 请求不能使用缓存
	        connection.setUseCaches(true);
            connection.setAllowUserInteraction(false);
            // 设置请求方式，默认为GET
            connection.setRequestMethod("GET");
	        ins = connection.getInputStream();
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int len = -1;
            while ((len = ins.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
            }
            byte[] b = outstream.toByteArray();
            outstream.close();
			hr.setResult(b);
			hr.setStatus(HttpResult.SUCCESS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ins!=null) {
					ins.close();
					ins = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hr;
	}

	
	public static String post(String urlStr){
//		String result = "";
//		try {
//			URL url = new URL(urlStr);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	        connection.setDoOutput(true);                 
//	        connection.setDoInput(true); 
//	        // 设置请求方式，默认为GET 
//	        connection.setRequestMethod("POST"); 
//	        // Post 请求不能使用缓存 
//	        connection.setUseCaches(false); 
//	        InputStream ins = connection.getInputStream();
//	        
//	        byte[] b = new byte[ins.available()];
//	        ins.read(b);
//	        
//	        String 
//	        
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "";
	}
	
	
	public static void readContentFromPost(String POST_URL) throws IOException { 

        // Post请求的url，与get不同的是不需要带参数 

        URL postUrl = new URL(POST_URL); 

        // 打开连接 

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection(); 

        //打开读写属性，默认均为false 

        connection.setDoOutput(true);                 

 connection.setDoInput(true); 

        // 设置请求方式，默认为GET 

        connection.setRequestMethod(" POST "); 

        // Post 请求不能使用缓存 

        connection.setUseCaches(false); 

        // URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。 

        // connection.setFollowRedirects(true); 

        //URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数 

        connection.setInstanceFollowRedirects(true); 

        // 配置连接的Content-type，配置为application/x- www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码 

        connection.setRequestProperty(" Content-Type ", 

                        " application/x-www-form-urlencoded "); 

        // 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成， 

        // 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略 

        //connection.connect(); 

        DataOutputStream out = new DataOutputStream(connection 

                        .getOutputStream()); 

        //正文内容其实跟get的URL中'?'后的参数字符串一致 

        String content = " firstname= "+URLEncoder.encode(" 一个大肥人 ", " utf-8 "); 

        // DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面 

        out.writeBytes(content); 

        out.flush(); 

        out.close(); // flush and close 

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 

        String line; 

        System.out.println(" ============================= "); 

        System.out.println(" Contents of post request "); 

        System.out.println(" ============================= "); 

        while ((line = reader.readLine()) != null) { 

                System.out.println(line); 

        } 

        System.out.println(" ============================= "); 

        System.out.println(" Contents of post request ends "); 

        System.out.println(" ============================= "); 

        reader.close(); 

        //connection.disconnect(); 

 } 

	
}
