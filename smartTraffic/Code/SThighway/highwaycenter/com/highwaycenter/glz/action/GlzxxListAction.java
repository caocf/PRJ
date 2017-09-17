package com.highwaycenter.glz.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.glz.model.HGlzGlzXxxx;
import com.highwaycenter.glz.service.GlzxxService;
import com.highwaycenter.log.service.CzrzService;
/*@Controller  
@ParentPackage("cement-interceptor")
@Namespace("/glzxxlist") */ //公路站信息
@SuppressWarnings("all") //忽略该类中的所有警告
public class GlzxxListAction extends BaseAction{

	private static final long serialVersionUID = 4989933114308182940L;
	static Logger log = Logger.getLogger(GlzxxListAction.class);
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="glzxxservice")
	private GlzxxService glzxxservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private BaseResult result;
	private Integer page;
	private Integer rows;
	private String token;
	private HGlzGlzXxxx glzXxxx;
	private String id;
    private String selectvalue;
	private String stationId;
	private String workDate;
	private String bridgeId;
	private String culverId;
	private String gljgdm;
	private String selectId;
	//1.公路站信息列表、详情。
	/**
	 * 公路站详情
	 * @param   id  (在你的list里面应该是xxxxId)
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzxxxq?token=?&id=?  
	 */
