package com.huzhouport.portDynmicNews.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.portDynmicNews.model.ModelPortDynmicNews;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ModelActionPortDynmicNews extends BaseAction implements
		ModelDriven<ModelPortDynmicNews> {
	ModelPortDynmicNews modelPortDynmicNews = new ModelPortDynmicNews();

	private List<ModelPortDynmicNews> list = new ArrayList<ModelPortDynmicNews>();// 返回列表

	

	private int totalPage;// 返回总页数
	
	private List<String> listImage;
	
	private String errorInfo;

	public int pagesInfo() throws Exception {
//		String pageUrl = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/";
//		URL url;
//		HttpURLConnection httpConn = null;
//		InputStreamReader input = null;
//		BufferedReader bufReader = null;
//		StringBuilder contentBuf = null;
//		String line = "";
//		url = new URL(pageUrl);
//		httpConn = (HttpURLConnection) url.openConnection();
////		httpConn.setConnectTimeout(15000);//设置2000毫秒访问主机的时间
////		httpConn.setReadTimeout(16000);//设置3000毫秒读取数据的时间
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
		totalPage = pagesInfo();
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		String str = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/index_rs_news_";

		String strURL = str + Integer.valueOf(this.getCpage())
				+ ".page?timestamp=1383549282578";
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
			ModelPortDynmicNews model = new ModelPortDynmicNews();
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
			model.setUrl("http://www.hzgh.gov.cn"
					+ herf[i].substring(beginUrl + 1, endUrl) + "shtml");
			list.add(model);

		}
		}catch (Exception e) {
			errorInfo="暂无相关数据......";
			//throw new Exception("网站正在维护中，暂无相关数据......");
		}
 		return SUCCESS;
	}
	public String captureHtml1() throws Exception {
		try{
		totalPage = 400;
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		for(int j=1;j<=totalPage;j++){
		String str = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/index_rs_news_";

		String strURL = str + Integer.valueOf(this.getCpage())
				+ ".page?timestamp=1383549282578";
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
			ModelPortDynmicNews model = new ModelPortDynmicNews();
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
			model.setUrl("http://www.hzgh.gov.cn"
					+ herf[i].substring(beginUrl + 1, endUrl) + "shtml");
			list.add(model);

		}
		}
		}catch (Exception e) {
			errorInfo="暂无相关数据......";
			//throw new Exception("网站正在维护中，暂无相关数据......");
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
		try{
		url = new URL(modelPortDynmicNews.getUrl());
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		String buf = contentBuf.toString();
		String a = (buf.split("<div id=\"content\">")[1])
				.split("<div class=\"clear\">")[0];
		String b = a.replace("href=\"", "href=\"http://www.hzgh.gov.cn");
		b = b.replace("src=\"", "src=\"http://www.hzgh.gov.cn").replaceAll("line-height: 35pt", "line-height: 25pt");
		//去掉所有的stly
		String noStly=b.replaceFirst("<p.*?>", "<p style=\"line-height: 25pt;text-align: center; \">").replaceAll("&nbsp;", "");
		//System.out.println(noStly);
//		String regex; 
//		String title = "";   
//		 regex = "<p.*?>.*?</p>";   
//		 final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);   
//		  final Matcher ma = pa.matcher(b);   
//		  final List<String> list1 = new ArrayList<String>();  
//		  while (ma.find())   
//		  {   
//			  list1.add(ma.group());   
//		  }   
//		  System.out.println(list1.get(0));
//		  System.out.println(list1.get(0).replaceAll("<.*?>", "").replaceAll("&nbsp;", ""));
//		  modelPortDynmicNews.setTitile(list1.get(0).replaceAll("<.*?>", "").replaceAll("&nbsp;", ""));// 去图片注释 
// 
//		  for (int i = 1; i < list1.size(); i++)   
//		  {   			    
//		   title = title + list1.get(i);   
//		  }   b.replaceAll("&nbsp;", "").replaceAll("font-size: 15pt", "font-size: 12pt").replaceAll("style.*?\"", "")
		
		
		modelPortDynmicNews.setContenct(noStly.replaceAll("font-size: 15pt", "font-size:13pt").replaceAll("mso-line-height-rule: exactly", "").replaceAll("text-indent: 79.5pt", ""));
//				b.replaceAll(
//				"mso-char-indent-count:.*?;", " mso-char-indent-count: 2.0")
//				.replaceAll("&nbsp;", "").replaceAll("text-indent.*?;", "text-indent:30pt;").replaceAll("line-height.*?;", "line-height:25pt;"));
		return SUCCESS;
	}

	
	public String findTopImageInfo() throws Exception {
		String link = "http://www.hzgh.gov.cn/index.html";
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		url = new URL(link);
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		String count = (buf.split("wp_skitter\"")[1]).split("<script")[0];
		String[] imgUrl=count.split("src=\"");
		for(int i=1;i<imgUrl.length;i++){
			listImage.add("http://www.hzgh.gov.cn"+imgUrl[i].split("\"")[0]);
		}
		return SUCCESS;
	}

	public ModelPortDynmicNews getModel() {
		return modelPortDynmicNews;
	}

	public ModelPortDynmicNews getModelPortDynmicNews() {
		return modelPortDynmicNews;
	}

	public void setModelPortDynmicNews(ModelPortDynmicNews modelPortDynmicNews) {
		this.modelPortDynmicNews = modelPortDynmicNews;
	}

	public List<ModelPortDynmicNews> getList() {
		return list;
	}

	public void setList(List<ModelPortDynmicNews> list) {
		this.list = list;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<String> getListImage() {
		return listImage;
	}

	public void setListImage(List<String> listImage) {
		this.listImage = listImage;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
