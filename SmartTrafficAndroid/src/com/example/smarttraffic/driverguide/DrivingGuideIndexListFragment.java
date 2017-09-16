package com.example.smarttraffic.driverguide;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.R;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.DateUtil;

/**
 * 
 * @author Administrator
 * 
 */
public class DrivingGuideIndexListFragment extends Fragment
{
	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;

	TextView textView5;
	TextView textView6;
	TextView textView7;
	TextView textView8;

	TextView[] textViews;
	TextView[] subTextViews;

	ListView listView;

	TextView search;
	Button button;

	LinearLayout layout;
	LinearLayout subitem;

	TextView updateTimeTextView;
	Date updateTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(
				R.layout.fragment_drving_guide_list_index, container, false);

		initView(rootView);

		return rootView;
	}

	private void initView(View root)
	{
		textView1 = (TextView) root.findViewById(R.id.textView1);
		textView2 = (TextView) root.findViewById(R.id.textView2);
		textView3 = (TextView) root.findViewById(R.id.textView3);
		textView4 = (TextView) root.findViewById(R.id.textView4);

		textView5 = (TextView) root.findViewById(R.id.textView5);
		textView6 = (TextView) root.findViewById(R.id.textView6);
		textView7 = (TextView) root.findViewById(R.id.textView7);
		textView8 = (TextView) root.findViewById(R.id.textView8);

		search = (EditText) root.findViewById(R.id.road_search_content);
		button = (Button) root.findViewById(R.id.road_search_submit);
		layout = (LinearLayout) root.findViewById(R.id.search_layout);
		subitem = (LinearLayout) root.findViewById(R.id.sub_item_layout);

		updateTimeTextView = (TextView) root.findViewById(R.id.update_time);

		textViews = new TextView[4];
		textViews[0] = textView1;
		textViews[1] = textView2;
		textViews[2] = textView3;
		textViews[3] = textView4;

		subTextViews = new TextView[4];
		subTextViews[0] = textView5;
		subTextViews[1] = textView6;
		subTextViews[2] = textView7;
		subTextViews[3] = textView8;

		listView = (ListView) root.findViewById(R.id.listView1);

		search1();
		changeItem(0);

		textView1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search1();
				changeItem(0);
			}
		});

		textView2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search2();
				changeItem(1);
			}
		});

		textView3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search3();
				changeItem(2);
			}
		});

		textView4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search4();
				changeItem(3);
			}
		});

		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search5();
				changeSubItem(0);
			}
		});

		textView5.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search3();
				changeSubItem(0);
			}
		});

		textView6.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByRoadClassTPIDescPager?plan=1&roadClass=%E5%BF%AB%E9%80%9F%E8%B7%AF&pageNum=1&pageSize=100");
				changeSubItem(1);
			}
		});

		textView7.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByRoadClassTPIDescPager?plan=1&roadClass=%E4%B8%BB%E5%B9%B2%E9%81%93&pageNum=1&pageSize=100");
				changeSubItem(2);
			}
		});

		textView8.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByLowRoadClassTPIDescPager?plan=1&pageSize=999&pageNum=1");
				changeSubItem(3);
			}
		});
	}

	private void searchSubItem()
	{
		switch (subitemIndex)
		{
		case 0:
			search3();
			break;

		case 1:
			search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByRoadClassTPIDescPager?plan=1&roadClass=%E5%BF%AB%E9%80%9F%E8%B7%AF&pageNum=1&pageSize=100");
			break;

		case 2:
			search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByRoadClassTPIDescPager?plan=1&roadClass=%E4%B8%BB%E5%B9%B2%E9%81%93&pageNum=1&pageSize=100");
			break;

		case 3:
			search8("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByLowRoadClassTPIDescPager?plan=1&pageSize=999&pageNum=1");
			break;

		}
	}

	private void changeItem(int index)
	{
		for (int i = 0; i < textViews.length; i++)
		{
			if (index == i)
			{
				textViews[i].setBackgroundColor(0xff2746a2);
				textViews[i].setTextColor(0xffffffff);
			} else
			{
				textViews[i].setBackgroundColor(0xffffffff);
				textViews[i].setTextColor(0xff666666);
			}
		}

		if (index == 2)
		{
			layout.setVisibility(View.VISIBLE);
			subitem.setVisibility(View.VISIBLE);

			changeSubItem(0);
		} else
		{
			subitem.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
		}
	}

	int subitemIndex;

	private void changeSubItem(int index)
	{
		subitemIndex = index;

		for (int i = 0; i < subTextViews.length; i++)
		{
			if (index == i)
			{
				subTextViews[i].setBackgroundColor(0xff2746a2);
				subTextViews[i].setTextColor(0xffffffff);
			} else
			{
				subTextViews[i].setBackgroundColor(0xffffffff);
				subTextViews[i].setTextColor(0xff666666);
			}
		}
	}

	private void search1()
	{
		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("areaTpis").toJSONString(),
						ZoneIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/cservice/queryAllAreaTPI?plan=3";
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<ZoneIndex> result = (List<ZoneIndex>) data;

				listView.setAdapter(new ZoneIndexAdapter(getActivity(), result));
				listView.setOnItemClickListener(null);

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));
			}
		}, getActivity()).start();
	}

	private void search2()
	{
		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("cbdTpis").toJSONString(), HotIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/cservice/queryCBDTPIByTPIDescPager?plan=1&pageNum=1&pageSize=10";
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<HotIndex> result = (List<HotIndex>) data;

				listView.setAdapter(new HotIndexAdapter(getActivity(), result));
				listView.setOnItemClickListener(null);

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));
			}
		}, getActivity()).start();
	}

	private void search3()
	{
		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("roadTpis").getJSONArray(0)
						.toJSONString(), RoadIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/cservice/queryRoadTPIByTPIDescPager?plan=1&direction=1&pageNum=1&pageSize=100";
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<RoadIndex> result = (List<RoadIndex>) data;

				listView.setAdapter(new RoadIndexAdapter(getActivity(), result));
				listView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						if (position > 0)
							search6(((RoadIndex) parent.getAdapter().getItem(
									position)).getRoadName());
					}
				});

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));
			}
		}, getActivity()).start();
	}

	private void search4()
	{
		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("passTpis").getJSONArray(0)
						.toJSONString(), CrossIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/cservice/queryPassTPIByTPIDescPager?plan=1&direction=1&pageNum=1&pageSize=10";
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<CrossIndex> result = (List<CrossIndex>) data;

				listView.setAdapter(new CrossIndexAdapter(getActivity(), result));
				listView.setOnItemClickListener(null);

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));
			}
		}, getActivity()).start();
	}

	/**
	 * 搜索
	 */
	private void search5()
	{
		if (search.getText().toString().trim().equals(""))
		{

			Toast.makeText(getActivity(), "请输入道路名称", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseObject(JSONObject.parseObject(data)
						.getJSONObject("roadTpi").toJSONString(),
						RoadIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl("http://115.231.73.253/jxtpi/cservice/queryUnifyRoadTPIByRoadName");
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("plan", 1);
				p.put("roadName", search.getText().toString().trim());

				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<RoadIndex> result = new ArrayList<RoadIndex>();
				result.add((RoadIndex) data);

				if (listView.getAdapter() == null)
					listView.setAdapter(new RoadIndexAdapter(getActivity(),
							result));
				else
					((RoadIndexAdapter) listView.getAdapter()).update(result);

			}
		}, getActivity()).start();
	}

	int index;
	String superName;

	/**
	 * 一级道路
	 * 
	 * @param name
	 */
	private void search6(final String name)
	{

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("roadTpis").toJSONString(),
						RoadIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl("http://115.231.73.253/jxtpi/cservice/queryTwoRoadTPIByRoadNameTPIDesc");
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("plan", 1);
				p.put("roadName", name);

				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<RoadIndex> result = (List<RoadIndex>) data;

				listView.setAdapter(new SubRoadIndexAdapter(getActivity(),
						result));
				listView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						if (position == 1)
						{
							searchSubItem();
						} else if (position > 1)
						{
							RoadIndex roadIndex = (RoadIndex) parent
									.getAdapter().getItem(position);
							search7(roadIndex.getRoadName(),
									roadIndex.getStartName(),
									roadIndex.getEndName());
						}
					}
				});

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));

				index = 1;
				superName = name;
			}
		}, getActivity()).start();
	}

	/**
	 * 二级道路
	 * 
	 * @param name
	 * @param start
	 * @param end
	 */
	private void search7(final String name, final String start, final String end)
	{

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("roadTpis").toJSONString(),
						RoadIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl("http://115.231.73.253/jxtpi/cservice/queryRoadSectionTPIByRoadInfoTPIDesc");
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("plan", 1);
				p.put("roadName", name);
				p.put("roadStart", start);
				p.put("roadEnd", end);

				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<RoadIndex> result = (List<RoadIndex>) data;

				listView.setAdapter(new SubRoadIndexAdapter(getActivity(),
						result));
				listView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						if (position == 1)
						{
							search6(superName);
						}

					}
				});

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));

				index = 1;
				superName = name;
			}
		}, getActivity()).start();
	}

	private void search8(final String path)
	{
		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				return JSON.parseArray(JSONObject.parseObject(data)
						.getJSONArray("roadTpis").toJSONString(),
						RoadIndex.class);
			};
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return path;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				List<RoadIndex> result = (List<RoadIndex>) data;

				listView.setAdapter(new RoadIndexAdapter(getActivity(), result));
				listView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						if (position > 0)
							search6(((RoadIndex) parent.getAdapter().getItem(
									position)).getRoadName());
					}
				});

				updateTime = new Date(1000 * Long.valueOf(result.get(0)
						.getTime()));

				updateTimeTextView.setText("更新时间："
						+ DateUtil.DateToString(updateTime));
			}
		}, getActivity()).start();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

}
