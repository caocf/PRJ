package com.huzhouport.equipment;





import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.equipment.EquipmentApproval.ListTask;


import com.huzhouport.main.User;
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


import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentShow extends Activity {
	private Query query=new Query();
	private String id,userid,username;
	private String actionUrl, param1;
    private String resultid;
    private User user=new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipment_show);
		Intent intent = getIntent();
		 id = intent.getStringExtra("id");
		 userid = intent.getStringExtra("userid");
		 username=intent.getStringExtra("username");
		 user.setUserId(Integer.parseInt(userid));
		 user.setName(username);
	    ImageButton back=(ImageButton)findViewById(R.id.equipment_show_back);
		back.setOnClickListener(new Back());
		ListTask task = new ListTask(this);  // 异步
        task.execute();	
        
        
    	ImageButton noButton=(ImageButton)findViewById(R.id.equipment_show_no);
		ImageButton yesButton=(ImageButton) findViewById(R.id.equipment_show_yes);
		noButton.setOnClickListener(new Nobutton());
		yesButton.setOnClickListener(new Yesbutton());
		
		ImageButton sendButton=(ImageButton)findViewById(R.id.equipment_show_send);
		sendButton.setOnClickListener(new Sendbutton());
	}
public void getNeirong(String result){
	
	JSONTokener jsonParser_User = new JSONTokener(result);
	try {
		JSONObject data = (JSONObject) jsonParser_User.nextValue();
		JSONObject	jsonArray=data.getJSONObject("equipmentbean");
		
		String equipmentUser=jsonArray.getString("equipmentUserName");
		TextView leaveOrOtUserText=(TextView)findViewById(R.id.equipment_show_equipmentUserNeirong);
		leaveOrOtUserText.setText(equipmentUser);
		
		String equipmentKind=jsonArray.getString("equipmentKindName");
		TextView equipmentKindText=(TextView)findViewById(R.id.equipment_show_equipmentKindNeirong);
		equipmentKindText.setText(equipmentKind);
		
	String equipmentDate=jsonArray.getString("equipmentDate").substring(0, jsonArray.getString("equipmentDate").lastIndexOf(":"));
	TextView equipmentDateText=(TextView)findViewById(R.id.equipment_show_equipmentDateNeirong);
	equipmentDateText.setText(equipmentDate);
	
	String equipmentReason=jsonArray.getString("equipmentReason");
	TextView equipmentReasonText=(TextView)findViewById(R.id.equipment_show_equipmentReasonNeirong);
	equipmentReasonText.setText(equipmentReason);
	
	String approvalID=jsonArray.getString("approvalName");
	TextView approvalIDText=(TextView)findViewById(R.id.equipment_show_approvalIDNeirong);
	approvalIDText.setText(approvalID);
	
	String approvalResult=jsonArray.getString("approvalResult");
	TextView approvalResultText=(TextView)findViewById(R.id.equipment_show_approvalResultNeirong);
	
	if(approvalResult.equals("0")){
		approvalResultText.setText("待审批");
	}

	if(approvalResult.equals("3")){
		approvalResultText.setText("不批准");
	}
	if(approvalResult.equals("4")){
		approvalResultText.setText("批准");
	}

	
	if(approvalResult.equals("0")){
		
		findViewById(R.id.equipment_show_approvalOpinion_tr).setVisibility(View.GONE);
		findViewById(R.id.equipment_show_approvalTime_tr).setVisibility(View.GONE);
	}else{
		
		String approvalOpinion=jsonArray.getString("approvalOpinion");
		TextView approvalOpinionText=(TextView)findViewById(R.id.equipment_show_approvalOpinionNeirong);
		approvalOpinionText.setText(approvalOpinion);
		
		
		String approvalTime=jsonArray.getString("approvalTime").substring(0, jsonArray.getString("approvalTime").lastIndexOf(":"));
		TextView approvalTimeText=(TextView)findViewById(R.id.equipment_show_approvalTimeNeirong);
		approvalTimeText.setText(approvalTime);	
	}
	} catch (Exception e) {
		Toast.makeText(EquipmentShow.this, "数据异常", Toast.LENGTH_SHORT).show();
		e.printStackTrace();
	}
}


    
	class Back implements View.OnClickListener{
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
	    	  pDialog = new ProgressDialog(EquipmentShow.this);
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
			return query.showEquipmentApproval(id);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);//获得数据
			super.onPostExecute(result);
		}
		  
	  }
	 
		class Nobutton implements View.OnClickListener{
			 public void onClick(View v){
				 findViewById(R.id.equipment_show_footR).setVisibility(View.GONE);
				findViewById(R.id.equipment_show_footR1).setVisibility(View.VISIBLE);
				EditText et = (EditText) findViewById(R.id.equipment_show_input);
				et.requestFocus(); //请求获取焦点
				//et.setFocusable(true);
				et.setFocusableInTouchMode(true);
			 }
		 }
		class Yesbutton implements View.OnClickListener{
			 public void onClick(View v){
				 resultid="4"; //准许
				 HttpFileUpTool hfu = new HttpFileUpTool();
				 actionUrl = HttpUtil.BASE_URL + "EquipmentApproval";
					param1 = "equipmentbean.equipmentID=" + id + 
							 "&equipmentbean.approvalResult=" + resultid
							+ "&equipmentbean.approvalOpinion=";
				ListTask1 task = new ListTask1(EquipmentShow.this); 
				task.execute();
			 }
		 }
		class Sendbutton implements View.OnClickListener{
			 public void onClick(View v){
				 EditText edit= (EditText) findViewById(R.id.equipment_show_input);
				 if(edit.getText().toString().equals("")){
					 Toast.makeText(EquipmentShow.this, "请填写审批原因", Toast.LENGTH_SHORT).show();
				 }else{
				 
				 resultid="3"; //不准许
				 HttpFileUpTool hfu = new HttpFileUpTool();
				 actionUrl = HttpUtil.BASE_URL + "EquipmentApproval";
				 param1 = "equipmentbean.equipmentID=" + id + 
						 "&equipmentbean.approvalResult=" + resultid
						+ "&equipmentbean.approvalOpinion="+edit.getText().toString();
				ListTask1 task = new ListTask1(EquipmentShow.this); 
				task.execute();
				 }
			 }
		 }
		
		
		 class ListTask1 extends AsyncTask<String, Integer, String> {
				ProgressDialog pDialog = null;

				public ListTask1() {

				}

				@SuppressWarnings("deprecation")
				public ListTask1(Context context) {
					  pDialog = new ProgressDialog(EquipmentShow.this);
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
			 					if (ListTask1.this != null && ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
			 						ListTask1.this.cancel(true);
								
							}
						});
						pDialog.setButton("取消", new DialogInterface.OnClickListener()
						{
							
							@Override
							public void onClick(DialogInterface dialog,int which)
							{
								if (pDialog != null)
									pDialog.dismiss();
			 					if (ListTask1.this != null && ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
			 						ListTask1.this.cancel(true);
								
							}
						});
						pDialog.show();
				}

				@Override
				protected String doInBackground(String... params) {
					
					if(isCancelled()) return null;//取消异步
					
					
					UploadActivity.tool.addFile("设备申请", actionUrl, param1);
					
	
						Intent intent = new Intent(EquipmentShow.this,
								EquipmentMain.class);
						intent.putExtra("User", user);
						startActivity(intent);
						setResult(20);
						finish();

			
					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					if (pDialog != null)
						pDialog.dismiss();

					super.onPostExecute(result);
				}

			}
		
	 
}
