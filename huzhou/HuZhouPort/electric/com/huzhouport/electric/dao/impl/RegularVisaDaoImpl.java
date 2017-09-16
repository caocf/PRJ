package com.huzhouport.electric.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electric.dao.RegularVisaDao;
import com.huzhouport.electric.model.RegularVisa;

public class RegularVisaDaoImpl extends BaseDaoImpl<RegularVisa> implements RegularVisaDao{

	public String addRegularVisaInfo(RegularVisa regularVisa) throws Exception {
		this.hibernateTemplate.save(regularVisa);
		return null;
	}

	public List<?> seeRegularVisaID(RegularVisa regularVisa) throws Exception {
		String hql = "  from RegularVisa r where r.regularvisaID='"+regularVisa.getRegularvisaID()+"'";
		return this.hibernateTemplate.find(hql);
	}

	public int countPageRegularVisaInfo(RegularVisa regularVisa,
			String condition) throws Exception {
		String hql = "select count(*) from RegularVisa r ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(regularVisa, hql);
	}

	public List<?> searchRegularVisaInfo(RegularVisa regularVisa,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		String hql = "  from RegularVisa r ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(regularVisa, hql,
				startSet, maxSet);
		return list;
	}

}
