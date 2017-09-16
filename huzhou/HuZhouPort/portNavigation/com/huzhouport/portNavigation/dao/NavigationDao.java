package com.huzhouport.portNavigation.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.portNavigation.model.ModelPortNavigation;

public interface NavigationDao extends BaseDao<ModelPortNavigation> {

	public List<ModelPortNavigation> findSameInfo(
			ModelPortNavigation modelPortNavigation) throws Exception;
	
	public List<ModelPortNavigation> findNavigationInfo(ModelPortNavigation modelPortNavigation,int startSet, int maxSet) throws Exception;
}
