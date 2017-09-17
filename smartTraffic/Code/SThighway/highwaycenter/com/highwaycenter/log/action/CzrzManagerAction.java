package com.highwaycenter.log.action;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.struts2.ServletActionContext;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.utils.FileUpload;
import com.common.utils.InputFomartConversion;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.model.HJcCzrz;
import com.highwaycenter.log.service.CzrzService;
import com.opensymphony.xwork2.ActionContext;
@SuppressWarnings("all") //忽略该类中的所有警告
public class CzrzManagerAction extends BaseAction{
	private static final long serialVersionUID = 322995768142400222L;
	
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	private String token;
	private Integer rows;
	private Integer page;
	private HJcCzrz czrz;
	private BaseResult result;
	private String startTime;
	private String endTime;
	private String selectvalue;
	private String rzbhlist;    //日志编号集合String
	private Integer rzbh;     // 日志编号
	
	/**
	 *  操作日志列表
	 * @param  startTime  查询开始时间
	 * @param  endTime  查询结束时间
	 * @param  selectvalue  搜索用户名
	 * @param page   
	 * @param rows
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/czrzmanager/czrzlist?token=&startTime=?&endTime=?&selectvalue=?
	 */
	/*@Action(value = "czrzlist", results = { @Result(name = "json", type = "json")})*/
	public String selectCzrzList(){

		//数据格式
		
		token=(token==null)?null:token.trim();
		startTime=(startTime==null)?null:startTime.trim();
		endTime=(endTime==null)?null:endTime.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //验证权限
    	 validate_code = this.glryservice.authorityCheck("czrzlist",token);
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

	this.result = this.czrzService.selectCzrzList(page,rows,startTime,endTime,selectvalue);
	return "json";
}

