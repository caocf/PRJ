package com.huzhouport.common.util;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

@SuppressWarnings("deprecation")
public class FileDownloadAction implements Action {

	private String fileName;// 初始的通过param指定的文件名属性

	private String inputPath;// 指定要被下载的文件路径

	public InputStream getInputStream() throws Exception {

		// 通过 ServletContext，也就是application 来读取数据
		InputStream in= ServletActionContext.getServletContext().getResourceAsStream(
				"/"+GlobalVar.FilePATH+"/" + fileName);
		if(in==null)
		in=new StringBufferInputStream("");
		return in;

	}
	
	public String execute() throws Exception {

		return SUCCESS;

	}

	public void setInputPath(String value) {

		inputPath = value;

	}

	public void setFileName(String fileName) {

		this.fileName = fileName;

	}

	/** 提供转换编码后的供下载用的文件名 */

	public String getDownloadFileName() {

		String downFileName = fileName;

		try {

			downFileName = new String(downFileName.getBytes(), "ISO8859-1");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}

		return downFileName;

	}

}
