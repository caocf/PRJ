package com.sts.complain.service;

import com.sts.complain.dao.ComplainDao;
import com.sts.complain.model.Complain;

public class ComplainService 
{
	ComplainDao complainDao;
	
	public ComplainDao getComplainDao() {
		return complainDao;
	}

	public void setComplainDao(ComplainDao complainDao) {
		this.complainDao = complainDao;
	}


	public boolean save(Complain complain)
	{
		return complainDao.save(complain);
	}
}
