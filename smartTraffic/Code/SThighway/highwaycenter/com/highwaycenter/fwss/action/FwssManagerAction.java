package com.highwaycenter.fwss.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.bzbx.action.BzbxManagerAction;
import com.highwaycenter.bzbx.service.BzbxService;
import com.highwaycenter.fwss.model.HFwFwq;
import com.highwaycenter.fwss.model.HFwSfz;
import com.highwaycenter.fwss.service.FwssService;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;

@SuppressWarnings("all") //忽略该类中的所有警告
public class FwssManagerAction extends BaseAction{
	static Logger log = Logger.getLogger(FwssManagerAction.class);
	@Resource(name="fwssservice")
	private FwssService fwssService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private String selectvalue;  //  搜索字段
	private String fwssbh;   //服务设施编号  sfz_XX :收费站类型的编号;fwq_XX :服务区类型的编号
	private Integer page;
	private Integer rows;
	private String token; 
	private BaseResult result;
	private HFwFwq fwq;
	private HFwSfz sfz;
	private String selectId;//编号
	
	/**服务区列表
	 * @param page   Integer 请求页数
	 * @param rows   Integer 请求行数
	 * @param token  String 令牌
	 * @param selectvalue  String 搜索字段（服务设施名称）
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/fwqlist     
	 */
	/*@Action(value = "fwqlist", results = { @Result(name = "json", type = "json")})*/
	public String  selectFwqList(){
		selectvalue= trimAll(selectvalue);	
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("fwqlist",token);
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

	    this.result = this.fwssService.selectFwssList(page, rows,selectvalue,1, selectId);
		return "json";
	}
	
