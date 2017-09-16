/**
 * 
 */
package com.huzhouport.addresslist;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author admin
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    //µ÷ÓÃ¸¸Àà¹¹ÔìÆ÷  
    public MySQLiteHelper(Context context, String name, CursorFactory factory,  
            int version) {  
        super(context, name, factory, version);  
    }  

	
	/* (·Ç Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO ×Ô¶¯Éú³ÉµÄ·½·¨´æ¸ù


		// ÕâÀïÃ»ÓÐ¿¼ÂÇÊý¾ÝÈßÓà£¬ÏÈ¿´¿´Ò»¸ö±íËùÕ¼ÓÃµÄ´óÐ¡
		db.execSQL("drop table if exists mm_user");

		
        db.execSQL("create table if not exists mm_user("  
                + "id integer primary key," 
                + "userId varchar,"        		        		                
        		+ "name varchar,"
                + "tel varchar,"  
                + "departmentName varchar)"); 
        
   
		
		
 
		 
        
		/*       
        db.execSQL("insert into t_user values ('»ð¾¯',0,'»ð¾¯','»ð¾¯','»ð¾¯','0573119','»ð¾¯huojing119')");
        db.execSQL("insert into t_user values ('·Ë¾¯',0,'»ð¾¯','»ð¾¯','·Ë¾¯','0573110','·Ë¾¯feijing110')");
        db.execSQL("insert into t_user values ('¼±¾ÈÖÐÐÄ',0,'»ð¾¯','»ð¾¯','¼±¾ÈÖÐÐÄ','0573120','¼±¾ÈÖÐÐÄjijiuzhongxin120')");
        db.execSQL("insert into t_user values ('½»Í¨¹ÊÕÏ',0,'»ð¾¯','»ð¾¯','½»Í¨¹ÊÕÏ','0573122','½»Í¨¹ÊÕÏjiaotongguz122')");
                
        db.execSQL("insert into t_user values ('³Â¹úÈÜ',0,'»ð¾¯','»ð¾¯','³Â¹úÈÜ','13761978101','³Â¹úÈÜchenguorong13761978101')");
        db.execSQL("insert into t_user values ('Ò¦ÞÈ·æ',0,'»ð¾¯','»ð¾¯','Ò¦ÞÈ·æ','18857380907','Ò¦ÞÈ·æyaoyifeng18857380907')");
        db.execSQL("insert into t_user values ('ÖÜ½£',0,'»ð¾¯','»ð¾¯','ÖÜ½£','15641295407','ÖÜ½£zhoujian15641295407')");
        db.execSQL("insert into t_user values ('ÕÔµ¤',1,'»ð¾¯','»ð¾¯','ÕÔµ¤','15547070605','ÕÔµ¤zhaodan15547070605')");
        db.execSQL("insert into t_user values ('³Â»ð¸ù',1,'»ð¾¯','»ð¾¯','³Â»ð¸ù','13357469764','³Â»ð¸ùchenhuogen13357469764')");
        db.execSQL("insert into t_user values ('½ª¶«Ã÷',1,'»ð¾¯','»ð¾¯','½ª¶«Ã÷','05733544431','½ª¶«Ã÷jiangdongming05733544431')");
        db.execSQL("insert into t_user values (,'ÐíÐ¡·¼',1,'»ð¾¯','»ð¾¯','ÐíÐ¡·¼','18806970536','ÐíÐ¡·¼xuxiaofang18806970536')");
        db.execSQL("insert into t_user values ('Éòµ¤µ¤',1,'»ð¾¯','»ð¾¯','Éòµ¤µ¤','15758996432','Éòµ¤µ¤shendandan15758996432')");

        

		db.execSQL("drop table if exists jacnamelist");
		
		db.execSQL("drop table if exists t_user");
		
		
        db.execSQL("create table if not exists t_user("  
                + "id integer primary key,"
        		+ "name varchar,"
                + "username varchar,"  
                + "password varchar,"
                + "Tel varchar,"	
                + "LawID varchar,"		
                + "DepartmentName varchar,"
                + "finditems  varchar)"); 
        
        //db.execSQL("alter table jacnamelist add column finditems varchar");

        db.execSQL("insert into jacnamelist values (null,0,'»ð¾¯','0573119','»ð¾¯huojing119')");
        db.execSQL("insert into jacnamelist values (null,0,'·Ë¾¯','0573110','·Ë¾¯feijing110')");
        db.execSQL("insert into jacnamelist values (null,0,'¼±¾ÈÖÐÐÄ','0573120','¼±¾ÈÖÐÐÄjijiuzhongxin120')");
        db.execSQL("insert into jacnamelist values (null,0,'½»Í¨¹ÊÕÏ','0573122','½»Í¨¹ÊÕÏjiaotongguz122')");
                
        db.execSQL("insert into jacnamelist values (null,1,'³Â¹úÈÜ','13761978101','³Â¹úÈÜchenguorong13761978101')");
        db.execSQL("insert into jacnamelist values (null,1,'Ò¦ÞÈ·æ','18857380907','Ò¦ÞÈ·æyaoyifeng18857380907')");
        db.execSQL("insert into jacnamelist values (null,1,'ÖÜ½£','15641295407','ÖÜ½£zhoujian15641295407')");
        db.execSQL("insert into jacnamelist values (null,1,'ÕÔµ¤','15547070605','ÕÔµ¤zhaodan15547070605')");
        db.execSQL("insert into jacnamelist values (null,1,'³Â»ð¸ù','13357469764','³Â»ð¸ùchenhuogen13357469764')");
        db.execSQL("insert into jacnamelist values (null,1,'½ª¶«Ã÷','05733544431','½ª¶«Ã÷jiangdongming05733544431')");
        db.execSQL("insert into jacnamelist values (null,1,'ÐíÐ¡·¼','18806970536','ÐíÐ¡·¼xuxiaofang18806970536')");
        db.execSQL("insert into jacnamelist values (null,1,'Éòµ¤µ¤','15758996432','Éòµ¤µ¤shendandan15758996432')");

*/
	}

	/* (·Ç Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO ×Ô¶¯Éú³ÉµÄ·½·¨´æ¸ù

	}
/*
	public int usercount() {
		int rows = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("cities", new String[] { "city_id",
				"city_name", "longitude", "latitude", "province_name",
				"country_name" }, null, null, null, null, null);
		if (cursor.moveToNext()) {
			rows = cursor.getCount();
		}
		if (cursor != null)
			cursor.close();
		return rows;
	}
*/	
	public List<String> findAllUser() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
		String sql = "SELECT * LawID FROM mm_user where DepartmentName=1 ";
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
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
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
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype where LawEnforcementName='ÖÎ³¬Ö´·¨'";
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
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype  where LawEnforcementName='Â·ËðÖ´·¨' ";
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
	
