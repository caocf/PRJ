package com.zjport.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class GetDataFromXSJ
{
	// 输入请求地址，获取收到的字符串
	public static String queryData(String urlPath)
	{
		String sxml = "";
		URL url;
		try
		{
			url = new URL(urlPath.trim());
			URLConnection con = url.openConnection();
			con.setConnectTimeout(30000);// 设置连接主机超时（单位：毫秒）
			con.setReadTimeout(30000);// 设置从主机读取数据超时（单位：毫秒）
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			con.connect();
			StringBuffer temp = new StringBuffer();
			InputStream in = new BufferedInputStream(con.getInputStream());
			Reader rd = new InputStreamReader(in, "UTF-8");
			int c = 0;
			while ((c = rd.read()) != -1)
			{
				temp.append((char) c);
			}
			in.close();
			sxml = temp.toString();
		} 
		catch (Exception e)
		{
			System.out.println("queryData error.");
			e.printStackTrace();
		}
		return sxml;
	}
}
