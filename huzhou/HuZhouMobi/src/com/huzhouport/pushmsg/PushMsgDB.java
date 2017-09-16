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
	private static final String DB_NAME = "HuZhouPortPublicNotify1.db"; // ���ݿ�����
	private static final int version = 1; // ���ݿ�汾

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

	// ����������Ϣ��,һ����ϢΨһ��msgbelong+messageid+modulerid����
	private boolean CreateNotifyPushMsgTable(SQLiteDatabase db) {
		boolean bFlag = false;
		try {
			String sSql = "CREATE TABLE [j_PushMsg] ("
					+ "[id] INTEGER  NOT NULL PRIMARY KEY," // ��������
					+ "[msgbelong] INTEGER," // ��Ϣ�����û�
					+ "[pushmsgid] INTEGER," // ������ϢID
					+ "[userid] INTEGER," // �û�ID 0-->�������е�, -1-->�����ڲ�,
											// -2-->�����ⲿ, >0-->����ĳ�û�.
					+ "[messageid] INTEGER," // ��Ϣ�ڸ���ģ���е����ID
					+ "[modulerid] INTEGER," // ��Ϣ����ģ��
					+ "[msgstatus] INTEGER," // ��Ϣ״̬
					+ "[pushmsgtime] VARCHAR(50),"// ��Ϣ����ʱ��
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

	// ɾ��������Ϣ��
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

	// ��������֪ͨ�����
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
			Log.d(TAG, "addNotifyPushMsgʧ��");
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
			Log.d(TAG, "addNotifyPushMsgʧ��");
			bFlag = false;
		}

		return bFlag;
	}
	
	//���������δ������
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

	// ��ȡһ��������Ϣ
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

	// ��ѯ�����û�ĳģ���δ����Ϣ����
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
			Log.d(TAG, "addPushMsgʧ��");
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
