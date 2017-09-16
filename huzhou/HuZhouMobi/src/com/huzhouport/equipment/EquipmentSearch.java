package com.huzhouport.equipment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EquipmentSearch extends Activity
{
	private ListView lv, lv1;
	private Button but, but1;
	private String userid, username;
	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> leave;
	private ArrayList<HashMap<String, Object>> leave1;
	private EditText searchtext; // 搜索框
	private ImageButton img_searchbutton, img_searchback;
	private String content;
	private int l1, l2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_search);

		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		username = intent.getStringExtra("username");

		but = (Button) findViewById(R.id.leaveandover_newlist_search_approvalbutton);
		but1 = (Button) findViewById(R.id.leaveandover_newlist_search_approvalbutton1);

		img_searchbutton = (ImageButton) findViewById(R.id.leaveandovertime_newlist_search_searchbutton);
		searchtext = (EditText) findViewById(R.id.leaveandovertime_newlist_search_searchtextname);
		img_searchback = (ImageButton) findViewById(R.id.leaveandovertime_newlist_search_back);

		img_searchback.setOnClickListener(new Back());
		img_searchbutton.setOnClickListener(new Search());

		lv = (ListView) findViewById(R.id.leaveandover_newlist_search_listview);
		lv1 = (ListView) findViewById(R.id.leaveandover_newlist_search_listview1);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode)
		{
			setResult(20);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class Back implements View.OnClickListener
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
			content = searchtext.getText().toString();
			if (content.length() == 0)
			{
				Toast.makeText(EquipmentSearch.this, "请先在搜索框里输入内容",
						Toast.LENGTH_SHORT).show();
			} else
			{
				ListTask task = new ListTask(EquipmentSearch.this); // 异步
				task.execute();
			}

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
			pDialog = new ProgressDialog(EquipmentSearch.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
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

			if (isCancelled())
				return null;// 取消异步

			return query.equipmentApprovalbyMycontent(userid, content);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getLeave(result);// 获得数据
			showLeave();
			showLeave1();
			super.onPostExecute(result);
		}

	}

	public void getLeave(String result)
	{

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("equipmentlist");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			leave = new ArrayList<HashMap<String, Object>>();
			leave1 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				String text1 = jsonObject.getString("equipmentKindName");
				String text2 = jsonObject.getString("equipmentUserName")
						+ " "
						+ jsonObject.getString("equipmentDate").substring(
								0,
								jsonObject.getString("equipmentDate")
										.lastIndexOf(":"));
				String text3 = jsonObject.getString("equipmentID");
				if (jsonObject.getString("approvalResult").equals("0"))
				{
					System.out.println("jsonArray===1");
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult").equals("3"))
				{
					leavemap.put("img", R.drawable.leave_status3);
				} else
				{
					leavemap.put("img", R.drawable.leave_status4);
				}

				if (jsonObject.getString("approvalResult").equals("0"))
				{
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leave.add(leavemap);
				} else
				{
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leave1.add(leavemap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(EquipmentSearch.this, "数据异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public void showLeave()
	{
		if (leave.size() == 0)
		{
			l1 = 0;
			but.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
		} else
		{
			l1 = 1;
			but.setVisibility(View.VISIBLE);
			lv.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new SimpleAdapter(EquipmentSearch.this,
					leave, R.layout.equipment_approvalitem, new String[] {
							"img", "text1", "text2", "text3" }, new int[] {
							R.id.equipment_approvalitem_img,
							R.id.equipment_approvalitem_name,
							R.id.equipment_approvalitem_name1,
							R.id.equipment_approvalitem_id });
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new leaveOnitem());
		}
	}

	public void showLeave1()
	{
		if (leave1.size() == 0)
		{
			l2 = 0;
			but1.setVisibility(View.GONE);
			lv1.setVisibility(View.GONE);
		} else
		{
			l2 = 1;

			but1.setVisibility(View.VISIBLE);
			lv1.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new SimpleAdapter(EquipmentSearch.this,
					leave1, R.layout.equipment_approvalitem, new String[] {
							"img", "text1", "text2", "text3" }, new int[] {
							R.id.equipment_approvalitem_img,
							R.id.equipment_approvalitem_name,
							R.id.equipment_approvalitem_name1,
							R.id.equipment_approvalitem_id });
			lv1.setAdapter(adapter);
			lv1.setOnItemClickListener(new leaveOnitem1());
		}
		int s = l1 + l2;
		if (s == 0)
		{
			Toast.makeText(EquipmentSearch.this, "没有找到相关数据", Toast.LENGTH_SHORT)
					.show();
		}

	}

	class leaveOnitem1 implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.equipment_approvalitem_id);
			Intent intent = new Intent(EquipmentSearch.this,
					EquipmentView.class);
			intent.putExtra("id", tv_id.getText().toString()); // 编号
			intent.putExtra("userid", userid); // 用户id
			startActivity(intent);
			Log log = new Log(username, "查看设备申请", GlobalVar.LOGSEE, ""); // 日志
			log.execute();
		}

	}

	class leaveOnitem implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.equipment_approvalitem_id);
			Intent intent = new Intent(EquipmentSearch.this,
					EquipmentShow.class);
			intent.putExtra("id", tv_id.getText().toString()); // 请假记录编号
			intent.putExtra("userid", userid); // 用户id
			intent.putExtra("username", username); // 用户name
			startActivityForResult(intent, 100);
			Log log = new Log(username, "查看设备申请", GlobalVar.LOGSEE, ""); // 日志
			log.execute();
		}

	}

}
