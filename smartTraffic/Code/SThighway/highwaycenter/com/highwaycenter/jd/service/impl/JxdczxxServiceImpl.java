package com.highwaycenter.jd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.common.CoordinateUtil;
import com.highwaycenter.common.HttpUtil;
import com.highwaycenter.data.dao.SnopeLogDao;
import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.jd.dao.JxdczxxDao;
import com.highwaycenter.jd.dao.TransportDataDao;
import com.highwaycenter.jd.model.HJdJxdczxx;
import com.highwaycenter.jd.model.HJdJxzdjwd;
import com.highwaycenter.jd.model.HJdTransportData;
import com.highwaycenter.jd.service.JxdczxxService;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;
import com.highwaycenter.video.model.HSpCameraDTO;


@Component
@Service("jxdczxxservice")
public class JxdczxxServiceImpl extends BaseService implements JxdczxxService{
	static Logger log = Logger.getLogger(JxdczxxServiceImpl.class);
	@Resource(name="jxdczxxdao")
	private JxdczxxDao jxdczxxDao;
	@Resource(name="lxjbxxdao")
	private LxjbxxDao lxjbxxDao;
	@Resource(name="transdatadao")
	private TransportDataDao transdataDao;
	@Resource(name="snopelogdao")
	private SnopeLogDao snopelogDao;

	private static String newpeportActionName=Constants.TRANSPORTDATA_INNER_URL+Constants.TRANSPORTDATA_ACTION_NEWREPORT;
	

	
	@Override
	public BaseResult selectJxdczxx(String dczbh) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult selectOptionList(String selectOption) {
		return null;
	}

	@Override
	public BaseResult selectTransportDataList(int page, int rows,
			String sjy,String sjm,String sjd,String sjhy,String sjseason) {
		try{
		String sj = sjy;
		HJdTransportData totalData = new HJdTransportData();
		if(sjhy!=null&&!sjhy.equals("")){
			sj+=sjhy+"H";
		}else if(sjseason!=null&&!sjseason.equals("")){
			sj+=sjseason;
		}else{
			if(sjm!=null&&!sjm.equals("")&&!sjm.equals("00")){
			sj+=sjm;
		}
		if(sjd!=null&&!sjd.equals("")&&!sjd.equals("00")){
			sj+=sjd;
		}
		}
		System.out.println("sj"+sj);
		String urlaction = newpeportActionName+"?sj="+sj+"&xzqbs="+Constants.TRANSPORTDATA_ACTION_NEWREPORT_PARAM2;
		System.out.println("urlaction"+urlaction);
		String resultlist = HttpUtil.sendPost(urlaction,null);
		List<HJdTransportData> list = new ArrayList<HJdTransportData>();
		if(resultlist!=null&&!resultlist.equals("")){
		   list = this.jsonToBean(resultlist);
		   if(list.size()>0){
			 totalData =list.get(list.size()-1);
		}
		list.remove(list.size()-1);
		}
		BaseQueryRecords records = this.ListInsertPage(page, rows, list);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(records);
		result.setObjs(new HJdTransportData[]{totalData});
		return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			return new BaseResult(90,"网络连接错误,数据请求失败");
		}
		
	}
	
	
	private BaseQueryRecords ListInsertPage(int page,int rows,List<HJdTransportData> datalist){
		BaseQueryRecords records;
		int total = datalist.size();
		if(page<1||rows<1){
			records = new BaseQueryRecords(datalist);
			return records;
		}else{
			List pageDate = new ArrayList();
			int startIndex = (page-1)*rows;
			int endIndex = page*rows-1;
			endIndex = (endIndex>(total-1))?(total-1):endIndex;
			for(int i=startIndex;i<=endIndex;i++){
				pageDate.add(datalist.get(i));
			}
		records= new BaseQueryRecords(pageDate,total,page,rows);
		return records;
		}
	}
	
	private List<HJdTransportData> jsonToBean(String jsonString){
		List<HJdTransportData> list = new ArrayList<HJdTransportData>();
		try {
			JSONArray jsonarray = new JSONArray(jsonString);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonObj = jsonarray.getJSONObject(i);
				HJdTransportData tdData = this.jsonToObject(jsonObj);
				list.add(tdData);
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		}

		return list;
	}
	
