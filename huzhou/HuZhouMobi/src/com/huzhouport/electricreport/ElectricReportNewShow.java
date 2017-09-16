package com.huzhouport.electricreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ElectricReportNewShow extends Activity {
	private String reportID; //航行电子报告id
	private Query								query	= new Query();
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13;
	private ListView list_boatman;
	private String ShipName;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreportnew_show);
		Intent intent = getIntent();
		reportID = intent.getStringExtra("reportID");
		
		
		ListTask task = new ListTask(this); // 异步
		task.execute();
		
		tv1=(TextView) findViewById(R.id.electricreport_see_name);
		tv2=(TextView) findViewById(R.id.electricreportnew_show_kind);
		tv3=(TextView) findViewById(R.id.electricreport_see_shipUserName);
		tv4=(TextView) findViewById(R.id.electricreport_see_arrival);
		tv5=(TextView) findViewById(R.id.electricreport_show_address1);
		tv6=(TextView) findViewById(R.id.electricreport_show_address2);
		tv7=(TextView) findViewById(R.id.electricreport_see_type);
		tv8=(TextView) findViewById(R.id.electricreport_see_cargoNumber);
		tv9=(TextView) findViewById(R.id.electricreport_show_pier);
		tv10=(TextView) findViewById(R.id.electricreport_show_arrivaltime);
		tv11=(TextView) findViewById(R.id.electricreport_show_draft);
		tv12=(TextView) findViewById(R.id.electricreport_show_drafttime);
		tv13= (TextView) findViewById(R.id.electricreport_see_outorin);
		list_boatman=(ListView) findViewById(R.id.electricreport_show_boatman);
		
		ImageButton back=(ImageButton)findViewById(R.id.electricreport_see_back);
	    back.setOnClickListener(new Back());
	}
	
	class Back implements View.OnClickListener{
		 public void onClick(View v){
			
			finish();
		 }
	 }
	

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;
		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			pDialog = new ProgressDialog(ElectricReportNewShow.this);
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
		protected String doInBackground(String... params)
		{
			if(isCancelled()) return null;//取消异步

			String result = query.ElectricReportByReportId(reportID);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			
			getNeirong(result);
			BoatmanShow task = new BoatmanShow();  // 异步
	        task.execute();
			super.onPostExecute(result);
		}

	}
	public void getNeirong(String result){
		
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jArray = data.getJSONArray("list");
			if(jArray.length()>0){
				JSONObject jsonArray = (JSONObject) jArray.opt(0);
				ShipName=jsonArray.getString("shipName");
				tv1.setText(jsonArray.getString("shipName"));
				if(jsonArray.getInt("reportKind")==1){
					tv2.setText("重船航行");
					tv7.setText(jsonArray.getString("cargoType"));
					tv8.setText(jsonArray.getString("cargoNumber")+jsonArray.getString("uniti"));
				}else{
					tv2.setText("空船航行");
					findViewById(R.id.electricreport_show_tr1).setVisibility(View.GONE);
					findViewById(R.id.electricreport_show_tr2).setVisibility(View.GONE);
				}
				tv3.setText(jsonArray.getString("shipUserName"));
				tv4.setText(jsonArray.getString("reportTime").substring(0,jsonArray.getString("reportTime").lastIndexOf(":")));
				tv5.setText(jsonArray.getString("startingPort"));
				tv6.setText(jsonArray.getString("arrivalPort"));
				if(tv5.getText().toString().indexOf("湖州")<0&&tv6.getText().toString().indexOf("湖州")<0){
					((TableRow)findViewById(R.id.electricreport_rl_outorin)).setVisibility(View.GONE);
					((TableRow)findViewById(R.id.electricreport_rl_wharf)).setVisibility(View.GONE);
				}else{
					((TableRow)findViewById(R.id.electricreport_rl_outorin)).setVisibility(View.VISIBLE);
					((TableRow)findViewById(R.id.electricreport_rl_wharf)).setVisibility(View.VISIBLE);
				}
				tv9.setText(jsonArray.getString("toBeDockedAtThePier"));
				tv10.setText(jsonArray.getString("estimatedTimeOfArrival").substring(0,jsonArray.getString("estimatedTimeOfArrival").lastIndexOf(":")));
				if(jsonArray.getInt("outOrIn")==1){
					tv13.setText("进港");
				}else{
					tv13.setText("出港");
				}
				if(jsonArray.getString("draft").length()!=0)
					tv11.setText(jsonArray.getString("draft")+"升");
				tv12.setText(CountTime.FormatTime2(jsonArray.getString("draftTime")));
				String shipInfo=jsonArray.getString("shipInfo");
				if(!shipInfo.equals("null")){
					String[] sul=shipInfo.split(";");
					ArrayList<Map<String, String>> listv = new ArrayList<Map<String, String>>();
					for(int i=0;i<sul.length;i++){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value",sul[i].split(",")[0]+":"+sul[i].split(",")[1]+"("+sul[i].split(",")[2]+":"+sul[i].split(",")[3]+")");
					listv.add(map);
					}
					SimpleAdapter adapter = new SimpleAdapter(ElectricReportNewShow.this, listv,
							R.layout.electric_item,
							new String[] {"value"}, new int[] {
									R.id.electric_item});

					list_boatman.setAdapter(adapter);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	class BoatmanShow extends AsyncTask<String ,Integer,String>{
		 
		
		@Override
		protected String  doInBackground(String... params) {
			if(isCancelled()) return null;//取消异步
			HttpFileUpTool hft=new HttpFileUpTool();
			Map<String, Object> p=new HashMap<String, Object>();
						String result = null;
						try {
							p.put("boatman.boatmanShip", ShipName);
							result = hft.post(HttpUtil.BASE_URL+"ShowBoatUserInfo", p);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			
			getBoatman(result);//获得数据
			 ScrollView scroller = (ScrollView) findViewById(R.id.leaveandovertime_newlist_view_scroll);
	          scroller.scrollTo(0, 0);
			super.onPostExecute(result);
		}
		  
	 
	public void getBoatman(String result){
			
			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray=data.getJSONArray("list");//list_boatman
					for(int i=0;i<jsonArray.length();i++){
						/*JSONArray jb0=(JSONArray) jsonArray.opt(0);
						JSONArray jb1=(JSONArray) jsonArray.opt(1);
						JSONArray jb2=(JSONArray) jsonArray.opt(2);
						if(jb0.get(0).toString()!=null&&jb0.get(0).toString()!="null"){
						tv12.setText(jb0.get(0).toString());
						}
						if(jb1.get(0).toString()!=null&&jb1.get(0).toString()!="null"){
						tv13.setText(jb1.get(0).toString());
						}
						if(jb2.get(0).toString()!=null&&jb2.get(0).toString()!="null"){
						tv14.setText(jb2.get(0).toString());
						}*/
					}
				  
				} catch (Exception e) {
				Toast.makeText(ElectricReportNewShow.this, "没有船员信息", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
	}
	}
}
