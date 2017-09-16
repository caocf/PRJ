package net.hxkg.ship;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;

public class XML2Json 
{ 
	    /** 
	     * 转换一个xml格式的字符串到json格式 
	     *  
	     * @param xml 
	     *            xml格式的字符串 
	     * @return 成功返回json 格式的字符串;失败反回null 
	     */  
	    @SuppressWarnings("unchecked")  
	    public static  String xml2JSON(String xml) 
	    {  
	        JSONObject obj = new JSONObject();  
	        try {  
	            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));  
	            SAXBuilder sb = new SAXBuilder();  
	            Document doc = sb.build(is);  
	            Element root = doc.getRootElement();  
	            obj.put(root.getName(), iterateElement(root));  
	            return obj.toString();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * 转换一个xml格式的字符串到json格式 
	     *  
	     * @param file 
	     *            java.io.File实例是一个有效的xml文件 
	     * @return 成功反回json 格式的字符串;失败反回null 
	     */  
	    @SuppressWarnings("unchecked")  
	    public static String xml2JSON(File file) {  
	        JSONObject obj = new JSONObject();  
	        try {  
	            SAXBuilder sb = new SAXBuilder();  
	            Document doc = sb.build(file);  
	            Element root = doc.getRootElement();  
	            obj.put(root.getName(), iterateElement(root));  
	            return obj.toString();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * 一个迭代方法 
	     *  
	     * @param element 
	     *            : org.jdom.Element 
	     * @return java.util.Map 实例 
	     */  
	    @SuppressWarnings("unchecked")  
	    private static Map  iterateElement(Element element) {  
	        List jiedian = element.getChildren();  
	        Element et = null;  
	        Map obj = new HashMap();  
	        List list = null;  
	        for (int i = 0; i < jiedian.size(); i++) {  
	            list = new LinkedList();  
	            et = (Element) jiedian.get(i);  
	            if (et.getTextTrim().equals("")) {  
	                if (et.getChildren().size() == 0)  
	                    continue;  
	                if (obj.containsKey(et.getName())) {  
	                    list = (List) obj.get(et.getName());  
	                }  
	                list.add(iterateElement(et));  
	                obj.put(et.getName(), list);  
	            } else {  
	                if (obj.containsKey(et.getName())) {  
	                    list = (List) obj.get(et.getName());  
	                }  
	                list.add(et.getTextTrim());  
	                obj.put(et.getName(), list);  
	            }  
	        }  
	        return obj;  
	    }
	    
	    public static List<Map<String, String>> AnalysisOfXML(String XmlString,
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
	    
	    public static List<Map<String, String>> AnalysisOfXML(String XmlString,
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
}
