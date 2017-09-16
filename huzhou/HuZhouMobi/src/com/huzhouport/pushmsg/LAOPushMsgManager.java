package com.huzhouport.pushmsg;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.huzhouport.common.util.HttpUtil;

/**
 * ��ټӰ�������Ϣ����
 * 
 * @author DJ
 * 
 */
public class LAOPushMsgManager {

	/**
	 * ���δ����δ����Ϣ�б�, ��ȡ����δ����δ����Ϣ�󣬽���Ϣ����Ϊ������δ�� ����̨���ͷ������
	 */
	public int getUnPushedMsg(int userid) {
		int size = 0;
		String url = HttpUtil.BASE_URL + "queryUnPushedMsg?onlysize=true&pushMsg.modulerid="
				+ PushMsg.MODULERID_LEAVEANDOVERTIME + "&pushMsg.userid="+userid;
		String result = HttpUtil.queryStringForPost(url);
		
		try {
			// ������ø�Ŀ¼�¸��û��������û���δ����Ϣ����
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
	 * ���������δ����Ϣ���� ��9���������ʾ�Ƿ���δ����Ϣ
	 */
	public int getPushedUnReadMsgCnt(int userid) {
		int size = 0;
		String url = HttpUtil.BASE_URL + "queryPushedUnReadMsgCnt?pushMsg.modulerid="
				+ PushMsg.MODULERID_LEAVEANDOVERTIME + "&pushMsg.userid="+userid;
		String result = HttpUtil.queryStringForPost(url);
		
		try {
			// ������ø�Ŀ¼�¸��û��������û���δ����Ϣ����
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
