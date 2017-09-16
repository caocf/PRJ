package com.huzhouport.electricreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ElectricReportNewShow extends Activity {
	private String reportID, index; // 航行电子报告id
	private Query query = new Query();
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv15, tv16,tv17,tv18,tv_prompt;
	private String  declareTime;
	private ImageButton img_update, showupdate;
	private User user;
	private RelativeLayout tr1, tr2;
	private ListView lv_boatman;
	private ImageView img_abnormal;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreportnew_show);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		reportID = intent.getStringExtra("reportID");
		index = intent.getStringExtra("index");
		
		img_abnormal=(ImageView)findViewById(R.id.electricreportnew_abnormal_img);
		tv_prompt=(TextView)findViewById(R.id.electricreport_see_prompt);
		tv1 = (TextView) findViewById(R.id.electricreport_see_name);
		tv2 = (TextView) findViewById(R.id.electricreportnew_show_kind);
		tv3 = (TextView) findViewById(R.id.electricreport_see_shipUserName);
		tv4 = (TextView) findViewById(R.id.electricreport_see_arrival);
		tv5 = (TextView) findViewById(R.id.electricreport_show_address1);
		tv6 = (TextView) findViewById(R.id.electricreport_show_address2);
		tv7 = (TextView) findViewById(R.id.electricreport_see_type);
		tv8 = (TextView) findViewById(R.id.electricreport_see_cargoNumber);
		tv9 = (TextView) findViewById(R.id.electricreport_show_pier);
		tv10 = (TextView) findViewById(R.id.electricreport_show_arrivaltime);
		tv11 = (TextView) findViewById(R.id.electricreport_show_draft);
		tv15 = (TextView) findViewById(R.id.electricreport_show_address_old);
		tv16 = (TextView) findViewById(R.id.electricreport_show_arrivaltime_old);
		tv17 = (TextView) findViewById(R.id.electricreport_show_drafttime);
		tv18 = (TextView) findViewById(R.id.electricreport_see_outorin);
		tr1 = (RelativeLayout) findViewById(R.id.eps_oldport);
		tr2 = (RelativeLayout) findViewById(R.id.eps_oldtime);
		ImageButton back = (ImageButton) findViewById(R.id.electricreport_see_back);
		showupdate = (ImageButton) findViewById(R.id.electricreport_see_update);
		back.setOnClickListener(new Back());
		lv_boatman=(ListView) findViewById(R.id.electricreport_see_listview);
		img_update = (ImageButton) findViewById(R.id.electricreport_show_update);
		img_update.setOnClickListener(new Update());
		showupdate.setOnClickListener(new ShowUpdate());
		
		ListTask task = new ListTask(this); // 异步
		task.execute();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (170 == resultCode) {
			setResult(170);
			finish();
		}
	}

	class Back implements View.OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}

	class Update implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewShow.this,
					ElectricReportNewUpdate.class);
			intent.putExtra("User", user);
			intent.putExtra("reportID", reportID);
			startActivityForResult(intent, 100);

		}
	}

	class ShowUpdate implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewShow.this,
					ElectricReportChange.class);
			intent.putExtra("User", user);
			intent.putExtra("reportID", reportID);
			intent.putExtra("oldPort", tv6.getText().toString());
			intent.putExtra("oldTime", tv10.getText().toString());
			startActivityForResult(intent, 100);

		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			pDialog = new ProgressDialog(ElectricReportNewShow.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			String result = query.ElectricReportByReportId(reportID);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();

			getNeirong(result);
			if (index.equals("1")) {
				showupdate.setVisibility(View.VISIBLE);
				new PublicTime(ElectricReportNewShow.this).execute(); // 获取当地时间
			}
			new GetOldDate().execute();
			super.onPostExecute(result);
		}

	}

	public void getNeirong(String result) {

		System.out.println(result);
		
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jArray = data.getJSONArray("list");
			if(jArray.length()>0){
				JSONObject jsonArray = (JSONObject) jArray.opt(0);
			tv1.setText(jsonArray.getString("shipName"));
			if (jsonArray.getInt("reportKind") == 1) {
				tv2.setText("重船航行");
				tv7.setText(jsonArray.getString("cargoType"));
				tv8.setText(jsonArray.getString("cargoNumber")
						+ jsonArray.getString("uniti"));
			} else {
				tv2.setText("空船航行");
				findViewById(R.id.electricreport_show_tr1).setVisibility(
						View.GONE);
				findViewById(R.id.electricreport_show_tr2).setVisibility(
						View.GONE);
			}
			if(jsonArray.getInt("abnormal")==1){
				img_abnormal.setImageDrawable(getResources().getDrawable(R.drawable.isnormal));
			}else{
				img_abnormal.setImageDrawable(getResources().getDrawable(R.drawable.abnormal));
			}
			if(!jsonArray.getString("prompt").equals("null")){
				tv_prompt.setText(Html.fromHtml(jsonArray.getString("prompt")));
			}
			tv3.setText(jsonArray.getString("shipUserName"));
			tv4.setText(CountTime.FormatTime2(jsonArray.getString("reportTime")));
			tv5.setText(jsonArray.getString("startingPort"));
			tv6.setText(jsonArray.getString("arrivalPort"));
			if(tv5.getText().toString().indexOf("湖州")<0&&tv6.getText().toString().indexOf("湖州")<0){
				((RelativeLayout)findViewById(R.id.electricreport_rl_outorin)).setVisibility(View.GONE);
				((RelativeLayout)findViewById(R.id.electricreport_rl_wharf)).setVisibility(View.GONE);
			}else{
				((RelativeLayout)findViewById(R.id.electricreport_rl_outorin)).setVisibility(View.VISIBLE);
				((RelativeLayout)findViewById(R.id.electricreport_rl_wharf)).setVisibility(View.VISIBLE);
			}
			
			tv9.setText(jsonArray.getString("toBeDockedAtThePier"));
			tv10.setText(CountTime.FormatTime2(jsonArray.getString("estimatedTimeOfArrival")));
			if(jsonArray.getString("draft").length()!=0)
				tv11.setText(jsonArray.getString("draft")+"升");
			if(jsonArray.getInt("outOrIn")==1){
				tv18.setText("进港");
			}else{
				tv18.setText("出港");
			}
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

				lv_boatman.setAdapter(adapter);
			}
			
			
			tv17.setText(CountTime.FormatTime2(jsonArray.getString("draftTime")));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}



		// 历史航线调整记录
		class GetOldDate extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... params) {

				return query.GetOldReport(reportID);
			}

			@Override
			protected void onPostExecute(String result) {
				if(result!=null){
					JSONTokener jsonParser_User = new JSONTokener(result);
					try {
						JSONObject data = (JSONObject) jsonParser_User.nextValue();
						JSONArray listArray=data.getJSONArray("list");
						
						if (listArray.length()>0) {
							JSONObject list1=(JSONObject) listArray.opt(0);
							tv16.setText(CountTime.FormatTime2(list1.getString("arrivalTime")));
							tv15.setText(list1.getString("arrivalPort"));
							tr1.setVisibility(View.VISIBLE); 
							tr2.setVisibility(View.VISIBLE); 
						}
						
					}catch (Exception e) {
						
							e.printStackTrace();
						}
				}
				 ScrollView scroller = (ScrollView) findViewById(R.id.leaveandovertime_newlist_view_scroll);
		          scroller.scrollTo(0, 0);
				super.onPostExecute(result);
			}
		}

		
	

	class PublicTime extends AsyncTask<String, Integer, String> {

		public PublicTime() {

		}

		public PublicTime(Context context) {
		}

		@Override
		protected String doInBackground(String... params) {
			return query.GetServiceTime();
		}

		@Override
		protected void onPostExecute(String result) {
			GetTime(result);// 获得数据
			super.onPostExecute(result);
		}

	}

	public void GetTime(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime");

			int trueandflase = Bigtime(tv4.getText().toString(), declareTime); // 是否超过1天
			if (trueandflase == 1) {
				img_update.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			Toast.makeText(ElectricReportNewShow.this, "获取时间失败",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public int Bigtime(String time2, String time1) { // 上次登录的时间 ，这次登录的时间
		int r = 0;
		long quot = 0;
		long quot1 = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH",Locale.getDefault());
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot1 = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24; // 计算几天
			quot1 = quot1 % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);// 计算差多少小时

			int lt = (int) quot;
			if (lt < 1) {
				r = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}
}
