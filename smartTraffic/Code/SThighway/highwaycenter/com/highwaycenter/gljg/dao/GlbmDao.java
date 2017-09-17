package com.highwaycenter.gljg.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;


public interface GlbmDao {
	public void save(HJcGlbm glbm);
	public String saveAndReturn(HJcGlbm glbm);
	public void update(HJcGlbm glbm);
	public void delete(HJcGlbm glbm);
	public HJcGlbm findById(String glbm_id); 
	public int countById(String glbm_id);
	public BaseQueryRecords findByKey(String key, Object value,int page,int rows);
	public BaseQueryRecords findBySql(SQL sql);
    public String selectSjjg(String bmdm);
    public String selectSjjgMc(String bmdm);
    public HJcGljg selectSjjgObj(String bmdm);
    public int countSameBm(String jgdm,String bmdm,String bmmc);
    public void updateBysql(String bmdm,String bmmc,String bmdesc);
    public void updateOrderColumn(String bmdm,int ordervalue);
    public List<HJcGlbm> selectGlbmBySsjgOrder(String ssgljgdm,String orderkey,boolean ifdesc);
    
    
}
