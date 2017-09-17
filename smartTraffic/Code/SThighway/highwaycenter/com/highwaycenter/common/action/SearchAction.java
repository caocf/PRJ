package com.highwaycenter.common.action;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.common.service.SerchService;
import com.highwaycenter.gljg.service.GlryService;


public class SearchAction extends BaseAction{
	static Logger log = Logger.getLogger(SearchAction.class);
	private static final long serialVersionUID = 2043069297430404804L;
	private BaseResult result;
	private String type;
	private String token; 
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="searchservice")
	private SerchService searchService;
	private String keyname;
	private String condition;
	private Integer page;
	private Integer rows;
	
	
	/**
	 * 根据分类获取不同对象的高级查询字段
	 * @param type   类型  1、路网  2、视频 3.1、服务区 3.2、收费站 4、桥梁 5、标志标线 6、公路站 8、交通量
	 * @param token  令牌 
	 * @return result.map
	 * 接口实例：http://localhost:8080/HighWayCenter/advancedsearch/advancedsearch     
	 */
	public String advancedSearch(){	
		
		 if(type==null||type.equals("")){
			 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			 return "json";
		 }
       
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
 	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
 	    //验证权限
 	    validate_code = this.checkAuthority(type, token); 
 	     if(validate_code!=Constants.SUCCESS_CODE){
 	    	this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
 	     }
 	     
		
		this.result = this.searchService.advancedSearch(type);
		
		return "json";
	}
	
	
	/**
	 * 根据分类获取不同对象的高级查询字段
	 * @param type   类型  1、路网  2、视频 3.1、服务区 3.2、收费站 4、桥梁 5、标志标线 6、公路站 8、交通量
	 * @param token  令牌
	 * @param keyname 下拉列表的key
	 * @return result.map
	 * 接口实例：http://localhost:8080/HighWayCenter/advancedsearch/searchselect     
	 */
	public String searchSelect(){

		 if(type==null||type.equals("")||keyname==null||keyname.equals("")){
			 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			 return "json";
		 }
		 
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
	    //验证权限
	    validate_code = this.checkAuthority(type, token); 
	     if(validate_code!=Constants.SUCCESS_CODE){
	    	this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	     }
	     this.result = this.searchService.searchSelect(type, keyname);
		
		return "json";
	}
	
	/**
	 * 高级查询
	 * @param type   类型  1、路网  2、视频 3.1、服务区 3.2、收费站 4、桥梁 5、标志标线 6、公路站 8、交通量
	 * @param token  令牌
	 * @param condition 条件
	 * @param page
	 * @param rows
	 * @return result.map
	 * 接口实例：http://localhost:8080/HighWayCenter/advancedsearch/selectbycondition   
	 */
	public String selectListByCondition(){
		 if(type==null||type.equals("")||condition==null||condition.equals("")){
			 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			 return "json";
		 }
		 
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
	    //验证权限
	    validate_code = this.checkAuthority(type, token); 
	     if(validate_code!=Constants.SUCCESS_CODE){
	    	this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	     }
	     	if(page==null||page.equals("")){
	  		  page = Constants.MOREN_PAGE;
	  	     }
	          if(rows==null||rows.equals("")){
	  		  rows=Constants.MOREN_ROWS;
	            }    
	  		
	     this.result = this.searchService.selectListByCondition(type, condition, page, rows);
		return "json";
	}
	
	
	private int checkAuthority(String type,String token){
			int validate_code;
	        if(type.equals("4")){ //桥梁信息       
	       	 validate_code = this.glryservice.authorityCheck("qlgklist",token);  
	        }else if(type.equals("1")){//路网信息
	       
	       	 validate_code = this.glryservice.authorityCheck("lxgklist",token);
	       	
	       
	        }else if(type.equals("3.1")){//服务区
	       
	         validate_code = this.glryservice.authorityCheck("fwqlist",token);
	         
	        }else if(type.equals("3.2")){//收费站
	        
	            validate_code = this.glryservice.authorityCheck("sfzlist",token);
	             
	        }else if(type.equals("5")){//标志标线
	        	
	            validate_code = this.glryservice.authorityCheck("bzbxlist",token);
	            
	        }else if(type.equals("6")){//公路站信息（基本信息+详情）
	        	
	            validate_code = this.glryservice.authorityCheck("glzgklist",token);
	             
	        }else if(type.equals("8")){//交调站基本信息
	        	
	            validate_code = this.glryservice.authorityCheck("dczxxlist",token);
	           
	        }else{
	        	validate_code = Constants.AUTHORITY_CHECK_FAIL_CODE;
	        }
	        return validate_code;
	}
	
	

	public BaseResult getResult() {
		return result;
	}


	public void setResult(BaseResult result) {
		this.result = result;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public String getKeyname() {
		return keyname;
	}


	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getRows() {
		return rows;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
	}

	

}
