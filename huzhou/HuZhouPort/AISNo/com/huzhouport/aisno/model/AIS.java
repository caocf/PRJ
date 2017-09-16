package com.huzhouport.aisno.model;

import java.io.File;

public class AIS {

	private String num;
	private String name;
	private String picpath;
	private String picname;
	private String adddate;
	private File file;
	private String fileFileName;
	private Integer appflag;
	private Integer modifyflag;
	String man;
	
	String checker;
	
	public Integer getModifyflag() {
		return modifyflag;
	}
	public void setModifyflag(Integer modifyflag) {
		this.modifyflag = modifyflag;
	}
	
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getPicname() {
		return picname;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public Integer getAppflag() {
		return appflag;
	}
	public void setAppflag(Integer appflag) {
		this.appflag = appflag;
	}
    
}
