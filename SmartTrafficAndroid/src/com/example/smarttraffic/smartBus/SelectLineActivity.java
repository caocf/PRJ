package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.LineHistoryAdapter;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.bean.BusLineForQueryByName;
import com.example.smarttraffic.smartBus.db.SmartBusHistoryDB;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 查询站点activity
 * 
 * @author Administrator zhou
 * 
 */
public class SelectLineActivity extends FragmentActivity
{
	String name;
	ListView content;

	public static final String SELECT_LINE_NAME = "select_line_name";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);

		name = getIntent().getStringExtra(SELECT_LINE_NAME);

		initHead(name);
		initListView();
		request();
	}

	private void initListView()
	{
		content = (ListView) findViewById(R.id.smart_bus_select_point_content);
		content.setOnItemClickListener(contentClickListener);
	}

	private void initHead(String str)
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("\"" + str + "\"相关线路");
		ManagerFragment.replaceFragment(this, R.id.smart_bus_select_point_head,
				fragment);
	}

	private void request()
	{
		new HttpThread(new BaseSearch()
		{

			@Override
			public Object parse(String data)
			{
				String data1 = DoString.parseThreeNetString(data);

				BusLineForQueryByName result = JSON.parseObject(data1,
						BusLineForQueryByName.class);

				List<BusLine> rList = new ArrayList<BusLine>();

				for (BusLine b : result.getLineList())
				{

					if (b.isIsRing())
					{
						rList.add(b);
					} else
					{
						BusLine busLine1 = (BusLine) b.clone();
						busLine1.setDirect(1);

						rList.add(busLine1);

						BusLine busLine2 = (BusLine) b.clone();
						busLine2.setDirect(2);

						rList.add(busLine2);
					}
				}

				return rList;
			}
		}, new BaseRequest()
		{

			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();
				params.setUrl(HttpUrlRequestAddress.SMART_BUS_LINE_SUUGESTION_URL);
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("lineName", name);
				params.setParams(p);

				return params;
			}

		}, new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				List<BusLine> busLines = (List<BusLine>) data;
				LineHistoryAdapter stationNearByAdapter = new LineHistoryAdapter(
						SelectLineActivity.this, busLines);
				content.setAdapter(stationNearByAdapter);
			}
		},"未查到相似线路").start();
	}

	OnItemClickListener contentClickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long ids)
		{
			LineHistoryAdapter adapter = ((LineHistoryAdapter) parent
					.getAdapter());

			saveHistory(adapter.getData().get(position));

			Bundle bundle = new Bundle();
			bundle.putInt(SmartBusLineDetailActivity.SMART_BUS_LINE_ID, adapter
					.getData().get(position).getId());
			bundle.putString(SmartBusLineDetailActivity.SMART_BUS_LINE_NAME,
					adapter.getData().get(position).getName());
			bundle.putInt(SmartBusLineDetailActivity.SMART_BUS_LINE_DIRECT,
					adapter.getData().get(position).getDirect());

			StartIntent.startIntentWithParam(SelectLineActivity.this,
					SmartBusLineDetailActivity.class, bundle);
		}
	};

	private void saveHistory(BusLine lineInfo)
	{

		if (lineInfo != null)
		{
			SmartBusHistoryDB dbOperate = new SmartBusHistoryDB(this);

			if (!dbOperate.isHistory(lineInfo))
			{
				dbOperate.insert(lineInfo);
			}
			dbOperate.CloseDB();
		}
	}
}