/*	@Action(value = "glzxxxq", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzxxXq(){
		
		System.out.println(" 进入公路站详情");
		log.trace("进入公路站详情");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glzxxxq",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
		this.result = this.glzxxservice.selectGlzXq(id);
    	 System.out.println(" 进入公路站详情end");
 		log.trace("进入公路站详情end");
 		//记录日志信息
 		/*this.czrzService.saveCzrz(token,"查看标志标线");//基本权限 29.查看公路站
*/		return "json";
	}

	/**
	 * 公路站概况列表
	 * @param   page
	 * @param   rows
	 * @param   gljgdm
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzgklist?token=?&page=?&rows=?&gljgdm=? 
	 */
	/*@Action(value = "glzgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzgkList(){
		
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 进入公路站详情列表");
		log.trace("进入公路站详情列表");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glzgklist",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
         */
     	if(page==null||page.equals("")){
     		page  = Constants.MOREN_PAGE;
     	}
     	if(rows==null||rows.equals("")){
     		rows = Constants.MOREN_ROWS;
     	}
     	
		this.result = this.glzxxservice.selectGlzgklist(page,rows,gljgdm,selectvalue,selectId);
 		//记录日志信息
 		/*this.czrzService.saveCzrz(token,"查看标志标线");//基本权限 29.查看公路站
*/		return "json";
	}

	/**
	 * 公路站名列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glznamelist?token=? 
	 */
	/*@Action(value = "glznamelist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlznameList(){
		
		System.out.println(" 进入公路站名列表");
		log.trace("进入公路站名列表");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
*/		this.result = this.glzxxservice.selectPropertyListByJbxx("id", "name");
    	 System.out.println(" 公路站名列表end");
 		log.trace("公路站名列表end");
		return "json";
	}
	
	//2.管养路段列表、详情。
	/**
	 * 管养路段概况列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/gyldgklist?token=? 
	 */
	/*@Action(value = "gyldgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectGyldList(){
		stationId=(stationId==null)?null:stationId.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 管养路段概况列表");
		log.trace("管养路段概况列表");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("gyldgklist",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
         
     	if(page==null||page.equals("")){
     		page  = Constants.MOREN_PAGE;
     	}
     	if(rows==null||rows.equals("")){
     		rows = Constants.MOREN_ROWS;
     	}
	
		this.result = this.glzxxservice.selectGyldgkList(page,rows,stationId,selectvalue);
 		//记录日志信息
 	/*	this.czrzService.saveCzrz(token,"查看管养路段");//基本权限 30.查看管养路段
*/		return "json";
	}
	/**
	 * 管养路段详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/gyldxq?token=?&id=?  
	 */
	/*@Action(value = "gyldxq", results = { @Result(name = "json", type = "json")})*/
	public String selectGyldXq(){
		
		System.out.println(" 管养路段详情");
		log.trace("管养路段详情");
		/*
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
    	 //验证权限
    	 validate_code = this.glryservice.authorityCheck("gyldxq",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
		this.result = this.glzxxservice.selectGyldXq(id);
 		/*this.czrzService.saveCzrz(token,"查看管养路段");//基本权限 30.查看管养路段
*/		return "json";
	}
	
	//3.公路站人员列表、详情。
	/**
	 * 单位人员概况列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/dwrylist?token=? 
	 */
	/*@Action(value = "dwrylist", results = { @Result(name = "json", type = "json")})*/
	public String selectDwryList(){
		stationId=(stationId==null)?null:stationId.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 单位人员概况列表");
		log.trace("单位人员概况列表");
		
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("dwrylist",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
         
     	if(page==null||page.equals("")){
     		page  = Constants.MOREN_PAGE;
     	}
     	if(rows==null||rows.equals("")){
     		rows = Constants.MOREN_ROWS;
     	}
	
		this.result = this.glzxxservice.selectDwrygkList(page,rows,stationId,selectvalue); 	
 		/*this.czrzService.saveCzrz(token,"查看单位人员");//基本权限 31.查看单位人员
*/		return "json";
	}
	/**
	 * 单位人员详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/dwryxq?token=?&id=?  
	 */
	/*@Action(value = "dwryxq", results = { @Result(name = "json", type = "json")})*/
	public String selectDwryXq(){
		
		System.out.println(" 单位人员详情");
		log.trace("单位人员详情");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("dwryxq",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }	*/
		this.result = this.glzxxservice.selectDwryXq(id);
 		/*this.czrzService.saveCzrz(token,"查看单位人员");//基本权限 31.查看单位人员
*/		return "json";
	}
	
	
	//4.桥梁列表、详情。
	/**
	 * 桥梁概况列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzqlgklist?token=? 
	 */
	/*@Action(value = "glzqlgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzQlgkList(){
		
		System.out.println(" 桥梁概况列表");
		log.trace("桥梁概况列表");
		
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
     	Short removeMake = null;     //null 不判断，0,1
	
		this.result = this.glzxxservice.selectGlzQlgkList(removeMake,page,rows,stationId,selectvalue); 	
		return "json";
	}
	
	/**
	 * 桥梁详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzqlxq?token=?&idx=?  
	 */
/*	@Action(value = "glzqlxq", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzQlXq(){
		
		System.out.println("桥梁详情");
		log.trace("桥梁详情");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
*/    		
		this.result = this.glzxxservice.selectGlzQlgkQx(id);

		return "json";
	}
	
	//5.涵洞列表、详情。
	/**
	 * 涵洞概况列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzhdgklist?token=? 
	 */
	/*@Action(value = "glzhdgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzHdgkList(){
		
		System.out.println(" 桥梁概况列表");
		log.trace("桥梁概况列表");
		
	/*	//验证令牌
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
     	Short removeMake = null;     //null 不判断，0,1
	
		this.result = this.glzxxservice.selectGlzHdgkList(removeMake,page,rows,stationId,selectvalue); 	
		return "json";
	}
	
	/**
	 * 涵洞详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glzhdxq?token=?&id=?  
	 */
	/*@Action(value = "glzhdxq", results = { @Result(name = "json", type = "json")})*/
	public String selectGlzHdXq(){
		
		System.out.println("涵洞详情");
		log.trace("涵洞详情");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }*/
    	 //令牌验证结束 
    		
		this.result = this.glzxxservice.selectGlzHdgkQx(id);

		return "json";
	}
	
	//6.月生产统计信息。
	/**
	 * 月生产统计信息列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   workDate
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/yscqktjxxlist?token=?&workDate=2014 
	 */
	/*@Action(value = "yscqktjxxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectDbYscqktjxxList(){
		stationId=(stationId==null)?null:stationId.trim();
		workDate=(workDate==null)?null:workDate.trim();
		System.out.println("月生产统计信息列表");
		log.trace("月生产统计信息列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("yscqktjxxlist",token);
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
     	if(page==null||page.equals("")){
     		page  = Constants.MOREN_PAGE;
     	}
     	if(rows==null||rows.equals("")){
     		rows = Constants.MOREN_ROWS;
     	}
     

		this.result = this.glzxxservice.selectDbYscqktjxx(page, rows, stationId, workDate);
 		/*this.czrzService.saveCzrz(token,"查看月生产统计信息");*///基本权限 32.查看月生产统计信息
		return "json";
	}
	
	
	
	/**
	 * 年份列表
	 * @return  result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/workdatelist?token=? 
	 */
	/*@Action(value = "workdatelist", results = { @Result(name = "json", type = "json")})*/
	public String selectWorkDateList(){
		
		System.out.println("年份列表");
		log.trace("年份列表");
		
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
*/
		this.result = this.glzxxservice.selectWorkDateList();
		return "json";
	}
	
	
	//7.公路巡查记录（注意包括相关联的信息，如巡查路段）
	/**
	 * 公路巡查记录列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   workDate
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glxclist?token=? 
	 */
	/*@Action(value = "glxclist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlxcList(){
		stationId=(stationId==null)?null:stationId.trim();
		workDate=(workDate==null)?null:workDate.trim();
		System.out.println("公路巡查记录列表");
		log.trace("公路巡查记录列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glxclist",token);
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
     
		this.result = this.glzxxservice.selectXcjlList(page, rows, stationId, workDate);
		/*this.czrzService.saveCzrz(token,"查看公路巡查");*///基本权限 33.查看公路巡查
		return "json";
	}
	
	/**
	 * 公路巡查记录详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glxcxq?token=?&id=?  
	 */
	/*@Action(value = "glxcxq", results = { @Result(name = "json", type = "json")})*/
	public String selectGlxcXq(){
		
		System.out.println("公路巡查记录详情");
		log.trace("公路巡查记录详情");
		
		//验证令牌
/*		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glxcxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
		this.result = this.glzxxservice.selectXcjlXq(id);
		/*this.czrzService.saveCzrz(token,"查看公路巡查");*///基本权限 33.查看公路巡查
		return "json";
	}

	//selectInspectDateList
	/**
	 * 公路巡查记录年份列表
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glxcdatelist?token=?  
	 */
	/*@Action(value = "glxcdatelist", results = { @Result(name = "json", type = "json")})*/
	public String selectGlxcDateList(){
		
		System.out.println("公路巡查记录年份列表");
		log.trace("公路巡查记录年份列表");
		
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
*/    		
		this.result = this.glzxxservice.selectInspectDateList();

		return "json";
	}
	//8.桥梁经常性检查（包括明细）
	/**
	 * 桥梁名字列表
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/qlnamelist?token=? 
	 */
	/*@Action(value = "qlnamelist", results = { @Result(name = "json", type = "json")})*/
	public String selectQlNameList(){
		
		System.out.println(" 桥梁名字列表");
		log.trace(" 桥梁名字列表");
		
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }*/
    	 //令牌验证结束
		this.result = this.glzxxservice.selectQlNameList();
		return "json";
	}
	
	/**
	 * 桥梁巡查记录列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   workDate
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/qlxclist?token=? 
	 */
	/*@Action(value = "qlxclist", results = { @Result(name = "json", type = "json")})*/
	public String selectQlxcList(){
		
		System.out.println("桥梁巡查记录列表");
		log.trace("桥梁巡查记录列表");
		
	/*	//验证令牌
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

		this.result = this.glzxxservice.selectQljcxjcList(page, rows, stationId,bridgeId, selectvalue);
		return "json";
	}
	
	/**
	 * 桥梁巡查记录详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/qlxcxq?token=?&id=?  
	 */
	/*@Action(value = "qlxcxq", results = { @Result(name = "json", type = "json")})*/
	public String selectQlxcXq(){
		
		System.out.println("桥梁巡查记录详情");
		log.trace("桥梁巡查记录详情");
		
	/*	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
*/    		
		this.result = this.glzxxservice.selectQljcxjxMx(id);

		return "json";
	}
	
	//9.涵洞经常性检查（包括明细）
	/**
	 * 涵洞名字列表
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/hdnamelist?token=? 
	 */
	/*@Action(value = "hdnamelist", results = { @Result(name = "json", type = "json")})*/
	public String selectHdNameList(){
		
		System.out.println("涵洞名字列表");
		log.trace(" 涵洞名字列表");
	/*	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
*/		this.result = this.glzxxservice.selectHdCodeList();
		return "json";
	}
	/**
	 * 涵洞巡查记录列表
	 * @param   page
	 * @param   rows
	 * @param   stationId
	 * @param   token
	 * @param   workDate
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glxclist?token=? 
	 */
	/*@Action(value = "hdxclist", results = { @Result(name = "json", type = "json")})*/
	public String selectHdxcList(){
		
		System.out.println("涵洞巡查记录列表");
		log.trace("涵洞巡查记录列表");
		
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

		this.result = this.glzxxservice.selectHdjcxjcList(page, rows, stationId,culverId, selectvalue);
		return "json";
	}
	
	/**
	 * 桥梁巡查记录详情
	 * @param   id
	 * @param   token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/glzxxlist/glxcxq?token=?&id=?  
	 */
	/*@Action(value = "hdxcxq", results = { @Result(name = "json", type = "json")})*/
	public String selectHdxcXq(){
		
		System.out.println("涵洞巡查记录详情");
		log.trace("涵洞巡查记录详情");
		
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束 
    		
		this.result = this.glzxxservice.selectHdjcxjxMx(id);

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


	public HGlzGlzXxxx getGlzXxxx() {
		return glzXxxx;
	}


	public void setGlzXxxx(HGlzGlzXxxx glzXxxx) {
		this.glzXxxx = glzXxxx;
	}

	public String getSelectvalue() {
		return selectvalue;
	}

	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}

	public String getCulverId() {
		return culverId;
	}

	public void setCulverId(String culverId) {
		this.culverId = culverId;
	}

	public String getGljgdm() {
		return gljgdm;
	}

	public void setGljgdm(String gljgdm) {
		this.gljgdm = gljgdm;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	

}
