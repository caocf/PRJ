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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ElectricReportNewList extends Activity {
	private User user = new User();

	private ListView lv;
	private View moreView; // 加载更多页面
	private SimpleAdapter adapter;
	private int cpage = 1, maxpage;
	private ImageButton ele_search;
	private ArrayList<HashMap<String, Object>> showlist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreport_list);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		lv = (ListView) findViewById(R.id.electricreport_main_list);

		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		ShowList();

		ListTask task = new ListTask(this); // 异步
		task.execute();

		ImageButton back = (ImageButton) findViewById(R.id.electricreport_list_back);
		back.setOnClickListener(new Back());

		ele_search = (ImageButton) findViewById(R.id.electricreport_list_searchreportadd);
		ele_search.setOnClickListener(new Search());
	}

	// 搜索
	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewSearch.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
		}
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	public void ShowList() {
		showlist = new ArrayList<HashMap<String, Object>>();
		adapter = new SimpleAdapter(ElectricReportNewList.this, showlist,
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

		lv.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
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
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	private void LoadList() {

		if (cpage < maxpage) {
			cpage += 1;
			new ListTask(this).execute();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		
		public ListTask(Context context) {
			if (cpage == 1) {
				pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {

			if (isCancelled())
				return null;// 取消异步

			HttpFileUpTool hft = new HttpFileUpTool();
			Map<String, Object> p = new HashMap<String, Object>();
			String result = null;
			try {
				p.put("cpage", cpage);
				result = hft.post(HttpUtil.BASE_URL
						+ "ElectricReportListByInfo", p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (cpage == 1) {
				if (pDialog != null)
					pDialog.dismiss();
			}
			if (result == null) {
				Toast.makeText(ElectricReportNewList.this, "网络异常",
						Toast.LENGTH_SHORT).show();
			} else {
				GetJson(result);

				adapter.notifyDataSetChanged();// 刷新
				if (cpage >= maxpage) {
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
		// System.out.println("result="+result);
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();

			maxpage = data.getInt("totalPage");
			JSONArray jsonArray = data.getJSONArray("list");
			// System.out.println("jsonArray="+jsonArray);
			if (jsonArray.length() == 0) {
				Toast.makeText(ElectricReportNewList.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
				findViewById(R.id.tableRow16).setVisibility(View.GONE);
			}
			{

				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, Object> showlistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("reportTime")
							.substring(
									0,
									jsonObject.getString("reportTime")
											.lastIndexOf(":"));
					String text3 = "";
					if (jsonObject.getInt("reportKind") == 1) {
						text3 = "类型：重船航行";
					} else {
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
		} catch (Exception e) {
			Toast.makeText(ElectricReportNewList.this, "暂无数据",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			findViewById(R.id.tableRow16).setVisibility(View.GONE);
		}

	}

	class ShowView implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.electricreportnew_item_id);
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewShow.class);
			intent.putExtra("reportID", tv_id.getText().toString());
			startActivity(intent);
			if (user != null) {
				new Log(user.getName(), "查看航行电子报告", GlobalVar.LOGSEE, "").execute();
			}

		}

	}
}
