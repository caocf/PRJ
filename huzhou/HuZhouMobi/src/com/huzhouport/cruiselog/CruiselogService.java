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
	private Timer timer; // ��ʱ��
	private TimerTask timertask; // ��ʱ��
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
		// ���ٶ�ʱ��
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
			// ��ʱ��
			timertask = new TimerTask()
			{
				public void run()
				{
					/*GeoPoint gp = mapTool.getAddrLocation();
					if (gp == null)
					{
						System.out.println("location��λʧ�ܣ��޷��ύ");
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

						// �������ݿ��һ��id
						ListTasklocation task = new ListTasklocation(); // �첽
						task.execute();

					}*/

				}
			};
			// ������ʱ��
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
