package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BaseHistoryDBoperation
{
	public BaseHistoryDBoperation(Context context)
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
	
	public void toUpdate(int id,int type,String content)
	{
		delete(id);
		insert(type, content);
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
	
	public List<BaseHistoryData> selectForBaseData(int type)
	{
		List<BaseHistoryData> result=new ArrayList<BaseHistoryData>();
		
		Cursor cursor=select(type);
		
		if(cursor.getCount()==-1)
		{
			return null;
		}
		
		while (cursor.moveToNext())
		{
			BaseHistoryData temp=new BaseHistoryData();
			
			temp.setId(cursor.getInt(0));
			temp.setType(cursor.getInt(1));
			temp.setContent(cursor.getString(2));
			
			result.add(temp);
		}
		
		cursor.close();
		
		return result;
	}
}
