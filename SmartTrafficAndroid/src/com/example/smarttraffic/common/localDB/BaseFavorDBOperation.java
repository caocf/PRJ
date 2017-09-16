package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BaseFavorDBOperation
{
	public BaseFavorDBOperation(Context context)
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
	
	public List<BaseFavorData> selectForBaseFavor(int type)
	{
		List<BaseFavorData> result=new ArrayList<BaseFavorData>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			BaseFavorData tempData=new BaseFavorData();
			
			String contentString=cursor.getString(2);			
			
			tempData.setContent(contentString);
			tempData.setId(cursor.getInt(0));
			tempData.setType(type);
			
			result.add(tempData);
		}
		
		cursor.close();
		
		return result;
	}
	
	public void deleteFavorID(int type,String content)
	{
		try
		{
			String[] args=new String[2];
			args[0]=String.valueOf(type);
			args[1]=content;
			
			Cursor cursor= db.rawQuery(FavorSQLString.IS_FAVOR_STATEMENT, args);
			
			if(cursor.getCount()>0)
			{
				cursor.moveToFirst();
				int i= cursor.getInt(0);
				delete(i);
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
