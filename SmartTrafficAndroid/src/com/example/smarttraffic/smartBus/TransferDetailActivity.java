package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.smartBus.adapter.TransferDetailAdapter;
import com.example.smarttraffic.smartBus.bean.Transfer;
import com.example.smarttraffic.smartBus.bean.TransferLine;
import com.example.smarttraffic.smartBus.bean.TransferLineDetail;
import com.example.smarttraffic.smartBus.bean.TransferLineTrails;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class TransferDetailActivity extends FragmentActivity
{

	public static final String TRANSFER_DETAIL_DATA = "transfer_detail_data";
	public static final String TRANSFER_DETAIL_DATA_ID = "transfer_detail_data_id";
	public static final String START_NAME = "start_name";
	public static final String END_NAME = "end_name";

	String data;
	int id;
	String start;
	String end;

	TextView nameTextView;
	TextView infoTextView;

	ListView contentListView;

	double lan1;
	double lon1;
	double lan2;
	double lon2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_detail);

		data = getIntent().getStringExtra(TRANSFER_DETAIL_DATA);
		id = getIntent().getIntExtra(TRANSFER_DETAIL_DATA_ID, 0);

		start = getIntent().getStringExtra(START_NAME);
		end = getIntent().getStringExtra(END_NAME);

		lan1 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
						0);
		lon1 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
						0);
		lan2 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
						0);
		lon2 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
						0);
		
		initHead();
		initView();

		show(parse(data, id));
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("线路换乘详情");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString(
						TransferDetailMapActivity.TRANSFER_DETAIL_MAP_DATA,
						data);
				bundle.putInt(
						TransferDetailMapActivity.TRANSFER_DETAIL_MAP_DATA_ID,
						id);
				
				bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN, lan1);
				bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN, lan2);
				bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON, lon1);
				bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON, lon2);

				StartIntent.startIntentWithParam(TransferDetailActivity.this,
						TransferDetailMapActivity.class, bundle);
			}
		});

		ManagerFragment.replaceFragment(this, R.id.transfer_detail_head,
				fragment);
	}

	private void initView()
	{
		nameTextView = (TextView) findViewById(R.id.transfer_detail_name);
		infoTextView = (TextView) findViewById(R.id.transfer_detail_info);
		contentListView = (ListView) findViewById(R.id.transfer_detail_content);
	}

	/*
	 * id从0开始
	 */
	private void show(Transfer info)
	{
		if (info == null)
			return;
		Transfer transfer = info;

		String name = "";
		for (String s : transfer.getLineList())
			name += s + "->";

		name = name.substring(0, name.length() - 2);

		nameTextView.setText(name);
		infoTextView.setText("全程" + transfer.getDistance() + "米/" + "耗时"
				+ transfer.getTime() + "分");

		TransferDetailAdapter transferDetailAdapter = new TransferDetailAdapter(
				this, transfer.getTransferLines(), start, end);
		contentListView.setAdapter(transferDetailAdapter);
	}

	public static Transfer parse(String data, int id)
	{
		Transfer result = new Transfer();

		try
		{
			JSONObject jsonObject = new JSONObject(data);
			JSONArray array = jsonObject.getJSONArray("List");

			jsonObject = array.getJSONObject(id);
			result.setDistance(jsonObject.getDouble("Distance"));
			result.setPrice(jsonObject.getDouble("Price"));
			result.setTime(jsonObject.getString("Time"));
			JSONArray tempArray = jsonObject.getJSONArray("LineList");
			List<String> linename = new ArrayList<String>();
			for (int j = 0; j < tempArray.length(); j++)
			{
				linename.add(tempArray.getString(j));
			}
			result.setLineList(linename);

			array = jsonObject.getJSONArray("NewList");

			List<TransferLine> transferLines = new ArrayList<TransferLine>();

			for (int i = 0; i < array.length(); i++)
			{
				JSONObject jsonObject2 = array.getJSONObject(i);

				TransferLine tempTransferLine = new TransferLine();
				tempTransferLine.setDirection(jsonObject2
						.getString("Direction"));
				tempTransferLine.setDistance(jsonObject2.getDouble("Distance"));
				tempTransferLine.setLan(jsonObject2.getDouble("Latitude"));
				tempTransferLine.setLon(jsonObject2.getDouble("Longitude"));

				try
				{
					tempTransferLine.setStationID(jsonObject2.getInt("StationId"));
				} catch (Exception e)
				{
				}
				try
				{
					tempTransferLine.setStationName(jsonObject2.getString("StationName"));
				} catch (Exception e)
				{
				}

				tempTransferLine.setTime(jsonObject2.getString("Time"));
				tempTransferLine.setType(jsonObject2.getInt("Type"));
				try
				{
					tempTransferLine.setStationType(jsonObject2.getInt("StationType"));
					tempTransferLine.setBikeStationAddress(jsonObject2.getString("BikeStationAddress"));
				}
				catch(Exception e)
				{
				}

				if (tempTransferLine.getType() == 1)
				{
					if (jsonObject2.getJSONObject("LineDetails") != null)
					{
						TransferLineDetail transferLineDetail = new TransferLineDetail();

						JSONObject lineJsonObject = jsonObject2
								.getJSONObject("LineDetails");

						transferLineDetail.setCount(lineJsonObject
								.getInt("StationCount"));
						transferLineDetail.setDistance(lineJsonObject
								.getDouble("Distance"));
						transferLineDetail.setEndTime(lineJsonObject
								.getString("EndTime"));
						transferLineDetail.setId(lineJsonObject.getInt("Id"));
						transferLineDetail.setLineType(lineJsonObject
								.getInt("LineType"));
						transferLineDetail.setName(lineJsonObject
								.getString("Name"));
						transferLineDetail.setPrice(lineJsonObject
								.getDouble("Price"));
						transferLineDetail.setStartTime(lineJsonObject
								.getString("StartTime"));

						tempTransferLine.setLineDetails(transferLineDetail);
					}

					if (jsonObject2.getJSONArray("LineTrailsList") != null)
					{

						JSONArray array2 = jsonObject2
								.getJSONArray("LineTrailsList");

						List<TransferLineTrails> trails = new ArrayList<TransferLineTrails>();

						for (int j = 0; j < array2.length(); j++)
						{
							JSONObject jsonObject3 = array2.getJSONObject(j);

							TransferLineTrails tempLineTrails = new TransferLineTrails();

							tempLineTrails.setLantitude(jsonObject3
									.getDouble("Latitude"));
							tempLineTrails.setLongtitude(jsonObject3
									.getDouble("Longitude"));
							tempLineTrails.setTrainIndex(jsonObject3
									.getInt("TrailIndex"));

							trails.add(tempLineTrails);
						}

						tempTransferLine.setLineTrails(trails);
					}
				}

				transferLines.add(tempTransferLine);
			}

			result.setTransferLines(transferLines);

		} catch (JSONException e)
		{
			System.out.println(e.toString());

			e.printStackTrace();
		}

		return result;
	}
}
