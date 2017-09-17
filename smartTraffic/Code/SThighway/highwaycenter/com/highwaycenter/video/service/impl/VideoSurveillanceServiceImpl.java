package com.highwaycenter.video.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.ksoap2.serialization.SoapObject;
import org.springframework.stereotype.Service;

import com.common.action.Constants;
import com.common.service.BaseService;
import com.common.utils.Md5Secure;
import com.common.utils.SoapUtil;
import com.highwaycenter.bean.CommonPageReq;
import com.highwaycenter.video.dao.CameraDtoDao;
import com.highwaycenter.video.dao.ControlUnitDtoDao;
import com.highwaycenter.video.dao.DeviceDtoDao;
import com.highwaycenter.video.dao.RegionInfoDtoDao;
import com.highwaycenter.video.model.HSpCameraDTO;
import com.highwaycenter.video.model.HSpCameraInfoDTO;
import com.highwaycenter.video.model.HSpControlUnitDTO;
import com.highwaycenter.video.model.HSpDeviceDTO;
import com.highwaycenter.video.model.HSpRegionInfoDTO;
import com.highwaycenter.video.service.VideoSurveillanceService;

@Service("videoservice")
public class VideoSurveillanceServiceImpl extends BaseService implements VideoSurveillanceService{
	static Logger log = Logger.getLogger(VideoSurveillanceServiceImpl.class);
	 
	@Resource(name="devicedtodao")
	private DeviceDtoDao deviceDtoDao;
	@Resource(name="cameradtodao")
	private CameraDtoDao cameraDtoDao;
	@Resource(name="controlunitdtodao")
	private ControlUnitDtoDao controlunitDao;
	@Resource(name="regioninfodtodao")
	private RegionInfoDtoDao regioninfoDao;
	private static SoapUtil soaputil = new SoapUtil();

	//用户登录方法名+参数
	private static String username=Constants.VIDEO_WS_USERNAME;       //登陆用户名
	private static String password;      //登陆密码
	private static String videoWsurl=Constants.VIDEO_WEBSERVICE_URL;  //webservice接口url
	private static String videoflaturl;     //平台url（登陆请求参数）
	private  String localIp;    //本机ip
	private  int localPort;   //本机端口
	private static String targetName = Constants.VIDEO_WS_TARGETNAME;//命名空间
	private static String methodLoginName="userLogin";
	private static int rows =100;   //默认取100行
	private int totalsize; // 总共的数目
	private int totalpage; // 页数
	//用户登录返回结果集
	private static String sessionId ;
	private String errorCode;
	private String result;
	private CommonPageReq pagereq;
	//分页获取设备的参数
	private String methodDevcieList = "getAllDevice ";
	//分页获取所有监控点参数
	private String methodCameraList="getAllCamera";
	//获取所有控制中心列表
	private String methodAllControlCenterForList="getAllControlCenterForList";
	//获取所有区域列表
	private String methodAllRegionInfoForList="getAllRegionInfoForList";
	
	//根据区域id获取摄像点
	private String methodCameraInfoListByRegionId ="getCameraInfoListByRegionId" ;
	//用户登出
	private String methodLogoutName = "userLogout";
	
    //分页获取

