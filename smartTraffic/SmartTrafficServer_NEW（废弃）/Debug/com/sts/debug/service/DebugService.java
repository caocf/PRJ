package com.sts.debug.service;

import java.util.List;

import com.sts.debug.dao.DebugDao;
import com.sts.debug.model.Debug;

public class DebugService 
{
	DebugDao debugDao;
	
	public DebugDao getDebugDao() {
		return debugDao;
	}

	public void setDebugDao(DebugDao debugDao) {
		this.debugDao = debugDao;
	}

	public boolean save(Debug debug)
	{
		return this.debugDao.save(debug);
	}
	
	public List<Debug> getALLDebugs()
	{		 
		return this.debugDao.getAllDebugs();
	}
}
