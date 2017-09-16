package com.huzhouport.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class UploadFileInfo
{
	public UploadFileInfo()
	{
	}
	public UploadFileInfo(String name,String url,Map<String,Object> p,Map<String,File> f,String type)
	{
		setName(name);
		setActionUrl(url);
		setParams(p);
		setFiles(f);
		setUploadClassName(type);
		setStatus(0);
		setIniFileDatas();
		setGet(false);
	}
	
	public UploadFileInfo(String name,String url,String params)
	{
		setName(name);
		setActionUrl(url);
		setGetParams(params);
		setStatus(0);
		setGet(true);
	}
	
	private boolean isGet;
	
	public boolean isGet() {
		return isGet;
	}
	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}
	private String getParams;
	public String getGetParams() {
		return getParams;
	}
	public void setGetParams(String getParams) {
		this.getParams = getParams;
	}

	private String name;
	private String actionUrl;
	private Map<String, Object> params;
	private Map<String, File> files;
	private String uploadClassName;
	
	private int status;
	
	private long totalLength;
	private long hasSendLength;
	
	public long getTotalLength()
	{
		return totalLength;
	}
	public void setTotalLength(long totalLength)
	{
		this.totalLength = totalLength;
	}
	public long getHasSendLength()
	{
		return hasSendLength;
	}
	public void setHasSendLength(long hasSendLength)
	{
		this.hasSendLength = hasSendLength;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getActionUrl()
	{
		return actionUrl;
	}
	public void setActionUrl(String actionUrl)
	{
		this.actionUrl = actionUrl;
	}
	public Map<String, Object> getParams()
	{
		return params;
	}
	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}
	public Map<String, File> getFiles()
	{
		return files;
	}
	public void setFiles(Map<String, File> files)
	{
		this.files = files;
	}
	public String getUploadClassName()
	{
		return uploadClassName;
	}
	public void setUploadClassName(String uploadClassName)
	{
		this.uploadClassName = uploadClassName;
	}
	
	ArrayList<FileData> fileDatas;
	public ArrayList<FileData> getFileDatas()
	{
		return fileDatas;
	}
	public void setFileDatas(ArrayList<FileData> fileDatas)
	{
		this.fileDatas = fileDatas;
	}
	

	public void setIniFileDatas()
	{
		if(getFiles()==null)
		{
			setTotalLength(1); //±ÜÃâ³ý0µÄÇé¿ö
			return;
		}
		
		if(fileDatas==null)
			fileDatas=new ArrayList<FileData>();
		
		long count=0;
		for(Map.Entry<String, File> entry : files.entrySet())
		{
			fileDatas.add(new FileData(entry.getKey(),entry.getValue().length(),0));
			count+=entry.getValue().length();
		}
		setTotalLength(count);
	}

	public void removeFile()
	{
		if(fileDatas!=null && fileDatas.size()>0)
			fileDatas.remove(0);
	}
	public void updataProgress(long progress,int index)
	{
		fileDatas.get(index).setSendSize(progress);
	}
	
	public double getPercentDouble()
	{
		return ((double)getHasSendLength())/getTotalLength();
	}
	public String getPercentString()
	{
		return String.valueOf((int)(getPercentDouble()*100))+"%";
	}
	
	private boolean checked;

	public boolean isChecked()
	{
		return checked;
	}
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
}
