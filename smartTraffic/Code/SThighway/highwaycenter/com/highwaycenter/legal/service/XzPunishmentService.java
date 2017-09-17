package com.highwaycenter.legal.service;

import com.common.action.BaseResult;
import com.highwaycenter.legal.model.HXzPunishment;

public interface XzPunishmentService {
	
	public BaseResult saveXzPunishment(HXzPunishment punishment);
	
	public BaseResult updateXzPunishment(HXzPunishment punishment);
	
	public BaseResult selectXzPunishmentList(int page,int rows,String selectkey,String selectValue,String xzcfjgdm);

	public BaseResult deleteXzPunishment(int xzcfbh);
	
	public BaseResult selectXzPunishmentXq(int xzcfbh);

	
	public BaseResult selectOptionList(String type);
}
