package com.huzhouport.common.tool;
/*
 * 获取码头接口
 * 沈丹丹
 * 通过webservice，经纬度，距离获取数据
 * 如果没有数据则通过后台接口获取数据库数据 并模糊搜索码头名称
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.serialization.SoapObject;

import com.huzhouport.common.util.Query;

public class WebServiceInterface {
	public static final String METHODNAME_PORTINFO = "portinfo";
	public static final String METHODNAME_GETPORT = "getport";

	public static List<Port> GetPortFormWs(String x, String y, int range,String mc) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("x", x);
		param.put("y", y);
		param.put("range", range);
		SoapObject result = (SoapObject) WebServiceOper.CallWebService(
				WebServiceOper.URL, WebServiceOper.NAMESPACE,
				METHODNAME_GETPORT, param); // 获得getportResponse
		List<Port> l_port = new ArrayList<Port>();
		if (result != null) {
			l_port = initDate(result.getProperty(0).toString());

		} else {
			System.out.println("从数据库里取数据");
			// 从数据库里取数据
			l_port = GetWharfList(mc);
		}
		return l_port;
	}

	public static List<Port> initDate(String result) {
		List<Port> list = new ArrayList<Port>();
		JSONTokener jsonParser = new JSONTokener(result);
		JSONArray data;
		try {
			data = (JSONArray) jsonParser.nextValue();
			for (int i = 0; i < data.length(); i++) {
				Port oPort = new Port();
				JSONObject json_info = (JSONObject) data.opt(i);
				oPort.setMc(json_info.getString("mc"));
				oPort.setBh(json_info.getString("bh"));
				oPort.setFzr(json_info.getString("fzr"));
				oPort.setJd(json_info.getString("jd"));
				oPort.setFzdh(json_info.getString("fzdh"));
				oPort.setWd(json_info.getString("wd"));

				list.add(oPort);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Port> GetWharfList(String mc) {
		List<Port> list = new ArrayList<Port>();
		Query query = new Query();
		String result = query.GetWharfListFromDate(mc);
		if (result != null) {

			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try {
				data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");
				for (int i = 0; i < jsonArray.length(); i++) {
					Port oPort = new Port();
					JSONObject json_info = (JSONObject) jsonArray.opt(i);
					oPort.setMc(json_info.getString("mc"));
					oPort.setBh(json_info.getString("bh"));
					oPort.setFzr(json_info.getString("fzr"));
					oPort.setJd(json_info.getString("jd"));
					oPort.setFzdh(json_info.getString("fzdh"));
					oPort.setWd(json_info.getString("wd"));

					list.add(oPort);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
