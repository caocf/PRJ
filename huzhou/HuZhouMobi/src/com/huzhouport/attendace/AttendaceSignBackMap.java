package com.huzhouport.attendace;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapView;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

@SuppressLint("SimpleDateFormat")
public class AttendaceSignBackMap extends Activity
{

	// private Button bt;
	private ImageButton imb, localback, bt;
	private TextView locatextback;
	private Double lat, lon;
	private String loca;
	// private String userName;
	private int userId;
	User user;
	private ListTask task;
	private String actionUrl, param;
	String info;
	MapView mapView;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 声明在布局前
		setContentView(R.layout.attendace_map_sign_backmap);
		mapView = (MapView) findViewById(R.id.attendace_main_backmap_bmapview);
		bt = (ImageButton) findViewById(R.id.attendace_main_backmap_button);

		imb = (ImageButton) findViewById(R.id.attendace_main_backmap);
		localback = (ImageButton) findViewById(R.id.attendace_main_backmap_localtext);
		locatextback = (TextView) findViewById(R.id.attendace_main_backlocaltext);
		// dateback = (TextView) findViewById(R.id.attendace_main_dateback);
		// tmx = (TextView) findViewById(R.id.attendace_main_backmap_titleText);
		// ims.setOnClickListener(new MyList());
		imb.setOnClickListener(new MyBack());
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		userId = user.getUserId();
		// userName = intent.getStringExtra("userName");
		localback.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				locatextback.setText(loca);
			}
		});
		/*
		 * SimpleDateFormat formatter = new SimpleDateFormat
		 * ("yyyy年MM月dd日   HH:mm:ss     "); Date curDate = new
		 * Date(System.currentTimeMillis());//获取当前时间 String str =
		 * formatter.format(curDate); dateback.setText(str);
		 */
		bt.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				addAttendaceInfo();
			}
		});
	}

	public void addAttendaceInfo()
	{

		if (null == lon || lon == 0 || null == lat || lat == 0 || loca == null)
		{
			// 写提示语句
			Toast.makeText(AttendaceSignBackMap.this, "定位失败，无法签退",
					Toast.LENGTH_SHORT).show();
			return;
		}

		actionUrl = HttpUtil.BASE_URL + "addSignInfoBack";
		param = "location.longitude=" + lon + "&location.latitude=" + lat
				+ "&sign.signUser=" + userId
				+ "&sign.signStatus=1&location.locationName=" + loca;
		task = new ListTask(this); // 异步
		task.execute();
	}

	class MyList implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(AttendaceSignBackMap.this,
					AttendaceList.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
			finish();
		}
	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			finish();
		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{

			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = null;
			result = hfu.sendPost(actionUrl, param);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if (result != null)
			{
				try
				{
					JSONTokener jsonParser = new JSONTokener(result);
					JSONObject data;
					data = (JSONObject) jsonParser.nextValue();
					info = data.getString("info");
					Toast.makeText(AttendaceSignBackMap.this, info,
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(AttendaceSignBackMap.this,
							AttendaceList.class);
					intent.putExtra("User", user);
					startActivity(intent);
					setResult(20);
					finish();
					if(user!=null)
					{
					Log log = new Log(user.getName(), "签退", GlobalVar.LOGSAVE,
							""); // 日志
					log.execute();
					}

				} catch (Exception e)
				{
					result = "签退失败";
					e.printStackTrace();
				}

				// Toast.makeText(AttendaceSignBackMap.this, result,
				// Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		mapView.onPause();
		Intent i = new Intent(AttendaceSignBackMap.this, AttendaceList.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		mapView.onResume();
		Intent i = new Intent(AttendaceSignBackMap.this, AttendaceList.class);
		stopService(i);
		super.onResume();
	}


}
