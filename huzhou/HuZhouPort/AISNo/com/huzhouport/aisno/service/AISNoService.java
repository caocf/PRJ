package com.huzhouport.aisno.service;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.aisno.dao.AISNoDao;
import com.huzhouport.aisno.model.AIS;
import com.huzhouport.aisno.model.AISNo;
import com.huzhouport.common.util.GlobalVar;

public class AISNoService {

	AISNoDao aisNoDao;

	public void setAisNoDao(AISNoDao aisNoDao) {
		this.aisNoDao = aisNoDao;
	}

	public AISNo queryShipNameByAisNo(String no) {
		List<AISNo> r = this.aisNoDao.queryShipNameByAisNo(no);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public AISNo queryShipNameByShipName(String name) {
		List<?> r = this.aisNoDao.queryShipNameByShipName(name);

		if (r != null && r.size() > 0)
		{
			int n=r.size();
			for(int x=0;x<r.size();x++)
			{
				AISNo a=(AISNo) r.get(x);
				if(a!=null)
					return a;
			}
		}
			

		return null;
	}
	
	public AIS queryEditShipByAis(String no) {
		List<AIS> r = this.aisNoDao.queryShipNameByEditAisNo(no);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public AIS queryEditShipByName(String name) {
		List<AIS> r = this.aisNoDao.queryShipNameByEditShipName(name);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public AIS hasEditData(String no) {
		List<AIS> r = this.aisNoDao.queryShipNameByEditAisNo(no);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public AIS hasEditDataByShipname(String shipname) {
		List<AIS> r = this.aisNoDao.queryShipNameByEditShipName(shipname);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public void saveAis(AIS a) {
		try {
			if (a.getFile() != null) {
				java.io.InputStream is = new java.io.FileInputStream(a
						.getFile());
				int beginIndex = a.getFileFileName().lastIndexOf('.');
				String stype = a.getFileFileName().substring(beginIndex + 1);
				String sname = (new Date()).getTime() + "." + stype;
				String root = ServletActionContext.getServletContext()
						.getRealPath(GlobalVar.FilePATH)
						+ "/" + sname;
				java.io.OutputStream os = new java.io.FileOutputStream(root);
				byte buffer[] = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
				os.close();
				is.close();
				
				a.setPicpath(sname);
				a.setPicname(a.getFileFileName());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		

		this.aisNoDao.saveAis(a);
	}

	public void updateAis(AIS a) {
		
		try {
			if (a.getFile() != null) {
				java.io.InputStream is = new java.io.FileInputStream(a
						.getFile());
				int beginIndex = a.getFileFileName().lastIndexOf('.');
				String stype = a.getFileFileName().substring(beginIndex + 1);
				String sname = (new Date()).getTime() + "." + stype;
				String root = ServletActionContext.getServletContext()
						.getRealPath(GlobalVar.FilePATH)
						+ "/" + sname;
				java.io.OutputStream os = new java.io.FileOutputStream(root);
				byte buffer[] = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
				os.close();
				is.close();
				
				a.setPicpath(sname);
				a.setPicname(a.getFileFileName());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		this.aisNoDao.updateAis(a);
	}
	
	
	
	

	public List<AIS> queryEditAis(int page,int num)
	{
		return this.aisNoDao.queryEditAis(page, num);
	}
	
	public int countAis()
	{
		return this.aisNoDao.countAis();
	}
	
	public AIS showAisInfo(String aisNo) {
		// TODO Auto-generated method stub
		List<AIS> r = this.aisNoDao.queryShipNameByEditAisNo(aisNo);

		if (r != null && r.size() > 0)
			return r.get(0);

		return null;
	}

	public void updateAisInfo(AIS a) {
		// TODO Auto-generated method stub
		this.aisNoDao.updateAisInfo(a);
	}
	
	public void updateShipnameByAisNo(AISNo aisno)
	{
		this.aisNoDao.updateShipnameByAisNo(aisno);
	}
}
