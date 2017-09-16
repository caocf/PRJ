package com.example.smarttraffic.smartBus;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.alarm.AlarmStationSelectActivity;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.nearby.NearByActivity;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.bean.BusArrive;
import com.example.smarttraffic.smartBus.bean.BusLineDetail;
import com.example.smarttraffic.smartBus.bean.LineInfo;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.BusHorizontalView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 线路详情
 * 
 * @author Administrator zhou
 * 
 */
public class SmartBusLineDetailActivity extends FragmentActivity
{

	BusHorizontalView busHorizontalView;

	TextView startTextView;
	TextView endTextView;
	TextView timeTextView;
	TextView priceTextView;
	TextView fullTimeTextView;
	TextView interTextView;
	public static final String SMART_BUS_LINE_ID = "smart_bus_line_id";
	public static final String SMART_BUS_LINE_DIRECT = "smart_bus_line_dirct";
	public static final String SMART_BUS_LINE_NAME = "smart_bus_line_name";

	int id;
	int stationID;
	int direct;
	String tempData;
	String name;

	BusLineDetail result;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_line_detail);

		id = getIntent().getIntExtra(SMART_BUS_LINE_ID, -1);int id=this.id;
		direct = getIntent().getIntExtra(SMART_BUS_LINE_DIRECT, 1);int d=direct;
		name = getIntent().getStringExtra(SMART_BUS_LINE_NAME);
		initView();
		initHead("线路详情(" + name + ")");

		query();
	}

	private void initView()
	{
		startTextView = (TextView) findViewById(R.id.smart_bus_line_detail_start);
		endTextView = (TextView) findViewById(R.id.smart_bus_line_detail_end);
		timeTextView = (TextView) findViewById(R.id.smart_bus_line_detail_time);
		priceTextView = (TextView) findViewById(R.id.smart_bus_line_detail_price);
		interTextView = (TextView) findViewById(R.id.smart_bus_line_detail_intertime);
		fullTimeTextView = (TextView) findViewById(R.id.smart_bus_line_detail_fulltime);
	}

	private final int DEFALT_NUM = 10;

	private void queryReal(int s)
	{
		this.stationID = s;

		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				BusArrive busArrive = new BusArrive();

				busArrive = JSON.parseObject(
						DoString.parseThreeNetString(data), BusArrive.class);
				return busArrive;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{			
				return HttpUrlRequestAddress.SMART_BUS_STATION_REAL_INFO_URL
						+ "?stationID=" + stationID + "&lineID=" + id
						+ "&direct=" + direct + "&count=" + DEFALT_NUM;
			}
		}, new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				BusArrive arrive = (BusArrive) data;

				busHorizontalView.setBusLocations(arrive.getBusLocationList());
				busHorizontalView.refresh();
			}
		}, getResources().getString(R.string.error_smart_bus_station_real))
				.start();
	}

	private void query()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				BusLineDetail busLineDetail = new BusLineDetail();
				tempData = DoString.parseThreeNetString(data);

				busLineDetail = JSON.parseObject(tempData, BusLineDetail.class);
				return busLineDetail;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_LINE_BY_ID_URL
						+ "?lineID=" + id + "&direct=" + direct;

			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				result = (BusLineDetail) data;

				if (direct !=2)
				{
					// startTextView.setText(result.getLine().getUpStartStationName());
					// endTextView.setText(result.getLine().getUpEndStationName());

					startTextView.setText(result.getUpList().get(0)
							.getStationName());
					endTextView.setText(result.getUpList()
							.get(result.getUpList().size() - 1)
							.getStationName());

					timeTextView.setText("首末班："
							+ result.getLine().getLineUpStartTime() + "-"
							+ result.getLine().getLineUpEndTime());
					priceTextView.setText("票价（元）："
							+ result.getLine().getPrice());
					interTextView.setText("发班间隔（分）："
							+ result.getLine().getLineupIntervalTime());
					fullTimeTextView.setText("线路长度（米）："
							+ result.getLine().getLineupLength());

					busHorizontalView = (BusHorizontalView) findViewById(R.id.busHorizontalView1);
					busHorizontalView.setDirect(1);
					busHorizontalView.setBusLine(result.getLine());
					busHorizontalView.setStationList(result.getUpList());
					busHorizontalView
							.setActivity(SmartBusLineDetailActivity.this);

					busHorizontalView.refresh();
				} 
				else
				{
					startTextView.setText(result.getDownList().get(0)
							.getStationName());
					endTextView.setText(result.getDownList()
							.get(result.getDownList().size() - 1)
							.getStationName());

					timeTextView.setText("首末班："
							+ result.getLine().getLineDownStartTime() + "-"
							+ result.getLine().getLineDownEndTime());
					priceTextView.setText("票价（元）："
							+ result.getLine().getPrice());
					interTextView.setText("发班间隔（分）："
							+ result.getLine().getLinedownIntervalTime());
					fullTimeTextView.setText("线路长度（米）："
							+ result.getLine().getLineupLength());

					busHorizontalView = (BusHorizontalView) findViewById(R.id.busHorizontalView1);
					busHorizontalView.setDirect(2);
					busHorizontalView.setBusLine(result.getLine());
					busHorizontalView.setStationList(result.getDownList());
					busHorizontalView.setActivity(SmartBusLineDetailActivity.this);
					busHorizontalView.refresh();
				}

				queryReal(busHorizontalView.getStationList()
						.get(busHorizontalView.getStationList().size() - 1)
						.getStationId());

				isFavor();

			}
		}, this, R.string.error_smart_bus_line_detail).start();
	}

	long clickTime=-1;
	
	private void initHead(String name)
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName(name);
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				long now =new Date().getTime();
				
				if((now-clickTime)<1000)
				{
					return ;
				}
				
				clickTime=now;
				
				Bundle bundle = new Bundle();
				bundle.putString(
						SmartBusLineMapActivity.SMART_BUS_LINE_MAP_DATA,
						tempData);
				bundle.putString(
						SmartBusLineMapActivity.SMART_BUS_LINE_MAP_NAME,
						SmartBusLineDetailActivity.this.name);
				bundle.putInt(
						SmartBusLineMapActivity.SMART_BUS_LINE_MAP_DIRECT,
						direct);
				bundle.putInt(SmartBusLineMapActivity.SMART_BUS_LINE_MAP_ID, id);

				StartIntent.startIntentWithParam(
						SmartBusLineDetailActivity.this,
						SmartBusLineMapActivity.class, bundle);
			}
		});

		ManagerFragment.replaceFragment(this, R.id.smart_bus_line_detail_head,
				fragment);
	}

	private void isFavor()
	{
		if (result == null)
			return;

		LineInfo lineInfo = new LineInfo();
		lineInfo.setId(id);
		lineInfo.setName(result.getLine().getName());
		lineInfo.setStart(result.getLine().getUpStartStationName());
		lineInfo.setStartTime(result.getLine().getLineUpStartTime());
		lineInfo.setEnd(result.getLine().getUpEndStationName());
		lineInfo.setEndTime(result.getLine().getLineUpEndTime());

		FavorDBOperate dbOperate = new FavorDBOperate(this);
		if (dbOperate.isFavor(ContentType.SMART_BUS_LINE_FAVOR, lineInfo))
		{
			((ImageView) findViewById(R.id.smart_bus_line_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor_checked);

		}
	}

	private void saveFavor()
	{
		LineInfo lineInfo = new LineInfo();
		lineInfo.setId(id);
		lineInfo.setName(result.getLine().getName());
		lineInfo.setStart(result.getLine().getUpStartStationName());
		lineInfo.setStartTime(result.getLine().getLineUpStartTime());
		lineInfo.setEnd(result.getLine().getUpEndStationName());
		lineInfo.setEndTime(result.getLine().getLineUpEndTime());

		FavorDBOperate dbOperate = new FavorDBOperate(this);
		if (dbOperate.isFavor(ContentType.SMART_BUS_LINE_FAVOR, lineInfo))
		{
			dbOperate.deleteFavor(ContentType.SMART_BUS_LINE_FAVOR, lineInfo);
			((ImageView) findViewById(R.id.smart_bus_line_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor);

			Toast.makeText(this, "收藏已取消", Toast.LENGTH_SHORT).show();
		} else
		{
			dbOperate.insertLine(ContentType.SMART_BUS_LINE_FAVOR, lineInfo);
			((ImageView) findViewById(R.id.smart_bus_line_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor_checked);
			Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
		}
		dbOperate.CloseDB();
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_line_detail_back:
			direct = direct == 1 ? 2 : 1;
			query();
			break;
		case R.id.smart_bus_line_detail_refresh:
			queryReal(stationID);
			break;

		case R.id.smart_bus_line_detail_clock:
			// StartIntent.startIntent(this, AlarmSearchActivity.class);

			Bundle bundle = new Bundle();
			bundle.putSerializable(AlarmStationSelectActivity.STATION_INFO,
					result);

			StartIntent.startIntentWithParam(this,
					AlarmStationSelectActivity.class, bundle);

			break;

		case R.id.smart_bus_line_detail_circle:
			StartIntent.startIntent(this, NearByActivity.class);
			break;

		case R.id.smart_bus_line_detail_favor:
			saveFavor();
			break;

		case R.id.smart_bus_line_detail_suggestion:
			Bundle suggesiontBundle = new Bundle();
			suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 9);
			StartIntent.startIntentWithParam(this, SuggestionActivity.class,
					suggesiontBundle);
			break;

		case R.id.smart_bus_line_detail_debug:
			Bundle debugBundle = new Bundle();
			debugBundle.putInt(DebugActivity.DEBUG_NAME, 9);
			StartIntent.startIntentWithParam(this, DebugActivity.class,
					debugBundle);
			break;
		}
	}

	// private void moni()
	// {
	// busHorizontalView=(BusHorizontalView)findViewById(R.id.busHorizontalView1);
	//
	// busHorizontalView.setStationList(new
	// String[]{"途径站点1","途径站点2","途径站点3","途径站点4","途径站点5","途径站点6",
	// "途径站点7","途径站点8","途径站点9","途径站点10","途径站点11","途径站点12","途径站点1","途径站点2","途径站点3","途径站点4","途径站点5","途径站点6",
	// "途径站点7","途径站点8","途径站点9","途径站点10","途径站点11","途径站点12"});
	//
	// busHorizontalView.setRealList(new int[]{4,9});
	// busHorizontalView.refresh();
	// }
}
