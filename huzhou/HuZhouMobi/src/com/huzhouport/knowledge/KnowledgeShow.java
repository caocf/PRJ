package com.huzhouport.knowledge;


import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class KnowledgeShow extends Activity{
	private WebView webview;
	private Query query = new Query();
	private String knowledgeID;
	private String link;
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.knowledge_show);
    	
    	Intent intent = getIntent();
    	knowledgeID = intent.getStringExtra("knowledgeID");

    	
    	webview= (WebView) findViewById(R.id.knowledge_webview);
    	
    	
    	ListTask task = new ListTask(this);  // 异步
        task.execute();
    	
      // 返回
 		ImageButton back = (ImageButton) findViewById(R.id.knowledge_back1);
 		back.setOnClickListener(new Back());
         
    }
    //设置回退
    public boolean onKeyDown(int keyCode,KeyEvent event){
		if((keyCode==KeyEvent.KEYCODE_BACK)&&webview.canGoBack()){
		webview.goBack();
		return true;
		}
		return super.onKeyDown(keyCode, event);
    }
    
    public class MyWebViewClient extends WebViewClient{
    	public boolean shouldOverviewUrlLoading(WebView view,String url){
    		view.loadUrl(url);
    		return true;
    	}
    }
    
    class Back implements OnClickListener {
		public void onClick(View v) {
//			Intent intent = new Intent(KnowledgeShow.this, KnowledgeView.class);
//			
//			startActivity(intent);
			finish();
		}
	}
    
    class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;

		public ListTask(Context context){
	    	  pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
				pDialog.show();	
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
			if(isCancelled()) return null;//取消异步
			
			return query.KnowLedgeView(knowledgeID);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			GetKnowledge(result);
			//设置WebView属性，能够执行JavaScript脚本
	    	webview.getSettings().setJavaScriptEnabled(true);
	    	//加载URL内容
	         webview.loadUrl(link);
	         webview.setWebViewClient(new MyWebViewClient());
			super.onPostExecute(result);
		}
		  
	  }
    public void GetKnowledge(String result)  {

		JSONTokener jsonParser_User = new JSONTokener(result);
		// System.out.println("jsonParser_User:  " + jsonParser_User);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser_User.nextValue();
		// System.out.println("data:  " + data);
		   JSONObject json = data.getJSONObject("knowledge");
	      link=json.getString("knowledgeContent");
	      System.out.println("link="+link); 
	      
		} catch (Exception e) {
			Toast.makeText(KnowledgeShow.this, "请检查网络", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
    
}