	/**
	 *  导出操作日志Excel
	 *  @param token
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/czrzmanager/exportexcel?token=?
	 */
	//导出excel表格
	public void  exportExcel(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		String jsonResult = null;
		startTime=(startTime==null)?null:startTime.trim();
		endTime=(endTime==null)?null:endTime.trim();
		selectvalue=(selectvalue==null)?null:selectvalue.trim();
		try {
			//验证令牌
			 int validate_code = this.glryservice.dllpCheck(token);
	   	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 		   jsonResult = "{'resultcode':"+validate_code+",'resultdesc':'"+Constants.DLLP_CHECK_FAIL_INFO+"'}";
	 		  response.getWriter().write(jsonResult);
	 	     }else {
	   	      this.result = this.czrzService.excelCreat(startTime,endTime,selectvalue);
	   	     // result.setResultcode(-1);
	          if(result.getResultcode()!=1||result.getObj()==null||result.getObj().equals("")){//文件生成失败
	        	 jsonResult = "{'resultcode':"+result.getResultcode()
	   	    			 +",'resultdesc':'"+result.getResultdesc()+"'}";
	        	 response.getWriter().write(jsonResult);
	         }else{
			
			//获取下载路径
			String downloadpath = (String) result.getObj();
			
			/*OutputStream out = new FileOutputStream(downloadpath);  */
			// path是指欲下载的文件的路径。
			File file = new File(downloadpath);
			// 取得文件名。
			String filename = file.getName();
			
			 HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
			//解决中文乱码问题
			 String userAgent = request.getHeader("User-Agent");
		    
            String encodeName = filenameEncode(filename,userAgent);
           //英文名直接getbytes
           // String encodeName = "filename:"+ new String(filename.getBytes());
            if(encodeName==null){
            	encodeName = "file="+(URLEncoder.encode(filename,"UTF-8")).getBytes();
            }
			//System.out.println("filename:"+ new String(filename.getBytes()));
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(downloadpath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			//response.setContentType("application/x-msdownload");
			response.setContentType("application/x-download"); 
			/*response.setContentType("application/vnd.ms-excel");*/
			response.setHeader("Content-disposition","attachment;"+encodeName);
			this.czrzService.saveCzrz(token,"导出日志","导出日志");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			
	       }
	     }
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
	
		
		
	}
	
	/**
	 *  操作日志详情
	 * @param rzbh   Integer日志编号
	 * @param token
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/czrzmanager/czrzxq?token=?&rzbh=     
	 */
	/*@Action(value = "czrzxq", results = { @Result(name = "json", type = "json")})*/
	public String selectCzrzxq(){
	
		//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	   //验证权限
    	 validate_code = this.glryservice.authorityCheck("czrzxq",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
         if(rzbh==null||rzbh.equals("")){
        	 this.result = new BaseResult(Constants.CZRZ_BHNONE_CODE,Constants.CZRZ_BHNONE_INFO);
        	 return "json";
         }
  	     this.result = this.czrzService.selectCzrzInfo(rzbh);
  	  
		return "json";
	}
	
	
	/**
	 *  新增操作日志
	 *  @param 
	 *  @param czrz.czr       String 操作人员
	 *  @param czrz.rzlxbh    String 日志类型编号
	 *  @param czrz.rzbt      String 日志标题
	 *  @param czrz.rzmc      String 日志内容
	 *  @param token      
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/czrzmanager/czrzsave?token=?czrz.rzmc=aa
	 */
	/*@Action(value = "czrzsave", results = { @Result(name = "json", type = "json")})*/
	public String saveCzrz(){
		
		token=(token==null)?null:token.trim();
		
	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	        return "json";
	 	   }
	     //验证权限
    	/* validate_code = this.glryservice.authorityCheck("czrzsave"); //暂时没有保存新增日志这个权限
    	 if(validate_code == Constants.AUTHORITY_CHECK_FAIL_CODE){
    		 this.result =  new BaseResult(Constants.AUTHORITY_CHECK_FAIL_CODE,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
	     
	     this.result = this.czrzService.saveCzrz(czrz,token);
         return "json";
	
	}
	

	
	/**
	 *  批量删除操作日志
	 * @param  rzbhlist  String 日志编号 格式","
	 * @return  result
	 * 接口实例：http://localhost:8080/HighWayCenter/czrzmanager/deleteczrz?token=?&rzbhlist=?
	 */
	/*@Action(value = "deleteczrz", results = { @Result(name = "json", type = "json")})*/
	public String deleteCzrzList(){
	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	     if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	        this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	 	    return "json";
	 	   }
	     //验证权限
    	 validate_code = this.glryservice.authorityCheck("deleteczrz",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
	     
	     List<String> czlzlist = new ArrayList<String> ();
			if(rzbhlist!=null&&!rzbhlist.trim().equals("")){
				//czlzlist = InputFomartConversion.springSplitStringlist(rzbhlist);
				this.result = this.czrzService.deleteCzrzList(rzbhlist);
			}else{
				this.result =  new BaseResult(Constants.CZRZ_BHNONE_CODE,Constants.CZRZ_BHNONE_INFO);
				return "json";
			}
        //记录日志信息
      	this.czrzService.saveCzrz(token,"删除日志","删除日志");//基本权限 34.删除日志
		return "json";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public HJcCzrz getCzrz() {
		return czrz;
	}
	public void setCzrz(HJcCzrz czrz) {
		this.czrz = czrz;
	}
	public BaseResult getResult() {
		return result;
	}
	public void setResult(BaseResult result) {
		this.result = result;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSelectvalue() {
		return selectvalue;
	}
	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}
	public String getRzbhlist() {
		return rzbhlist;
	}
	public void setRzbhlist(String rzbhlist) {
		this.rzbhlist = rzbhlist;
	}
	public Integer getRzbh() {
		return rzbh;
	}
	public void setRzbh(Integer rzbh) {
		this.rzbh = rzbh;
	}
	
	
	//兼容多浏览器的下载文件中文名乱码
	public static String filenameEncode(String filename,String userAgent){
	   	String rtn = "";
	     try { 
	    	String new_filename = URLEncoder.encode(filename, "UTF8");  
	        
	    	// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的  
	    	 rtn = "filename=\"" + new_filename + "\"";  
	    	 
	    	
	    	if (userAgent != null)  
	    	{  
	    	     userAgent = userAgent.toLowerCase();  
	    	      // IE浏览器，只能采用URLEncoder编码  
	    	     if (userAgent.indexOf("msie") != -1)  
	    	    {  
	    	        rtn = "filename=\"" + new_filename + "\"";  
	    	    }  
	    	     // Opera浏览器只能采用filename*  
	    	     else if (userAgent.indexOf("opera") != -1)  
	    	     {  
	    	        rtn = "filename*=UTF-8''" + new_filename;  
	    	    }  
	    	    // Safari浏览器，只能采用ISO编码的中文输出  
	    	      else if (userAgent.indexOf("safari") != -1 )  
	    	      {  
	    	        
						rtn = "filename=\"" + new String(filename.getBytes("UTF-8"),"ISO8859-1") + "\"";
					
	    	      }  
	    	      // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出  
	    	      else if (userAgent.indexOf("applewebkit") != -1 )  
	    	       {  
	    	        
	    	          rtn = "filename=\"" + new_filename + "\"";  
	    	       }  
	    	      // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出  
	    	       else if (userAgent.indexOf("mozilla") != -1)  
	    	       {  
	    	          rtn = "filename*=UTF-8''" + new_filename;  
	    	      }  
	    	   }  
	    	  } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	    	 return rtn;
	    }
	             
	
	
	

}
