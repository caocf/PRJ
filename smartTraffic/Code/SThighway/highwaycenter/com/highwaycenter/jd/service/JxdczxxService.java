package com.highwaycenter.jd.service;

import com.common.action.BaseResult;

public interface JxdczxxService {

	public BaseResult selectJxdczxx(String dczbh);
	
	public BaseResult selectOptionList(String sql);
	
	public BaseResult selectJxdczxxSimple(String dczbh);
	
	public BaseResult selectJxdczxxList(String condition,Integer page,Integer rows,String selectId);
	
	public BaseResult selectTransportDataList(int page,int rows,
			String sjy,String sjm,String sjd,String sjhy,String sjseason);
	
	public void saveTransportData();
	
	public BaseResult selectTransDataTimeCS();
}
