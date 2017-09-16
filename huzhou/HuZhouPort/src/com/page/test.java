package com.page;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) throws Exception {
		captureHtml("hzgh/xwdt/ghdt/hzgh/xwdt/ghdt/");
	}

	public static void captureHtml(String ip) throws Exception {
		String pageUrl = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/";
		URL url = null;
		HttpURLConnection httpConn = null;
		InputStreamReader input = null;
		BufferedReader bufReader = null;
		StringBuilder contentBuf = null;
		String line = "";
		url = new URL(pageUrl);
		httpConn = (HttpURLConnection) url.openConnection();
		input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		bufReader = new BufferedReader(input);

		contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}

		String[] listPage = contentBuf.toString().split("index.html\',");
		String pages = listPage[1];
		int endPage = pages.indexOf(",");
		int page = Integer.valueOf(pages.substring(0, endPage));
		String str = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/index_rs_news_";
		dataInfo d=null;
		List<dataInfo> list=new ArrayList<dataInfo>();
		for (int j = 1; j <=page; j++) {

			String strURL = str+j+".page?timestamp=1383549282578";
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
				d=new dataInfo();
				int beginIx = tiaoshu[i].indexOf(">");
				int endIx = tiaoshu[i].indexOf("</a>");
				String result = tiaoshu[i].substring(beginIx + 1, endIx);
				int begind = span[i].indexOf(">");
				int endd = span[i].indexOf("</span>");
				String resultd = span[i].substring(begind + 1, endd);
				d.setTitile(result);
				d.setDate(resultd);	
				int beginUrl=herf[i].indexOf("\"");
				int endUrl=herf[i].indexOf("shtml\"");
				d.setUrl(herf[i].substring(beginUrl + 1, endUrl)+"shtml");
				System.out.println(d.getUrl());
				list.add(d);
			}			
		}
		System.out.println(list.size());
		
	}
}

class dataInfo{
	private String titile;//标题
	private String date;//时间
	private String url;//连接
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
