package com.huzhouport.integratedquery.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class StreamController {
	/**
	* 图书显示获取post流
	* */
	@RequestMapping(value = "getStreamPost", method = RequestMethod.POST)
	public void getStreamPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
	StringBuffer info= new StringBuffer();
	InputStream in;
	try {
	in = request.getInputStream();
	BufferedInputStream buf=new BufferedInputStream(in);
	byte[] buffer=new byte[1024]; 
	int iRead;
	while((iRead=buf.read(buffer))!=-1) 
	{
	info.append(new String(buffer,0,iRead,"UTF-8"));
	}	
	response.setCharacterEncoding("utf-8");
	response.getWriter().println(info);
	System.out.println(info);
	} catch (IOException e) {
	e.printStackTrace();
	}

	}	

	@RequestMapping(value = "setStreamPost", method = RequestMethod.POST)
	public void setStreamPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
	StringBuffer info= new StringBuffer();
	OutputStream out;
	InputStream in;
	try {
	  URL requestUrl = new URL("http://adm/xxx/advance");
	HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(5*1000);	
	conn.setDoOutput(true);
	conn.setDoInput(true);
	conn.setRequestProperty("Cache-Control", "no-cache");
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
	String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	data += "<book><content><![CDATA[111]]></content></book>";
	byte[] byteStream = data.getBytes("utf-8");
	conn.setRequestProperty("Content-Length", byteStream.length+"");
	out = conn.getOutputStream();
	out.write(byteStream);	
	out.flush();
	out.close();
	//获取返回来的值
	in = conn.getInputStream();
	BufferedInputStream buff=new BufferedInputStream(in);
	byte[] buffer=new byte[1024]; 
	int iRead;
	while((iRead=buff.read(buffer))!=-1) 
	{
	info.append(new String(buffer,0,iRead,"utf-8"));
	}
	System.out.println(info);
	response.setCharacterEncoding("utf-8");
	response.getWriter().println(info);
	System.out.println("-------------");

	conn.disconnect();
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}

	}	



}
