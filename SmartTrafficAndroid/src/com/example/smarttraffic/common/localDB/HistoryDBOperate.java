package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.favor.OneItemData;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.bean.StationInfo;
import com.example.smarttraffic.util.DoString;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HistoryDBOperate 
{
	public HistoryDBOperate(Context context)
	{
		init(context);
	}
	
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	private void init(Context context)
	{
		dbHelper=new DBHelper(context);
		OpenWriteDB();
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
	
	public void insert(int type,String content)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=content;
		
		db.execSQL(HistorySQLStrings.INSERT_HISTORY_STATEMENT, args);
	}
	
	public void insert(int type,String start,String end)
	{
		String content=DoString.mergerByDefault(new String[]{start,end});
		
		insert(type, content);
	}
	
	public void insertLine(int type,BusLine lineInfo)
	{
		String[] temp=new String[]{String.valueOf(lineInfo.getId()),lineInfo.getName(),lineInfo.getDownStartStationName(),lineInfo.getDownEndStationName()};
		String content=DoString.mergerByDefault(temp);
		insert(type, content);
	}
	
	public void insertStation(int type,StationInfo stationInfo)
	{
		String[] temp=new String[]{String.valueOf(stationInfo.getId()),stationInfo.getName(),DoString.mergerByDefault(stationInfo.getLines())};
		String content=DoString.mergerByDefault(temp);
		insert(type, content);
	}
	
	public void toUpdate(int id,int type,String content)
	{
		delete(id);
		insert(type, content);
	}
	
	public void toUpdate(int id,int type,String arg1,String arg2)
	{
		delete(id);
		insert(type,arg1,arg2);
	}
	
	public boolean isHistory(int type,BusLine lineInfo)
	{
		String[] temp=new String[]{String.valueOf(lineInfo.getId()),lineInfo.getName(),lineInfo.getDownStartStationName(),lineInfo.getDownEndStationName()};
		
		String content=DoString.mergerByDefault(temp);
		
		return isHistory(type, content);
	}
	
	public boolean isHistory(int type,StationInfo stationInfo)
	{
		String[] temp=new String[]{String.valueOf(stationInfo.getId()),stationInfo.getName(),DoString.mergerByDefault(stationInfo.getLines())};
		
		String content=DoString.mergerByDefault(temp);
		
		return isHistory(type, content);
	}
	
	public boolean isHistory(int type,String content)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=content;
		
		Cursor cursor= db.rawQuery(HistorySQLStrings.IS_HISTORY_STATEMENT, args);
		
		if(cursor.getCount()>0)
			return true;
		return false;
	}
	
	public boolean isHistory(int type,String arg1,String arg2)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=DoString.mergerByDefault(new String[]{arg1,arg2});
		
		Cursor cursor= db.rawQuery(HistorySQLStrings.IS_HISTORY_STATEMENT, args);
		
		if(cursor.getCount()>0)
			return true;
		return false;
	}
	
	public void update(int id,String content)
	{
		String[] args=new String[2];
		args[0]=content;
		args[1]=String.valueOf(id);
		
		db.execSQL(HistorySQLStrings.UPDATE_HISTORY_STATEMENT, args);
	}
	
	public void delete(int id)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(id);
		
		db.execSQL(HistorySQLStrings.DELETE_HISTORY_STATEMENT, args);
	}
	
	public void deleteAll(int type)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(type);
		
		db.execSQL(HistorySQLStrings.DELETE_ALL_HISTORY_STATEMENT, args);
	}
	
	public Cursor select(int type)
	{
		return select(type, true);
	}
	
	public Cursor select(int type,boolean desc)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(type);
		
		if(desc)
			return db.rawQuery(HistorySQLStrings.SELECT_HISTORY_STATEMENT, args);
		else
			return db.rawQuery(HistorySQLStrings.SELECT_HISTORY_STATEMENT_ASC, args);
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
	
	public List<BusLine> selectForLineInfo(int type)
	{
		List<BusLine> result=new ArrayList<BusLine>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		try
		{		
			while (cursor.moveToNext())
			{
				BusLine tempData=new BusLine();
				
				String contentString=cursor.getString(2);
				
				String[] resultString=DoString.splitByDefault(contentString);
				
				tempData.setId(Integer.valueOf(resultString[0]));
				tempData.setName(resultString[1]);
				tempData.setDownStartStationName(resultString[2]);
				tempData.setDownEndStationName(resultString[3]);
				
				result.add(tempData);
			}
		}
		catch(Exception e)
		{
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
		
		try
		{		
			while (cursor.moveToNext())
			{
				TwoItemsData tempData=new TwoItemsData();
				
				String contentString=cursor.getString(2);
				
				String[] resultString=DoString.splitByDefault(contentString);
				
				tempData.setId(cursor.getInt(0));
				tempData.setStart(resultString[0]);
				tempData.setEnd(resultString[1]);
				
				result.add(tempData);
			}
		}
		catch(Exception e)
		{
		}
		
		cursor.close();
		
		return result;
	}
	
	public List<String> selectForString(int type)
	{
		List<String> result=new ArrayList<String>();
		
		Cursor cursor=select(type,false);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			String tempData;
			
			String contentString=cursor.getString(2);
			
			tempData=contentString;
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
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
			
			tempData.setId(cursor.getInt(0));
			tempData.setData(contentString);
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
}