	@Override
	public void selectAllDC() throws Exception{
	try{
		System.out.println("视频ws触发时间"+new Date());
		//登陆
		 this.loginResult();
		//获取设备信息并保存
		List<HSpDeviceDTO> devicelist = seletcAllDevice();
			//获取监控点信息
		List<HSpCameraDTO> camerelist =   selectAllCamera();	
		selectAllControlCenterForList();
		selectAllRegionInfoForList();
		this.logoutResult();
		System.out.println("区域下的设备开始");
	  }catch(Exception e){
		  e.printStackTrace();
		  throw(e);
	  }
	}

	
/**
 * 根据区域获取不同区域下的摄像机关系
 */
	@Override
	public void selectRegionCamera() {
		try{
			System.out.println("区域摄像机关系"+new Date());
			//登陆
			 this.loginResult();
			 selectCameraInfoListByRegionId();
			 this.logoutResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void selectAllControlCenterForList() throws Exception{
		System.out.println("以下开始调试控制中心列表");
		List<HSpControlUnitDTO> dtolist = new ArrayList<HSpControlUnitDTO>();
		try{
	
		List<Map<String, Object>>  centerMap  = new  ArrayList<Map<String, Object>>();//初始方法与设备相同
		centerMap = this. addParamToList("sessionId", sessionId,centerMap);  
		//调webservice的method
		SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,methodAllControlCenterForList,centerMap);
		if(soap!=null){
		
		SoapObject centersoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
  
		 result =  this.parseSoapObjectToObj(centersoap, "result","String").toString();
		 errorCode =  (this.parseSoapObjectToObj(centersoap, "errorCode","String").toString());
		 if(result.equals("true")){
			 dtolist = soaputil.parseSoapVector(HSpControlUnitDTO.class,"ControlUnitDTO",centersoap);
					  System.out.println("center大小"+dtolist.size());
					  //保存监控点
			          if(dtolist!=null&&dtolist.size()>0){
			        	  this.controlunitDao.deleteall();
			        	  Iterator<HSpControlUnitDTO> it  = dtolist.iterator();
			        	  while(it.hasNext()){
			        		  HSpControlUnitDTO center  = it.next();
			        		  this.controlunitDao.save(center);
			        	  }
			        	  
			          }
					 
				}else if(errorCode.equals("10010")){//sessionId过期
					//重新登陆
					this.loginResult();
					//重新在跑一次这个方法
					selectAllControlCenterForList();
					
				}else{
					log.error(errorCode);
					throw(new Exception());
				}
		}	
				}catch(Exception e){
					e.printStackTrace();
					log.error(e);
					throw(e);
				}			

		
	}


	@Override
	public void selectAllRegionInfoForList() throws Exception{
		System.out.println("以下开始调试控制中心列表");
		List<HSpRegionInfoDTO> dtolist = new ArrayList<HSpRegionInfoDTO>();
		try{
	
		List<Map<String, Object>>  regionMap  = new  ArrayList<Map<String, Object>>();//初始方法与设备相同
		regionMap = this. addParamToList("sessionId", sessionId,regionMap);  
		//调webservice的method
		SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,this.methodAllRegionInfoForList,regionMap);
		if(soap!=null){
		SoapObject centersoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
  
		 result =  this.parseSoapObjectToObj(centersoap, "result","String").toString();
		 errorCode =  (this.parseSoapObjectToObj(centersoap, "errorCode","String").toString());
		 if(result.equals("true")){
					 dtolist = soaputil.parseSoapVector(HSpRegionInfoDTO.class,"RegionInfoDTO",centersoap);
					  System.out.println("center大小"+dtolist.size());
					  //保存监控点
			          if(dtolist!=null&&dtolist.size()>0){
			        	  this.regioninfoDao.deleteall();
			        	  Iterator<HSpRegionInfoDTO> it  = dtolist.iterator();
			        	  while(it.hasNext()){
			        		  HSpRegionInfoDTO region  = it.next();
			        		  this.regioninfoDao.save(region);
			        	  }
			        	  
			          }
					 
				}else if(errorCode.equals("10010")){//sessionId过期
					//重新登陆
					this.loginResult();
					//重新在跑一次这个方法
					selectAllRegionInfoForList();
					
				}else{
					log.error(errorCode);
					throw(new Exception());
				}
		}
				}catch(Exception e){
					e.printStackTrace();
					log.error(e);
					throw(e);
				}			

		
		
	}


