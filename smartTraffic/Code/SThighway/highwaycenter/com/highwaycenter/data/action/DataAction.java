package com.highwaycenter.data.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.data.service.DataService;
import com.highwaycenter.gljg.service.GlryService;


public class DataAction extends BaseAction{

	private static final long serialVersionUID = -3419058634465917695L;
	static Logger log = Logger.getLogger(DataAction.class);

	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="dataservice")
	private DataService dataservice;
	private BaseResult result;
	private String token; 
	private Integer page;
	private Integer rows;
	private String moduletype;
	private String time;
	private Integer xnumber;
	
	
	/**
	 * 数据同步状态查看列表
	 * @param page   页数
	 * @param rows   行数
	 * @param token  令牌 
	 * @return result.map
	 * 接口实例：http://localhost:8080/HighWayCenter/datastate/selectdatastate    
	 */
	public String selectDataState(){
		token=(token==null)?null:token.trim();
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限  data 47
   	    validate_code = this.glryservice.authorityCheck("data",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	      }
     	if(page==null||page.equals("")){
		  page = Constants.MOREN_PAGE;
	     }
        if(rows==null||rows.equals("")){
		  rows=Constants.MOREN_ROWS;
          }    
		
		/*this.result = this.dataservice.selectDataState(page, rows);*/
        //this.dataservice.readAndInsert();
	
		return "json";
	}


    /**
     * 查看数据是否正在同步(暂定2个模块)
     *@param  token
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/datastate/datasyncsystem   
     */
	public String selectCurrentState(){
		try{
		token=(token==null)?null:token.trim();
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限  data 47
   	    validate_code = this.glryservice.authorityCheck("data",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	      }
		this.result = this.dataservice.selectCurrentKettleState();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "json";
	}
	

    /**
     * 查看数据同步详情
     * @param token
     * @param moduletype     模块类型
     * @param time           查看时间        2015-03-15
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/datastate/findmoreinfo   
     */
	public String selectStateXq(){
		token=(token==null)?null:token.trim();
		try{
		//验证令牌
		int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限  data 47
   	    validate_code = this.glryservice.authorityCheck("data",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	      }
     	if(moduletype==null||moduletype.equals("")){
     		this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
     		return "json";
     	}
     	//System.out.println("time"+time);
     	 
     	 this.result = this.dataservice.selectModuleXq(moduletype,time);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	
	

    /**
     * 查看数据同步折线图
     * @param token
     * @param moduletype     模块类型
     * @param xnumber        统计时间        xnumber=1 ,2 ,3(包括当天)
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/datastate/datalinechartinfo   
     */
	public String selectStateLine(){
		token=(token==null)?null:token.trim();
		try{
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
 	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	    return "json";
     }
  	    //验证权限  data 47
   	    validate_code = this.glryservice.authorityCheck("data",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	      }
     	if(moduletype==null||moduletype.equals("")){
     		this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
     		return "json";
     	}
     	if(xnumber==null||xnumber.equals("")){
     		xnumber = 5;
     	}
     	 this.result = this.dataservice.selectModuleLine(moduletype, xnumber);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	
    /**
     * 查看数据同步成功率
     * @param token
     * @param moduletype     模块类型
     * @param time        统计成功率的开始时间         2015-03-15
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/datastate/datapiechartinfo   
     */
	public String selectSuccessRate(){
		token=(token==null)?null:token.trim();
		try{
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限  data 47
   	    validate_code = this.glryservice.authorityCheck("data",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	      }
     	if(moduletype==null||moduletype.equals("")){
     		this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
     		return "json";
     	}
     	
     	 this.result = this.dataservice.selectSuccessRate(time, moduletype);
		}catch(Exception e){
			e.printStackTrace();
		}
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


	public String getModuletype() {
		return moduletype;
	}


	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public Integer getXnumber() {
		return xnumber;
	}


	public void setXnumber(Integer xnumber) {
		this.xnumber = xnumber;
	}
	
	
	
}
