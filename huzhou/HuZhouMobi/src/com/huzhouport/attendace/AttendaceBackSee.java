package com.huzhouport.attendace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;

public class AttendaceBackSee extends Activity {
	private Query query = new Query();
	MapView mapView;
	//private User user;
	private String signID;
	private ImageButton inback;
	private TextView inaddress,intime;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendace_map_signback_see);
		mapView = (MapView) findViewById(R.id.attendace_signback_inmap_bmapView);		
		inback = (ImageButton)findViewById(R.id.attendace_signback_inmap);
		//TextView intext = (TextView)findViewById(R.id.attendace_signback_inmap_titleText);
		inaddress = (TextView)findViewById(R.id.attendace_signback_localtextsignin);
		intime = (TextView)findViewById(R.id.attendace_signback_datesignin);
		Intent intent = getIntent();
		//user = (User) intent.getSerializableExtra("User");
		signID=intent.getStringExtra("signID");
		inback.setOnClickListener(new MyBack());
		ListTask task = new ListTask(this);  // 异步
        task.execute();
	}
	
	public void getAttendaceInSee(String result){
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();			
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			for(int i=0;i<jsonArray.length();i++)
			{
				
				JSONObject jsonObject = (JSONObject) jsonArray.opt(0);
				//JSONObject jsonObject1 = (JSONObject) jsonArray.opt(1);
				System.out.println("   lkk"+jsonObject.getInt("latitude"));
				/*GeoPoint geoPoint=mapTool.getGeoPoint(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
				mapTool.setCenter(geoPoint);
				mapTool.drawPoint(geoPoint);*/
				String valueString=changeYMDHMS(jsonObject.get("signTime").toString()).substring(0, 16);
				inaddress.setText(jsonObject.getString("locationName"));
				intime.setText(valueString);
				
				
				
			}
		} catch (Exception e) {
			Toast.makeText(AttendaceBackSee.this, "获取签到信息失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
	}
	public static String changeYMDHMS(String value) {
		String sRet = "";
		if(null!=value){
		return value.replace("T", " ");
		}
		return sRet;
	}

//	//获取位置信息
//			public void getLocationFromService(String result) {
//				JSONTokener jsonParser = new JSONTokener(result);
//				JSONObject data;
//				try {
//					data = (JSONObject) jsonParser.nextValue();
//					JSONArray jsonArray = data.getJSONArray("list");
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
//						GeoPoint geoPoint=new GeoPoint(jsonObject.getInt("latitude"), jsonObject.getInt("longitude"));
//						mapTool.setCenter(geoPoint);
//						mapTool.drawPoint(geoPoint);
//					}
//				} catch (Exception e) {
//					Toast.makeText(AttendaceInSee.this, "位置信息获取失败", Toast.LENGTH_SHORT)
//							.show();
//					e.printStackTrace();
//				}
//			}
	class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      public ListTask(Context context){
		  pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
			
			String result;
			result=query.ShowAttendaceListInId(signID);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getAttendaceInSee(result);
			super.onPostExecute(result);
		}
		  
	  }
	
	class MyBack implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			finish();	
		}	
		
	}
	@Override
	protected void onDestroy() {
		mapView.onDestroy();	
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();	
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();		
		super.onResume();
	}
}

