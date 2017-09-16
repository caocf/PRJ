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
    //���ø��๹����  
    public MySQLiteHelper(Context context, String name, CursorFactory factory,  
            int version) {  
        super(context, name, factory, version);  
    }  

	
	/* (�� Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �Զ����ɵķ������


		// ����û�п����������࣬�ȿ���һ������ռ�õĴ�С
		db.execSQL("drop table if exists mm_user");

		
        db.execSQL("create table if not exists mm_user("  
                + "id integer primary key," 
                + "userId varchar,"        		        		                
        		+ "name varchar,"
                + "tel varchar,"  
                + "departmentName varchar)"); 
        
   
		
		
 
		 
        
		/*       
        db.execSQL("insert into t_user values ('��',0,'��','��','��','0573119','��huojing119')");
        db.execSQL("insert into t_user values ('�˾�',0,'��','��','�˾�','0573110','�˾�feijing110')");
        db.execSQL("insert into t_user values ('��������',0,'��','��','��������','0573120','��������jijiuzhongxin120')");
        db.execSQL("insert into t_user values ('��ͨ����',0,'��','��','��ͨ����','0573122','��ͨ����jiaotongguz122')");
                
        db.execSQL("insert into t_user values ('�¹���',0,'��','��','�¹���','13761978101','�¹���chenguorong13761978101')");
        db.execSQL("insert into t_user values ('Ҧ�ȷ�',0,'��','��','Ҧ�ȷ�','18857380907','Ҧ�ȷ�yaoyifeng18857380907')");
        db.execSQL("insert into t_user values ('�ܽ�',0,'��','��','�ܽ�','15641295407','�ܽ�zhoujian15641295407')");
        db.execSQL("insert into t_user values ('�Ե�',1,'��','��','�Ե�','15547070605','�Ե�zhaodan15547070605')");
        db.execSQL("insert into t_user values ('�»��',1,'��','��','�»��','13357469764','�»��chenhuogen13357469764')");
        db.execSQL("insert into t_user values ('������',1,'��','��','������','05733544431','������jiangdongming05733544431')");
        db.execSQL("insert into t_user values (,'��С��',1,'��','��','��С��','18806970536','��С��xuxiaofang18806970536')");
        db.execSQL("insert into t_user values ('�򵤵�',1,'��','��','�򵤵�','15758996432','�򵤵�shendandan15758996432')");

        

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

        db.execSQL("insert into jacnamelist values (null,0,'��','0573119','��huojing119')");
        db.execSQL("insert into jacnamelist values (null,0,'�˾�','0573110','�˾�feijing110')");
        db.execSQL("insert into jacnamelist values (null,0,'��������','0573120','��������jijiuzhongxin120')");
        db.execSQL("insert into jacnamelist values (null,0,'��ͨ����','0573122','��ͨ����jiaotongguz122')");
                
        db.execSQL("insert into jacnamelist values (null,1,'�¹���','13761978101','�¹���chenguorong13761978101')");
        db.execSQL("insert into jacnamelist values (null,1,'Ҧ�ȷ�','18857380907','Ҧ�ȷ�yaoyifeng18857380907')");
        db.execSQL("insert into jacnamelist values (null,1,'�ܽ�','15641295407','�ܽ�zhoujian15641295407')");
        db.execSQL("insert into jacnamelist values (null,1,'�Ե�','15547070605','�Ե�zhaodan15547070605')");
        db.execSQL("insert into jacnamelist values (null,1,'�»��','13357469764','�»��chenhuogen13357469764')");
        db.execSQL("insert into jacnamelist values (null,1,'������','05733544431','������jiangdongming05733544431')");
        db.execSQL("insert into jacnamelist values (null,1,'��С��','18806970536','��С��xuxiaofang18806970536')");
        db.execSQL("insert into jacnamelist values (null,1,'�򵤵�','15758996432','�򵤵�shendandan15758996432')");

*/
	}

	/* (�� Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������

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
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype where LawEnforcementName='�γ�ִ��'";
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
		String sql = "SELECT LawItemID, LawItemName ,PartOfLawEnforcement FROM t_enforcementype  where LawEnforcementName='·��ִ��' ";
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
