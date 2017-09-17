package com.highwaycenter.video.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.common.CoordinateUtil;
import com.highwaycenter.video.dao.CameraDtoDao;
import com.highwaycenter.video.dao.ControlUnitDtoDao;
import com.highwaycenter.video.dao.DeviceDtoDao;
import com.highwaycenter.video.dao.RegionInfoDtoDao;
import com.highwaycenter.video.dao.SpSxxDao;
import com.highwaycenter.video.model.HSpCameraDTO;
import com.highwaycenter.video.model.HSpRegionInfoDTO;
import com.highwaycenter.video.model.HSpSxx;
import com.highwaycenter.video.service.videoListService;
@Service("videolistservice")
public class videoListServiceImpl extends BaseService implements videoListService {

	@Resource(name="cameradtodao")
	private CameraDtoDao cameraDtoDao;
	@Resource(name="devicedtodao")
	private DeviceDtoDao deviceDtoDao;
	@Resource(name="controlunitdtodao")
	private ControlUnitDtoDao controlunitDtoDao;
	@Resource(name="regioninfodtodao")
	private RegionInfoDtoDao regioninfoDtoDao;
	@Resource(name="spsxxdao")
	private SpSxxDao spsxxDao;
	@Override
	public BaseResult selectAllDevice() {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List list = this.deviceDtoDao.selectDeviceList();
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectAllCamera(Integer deviceId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List list = this.cameraDtoDao.selectCameraList(deviceId);
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectCameraDto(Integer cameraId) {
		HSpCameraDTO camera = this.cameraDtoDao.selectCamera(cameraId);
		String net = this.deviceDtoDao.selectNetWorkAddr(camera.getDeviceId());
		camera.setNetworkAddr(net);
		HSpCameraDTO jwdtemp = this.selectCameraJwd(camera.getName());
		if(jwdtemp!=null){
			camera.setLatitude(jwdtemp.getLatitude());
			camera.setLongitude(jwdtemp.getLongitude());
		}
		
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(camera);
		//result.setObjs(new String[]{net});
		return result;
	}

	@Override
	public BaseResult selectControlCenter() {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List list = this.controlunitDtoDao.selectAllControlUnit();		
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectRegion(Integer controlUnitId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List list = this.regioninfoDtoDao.selectAllRegion(controlUnitId);
		List<Integer> noexitlist = this.cameraDtoDao.selectNoCameraByRegion();
		if(list!=null&&list.size()>0&&noexitlist!=null&&noexitlist.size()>0){
			for(int i=0;i<list.size();i++){
				HSpRegionInfoDTO region = (HSpRegionInfoDTO) list.get(i);
				if(noexitlist.contains(region.getRegionId())){
					list.remove(i);
				}
			}
		}

		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectCameraByRegion(Integer regionId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List list = this.cameraDtoDao.selectCameralistByRegion(regionId);
		result.setList(list);
		return result;
	}

	@Override
	public HSpCameraDTO selectCameraJwd(String cameraname)  {
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
			Integer dir = this.cameraDtoDao.selectDir(lxdm, fxm);
			String dirstr = null;
			if(dir!=null){
				dirstr = dir.toString();
			}
            String json = CoordinateUtil.transferKzh(stakenames, null, dirstr);
            if(json==null||json.equals("")){
            	return null;
            }
            
            JSONObject  jsonObj = new JSONObject(json);
            String statusCode = jsonObj.getString("statuscode");
            if(statusCode.equals("100000")){
            	JSONArray jsonarray = jsonObj.getJSONArray("stakelist");
            	JSONObject stakeobj = jsonarray.getJSONObject(0);
            	Double jw = stakeobj.getDouble("offsetlongitude");
            	Double wd = stakeobj.getDouble("offsetlatitude");
            	HSpCameraDTO jwcamera = new HSpCameraDTO();
            	jwcamera.setLongitude(jw);
            	jwcamera.setLatitude(wd);
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
	public BaseResult selectSpssxList(int page, int rows) {
	   BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	   BaseQueryRecords records = this.spsxxDao.selectSxxList(page, rows);
	   List<HSpSxx> sxxlist = (List<HSpSxx>) records.getData();
	   if(sxxlist!=null&&sxxlist.size()>0){
		   for(int i=0;i<sxxlist.size();i++){
			   HSpSxx sxxtemp = sxxlist.get(i);
			   if(sxxtemp.getSxx()!=null){
				   if(sxxtemp.getSxx()==0)
				      sxxtemp.setSxxname("上行");
				   if(sxxtemp.getSxx()==1)
					   sxxtemp.setSxxname("下行");
				   sxxlist.set(i, sxxtemp);
				   
			   }   
		   }
		   records.setData(sxxlist);
	   }
	
	   result.setObj(records);
	   return result;
	}

	@Override
	public BaseResult selectCameraName(int type, String selectvalue) {
	   BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	   List<String> namelist = new ArrayList<String>();
	   Set<String> set = new HashSet<String>();
	   List selectList = new ArrayList();
		if(type==1){//路线代码
		 namelist = this.spsxxDao.selectCameraName(null);
		}
		if(type==4){//方向名 是联动的
			if(selectvalue==null||selectvalue.equals("")){
				result.setList(new ArrayList());
				return result;
			}
			 namelist = this.spsxxDao.selectCameraName(selectvalue);
			
		}
		if(namelist!=null&&namelist.size()>0){
		   for(String cameraname:namelist){
		     String[] knames = cameraname.split(" ");
		      if(knames.length>4){
			    String select =knames[type];
			    set.add(select);
		    }
		   }
		}
		selectList.addAll(set);
		result.setList(selectList);
		return result;
	}

	@Override
	public BaseResult saveSpssx(HSpSxx sxx) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		int count = this.spsxxDao.countSameSpsxx(sxx);
		if(count>0){
			return new BaseResult(1003,"相同的数据已存在，数据输入错误！");
		}
		this.spsxxDao.saveSpSxx(sxx);
		return result;
	}

	@Override
	public BaseResult deleteSpssx(int sxxId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		this.spsxxDao.deleteSpSxx(sxxId);
		return result;
	}

	@Override
	public BaseResult updateSpssx(HSpSxx sxx) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		int count = this.spsxxDao.countSameSpsxx(sxx);
		if(count>0){
			return new BaseResult(1003,"相同的数据已存在，数据输入错误！");
		}
		this.spsxxDao.updateSpSxx(sxx);
		return result;
	}

	@Override
	public BaseResult selectUnique(int sxxId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HSpSxx sxxtemp = this.spsxxDao.selectUnique(sxxId);
		if(sxxtemp.getSxx()!=null){
		   if(sxxtemp.getSxx()==0)
			      sxxtemp.setSxxname("上行");
			   if(sxxtemp.getSxx()==1)
				   sxxtemp.setSxxname("下行");
		}
		result.setObj(sxxtemp);
		return result;
	}

}
