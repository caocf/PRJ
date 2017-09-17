package com.highwaycenter.common.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;

public class AuthorityCheckAction extends BaseAction{

	private static final long serialVersionUID = -8938039299066307478L;
	static Logger log = Logger.getLogger(AuthorityCheckAction.class);

	@Resource(name="glryservice")
	private GlryService glryservice;
	private BaseResult result;
	private String token;
	private String autname;  //权限名字
	
	
	
	/**
	 * 根据权限自定义的名字（从authority.properties读取）查看用户是否有该权限
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/authoritycheck/checkbyname
	 */
	public String checkAuthorityByname(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
	     //验证权限  
	   	    validate_code = this.glryservice.authorityCheck(autname,token);
	     	 if(validate_code != Constants.SUCCESS_CODE){
	   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	  		    return "json";
	  	      }
	     	this.result =  new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		return "json";
	}
	
	/**
	 * 用户是否有统计分析的权限
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/authoritycheck/checkstatictis
	 */
	
    public String checkStatisticsAuthority(){
    	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
	     //验证权限 54 查看统计分析
	   	    validate_code = this.glryservice.authorityCheck("cktjfx",token);
	     	 if(validate_code != Constants.SUCCESS_CODE){
	   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	  		    return "json";
	  	    }
	     	this.result =  new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		return "json";
	}
    
	
	/**
	 * 获取用户全部权限
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/authoritycheck/selectallaut
	 * 
	 */
	public String selectAllAut(){
		
		this.result = this.glryservice.selectAllAu(token);
		
		return "json";
	}
	
	public BaseResult getResult() {
		return result;
	}
	public void setResult(BaseResult result) {
		this.result = result;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getAutname() {
		return autname;
	}

	public void setAutname(String autname) {
		this.autname = autname;
	} 
	
	
	
	
	

}