/*
	public int cartypecount() {
		int rows = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("cities", new String[] { "city_id",
				"city_name", "longitude", "latitude", "province_name",
				"country_name" }, null, null, null, null, null);
		if (cursor.moveToNext()) {
			rows = cursor.getCount();
		}
		if (cursor != null)
			cursor.close();
		return rows;
	}	
	public List<String> findAllCarType() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
		String sql = "SELECT CarTypeID CarTypeName FROM t_cartype ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("province_name"));
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}

	public int enforcementtypecount() {
		int rows = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("cities", new String[] { "city_id",
				"city_name", "longitude", "latitude", "province_name",
				"country_name" }, null, null, null, null, null);
		if (cursor.moveToNext()) {
			rows = cursor.getCount();
		}
		if (cursor != null)
			cursor.close();
		return rows;
	}	
	
	public List<String> findAllEnforcementType() {
		List<String> provs = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		// Cursor cursor = db.query("cities",
		// new String[] { "province_name" }, null, null,
		// "province_name", null, "province_name");
		String sql = "SELECT LawItemID LawItemName PartOfLawEnforcement FROM t_enforcementype ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String prov = cursor.getString(cursor
					.getColumnIndex("province_name"));
			provs.add(prov);
		}
		if (cursor != null)
			cursor.close();
		return provs;
	}

	
	public List<String> findCitiesByProv(String provName)
			throws UnsupportedEncodingException {
		List<String> cities = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("t_user", new String[] { "name" },
				"name=?",
				new String[] { new String(provName.getBytes(), "UTF-8") },
				null, null, null);
		while (cursor.moveToNext()) {
			cities.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		if (cursor != null)
			cursor.close();
		return cities;
	}
	*/
	
}
