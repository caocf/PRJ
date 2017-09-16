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

import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;


import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class KnowledgetextShow extends Activity{
	private TextView textview,title,summary,time;
	private Query query = new Query();
	private String knowledgeID;
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.knowledge_listtextshow);
    	
    	Intent intent = getIntent();
    	knowledgeID = intent.getStringExtra("knowledgeID");

    	
    	textview= (TextView) findViewById(R.id.knowledge_listtext_textview);
    	title= (TextView) findViewById(R.id.knowledge_listtext_title);
    	time= (TextView) findViewById(R.id.knowledge_listtext_time);
    	summary= (TextView) findViewById(R.id.knowledge_listtext_summary);
    	
    	ListTask task = new ListTask(this);  // 异步
        task.execute();

 		ImageButton back = (ImageButton) findViewById(R.id.knowledge_listtext_back1);
 		back.setOnClickListener(new Back());
         
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
	    	  pDialog = new ProgressDialog(KnowledgetextShow.this);
			  pDialog.setTitle("提示");
			  pDialog.setMessage("正在加载中，请稍候。。。");
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
			if(isCancelled()) return null;//取消异步
			return query.KnowLedgeView(knowledgeID);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if(result!=null){
			GetKnowledge(result);
			}else{
				Toast.makeText(KnowledgetextShow.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
		  
	  }
    public void GetKnowledge(String result)  {

		JSONTokener jsonParser_User = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser_User.nextValue();
		   JSONObject json = data.getJSONObject("knowledge");
		   textview.setText(Html.fromHtml(json.getString("knowledgeContent")));
		   title.setText(Html.fromHtml(json.getString("knowledgeName")));
		   time.setText(Html.fromHtml(json.getString("date").substring(0,json.getString("date").lastIndexOf(":")).replace("T", " ")));
		   String summaryStr = json.getString("knowledgeIndex");
		   if (summaryStr == null || summaryStr.equals("") || summaryStr.equals("null"))
			   summary.setVisibility(View.GONE);
		   else {
			   summary.setVisibility(View.GONE);
			   summary.setText("摘要："+Html.fromHtml(json.getString("knowledgeIndex")));
		   }
		} catch (Exception e) {
			Toast.makeText(KnowledgetextShow.this, "请检查网络", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
    
}
