package com.example.smarttraffic.smartBus.fragment;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper2;
import com.example.smarttraffic.common.adapter.PopListener;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.map.MapSelectPointActivity;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.SelectPointActivity;
import com.example.smarttraffic.smartBus.SmartBusFavorActivity;
import com.example.smarttraffic.smartBus.SmartBusTransferListActivity;
import com.example.smarttraffic.smartBus.adapter.TransferHistoryAdapter;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.VoiceListener;

/**
 * 换乘界面
 * 
 * @author Administrator zhou
 * 
 */
public class SmartBusTransferFragment extends Fragment
{

	TransferHistoryAdapter historyAdapter;
	ListView historyListView;
	InputListView startInputListView;
	InputListView endInputListView;
	Button searchButton;
	TextView clearTextView;
	ImageView exchangeImageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_smart_bus_transfer,
				container, false);

		initView(rootView);
		return rootView;
	}

	/**
	 * 初始化视图
	 * 
	 * @param rootView
	 */
	private void initView(View rootView)
	{
		historyListView = (ListView) rootView
				.findViewById(R.id.trip_driving_self_history);
		searchButton = (Button) rootView
				.findViewById(R.id.trip_driving_self_search);
		endInputListView = (InputListView) rootView
				.findViewById(R.id.trip_driving_self_end);
		startInputListView = (InputListView) rootView
				.findViewById(R.id.trip_driving_self_start);
		clearTextView = (TextView) rootView
				.findViewById(R.id.trip_driving_self_clear);
		exchangeImageView = (ImageView) rootView
				.findViewById(R.id.bus_search_exchange);

		startInputListView.setHint(getResources().getString(
				R.string.string_trip_enter_start_city_label));
		endInputListView.setHint(getResources().getString(
				R.string.string_trip_enter_end_city_label));

		loadHistory();

		onclick click = new onclick();
		searchButton.setOnClickListener(click);
		clearTextView.setOnClickListener(click);
		exchangeImageView.setOnClickListener(click);

		historyListView.setOnItemClickListener(new onHistoryItemClick());
		new BaseAdapterLongClickWrapper2(historyListView,getActivity(),new addFavor());

		startInputListView.setListeners(new VoiceListener(getActivity(),
				startInputListView), 0);
		endInputListView.setListeners(new VoiceListener(getActivity(),
				endInputListView), 0);
		startInputListView.setListeners(new inputFavorListener(), 1);
		endInputListView.setListeners(new inputFavorListener(), 1);
		startInputListView.setListeners(new inputLocationListener(
				startInputListView, 1), 2);
		endInputListView.setListeners(new inputLocationListener(
				endInputListView, 2), 2);

		startInputListView.setListeners(new selectPointListener(1), 3);
		endInputListView.setListeners(new selectPointListener(2), 3);
		
		startInputListView.setTextWatcher(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				String temp = s.toString();

				if (temp.length() > 0)
				{
					startInputListView.showList(false);
				}
			}
		});
		
		endInputListView.setTextWatcher(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				String temp = s.toString();

				if (temp.length() > 0)
				{
					endInputListView.showList(false);
				}
			}
		});

	}
	
	class addFavor implements PopListener
	{
		@Override
		public void click(Object o)
		{
			if (UserControl.getUser() == null)
			{
				Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT)
						.show();
				return;
			}

			final TwoItemsData transfer = (TwoItemsData) o;

			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					System.out.println(data);
					
					return JSON.parseObject(JSON.parseObject(data).getJSONObject("message").toJSONString(),Message.class);
				}
			}, new BaseRequest()
			{
				@Override
				public PostParams CreatePostParams()
				{
					PostParams params = new PostParams();

					params.setUrl(HttpUrlRequestAddress.FAVOR_ADD_TRANSFER_URL);

					Map<String, Object> p = new HashMap<String, Object>();
					p.put("userid", UserControl.getUser().getUserid());
					p.put("startLantitude","-1" );
					p.put("startLongtitude","-1");
					p.put("endLantitude","-1");
					p.put("endLongtitude","-1");
					p.put("startName",transfer.getStart());
					p.put("endName",transfer.getEnd());
					
					params.setParams(p);

					return params;
				}
			}, new UpdateView()
			{

				@Override
				public void update(Object data)
				{
					Message message=(Message) data;
					
					Toast.makeText(getActivity(), message.getStatus()==1?"收藏成功":"收藏失败",Toast.LENGTH_SHORT).show();
				}
			}, getActivity(), "收藏线路失败").start();
		}
	}

	/**
	 * 历史记录内容点击事件监听
	 */
	class onHistoryItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			search(historyAdapter.getData().get(position).getStart(),
					historyAdapter.getData().get(position).getEnd());
		}
	}

	public static final String DRIVING_INFO = "driving_info";
	public static final String STATEGY = "stategy";

	/**
	 * 视图点击监听事件
	 * 
	 */
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{

			case R.id.trip_driving_self_clear:
				deleteHistory();
				loadHistory();
				break;

			case R.id.bus_search_exchange:
				ViewSetter.exchangeInputlistValue(startInputListView,
						endInputListView);
				break;

			case R.id.trip_driving_self_search:
				saveHistory();
				search(startInputListView.getText().toString(),
						endInputListView.getText().toString());
				break;
			}
		}
	}

	private void search(String start, String end)
	{
		if (start.equals(""))
		{
			Toast.makeText(getActivity(), "起点不能为空", Toast.LENGTH_SHORT).show();
		} else if (end.equals(""))
		{
			Toast.makeText(getActivity(), "终点不能为空", Toast.LENGTH_SHORT).show();
		} else
		{
			Bundle bundle = new Bundle();
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
					start);
			bundle.putString(
					SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
					end);

			if (startSelectResult != null
					&& startSelectResult.name != null
					&& startSelectResult.name.equals(startInputListView
							.getText().toString()))
			{
				bundle.putDouble(SelectPointActivity.START_LAN,
						startSelectResult.lan);
				bundle.putDouble(SelectPointActivity.START_LON,
						startSelectResult.lon);
			}

			if (endSelectResult != null
					&& endSelectResult.name != null
					&& endSelectResult.name.equals(endInputListView.getText()
							.toString()))
			{
				bundle.putDouble(SelectPointActivity.END_LAN,
						endSelectResult.lan);
				bundle.putDouble(SelectPointActivity.END_LON,
						endSelectResult.lon);
			}

			StartIntent.startIntentWithParam(getActivity(),
					SelectPointActivity.class, bundle);
		}
	}

	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		HistoryDBOperate dbOperate = new HistoryDBOperate(getActivity());

		if (historyListView.getAdapter() == null)
		{
			historyAdapter = new TransferHistoryAdapter(
					getActivity(),
					dbOperate
							.selectForTwoItem(ContentType.SMART_BUS_TRANSFER_HISTORY));
			historyListView.setAdapter(historyAdapter);
			// ListviewControl.setListViewHeightBasedOnChildren(historyListView);
		} else
		{
			historyAdapter.update(dbOperate
					.selectForTwoItem(ContentType.SMART_BUS_TRANSFER_HISTORY));
		}

		dbOperate.CloseDB();
	}

	/**
	 * 删除搜索历史记录
	 */
	private void deleteHistory()
	{
		HistoryDBOperate dbOperate = new HistoryDBOperate(getActivity());
		dbOperate.deleteAll(ContentType.SMART_BUS_TRANSFER_HISTORY);
		dbOperate.CloseDB();

		// clearTextView.setVisibility(View.GONE);
		getActivity().findViewById(R.id.smart_bus_transfer_history)
				.setVisibility(View.GONE);
	}

	/**
	 * 保存搜索历史记录
	 */
	private void saveHistory()
	{
		String start = startInputListView.getText();
		String end = endInputListView.getText();

		if (start != "" && end != "")
		{
			HistoryDBOperate dbOperate = new HistoryDBOperate(getActivity());

			if (dbOperate.isHistory(ContentType.SMART_BUS_TRANSFER_HISTORY,
					start, end))
			{
			} else
			{
				dbOperate.insert(ContentType.SMART_BUS_TRANSFER_HISTORY, start,
						end);
			}
			dbOperate.CloseDB();
		}
	}

	public static final String MY_LOCATION = "我的位置";

	/**
	 * 输入选项监听函数
	 * 
	 */
	class inputLocationListener implements InputListViewListener
	{
		private InputListView inputListView;
		private int id;

		public inputLocationListener(InputListView inputListView, int id)
		{
			this.inputListView = inputListView;
			this.id = id;
		}

		@Override
		public void inputSelectListener()
		{
			inputListView.setText(MY_LOCATION);
			inputListView.showList(false);
			myLocationExchangeLock(id);
		}
	}

	class selectPointListener implements InputListViewListener
	{
		int id;

		public selectPointListener(int id)
		{
			this.id = id;
		}

		@Override
		public void inputSelectListener()
		{
			StartIntent.startIntentForResult(getActivity(),
					MapSelectPointActivity.class, id);
		}

	}

	/**
	 * 
	 * @param i
	 */
	public void myLocationExchangeLock(int i)
	{
		String start = startInputListView.getText();
		String end = endInputListView.getText();

		if (i == 1 && end.equals(MY_LOCATION))
		{
			endInputListView.setText("");
		} else if (i == 2 && start.equals(MY_LOCATION))
		{
			startInputListView.setText("");
		}
	}

	class inputFavorListener implements InputListViewListener
	{
		@Override
		public void inputSelectListener()
		{
			StartIntent.startIntent(getActivity(), SmartBusFavorActivity.class);
		}
	}

	MapSelectResult startSelectResult;
	MapSelectResult endSelectResult;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1)
		{
			startSelectResult = new MapSelectResult();
			startSelectResult.lan = data.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LAN, 0);
			startSelectResult.lon = data.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LON, 0);
			startSelectResult.name = data
					.getStringExtra(MapSelectPointActivity.MAP_SELECT_ADDR);

			if (startSelectResult.name == null
					|| startSelectResult.name.equals(""))
				startSelectResult.name = "未知路段名";

			startInputListView.setText(startSelectResult.name);
		} else if (requestCode == 2)
		{
			endSelectResult = new MapSelectResult();
			endSelectResult.lan = data.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LAN, 0);
			endSelectResult.lon = data.getDoubleExtra(
					MapSelectPointActivity.MAP_SELECT_LON, 0);
			endSelectResult.name = data
					.getStringExtra(MapSelectPointActivity.MAP_SELECT_ADDR);

			if (endSelectResult.name == null || endSelectResult.name.equals(""))
				endSelectResult.name = "未知路段名";

			endInputListView.setText(endSelectResult.name);
		}
	}

	@Override
	public void onResume()
	{
		startInputListView.showList(false);
		endInputListView.showList(false);

		loadHistory();

		if (historyAdapter.getCount() == 0)
		{
			// clearTextView.setVisibility(View.GONE);
			getActivity().findViewById(R.id.smart_bus_transfer_history)
					.setVisibility(View.GONE);
		} else
		{
			// clearTextView.setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.smart_bus_transfer_history)
					.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}

	class MapSelectResult
	{
		double lan;
		double lon;
		String name;
	}
}
