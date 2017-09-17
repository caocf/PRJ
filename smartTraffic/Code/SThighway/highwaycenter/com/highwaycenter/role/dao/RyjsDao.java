package com.highwaycenter.role.dao;


import java.util.List;

import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.role.model.HJcRyjs;

public interface RyjsDao {
	public void save(HJcRyjs ryjs);
	public void update(HJcRyjs ryjs);
	public void delete(HJcRyjs ryjs);
	public HJcRyjs findByGlry(HJcGlry glry);
	public int  selectCountByJsbh(int jsbh);
    public void deleteByRy(Integer rybh);
    public Integer findjsbhByRy(Integer rybh);
    public List<Integer> findRylistByJs(Integer jsbh);
}
