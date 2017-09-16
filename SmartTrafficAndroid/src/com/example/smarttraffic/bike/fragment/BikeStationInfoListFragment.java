package com.example.smarttraffic.bike.fragment;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.adapter.BikeStationInfoListAdapter;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfo;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfoTemp;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.drivingSchool.ReplaceByParentLayoutInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BikeStationInfoListFragment extends Fragment
{
	BikeStationInfoListAdapter listAdapter;
	
	MapControl mapControl;
	
	ListView listView;
	
	List<BikeStation> data;
	public void setData(List<BikeStation> data)
	{
		this.data = data;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_bike_info_list, container,false);
		
		HeadLCRFragment fragment=new HeadLCRFragment();
		fragment.setTitleName("公共自行车");
		fragment.setRightName("地图");
		fragment.setRightListener(new MapOnclick());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_list_head, fragment);
		
		initView(rootView);
		
		show();
//		update();
		
		return rootView;
	}
	
	public void show()
	{
		if(listAdapter==null)
		{
			listAdapter=new BikeStationInfoListAdapter(BikeStationInfoListFragment.this.getActivity(), data);
			listView.setAdapter(listAdapter);
		}
		else
		{
			listAdapter.refreshList(data);
		}
	}
	
	public void update()
	{
		if(data==null)
			return;
		else
			searchReal();	
	}

	private void searchReal()
	{
		new HttpThread(
		new BaseSearch(){
			@Override
			public Object parse(String d)
			{
				System.out.println(d);
				
				BikeParkingPlaceInfoTemp temp=JSON.parseObject(DoString.parseThreeNetString(d), BikeParkingPlaceInfoTemp.class);
				
				for(BikeParkingPlaceInfo b:temp.getList())
				{
					for(BikeStation s:data)
					{
						if(b.getStationid()==s.getId())
						{
							s.setLeft(b.getCount());
							break;
						}
					}
				}
				
				return data;
			}
		}, 
		new BaseRequest(){@Override
		public String CreateUrl()
		{
			String result=HttpUrlRequestAddress.SMART_BIKE_STATION_REAL_BY_ID_URL+"?bikeList=";
			
			for(int i=0;i<data.size();i++)
			{
				if(i>0)
					result+=",";
				result+=data.get(i).getId();
			}
			
			System.out.println(result);
			
			return result;
		}}, new UpdateView()
		{
			
			@Override
			public void update(Object d)
			{
				if(listAdapter==null)
				{
					listAdapter=new BikeStationInfoListAdapter(BikeStationInfoListFragment.this.getActivity(), data);
					listView.setAdapter(listAdapter);
				}
				else
				{
					listAdapter.refreshList(data);
				}
			}
		},getActivity(),R.string.error_bike_station_info).start();
	}

	private void initView(View rootView)
	{		
		listView=(ListView)rootView.findViewById(R.id.driving_list);
		listView.setOnItemClickListener(new detailOnclick());
	}
	
	public static final String BIKE_STATION_INFO="bike_station_info";
	class detailOnclick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			BikeStation bikeStation=listAdapter.getData().get(position);
			Bundle bundle=new Bundle();
			bundle.putSerializable(BIKE_STATION_INFO, bikeStation);
			
			StartIntent.startIntentWithParam(getActivity(), BikeStationDetailActivity.class,bundle);	
		}
	}
	
	class MapOnclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(replaceByParentLayout!=null)
				replaceByParentLayout.replace(2);		
		}
	}
	
	ReplaceByParentLayoutInterface replaceByParentLayout;

	public void setReplaceByParentLayout(ReplaceByParentLayoutInterface replaceByParentLayout)
	{
		this.replaceByParentLayout = replaceByParentLayout;
	}
	
	@Override
	public void onResume()
	{
		
		super.onResume();
	}
}
