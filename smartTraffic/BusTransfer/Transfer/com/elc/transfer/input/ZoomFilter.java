package com.elc.transfer.input;

import com.elc.transfer.entity.Request;

/**
 * 区域范围过滤器（过滤嘉兴范围外的经纬度）
 * @author Administrator
 *
 */
public class ZoomFilter extends BaseFilter{

	/*
	 * 有效区域范围
	 */
	public double available_lan1;
	public double available_lan2;
	public double available_lon1;
	public double available_lon2;
	
	@Override
	public boolean doFilter(Request r) {
		
		return false;
	}

}
