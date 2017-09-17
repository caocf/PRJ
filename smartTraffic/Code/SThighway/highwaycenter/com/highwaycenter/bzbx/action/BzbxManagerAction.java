package com.highwaycenter.bzbx.action;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.utils.FileUpload;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
import com.highwaycenter.bzbx.service.BzbxService;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.glz.action.GlzxxListAction;
import com.highwaycenter.log.service.CzrzService;

@SuppressWarnings("all") //忽略该类中的所有警告
public class BzbxManagerAction extends BaseAction{

	private static final long serialVersionUID = -471807512428702930L;
	static Logger log = Logger.getLogger(BzbxManagerAction.class);
	@Resource(name="bzbxservice")
	private BzbxService bzbxService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private Integer xzqhdm;
	private String selectvalue;
	private Integer lxbh;
	private Integer page;
	private Integer rows;
	private String token;
	private BaseResult result;
	private HBzbxBzbx bzbx;
    private File uploadFile;
    private String uploadFileContentType;// 得到文件的类型
    private String uploadFileFileName;// 得到文件的名称
    private String selectId;  //标志标线编号
    
	/**
	 *  标志标线列表
	 * @param  xzqhdm
	 * @param  lxbh;
	 * @param page   
	 * @param rows
	 * @param selectvalue
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/bzbxlist?xzqhdm=?&lxbh=?&token=?&page=?&rows=?&selectvalue=?      
	 */
	/*@Action(value = "bzbxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectBzbxList(){

	/*	token=(token==null)?null:token.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }*/
   	    //验证权限
    	/* validate_code = this.glryservice.authorityCheck("bzbxlist",token);
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

	this.result = this.bzbxService.selectBzbxGkList(page, rows, xzqhdm, lxbh, selectvalue,selectId);
	//记录日志信息
	/*this.czrzService.saveCzrz(token,"查看标志标线");//基本权限 22.查看标志标线
*/	
	
	
	return "json";
}
	/**
	 *  标志标线详情
	 * @param bzbx.bzbxbh
	 * @param token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/bzbxxq     
	 */
	/*@Action(value = "bzbxxq", results = { @Result(name = "json", type = "json")})*/
	public String selectBzbxQx(){
		/*//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("bzbxxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
  	     if(bzbx==null||bzbx.getBzbxbh()==null||bzbx.getBzbxbh().equals("")){
  	    	 this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
 	 	    return "json";
  	     }
  	    	 
  	   this.result = this.bzbxService.selectBzbxXQ(bzbx.getBzbxbh());
  	   /*this.czrzService.saveCzrz(token,"查看标志标线");//基本权限 22.查看标志标线
*/		return "json";
	}
	
	/**
	 *  标志标线类型列表
	 * @return  result.list
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/bzbxlxlist?token=?
	 */
	/*@Action(value = "bzbxlxlist", results = { @Result(name = "json", type = "json")})*/
	public String selectBzbxlxList(){

	/*		//验证令牌
			 int validate_code = this.glryservice.dllpCheck(token);
	   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 		    return "json";
	 	     }*/

		this.result = this.bzbxService.selectBzbxlxList();

		return "json";
	}
	
	/**
	 *  新增标志标线
	 *  @param bzbx.xzqhdm  Integer 行政区划代码
	 *  @param bzbx.xlmc    String 线路名称
	 *  @param bzbx.bzbxlxbh  Integer 标志标线类型编号
	 *  @param bzbx.gldj      String 公路等级
	 *  @param bzbx.bzwzl     String 标志位置（左）
	 *  @param bzbx.bzwzm     String 标志位置（中）
	 *  @param bzbx.bzwzr     String 标志位置右
	 *  @param bzbx.bmnrta    String 版面内容图案
	 *  @param bzbx.bmnrsl    String 版面内容数量
	 *  @param bzbx.bmcc      String 版面尺寸
	 *  @param bzbx.zcxsjgg   String 支撑形式及规格
	 *  @param bzbx.azsj      String 安装时间
	 *  @param bzbx.zzazdw    String 制作安装单位
	 *  @param bzbx.gldw      String 管理单位
	 *  @param bzbx.bz        String 备注
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/savebzbx?token=?
	 */
	/*@Action(value = "savebzbx", results = { @Result(name = "json", type = "json")})*/
	public void saveBzbx(){
		String jsonResult = null;
		token=(token==null)?null:token.trim();		
	//验证令牌
		try{
		
		 int validate_code = this.glryservice.dllpCheck(token);
		 int aut_code = this.glryservice.authorityCheck("savebzbx",token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	       // jsonResult = "{'resultcode':-1,'resultdesc':'权限验证失败,请重新登陆'}";
	        jsonResult = "{'resultcode':"+result.getResultcode()+",'resultdesc':"+result.getResultdesc()+"'}";
	 	   } else if(validate_code != Constants.SUCCESS_CODE){//验证权限
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
    		 jsonResult = "{'resultcode':"+result.getResultcode()+",'resultdesc':"+result.getResultdesc()+"'}";
    		
   	     }else  if(bzbx==null||bzbx.getXzqhdm()==null||bzbx.getXzqhdm().equals("")){
        	this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
        	//jsonResult = "{'resultcode':3,'resultdesc':'信息填写不完整'}";
        	jsonResult = "{'resultcode':"+result.getResultcode()+",'resultdesc':"+result.getResultdesc()+"'}";
        }else{
        
        
        this.result = this.bzbxService.saveBzbx(bzbx,uploadFile,uploadFileFileName);
        jsonResult = "{'resultcode':"+result.getResultcode()+
				   ",'resultdesc':'"+result.getResultdesc()+"'}";	
        }
      //记录日志信息
	    if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	   this.czrzService.saveCzrz(token,"新增标志标线","新增标志标线");//基本权限 23.新增标志标线  	
	     }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html");
	    response.getWriter().write(jsonResult);
		
        
       
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  编辑标志标线
	 *  @param bzbx.bzbxbh  Integer 标志标线编号
	 *  @param bzbx.xzqhdm  Integer 行政区划代码
	 *  @param bzbx.xlmc    String 线路名称
	 *  @param bzbx.bzbxlxbh  Integer 标志标线类型编号
	 *  @param bzbx.gldj      String 公路等级
	 *  @param bzbx.bzwzl     String 标志位置（左）
	 *  @param bzbx.bzwzm     String 标志位置（中）
	 *  @param bzbx.bzwzr     String 标志位置右
	 *  @param bzbx.bmnrta    String 版面内容图案
	 *  @param bzbx.bmnrsl    String 版面内容数量
	 *  @param bzbx.bmcc      String 版面尺寸
	 *  @param bzbx.zcxsjgg   String 支撑形式及规格
	 *  @param bzbx.azsj      String 安装时间
	 *  @param bzbx.zzazdw    String 制作安装单位
	 *  @param bzbx.gldw      String 管理单位
	 *  @param bzbx.bz        String 备注
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/updatebzbx?token=?
	 */
	/*@Action(value = "updatebzbx", results = { @Result(name = "json", type = "json")})*/
	public void updateBzbx(){
	//验证令牌
		String jsonResult = null;
		 int validate_code = this.glryservice.dllpCheck(token);
		 int aut_code = this.glryservice.authorityCheck("updatebzbx",token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){	     
	         this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	         jsonResult = jsonTostring(result);
	     }else if(aut_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(aut_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
    		 jsonResult = jsonTostring(result);
   	     }else if(bzbx==null||bzbx.getBzbxbh()==null||bzbx.getBzbxbh().equals("")||
        	bzbx.getXzqhdm()==null||bzbx.getXzqhdm().equals("")){
        	this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
        	 jsonResult = jsonTostring(result);
        }else{
        this.result = this.bzbxService.updateBzbx(bzbx,uploadFile,uploadFileFileName);
        jsonResult = jsonTostring(result);
        }
        //记录日志信息
	    if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
      	     this.czrzService.saveCzrz(token,"编辑标志标线","编辑标志标线");//基本权限 24.编辑标志标线
	     }
      	try {
      	HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html");
	    
			response.getWriter().write(jsonResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 *  删除标志标线
	 * @param bzbx.bzbxbh  Integer 标志标线编号
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/deletebzbx?token=?
	 */
	/*@Action(value = "deletebzbx", results = { @Result(name = "json", type = "json")})*/
	public String deleteBzbx(){
	    //验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 	    return "json";
	 	   }
	     //验证权限
    	 validate_code = this.glryservice.authorityCheck("deletebzbx",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     
	     
        if(bzbx==null||bzbx.getBzbxbh()==null||bzbx.getBzbxbh().equals("")){
        	this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
	 	    return "json";
        }
          this.result = this.bzbxService.deleteBzbx(bzbx.getBzbxbh());
        
        //记录日志信息  
        if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
      	this.czrzService.saveCzrz(token,"删除标志标线","删除标志标线");//基本权限 25.删除标志标线
        }
		return "json";
	}
	
	/**
	 * 查询所有国标图片路径
	 * @param token
	 * @return
	 * 接口实例：http://localhost:8080/HighWayCenter/bzbxmanager/chinesestandardlist?token=?
	 */
	public String selectChineseStandard(){
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 	    return "json";
	 	   }
		this.result = this.bzbxService.selectChineseStandard();
		return "json";
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

	public Integer getLxbh() {
		return lxbh;
	}
	public void setLxbh(Integer lxbh) {
		this.lxbh = lxbh;
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

	public HBzbxBzbx getBzbx() {
		return bzbx;
	}

	public void setBzbx(HBzbxBzbx bzbx) {
		this.bzbx = bzbx;
	}
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	
	protected String jsonTostring(BaseResult result){
		String jsonString="{resultcode:"+result.getResultcode()+",resultdesc:'"+result.getResultdesc()+"'}";
		return jsonString;
	}
	public String getSelectId() {
		return selectId;
	}
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

}
