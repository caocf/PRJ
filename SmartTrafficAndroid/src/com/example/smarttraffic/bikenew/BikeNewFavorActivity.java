package com.example.smarttraffic.bikenew;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.adapter.StationFavorAdapter;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BikeNewFavorActivity extends FragmentActivity
{
	ListView listView;
	StationFavorAdapter favorAdapter;
	ImageView deleteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_trip_favor);

		listView = (ListView) this.findViewById(R.id.trip_favor_listview);

		initHead();
	}

	public void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("收藏夹");
		fragment.setRightName("删除");
		fragment.setRightListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(BikeNewFavorActivity.this,
						BikeNewDeleteFavorActivity.class);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.parking_home_head, fragment);
	}

	public void updateList()
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(this, "您尚未登录", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				System.out.println(data);

				JSONObject object = JSON.parseObject(data);

				Message message = JSON.parseObject(
						object.getJSONObject("message").toJSONString(),
						Message.class);

				if (message.getStatus() == 1)
				{
					List<BikeStation> infos = new ArrayList<BikeStation>();

					JSONArray array = object.getJSONArray("result");

					for (int i = 0; i < array.size(); i++)
					{
						JSONObject jsonObject = array.getJSONObject(i);

						BikeStation temp = new BikeStation();
						temp.setFavorID(jsonObject.getIntValue("id"));
						temp.setId(jsonObject.getIntValue("stationID"));
						temp.setName(jsonObject.getString("stationName"));
						temp.setAddress(jsonObject.getString("address"));

						infos.add(temp);
					}

					return infos;
				}

				return message;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.FAVOR_FINE_BIKE_STATION_URL
						+ "?userid=" + UserControl.getUser().getUserid();
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				if (data instanceof Message)
				{
					Toast.makeText(BikeNewFavorActivity.this, "查找收藏记录失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (data instanceof List<?>)
				{
					favorAdapter = new StationFavorAdapter(
							BikeNewFavorActivity.this, (List<BikeStation>) data);
					listView.setAdapter(favorAdapter);

					listView.setOnItemClickListener(new OnItemClickListener()
					{

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							BikeStation bikeStation=favorAdapter.getData().get(position);
							Bundle bundle=new Bundle();
							bundle.putSerializable(BikeStationInfoListFragment.BIKE_STATION_INFO, bikeStation);
							bundle.putInt(BikeStationDetailActivity.IS_NEED_REQUEST, 1);
							
							StartIntent.startIntentWithParam(BikeNewFavorActivity.this, BikeStationDetailActivity.class,bundle);	
						}
					});

				}

			}
		}, "查找收藏站点失败").start();

	}

	@Override
	protected void onResume()
	{
		updateList();
		super.onResume();
	}
}
