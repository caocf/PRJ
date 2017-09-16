package com.huzhouport.common.tool;
/*
 * ¸Ä±äÒÑ¶ÁÎ´¶Á×´Ì¬
 * Éòµ¤µ¤
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;

import android.os.AsyncTask;

public class PushMsg {
	Query query=new Query();
	 Object[] date=new Object[2];
	public  void PushMsgToChange(Object messageId,Object userId){
		date[0]=messageId;
		date[1]=userId;
		new UpdatePushmsg().execute();
	}
	class UpdatePushmsg extends AsyncTask<Void,Void, Void> {
		
		protected Void doInBackground(Void... params) {
			Map<String, Object> paramdate=new HashMap<String, Object>();
			HttpFileUpTool hft=new HttpFileUpTool();
			paramdate.put("pushMsg.messageid", date[0]);
			paramdate.put("pushMsg.userid", date[1]);
			try {
				hft.post(HttpUtil.BASE_URL+"UpdatePushmsgstatusByInfor", paramdate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		

		

	}
}
