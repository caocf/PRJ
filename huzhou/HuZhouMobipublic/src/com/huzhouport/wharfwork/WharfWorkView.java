package com.huzhouport.wharfwork;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GetBitmap;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.common.util.ShowImage;
import com.huzhouport.map.MapTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WharfWorkView extends Activity
{
	private Query query = new Query();
	private String id;
	MapTool mapTool;
	MapView mapView;
	private String workPhoto;
	private ImageView workPhotoText;
	private Bitmap pic;
	private Button bu;

	protected void onCreate(Bundle savedInstanceState)
	{
		// 声明在布局前
		mapTool = new MapTool(getApplication());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_view);

		workPhotoText = (ImageView) findViewById(R.id.wharfwork_view_workPhoto);
		bu = (Button) findViewById(R.id.wharfwork_view_button);
		id = getIntent().getStringExtra("id");

		mapView = (MapView) findViewById(R.id.wharfwork_view_map);
		// 初始化地图
		mapTool.iniMapView(mapView, WharfWorkView.this);
		mapTool.locationDefault();
		mapTool.locationOnce();

		new ListTask(this).execute();

		CommonListenerWrapper.commonBackWrapperListener(R.id.wharfwork_view_back,this);
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			String result = query.viewWharfWork(id);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
            if(result==null){
            	Toast.makeText(WharfWorkView.this,"网络异常", Toast.LENGTH_SHORT).show();
            }else{
			getNeirong(result);
            }
			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}

	public void getNeirong(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject jsonArray = data.getJSONObject("wharfbean");
			// System.out.println("jsonArray==="+jsonArray);

			GeoPoint geoPoint = new GeoPoint(jsonArray.getInt("latitude"),
					jsonArray.getInt("longitude"));

			System.out.println("latitude===" + jsonArray.getInt("latitude")
					+ "    " + jsonArray.getInt("longitude"));

			mapTool.setCenter(geoPoint);
			mapTool.drawPoint(geoPoint);

			String wharfID = "码头编号：" + jsonArray.getString("wharfID");
			TextView wharfIDText = (TextView) findViewById(R.id.wharfwork_view_wharfworkid);
			wharfIDText.setText(wharfID);

			String wharfWorkSurplus = "剩余吞吐量："
					+ jsonArray.getString("wharfWorkSurplus") + "吨";
			TextView wharfWorkSurplusText = (TextView) findViewById(R.id.wharfwork_view_wharfWorkSurplus);
			wharfWorkSurplusText.setText(wharfWorkSurplus);

			String name = jsonArray.getString("name");
			TextView nameText = (TextView) findViewById(R.id.wharfwork_view_nameneirong);
			nameText.setText(name);

			String shipName = jsonArray.getString("shipName");
			TextView shipNameText = (TextView) findViewById(R.id.wharfwork_view_shipNameneirong);
			shipNameText.setText(shipName);

			String startingPort = jsonArray.getString("startingPortName");
			TextView startingPortText = (TextView) findViewById(R.id.wharfwork_view_startingPortneirong);
			startingPortText.setText(startingPort);

			String arrivalPort = jsonArray.getString("arrivalPortName");
			TextView arrivalPortText = (TextView) findViewById(R.id.wharfwork_view_arrivalPortneirong);
			arrivalPortText.setText(arrivalPort);

			String cargoTypes = jsonArray.getString("cargoTypes");
			TextView cargoTypesText = (TextView) findViewById(R.id.wharfwork_view_cargoTypesneirong);
			cargoTypesText.setText(cargoTypes);

			String carrying = jsonArray.getString("carrying")
					+ jsonArray.getString("uniti");
			TextView carryingText = (TextView) findViewById(R.id.wharfwork_view_carryingneirong);
			carryingText.setText(carrying);

			String portMart = "";
			if (jsonArray.getString("portMart").equals("1"))
			{
				portMart = "进港";
			} else
			{
				portMart = "出港";
			}
			TextView portMartText = (TextView) findViewById(R.id.wharfwork_view_portMartneirong);
			portMartText.setText(portMart);

			String workTime = jsonArray.getString("workTime").substring(0,
					jsonArray.getString("workTime").lastIndexOf(":"));
			TextView workTimeText = (TextView) findViewById(R.id.wharfwork_view_workTimeneirong);
			workTimeText.setText(workTime);

			String declareTime = jsonArray.getString("declareTime").substring(
					0, jsonArray.getString("declareTime").lastIndexOf(":"));
			TextView declareTimeText = (TextView) findViewById(R.id.wharfwork_view_declareTimeneirong);
			declareTimeText.setText(declareTime);

			String locationName = jsonArray.getString("locationName");
			TextView locationNameText = (TextView) findViewById(R.id.wharfwork_view_locationNameneirong);
			locationNameText.setText(locationName);

			workPhoto = jsonArray.getString("workPhoto");
			System.out.println("url====" + workPhoto);

			if (workPhoto.equals("") || workPhoto.equals("null"))
			{
				bu.setVisibility(View.GONE);
				workPhotoText.setVisibility(View.GONE);
			} else
			{
				new GetBitmapList().execute(); // 异步
			}

			// Bitmap pic = GetBitmap.GetThumbnailUtils(workPhoto);
			// workPhotoText.setImageBitmap(pic);// 向图片控件设置略缩图

		} catch (Exception e)
		{
			Toast.makeText(WharfWorkView.this, "网络异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class GetBitmapList extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{

			pic = GetBitmap.GetThumbnailUtils(workPhoto);

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			workPhotoText.setImageBitmap(pic);// 向图片控件设置略缩图
			workPhotoText.setOnClickListener(new Photo());
			super.onPostExecute(result);
		}

	}

	class Photo implements View.OnClickListener
	{
		public void onClick(View v)
		{
			new AlertDialog.Builder(WharfWorkView.this)
					.setTitle("下载图片")
					.setMessage("是否下载" + workPhoto + "？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener()
							{

								public void onClick(DialogInterface dialog,
										int which)
								{
									String urlstr = HttpUtil.BASE_URL
											+ GlobalVar.FILE_SERVER_PATH
											+ workPhoto;
									new Download().execute(urlstr, workPhoto);
								}
							}).setNegativeButton("否", null).show();

		}
	}

	class Download extends AsyncTask<String, Void, Integer>
	{

		String imageURL;

		@Override
		protected Integer doInBackground(String... params)
		{

			imageURL = GlobalVar.FILE_DOWNLOAD_PATH + params[1];
			return query.download(params[0], GlobalVar.FILE_DOWNLOAD_PATH,
					params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{

			if (result == 0 || result == 1)
				ShowImage.ShowImageBySystemApp(imageURL, WharfWorkView.this);

			if (result == -1)
				Toast.makeText(WharfWorkView.this, "下载失败", Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}

	}
}
