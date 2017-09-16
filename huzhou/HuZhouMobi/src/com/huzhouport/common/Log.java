package com.huzhouport.common;
import com.huzhouport.common.util.Query;

import android.os.AsyncTask;

/**
 * 独立的log异步操作
 * @author Administrator zwc
 *
 */
public class Log extends AsyncTask<String, Integer, String>
{
	//内部app类型 2
	private static int sourcetype=2;
	
	String user;
	String content;
	int type;

	String bz;
	
	public Log(String user, String content, int type,String bz)
	{
		this.user = user;
		this.content = content;
		this.type = type;
		this.bz=bz;
	}

	@Override
	protected String doInBackground(String... params) {
		new Query().SaveLog(user==null?"":user, content==null?"":content, type, sourcetype,bz);
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
