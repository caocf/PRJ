package com.huzhouport.upload;

public class FileData
{
	public FileData()
	{
		
	}
	
	public FileData(String fname,long fsize,long fsendsize)
	{
		setFileName(fname);
		setFileSize(fsize);
		setSendSize(fsendsize);
	}
	
	String fileName;
	long fileSize;
	long sendSize;
	
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public long getFileSize()
	{
		return fileSize;
	}
	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}
	public long getSendSize()
	{
		return sendSize;
	}
	public void setSendSize(long sendSize)
	{
		this.sendSize = sendSize;
	}
	
	
}
