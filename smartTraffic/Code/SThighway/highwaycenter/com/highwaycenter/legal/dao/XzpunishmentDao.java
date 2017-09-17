package com.highwaycenter.legal.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.legal.model.HXzPunishment;

public interface XzpunishmentDao {
	
	public void save(HXzPunishment punishment);
	public void delete(int xzcfbh);
	public void update(HXzPunishment punishment);
	public BaseQueryRecords selectXzcfList(int page,int rows
			,String xzcfjgdm,String selectKey, String selectValue);
	
	public int countSameNameByWh(String whname,Integer xzcfbh,Integer isdelete);
	public HXzPunishment selectXzPunishmentXq(int xzcfbh);
	public void updateState(Integer state,int xzcfbh );
	public Integer selectState(int xzcfbh);
    public Object selectValueByKey(String key,int xzcfbh);
    public List<HXzPunishment> selectXzlistByState(int state);
    public void updateStateAll(Integer state,String zxwhlist);
    public Integer selectcountSameByWh(String whname,Integer isdelete);
    public void updateStateByxzwh(Integer state,String wh );
    public List<selectListBean> selectList(String tablename,String keycolumn,String valuecolumn);
	public void updateDataVersion();
	public void updateStatesByCondition(int state,String condition);
    
}
