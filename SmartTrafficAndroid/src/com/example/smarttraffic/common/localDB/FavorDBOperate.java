package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.alarm.AlarmInfo;
import com.example.smarttraffic.favor.OneItemData;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.smartBus.bean.LineInfo;
import com.example.smarttraffic.smartBus.bean.StationInfo;
import com.example.smarttraffic.util.DoString;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavorDBOperate 
{	
	public FavorDBOperate(Context context)
	{
		init(context);
	}
	
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	private void init(Context context)
	{
		dbHelper=new DBHelper(context);
		OpenReadDB();
	}
	
	public void OpenReadDB()
	{
		if (db == null || !db.isOpen())
			db = dbHelper.getReadableDatabase();
	}

	public void OpenWriteDB()
	{
		if (db == null || !db.isOpen())
			db = dbHelper.getWritableDatabase();
	}

	public void CloseDB()
	{
		if (db != null && db.isOpen())
			db.close();
	}
	
	public void insert(int type,String arg1,String arg2,double lan1,double lon1,double lan2,double lon2)
	{
		int nlan1=(int)(lan1*1e6);
		int nlon1=(int)(lon1*1e6);
		int nlan2=(int)(lan2*1e6);
		int nlon2=(int)(lon2*1e6);
		
		insert(type, arg1, arg2, nlan1, nlon1, nlan2, nlon2);
	}
	
	public void insert(int type,String arg1,String arg2,int lan1,int lon1,int lan2,int lon2)
	{
		Object[] args=new Object[6];
		args[0]=String.valueOf(type);
		args[1]=DoString.mergerByDefault(new String[]{arg1,arg2});
		args[2]=lan1;
		args[3]=lon1;
		args[4]=lan2;
		args[5]=lon2;
		
		db.execSQL(FavorSQLString.INSERT_FAVOR_STATEMENT, args);
	}
	
	public void insert(int type,String arg1,String arg2)
	{
		insert(type, arg1, arg2, 0, 0, 0, 0);
	}
	
	public void insert(int type,String arg1,String arg2,int lan1,int lon1)
	{
		insert(type, arg1, arg2, lan1, lon1, 0, 0);
	}
	
	public void insert(int type,String content)
	{
		Object[] args=new Object[6];
		args[0]=String.valueOf(type);
		args[1]=content;
		args[2]=0;
		args[3]=0;
		args[4]=0;
		args[5]=0;
				
		db.execSQL(FavorSQLString.INSERT_FAVOR_STATEMENT, args);
	}
	
	public void insert(int type,String[] content)
	{
		Object[] args=new Object[6];
		args[0]=String.valueOf(type);
		args[1]=DoString.mergerByDefault(content);
		args[2]=0;
		args[3]=0;
		args[4]=0;
		args[5]=0;
				
		db.execSQL(FavorSQLString.INSERT_FAVOR_STATEMENT, args);
	}
	
	public void update(int id,String content)
	{
		String[] args=new String[2];
		args[0]=content;
		args[1]=String.valueOf(id);
		
		db.execSQL(FavorSQLString.UPDATE_FAVOR_STATEMENT, args);
	}
	
	public void delete(int id)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(id);
		
		db.execSQL(FavorSQLString.DELETE_FAVOR_STATEMENT, args);
	}
	
	public Cursor select(int type)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(type);
		
		return db.rawQuery(FavorSQLString.SELECT_FAVOR_STATEMENT, args);
	}
	
	public boolean isFavor(int type,String content)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=content;
		
		Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
		
		if(cursor.getCount()>0)
			return true;
		return false;
	}
	
	public void insertAlarm(int type,AlarmInfo alarmInfo)
	{
//		String[] temp=new String[]{String.valueOf(alarmInfo.getLineID()),alarmInfo.getLineName(),
//				String.valueOf(alarmInfo.getMethod()),String.valueOf(alarmInfo.getStationID()),
//				alarmInfo.getStationName(),String.valueOf(alarmInfo.isAdvance()),
//				String.valueOf(alarmInfo.isCrowed()),String.valueOf(alarmInfo.isOpen()),
//				String.valueOf(alarmInfo.isUp()),String.valueOf(alarmInfo.getPlanUpCar()),
//				String.valueOf(alarmInfo.getTime()),String.valueOf(alarmInfo.getWeekSelect())};
//		String content=DoString.mergerByDefault(temp);
		alarmInfo.setId(0);
		alarmInfo.setSelect(false);
		alarmInfo.setData("");
		String content=JSON.toJSONString(alarmInfo);
		insert(type, content);
	}
	
	public void insertLine(int type,LineInfo lineInfo)
	{
		String[] temp=new String[]{String.valueOf(lineInfo.getId()),lineInfo.getName(),lineInfo.getStart(),lineInfo.getEnd(),lineInfo.getStartTime(),lineInfo.getEndTime()};
		String content=DoString.mergerByDefault(temp);
		insert(type, content);
	}
	
	public void insertStation(int type,StationInfo stationInfo)
	{
		String[] temp=new String[]{String.valueOf(stationInfo.getId()),stationInfo.getName(),DoString.mergerByDefault(stationInfo.getLines())};
		String content=DoString.mergerByDefault(temp);
		insert(type, content);
	}
	
	public boolean isAlarm(int type,AlarmInfo alarmInfo)
	{
//		String[] temp=new String[]{String.valueOf(alarmInfo.getLineID()),alarmInfo.getLineName(),
//				String.valueOf(alarmInfo.getMethod()),String.valueOf(alarmInfo.getStationID()),
//				alarmInfo.getStationName(),String.valueOf(alarmInfo.isAdvance()),
//				String.valueOf(alarmInfo.isCrowed()),String.valueOf(alarmInfo.isOpen()),
//				String.valueOf(alarmInfo.isUp()),String.valueOf(alarmInfo.getPlanUpCar()),
//				String.valueOf(alarmInfo.getTime()),String.valueOf(alarmInfo.getWeekSelect())};
//		String content=DoString.mergerByDefault(temp);
		
		alarmInfo.setId(0);
		alarmInfo.setSelect(false);
		alarmInfo.setData("");
		String content=JSON.toJSONString(alarmInfo);
		return isFavor(type, content);
	}
	public boolean isFavor(int type,LineInfo lineInfo)
	{
		String[] temp=new String[]{String.valueOf(lineInfo.getId()),lineInfo.getName(),lineInfo.getStart(),lineInfo.getEnd(),lineInfo.getStartTime(),lineInfo.getEndTime()};
		
		String content=DoString.mergerByDefault(temp);
		
		return isFavor(type, content);
	}
	
	public void deleteFavor(int type,LineInfo lineInfo)
	{
		String[] temp=new String[]{String.valueOf(lineInfo.getId()),lineInfo.getName(),lineInfo.getStart(),lineInfo.getEnd(),lineInfo.getStartTime(),lineInfo.getEndTime()};
		
		String content=DoString.mergerByDefault(temp);
		int i=getFavorID(type, content);
		if(i!=-1)
			delete(i);
	}
	
	public boolean isFavor(int type,StationInfo stationInfo)
	{
		String[] temp=new String[]{String.valueOf(stationInfo.getId()),stationInfo.getName(),DoString.mergerByDefault(stationInfo.getLines())};
		
		String content=DoString.mergerByDefault(temp);
		
		return isFavor(type, content);
	}
	
	public void deleteFavor(int type,StationInfo stationInfo)
	{
		String[] temp=new String[]{String.valueOf(stationInfo.getId()),stationInfo.getName(),DoString.mergerByDefault(stationInfo.getLines())};
		
		String content=DoString.mergerByDefault(temp);
		int i=getFavorID(type, content);
		if(i!=-1)
			delete(i);
	}
	
	public boolean isFavor(int type,String arg1,String arg2)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=DoString.mergerByDefault(new String[]{arg1,arg2});
		
		Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
		
		if(cursor.getCount()>0)
			return true;
		return false;
	}
	
	public int getFavorID(int type,String arg)
	{
		try
		{
			String[] args=new String[2];
			args[0]=String.valueOf(type);
			args[1]=arg;
			
			Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
			
			if(cursor.getCount()>0)
			{
				cursor.moveToFirst();
				return cursor.getInt(0);
			}
		}
		catch(Exception e)
		{
			return -1;
		}
		return -1;
	}
	
	public int getFavorID(int type,String arg1,String arg2)
	{
		try
		{
			String[] args=new String[2];
			args[0]=String.valueOf(type);
			args[1]=DoString.mergerByDefault(new String[]{arg1,arg2});
			
			Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
			
			if(cursor.getCount()>0)
			{
				cursor.moveToFirst();
				return cursor.getInt(0);
			}
		}
		catch(Exception e)
		{
			
		}
		return -1;
	}
	
	public List<OneItemData> selectForOneItem(int type)
	{
		List<OneItemData> result=new ArrayList<OneItemData>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			OneItemData tempData=new OneItemData();
			
			String contentString=cursor.getString(2);			
			
			tempData.setData(contentString);
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<TwoItemsData> selectForMutiItem(int type)
	{
		List<TwoItemsData> result=new ArrayList<TwoItemsData>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			TwoItemsData tempData=new TwoItemsData();
			
			tempData.setId(cursor.getInt(0));
			
			String contentString=cursor.getString(2);
			
			String[] resultString=DoString.splitByDefault(contentString);
			
			tempData.setStart(resultString[0]);
			tempData.setEnd(resultString[1]);
			tempData.setTotal(resultString);
			
			tempData.setLan1(cursor.getInt(3));
			tempData.setLon1(cursor.getInt(4));
			tempData.setLan2(cursor.getInt(5));
			tempData.setLon2(cursor.getInt(6));
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<TwoItemsData> selectForTwoItem(int type)
	{
		List<TwoItemsData> result=new ArrayList<TwoItemsData>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			TwoItemsData tempData=new TwoItemsData();
			
			tempData.setId(cursor.getInt(0));
			
			String contentString=cursor.getString(2);
			
			String[] resultString=DoString.splitByDefault(contentString);
			
			tempData.setStart(resultString[0]);
			tempData.setEnd(resultString[1]);
			tempData.setTotal(resultString);
			
			tempData.setLan1(cursor.getInt(3));
			tempData.setLon1(cursor.getInt(4));
			tempData.setLan2(cursor.getInt(5));
			tempData.setLon2(cursor.getInt(6));
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<StationInfo> selectForStationInfo(int type)
	{
		List<StationInfo> result=new ArrayList<StationInfo>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		try
		{		
			while (cursor.moveToNext())
			{
				StationInfo tempData=new StationInfo();
				
				String contentString=cursor.getString(2);
				
				String[] resultString=DoString.splitByDefault(contentString);
				
				tempData.setFavorID(cursor.getInt(0));
				
				tempData.setId(Integer.valueOf(resultString[0]));
				tempData.setName(resultString[1]);
				
				if(resultString.length>2)
				{
					String[] lines=new String[resultString.length-2];
					
					for(int i=0;i<lines.length;i++)
					{
						lines[i]=resultString[i+2];
					}
					
					tempData.setLines(lines);
				}		
				
				result.add(tempData);
			}
		}
		catch(Exception e)
		{
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<LineInfo> selectForLineInfo(int type)
	{
		List<LineInfo> result=new ArrayList<LineInfo>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		try
		{		
			while (cursor.moveToNext())
			{
				LineInfo tempData=new LineInfo();
				
				String contentString=cursor.getString(2);
				
				tempData.setFavorID(cursor.getInt(0));
				
				String[] resultString=DoString.splitByDefault(contentString);
				
				tempData.setId(Integer.valueOf(resultString[0]));
				tempData.setName(resultString[1]);
				tempData.setStart(resultString[2]);
				tempData.setEnd(resultString[3]);
				tempData.setStartTime(resultString[4]);
				tempData.setEndTime(resultString[5]);
				
				result.add(tempData);
			}
		}
		catch(Exception e)
		{
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<AlarmInfo> selectForAlarmInfo(int type)
	{
		List<AlarmInfo> result=new ArrayList<AlarmInfo>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		try
		{		
			while (cursor.moveToNext())
			{
				AlarmInfo tempData=new AlarmInfo();
					
				String contentString=cursor.getString(2);
				tempData=JSON.parseObject(contentString, AlarmInfo.class);
				tempData.setId(cursor.getInt(0));
				
//				String[] resultString=DoString.splitByDefault(contentString);
//				
//				tempData.setAdvance(Boolean.valueOf(resultString[5]));
//				tempData.setCrowed(Boolean.valueOf(resultString[6]));
//				tempData.setLineID(Integer.valueOf(resultString[0]));
//				tempData.setLineName(resultString[1]);
//				tempData.setMethod(Integer.valueOf(resultString[2]));
//				tempData.setOpen(Boolean.valueOf(resultString[7]));
//				tempData.setPlanUpCar(Integer.valueOf(resultString[9]));
//				tempData.setStationID(Integer.valueOf(resultString[3]));
//				tempData.setStationName(resultString[4]);
//				tempData.setTime(Integer.valueOf(resultString[10]));
//				tempData.setUp(Boolean.valueOf(resultString[8]));
//				tempData.setWeekSelect(resultString[11]);
				
				result.add(tempData);
			}
		}
		catch(Exception e)
		{
		}
		
		cursor.close();
		
		return result;
	}
}
