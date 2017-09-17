package com.highwaycenter.gljg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.highwaycenter.gljg.dao.GlbmDao;
import com.highwaycenter.gljg.dao.GlbmgxDao;
import com.highwaycenter.gljg.dao.GljgDao;
import com.highwaycenter.gljg.dao.GlryDao;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGlbmgx;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.service.GljgService;

@Service("gljgservice")
public class GljgServiceImpl extends BaseService implements GljgService{
	
	@Resource(name="glbmdao")
	private GlbmDao glbmDao;
	@Resource(name="glbmgxdao")
	private GlbmgxDao glbmgxDao;
	@Resource(name="gljgdao")
	private GljgDao gljgDao;
	@Resource(name="glrydao")
	private GlryDao glryDao;
	@Override
	public BaseResult gljgSave(HJcGljg gljg,List<String> bmmclist) {
		
		//判断名称是否唯一   undo
		int count_mc = this.gljgDao.countByKey("gljgmc", gljg.getGljgmc());
		if(count_mc>0){
			return new BaseResult(Constants.NAME_REPEAT_CODE,Constants.NAME_REPEAT_INFO);
		}
		
		if(gljg.getSjgljgdm()!=null&&!gljg.getSjgljgdm().equals("")){
			int count_sjjg = this.gljgDao.countByKey("gljgdm", gljg.getSjgljgdm());
			if(count_sjjg==0){
				return new BaseResult(Constants.JGBH_ERROR_CODE,Constants.JGBH_ERROR_INFO);
			}
		}
		
		    if(gljg.getSfxzcfjg()==null||gljg.getSfxzcfjg().equals("")){
		    	gljg.setSfxzcfjg(1);
		    }
		    
		    String gljgbh = this.gljgDao.saveAndReturn(gljg);  //保存管理机构	
		    if(bmmclist.size()>0){
		    	//下级部门名称存在
		       for(String bmmc:bmmclist){
		    	 HJcGlbm glbm = new HJcGlbm();
		    	 glbm.setBmmc(bmmc);
		    	 glbm.setSsgljgdm(gljgbh);
		    	 this.glbmDao.save(glbm);                     //保存多个管理部门    
		       }
		    }

		    BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		    result.setObj(gljgbh);
		    return result;	
	
	}

	@Override
	public BaseResult gljgUpdate(HJcGljg gljg,String nameOnlyFlag) {
  
		HJcGljg count_mc = this.gljgDao.findByBhAndJgmc(gljg.getGljgdm(), gljg.getGljgmc());

		if(count_mc!=null){
			return new BaseResult(Constants.NAME_REPEAT_CODE,Constants.NAME_REPEAT_INFO);
		}
		
		if(gljg.getSjgljgdm()!=null&&!gljg.getSjgljgdm().equals("")){
			int count_sjjg = this.gljgDao.countByKey("gljgdm", gljg.getSjgljgdm());
			if(count_sjjg==0){
				return new BaseResult(Constants.JGBH_ERROR_CODE,Constants.JGBH_ERROR_INFO);
			}
		}
		
		if(nameOnlyFlag!=null&&nameOnlyFlag.equals("1")){//只修改名称
			
			this.gljgDao.updateName(gljg.getGljgdm(), gljg.getGljgmc());

		}else{
			 Integer xzjg = this.gljgDao.selectSfxzcfjg(gljg.getGljgdm());
			 if(gljg.getSfxzcfjg()==null||gljg.getSfxzcfjg().equals("")){
			    	gljg.setSfxzcfjg(xzjg);
			    }
			    
			//不修改机构上下级关系
			String sjgljgdm = this.gljgDao.selectSjgljgdm(gljg.getGljgdm());		
			gljg.setSjgljgdm(sjgljgdm);
	    	this.gljgDao.update(gljg);

		}	
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
					
				
	}

	@Override
	public BaseResult gljgDelete(String gljgdm) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HJcGljg gljg = this.gljgDao.findById(gljgdm);
		if(gljg==null){
			return new BaseResult(Constants.JGBH_ERROR_CODE,Constants.JGBH_ERROR_INFO);
		}
		
		//判断是否有下级机构
		List xjgljgs = this.gljgDao.findByKey("sjgljgdm", gljgdm).getData();
		if(xjgljgs.size()>0){
			return new BaseResult(Constants.XJJGBH_ERROR_CODE,Constants.XJJGBH_ERROR_INFO);
		}

