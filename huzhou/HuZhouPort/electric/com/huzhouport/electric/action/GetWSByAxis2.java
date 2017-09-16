package com.huzhouport.electric.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.huzhouport.common.util.AnalysisData;
import com.huzhouport.common.util.GlobalVar;

public class GetWSByAxis2 {
	
	private static AnalysisData ad=new AnalysisData();
	public static String sendXmlByhuzGetDQShip(int iConnectTimeout, int iReadTimeout) throws Exception {

		String result = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
			call.setOperationName("huzGetDQShip");// WSDL里面描述的接口名称
			call.setTimeout(iConnectTimeout);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			result = (String) call.invoke(new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*@Test
	public static String sendSms(int iConnectTimeout, int iReadTimeout)
			throws Exception {
		String result = null;
		try {

			// 直接引用远程的wsdl文件
			// 以下都是套路
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTimeout(iConnectTimeout);
			call.setTargetEndpointAddress(endpoint);
			call.setOperation("huzGetDQShip");
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			// 给方法传递参数，并且调用方法
			SOAPEnvelope env = new SOAPEnvelope();
            SOAPBodyElement sbe = new SOAPBodyElement(XMLUtils.StringToElement(endpoint2, "tns:huzGetDQShip", ""));
            env.addBodyElement(sbe);
       
            System.out.println("\n============= Request ==============");
            XMLUtils.PrettyElementToStream(env.getAsDOM(), System.out);

            call.invoke(env);

            MessageContext mc = call.getMessageContext();
            System.out.println("\n============= Response ==============");
            XMLUtils.PrettyElementToStream((Element) mc.getResponseMessage().getSOAPEnvelope().getAsDOM().getChildNodes(), System.out);
            mc.getResponseMessage().getSOAPEnvelope().getAsDocument();
			System.out.println("result is " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static void Main() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < 9; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("nodeName", "in" + i);
			if(i==0){
				map.put("nodeVale", "测试");
			}else if(i==4){
				map.put("nodeVale", "157458");
			}else if(i==8){
				map.put("nodeVale","299");
			}else{
				map.put("nodeVale", String.valueOf(i + 1));
			}
			
			list.add(map);
		}
		try {
			sendXmlByVoilate(list, GlobalVar.HOST_CONNECT_TIME,
					GlobalVar.HOST_READ_LONG_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void Main2() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i <7; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("nodeName", "in" + i);
			if(i==0){
				map.put("nodeVale", "测试");
			}else if(i==2){
				map.put("nodeVale", "5");
			}else if(i==4){
				map.put("nodeVale","166");
			}else if(i==5){
				map.put("nodeVale","167");
			}else if(i==6){
				map.put("nodeVale","299");
			}else{
				map.put("nodeVale", String.valueOf(i + 1));
			}
			
			list.add(map);
		}
		try {
			sendXmlByShipReport(list, GlobalVar.HOST_CONNECT_TIME,
					GlobalVar.HOST_READ_LONG_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public static Boolean ShipVoilate(List<Object[]> list_ill,String evi) {
		Boolean ispost=false;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i <9; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("nodeName", "in" + i);
			/*if(i==0){
				map.put("nodeVale", "测试");
			}else*/ if(i==7){
				map.put("nodeVale", evi);
			}else if(i==8){
				if(list_ill.get(0)[7]!=null){
				map.put("nodeVale", list_ill.get(0)[7].toString());
				}else{
					return false;
				}
			}else{
				map.put("nodeVale", list_ill.get(0)[i].toString());
			}
			
			list.add(map);
		}
		try {
			String resultXml=sendXmlByVoilate(list, GlobalVar.HOST_CONNECT_TIME,GlobalVar.HOST_READ_LONG_TIME);
			if(resultXml!=null){
				ispost=ad.GetOperationByWebservice(resultXml,GlobalVar.WEBSERVICE_STATUS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ispost;
	}

	public static String sendXmlByVoilate(List<HashMap<String, String>> list,
			int iConnectTimeout, int iReadTimeout) throws Exception {

		String result = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
			call.setOperationName("huzAddVoilate");// WSDL里面描述的接口名称
			Object[] datalist = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				call.addParameter(list.get(i).get("nodeName"),
						org.apache.axis.encoding.XMLType.XSD_DATE,
						javax.xml.rpc.ParameterMode.IN);// 接口的参数
				datalist[i] = list.get(i).get("nodeVale");

			}
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			result = (String) call.invoke(datalist);
			// 给方法传递参数，并且调用方法
			//System.out.println("result is " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String sendXmlByShipReport(List<HashMap<String, String>> list,
			int iConnectTimeout, int iReadTimeout) throws Exception {

		String result = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
			call.setOperationName("huzAddShipReport");// WSDL里面描述的接口名称
			Object[] datalist = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				call.addParameter(list.get(i).get("nodeName"),
						org.apache.axis.encoding.XMLType.XSD_DATE,
						javax.xml.rpc.ParameterMode.IN);// 接口的参数
				datalist[i] = list.get(i).get("nodeVale");

			}
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			result = (String) call.invoke(datalist);
			// 给方法传递参数，并且调用方法
			System.out.println("result is " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
