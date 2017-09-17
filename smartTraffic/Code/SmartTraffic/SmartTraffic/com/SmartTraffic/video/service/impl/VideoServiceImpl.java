package com.SmartTraffic.video.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.SmartTraffic.common.CoordinateUtil;
import com.SmartTraffic.highway.dao.HighwayBaseDao;
import com.SmartTraffic.video.dao.VideoDao;
import com.SmartTraffic.video.dao.impl.VideoAccessDao;
import com.SmartTraffic.video.model.CameraDto;
import com.SmartTraffic.video.model.STCamera;
import com.SmartTraffic.video.model.STControlUnit;
import com.SmartTraffic.video.model.STRegion;
import com.SmartTraffic.video.service.VideoService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.tree.model.Tree;

@Service("videoService")
public class VideoServiceImpl extends BaseService implements VideoService{

	@Resource(name="videoDao")
	private VideoDao videoDao;
	@Resource(name="videoAccessDao")
	private VideoAccessDao videoAccessDao;
	@Resource(name="highwayBaseDao")
	private HighwayBaseDao highwayBaseDao;
	
	
	

	@Override
	public BaseResult selectControlUnit() {
		//List<STControlUnit> unitlist = this.videoDao.selectControlUnit();
		List<?> unitlist = this.videoDao.selectControlList();
		BaseResult result = new BaseResultOK();
		
		result.setObj(unitlist);
		return result;
	}



	@Override
	public BaseResult selectRegion(int unitId) {
		//List<STRegion> regionList = this.videoDao.selectRegionByUnit(unitId);
		List<?> regionList = this.videoDao.selectRegionList(unitId);
        BaseResult result = new BaseResultOK();
		result.setObj(regionList);
		return result;
	}



	@Override
	public BaseResult selectCamere(int regionId) {
		//List<STCamera> cameraList = this.videoDao.selectSTCameraByRegion(regionId);
		List<?> cameraList = this.videoDao.selectCamera(regionId);
        BaseResult result = new BaseResultOK();	
		result.setObj(cameraList);
		return result;
	}

	@Override
	public BaseResult updateLonlat(int cameraId,float longitude,float latitude) {
		this.videoDao.updateLonlat(cameraId, longitude, latitude);
		return new BaseResultOK();
	}



	@Override
	public BaseResult selectLonlat(int cameraId) {
        BaseResult result = new BaseResultOK();	
        Object obj  = this.videoDao.selectCameraItem(cameraId);
        result.setObj(obj);
		return result;
	}



