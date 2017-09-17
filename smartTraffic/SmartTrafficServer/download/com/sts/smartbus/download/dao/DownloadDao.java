package com.sts.smartbus.download.dao;

import java.util.List;
import java.util.Map;

import com.sts.smartbus.download.model.Download;

public interface DownloadDao 
{
	//添加下载信息
	public void addDownload(Download download);
	
	//获取统计信息
	public List<Map<String, Object>> queryDownloadCount(String start,String end);
}
