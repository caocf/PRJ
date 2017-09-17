package com.highwaycenter.glz.service;
//46update
import java.sql.Timestamp;

import com.common.action.BaseResult;

public interface GlzxxService {

	public BaseResult selectGlzXq(String id);
	
	public BaseResult selectGlzgklist(Integer page,Integer rows,String gljgdm,String selectvalue,String selectId);
	
	public BaseResult selectProperteList(String key1,String key2);
	
	public BaseResult selectPropertyListByJbxx(String key1,String key2);
	
	public BaseResult selectGyldgkList(Integer page,Integer rows,String stationId,String selectvalue);
		
	public BaseResult selectGyldXq(String id);
	
	public BaseResult selectDwrygkList(Integer page,Integer rows,String stationId,String selectvalue);
	
	public BaseResult selectDwryXq(String id);
	
	public BaseResult selectGlzQlgkList(Short removeMake,Integer page,Integer rows,String stationId,String selectvalue);
	
	public BaseResult selectGlzQlgkQx(String id);
	
	public BaseResult selectGlzHdgkList(Short removeMake,Integer page,Integer rows,String stationId,String selectvalue);
	
	public BaseResult selectGlzHdgkQx(String id);
	
	public BaseResult selectWorkDateList();
	
	public BaseResult selectDbYscqktjxx(int page, int rows,String stationId, String workDate);
	
	public BaseResult selectXcjlList(int page,int rows,String stationId,String workDate);
	
	public BaseResult selectXcjlXq(String id);
	
	public BaseResult selectInspectDateList();
	
	public BaseResult selectQljcxjcList(Integer page,Integer rows,String stationId,String bridgeId,String selectvalue);
	
	public BaseResult selectQljcxjxMx(String id);
	
	public BaseResult selectQlNameList();
	
	public BaseResult selectHdjcxjcList(Integer page,Integer rows,String stationId,String culverId,String selectvalue);
	
	public BaseResult selectHdjcxjxMx(String id);
	
	public BaseResult selectHdCodeList();
	
	
}
