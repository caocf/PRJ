package com.huzhouport.apply.dao.impl;

import java.util.List;

import com.huzhouport.apply.dao.ApplyDao;
import com.huzhouport.apply.model.Apply;
import com.huzhouport.common.dao.impl.BaseDaoImpl;

public class ApplyDaoImpl extends BaseDaoImpl<Apply> implements ApplyDao {

	public String addApplyInfo(Apply apply) throws Exception {
		this.hibernateTemplate.save(apply);
		return null;
	}

	public int countPageApplyInfo(Apply apply, String condition)
			throws Exception {
		String hql = "select count(*) from Apply a ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(apply, hql);
	}

	public List<?> searchApplyInfo(Apply apply, String condition,
			String sequence, int startSet, int maxSet) throws Exception {
		String hql = "  from Apply a ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(apply, hql,
				startSet, maxSet);
		return list;
	}

	public List<?> seeApplyID(Apply apply) throws Exception {
		String hql = "  from Apply a where a.preapplyID='"+apply.getPreapplyID()+"'";
		return this.hibernateTemplate.find(hql);
	}

}
