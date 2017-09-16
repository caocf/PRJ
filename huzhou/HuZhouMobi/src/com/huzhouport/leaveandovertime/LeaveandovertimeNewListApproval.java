package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;
import com.huzhouport.pushmsg.PushMsg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
 * ��������
 * 
 * @author Administrator
 * 
 */
public class LeaveandovertimeNewListApproval extends Fragment
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
	public void onResume() {
		new ListTask(getActivity()).execute();
		super.onResume();
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

		// ��ȡ���������������б�
		//new ListTask(getActivity()).execute();

		return view;
	}

	/**
	 * ͨ���˷�������fragmentʵ����
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	static LeaveandovertimeNewListApproval newInstance(String id, String name)
	{
		LeaveandovertimeNewListApproval newFragment = new LeaveandovertimeNewListApproval();
		Bundle bundle1 = new Bundle();
		bundle1.putString("userid", id);
		bundle1.putString("username", name);
		newFragment.setArguments(bundle1);
		return newFragment;

	}

	/**
	 * ��ȡ������������
	 * 
	 * @author Administrator
	 * 
	 */
	class ListTask extends AsyncTask<String, Integer, String[]>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(
					LeaveandovertimeNewListApproval.this.getActivity(),
					ListTask.this);
			pDialog.show();

		}

		@Override
		protected String[] doInBackground(String... params)
		{

			if (isCancelled())
				return null;// ȡ���첽

			String[] ssStrings = new String[2];
			ssStrings[0] = query.LeaveAndOvertimeunfinish(userid);
			ssStrings[1] = query.LeaveAndOvertimefinish(userid);

			return ssStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if (result[0] == null || result[0].equals("null")
					|| result[0].equals("") || result[1] == null
					|| result[1].equals("null") || result[1].equals("")) {
				Toast.makeText(getActivity(), "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else {
			leave = getLeave(result[0], true);// ���δ�������
			showLeave(leave, but, lv);
			lv.setOnItemClickListener(new leaveOnitem());

			leave1 = getLeave(result[1], false);// ������������
			showLeave(leave1, but1, lv1);
			lv1.setOnItemClickListener(new leaveOnitem1());
			}

			super.onPostExecute(result);
		}

	}

	/**
	 * ��ȡ�������
	 * 
	 * @param result
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getLeave(String result,
			boolean unFinished)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("leavelist");

			ArrayList<HashMap<String, Object>> tempArrayList = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				
				try {
					String msgstatus = jsonObject.getString("msgstatus");
					
					//������Ϣ״̬
					if (msgstatus != null)
						leavemap.put("msgstatus", Integer.parseInt(msgstatus));
					else
						leavemap.put("msgstatus", PushMsg.MSGSTATUS_PUSHED_READ);
					
				} catch (JSONException e) {
					leavemap.put("msgstatus", PushMsg.MSGSTATUS_PUSHED_READ);
				}
				
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

				if (jsonObject.getString("approvalResult").equals("������"))
				{
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
				String approvalID = jsonObject.getString("approvalID");

				if (unFinished)
				{
					if (approvalID.equals(userid))
					{
						if (jsonObject.getString("approvalResult")
								.equals("������")
								|| jsonObject.getString("approvalResult")
										.equals("��������"))
						{
							leavemap.put("text1", text1);
							leavemap.put("text2", text2);
							leavemap.put("text3", text3);

						}
					}
				} else
				{
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
				}

				tempArrayList.add(leavemap);
			}

			return tempArrayList;

		} catch (Exception e)
		{
			Toast.makeText(getActivity(), "�����쳣", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ��ʾ�б���Ϣ
	 * 
	 * @param data
	 * @param button
	 * @param listView
	 */
	public void showLeave(ArrayList<HashMap<String, Object>> data,
			Button button, ListView listView)
	{
		if (data == null || data.size() == 0)
		{
			button.setVisibility(View.GONE);
		} else
		{
			button.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new ExtSimpleAdapter(getActivity(), data,
					R.layout.leaveandovertime_newlist_approvalitem,
					new String[] { "imgleft", "kind", "img", "text1", "text2",
							"text3" }, new int[] {
							R.id.leaveandovertime_newlist_approvalitem_leftimg,
							R.id.leaveandovertime_newlist_approvalitem_kind,
							R.id.leaveandovertime_newlist_approvalitem_img,
							R.id.leaveandovertime_newlist_approvalitem_name,
							R.id.leaveandovertime_newlist_approvalitem_name1,
							R.id.leaveandovertime_newlist_approvalitem_id });
			listView.setAdapter(adapter);
		}
	}
	
	class ExtSimpleAdapter extends SimpleAdapter{

		public ExtSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = super.getView(position, convertView, parent);
			TextView tv = (TextView) v.findViewById(R.id.leaveandovertime_newlist_approvalitem_name);
			Map<String, Object> dt = (Map<String, Object>) this.getItem(position);
			
			if ((Integer)dt.get("msgstatus") == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD 
				|| (Integer)dt.get("msgstatus") == PushMsg.MSGSTATUS_PUSHED_NOTREAD) {
				tv.setTextColor(Color.parseColor("#333333"));
			}
			else{
				tv.setTextColor(Color.parseColor("#999999"));
			}
			return v;
		}
	}

	/**
	 * ����������¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class leaveOnitem1 implements OnItemClickListener
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

	/**
	 * δ��������¼�
	 * 
	 * @author Administrator
	 * 
	 */
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
					LeaveandovertimeNewListShow.class);
			intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
			intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ� 1���
																	// 2�Ӱ�
			intent.putExtra("userid", userid); // �û�id
			intent.putExtra("username", username); //
			// startActivity(intent);
			startActivityForResult(intent, 100);

			//new Log(username, "�鿴��ټӰ�", GlobalVar.LOGSEE, 1).execute(); // ��־
		}

	}

}
