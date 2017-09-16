package com.huzhouport.common;

import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.os.AsyncTask;

/**
 * ������log�첽����
 * @author Administrator zwc
 *
 */
public class Log extends AsyncTask<String, Integer, String>
{
	//����app ����  3
	//��ͷapp ����  4
	public static int sourceShipType=3;
	public static int sourceWharfType=4;
	
	User user;
	String content;
	int type;

	String bz;
	
	public Log(User user, String content, int type,String bz)
	{
		this.user = user;
		this.content = content;
		this.type = type;
		this.bz=bz;
	}

	@Override
	protected String doInBackground(String... params) {
		
		if(user==null)
			return null;
		
		if(user.getWharfBindingList()!=null)
			new Query().SaveLog(user.getUserName(), content==null?"":content, type, sourceWharfType,bz);
		else if(user.getShipBindingList()!=null)
			new Query().SaveLog(user.getUserName(), content==null?"":content, type, sourceShipType,bz);
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
