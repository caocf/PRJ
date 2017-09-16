package com.huzhouport.knowledge;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
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
    	
    	
    	ListTask task = new ListTask(this);  // �첽
        task.execute();
    	
    	
    	
    	
    	
    	
    	//����WebView���ԣ��ܹ�ִ��JavaScript�ű�
    	//webview.getSettings().setJavaScriptEnabled(true);
    	
    	
//    	webview.getSettings().setSupportZoom(true); // ������������
//    	webview.getSettings().setBuiltInZoomControls(true); // �����������ſؼ�
//    	webview.getSettings().setUseWideViewPort(true); //���ô����ԣ�������������š�
//    	webview.getSettings().setLoadWithOverviewMode(true); // ��������Ļ�Ĵ�С
    	//����URL����
        // webview.loadUrl(HttpUtil.BASE_URL+"page/officoa/KnowledgeShow.jsp?knowledgeID="+id);
    	
       //����web��ͼ�ͻ���
        // webview.setWebViewClient(new MyWebViewClient());
         
      // ����
 		ImageButton back = (ImageButton) findViewById(R.id.knowledge_back1);
 		back.setOnClickListener(new Back());
         
    }
    //���û���
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
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
	    	  pDialog = new ProgressDialog(KnowledgeShow.this);
			  pDialog.setTitle("��ʾ");
			  pDialog.setMessage("���ڼ����У����Ժ򡣡���");
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
				pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
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
			if(isCancelled()) return null;//ȡ���첽
			
			return query.KnowLedgeView(knowledgeID);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			GetKnowledge(result);
			//����WebView���ԣ��ܹ�ִ��JavaScript�ű�
	    	webview.getSettings().setJavaScriptEnabled(true);
	    	//����URL����
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
			Toast.makeText(KnowledgeShow.this, "��������", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
    
}
