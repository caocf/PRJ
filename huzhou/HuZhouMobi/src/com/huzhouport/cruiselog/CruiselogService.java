package com.huzhouport.cruiselog;

import java.util.Timer;
import java.util.TimerTask;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class CruiselogService extends Service
{
	private String cruiselogid;
	private Timer timer; // 定时器
	private TimerTask timertask; // 定时器
	private String locationurl = "";
	private String locationparam = "";

	@Override
	public IBinder onBind(Intent arg0)
	{

		System.out.println("--onBind");
		return null;
	}
 
	@Override
	public void onCreate()
	{
		System.out.println("--onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy()
	{
		System.out.println("--onDestroy");
		// 销毁定时器
		if(timer!=null){
			timer.cancel();
		}
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("--onStartCommand");

		if (intent != null)
		{
			cruiselogid = intent.getStringExtra("cruiselogid");
			// 定时器
			timertask = new TimerTask()
			{
				public void run()
				{
					/*GeoPoint gp = mapTool.getAddrLocation();
					if (gp == null)
					{
						System.out.println("location定位失败，无法提交");
					} else
					{
						System.out.println("location=" + gp.getLongitudeE6()
								+ "  " + gp.getLatitudeE6());
						System.out.println("locationname="
								+ mapTool.getAddrLocationAddress());

						locationurl = HttpUtil.BASE_URL + "cruiseLoglocation";
						locationparam = "location.longitude="
								+ gp.getLongitudeE6() + "&location.latitude="
								+ gp.getLatitudeE6()
								+ "&location.locationName="
								+ mapTool.getAddrLocationAddress()
								+ "&cruiseLogID=" + cruiselogid;

						// 放入数据库且获得id
						ListTasklocation task = new ListTasklocation(); // 异步
						task.execute();

					}*/

				}
			};
			// 开启定时器
			timer = new Timer();
			timer.schedule(timertask, 5000, GlobalVar.Cruiselog_Time);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	class ListTasklocation extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTasklocation()
		{

		}

		@Override
		protected String doInBackground(String... params)
		{

			HttpFileUpTool hfu = new HttpFileUpTool();
			try
			{

				hfu.sendPost1(locationurl, locationparam);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{

			super.onPostExecute(result);
		}

	}

}