	@Override
	public void selectCameraInfoListByRegionId() throws Exception{
		System.out.println("以下开始调试根据区域获取摄像点");
		try{
          //遍历region
			List<Integer> regionIdlist = this.regioninfoDao.selectAllRegionId();
			System.out.println("regionIdlist.size()......"+regionIdlist.size());
			if(regionIdlist!=null&&regionIdlist.size()>0){
				this.cameraDtoDao.deleteallCamerainfo();
				for(int i:regionIdlist){
					this.saveCameraInfo(i);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw(e);
		}
		
	}
		public void saveCameraInfo(Integer regionId) throws Exception{
			List<HSpCameraInfoDTO> dtolist = new ArrayList<HSpCameraInfoDTO>();
			try{
				List<Map<String, Object>>  regionCameraMap  = new  ArrayList<Map<String, Object>>();//初始方法与设备相同
				regionCameraMap = this. addParamToList("sessionId", sessionId,regionCameraMap);  
				regionCameraMap = this. addParamToList("regionId", regionId,regionCameraMap);  
				
				
				SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,this.methodCameraInfoListByRegionId,regionCameraMap);
				if(soap!=null){
				SoapObject centersoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
		  
				 result =  this.parseSoapObjectToObj(centersoap, "result","String").toString();
				 errorCode =  (this.parseSoapObjectToObj(centersoap, "errorCode","String").toString());
				 if(result.equals("true")){
				       //该区域下有camera
					 dtolist = soaputil.parseSoapVector(HSpCameraInfoDTO.class,"CameraInfoDTO",centersoap);
					  System.out.println("center大小"+dtolist.size());
					  //保存监控点
			          if(dtolist!=null&&dtolist.size()>0){
			        	
			        	  Iterator<HSpCameraInfoDTO> it  = dtolist.iterator();
			        	  while(it.hasNext()){
			        		  HSpCameraInfoDTO camerainfo  = it.next();
			        		  this.cameraDtoDao.saveCameraInfo(camerainfo.getCameraId(), camerainfo.getRegionId());
			        	  }
			        	  
			          }					 
				}else if(errorCode.equals("10010")){//sessionId过期
					//重新登陆
					this.loginResult();
					//重新在跑一次这个方法
					saveCameraInfo(regionId);
				 }else if(errorCode.equals("30001")){
					 log.debug("该区域没有下级camera");
				 }else{
					 log.error(errorCode);
					 throw(new Exception());
				 }
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
			
			
			
		}
		
		
		
	@Override
	public List selectAllCamera() throws Exception{
		System.out.println("以下开始调试监控点信息");
		List<HSpCameraDTO> dtolist = new ArrayList<HSpCameraDTO>();
		try{
		//初始化参数
		pagereq = new CommonPageReq(1,rows);
		List<Map<String, Object>>  cameraMap  = this.initDeviceListParam(pagereq); //初始方法与设备相同
		
		//调webservice的method
		SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,methodCameraList,cameraMap);
		if(soap!=null){
		SoapObject camerasoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
  
		 result =  this.parseSoapObjectToObj(camerasoap, "result","String").toString();
		 errorCode =  (this.parseSoapObjectToObj(camerasoap, "errorCode","String").toString());
		 if(result.equals("true")){
					 dtolist = soaputil.parseSoapVector(HSpCameraDTO.class,"CameraDTO",camerasoap);
					  totalpage = (int) this.parseSoapObjectToObj(camerasoap, "totalPage","Integer");
					  for(int i=2;i<=totalpage;i++){
						  CommonPageReq pagereq = new CommonPageReq(i,rows);
						  List<HSpCameraDTO> newpageList =  seletcCameraByPage(pagereq);
						  if(newpageList==null){
							  dtolist=null;
							  break;
						  }else{
							  dtolist.addAll(newpageList);
						  }
						  
					  }
					  System.out.println("camera大小"+dtolist.size());
					  //保存监控点
			          if(dtolist!=null&&dtolist.size()>0){
			        	  this.cameraDtoDao.deleteAll();
			        	  Iterator<HSpCameraDTO> it  = dtolist.iterator();
			        	  while(it.hasNext()){
			        		  HSpCameraDTO camera  = it.next();
			        		  this.cameraDtoDao.saveCameraDto(camera);
			        	  }
			        	  
			          }
					  
		  
					  return null;
				}else if(errorCode.equals("10010")){//sessionId过期
					//重新登陆
					this.loginResult();
					//重新在跑一次这个方法
					selectAllCamera();
					
				}else{
					log.error(errorCode);	
					return null;
					
					
				}
		    }
				}catch(Exception e){
					e.printStackTrace();
					log.error(e);
					throw(e);
				}			
				
		return null;
			
	}
	
	/**
	 * 获取所有设备保存到数据库
	 * @throws Exception 
	 */
	@Override
	public List<HSpDeviceDTO> seletcAllDevice() throws Exception {
		List<HSpDeviceDTO> dtolist = new ArrayList<HSpDeviceDTO>();
		try {
		//初始化参数
		pagereq = new CommonPageReq(1,rows);
		List<Map<String, Object>>  deviceMap  = this.initDeviceListParam(pagereq);
	
		//调webservice的method
		SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,methodDevcieList,deviceMap);
		if(soap!=null){
		SoapObject devicesoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
		
		result =  this.parseSoapObjectToObj(devicesoap, "result","String").toString();
		errorCode =  (this.parseSoapObjectToObj(devicesoap, "errorCode","String").toString());
		if(result.equals("true")){
			  dtolist = soaputil.parseSoapVector(HSpDeviceDTO.class,"DeviceDTO",devicesoap);
			  totalpage = (int) this.parseSoapObjectToObj(devicesoap, "totalPage","Integer");
			  for(int i=2;i<=totalpage;i++){
				  CommonPageReq pagereq = new CommonPageReq(i,rows);
				  List newpageList =  seletcDeviceByPage(pagereq);
				  if(newpageList==null){
					  dtolist=null;
					  break;
				  }else{
					  dtolist.addAll(newpageList);
				  }
			  }
			  //先删除设备然后保存设备
			  
	          if(dtolist!=null&&dtolist.size()>0){
	            this.deviceDtoDao.deleteAll();
	   
	        	  Iterator<HSpDeviceDTO> it  = dtolist.iterator();
	        	  while(it.hasNext()){
	        		  HSpDeviceDTO device  = it.next();
	        		  this.deviceDtoDao.saveDeviceDto(device);
	        	  }
	        	  
	          }
			  
  
			  return dtolist;
		}else if(errorCode.equals("10010")){//sessionId过期
			//重新登陆
			this.loginResult();
			//重新在跑一次这个方法
			seletcAllDevice();
			
		}else{
			log.error(errorCode);
			return null;
		}
		}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw (e);
		}
		return null;
		
	}
	
	public List<HSpDeviceDTO> seletcDeviceByPage(CommonPageReq pagereq) throws Exception{
	List<HSpDeviceDTO> pagelist = new ArrayList<HSpDeviceDTO>();
	try{
		//初始化参数
		List<Map<String, Object>>  deviceMap  = this.initDeviceListParam(pagereq);
		SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,methodDevcieList,deviceMap);
		if(soap!=null){
		SoapObject devicesoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
		result =  this.parseSoapObjectToObj(devicesoap, "result","String").toString();
		errorCode =  (this.parseSoapObjectToObj(devicesoap, "errorCode","String").toString());
		if(result.equals("true")){
			pagelist = soaputil.parseSoapVector(HSpDeviceDTO.class,"DeviceDTO",devicesoap);
			//dtolist.addAll(pagelist);
		}else if(errorCode.equals("10010")){//sessionId过期
			//重新登陆
			this.loginResult();
			//重新在跑一次这个方法
			seletcDeviceByPage(pagereq);
			
		}else{
			log.error(errorCode);
			return null;
		}
	 }
	}catch(Exception e){
		e.printStackTrace();
		log.error(e);
		throw(e);
		
	}
	return pagelist;
	
}
	
	
	public List<HSpCameraDTO> seletcCameraByPage(CommonPageReq pagereq) throws Exception{
		List<HSpCameraDTO> pagelist = new ArrayList<HSpCameraDTO>();
		try{
			//初始化参数
			List<Map<String, Object>>  deviceMap  = this.initDeviceListParam(pagereq);
			SoapObject soap =  soaputil.CallWebServiceSoap(videoWsurl,targetName,methodCameraList,deviceMap);
			if(soap!=null){
			SoapObject camerasoap = (SoapObject)this.parseSoapObjectToObj(soap,"return","SoapObject");
			result =  this.parseSoapObjectToObj(camerasoap, "result","String").toString();
			errorCode =  (this.parseSoapObjectToObj(camerasoap, "errorCode","String").toString());
			if(result.equals("true")){
				pagelist = soaputil.parseSoapVector(HSpCameraDTO.class,"CameraDTO",camerasoap);
				//dtolist.addAll(pagelist);
			}else if(errorCode.equals("10010")){//sessionId过期
				//重新登陆
				this.loginResult();
				//重新在跑一次这个方法
				seletcCameraByPage(pagereq);
				
			}else{
				log.error(errorCode);
				return null;
			}
		 }
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw(e);
		}
		return pagelist;
		
	}
	
