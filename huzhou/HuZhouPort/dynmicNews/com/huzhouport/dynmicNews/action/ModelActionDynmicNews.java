package com.huzhouport.dynmicNews.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.dynmicNews.model.ModelDynmicNews;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ModelActionDynmicNews extends BaseAction implements
		ModelDriven<ModelDynmicNews> {
	ModelDynmicNews modelPortDynmicNews = new ModelDynmicNews();

	private List<ModelDynmicNews> list = new ArrayList<ModelDynmicNews>();// 返回列表

	private int totalPage;// 返回总页数

	private List<String> listImage;

	private String errorInfo;

	public int pagesInfo() throws Exception {
		String pageUrl = "http://zjgh.zjt.gov.cn/col/col6711/index.html";
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line = "";
		url = new URL(pageUrl);
		httpConn = (HttpURLConnection) url.openConnection();
		// httpConn.setConnectTimeout(15000);//设置2000毫秒访问主机的时间
		// httpConn.setReadTimeout(16000);//设置3000毫秒读取数据的时间
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}

		// String[] listPage = contentBuf.toString().split("index.html\',");
		// String pages = listPage[1];
		// int endPage = pages.indexOf(",");
		// int page = Integer.valueOf(pages.substring(0, endPage));
		return 400;
	}

	// 列表
	public String captureHtml() throws Exception {
		try {
			//totalPage = 500;
			URL url;
			HttpURLConnection httpConn = null;
			InputStreamReader input = null;
			BufferedReader bufReader = null;
			StringBuilder contentBuf = null;
			String line;
			String str = "http://zjgh.zjt.gov.cn/col/col6711/index.html";
			url = new URL(str);
			httpConn = (HttpURLConnection) url.openConnection();
			input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			bufReader = new BufferedReader(input);

			contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}
			String buf = contentBuf.toString();
			String[] herf = buf.split("urls\\[i\\]=\\'");
			String[] headers = buf.split("headers\\[i\\]=\\'");
			String[] year = buf.split("year\\[i\\]=\\'");
			String[] month = buf.split("month\\[i\\]=\\'");
			String[] day = buf.split("day\\[i\\]=\\'");
			int startpage=(Integer.valueOf(this.getCpage())-1)*15+1;	
			int endPage=(Integer.valueOf(this.getCpage())-1)*15+16;
			if(herf.length<endPage){
				throw new Exception("");
			}
			int g = herf.length % 15;
			if (g == 0) {
				totalPage = herf.length / 15;
			} else {
				totalPage = (herf.length / 15) + 1;
			}
			for (int i = startpage; i < endPage; i++) {
				ModelDynmicNews model = new ModelDynmicNews();
				model.setUrl("http://zjgh.zjt.gov.cn"
						+ herf[i].split("\\';")[0]);
				model.setTitile(headers[i].split("\\';")[0]);
				model.setDate(year[i].split("\\';")[0] + "-"
						+ month[i].split("\\';")[0] + "-"
						+ day[i].split("\\';")[0]);
			//	System.out.println(model.getUrl() + "-" + model.getTitile());
				list.add(model);

			}
		} catch (Exception e) {
			errorInfo = "暂无相关数据......";
			// throw new Exception("网站正在维护中，暂无相关数据......");
		}
		return SUCCESS;
	}

	/*
	 * public String captureHtml1() throws Exception { try { totalPage = 400;
	 * URL url; HttpURLConnection httpConn = null; InputStreamReader input =
	 * null; BufferedReader bufReader = null; StringBuilder contentBuf = null;
	 * String line; for (int j = 1; j <= totalPage; j++) {
	 * 
	 * String str = "http://zjgh.zjt.gov.cn";
	 * 
	 * String strURL = str + Integer.valueOf(this.getCpage()) +
	 * "/col/col6711/index.html"; url = new URL(strURL); httpConn =
	 * (HttpURLConnection) url.openConnection(); input = new
	 * InputStreamReader(httpConn.getInputStream(), "utf-8"); bufReader = new
	 * BufferedReader(input);
	 * 
	 * contentBuf = new StringBuilder(); while ((line = bufReader.readLine()) !=
	 * null) { contentBuf.append(line); } String buf = contentBuf.toString();
	 * String[] herf = buf.split("href="); String[] tiaoshu = buf.split("<li>");
	 * String[] span = buf.split("<span"); for (int i = 1; i < tiaoshu.length;
	 * i++) { ModelDynmicNews model = new ModelDynmicNews(); int beginIx =
	 * tiaoshu[i].indexOf(">"); int endIx = tiaoshu[i].indexOf("</a>"); String
	 * result = tiaoshu[i].substring(beginIx + 1, endIx); int begind =
	 * span[i].indexOf(">"); int endd = span[i].indexOf("</span>"); String
	 * resultd = span[i].substring(begind + 1, endd); model.setTitile(result);
	 * model.setDate(resultd); int beginUrl = herf[i].indexOf("\""); int endUrl
	 * = herf[i].indexOf("shtml\""); model .setUrl("http://zjgh.zjt.gov.cn" +
	 * herf[i].substring(beginUrl + 1, endUrl) + "shtml"); list.add(model);
	 * 
	 * } } } catch (Exception e) { errorInfo = "暂无相关数据......"; // throw new
	 * Exception("网站正在维护中，暂无相关数据......"); } return SUCCESS; }
	 */

	public String findDetailInfo() throws Exception {
		URL url;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line;
		try {
			url = new URL(this.modelPortDynmicNews.getUrl());
			httpConn = (HttpURLConnection) url.openConnection();
			input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			bufReader = new BufferedReader(input);

			contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		String buf = contentBuf.toString();

		String content = buf
				.split("\\<table width=\\\"100%\\\" border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" style=\\\"border-bottom\\:solid 1px \\#CCC\\;\\\"\\>")[1]
				.split("\\<\\!--\\<\\$\\[信息内容\\]\\>end--\\>\\<\\/td\\>\\<\\/tr\\>\\<\\/table\\>")[0];
		content = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-bottom:solid 1px #CCC;\">"
				+ content + "</td></tr></table>";
		content=content.replace(">发布日期", " style='font-size:17px;line-height:27px; padding-top:10px;'>发布日期");
		content = content
				.replace(
						"字号：[ <a href='javascript:doZoom(16)' class='hui'>大</a> <a href='javascript:doZoom(14)' class='hui'>中</a> <a href='javascript:doZoom(12)' class='hui'>小</a> ]",
						"").replace("border-bottom:1px #ccc solid;", "")
				.replace("border-bottom:solid 1px #CCC;", "").replace("font-size:16px", "font-size:22px").replace("class=zhengwen", "style='font-size:17px;line-height:27px; padding:10px;'").replace("style=\"line-height:22px; padding:10px;\"", "style='font-size:17px;line-height:27px; padding:10px;'");

		modelPortDynmicNews.setContenct(content);

		return SUCCESS;

	}

	public ModelDynmicNews getModelPortDynmicNews() {
		return modelPortDynmicNews;
	}

	public void setModelPortDynmicNews(ModelDynmicNews modelPortDynmicNews) {
		this.modelPortDynmicNews = modelPortDynmicNews;
	}

	public List<ModelDynmicNews> getList() {
		return list;
	}

	public void setList(List<ModelDynmicNews> list) {
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

	public ModelDynmicNews getModel() {
		// TODO Auto-generated method stub
		return modelPortDynmicNews;
	}

}
