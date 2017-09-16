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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EquipmentApproval extends Fragment
{
	private ListView lv, lv1;
	private Button but, but1;
	private String userid, username;
	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> leave;
	private ArrayList<HashMap<String, Object>> leave1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		// user = new User(getArguments());
		userid = getArguments().getString("userid");
		username = getArguments().getString("username");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		View view = inflater.inflate(
				R.layout.leaveandovertime_newlist_approval, container, false);
		lv = (ListView) view.findViewById(R.id.leaveandover_newlist_listview);
		lv1 = (ListView) view.findViewById(R.id.leaveandover_newlist_listview1);
		but = (Button) view
				.findViewById(R.id.leaveandover_newlist_approvalbutton);
		but1 = (Button) view
				.findViewById(R.id.leaveandover_newlist_approvalbutton1);
		intDate(view);
		return view;
	}

	static EquipmentApproval newInstance(String id, String name)
	{
		EquipmentApproval newFragment = new EquipmentApproval();
		Bundle bundle1 = new Bundle();
		bundle1.putString("userid", id);
		bundle1.putString("username", name);
		newFragment.setArguments(bundle1);
		return newFragment;

	}

	private void intDate(View view)
	{
		// 获取显示列表信息
		ListTask task = new ListTask(getActivity()); // 异步
		task.execute();

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
			pDialog = new ProgressDialog(getActivity());
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
			/*
			 * String[] ssStrings=new String[2];
			 * ssStrings[0]=query.LeaveAndOvertimeunfinish(userid);
			 * ssStrings[1]=query.LeaveAndOvertimefinish(userid);
			 */
			return query.equipmentApprovalbyMy(userid);
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
			Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void showLeave()
	{
		if (leave.size() == 0)
		{
			but.setVisibility(View.GONE);
		} else
		{
			but.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), leave,
					R.layout.equipment_approvalitem, new String[] { "img",
							"text1", "text2", "text3" }, new int[] {
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
			but1.setVisibility(View.GONE);
		} else
		{
			but1.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), leave1,
					R.layout.equipment_approvalitem, new String[] { "img",
							"text1", "text2", "text3" }, new int[] {
							R.id.equipment_approvalitem_img,
							R.id.equipment_approvalitem_name,
							R.id.equipment_approvalitem_name1,
							R.id.equipment_approvalitem_id });
			lv1.setAdapter(adapter);
			lv1.setOnItemClickListener(new leaveOnitem1());
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
			Intent intent = new Intent(getActivity(), EquipmentView.class);
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
			Intent intent = new Intent(getActivity(), EquipmentShow.class);
			intent.putExtra("id", tv_id.getText().toString()); // 请假记录编号
			intent.putExtra("userid", userid); // 用户id
			intent.putExtra("username", username); // 用户name
			startActivityForResult(intent, 100);

			Log log = new Log(username, "查看设备申请", GlobalVar.LOGSEE, ""); // 日志
			log.execute();
		}

	}

}
