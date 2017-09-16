package com.huzhouport.dangerousgoodsjob;






import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;





import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;









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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;



import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DangerousgoodsjobAdd extends Activity {
	private String shipName;
	private Query query = new Query();
	private ArrayList<HashMap<String,Object>> dangerousgoodsjob;
	private String content;//搜索框内容
	private String name;
	private ListView lv;
	private Spinner spinner_kind; //下拉框
	private ClearEditText searchtext; //搜索框
	private int[] timeList = new int[5];
	private TextView ShowworkTIme;
	private String actionUrl,param1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_add_add);

		
		Intent intent = getIntent();
		shipName=intent.getStringExtra("shipName");
		
		findViewById(R.id.dangerousgoodsjob_leaveaddrelat1).setVisibility(View.GONE);
		
		//下拉框
		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsjob_add_DangerousLevelNeirong);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.dangerousgoodsportln_DangerousLevel_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);
		
	//选择作业码头
   ImageButton selectwharfName=(ImageButton) findViewById(R.id.dangerousgoodsjob_add_wharfNameimg);
	selectwharfName.setOnClickListener(new SelectwharfName());
	
	  
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
	 Date   curDate=new Date();
	String   str = formatter.format(curDate);
	
	
	TextView shipNameText=(TextView)findViewById(R.id.dangerousgoodsjob_add_shipNameNeirong);
	shipNameText.setText(shipName);
	TextView declareTimeText=(TextView)findViewById(R.id.dangerousgoodsjob_add_declareTimeNeirong);
    declareTimeText.setText(str);
	
	
    String[] substring = str.split(" "); 
    System.out.println("time==="+str);
    System.out.println("time==="+substring[0]);
    System.out.println("time==="+substring[1]);
    String[] substring1 = substring[0].split("-");
    String[] substring2 = substring[1].split(":");
    timeList[0] = Integer.parseInt(substring1[0]);
    timeList[1] = Integer.parseInt(substring1[1]);
    timeList[2] = Integer.parseInt(substring1[2]);
    timeList[3] = Integer.parseInt(substring2[0]);
    timeList[4] = Integer.parseInt(substring2[1]);
	
    
    
	
	//申请作业时间
	ShowworkTIme=(TextView) findViewById(R.id.dangerousgoodsjob_add_workTImeNeirong);
	ShowworkTIme.setOnClickListener(new ShowworkTIme());
	
	
		
		
	lv=(ListView) findViewById(R.id.dangerousgoodsjob_add_viewlist);
		

	ImageButton back=(ImageButton)findViewById(R.id.dangerousgoodsjob_addback);
	back.setOnClickListener(new Back());
	//用户选择返回
	ImageButton selectback=(ImageButton)findViewById(R.id.dangerousgoodsjob_wharfName_back);
	selectback.setOnClickListener(new Selectback());	
		
	
	//搜索的图标	
 	ImageButton search=(ImageButton)findViewById(R.id.dangerousgoodsjob_wharfName_search);
 	search.setOnClickListener(new Search());
 	
 	searchtext=(ClearEditText) findViewById(R.id.dangerousgoodsjob_wharfName_searchtext);
 	searchtext.addTextChangedListener(new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String text=searchtext.getText().toString();
			System.out.println("searchtext=="+text);
			
			if(text.length()==0){
				//System.out.println("searchtext====1");
				
				findViewById(R.id.dangerousgoodsjob_wharfName_search1).setVisibility(View.VISIBLE);
				findViewById(R.id.dangerousgoodsjob_wharfName_search2).setVisibility(View.GONE);
				content=null;
				ListTask1 task1 = new ListTask1(DangerousgoodsjobAdd.this);  // 异步
		        task1.execute();
			}else{
				//name=text;
				findViewById(R.id.dangerousgoodsjob_wharfName_search2).setVisibility(View.VISIBLE);
				findViewById(R.id.dangerousgoodsjob_wharfName_search1).setVisibility(View.GONE);
			}
			
		}
 		
 	});
 	
 	//取消
 			Button quxiao=(Button)findViewById(R.id.dangerousgoodsjob_wharfName_search1);
 			quxiao.setOnClickListener(new Quxiao());
 	//搜索按钮
			Button search1=(Button)findViewById(R.id.dangerousgoodsjob_wharfName_search2);
			search1.setOnClickListener(new SearchButton());
 	
	
	
	
		
	
	//提交按钮
			ImageButton sumbit=(ImageButton) findViewById(R.id.dangerousgoodsjob_add_submit);
			sumbit.setOnClickListener(new Sumbit());
		
			
	}
	








	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	  public class Selectback implements View.OnClickListener{
			public void onClick(View v){
				findViewById(R.id.dangerousgoodsjob_leaveaddrelat1).setVisibility(View.GONE);
				findViewById(R.id.dangerousgoodsjob_add_addtable).setVisibility(View.VISIBLE);
				findViewById(R.id.dangerousgoodsjob_leaveaddtiteltable).setVisibility(View.VISIBLE);
			}
		}	
	  public class Search implements View.OnClickListener{
			public void onClick(View v){
				findViewById(R.id.dangerousgoodsjob_wharfName_titeltable1).setVisibility(View.VISIBLE);
			}
		}
	  public class Quxiao implements View.OnClickListener{
			public void onClick(View v){
				//findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
				findViewById(R.id.dangerousgoodsjob_wharfName_titeltable1).setVisibility(View.GONE);
			}
		}
	  public class SearchButton implements View.OnClickListener{
			public void onClick(View v){
			content=searchtext.getText().toString();
			ListTask1 task1 = new ListTask1(DangerousgoodsjobAdd.this);  // 异步
	        task1.execute();
					
			
			}
		}

	public class SelectwharfName implements View.OnClickListener{
		public void onClick(View v){
			findViewById(R.id.dangerousgoodsjob_leaveaddrelat1).setVisibility(View.VISIBLE);
			findViewById(R.id.dangerousgoodsjob_add_addtable).setVisibility(View.GONE);
			findViewById(R.id.dangerousgoodsjob_leaveaddtiteltable).setVisibility(View.GONE);
			
			content=null;
			
			ListTask1 task1 = new ListTask1(DangerousgoodsjobAdd.this);  // 异步
	        task1.execute();
			
		
		}
	}

	


		class Sumbit implements OnClickListener{

		
			public void onClick(View v) {
				//获得要传的值
			TextView declareTime=(TextView)findViewById(R.id.dangerousgoodsjob_add_declareTimeNeirong);	
			EditText startingPortName=(EditText) findViewById(R.id.dangerousgoodsjob_add_StartingPortNeirong);	
			EditText arrivalPortName=(EditText) findViewById(R.id.dangerousgoodsjob_add_ArrivalPortNeirong);
			EditText cargoTypes=(EditText) findViewById(R.id.dangerousgoodsjob_add_CargoTypesNeirong);
			TextView wharfID=(TextView) findViewById(R.id.dangerousgoodsjob_add_wharfID);
			TextView workTIme=(TextView) findViewById(R.id.dangerousgoodsjob_add_workTImeNeirong);
			EditText carrying=(EditText) findViewById(R.id.dangerousgoodsjob_add_CarryingNeirong);
			
			String declareTime1=declareTime.getText().toString();
			String startingPortName1=startingPortName.getText().toString();
			String arrivalPortName1=arrivalPortName.getText().toString();
			String cargoTypes1=cargoTypes.getText().toString();
			String dangerousLevel=spinner_kind.getSelectedItem().toString();
			String wharfID1=wharfID.getText().toString();
			String workTIme1=workTIme.getText().toString();
			String carrying1=carrying.getText().toString();
		   if(startingPortName1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请填写起运港", Toast.LENGTH_SHORT).show();   
		   }else if(arrivalPortName1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请填写目的港", Toast.LENGTH_SHORT).show();   
		   }else if(cargoTypes1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请填写货物名称", Toast.LENGTH_SHORT).show(); 
		   }else if(wharfID1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请选择作业码头名称", Toast.LENGTH_SHORT).show(); 
		   }else if(workTIme1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请填写申请作业时间", Toast.LENGTH_SHORT).show(); 
		   }else if(carrying1.equals("")){
			   Toast.makeText(DangerousgoodsjobAdd.this, "请填写载重", Toast.LENGTH_SHORT).show(); 
		   }else if(!isNum(carrying1)){
			   Toast.makeText(DangerousgoodsjobAdd.this, "载重必须是数字", Toast.LENGTH_SHORT).show(); 
		   }else{
			   
				 actionUrl = HttpUtil.BASE_URL + "newDangerousGoodsJob";
				param1="dangerousWorkDeclareBean.shipName="+shipName+"&dangerousWorkDeclareBean.declareTime="+declareTime1+"&dangerousWorkDeclareBean.startingPortName="+startingPortName1+"&dangerousWorkDeclareBean.arrivalPortName="+arrivalPortName1+"&dangerousWorkDeclareBean.cargoTypes="+cargoTypes1+"&dangerousWorkDeclareBean.dangerousLevel="+dangerousLevel+"&dangerousWorkDeclareBean.wharfID="+wharfID1+"&dangerousWorkDeclareBean.workTIme="+workTIme1+"&dangerousWorkDeclareBean.carrying="+carrying1;
				
				ListTask task = new ListTask(DangerousgoodsjobAdd.this);  // 异步
			    task.execute();
			   
			   
		   }

		}
			
		}
		//判断是否是数字
		public static boolean isNum(String str){
			return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
		
		public void findWharfJobNum(String result){
		
			JSONTokener jsonParser =new JSONTokener(result);
			JSONObject data;
			try {
				data=(JSONObject) jsonParser.nextValue();
				JSONArray jsonArray=data.getJSONArray("list2");
				System.out.println("jsonArray==="+jsonArray.length()+jsonArray);
				 if(jsonArray.length()==0){
				   Toast.makeText(DangerousgoodsjobAdd.this, R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
				 }{
					 dangerousgoodsjob=new ArrayList<HashMap<String, Object>>();
						for(int i=0;i<jsonArray.length();i++){
						HashMap<String, Object> dangerousgoodsjobmap = new HashMap<String, Object>();	
						JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
						String text1=jsonObject.getString("wharfID");
						String text2=jsonObject.getString("wharfName");
						
						
						dangerousgoodsjobmap.put("text1", text1);
						dangerousgoodsjobmap.put("text2", text2);
						
					    dangerousgoodsjob.add(dangerousgoodsjobmap);
				}
			}
			} catch (Exception e) {
				Toast.makeText(DangerousgoodsjobAdd.this, "请检查网络", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
		}
		  public void ShowWharfJobNum(){
			  System.out.println("dangerousgoodsjob=="+dangerousgoodsjob);
			  SimpleAdapter adapter=new SimpleAdapter(DangerousgoodsjobAdd.this,dangerousgoodsjob,R.layout.dangerousgoodsjob_add_additem,new String[]{"text1","text2"},new int[]{R.id.dangerousgoodsjob_wharfID,R.id.dangerousgoodsjob_addwharfName});
			   lv.setAdapter(adapter);
		      lv.setOnItemClickListener(new DangerousgoodsjobItem());
		  }
		
		  class DangerousgoodsjobItem implements OnItemClickListener{

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					TextView tv_id=(TextView) arg1.findViewById(R.id.dangerousgoodsjob_wharfID);
					TextView tv_name=(TextView) arg1.findViewById(R.id.dangerousgoodsjob_addwharfName); 
					TextView tv_id1=(TextView)findViewById(R.id.dangerousgoodsjob_add_wharfID);
					TextView tv_name1=(TextView)findViewById(R.id.dangerousgoodsjob_add_wharfNameNeirong); 
					String id=tv_id.getText().toString();
					String name=tv_name.getText().toString();
					System.out.println("id==="+id+" name=="+name);
					tv_id1.setText(id);
					tv_name1.setText(name);
				     
					findViewById(R.id.dangerousgoodsjob_leaveaddrelat1).setVisibility(View.GONE);
					findViewById(R.id.dangerousgoodsjob_add_addtable).setVisibility(View.VISIBLE);
					findViewById(R.id.dangerousgoodsjob_leaveaddtiteltable).setVisibility(View.VISIBLE);
				}
				  
			  }
		  
		//显示日期
			class ShowworkTIme implements OnClickListener {

				@Override
				public void onClick(View v) {

					new TimePickerDialog(DangerousgoodsjobAdd.this, new OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							timeList[3] = hourOfDay;
							timeList[4] = minute;
							ShowworkTIme.setText(timeList[0] + "-" + timeList[1] + "-"
									+ timeList[2]+" "+timeList[3]+":"+timeList[4]);
							//endDate.setText(timeList[3] + ":" + timeList[4]);
						}
					}, timeList[3], timeList[4], true).show();
					
					
					
					new DatePickerDialog(DangerousgoodsjobAdd.this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {

							if (year < 1901 || year > 2049) {
								// 不在查询范围内
								new AlertDialog.Builder(DangerousgoodsjobAdd.this)
										.setTitle("错误日期")
										.setMessage("跳转日期范围(1901/1/1-2049/12/31)")
										.setPositiveButton("确认", null).show();
							} else {
								timeList[0] = year;
								timeList[1] = monthOfYear+1;
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
							Intent intent=new Intent(DangerousgoodsjobAdd.this, DangerousgoodsjobAddList.class);
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
					Toast.makeText(DangerousgoodsjobAdd.this, result, Toast.LENGTH_SHORT).show();
					
					super.onPostExecute(result);
				}
				  
			  }  
			
			class ListTask1 extends AsyncTask<String ,Integer,String>{
				  ProgressDialog	pDialog	= null;
				  public ListTask1(){
					  
				  }
			      public ListTask1(Context context){
				  pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
				  }
				  
				@Override
				protected String  doInBackground(String... params) {
				//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
				//showDangerousgoodsportln();
					
					String result;
					if(content==null){
						result=query.findWharfJobNum();
						}else{
						result=query.selectWharfJobNum(content);	
						}
					
					
					return result;
				}
				@Override
				protected void onPostExecute(String result) {
					if (pDialog != null)
						pDialog.dismiss();
					//得到数据
					findWharfJobNum(result);
				   //显示
					ShowWharfJobNum();
					super.onPostExecute(result);
				}
				  
			  }
			
			
}
