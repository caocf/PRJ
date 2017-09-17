package com.elc.transfer.util;

import java.util.ArrayList;
import java.util.List;

import com.elc.transfer.entity.Line;
import com.elc.transfer.entity.Station;
import com.elc.transfer.modeling.Loading;

/**
 * 
 * @author Administrator
 *
 */
public class TransferUtil {

	/**
	 * 查询周边站点
	 * @param lan 当前纬度
	 * @param lon 当前经度
	 * @param radius 半径
	 * @return
	 */
	public static List<Station> queryNearbyStation(double lan,double lon,int radius)
	{
		List<Station> result=new ArrayList<Station>();
		
		List<Station> all=Loading.getStations();
		
		for(Station s:all)
		{
			double distance=GPSToMeter.ComputeDistance(lan, lon, s.getLan(), s.getLon());
			
//			System.out.println(s.getName()+":"+distance);
		
			if( distance<= radius)
				result.add(s);
		}
		
		return result;
	}
	
	/**
	 * 站点列表所有经过非重复线路
	 * @param s 站点列表
	 * @return 线路列表
	 */
	public static List<Line> queryAllLine(List<Station> s)
	{
		List<Line> result=new ArrayList<Line>();
		
		for(Station t:s)
		{
			for(Line l:t.getLines())
				if(!result.contains(l))
					result.add(l);
		}
		
		return result;
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public static List<Station> queryAllStation(List<Line> l)
	{
		List<Station> result=new ArrayList<Station>();
		
		for(Line t:l)
		{
			for(Station s:t.getStations())
				if(!result.contains(s))
					result.add(s);
		}
		
		return result;
	}
	
	/**
	 * 相同线路
	 * @param s
	 * @param e
	 * @return
	 */
	public static List<Line> crossLine(Station s,Station e)
	{
		List<Line> result=new ArrayList<Line>();
		
		try
		{
		for(Line start:s.getLines())
		{
			for(Line end:e.getLines())
			{
				if(start.getId()==end.getId() && start.getDirect()==end.getDirect())
					result.add(end);
			}
		}
		}
		catch(Exception exc)
		{
			
		}
		
		return result;
	}
}
