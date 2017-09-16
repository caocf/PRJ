package com.hxkg.localdb;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDB extends SQLiteOpenHelper
{
	SQLiteDatabase db;

	public LocalDB(Context context)
	{
		super(context, "LocalDB", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		this.db=db;
		CreateNoteTB();
		
	}
	
	private void  CreateNoteTB()
	{
		final String tb_sql = "CREATE TABLE IF NOT EXISTS " 
				+ "Note (id  INTEGER,recordtime TEXT,status INTEGER,type INTEGER,userid INTEGER)";
		db.execSQL(tb_sql);
	}
	public void InsertIntoNoteTB(int noteid,int type,int userid) 
	{
		db.execSQL("REPLACE INTO Note VALUES (?, ?,?,?,?)",new Object[]{noteid,new Date(),1,type,userid});
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//新版本有变化时这里更改表结构		
	}

}
