package com.example.smarttraffic.driverguide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.R;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.news.Ydld;
import com.example.smarttraffic.voice.PushAbInfo;

/**
 * 
 * @author Administrator
 * 
 */
public class DrivingGuideRoadFragment extends CNMKMapFragment
{
	CNMKMapView mMapView;
	MapControl mapControl;

	//ListView listView;
	AutoScrollView listView;
	TextView autotext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_drving_guide_road,
				container, false);

		initView(rootView);
		SharedPreferences sp=this.getActivity().getSharedPreferences("speed", 0);
		int sped=sp.getInt("s", 20);
		listView = (AutoScrollView) rootView.findViewById(R.id.listView1);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listView.autoScroll();
				
			}
		}, 1000);
		
		listView.setSpeed(sped);
		//listView.setPeriod(5);ScrollView
		listView.setType(0);
		addYDLD();
		Intent intent=new Intent(this.getActivity(),PushAbInfo.class);
		this.getActivity().startService(intent);

		return rootView;
	}

	private void initView(View root)
	{
		mMapView = (CNMKMapView) root
				.findViewById(R.id.driving_guide_road_mapview);

		mMapView.changeLanguage("zh");
		super.setMapView(mMapView);
		initMapActivity();
		mMapView.setBuiltInZoomControls(false);
		mMapView.setDrawOverlayWhenZooming(true);

		mapControl = new MapControl(getActivity());
		mapControl.initMap(mMapView);

		mMapView.setTraffic(true);
		autotext=(TextView) root.findViewById(R.id.autotext);
	}

	private void addYDLD()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				List<Ydld> result = new ArrayList<Ydld>();
				JSONArray array = JSON.parseObject(data).getJSONArray(
						"mergedConRtics");
				for (int i = 0; i < array.size(); i++)
				{
					Ydld y = new Ydld();

					JSONObject object = array.getJSONObject(i);
					y.setLocalKind(object.getString("localKind"));
					y.setRoadName(object.getString("roadName"));

					object = object.getJSONArray("roadSections").getJSONObject(
							0);
					y.setDesc(object.getString("desc"));

					y.setRsEnd(object.getString("rsEnd"));
					y.setRsStart(object.getString("rsStart"));

					result.add(y);
				}

				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/conanalyse/queryMergedConRticByTime?plan=3&timeType=1";
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				/*listView.setAdapter(new MapShowListAdapter(getActivity(),
						(List<Ydld>) data));

				initTimer();*/
				List<Ydld> list=(List<Ydld>)data;
				for(int i=0;i<list.size();i++)
				{
					Ydld ydld=list.get(i);
					autotext.append(ydld.getRoadName()+":"+ydld.getRsStart() + "-"+ ydld.getRsEnd()+" "+ydld.getDesc());
					autotext.append("\n");
					autotext.setLineSpacing(0, 1.5f);
				}
				
			}
		}).start();
	}

	Timer timer;
	private int count = 0;
/*
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				try
				{
					if(count>=listView.getAdapter().getCount())
						count=0;
					
					listView.setSelection(count);
				} catch (Exception e)
				{
					count = 0;
					listView.setSelection(count);
				}
			}
		}
	};

	private void initTimer()
	{
		timer = new Timer();

		timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				count++;
				handler.sendEmptyMessage(1);

			}
		}, 500, 1000);
	}*/

	@Override
	public void onDestroy()
	{
		if (mMapView != null)
		{
			mMapView.destory();
			mMapView = null;
		}

		if(timer!=null)
			timer.cancel();
		super.onDestroy();
	}

}
