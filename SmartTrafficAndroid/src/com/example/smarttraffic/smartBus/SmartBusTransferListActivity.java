package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.GeoPoint;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.alarm.AlarmSearchActivity;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.nearby.NearByActivity;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.TransferAdapter;
import com.example.smarttraffic.smartBus.bean.Transfer;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SmartBusTransferListActivity extends FragmentActivity
{
	public static final String SMART_BUS_TRANSFER_LIST_START_NAME = "smart_bus_transfer_list_start_name";
	public static final String SMART_BUS_TRANSFER_LIST_END_NAME = "smart_bus_transfer_list_end_name";
	public static final String SMART_BUS_TRANSFER_LIST_START_GEO_lAN = "smart_bus_transfer_list_start_geo_lan";
	public static final String SMART_BUS_TRANSFER_LIST_END_GEO_LAN = "smart_bus_transfer_list_end_geo_lan";
	public static final String SMART_BUS_TRANSFER_LIST_START_GEO_lON = "smart_bus_transfer_list_start_geo_lon";
	public static final String SMART_BUS_TRANSFER_LIST_END_GEO_LON = "smart_bus_transfer_list_end_geo_lon";

	private String start;
	private String end;
	private double lan1;
	private double lon1;
	private double lan2;
	private double lon2;

	TextView startTextView;
	TextView endTextView;

	ListView listView;

	String tempStringData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_transfer_list);

		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("换乘方案");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString(
						TransferDetailMapActivity.TRANSFER_DETAIL_MAP_DATA,
						tempStringData);
				bundle.putDouble(SMART_BUS_TRANSFER_LIST_START_GEO_lAN, lan1);
				bundle.putDouble(SMART_BUS_TRANSFER_LIST_END_GEO_LAN, lan2);
				bundle.putDouble(SMART_BUS_TRANSFER_LIST_START_GEO_lON, lon1);
				bundle.putDouble(SMART_BUS_TRANSFER_LIST_END_GEO_LON, lon2);

				StartIntent.startIntentWithParam(
						SmartBusTransferListActivity.this,
						TransferDetailMapActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this,
				R.id.smart_bus_transfer_list_head, fragment);

		start = getIntent().getStringExtra(SMART_BUS_TRANSFER_LIST_START_NAME);
		end = getIntent().getStringExtra(SMART_BUS_TRANSFER_LIST_END_NAME);
		lan1 = getIntent().getDoubleExtra(
				SMART_BUS_TRANSFER_LIST_START_GEO_lAN, 0);
		lon1 = getIntent().getDoubleExtra(
				SMART_BUS_TRANSFER_LIST_START_GEO_lON, 0);
		lan2 = getIntent().getDoubleExtra(SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
				0);
		lon2 = getIntent().getDoubleExtra(SMART_BUS_TRANSFER_LIST_END_GEO_LON,
				0);

		selectID = 0;
		initView();

		transfor();

		search();
	}

	/**
	 * 02转84
	 */
	private void transfor()
	{
		GeoPoint point1 = PointTraslation.gcj_To_Gps84(lan1, lon1);
		GeoPoint point2 = PointTraslation.gcj_To_Gps84(lan2, lon2);

		lan1 = point1.getLatitudeE6() * 1.0 / 1e6;
		lon1 = point1.getLongitudeE6() * 1.0 / 1e6;

		lan2 = point2.getLatitudeE6() * 1.0 / 1e6;
		lon2 = point2.getLongitudeE6() * 1.0 / 1e6;
	}

	public void initView()
	{
		startTextView = (TextView) findViewById(R.id.smart_bus_transfer_list_start);
		endTextView = (TextView) findViewById(R.id.smart_bus_transfer_list_end);
		listView = (ListView) findViewById(R.id.smart_bus_transfer_list_listview);

		startTextView.setText(start);
		endTextView.setText(end);
	}

	int selectID;

	public void select(View v)
	{
		int[] selects = new int[] { R.id.smart_bus_transfer_list_select_1,
				R.id.smart_bus_transfer_list_select_2,
				R.id.smart_bus_transfer_list_select_3,
				R.id.smart_bus_transfer_list_select_4,
				R.id.smart_bus_transfer_list_select_5 };

		for (int i = 0; i < selects.length; i++)
		{
			if (v.getId() == selects[i])
			{
				if (i == selectID)
				{
					return;
				} else
				{
					findViewById(selects[selectID]).setBackgroundResource(
							R.drawable.textview_border_shape);
					((TextView) findViewById(selects[selectID]))
							.setTextColor(getResources().getColor(
									R.color.color_smart_bus_item_unselect));
					findViewById(selects[i]).setBackgroundResource(
							R.color.color_smart_bus_item_select);
					((TextView) findViewById(selects[i]))
							.setTextColor(getResources().getColor(
									R.color.back_groud_white));
					selectID = i;
				}

				search();
				return;
			}
		}

		return;
	}

	public void search()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				tempStringData = DoString.parseThreeNetString(data);

				System.out.println("result:" + tempStringData);

				List<Transfer> result = new ArrayList<Transfer>();

				try
				{
					JSONObject jsonObject = new JSONObject(tempStringData);
					JSONArray array = jsonObject.getJSONArray("List");

					for (int i = 0; i < array.length(); i++)
					{
						jsonObject = array.getJSONObject(i);
						Transfer temp = new Transfer();

						temp.setDistance(jsonObject.getDouble("Distance"));
						temp.setPrice(jsonObject.getDouble("Price"));
						temp.setTime(jsonObject.getString("Time"));

						JSONArray tempArray = jsonObject.getJSONArray("LineList");

						List<String> linename = new ArrayList<String>();

						for (int j = 0; j < tempArray.length(); j++)
						{
							linename.add(tempArray.getString(j));
						}
						temp.setLineList(linename);

						result.add(temp);
					}
				} catch (JSONException e)
				{
					System.out.println(e);
				}
				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String url = HttpUrlRequestAddress.SMART_BUS_TRANSFER;
				url += "?startLantitude=" + lan1;
				url += "&startLontitude=" + lon1;
				url += "&endLantitude=" + lan2;
				url += "&endLontitude=" + lon2;
				url += "&order=" + (selectID + 1);
				url += "&count=" + 5;
				url += "&isBike=true";

				System.out.println("aaa:" + url);

				// String url =
				// "http://115.231.73.253:8091/zhjtapi/bus/queryBusTransfer?";
				// url += "startPointLat=" + lan1;
				// url += "&startPointLon=" + lon1;
				// url += "&endPointLat=" + lan2;
				// url += "&endPointLon=" + lon2;
				// url += "&order=" + (selectID + 1);
				return url;
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				List<Transfer> transfers = (List<Transfer>) data;
				listView.setAdapter(new TransferAdapter(
						SmartBusTransferListActivity.this, transfers));
				listView.setOnItemClickListener(lineClickListener);
			}
		}, this, R.string.error_smart_bus_transfer).start();
	}

	public OnItemClickListener lineClickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Bundle bundle = new Bundle();
			bundle.putString(TransferDetailActivity.TRANSFER_DETAIL_DATA,
					tempStringData);
			bundle.putInt(TransferDetailActivity.TRANSFER_DETAIL_DATA_ID,
					position);
			bundle.putString(TransferDetailActivity.START_NAME, start);
			bundle.putString(TransferDetailActivity.END_NAME, end);
			bundle.putDouble(SMART_BUS_TRANSFER_LIST_START_GEO_lAN, lan1);
			bundle.putDouble(SMART_BUS_TRANSFER_LIST_END_GEO_LAN, lan2);
			bundle.putDouble(SMART_BUS_TRANSFER_LIST_START_GEO_lON, lon1);
			bundle.putDouble(SMART_BUS_TRANSFER_LIST_END_GEO_LON, lon2);

			StartIntent.startIntentWithParam(SmartBusTransferListActivity.this,
					TransferDetailActivity.class, bundle);
		}
	};

	public void changeStartAndEnd(View v)
	{
		String temp = start;
		start = end;
		end = temp;
		startTextView.setText(start);
		endTextView.setText(end);

		double temp1 = lan1;
		lan1 = lan2;
		lan2 = temp1;

		temp1 = lon1;
		lon1 = lon2;
		lon2 = temp1;

		search();
	}

	private void saveFavor()
	{
		FavorDBOperate dbOperate = new FavorDBOperate(this);

		if (!dbOperate
				.isFavor(ContentType.SMART_BUS_TRANSFER_FAVOR, start, end))
			dbOperate.insert(ContentType.SMART_BUS_TRANSFER_FAVOR, start, end,
					lan1, lon1, lan2, lon2);

		dbOperate.CloseDB();
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{

		case R.id.smart_bus_transfer_detail_clock:
			StartIntent.startIntent(this, AlarmSearchActivity.class);
			break;

		case R.id.smart_bus_transfer_detail_circle:
			StartIntent.startIntent(this, NearByActivity.class);
			break;

		case R.id.smart_bus_transfer_detail_favor:
			saveFavor();
			break;

		case R.id.smart_bus_transfer_detail_suggestion:
			Bundle suggesiontBundle = new Bundle();
			suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 9);
			StartIntent.startIntentWithParam(this, SuggestionActivity.class,
					suggesiontBundle);
			break;

		case R.id.smart_bus_transfer_detail_debug:
			Bundle debugBundle = new Bundle();
			debugBundle.putInt(DebugActivity.DEBUG_NAME, 9);
			StartIntent.startIntentWithParam(this, DebugActivity.class,
					debugBundle);
			break;
		}
	}

	public void refreshLine(View v)
	{
		search();
	}
}
