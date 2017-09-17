package com.highwaycenter.jd.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.jd.service.JxdczxxService;
import com.highwaycenter.log.service.CzrzService;

public class JxdczxxAction extends BaseAction{
	static Logger log = Logger.getLogger(JxdczxxAction.class);
	private static final long serialVersionUID = 2468459371610290231L;
	
	@Resource(name="jxdczxxservice")
	private JxdczxxService jxdczxxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private Integer page;
	private Integer rows;
	private String token; 
	private BaseResult result;
	private String dczbh;
	private String optionName;  //选项名称:dczlx调查站类型，dcff调查方法，gdfs供电方式，txfs通讯方式，glgn公路功能
	private String xzqhdm;
	private String dczlxbh;
	private String dcffbh;
	private String gdfsbh;
	private String txfsbh;
	private String glgnbh;
	private String selectvalue;
	private String sjYear;//交通量数据查询接口参数1：sj(年)2014
	private String sjMonth;//交通量数据查询接口参数1：sj(月)04
	private String sjDay;//交通量数据查询接口参数1：sj(日)27
	private String sjHyear;//交通量数据查询接口参数1：sj(半年)1,2
	private String sjSeason;//交通量数据查询接口参数1：sj(季度)1,2,3,4
	private String selectId;//调查站编号
	
