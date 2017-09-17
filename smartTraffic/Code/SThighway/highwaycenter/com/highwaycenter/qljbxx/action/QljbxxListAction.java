package com.highwaycenter.qljbxx.action;

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
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
import com.highwaycenter.qljbxx.service.QljbxxService;
/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/qljbxxlist") */ 
@SuppressWarnings("all") //忽略该类中的所有警告
public class QljbxxListAction extends BaseAction{

	static Logger log = Logger.getLogger(QljbxxListAction.class);
	@Resource(name="qljbxxservice")
	private QljbxxService qljbxxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private BaseResult result;
	private Integer page;
	private Integer rows;
	private String token;
	private String bzbm;   //桥梁标识编码
	private Integer xzqhdm;
	private String selectvalue;//检索字段
	private String selectId; //桥梁编号
	private static final long serialVersionUID = -7982171251184373295L;
	
	/**
	 * 获取行政区划列表
	 *  @param  token
	 * @return  result.list
	 *  接口实例：http://localhost:8080/HighWayCenter/qljbxxlist/xzqhlist?token=?  
	 */
	/*@Action(value = "xzqhlist", results = { @Result(name = "json", type = "json")})*/
	public String selectXzqhList(){
		try{
		System.out.println(" 行政区划列表");
		log.trace("行政区划列表");
	/*
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
        */
    	
		this.result = this.qljbxxService.selectXzqhList();
		//记录日志信息
		}catch(Exception e){
			e.printStackTrace();
		}
		return "json";
		
		
	}
	
	
	/**
	 * 桥梁概况列表
	 * @param   page
	 * @param   rows
	 * @param   xzqhdm
	 * @param selectvalue
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/qljbxxlist/qlgklist?token=?&xzqhdm=?&selectvalue=?  
	 */
	/*@Action(value = "qlgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectQlgkList(){
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 进入桥梁概况列表");
		log.trace("进入桥梁概况列表");
		try{
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("qlgklist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	if(page==null||page.equals("")){
    		page  = Constants.MOREN_PAGE;
    	}
    	if(rows==null||rows.equals("")){
    		rows = Constants.MOREN_ROWS;
    	}
    	
		this.result = this.qljbxxService.selectQlgkList(page, rows,xzqhdm,selectvalue,selectId);
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
    	 System.out.println(" 桥梁概况列表end");
 		log.trace("桥梁概况列表end");
 		/*this.czrzService.saveCzrz(token,"查看桥梁");*///基本权限 27.查看桥梁
		return "json";
	}

	/**
	 * 桥梁详情列表
	 * @param   page
	 * @param   rows
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/qljbxxlist/qlqxlist?token=?  
	 */
	/*@Action(value = "qlqxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectQlxqList(){
		
		System.out.println(" 进入桥梁详情列表");
		log.trace("进入桥梁详情列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
*/        
    	if(page==null||page.equals("")){
    		page  = Constants.MOREN_PAGE;
    	}
    	if(rows==null||rows.equals("")){
    		rows = Constants.MOREN_ROWS;
    	}
    	
		this.result = this.qljbxxService.selectQlqxList(page, rows);
		
    	 System.out.println(" 桥梁详情列表end");
 		log.trace("桥梁详情列表end");
 		/*this.czrzService.saveCzrz(token,"查看桥梁");*///基本权限 27.查看桥梁
		return "json";
	}
	
	
	
	/**
	 * 桥梁详情
	 * @param   bzbm
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/qljbxxlist/qlqx?token=?&bzbm=?  
	 */
	/*@Action(value = "qlqx", results = { @Result(name = "json", type = "json")})*/
	public String selectQlxq(){
		
		System.out.println(" 进入桥梁详情列表");
		log.trace("进入桥梁详情列表");
		/*
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	 validate_code = this.glryservice.authorityCheck("qlqx",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	if(bzbm==null||bzbm.trim().equals("")){
    		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
    		return "json";
    	}
    		
		this.result = this.qljbxxService.selectQlqx(bzbm);
    	 System.out.println(" 桥梁详情列表end");
 		log.trace("桥梁详情列表end");
 		/*this.czrzService.saveCzrz(token,"查看桥梁");*///基本权限 27.查看桥梁
		return "json";
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBzbm() {
		return bzbm;
	}

	public void setBzbm(String bzbm) {
		this.bzbm = bzbm;
	}








	public Integer getXzqhdm() {
		return xzqhdm;
	}


	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}


	public String getSelectvalue() {
		return selectvalue;
	}




	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}


	public String getSelectId() {
		return selectId;
	}


	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	
	
	
	
	
	

}
