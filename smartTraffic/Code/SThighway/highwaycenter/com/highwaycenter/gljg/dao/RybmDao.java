package com.highwaycenter.gljg.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcRybm;
import com.highwaycenter.gljg.model.HjcZw;

public interface RybmDao {
	public void save(HJcRybm rybm);
	public void deleteByry(Integer rybh);
	public void deleteBybm(String bmbh);
	public void updateRybmorder(int rybmbh,int order);
	public  List<HJcRybm> selectRybmList(int rybh);
	public List<String> selectBmByry(Integer rybh);	
	public List<GlryInfo> selectGlryBySsjgOrder(String bmbh,
			String orderkey, boolean ifdesc);
	public List<HJcGlbm> selectBmObjList(int rybh);
	public int selectZwbh(String zwmc);
	
	
	/**
     * 职位
     */
	public BaseQueryRecords selectZwlistOrder(int page,int rows);
    public void savePost(String zwmc);
    public void deletePost(int zwbh);
    public void updatePost(int zwbh ,String zwmc);
	public String selectZwstr(String zwbhs);
	public String selectzwbhs(String zwmc);
	public void updateRybmzw(int rybh,String bgsdh,String zwbh);
	public String selectAllZw();
	public int countSameName(Integer zwbh,String zwmc);
    public String selectnameByBh(String tablename, String selectKey,
			String bhkey, Object bhvalue);
	
}
