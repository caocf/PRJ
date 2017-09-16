package com.example.smarttraffic.drivingSchool;

import java.util.List;

import cennavi.cenmapsdk.android.GeoPoint;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.DrivingInputviewListener;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DrivingInfoListFragment extends Fragment implements UpdateView
{

	TextView defaultSortTextView;
	TextView levelSortTextView;
	int kind;
	DrivingListAdapter listAdapter;
	
	Button search;
	
	InputListView searchInputListView;
	private GeoPoint locationPoint;
	
	@SuppressWarnings("unchecked")
	public void update(Object data)
	{
		if(data instanceof List<?>)
		{
			List<DrivingSchool> dataList=(List<DrivingSchool>)data;
//			if(listAdapter==null)
//			{
				listAdapter=new DrivingListAdapter(getActivity(), dataList);
				listView.setAdapter(listAdapter);
//			}	
//			else 
//			{
//				listAdapter.refreshList(dataList);
//			}
		}
	}	
	
	public GeoPoint getLocationPoint()
	{
		return locationPoint;
	}

	public void setLocationPoint(GeoPoint locationPoint)
	{
		this.locationPoint = locationPoint;
	}


	ListView listView;
	SchoolRequest request;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_driving_info_list, container,false);
		
		HeadLCRFragment fragment=new HeadLCRFragment();
		fragment.setTitleName("驾培服务");
		fragment.setRightName("地图");
		fragment.setRightListener(new MapOnclick());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_list_head, fragment);
		
		initView(rootView);
		kind=0;
		request=new SchoolRequest();
		request.setPage(1);
		request.setNum(100);
		
		new HttpThread(new SchoolSearch(), request, this,getActivity(),R.string.error_driving_station_info).start();
		
		return rootView;
	}
	
	
	DrivingInputviewListener drivingInputviewListener;
	private void initView(View rootView)
	{
		search=(Button)rootView.findViewById(R.id.driving_search);
		
		search.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if(searchInputListView.getText().equals(""))
					Toast.makeText(DrivingInfoListFragment.this.getActivity(), "驾校不能为空", Toast.LENGTH_SHORT).show();
				else
				{
					request.setName(searchInputListView.getText());
					new HttpThread(new SchoolSearch(), request, DrivingInfoListFragment.this,getActivity(),R.string.error_driving_station_info).start();
				}
			}
		});
		
		defaultSortTextView=(TextView)rootView.findViewById(R.id.driving_sort_default);
		levelSortTextView=(TextView)rootView.findViewById(R.id.driving_sort_level);
		
		listView=(ListView)rootView.findViewById(R.id.driving_list);
		listView.setOnItemClickListener(new detailOnclick());
		
		onKindClick click=new onKindClick();
		defaultSortTextView.setOnClickListener(click);
		levelSortTextView.setOnClickListener(click);
		
		searchInputListView=(InputListView)rootView.findViewById(R.id.driving_school_search_inputview);
		
		drivingInputviewListener=new DrivingInputviewListener(searchInputListView);
		InputListViewListener[] listenersInput=new InputListViewListener[]{mylocationListener,new VoiceListener(getActivity(), searchInputListView),favorListener};
		drivingInputviewListener.setListener(listenersInput);
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
	
	public static final String DRIVING_SCHOOL_INFO="driving_school_info";	
	
	class detailOnclick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			DrivingSchool drivingSchool=listAdapter.getData().get(position);
			Bundle bundle=new Bundle();
			bundle.putSerializable(DRIVING_SCHOOL_INFO, drivingSchool);

			StartIntent.startIntentWithParam(getActivity(), DetailSchoolActivity.class,bundle);	
		}
	}

		
	class onKindClick implements OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) {
			case R.id.driving_sort_default:
				listAdapter.clear();
				kind=0;
				defaultSortTextView.setTextColor(getResources().getColor(R.color.driving_name_color));
				levelSortTextView.setTextColor(getResources().getColor(R.color.driving_unname_color));
				new HttpThread(new SchoolSearch(), request, DrivingInfoListFragment.this,DrivingInfoListFragment.this.getActivity(),R.string.error_driving_station_info).start();
				break;

			case R.id.driving_sort_level:
				listAdapter.clear();
				kind=1;
				levelSortTextView.setTextColor(getResources().getColor(R.color.driving_name_color));
				defaultSortTextView.setTextColor(getResources().getColor(R.color.driving_unname_color));
				new HttpThread(new SchoolSearch(), request, DrivingInfoListFragment.this,DrivingInfoListFragment.this.getActivity(),R.string.error_driving_station_info).start();
				break;
			}
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
}
