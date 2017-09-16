package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smarttraffic.favor.TwoItemsData;

public class DetailFavorDBOperate
{
	public DetailFavorDBOperate(Context context)
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
	
	public void insert(int type,String name,String address,String phone,int level,int lantitude,int longtitude)
	{
		String[] args=new String[7];
		args[0]=String.valueOf(type);
		args[1]=name;
		args[2]=address;
		args[3]=phone;
		args[4]=String.valueOf(level);
		args[5]=String.valueOf(lantitude);
		args[6]=String.valueOf(longtitude);
		
		db.execSQL(DetailFavorSQLString.INSERT_FAVOR_STATEMENT, args);
	}
	
//	public void update(int id,String content)
//	{
//		String[] args=new String[2];
//		args[0]=content;
//		args[1]=String.valueOf(id);
//		
//		db.execSQL(FavorSQLString.UPDATE_FAVOR_STATEMENT, args);
//	}
	
	public void delete(int id)
	{
		String[] args=new String[1];
		args[0]=String.valueOf(id);
		
		db.execSQL(DetailFavorSQLString.DELETE_FAVOR_STATEMENT, args);
	}
	
	public Cursor select(int type)
	{
		try
		{
			String[] args=new String[1];
			args[0]=String.valueOf(type);
			
			return db.rawQuery(DetailFavorSQLString.SELECT_FAVOR_STATEMENT, args);
		} catch (Exception e)
		{
			
		}
		return null;
	}
	
	public boolean isFavor(int type,String name)
	{
		String[] args=new String[2];
		args[0]=String.valueOf(type);
		args[1]=name;
		
		Cursor cursor= db.rawQuery(DetailFavorSQLString.IS_FAVOR_STATEMENT, args);
		
		if(cursor.getCount()>0)
			return true;
		return false;
	}
//	
//	public boolean isFavor(int type,String arg1,String arg2)
//	{
//		String[] args=new String[2];
//		args[0]=String.valueOf(type);
//		args[1]=DoString.mergerByDefault(new String[]{arg1,arg2});
//		
//		Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
//		
//		if(cursor.getCount()>0)
//			return true;
//		return false;
//	}
	
//	public List<OneItemData> selectForOneItem(int type)
//	{
//		List<OneItemData> result=new ArrayList<OneItemData>();
//		
//		Cursor cursor=select(type);
//		
//		if(cursor.getCount()==-1)
//		{
//			return null;
//		}
//		
//		while (cursor.moveToNext())
//		{
//			OneItemData tempData=new OneItemData();
//			
//			String contentString=cursor.getString(2);			
//			
//			tempData.setData(contentString);
//			
//			result.add(tempData);
//		}
//		
//		cursor.close();
//		
//		return result;
//	}
//	
	public List<TwoItemsData> selectForTwoItem(int type)
	{
		List<TwoItemsData> result=new ArrayList<TwoItemsData>();
		
		Cursor cursor=select(type);
		
		if(cursor==null || cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			TwoItemsData tempData=new TwoItemsData();
			
			tempData.setId(cursor.getInt(0));
			
			String name=cursor.getString(2);
			
			String address=cursor.getString(3);
			
			tempData.setStart(name);
			tempData.setEnd(address);
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
	
//	public DrivingSchool selectByID()
//	{
//		
//	}
}
