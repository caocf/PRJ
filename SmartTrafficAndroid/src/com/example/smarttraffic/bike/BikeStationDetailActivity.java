package com.example.smarttraffic.bike;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfo;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfoTemp;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.db.BikeFavorDB;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.GoThereMapActivity;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BikeStationDetailActivity extends FragmentActivity
{

	TextView idTextView;
	TextView nameTextView;
	TextView addressTextView;
	TextView infoTextView;
	TextView timeTextView;

	boolean isFavor;
	BikeStation bikeStation;

	public static String IS_NEED_REQUEST = "is_need_request";
	int needReload;

	// private String EXTERN_REQUEST_ID="extern_request_id";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_bike_station);

		initHead(savedInstanceState);
		initView();
	}

	public void initView()
	{		
		needReload=getIntent().getIntExtra(IS_NEED_REQUEST, 0);
		bikeStation=(BikeStation)getIntent().getSerializableExtra(BikeStationInfoListFragment.BIKE_STATION_INFO);
		
		idTextView=(TextView)findViewById(R.id.bike_detatil_id);
		nameTextView=(TextView)findViewById(R.id.bike_detatil_name);
		addressTextView=(TextView)findViewById(R.id.bike_detatil_address);
		timeTextView=(TextView)findViewById(R.id.bike_detatil_time);
		infoTextView=(TextView)findViewById(R.id.bike_detatil_info);

		if(needReload==0)
			show();
		else
			search(bikeStation.getId());
	}

	private void search(final int id)
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				BikeStation result = JSON.parseObject(
						DoString.parseThreeNetString(data),
						BikeStation.class);
				
				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BIKE_STATION_BY_ID_URL
						+ "?stationID=" + id;
			}
		}, new UpdateView()
		{
			@Override
			public void update(Object data)
			{

				bikeStation=(BikeStation)data;
				searchReal();

			}
		}).start();
	}

	private void searchReal()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String d)
			{
				BikeParkingPlaceInfoTemp temp = JSON.parseObject(
						DoString.parseThreeNetString(d),
						BikeParkingPlaceInfoTemp.class);

				for (BikeParkingPlaceInfo b : temp.getList())
				{

						if (b.getStationid() == bikeStation.getId())
						{
							bikeStation.setLeft(b.getCount());
							break;
						}
					
				}

				return bikeStation;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String result = HttpUrlRequestAddress.SMART_BIKE_STATION_REAL_BY_ID_URL
						+ "?bikeList="+bikeStation.getId();


				return result;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object d)
			{
				show();
			}
		}, this, "查找自行车实时数据失败").start();
	}

	private void show()
	{
		idTextView.setText(String.valueOf(bikeStation.getId()));
		nameTextView.setText(bikeStation.getName());
		addressTextView.setText(bikeStation.getAddress());
		timeTextView.setText(bikeStation.getServiceTime());

		String[] content = new String[] { "可借",
				String.valueOf(bikeStation.getLeft()), "辆  可还",
				String.valueOf(bikeStation.getBorrowed()), "辆" };
		String[] color = new String[] { "#000000", "#ff0000", "#000000",
				"#ff0000", "#000000" };
		ViewSetter.setTextviewColor(infoTextView, content, color);
		// infoTextView.setText("可借"+bikeStation.getLeft()+"辆 可还"+bikeStation.getBorrowed()+"辆");

		// BikeFavorDB favorDBOperate=new BikeFavorDB(this);
		//
		// isFavor=favorDBOperate.isFavorStation(bikeStation);
		//
		// favorDBOperate.CloseDB();
	}

	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null)
		{
			HeadFavorFragment headLCRFragment = new HeadFavorFragment();

			headLCRFragment.setTitleName("详情");
			headLCRFragment.setRightName("收藏");
			headLCRFragment.setRightListen(mapListener);

			ManagerFragment.replaceFragment(this, R.id.driving_detail_head,
					headLCRFragment);
		}
	}

	public void addToFavor(View v)
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(this, "您尚未登录", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				System.out.println(data);

				return JSON.parseObject(
						JSON.parseObject(data).getJSONObject("message")
								.toJSONString(), Message.class);
			}
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl(HttpUrlRequestAddress.FAVOR_ADD_BIKE_STATION_URL);

				Map<String, Object> p = new HashMap<String, Object>();
				p.put("userid", UserControl.getUser().getUserid());
				p.put("stationID", bikeStation.getId());
				p.put("name", bikeStation.getName());

				p.put("address", bikeStation.getAddress());

				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				Message message = (Message) data;

				String result = "";
				if (message.getStatus() == 1)
					result = "收藏成功";
				else if (message.getStatus() == -3)
					result = "此站点已收藏";
				else
					result = "收藏失败";

				Toast.makeText(BikeStationDetailActivity.this, result,
						Toast.LENGTH_SHORT).show();
			}
		}, this, "收藏站点失败").start();
	}

	public void favor(View v)
	{
		if (isFavor)
		{
			BikeFavorDB favorDB = new BikeFavorDB(this);
			favorDB.delete(bikeStation);

			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();

			((ImageView) findViewById(R.id.detail_favor))
					.setImageResource(R.drawable.smart_bus_favor);

			isFavor = false;
		} else
		{
			BikeFavorDB favorDBOperate = new BikeFavorDB(this);
			favorDBOperate.insertStation(bikeStation);
			favorDBOperate.CloseDB();
			Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();

			((ImageView) findViewById(R.id.detail_favor))
					.setImageResource(R.drawable.smart_bus_favor_checked);
			isFavor = true;
		}
	}

	public static final String MAP_INTENT = "map_intent";
	public static final String MAP_PARAMS = "map_params";
	public OnClickListener mapListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			addToFavor(v);

			// Bundle bundle=new Bundle();
			// bundle.putSerializable(MAP_PARAMS, bikeStation);
			// bundle.putInt(MAP_INTENT, 1);
			//
			// StartIntent.startIntentWithParam(BikeStationDetailActivity.this,
			// BikeHomeActivity.class, bundle);
		}
	};

	public void gothere(View v)
	{
		Bundle bundle = new Bundle();
		bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE,
				bikeStation.getLatitude());
		bundle.putDouble(GoThereMapActivity.GO_THERE_LONGTITUDE,
				bikeStation.getLongitude());

		StartIntent
				.startIntentWithParam(this, GoThereMapActivity.class, bundle);
	}

	public void suggest(View v)
	{
		Bundle suggesiontBundle = new Bundle();
		suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 8);
		StartIntent.startIntentWithParam(this, SuggestionActivity.class,
				suggesiontBundle);
	}

	public void debug(View v)
	{
		Bundle debugBundle = new Bundle();
		debugBundle.putInt(DebugActivity.DEBUG_NAME, 8);
		StartIntent
				.startIntentWithParam(this, DebugActivity.class, debugBundle);
	}
}
