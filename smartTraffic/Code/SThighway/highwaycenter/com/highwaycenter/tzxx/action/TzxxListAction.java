package com.highwaycenter.tzxx.action;

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
import com.highwaycenter.lxjbxx.action.LxjbxxListAction;
import com.highwaycenter.tzxx.service.TzxxService;
/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/tzxxlist") */ // 通阻信息列表路径
@SuppressWarnings("all") //忽略该类中的所有警告
public class TzxxListAction extends BaseAction{

	private static final long serialVersionUID = -2689359837576196514L;
	static Logger log = Logger.getLogger(TzxxListAction.class);
	@Resource(name="tzxxservice")
	private TzxxService tzxxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	private BaseResult result;
	private Integer page;
	private Integer rows;
	private String token;
	private String bzbm;   //路线标识编码
	private Integer xzqhdm;
	private String selectvalue;
	private String columnId;//通阻信息类别
	private String main_id;  //通阻信息id

	/**
	 * 通阻信息列表
	 * @param   page
	 * @param   rows
	 * @param   columnId  类别id
	 * @param   selectvalue  检索字段
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/tzxxlist/tzxxlist?token=?  
	 */
	/*@Action(value = "tzxxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectTzxxList(){
		
		columnId=(columnId==null)?null:columnId.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println("通阻信息列表");
		log.trace("通阻信息列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("tzxxlist",token);
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
    	
		this.result = this.tzxxService.selectTzxxList(page, rows, columnId, selectvalue);
		return "json";
	}
	
	
	
	/**
	 * 通阻信息详情
	 * @param   main_id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/tzxxlist/tzxxxq?token=?&main_id=?  
	 */
	/*@Action(value = "tzxxxq", results = { @Result(name = "json", type = "json")})*/
	public String selectTzxxXq(){
		
		System.out.println(" 进入路线详情列表");
		log.trace("进入路线详情列表");
		
		//验证令牌
	/*	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("tzxxxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	if(main_id==null||main_id.trim().equals("")){
    		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
    		return "json";
    	}
    		*/
		this.result = this.tzxxService.selectTzxxXq(main_id);
		return "json";
	}
	
	/**
	 * 通阻类别列表
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/tzxxlist/tzcolumnlist?token=?&bzbm=?  
	 */
	/*@Action(value = "tzcolumnlist", results = { @Result(name = "json", type = "json")})*/
	public String selectColumnList(){
		
		System.out.println("通阻类别列表");
		log.trace("通阻类别列表");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
        */
    	
		this.result = this.tzxxService.selectTzxxColumnList();
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



	public String getColumnId() {
		return columnId;
	}



	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}



	public String getMain_id() {
		return main_id;
	}



	public void setMain_id(String main_id) {
		this.main_id = main_id;
	}
	
	
	
	
	

}
