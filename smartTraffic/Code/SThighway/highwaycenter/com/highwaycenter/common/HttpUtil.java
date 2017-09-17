package com.highwaycenter.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {
	public static String sendPost(String url, String param) throws Exception {
		String result = "";
		try {
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Charset", "utf-8");
			PrintWriter out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(),"utf-8"));
			out.print(param);
			out.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {

			System.out.println("没有结果！" + e);
            throw e;  
		}
		
		return result;
	}
	
	
	public static String sendGet(String url) throws Exception  {
		String result = "";
		try {
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection();
			httpConn.setConnectTimeout(5*1000);
			httpConn.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {

			System.out.println("没有结果！" + e);
            throw e;
		}
		
		return result;
	}
}
