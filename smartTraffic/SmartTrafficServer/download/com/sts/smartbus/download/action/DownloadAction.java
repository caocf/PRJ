package com.sts.smartbus.download.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.sts.smartbus.download.model.Download;
import com.sts.smartbus.download.model.Version;
import com.sts.smartbus.download.model.VersionShortInfo;
import com.sts.smartbus.download.service.DownloadService;

public class DownloadAction {
	public static String INNER_VERSION_INFO_ADDRESS = ServletActionContext
			.getServletContext().getRealPath("/version/versionNew.json.txt");

	private Version version; // 版本信息
	private DownloadService downloadService;
	private String directURL;
	private List<Map<String, Object>> countInfo;

	public List<Map<String, Object>> getCountInfo() {
		return countInfo;
	}

	public void setDirectURL(String directURL) {
		this.directURL = directURL;
	}

	public String getDirectURL() {
		return directURL;
	}

	public Version getVersion() {
		return version;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	// 获取最新版本信息接口
	public String queryLastVersion() {
		version = queryVersion();

		System.out.println(version.getVersionID());

		return "success";
	}

	String oldversion;
	String equitment;
	String imei;

	public void setOldversion(String oldversion) {
		this.oldversion = oldversion;
	}

	public void setEquitment(String equitment) {
		this.equitment = equitment;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	String weixinpage = "module/appversioncheck/weixindl.jsp";
	
	public String getWeixinpage() {
		return weixinpage;
	}

	// 下载并添加记录
	public String downLastApp() {

		String useragent = ServletActionContext.getRequest().getHeader(
				"User-Agent");

		// 如果是微信，则自动重定向到下载页面
		if (useragent.contains("MicroMessenger")) {
			return "weixin";
		}
		
		version = queryVersion();

		directURL = version.getVersionDownUrl();

		Download download = new Download();
		download.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date()));
		download.setIp(ServletActionContext.getRequest().getRemoteAddr());
		download.setAppVersion(String.valueOf(version.getVersionID()));
		download.setOldVersion(oldversion);
		download.setEquitment(equitment);
		download.setImei(imei);

		this.downloadService.addDownload(download);

		return "success";
	}

	String startTime;
	String endTime;

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String queryAppCount() {
		this.countInfo = this.downloadService.queryDownloadCount(startTime,
				endTime);

		return "success";
	}

	public Version queryVersion() {

		try {
			String addr = "";

			addr = INNER_VERSION_INFO_ADDRESS;

			System.out.println(addr);

			if (!addr.equals("")) {
				List<VersionShortInfo> vl = this.downloadService
						.GetAppList(addr);

				System.out.println("版本数：" + vl.size());

				String url = "";
				int id = 0;

				for (VersionShortInfo v : vl) {
					if (v.getVersionID() > id) {
						id = v.getVersionID();
						url = v.getVersionDownUrl();
					}
				}

				return this.downloadService
						.getLastVersionID(ServletActionContext
								.getServletContext().getRealPath(url));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

}
