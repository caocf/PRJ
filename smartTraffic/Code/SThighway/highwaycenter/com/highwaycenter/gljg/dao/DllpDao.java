package com.highwaycenter.gljg.dao;

import java.sql.Timestamp;

import com.highwaycenter.gljg.model.HJcDllp;
import com.highwaycenter.gljg.model.HJcGlry;

public interface DllpDao {
	public void save(HJcDllp dllp);
	public void update(HJcDllp dllp);
	public void delete(HJcDllp dllp);
	public String saveAndReturn(HJcDllp dllp);
	public HJcDllp findById(String dllp_ip); 
	public int countdllp(String token);
	public void deleteByRy(Integer rybh);
	public HJcGlry findRyBylp(String token);
    public Integer rybhBylp(String token);
    public void deleteByLp(String lp);
    public String selectLpByRy(Integer rybh);
    public void updateLp(String newlp,Timestamp dlsj,
    		String dldz,Timestamp scczsj,Integer rybh);
}