    /**调查站信息详情
	 * @param token  String 令牌
	 * @param dczbh  String 调查站编号    
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/jxdczxx/dczxx     
	 */
	public String selectJczxxXq(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("dczxx",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   dczbh = (dczbh==null)?null:dczbh.trim();
  	   if(dczbh==null||dczbh.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.jxdczxxService.selectJxdczxxSimple(dczbh);
		return "json";
	}
	
	
	
	/**下拉列表
	 * @param token  String 令牌
	 * @param optionName  String //选项名称:dczlx调查站类型，dcff调查方法，gdfs供电方式，txfs通讯方式，glgn公路功能    
	 * @return result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/jxdczxx/optionlist     
	 */
	public String selectOptionList(){
		try{
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	   /* //验证权限
    	 validate_code = this.glryservice.authorityCheck("dczxx");
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
  	   optionName = (optionName==null)?null:optionName.trim();
  	   if(optionName==null||optionName.equals("")){
  		   this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
  		   return "json";
  	   }
		
  	   this.result = this.jxdczxxService.selectOptionList(optionName);
		}catch(java.lang.Exception e ){
			e.printStackTrace();
		}
		return "json";
	}
	
	
	/**交通量数据查询接口
	 * @param token  String 令牌
	 * @param sjYear  String 年
	 * @param sjMonth  String 月
	 * @param sjDay  String 日
	 * @param page
	 * @param rows
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/jxdczxx/transdatalist     
	 */
	public String selectTransDataList(){
		
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("transdatalist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	   sjYear = trimAll(sjYear);
  	   sjMonth = trimAll(sjMonth);
  	   sjDay = trimAll(sjDay);
  	   sjHyear= trimAll(sjHyear);
  	   sjSeason = trimAll(sjSeason);
  	   if(page==null||page.equals("")){
   		   page  = Constants.MOREN_PAGE;
     	}
     	if(rows==null||rows.equals("")){
    	 	rows = Constants.MOREN_ROWS;
    	}
	    
     	if(sjYear==null||sjYear.equals("")){
     		  this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
     		   return "json";
     	}
		
  	   this.result = this.jxdczxxService.selectTransportDataList(page, rows, 
  			   sjYear, sjMonth, sjDay,sjHyear,sjSeason);
	
		return "json";
	}
	
	
	/**
	 * 省交调GIS地图拥挤度--实时车速（拥挤度） 接口
	 * @param token  String 令牌
	 *  接口实例：http://localhost:8080/HighWayCenter/jxdczxx/timecs
	 */
public String selectTransDataTimeCS(){
		
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("transdatalist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  
  	   this.result = this.jxdczxxService.selectTransDataTimeCS();
	   /*System.out.println("zzzzzzzzzz"+result.getObj());*/
		return "json";
	}
	
	
	
	
	
	/**调查站信息列表
	 * @param token    String 令牌
	 * @param page
	 * @param rows
	 * @param xzqhdm   String 行政区划代码
	 * @param dczlxbh  String 调查站类型编号
	 * @param dcffbh   String调查方法编号
	 * @param gdfsbh   String 供电方式编号
	 * @param txfsbh   String 通讯方式编号
	 * @param glgn     String 公路功能编号
	 * @param selectvalue String 搜索字段（调查站名字模糊查询）
	 * @return result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/jxdczxx/dczxxlist     
	 */
	public String selectDczxxList(){
		try{
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("dczxxlist",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
  	    String condition = " ";
  	    xzqhdm = trimAll(xzqhdm);
  	  condition = this.creatContidion(condition,"xzqh.xzqhdm",xzqhdm);
  	    dczlxbh = trimAll(dczlxbh);
  	  condition =  this.creatContidion(condition,"dczlx.dczlxbh",dczlxbh);
  	    dcffbh = trimAll(dcffbh);
  	  condition = this.creatContidion(condition,"dcff.dcffbh",dcffbh);
  	    gdfsbh = trimAll(gdfsbh);
  	  condition = this.creatContidion(condition,"gdfs.gdfsbh",gdfsbh);
    	 txfsbh = trimAll(txfsbh);
      condition = this.creatContidion(condition,"txfs.txfsbh",txfsbh);
    	 glgnbh = trimAll(glgnbh);
      condition = this.creatContidion(condition,"glgn.glgnbh",glgnbh);
         selectvalue = trimAll(selectvalue);
      if(selectvalue!=null&&!selectvalue.equals("")){
    	  condition+=" and a.dczmc like '%"+selectvalue+"%' ";
      }
      System.out.println("condition"+condition);
    	 if(page==null||page.equals("")){
      		page  = Constants.MOREN_PAGE;
      	}
      	if(rows==null||rows.equals("")){
      		rows = Constants.MOREN_ROWS;
      	}
  	  
  	   this.result = this.jxdczxxService.selectJxdczxxList(condition,page,rows,selectId);
		}catch(java.lang.Exception e ){
			e.printStackTrace();
		}
		return "json";
	}
	
	private String trimAll(String temp){
		return (temp==null)?null:temp.trim();
	}
	
	private String creatContidion(String condition,String optionname,String optionvalue){
		if(optionvalue!=null&&!optionvalue.equals("")){
			condition += " and "+optionname+"="+optionvalue+" ";
		}
		return condition;
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
	public String getDczbh() {
		return dczbh;
	}
	public void setDczbh(String dczbh) {
		this.dczbh = dczbh;
	}


	public String getOptionName() {
		return optionName;
	}


	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}



	public String getXzqhdm() {
		return xzqhdm;
	}



	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}



	public String getDczlxbh() {
		return dczlxbh;
	}



	public void setDczlxbh(String dczlxbh) {
		this.dczlxbh = dczlxbh;
	}



	public String getDcffbh() {
		return dcffbh;
	}



	public void setDcffbh(String dcffbh) {
		this.dcffbh = dcffbh;
	}



	public String getGdfsbh() {
		return gdfsbh;
	}



	public void setGdfsbh(String gdfsbh) {
		this.gdfsbh = gdfsbh;
	}



	public String getTxfsbh() {
		return txfsbh;
	}



	public void setTxfsbh(String txfsbh) {
		this.txfsbh = txfsbh;
	}



	public String getGlgnbh() {
		return glgnbh;
	}



	public void setGlgnbh(String glgnbh) {
		this.glgnbh = glgnbh;
	}



	public String getSelectvalue() {
		return selectvalue;
	}



	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}



	public String getSjYear() {
		return sjYear;
	}



	public void setSjYear(String sjYear) {
		this.sjYear = sjYear;
	}



	public String getSjMonth() {
		return sjMonth;
	}



	public void setSjMonth(String sjMonth) {
		this.sjMonth = sjMonth;
	}



	public String getSjDay() {
		return sjDay;
	}



	public void setSjDay(String sjDay) {
		this.sjDay = sjDay;
	}



	public String getSjHyear() {
		return sjHyear;
	}



	public void setSjHyear(String sjHyear) {
		this.sjHyear = sjHyear;
	}



	public String getSjSeason() {
		return sjSeason;
	}



	public void setSjSeason(String sjSeason) {
		this.sjSeason = sjSeason;
	}



	public String getSelectId() {
		return selectId;
	}



	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}



	

}
