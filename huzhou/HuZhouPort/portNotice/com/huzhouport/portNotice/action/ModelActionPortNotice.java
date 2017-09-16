package com.huzhouport.portNotice.action;

import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.portNotice.model.ModelPortNotice;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ModelActionPortNotice extends BaseAction implements ModelDriven<ModelPortNotice>{
	ModelPortNotice modelPortNotice = new ModelPortNotice();

	private List<ModelPortNotice> list=new ArrayList<ModelPortNotice>();//返回列表
	
	
	private int totalPage;//返回总页数

	private String errorInfo;
	
	public int pagesInfo() throws Exception {
//		String pageUrl = "http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/";
//		URL url ;
//		HttpURLConnection httpConn = null;
//		InputStreamReader input = null;
//		BufferedReader bufReader = null;
//		StringBuilder contentBuf = null;
//		String line = "";
//		url = new URL(pageUrl);
//		httpConn = (HttpURLConnection) url.openConnection();
//		httpConn.setConnectTimeout(12000);//设置2000毫秒访问主机的时间
//		httpConn.setReadTimeout(13000);//设置3000毫秒读取数据的时间
//		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
//		bufReader = new BufferedReader(input);
//
//		contentBuf = new StringBuilder();
//		while ((line = bufReader.readLine()) != null) {
//			contentBuf.append(line);
//		}
//
//		String[] listPage = contentBuf.toString().split("index.html\',");
//		String pages = listPage[1];
//		int endPage = pages.indexOf(",");
//		int page = Integer.valueOf(pages.substring(0, endPage));
		return 400;
	}

	public String captureHtml() throws Exception {
		try{
		totalPage=pagesInfo();
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		String str = "http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/index_rs_news_";

		String strURL = str +  Integer.valueOf(this.getCpage()) + ".page?timestamp=1383641586000";
		url = new URL(strURL);
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		String[] herf = buf.split("href=");
		String[] tiaoshu = buf.split("<li>");
		String[] span = buf.split("<span");
		for (int i = 1; i < tiaoshu.length; i++) {
			ModelPortNotice model = new ModelPortNotice();
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
			model.setUrl("http://www.hzgh.gov.cn"+herf[i].substring(beginUrl + 1, endUrl) + "shtml");
			list.add(model);			
		}
		}catch (Exception e) {
			errorInfo="暂无相关数据...";
		}
		return SUCCESS;
	}
	
	
	public String captureHtml1() throws Exception {
		try{
		totalPage=400;
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		for(int j=1;j<=totalPage;j++){
		String str = "http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/index_rs_news_";

		String strURL = str +  j + ".page?timestamp=1383641586000";
		url = new URL(strURL);
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		String[] herf = buf.split("href=");
		String[] tiaoshu = buf.split("<li>");
		String[] span = buf.split("<span");
		for (int i = 1; i < tiaoshu.length; i++) {
			ModelPortNotice model = new ModelPortNotice();
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
			model.setUrl("http://www.hzgh.gov.cn"+herf[i].substring(beginUrl + 1, endUrl) + "shtml");
			list.add(model);			
		}
		}
		}catch (Exception e) {
			errorInfo="暂无相关数据...";
		}
		
		return SUCCESS;
	}
	public String findDetailInfo() throws Exception {
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		url = new URL(modelPortNotice.getUrl());
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		String a = (buf.split("<div id=\"content\">")[1])
				.split("<div class=\"clear\">")[0];
		String b = a.replace("href=\"", "href=\"http://www.hzgh.gov.cn");
		b = b.replace("src=\"", "src=\"http://www.hzgh.gov.cn");
		
		String noStly=b.replaceFirst("<p.*?>", "<p style=\"line-height: 25pt;text-align: center; \">").replaceAll("&nbsp;", "");
//		System.out.println(noStly);
		
		modelPortNotice.setContenct(noStly.replaceAll("font-size: 15pt", "font-size:14pt"));
		
		//contenct=b;
		return SUCCESS;
	}

	public ModelPortNotice getModel() {
		return modelPortNotice;
	}

	public ModelPortNotice getModelPortNotice() {
		return modelPortNotice;
	}

	public void setModelPortNotice(ModelPortNotice modelPortNotice) {
		this.modelPortNotice = modelPortNotice;
	}

	public List<ModelPortNotice> getList() {
		return list;
	}

	public void setList(List<ModelPortNotice> list) {
		this.list = list;
	}


	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
}
