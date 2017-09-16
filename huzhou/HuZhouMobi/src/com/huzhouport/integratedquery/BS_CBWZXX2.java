package com.huzhouport.integratedquery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.TextViewUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BS_CBWZXX2 extends Fragment
{
	private List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
	ListView expandlistview;
	private String getResult, title;
	private RelativeLayout rl;
	TextView tishi;

	private MyAdapter adapter;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getResult = getArguments().getString("getResult");
		title = getArguments().getString("title");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.bs_cbwzxx2, container, false);
		rl = (RelativeLayout) view.findViewById(R.id.bs_cbwzxx_tishi);
		expandlistview = (ListView) view.findViewById(R.id.bs_cbwzxx_datalist);
		tishi = (TextView) view.findViewById(R.id.bs_cbwzxx_noresult);
		showExpandableListView();
		if (getResult.length() == 0)
		{
			new GetDate().execute();
		} else
			initData(getResult);
		return view;
	}

	public void showExpandableListView()
	{
		adapter = new MyAdapter(getActivity());
		expandlistview.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	static BS_CBWZXX2 newInstance(String getResult, String title)
	{
		BS_CBWZXX2 newFragment = new BS_CBWZXX2();
		Bundle obundle = new Bundle();
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

	}

	class GetDate extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);

			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "queryVoilateShip";
			try
			{
				getResult = hfu.post(actionUrl, paramsDate);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			initData(getResult);
			super.onPostExecute(result);
		}

	}

	public void initData(String getResult)
	{
		parentList=new ArrayList<Map<String,Object>>();
		
		JSONTokener jsonParser = new JSONTokener(getResult);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			String listDateString = data.getString("list");
			if (listDateString.equals("null"))
			{
				tishi.setVisibility(View.VISIBLE);
			} else
			{
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					HashMap<String, Object> map = new HashMap<String, Object>();

					map.put("content", jsonObject2.getString("content"));
					map.put("blacklist", jsonObject2.getString("blacklist"));
					map.put("loc", jsonObject2.getString("loc"));
					map.put("type", jsonObject2.getString("type"));
					map.put("date", jsonObject2.getString("date"));
					parentList.add(map);
				}
				if (jsonArray.length() == 0)
				{
					rl.setVisibility(View.VISIBLE);
					expandlistview.setVisibility(View.GONE);
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 显示列表
		adapter.notifyDataSetChanged();

	}

	class MyAdapter extends BaseAdapter
	{
		Context context;
		LayoutInflater mlayoutInflater;

		MyAdapter(Context context)
		{
			this.context = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return parentList.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return parentList.get(arg0);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = mlayoutInflater.inflate(R.layout.cbwzxx_group2, null);
			Map<String, Object> d = parentList.get(position);

			TextViewUtil.setText(convertView, R.id.textView1,
					"类型：" + d.get("type").toString());
			TextViewUtil.setText(convertView, R.id.textView2,
					"时间：" + d.get("date").toString());
			TextViewUtil.setText(convertView, R.id.textView3,
					"内容：" + d.get("content").toString());
			TextViewUtil.setText(convertView, R.id.textView4,
					"地点：" + d.get("loc").toString());

			return convertView;
		}

	}
}
