package com.huzhouport.common.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String TAG = "MySQLiteHelper";
	private static final String DB_NAME = "HuZhouPort.db"; // 数据库名称
	private static final int version = 4; // 数据库版本

	public MySQLiteHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		boolean bFlag = true;
		bFlag &= CreateHostUserTable(db); // 登陆用户表
		bFlag &= CreateScheduleTable(db); // 创建日程表
		bFlag &= CreatePushMsgTable(db); // 创建推送信息表

		if (!bFlag) {
			Log.i(TAG, "创建数据库失败");
		}
	}

	// 修改表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if (oldVersion < 3) {
			boolean bFlag = true;
			bFlag &= CreatePushMsgTable(db); // 创建推送信息表
			if (!bFlag) {
				Log.i(TAG, "创建数据库失败");
			}
		}
	}

	// 登陆用户表
	public boolean CreateHostUserTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			String sSql = "CREATE TABLE [j_User] ([UserID] VARCHAR(20)  NOT NULL PRIMARY KEY,[UserName] "
					+ "VARCHAR(50),[Name] VARCHAR(50),[Password] VARCHAR(50),[PartOfDepartment] INTEGER ,"
					+ "[PartOfRole] INTEGER,[Tel] VARCHAR(50),[Email] VARCHAR(50),[LawID] VARCHAR(50),"
					+ "[PartOfPost] INTEGER,[UserStatus] INTEGER)";
			db.execSQL(sSql);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 增加登陆用户
	public boolean InsertHostUser(SQLiteDatabase db, Object[] bindArgs) {
		boolean bFlag = false;
		try {
			String sql = "insert into j_User(UserID,UserName,Name) values(?,?,?)";
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 删除登陆用户
	public boolean DeleteHostUser(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			db.delete("j_User", null, null);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// Grond add Start
	// 建立推送通知公告表
	public boolean CreatePushMsgTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			String sSql = "CREATE TABLE [j_PushMsg] ([ID] INTEGER  NOT NULL PRIMARY KEY,"
					+ "[userid] INTEGER,[pushMsgID] INTEGER, [messageid] INTEGER, [msgstatus] INTEGER,[pushmsgtime] VARCHAR(50))";
			db.execSQL(sSql);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 插入推送通知公告表
	public boolean InsertPushMsg(SQLiteDatabase db, int userid, int pushMsgID,
			int messageid, int msgstatus, String pushmsgtime) {
		boolean bFlag = false;
		try {
			String sql = "insert into j_PushMsg(userid,pushMsgID,messageid,msgstatus,pushmsgtime) values(?,?,?,?,?)";
			Object[] bindArgs = { userid, pushMsgID, messageid, msgstatus,
					pushmsgtime };
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 查询推送通知公告表
	public int SearchPushedMsgById(SQLiteDatabase db, int userid, int pushMsgID) {
		int ScheduleList = 0;
		try {
			String sql = "select * from j_PushMsg where pushMsgID=" + pushMsgID
					+ " and userid=" + userid;
			Cursor oCursor = db.rawQuery(sql, null);
			ScheduleList = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}

		return ScheduleList;

	}

	// 查询推送通知公告表
	public int getPushedMsgCntBymessageId(SQLiteDatabase db, int userid,
			int messageid) {
		int cnt = 0;
		try {
			String sql = "select * from j_PushMsg where msgstatus = 0 and messageid="
					+ messageid + " and userid=" + userid;
			Cursor oCursor = db.rawQuery(sql, null);
			cnt = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}

		return cnt;

	}

	// 修改为已读
	public void setPushedMsgReadBymessageId(SQLiteDatabase db, int userid,
			int messageid) {
		try {
			String sql = "update j_PushMsg set msgstatus = 1 where messageid="
					+ messageid + " and userid=" + userid;
			db.execSQL(sql);
		} catch (Exception e) {
		}
	}

	public String getpushMsgTime(SQLiteDatabase db, int userid, int messageid) {
		String pushmsgtime = "";
		try {
			String sql = "select * from j_PushMsg where messageid=" + messageid
					+ " and userid=" + userid;
			Cursor oCursor = db.rawQuery(sql, null);
			if (oCursor.moveToFirst()) {
				pushmsgtime = oCursor.getString(oCursor
						.getColumnIndex("pushmsgtime"));
				pushmsgtime = pushmsgtime.replaceAll("T", " ");
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return pushmsgtime;
	}

	// Grond add Ended

	// 日程表
	public boolean CreateScheduleTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			String sSql = "CREATE TABLE [j_Schedule] ([EventID] INTEGER  NOT NULL PRIMARY KEY,"
					+ "[RemindTime] VARCHAR(100),[EventName] VARCHAR(50),[EventRemindType] INTEGER)";
			db.execSQL(sSql);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 查询全部日程
	public int SearchHostUser(SQLiteDatabase db) {
		int UserId = 0;
		try {
			Cursor oCursor = db.rawQuery("j_User", null);
			if (oCursor.moveToFirst()) {
				UserId = oCursor.getInt(oCursor.getColumnIndex("UserID"));
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return UserId;

	}

	// 查询全部日程
	public int SearchScheduleById(SQLiteDatabase db, int EventID) {
		int ScheduleList = 0;
		try {
			String sql = "select * from j_Schedule where EventID=" + EventID;
			Cursor oCursor = db.rawQuery(sql, null);
			ScheduleList = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}

		return ScheduleList;

	}

	// 增加日程
	public boolean InsertSchedule(SQLiteDatabase db, Object[] bindArgs) {
		boolean bFlag = false;
		try {
			String sql = "insert into j_Schedule(EventID,RemindTime,EventName,EventRemindType) values(?,?,?,?)";
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 删除全部日程
	public boolean DeleteSchedule(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			db.delete("j_Schedule", null, null);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 修改日程
	public boolean UpdateScheduleTable(SQLiteDatabase db, Object[] bindArgs) {
		boolean bFlag = false;
		try {
			String sql = "update j_Schedule set RemindTime='" + bindArgs[1]
					+ "',EventName='" + bindArgs[2] + "',EventRemindType="
					+ bindArgs[3] + " where EventID=" + bindArgs[1];
			db.execSQL(sql);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;

	}

	// 查询全部日程
	public List<Map<String, Object>> SearchSchedule(SQLiteDatabase db) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String sqlString = "select * from j_Schedule";
			Cursor oCursor = db.rawQuery(sqlString, null);
			if (oCursor.getCount() != 0) {

				if (oCursor.moveToFirst()) {
					for (int i = 0; i < oCursor.getCount(); i++) {
						Map<String, Object> param = new HashMap<String, Object>();
						oCursor.moveToPosition(i);
						param.put("EventID", oCursor.getInt(oCursor
								.getColumnIndex("EventID")));
						param.put("RemindTime", oCursor.getString(oCursor
								.getColumnIndex("RemindTime")));
						param.put("EventName", oCursor.getString(oCursor
								.getColumnIndex("EventName")));
						param.put("EventRemindType", oCursor.getInt(oCursor
								.getColumnIndex("EventRemindType")));
						list.add(param);
					}
				}
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/*
	 * // setip表 public boolean CreateSetIPTable(SQLiteDatabase db) { boolean
	 * bFlag = false; try { String sSql =
	 * "CREATE TABLE [j_SetIP] ([SetID] VARCHAR(20)  NOT NULL PRIMARY KEY,[SetIP] "
	 * + "VARCHAR(50),[SetPort] VARCHAR(50))"; db.execSQL(sSql); bFlag = true; }
	 * catch (Exception e) { bFlag = false; } return bFlag;
	 * 
	 * }
	 */

}
