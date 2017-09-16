package com.huzhouport.leaveandovertime;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LeaveandovertimeNewListApproval extends Fragment implements OnScrollListener
{
	private ListView lv, lv1;
	private Button but, but1;
	private String userid, username;
	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> leave=new ArrayList<>();
	private ArrayList<HashMap<String, Object>> leave1;
	
	String id1[]=new String[2];
	String id2[]=new String[2];
	SimpleAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		userid = getArguments().getString("userid");
		username = getArguments().getString("username");
	}

	@Override
	public void onResume() 
	{
		if(leave!=null)
			leave.clear();
		new ListTask(getActivity()).execute("0");
		super.onResume();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.leaveandovertime_newlist_approval, container, false);
		lv = (ListView) view.findViewById(R.id.leaveandover_newlist_listview);
		lv.setOnItemClickListener(new leaveOnitem());
		lv.setOnScrollListener(this);
		adapter= new ExtSimpleAdapter(getActivity(), leave,
				R.layout.leaveandovertime_newlist_approvalitem,
				new String[] { "imgleft", "kind", "img", "text1", "text2",
						"text3" }, new int[] 
								{
						R.id.leaveandovertime_newlist_approvalitem_leftimg,
						R.id.leaveandovertime_newlist_approvalitem_kind,
						R.id.leaveandovertime_newlist_approvalitem_img,
						R.id.leaveandovertime_newlist_approvalitem_name,
						R.id.leaveandovertime_newlist_approvalitem_name1,
						R.id.leaveandovertime_newlist_approvalitem_id 
						});
		lv.setAdapter(adapter);
		/*lv1 = (ListView) view.findViewById(R.id.leaveandover_newlist_listview1);
		but = (Button) view.findViewById(R.id.leaveandover_newlist_approvalbutton);
		but1 = (Button) view.findViewById(R.id.leaveandover_newlist_approvalbutton1);*/

		return view;
	}
	
	static LeaveandovertimeNewListApproval newInstance(String id, String name)
	{
		LeaveandovertimeNewListApproval newFragment = new LeaveandovertimeNewListApproval();
		Bundle bundle1 = new Bundle();
		bundle1.putString("userid", id);
		bundle1.putString("username", name);
		newFragment.setArguments(bundle1);
		return newFragment;

	}

	class ListTask extends AsyncTask<String, Integer, String>
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
		protected String doInBackground(String... params)
		{
			
			if (isCancelled())
				return null;

			//String[] ssStrings = new String[2];			
			
			String s = query.LeaveAndOvertimefinish(userid,params[0]);
			//ssStrings[1] = query.LeaveAndOvertimefinish(userid,1);
			
			return s;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if (result == null || result.equals("null")|| result.equals("")) 
			{
				Toast.makeText(getActivity(), "暂无数据",Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				getLeave(result, id1);
				showLeave(leave, but);
			}

			super.onPostExecute(result);
		}

	}
	
	public void getLeave(String result,String id[])
	{		
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("obj");
			//System.out.println(jsonArray.toString());
			
			
			for (int i = 0; i < jsonArray.length(); i++)
			{
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONArray leaArray=(JSONArray) jsonArray.opt(i);
				JSONObject jsonObject = (JSONObject) leaArray.opt(3);
				
					
				
				JSONObject jsonleaveOrOtKind=jsonObject.getJSONObject("leaveOrOtKind");				
				if (jsonleaveOrOtKind.getString("kindType").equals("1"))
				{
					leavemap.put("imgleft", R.drawable.leave_leave);
					leavemap.put("kind", jsonleaveOrOtKind.getString("kindType"));
				} 
				else if (jsonleaveOrOtKind.getString("kindType").equals("2"))
				{
					leavemap.put("imgleft", R.drawable.leave_overtime);
					leavemap.put("kind", jsonleaveOrOtKind.getString("kindType"));
				} else
				{
					leavemap.put("imgleft", R.drawable.leave_travel);
					leavemap.put("kind", jsonleaveOrOtKind.getString("kindType"));
				}

				String text1 = jsonObject.getString("leaveOrOtReason");
				JSONObject jsonleaveOrOtUser=jsonObject.getJSONObject("user");	
				String text2 = jsonleaveOrOtUser.getString("xm")
						+ " "
						+ jsonObject.getString("leaveOrOtDate");
				String text3 = jsonObject.getString("leaveOrOtID");
				id[0]=text3;

				if (jsonObject.getString("approvalResult").equals("待审批"))
				{
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult")
						.equals("正在审批"))
				{
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult").equals("驳回"))
				{
					leavemap.put("img", R.drawable.leave_status3);
				} else
				{
					leavemap.put("img", R.drawable.leave_status4);
				}
				
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
				

					leave.add(leavemap);
			}
		

		} catch (Exception e)
		{
			Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void showLeave(ArrayList<HashMap<String, Object>> data,Button button)
	{
		if(data.size()==0)
			return;
		 adapter.notifyDataSetChanged();		
	}
	
	class ExtSimpleAdapter extends SimpleAdapter
	{
		public ExtSimpleAdapter(Context context,List<? extends Map<String, ?>> data, int resource,String[] from, int[] to) 
		{
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			View v = super.getView(position, convertView, parent);
			TextView tv = (TextView) v.findViewById(R.id.leaveandovertime_newlist_approvalitem_name);
			Map<String, Object> dt = (Map<String, Object>) this.getItem(position);
			
			/*if ((Integer)dt.get("msgstatus") == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD 
				|| (Integer)dt.get("msgstatus") == PushMsg.MSGSTATUS_PUSHED_NOTREAD) {
				tv.setTextColor(Color.parseColor("#333333"));
			}
			else{
				tv.setTextColor(Color.parseColor("#999999"));
			}*/
			return v;
		}
	}

	class leaveOnitem1 implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);
			Intent intent = new Intent(getActivity(),
					LeaveandovertimeNewListShow.class);
			intent.putExtra("id", id2[0]); 
			intent.putExtra("kind", tv_kind.getText().toString()); 																	
			intent.putExtra("userid", userid);
			startActivity(intent);
		}
	}

	class leaveOnitem implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);
			Intent intent = new Intent(getActivity(),
					LeaveandovertimeNewListShow.class);
			intent.putExtra("id", tv_id.getText().toString()); 
			intent.putExtra("kind", tv_kind.getText().toString());
			intent.putExtra("userid", userid); 
			intent.putExtra("username", username); 
			intent.putExtra("mode", 0);
			startActivityForResult(intent, 100);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		switch (scrollState) 
		{
		case OnScrollListener.SCROLL_STATE_IDLE:
			if(lv.getLastVisiblePosition()==(lv.getCount()-1))
			{
				int t=(lv.getLastVisiblePosition()+1)<10?0:(lv.getLastVisiblePosition()+1);
				new ListTask(getActivity()).execute(String.valueOf(t/10+1));
			}
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) 
	{
		// TODO Auto-generated method stub
		
	}

}
