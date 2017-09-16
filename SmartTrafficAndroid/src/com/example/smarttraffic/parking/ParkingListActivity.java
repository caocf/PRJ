package com.example.smarttraffic.parking;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cennavi.cenmapsdk.android.GeoPoint;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ParkingListActivity extends FragmentActivity
{
	public static final String LAN = "lan";
	public static final String LON = "lon";

	ListView content;

	private double lan;
	private double lon;

	public int distance = 1000;

	List<Parking> parkings;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parking_list);

		initHead();

		lan = getIntent().getDoubleExtra(LAN, -1);
		lon = getIntent().getDoubleExtra(LON, -1);

		content = (ListView) findViewById(R.id.parking_list_content);

		if (lan > 0 && lon > 0)
		{
			transfor();
			request();
		}
	}

	/**
	 * 02转84
	 */
	private void transfor()
	{
		GeoPoint point1 = PointTraslation.gcj_To_Gps84(lan, lon);

		lan = point1.getLatitudeE6() * 1.0 / 1e6;
		lon = point1.getLongitudeE6() * 1.0 / 1e6;
	}

	long clickTime=-1;
	
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("搜索结果");
		fragment.setRightName("地图");
		fragment.setRightListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				long now =new Date().getTime();
				
				if((now-clickTime)<1000)
				{
					return ;
				}
				
				clickTime=now;
				
				
				Bundle bundle = new Bundle();

				bundle.putDouble(ParkingMapActivity.LAN, lan);
				bundle.putDouble(ParkingMapActivity.LON, lon);
				bundle.putSerializable(ParkingMapActivity.DATA,
						(Serializable) parkings);

				StartIntent.startIntentWithParam(ParkingListActivity.this,
						ParkingMapActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.parking_list_head, fragment);
	}

	private void request()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseArray(
						JSON.parseObject(data).getJSONArray("result")
								.toJSONString(), Parking.class);
			}
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl(HttpUrlRequestAddress.PARKING_LOCATION_URL);

				Map<String, Object> p = new HashMap<String, Object>();
				p.put("radius", distance);
				p.put("lan", String.valueOf(lan));
				p.put("lon", String.valueOf(lon));
				p.put("isReal", "1");
				p.put("page", "1");
				p.put("num", "20");
				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				parkings = (List<Parking>) data;

				if ((parkings == null || parkings.size() == 0)
						&& distance == 1000)
				{
					distance = 3000;
					request();
				} else
					content.setAdapter(new ParkingListAdapter(
							ParkingListActivity.this, parkings));
				// content.setOnItemClickListener(new OnItemClickListener()
				// {
				// @Override
				// public void onItemClick(AdapterView<?> parent, View view,
				// int position, long id)
				// {
				// Bundle bundle=new Bundle();
				//
				// bundle.putSerializable(ParkingDetailActivity.PARKING,
				// parkings.get(position));
				//
				// StartIntent.startIntentWithParam(ParkingListActivity.this,ParkingDetailActivity.class,
				// bundle);
				// }
				// });
			}
		}, this, "附近无停车点").start();
	}
}
