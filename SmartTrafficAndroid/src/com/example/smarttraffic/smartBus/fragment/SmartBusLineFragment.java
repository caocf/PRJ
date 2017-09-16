package com.example.smarttraffic.smartBus.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper2;
import com.example.smarttraffic.common.adapter.PopListener;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.SelectLineActivity;
import com.example.smarttraffic.smartBus.SmartBusFavorActivity;
import com.example.smarttraffic.smartBus.SmartBusLineDetailActivity;
import com.example.smarttraffic.smartBus.adapter.LineHistoryAdapter;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.db.SmartBusHistoryDB;
import com.example.smarttraffic.smartBus.parse.LineSuggestRequest;
import com.example.smarttraffic.smartBus.parse.LineSuggestSearch;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.SmartLineInputviewListener;
import com.example.smarttraffic.view.VoiceListener;

/**
 * 搜索公交线路
 * 
 * @author Administrator zhou
 * 
 */
public class SmartBusLineFragment extends Fragment
{
	LineHistoryAdapter historyAdapter;
	ListView historyListView;
	InputListView lineInputListView;
	SmartLineInputviewListener smartLineInputviewListener;
	Button searchButton;
	TextView clearTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_smart_bus_line,
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
		lineInputListView = (InputListView) rootView
				.findViewById(R.id.trip_driving_self_end);
		clearTextView = (TextView) rootView
				.findViewById(R.id.smart_bus_line_history_clear);

		smartLineInputviewListener = new SmartLineInputviewListener(
				lineInputListView);
		smartLineInputviewListener.setListener(new InputListViewListener[] {
				new VoiceListener(getActivity(), lineInputListView),
				new inputFavorListener() });

		lineInputListView.setHint("请输入路线名称（如1路）");
		lineInputListView.setTextWatcher(inputContentListener);

		onclick click = new onclick();
		searchButton.setOnClickListener(click);
		clearTextView.setOnClickListener(click);

		historyListView.setOnItemClickListener(new onHistoryItemClick());
		new BaseAdapterLongClickWrapper2(historyListView, getActivity(),
				new addFavor());
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

			final BusLine busLine = (BusLine) o;

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

					params.setUrl(HttpUrlRequestAddress.FAVOR_ADD_LINE_URL);

					Map<String, Object> p = new HashMap<String, Object>();
					p.put("userid", UserControl.getUser().getUserid());
					p.put("lineID", busLine.getId());
					p.put("name", busLine.getName());
					p.put("direct", busLine.getDirect());

					if (busLine.getDirect() == 2)
					{
						p.put("startName", busLine.getDownStartStationName()
								.replaceAll("\\d+", ""));
						p.put("endName", busLine.getDownEndStationName()
								.replaceAll("\\d+", ""));
					} else
					{
						p.put("startName", busLine.getUpStartStationName()
								.replaceAll("\\d+", ""));
						p.put("endName", busLine.getUpEndStationName()
								.replaceAll("\\d+", ""));
					}

					params.setParams(p);

