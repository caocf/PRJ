package com.highwaycenter.gljg.service;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.bean.AuthoritySession;
import com.highwaycenter.gljg.model.HJcDllp;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.gljg.model.HjcZw;

public interface GlryService {
	public BaseResult login(String username, String password,Timestamp dlsj, String dldz,
			Timestamp scczsj,String isRemember);
	public String dllpSave(HJcGlry glry,Timestamp dlsj,String dldz,Timestamp scczsj);
	/**
	 * 根据用户编号返回所有基本权限
	 * @param rybh
	 * @return
	 */

	public BaseResult glrySave(HJcGlry glry,String bmdm,Integer jsbh,String zwbh,String bgsdh);
	public BaseResult glryUpdate(HJcGlry glry,String bmdm,Integer jsbh,String zwbh,String bgsdh);
	public BaseResult glryBasicUpdate(HJcGlry glry);
	public BaseResult glryDelete(Integer rybh,String token);
	public BaseResult jsToRy(Integer rybh,Integer jsbh);
	public int dllpCheck(String token);
	public BaseResult selectGlryInfo(Integer rybh);
	public BaseResult passwordUpdate(String token,String oldpw,String newpw);
	public BaseResult glryListNobm(Integer page,Integer rows);
	public BaseResult glryListByProperty(Integer page,Integer rows,String xmpyszm);
	public BaseResult pwChongzhi(Integer rybh,String newpw);
	public BaseResult logout(String token);
	public BaseResult selectUserInfo(String token);
	public BaseResult decodePassword();
	public int authorityCheck(String actionName,String token);
	public BaseResult selectZwList(int page,int rows);
	public BaseResult savePost(HjcZw zw);
	public BaseResult updatePost(HjcZw zw);
	public BaseResult deletePost(HjcZw zw);
	public AuthoritySession selectUserQx(Integer rybh);
	public BaseResult selectAllAu(String token);

}