	/**
	 * 用户登陆
	 * @return  sessionId
	 */
	public String loginResult(){
		
		  try {
			  List<Map<String, Object>>  loginMap  = initLoginParam();//初始化参数
			  SoapObject soapobj = soaputil.CallWebServiceSoap(videoWsurl,targetName,methodLoginName,loginMap);
			  SoapObject resultobj = (SoapObject) this.parseSoapObjectToObj(soapobj, "return","SoapObject");
			   result =  this.parseSoapObjectToObj(resultobj, "result","String").toString();
			   errorCode =  (this.parseSoapObjectToObj(resultobj, "errorCode","String").toString());
			   System.out.println("登录结果"+result+errorCode);
			   if(result.equals("true")&&errorCode.equals("0")){
				   sessionId = (String) this.parseSoapObjectToObj(resultobj, "sessionId","String");
				   System.out.println("sessionId"+sessionId); 
				   return sessionId;
	   
			   }else{
				   
				   log.error("CMS接口返回错误代码"+errorCode);
				   return null;
				  
			   }	   
			} catch (Exception e) {
				e.printStackTrace();
			  log.error("CMS接口返回错误代码"+e);
				return null;
			}
	}
	/**
	 * 用户登出
	 * @return  登出结果
	 */
	public boolean logoutResult() {
		try{
		
		System.out.println("Logout 的  sessionId====="+sessionId);
		 List<Map<String, Object>>  logoutMap  = new ArrayList<Map<String, Object>>();//初始化参数
		 Map<String, Object> sessionMap = new HashMap<String, Object>();
		 sessionMap.put("sessionId",  sessionId);
		 logoutMap.add(sessionMap);
		 SoapObject soapobj = soaputil.CallWebServiceSoap(videoWsurl,targetName,methodLogoutName,logoutMap);
		 SoapObject resultobj = (SoapObject) this.parseSoapObjectToObj(soapobj, "return","SoapObject");
		   result =  this.parseSoapObjectToObj(resultobj, "result","String").toString();
		   errorCode =  (this.parseSoapObjectToObj(resultobj, "errorCode","String").toString());
		   System.out.println("登出结果"+result+errorCode);
		 
		 if(result.equals("true")&&errorCode.equals("0")){
               sessionId = null;
			  return true;
	
		   }else{
	 
			   log.error("CMS接口返回错误代码"+errorCode);
			   return false;
		   }
		}catch(java.lang.Exception e){
			e.printStackTrace();
			log.error(e);
			return false;
		}
		
	}
	
	
	/**初始化用户登陆参数
	 * 
	 * @return  list
	 */
	private List<Map<String, Object>> initLoginParam(){
		List<Map<String, Object>>  loginMap = new ArrayList<Map<String, Object>>();
		//密码加密
		password = Md5Secure.encode(Constants.VIDEO_WS_PASSWORD);
		log.debug("md5加密密码"+password);
		//本机ip,port
		InetAddress addr;
		try {
			//HttpServletRequest request = ServletActionContext.getRequest();
			addr = InetAddress.getLocalHost();
		    String myip=addr.getHostAddress();//获得本机IP
		    
		    localIp = myip.replace(".", "\u002e");//将ip 特殊符号进行unicode编码
		    videoflaturl = Constants.VIDEO_FLAT_URL.replace(".", "\u002e");//将ip 特殊符号进行unicode编码
		    localPort = 80;  
		    addParamToList("userName",username,loginMap);  //参数添加
		    addParamToList("pwd", password,loginMap);
		    addParamToList("clientIp", localIp,loginMap);
		    addParamToList("clientPort", localPort,loginMap);
		    addParamToList("cmsUrl",videoflaturl,loginMap);
		   
		   
		} catch (UnknownHostException e) {
			log.error(e);
			e.printStackTrace();
		}
		return loginMap;
	}

