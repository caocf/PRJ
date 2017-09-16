package com.huzhouport.download.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.download.model.Download;
import com.huzhouport.download.model.Version;
import com.huzhouport.download.model.VersionShortInfo;

public interface DownloadService 
{
	//添加下载信息记录
	public void addDownload(Download download);
	
	//所有版本列表信息
	public List<VersionShortInfo> GetAppList(String path);
	
	//最新版本id
	public Version getLastVersionID(String versionAddr);
	
	//获取app下载统计
	public List<Map<String, Object>> queryDownloadCount(int appid);
}
