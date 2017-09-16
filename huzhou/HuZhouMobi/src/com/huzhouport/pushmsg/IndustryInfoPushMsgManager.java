package com.huzhouport.pushmsg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.common.util.Query;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 行业资讯推送消息
 * 
 * @author DJ
 * 
 */
public class IndustryInfoPushMsgManager
{
	private static String TAG = "IndustryInfoPushMsgManager";
	private Context context;
	private static int MODULER = PushMsg.MODULERID_INDUSTRYINFO;
	private static int[] USERIDS = { PushMsg.USERID_BOTH, PushMsg.USERID_INNER };
	private static String USERIDSSTR = "" + PushMsg.USERID_BOTH + ","
			+ PushMsg.USERID_INNER;

	public IndustryInfoPushMsgManager(Context context)
	{
		this.context = context;
	}

	/**
	 * 添加行业资讯消息到本地数据库
	 * 
	 * @param msgbelong
	 * @param msg
	 */
	private void addPushedMsg(int msgbelong, PushMsg msg)
	{

		try
		{
			PushMsgDB pushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = pushMsgDB.getWritableDatabase();

			List<PushMsg> msgs = pushMsgDB.findPushMsgs(db, msgbelong, MODULER,
					USERIDS, msg.getMessageid());

			if (msgs.size() > 0)
			{
				Log.d(TAG, "数据已存在： " + msg.getPushmsgid());
				return;
			} else
			{
				msg.setMsgbelong(msgbelong);
				pushMsgDB.addPushMsg(db, msg);
				Log.d(TAG, "数据添加成功： " + msg.getPushmsgid());
			}

			if (db != null)
				db.close();
		} catch (Exception e)
		{
		}
	}

	/**
	 * 查找某用户的行业资讯推送消息
	 * 
	 * @return
	 */
	public List<PushMsg> findPushedMsg(int msgbelong, int messageid)
	{

		try
		{
			PushMsgDB pushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = pushMsgDB.getWritableDatabase();

			List<PushMsg> msgs = pushMsgDB.findPushMsgs(db, msgbelong, MODULER,
					USERIDS, messageid);

			if (db != null)
				db.close();
			return msgs;
		} catch (Exception e)
		{
			return new ArrayList<PushMsg>();
		}
	}

	/**
	 * 将未推送消息推送入本地数据库 供 后台服务 及 页面列表加载时调用
	 */
	public void pushedUnPushedMsg(int msgbelong, List<PushMsg> msgs)
	{
		for (int i = 0; i < msgs.size(); i++)
		{
			this.addPushedMsg(msgbelong, msgs.get(i));
		}
	}

	public int pullUnPushedMsg(int msgbelong)
	{

		try
		{
			PushMsgDB pushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = pushMsgDB.getWritableDatabase();
			List<PushMsg> msgs = pushMsgDB.findPushMsgsAll(db, msgbelong,
					MODULER);
			if (db != null)
				db.close();

			String messageids = "";
			for (int i = 0; i < msgs.size(); i++)
			{
				messageids += "" + msgs.get(i).getMessageid() + ",";
			}
			if (!messageids.equals(""))
				messageids = messageids.substring(0, messageids.length() - 1);

			String result = new Query().showIndustryInfoExclude(USERIDSSTR,
					messageids);

			Log.e(TAG, result);

			List<PushMsg> pushMsgs = new ArrayList<PushMsg>();
			try
			{
				// 解析获得根目录下该用户的面向用户的未读消息数量
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

					int modulerid = jsonObject.getInt("modulerid");
					if (modulerid == NotifyPushMsg.MODULERID_INDUSTRYINFO)
					{
						PushMsg pushMsg = new PushMsg();
						pushMsg.setMessageid(jsonObject.getInt("messageid"));
						pushMsg.setModulerid(jsonObject.getInt("modulerid"));
						pushMsg.setMsgbelong(msgbelong);
						pushMsg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
						pushMsg.setPushmsgid(jsonObject.getInt("pushmsgid"));
						pushMsg.setPushmsgtime(jsonObject.getString(
								"pushmsgtime").replaceAll("T", " "));
						pushMsg.setUserid(jsonObject.getInt("userid"));

						pushMsgs.add(pushMsg);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			for (int i = 0; i < pushMsgs.size(); i++)
			{
				addPushedMsg(msgbelong, pushMsgs.get(i));
			}

			return pushMsgs.size();
		} catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读 供后台推送服务调用
	 */
	public int getUnPushedMsg(int msgbelong)
	{
		int cnt = 0;

		/**
		 * 从服务器加载本地数据库没有的消息
		 */
		cnt = pullUnPushedMsg(msgbelong);

		return cnt;
	}

	/**
	 * 获得已推送未读消息数量 供9宫格调用显示是否有未读消息
	 */
	public int getPushedUnReadMsgCnt(int msgbelong)
	{
		int cnt = 0;
		PushMsgDB pushMsgDB = new PushMsgDB(context);
		SQLiteDatabase db = pushMsgDB.getWritableDatabase();

		List<Integer> statuss = new ArrayList<Integer>();
		statuss.add(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
		cnt = pushMsgDB.coundPushMsgsByStatus(db, msgbelong, MODULER, statuss);
		Log.d(TAG, "未读消息数量为：" + cnt);
		if (db != null)
			db.close();
		return cnt;
	}

	public void readPushMsg(int msgbelong, int messageid)
	{
		PushMsgDB pushMsgDB = new PushMsgDB(context);
		SQLiteDatabase db = pushMsgDB.getWritableDatabase();

		List<PushMsg> msgs = pushMsgDB.findPushMsgs(db, msgbelong, MODULER,
				USERIDS, messageid);

		if (msgs != null && msgs.size() > 0)
		{
			PushMsg msg = msgs.get(0);

			msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_READ);
			pushMsgDB.updatePushMsgStatus(db, msg);
			Log.d(TAG, "读取推送消息： " + msg.getPushmsgid());
		}

		if (db != null)
			db.close();
	}
}
