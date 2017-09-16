package com.example.smarttraffic.news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.R;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

/**
 * 新新闻列表
 * 
 * @author Administrator
 * 
 */
public class NewsListFragment extends Fragment
{

	private int id;

	public void setId(int id)
	{
		this.id = id;
	}

	ListView contentListView;

	NewsRequest newsRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_news_list,
				container, false);

		contentListView = (ListView) rootView
				.findViewById(R.id.news_content_listview);

		switch (id)
		{
		case 1:
			addTZXX();
			break;

		case 0:
			addYDLD();
			break;

		case 2:
			addJTGZ();
			break;
		}

		return rootView;
	}

	private void addYDLD()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				List<Ydld> result=new ArrayList<Ydld>();
				JSONArray array=JSON.parseObject(data).getJSONArray("mergedConRtics");
				for(int i=0;i<array.size();i++)
				{
					Ydld y=new Ydld();
					
					JSONObject object=array.getJSONObject(i);
					y.setLocalKind(object.getString("localKind"));
					y.setRoadName(object.getString("roadName"));
					
					object=object.getJSONArray("roadSections").getJSONObject(0);
					y.setDesc(object.getString("desc"));
					
					y.setRsEnd(object.getString("rsEnd"));
					y.setRsStart(object.getString("rsStart"));
					
					result.add(y);
				}
				
				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return "http://115.231.73.253/jxtpi/conanalyse/queryMergedConRticByTime?plan=3&timeType=1";
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				contentListView.setAdapter(new YdldAdapter(getActivity(), (List<Ydld>)data));
			}
		}).start();
	}

	List<Tzxx> tzxxs;

	private void addTZXX()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				tzxxs = JSON.parseArray(data, Tzxx.class);

				return tzxxs;
			}
		}, new BaseRequest()
		{
			@SuppressLint("SimpleDateFormat") @Override
			public String CreateUrl()
			{
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				String ss = new SimpleDateFormat("yyyy-MM-dd").format(calendar
						.getTime());

				return HttpUrlRequestAddress.NEWS_LIST_URL + "?startTime=" + ss
						+ "&endTime=2099-12-31";
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				tzxxs = (List<Tzxx>) data;

				TzxxAdapter adapter = new TzxxAdapter(getActivity(), tzxxs);
				contentListView.setAdapter(adapter);
				contentListView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						Bundle bundle=new Bundle();
						bundle.putSerializable(NewsListDetailActivity.DETAIL_NEWS_TZXX, tzxxs.get(position));
						
						StartIntent.startIntentWithParam(getActivity(), NewsListDetailActivity.class, bundle);
					}
				});
			}
		}).start();
	}
	
	
	List<JTGZ> jtgzs;

	private void addJTGZ()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				System.out.println(data);
				
				jtgzs = JSON.parseArray(JSON.parseObject(data).getJSONArray("jtgzs").toJSONString(), JTGZ.class);

				return jtgzs;
			}
		}, new BaseRequest()
		{
			@SuppressLint("SimpleDateFormat") @Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.NEWS_JTGZ_LIST_URL;
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				jtgzs = (List<JTGZ>) data;

				JtgzAdapter adapter = new JtgzAdapter(getActivity(), jtgzs);
				contentListView.setAdapter(adapter);
				
				contentListView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						Bundle bundle=new Bundle();
						bundle.putSerializable(NewsJTGZDetailActivity.DETAIL_NEWS_JTGZ, jtgzs.get(position));
						
						StartIntent.startIntentWithParam(getActivity(), NewsJTGZDetailActivity.class, bundle);
					}
				});
			}
		}).start();
	}

}
