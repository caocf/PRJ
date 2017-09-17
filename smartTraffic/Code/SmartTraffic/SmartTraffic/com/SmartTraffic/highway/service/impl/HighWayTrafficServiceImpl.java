package com.SmartTraffic.highway.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.SmartTraffic.common.Constants;
import com.SmartTraffic.common.CoordinateUtil;
import com.SmartTraffic.common.HttpUtil;
import com.SmartTraffic.highway.dao.HighwayBaseDao;
import com.SmartTraffic.highway.model.GczModel;
import com.SmartTraffic.highway.model.HJdTransportData;
import com.SmartTraffic.highway.model.TrafficModel;
import com.SmartTraffic.highway.service.HighWayTrafficService;
import com.SmartTraffic.multiple.service.impl.MultipleServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.PropertyLoader;

@Service("hwtrafficService")
public class HighWayTrafficServiceImpl extends BaseService implements HighWayTrafficService{

	public BaseResult result = new BaseResult();
	private static String newpeportActionName=Constants.TRANSPORTDATA_INNER_URL+Constants.TRANSPORTDATA_ACTION_NEWREPORT;
	
	@Resource(name="highwayBaseDao")
	private HighwayBaseDao highwayBaseDao;
	//交调--实时拥挤度查询
		private static final String timecsActionUrl ;
		static{
			 Properties pro = PropertyLoader.getPropertiesFromClassPath("infourl.properties",null);    	 
			timecsActionUrl= pro.getProperty("jdurl.timecs")+Constants.TRANSPORTDATA_ACTION_TIMECS+"?"+   
		    Constants.TRANSPORTDATA_ACTION_TIMECS_PARAM1_NAME+"="+Constants.TRANSPORTDATA_ACTION_TIMECS_PARAM1_VALUE;
		}
		  
		
	@Override
	public BaseResult selectTransDataTimeCS() {	
		try{	
			List<TrafficModel> modellist = new ArrayList<TrafficModel>(); 
			System.out.println("urlaction"+timecsActionUrl);
			String resultlist = HttpUtil.sendGet(timecsActionUrl);
			System.out.println(resultlist);
			if(resultlist!=null&&!resultlist.equals("")){
				JSONObject jsonobj = JSONObject.parseObject(resultlist);
				JSONArray jsonarray = JSONArray.parseArray(jsonobj.getString("data"));
				System.out.println("jsonarray size"+jsonarray.size());
				if (jsonarray!=null&&jsonarray.size()>0){
					for(int i=0;i<jsonarray.size();i++){
						JSONObject obj = jsonarray.getJSONObject(i);
						TrafficModel model = changeJsonToObj(obj);
						//请求该路线的标记点集合
						model = selectLinePoint(model);
						modellist.add(model);
					}
				}
			}
			
			/*//test
			TrafficModel modeltest = new TrafficModel();
			modeltest.setLXBH("G60");
			modeltest.setQDZH("105");
			modeltest.setZDZH("125");
			modeltest.setYJD_S("5");
			modeltest.setYJD_X("1");
			modeltest = selectLinePoint(modeltest);
			modellist.add(modeltest);
			//TEST
*/			
			result.setResultcode(BaseResult.RESULT_OK);
			result.setObj(modellist);
			return result;
		 }catch(IOException e){
			 MultipleServiceImpl.writeFile(timecsActionUrl, "交调请求错误："+e.getMessage());
			 System.out.println("网络连接错误");
			  e.getMessage();
			  writeFile(timecsActionUrl,"访问实时拥挤度网络连接错误："+e.getMessage());
			return new BaseResult(90,"网络连接错误,数据请求失败");
		   }
	}

	
	private TrafficModel changeJsonToObj(JSONObject obj){
		TrafficModel model = new TrafficModel();
		String lxbhlold = obj.getString("LXBH");
		String lxbh = lxbhlold.substring(0, lxbhlold.length()-7);
		model.setLXBH(lxbh);
		model.setQDZH(obj.getString("QDZH"));
		model.setZDZH(obj.getString("ZDZH"));
		model.setYJD_S(obj.getString("YJD_S"));		
		model.setYJD_X(obj.getString("YJD_X"));	
		model.setYJD_A(obj.getString("YJD_A"));	
		return model;		
	}
														

