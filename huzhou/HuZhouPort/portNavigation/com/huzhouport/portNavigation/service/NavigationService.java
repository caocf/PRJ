package com.huzhouport.portNavigation.service;

import java.util.List;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.portNavigation.model.ModelPortNavigation;

public interface NavigationService extends BaseService<ModelPortNavigation> {

	public List<ModelPortNavigation> findNavigationServiceInfo(ModelPortNavigation modelPortNavigation,int totalPage,int cape)
			throws Exception;
}
