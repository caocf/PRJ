package com.highwaycenter.gljg.action;
//xiugai
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.utils.InputFomartConversion;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.service.GljgService;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;


/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/jgmanager")  // 机构部门管理路径
*/
@SuppressWarnings("all") //忽略该类中的所有警告
public class GljgManagerAction extends BaseAction{
	private static final long serialVersionUID = 4383801753267917786L;
	
	@Resource(name="gljgservice")
	private GljgService gljgService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private String token;
	private HJcGlbm glbm;    // glbm.bmmc  部门名称
	private HJcGljg gljg;    //管理机构
	private String jgdm;     //机构代码
    private BaseResult result;
    private String glbmList ;//新增管理机构的时候输入的管理部门
    private String sjbmdm;   //上级部门代码
	private String nameOnlyFlag;  //判断是否只编辑name
	/**
	 * 新增部门
	 * @param  glbm.bmmc   部门名称
	 * @param  jgdm  所属管理机构代码
	 * @param  sjbmdm  上级部门代码
	 * @return 部门新增结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/glbmsave?glbm.bmmc=政务公开科室&jgdm=?&sjbmdm=?
	 */
   /*  @Action(value = "glbmsave", results = { @Result(name = "json", type = "json")})*/
	 public String glbmSave(){
    	 
    	//验证令牌
    	 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	 //令牌验证结束
     	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glbmsave",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		//判断部门名称是否为空
		if(glbm==null||glbm.getBmmc()==null||glbm.getBmmc().trim().equals("")){
		
			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		}else{
			this.result = this.gljgService.glbmSave(glbm,jgdm,sjbmdm);				
		}
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		//记录日志信息
		  this.czrzService.saveCzrz(token,"新增部门","新增部门——“"+glbm.getBmmc()+"”");//基本权限 7.新增部门
		}
		return "json";
	}
    
    /**
	 * 更新部门
	 * @param  glbm.bmdm   部门代码
	 * @param  glbm.bmmc   部门名称
	 * @return 部门更新结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/glbmupdate?glbm.bmdm=402881fb4c2b8994014c2b89ae340000&glbm.bmmc=接待部啦&jgdm=402881fb4c2ab2c5014c2ab554b70004
	 */
   /* @Action(value = "glbmupdate", results = { @Result(name = "json", type = "json")})*/
	public String glbmUpdate(){  
    	//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	 validate_code = this.glryservice.authorityCheck("glbmupdate",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 
    	//判断部门名称是否为空
 		if(glbm==null||glbm.getBmmc()==null||glbm.getBmmc().trim().equals("")||glbm.getBmdm()==null||glbm.getBmdm().trim().equals("")){
 		
 			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
 		}else{
 			glbm.setBmdm(glbm.getBmdm().trim());
 			glbm.setBmmc(glbm.getBmmc().trim());
			this.result = this.gljgService.glbmUpdate(glbm);				
		}
 		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
 		//记录日志信息
 		 this.czrzService.saveCzrz(token,"编辑部门","编辑部门——“"+glbm.getBmmc()+"”");//基本权限 8.编辑部门
 		}
		return "json";
	}
    
    /**
	 * 删除部门   
	 * @param glbm.bmdm  管理机构代码
	 * @return  机构删除结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/glbmdelete?glbm.bmdm=402881e94c2d23fd014c2d26dbf50001&token=ZrlgdDwU
	 */
  /*  @Action(value = "glbmdelete", results = { @Result(name = "json", type = "json")})*/
   	public String glbmDelete(){
    	//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 validate_code = this.glryservice.authorityCheck("glbmdelete",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
    	if(glbm==null||glbm.getBmdm()==null||glbm.getBmdm().trim().equals("")){
    		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
    	    return "json";
    	}
   	    this.result = this.gljgService.glbmDelete(glbm);
   	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
 		//记录日志信息
		 this.czrzService.saveCzrz(token,"删除部门","删除部门——“"+(String)result.getObj()+"”");//基本权限 9.删除部门
   	 }
   		return "json";
   	}
    

	/**
	 * 新增机构    允许添加下级部门名称
	 * @param token
	 * @param gljg.gljgmc  管理机构名称
	 * @param gljg.sjgljgdm  上级管理机构代码
	 * @param gljg.lxdh    联系电话
	 * @param gljg.lxdz    联系地址
	 * @param gljg.jd      经度
	 * @param gljg.wd     纬度
	 * @param glbmList    下级部门名称   该下级部门名称输入格式控制：XX科,XX科,XX科,XX科
	 * @return  机构新增结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/gljgsave?gljg.gljgmc=嘉兴市公路管理局&gljg.lxdh=0573-82226078&gljg.lxdz=嘉兴市经济开发区开禧路46号
	 */
  /*  @Action(value = "gljgsave", results = { @Result(name = "json", type = "json")})*/
	public String gljgSave(){
    	
    	
    	//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	 validate_code = this.glryservice.authorityCheck("gljgsave",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	//System.out.println("传入前："+gljg.getGljgmc()+" "+gljg.getLxdh()+" "+gljg.getLxdz());
		//判断部门名称是否为空
		if(gljg==null||gljg.getGljgmc()==null||gljg.getGljgmc().trim().equals("")){
			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		}else{
			List<String> bmmclist = new ArrayList<String> ();
			if(glbmList!=null&&!glbmList.trim().equals("")){
				bmmclist = InputFomartConversion.springSplitStringlist(glbmList);
			}
			this.result = this.gljgService.gljgSave(gljg, bmmclist);		
		}
		System.out.println("存数据库后："+gljg.getGljgmc()+" "+gljg.getLxdh()+" "+gljg.getLxdz());
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		//记录日志信息
		this.czrzService.saveCzrz(token,"新增管理机构","新增管理机构——“"+gljg.getGljgmc()+"”");//基本权限4.新增管理机构
		}
		return "json";
	}
    
    
    /**
	 * 更新机构   不允许修改下级部门名称
	 * @param gljg.gljgdm  管理机构代码
	 * @param gljg.gljgmc  管理机构名称
	 * @param gljg.sjgljgdm  上级管理机构代码
	 * @param gljg.lxdh    联系电话
	 * @param gljg.lxdz    联系地址
	 * @param gljg.jd      经度
	 * @param gljg.wd     纬度
	 * @return  机构更新结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/gljgupdate?gljg.gljgdm=402881fb4c2abd1d014c2abd6a320000
	 * &gljg.gljgmc=乌镇公路管理局&gljg.lxdh=0573-82226078123&gljg.lxdz=嘉兴市经济开发区开禧路46号abc&nameOnlyFlag=1
	 */
   /* @Action(value = "gljgupdate", results = { @Result(name = "json", type = "json")})*/
	public String gljgUpdate(){
    	try{
    	//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 validate_code = this.glryservice.authorityCheck("gljgupdate",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
    	 if(gljg==null||gljg.getGljgmc()==null||gljg.getGljgmc().trim().equals("")){
 			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
 		}else{
			this.result = this.gljgService.gljgUpdate(gljg,nameOnlyFlag);		
		}
		System.out.println("存数据库后："+gljg.getGljgmc()+" "+gljg.getLxdh()+" "+gljg.getLxdz());
    	}catch(java.lang.Exception e){
    		e.printStackTrace();
    	}
    	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	//记录日志信息
    	this.czrzService.saveCzrz(token,"编辑管理机构","编辑管理机构——“"+gljg.getGljgmc()+"”");//基本权限5.编辑管理机构
    	}
		return "json";
	}
      
    /**
	 * 删除机构   
	 * @param gljg.gljgdm  管理机构代码
	 * @return  机构删除结果
	 * 接口实例：http://localhost:8080/HighWayCenter/jgmanager/gljgdelete?gljg.gljgdm=402881fb4c2af9b6014c2afdd62a0000
	 */
    /*@Action(value = "gljgdelete", results = { @Result(name = "json", type = "json")})*/
   	public String gljgDelete(){

    	//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 validate_code = this.glryservice.authorityCheck("gljgdelete",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 if(gljg==null||gljg.getGljgdm()==null||gljg.getGljgdm().trim().equals("")){
    		 this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
    	     return "json";
    	 }
    	 //令牌验证结束
   	    this.result = this.gljgService.gljgDelete(gljg.getGljgdm());
   	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	//记录日志信息
    	this.czrzService.saveCzrz(token,"删除管理机构","删除管理机构——“"+(String)result.getObj()+"”");//基本权限6.删除管理机构
   	 }
   		return "json";
   	}

    
	public HJcGlbm getGlbm() {
		return glbm;
	}

	public void setGlbm(HJcGlbm glbm) {
		this.glbm = glbm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}
	
	public String getGlbmList() {
		return glbmList;
	}

	public void setGlbmList(String glbmList) {
		this.glbmList = glbmList;
	}

	public HJcGljg getGljg() {
		return gljg;
	}

	public void setGljg(HJcGljg gljg) {
		this.gljg = gljg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSjbmdm() {
		return sjbmdm;
	}

	public void setSjbmdm(String sjbmdm) {
		this.sjbmdm = sjbmdm;
	}

	public String getNameOnlyFlag() {
		return nameOnlyFlag;
	}

	public void setNameOnlyFlag(String nameOnlyFlag) {
		this.nameOnlyFlag = nameOnlyFlag;
	}
	

}
