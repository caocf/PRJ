package com.huzhouport.navigation;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageButton;
import android.widget.TextView;

public class PortNavigationSee extends Activity {

	private Query query = new Query();
	private WebView pnstexn;
	private TextView pnstxn;
	private ImageButton pnts_backn;
	private String userId, userName;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portnavigation_see);
		pnstexn = (WebView) findViewById(R.id.portnavigation_see_count);
		pnstxn = (TextView) findViewById(R.id.portnavigation_see_titleText);
		pnts_backn = (ImageButton) findViewById(R.id.portnavigation_see_back);

		pnts_backn.setOnClickListener(new MyBack());
		Intent intent = getIntent();
		String url=intent.getStringExtra("url");
		ListTask task = new ListTask(this);  // 异步
        task.execute(url);
	}
	public void searchListView(String result) {
//		String pdnResult = query.seePortNavigation(url);
//		System.out.println(pdnResult);
		JSONTokener jsonParser = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONObject jo = data.getJSONObject("modelPortNavigation");
			WebSettings webSettings = pnstexn.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			pnstexn.loadDataWithBaseURL("","<html><body>" + jo.getString("contenct")
							+ "</body></html>", "text/html", "utf-8", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class MyBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			/*Intent intent = new Intent(PortNavigationSee.this, PortNavigationMain.class);
			intent.putExtra("userId", userId);
			intent.putExtra("userName", userName);
			startActivity(intent);*/
			finish();

		}

	}
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
		  //pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
	    	  pDialog = new ProgressDialog(PortNavigationSee.this);
				pDialog.setTitle("提示");
				pDialog.setMessage("数据正在加载中，请稍候···");
				pDialog.setCancelable(true);
				pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.show();	
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
		//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
		//showDangerousgoodsportln();
			
			String result;
			
				result=query.seePortnavigation(params[0]);
			
			
			
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			searchListView(result);
			super.onPostExecute(result);
		}
		  
	  }
}
