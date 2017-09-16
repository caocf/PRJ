package com.huzhouport.leaveandovertime;


import net.hxkg.ghmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class LeaveandovertimeNewListAddKindName extends Activity {
	private ListView lv;
	private String kind;
	private Query query=new Query();
	private ArrayList<HashMap<String, Object>> leave=new ArrayList<HashMap<String, Object>>();
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_add_kindname);
		Intent intent = getIntent();
		kind=intent.getStringExtra("kind");
		
		lv = (ListView) findViewById(R.id.leaveandovertime_newlist_addkind_list);	
		
				ListTask task = new ListTask(this); 
		        task.execute();
		
		
		ImageButton back=(ImageButton)findViewById(R.id.leaveandovertime_newlist_addkind_back);
		back.setOnClickListener(new Back());
		
		
	}
	
	public void getLeave()
	{
		HashMap<String, Object> leavemap1 = new HashMap<String, Object>();	
		leavemap1.put("text1", "事假");
		leavemap1.put("text2","1");
		leave.add(leavemap1);
		HashMap<String, Object> leavemap2 = new HashMap<String, Object>();	
		leavemap2.put("text1", "病假");
		leavemap2.put("text2","3");
		leave.add(leavemap2);
		HashMap<String, Object> leavemap3 = new HashMap<String, Object>();	
		leavemap3.put("text1", "婚假");
		leavemap3.put("text2","4");
		leave.add(leavemap3);
		
		HashMap<String, Object> leavemap4 = new HashMap<String, Object>();	
		leavemap4.put("text1", "产假");
		leavemap4.put("text2","6");
		leave.add(leavemap4);
		
		HashMap<String, Object> leavemap5 = new HashMap<String, Object>();	
		leavemap5.put("text1", "丧假");
		leavemap5.put("text2","7");
		leave.add(leavemap5);
	}
	
	public void getLeave(String result)
	{		
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("leaveOrOtKind");
			leave = new ArrayList<HashMap<String, Object>>();
			for(int i=0;i<jsonArray.length();i++){
				HashMap<String, Object> leavemap = new HashMap<String, Object>();		
		JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
		String 	text1	=jsonObject.getString("kindName");
		String text2=jsonObject.getString("kindID");
		leavemap.put("text1",text1);
		leavemap.put("text2",text2);
		leave.add(leavemap);
		}	
			
		} catch (Exception e) 
		{
			Toast.makeText(LeaveandovertimeNewListAddKindName.this, "数据异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	

	
	
	
	
     public void showLeave()
     {
    	 SimpleAdapter adapter = new 
    			 SimpleAdapter(LeaveandovertimeNewListAddKindName.this,leave,
    					 R.layout.leaveandovertime_add_item, new String[] {"text1","text2"}, 
    					 new int[] { R.id.leaveandovertime_add_name,R.id.leaveandovertime_add_id});
    	 lv.setAdapter(adapter);
    	 lv.setOnItemClickListener(new leaveOnitem());
    		 	
     }
	
	 class Back implements View.OnClickListener{
		 public void onClick(View v){
			finish();
		 }
	 }
	 class leaveOnitem implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_name= (TextView) arg1.findViewById(R.id.leaveandovertime_add_name);
			TextView tv_id= (TextView) arg1.findViewById(R.id.leaveandovertime_add_id);
			System.out.println("tv_id==="+tv_id);
			Intent intent = new Intent();
			intent.putExtra("kind_name", tv_name.getText().toString());
			intent.putExtra("kind_id", tv_id.getText().toString());
			setResult(40, intent);
			finish();
		}
		 
	 }

	 
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
		  @SuppressWarnings("deprecation")
		public ListTask(Context context){
	    	  pDialog = new ProgressDialog(LeaveandovertimeNewListAddKindName.this);
			 
			  pDialog.setMessage("数据加载中");
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
			if(isCancelled()) return null;//ȡ���첽
			
			return query.LeaveandovertimeKindType(kind);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			//getLeave(result);
			getLeave();
			showLeave();
			super.onPostExecute(result);
		}
		  
	  }
	  
	 
}
