package com.example.smarttraffic.bikenew;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.search.CNMKPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.MapSelectPointActivity;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.nearby.NearbyDetailListAdapter;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.ParkingInputviewListener;
import com.example.smarttraffic.view.VoiceListener;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BikeNewHomeActivity extends FragmentActivity
{
	ListView historyListView;
	InputListView inputListView;
	ParkingInputviewListener parkingInputviewListener;
	Button searchButton;
	TextView clearTextView;

	MapControl mapControl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_new_home);

		mapControl = new MapControl(this);

		initHead();
		initView();
	}

	/**
	 * 初始化头部内容
	 */
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("公共自行车");
		fragment.setRightName("收藏夹");
		fragment.setRightListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(BikeNewHomeActivity.this, BikeNewFavorActivity.class);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.parking_home_head, fragment);
	}

	private void initView()
	{
		historyListView = (ListView) findViewById(R.id.parking_home_history);
		searchButton = (Button) findViewById(R.id.parking_home_search);
		inputListView = (InputListView) findViewById(R.id.parking_home_input);
		clearTextView = (TextView) findViewById(R.id.parking_home_history_clear);

		searchButton.setOnClickListener(new Search());
		inputListView.setHint("请输入地址");
		inputListView.setTextWatcher(inputContentListener);

		parkingInputviewListener = new ParkingInputviewListener(inputListView);
		parkingInputviewListener.setListener(new InputListViewListener[] {
				new VoiceListener(this, inputListView),
				new myloactionListener(), new mapChoice() });

		clearTextView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				deleteHistory();
				loadHistory();
			}
		});

		historyListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Bundle bundle = new Bundle();
				NearBy n = (NearBy) parent.getAdapter().getItem(position);

				bundle.putDouble(BikeNewListActivity.LAN, n.getLan());
				bundle.putDouble(BikeNewListActivity.LON, n.getLon());

				saveHistory(n);

				StartIntent.startIntentWithParam(BikeNewHomeActivity.this,
						BikeNewListActivity.class, bundle);
			}
		});
	}

	TextWatcher inputContentListener = new TextWatcher()
	{

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			String temp = s.toString();

			if (temp.equals(""))
				loadHistory();
			else if (temp.length() > 0)
			{
				inputListView.showList(false);
				addSuggestion(temp);
			}
		}
	};

	protected void onActivityResult(int arg0, int arg1,
			android.content.Intent arg2)
	{
		if (arg0 == 1 && arg1 == MapSelectPointActivity.MAP_RESULT_CODE)
		{
			Bundle bundle = new Bundle();

			bundle.putDouble(BikeNewListActivity.LAN, arg2.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LAN, 0));
			bundle.putDouble(BikeNewListActivity.LON, arg2.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LON, 0));

			StartIntent.startIntentWithParam(BikeNewHomeActivity.this,
					BikeNewListActivity.class, bundle);
		}
	};

	class mapChoice implements InputListViewListener
	{
		@Override
		public void inputSelectListener()
		{
			StartIntent.startIntentForResult(BikeNewHomeActivity.this,
					MapSelectPointActivity.class, 1);
		}

	}

	int clickNum;
	
	class myloactionListener implements InputListViewListener
	{
		@Override
		public void inputSelectListener()
		{
			if (mapControl == null)
				mapControl = new MapControl(BikeNewHomeActivity.this);
			
			clickNum++;
			
			if(clickNum>1)
				return;
			
			mapControl.setLocationListener(new ICNMKLocationListener()
			{

				@Override
				public void onLocationChanged(CNMKLocation location)
				{
					mapControl.removeLocationWithoutView();

					Bundle bundle = new Bundle();

					bundle.putDouble(BikeNewListActivity.LAN,
							location.getLatitude());
					bundle.putDouble(BikeNewListActivity.LON,
							location.getLongitude());

					StartIntent.startIntentWithParam(BikeNewHomeActivity.this,
							BikeNewListActivity.class, bundle);
					
				}

				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{
				}
			});
			mapControl.addLocationWithoutView();
		}

	}

	private void addSuggestion(String str)
	{
		if (mapControl == null)
			mapControl = new MapControl(BikeNewHomeActivity.this);

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
						Toast.makeText(BikeNewHomeActivity.this, "无数据",
								Toast.LENGTH_SHORT).show();
						return;
					}
					
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

					

					historyListView.setAdapter(new NearbyDetailListAdapter(
							BikeNewHomeActivity.this, (List<NearBy>) data,
							false));

					updateHistoryOrSuggestion(false, (List<NearBy>) data);
				} catch (Exception e)
				{
				}
			}
		});

		mapControl.searchPOI(str);
	}


	class Search implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (inputListView.getText().equals(""))
			{
				Toast.makeText(BikeNewHomeActivity.this, "搜索内容不可为空",
						Toast.LENGTH_SHORT).show();
			} else
			{
				 Bundle bundle = new Bundle();
				 bundle.putString(SearchHintActivity.NAME,
				 inputListView.getText());
				 StartIntent.startIntentWithParam(BikeNewHomeActivity.this,SearchHintActivity
				 .class, bundle);
			}
		}
	}

	private void deleteHistory()
	{
		BikeNewHistoryDB dbOperate = new BikeNewHistoryDB(this);
		dbOperate.deleteAll(ContentType.BIKE_NEW_HISTORY);
		dbOperate.CloseDB();

		findViewById(R.id.parking_home_history_layout).setVisibility(View.GONE);
	}

	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		BikeNewHistoryDB dbOperate = new BikeNewHistoryDB(this);

		List<NearBy> data = dbOperate.selectForHistory();

		dbOperate.CloseDB();

		updateHistoryOrSuggestion(true, data);

	}

	private void saveHistory(NearBy n)
	{
		if (n != null)
		{
			BikeNewHistoryDB dbOperate = new BikeNewHistoryDB(this);

			if (!dbOperate.isHistory(n))
			{
				dbOperate.insert(n);
			}
			dbOperate.CloseDB();
		}
	}

	NearbyDetailListAdapter adapter;

	private void updateHistoryOrSuggestion(boolean ishistory, List<NearBy> data)
	{
		adapter = new NearbyDetailListAdapter(BikeNewHomeActivity.this, data,
				false);
		historyListView.setAdapter(adapter);

		if (adapter.getCount() == 0)
		{
			findViewById(R.id.parking_home_history_layout).setVisibility(
					View.GONE);
		} else
		{
			clearTextView.setVisibility(View.VISIBLE);
			findViewById(R.id.parking_home_history_layout).setVisibility(
					View.VISIBLE);
		}

		if (ishistory)
		{
			clearTextView.setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.parking_home_history_name))
					.setText("历史记录");
		} else
		{
			clearTextView.setVisibility(View.GONE);
			((TextView) findViewById(R.id.parking_home_history_name))
					.setText("提示:");
		}
	}

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
		clickNum=0;
		
		if(inputListView!=null)
		inputListView.showList(false);
		
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();

		loadHistory();
		super.onResume();
	}
}
