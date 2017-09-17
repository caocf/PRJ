package com.highwaycenter.legal.action;

import javax.annotation.Resource;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.legal.model.HXzPunishment;
import com.highwaycenter.legal.service.XzPunishmentService;
import com.highwaycenter.log.service.CzrzService;

public class XzPunishmentAction extends BaseAction{

	private static final long serialVersionUID = -2692304406302210275L;
	private Integer page;
	private Integer rows;
	private String token; 
	private BaseResult result;
	private String selectvalue;
	private String selectkey;
	private String xzcfjgdm;
	private Integer xzcfbh;
	private String type;  //行政处罚下拉框类型
	private HXzPunishment punishment;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	@Resource(name="punishmentservice")
	private  XzPunishmentService punishService;
	
	
	/**行政处罚下拉框列表
	 * @param token  String 令牌
	 * @param type   1:行政处罚权利事项表;2: 证件类型表;3:行政处罚类型编号
	 * @return result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/selectoptionlist
	 */
	public String selectOptionList(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("xzzflist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   if(type==null||type.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.punishService.selectOptionList(type);
		return "json";

	}
	
	/**行政处罚详情
	 * @param token  String 令牌
	 * @param xzcfbh  行政处罚编号
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/xzcfxq  
	 */
	public String selectXzcfXq(){
		
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("xzzflist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   if(xzcfbh==null||xzcfbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.punishService.selectXzPunishmentXq(xzcfbh);
		return "json";

	}
	
	/**
	 * 行政处罚列表
	 * @param   page
	 * @param   rows
	 * @param   xzqhdm       行政区划代码
	 * @param   selectvalue  搜索框搜索值
	 * @param   selectkey  搜索框搜索值
	 * @param  token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/xzcflist
	 */
	/*@Action(value = "lxgklist", results = { @Result(name = "json", type = "json")})*/
	public String selectXzcfList(){
		selectkey =(selectkey==null)?null:selectkey.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		System.out.println(" 行政执法概况列表");
		
		try{
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //验证权限
    	 validate_code = this.glryservice.authorityCheck("xzzflist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
        
    	if(page==null||page.equals("")){
    		page  = Constants.MOREN_PAGE;
    	}
    	if(rows==null||rows.equals("")){
    		rows = Constants.MOREN_ROWS;
    	}
    	
		this.result = this.punishService.selectXzPunishmentList(page, rows, selectkey, selectvalue, xzcfjgdm);
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
    	 System.out.println(" 行政区划列表end");

		return "json";
	}
	
	/**
	 *  新增行政执法
	 *  @param 
	 *  @param punishment.xzqhdm       String 行政区划代码
	 *  @param punishment.xzcfjdwh    String 行政处罚决定文号
	 *  @param punishment.ajmc    String 案件名称
	 *  @param punishment.cfrxm      String 处罚人姓名
	 *  @param punishment.wfqyzjjgdm      String 违法企业组织机构代码
	 *  @param punishment.fddbrxm      String 法定代表人姓名
	 *  @param punishment.zywfss      String 主要违法事实
	 *  @param punishment.xzcf      String 行政处罚履行方式和期限
	 *  @param punishment.xzcfzl      String 行政处罚的种类和依据
	 *  @param punishment.xzcfrq      String 行政处罚日期
	 *  @param punishment.bz      String 备注
	 *  以下是新增字段
	 *  @param punishment.xzcftype  被行政处罚类型(下拉框传值)
	 *  @param punishment.xzcfcardtype  被行政处罚对象证件类型(下拉框传值)
	 *  @param punishment.xzcfcardnumber  被行政处罚对象证件号码(输入)
	 *  @param punishment.legalmanIdcard  String 被处罚单位法人身份证(输入)
	 *  @param punishment.xzcfitemid;        String 行政处罚权利事项编号(下拉框传值)
	 *  @param token      
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/savexzcf
	 */
	public String saveXzcf(){
		
		token=(token==null)?null:token.trim();
		if(punishment==null||punishment.getXzcfjdwh()==null||punishment.getXzcfjdwh().trim().equals("")){
			this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			 return "json";
		}
		punishment.setXzcfjdwh(punishment.getXzcfjdwh().trim());
	  //验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	        return "json";
	 	   }
	     //验证权限
    	 validate_code = this.glryservice.authorityCheck("savexzzf",token); //暂时没有保存新增日志这个权限
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     
	     this.result = this.punishService.saveXzPunishment(punishment);
	     //新增操作日志
	     if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
	     this.czrzService.saveCzrz(token,"新增行政处罚", "新增行政处罚——“"+punishment.getXzcfjdwh()+"”");
	     }
         return "json";
	
	}
	
	/**
	 *  编辑行政执法
	 *  @param punishment.xzcfbh      String  行政处罚编号
	 *  @param punishment.xzqhdm       String 行政区划代码
	 *  @param punishment.xzcfjdwh    String 行政处罚决定文号
	 *   @param punishment.ajmc    String 案件名称
	 *  @param punishment.cfrxm      String 处罚人姓名
	 *  @param punishment.wfqyzjjgdm      String 违法企业组织机构代码
	 *  @param punishment.fddbrxm      String 法定代表人姓名
	 *  @param punishment.zywfss      String 注意违法事实
	 *  @param punishment.xzcf      String 行政处罚履行方式和期限
	 *  @param punishment.xzcfzl      String 行政处罚的种类和依据
	 *  @param punishment.xzcfrq      String 行政处罚日期
	 *  @param punishment.bz      String 备注
	 *  *  以下是新增字段
	 *  @param punishment.xzcftype  被行政处罚类型(下拉框传值)
	 *  @param punishment.xzcfcardtype  被行政处罚对象证件类型(下拉框传值)
	 *  @param punishment.xzcfcardnumber  被行政处罚对象证件号码(输入)
	 *  @param punishment.legalmanIdcard  String 被处罚单位法人身份证(输入)
	 *  @param punishment.xzcfitemid;        String 行政处罚权利事项编号(下拉框传值)
	 *  @param token      
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/updatexzcf
	 */
	public String updateXzcf(){
		
		token=(token==null)?null:token.trim();
		if(punishment==null||punishment.getXzcfbh()==null||punishment.getXzcfbh().equals("")||punishment.getXzcfjdwh()==null||punishment.getXzcfjdwh().trim().equals("")){
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
    	 validate_code = this.glryservice.authorityCheck("updatexzzf",token); //暂时没有保存新增日志这个权限
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     
	     this.result = this.punishService.updateXzPunishment(punishment);
	     
	     //新增操作日志
	     if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
	     this.czrzService.saveCzrz(token,"编辑行政处罚", "编辑行政处罚——“"+punishment.getXzcfjdwh()+"”");
	     }
         return "json";
	
	}
	
	/**
	 *  删除行政执法
	 *  @param punishment.xzcfbh      Integer  行政处罚编号
	 *  @param token      
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/xzcf/updatexzcf
	 */
	public String deleteXzcf(){

		if(punishment==null||punishment.getXzcfbh()==null||punishment.getXzcfbh().equals("")){
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
    	 validate_code = this.glryservice.authorityCheck("updatexzzf",token); //暂时没有保存新增日志这个权限
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     
	     this.result = this.punishService.deleteXzPunishment(punishment.getXzcfbh());
	     
	     //新增操作日志
	     if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
	    	 String wh = "";
	    	 if(result.getObj()!=null){
	    		 wh = (String)result.getObj();
	    	 }
	           this.czrzService.saveCzrz(token,"删除行政处罚", "删除行政处罚——“"+wh+"”");
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

	public String getSelectvalue() {
		return selectvalue;
	}

	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}

	public String getSelectkey() {
		return selectkey;
	}

	public void setSelectkey(String selectkey) {
		this.selectkey = selectkey;
	}


	public String getXzcfjgdm() {
		return xzcfjgdm;
	}

	public void setXzcfjgdm(String xzcfjgdm) {
		this.xzcfjgdm = xzcfjgdm;
	}

	public Integer getXzcfbh() {
		return xzcfbh;
	}

	public void setXzcfbh(Integer xzcfbh) {
		this.xzcfbh = xzcfbh;
	}

	public HXzPunishment getPunishment() {
		return punishment;
	}

	public void setPunishment(HXzPunishment punishment) {
		this.punishment = punishment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
