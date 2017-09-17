package com.SmartTraffic.video.action;

import javax.annotation.Resource;

import com.SmartTraffic.video.service.VideoService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;

public class VideoAction extends BaseAction{

	private static final long serialVersionUID = 203023246115287036L;
	//public static  VideoCallBack videoCall ;
	@Resource(name="videoService")
	private VideoService videoService;	
	public BaseResult result;
	public int unitId;
	public int regionId;
	public int cameraId;
	public String cameraName;//摄像头名称
	public float longitude;  //经度
	public float latitude;   //纬度
	
	/*public String loginVideoPlat(){
	videoLibrary li= videoLibrary.Instance;	
		li.Plat_Init();
		int handle = li.Plat_LoginCMS("172.20.44.194","admin","12345","8011");
		//获取视频
		final String camera_id="3849";
		final String url_paly = li.Plat_QueryRealStreamURL(camera_id,handle);
		//回调函数
		int play_start = li.Plat_PlayVideo(url_paly, new LongByReference(123), handle,videoLibrary.mRealPlayCallBack,Pointer.NULL);
		System.out.println(" play_start："+play_start);
		return "success";
		
	}*/
	
	
    //获取控制中心
	public String selectControlUnit(){
		this.result = this.videoService.selectControlUnit();
		return "success";
	
	}
	
	//获取区域
	public String selectRegion(){
		this.result = this.videoService.selectRegion(unitId);
		return "success";
	}
	
	//获取摄像头
	public String selectCamere(){
		this.result = this.videoService.selectCamere(regionId);
		return "success";
	}
	
	
	//修改经纬度
	public String updateLonlat(){
		try{
			this.result =	this.videoService.updateLonlat(cameraId, longitude, latitude);
			
		}catch(Exception e){
			this.result = new BaseResult(1001,"更新失败");
		}
		return "success";
	}
	
	public String selectLonlat(){
		this.result = this.videoService.selectLonlat(cameraId);
		return "success";
		
	}
	
	public String selectPlayInfo(){
		try{
		System.out.println(cameraName);
		this.result = this.videoService.selectPlayInfo(cameraId);
		
		return "success";
		}catch(Exception e){
			this.result = new BaseResultFailed("没有播放参数");
			return "success";
		}
	}
	
	public String tbGlvideo(){
		this.result = this.videoService.tbGlvideo();
		return "success";
	}
	
	
	public String tbVideo(){
		this.result = this.videoService.tbVideo();
		return "success";
	}
	
	public String selectAllCamera(){
		this.result = this.videoService.selectAllCamera();
		return "success";
	}
	
	public BaseResult getResult() {
		return result;
	}


	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}


	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	
	
}
