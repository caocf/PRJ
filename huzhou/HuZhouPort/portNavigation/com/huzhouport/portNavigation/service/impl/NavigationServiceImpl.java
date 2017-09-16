package com.huzhouport.portNavigation.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.portNavigation.dao.NavigationDao;
import com.huzhouport.portNavigation.model.ModelPortNavigation;
import com.huzhouport.portNavigation.service.NavigationService;

public class NavigationServiceImpl extends BaseServiceImpl<ModelPortNavigation>
		implements NavigationService {

	private NavigationDao navigationDao;

	public void setNavigationDao(NavigationDao navigationDao) {
		this.navigationDao = navigationDao;
	}

	public List<ModelPortNavigation> findNavigationServiceInfo(
			ModelPortNavigation modelPortNavigation, int totalPage, int cape)
			throws Exception {
		findUrlAndAdd(totalPage);
		int beginIndex = (cape - 1) * 10;
		return this.navigationDao.findNavigationInfo(modelPortNavigation,
				beginIndex, 10);
	}

	/**
	 * 查询远程数据并增加数据
	 * 
	 * @param totalPage
	 */
	protected void findUrlAndAdd(int totalPage) {
		try {
			URL url;
			HttpURLConnection httpConn = null;
			InputStreamReader input = null;
			BufferedReader bufReader = null;
			StringBuilder contentBuf = null;
			String line;
			String str = "http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/hxtg/index_rs_news_";
			for (int cape = 1; cape <= totalPage; cape++) {
				String strURL = str + cape + ".page?timestamp=1401355053484";
				url = new URL(strURL);
				httpConn = (HttpURLConnection) url.openConnection();
				input = new InputStreamReader(httpConn.getInputStream(),
						"utf-8");
				bufReader = new BufferedReader(input);

				contentBuf = new StringBuilder();
				while ((line = bufReader.readLine()) != null) {
					contentBuf.append(line);
				}
				String buf = contentBuf.toString();
				String[] herf = buf.split("href=");
				String[] tiaoshu = buf.split("<li>");
				String[] span = buf.split("<span");
				ModelPortNavigation model = null;
				for (int i = 1; i < tiaoshu.length; i++) {
					model = new ModelPortNavigation();
					int beginIx = tiaoshu[i].indexOf(">");
					int endIx = tiaoshu[i].indexOf("</a>");
					String result = tiaoshu[i].substring(beginIx + 1, endIx);
					int begind = span[i].indexOf(">");
					int endd = span[i].indexOf("</span>");
					String resultd = span[i].substring(begind + 1, endd);
					model.setTitile(result);
					model.setDate(resultd);
					int beginUrl = herf[i].indexOf("\"");
					int endUrl = herf[i].indexOf("shtml\"");
					model
							.setUrl("http://www.hzgh.gov.cn"
									+ herf[i].substring(beginUrl + 1, endUrl)
									+ "shtml");
					if (this.navigationDao.findSameInfo(model).size() > 0) {
						break;
					}
					this.add(model);
				}
			}
		} catch (Exception e) {
e.printStackTrace();
		}
	}
}
