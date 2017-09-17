package com.highwaycenter.glz.dao;
//67update
import java.sql.Timestamp;
import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.selectListBean;

public interface DbYscqktjxxDao {
	public BaseQueryRecords selectYsctjxxList(int page,int rows,String stationId, String workDate);

	public List<selectListBean> selectWorkDateList();
}
