package com.example.smarttraffic.common.localDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper 
{
	public static final String db_name="Traffic";
	public static final int db_version=1;
	
	public DBHelper(Context context) {
		super(context, db_name, null, db_version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(HistorySQLStrings.CREATE_HISTORY_DB);
		db.execSQL(FavorSQLString.CREATE_FAVOR_DB);
//		db.execSQL(DetailFavorSQLString.CREATE_FAVOR_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		onCreate(db);
	}

}
