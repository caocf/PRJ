package com.highwaycenter.video.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.video.model.HSpSxx;
import com.highwaycenter.video.service.VideoSurveillanceService;
import com.highwaycenter.video.service.videoListService;
import com.opensymphony.xwork2.ActionContext;

public class VideoSurveillanceAction extends BaseAction{


	private static final long serialVersionUID = -5594113115553302306L;
    @Resource(name="videoservice")
	private VideoSurveillanceService videoService;	
    @Resource(name="videolistservice")
    private videoListService videolistService;
    @Resource(name="glryservice")
	private GlryService glryservice;
	private Integer rows;
	private Integer page;
	private String token;
	private BaseResult result;
	private Integer deviceId;
	private Integer cameraId;
	private Integer controlUnitId;//控制中心编号
	private Integer regionId;//区域编号
	private Integer sxxId;//视频上下行编号
	private HSpSxx spsxx;//视频上下行
	private String selectvalue;//联动字段
	private Integer type;//1是路线代码 4是方向名


    /**
     * 插件下载
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/exedownload
     */
    public String downloadExe(){
       try{
    	//获取下载路径
		String downloadpath = ServletActionContext.getServletContext().getRealPath("/")
				 +"/exedownload/WebComponents.exe";
		File file = new File(downloadpath);
		// 取得文件名。
		String filename = file.getName();
		
		// HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		
       //英文名直接getbytes
        String encodeName = "filename="+ new String(filename.getBytes());
        if(encodeName==null){
        	encodeName = "filename="+(URLEncoder.encode(filename,"UTF-8")).getBytes();
        }

		InputStream fis = new BufferedInputStream(new FileInputStream(downloadpath));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes()));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(
				response.getOutputStream());
		response.setContentType("application/octet-stream bin");  //下载exe格式
		response.setHeader("Content-disposition","attachment;"+encodeName);
		
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
    	
       }catch(Exception  e){
    		e.printStackTrace();
    	}
       return "json";
       }


   /**
    * 获取所有设备及组织
    * 
    * 
    * 接口实例：http://localhost:8080/HighWayCenter/videolist/updatesqldate
    */
    public String selectAllDCCR(){
    	try{
    	int validate_code = this.glryservice.dllpCheck(token);
      	/* if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
    		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
    		    return "json";
    	     }
      	    //令牌验证结束
        	//验证权限
      	 validate_code = this.glryservice.authorityCheck("whspsxx",token);
      	 if(validate_code != Constants.SUCCESS_CODE){
   		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }*/
    	this.videoService.selectAllDC();
    	this.videoService.selectRegionCamera();
    	this.result = new  BaseResult(1,"更新成功");
    	}catch(Exception e)
    	{
    		this.result = new BaseResult(1004,"更新失败");
    		return "json";
    	}
    	return "json";
    }
    
    /**
     *  根据区域获取摄像机列表
    * @Param token
    * @param regionId
    * @return
    * 接口实例：http://localhost:8080/HighWayCenter/videolist/regioncameralist
    */
   public String selectRegionCameraList(){
   	//验证令牌
/*		 int validate_code = this.glryservice.dllpCheck(token);
  	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //令牌验证结束
    	//验证权限
  	 validate_code = this.glryservice.authorityCheck("cameralist",token);
  	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
	if(regionId==null||regionId.equals("")){
		this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
		return "json";
	}
  	
   	this.result = this.videolistService.selectCameraByRegion(regionId);
   	return "json";
   }
    /**
     * 获取设备和区域关系
     * 
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/updatesqlconnection
     * @return
     */
    public String selectRegionCamera(){
    	try{
    	/*int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	    //令牌验证结束
       	//验证权限
     	 validate_code = this.glryservice.authorityCheck("whspsxx",token);
     	 if(validate_code != Constants.SUCCESS_CODE){
  		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
  		    return "json";
  	     }*/
    	this.videoService.selectRegionCamera();
    	}catch(Exception e)
    	{
    		this.result = new BaseResult(1004,"更新失败");
    		return "json";
    	}
    	return "json";
    }
    
    /**
     * 设备列表
     * @Param token
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/devicelist
     */
    public String selectDeviceList(){
    	//验证令牌
	 int validate_code = this.glryservice.dllpCheck(token);
/*   	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //令牌验证结束
     	//验证权限
   	 validate_code = this.glryservice.authorityCheck("devicelist",token);
   	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
    	this.result = this.videolistService.selectAllDevice();
    	return "json";
    }
    
    /**
     * camera列表
     * @Param token
     * @Param  deviceId Integer 设备id
     * @return result.list
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/cameralist
     */
    public String selectCameraList(){
        	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	/* if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //令牌验证结束
    	//验证权限
  	 validate_code = this.glryservice.authorityCheck("cameralist",token);
  	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
    	if(deviceId==null||deviceId.equals("")){
    		this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		return "json";
    	}
    	this.result = this.videolistService.selectAllCamera(deviceId);
    	return "json";
    	
    }
    
    /**
     * 通道信息
     * @Param token
     * @Param  cameraId Integer 摄像机id 
     * @return result.obj
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/cameradto
     */
    public String selectCameraDTO(){
        	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
  	/* if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //令牌验证结束
    	//验证权限
  	 validate_code = this.glryservice.authorityCheck("cameralist",token);
  	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
    	if(cameraId==null||cameraId.equals("")){
    		this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		return "json";
    	}
    	this.result = this.videolistService.selectCameraDto(cameraId);
    	
    	return "json";
    }
    
    /**
     * 控制中心列表
     * @Param token
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/controlunitlist
     */
    public String selectControlUnitList(){
    	//验证令牌
	/*	 int validate_code = this.glryservice.dllpCheck(token);
   	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //令牌验证结束
     	//验证权限
   	 validate_code = this.glryservice.authorityCheck("cameralist",token);
   	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
    	this.result = this.videolistService.selectControlCenter();
    	return "json";
    }
    
    /**
     * 区域列表
     * @Param token
     * @Param controlUnitId
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/regionlist
     */
    public String selectRegionList(){
    	//验证令牌
	/* int validate_code = this.glryservice.dllpCheck(token);
   	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
 		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
 		    return "json";
 	     }
   	    //令牌验证结束
     	//验证权限
   	 validate_code = this.glryservice.authorityCheck("cameralist",token);
   	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }*/
 	if(controlUnitId==null||controlUnitId.equals("")){
		this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
		return "json";
	}
   	
    	this.result = this.videolistService.selectRegion(controlUnitId);
    	return "json";
    }
    
    
  
    
    
    /**
     * 新增视频上下行
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/savespsxx
     */
    public String saveSpSxx(){
        //验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	    //令牌验证结束
       	//验证权限--维护视频
     	  validate_code = this.glryservice.authorityCheck("whspsxx",token);
       	 if(validate_code != Constants.SUCCESS_CODE){
   		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }    
       	 
       	 if(spsxx==null){
       		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,
       				 Constants.REQUEST_PARAM_NULL_INFO);
       		 return "json";
       	 }
       	 this.result = this.videolistService.saveSpssx(spsxx);
       	 
    	return "json";
    }
    /**
     * 编辑视频上下行
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/updatespsxx
     */
    public String updateSpSxx(){
        //验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	 //令牌验证结束
       	//验证权限--维护视频
     	  validate_code = this.glryservice.authorityCheck("whspsxx",token);
       	 if(validate_code != Constants.SUCCESS_CODE){
   		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }    
      	 if(spsxx==null||spsxx.getSxxId()==null){
       		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,
       				 Constants.REQUEST_PARAM_NULL_INFO);
       		 return "json";
       	 }
       	this.result =  this.videolistService.updateSpssx(spsxx);

    	return "json";
    }
    
    
    /**
     * 删除视频上下行
     * http://localhost:8080/HighWayCenter/videolist/deletespsxx
     */
    public String deleteSpSxx(){
        //验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	    //令牌验证结束
       	//验证权限--维护视频
     	  validate_code = this.glryservice.authorityCheck("whspsxx",token);
       	 if(validate_code != Constants.SUCCESS_CODE){
   		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }    
       	 if(sxxId==null){
       		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,
       				 Constants.REQUEST_PARAM_NULL_INFO);
       		 return "json";
       	 }
       	this.result =  this.videolistService.deleteSpssx(sxxId);
    	return "json";
    }   
    
    /**
     * 视频上下行上下拉框
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/spsxxoption
     */
    public String selectSpSxxoption(){
        //验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	    //令牌验证结束
       	
       	 if(type==null){
       		 this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,
       				 Constants.REQUEST_PARAM_NULL_INFO);
       		 return "json";
       	 }
       	 this.result = this.videolistService.selectCameraName(type, selectvalue);
    	return "json";
    }
    


	/**
     * 查询视频上下行列表
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/spsxxlist
     */
    public String selectSpSxxList(){
    //验证令牌
	 int validate_code = this.glryservice.dllpCheck(token);
  	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //令牌验证结束
    	//验证权限--维护视频
  	  validate_code = this.glryservice.authorityCheck("whspsxx",token);
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
    	 
    	this.result = this.videolistService.selectSpssxList(page, rows);
    	
    	return "json";
    }
    
    /**
     * 查询视频上下行内容
     * @return
     * 接口实例：http://localhost:8080/HighWayCenter/videolist/spsxxunique
     */
    public String selectSpSxxUnique(){
    //验证令牌
	 int validate_code = this.glryservice.dllpCheck(token);
  	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		    return "json";
	     }
  	    //令牌验证结束
    	//验证权限--维护视频
  	  validate_code = this.glryservice.authorityCheck("whspsxx",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
		    return "json";
	     }    
        	if(sxxId==null||sxxId.equals("")){
    		    this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,
      				 Constants.REQUEST_PARAM_NULL_INFO);
      		 return "json";
        	}
    	this.result = this.videolistService.selectUnique(sxxId);
    	
    	return "json";
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



	public Integer getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}



	public Integer getCameraId() {
		return cameraId;
	}



	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}


	public Integer getControlUnitId() {
		return controlUnitId;
	}


	public void setControlUnitId(Integer controlUnitId) {
		this.controlUnitId = controlUnitId;
	}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public Integer getSxxId() {
		return sxxId;
	}


	public void setSxxId(Integer sxxId) {
		this.sxxId = sxxId;
	}


	public HSpSxx getSpsxx() {
		return spsxx;
	}


	public void setSpsxx(HSpSxx spsxx) {
		this.spsxx = spsxx;
	}


	public String getSelectvalue() {
		return selectvalue;
	}


	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}
    	
   
    public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}

	
	
	
}
