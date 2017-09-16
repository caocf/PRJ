package com.visionagent.utils;

/*@author 沈丹丹
 * 接收信息
 *并解析
 *第二期
 * */

import com.visionagent.utils.xml.XMLObject;
import com.visionagent.utils.xml.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AnalysisData {
	private final static String SimpleDateFormat = "yyyy-MM-dd HH:mm:ss";
	private final static Logger log = LoggerFactory.getLogger(AnalysisData.class);
	// 得到令牌
	@SuppressWarnings("unchecked")
	public String GetToken() throws Exception 
	{
		
		
		String urlPath = null;

			urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_LOGIN
					+ "?" + GlobalVar.PORTDATABASE_LOGIN_USER;
			String token = GetToken(urlPath, GlobalVar.HOST_CONNECT_TIME,
					GlobalVar.HOST_READ_TIME);

		return token;
	}

	// 从服务端得到令牌
	@SuppressWarnings("unchecked")
	public String GetToken(String urlPath, int iConnectTimeout, int iReadTimeout)
			throws Exception {
		URL url = new URL(urlPath.trim());
		URLConnection con = url.openConnection();
		
		con.setConnectTimeout(iConnectTimeout);// 设置连接主机超时（单位：毫秒）
		con.setReadTimeout(iReadTimeout);// 设置从主机读取数据超时（单位：毫秒）
		con.setDoOutput(true);
		//con.setRequestProperty("Pragma:", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		
		con.setRequestProperty("Content-Type", "text/xml");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con
				.getInputStream()));
		
		String line = "";
		String sxml = "";
		for (line = br.readLine(); line != null; line = br.readLine()) {
			sxml = sxml + line;
		}
		XMLObject xml =  XMLUtils.xml2Map(sxml);
		XMLObject result = xml.getXMLObject("result");
		XMLObject record = result.getXMLObject("record");
		String token = String.valueOf(record.getXMLObject("token").get("value"));
		return token;
	}


	public String GetPostDataByXML(String urlPath, int iConnectTimeout,
			int iReadTimeout) throws Exception {
		URL url = new URL(urlPath.trim());
		URLConnection con = url.openConnection();
		con.setConnectTimeout(iConnectTimeout);// 设置连接主机超时（单位：毫秒）
		con.setReadTimeout(iReadTimeout);// 设置从主机读取数据超时（单位：毫秒）
		con.setDoOutput(true);
		//con.setRequestProperty("Pragma:", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");

		StringBuffer temp = new StringBuffer();
		InputStream in = new BufferedInputStream(con.getInputStream());
		Reader rd = new InputStreamReader(in, "UTF-8");
		String sxml = "";
		int c = 0;
		while ((c = rd.read()) != -1) {
			temp.append((char) c);
		}
		in.close();
		sxml = temp.toString();// 得到xml
		return sxml;
	}

	// 输入xml字符串和节点，解析节点下的所有子节点和子节点的值
	public List<XMLObject> AnalysisOfXML(String XmlString,
			String NodeName) throws Exception {

		XMLObject xml =  XMLUtils.xml2Map(XmlString);
		Object o = xml.recursionValue(NodeName);
		if(o instanceof List){
			return (List<XMLObject>) o;
		}else{
			List<XMLObject> list = new ArrayList<>();
			if(o != null){
				list.add((XMLObject) o);
			}
			return list;
		}
	}
}
