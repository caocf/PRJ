package com.elc.transfer.modeling;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.elc.transfer.entity.Direct;
import com.elc.transfer.entity.Line;
import com.elc.transfer.entity.Station;

/**
 * 初始化内存加载
 * @author Administrator zwc
 * 
 */
public class Loading {

	/**
	 * 内存线路缓存
	 */
	private static List<Line> lines = null;
	
	/**
	 * 内存站点缓存
	 */
	private static List<Station> stations = null;

	/**
	 * 获取缓存线路列表
	 * @return
	 */
	public static List<Line> getLines() {
		if (lines == null)
			reload();

		return lines;
	}

	/**
	 * 获取缓存站点列表
	 * @return
	 */
	public static List<Station> getStations() {
		if (stations == null)
			reload();

		return stations;
	}

	/**
	 * 初始化加载
	 */
	public static void load() {

		loadStation();
		
		loadLine();

		System.out.println("线路数：" + lines.size() + "启动+获取线路耗时");
		System.out.println("站点数" + stations.size() + "耗时");
	}

	/**
	 * 重新加载
	 */
	public static void reload() {
		clear();
		load();
	}

	/**
	 * 清除所有加载
	 */
	public static void clear() {
		lines = null;
		stations = null;
	}

	/**
	 * 查找周边站点
	 */
	public static void findNearbyStation() {

	}

	/**
	 * 从数据库中查找线路，缓存到内存中
	 */
	public static void loadLine()
	{
		String sql="select l.xlbh,l.xlmc,t.zdbh,t.ZDCX,t.XLFX from ZHJTADMIN.Z_GJ_XLJBXX l ,ZHJTADMIN.Z_GJ_XLZXB t where l.xlbh=t.xlbh order by l.xlbh,t.zdcx";
		List<?> temp=OracleSessionFactoryManager.querySQL(sql);
		
		try
		{
			for(int i=0;i<temp.size();i++)
			{
				Object[] objs=(Object[])temp.get(i);
				
				int id=(new BigDecimal(String.valueOf(objs[0]))).intValue();
				int zdbh=(new BigDecimal(String.valueOf(objs[2]))).intValue();
				int zdfx=(new BigDecimal(String.valueOf(objs[4]))).intValue();
				
				
				Line tempLine=findLine(id,zdfx);
				
				tempLine.setName((String)objs[1]);
							
				Station tempStation=findStation(zdbh);
				tempStation.addLine(tempLine);
				tempLine.addStations(tempStation);
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 从数据库中查找站点，缓存到内存中
	 */
	public static void loadStation()
	{
		if(stations==null)
			 stations=new ArrayList<>();
		
		String sql="select l.zdbh,l.zdmc,l.zdwd,l.zdjd from ZHJTADMIN.Z_GJ_ZDJBXX l order by l.zdbh";
		List<?> temp=OracleSessionFactoryManager.querySQL(sql);
		
		try
		{
			for(int i=0;i<temp.size();i++)
			{
				Object[] objs=(Object[])temp.get(i);
				
				Station tempLine=new Station();
				
				tempLine.setId((new BigDecimal(String.valueOf(objs[0]))).intValue());
				tempLine.setName((String)objs[1]);
				tempLine.setLan(Double.valueOf((String)objs[2]));
				tempLine.setLon(Double.valueOf((String)objs[3]));
				
				stations.add(tempLine);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static List<Direct> directs=new ArrayList<Direct>();
	
	public static void loadDirect()
	{
		String sql="select d.bt_startstation,d.bt_endstation,d.bt_line,d.bt_startname,d.bt_endname,d.bt_linename from direct d";
		
		MysqlSessionFactoryManager.openSession();
		
		List<?> temp=MysqlSessionFactoryManager.querySQL(sql);
		
		try {
			for(int i=0;i<temp.size();i++)
			{
				Object[] objs=(Object[])temp.get(i);
				
				Direct direct=new Direct();
				
				direct.setStart((Integer)objs[0]);
				direct.setEnd((Integer)objs[1]);
				direct.setLine((Integer)objs[2]);
				direct.setStartName((String)objs[3]);
				direct.setEndName((String)objs[4]);
				direct.setLineName((String)objs[5]);
				
				directs.add(direct);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		MysqlSessionFactoryManager.closeSession();
	}
	
	/**
	 * 根据id查找内存中是否存在该站点，如是返回，如否返回一个new对象
	 * @param id 站点id
	 * @return
	 */
	public static Station findStation(int id) {
		if (stations == null) 
			stations = new ArrayList<>();
		
		for(Station s:stations)
		{
			if(s.getId()==id)
				return s;
		}
		
		Station newStation=new Station();
		stations.add(newStation);
		
		return newStation;
	}

	/**
	 * 根据id查找内存中是否存在该线路，如是返回，如否返回一个new对象
	 * @param id 线路id
	 * @param direct
	 * @return
	 */
	public static Line findLine(int id,int direct) {
		if (lines == null) 
			lines = new ArrayList<>();

		
		for(Line l:lines)
		{
			if(l.getId()==id && l.getDirect()==direct)
				return l;
		}
		
		Line newLine=new Line();
		newLine.setId(id);
		newLine.setDirect(direct);
		lines.add(newLine);
		
		return newLine;
		
	}
}
