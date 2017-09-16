package com.example.smarttraffic.bike.fragment;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.BikeStationRequestByLocation;
import com.example.smarttraffic.bike.BikeStationRequestByStationName;
import com.example.smarttraffic.bike.BikeStationSearch;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfo;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfoTemp;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.drivingSchool.ReplaceByParentLayoutInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.tripPlan.TripLineMapActivity;
import com.example.smarttraffic.tripPlan.bean.DrivingPlan;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.BikeInputviewListener;
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
import android.widget.Toast;

public class BikeStationInfoMapFragment extends CNMKMapFragment implements
		UpdateView
{
	BikeStationRequestByLocation request;
	CNMKMapView mapview;
	MapControl mapControl;
	List<BikeStation> dataList;

	ImageView locationImageView;
	ImageView enlargeImageView;
	ImageView disLargeImageView;

	public List<BikeStation> getDataList()
	{
		return dataList;
	}

	Button search;
	InputListView searchInputListView;

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data)
	{
		if (data instanceof List<?>)
		{
			dataList = (List<BikeStation>) data;
			searchReal();

		}

	}

	private void mark(List<BikeStation> dataList)
	{
		List<GeoPoint> point1=new ArrayList<GeoPoint>();  //紧张
		List<GeoPoint> point2=new ArrayList<GeoPoint>();  //一般
		List<GeoPoint> point3=new ArrayList<GeoPoint>();  //很多
//		List<GeoPoint> point4=new ArrayList<GeoPoint>();
		
		List<BikeStation> bikeStations1=new ArrayList<BikeStation>();  //紧张
		List<BikeStation> bikeStations2=new ArrayList<BikeStation>();  //一般
		List<BikeStation> bikeStations3=new ArrayList<BikeStation>();  //很多
//		List<BikeStation> bikeStations4=new ArrayList<BikeStation>();
		
		for (int i = 0; i < dataList.size(); i++)
		{
			BikeStation temp=dataList.get(i);
			
			double percent=((double)temp.getLeft())*100/temp.getCount();
			
			if (percent<30)
			{
				bikeStations1.add(temp);
				point1.add(new GeoPoint(temp.getLatitude(),temp.getLongitude()));
			}
			else if(percent<60)
			{
				bikeStations2.add(temp);
				point2.add(new GeoPoint(temp.getLatitude(),temp.getLongitude()));
			}
			else
			{
				bikeStations3.add(temp);
				point3.add(new GeoPoint(temp.getLatitude(),temp.getLongitude()));
			}
		}

		if (mapControl != null)
		{
			mapControl.createMapOverItem(
					getActivity(),
					BikeStationInfoMapFragment.this.getActivity()
							.getLayoutInflater()
							.inflate(R.layout.map_pop_layout, null), point1,
					new onPopClick(bikeStations1),R.drawable.bike_red_1);
			
			mapControl.createMapOverItem(
					getActivity(),
					BikeStationInfoMapFragment.this.getActivity()
							.getLayoutInflater()
							.inflate(R.layout.map_pop_layout, null), point2,
					new onPopClick(bikeStations2),R.drawable.bike_yellow_1);
			
			mapControl.createMapOverItem(
					getActivity(),
					BikeStationInfoMapFragment.this.getActivity()
							.getLayoutInflater()
							.inflate(R.layout.map_pop_layout, null), point3,
					new onPopClick(bikeStations3),R.drawable.bike_green_1);
		}
	}

	class onPopClick implements OnPopInterface
	{
		int i;
		List<BikeStation> bikeStations;
		
		public onPopClick(List<BikeStation> bikeStations)
		{
			this.bikeStations=bikeStations;
		}

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (bikeStations != null)
			{
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);

				name.setText(bikeStations.get(i).getName());
				info.setText("可借" + bikeStations.get(i).getLeft() + " 可还"
						+ bikeStations.get(i).getBorrowed());
				goView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Bundle bundle=new Bundle();
						DrivingPlan drivingPlan=new DrivingPlan();
						drivingPlan.setLan1(locationGeoPoint.getLatitudeE6());
						drivingPlan.setLon1(locationGeoPoint.getLongitudeE6());
						drivingPlan.setLan2((int)(1e6*bikeStations.get(onPopClick.this.i).getLatitude()));
						drivingPlan.setLon2((int)(1e6*bikeStations.get(onPopClick.this.i).getLongitude()));
						drivingPlan.setStart("我的位置");
						drivingPlan.setEnd(bikeStations.get(onPopClick.this.i).getName());
						bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, drivingPlan);
						StartIntent.startIntentWithParam(BikeStationInfoMapFragment.this.getActivity(), TripLineMapActivity.class, bundle);
					}
				});
			}

			return v;
		}
	}

	BikeInputviewListener bikeInputviewListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_bike_info_map,
				container, false);

		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("公共自行车");
		fragment.setRightName("列表");
		fragment.setRightListener(new ListOnclick());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_map_head,
				fragment);

		initView(rootView);

		if (mapview == null)
		{
			mapview = (CNMKMapView) rootView
					.findViewById(R.id.driving_mapview_map);

			super.setMapView(mapview);
			initMapActivity();

			if (mapControl == null)
				mapControl = new MapControl(getActivity());
			mapControl.initMap(mapview);
			mapControl.setLocationListener(new locationListenerDefault());
			mapControl.addLocation();
		}

		bikeInputviewListener = new BikeInputviewListener(searchInputListView);
		InputListViewListener[] listenersInput = new InputListViewListener[] {
				mylocationListener,
				new VoiceListener(getActivity(), searchInputListView),
				selectMapPoint };
		bikeInputviewListener.setListener(listenersInput);

		request = new BikeStationRequestByLocation();

		if (getActivity().getIntent().getIntExtra(
				BikeStationDetailActivity.MAP_INTENT, 0) == 0)
		{
			mapControl.addLocation();
		} else if (getActivity().getIntent().getIntExtra(
				BikeStationDetailActivity.MAP_INTENT, 0) == 1)
		{
			BikeStation bikeStation = (BikeStation) getActivity().getIntent()
					.getSerializableExtra(BikeStationDetailActivity.MAP_PARAMS);
			if (dataList == null)
				dataList = new ArrayList<BikeStation>();
			dataList.add(bikeStation);
			mark(dataList);
		}

		search(30.700, 120.77);
		
		return rootView;
	}

	InputListViewListener mylocationListener = new InputListViewListener()
	{
		@Override
		public void inputSelectListener()
		{
			searchInputListView.showList(false);
			mapControl.clearOverlays();
			mapControl.setLocationListener(new locationForNearbyListener());
			mapControl.addLocation();
		}
	};

	InputListViewListener selectMapPoint = new InputListViewListener()
	{
		@Override
		public void inputSelectListener()
		{
			Toast.makeText(getActivity(), "暂无地图选点", Toast.LENGTH_SHORT).show();
		}
	};

	public void initView(View v)
	{
		locationImageView = (ImageView) v
				.findViewById(R.id.base_mapview_location);
		locationImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mapControl.addLocation();
			}
		});

		enlargeImageView = (ImageView) v
				.findViewById(R.id.base_mapview_enlarge);
		enlargeImageView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mapControl.zoomIn();
			}
		});

		disLargeImageView = (ImageView) v
				.findViewById(R.id.base_mapview_reduce);
		disLargeImageView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mapControl.zoomOut();
			}
		});

		search = (Button) v.findViewById(R.id.driving_search);
		search.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (searchInputListView.getText().equals(""))
					return;
				mapControl.clearOverlays();

				BikeStationRequestByStationName requestByStationName = new BikeStationRequestByStationName();
				requestByStationName.setStationName(searchInputListView
						.getText());
				new HttpThread(new BikeStationSearch(), requestByStationName,
						BikeStationInfoMapFragment.this,
						BikeStationInfoMapFragment.this.getActivity(),
						R.string.error_bike_station_info).start();

			}
		});

		searchInputListView = (InputListView) v
				.findViewById(R.id.driving_school_search_inputview);
		searchInputListView.setHint("请输入自行车站点名称");
	}

	class ListOnclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (replaceByParentLayout != null)
				replaceByParentLayout.replace(1);
		}
	}

	ReplaceByParentLayoutInterface replaceByParentLayout;

	public void setReplaceByParentLayout(
			ReplaceByParentLayoutInterface replaceByParentLayout)
	{
		this.replaceByParentLayout = replaceByParentLayout;
	}

	GeoPoint locationGeoPoint;

	public GeoPoint getLocationGeoPoint()
	{
		return locationGeoPoint;
	}
	
	class locationListenerDefault implements ICNMKLocationListener
	{

		@Override
		public void onGPSStatusChanged(GpsStatus arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLocationChanged(CNMKLocation location)
		{
			if (location != null)
			{
				GeoPoint pt = new GeoPoint(
						(int) (location.getLatitude() * (1e6 * AA.LV)),
						(int) (location.getLongitude() * (1e6 * AA.LV)));
				mapview.getController().setCenter(pt);
				locationGeoPoint = pt;
			}
			mapControl.deleteLocation();

		}
		
	}
	
	class locationForNearbyListener implements ICNMKLocationListener
	{
		@Override
		public void onGPSStatusChanged(GpsStatus arg0)
		{

		}

		@Override
		public void onLocationChanged(CNMKLocation location)
		{
			if (location != null)
			{
				GeoPoint pt = new GeoPoint(
						(int) (location.getLatitude() * (1e6 * AA.LV)),
						(int) (location.getLongitude() * (1e6 * AA.LV)));
				mapview.getController().setCenter(pt);
				locationGeoPoint = pt;
			}
			mapControl.deleteLocation();

			System.out.println("add location");

			request.setCount(20);
			request.setDistance(2000);
			search(location.getLatitude(), location.getLongitude());
		}
	}

	private void search(double lan, double lon)
	{
		request.setLan(lan);
		request.setLon(lon);

		new HttpThread(new BikeStationSearch(), request, this, getActivity(),
				R.string.error_bike_station_info).start();
	}

	private void searchReal()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String d)
			{
				System.out.println(d);

				BikeParkingPlaceInfoTemp temp = JSON.parseObject(
						DoString.parseThreeNetString(d),
						BikeParkingPlaceInfoTemp.class);

				for (BikeParkingPlaceInfo b : temp.getList())
				{
					for (BikeStation s : dataList)
					{
						if (b.getStationid() == s.getId())
						{
							s.setLeft(b.getCount());
							break;
						}
					}
				}

				return dataList;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String result = HttpUrlRequestAddress.SMART_BIKE_STATION_REAL_BY_ID_URL
						+ "?bikeList=";

				for (int i = 0; i < dataList.size(); i++)
				{
					if (i > 0)
						result += ",";
					result += dataList.get(i).getId();
				}

				System.out.println(result);

				return result;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object d)
			{
				mark(dataList);
			}
		}, getActivity(), R.string.error_bike_station_info).start();
	}

	@Override
	public void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) getActivity()
				.getApplication();
		app.mCNMKAPImgr.start();

		super.onResume();
	}

	@Override
	public void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) getActivity()
				.getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}
}
