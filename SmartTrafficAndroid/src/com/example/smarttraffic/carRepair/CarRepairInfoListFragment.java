package com.example.smarttraffic.carRepair;

import java.util.List;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.ReplaceByParentLayoutInterface;
import com.example.smarttraffic.drivingSchool.SchoolSearch;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.CarRepairInputviewListener;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.VoiceListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CarRepairInfoListFragment extends Fragment implements UpdateView
{
	CarRepairListAdapter listAdapter;
	
	@SuppressWarnings("unchecked")
	public void update(Object data)
	{
		if(data instanceof List<?>)
		{	
			List<CarRepair> dataList=(List<CarRepair>)data;
//			if(listAdapter==null)
//			{
				listAdapter=new CarRepairListAdapter(getActivity(), dataList);
				listView.setAdapter(listAdapter);
//			}	
//			else 
//			{
//				listAdapter.refreshList(dataList);
//			}
		}
	}
	
	ListView listView;
	CarRepairRequest request;
	Button search;
	InputListView searchInputListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_car_repair_info_list, container,false);
		
		HeadLCRFragment fragment=new HeadLCRFragment();
		fragment.setTitleName("汽车维修");
		fragment.setRightName("地图");
		fragment.setRightListener(new MapOnclick());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_list_head, fragment);
		
		initView(rootView);
		request=new CarRepairRequest();
		request.setPage(1);
		request.setNum(100);
		
		return rootView;
	}
	
	
	CarRepairInputviewListener carInputviewListener;
	private void initView(View rootView)
	{		
		search=(Button)rootView.findViewById(R.id.repair_search);
		search.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if(searchInputListView.getText().equals(""))
					Toast.makeText(CarRepairInfoListFragment.this.getActivity(), "汽修点不能为空", Toast.LENGTH_SHORT).show();
				else
				{
					request.setName(searchInputListView.getText());
					new HttpThread(new CarRepairSearch(), request, CarRepairInfoListFragment.this,getActivity(),R.string.error_car_repair_station_info).start();
				}
			}
		});
		
		listView=(ListView)rootView.findViewById(R.id.driving_list);
		listView.setOnItemClickListener(new detailOnclick());
		searchInputListView=(InputListView)rootView.findViewById(R.id.driving_school_search_inputview);
		
		carInputviewListener=new CarRepairInputviewListener(searchInputListView);
		InputListViewListener[] listenersInput=new InputListViewListener[]{mylocationListener,new VoiceListener(getActivity(), searchInputListView),favorListener};
		carInputviewListener.setListener(listenersInput);
	}
	
	InputListViewListener mylocationListener=new InputListViewListener()
	{		
		@Override
		public void inputSelectListener()
		{
			searchInputListView.setText("我的位置");		
			searchInputListView.showList(false);
		}
	};
	
	InputListViewListener favorListener=new InputListViewListener()
	{
		
		@Override
		public void inputSelectListener()
		{
			replaceByParentLayout.replace(3);			
		}
	};
	
	
	public static final String CAR_REPAIR_INFO="car_repair_info";
	class detailOnclick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			CarRepair carRepair=listAdapter.getData().get(position);
			Bundle bundle=new Bundle();
			bundle.putSerializable(CAR_REPAIR_INFO, carRepair);
			
			StartIntent.startIntentWithParam(getActivity(), DetailCarRepairActivity.class,bundle);	
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
		new HttpThread(new CarRepairSearch(),request,this,getActivity(),R.string.error_car_repair_station_info).start();
		super.onResume();
	}
}
