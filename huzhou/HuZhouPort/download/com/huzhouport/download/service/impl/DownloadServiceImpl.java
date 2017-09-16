package com.huzhouport.download.service.impl;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.huzhouport.download.dao.DownloadDao;
import com.huzhouport.download.model.Download;
import com.huzhouport.download.model.Version;
import com.huzhouport.download.model.VersionShortInfo;
import com.huzhouport.download.service.DownloadService;

public class DownloadServiceImpl implements DownloadService
{
	DownloadDao downloadDao;
	public void setDownloadDao(DownloadDao downloadDao) {
		this.downloadDao = downloadDao;
	}
	
	
	//添加下载记录
	public void addDownload(Download download) 
	{
		this.downloadDao.addDownload(download);
	}

	//获取版本列表信息
	public List<VersionShortInfo> GetAppList(String path)
	{
		String result="";
		result=GetFile(path);
		
		List<VersionShortInfo> r=new ArrayList<VersionShortInfo>();
		
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray array=jsonObject.getJSONArray("versionList");
		
		for(int i=0;i<array.size();i++)
		{
			VersionShortInfo versionShortInfo=new VersionShortInfo();
			versionShortInfo.setVersionID(array.getJSONObject(i).getInt("versionID"));
			versionShortInfo.setVersionDownUrl(array.getJSONObject(i).getString("versionDownUrl"));
			
			r.add(versionShortInfo);
		}
		
		return r;
	}
	
	
	//最新版本信息
	public Version getLastVersionID(String versionAddr)
	{
//		List<VersionShortInfo> vl=GetAppList(versionAddr);
//		
//		String url="";
//		int id=0;
//		
//		for(VersionShortInfo v:vl)
//		{
//			if(v.getVersionID()>id)
//			{
//				id=v.getVersionID();
//				url=v.getVersionDownUrl();
//			}
//		}
		
		String result="";
		result=GetFile(versionAddr);
		
		Version v=JSON.parseObject(result,Version.class);
		
		return v;
	}
	

	//读取文件转字符串
	public String GetFile(String path)
	{
		 StringBuffer   sbFile;   
		 try
		 {
	         FileReader   in   =   new   FileReader(path);   
	         char[]   buffer   =   new   char[4096];   
	         int   len;   
	         sbFile   =   new   StringBuffer();   
	         while   (   (len   =   in.read(buffer))   !=   -1)   {   
	             String   s   =   new   String(buffer,   0,   len);   
	             sbFile.append(s);   
	         }
	         return   sbFile.toString();   
		 }
		 catch (Exception e) 
		 {
			 System.out.println(e);
		 }
		 
         return "";
	}


	public List<Map<String, Object>> queryDownloadCount(int appid) {
		return downloadDao.queryDownloadCount(appid);
	}

	
}
