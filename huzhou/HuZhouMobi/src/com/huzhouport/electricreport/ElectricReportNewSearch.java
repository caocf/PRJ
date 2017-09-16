package com.huzhouport.electricreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ElectricReportNewSearch extends Activity
{
	private ImageButton elt_back;
	private ListView lv;
	public String shipName;
	private int cpage = 1, maxpage;
	private String content;
	private ClearEditText searchtext; // 搜索框
	private View moreView; // 加载更多页面
	private SimpleAdapter adapter;
	private ArrayList<HashMap<String, Object>> showlist;

	private User user;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreport_search);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");

		findViewById(R.id.tableRow17).setVisibility(View.GONE);
		elt_back = (ImageButton) findViewById(R.id.electricreport_search_back);
		lv = (ListView) findViewById(R.id.electricreport_search_list);
		searchtext = (ClearEditText) findViewById(R.id.electricreport_search_edit01);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		ShowList();

		elt_back.setOnClickListener(new MyBack());

		// 搜索按钮
		ImageButton search1 = (ImageButton) findViewById(R.id.electricreport_search_bt);
		search1.setOnClickListener(new searchOrBack());
	}

	class searchOrBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			showlist.clear();
			cpage = 1;
			content = searchtext.getText().toString();
			new ListTask(ElectricReportNewSearch.this).execute();

		}

	}

	// 返回
	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			finish();
		}
	}

	public void ShowList()
	{
		showlist = new ArrayList<HashMap<String, Object>>();
		adapter = new SimpleAdapter(ElectricReportNewSearch.this, showlist,
				R.layout.electricreportnew_item, new String[] { "text1",
						"text2", "text3", "text4", "text5" }, new int[] {
						R.id.electricreportnew_item_name,
						R.id.electricreportnew_item_time,
						R.id.electricreportnew_item_kind,
						R.id.electricreportnew_item_id,
						R.id.electricreportnew_item_port });
		lv.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		lv.setAdapter(adapter);
		// adapter.notifyDataSetChanged(); //刷新数据
		moreView.setVisibility(View.GONE);
		lv.setOnItemClickListener(new ShowView());

		lv.setOnScrollListener(new OnScrollListener()
		{

			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1))
					{
						moreView.setVisibility(View.VISIBLE);
						moreView.findViewById(R.id.progressBar2).setVisibility(
								View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text))
								.setText(R.string.more);
						LoadList();
					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
			}
		});
	}

	private void LoadList()
	{

		if (cpage < maxpage)
		{
			cpage += 1;
			new ListTask(this).execute();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			if (cpage == 1)
			{
				pDialog = new WaitingDialog().createDefaultProgressDialog(
						context, this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// 取消异步
			HttpFileUpTool hft = new HttpFileUpTool();
			Map<String, Object> p = new HashMap<String, Object>();
			String result = null;
			try
			{
				p.put("cpage", cpage);
				p.put("electricReportNew.shipInfo", content);
				result = hft.post(HttpUtil.BASE_URL
						+ "ElectricReportListByInfo", p);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return result;

		}

		@Override
		protected void onPostExecute(String result)
		{
			if (cpage == 1)
			{
				if (pDialog != null)
					pDialog.dismiss();
			}
			if (result == null)
			{
				Toast.makeText(ElectricReportNewSearch.this, "网络异常",
						Toast.LENGTH_SHORT).show();
			} else
			{
				GetJson(result);

				adapter.notifyDataSetChanged();// 刷新
				if (cpage >= maxpage)
				{
					moreView.setVisibility(View.GONE);
					lv.removeFooterView(moreView); // 移除底部视图
					// Toast.makeText(IllegalSearch.this, "已加载全部数据",
					// 3000).show();
				}
				moreView.findViewById(R.id.progressBar2).setVisibility(
						View.GONE);
				((TextView) moreView.findViewById(R.id.loadmore_text))
						.setText(R.string.moreing);
			}
			super.onPostExecute(result);
		}
	}

	public void GetJson(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();

			maxpage = data.getInt("totalPage");
			JSONArray jsonArray = data.getJSONArray("list");
			if (jsonArray.length() == 0)
			{
				Toast.makeText(ElectricReportNewSearch.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
				findViewById(R.id.tableRow17).setVisibility(View.GONE);
			}
			{
				findViewById(R.id.tableRow17).setVisibility(View.VISIBLE);
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> showlistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("reportTime")
							.substring(
									0,
									jsonObject.getString("reportTime")
											.lastIndexOf(":"));
					String text3 = "";
					if (jsonObject.getInt("reportKind") == 1)
					{
						text3 = "类型：重船航行";
					} else
					{
						text3 = "类型：空船航行";
					}
					String text4 = jsonObject.getString("reportID");
					String text5 = jsonObject.getString("startingPort") + "--"
							+ jsonObject.getString("arrivalPort");
					showlistmap.put("text1", text1);
					showlistmap.put("text2", text2);
					showlistmap.put("text3", text3);
					showlistmap.put("text4", text4);
					showlistmap.put("text5", text5);
					showlist.add(showlistmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(ElectricReportNewSearch.this, "暂无数据",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			findViewById(R.id.tableRow17).setVisibility(View.GONE);
		}

	}

	class ShowView implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.electricreportnew_item_id);
			Intent intent = new Intent(ElectricReportNewSearch.this,
					ElectricReportNewShow.class);
			intent.putExtra("reportID", tv_id.getText().toString());
			startActivity(intent);

			if (user != null)
			{
				Log log = new Log(user.getName(), "查看航行电子报告", GlobalVar.LOGSEE,
						""); // 日志
				log.execute();
			}

		}

	}
}
