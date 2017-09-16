package com.huzhouport.portdynmicnews;

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
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class IndusrtyInfoSee extends Activity{
	private TextView textview,title,summary,time;
	private Query query = new Query();
	private String id;
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.knowledge_listtextshow);
    	
    	Intent intent = getIntent();
    	id = intent.getStringExtra("id");

    	TextView t=(TextView) findViewById(R.id.knowledge_listtext_title1);
    	t.setText("��Ѷ�鿴");
    	
    	textview= (TextView) findViewById(R.id.knowledge_listtext_textview);
    	title= (TextView) findViewById(R.id.knowledge_listtext_title);
    	time= (TextView) findViewById(R.id.knowledge_listtext_time);
    	time.setVisibility(View.VISIBLE);
    	summary= (TextView) findViewById(R.id.knowledge_listtext_summary);
    	summary.setVisibility(View.GONE);
    	ListTask task = new ListTask(this);  // �첽
        task.execute();

 		ImageButton back = (ImageButton) findViewById(R.id.knowledge_listtext_back1);
 		back.setOnClickListener(new Back());
         
    }


    
    class Back implements OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(IndusrtyInfoSee.this,IndustryInfoActivity.class);
			startActivity(intent);
			
			finish();
		}
	}
    
    /**
	 * ���˰�ť�¼�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent(IndusrtyInfoSee.this,IndustryInfoActivity.class);
			startActivity(intent);
			
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
	    	  pDialog = new ProgressDialog(IndusrtyInfoSee.this);
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
			return query.seeIndustryInfo(id);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if(result!=null){
			GetIndustryInfo(result);
			}else{
				Toast.makeText(IndusrtyInfoSee.this, "�����쳣", Toast.LENGTH_SHORT).show();
			}
		
			super.onPostExecute(result);
		}
		  
	  }
    public void GetIndustryInfo(String result)  {
		JSONTokener jsonParser_User = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser_User.nextValue();
		   JSONObject json = data.getJSONObject("industryinfo");
		   textview.setText(Html.fromHtml(json.getString("content")));
		   title.setText(Html.fromHtml(json.getString("title")));
		   time.setText(Html.fromHtml(json.get("updatetime").toString().substring(0,json.get("updatetime").toString().lastIndexOf(":")).replace("T", " ")));
		   //summary.setText("ժҪ��"+Html.fromHtml(json.getString("knowledgeIndex")));
		} catch (Exception e) {
			Toast.makeText(IndusrtyInfoSee.this, "�����쳣", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
    
}