	@SuppressWarnings("unchecked")
	@Override
	public BaseResult selectPlayInfo(int cameraId) {
		try{
			Object obj = this.videoAccessDao.selectPlayInfo(cameraId);
			if(obj!=null){
		      Map<String,Object> map = (Map<String, Object>) obj;
			  BaseResult result = new BaseResultOK();	
			  result.setObj(creatXmlByDom4j(map));
			  return result;
			}else{
				 return  new BaseResultFailed("没有播放参数");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResultFailed("没有播放参数");
		}
		
	}
	
	
	
	/**
	 * 创建xml 格式的result
	 * @param result
	 * @param body
	 * @return
	 */
	private String creatXmlByDom4j(Map<String,Object> map){
		    String strXML = null;
	        Document document = DocumentHelper.createDocument();
	        Element root = document.addElement("Parament");
	        
	        Element MatrixCode = root.addElement("MatrixCode");
	        MatrixCode.addText(getValueByParam("MATRIXCODE",map));
	        
	        Element MonitorID = root.addElement("MonitorID");
	        MonitorID.addText(getValueByParam("MONITORID",map));
	        
	        Element CameraID = root.addElement("CameraID");
	        CameraID.addText(getValueByParam("CAMERAID",map));
	        
	        Element CameraName = root.addElement("CameraName");
	        CameraName.addText(getValueByParam("CAMERANAME",map));
	        
	        Element DeviceIP = root.addElement("DeviceIP");
	        //DeviceIP.addText(getValueByParam("DEVICEIP",map));
	        DeviceIP.addText("172.20.44.194");
   
	        Element DevicePort = root.addElement("DevicePort");
	     //   DevicePort.addText(getValueByParam("DEVICEPORT",map));
	        DevicePort.addText("8011");
	        
	        Element DeviceType = root.addElement("DeviceType");
	        DeviceType.addText(getValueByParam("DEVICETYPE",map));
	        
	        Element User = root.addElement("User");
	        User.addText(getValueByParam("user",map));
	        
	        Element Password = root.addElement("Password");
	        Password.addText(getValueByParam("PASSWORD",map));
	        
	        Element ChannelNum = root.addElement("ChannelNum");
	        ChannelNum.addText(getValueByParam("CHANNELNUM",map));
	        
	        Element ProtocolType = root.addElement("ProtocolType");
	        ProtocolType.addText(getValueByParam("PROTOCOLTYPE",map));
	        
	        Element StreamType = root.addElement("StreamType");
	        StreamType.addText(getValueByParam("STREAMTYPE",map));
	        
	        Element IsEhomeDevice = root.addElement("IsEhomeDevice");
	        IsEhomeDevice.addText(getValueByParam("ISEHOMEDEVICE",map));
	        
	        Element VagIp = root.addElement("VagIp");
	       // VagIp.addText(getValueByParam("user",map));
	        
	        Element VagPort = root.addElement("VagPort");
	        //StreamType.addText(getValueByParam("STREAMTYPE",map));
	        
	        Element UseCisco = root.addElement("UseCisco");
	        UseCisco.addText(getValueByParam("USECISCO",map));
	                
	        Element RegionID = root.addElement("RegionID");
	        RegionID.addText(getValueByParam("REGIONID",map));        
	        
	        Element ControlUnitID = root.addElement("ControlUnitID");
	        ControlUnitID.addText(getValueByParam("CONTROLUNITID",map));      
	        
	        Element CameraIndexCode = root.addElement("CameraIndexCode");
	        CameraIndexCode.addText(getValueByParam("CAMERAINDEXCODE",map));
	        
	        Element StreamServerIP = root.addElement("StreamServerIP");
	      //  StreamServerIP.addText(getValueByParam("CAMERAINDEXCODE",map));   
	        
	        Element StreamServerPort = root.addElement("StreamServerPort");
	        
	        Element IsUseStreamServer = root.addElement("IsUseStreamServer");
	        IsUseStreamServer.addText(getValueByParam("ISUSESTREAMSERVER",map));
	        
	        Element DeviceID = root.addElement("DeviceID");
	        DeviceID.addText(getValueByParam("DEVICEID",map));     
	        
	        Element UserID = root.addElement("UserID");
	        UserID.addText(getValueByParam("USERID",map));
	        
	        Element PagIP = root.addElement("PagIP");
	      // PagIP.addText(getValueByParam("USERID",map));
	        
	        Element PagPort = root.addElement("PagPort");
	        
	        Element UserSession = root.addElement("UserSession");
	       // UserSession.addText(getValueByParam("USERSESSION",map));
	        UserSession.addText("fc6f8f75-f28a-483b-9a7b-e7fa2fb1be17");
	        
	        Element UserRightLevel = root.addElement("UserRightLevel");
	        UserRightLevel.addText(getValueByParam("USERRIGHTLEVEL",map));
	        
	        Element LinkedMode = root.addElement("LinkedMode");
	        LinkedMode.addText(getValueByParam("LINKEDMODE",map));
	        
	        Element UseStreamServerCascade = root.addElement("UseStreamServerCascade");
	        UseStreamServerCascade.addText(getValueByParam("USESTREAMSERVERCASCADE",map));
	        
	        
	        Element CascadeStreamRtspPath = root.addElement("CascadeStreamRtspPath");
	        //CascadeStreamRtspPath.addText(getValueByParam("USESTREAMSERVERCASCADE",map));
	       
	        Element Cascade = root.addElement("Cascade");
	        Cascade.addText(getValueByParam("CASCADE",map));
	        
	        Element CascadeCameraIndexcode = root.addElement("CascadeCameraIndexcode");
	        CascadeCameraIndexcode.addText(getValueByParam("CASCADECAMERAINDEXCODE",map));
	       
	        Element CascadeIasIp = root.addElement("CascadeIasIp");
	        CascadeIasIp.addText(getValueByParam("CASCADEIASIP",map));
	        
	        Element CascadeIasPort = root.addElement("CascadeIasPort");
	        CascadeIasPort.addText(getValueByParam("CASCADEIASPORT",map));
	        
	        StringWriter strWtr = new StringWriter();
	        OutputFormat format = OutputFormat.createPrettyPrint();
/*	        format.setEncoding("utf-8")*/;
	        XMLWriter xmlWriter =new XMLWriter(strWtr, format);
	        try {
	        	
	            xmlWriter.write(document);
	        } catch (IOException e1) {
	            System.out.println("创建xml失败");
	            e1.printStackTrace();
	            return "创建xml失败"+e1;
	        }
	        strXML = strWtr.toString();
	        //System.out.println(strXML);
	        return strXML;
	}
	
	
	private String getValueByParam(String param,Map<String,Object> map){
		Object val = map.get(param);
		if(val!=null){
			return val.toString();
		}else{
			return "";
		}
	}



	@Override
	public BaseResult tbGlvideo() {
		//1.获取公路数据库数据，转换
		List<CameraDto> camelist = new ArrayList<CameraDto>();
		//2.更新
		try{
		List<Map<String,Object>> list = (List<Map<String, Object>>) highwayBaseDao.selectVideoInfo();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String name = (String) map.get("name");
			String index = (String) map.get("indexCode");
			System.out.println("index"+index);
			CameraDto jwd = this.selectCameraJwd(name);
			if(jwd==null){
				continue;
			}
			jwd.setIndexCode(index);
			camelist.add(jwd);
			
		}
		this.videoDao.plUpdate(camelist);
	}catch(Exception e){
		e.printStackTrace();
		}
		return new BaseResultOK();
	}
	
	
	
	
	public  CameraDto selectCameraJwd(String cameraname)  {
		try{
		//分割名字
		 if(cameraname.indexOf(" ")<0||cameraname.indexOf("K")<0){
			 return null;
		 }
		String[] knames = cameraname.split(" ");
		if(knames.length>4){
			String fxm =knames[4];
			String lxdm = knames[1];
			String knamepre = knames[2];
			String knameafter = knames[3];
			if(knamepre.indexOf("K")<0){
				return null;
			}
			String stakenames = CoordinateUtil.transferStakename(lxdm, knamepre, knameafter);
			//String stakenames = CoordinateUtil.transferStakename("G2501", "K100", "100");
			Integer dir = this.highwayBaseDao.selectDir(lxdm, fxm);
			String dirstr = null;
			if(dir!=null){
				dirstr = dir.toString();
			}
            String json = CoordinateUtil.transferKzh(stakenames, null, dirstr);
            if(json==null||json.equals("")){
            	return null;
            }
            
            JSONObject  jsonObj = JSONObject.parseObject(json);
            String statusCode = jsonObj.getString("statuscode");
            if(statusCode.equals("100000")){
            	JSONArray jsonarray = jsonObj.getJSONArray("stakelist");
            	JSONObject stakeobj = jsonarray.getJSONObject(0);
            	Double jw = stakeobj.getDouble("offsetlongitude");
            	Double wd = stakeobj.getDouble("offsetlatitude");
            	CameraDto jwcamera = new CameraDto();
            	jwcamera.setLon(jw);
            	jwcamera.setLan(wd);
            	return jwcamera;
            }else{
            	return null;
            }
		}
		}catch( JSONException e){
			e.printStackTrace();
		
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return null;
	}



	@Override
	public BaseResult selectAllCamera() {
		try{
			List list = this.videoDao.selectAllCamera();
			BaseResult result = new BaseResult();
			result.setObj(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResultFailed("没有视频信息");
		}
	}



	@Override
	public BaseResult tbVideo() {
		try{
		//查询所有重命名并且有经纬度信息的数据
	    List<Map<String,Object>> list = (List<Map<String, Object>>) this.videoDao.selectAllSameCamera();
					
	   for(int i=0;i<list.size();i++){

		Map<String,Object> map = list.get(i);
		String name = (String) map.get("NAME");
		String  lat= (String) map.get("LATITUDE");
		String lon = (String) map.get("LONGITUDE");
		String  index= (String) map.get("INDEX_CODE");
		String hql = String.format("update Z_AC_CAMERA_INFO set LONGITUDE = '%s',LATITUDE = '%s' where NAME ='%s'  ",lon,lat,name,index);
	    System.out.println(hql+";");
	   
	   }
	    
	   //this.videoDao.updateSameCamera(list);
		return new BaseResultOK();
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResultFailed();
		}
	}
}