	/**服务区详情
	 * @param token  String 令牌
	 * @param fwssbh  String 服务设施编号    
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/fwqxq     
	 */
	public String selectFwqXq(){
		//验证令牌
		 /*int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("fwqxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
  	   fwssbh = trimAll(fwssbh);
  	   if(fwssbh==null||fwssbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.fwssService.selectFwssXq(fwssbh,1);
		
		return "json";
	}
	
	
	/**收费站列表
	 * @param page   Integer 请求页数
	 * @param rows   Integer 请求行数
	 * @param token  String 令牌
	 * @param selectvalue  String 搜索字段（服务设施名称）
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/sfzlist     
	 */
	public String  selectSfzList(){
		selectvalue= trimAll(selectvalue);	
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("sfzlist",token);
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

	    this.result = this.fwssService.selectFwssList(page, rows,selectvalue,2,selectId);
		return "json";
	}
	
	/**收费站详情
	 * @param token  String 令牌
	 * @param fwssbh  String 服务设施编号    
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/sfzxq     
	 */
	public String selectSfzXq(){
		//验证令牌
	/*	 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("sfzxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
  	   fwssbh = trimAll(fwssbh);
  	   if(fwssbh==null||fwssbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.fwssService.selectFwssXq(fwssbh,2);
		
		return "json";
	}
	
	
	/**删除服务区
	 * @param token  String 令牌
	 * @param fwssbh  String 服务设施编号    
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/deletefwq   
	 */
	public String deleteFwq(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("deletefwq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   fwssbh = trimAll(fwssbh);
  	   if(fwssbh==null||fwssbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.fwssService.deleteFwss(fwssbh,1);
  	   
  	   this.czrzService.saveCzrz(token,"删除服务区","删除服务区——“"+(String)result.getObj()+"”");//基本权限 40.删除服务区  	
		return "json";
	}
	
	/**删除收费站
	 * @param token  String 令牌
	 * @param fwssbh  String 服务设施编号    
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/deletesfz   
	 */
	public String deleteSfz(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("deletesfz",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   fwssbh = trimAll(fwssbh);
  	   if(fwssbh==null||fwssbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.fwssService.deleteFwss(fwssbh,2);
  	   this.czrzService.saveCzrz(token,"删除收费站","删除收费站——“"+(String)result.getObj()+"”");//基本权限 44.删除收费站  
		return "json";
	}

	/**新增服务区
	 * @param token  String 令牌
	 * @param fwq.fwqmc   String//服务区名称
	 * @param fwq.ssgs    String//所属公司
	 * @param fwq.xlmc    String//线路名称
	 * @param fwq.sxjkzh  String//上行进口桩号
	 * @param fwq.xxjkzh  String//下行进口桩号
	 * @param fwq.sxckzh  String//上行出口桩号
	 * @param fwq.xxckzh   String//下行出口桩号
	 * @param fwq.jyss    Integer//加油设施
	 * @param fwq.cyss    Integer//餐饮设施
	 * @param fwq.zsss    Integer//住宿设施
	 * @param fwq.gwss    Integer//购物设施
	 * @param fwq.clwxss  Integer //车辆维修设施
	 * @param fwq.glzdzh  String//管理中队桩号
	 * @param fwq.zczzh   String//治超站桩号
	 * @param fwq.gljjzh   String//管理交警桩号
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/savefwq  
	 */
	public String saveFwq(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("savefwq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	fwq = this.trimFwq(fwq);
    	 //验证参数是否完整（只验证名字）
  	    if(fwq==null||fwq.getFwqmc()==null||fwq.getFwqmc().equals("")){
  	    	 this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
  	    	 return "json";
  	    }
    	
  	   this.result = this.fwssService.saveFwq(fwq);
  	   if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
  	   this.czrzService.saveCzrz(token,"新增服务区","新增服务区——“"+fwq.getFwqmc()+"”");//基本权限 38.新增服务区 
  	   }
		return "json";
	}
	
	

	/**编辑服务区
	 * @param token  String 令牌
	 * @param fwq.fwqbh   Integer//服务区编号
	 * @param fwq.fwqmc   String//服务区名称
	 * @param fwq.ssgs    String//所属公司
	 * @param fwq.xlmc    String//线路名称
	 * @param fwq.sxjkzh  String//上行进口桩号
	 * @param fwq.xxjkzh  String//下行进口桩号
	 * @param fwq.sxckzh  String//上行出口桩号
	 * @param fwq.xxckzh   String//下行出口桩号
	 * @param fwq.jyss    Integer//加油设施
	 * @param fwq.cyss    Integer//餐饮设施
	 * @param fwq.zsss    Integer//住宿设施
	 * @param fwq.gwss    Integer//购物设施
	 * @param fwq.clwxss  Integer //车辆维修设施
	 * @param fwq.glzdzh  String//管理中队桩号
	 * @param fwq.zczzh   String//治超站桩号
	 * @param fwq.gljjzh   String//管理交警桩号
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/updatefwq   
	 */
	public String updateFwq(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("updatefwq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	fwq = this.trimFwq(fwq);
    	 //验证参数是否完整（验证编号，名字）
  	    if(fwq==null||fwq.getFwqbh()==null||fwq.getFwqbh().equals("")||fwq.getFwqmc()==null||fwq.getFwqmc().equals("")){
  	    	 this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
  	    	 return "json";
  	    }
    	 
  	 
		
  	   this.result = this.fwssService.updateFwq(fwq);
  	 if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
  	   this.czrzService.saveCzrz(token,"编辑服务区","编辑服务区——“"+fwq.getFwqmc()+"”");//基本权限 39.编辑服务区 
  	 }
		return "json";
	}
	
	/**
	 * 新增收费站
	 * @param token   String 令牌
	 * @param sfz.sfzmc   String/收费站名称
	 * @param sfz.sfzpjzm String/收费站票据站名
	 * @param sfz.ssgs    String//所属公司
	 * @param sfz.xlmc    String//线路名称
	 * @param sfz.sfzlxbh  Integer//收费站类型编号
	 * @param sfz.jketccdsl  Integer//进口etc车道数量
	 * @param sfz.jkrgcdsl  Integer//进口人工车道数量
	 * @param sfz.zxsxjkzh   String//主线上行进口桩号
	 * @param sfz.zxxxjkzh    String//主线下行进口桩号
	 * @param sfz.cketccdsl    Integer//出口etc车道数量
	 * @param sfz.ckrgcdsl    Integer//出口人工车道数量
	 * @param sfz.zxsxckzh    String//主线上行出口桩号
	 * @param sfz.zxxxckzh  String //主线下行出口桩号
	 * @param sfz.ckzx  String//出口指向
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/savesfz    
	 */
	public String saveSfz(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
 	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
 	    //验证权限
     	 validate_code = this.glryservice.authorityCheck("savesfz",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     sfz = this.trimSfz(sfz);
	   //验证参数是否完整（只验证名字）
	     if(sfz==null||sfz.getSfzmc()==null||sfz.getSfzmc().equals("")){
	    	 this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
  	    	 return "json";
	     }
		this.result = this.fwssService.saveSfz(sfz);
		 if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		 this.czrzService.saveCzrz(token,"新增收费站","新增收费站——“"+sfz.getSfzmc()+"”");//基本权限 42.新增收费站 
		 }
		return "json";
	}
	
	/**
	 * 编辑收费站
	 * @param token   String 令牌
	 * @param sfz.sfzbh   Integer//收费站编号
	 * @param sfz.sfzmc   String/收费站名称
	 * @param sfz.sfzpjzm String/收费站票据站名
	 * @param sfz.ssgs    String//所属公司
	 * @param sfz.xlmc    String//线路名称
	 * @param sfz.sfzlxbh  Integer//收费站类型编号
	 * @param sfz.jketccdsl  Integer//进口etc车道数量
	 * @param sfz.jkrgcdsl  Integer//进口人工车道数量
	 * @param sfz.zxsxjkzh   String//主线上行进口桩号
	 * @param sfz.zxxxjkzh    String//主线下行进口桩号
	 * @param sfz.cketccdsl    Integer//出口etc车道数量
	 * @param sfz.ckrgcdsl    Integer//出口人工车道数量
	 * @param sfz.zxsxckzh    String//主线上行出口桩号
	 * @param sfz.zxxxckzh  String //主线下行出口桩号
	 * @param sfz.ckzx  String//出口指向
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/fwssmanager/updatesfz    
	 */
	public String updateSfz(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
 	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
 	    //验证权限
     	 validate_code = this.glryservice.authorityCheck("updatesfz",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     sfz = this.trimSfz(sfz);
	   //验证参数是否完整（验证编号，名字）
	     if(sfz==null||sfz.getSfzbh()==null||sfz.getSfzbh().equals("")||sfz.getSfzmc()==null||sfz.getSfzmc().equals("")){
	    	 this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
  	    	 return "json";
	     }
		this.result = this.fwssService.updateSfz(sfz);
		 if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
		this.czrzService.saveCzrz(token,"编辑收费站","编辑收费站——“"+sfz.getSfzmc()+"”");//基本权限 43.编辑收费站 
		 }
		return "json";
	}

	
	protected String trimAll(String temp){
		return (temp==null)?null:temp.trim();
	}

	protected HFwFwq trimFwq(HFwFwq fwq){
		fwq.setFwqmc(fwq.getFwqmc());
		fwq.setSsgs(trimAll(fwq.getSsgs()));
		fwq.setXlmc(trimAll(fwq.getXlmc()));
		fwq.setSxjkzh(trimAll(fwq.getSxjkzh()));
		fwq.setXxjkzh(trimAll(fwq.getXxjkzh()));
		fwq.setSxckzh(trimAll(fwq.getSxckzh()));
		fwq.setXxckzh(trimAll(fwq.getXxckzh()));
		fwq.setGlzdzh(trimAll(fwq.getGlzdzh()));
		fwq.setZczzh(trimAll(fwq.getZczzh()));
		fwq.setGljjzh(trimAll(fwq.getGljjzh()));
		return fwq;
	}
	
	
	protected HFwSfz trimSfz(HFwSfz sfz){
		sfz.setSfzmc(trimAll(sfz.getSfzmc()));
		sfz.setSfzpjzm(trimAll(sfz.getSfzpjzm()));
		sfz.setSsgs(trimAll(sfz.getSsgs()));
		sfz.setXlmc(trimAll(sfz.getXlmc()));
		sfz.setZxsxjkzh(trimAll(sfz.getZxsxjkzh()));
		sfz.setZxxxjkzh(trimAll(sfz.getZxxxjkzh()));
		sfz.setZxsxckzh(trimAll(sfz.getZxsxckzh()));
		sfz.setZxxxckzh(trimAll(sfz.getZxxxckzh()));
		sfz.setCkzx(trimAll(sfz.getCkzx()));
		return sfz;
	}
	public String getSelectvalue() {
		return selectvalue;
	}

	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}

	public String getFwssbh() {
		return fwssbh;
	}

	public void setFwssbh(String fwssbh) {
		this.fwssbh = fwssbh;
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


	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	public HFwFwq getFwq() {
		return fwq;
	}

	public void setFwq(HFwFwq fwq) {
		this.fwq = fwq;
	}

	public HFwSfz getSfz() {
		return sfz;
	}

	public void setSfz(HFwSfz sfz) {
		this.sfz = sfz;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	
	
}
