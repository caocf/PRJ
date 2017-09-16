package com.huzhouport.pushmsg;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PushMsgDB extends SQLiteOpenHelper {
	private String TAG = "PushMsgDB";
	private static final String DB_NAME = "HuZhouPortPublicNotify1.db"; // 数据库名称
	private static final int version = 1; // 数据库版本

	public PushMsgDB(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CreateNotifyPushMsgTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		if (oldVersion != version) 
		{
			DeleteNotifyPushMsgTable(db);
			CreateNotifyPushMsgTable(db);
		}

	}

	// 建立推送消息表,一条消息唯一由msgbelong+messageid+modulerid决定
	private boolean CreateNotifyPushMsgTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			String sSql = "CREATE TABLE [j_PushMsg] ("
					+ "[id] INTEGER  NOT NULL PRIMARY KEY," // 自增主键
					+ "[msgbelong] INTEGER," // 消息所属用户
					+ "[pushmsgid] INTEGER," // 推送消息ID
					+ "[userid] INTEGER," // 用户ID 0-->推送所有的, -1-->推送内部,
											// -2-->推送外部, >0-->具体某用户.
					+ "[messageid] INTEGER," // 消息在各自模块中的外键ID
					+ "[modulerid] INTEGER," // 消息所属模块
					+ "[msgstatus] INTEGER," // 消息状态
					+ "[pushmsgtime] VARCHAR(50),"// 消息推送时间
					+ "[kindid] INTEGER)"; 
			db.execSQL(sSql);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;
	}

	public PushMsg findNotifyPushMsg(SQLiteDatabase db, int msgbelong,int pushmsgid)
	{		
		PushMsg msg = null;
		try {
			String sql = "select * from j_PushMsg where msgbelong=" + msgbelong
					+ " and pushmsgid=" + pushmsgid + " and modulerid="
					+ PushMsg.MODULERID_NOTIFY;
			Cursor oCursor = db.rawQuery(sql, null);
			if (oCursor.moveToFirst()) 
			{
				int id, userid, messageid, modulerid, msgstatus;
				String pushmsgtime;

				id = oCursor.getInt(oCursor.getColumnIndex("id"));
				userid = oCursor.getInt(oCursor.getColumnIndex("userid"));
				messageid = oCursor.getInt(oCursor.getColumnIndex("messageid"));
				modulerid = oCursor.getInt(oCursor.getColumnIndex("modulerid"));
				msgstatus = oCursor.getInt(oCursor.getColumnIndex("msgstatus"));
				pushmsgtime = oCursor.getString(oCursor
						.getColumnIndex("pushmsgtime"));
				msg = new PushMsg();
				msg.setId(id);
				msg.setMsgbelong(msgbelong);
				msg.setUserid(userid);
				msg.setPushmsgid(pushmsgid);
				msg.setMessageid(messageid);
				msg.setModulerid(modulerid);
				msg.setMsgstatus(msgstatus);
				msg.setPushmsgtime(pushmsgtime);
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return msg;
	}

	// 删除推送消息表
	public boolean DeleteNotifyPushMsgTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try 
		{
			db.delete("j_PushMsg", null, null);
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
		}
		return bFlag;
	}

	// 插入推送通知公告表
	public boolean addNotifyPushMsg(SQLiteDatabase db, PushMsg msg) {
		boolean bFlag = false;
		try {
			String sql = "insert into j_PushMsg(msgbelong,pushmsgid,userid,messageid,modulerid,msgstatus,pushmsgtime) values(?,?,?,?,?,?,?)";

			String pushmsgtime = msg.getPushmsgtime().replaceAll("T", " ");

			Object[] bindArgs = { msg.getMsgbelong(), msg.getPushmsgid(),
					msg.getUserid(), msg.getMessageid(), msg.getModulerid(),
					msg.getMsgstatus(), pushmsgtime };
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			Log.d(TAG, "addNotifyPushMsg失败");
			bFlag = false;
		}

		return bFlag;
	}
	public boolean addNotifyPushMsgNew(SQLiteDatabase db, NotifyPushMsg msg)
	{
		boolean bFlag = false;
		try {
			String sql = "insert into j_PushMsg(msgbelong,pushmsgid,userid,messageid,modulerid,msgstatus,pushmsgtime,kindid) values(?,?,?,?,?,?,?,?)";

			String pushmsgtime = msg.getPushmsgtime().replaceAll("T", " ");

			Object[] bindArgs = { msg.getMsgbelong(), msg.getPushmsgid(),
					msg.getUserid(), msg.getMessageid(), msg.getModulerid(),
					msg.getMsgstatus(), pushmsgtime ,msg.getPartofkind()};
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			Log.d(TAG, "addNotifyPushMsg失败");
			bFlag = false;
		}

		return bFlag;
	}
	
	//计算类别下未读数量
	public int CountUnReadByForderID(SQLiteDatabase db, int msgbelong,int forderid) 
	{
		int cnt = 0;
		try {
			String sql = "select * from j_PushMsg where (msgstatus="
					+ PushMsg.MSGSTATUS_NOTPUSH_NOTREAD + " or msgstatus="
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD + ") and msgbelong="
					+ msgbelong + " and modulerid=" + PushMsg.MODULERID_NOTIFY+" and kindid="+forderid;
			Cursor oCursor = db.rawQuery(sql, null);
			cnt = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return cnt;
	}
	
	public int allCount(SQLiteDatabase db,int forderid)
	{
		Cursor cursor=db.query("j_PushMsg", null,"kindid="+forderid,null, null, null, null);
		return cursor.getCount();
	}

	// 读取一条推送消息
	public void updateNotifyPushMsgStatusRead(SQLiteDatabase db, int msgbelong,
			int pushmsgid) {
		try {
			String sql = "update j_PushMsg set msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where msgbelong="
					+ msgbelong + " and pushmsgid=" + pushmsgid
					+ " and modulerid=" + PushMsg.MODULERID_NOTIFY;
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readNotifyPushMsgAll(SQLiteDatabase db, int msgbelong) {
		try {
			String sql = "update j_PushMsg set msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where msgbelong="
					+ msgbelong + " and modulerid=" + PushMsg.MODULERID_NOTIFY;
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询所属用户某模块的未读消息数量
	public int countUnreadNotifyPushMsg(SQLiteDatabase db, int msgbelong) {
		int cnt = 0;
		try {
			String sql = "select * from j_PushMsg where (msgstatus="
					+ PushMsg.MSGSTATUS_NOTPUSH_NOTREAD + " or msgstatus="
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD + ") and msgbelong="
					+ msgbelong + " and modulerid=" + PushMsg.MODULERID_NOTIFY;
			Cursor oCursor = db.rawQuery(sql, null);
			cnt = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return cnt;
	}

	// //////////////////////////////////////////////////////////
	public boolean addPushMsg(SQLiteDatabase db, PushMsg msg) {
		boolean bFlag = false;
		try {
			String sql = "insert into j_PushMsg(msgbelong,pushmsgid,userid,messageid,modulerid,msgstatus,pushmsgtime) values(?,?,?,?,?,?,?)";

			String pushmsgtime = msg.getPushmsgtime().replaceAll("T", " ");

			Object[] bindArgs = { msg.getMsgbelong(), msg.getPushmsgid(),
					msg.getUserid(), msg.getMessageid(), msg.getModulerid(),
					msg.getMsgstatus(), pushmsgtime };
			db.execSQL(sql, bindArgs);
			bFlag = true;
		} catch (Exception e) {
			Log.d(TAG, "addPushMsg失败");
			bFlag = false;
		}

		return bFlag;
	}

	public void updatePushMsgStatus(SQLiteDatabase db, PushMsg msg) {
		try {
			String sql = "update j_PushMsg set msgstatus = "
					+ msg.getMsgstatus() + " where id=" + msg.getId();
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PushMsg> findPushMsgs(SQLiteDatabase db, int msgbelong,
			int modulerid, int[] userids, int messageid) {
		List<PushMsg> msgs = new ArrayList<PushMsg>();
		try {
			String sql = "select * from j_PushMsg where msgbelong=" + msgbelong;

			sql += " and modulerid=" + modulerid;
			sql += " and messageid=" + messageid;
			if (userids.length == 1)
				sql += " and userid=" + userids[0];
			else if (userids.length > 1) {
				String uidsstr = "";
				for (int i = 0; i < userids.length; i++) {
					uidsstr += userids[i] + ",";
				}
				uidsstr = uidsstr.substring(0, uidsstr.length() - 1);
				sql += " and userid in (" + uidsstr + ")";
			} else
				return msgs;

			Cursor oCursor = db.rawQuery(sql, null);
			for (oCursor.moveToFirst(); !oCursor.isAfterLast(); oCursor
					.moveToNext()) {
				int id, userid, pushmsgid, msgstatus;
				String pushmsgtime;

				userid = oCursor.getInt(oCursor.getColumnIndex("userid"));
				id = oCursor.getInt(oCursor.getColumnIndex("id"));
				pushmsgid = oCursor.getInt(oCursor.getColumnIndex("pushmsgid"));
				msgstatus = oCursor.getInt(oCursor.getColumnIndex("msgstatus"));
				pushmsgtime = oCursor.getString(oCursor
						.getColumnIndex("pushmsgtime"));

				PushMsg msg = new PushMsg();
				msg.setId(id);
				msg.setMsgbelong(msgbelong);
				msg.setUserid(userid);
				msg.setPushmsgid(pushmsgid);
				msg.setMessageid(messageid);
				msg.setModulerid(modulerid);
				msg.setMsgstatus(msgstatus);
				msg.setPushmsgtime(pushmsgtime);
				msgs.add(msg);
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return msgs;
	}

	public int coundPushMsgsByStatus(SQLiteDatabase db, int msgbelong,
			int moduler, List<Integer> statuss) {
		int cnt = 0;
		try {
			String sql = "select * from j_PushMsg where msgbelong=" + msgbelong
					+ " and modulerid=" + moduler;

			if (statuss.size() > 0) {
				String statusstr = "";
				for (int i = 0; i < statuss.size(); i++) {
					statusstr += statuss.get(i) + ",";
				}
				statusstr = statusstr.substring(0, statusstr.length() - 1);
				sql += " and msgstatus in (" + statusstr + ")";
			} else {
				return 0;
			}

			Cursor oCursor = db.rawQuery(sql, null);
			cnt = oCursor.getCount();
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}

		return cnt;
	}

	public List<PushMsg> findPushMsgs(SQLiteDatabase db, int msgbelong,
			int pushmsgid) {
		List<PushMsg> msgs = new ArrayList<PushMsg>();
		try {
			String sql = "select * from j_PushMsg where msgbelong=" + msgbelong
					+ " and pushmsgid=" + pushmsgid;
			Cursor oCursor = db.rawQuery(sql, null);
			for (oCursor.moveToFirst(); !oCursor.isAfterLast(); oCursor
					.moveToNext()) {
				int id, userid, messageid, modulerid, msgstatus;
				String pushmsgtime;

				id = oCursor.getInt(oCursor.getColumnIndex("id"));
				userid = oCursor.getInt(oCursor.getColumnIndex("userid"));
				messageid = oCursor.getInt(oCursor.getColumnIndex("messageid"));
				modulerid = oCursor.getInt(oCursor.getColumnIndex("modulerid"));
				msgstatus = oCursor.getInt(oCursor.getColumnIndex("msgstatus"));
				pushmsgtime = oCursor.getString(oCursor
						.getColumnIndex("pushmsgtime"));

				PushMsg msg = new PushMsg();
				msg.setId(id);
				msg.setMsgbelong(msgbelong);
				msg.setUserid(userid);
				msg.setPushmsgid(pushmsgid);
				msg.setMessageid(messageid);
				msg.setModulerid(modulerid);
				msg.setMsgstatus(msgstatus);
				msg.setPushmsgtime(pushmsgtime);
				msgs.add(msg);
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return msgs;
	}

	public List<PushMsg> findPushMsgsAll(SQLiteDatabase db, int msgbelong,
			int modulerid) {
		List<PushMsg> msgs = new ArrayList<PushMsg>();
		try {
			String sql = "select * from j_PushMsg where msgbelong=" + msgbelong
					+ " and modulerid=" + modulerid;
			Cursor oCursor = db.rawQuery(sql, null);
			for (oCursor.moveToFirst(); !oCursor.isAfterLast(); oCursor
					.moveToNext()) {
				int id, userid, messageid, msgstatus, pushmsgid;
				String pushmsgtime;

				id = oCursor.getInt(oCursor.getColumnIndex("id"));
				userid = oCursor.getInt(oCursor.getColumnIndex("userid"));
				messageid = oCursor.getInt(oCursor.getColumnIndex("messageid"));
				msgstatus = oCursor.getInt(oCursor.getColumnIndex("msgstatus"));
				pushmsgid = oCursor.getInt(oCursor.getColumnIndex("pushmsgid"));
				pushmsgtime = oCursor.getString(oCursor
						.getColumnIndex("pushmsgtime"));

				PushMsg msg = new PushMsg();
				msg.setId(id);
				msg.setMsgbelong(msgbelong);
				msg.setUserid(userid);
				msg.setPushmsgid(pushmsgid);
				msg.setMessageid(messageid);
				msg.setModulerid(modulerid);
				msg.setMsgstatus(msgstatus);
				msg.setPushmsgtime(pushmsgtime);
				msgs.add(msg);
			}
			if (oCursor != null)
				oCursor.close();
		} catch (Exception e) {
		}
		return msgs;
	}
}
