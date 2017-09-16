package com.word;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class word {
	public static void main(String[] args) throws Exception {
		captureHtml("hzgh/zwgk/tzgg/hzgh/zwgk/tzgg/");
	}

	public static void captureHtml(String ip) throws Exception {
		String strURL = "http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/index_rs_news_1.page?timestamp=1383641586000";
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn
				.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		dataInfo d = null;
		List<dataInfo> list = new ArrayList<dataInfo>();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		String[] herf = buf.split("href=");
		String[] tiaoshu = buf.split("<li>");
		String[] span = buf.split("<span");
		for (int i = 1; i < tiaoshu.length; i++) {
			d = new dataInfo();
			int beginIx = tiaoshu[i].indexOf(">");
			int endIx = tiaoshu[i].indexOf("</a>");
			String result = tiaoshu[i].substring(beginIx + 1, endIx);
			int begind = span[i].indexOf(">");
			int endd = span[i].indexOf("</span>");
			String resultd = span[i].substring(begind + 1, endd);
			d.setTitile(result);
			d.setDate(resultd);
			int beginUrl = herf[i].indexOf("\"");
			int endUrl = herf[i].indexOf("shtml\"");
			d.setUrl(herf[i].substring(beginUrl + 1, endUrl) + "shtml");
			String down = findDataInfo("http://www.hzgh.gov.cn" + d.getUrl());
			String[] downUrlList = down.split("&nbsp;<a href=\"");
			if(downUrlList.length>=2){
				d.setXiazai(downUrlList[1].split("\"")[0]);
				d.setXiazaiTitile(downUrlList[1].split(">")[1].split("<")[0]);
			}
			System.out.println(result+"   "+resultd+"   "+d.getUrl()+"    "+d.getXiazaiTitile()+"    "+d.getXiazai()+"   ");
			list.add(d);

		}
	}

	public static String findDataInfo(String strURL) throws Exception {
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn
				.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		
		String buf = contentBuf.toString();
		//System.out.println(buf);
		return buf;
	}
}

class dataInfo {
	private String titile;// 标题
	private String date;// 时间
	private String url;// 连接
	private String xiazai;// 下载链接
	private String xiazaiTitile;// 下载的标题

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

	public String getXiazai() {
		return xiazai;
	}

	public void setXiazai(String xiazai) {
		this.xiazai = xiazai;
	}

	public String getXiazaiTitile() {
		return xiazaiTitile;
	}

	public void setXiazaiTitile(String xiazaiTitile) {
		this.xiazaiTitile = xiazaiTitile;
	}

}