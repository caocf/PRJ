package com.huzhouport.publicuser.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.publicuser.model.PublicUser;
import com.opensymphony.xwork2.ModelDriven;

public class RegisterAction extends BaseAction implements
ModelDriven<PublicUser>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	File upfile;
	
	String filename;
	
	private PublicUser publicUser = new PublicUser();
	
	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}
	
	public void setUpfile(File upfile)
	{
		this.upfile=upfile;
		System.out.println("uplllllload");
	}
	
	public File getUpfile()
	{
		return upfile;
	}
	
	public void setFilename(String filename)
	{
		this.filename=filename;System.out.println("fffffff");
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public String DoReg() throws IOException
	{
		System.out.println("koko");
		/*
		String root = ServletActionContext.getServletContext().getRealPath("/image");
		InputStream is = new FileInputStream(upfile);
		OutputStream os = new FileOutputStream(new File(root, getFilename()));
		byte[] buffer = new byte[1024];
		
		int length = 0;
        
		while ((length = is.read(buffer)) > 0) 
		{  
            os.write(buffer, 0, length);  
        }
        
        os.close();
        is.close();*/
		return SUCCESS;
	}

	public PublicUser getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
