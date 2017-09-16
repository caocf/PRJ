package com.huzhouport.integratedquery.action;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.AnalysisData;
import com.huzhouport.common.util.GlobalVar;

public class GetDate extends BaseAction {
	private static final long serialVersionUID = 1L;
	private List<Map<String, String>> list;
	private List<Map<String, String>> jf_warn;// 未缴费信息
	private List<Object[]> jf_nowarn;// 已缴费信息 按月
	private String cbname;
	private String method;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private int scape;// 当前页
	private AnalysisData ad = new AnalysisData();

	public List<?> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public List<Map<String, String>> getJf_warn() {
		return jf_warn;
	}

	public void setJf_warn(List<Map<String, String>> jfWarn) {
		jf_warn = jfWarn;
	}

	public List<Object[]> getJf_nowarn() {
		return jf_nowarn;
	}

	public void setJf_nowarn(List<Object[]> jfNowarn) {
		jf_nowarn = jfNowarn;
	}

	public String getCbname() {
		return cbname;
	}

	public void setCbname(String cbname) {
		this.cbname = cbname;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getScape() {
		return scape;
	}

	public void setScape(int scape) {
		this.scape = scape;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public String GetDateFromPhone() throws Exception {
		int method1 = Integer.parseInt(method);
		// cbname=new String(cbname.getBytes("ISO8859-1"),"UTF-8");
		switch (method1) {
		case 1:// 船舶基本信息
			ShowCBJBXXInfo(cbname);
			break;
		case 2:// 证书
			ShowCBCZXXInfo(cbname);
			break;
		case 3:// 缴费
			ShowCBJFXXInfo(cbname);
			break;
		case 4:// 船检
			ShowCBJYXXInfo(cbname);
			break;
		case 5:// 违章
			ShowCBWZXXInfo(cbname);
			break;
		case 6:// 签证
			ShowCBQZXXInfo(cbname);
			break;
		default:
			break;
		}
		System.out.println("list:" + list);
		return SUCCESS;
	}

	// 船舶基本信息 二期
	public List<?> ShowCBJBXXInfo(String sCBname) throws Exception {
		
		System.out.println("船舶基本信息ShowCBJBXXInfo11111");
		String token = ad.GetToken();// 获取令牌
		
		String urlPath = null;
		sCBname = URLEncoder.encode(sCBname, "UTF-8");
		
		urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
				+ GlobalVar.PORTDATABASE_SERVICECODE
				+ GlobalVar.PORTDATABASE_SERVICECODE_JB + "&name=" + sCBname
				+ "&token=" + token;
		System.out.println("船舶基本信息ShowCBJBXXInfo44444");
		String XmlString = ad.GetPostDataByXML(urlPath,
				GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		list = ad.AnalysisOfXML(XmlString, "record");
		
		return list;
	}

	// 航运企业
	public String GetShipingCompanies() throws Exception {
		String token = ad.GetToken();// 获取令牌
		String urlPath = null;
		String sCBname = URLEncoder.encode(cbname, "UTF-8");
		urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
				+ GlobalVar.PORTDATABASE_SERVICECODE
				+ GlobalVar.PORTDATABASE_SERVICECODE_HYQY + "&name=" + sCBname
				+ "&token=" + token;
		String XmlString = ad.GetPostDataByXML(urlPath,
				GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		// totalPage = ad.GetWarmMessage(XmlString);
		list = ad.AnalysisOfXML(XmlString, "record");
		return SUCCESS;
	}

	// 船舶缴费信息 二期
	public void ShowCBJFXXInfo(String sCBname) throws Exception {
		try {
			String token = ad.GetToken();// 获取令牌
			String urlPath = null;
			scape = scape - 1;
			sCBname = URLEncoder.encode(sCBname, "UTF-8");
			urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH
					+ "?" + GlobalVar.PORTDATABASE_SERVICECODE
					+ GlobalVar.PORTDATABASE_SERVICECODE_JF + "&name="
					+ sCBname + "&token=" + token + "&"
					+ GlobalVar.PORTDATABASE_PAGE + scape;
			String XmlString = ad.GetPostDataByXML(urlPath,
					GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = ad.GetTotalPage(XmlString, "pager");
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			list = ad.AnalysisOfXML(XmlString, "recordset", "record");
			ShowCBJFXXInfoToList();// 数据处理
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 船舶检验信息 二期
	public void ShowCBJYXXInfo(String sCBname) throws Exception {
		try {
			String token = ad.GetToken();// 获取令牌
			String urlPath = null;
			scape = scape - 1;
			sCBname = URLEncoder.encode(sCBname, "UTF-8");
			urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH
					+ "?" + GlobalVar.PORTDATABASE_SERVICECODE
					+ GlobalVar.PORTDATABASE_SERVICECODE_JY + "&name="
					+ sCBname + "&token=" + token + "&"
					+ GlobalVar.PORTDATABASE_PAGE + scape;
			String XmlString = ad.GetPostDataByXML(urlPath,
					GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = ad.GetTotalPage(XmlString, "pager");
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			list = ad.AnalysisOfXML(XmlString, "recordset", "record");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 船舶持证（证书）信息 二期
	public List<?> ShowCBCZXXInfo(String sCBname) throws Exception {
		String token = ad.GetToken();// 获取令牌
		String urlPath = null;
		scape = scape - 1;
		sCBname = URLEncoder.encode(sCBname, "UTF-8");
		urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
				+ GlobalVar.PORTDATABASE_SERVICECODE
				+ GlobalVar.PORTDATABASE_SERVICECODE_CZ + "&name=" + sCBname
				+ "&token=" + token + "&" + GlobalVar.PORTDATABASE_PAGE + scape;
		String XmlString = ad.GetPostDataByXML(urlPath,
				GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = ad.GetTotalPage(XmlString, "pager");
		totalPage = map.get("pages");
		allTotal = map.get("allTotal");
		list = ad.AnalysisOfXML(XmlString, "recordset", "record");
		list = ad.OrderByZSXX(list);
		return list;
	}

	// 船舶缴费信息 二期的数据处理
	@SuppressWarnings("unchecked")
	public void ShowCBJFXXInfoToList() throws Exception {
		jf_warn = new ArrayList<Map<String, String>>();
		jf_nowarn = new ArrayList<Object[]>();

		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).get("YXQZ").split("-")[0].equals("1900")) {
				if (list.get(i).get("YJZE").equals(list.get(i).get("SJZE"))
						&& i != 1 && i != 2) {
					int a = 0;
					String time = list.get(i).get("YXQZ").split("-")[0] + "年"
							+ list.get(i).get("YXQZ").split("-")[1];
					// 未欠费
					for (int j = 0; j < jf_nowarn.size(); j++) {
						if (time.equals(jf_nowarn.get(j)[0].toString())) {
							a = 1;
							Object[] listInfo = new Object[2];
							listInfo[0] = time;
							listInfo[1] = jf_nowarn.get(j)[1];
							((List<Map<String, String>>) listInfo[1]).add(list
									.get(i));
							jf_nowarn.set(j, listInfo);
							break;
						}
					}
					// 不重复
					if (a == 0) {
						Object[] listInfo = new Object[2];
						listInfo[0] = time;
						List<Map<String, String>> l = new ArrayList<Map<String, String>>();
						l.add(list.get(i));
						listInfo[1] = l;
						jf_nowarn.add(listInfo);
					}
				} else {
					// 欠费
					jf_warn.add(list.get(i));
				}
			} else {
				allTotal--;
			}
		}

	}

	// 船舶签证信息 二期
	public List<?> ShowCBQZXXInfo(String sCBname) throws Exception {
		String token = ad.GetToken();// 获取令牌
		String urlPath = null;
		scape = scape - 1;
		sCBname = URLEncoder.encode(sCBname, "UTF-8");
		urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
				+ GlobalVar.PORTDATABASE_SERVICECODE
				+ GlobalVar.PORTDATABASE_SERVICECODE_QZ + "&name=" + sCBname
				+ "&token=" + token + "&" + GlobalVar.PORTDATABASE_PAGE + scape;
		String XmlString = ad.GetPostDataByXML(urlPath,
				GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		Map<String, Integer> map = new HashMap<String, Integer>();
		System.out.println("urlPath:" + urlPath);
		map = ad.GetTotalPage(XmlString, "pager");
		totalPage = map.get("pages");
		allTotal = map.get("allTotal");
		/* list = ad.AnalysisOfXML(XmlString,"recordset","record"); */
		/* 倒叙签证 */
		if (totalPage == 1) {
			scape = totalPage - scape - 1;
			urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH
					+ "?" + GlobalVar.PORTDATABASE_SERVICECODE
					+ GlobalVar.PORTDATABASE_SERVICECODE_QZ + "&name="
					+ sCBname + "&token=" + token + "&"
					+ GlobalVar.PORTDATABASE_PAGE + scape;
			XmlString = ad.GetPostDataByXML(urlPath,
					GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		}
		list = ad.AnalysisOfXMLReverse(XmlString, "recordset", "record");
		return list;
	}

	// 船舶违章信息 二期
	public List<?> ShowCBWZXXInfo(String sCBname) throws Exception {
		String token = ad.GetToken();// 获取令牌
		String urlPath = null;
		sCBname = URLEncoder.encode(sCBname, "UTF-8");
		scape = scape - 1;
		urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
				+ GlobalVar.PORTDATABASE_PAGE + scape + "&"
				+ GlobalVar.PORTDATABASE_SERVICECODE
				+ GlobalVar.PORTDATABASE_SERVICECODE_WZ + "&name=" + sCBname
				+ "&token=" + token;
		String XmlString = ad.GetPostDataByXML(urlPath,
				GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = ad.GetTotalPage(XmlString, "pager");
		totalPage = map.get("pages");
		allTotal = map.get("allTotal");
		list = ad.AnalysisOfXML(XmlString, "recordset", "record");
		System.out.println(list);
		return list;
	}

	/**
	 * 查询船舶违章信息
	 * 
	 * @return
	 */
	public String queryVoilateShip() {

		String result = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
			call.setOperationName("huzGetVoilate");
			call.setTimeout(GlobalVar.HOST_CONNECT_LONG_TIME);
			Object[] datalist = new Object[1];
			call.addParameter("shipNum",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);// 接口的参数
			datalist[0] = cbname;

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			result = (String) call.invoke(datalist);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			
			list = AnalysisAttributeOfXML(result);
		} catch (Exception e) {
			
			System.out.println(e);
		}

		return "success";
	}

	public static List<Map<String, String>> AnalysisAttributeOfXML(
			String XmlString) throws Exception {
		Reader in = new StringReader(XmlString);
		Document document = new SAXBuilder().build(in);
		Element RootNode = document.getRootElement();
		List NodeList = RootNode.getChildren("dataList");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		if (NodeList.size() > 0) {
			List datalist = ((Element) NodeList.get(0)).getChildren();
			for (int j = 0; j < datalist.size(); j++) {
				Map<String, String> map = new HashMap<String, String>();

				List datadetail = ((Element) datalist.get(j)).getChildren();

				for (int m = 0; m < datadetail.size(); m++) {

					map.put(((Element) datadetail.get(m)).getName(),
							((Element) datadetail.get(m)).getText());

				}

				list2.add(map);
			}
		}
		return list2;
	}

}