	@Override
	public void saveTransportData() {
	
		
	}
	
	
	 
	 
	 private HJdTransportData jsonToObject(JSONObject  jsonObj){
		 HJdTransportData tdData = new HJdTransportData();
         try{
        	//System.out.println("畜力车"+(jsonObj.get("畜力车")));
		    tdData.setClc((jsonObj.get("畜力车").equals(null))?0:(Integer)jsonObj.get("畜力车"));
			tdData.setDkc((jsonObj.get("大客车").equals(null))?0:(Integer)jsonObj.get("大客车"));
			tdData.setDxhc((jsonObj.get("大型货车").equals(null))?0:(Integer)jsonObj.get("大型货车"));
			if(jsonObj.get("观测里程（公里）").equals(null)||jsonObj.get("观测里程（公里）").equals("")){
				tdData.setGclc(new Double(0));
			}else{
			tdData.setGclc(jsonObj.getDouble("观测里程（公里）"));
			}
			tdData.setJdcdlshj((jsonObj.get("机动车当量数合计").equals(null))?0:(Integer)jsonObj.get("机动车当量数合计"));
			tdData.setJdczrshj((jsonObj.get("机动车自然数合计").equals(null))?0:(Integer)jsonObj.get("机动车自然数合计"));
			if(jsonObj.get("交通拥挤度").equals(null)||jsonObj.get("交通拥挤度").equals("")){
				tdData.setJtyjd(new Double(0));
			}else{
			  tdData.setJtyjd(jsonObj.getDouble("交通拥挤度"));
			}
			//tdData.setJtyjd((Double)jsonObj.get("交通拥挤度"));
			tdData.setJzxc((jsonObj.get("集装箱车").equals(null))?0:(Integer)jsonObj.get("集装箱车"));
			//tdData.setLxdm(jsonObj.getString(null));
			tdData.setLxmc((String)jsonObj.get("路线名称"));
			tdData.setMtc((jsonObj.get("摩托车").equals(null))?0:(Integer)jsonObj.get("摩托车"));
			tdData.setQcdlshj((jsonObj.get("汽车当量数合计").equals(null))?0:(Integer)jsonObj.get("汽车当量数合计"));
			tdData.setQczrshj((jsonObj.get("汽车自然数合计").equals(null))?0:(Integer)jsonObj.get("汽车自然数合计"));
			tdData.setRlc((jsonObj.get("人力车").equals(null))?0:(Integer)jsonObj.get("人力车"));
			tdData.setSyjtl((jsonObj.get("适应交通量（辆/日）").equals(null))?0:(Integer)jsonObj.get("适应交通量（辆/日）"));
		//	tdData.setTddm(jsonObj.get(null));
			tdData.setTdxhc((jsonObj.get("特大型货车").equals(null))?0:(Integer)jsonObj.get("特大型货车"));
			//tdData.setTjsj(jsonObj.get(key));
			tdData.setTlj((jsonObj.get("拖拉机").equals(null))?0:(Integer)jsonObj.get("拖拉机"));
			tdData.setXsl((jsonObj.get("行驶量（万车公里/日）").equals(null))?0:(Integer)jsonObj.get("行驶量（万车公里/日）"));
			tdData.setXxhc((jsonObj.get("小型货车").equals(null))?0:(Integer)jsonObj.get("小型货车"));
			tdData.setZxc((jsonObj.get("中型货车").equals(null))?0:(Integer)jsonObj.get("中型货车"));
			tdData.setZxhc((jsonObj.get("自行车").equals(null))?0:(Integer)jsonObj.get("自行车"));
			tdData.setZxkc((jsonObj.get("中小客车").equals(null))?0:(Integer)jsonObj.get("中小客车"));
         } catch (JSONException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		 return tdData;
	 }

	@Override
	public BaseResult selectTransDataTimeCS() {
	return null;
	}

	@Override
	public BaseResult selectJxdczxxSimple(String dczbh) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult selectJxdczxxList(String condition, Integer page,
			Integer rows, String selectId) {
		// TODO Auto-generated method stub
		return null;
	}
}
