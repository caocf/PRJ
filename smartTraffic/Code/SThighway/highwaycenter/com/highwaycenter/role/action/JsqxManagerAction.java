package com.highwaycenter.role.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.utils.InputFomartConversion;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcJsqx;

import com.highwaycenter.role.service.JsqxService;


@SuppressWarnings("all") //忽略该类中的所有警告
public class JsqxManagerAction extends BaseAction{

	static Logger log = Logger.getLogger(JsqxManagerAction.class);
	private static final long serialVersionUID = 8765058717751439230L;
	@Resource(name="jsqxservice")
	private JsqxService jsqxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private String token;
	private BaseResult result;
	private String qxlist;          //权限编号的集合  每个权限编号以","分割
	private HJcJsqx jsqx;
	private HJcJbjsb js;
	private Integer rows;
	private Integer page;
	
	/**
	 * 版本2角色接口1：新增角色 
	 * @param  js.jsmc    角色名称
	 * @param qxlist 权限集合
	 * @param token  令牌
	 * @return  新增角色的结果  角色编号  
	 * 接口实例：http://localhost:8080/HighWayCenter/jsqxmanager/jssave?js.jsmc=?                 
	 */
	public String jsSave(){	
		
	    log.debug("新增角色start-------------------");

	   	//验证令牌
   	     int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     } //令牌验证结束
    	 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("jssave",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		 if(js==null||js.getJsmc()==null||js.getJsmc().trim().equals("")){
			//角色名称为空
			this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);	
		    return "json";
		}	  
		 js.setJsmc(trimAll(js.getJsmc()));
		 qxlist = trimAll(qxlist);
		 //将权限集合存入list 
	     List<Integer> qxbhList = new ArrayList<Integer>();	
		 if(qxlist!=null&&!qxlist.equals("")){
		    qxbhList = InputFomartConversion.springSplitIntegerlist(qxlist);
	      }
    
	      this.result= this.jsqxService.jsSave(js,qxbhList); 
		log.debug("新增角色over-------------------");	
		System.out.println("新增角色over-------------------");
		//记录日志信息
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		this.czrzService.saveCzrz(token,"新增角色","新增角色——“"+js.getJsmc()+"”");//基本权限 16.新增角色
		}
		return "json";
	}
	
	/**版本2角色接口2：编辑角色
	 * 更新角色 
	 * @param  js.jsbh    角色编号
	 * @param  js.jsmc    角色名称
	 * @param qxlist 权限集合
	 * @return result 角色更新结果  
	 * 接口实例：http://localhost:8080/HighWayCenter/jsqxmanager/jsupdate?js.jsbh=?&js.jsmc=?&qxlist=?                 
	 */
	public String jsUpdate(){
		log.debug("更新角色start-------------------");	
		//验证令牌
   	     int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("jsupdate",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
		if(js==null||js.getJsbh()==null||js.getJsbh().equals("")||js.getJsmc()==null||js.getJsmc().trim().equals("")){
			//角色编号名称为空
			this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);	
			return "json";
		}
		//trim
		js.setJsmc(trimAll(js.getJsmc()));
		qxlist = trimAll(qxlist);
		 //将权限集合存入list 
	     List<Integer> qxbhList = InputFomartConversion.springSplitIntegerlist(qxlist); 
	     this.result= this.jsqxService.jsUpdate(js,qxbhList); 
		
		log.debug("更新角色over-------------------");	
		System.out.println("更新角色over-------------------");	
		//记录日志信息
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		this.czrzService.saveCzrz(token,"编辑角色","编辑角色——“"+js.getJsmc()+"”");//基本权限 17.编辑角色
		}
		return "json";
	}
	
	
	/**版本2角色接口3：删除角色
	 * 删除角色 
	 * @param  token
	 * @param  js.jsbh    角色编号
	 * @return result
	 * 接口实例：http://localhost:8080/HighWayCenter/jsqxmanager/jsdelete?js.jsbh=2                 
	 */
	public String jsDelete(){
		log.debug("删除角色-------------------");	
		//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("jsdelete",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
		if(js==null||js.getJsbh()==null||js.getJsbh().equals("")){
			//角色编号为空
			this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			return "json";
		}
			
		    this.result= this.jsqxService.jsDelete(js.getJsbh()); 
		//记录日志信息
		    if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		this.czrzService.saveCzrz(token,"删除角色","删除角色——“"+(String)result.getObj()+"”");//基本权限 18.删除角色
		    }
		return "json";
		
	}

	
	
	/**
	 * 版本2角色接口4：获取角色列表及角色权限集合描述
	 * @param token    
     * @param page    所请求的页数
	 * @param rows    所请求的行数
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/jsqxmanager/jslistall?page=1&rows=10                  
	 */
	/*@Action(value = "jslistall", results = { @Result(name = "json", type = "json")})*/
	public String jsListAll(){
		try{
			//验证令牌
		   	 int validate_code = this.glryservice.dllpCheck(token);
		     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		  		    return "json";
		  	  }
		   //验证权限
	    	 validate_code = this.glryservice.authorityCheck("jslistall",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }
		if(page==null||page.equals("")){
			//默认系统页数1
			page = Constants.MOREN_PAGE;
		}
		if(rows == null||rows.equals("")){
			//默认系统行数10
			rows = Constants.MOREN_ROWS;
		}
		this.result = this.jsqxService.jsListAll(page, rows);
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看角色");*///基本权限 14.查看角色
		return "json";
		
	}
	
	/**人员模块接口：获取角色列表做人员编辑下拉框
	 * 获取角色列表
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/jsqxmanager/jslist?token=?                 
	 */
	/*@Action(value = "jslist", results = { @Result(name = "json", type = "json")})*/
	public String jsList(){
		try{
			//验证令牌
		   	 int validate_code = this.glryservice.dllpCheck(token);
		    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		  		    return "json";
		  	     }
	
		this.result = this.jsqxService.jslist();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";
		
	}
	
	
	public HJcJsqx getJsqx() {
		return jsqx;
	}



	public void setJsqx(HJcJsqx jsqx) {
		this.jsqx = jsqx;
	}

	
	
	public BaseResult getResult() {
		return result;
	}


	public void setResult(BaseResult result) {
		this.result = result;
	}





	public String getQxlist() {
		return qxlist;
	}





	public void setQxlist(String qxlist) {
		this.qxlist = qxlist;
	}





	public HJcJbjsb getJs() {
		return js;
	}





	public void setJs(HJcJbjsb js) {
		this.js = js;
	}
	



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public Integer getRows() {
		return rows;
	}



	public void setRows(Integer rows) {
		this.rows = rows;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}


	protected String trimAll(String trimstring){
		 trimstring = (trimstring==null)?null:trimstring.trim();
		 return  trimstring;
	}
	

}
