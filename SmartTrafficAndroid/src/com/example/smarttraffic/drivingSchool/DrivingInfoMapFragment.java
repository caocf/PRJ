package com.example.smarttraffic.drivingSchool;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.tripPlan.TripMapActivity;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.DrivingInputviewListener;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.VoiceListener;

import android.location.GpsStatus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DrivingInfoMapFragment extends CNMKMapFragment implements UpdateView
{
	SchoolRequest request;
	CNMKMapView mapview;
	MapControl mapControl;
	List<DrivingSchool> dataList;
	Button search;
	InputListView searchInputListView;
	
	ImageView locImageView;
	ImageView zoomInImageView;
	ImageView zoomOutImageView;
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data)
	{
		if(data instanceof List<?>)
		{
			dataList=(List<DrivingSchool>)data;
			mark(dataList);
		}
	}
	
	private void mark(List<DrivingSchool> dataList)
	{
		GeoPoint[] points=new GeoPoint[dataList.size()];
		for(int i=0;i<dataList.size();i++)
		{
			points[i]=new GeoPoint(dataList.get(i).getLantitude(),dataList.get(i).getLongtitude());
		}
		
		if(mapControl!=null)
		{
			mapControl.createMapOverItem(getActivity(), DrivingInfoMapFragment.this.getActivity().getLayoutInflater().inflate(R.layout.map_pop_layout, null), points,new onPopClick());
		}	
	}
	
	class onPopClick implements OnPopInterface
	{		
		int i;		
		@Override
		public View onPop(int i, View v)
		{
			this.i=i;
			if(dataList!=null)
			{
				TextView name=(TextView)v.findViewById(R.id.driving_school_name);
				TextView info=(TextView)v.findViewById(R.id.driving_school_information);
				ImageView goView=(ImageView)v.findViewById(R.id.driving_get_there);
				
				name.setText(dataList.get(i).getName());
				info.setText(dataList.get(i).getAddress());
				goView.setOnClickListener(new OnClickListener()
				{					
					@Override
					public void onClick(View v)
					{
						DrivingSchool drivingSchool=dataList.get(onPopClick.this.i);
						Bundle bundle=new Bundle();
						bundle.putSerializable(DrivingInfoListFragment.DRIVING_SCHOOL_INFO, drivingSchool);
						bundle.putInt(TripMapActivity.FROM_NAME, 1);
						
						StartIntent.startIntentWithParam(getActivity(), TripMapActivity.class,bundle);
					}
				});
			}
			
			return v;
		}
	}
	
	DrivingInputviewListener drivingInputviewListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View rootView=inflater.inflate(R.layout.fragment_driving_info_map, container, false);
		
		HeadLCRFragment fragment=new HeadLCRFragment();
		fragment.setTitleName("驾培服务");
		fragment.setRightName("列表");
		fragment.setRightListener(new ListOnclick());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_map_head, fragment);
		
		initView(rootView);
				
		if(mapview==null)
		{
			mapview=(CNMKMapView)rootView.findViewById(R.id.driving_mapview_map);
			
			super.setMapView(mapview);
			initMapActivity();
			
			if(mapControl==null)
				mapControl=new MapControl(getActivity());
			mapControl.initMap(mapview);
			mapControl.setLocationListener(new locationListener());
			mapControl.addLocation();
		}		
		
		drivingInputviewListener=new DrivingInputviewListener(searchInputListView);
		InputListViewListener[] listenersInput=new InputListViewListener[]{mylocationListener,new VoiceListener(getActivity(), searchInputListView),favorListener};
		drivingInputviewListener.setListener(listenersInput);
		
		request=new SchoolRequest();
			
		return rootView;
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
	
	public void initView(View v)
	{
		locImageView=(ImageView)v.findViewById(R.id.base_mapview_location);
		locImageView.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				mapControl.addLocation();
			}
		});
		
		zoomInImageView=(ImageView)v.findViewById(R.id.base_mapview_enlarge);
		zoomInImageView.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				mapControl.zoomIn();
			}
		});
		
		zoomOutImageView=(ImageView)v.findViewById(R.id.base_mapview_reduce);
		zoomOutImageView.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				mapControl.zoomOut();
			}
		});
		
		search=(Button)v.findViewById(R.id.driving_search);
		search.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				mapControl.clearOverlays();
				request.setName(searchInputListView.getText());
				new HttpThread(new SchoolSearch(), request, DrivingInfoMapFragment.this,DrivingInfoMapFragment.this.getActivity(),R.string.error_driving_station_info).start();
			}
		});
		
		searchInputListView=(InputListView)v.findViewById(R.id.driving_school_search_inputview);
		searchInputListView.setHint("请输入驾校名称或区域");
	}

	class ListOnclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(replaceByParentLayout!=null)
				replaceByParentLayout.replace(1);		
		}
	}
	
	ReplaceByParentLayoutInterface replaceByParentLayout;

	public void setReplaceByParentLayout(ReplaceByParentLayoutInterface replaceByParentLayout)
	{
		this.replaceByParentLayout = replaceByParentLayout;
	}
	
	GeoPoint locationGeoPoint;
		
	public GeoPoint getLocationGeoPoint()
	{
		return locationGeoPoint;
	}

	class locationListener implements ICNMKLocationListener
	{
		@Override
		public void onGPSStatusChanged(GpsStatus arg0)
		{
						
		}
		@Override
		public void onLocationChanged(CNMKLocation location)
		{
			if (location != null) {
				GeoPoint pt = new GeoPoint(
						(int) (location.getLatitude() * (1e6 * AA.LV)),
						(int) (location.getLongitude() * (1e6 * AA.LV)));
				mapview.getController().setCenter(pt);
				locationGeoPoint=pt;
			}
			mapControl.deleteLocation();
		}
	}
		
	@Override
	public void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) getActivity().getApplication();
		app.mCNMKAPImgr.start();
		
		if(getActivity().getIntent().getIntExtra(DetailSchoolActivity.MAP_INTENT, 0)==0)
		{
			new HttpThread(new SchoolSearch(), request, this,getActivity(),R.string.error_driving_station_info).start();
		}
		else if(getActivity().getIntent().getIntExtra(DetailSchoolActivity.MAP_INTENT, 0)==1)
		{
			DrivingSchool drivingSchool=(DrivingSchool)getActivity().getIntent().getSerializableExtra(DetailSchoolActivity.MAP_PARAMS);
			if(dataList==null)
				dataList=new ArrayList<DrivingSchool>();
			dataList.add(drivingSchool);
			mark(dataList);
		}
		
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) getActivity().getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}
}
