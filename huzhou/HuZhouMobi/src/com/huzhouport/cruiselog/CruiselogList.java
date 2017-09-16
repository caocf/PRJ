package com.huzhouport.cruiselog;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CruiselogList extends Activity
{
	private Query query = new Query();
	private ListView lv, lv1;
	private ClearEditText searchtext; // 搜索框
	private String content;// 搜索框内容
	private ArrayList<HashMap<String, Object>> cruiseloglist;
	private ArrayList<HashMap<String, Object>> cruiseloglist1;

	private User user;

	private int unnum = 0, num = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cruiselog_main);

		user = (User) getIntent().getSerializableExtra("User");

		lv = (ListView) findViewById(R.id.cruiselog_listview1);
		lv1 = (ListView) findViewById(R.id.cruiselog_listview2);

		// 获取显示列表信息
		ListTask task = new ListTask(this); // 异步
		task.execute();

		ImageButton back = (ImageButton) findViewById(R.id.cruiselog_back);
		back.setOnClickListener(new Back());

		// 搜索的图标
		ImageButton search = (ImageButton) findViewById(R.id.cruiselog_search);
		search.setOnClickListener(new Search());

		searchtext = (ClearEditText) findViewById(R.id.cruiselog_searchtext);

		// 取消
		ImageButton quxiao = (ImageButton) findViewById(R.id.cruiselog_searchtext_back);
		quxiao.setOnClickListener(new Quxiao());
		// 搜索按钮
		ImageButton search1 = (ImageButton) findViewById(R.id.cruiselog_searchtext_bt);
		search1.setOnClickListener(new SearchButton());

		// 新建
		ImageButton img = (ImageButton) findViewById(R.id.cruiselog_new);
		img.setOnClickListener(new Img());

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode)
		{

			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getNeirong(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("cruiseLogBeanList");
			if (jsonArray.length() == 0)
			{
				findViewById(R.id.cruiselog_unfinsh).setVisibility(View.GONE);
			} else
			{
				unnum = 1;
				findViewById(R.id.cruiselog_unfinsh)
						.setVisibility(View.VISIBLE);
				cruiseloglist = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> cruiseloglistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String name = jsonObject.getString("cruiseLogLoaction");
					String user = "巡航人员："
							+ jsonObject.getString("cruiseLogUserName");
					// String
					// time=jsonObject.getString("startTime").substring(0,jsonObject.getString("startTime").lastIndexOf(":"))+"--"+jsonObject.getString("endTime").substring(0,jsonObject.getString("endTime").lastIndexOf(":"));
					String time = jsonObject.getString("startTime").substring(
							0,
							jsonObject.getString("startTime").lastIndexOf(":"));
					String id = jsonObject.getString("cruiseLogID");
					cruiseloglistmap.put("name", name);
					cruiseloglistmap.put("user", user);
					cruiseloglistmap.put("time", time);
					cruiseloglistmap.put("id", id);
					cruiseloglist.add(cruiseloglistmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(CruiselogList.this, "请检查网络", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public void showListview()
	{

		if (cruiseloglist == null)
		{

		} else
		{

			SimpleAdapter adapter = new SimpleAdapter(CruiselogList.this,
					cruiseloglist, R.layout.cruiselog_mainitem, new String[] {
							"name", "user", "time", "id" }, new int[] {
							R.id.cruiselog_name, R.id.cruiselog_user,
							R.id.cruiselog_time, R.id.cruiselog_id });
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new CruiselogItem1());
		}
	}

	public void getNeirong1(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("cruiseLogBeanList");
			if (jsonArray.length() == 0)
			{
				findViewById(R.id.cruiselog_finsh).setVisibility(View.GONE);
			} else
			{
				num = 1;
				cruiseloglist1 = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++)
				{
					findViewById(R.id.cruiselog_finsh).setVisibility(
							View.VISIBLE);
					HashMap<String, Object> cruiseloglistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String name = jsonObject.getString("cruiseLogLoaction");
					String user = "巡航人员："
							+ jsonObject.getString("cruiseLogUserName");
					String time = jsonObject.getString("startTime").substring(
							0,
							jsonObject.getString("startTime").lastIndexOf(":"))
							+ "--"
							+ jsonObject.getString("endTime").substring(
									0,
									jsonObject.getString("endTime")
											.lastIndexOf(":"));
					String id = jsonObject.getString("cruiseLogID");
					cruiseloglistmap.put("name", name);
					cruiseloglistmap.put("user", user);
					cruiseloglistmap.put("time", time);
					cruiseloglistmap.put("id", id);
					cruiseloglist1.add(cruiseloglistmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(CruiselogList.this, "请检查网络", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}

	public void showListview1()
	{
		if (cruiseloglist1 == null)
		{

		} else
		{
			SimpleAdapter adapter = new SimpleAdapter(CruiselogList.this,
					cruiseloglist1, R.layout.cruiselog_mainitem1, new String[] {
							"name", "user", "time", "id" }, new int[] {
							R.id.cruiselog_name1, R.id.cruiselog_user1,
							R.id.cruiselog_time1, R.id.cruiselog_id1 });
			lv1.setAdapter(adapter);
			lv1.setOnItemClickListener(new CruiselogItem());
		}
	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{

			finish();
		}
	}

	public class Search implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.cruiselog_titeltable1)
					.setVisibility(View.VISIBLE);
		}
	}

	public class Quxiao implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.cruiselog_titeltable1).setVisibility(View.GONE);
		}
	}

	public class SearchButton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content = searchtext.getText().toString();

			num = 0;
			unnum = 0;
			ListTask1 task = new ListTask1(CruiselogList.this); // 异步
			task.execute();

		}
	}

	public void getNeirongBycontent(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("cruiseLogBeanList");
			cruiseloglist = new ArrayList<HashMap<String, Object>>();
			if (jsonArray.length() == 0)
			{
				findViewById(R.id.cruiselog_unfinsh).setVisibility(View.GONE);
			} else
			{
				unnum = 1;
				findViewById(R.id.cruiselog_unfinsh)
						.setVisibility(View.VISIBLE);
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> cruiseloglistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String name = jsonObject.getString("cruiseLogLoaction");
					String user = "巡航人员："
							+ jsonObject.getString("cruiseLogUserName");
					String time = jsonObject.getString("startTime").substring(
							0,
							jsonObject.getString("startTime").lastIndexOf(":"));
					String id = jsonObject.getString("cruiseLogID");
					cruiseloglistmap.put("name", name);
					cruiseloglistmap.put("user", user);
					cruiseloglistmap.put("time", time);
					cruiseloglistmap.put("id", id);
					cruiseloglist.add(cruiseloglistmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(CruiselogList.this, "查找数据失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}

	public void getNeirongBycontent1(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("cruiseLogBeanList");
			cruiseloglist1 = new ArrayList<HashMap<String, Object>>();
			if (jsonArray.length() == 0)
			{
				findViewById(R.id.cruiselog_finsh).setVisibility(View.GONE);
			} else
			{
				num = 1;
				for (int i = 0; i < jsonArray.length(); i++)
				{
					findViewById(R.id.cruiselog_finsh).setVisibility(
							View.VISIBLE);
					HashMap<String, Object> cruiseloglistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String name = jsonObject.getString("cruiseLogLoaction");
					String user = "巡航人员："
							+ jsonObject.getString("cruiseLogUserName");
					String time = jsonObject.getString("startTime").substring(
							0,
							jsonObject.getString("startTime").lastIndexOf(":"))
							+ "--"
							+ jsonObject.getString("endTime").substring(
									0,
									jsonObject.getString("endTime")
											.lastIndexOf(":"));
					String id = jsonObject.getString("cruiseLogID");
					cruiseloglistmap.put("name", name);
					cruiseloglistmap.put("user", user);
					cruiseloglistmap.put("time", time);
					cruiseloglistmap.put("id", id);
					cruiseloglist1.add(cruiseloglistmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(CruiselogList.this, "获取数据失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}

	class CruiselogItem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{

			TextView tv_id = (TextView) arg1.findViewById(R.id.cruiselog_id1);
			Intent intent = new Intent(CruiselogList.this,
					CruiselogViewFinsh.class);
			intent.putExtra("id", tv_id.getText().toString());
			// startActivityForResult(intent,100);
			intent.putExtra("User", user);
			startActivity(intent);

			if (user != null)
			{
				Log log = new Log(user.getName(), "查看巡航日志", GlobalVar.LOGSEE,
						""); // 日志
				log.execute();
			}

			// Toast.makeText(DangerousgoodsportlnList.this,
			// tv_id.getText().toString(), Toast.LENGTH_SHORT).show();
		}

	}

	class CruiselogItem1 implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{

			TextView tv_id = (TextView) arg1.findViewById(R.id.cruiselog_id);
			Intent intent = new Intent(CruiselogList.this, CruiselogAdd.class);
			intent.putExtra("cruiselogid", tv_id.getText().toString());
			
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
			
		}

	}

	class Img implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			// Intent intent=new Intent(Addresslist.this,
			// AddresslistMenu.class);
			if (unnum == 1)
			{
				Toast.makeText(CruiselogList.this, "有巡航日志尚未完成",
						Toast.LENGTH_SHORT).show();
			} else
			{
				Intent intent = new Intent(CruiselogList.this,
						CruiselogAdd.class);
				intent.putExtra("cruiselogid", "0");
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);
			}
		}

	}

	class ListTask extends AsyncTask<String, Integer, String[]>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();

		}

		@Override
		protected String[] doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异

			String[] ssStrings = new String[2];
			ssStrings[0] = query.showCruiseLogUnfinish();
			ssStrings[1] = query.showCruiseLogFinish();

			return ssStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result[0]); // 获得数据
			showListview();// 显示数据
			getNeirong1(result[1]); // 获得数据
			showListview1();// 显示数据

			if (num + unnum == 0)
			{
				Toast.makeText(CruiselogList.this, "暂无相关数据", Toast.LENGTH_SHORT)
						.show();
			}
			super.onPostExecute(result);
		}

	}

	class ListTask1 extends AsyncTask<String, Integer, String[]>
	{
		ProgressDialog pDialog = null;

		public ListTask1(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();

		}

		@Override
		protected String[] doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			String[] ssStrings = new String[2];
			ssStrings[0] = query.selectCruiseLogUnfinish(content);
			ssStrings[1] = query.selectCruiseLogFinish(content);

			return ssStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getNeirongBycontent(result[0]); // 获得数据
			showListview();// 显示数据
			getNeirongBycontent1(result[1]); // 获得数据
			showListview1();// 显示数据

			if (num + unnum == 0)
			{
				Toast.makeText(CruiselogList.this, "暂无相关数据", Toast.LENGTH_SHORT)
						.show();
			}
			super.onPostExecute(result);
		}

	}

}
