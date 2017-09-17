package com.highwaycenter.lxjbxx.action;

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
import com.highwaycenter.lxjbxx.service.LxjbxxService;

/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/lxjbxxlist")  */
@SuppressWarnings("all") //忽略该类中的所有警告
public class LxjbxxListAction extends BaseAction{

	private static final long serialVersionUID = -8302399651825079285L;
	static Logger log = Logger.getLogger(LxjbxxListAction.class);
	@Resource(name="lxjbxxservice")
	private LxjbxxService lxjbxxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private BaseResult result;
	private Integer page;
	private Integer rows;
	private String token;
	private String bzbm;   //路线标识编码
	private Integer xzqhdm;
	private String selectvalue;
	private String selectId;  //路线标志编码
	private String intru1;//简介1
	private String intru2;//简介2
	
	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	/**
	 * 路线概况列表
	 * @param   page
	 * @param   rows
	 * @param   xzqhdm       行政区划代码
	 * @param   selectvalue  搜索框搜索值
	 * 
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/lxgklist?token=?&xzqhdm=?&selectvalue=?  
	 */
	/*@Action(value = "lxgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectLxgkList(){

		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 进入路线概况列表");
		log.trace("进入路线概况列表");
		try{
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //验证权限
    	 validate_code = this.glryservice.authorityCheck("lxgklist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	 
    	 //令牌验证结束
        
    	if(page==null||page.equals("")){
    		page  = Constants.MOREN_PAGE;
    	}
    	if(rows==null||rows.equals("")){
    		rows = Constants.MOREN_ROWS;
    	}
    	
		this.result = this.lxjbxxService.selectLxgkList(page, rows,xzqhdm,selectvalue,selectId);
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
    	 System.out.println(" 路线概况列表end");
 		log.trace("路线概况列表end");
 		//记录日志信息
 		/*this.czrzService.saveCzrz(token,"查看路线");*///基本权限 26.查看路线
		return "json";
	}

	/**
	 * 路线详情列表
	 * @param   page
	 * @param   rows
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/lxqxlist?token=?  
	 */
	/*@Action(value = "lxqxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectLxxqList(){
		
		System.out.println(" 进入路线详情列表");
		log.trace("进入路线详情列表");
		
		/*//验证令牌
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
    	
		this.result = this.lxjbxxService.selectLxqxList(page, rows);
		
    	 System.out.println(" 路线详情列表end");
 		log.trace("路线详情列表end");
 		/*this.czrzService.saveCzrz(token,"查看路线");*///基本权限 26.查看路线
		return "json";
	}
	
	
	
	/**
	 * 路线详情
	 * @param   bzbm
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/lxqx?token=?&bzbm=?  
	 */
	/*@Action(value = "lxqx", results = { @Result(name = "json", type = "json")})*/
	public String selectLxxq(){
		
		System.out.println(" 进入路线详情列表");
		log.trace("进入路线详情列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("lxqx",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	if(bzbm==null||bzbm.trim().equals("")){
    		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
    		return "json";
    	}
    		
		this.result = this.lxjbxxService.selectLxqx(bzbm);
    	 System.out.println(" 路线详情列表end");
 		log.trace("路线详情列表end");
 		/*this.czrzService.saveCzrz(token,"查看路线");*///基本权限 26.查看路线
		return "json";
	}
	
	
	/**
	 * 行政区划列表
	 * @param   page
	 * @param   rows
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/xzqhlist?token=?  
	 */
	/*@Action(value = "xzqhlist", results = { @Result(name = "json", type = "json")})*/
	public String selectXzqhList(){
		
		System.out.println(" 进入行政区划列表");
		log.trace("进入行政区划列表");
		try{
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束  	
*/		this.result = this.lxjbxxService.selectXzqhList();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
    	 System.out.println(" 行政区划列表end");
 		log.trace("行政区划列表end");
		return "json";
	}

	
	
	/**
	 * 桥梁路线简介搜索
	 * @param  token  
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/lwintroduce  
	 * @return
	 */
	public String selectLxqlIntroduce(){
		System.out.println("进入桥梁路线简介页面");
		log.trace("进入桥梁路线简介页面");
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }*/
    	//暂时不验证权限
		//获取简介
    	 this.result = this.lxjbxxService.selectLwjc();
    	//获取用户是否有编辑权限
    	// validate_code = this.glryservice.authorityCheck("lxintroduce",token);
   // 	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result.setObj(-1); //-1是无权限
   	    // }else{
   	    ////	this.result.setObj(1);  //1是有权限
   	   //  }
		return "json";

	}
	
	
	
	/**
	 * 桥梁路线简介编辑
	 * @param  token  
	 * @param  intru1  
	 * @param  intru2  
	 * 接口实例：http://localhost:8080/HighWayCenter/lxjbxxlist/editlwintroduce  
	 * @return
	 */
	public String editLxqlIntroduce(){
		System.out.println("编辑桥梁路线简介");
		log.trace("编辑桥梁路线");
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
     	//验证权限
    	 validate_code = this.glryservice.authorityCheck("lxintroduce",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 this.result = this.lxjbxxService.editLwjc(intru1, intru2); 
    	 this.czrzService.saveCzrz(token, "编辑路网简介", "编辑路网简介");
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

	public String getIntru1() {
		return intru1;
	}

	public void setIntru1(String intru1) {
		this.intru1 = intru1;
	}

	public String getIntru2() {
		return intru2;
	}

	public void setIntru2(String intru2) {
		this.intru2 = intru2;
	}
	
	
	

}
