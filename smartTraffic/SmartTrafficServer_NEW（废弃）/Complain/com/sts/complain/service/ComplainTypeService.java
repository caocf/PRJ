package com.sts.complain.service;

import java.util.List;

import com.sts.complain.dao.ComplainTypeDao;
import com.sts.complain.model.ComplainType;

public class ComplainTypeService 
{
	ComplainTypeDao complainTypeDao;
		
	public ComplainTypeDao getComplainTypeDao() {
		return complainTypeDao;
	}

	public void setComplainTypeDao(ComplainTypeDao complainTypeDao) {
		this.complainTypeDao = complainTypeDao;
	}



	public List<ComplainType> GetAllComplainType()
	{
		return this.complainTypeDao.getAllComplainTypes();
	}
}
