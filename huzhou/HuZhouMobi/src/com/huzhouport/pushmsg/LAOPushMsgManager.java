package com.huzhouport.pushmsg;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.huzhouport.common.util.HttpUtil;

/**
 * 请假加班推送消息管理
 * 
 * @author DJ
 * 
 */
public class LAOPushMsgManager {

	/**
	 * 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读 供后台推送服务调用
	 */
	public int getUnPushedMsg(int userid) {
		int size = 0;
		String url = HttpUtil.BASE_URL + "queryUnPushedMsg?onlysize=true&pushMsg.modulerid="
				+ PushMsg.MODULERID_LEAVEANDOVERTIME + "&pushMsg.userid="+userid;
		String result = HttpUtil.queryStringForPost(url);
		
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("pushmsgs");
 
			JSONObject jsonObject = (JSONObject) jsonArray.opt(0);
			
			size = Integer.parseInt(jsonObject.getString("size"));
			
			Log.d("LAOPushMsgManager", "getUnPushedMsg Size: "+size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 获得已推送未读消息数量 供9宫格调用显示是否有未读消息
	 */
	public int getPushedUnReadMsgCnt(int userid) {
		int size = 0;
		String url = HttpUtil.BASE_URL + "queryPushedUnReadMsgCnt?pushMsg.modulerid="
				+ PushMsg.MODULERID_LEAVEANDOVERTIME + "&pushMsg.userid="+userid;
		String result = HttpUtil.queryStringForPost(url);
		
		try {
			// 解析获得根目录下该用户的面向用户的未读消息数量
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("pushmsgs");
 
			JSONObject jsonObject = (JSONObject) jsonArray.opt(0);
			
			size = Integer.parseInt(jsonObject.getString("size"));
			
			Log.d("LAOPushMsgManager", "getPushedUnReadMsgCnt Size: "+size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return size;
	}
}
