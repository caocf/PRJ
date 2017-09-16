package com.huzhouport.log.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;


import com.huzhouport.log.dao.Dao;
import com.huzhouport.log.model.Log;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.Server;








public class ServerImpl implements Server{
	private Dao dao;
	
	
	
	
//	public Dao getDao() {
//		return dao;
//	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	
	


	public PageModel<Log> findByScrollServer(int currentPage, int maxPage,String action) { //日志分页
		// TODO Auto-generated method stub
		String hql=" from Log l,LogStyle ls where l.partOfStyle=ls.styleId order by l.logTime desc";
		System.out.println("hql="+hql);
		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	
	public void delete(String id){  //日志删除
		String hql="delete Log l where l.logId= "+id;
		System.out.println("hql=="+hql);
		dao.delete(hql);
	}
	public PageModel<Log> findByScrollServer1(int currentPage,int maxPage ,String action,String loguser,String logtime,String logcontent,String stylename){ //日志查找
		String hql=" from Log l,LogStyle ls where l.partOfStyle=ls.styleId";
		if(loguser.length()!=0){
			hql=hql+" and logUser like '%"+loguser+"%'";
		}
		if(logtime.length()!=0){
			hql=hql+" and logTime like '%"+logtime+"%'";
		}
		if(logcontent.length()!=0){
			hql=hql+" and logContent like '%"+logcontent+"%'";
		}
		if(stylename.length()!=0){
			hql=hql+" and styleName like '%"+stylename+"%'";
		}
		hql=hql+" order by l.logTime desc"; 
		System.out.println("hql=="+hql);
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}


}
