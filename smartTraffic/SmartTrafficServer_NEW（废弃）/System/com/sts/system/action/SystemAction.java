package com.sts.system.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.sts.system.model.About;
import com.sts.system.model.Version;
import com.sts.system.model.VersionShortInfo;
import com.sts.system.service.SystemService;

public class SystemAction 
{
	About about;
	SystemService systemService;
	public About getAbout() {
		return about;
	}
	public void setAbout(About about) {
		this.about = about;
	}
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	/**
	 * 关于
	 * @return
	 */
	public String GetAbout()
	{
		about=this.systemService.GetSystemAbout();
		return "success";
	}
	
	String[] helps;
	public String[] getHelps() {
		return helps;
	}
	
	/**
	 * 帮助
	 * @return
	 */
	public String GetHelp()
	{
		helps=this.systemService.GetHelp();
		return "success";
	}
	
	Version version;
	public Version getVersion() {
		return version;
	}
	
	/**
	 * 返回最新APP连接地址
	 * @return
	 */
	public String GetAppNewestAppInfo()
	{
		try
		{					
			version=this.systemService.GetAppUrl(ServletActionContext.getServletContext().getRealPath("/version/version.json.txt"));		
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return "success";
	}
	
	List<VersionShortInfo> versionList;
	public List<VersionShortInfo> getVersionList() {
		return versionList;
	}
	public String GetAppList()
	{
		versionList=this.systemService.GetAppList(ServletActionContext.getServletContext().getRealPath("/version/version.json.txt"));
		
		return "success";
	}
}
