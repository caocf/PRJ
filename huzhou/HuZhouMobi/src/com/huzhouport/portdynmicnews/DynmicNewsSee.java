package com.huzhouport.portdynmicnews;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.example.huzhouport.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;

/**
 * 港航动态详情
 * @author Administrator
 *
 */
public class DynmicNewsSee extends Activity
{
	private Query query = new Query();
	private WebView tex;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portdynmicnews_see);
		tex = (WebView) findViewById(R.id.portdynmicnews_see_count);

		//设置返回按钮
		CommonListenerWrapper.commonBackWrapperListener(
				R.id.portdynmicnews_see_back, this);

		//查找新闻
		String url = getIntent().getStringExtra("url");
		new queryNewsTask(this).execute(url); 

	}

	/**
	 * 查找新闻异步线程
	 * @author Administrator
	 *
	 */
	class queryNewsTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		
		public queryNewsTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(DynmicNewsSee.this, queryNewsTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			return query.seePortDynmicNews(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			showNews(result);
			super.onPostExecute(result);
		}

	}
	
	@SuppressLint("SetJavaScriptEnabled") 
	public void showNews(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONObject jo = data.getJSONObject("modelPortDynmicNews");
			WebSettings webSettings = tex.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			tex.loadDataWithBaseURL("",
					"<html><body>" + jo.getString("contenct")
							+ "</body></html>", "text/html", "utf-8", "");

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