	/**
	 * 初始化获取所有设备的参数
	 * @param pagereq
	 * @return
	 */
	private List<Map<String, Object>> initDeviceListParam(CommonPageReq pagereq){
		List<Map<String, Object>>  deviceMap = new ArrayList<Map<String, Object>>();
		
		    addParamToList("sessionId", sessionId,deviceMap);  //参数添加
		    addParamToList("req", pagereq,deviceMap);

		return deviceMap;
	}
	
	
	
	private List<Map<String, Object>> addParamToList(String name,Object key,List<Map<String, Object>>  maplist){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, key);
		maplist.add(map);
		return maplist;
		
	}
	
	
	/**
	 * 解析soapobject对象
	 */
	public  Object parseSoapObjectToObj(
			SoapObject sobj,String propertyName,String type) {
		Object obj = null;
        if (sobj != null) {
            int size = sobj.getPropertyCount();
            if (size > 0) {
            	if(type.equals("String")){
            		if(sobj.getProperty(propertyName)==null){
            			return null;
            		}else{
            	    return sobj.getPropertyAsString(propertyName);  }
            	}else if(type.equals("SoapObject")){
            	    return (SoapObject)sobj.getProperty(propertyName);  
            	}else if(type.equals("Integer")){
            		if(sobj.getProperty(propertyName)!=null&&!sobj.getPropertyAsString(propertyName).equals("")){
            		return Integer.valueOf(sobj.getPropertyAsString(propertyName));
            		}else{
            			return 0;
            		}
            	}
            }
 
     }
        return null;
	}






	

	
	
}
