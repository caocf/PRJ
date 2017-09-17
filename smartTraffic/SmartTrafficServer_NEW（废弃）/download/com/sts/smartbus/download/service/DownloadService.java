package com.sts.smartbus.download.service;

import java.util.List;
import java.util.Map;

import com.sts.smartbus.download.model.Download;
import com.sts.smartbus.download.model.Version;
import com.sts.smartbus.download.model.VersionShortInfo;

public interface DownloadService 
{
	//添加下载信息记录
	public void addDownload(Download download);
	
	//所有版本列表信息
	public List<VersionShortInfo> GetAppList(String path);
	
	//最新版本id
	public Version getLastVersionID(String versionAddr);
	
	//获取app下载统计
	public List<Map<String, Object>> queryDownloadCount();
}
