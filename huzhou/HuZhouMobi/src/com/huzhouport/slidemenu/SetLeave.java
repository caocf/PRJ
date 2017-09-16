package com.huzhouport.slidemenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.leaveandovertime.LeaveandovertimeAddUSer;

import com.huzhouport.upload.UploadActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetLeave extends Activity {
	private Query query=new Query();
	private TextView tv1, tv2, tv3;
	private TextView id1,id2,id3;
	private String userid;
	private String actionUrl,param1;
	private String adduserid,adduser_name,adduser_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_leave);
		
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_leave_back);
	    back.setOnClickListener(new Back());
	    
	    ImageButton submit=(ImageButton) findViewById(R.id.fragment_set_leave_add);
	    submit.setOnClickListener(new Submit());
	    
	    tv1=(TextView) findViewById(R.id.fragment_set_leave_tv2);
//	    tv2=(TextView) findViewById(R.id.fragment_set_leave_tv4);
//	    tv3=(TextView) findViewById(R.id.fragment_set_leave_tv6);
	    id1=(TextView) findViewById(R.id.fragment_set_leave_tv2_id);
//	    id2=(TextView) findViewById(R.id.fragment_set_leave_tv4_id);
//	    id3=(TextView) findViewById(R.id.fragment_set_leave_tv6_id);
	    
	    tv1.setOnClickListener(new AddUser1());
/*		tv2.setOnClickListener(new AddUser2());
		tv3.setOnClickListener(new AddUser3());*/
	    
	    
	    ListTask task = new ListTask(this);  // 异步
        task.execute();
        
        userid=getIntent().getStringExtra("userid");
	   
	    System.out.println("userid=="+userid);
}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 30) {

			//从子窗口中获取返回结果
			adduserid = data.getStringExtra("adduserid"); 
			adduser_name = data.getStringExtra("adduser_name"); 
			adduser_id = data.getStringExtra("adduser_id"); 
			
			if(adduserid.equals("1")){
				 
                 tv1.setText(adduser_name);
                 id1.setText(adduser_id);
			}
			if(adduserid.equals("2")){
                tv2.setText(adduser_name);
                id2.setText(adduser_id);
			}
			if(adduserid.equals("3")){
                tv3.setText(adduser_name);
                id3.setText(adduser_id);
			}
		}
	}
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
	    	  pDialog = new ProgressDialog(SetLeave.this);
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
			
			return query.LeaveOrOtApproval(userid);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);//获得数据
			
			super.onPostExecute(result);
		}
		  
	  
	 public void getNeirong(String result){
			
			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray	jsonArray=data.getJSONArray("approvalbean");
				if(jsonArray.length()==0){
					
				 }else{ 
				JSONObject  jsonObject=jsonArray.getJSONObject(0);
				if(jsonObject.getString("approvalName1").equals("0")){
							
				}else{
					tv1.setText(jsonObject.getString("approvalName1"));
					id1.setText(jsonObject.getString("approval1"));
				}
				
				if(jsonObject.getString("approvalName2").equals("0")){
					
				}else{
					//tv2.setText(jsonObject.getString("approvalName2"));
					//id2.setText(jsonObject.getString("approval2"));
				}
				
               if(jsonObject.getString("approvalName3").equals("0")){
					
				}else{
					//tv3.setText(jsonObject.getString("approvalName3"));
					//id3.setText(jsonObject.getString("approval3"));
				}
				
				
				  }
				} catch (Exception e) {
				Toast.makeText(SetLeave.this, "网络异常", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
}
	 }
	 
	 public class Submit implements View.OnClickListener{
			public void onClick(View v){
				if(id1.getText().toString().equals("")){
					Toast.makeText(SetLeave.this, "请选择审批人", Toast.LENGTH_SHORT).show();
				}else{
					 actionUrl = HttpUtil.BASE_URL + "SaveLeaveOrOtApproval";
				     param1="approval.userID="+userid+"&approval.approval1="+id1.getText().toString()+"&approval.approval2=0"+"&approval.approval3=0";
				     ListTask4 task = new ListTask4(SetLeave.this);  // 异步
					    task.execute();
				}
			}
		}

	 
	 class ListTask4 extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask4(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask4(Context context){
	    	  pDialog = new ProgressDialog(SetLeave.this);
			  pDialog.setTitle("提示");
			  pDialog.setMessage("正在提交中，请稍候。。。");
			  pDialog.setCancelable(true);
			  pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask4.this != null && ListTask4.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask4.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask4.this != null && ListTask4.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask4.this.cancel(true);
						
					}
				});
				pDialog.show();	
		  }
		  
		@Override
		protected String doInBackground(String... params) {
			
			if(isCancelled()) return null;//取消异步
			
			UploadActivity.tool.addFile("请假加班设置", actionUrl, param1);
			finish();
			return null ;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();	
			super.onPostExecute(result);
		}
		  
	  }
	 
	 public class AddUser1 implements View.OnClickListener{

			@Override
			public void onClick(View v) {
		
				
				Intent intent=new Intent(SetLeave.this, LeaveandovertimeAddUSer.class);
				intent.putExtra("adduserid", "1");
				intent.putExtra("departmentId", "-1");
				intent.putExtra("userid", userid);
				intent.putExtra("adduserid1",id1.getText().toString());
				intent.putExtra("adduserid2","");
				intent.putExtra("adduserid3","");
				startActivityForResult(intent, 100);

					
			
				
			}
			}
			public class AddUser2 implements View.OnClickListener{
			public void onClick(View v) {
	
		   	Intent intent=new Intent(SetLeave.this, LeaveandovertimeAddUSer.class);
			intent.putExtra("adduserid", "2");
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			intent.putExtra("adduserid1",id1.getText().toString());
			intent.putExtra("adduserid2",id2.getText().toString());
			intent.putExtra("adduserid3",id3.getText().toString());
			startActivityForResult(intent, 100);
		       
			}
			}
			public class AddUser3 implements View.OnClickListener{
			public void onClick(View v) {
		/*		parentList = new ArrayList<Map<String, Object>>();
				allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		       name=null;
		       
		       appid=3;
		   	// 人员获取
		       ListTask1 task = new ListTask1(LeaveandovertimeAdd.this);  // 异步
		       task.execute();*/
			
			
		   	Intent intent=new Intent(SetLeave.this, LeaveandovertimeAddUSer.class);
			intent.putExtra("adduserid", "3");
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			intent.putExtra("adduserid1",id1.getText().toString());
			intent.putExtra("adduserid2",id2.getText().toString());
			intent.putExtra("adduserid3",id3.getText().toString());
			startActivityForResult(intent, 100);
			}
			
		}
	 
	
}