	private TrafficModel selectLinePoint(TrafficModel model){
		String stake1 = CoordinateUtil.transferStakename(model.getLXBH(),model.getQDZH());
		String stake2 = CoordinateUtil.transferStakename(model.getLXBH(),model.getZDZH());
		//查询上行line 0
		  if(stake1==null||stake2==null){
		    	model.setSxLineCode(300006);
		    	model.setXxLineCode(300006);
		    	return model;
		    }
			TrafficModel sxmodel = CoordinateUtil.transLine(stake1, stake2, 0, null);
			TrafficModel xxmodel = CoordinateUtil.transLine(stake1, stake2, 1, null);
			model.setSxLineCode(sxmodel.getSxLineCode());
			model.setSxList(sxmodel.getSxList());
			model.setXxLineCode(xxmodel.getSxLineCode());
			model.setXxList(xxmodel.getSxList());
			
			return model;
	}
	public static void writeFile(String url,String result){
		//记录时间
	    String today = DateTimeUtil.getCurrTimeFmt("yyyy_MM_dd");
		String filePath = ServletActionContext.getServletContext().getRealPath("/")+today+"_connect.txt";
		File file =new File(filePath);
		try{ 
		if(!file.exists()){
				file.createNewFile();		
		}
		
		FileWriter fileWritter;
		fileWritter = new FileWriter(filePath,true);
		
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        String now = DateTimeUtil.getCurrTimeFmt();
        bufferWritter.write(now+":远程连接：'"+url+"'失败，返回结果："+"\r\n");
        bufferWritter.write(result+"\r\n");
        bufferWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public BaseResult selectJdlx() {
		//查询路线信息
		try{
		List<?> lxxxlist = this.highwayBaseDao.selectLxinfo();
		BaseResult result = new BaseResultOK();
		result.setObj(lxxxlist);
		return result;
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResultFailed();
		}
	}

	
	protected GczModel selectJdInfo(GczModel model) {
		String lxdm = model.getLx();
		String lxjc = model.getLxjc();
	    String  matchFlag = null;
	    String sj,sjStart,sjEnd;
	    try{
	    //查询一个路线代码是否存在2个不同的交通量信息
	    	List nameMatch = this.highwayBaseDao.selectTransSame(lxdm,lxjc);
	    	if(nameMatch!=null&&nameMatch.size()>0){
	    		matchFlag = lxjc;
	    	}
	     //查询年份数据()
	    	 sj = DateTimeUtil.getCurrTimeFmt("yyyy");
			 sjStart = sj+"0101";
			 sjEnd = sj+"1231";
			System.out.println("开始时间："+sjStart+"结束时间："+sjEnd);
			   HJdTransportData yData = this.highwayBaseDao.selectTransMon(lxdm,sjStart,sjEnd,matchFlag);
			    if(yData!=null){
			      yData.setTjsj(sj);
			    }
	    	
		//查询月份数据()
			    Date startTime = DateTimeUtil.getFirstDateOfMonth(new Date());
				  Date endTime = DateTimeUtil.getLastDateOfMonth(new Date());
				   sjStart = DateTimeUtil.getTimeFmt(startTime,"yyyyMMdd");
				   sjEnd = DateTimeUtil.getTimeFmt(endTime,"yyyyMMdd");
				  System.out.println("开始时间："+sjStart+"结束时间："+sjEnd);
				      HJdTransportData mData = this.highwayBaseDao.selectTransMon(lxdm,sjStart,sjEnd,matchFlag);
				      if(mData!=null){
				    	  mData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMM"));
				      }

	
		//查询日的数据()数据库
		HJdTransportData dData = this.highwayBaseDao.selectDayTransdata(lxdm,matchFlag);
		
		if(dData == null)
		{
			dData = new HJdTransportData();
			dData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMMdd"));
			
		}
		if(mData == null)
		{
			mData = new HJdTransportData();
			 mData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMM"));
		}
		if(yData == null)
		{
			yData = new HJdTransportData();
			yData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyy"));
		}
		model.setDayData(dData);
		model.setYearData(yData);
		model.setMonthData(mData);
		return model;
		 
	    }catch(Exception e){
	    	HJdTransportData  dData = new HJdTransportData();
			dData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMMdd"));
			HJdTransportData  mData = new HJdTransportData();
			mData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMM"));
			HJdTransportData  yData = new HJdTransportData();
			yData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyy"));
			model.setDayData(dData);
			model.setYearData(yData);
			model.setMonthData(mData);
	    	return model;
	    }
	}
 
	
	
	protected HJdTransportData selectSameLxTrans(String lxdm,List<HJdTransportData>  data){
		System.out.println("开始查询数据是否相同"+DateTimeUtil.getCurrTimeFmt());
		HJdTransportData pos = null;
		if(data!=null&&data.size()>0){
			for(int i=0;i<data.size();i++){
				HJdTransportData temp = data.get(i);
				if(temp.getLxdm().equals(lxdm)){
					pos = temp;
				}
			}
		}
		System.out.println("开始查询数据是否相同over"+DateTimeUtil.getCurrTimeFmt());
		return pos;
	}
	
	
	@Override
	public BaseResult selectGczInfo() {

		try{
		List<GczModel> list = this.highwayBaseDao.selectGczInfo();
		if(list!=null&&list.size()>0){
		for(int i=0;i<list.size();i++){
			GczModel gcz = list.get(i);
			gcz = this.selectJdInfo(gcz);
			list.set(i, gcz);
			}
		}
		BaseResult result = new BaseResultOK();
		result.setObj(list);
		return result;
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResultFailed();
		}
	}
	
	//请求交调记录保存到数据库
			private  static List<HJdTransportData> findHJdTransportDataBySj(String sj) {
				List<HJdTransportData> list = new ArrayList<HJdTransportData>();
				System.out.println("请求开始"+DateTimeUtil.getCurrTimeFmt());
				String urlaction = newpeportActionName + "?sj=" + sj
						+ "&xzqbs="
						+ Constants.TRANSPORTDATA_ACTION_NEWREPORT_PARAM2;
				System.out.println("urlaction" + urlaction);
				try{
				  String resultlist = HttpUtil.sendGet(urlaction);
					System.out.println("请求结束，开始转换"+DateTimeUtil.getCurrTimeFmt());
			    	if (resultlist != null && !resultlist.equals("")
						&& !resultlist.equals("[]")) {
					JSONArray jsonarray =  JSONArray.parseArray(resultlist);
					for (int i = 0; i < jsonarray.size() - 1; i++) { // 丢弃合计数据
						JSONObject jsonObj = jsonarray.getJSONObject(i);
						HJdTransportData tdData = jsonToObject(jsonObj);
						tdData.setTjsj(sj);
						// 路线名称
						String lxmc = tdData.getLxmc();
						String lxdm = lxmc;
						if (lxmc != null && lxmc.indexOf("(") > 0) {
							lxdm = lxmc.substring(0, lxmc.indexOf("("));
						}
					
						tdData.setLxdm(lxdm);
						list.add(tdData);
					}

				    } 
			    	}catch(Exception e){
					e.printStackTrace();
					return list;
				}
				System.out.println("转换结束"+DateTimeUtil.getCurrTimeFmt());
			return list;

	}		
			 private static HJdTransportData jsonToObject(JSONObject  jsonObj){
				 HJdTransportData tdData = new HJdTransportData();
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
		       
				 return tdData;
			 }


			@Override
			public BaseResult selectDayData(String lxdm) {
				try{
				//查询日的数据()数据库
				HJdTransportData dData = this.highwayBaseDao.selectDayTransdata(lxdm,null);
				BaseResult result = new BaseResultOK();
				result.setObj(dData);
				return result;
				}catch(Exception e){
					e.printStackTrace();
					return new BaseResultFailed();
				}
			}


			@Override
			public BaseResult selectMonData(String lxdm) {
				//  String sj = DateTimeUtil.getCurrTimeFmt("yyyyMM");
				  Date startTime = DateTimeUtil.getFirstDateOfMonth(new Date());
				  Date endTime = DateTimeUtil.getLastDateOfMonth(new Date());
				  String sjStart = DateTimeUtil.getTimeFmt(startTime,"yyyyMMdd");
				  String sjEnd = DateTimeUtil.getTimeFmt(endTime,"yyyyMMdd");
				  System.out.println("开始时间："+sjStart+"结束时间："+sjEnd);
				 try{
				      HJdTransportData yData = this.highwayBaseDao.selectTransMon(lxdm,sjStart,sjEnd,null);
				      if(yData!=null){
				    	  yData.setTjsj(DateTimeUtil.getCurrTimeFmt("yyyyMM"));
				      }
				     
					  BaseResult result = new BaseResultOK();
					  result.setObj(yData);
					  return result;
					}catch(Exception e){
						e.printStackTrace();
						return new BaseResultFailed();
					}
				
			}


			@Override
			public BaseResult selectYearData(String lxdm) {
				String sj = DateTimeUtil.getCurrTimeFmt("yyyy");
				  String sjStart = sj+"0101";
				  String sjEnd = sj+"1231";
				  System.out.println("开始时间："+sjStart+"结束时间："+sjEnd);
				 try{
				      HJdTransportData yData = this.highwayBaseDao.selectTransMon(lxdm,sjStart,sjEnd,null);
				      if(yData!=null){
				    	  yData.setTjsj(sj);
				      }
				     
					  BaseResult result = new BaseResultOK();
					  result.setObj(yData);
					  return result;
					}catch(Exception e){
						e.printStackTrace();
						return new BaseResultFailed();
					}
				
			}

			
}
