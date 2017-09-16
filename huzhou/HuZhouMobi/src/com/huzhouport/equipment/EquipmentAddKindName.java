package com.huzhouport.equipment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.equipment.EquipmentAdd.ListTask;


import java.util.ArrayList;
import java.util.HashMap;
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

public class EquipmentAddKindName extends Activity {
	private ListView lv;
	private Query query=new Query();
	private ArrayList<HashMap<String, Object>> leave;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_add_kindname);
		
		lv = (ListView) findViewById(R.id.leaveandovertime_newlist_addkind_list);	

		//获取显示列表信息
				ListTask task = new ListTask(this);  // 异步
		        task.execute();
		
		
		ImageButton back=(ImageButton)findViewById(R.id.leaveandovertime_newlist_addkind_back);
		back.setOnClickListener(new Back());
		TextView title=(TextView) findViewById(R.id.leaveandovertime_newlist_addkind_title);
		title.setText("申请物品选择");
		
	}
	
	public void getLeave(String result){
		
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("equipmentkindlist");
			leave = new ArrayList<HashMap<String, Object>>();
			for(int i=0;i<jsonArray.length();i++){
				HashMap<String, Object> leavemap = new HashMap<String, Object>();		
		JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
		String 	text1	=jsonObject.getString("equipmentKindName");
		String text2=jsonObject.getString("equipmentKindID");
		leavemap.put("text1",text1);
		leavemap.put("text2",text2);
		leave.add(leavemap);
		}
			
			
			
			
		} catch (Exception e) {
			Toast.makeText(EquipmentAddKindName.this, "数据异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	

	
	
	
	
     public void showLeave(){
    	 SimpleAdapter adapter = new SimpleAdapter(EquipmentAddKindName.this,leave, R.layout.leaveandovertime_add_item, new String[] {"text1","text2"}, new int[] { R.id.leaveandovertime_add_name,R.id.leaveandovertime_add_id});
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
			intent.putExtra("kind_name", tv_name.getText().toString());// 设置回传的意图
			intent.putExtra("kind_id", tv_id.getText().toString());// 设置回传的意图
			setResult(40, intent);
			finish();
		}
		 
	 }

	 
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      public ListTask(Context context){
	    	  pDialog = new ProgressDialog(EquipmentAddKindName.this);
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
			
			return query.equipmentkindAll();
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getLeave(result);//获得数据
			showLeave();
			super.onPostExecute(result);
		}
		  
	  }
	  
	 
}
