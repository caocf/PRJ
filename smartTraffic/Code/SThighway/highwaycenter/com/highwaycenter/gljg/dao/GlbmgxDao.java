package com.highwaycenter.gljg.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.TreeNodes;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGlbmgx;

public interface GlbmgxDao {
	public void save(HJcGlbmgx glbmgx);
	public void delete(HJcGlbmgx glbmgx);
	public BaseQueryRecords findBySql(SQL sql);
	public int count(String key,Object value);
	public int deleteByBmdm(String bmdm);
	public List<TreeNodes> findxjbmNode(String sjbmdm);
	public String findUniqueBySql(SQL sql);
	public int countSameBm(String sjbmdm,String bmdm,String bmmc);
	public List<HJcGlbm> selectbmlist(String sjbmdm);

}
