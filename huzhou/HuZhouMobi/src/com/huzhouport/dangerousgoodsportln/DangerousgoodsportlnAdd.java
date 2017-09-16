package com.huzhouport.dangerousgoodsportln;








import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.huzhouport.R;






import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.dangerousgoodsportln.DangerousgoodsportlnapprovalSubmit.ListTask;







import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;


import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;



import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DangerousgoodsportlnAdd extends Activity {
	private String shipName;
	private Query query = new Query();
	private String actionUrl,param1;

	private String name;
	
	private Spinner spinner_kind; //下拉框
	private int[] timeList = new int[5]; //时间
	private TextView ShowportTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsportln_add_add);
		
		//焦点的问题
//		EditText neirong=(EditText) findViewById(R.id.leaveandovertime_add_leaveOrOtReasonNeirong);
//		neirong.setOnClickListener(new Neirong());
		
		
		Intent intent = getIntent();
		shipName=intent.getStringExtra("shipName");
		

		//下拉框
		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsportln_add_DangerousLevelNeirong);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.dangerousgoodsportln_DangerousLevel_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);
		
	
		
	    TextView shipNameText=(TextView)findViewById(R.id.dangerousgoodsportln_add_shipNameNeirong);
		shipNameText.setText(shipName);
		
		//获取当前时间
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		 Date   curDate=new Date();
		String   declareTime = formatter.format(curDate);
		 TextView declareTimeText=(TextView)findViewById(R.id.dangerousgoodsportln_add_declareTimeNeirong);
	    declareTimeText.setText(declareTime);
	    
	    
	    //进港时间
	    String[] substring = declareTime.split(" ");
	    String[] substring1 = substring[0].split("-");
	    String[] substring2 = substring[1].split(":");
	    timeList[0] = Integer.parseInt(substring1[0]);
	    timeList[1] = Integer.parseInt(substring1[1]);
	    timeList[2] = Integer.parseInt(substring1[2]);
	    timeList[3] = Integer.parseInt(substring2[0]);
	    timeList[4] = Integer.parseInt(substring2[1]);
	
	
	    ShowportTime=(TextView) findViewById(R.id.dangerousgoodsportln_add_portTimeNeirong);
	    ShowportTime.setOnClickListener(new ShowportTime());
		

	ImageButton back=(ImageButton)findViewById(R.id.dangerousgoodsportln_addback);
	back.setOnClickListener(new Back());
	
		
	
		
	
	//提交按钮
			ImageButton sumbit=(ImageButton) findViewById(R.id.dangerousgoodsportln_add_submit);
			sumbit.setOnClickListener(new Sumbit());
		
			
	}
	








	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}


	


		class Sumbit implements OnClickListener{

		
			public void onClick(View v) {
				//获得要传的值
			TextView declareTime=(TextView) findViewById(R.id.dangerousgoodsportln_add_declareTimeNeirong);
			EditText startingPortName=(EditText) findViewById(R.id.dangerousgoodsportln_add_StartingPortNeirong);	
			EditText arrivalPortName=(EditText) findViewById(R.id.dangerousgoodsportln_add_ArrivalPortNeirong);
			EditText cargoTypes=(EditText) findViewById(R.id.dangerousgoodsportln_add_CargoTypesNeirong);
			EditText carrying=(EditText) findViewById(R.id.dangerousgoodsportln_add_CarryingNeirong);
			
			String declareTime1=declareTime.getText().toString();
			String portTime=ShowportTime.getText().toString();
			String startingPortName1=startingPortName.getText().toString();
			String arrivalPortName1=arrivalPortName.getText().toString();
			String cargoTypes1=cargoTypes.getText().toString();
			String dangerousLevel=spinner_kind.getSelectedItem().toString();	
			String carrying1=carrying.getText().toString();
		   if(portTime.equals("")){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "请填写进港时间", Toast.LENGTH_SHORT).show();
		   }else if(startingPortName1.equals("")){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "请填写起运港", Toast.LENGTH_SHORT).show();   
		   }else if(arrivalPortName1.equals("")){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "请填写目的港", Toast.LENGTH_SHORT).show();   
		   }else if(cargoTypes1.equals("")){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "请填写货物名称", Toast.LENGTH_SHORT).show(); 
		   }else if(carrying1.equals("")){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "请填写载重", Toast.LENGTH_SHORT).show(); 
		   }else if(!isNum(carrying1)){
			   Toast.makeText(DangerousgoodsportlnAdd.this, "载重必须是数字", Toast.LENGTH_SHORT).show(); 
		   }else{
			  
				actionUrl = HttpUtil.BASE_URL + "newDangerousGoodsPortln";
				param1="dangerousArrivalDeclareBean.shipName="+shipName+"&dangerousArrivalDeclareBean.declareTime="+declareTime1+"&dangerousArrivalDeclareBean.portTime="+portTime+"&dangerousArrivalDeclareBean.startingPortName="+startingPortName1+"&dangerousArrivalDeclareBean.arrivalPortName="+arrivalPortName1+"&dangerousArrivalDeclareBean.cargoTypes="+cargoTypes1+"&dangerousArrivalDeclareBean.dangerousLevel="+dangerousLevel+"&dangerousArrivalDeclareBean.carrying="+carrying1;
				
				ListTask task = new ListTask(DangerousgoodsportlnAdd.this);  // 异步
			    task.execute();
				
				
		   }
			
			
			
			
			
			
		/*	if(id1.equals("")){
				Toast.makeText(DangerousgoodsportlnAdd.this, "请填写直接领导", Toast.LENGTH_SHORT).show();
			}else if(id2.equals("")&&!id3.equals("")){
				Toast.makeText(DangerousgoodsportlnAdd.this, "请填写上级领导", Toast.LENGTH_SHORT).show();
			}else if(Reason.equals("")){
				Toast.makeText(DangerousgoodsportlnAdd.this, "请填写事由", Toast.LENGTH_SHORT).show();
			}else if(id2.equals("")){  // 1 
				//Toast.makeText(LeaveandovertimeAdd.this, "成功1", Toast.LENGTH_SHORT).show();
				//提交
				HttpFileUpTool hfu = new HttpFileUpTool();
				String actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
				String param1 ="leaveOrOtKindbean.approvalID1="+id1+"&leaveOrOtKindbean.approvalID2=0&leaveOrOtKindbean.approvalID3=0&leaveOrOtKindbean.leaveOrOtUser="+userid+"&leaveOrOtKindbean.kindName="+kindname+"&leaveOrOtKindbean.leaveOrOtReason="+Reason+"&leaveOrOtKindbean.beginDate="+begintime+"&leaveOrOtKindbean.endDate="+endtime;
				System.out.println("param===="+param1);
				try {
					hfu.sendPost1(actionUrl, param1);
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存成功", Toast.LENGTH_SHORT).show();
					if(kind.equals("2")){
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeOvertimeshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}else{
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeLeaveshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}	
				} catch (Exception e) {
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				
			}else if(id3.equals("")){   // 1 2 
				//Toast.makeText(LeaveandovertimeAdd.this, "成功2", Toast.LENGTH_SHORT).show();
				//提交
				HttpFileUpTool hfu = new HttpFileUpTool();
				String actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
				String param1 ="leaveOrOtKindbean.approvalID1="+id1+"&leaveOrOtKindbean.approvalID2="+id2+"&leaveOrOtKindbean.approvalID3=0&leaveOrOtKindbean.leaveOrOtUser="+userid+"&leaveOrOtKindbean.kindName="+kindname+"&leaveOrOtKindbean.leaveOrOtReason="+Reason+"&leaveOrOtKindbean.beginDate="+begintime+"&leaveOrOtKindbean.endDate="+endtime;
				System.out.println("param===="+param1);
				try {
					hfu.sendPost1(actionUrl, param1);
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存成功", Toast.LENGTH_SHORT).show();
					if(kind.equals("2")){
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeOvertimeshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}else{
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeLeaveshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}	
				} catch (Exception e) {
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				
			}else {    // 1 2 3
				//Toast.makeText(LeaveandovertimeAdd.this, "成功3", Toast.LENGTH_SHORT).show();
				//提交
				HttpFileUpTool hfu = new HttpFileUpTool();
				String actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
				String param1 ="leaveOrOtKindbean.approvalID1="+id1+"&leaveOrOtKindbean.approvalID2="+id2+"&leaveOrOtKindbean.approvalID3="+id3+"&leaveOrOtKindbean.leaveOrOtUser="+userid+"&leaveOrOtKindbean.kindName="+kindname+"&leaveOrOtKindbean.leaveOrOtReason="+Reason+"&leaveOrOtKindbean.beginDate="+begintime+"&leaveOrOtKindbean.endDate="+endtime;
				System.out.println("param===="+param1);
				try {
					hfu.sendPost1(actionUrl, param1);
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存成功", Toast.LENGTH_SHORT).show();
					if(kind.equals("2")){
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeOvertimeshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}else{
					Intent intent=new Intent(DangerousgoodsportlnAdd.this, LeaveandovertimeLeaveshow.class);
					intent.putExtra("userid", userid);
					startActivity(intent);
					finish();
					}	
				} catch (Exception e) {
					Toast.makeText(DangerousgoodsportlnAdd.this, "保存失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}*/
						

		}
			
		}
		
		public static boolean isNum(String str){
			return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
		
		//显示日期
		class ShowportTime implements OnClickListener {

			@Override
			public void onClick(View v) {

				new TimePickerDialog(DangerousgoodsportlnAdd.this, new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						timeList[3] = hourOfDay;
						timeList[4] = minute;
						ShowportTime.setText(timeList[0] + "-" + timeList[1] + "-"
								+ timeList[2]+" "+timeList[3]+":"+timeList[4]);
						//endDate.setText(timeList[3] + ":" + timeList[4]);
					}
				}, timeList[3], timeList[4], true).show();
				
				
				
				new DatePickerDialog(DangerousgoodsportlnAdd.this, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						if (year < 1901 || year > 2049) {
							// 不在查询范围内
							new AlertDialog.Builder(DangerousgoodsportlnAdd.this)
									.setTitle("错误日期")
									.setMessage("跳转日期范围(1901/1/1-2049/12/31)")
									.setPositiveButton("确认", null).show();
						} else {
							timeList[0] = year;
							timeList[1] = monthOfYear+1 ;
							timeList[2] = dayOfMonth;
						

						}
					}
				}, timeList[0], timeList[1]-1, timeList[2]).show();
				
				
			}

		}
		
		class ListTask extends AsyncTask<String ,Integer,String>{
			  ProgressDialog	pDialog	= null;
			  public ListTask(){
				  
			  }
		      public ListTask(Context context){
			  pDialog = ProgressDialog.show(context, "提示", "正在提交中，请稍候。。。", true); 
			  }
			  
			@Override
			protected String doInBackground(String... params) {
				HttpFileUpTool hfu = new HttpFileUpTool();
				String result="";
				try {
				
						hfu.sendPost1(actionUrl, param1);
						result="保存成功";
						Intent intent=new Intent(DangerousgoodsportlnAdd.this, DangerousgoodsportlnAddList.class);
						startActivity(intent);
						setResult(20);
						finish();
						
						
			
					
					
					
					
					
				} catch (Exception e) {
					 result="保存失败";
					e.printStackTrace();
				}
				return result ;
			}
			@Override
			protected void onPostExecute(String result) {
				if (pDialog != null)
					pDialog.dismiss();
				Toast.makeText(DangerousgoodsportlnAdd.this, result, Toast.LENGTH_SHORT).show();
				
				super.onPostExecute(result);
			}
			  
		  }
}
