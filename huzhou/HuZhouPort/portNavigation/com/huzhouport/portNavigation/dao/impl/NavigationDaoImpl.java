package com.huzhouport.portNavigation.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.portNavigation.dao.NavigationDao;
import com.huzhouport.portNavigation.model.ModelPortNavigation;

public class NavigationDaoImpl extends BaseDaoImpl<ModelPortNavigation>
		implements NavigationDao {

	@SuppressWarnings("unchecked")
	public List<ModelPortNavigation> findSameInfo(
			ModelPortNavigation modelPortNavigation) throws Exception {
		String hql = "from ModelPortNavigation m where m.titile='"
				+ modelPortNavigation.getTitile() + "' and m.date='"
				+ modelPortNavigation.getDate() + "' and m.url='"
				+ modelPortNavigation.getUrl() + "'";
		return this.hibernateTemplate.find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<ModelPortNavigation> findNavigationInfo(
			ModelPortNavigation modelPortNavigation, int startSet, int maxSet)
			throws Exception {

		String hql = "from ModelPortNavigation m ";
		if (null != modelPortNavigation.getTitile()
				&& !modelPortNavigation.getTitile().equals("")) {
			hql += " where m.titile like '%" + modelPortNavigation.getTitile()
					+ "%'";
		}
		List<ModelPortNavigation> list = (List<ModelPortNavigation>) this
				.queryqueryEForeignKey(modelPortNavigation, hql, startSet,
						maxSet);
		return list;
	}

}
