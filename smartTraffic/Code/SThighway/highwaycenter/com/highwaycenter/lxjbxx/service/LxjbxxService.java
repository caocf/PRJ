package com.highwaycenter.lxjbxx.service;

import com.common.action.BaseResult;

public interface LxjbxxService {
	
	public BaseResult selectLxgkList(Integer page,Integer rows,Integer xzqhdm,String selectvalue,String selectId);
	
	public BaseResult selectLxqxList(Integer page,Integer rows);
	
	public BaseResult selectLxqx(String bzbm);

	public BaseResult selectXzqhList();
	
	public BaseResult selectLwjc();
	
	public BaseResult editLwjc(String jc1,String jc2);
	
}
