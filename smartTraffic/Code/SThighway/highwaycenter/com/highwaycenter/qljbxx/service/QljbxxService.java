package com.highwaycenter.qljbxx.service;

import com.common.action.BaseResult;

public interface QljbxxService {
	
	public BaseResult selectQlgkList(Integer page,Integer rows,Integer xzqhdm,String selectvalue,String selectId);
	
	public BaseResult selectQlqxList(Integer page,Integer rows);
	
	public BaseResult selectQlqx(String bzbm);

	public BaseResult selectXzqhList();

}
