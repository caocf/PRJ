package com.huzhouport.dangerousgoodsjob;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DangerousgoodsjobList extends Activity
{
	private ListView lv;
	private EditText searchtext; // 搜索框
	private String content = "";// 搜索框内容
	private Query query = new Query();
	private String userId;
	private User user;
	private ArrayList<HashMap<String, Object>> dangerousgoodsjob;
	private boolean issearch = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_list);

		user = (User) getIntent().getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());

		lv = (ListView) findViewById(R.id.dangerousgoodsjob_viewlist);

		ListTask task = new ListTask(this); // 异步
		task.execute();

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsjob_back);
		back.setOnClickListener(new Back());

		// 搜索的图标
		ImageButton search = (ImageButton) findViewById(R.id.dangerousgoodsjob_search);
		search.setOnClickListener(new Search());

		searchtext = (EditText) findViewById(R.id.dangerousgoodsjob_searchtext);
		searchtext.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				String text = searchtext.getText().toString();

				if (text.length() == 0)
				{
					content = "";
					ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
					task.execute();
				}

			}

		});

		// 搜索按钮
		ImageButton search1 = (ImageButton) findViewById(R.id.dangerousgoodsjob_search_searchbutton);
		search1.setOnClickListener(new SearchButton());
		ImageButton nosearch = (ImageButton) findViewById(R.id.dangerousgoodsjob_search_back);
		nosearch.setOnClickListener(new NoSearch());

	}

	/**
	 * 后退按钮事件监听
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (issearch)
			{
				findViewById(R.id.dangerousgoodsjob_table).setVisibility(
						View.VISIBLE);
				findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
						View.INVISIBLE);
				content = "";
				issearch = false;
				ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
				task.execute();
			} else
			{
				finish();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
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

	public void getDangerousgoodsjob(String result)
	{

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			if (jsonArray.length() == 0)
			{
				Toast.makeText(DangerousgoodsjobList.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
			}
			{
				dangerousgoodsjob = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> dangerousgoodsjobmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("wharfID");
					String text3 = jsonObject.getString("declareTime")
							.substring(
									0,
									jsonObject.getString("declareTime")
											.lastIndexOf(":"));
					String text4 = jsonObject.getString("declareID");
					String text5 = jsonObject.getString("reviewResult");
					dangerousgoodsjobmap.put("text1", text1);
					dangerousgoodsjobmap.put("text2", text2);
					dangerousgoodsjobmap.put("text3", text3);
					dangerousgoodsjobmap.put("text4", text4);
					dangerousgoodsjobmap.put("text5", text5);

					int reviewResult = jsonObject.getInt("reviewResult"); //
					if (reviewResult == 0)
						dangerousgoodsjobmap
								.put("img", R.drawable.leavestatus1);
					else if (reviewResult == 4)
						dangerousgoodsjobmap
								.put("img", R.drawable.leavestatus4);
					else if (reviewResult == 3)
						dangerousgoodsjobmap
								.put("img", R.drawable.leavestatus3);
					dangerousgoodsjob.add(dangerousgoodsjobmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(DangerousgoodsjobList.this, "请检查网络",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void showDangerousgoodsjob()
	{
		SimpleAdapter adapter = new SimpleAdapter(DangerousgoodsjobList.this,
				dangerousgoodsjob, R.layout.dangerousgoodsjob_item,
				new String[] { "text1", "text3", "text2", "img", "text4",
						"text5" }, new int[] { R.id.dangerousgoodsjob_shipName,
						R.id.dangerousgoodsjob_declareTime,
						R.id.dangerousgoodsjob_wharfName,
						R.id.dangerousgoodsjob_addImage,
						R.id.dangerousgoodsjob_declareID,
						R.id.dangerousgoodsjob_result });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new DangerousgoodsportlnItem());
	}

	class DangerousgoodsportlnItem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.dangerousgoodsjob_declareID);
			TextView tv_result = (TextView) arg1
					.findViewById(R.id.dangerousgoodsjob_result);
			String text7 = tv_result.getText().toString();
			System.out.print("aa" + text7);
			if (text7.equals("0"))
			{
				Intent intent = new Intent(DangerousgoodsjobList.this,
						DangerousgoodsjobapprovalView.class);
				intent.putExtra("id", tv_id.getText().toString());
				intent.putExtra("userid", userId);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);

				if (user != null)
				{
					new Log(user.getName(),"查看危险货物码头作业",GlobalVar.LOGSEE,"").execute();
				}
			} else
			{
				Intent intent = new Intent(DangerousgoodsjobList.this,
						DangerousgoodsjobAddView.class);
				intent.putExtra("id", tv_id.getText().toString());
				intent.putExtra("userid", userId);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);

				if (user != null)
				{
					new Log(user.getName(),"查看危险货物码头作业",GlobalVar.LOGSEE,"").execute();
				}
			}

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

			findViewById(R.id.dangerousgoodsjob_table).setVisibility(
					View.INVISIBLE);
			findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
					View.VISIBLE);
			issearch = true;
		}
	}

	public class Quxiao implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.dangerousgoodsjob_table2)
					.setVisibility(View.GONE);
		}
	}

	public class SearchButton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content = searchtext.getText().toString();
			ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
			task.execute();

		}
	}

	public class NoSearch implements View.OnClickListener
	{
		public void onClick(View v)
		{
			findViewById(R.id.dangerousgoodsjob_table).setVisibility(
					View.VISIBLE);
			findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
					View.INVISIBLE);
			content = "";
			issearch = false;
			ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
			task.execute();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			// dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
			// showDangerousgoodsportln();

			if (isCancelled())
				return null;// 取消异步

			String result;
			if (content.equals(""))
			{
				result = query.showDangerousGoodsJob();
			} else
			{
				result = query.selectDangerousGoodsJob1(content);
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if (result == null)
			{
				Toast.makeText(DangerousgoodsjobList.this, "网络异常",
						Toast.LENGTH_SHORT).show();
			} else
			{
				// 获取列表信息
				getDangerousgoodsjob(result);
				// 显示列表信息
				showDangerousgoodsjob();
			}
			super.onPostExecute(result);
		}

	}

}
