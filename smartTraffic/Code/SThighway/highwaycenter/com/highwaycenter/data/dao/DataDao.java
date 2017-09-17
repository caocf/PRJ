package com.highwaycenter.data.dao;

import java.util.Date;
import java.util.List;

import com.common.dao.BaseQueryRecords;


public interface DataDao {
	
	public BaseQueryRecords selectDataState(int page,int rows);
	
	public Date  selectCurrentMaxTime(String transName);
	
	/**
	 * 统计同步数据
	 */
	//public SnapshotRecord selectTotalLine(String transName);
	
	/**
	 * 删除数据
	 */
	public void deleteModuleData(String transName);
	
	/**
	 * 查询正在跑的数据
	 * @param transName
	 * @return
	 */
	public List<String>  selectRunTransname();
}
