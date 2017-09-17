package com.highwaycenter.log.service;

import com.common.action.BaseResult;
import com.highwaycenter.log.model.HJcCzrz;

public interface CzrzService {
	public BaseResult saveCzrz(HJcCzrz czrz,String token);
	public BaseResult deleteCzrz(int rzbh);
	public BaseResult selectCzrzListAll(int page,int rows);
	public BaseResult selectCzrzInfo(int rzbh);
	public BaseResult saveCzrz(String token,String operateTitle,String operateContent);
    public BaseResult selectCzrzList(int page,int rows,String startTime,String endTime,String selectvalue);	
    public BaseResult deleteCzrzList(String rzbhlist);
    public BaseResult excelCreat(String startTime,String endTime, String selectvalue); //
    
}
