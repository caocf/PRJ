package com.highwaycenter.gljg.action;
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
import com.common.utils.PropertyLoader;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.service.GljgListService;
import com.highwaycenter.gljg.service.GljgService;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;

/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/gljglist")*/  // 人员管理路径
@SuppressWarnings("all") //忽略该类中的所有警告
public class GljgListAction extends BaseAction{
	private static final long serialVersionUID = 3203388088952250686L;
	@Resource(name="gljgservice")
	private GljgService gljgService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="gljglistservice")
	private GljgListService gljglistService;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private Integer page;
	private Integer rows;
	private HJcGljg gljg;
	private HJcGlbm glbm;
	private String token;
	private BaseResult result;
	private String xmpyszm;
	private String selectvalue;
	private String nameOnlyFlag;
    private String glbmdm;
    private String gljgdm;
    private String orderlist;  //部门排序集合
    private String sfzxcfjg;// 0：不是行政处罚机构 1：是行政处罚机构
	


	/**
	 *  获取机构列表
	 *  @param page    所请求的页数
	 *  @param rows    所请求的行数
	 *  @param token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/gljglist       
	 */
	/*@Action(value = "gljglist", results = { @Result(name = "json", type = "json")})*/
	public String gljgList(){	
		
		System.out.println("获取机构列表");
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("gljglist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
		this.result = this.gljglistService.selectjgAll(sfzxcfjg);
		
		System.out.println("获取机构列表");
		//记录日志信息
	/*	this.czrzService.saveCzrz(token,"查看管理机构");//基本权限 1.查看管理机构
*/		return "json";
		
		
	}

	
	/**
	 *  根据父机构获取机构列表
	 *  @param gljg.gljgdm    
	 *  @param token
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/gljglistbyfjg       
	 */
	/*@Action(value = "gljglistbyfjg", results = { @Result(name = "json", type = "json")})*/
	public String gljgListByFjg(){	
		//
		try{
		this.result = this.gljglistService.selectZgljgByFjg(gljg.getGljgdm());
		return "json";
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";
		
	}


	/**
	 *  根据机构获取部门列表
	 *  @param gljg.gljgdm    
	 *  @param token 
	 *  @return 
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/glbmlistbyfjg&token=?&gljg.gljgdm=402881e94c2d23fd014c2d25d1aa0000       
	 */
	/*@Action(value = "glbmlistbyfjg", results = { @Result(name = "json", type = "json")})*/
	public String glbmListByFjg(){
		System.out.println("根据机构获取部门列表");
		try{
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
     	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glbmlistbyfjg",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
   	    //令牌验证结束
   	     if(gljg==null||gljg.getGljgdm()==null||gljg.getGljgdm().trim().equals("")){
   	    	 //请求机构参数错误
   	    	 this.result = new BaseResult(Constants.JGBH_NULL_CODE,Constants.JGBH_NULL_INFO);
   	    	 return "json";
   	     }
   	    gljg.setGljgdm(gljg.getGljgdm().trim());
		this.result = this.gljglistService.selectBmByJg(gljg.getGljgdm());
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("根据机构获取部门列表");
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看部门");//基本权限 2.查看部门
*/		return "json";
		
	}


	
	/**
	 *  根据部门获取人员列表
	 * @param glbm.bmdm 
	 * @param gljg.gljgdm  
	 * @param page   
	 * @param rows
	 * @param xmpyszm
	 * @param selectvalue
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/glrylistbybm?glbm.bmdm=1&xmpyszm=L&tokens=?&page=&rows=?&selectvalue=?       
	 */
	/*@Action(value = "glrylistbybm", results = { @Result(name = "json", type = "json")})*/
	public String gllryListByBm(){	
		try{
		/*	//验证令牌
			 int validate_code = this.glryservice.dllpCheck(token);
	   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 		    return "json";
	 	     }
	   	     
	   	//验证权限
	    	 validate_code = this.glryservice.authorityCheck("glrylistbybm",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }*/
			if(page==null||page.equals("")){
					page = Constants.MOREN_PAGE;
				}
			if(rows==null||rows.equals("")){
					rows=Constants.MOREN_ROWS;
			}
		if(glbm==null){
			glbm = new HJcGlbm();
		}
		if(gljg==null){
			gljg = new HJcGljg();
		}
		xmpyszm=(xmpyszm==null)?null:xmpyszm.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		this.result = this.gljglistService.selectRyByBm(glbm.getBmdm(),gljg.getGljgdm(), page, rows, xmpyszm,selectvalue);
	
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看管理人员");//基本权限 3.查看管理人员
*/		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	
	/**
	 *  根据部门编号获取一级下级部门列表（名称，编号）
	 * @param glbmdm  管理部门代码
	 * @param gljgdm  管理机构代码
	 * @param token   令牌
	 * @return  result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/selectorderlist?glbmdm=?&gljgdm=?&tokens=?
	 */
	/*@Action(value = "selectorderlist", results = { @Result(name = "json", type = "json")})*/
	public String selectOrderList(){	
		try{
			/*//验证令牌
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
	    	 */
			
	   	  if(glbmdm==null&&gljgdm==null){
				this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			    return "json";
			}
			
		
	
		this.result = this.gljglistService.selectOrderList(glbmdm,gljgdm);
	
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看管理人员");//基本权限 3.查看管理人员
*/		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	/**
	 *  根据部门编号获取人员列表（名称，编号）
	 * @param glbmdm  管理部门代码
	 * @param gljgdm  管理机构代码
	 * @param token   令牌
	 * @return  result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/selectryorderlist?glbmdm=?&gljgdm=?&tokens=?
	 */
	/*@Action(value = "selectryorderlist", results = { @Result(name = "json", type = "json")})*/
	public String selectRyOrderList(){	
		try{
			//验证令牌
			/* int validate_code = this.glryservice.dllpCheck(token);
	   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 		    return "json";
	 	     }
	   	  //令牌验证结束
	    	 validate_code = this.glryservice.authorityCheck("glryupdate",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }*/
	    	 			
	   	  if(glbmdm==null){
				this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			    return "json";
			}

		this.result = this.gljglistService.selectRyOrderList(glbmdm);
	
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看管理人员");//基本权限 3.查看管理人员
*/		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";
	}
   /**
    * 根据列表信息获取排序信息
    * @param orderlist  排序字段String
	* @param token   令牌
	* @return  result.resultcode 1
	* 接口实例：http://localhost:8080/HighWayCenter/gljglist/updateorderlist?orderlist=?&token=?
    */
	public String updateOrderList(){
		/* int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 validate_code = this.glryservice.authorityCheck("glbmupdate",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	 orderlist = (orderlist==null)?null:orderlist.trim();
    	 if(orderlist==null||orderlist.equals("")){
    		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		 return "json";
    	 }
		
    	 List<String> ordercolumn = new ArrayList<String> ();
    	 ordercolumn = InputFomartConversion.springSplitStringlist(orderlist);
		
    	 this.result = this.gljglistService.updateOrderList(ordercolumn);
		return "json";
	}
	
	
	 /**
	    * 根据列表信息获取人员排序
	    * @param orderlist  排序字段String
		* @param token   令牌
		* @return  result.resultcode 1
		* 接口实例：http://localhost:8080/HighWayCenter/gljglist/updateorderlist?orderlist=?&token=?
	    */
		public String updateRyOrderList(){
			/* int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 validate_code = this.glryservice.authorityCheck("glryupdate",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }*/
	    	 orderlist = (orderlist==null)?null:orderlist.trim();
	    	 if(orderlist==null||orderlist.equals("")){
	    		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
	    		 return "json";
	    	 }
			
	    	 List<String> ordercolumn = new ArrayList<String> ();
	    	 ordercolumn = InputFomartConversion.springSplitStringlist(orderlist);
			
	    	 this.result = this.gljglistService.updateRyOrderList(ordercolumn);
			return "json";
		}
	/**
	 *  获取机构信息
	 *  @param gljg.gljgdm    
	 *  @param token 
	 *  @return 
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/findjginfo?token=?&gljg.gljgdm=402881e94c2d23fd014c2d25d1aa0000       
	 */
	
	/*@Action(value = "findjginfo", results = { @Result(name = "json", type = "json")})*/
	public String findJgInfo(){
		
			System.out.println("获取机构信息");
			/*//验证令牌
			 int validate_code = this.glryservice.dllpCheck(token);
	   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 		    return "json";
	 	     }
	   	 //令牌验证结束
	   	//验证权限
	    	 validate_code = this.glryservice.authorityCheck("findjginfo",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }*/
	   	     if(gljg==null||gljg.getGljgdm()==null||gljg.getGljgdm().trim().equals("")){
	   	    	 //请求机构参数错误
	   	    	 this.result = new BaseResult(Constants.JGBH_NULL_CODE,Constants.JGBH_NULL_INFO);
	   	    	 return "json";
	   	     }
			this.result = this.gljglistService.selectJgInfo(gljg.getGljgdm());
			
		System.out.println("获取机构信息");
		//记录日志信息
		/*this.czrzService.saveCzrz(token,"查看管理机构");//基本权限 1.查看管理机构
*/			return "json";
	}
	
	
	/**
	 * 获取部门基本信息
	 * @param glbmdm   部门代码
	 * @param token    令牌
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/gljglist/selectbmxq?token=?&glbmdm=?
	 */
	public String selectBmXq(){
		System.out.println("获取机构信息");
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }*/
     	 //令牌验证结束
		this.result = this.gljglistService.selectBmXq(glbmdm);
		
		return "json";
	}
	
	/**
	 * 获取全部管理机构的基本信息列表
	 * @return
	 */
	public String selectGljgList(){
		System.out.println("获取机构信息");
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
    	//验证权限
   	  validate_code = this.glryservice.authorityCheck("gljglist",token);
   	 if(validate_code != Constants.SUCCESS_CODE){
   		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	     }*/
   	 
   	     if(sfzxcfjg!=null&&sfzxcfjg.equals("1")){
   	    	this.result = this.gljglistService.selectXzcfGljg();
   	     }else{
		    this.result = this.gljglistService.selectGljgList(); 
   	     }
		return "json";
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

	public HJcGljg getGljg() {
		return gljg;
	}

	public void setGljg(HJcGljg gljg) {
		this.gljg = gljg;
	}

	public HJcGlbm getGlbm() {
		return glbm;
	}

	public void setGlbm(HJcGlbm glbm) {
		this.glbm = glbm;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}
	
	public String getXmpyszm() {
		return xmpyszm;
	}


	public void setXmpyszm(String xmpyszm) {
		this.xmpyszm = xmpyszm;
	}


	public String getSelectvalue() {
		return selectvalue;
	}


	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}


	public String getNameOnlyFlag() {
		return nameOnlyFlag;
	}


	public void setNameOnlyFlag(String nameOnlyFlag) {
		this.nameOnlyFlag = nameOnlyFlag;
	}


	public String getGlbmdm() {
		return glbmdm;
	}


	public void setGlbmdm(String glbmdm) {
		this.glbmdm = glbmdm;
	}


	public String getGljgdm() {
		return gljgdm;
	}


	public void setGljgdm(String gljgdm) {
		this.gljgdm = gljgdm;
	}


	public String getOrderlist() {
		return orderlist;
	}


	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}


	public String getSfzxcfjg() {
		return sfzxcfjg;
	}

	public void setSfzxcfjg(String sfzxcfjg) {
		this.sfzxcfjg = sfzxcfjg;
	}
	
	

}