					return params;
				}
			}, new UpdateView()
			{

				@Override
				public void update(Object data)
				{
					Message message=(Message) data;
					
					String hint="收藏失败";
					switch (message.getStatus())
					{
					case 1:
						hint="收藏成功";
						break;

					case -3:
						hint="您已收藏该线路";
						break;
					}
					
					Toast.makeText(getActivity(), hint,Toast.LENGTH_SHORT).show();
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
			search(historyAdapter.getData().get(position).getId(),
					historyAdapter.getData().get(position).getName(),
					historyAdapter.getData().get(position).getDirect());
			saveHistory(historyAdapter.getData().get(position));
		}
	}

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

			case R.id.smart_bus_line_history_clear:
				deleteHistory();
				loadHistory();
				break;

			case R.id.trip_driving_self_search:
				// saveHistory(null);
				search();
				break;
			}
		}
	}

	/**
	 * 搜索详细线路
	 * 
	 * @param id
	 */
	private void search(int id, String name, int direct)
	{
		Bundle bundle = new Bundle();
		bundle.putInt(SmartBusLineDetailActivity.SMART_BUS_LINE_ID, id);
		bundle.putString(SmartBusLineDetailActivity.SMART_BUS_LINE_NAME, name);
		bundle.putInt(SmartBusLineDetailActivity.SMART_BUS_LINE_DIRECT, direct);
		StartIntent.startIntentWithParam(getActivity(),
				SmartBusLineDetailActivity.class, bundle);
	}

	/**
	 * 搜索
	 */
	private void search()
	{
		if (lineInputListView.getText().equals(""))
		{
			Toast.makeText(getActivity(), "搜索线路不可为空", Toast.LENGTH_SHORT)
					.show();
		} else
		{
			Bundle bundle = new Bundle();
			bundle.putString(SelectLineActivity.SELECT_LINE_NAME,
					lineInputListView.getText());
			StartIntent.startIntentWithParam(getActivity(),
					SelectLineActivity.class, bundle);
		}
	}

	/**
	 * 内容输入监听
	 */
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
			{
				loadHistory();
			} else if (temp.length() > 0)
			{
				lineInputListView.showList(false);
				addSuggestion(temp);
			}
		}
	};

	/**
	 * 建议搜索
	 */
	String tempInput;

	private void addSuggestion(String str)
	{
		tempInput = str;
		new HttpThread(new LineSuggestSearch(), new LineSuggestRequest(
				tempInput), new UpdateView()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				List<BusLine> busLines = (List<BusLine>) data;

				updateHistoryOrSuggestion(false, busLines);
			}
		}, "未查到相似线路").start();
	}

	/**
	 * 更新历史记录或搜索建议
	 * 
	 * @param ishistory
	 * @param data
	 */
	private void updateHistoryOrSuggestion(boolean ishistory, List<BusLine> data)
	{
		if (historyListView.getAdapter() == null)
		{
			historyAdapter = new LineHistoryAdapter(getActivity(), data);
			historyListView.setAdapter(historyAdapter);
		} else
		{
			historyAdapter.update(data);
		}

		if (historyAdapter.getCount() == 0)
		{
			getActivity().findViewById(R.id.smart_bus_line_history_layout)
					.setVisibility(View.GONE);
		} else
		{
			clearTextView.setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.smart_bus_line_history_layout)
					.setVisibility(View.VISIBLE);
		}

		if (ishistory)
		{
			getActivity().findViewById(R.id.smart_bus_line_history_clear)
					.setVisibility(View.VISIBLE);
			((TextView) getActivity().findViewById(
					R.id.smart_bus_line_history_name)).setText("历史记录");
		} else
		{
			getActivity().findViewById(R.id.smart_bus_line_history_clear)
					.setVisibility(View.GONE);
			((TextView) getActivity().findViewById(
					R.id.smart_bus_line_history_name)).setText("提示:");
		}
	}

	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		SmartBusHistoryDB dbOperate = new SmartBusHistoryDB(getActivity());

		List<BusLine> data = dbOperate.selectForHistory();

		dbOperate.CloseDB();

		updateHistoryOrSuggestion(true, data);

	}

	/**
	 * 删除搜索历史记录
	 */
	private void deleteHistory()
	{
		SmartBusHistoryDB dbOperate = new SmartBusHistoryDB(getActivity());
		dbOperate.deleteAll(ContentType.SMART_BUS_LINE_HISTORY);
		dbOperate.CloseDB();

		getActivity().findViewById(R.id.smart_bus_line_history_layout)
				.setVisibility(View.GONE);
	}

	/**
	 * 保存搜索历史记录
	 */
	private void saveHistory(BusLine lineInfo)
	{
		if (lineInfo != null)
		{
			SmartBusHistoryDB dbOperate = new SmartBusHistoryDB(getActivity());

			if (!dbOperate.isHistory(lineInfo))
			{
				dbOperate.insert(lineInfo);
			}
			dbOperate.CloseDB();
		}
	}

	/**
	 * 跳转收藏夹
	 * 
	 * @author Administrator zwc
	 * 
	 */
	class inputFavorListener implements InputListViewListener
	{
		@Override
		public void inputSelectListener()
		{
			Bundle bundle = new Bundle();
			bundle.putInt(SmartBusFavorActivity.SMART_BUS_FAVOR_FIRST_PAGE, 1);
			StartIntent.startIntentWithParam(getActivity(),
					SmartBusFavorActivity.class, bundle);
		}
	}

	@Override
	public void onResume()
	{
		loadHistory();
		super.onResume();
	}
}
