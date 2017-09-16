package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.search.CNMKNewPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKNewPoiResult;
import cennavi.cenmapsdk.android.search.CNMKPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.nearby.NearbyDetailListAdapter;
import com.example.smarttraffic.smartBus.fragment.SmartBusTransferFragment;
import com.example.smarttraffic.util.StartIntent;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectPointActivity extends FragmentActivity
{
	public static final String START_LAN = "start_lan";
	public static final String START_LON = "start_lon";
	public static final String END_LAN = "end_lan";
	public static final String END_LON = "end_lon";

	double startLan;
	double startLon;
	double endLan;
	double endLon;

	String start;
	String end;
	double lan1;
	double lon1;

	ListView content;

	public static final String SELECT_POINT_ID = "select_point_id";
	int id;

	MapControl mapControl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);

		start = getIntent()
				.getStringExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME);
		end = getIntent().getStringExtra(
				SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME);

		startLan = getIntent().getDoubleExtra(START_LAN, 0);
		startLon = getIntent().getDoubleExtra(START_LON, 0);
		endLan = getIntent().getDoubleExtra(END_LAN, 0);
		endLon = getIntent().getDoubleExtra(END_LON, 0);

		id = getIntent().getIntExtra(SELECT_POINT_ID, 0);

		if (id == 0)
			initHead(start);
		else if (id == 1)
		{
			lan1 = getIntent()
					.getDoubleExtra(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
							0);
			lon1 = getIntent()
					.getDoubleExtra(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
							0);
			initHead(end);
		}

		initListView();

		if (id == 0 && startLan > 1 && startLon > 1)
		{
			Bundle bundle = new Bundle();
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
					start);
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
					end);

			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
					startLan);
			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
					startLon);

			bundle.putDouble(END_LAN, endLan);
			bundle.putDouble(END_LON, endLon);

			bundle.putInt(SELECT_POINT_ID, 1);

			StartIntent.startIntentWithParam(SelectPointActivity.this,
					SelectPointActivity.class, bundle);
			this.finish();
		} else if (id == 0
				&& start.equals(SmartBusTransferFragment.MY_LOCATION))
		{
			mapControl = new MapControl(this);
			mapControl.setLocationListener(new ICNMKLocationListener()
			{

				@Override
				public void onLocationChanged(CNMKLocation arg0)
				{
					mapControl.removeLocationWithoutView();

					Bundle bundle = new Bundle();
					bundle.putString(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
							start);
					bundle.putString(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
							end);

					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
							arg0.getLatitude());
					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
							arg0.getLongitude());

					bundle.putDouble(END_LAN, endLan);
					bundle.putDouble(END_LON, endLon);

					bundle.putInt(SELECT_POINT_ID, 1);

					StartIntent.startIntentWithParam(SelectPointActivity.this,
							SelectPointActivity.class, bundle);
					SelectPointActivity.this.finish();
				}

				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{
				}
			});
			mapControl.addLocationWithoutView();
		} else if (id == 1 && endLan > 1 && endLon > 1)
		{
			Bundle bundle = new Bundle();
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
					start);
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
					end);

			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
					lan1);
			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
					lon1);

			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
					endLan);
			bundle.putDouble(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
					endLon);

			StartIntent.startIntentWithParam(SelectPointActivity.this,
					SmartBusTransferListActivity.class, bundle);

			SelectPointActivity.this.finish();
		} else if (id == 1 && end.equals(SmartBusTransferFragment.MY_LOCATION))
		{
			mapControl = new MapControl(this);
			mapControl.setLocationListener(new ICNMKLocationListener()
			{

				@Override
				public void onLocationChanged(CNMKLocation arg0)
				{
					mapControl.removeLocationWithoutView();

					Bundle bundle = new Bundle();
					bundle.putString(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
							start);
					bundle.putString(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
							end);

					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
							lan1);
					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
							lon1);

					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
							arg0.getLatitude());
					bundle.putDouble(
							SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
							arg0.getLongitude());

					StartIntent.startIntentWithParam(SelectPointActivity.this,
							SmartBusTransferListActivity.class, bundle);

					SelectPointActivity.this.finish();
				}

				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{
				}
			});
			mapControl.addLocationWithoutView();
		} else
		{
			request();
		}

	}

	private void initListView()
	{
		content = (ListView) findViewById(R.id.smart_bus_select_point_content);
		content.setOnItemClickListener(contentClickListener);
	}

	private void initHead(String str)
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("\"" + str + "\"相关地点");
		ManagerFragment.replaceFragment(this, R.id.smart_bus_select_point_head,
				fragment);
	}

	private void request()
	{
		if (mapControl == null)
			mapControl = new MapControl(SelectPointActivity.this);

		mapControl.setSearchPoi(new SearchListener()
		{

			@Override
			public void searchListener(Object arg0, int arg1, boolean arg2,
					String arg3)
			{
				try
				{
					if (arg0 == null)
					{
						Toast.makeText(SelectPointActivity.this, "无数据",
								Toast.LENGTH_SHORT).show();
						return;
					}
//					CNMKNewPoiResult result = (CNMKNewPoiResult) arg0;
//
//					List<CNMKNewPoiInfo> list = result.getmPois();
//
//					List<NearBy> data = new ArrayList<NearBy>();
//
//					for (int i = 0; i < list.size(); i++)
//					{
//						NearBy temp = new NearBy();
//
//						CNMKNewPoiInfo temp1 = list.get(i);
//
//						temp.setAddress(temp1.getAddress());
//						temp.setId("");
//						temp.setLan(temp1.getGp().getLatitudeE6() * 1.0 / 1e6);
//						temp.setLon(temp1.getGp().getLongitudeE6() * 1.0 / 1e6);
//						temp.setName(temp1.getName());
//						temp.setPhone("");
//						temp.setRegion("");
//						temp.setStreet("");
//						temp.setTime("");
//
//						data.add(temp);
//					}

					CNMKPoiResult result = (CNMKPoiResult) arg0;

					List<CNMKPoiInfo> list = result.getPois();

					List<NearBy> data = new ArrayList<NearBy>();

					for (int i = 0; i < list.size(); i++)
					{
						NearBy temp = new NearBy();

						CNMKPoiInfo temp1 = list.get(i);

						temp.setAddress(temp1.getAddress());
						temp.setId("");
						temp.setLan(temp1.getGeoPoint().getLatitudeE6() * 1.0 / 1e6);
						temp.setLon(temp1.getGeoPoint().getLongitudeE6() * 1.0 / 1e6);
						temp.setName(temp1.getName());
						temp.setPhone("");
						temp.setRegion("");
						temp.setStreet("");
						temp.setTime("");

						data.add(temp);
					}
					
					content.setAdapter(new NearbyDetailListAdapter(
							SelectPointActivity.this, (List<NearBy>) data,
							false));
				} catch (Exception e)
				{
				}

			}
		});

		mapControl.searchPOI(id == 0 ? start : end);
	}

	// private void request()
	// {
	// new HttpThread(new BaseSearch()
	// {
	//
	// @Override
	// public Object parse(String data)
	// {
	// List<NearBy> result = new ArrayList<NearBy>();
	//
	// try
	// {
	// JSONObject jsonObject = new JSONObject(data);
	// JSONArray array = jsonObject.getJSONArray("circles");
	//
	// for (int i = 0; i < array.length(); i++)
	// {
	// NearBy temp = new NearBy();
	//
	// jsonObject = array.getJSONObject(i);
	//
	// temp.setAddress(jsonObject.getString("address"));
	// temp.setId(jsonObject.getString("id"));
	// temp.setLan(jsonObject.getDouble("lan"));
	// temp.setLon(jsonObject.getDouble("lon"));
	// temp.setName(jsonObject.getString("name"));
	// temp.setRegion(jsonObject.getString("region"));
	// temp.setStreet(jsonObject.getString("street"));
	// temp.setTime(jsonObject.getString("time"));
	//
	// result.add(temp);
	// }
	// } catch (JSONException e)
	// {
	// e.printStackTrace();
	// }
	//
	// return result;
	// }
	// }, new BaseRequest()
	// {
	// @Override
	// public PostParams CreatePostParams()
	// {
	// PostParams param = new PostParams();
	// param.setUrl(HttpUrlRequestAddress.SMART_BUS_CIRCLE_BY_NAME_URL);
	//
	// Map<String, Object> p = new HashMap<String, Object>();
	//
	// if (id == 0)
	// p.put("name", start);
	// else
	// p.put("name", end);
	// param.setParams(p);
	//
	// return param;
	// }
	// }, new UpdateView()
	// {
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public void update(Object data)
	// {
	// content.setAdapter(new NearbyDetailListAdapter(
	// SelectPointActivity.this, (List<NearBy>) data, false));
	// }
	// }, this, R.string.error_smart_bus_point_info).start();
	// }

	OnItemClickListener contentClickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long ids)
		{
			NearBy nearBy = ((NearbyDetailListAdapter) parent.getAdapter())
					.getData().get(position);

			Bundle bundle = new Bundle();

			if (id == 0)
			{
				bundle.putString(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
						nearBy.getName());
				bundle.putString(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
						end);

				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
						nearBy.getLan());
				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
						nearBy.getLon());

				bundle.putDouble(END_LAN, endLan);
				bundle.putDouble(END_LON, endLon);

				bundle.putInt(SELECT_POINT_ID, 1);

				StartIntent.startIntentWithParam(SelectPointActivity.this,
						SelectPointActivity.class, bundle);
				SelectPointActivity.this.finish();
			} else if (id == 1)
			{
				bundle.putString(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
						start);
				bundle.putString(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
						nearBy.getName());

				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
						lan1);
				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
						lon1);

				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
						nearBy.getLan());
				bundle.putDouble(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
						nearBy.getLon());

				StartIntent.startIntentWithParam(SelectPointActivity.this,
						SmartBusTransferListActivity.class, bundle);
				SelectPointActivity.this.finish();
			}

			
		}
	};

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();
		super.onResume();
	}

}
