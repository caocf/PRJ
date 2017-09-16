package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;

import net.hxkg.ghmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * �ҵ�����ҳ��
 * @author Administrator
 *
 */
public class LeaveandovertimeNewListApply extends Fragment
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
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		// user = new User(getArguments());
		userid = getArguments().getString("userid");
		username = getArguments().getString("username");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO �Զ����ɵķ������
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

	static LeaveandovertimeNewListApply newInstance(String id, String name)
	{
		LeaveandovertimeNewListApply newFragment = new LeaveandovertimeNewListApply();
		Bundle bundle1 = new Bundle();
		bundle1.putString("userid", id);
		bundle1.putString("username", name);
		newFragment.setArguments(bundle1);
		return newFragment;

	}

	private void intDate(View view)
	{
		ListTask task = new ListTask(getActivity()); // �첽
		task.execute();
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, ListTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;
			return query.LeaveAndOvertiemALLbymy(userid);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if(result!=null){
			getLeave(result);// �������
			showLeave();
			showLeave1();
			}
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
			JSONArray jsonArray = data.getJSONArray("leavelist");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			leave = new ArrayList<HashMap<String, Object>>();
			leave1 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				if (jsonObject.getString("kindType").equals("1"))
				{
					leavemap.put("imgleft", R.drawable.leave_leave);
					leavemap.put("kind", jsonObject.getString("kindType"));
				} else if (jsonObject.getString("kindType").equals("2"))
				{
					leavemap.put("imgleft", R.drawable.leave_overtime);
					leavemap.put("kind", jsonObject.getString("kindType"));
				} else
				{
					leavemap.put("imgleft", R.drawable.leave_travel);
					leavemap.put("kind", jsonObject.getString("kindType"));
				}

				String text1 = jsonObject.getString("leaveOrOtReason");
				String text2 = jsonObject.getString("leaveOrOtUser")
						+ " "
						+ jsonObject.getString("leaveOrOtDate").substring(
								0,
								jsonObject.getString("beginDate").lastIndexOf(
										":"));
				String text3 = jsonObject.getString("leaveOrOtID");

				System.out.println("jsonArray===" + text1);
				System.out.println("jsonArray===" + text2);
				if (jsonObject.getString("approvalResult").equals("������"))
				{
					System.out.println("jsonArray===1");
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult")
						.equals("��������"))
				{
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult").equals("����"))
				{
					leavemap.put("img", R.drawable.leave_status3);
				} else
				{
					leavemap.put("img", R.drawable.leave_status4);
				}

				if (jsonObject.getString("approvalResult").equals("������")
						|| jsonObject.getString("approvalResult")
								.equals("��������"))
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
			Toast.makeText(getActivity(), "�����쳣", Toast.LENGTH_SHORT).show();
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
					R.layout.leaveandovertime_newlist_approvalitem,
					new String[] { "imgleft", "kind", "img", "text1", "text2",
							"text3" }, new int[] {
							R.id.leaveandovertime_newlist_approvalitem_leftimg,
							R.id.leaveandovertime_newlist_approvalitem_kind,
							R.id.leaveandovertime_newlist_approvalitem_img,
							R.id.leaveandovertime_newlist_approvalitem_name,
							R.id.leaveandovertime_newlist_approvalitem_name1,
							R.id.leaveandovertime_newlist_approvalitem_id });
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
					R.layout.leaveandovertime_newlist_approvalitem,
					new String[] { "imgleft", "kind", "img", "text1", "text2",
							"text3" }, new int[] {
							R.id.leaveandovertime_newlist_approvalitem_leftimg,
							R.id.leaveandovertime_newlist_approvalitem_kind,
							R.id.leaveandovertime_newlist_approvalitem_img,
							R.id.leaveandovertime_newlist_approvalitem_name,
							R.id.leaveandovertime_newlist_approvalitem_name1,
							R.id.leaveandovertime_newlist_approvalitem_id });
			lv1.setAdapter(adapter);
			lv1.setAdapter(adapter);
			lv1.setOnItemClickListener(new leaveOnitem());
		}
	}

	class leaveOnitem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);
			Intent intent = new Intent(getActivity(),
					LeaveandovertimeNewListView.class);
			intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
			intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ� 1���
																	// 2�Ӱ�
			intent.putExtra("userid", userid); // �û�id
			startActivity(intent);

			//new Log(username, "�鿴��ټӰ�", GlobalVar.LOGSEE, 1).execute();
		}
	}
}
