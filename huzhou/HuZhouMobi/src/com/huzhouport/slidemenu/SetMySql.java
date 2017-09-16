/**
 * 
 */
package com.huzhouport.slidemenu;
import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.util.HttpUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author admin
 *
 */
public class SetMySql extends SQLiteOpenHelper {
    //调用父类构造器  
    public SetMySql(Context context, String name, CursorFactory factory,  
            int version) {  
        super(context, name, factory, version);  
    }  

	
	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根


		// 这里没有考虑数据冗余，先看看一个表所占用的大小
		db.execSQL("drop table if exists t_setip");

		
        db.execSQL("create table if not exists t_setip ([SetID] VARCHAR(20)  NOT NULL PRIMARY KEY,[SetIP] "
					+ "VARCHAR(50),[SetPort] VARCHAR(50))"); 
        
    	
	    db.execSQL("insert into t_setip values ('1','"+HttpUtil.BASE_URL_IP+"','"+HttpUtil.BASE_URL_PORT+"')");
	        
    	
		
	}

	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根

	}

	public List<String> findAllUser() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT UserID, name, LawID FROM t_user where DepartmentName=1 ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("UserID")) + ":" + cursor.getString(cursor
					.getColumnIndex("name"))+ "(" +cursor.getString(cursor
							.getColumnIndex("LawID"))+ ")";
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}
	public List<String> findAllCarType() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT CarTypeID, CarTypeName FROM t_cartype ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("CarTypeID"))+":"+cursor.getString(cursor
					.getColumnIndex("CarTypeName"));
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}

	
	public List<String> findAllZhichaoEnforcementType() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype where LawEnforcementName='治超执法'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("LawItemID"))+":"+cursor.getString(cursor
					.getColumnIndex("LawItemName"));
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}

	public List<String> findAllLusunEnforcementType() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype  where LawEnforcementName='路损执法' ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("LawItemID"))+":"+cursor.getString(cursor
					.getColumnIndex("LawItemName"));
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}

	
}