		//判断是否有下级部门
		 List list = this.glbmDao.findByKey("ssgljgdm", gljgdm, -1, -1).getData();
		if(list!=null&&list.size()>0){
			//下级部门存在，不能删除该机构
			return new BaseResult(Constants.XJBMBH_ERROR_CODE,Constants.XJBMBH_ERROR_INFO);
		}else{
			result.setObj(gljg.getGljgmc());
			this.gljgDao.delete(gljg);
		    return result;
		}
	}
	
	@Override
	public BaseResult glbmSave(HJcGlbm glbm,String jgdm,String sjbmdm) {
		glbm.setOrdercolumn(0);
		BaseResult result = new BaseResult();
		try{
		if(jgdm!=null&&!jgdm.trim().equals("")){
		//验证jgdm是否存在
		HJcGljg gljg = this.gljgDao.findById(jgdm);
	    System.out.println("hql语句分割————————————————————————————————————");
		  if(gljg==null){
			//此管理机构不存在，返回错误信息
			result.setResultcode(Constants.JGBH_ERROR_CODE);
			 result.setResultdesc(Constants.JGBH_ERROR_INFO);
			 return result;
		  }
		  //验证机构下是否有同名部门
		  int namecount = this.glbmDao.countSameBm(jgdm,null,glbm.getBmmc() );
		  if(namecount>0){
			  return new BaseResult(Constants.BMMCC_RENAME_CODE,Constants.BMMCC_RENAME_INFO);
		  }
			glbm.setSsgljgdm(gljg.getGljgdm());
		}
		
		if(sjbmdm!=null&&!sjbmdm.trim().equals("")){
			//验证该部门下是否有同名子部门
			int namecount = this.glbmgxDao.countSameBm(sjbmdm, null, glbm.getBmmc());
			  if(namecount>0){
				  return new BaseResult(Constants.BMMCC_RENAME_CODE,Constants.BMMCC_RENAME_INFO);
			  }
		  }		
			String glbmdm = this.glbmDao.saveAndReturn(glbm);
			if(sjbmdm!=null&&!sjbmdm.trim().equals("")){
				//新增上下级部门关系
				HJcGlbmgx glbmgx = new HJcGlbmgx(glbmdm,sjbmdm);
				this.glbmgxDao.save(glbmgx);
			}
			
			 result.setResultcode(Constants.SUCCESS_CODE);
			 result.setResultdesc(Constants.SUCCESS_INFO);
			 result.setObj(glbmdm);
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return result;		
 	}
	
	@Override
	public BaseResult glbmUpdate(HJcGlbm glbm) {
		BaseResult result = new BaseResult();
		//判断是子部门还是父部门
	    HJcGlbm bmnew  = this.glbmDao.findById(glbm.getBmdm());  
	    if(bmnew==null){
	    	return new BaseResult(Constants.BMBH_ERROR_CODE,Constants.BMBH_ERROR_INFO);
	    }
		//验证重名
		if(bmnew.getSsgljgdm()==null||bmnew.getBmdm().trim().equals("")){
			//是子部门，查找上级父部门
			String  sql = "select sjbmdm from h_jc_glbmgx where bmdm ='"+glbm.getBmdm()+"'";
			String fbmdm = this.glbmgxDao.findUniqueBySql(new SQL(sql));
			if(fbmdm==null||fbmdm.equals("")){
				return new BaseResult(Constants.BMBH_ERROR_CODE,Constants.BMBH_ERROR_INFO);
			}
			//在关系表中查询同名部门
			int countname = this.glbmgxDao.countSameBm(fbmdm, glbm.getBmdm(), glbm.getBmmc());
			if(countname>0){
				return new BaseResult(Constants.BMMCC_RENAME_CODE,Constants.BMMCC_RENAME_INFO);
			}
			
		}else{
			//是父机构，在机构表中验证
			int countname = this.glbmDao.countSameBm(bmnew.getSsgljgdm(), glbm.getBmdm(), glbm.getBmmc());
			if(countname>0){
				return new BaseResult(Constants.BMMCC_RENAME_CODE,Constants.BMMCC_RENAME_INFO);
			}
		}
		this.glbmDao.updateBysql(glbm.getBmdm(),glbm.getBmmc(),glbm.getBmdesc());
			 result.setResultcode(Constants.SUCCESS_CODE);
			 result.setResultdesc(Constants.SUCCESS_INFO);
		
		return result;		
 	}
	
	@Override
	public BaseResult glbmDelete(HJcGlbm glbm) {	
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HJcGlbm glbmNew = this.glbmDao.findById(glbm.getBmdm());
		if(glbmNew==null){
			return new BaseResult(Constants.BMBH_ERROR_CODE,Constants.BMBH_ERROR_INFO);
		}		
		//验证该管理部门下是否挂着管理人员
		int glrycount = this.glryDao.selectCountByGlbm(glbm);
		if(glrycount>0){
			//该部门下还有管理人员，不能直接删除
			return new BaseResult(Constants.RY_EXIT_CODE,Constants.RY_EXIT_INFO);
		}
		
		//判断是否有下级关系
		int xjbmcount = this.glbmgxDao.count("sjbmdm", glbm.getBmdm());
		if(xjbmcount>0){
			return new  BaseResult(36,"下级部门存在,不能删除");
		}
		
		
		   this.glbmgxDao.deleteByBmdm(glbm.getBmdm());
		   result.setObj(glbmNew.getBmmc());
		   //删除作为上级的上下级部门关系
			this.glbmDao.delete(glbmNew);		
			return result;			
	
	   
		
 	}
}
