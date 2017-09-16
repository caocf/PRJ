package com.huzhouport.wharfSelect.service.webservice;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.SoapUtil;
import com.huzhouport.wharfSelect.service.webservice.model.WharfWebServiceNode;

public class WharfWebservice {
	public static List<WharfWebServiceNode> getAllPort() {
		List<WharfWebServiceNode> nodes = new ArrayList<WharfWebServiceNode>();
		String result = SoapUtil.CallWebServiceForString(

				GlobalVar.WHARFWEBSERVICE_URL,
				GlobalVar.WHARFWEBSERVICE_NAMESPACE, "getallport", null);

		try {
			JSONTokener jsonToken = new JSONTokener(result);
			JSONArray data;
			data = (JSONArray) jsonToken.nextValue();
			for (int i = 0; i < data.size(); i++) {
				JSONObject json_info = (JSONObject) data.opt(i);
				String mc = json_info.getString("mc"); // 码头名称
				String bh = json_info.getString("bh"); // 码头编号
				String fzr = json_info.getString("fzr"); // 负责人
				String fzdh = json_info.getString("fzdh"); // 负责电话
				String jd = json_info.getString("jd"); // 经度
				String wd = json_info.getString("wd"); // 纬度
				String qy = json_info.getString("qy"); // 片区
				
				WharfWebServiceNode node = new WharfWebServiceNode();
				
				node.setMc(mc);
				node.setBh(bh);
				node.setFzdh(fzdh);
				node.setFzr(fzr);
				node.setJd(jd);
				node.setWd(wd);
				node.setQy(qy);
				
				nodes.add(node);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}
}
