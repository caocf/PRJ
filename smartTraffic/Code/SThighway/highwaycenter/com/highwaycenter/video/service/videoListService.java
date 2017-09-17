package com.highwaycenter.video.service;

import com.common.action.BaseResult;
import com.highwaycenter.video.model.HSpCameraDTO;
import com.highwaycenter.video.model.HSpSxx;


public interface videoListService {
	
	//获取所有设备信息
	public BaseResult selectAllDevice();
	
	//获取单个设备的通道信息

	public BaseResult selectAllCamera(Integer deviceId);
	
	//获取单个通道信息
	
	public BaseResult selectCameraDto(Integer cameraId);
	
	//获取控制中心
	public BaseResult selectControlCenter();
	
	//根据控制中心获取组织机构
	public BaseResult selectRegion(Integer controlUnitId);
	
	//根据组织机构获取摄像
	public BaseResult selectCameraByRegion(Integer regionId);
	
	//获取经纬度
	public HSpCameraDTO selectCameraJwd(String cameraname);
	
	//获取上下行列表
	public BaseResult selectSpssxList(int page,int rows);
	
	//获取上下行的下拉框
	public BaseResult selectCameraName(int type,String selectvalue);
	
	//新建上下行
	public BaseResult saveSpssx(HSpSxx sxx);
	
	//删除上下行
	public BaseResult deleteSpssx(int sxxId);
	
	//修改上下行
	public BaseResult updateSpssx(HSpSxx sxx);
	
    //查询上下行详情
	public BaseResult selectUnique(int sxxId);
}
