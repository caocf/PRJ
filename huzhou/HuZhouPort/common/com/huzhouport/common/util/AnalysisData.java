package com.huzhouport.common.util;

/*@author 沈丹丹
 * 接收信息
 *并解析
 *第二期
 * */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import com.opensymphony.xwork2.ActionContext;

public class AnalysisData {
	private final static String SimpleDateFormat = "yyyy-MM-dd HH:mm:ss";
	// 得到令牌
	@SuppressWarnings("unchecked")
	public String GetToken() throws Exception 
	{
		
		
		Map session = ActionContext.getContext().getSession();
		String token = (String) session.get("token");// 获取令牌
		String urlPath = null;
		System.out.println(token);
		if (token == null) {
			
			urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_LOGIN
					+ "?" + GlobalVar.PORTDATABASE_LOGIN_USER;
			token = GetToken(urlPath, GlobalVar.HOST_CONNECT_TIME,
					GlobalVar.HOST_READ_TIME);
		}
		
		return token;
	}

	// 从服务端得到令牌
	@SuppressWarnings("unchecked")
	public String GetToken(String urlPath, int iConnectTimeout, int iReadTimeout)
			throws Exception {
		URL url = new URL(urlPath.trim());
		System.out.println(url);
		URLConnection con = url.openConnection();
		
		con.setConnectTimeout(iConnectTimeout);// 设置连接主机超时（单位：毫秒）
		System.out.println("船舶基本信息ShowCBJBXXInfo2222");
		con.setReadTimeout(iReadTimeout);// 设置从主机读取数据超时（单位：毫秒）
		System.out.println("船舶基本信息ShowCBJBXXInfo3333");
		con.setDoOutput(true);
		System.out.println("船舶基本信息ShowCBJBXXInfo4444");
		//con.setRequestProperty("Pragma:", "no-cache");
		System.out.println("船舶基本信息ShowCBJBXXInfo5555");
		con.setRequestProperty("Cache-Control", "no-cache");
		
		con.setRequestProperty("Content-Type", "text/xml");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con
				.getInputStream()));
		
		String line = "";
		String sxml = "";
		for (line = br.readLine(); line != null; line = br.readLine()) {
			sxml = sxml + line;
		}
		System.out.println(sxml);
		Reader in = new StringReader(sxml);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeList = RootNode.getChildren("record");
		List TokenList = ((Element) NodeList.get(0)).getChildren("token"); 
		String token = ((Element) TokenList.get(0)).getText();
		Map session = ActionContext.getContext().getSession();
		session.put("token", token);
		return token;
	}

	// 输入post请求地址，获取收到的字符串
	public String GetPostDataByXML(String urlPath) throws Exception {
		URL url = new URL(urlPath.trim());
		URLConnection con = url.openConnection();
		con.setConnectTimeout(30000);// 设置连接主机超时（单位：毫秒）
		con.setReadTimeout(30000);// 设置从主机读取数据超时（单位：毫秒）
		con.setDoOutput(true);
		//con.setRequestProperty("Pragma:", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Content-Type", "text/xml");

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
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> AnalysisOfXML(String XmlString,
			String NodeName) throws Exception {
		
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeList = RootNode.getChildren(NodeName);
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

	// 输入xml字符串和节点，解析节点下的所有子节点的子节点和该节点的值
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> AnalysisOfXML(String XmlString,
			String NodeName, String cNode) throws Exception {
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeParent = RootNode.getChildren(NodeName);

		List NodeList = ((Element) NodeParent.get(0)).getChildren(cNode);
		Element Node = null;
		// if (NodeList.size() > 0) {
		for (int i = 0; i < NodeList.size(); i++) {
			Node = (Element) NodeList.get(i);
			List employeeInfo = Node.getChildren();
			map = new HashMap<String, String>();
			for (int j = 0; j < employeeInfo.size(); j++) {
				map.put(((Element) employeeInfo.get(j)).getName(),
						((Element) employeeInfo.get(j)).getText());

			}
			list.add(map);
		}
		/*
		 * } else { Map<String, String> map2 = map = new HashMap<String,
		 * String>(); map2.put("result", "没有相关信息"); list.add(map2); }
		 */
		return list;
	}
	// 输入xml字符串和节点，解析节点下的所有子节点的子节点和该节点的值   倒叙
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> AnalysisOfXMLReverse(String XmlString,
			String NodeName, String cNode) throws Exception {
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeParent = RootNode.getChildren(NodeName);

		List NodeList = ((Element) NodeParent.get(0)).getChildren(cNode);
		Element Node = null;
		// if (NodeList.size() > 0) {
		for (int i = NodeList.size()-1; i >=0; i--) {
			Node = (Element) NodeList.get(i);
			List employeeInfo = Node.getChildren();
			map = new HashMap<String, String>();
			for (int j = 0; j < employeeInfo.size(); j++) {
				map.put(((Element) employeeInfo.get(j)).getName(),
						((Element) employeeInfo.get(j)).getText());

			}
			list.add(map);
		}
		/*
		 * } else { Map<String, String> map2 = map = new HashMap<String,
		 * String>(); map2.put("result", "没有相关信息"); list.add(map2); }
		 */
		return list;
	}


	// 输入xml字符串和节点，解析节点下的所有子节点的子节点和该节点的值
	@SuppressWarnings("unchecked")
	public List<?> AnalysisOfXML3(String XmlString, String NodeName,
			String cNode, String ccNode) throws Exception {
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeParent = RootNode.getChildren(NodeName);

		List cNodeList = ((Element) NodeParent.get(0)).getChildren(cNode);
		List NodeList = ((Element) cNodeList.get(0)).getChildren(ccNode);
		// if (NodeList.size() > 0) {
		Element Node = null;
		map = new HashMap<String, String>();
		map.put("result", "");
		list.add(map);
		for (int i = 0; i < NodeList.size(); i++) {
			Node = (Element) NodeList.get(i);
			List employeeInfo = Node.getChildren();
			map = new HashMap<String, String>();
			for (int j = 0; j < employeeInfo.size(); j++) {
				map.put(((Element) employeeInfo.get(j)).getName(),
						((Element) employeeInfo.get(j)).getText());

			}
			list.add(map);
		}

		/*
		 * } else { Map<String, String> map2 = map = new HashMap<String,
		 * String>(); map2.put("result", "没有相关信息"); list.add(map2); }
		 */
		return list;
	}

	public String AnalysisNodeText(String sxmlString, String snode,
			String schildnode) throws Exception {
		Reader in = new StringReader(sxmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List<?> NodeList = RootNode.getChildren(snode);
		List<?> cnodeList = ((Element) NodeList.get(0)).getChildren(schildnode);
		String snodetext = ((Element) cnodeList.get(0)).getText();
		return snodetext;
	}

	// 条数页码计算
	public Map<String, Integer> GetTotalPage(String XmlString, String nodeName) throws Exception {
		List<Map<String, String>> listByPage = AnalysisOfXML(XmlString, nodeName);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (listByPage.size() > 0) {
			int total = Integer.parseInt(listByPage.get(0).get("total"));
			int pageSize = Integer.parseInt(listByPage.get(0).get("pageSize"));
			int totalPage = total / pageSize + 1;
			map.put("allTotal", total);
			map.put("pages", totalPage);
		}
		return map;
	}

	// 航运企业
	public Integer GetWarmMessage(String XmlString) throws Exception {
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List<?> NodeParent =RootNode.getChildren("returnCode");
		int returnCode = Integer.parseInt(((Element)NodeParent.get(0)).getText());	
		return returnCode;
	}
	// WEBSERVICE
	public Boolean GetOperationByWebservice(String XmlString,String node) throws Exception {
		Boolean check=false;
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List<?> NodeParent =RootNode.getChildren(node);
		String returnCode = ((Element)NodeParent.get(0)).getText();	
		if(returnCode.equals(GlobalVar.WEBSERVICE_SUCCESS)){
			check=true;	
		}
		return check;
	}
	// json字符串转
	private static String diskListContent = "[{\"n1\":\"asd\",\"n2\":22,\"n3\":\"45.40GB\","
			+ "\"n4\":\"qwerty\",\"n5\":\"asd\",},"
			+ "{\"n1\":\"local\","
			+ "\"n2\":1,\"n3\":\"279.40GB\",\"n4\":\"ST3300656SS\",\"n5\":\"\\/devm\\/d0\"}]";

	/***
	 * json字符串转java List
	 * 
	 * @param rsContent
	 * @return
	 * @throws Exception
	 */
	private static List<Map<String, String>> jsonStringToList(String rsContent)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		System.out.println("json字符串内容如下");
		System.out.println(arry);
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

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<Map<String, String>> list1 = jsonStringToList(diskListContent);
		System.out.println("json字符串成map");
		for (Map<String, String> m : list1) {
			System.out.println(m);
		}
		System.out.println("map转换成json字符串");
		for (Map<String, String> m : list1) {
			JSONArray jsonArray = JSONArray.fromObject(m);
			System.out.println(jsonArray.toString());
		}
		System.out.println("list转换成json字符串");
		JSONArray jsonArray2 = JSONArray.fromObject(list1);
		System.out.println(jsonArray2.toString());
	}

	// json字符串成map
	public Map<String, String> JsonToMap(String list) throws Exception {
		List<Map<String, String>> list1 = jsonStringToList(list);
		Map<String, String> map = null;
		for (Map<String, String> m : list1) {
			System.out.println(m);
			map = new HashMap<String, String>();
			map.putAll(m);
		}
		return map;
	}

	// map转换成json字符串
	public String MapToJson(String list) throws Exception {
		List<Map<String, String>> list1 = jsonStringToList(list);
		JSONArray jsonArray = null;
		for (Map<String, String> m : list1) {
			jsonArray = JSONArray.fromObject(m);
			System.out.println(jsonArray.toString());
		}
		return jsonArray.toString();
	}

	// list转换成json字符串
	public String ListToJson(List<?> list) throws Exception {
		JSONArray jsonArray2 = JSONArray.fromObject(list);
		return jsonArray2.toString();
	}
	public List<Map<String, String>> OrderByZSXX(List<Map<String, String>> list) throws Exception {
		int listsize=list.size();
		for (int i = 0; i < listsize; i++) {
			if (list.get(i).get("YXRQ").length() != 0) {
				if (distanceTime(list.get(i).get("YXRQ")) < 0) {
					list.get(i).put("outtime", "1");
				} else{
					list.get(i).put("outtime", "2");
				}
					
			}
			else
				list.get(i).put("outtime", "1");
		}
		
		for (int i = 0; i < listsize - 1; i++) {
			for (int j = i + 1; j < listsize; j++) {
				String i_yxrq=list.get(i).get("yxrq");
				String j_yxrq=list.get(j).get("YXRQ");
				if(i_yxrq.length()==0&&j_yxrq.length()!=0){
					Map<String, String> map = new HashMap<String, String>();
					map.putAll(list.get(i));
					list.get(i).putAll(list.get(j));
					list.get(j).putAll(map);	
				}
				if(i_yxrq.length()!=0&&j_yxrq.length()!=0){
				     long beteen=distanceBetweenCurren(i_yxrq,j_yxrq);
					if (beteen<0) {
						Map<String, String> map = new HashMap<String, String>();
						map.putAll(list.get(i));
						list.get(i).putAll(list.get(j));
						list.get(j).putAll(map);						
					}
				}
			}
		}
		return list;
	}
	// 2个时间比较
	public static long distanceBetweenCurren(String time1, String time2) {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat(SimpleDateFormat, Locale.getDefault())
					.parse(time1);
			date2 = new SimpleDateFormat(SimpleDateFormat, Locale.getDefault())
					.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (date2.getTime() - date1.getTime()) / 1000;

	}
	// 时间和系统时间比较
	public static long distanceTime(String time1) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat(SimpleDateFormat, Locale.getDefault())
					.parse(time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (System.currentTimeMillis() - date1.getTime()) / 1000;

	}
}
