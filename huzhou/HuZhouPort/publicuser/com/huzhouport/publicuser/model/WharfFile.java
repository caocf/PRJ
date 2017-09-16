package com.huzhouport.publicuser.model;

import java.io.File;
import java.util.List;

public class WharfFile implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int fileId;
	private int wharfId;
	private String fileName;
	private String filePath;
	private File file;
	
	private List<File> pic;
	private List<String> picFileName;
	private List<String> picContentType;
	
	public List<File> getPic() {
		return pic;
	}
	public void setPic(List<File> pic) {
		this.pic = pic;
	}
	public List<String> getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(List<String> picFileName) {
		this.picFileName = picFileName;
	}
	public List<String> getPicContentType() {
		return picContentType;
	}
	public void setPicContentType(List<String> picContentType) {
		this.picContentType = picContentType;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getWharfId() {
		return wharfId;
	}
	public void setWharfId(int wharfId) {
		this.wharfId = wharfId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
}
