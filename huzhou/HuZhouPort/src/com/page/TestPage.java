package com.page;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestPage {
	public static void main(String[] args) throws Exception {
		captureHtml("hzgh/xwdt/ghdt/hzgh/xwdt/ghdt/");
	}

	public static void captureHtml(String ip) throws Exception {
		String strURL = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/index_rs_news_1.page?timestamp=1383549282578";
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn
				.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		dataInfo d=null;
		List<dataInfo> list=new ArrayList<dataInfo>();
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
			System.out.println(result+" "+resultd+" "+d.getUrl());
		}
	}
}
