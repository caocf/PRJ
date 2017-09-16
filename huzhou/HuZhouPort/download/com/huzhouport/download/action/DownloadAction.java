package com.huzhouport.download.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.download.model.Download;
import com.huzhouport.download.model.Version;
import com.huzhouport.download.model.VersionShortInfo;
import com.huzhouport.download.service.DownloadService;

public class DownloadAction 
{
	public static String INNER_VERSION_INFO_ADDRESS=ServletActionContext.getServletContext().getRealPath("/version/version.json");
	public static String OUTER_VERSION_INFO_ADDRESS=ServletActionContext.getServletContext().getRealPath("/version/publicVersion.json");
	
	
	private int appid;					//1:内部版 2:外部版
	private Version version;			//版本信息
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
	public void setAppid(int appid) 
	{
		this.appid = appid;
	}
	public Version getVersion() {
		return version;
	}
	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}
	
	//获取最新版本信息接口
	public String queryLastVersion()
	{
		version=queryVersion();
		
		return "success";
	}

	//下载并添加记录
	public String downLastApp()
	{
		version=queryVersion();

		directURL=version.getVersionDownUrl();
		
		System.out.println(directURL);
		
		//添加下载记录
		if(appid==1 || appid==2)
		{
			Download download=new Download();
			download.setAppId(appid);
			download.setDate(new Date());
			download.setIp(ServletActionContext.getRequest().getRemoteAddr());
			download.setAppVersion(String.valueOf(version.getVersionID()));
			this.downloadService.addDownload(download);
		}

		return "success";
	}
	
	//查询app下载量 appid=0为所有app统计量
	public String queryAppCount()
	{
		if(appid==0 || appid==1 || appid==2)
			this.countInfo=this.downloadService.queryDownloadCount(appid);
		
		return "success";
	}
	
	
	public Version queryVersion()
	{
		String addr="";
		
		//内部还是外部版
		switch (appid) 
		{
			case 1:
				addr=INNER_VERSION_INFO_ADDRESS;
				break;
			case 2:
				addr=OUTER_VERSION_INFO_ADDRESS;
				break;
		}
		
		if(!addr.equals(""))
		{
			List<VersionShortInfo> vl=this.downloadService.GetAppList(addr);
			
			String url="";
			int id=0;
			
			for(VersionShortInfo v:vl)
			{
				if(v.getVersionID()>id)
				{
					id=v.getVersionID();
					url=v.getVersionDownUrl();
				}
			}
			
			return this.downloadService.getLastVersionID(ServletActionContext.getServletContext().getRealPath(url));
		}
		return null;
	}
	
}
