package com.huzhouport.wharfwork;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.tool.SelectPicPopupWindow;
import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.electricreport.ElectricReportNewAddPort;
import com.huzhouport.map.MapTool;
import com.huzhouport.model.User;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WharfWorkAdd extends Activity
{

	MapTool mapTool;
	MapView mapView;
	private String wharfworkid;
	LocationData locationData = new LocationData();
	private Double lat, lon;

	private TextView address;
	private TextView goods, carryingtype, time, portmarttv,
			wharfWorkSurplusText;
	private String name, uniti, portmart;
	private String declareTime;
	private int[] timeList = new int[5];
	private ImageButton photoimg;
	private ImageView photoView;
	private String wharfWorkSurplus;

	private String photoname;

	private int photoid = 0; // 是否有图片
	private User user;
	private String username;
	private String actionUrl;
	Map<String, Object> paramsDtae = new HashMap<String, Object>();

	Map<String, File> files = new HashMap<String, File>();

	private TextView tv_start, tv_end;
	private Query query = new Query();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mapTool = new MapTool(getApplication());
		setContentView(R.layout.wharfwork_add);
		mapView = (MapView) findViewById(R.id.wharfwork_add_map);
		// 初始化地图
		mapTool.iniMapView(mapView, WharfWorkAdd.this);
		mapTool.registerLocationListen(locationListener);// 添加监听器
		mapTool.locationOnce();

		user = (User) getIntent().getSerializableExtra("User");
		wharfworkid = user.getWharfBindingList().get(user.getBindnum())
				.getWharfNumber();
		username = user.getUserName();

		portmarttv = (TextView) findViewById(R.id.wharfwork_add_portMartneirong);
		portmarttv.setOnClickListener(new Selectportmart());

		address = (TextView) findViewById(R.id.wharfwork_add_locationNameneirong);

		time = (TextView) findViewById(R.id.wharfwork_add_workTimeneirong);

		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_back);
		back.setOnClickListener(new Back());

		goods = (TextView) findViewById(R.id.wharfwork_add_cargoTypesneirong);
		goods.setOnClickListener(new Selectgoods());

		carryingtype = (TextView) findViewById(R.id.wharfwork_add_carrying_type);
		carryingtype.setOnClickListener(new AddUniti());

		tv_start = (TextView) findViewById(R.id.wharfwork_add_startingPortneirong);
		tv_end = (TextView) findViewById(R.id.wharfwork_add_arrivalPortneirong);
		tv_start.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(WharfWorkAdd.this,
						ElectricReportNewAddPort.class);
				intent.putExtra("kind", "1");
				startActivityForResult(intent, 20);
			}
		});
		tv_end.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(WharfWorkAdd.this,
						ElectricReportNewAddPort.class);
				intent.putExtra("kind", "2");
				startActivityForResult(intent, 20);
			}
		});

		photoView = (ImageView) findViewById(R.id.wharfwork_add_workPhoto);
		photoimg = (ImageButton) findViewById(R.id.wharfwork_add_bottom);
		photoimg.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (photoid == 0)
				{
					Intent getImageByCamera = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					startActivityForResult(getImageByCamera, 2);
				} else
				{
					Toast.makeText(WharfWorkAdd.this, "只能提交一张照片",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// 提交按钮
		ImageButton sumbit = (ImageButton) findViewById(R.id.wharfwork_add_submit);
		sumbit.setOnClickListener(new Sumbit());
		time.setOnClickListener(new Time());

		wharfWorkSurplusText = (TextView) findViewById(R.id.wharfwork_add_wharfWorkSurplus);
		TextView wharfworkidText = (TextView) findViewById(R.id.wharfwork_add_wharfworkid);
		wharfworkidText.setText("码头编号：" + wharfworkid);

		new ListTask(this).execute();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 可以根据多个请求代码来作相应的操作

		if (20 == resultCode)
		{
			portmart = data.getStringExtra("portmart");
			portmarttv.setText(portmart);

		}

		if (30 == resultCode)
		{
			name = data.getStringExtra("name");
			goods.setText(name);

		}
		if (40 == resultCode)
		{
			uniti = data.getStringExtra("unit");
			carryingtype.setText(uniti);

		}
		if (50 == resultCode)
		{
			if (data.getStringExtra("kind").equals("1"))
			{
				tv_start.setText(data.getStringExtra("name"));
			} else
			{
				tv_end.setText(data.getStringExtra("name"));
			}

		}

		// 拍照
		if (requestCode == 2 && resultCode == RESULT_OK && data != null)
		{
			String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
			photoname = oStrings[0] + ".jpg";

			files.put(oStrings[0] + ".jpg", new File(oStrings[1]));

			// 取照片

			if (data != null)
			{
				Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");

				photoView.setImageBitmap(cameraBitmap);
				photoView.setOnClickListener(new Photo());

				photoid = 1;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
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
			if (isCancelled())
				return null;

			String result = query.GetwharfWorkSurplus(user.getUserId() + "");

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			if (result == null)
			{
				Toast.makeText(WharfWorkAdd.this, "网络异常", Toast.LENGTH_SHORT)
						.show();
			} else
			{
				getwharfWorkSurplus(result);
				if (wharfWorkSurplus.equals("null"))
				{
					Toast.makeText(WharfWorkAdd.this, "请先设置码头定量",
							Toast.LENGTH_SHORT).show();
					wharfWorkSurplusText.setText("剩余吞吐量：");
				} else
				{
					wharfWorkSurplusText.setText("剩余吞吐量：" + wharfWorkSurplus
							+ "吨");
				}
			}

			super.onPostExecute(result);
		}

	}

	public void getwharfWorkSurplus(String result)
	{
		System.out.println("result" + result);
		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject wharfBinding = data.getJSONObject("wharfBinding");
			wharfWorkSurplus = wharfBinding.getString("wharfWorkSurplus");
		} catch (Exception e)
		{
			Toast.makeText(WharfWorkAdd.this, "网络异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class Photo implements View.OnClickListener
	{
		public void onClick(View v)
		{
			new AlertDialog.Builder(WharfWorkAdd.this)
					.setTitle("移除文件")
					.setMessage("移除" + photoname + "？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener()
							{

								public void onClick(DialogInterface dialog,
										int which)
								{
									photoView.setImageBitmap(null);
									photoid = 0;

									files = new HashMap<String, File>();
								}
							}).setNegativeButton("否", null).show();

		}
	}

	public static Bitmap drawable2Bitmap(Drawable drawable)
	{
		if (drawable instanceof BitmapDrawable)
		{
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable)
		{
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else
		{
			return null;
		}
	}

	class AddUniti implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(WharfWorkAdd.this,
					WharfWorkAddUniti.class);
			// intent.putExtra("User", user);
			startActivityForResult(intent, 80);
		}
	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{

			finish();
		}
	}

	public class Selectgoods implements View.OnClickListener
	{
		public void onClick(View v)
		{

			Intent intent = new Intent(WharfWorkAdd.this,
					WharfWorkAddGoods.class);

			startActivityForResult(intent, 100);

		}
	}

	public class Selectportmart implements View.OnClickListener
	{
		public void onClick(View v)
		{

			Intent intent = new Intent(WharfWorkAdd.this,
					WharfWorkAddPortMart.class);

			startActivityForResult(intent, 100);
		}
	}

	/**
	 * 添加监听器
	 */
	private BDLocationListener locationListener = new BDLocationListener()
	{

		@Override
		public void onReceivePoi(BDLocation location)
		{

		}

		@Override
		public void onReceiveLocation(BDLocation location)
		{

			if (location == null)
				return;
			if (mapView == null)
				return;
			else if (mapView.getOverlays() == null)
				return;

			MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mapView);

			locationData.latitude = location.getLatitude();
			locationData.longitude = location.getLongitude();
			lon = (double) location.getLongitude();
			lat = (double) location.getLatitude();

			myLocationOverlay.setData(locationData);

			mapView.getOverlays().add(myLocationOverlay);
			mapView.refresh();

			if (mapTool.mapConfig.getbKeyRight())
				mapView.getController().animateTo(
						new GeoPoint((int) (locationData.latitude * 1e6),
								(int) (locationData.longitude * 1e6)));

			address.setText(location.getAddrStr());

		}
	};

	public void GetTime(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime");

			// 进港时间
			String[] substring = declareTime.split(" ");
			String[] substring1 = substring[0].split("-");
			String[] substring2 = substring[1].split(":");
			timeList[0] = Integer.parseInt(substring1[0]);
			timeList[1] = Integer.parseInt(substring1[1]);
			timeList[2] = Integer.parseInt(substring1[2]);
			timeList[3] = Integer.parseInt(substring2[0]);
			timeList[4] = Integer.parseInt(substring2[1]);
			time.setText(declareTime.substring(0, declareTime.lastIndexOf(":")));
		} catch (Exception e)
		{
			Toast.makeText(WharfWorkAdd.this, "数据获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	// 显示日期
	class Time implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
					WharfWorkAdd.this, v, time);
			menuWindow.showAtLocation(v, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
		}

	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// 获得要传的值

			EditText shipName = (EditText) findViewById(R.id.wharfwork_add_shipNameneirong);
			TextView startingPortName = (TextView) findViewById(R.id.wharfwork_add_startingPortneirong);
			TextView arrivalPortName = (TextView) findViewById(R.id.wharfwork_add_arrivalPortneirong);
			EditText carrying = (EditText) findViewById(R.id.wharfwork_add_carryingneirong);
			TextView locationName = (TextView) findViewById(R.id.wharfwork_add_locationNameneirong);

			paramsDtae.put("wharfbean.wharfID", wharfworkid);
			paramsDtae.put("wharfbean.wharfWorkSurplus", wharfWorkSurplus);
			paramsDtae.put("wharfbean.shipName", shipName.getText().toString());
			paramsDtae.put("wharfbean.startingPortName", startingPortName
					.getText().toString());
			paramsDtae.put("wharfbean.arrivalPortName", arrivalPortName
					.getText().toString());

			if (portmarttv.getText().toString().equals("进港"))
			{
				paramsDtae.put("wharfbean.portMart", 1);
			} else
			{
				paramsDtae.put("wharfbean.portMart", 2);
			}

			paramsDtae.put("wharfbean.cargoTypes", goods.getText().toString());
			paramsDtae.put("wharfbean.carrying", carrying.getText().toString());
			paramsDtae
					.put("wharfbean.uniti", carryingtype.getText().toString());
			paramsDtae.put("wharfbean.workTime", time.getText().toString());

			paramsDtae.put("wharfbean.name", username);

			// GeoPoint gp = mapTool.getAddrLocation();
			if (lon == null || lon == 0 || lon.equals("") || lat == null
					|| lat == 0 || lat.equals(""))
			{
				Toast.makeText(WharfWorkAdd.this, "定位失败，无法提交",
						Toast.LENGTH_SHORT).show();
			} else
			{

				paramsDtae.put("wharfbean.longitude", lon);
				paramsDtae.put("wharfbean.latitude", lat);
				paramsDtae.put("wharfbean.locationName", locationName.getText()
						.toString());

				actionUrl = HttpUtil.BASE_URL + "addWharfWork";

				if (wharfWorkSurplus.equals("null"))
				{
					Toast.makeText(WharfWorkAdd.this, "剩余吞吐量不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (carrying.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "货物数量不能为空",
							Toast.LENGTH_SHORT).show();

				} else if (Integer.valueOf(wharfWorkSurplus)
						- Integer.valueOf(carrying.getText().toString()) <= 0)
				{
					Toast.makeText(WharfWorkAdd.this, "剩余吞吐量不能小于货物数量",
							Toast.LENGTH_SHORT).show();
				} else if (shipName.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请填写船名号",
							Toast.LENGTH_SHORT).show();
				} else if (startingPortName.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请填写起运港",
							Toast.LENGTH_SHORT).show();
				} else if (arrivalPortName.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请填写目的港",
							Toast.LENGTH_SHORT).show();
				} else if (goods.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请选择货物",
							Toast.LENGTH_SHORT).show();
				} else if (carrying.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请填写货物数量",
							Toast.LENGTH_SHORT).show();
				} else if (portmarttv.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请选择进出港标记",
							Toast.LENGTH_SHORT).show();
				} else if (time.getText().toString().equals(""))
				{
					Toast.makeText(WharfWorkAdd.this, "请选择作业时间",
							Toast.LENGTH_SHORT).show();
				} else
				{
					ListTask2 task2 = new ListTask2(WharfWorkAdd.this); // 异步
					task2.execute();
				}
			}
		}
	}

	class ListTask2 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask2(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this, "正在提交中，请稍候。。。");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			UploadActivity.tool.addFile("码头作业报告", actionUrl, paramsDtae, files,
					"wharfbean.ef");

			Intent intent = new Intent(WharfWorkAdd.this, WharfWorkList.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(20);
			finish();

			new Log(user, "添加码头作业报告", GlobalVar.LOGSAVE, "").execute();

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}

}
