package com.sts.smartbus.service;

import java.util.List;

import com.sts.smartbus.dao.LineTraceDao;
import com.sts.smartbus.model.QRCode;
import com.sts.smartbus.model.Trace;

public class LineTraceService 
{
	LineTraceDao lineTraceDao;
	public void setLineTraceDao(LineTraceDao lineTraceDao) {
		this.lineTraceDao = lineTraceDao;
	}
	
	public String queryTrace(int id,int direct)
	{
		List<Trace> resultTrace= lineTraceDao.queryTrace(id, direct);
		
		String result="";
		
		for(Trace t:resultTrace)
		{
			result+=t.getLon()+","+t.getLan()+";";
		}
		
		return result;
	}
	
	public QRCode queryQRCode(String qr)
	{
		List<QRCode> r=this.lineTraceDao.queryQR(qr);
		
		if(r!=null && r.size()>0)
			return r.get(0);
		
		return null;
	}
}
