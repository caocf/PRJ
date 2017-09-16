package com.example.smarttraffic.bikenew;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.adapter.StationFavorAdapter;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class BikeNewDeleteFavorActivity extends FragmentActivity
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
		
		deleteButton=(ImageView)this.findViewById(R.id.trip_favor_delete);
		deleteButton.setVisibility(View.VISIBLE);
		deleteButton.setOnClickListener(new delete());
	}
	
	class delete implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			String stations = "";

			for (BikeStation l : favorAdapter.getData())
			{
				if (l.isSelect())
				{
					if (stations.equals(""))
						stations += l.getFavorID();
					else
						stations += "," + l.getFavorID();
				}
			}

			final String finalStations = stations;
			
			if(finalStations.equals(""))
			{
				Toast.makeText(BikeNewDeleteFavorActivity.this,
						"请选择删除项",
						Toast.LENGTH_SHORT).show();
				return;
			}

			System.out.println(finalStations);

			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					return JSON.parseObject(
							JSON.parseObject(data).getJSONObject("message")
									.toJSONString(), Message.class);
				}
			}, new BaseRequest()
			{
				@Override
				public String CreateUrl()
				{

					return HttpUrlRequestAddress.FAVOR_DELETE_BIKE_STATION_URL
							+ "?userid=" + UserControl.getUser().getUserid()
							+ "&stationIds=" + finalStations;
				}
			}, new UpdateView()
			{

				@Override
				public void update(Object data)
				{
					Message message = (Message) data;

					Toast.makeText(BikeNewDeleteFavorActivity.this,
							message.getStatus() == 1 ? "删除成功" : "删除失败",
							Toast.LENGTH_SHORT).show();
					updateList();
				}
			}, "删除收藏线路失败").start();
		}
	}

	public void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("收藏夹");
		fragment.setRightName("全选");
		fragment.setRightListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(favorAdapter!=null)
					favorAdapter.selectALL(true);
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
					Toast.makeText(BikeNewDeleteFavorActivity.this, "查找收藏记录失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (data instanceof List<?>)
				{
					favorAdapter = new StationFavorAdapter(
							BikeNewDeleteFavorActivity.this, (List<BikeStation>) data,2);
					listView.setAdapter(favorAdapter);
					listView.setOnItemClickListener(new OnItemClickListener()
					{

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							favorAdapter.changeCheck(position);
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
