package com.highwaycenter.gljg.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGlry;

public interface GlryDao {
	public void save(HJcGlry glry);
	public Integer saveAndReturn(HJcGlry glry);
	public void update(HJcGlry glry);
	public void delete(HJcGlry glry);
	public HJcGlry findById(int glry_id); 
	public BaseQueryRecords findByKey(String key, Object value,int page,int rows);
	public HJcGlry findByKey(String key,Object value);
	public HJcGlry findByName(String username);
	public HJcGlry findBySjch(String sjch);
	public int selectCountByGlbm(HJcGlbm glbm);  //根据部门代码查询该部门下人员数
	public HJcGlry findByBhAndSjch(int rybh,String sjch);  //根据编号和手机号查询非该人员是否有同样的手机号
	public GlryInfo selectRyInfo(int rybh);
	public BaseQueryRecords selectGlryListNoBm(int page,int rows);   //获取全部没有上级部门带有角色信息的人员列表
	public BaseQueryRecords selectGlryListNoJS(int page,int rows);   //获取全部有上级部门没有角色信息的人员列表
	public BaseQueryRecords selectGlryListNoBMJS(int page,int rows); //获取只有人员信息的列表
	public BaseQueryRecords selectGlryListByProterty(int page,int rows,String key,Object value);
	public BaseQueryRecords selectGlryListByProtertys(int page, int rows,
			List<String> keys, List<Object> values);
	public String selectRymm(Integer rybn);
	public BaseQueryRecords selectGlryList(int page,int rows,
			String bmlist,String xmpyszm,String selectvalue,int fjgFlag);
	public HJcGlry findByUsername(String username);
	public HJcGlry findByBhAndRyxm(int rybh, String ryxm);
	public int countPhoneName(String sjch,String ryxm);
	public int countBhPhoneName(int rybh,String sjch,String ryxm);

}
