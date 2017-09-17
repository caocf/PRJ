package com.highwaycenter.log.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.CzrzInfo;
import com.highwaycenter.log.model.HJcCzrz;

public interface CzrzDao {
	public void saveCzrz(HJcCzrz czrz);
	public void deleteCzrz(HJcCzrz czrz);
	public HJcCzrz findInfoByRzbh(int czrzbh);
	public HJcCzrz findByRzbh(int czrzbh);
	public List <CzrzInfo> excelInputCzrz(String startTime,String endTime, String selectvalue);
	public void deleteByRy(Integer rybh);
	//public BaseQueryRecords findByProterty(int page,int rows);
	public BaseQueryRecords selectCzrzList(int page, int rows, String startTime,String endTime, String selectvalue);	
	public void deletList(String list);

}
