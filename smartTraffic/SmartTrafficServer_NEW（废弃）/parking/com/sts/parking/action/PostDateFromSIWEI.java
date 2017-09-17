package com.sts.parking.action;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PostDateFromSIWEI {
	// 输入post请求地址，获取收到的字符串
	public String GetData(String urlPath) {
		String sxml = "";
		URL url;
		try {
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
			while ((c = rd.read()) != -1) {
				temp.append((char) c);
			}
			in.close();
			sxml = temp.toString();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		
		return sxml;
	}
	
	public String GetDataFromThreeNet(String urlPath) {
		String sxml = "";
		URL url;
		try {
			url = new URL(urlPath.trim());
			URLConnection con = url.openConnection();
			con.setConnectTimeout(300000);// 设置连接主机超时（单位：毫秒）
			con.setReadTimeout(300000);// 设置从主机读取数据超时（单位：毫秒）
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("accept", "application/json;gps/gps");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
						
			con.connect();
			StringBuffer temp = new StringBuffer();
			InputStream in = new BufferedInputStream(con.getInputStream());
			Reader rd = new InputStreamReader(in, "UTF-8");
			int c = 0;
			while ((c = rd.read()) != -1) {
				temp.append((char) c);
			}
			in.close();
			sxml = temp.toString();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		
		return sxml;
	}

	
	public List<?> ParkingData(String urlPath) {
		List<?> list = null;
		try {
			String sxml = GetData(urlPath);
			list=jsonStringToList(sxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<?> GetDateByXML(String urlPath) {
		List<?> list = null;
		try {
			String sxml = GetData(urlPath);
			list=AnalysisOfXML(sxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public List<Map<String, String>> jsonStringToList(String rsContent)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			Map<String, String> map = new HashMap<String, String>();
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = jsonObject.get(key).toString();
				map.put(key, value);
			}
			rsList.add(map);
		}
		return rsList;
	}
	// 输入xml字符串和节点，解析节点下的所有子节点和子节点的值
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> AnalysisOfXML(String XmlString) throws Exception {
		
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeList = RootNode.getChildren();
		Element Node = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < NodeList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			Node = (Element) NodeList.get(i);
			List employeeInfo = Node.getChildren();

			for (int j = 0; j < employeeInfo.size(); j++) {
				map.put(((Element) employeeInfo.get(j)).getName(),
						((Element) employeeInfo.get(j)).getText());
			}

			list.add(map);
		}
		return list;
	}
}
