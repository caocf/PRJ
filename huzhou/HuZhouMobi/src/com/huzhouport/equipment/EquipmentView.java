package com.huzhouport.equipment;





import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.equipment.EquipmentApproval.ListTask;



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

public class EquipmentView extends Activity {
	private Query query=new Query();
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipment_view);
		Intent intent = getIntent();
		 id = intent.getStringExtra("id");
	    ImageButton back=(ImageButton)findViewById(R.id.equipment_view_back);
		back.setOnClickListener(new Back());
		ListTask task = new ListTask(this);  // 异步
        task.execute();	
	}
public void getNeirong(String result){
	
	JSONTokener jsonParser_User = new JSONTokener(result);
	try {
		JSONObject data = (JSONObject) jsonParser_User.nextValue();
		JSONObject	jsonArray=data.getJSONObject("equipmentbean");
		
		String equipmentUser=jsonArray.getString("equipmentUserName");
		TextView leaveOrOtUserText=(TextView)findViewById(R.id.equipment_view_equipmentUserNeirong);
		leaveOrOtUserText.setText(equipmentUser);
		
		String equipmentKind=jsonArray.getString("equipmentKindName");
		TextView equipmentKindText=(TextView)findViewById(R.id.equipment_view_equipmentKindNeirong);
		equipmentKindText.setText(equipmentKind);
		
	String equipmentDate=jsonArray.getString("equipmentDate").substring(0, jsonArray.getString("equipmentDate").lastIndexOf(":"));
	TextView equipmentDateText=(TextView)findViewById(R.id.equipment_view_equipmentDateNeirong);
	equipmentDateText.setText(equipmentDate);
	
	String equipmentReason=jsonArray.getString("equipmentReason");
	TextView equipmentReasonText=(TextView)findViewById(R.id.equipment_view_equipmentReasonNeirong);
	equipmentReasonText.setText(equipmentReason);
	
	String approvalID=jsonArray.getString("approvalName");
	TextView approvalIDText=(TextView)findViewById(R.id.equipment_view_approvalIDNeirong);
	approvalIDText.setText(approvalID);
	
	String approvalResult=jsonArray.getString("approvalResult");
	TextView approvalResultText=(TextView)findViewById(R.id.equipment_view_approvalResultNeirong);
	
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
		
		findViewById(R.id.equipment_view_approvalOpinion_tr).setVisibility(View.GONE);
		findViewById(R.id.equipment_view_approvalTime_tr).setVisibility(View.GONE);
	}else{
		
		String approvalOpinion=jsonArray.getString("approvalOpinion");
		TextView approvalOpinionText=(TextView)findViewById(R.id.equipment_view_approvalOpinionNeirong);
		approvalOpinionText.setText(approvalOpinion);
		
		
		String approvalTime=jsonArray.getString("approvalTime").substring(0, jsonArray.getString("approvalTime").lastIndexOf(":"));
		TextView approvalTimeText=(TextView)findViewById(R.id.equipment_view_approvalTimeNeirong);
		approvalTimeText.setText(approvalTime);
		
		
		
	}
	
	
	

	
	
	
	
	
		
	} catch (Exception e) {
		Toast.makeText(EquipmentView.this, "数据异常", Toast.LENGTH_SHORT).show();
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
	    	  pDialog = new ProgressDialog(EquipmentView.this);
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
}
