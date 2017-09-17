package com.sts.smartbus.action;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.smartbus.dao.SmartBusOracleDao;
import com.sts.smartbus.model.BikeStation;
import com.sts.smartbus.model.BikeStationTemp;
import com.sts.smartbus.model.ZxcZdxx;


public class BikeStationTimer 
{
	
	public static String URL= "http://phoneapp.jx96520.com.cn:10100/bike/station/nearby/?application=jiaotongju&longitude=120.756&latitude=30.764&distance=999999999&count=10000";
	
	public static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public void doIt()
	{
		PostDateFromSIWEI postDateFromSIWEI=new PostDateFromSIWEI();
		String jsonStr = postDateFromSIWEI.GetDataFromThreeNet(URL);
		
		// 返回结果转成list对象
		BikeStationTemp result=JSON.parseObject(jsonStr, BikeStationTemp.class);
		List<BikeStation> list=result.getStationList();
		if(list == null || list.size() == 0)
		{
			return;
		}
		
		SmartBusOracleDao smartBusOracleDao = (SmartBusOracleDao) applicationContext.getBean("smartBusOracleDao");
		
		// 清除之前的站点信息
		smartBusOracleDao.ClearZdxx();
		
		// 遍历接口返回的站点信息，并保存
		for(BikeStation bs : list)
		{
			ZxcZdxx zdxx = new ZxcZdxx();
			zdxx.setZdbh(bs.getId());
			zdxx.setZdmc(bs.getName());
			zdxx.setZdqy(bs.getAreaName());
			zdxx.setZddz(bs.getAddress());
			zdxx.setZcws(bs.getCount());
			zdxx.setFwdh(bs.getStationPhone());
			zdxx.setFwsj(bs.getServiceTime());
			zdxx.setSfqt(bs.isIsAllDay()?1:0);
			zdxx.setZdjd(String.valueOf(bs.getLongitude()));
			zdxx.setZdwd(String.valueOf(bs.getLatitude()));
			zdxx.setSfyrzb(bs.isPersonDuty()?1:0);
			
			smartBusOracleDao.saveZdxx(zdxx);
		}
	}
	

}
