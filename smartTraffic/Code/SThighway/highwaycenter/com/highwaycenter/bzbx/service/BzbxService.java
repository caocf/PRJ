package com.highwaycenter.bzbx.service;

import java.io.File;

import com.common.action.BaseResult;
import com.highwaycenter.bzbx.model.HBzbxBzbx;

public interface BzbxService {

	public BaseResult selectBzbxGkList(int page,int rows,
			Integer xzqhdm,Integer bzbxlxbh,String selectValue,String selectId);
	
	public BaseResult selectBzbxlxList();
	
	public BaseResult selectBzbxXQ(Integer bzbxbh);
	
	public BaseResult saveBzbx(HBzbxBzbx bzbx,File uploadFile,String uploadFileFileName);
	
	public BaseResult updateBzbx(HBzbxBzbx bzbx,File uploadFile,String uploadFileFileName);
	
	public BaseResult deleteBzbx(Integer bzbxbh);
	
	public BaseResult selectChineseStandard();
}
