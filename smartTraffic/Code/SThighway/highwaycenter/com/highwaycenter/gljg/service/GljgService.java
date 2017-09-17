package com.highwaycenter.gljg.service;
//xiugai
import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;

public interface GljgService {
	
	public BaseResult gljgSave(HJcGljg gljg,List<String> bmmclist);
	public BaseResult gljgUpdate(HJcGljg gljg,String gljgUpdate);
	public BaseResult gljgDelete(String gljgdm);
	public BaseResult glbmSave(HJcGlbm glbm,String jgdm,String sjbmdm);
	public BaseResult glbmUpdate(HJcGlbm glbm);  //只修改部门名字
	public BaseResult glbmDelete(HJcGlbm glbm);

}
