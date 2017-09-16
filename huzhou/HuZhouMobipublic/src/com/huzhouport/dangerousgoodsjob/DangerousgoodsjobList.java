package com.huzhouport.dangerousgoodsjob;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DangerousgoodsjobList extends Activity {
	private ListView lv;
	private ClearEditText searchtext; // 搜索框
	private String content;// 搜索框内容
	private Query query = new Query();
	private String userId;
	private User user;
	private ArrayList<HashMap<String, Object>> dangerousgoodsjob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_list);

		user = (User) getIntent().getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());

		lv = (ListView) findViewById(R.id.dangerousgoodsjob_viewlist);

		// //获取列表信息
		// getDangerousgoodsjob(content);
		// //显示列表信息
		// showDangerousgoodsjob();

		// 获取显示列表信息
		ListTask task = new ListTask(this); // 异步
		task.execute();

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsjob_back);
		back.setOnClickListener(new Back());

		// 搜索的图标
		ImageButton search = (ImageButton) findViewById(R.id.dangerousgoodsjob_search);
		search.setOnClickListener(new Search());

		searchtext = (ClearEditText) findViewById(R.id.dangerousgoodsjob_searchtext);
		searchtext.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = searchtext.getText().toString();
				System.out.println("searchtext==" + text);

				if (text.length() == 0) {
					// System.out.println("searchtext====1");

					findViewById(R.id.dangerousgoodsjob_search1).setVisibility(
							View.VISIBLE);
					findViewById(R.id.dangerousgoodsjob_search2).setVisibility(
							View.GONE);
					content = null;
					ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
					task.execute();

				} else {
					// name=text;
					findViewById(R.id.dangerousgoodsjob_search2).setVisibility(
							View.VISIBLE);
					findViewById(R.id.dangerousgoodsjob_search1).setVisibility(
							View.GONE);
				}

			}

		});

		// 取消
		Button quxiao = (Button) findViewById(R.id.dangerousgoodsjob_search1);
		quxiao.setOnClickListener(new Quxiao());
		// 搜索按钮
		Button search1 = (Button) findViewById(R.id.dangerousgoodsjob_search2);
		search1.setOnClickListener(new SearchButton());

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {

			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getDangerousgoodsjob(String result) {

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			if (jsonArray.length() == 0) {
				Toast.makeText(DangerousgoodsjobList.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
			}
			{
				dangerousgoodsjob = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, Object> dangerousgoodsjobmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("wharfName");
					String text3 = jsonObject.getString("declareTime")
							.substring(
									0,
									jsonObject.getString("declareTime")
											.lastIndexOf(":"));
					String text4 = jsonObject.getString("declareID");
					dangerousgoodsjobmap.put("text1", text1);
					dangerousgoodsjobmap.put("text2", text2);
					dangerousgoodsjobmap.put("text3", text3);
					dangerousgoodsjobmap.put("text4", text4);
					dangerousgoodsjob.add(dangerousgoodsjobmap);
				}
			}
		} catch (Exception e) {
			Toast.makeText(DangerousgoodsjobList.this, "请检查网络",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void showDangerousgoodsjob() {
		SimpleAdapter adapter = new SimpleAdapter(DangerousgoodsjobList.this,
				dangerousgoodsjob, R.layout.dangerousgoodsjob_item,
				new String[] { "text1", "text2", "text3", "text4" }, new int[] {
						R.id.dangerousgoodsjob_shipName,
						R.id.dangerousgoodsjob_wharfName,
						R.id.dangerousgoodsjob_declareTime,
						R.id.dangerousgoodsjob_declareID });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new DangerousgoodsportlnItem());
	}

	class DangerousgoodsportlnItem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.dangerousgoodsjob_declareID);
			Intent intent = new Intent(DangerousgoodsjobList.this,
					DangerousgoodsjobapprovalView.class);
			intent.putExtra("id", tv_id.getText().toString());
			intent.putExtra("userid", userId);
			intent.putExtra("User", user);
			// startActivity(intent);
			startActivityForResult(intent, 100);

			// Toast.makeText(DangerousgoodsportlnList.this,
			// tv_id.getText().toString(), Toast.LENGTH_SHORT).show();
		}

	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}

	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
					View.VISIBLE);
		}
	}

	public class Quxiao implements View.OnClickListener {
		public void onClick(View v) {
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.dangerousgoodsjob_table2)
					.setVisibility(View.GONE);
		}
	}

	public class SearchButton implements View.OnClickListener {
		public void onClick(View v) {
			content = searchtext.getText().toString();
			ListTask task = new ListTask(DangerousgoodsjobList.this); // 异步
			task.execute();

		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		public ListTask(Context context) {
			pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true);
		}

		@Override
		protected String doInBackground(String... params) {
			// dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
			// showDangerousgoodsportln();

			String result;
			if (content == null) {
				result = query.showDangerousGoodsJob();
			} else {
				result = query.selectDangerousGoodsJob1(content);
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			// 获取列表信息
			getDangerousgoodsjob(result);
			// 显示列表信息
			showDangerousgoodsjob();
			super.onPostExecute(result);
		}

	}

}
