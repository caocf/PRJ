package com.huzhouport.portdynmicnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 港航动态
 * 
 * @author Administrator
 * 
 */
public class IndustryInfoMain extends  Fragment
{
	private Query query = new Query();
	private View moreView;
	private ListView pml;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;
	private SimpleAdapter adapter;

	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.portdynmicnews_fragment,
				null);
		// 添加底部视图
				moreView =getActivity(). getLayoutInflater().inflate(R.layout.dateload, null);
				moreView.setVisibility(View.GONE);
				pml = (ListView) v.findViewById(R.id.portdynmicnews_main_list);
				pml.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
				adapter = new SimpleAdapter(getActivity(), listv, R.layout.portdynmicnews_list,
						new String[] { "title", "date" }, new int[] {
								R.id.portdynmicnews_main_title,
								R.id.portdynmicnews_main_data });
				pml.setAdapter(adapter);

				// 设置点击事件，滚动事件
				pml.setOnItemClickListener(new ProtDynmicNewsListener());
				pml.setOnScrollListener(new scrollNewsListener());

				// 查找第一页数据
				String data = "page=" + cpage;
				new queryIndustryInfo(getActivity(), true).execute(data);
		return v;
	}

	/**
	 * 列表点击事件
	 * 
	 * @author Administrator
	 * 
	 */
	class ProtDynmicNewsListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (arg2 < listv.size())
			{
				Intent intent = new Intent(getActivity(),
						IndusrtyInfoSee.class);
				intent.putExtra("id",  listv.get(arg2).get("id").toString());
				startActivity(intent);
			}
		}
	}

	/**
	 * 列表滚动事件
	 * 
	 * @author Administrator
	 * 
	 */
	class scrollNewsListener implements OnScrollListener
	{

		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
			// 当不滚动时并滚动到底部时
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == (view.getCount() - 1))
			{
				// 然后 经行一些业务操作
				if (cpage < maxpage)
				{
					moreView.findViewById(R.id.progressBar2).setVisibility(
							View.VISIBLE);
					((TextView) moreView.findViewById(R.id.loadmore_text))
							.setText(R.string.more);
					cpage += 1;
					String data = "cpage=" + cpage;
					new queryIndustryInfo(getActivity(), false)
							.execute(data);
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount)
		{

		}
	}

	/**
	 * 查询港航动态新闻
	 * 
	 * @author Administrator
	 * 
	 */
	class queryIndustryInfo extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		boolean showDialog;

		public queryIndustryInfo(Context context, boolean showDialog)
		{

			this.showDialog = showDialog;

			if (showDialog)
			{

				pDialog = new WaitingDialog().createDefaultProgressDialog(
						getActivity(), queryIndustryInfo.this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params)
		{
			return query.showIndustryInfo(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
 
			if(result==null){
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
			}else{
			parseNewDynmic(result);
			updateListview();
			}
			super.onPostExecute(result);
		}

	}

	/**
	 * 解析新动态
	 * 
	 * @param result
	 *            港航动态json数据
	 */
	public void parseNewDynmic(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("pages");
			String errorInfo = data.getString("total");
			if (errorInfo.equals("0"))
			{
				Toast.makeText(getActivity(), "暂无相关数据...",
						Toast.LENGTH_SHORT).show();

			} else
			{
				// 接下来的就是JSON对象的操作了
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", jsonObject.get("title"));
					map.put("date", jsonObject.get("updatetime"));
					map.put("id", jsonObject.get("id").toString());
					listv.add(map);
				}
			}
		} catch (Exception e)
		{

			e.printStackTrace();
		}
	}

	/**
	 * 更新新新闻列表
	 */
	public void updateListview()
	{
		moreView.setVisibility(View.VISIBLE);

		if (adapter.getCount() > 0)
		{
			adapter.notifyDataSetChanged();
		}
		if (cpage >= maxpage)
		{
			moreView.setVisibility(View.GONE);
			pml.removeFooterView(moreView);
		}
		moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
		((TextView) moreView.findViewById(R.id.loadmore_text))
				.setText(R.string.moreing);
	}

}
