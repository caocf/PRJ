package com.huzhouport.integratedquery.action;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.huzhouport.common.util.GlobalVar;

public class Webservice {
	public static final String ILLEGAL_CHECKER = "违章检查器";
	public static final String CUSTOM_CHECKER = "自定义检查器:部海事局签证数据查询";
	public static final String DRAFT_CHECKER = "吃水检查器";
	public static final String CERTIFICATE_CHECKER = "证书检查器";
	public static final String DECK_CHECKER = "套牌检查器";
	public static final String FEES_CHECKER = "规费检查器";
	public static final String BLACKLIST_CHECKER = "黑名单检查器";
	public static final String SIGNATURE_CHECKER = "签证检查器";
	public static final String MONITOR_CHECKER = "重点监管站报告检查器";
	public static final String SHIP_FILE_CHECKER = "船舶档案检查器";
	
	
	private List<?> list;
	private String resultString;
	private String shipName;
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	
	
	
	
	


	public static String huzGetCheckResult(String shipNum) { 

		String result = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
			call.setOperationName("huzGetCheckResult");
			call.setTimeout(GlobalVar.HOST_CONNECT_LONG_TIME);
			Object[] datalist ={ shipNum};
					//new Object[2];
			call.addParameter("shipNum",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);// 接口的参数
			datalist[0] = shipNum;
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			result = (String) call.invoke(datalist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}


	// 输入xml字符串和节点，解析节点下的所有子节点的子节点和该节点的    属性值
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> AnalysisAttributeOfXML(String XmlString) throws Exception {
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeList = RootNode.getChildren("dataList");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		if(NodeList.size()>0){
		List employeeInfo = ((Element) NodeList.get(0)).getChildren();
		for (int j = 0; j < employeeInfo.size(); j++) {
			Map<String, String> map = new HashMap<String, String>();
			int num=((Element) employeeInfo.get(j)).getAttributes().size();
			for(int n = 0; n <num ; n++){
				map.put("filterName",((Element) employeeInfo.get(j)).getAttributeValue("filterName"));
				String alarmLevel=((Element) employeeInfo.get(j)).getAttributeValue("alarmLevel");
				map.put("alarmLevel",alarmLevel);
				if(!alarmLevel.equals("0")){
					List node_detail = ((Element)employeeInfo.get(j)).getChildren();
					node_detail=((Element)node_detail.get(0)).getChildren();
					String warning="";
					for(int m = 0; m <node_detail.size() ; m++){
						if(warning.length()!=0)
							warning+=","+((Element)node_detail.get(m)).getText();
						else
							warning+=((Element)node_detail.get(m)).getText();
					}
					map.put("description",warning);
				}
			}
			list2.add(map);
		}
		}
		return list2;
	}
	//提示或验证是否黑名单等
	public  String CheckShipResult() {
		try {
			List<Map<String, String>> list2=AnalysisAttributeOfXML(huzGetCheckResult(shipName));
			for(int i = 0; i < list2.size(); i++){
				Map<String, String> map=new HashMap<String, String>();
				map=list2.get(i);
				if(map.get("alarmLevel").equals("1")){
					resultString+=map.get("filterName")+"提醒:"+map.get("description")+"。";
				}else if(map.get("alarmLevel").equals("2")){
					resultString+=map.get("filterName")+"告警:"+map.get("description")+"。";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
		
	}
	//提示或验证是否黑名单等
	public static  String CheckShipResultForPrompt(String ShipName) {
		String result = "";
		try {
			List<Map<String, String>> list2=AnalysisAttributeOfXML(huzGetCheckResult(ShipName));
			for(int i = 0; i < list2.size(); i++){
				Map<String, String> map=new HashMap<String, String>();
				map=list2.get(i);
				if(map.get("alarmLevel").equals("1")){
					result+="<font color=#ff9800>"+map.get("filterName")+"提醒:"+map.get("description")+"。</font><br/>";
				}else if(map.get("alarmLevel").equals("2")){
					result+="<font color=red>"+map.get("filterName")+"告警:"+map.get("description")+"。</font><br/>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	//通知公告
	public  String CheckShipResultByList() {
		List<Map<String, String>>  list3= new ArrayList<Map<String, String>>();
		try {
			List<Map<String, String>> list2=AnalysisAttributeOfXML(huzGetCheckResult(shipName));
			for(int i = 0; i < list2.size(); i++){
				Map<String, String> map=new HashMap<String, String>();
				map=list2.get(i);
				if(!map.get("alarmLevel").equals("0")){
					list3.add(map);
				}
			}
			list=list3;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
