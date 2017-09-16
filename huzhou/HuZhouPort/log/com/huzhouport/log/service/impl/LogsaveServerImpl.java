package com.huzhouport.log.service.impl;



import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import org.hibernate.Session;


import com.huzhouport.log.dao.Dao;
import com.huzhouport.log.model.Log;
import com.huzhouport.log.service.LogsaveServer;








public class LogsaveServerImpl implements  LogsaveServer{
	private Dao dao;
	

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public boolean logsave( String logUser,
			String logContent, int partOfStyle,int isApp,String bz) {    //操作人  ， 日志内容 ， 日志类型         注 日志类型 ： 1代表 登录，  2 代表 删除 ， 3代表添加 ，  4代表修改 ， 5代表退出       ； 是否是app日志 ： 1app日志 ，0pc日志
		Log log=new Log();
		log.setLogUser(logUser);
		log.setLogContent(logContent);
		log.setPartOfStyle(partOfStyle);
		log.setIsApp(isApp);
		log.setBz(bz);
		
		Calendar c=Calendar.getInstance();
		Date d=c.getTime();
		String a=d.toLocaleString();
		log.setLogTime(a);
		boolean abc=dao.logsave(log);
		
		return abc;
		
		
	}

	

	
	

}
