package com.huzhouport.attendace;

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

public class AttendaceSignIn extends Activity
{

	private ImageButton inmib, locasin, bitin;;
	@SuppressWarnings("unused")
	private TextView tmix, locatextsign, signindate;

	private Double lat, lon;
	private String loca;

	@SuppressWarnings("unused")
	private String userName;
	private int userId;

	User user;
	private ListTask task;
	private String actionUrl, param;
	String info;
	MapView mapView;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendace_map_sign_in);

		locasin = (ImageButton) findViewById(R.id.attendace_main_inmap_localtext);
		locatextsign = (TextView) findViewById(R.id.attendace_main_localtextsignin);
		// signindate = (TextView) findViewById(R.id.attendace_main_datesignin);

		bitin = (ImageButton) findViewById(R.id.attendace_mainin_inmap_button);
		inmib = (ImageButton) findViewById(R.id.attendace_main_inmap);
		/* imis = (ImageButton) findViewById(R.id.attendace_main_searchin); */
		tmix = (TextView) findViewById(R.id.attendace_main_inmap_titleText);

		// imis.setOnClickListener(new MyList());
		inmib.setOnClickListener(new MyBack());
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		userId = user.getUserId();
		userName = intent.getStringExtra("userName");

		locasin.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				locatextsign.setText(loca);
			}
		});
		
		bitin.setOnClickListener(new OnClickListener()
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
			Toast.makeText(AttendaceSignIn.this, "定位失败，无法签到",
					Toast.LENGTH_SHORT).show();
			return;
		}
		actionUrl = HttpUtil.BASE_URL + "addSignInfo";
		param = "location.longitude=" + lon + "&location.latitude=" + lat
				+ "&sign.signUser=" + userId
				+ "&sign.signStatus=0&location.locationName=" + loca;
		task = new ListTask(this); // 异步
		task.execute();
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
					Toast.makeText(AttendaceSignIn.this, info,
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(AttendaceSignIn.this,
							AttendaceList.class);
					intent.putExtra("User", user);
					startActivity(intent);
					setResult(20);
					finish();

					if(user!=null)
					{
					Log log = new Log(user.getName(), "签到", GlobalVar.LOGSAVE,
							""); // 日志
					log.execute();
					}

				} catch (Exception e)
				{
					result = "保存失败";
					e.printStackTrace();
				}

				// Toast.makeText(AttendaceSignIn.this, result,
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
		Intent i = new Intent(AttendaceSignIn.this, AttendaceList.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		mapView.onResume();
		Intent i = new Intent(AttendaceSignIn.this, AttendaceList.class);
		stopService(i);
		super.onResume();
	}

}
