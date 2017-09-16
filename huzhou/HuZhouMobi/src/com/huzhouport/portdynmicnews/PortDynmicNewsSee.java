package com.huzhouport.portdynmicnews;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.portdynmicnews.PortDynmicNewsMain.ListTask;
import com.huzhouport.portdynmicnews.PortDynmicNewsMain.ListTaskList;

public class PortDynmicNewsSee extends Activity {
	private Query query = new Query();
	private WebView tex;
	private TextView pntx;
	private ImageButton pns_back;
	private String userId, userName;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portdynmicnews_see);
		tex = (WebView) findViewById(R.id.portdynmicnews_see_count);
		pntx = (TextView) findViewById(R.id.portdynmicnews_see_titleText);
		pns_back = (ImageButton) findViewById(R.id.portdynmicnews_see_back);	
		pns_back.setOnClickListener(new MyBack());
		Intent intent = getIntent();
		String url=intent.getStringExtra("url");
		ListTask task = new ListTask(this);  // 异步
        task.execute(url);
	}
		public void searchListView(String result) {
		//String result=query.seePortDynmicNews(url);
		//System.out.println(result);
		JSONTokener jsonParser = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONObject jo=data.getJSONObject("modelPortDynmicNews");
			WebSettings webSettings = tex.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN) ;
			tex.loadDataWithBaseURL("", "<html><body>" + jo.getString("contenct")
					+ "</body></html>", "text/html", "utf-8", "");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	class MyBack implements OnClickListener{

		@Override
		public void onClick(View v) {
			/*Intent intent=new Intent(PortDynmicNewsSee.this,PortDynmicNewsMain.class);
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
	    	  pDialog = new ProgressDialog(PortDynmicNewsSee.this);
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
			
				result=query.seePortDynmicNews(params[0]);
			
			
			
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
