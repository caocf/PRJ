package com.example.smarttraffic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bikenew.BikeNewHomeActivity;
import com.example.smarttraffic.driverguide.DriverGuideActivity;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.news.NewsHomeActivity;
import com.example.smarttraffic.parking.ParkingHomeActivity;
import com.example.smarttraffic.smartBus.SmartBusHomeActivity;
import com.example.smarttraffic.system.CheckVersion;
import com.example.smarttraffic.taxi.TaxiListActivity;
import com.example.smarttraffic.trafficValue.TrafficValue;
import com.example.smarttraffic.trafficValue.TrafficValueRequest;
import com.example.smarttraffic.trafficValue.TrafficValueSearch;
import com.example.smarttraffic.util.GetArray;
import com.example.smarttraffic.util.ListviewControl;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.TextViewUtil;
import com.example.smarttraffic.util.WindowsUtil;
import com.example.smarttraffic.weather.Weather2;
import com.example.smarttraffic.weather.WeatherRequest;
import com.example.smarttraffic.weather.WeatherSearch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * 禾行通主界面fragment
 * 
 * @author Administrator zhou
 * 
 */
public class HomeFragment extends Fragment implements UpdateView
{
	@Override
	public void update(Object data)
	{
		if (data instanceof Weather2)
		{
			Weather2 weather = (Weather2) data;

			TextViewUtil.setText(getActivity(), R.id.home_weather,
					weather.getWeather() + " " + weather.getL_tmp() + "-"
							+ weather.getH_tmp() + "℃ " + weather.getWD());
			// TextViewUtil.setText(getActivity(), R.id.home_time,
			// "今日"+weather.getTime()+"更新");

			count++;

		}
	}

	private String titles[] = null; // 九宫格内容文本
	private int images[] = null; // 九宫格内容图片
	private static final int itemCount = 6; // 九宫格选项数目

	public static int count = 0; // 天气加载判断参数，加载成功后置1，只当值为0时获取数据，避免每次进入页面重新加载信息

	GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		initView(rootView);

		if (count == 0)
		{
			new HttpThread(new WeatherSearch(), new WeatherRequest(), this,
					"查找天气信息失败").start();

			new CheckVersion(getActivity(), false,true).check();
		}

		//活跃度统计
		new HttpThread(new BaseSearch(){
			@Override
			public Object parse(String data)
			{
				return data;
			}
		}, new BaseRequest(){
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params=new PostParams();
				
				params.setUrl(HttpUrlRequestAddress.LIVENESS_ADD_URL);
				
				Map<String, Object> d=new HashMap<String, Object>();
				
				TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Activity.TELEPHONY_SERVICE);     
				
				System.out.println(tm.getDeviceId());
				
				d.put("liveness.imei", tm.getDeviceId());
				d.put("liveness.version", CheckVersion.getCurrentVersion(getActivity()));
				d.put("liveness.equitment", Build.MODEL);
				
				params.setParams(d);
				
				return params;
			}
		}, null,"").start();
		
		new HttpThread(new TrafficValueSearch(), new TrafficValueRequest(),
				new UpdateView()
				{

					@SuppressLint("SimpleDateFormat")
					@Override
					public void update(Object data)
					{
						if (data instanceof TrafficValue)
						{
							TrafficValue result = (TrafficValue) data;

							TextViewUtil.setText(getView(),
									R.id.home_traffic_index, result.getTpi());
							TextViewUtil.setText(getView(),
									R.id.home_traffic_introduce,
									result.getTpiLevel());

							Map<String, Integer> pic = new HashMap<String, Integer>();
							pic.put("畅通", R.drawable.bg_traffic_green);
							pic.put("基本畅通", R.drawable.bg_traffic_green);
							pic.put("轻度拥堵", R.drawable.bg_traffic_yello);
							pic.put("中度拥堵", R.drawable.bg_traffic_orange);
							pic.put("严重拥堵", R.drawable.bg_traffic_read);

							Integer i = pic.get(result.getTpiLevel());

							if (i == null || i == 0)
							{
								i = R.drawable.bg_traffic_gray;
							}

							getActivity().findViewById(
									R.id.home_traffic_introduce)
									.setBackgroundResource(i);

							try
							{
								Date d = new Date(result.getTime() * 1000);
								String sd = new SimpleDateFormat(
										"yyyy/MM/dd HH:mm").format(d);

								TextViewUtil.setText(getView(),
										R.id.home_traffic_speed, "路网平均速度："
												+ result.getAvgSpeed()
												+ "km/h " + sd);
							} catch (Exception e)
							{
							}
						}
					}
				}, "查找交通指数信息失败").start();

		return rootView;
	}

	/**
	 * 初始化界面
	 * 
	 * @param rootView
	 */
	public void initView(View rootView)
	{
		gridView = (GridView) rootView.findViewById(R.id.gridview);

		images = new int[] { R.drawable.home_bus, R.drawable.home_taxi,
				R.drawable.home_bike, R.drawable.home_parking,
				R.drawable.home_trip, R.drawable.home_news };
		titles = GetArray.getStringArrayByID(R.array.array_main_content,
				getActivity());

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < itemCount; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", images[i]);
			map.put("itemText", titles[i]);
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
				lstImageItem, R.layout.ninth_item, new String[] { "itemImage",
						"itemText" },
				new int[] { R.id.itemImage, R.id.itemText });
		gridView.setAdapter(saImageItems);
		gridView.setOnItemClickListener(new ItemListener());
	}

	/**
	 * 点击进入子模块事件
	 * 
	 * @author Administrator
	 * 
	 */
	class ItemListener implements OnItemClickListener
	{
		Class<?>[] toDestClasses = new Class[] { SmartBusHomeActivity.class,
				TaxiListActivity.class, BikeNewHomeActivity.class, ParkingHomeActivity.class,
				DriverGuideActivity.class, NewsHomeActivity.class };

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int location, long id)
		{
			if (location >= 0 && location <= itemCount)
			{
				try
				{
//					if (location == 1)
//					{
//						if (!PackageInstaller.checkApkExist(
//								HomeFragment.this.getActivity(),
//								PackageInstaller.apkPackage))
//						{
//							PackageInstaller.install(
//									HomeFragment.this.getActivity(),
//									PackageInstaller.apkName);
//						}
//						return;
//					} else 
						if (location == 3 || location == 4)
					{
						ComponentName componetName = new ComponentName(
								"rocket.trafficeye.android.hmi2",
								"rocket.trafficeye.android.hmi2.GuRouteActivity");
						Intent intent = new Intent();
						intent.setComponent(componetName);
						startActivity(intent);

						return;
					}
				} catch (Exception e)
				{
				}

				if (toDestClasses[location] != null)
				{
					StartIntent.startIntent(getActivity(),
							toDestClasses[location]);
				} else
				{
					Toast.makeText(getActivity(), R.string.kaifatishi,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	
	@Override
	public void onResume()
	{
		ListviewControl.setGridViewHeightBasedOnChildren(gridView, 2,
				WindowsUtil.dpToPx(getActivity(), 10 * 3));

		super.onResume();
	}

	/**
	 * 退出时对地图API进行销毁
	 */
	@Override
	public void onDestroy()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) getActivity()
				.getApplication();
		if (app.mCNMKAPImgr != null)
		{
			app.mCNMKAPImgr.destroy();
			app.mCNMKAPImgr = null;
		}
		super.onDestroy();
		System.exit(0);
	}
}
