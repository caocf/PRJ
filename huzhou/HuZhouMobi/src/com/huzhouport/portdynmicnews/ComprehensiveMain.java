package com.huzhouport.portdynmicnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ComprehensiveMain extends Activity
{
	private Query query = new Query();

	private ImageButton pmback;

	private View moreView;
	private ListView pml;
	private User user;

	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;

	private SimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comprehensive_main);

		pmback = (ImageButton) findViewById(R.id.comprehensive_main_back);
		pml = (ListView) findViewById(R.id.comprehensive_main_list);
		// loadmore = (Button) findViewById(R.id.portdynmicnews_loadMoreButton);
		pmback.setOnClickListener(new MyBack());
		// 点击加载更多
		// loadmore.setOnClickListener(new AddMoreList());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");

		pml.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		String data = "cpage=" + cpage;
		new ListTask(this).execute(data);

	}

	// 列表
	public void creatListView(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
			String errorInfo = data.getString("errorInfo");
			if (null != errorInfo && !errorInfo.equals("")
					&& !errorInfo.equals("null"))
			{
				Toast.makeText(ComprehensiveMain.this, "暂无相关数据...",
						Toast.LENGTH_SHORT).show();

			} else
			{
				// 接下来的就是JSON对象的操作了
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("titile", jsonObject.get("titile"));
					map.put("date", jsonObject.get("date"));
					map.put("url", jsonObject.get("url"));
					listv.add(map);
				}
				adapter = new SimpleAdapter(ComprehensiveMain.this, listv,
						R.layout.portdynmicnews_list, new String[] { "titile",
								"date" }, new int[] {
								R.id.portdynmicnews_main_title,
								R.id.portdynmicnews_main_data });

				if (cpage == 1)
				{
					pml.setAdapter(adapter);
				}
				adapter.notifyDataSetChanged();
				moreView.setVisibility(View.VISIBLE);

				pml.setOnItemClickListener(new ProtDynmicNewsListener());
				pml.setOnScrollListener(new OnScrollListener()
				{

					public void onScrollStateChanged(AbsListView view,
							int scrollState)
					{
						// 当不滚动时
						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
						{

							// // 判断滚动到底部
							if (view.getLastVisiblePosition() == (view
									.getCount() - 1))
							{
								// 然后 经行一些业务操作
								if (cpage < maxpage)
								{
									moreView.findViewById(R.id.progressBar2)
											.setVisibility(View.VISIBLE);
									((TextView) moreView
											.findViewById(R.id.loadmore_text))
											.setText(R.string.more);
									cpage += 1;
									String data = "cpage=" + cpage;
									new ListTaskList()
											.execute(data);
								}
							}
						}
						// cpage += 1;
						// String data = "cpage=" + cpage;
						// new
						// ListTaskList(PortDynmicNewsMain.this).execute(data);
						// //Toast.makeText(PortDynmicNewsMain.this,
						// "相关数据...",Toast.LENGTH_SHORT).show();
						// }
						// if(scrollState !=
						// OnScrollListener.SCROLL_STATE_IDLE){
						// loadmore.setVisibility(View.GONE) ;//表示隐藏；
						// }

					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount)
					{
						// is_page=(firstVisibleItem+visibleItemCount==totalItemCount);
					}
				});

			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ProtDynmicNewsListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			Intent intent = new Intent(ComprehensiveMain.this,
					ComprehensiveSee.class);
			System.out.println(listv.get(arg2).get("url"));
			intent.putExtra("url", (String) listv.get(arg2).get("url"));
			intent.putExtra("User", user);
			startActivity(intent);

			if (user != null)
			{
				new Log(user.getName(), "查看知识库", GlobalVar.LOGSEE, "")
						.execute();
			}

		}
	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// Intent intent=new Intent(PortDynmicNewsMain.this,OfficOA.class);
			// startActivity(intent);
			finish();

		}

	}

	class AddMoreList implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// 加载更多方法
			cpage += 1;
			String data = "cpage=" + cpage;
			new ListTask(ComprehensiveMain.this).execute(data);
			// creatListView(result);
		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			// pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。",
			// true);
			pDialog = new ProgressDialog(ComprehensiveMain.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("数据正在加载中，请稍候···");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{

				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			String result;

			result = query.showComprehensive(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			creatListView(result);
			if (adapter != null)
			{
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage)
			{
				moreView.setVisibility(View.GONE);
				pml.removeFooterView(moreView); // 移除底部视图
				// Toast.makeText(ComprehensiveMain.this, "已加载全部数据",
				// 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}

	}

	@SuppressLint("ShowToast")
	class ListTaskList extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		@Override
		protected String doInBackground(String... params)
		{
			String result;
			result = query.showComprehensive(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			creatListView(result);
			if (adapter != null)
			{
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage)
			{
				moreView.setVisibility(View.GONE);
				pml.removeFooterView(moreView); // 移除底部视图
				// Toast.makeText(ComprehensiveMain.this, "已加载全部数据",
				// 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			// loadmore.setVisibility(View.GONE) ;//表示隐藏；
			super.onPostExecute(result);
		}

	}

}
