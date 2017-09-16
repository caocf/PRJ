package com.huzhouport.pushmsg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

/**
 * 通知公告信息管理
 * 
 * @author DongJun
 * 
 */
public class NotifyPushMsgManger {
	private static String TAG = "NotifyPushMsgManger";
	private Context context;
	private static String ORI = "inner";

	public NotifyPushMsgManger(Context context) {
		this.context = context;
	}

	private static void updatepushmsgstatus(int pushmsgid, int newstatus) {
		String url = HttpUtil.BASE_URL
				+ "Updatepushmsgstatus?pushMsg.pushmsgid=" + pushmsgid
				+ "&pushMsg.msgstatus=" + newstatus;
		HttpUtil.queryStringForPost(url);
	}

	private static void readallpushmsgs(int userid, int modulid) {
		String url = HttpUtil.BASE_URL + "addAllMessageToReaded?userid="
				+ userid + "&moduleid=" + modulid;
		HttpUtil.queryStringForPost(url);
	}

	/**
	 * 搜索通知信息
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static List<NotifyPushMsg> queryNotifyMsgListAllByContent(
			String ori, int userid, int folderid, String content) {
		List<NotifyPushMsg> notifyPushMsgs = new ArrayList<NotifyPushMsg>();
		String url = HttpUtil.BASE_URL + "queryNotifyMsgListAllByContent?ori="
				+ ori + "&folderid=" + folderid + "&userid=" + userid
				+ "&content=" + content;
		String result = HttpUtil.queryStringForPost(url);
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("result");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				int modulerid = jsonObject.getInt("modulerid");
				if (modulerid == NotifyPushMsg.MODULERID_NOTIFY) {
					NotifyPushMsg pushMsg = new NotifyPushMsg();
					pushMsg.setMessageid(jsonObject.getInt("messageid"));
					pushMsg.setModulerid(jsonObject.getInt("modulerid"));
					pushMsg.setMsgbelong(userid);
					pushMsg.setMsgstatus(jsonObject.getInt("msgstatus"));
					pushMsg.setPushmsgid(jsonObject.getInt("pushmsgid"));
					pushMsg.setPushmsgtime(jsonObject.getString("pushmsgtime")
							.replaceAll("T", " "));
					pushMsg.setUserid(jsonObject.getInt("userid"));

					pushMsg.setKnowledgeid(jsonObject.getInt("knowledgeID"));
					pushMsg.setKnowledgename(jsonObject
							.getString("knowledgeName"));
					pushMsg.setKnowledgeindex(jsonObject
							.getString("knowledgeIndex"));
					pushMsg.setPartofkind(jsonObject.getInt("partOfKind"));
					pushMsg.setIslink(jsonObject.getInt("isLink"));
					pushMsg.setIslinkinfo(jsonObject.getInt("isLinkInfo"));
					pushMsg.setKnowledgecontent(jsonObject
							.getString("knowledgeContent"));

					notifyPushMsgs.add(pushMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyPushMsgs;
	}

	/**
	 * 获得某目录下某用户的所有通知公告消息，不包括子目录
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static List<NotifyPushMsg> queryNotifyMsgList(String ori,
			int userid, int folderid) {
		List<NotifyPushMsg> notifyPushMsgs = new ArrayList<NotifyPushMsg>();

		String url = HttpUtil.BASE_URL + "queryNotifyMsgList?ori=" + ori
				+ "&userid=" + userid + "&folderid=" + folderid;
		String result = HttpUtil.queryStringForPost(url);

		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("result");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				int modulerid = jsonObject.getInt("modulerid");
				if (modulerid == NotifyPushMsg.MODULERID_NOTIFY) {
					NotifyPushMsg pushMsg = new NotifyPushMsg();
					pushMsg.setMessageid(jsonObject.getInt("messageid"));
					pushMsg.setModulerid(jsonObject.getInt("modulerid"));
					pushMsg.setMsgbelong(userid);
					pushMsg.setMsgstatus(jsonObject.getInt("msgstatus"));
					pushMsg.setPushmsgid(jsonObject.getInt("pushmsgid"));
					pushMsg.setPushmsgtime(jsonObject.getString("pushmsgtime")
							.replaceAll("T", " "));
					pushMsg.setUserid(jsonObject.getInt("userid"));

					pushMsg.setKnowledgeid(jsonObject.getInt("knowledgeID"));
					pushMsg.setKnowledgename(jsonObject
							.getString("knowledgeName"));
					pushMsg.setKnowledgeindex(jsonObject
							.getString("knowledgeIndex"));
					pushMsg.setPartofkind(jsonObject.getInt("partOfKind"));
					pushMsg.setIslink(jsonObject.getInt("isLink"));
					pushMsg.setIslinkinfo(jsonObject.getInt("isLinkInfo"));
					pushMsg.setKnowledgecontent(jsonObject
							.getString("knowledgeContent"));

					notifyPushMsgs.add(pushMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyPushMsgs;
	}

	/**
	 * 获得某目录下所有非面向用户的通知公告消息，包括子目录
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static List<NotifyPushMsg> queryNonUserOriNotifyMsgAll(String ori,int userid, int folderid)
	{
		List<NotifyPushMsg> notifyPushMsgs = new ArrayList<NotifyPushMsg>();
		String url = HttpUtil.BASE_URL + "queryNonUserOriNotifyMsgAll?ori="
				+ ori + "&folderid=" + folderid;
		String result = HttpUtil.queryStringForPost(url);
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("result");

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				int modulerid = jsonObject.getInt("modulerid");
				if (modulerid == NotifyPushMsg.MODULERID_NOTIFY) {
					NotifyPushMsg pushMsg = new NotifyPushMsg();
					pushMsg.setMessageid(jsonObject.getInt("messageid"));
					pushMsg.setModulerid(jsonObject.getInt("modulerid"));
					pushMsg.setMsgbelong(userid);
					pushMsg.setMsgstatus(jsonObject.getInt("msgstatus"));
					pushMsg.setPushmsgid(jsonObject.getInt("pushmsgid"));
					pushMsg.setPushmsgtime(jsonObject.getString("pushmsgtime")
							.replaceAll("T", " "));
					pushMsg.setUserid(jsonObject.getInt("userid"));

					pushMsg.setKnowledgeid(jsonObject.getInt("knowledgeID"));
					pushMsg.setKnowledgename(jsonObject
							.getString("knowledgeName"));
					pushMsg.setKnowledgeindex(jsonObject
							.getString("knowledgeIndex"));
					pushMsg.setPartofkind(jsonObject.getInt("partOfKind"));
					pushMsg.setIslink(jsonObject.getInt("isLink"));
					pushMsg.setIslinkinfo(jsonObject.getInt("isLinkInfo"));
					pushMsg.setKnowledgecontent(jsonObject
							.getString("knowledgeContent"));

					notifyPushMsgs.add(pushMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyPushMsgs;
	}

	/**
	 * 获得某目录下某用户的所有通知公告消息，包括子目录
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static List<NotifyPushMsg> queryNotifyMsgListAll(String ori,
			int userid, int folderid) {
		List<NotifyPushMsg> notifyPushMsgs = new ArrayList<NotifyPushMsg>();
		String url = HttpUtil.BASE_URL + "queryNotifyMsgListAll?ori=" + ori
				+ "&userid=" + userid + "&folderid=" + folderid;
		String result = HttpUtil.queryStringForPost(url);
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("result");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				int modulerid = jsonObject.getInt("modulerid");
				if (modulerid == NotifyPushMsg.MODULERID_NOTIFY) {
					NotifyPushMsg pushMsg = new NotifyPushMsg();
					pushMsg.setMessageid(jsonObject.getInt("messageid"));
					pushMsg.setModulerid(jsonObject.getInt("modulerid"));
					pushMsg.setMsgbelong(userid);
					pushMsg.setMsgstatus(jsonObject.getInt("msgstatus"));
					pushMsg.setPushmsgid(jsonObject.getInt("pushmsgid"));
					pushMsg.setPushmsgtime(jsonObject.getString("pushmsgtime")
							.replaceAll("T", " "));
					pushMsg.setUserid(jsonObject.getInt("userid"));

					pushMsg.setKnowledgeid(jsonObject.getInt("knowledgeID"));
					pushMsg.setKnowledgename(jsonObject
							.getString("knowledgeName"));
					pushMsg.setKnowledgeindex(jsonObject
							.getString("knowledgeIndex"));
					pushMsg.setPartofkind(jsonObject.getInt("partOfKind"));
					pushMsg.setIslink(jsonObject.getInt("isLink"));
					pushMsg.setIslinkinfo(jsonObject.getInt("isLinkInfo"));
					pushMsg.setKnowledgecontent(jsonObject
							.getString("knowledgeContent"));

					notifyPushMsgs.add(pushMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyPushMsgs;
	}

	/**
	 * 获得目录结构
	 * 
	 * @param folderid
	 * @return
	 */
	private static List<NotifyPushMsgFolder> queryFolderStructure(int folderid) {
		List<NotifyPushMsgFolder> folders = new ArrayList<NotifyPushMsgFolder>();
		String url = HttpUtil.BASE_URL + "queryFolderStructure?folderid="
				+ folderid;
		String result = HttpUtil.queryStringForPost(url);
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("knowledgeKinds");

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				int id;
				String name;
				int parentid;

				id = jsonObject.getInt("kindID");
				name = jsonObject.getString("kindName");
				parentid = jsonObject.getInt("kindIndex");
				NotifyPushMsgFolder folder = new NotifyPushMsgFolder();
				folder.setId(id);
				folder.setName(name);
				folder.setParentid(parentid);
				folders.add(folder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folders;
	}

	/**
	 * 获得目录下面向用户的未读消息数量
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static int queryUserOriUnreadNotifyCnt(String ori, int userid,int folderid)
	{
		int cnt = 0;

		String url = HttpUtil.BASE_URL + "queryUserOriUnreadNotifyCnt?userid="+ userid + "&folderid=" + folderid + "&ori=" + ori;
		String result = HttpUtil.queryStringForPost(url);

		try
		{
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();

			cnt = data.getInt("unreadcnt");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * 获得目录下面向用户的未读消息数量
	 * 
	 * @param ori
	 * @param userid
	 * @param folderid
	 * @return
	 */
	private static int queryUserOriUnreadAndUnPushNotifyCnt(String ori,
			int userid, int folderid) {
		int cnt = 0;

		String url = HttpUtil.BASE_URL
				+ "queryUserOriUnreadAndUnPushNotifyCnt?userid=" + userid
				+ "&folderid=" + folderid + "&ori=" + ori;
		String result = HttpUtil.queryStringForPost(url);

		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();

			cnt = data.getInt("unreadcnt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * 获得用户 所有非面向用户的通知消息列表
	 * 
	 * @param ori
	 * @param userid
	 * @return
	 */
	private static List<NotifyPushMsg> queryNonUserOriNotifyListAll(String ori,
			int userid) {
		List<NotifyPushMsg> notifyPushMsgs = new ArrayList<NotifyPushMsg>();

		String url = HttpUtil.BASE_URL + "queryNonUserOriNotifyListAll?ori="
				+ ori + "&userid=" + userid;
		String result = HttpUtil.queryStringForPost(url);
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("result");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				int pushmsgid, userid1, messageid, modulerid, msgstatus;
				String pushmsgtime;
				pushmsgid = jsonObject.getInt("pushmsgid");
				userid1 = jsonObject.getInt("userid");
				messageid = jsonObject.getInt("messageid");
				modulerid = jsonObject.getInt("modulerid");
				msgstatus = jsonObject.getInt("msgstatus");
				pushmsgtime = jsonObject.getString("pushmsgtime").replaceAll(
						"T", " ");
				if (modulerid == NotifyPushMsg.MODULERID_NOTIFY) {
					NotifyPushMsg pushMsg = new NotifyPushMsg();
					pushMsg.setMessageid(messageid);
					pushMsg.setModulerid(modulerid);
					pushMsg.setMsgbelong(userid);
					pushMsg.setMsgstatus(msgstatus);
					pushMsg.setPushmsgid(pushmsgid);
					pushMsg.setPushmsgtime(pushmsgtime);
					pushMsg.setUserid(userid1);
					notifyPushMsgs.add(pushMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notifyPushMsgs;
	}

	/*public int getUserUnPushedUnreadNotifyCnt(User user) 
	{
		try {

			int userid = -1;

			if (user != null)
				userid = user.getUserId();

			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);

			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();
			// 获得目录下 面向用户的未读消息数量
			int oriuserunpushedunreadcnt = queryUserOriUnreadAndUnPushNotifyCnt(ORI, userid, -1);

			int nonuseroriunpushedunreadcnt = 0;
			// 同步非面向用户的通知公告消息
			List<NotifyPushMsg> notifyPushMsgs = queryNonUserOriNotifyListAll(
					ORI, userid);


			for (int i = 0; i < notifyPushMsgs.size(); i++) {
				NotifyPushMsg msg = notifyPushMsgs.get(i);
				// 如果本地数据库没有，则同步到本地数据库
				if (notifyPushMsgDB.findNotifyPushMsg(db, userid,
						msg.getPushmsgid()) == null) {
					msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
					msg.setMsgbelong(userid);
					notifyPushMsgDB.addNotifyPushMsg(db, msg);
					nonuseroriunpushedunreadcnt++;
				}
			}

			if (db != null)
				db.close();
			return oriuserunpushedunreadcnt + nonuseroriunpushedunreadcnt;
		} catch (Exception e) {
			return 0;
		}
	}*/

	/**
	 * 检查用户未读信息条数，不能在UI线程中调用
	 * 
	 * @param user
	 * @return
	 */
	public int getUserUnreadNotifyCnt(User user) 
	{
		try 
		{
			int userid = -1;

			if (user != null) userid = user.getUserId();

			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);

			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();
			// 获得目录下 面向用户的未读消息数量
			int oriuserunreadcnt = queryUserOriUnreadNotifyCnt(ORI, userid, -1);
			int orinonuserunreadcnt = 0;

			// 同步非面向用户的通知公告消息
			List<NotifyPushMsg> notifyPushMsgs = queryNonUserOriNotifyListAll(ORI, userid);
			for (int i = 0; i < notifyPushMsgs.size(); i++) {
				NotifyPushMsg msg = notifyPushMsgs.get(i);

				if (msg.getUserid() != PushMsg.USERID_BOTH
						&& msg.getUserid() != PushMsg.USERID_INNER
						&& msg.getUserid() != PushMsg.USERID_PUBLIC)
					continue;
				PushMsg dbmsg = notifyPushMsgDB.findNotifyPushMsg(db, userid,
						msg.getPushmsgid());
				// 如果本地数据库没有，则同步到本地数据库
				if (dbmsg == null) {
					// msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
					// msg.setMsgbelong(userid);
					// notifyPushMsgDB.addNotifyPushMsg(db, msg);
					orinonuserunreadcnt++;
				} else {
					if (dbmsg.getMsgstatus() == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD
							|| dbmsg.getMsgstatus() == PushMsg.MSGSTATUS_PUSHED_NOTREAD)
						orinonuserunreadcnt++;
				}
			}

			if (db != null)
				db.close();
			return oriuserunreadcnt + orinonuserunreadcnt;
		} catch (Exception e) {
			return 0;
		}
	}

	public List<NotifyPushMsg> searchPushMsgs(User user, int folderid,
			String content) {
		try {
			int userid = -1;
			if (user != null)
				userid = user.getUserId();
			List<NotifyPushMsg> msgs = queryNotifyMsgListAllByContent(ORI,
					userid, folderid, content);

			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();
			// 推送用户不是面向用户的，查本地数据表确认是否已读
			for (int i = 0; i < msgs.size(); i++) {
				NotifyPushMsg msg = msgs.get(i);

				if (msg.getUserid() == PushMsg.USERID_BOTH
						|| msg.getUserid() == PushMsg.USERID_INNER
						|| msg.getUserid() == PushMsg.USERID_PUBLIC) {
					PushMsg msgg = notifyPushMsgDB.findNotifyPushMsg(db,
							userid, msg.getPushmsgid());

					// 如果这条信息数据库中不存在，则写入本地数据库中，并为未读
					if (msgg == null) {
						msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
						msg.setMsgbelong(userid);
						notifyPushMsgDB.addNotifyPushMsgNew(db, msg);
					} else
						// 否则将信息状态设置为数据库中的信息读取状态
						msg.setMsgstatus(msgg.getMsgstatus());
				}
			}
			if (db != null)
				db.close();
			return msgs;
		} catch (Exception e) {
			return new ArrayList<NotifyPushMsg>();
		}
	}

	/**
	 * 获得某目录下的信息列表,已包含已读未读
	 * 
	 * @param folderid
	 * @return
	 */
	public List<NotifyPushMsg> getFolderNotify(User user, int folderid)
	{
		try 
		{
			int userid = -1;
			if (user != null)
				userid = user.getUserId();
			List<NotifyPushMsg> msgs = queryNotifyMsgList(ORI, userid, folderid);

			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();
			// 推送用户不是面向用户的，查本地数据表确认是否已读
			for (int i = 0; i < msgs.size(); i++) {
				NotifyPushMsg msg = msgs.get(i);

				if (msg.getUserid() == PushMsg.USERID_BOTH
						|| msg.getUserid() == PushMsg.USERID_INNER
						|| msg.getUserid() == PushMsg.USERID_PUBLIC) {
					PushMsg msgg = notifyPushMsgDB.findNotifyPushMsg(db,
							userid, msg.getPushmsgid());

					// 如果这条信息数据库中不存在，则写入本地数据库中，并为未读
					if (msgg == null) {
						msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
						msg.setMsgbelong(userid);
						notifyPushMsgDB.addNotifyPushMsgNew(db, msg);
					} else
						// 否则将信息状态设置为数据库中的信息读取状态
						msg.setMsgstatus(msgg.getMsgstatus());
				}
			}
			if (db != null)
				db.close();
			return msgs;
		}
		catch (Exception e) {
			return new ArrayList<NotifyPushMsg>();
		}
	}

	public List<NotifyPushMsgFolder> getFolders(User user, int folderid) 
	{
		try 
		{
			List<NotifyPushMsgFolder> folders = queryFolderStructure(folderid);
			int userid = -1;
			if (user != null)
				userid = user.getUserId();
			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();

			// 设置该目录的未读信息数
			for (int i = 0; i < folders.size(); i++) 
			{
				NotifyPushMsgFolder folder = folders.get(i);
				folder.setHasunreadmsg(false);

				int useroriunreadcnt = 0;
				if (user != null)// 获得该目录下的面向用户的未读通知公告数量
					useroriunreadcnt = queryUserOriUnreadNotifyCnt(ORI, userid,folder.getId());
				if (useroriunreadcnt > 0)
				{
					folder.setHasunreadmsg(true);
					continue;
				}
				int count=notifyPushMsgDB.CountUnReadByForderID(db, userid, folder.getId());
				folder.setHasunreadmsg(count>0);
				if(count==0&&notifyPushMsgDB.allCount(db,folder.getId())==0)
					folder.setHasunreadmsg(true);
				
				
				
				/*boolean hasunreadnonori = false;
				// 获得该目录下的非面向用户的未读通知公告，并通过数据库查找已读未读情况
				List<NotifyPushMsg> nonuseroriMsgs = queryNonUserOriNotifyMsgAll(ORI, userid, folder.getId());
				for (int j = 0; j < nonuseroriMsgs.size(); j++)
				{
					NotifyPushMsg msg = nonuseroriMsgs.get(j);

					PushMsg msgg = notifyPushMsgDB.findNotifyPushMsg(db,
							userid, msg.getPushmsgid());

					// 如果这条信息数据库中不存在，则写入本地数据库中，并为未读
					if (msgg == null) 
					{
						msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
						msg.setMsgbelong(userid);
						notifyPushMsgDB.addNotifyPushMsg(db, msg);
						hasunreadnonori = true;
						break;// 不需要后面的遍历
					} 
					else 
					{
						if (msgg.getMsgstatus() == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD
								|| msgg.getMsgstatus() == PushMsg.MSGSTATUS_PUSHED_NOTREAD) {
							hasunreadnonori = true;
							break;// 不需要后面的遍历
						}
					}
				}
				folder.setHasunreadmsg(hasunreadnonori);*/
			}

			if (db != null)
				db.close();
			return folders;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<NotifyPushMsgFolder>();
		}
	}

	public void readNotifyMsg(User user, NotifyPushMsg msg) {
		try {
			int userid = -1;

			if (user != null)
				userid = user.getUserId();

			// 非面向用户的，更新本地数据库
			if (msg.getUserid() == PushMsg.USERID_BOTH
					|| msg.getUserid() == PushMsg.USERID_INNER
					|| msg.getUserid() == PushMsg.USERID_PUBLIC) {
				PushMsgDB notifyPushMsgDB = new PushMsgDB(context);

				SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();

				notifyPushMsgDB.updateNotifyPushMsgStatusRead(db, userid,
						msg.getPushmsgid());

				if (db != null)
					db.close();
			} else {
				new AsyncTask<NotifyPushMsg, Void, Void>() {

					@Override
					protected Void doInBackground(NotifyPushMsg... params) {
						NotifyPushMsg msg = params[0];
						updatepushmsgstatus(msg.getPushmsgid(),
								PushMsg.MSGSTATUS_PUSHED_READ);
						return null;
					}

				}.execute(msg);
			}
		} catch (Exception e) {
		}
	}

	public void readall(User user) {
		try {
			int userid = -1;

			if (user != null)
				userid = user.getUserId();

			Log.d(TAG, "readall: " + userid);

			/**
			 * 读取所有的本地
			 */
			PushMsgDB notifyPushMsgDB = new PushMsgDB(context);
			SQLiteDatabase db = notifyPushMsgDB.getWritableDatabase();
			notifyPushMsgDB.readNotifyPushMsgAll(db, userid);
			if (db != null)
				db.close();

			/**
			 * 读取所有的远程
			 */
			new AsyncTask<Integer, Void, Void>() {

				@Override
				protected Void doInBackground(Integer... params) {
					int userid = params[0];
					try {
						readallpushmsgs(userid, PushMsg.MODULERID_NOTIFY);
					} catch (Exception e) {
					}
					return null;
				}

			}.execute(userid);
		} catch (Exception e) {
		}
	}
}
