package com.sts.repair.service;

import com.sts.repair.dao.CallHelpDao;
import com.sts.repair.model.CallHelp;


public class CallHelpService {

	CallHelpDao callHelpDao;
	public void setCallHelpDao(CallHelpDao callHelpDao) {
		this.callHelpDao = callHelpDao;
	}
	
	public void save(CallHelp c)
	{
		this.callHelpDao.save(c);
	}
}
