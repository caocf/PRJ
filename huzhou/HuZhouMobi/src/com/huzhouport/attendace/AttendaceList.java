package com.huzhouport.attendace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

@SuppressLint("ShowToast")
public class AttendaceList extends Activity
{

	private Query query = new Query();
	private User user;
	@SuppressWarnings("unused")
	private String userId, userName;
	private ImageButton att_back, att_inbu, att_backbu;

	@SuppressWarnings("unused")
	private TextView att_title, att_list_sign, att_list_location,
			att_list_time, att_list_user;
	private ListView att_list;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();;
	private int cpage = 1, maxpage;

	private SimpleAdapter adapter;
	private View moreView;
	private String reviewWether;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendace_sign_list);

		att_title = (TextView) findViewById(R.id.attendace_sign_listview_titleText);
		att_back = (ImageButton) findViewById(R.id.attendace_sign_listview_back);
		// ims = (ImageButton) findViewById(R.id.attendace_list_search);
		att_list = (ListView) findViewById(R.id.attendace_sign_listview);
		att_inbu = (ImageButton) findViewById(R.id.attendace_button_signin);
		att_backbu = (ImageButton) findViewById(R.id.attendace_button_signback);
		// att_loadmore = (Button) findViewById(R.id.loadMoreButton);
		// att_list_sign = (TextView)
		// findViewById(R.id.attendace_sign_list_sname);
		att_list_location = (TextView) findViewById(R.id.attendace_sign_list_slocation);
		att_list_time = (TextView) findViewById(R.id.attendace_sign_list_stime);
		att_list_user = (TextView) findViewById(R.id.attendace_sign_list_suser);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		att_back.setOnClickListener(new MyBack());
		att_inbu.setOnClickListener(new SignIn());
		att_backbu.setOnClickListener(new SignBack());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		att_list.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		// att_loadmore.setOnClickListener(new AddMoreList());

		String date = "queryCondition.listKeyValuePair[0].key=signUser&queryCondition.listKeyValuePair[0].pair==&queryCondition.listKeyValuePair[0].value="
				+ user.getUserId()
				+ "&signStatus=2&queryCondition.orderByFielName=s.signTime&queryCondition.sequence=desc&cpage="
				+ cpage;
		new ListTask(this).execute(date);

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

	public void creatListView(String result)
	{

		// String result = query.ShowAttendaceList(date);
		JSONTokener jsonParser = new JSONTokener(result);
		// JSONObject title = (JSONObject) jsonParser.nextValue();
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONArray jsonArray2 = (JSONArray) jsonArray.get(i);
				JSONObject jsonObject = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(1);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(2);
				Map<String, Object> map = new HashMap<String, Object>();
				/*
				 * String
				 * valueStr=changeSignStatus(jsonObject.get("signStatus").
				 * toString()); map.put("signStatus", valueStr);
				 */
				reviewWether = jsonObject.get("signStatus").toString();
				if (reviewWether.equals("0"))
					map.put("img", R.drawable.signinbutton);
				else if (reviewWether.equals("1"))
					map.put("img", R.drawable.signbackbutton);
				String valueString = changeYMDHMS(
						jsonObject.get("signTime").toString()).substring(0, 16);
				map.put("signTime", valueString);
				map.put("locationName", jsonObject2.getString("locationName"));
				map.put("name", jsonObject1.getString("name"));
				map.put("signID", jsonObject.getString("signID"));
				listv.add(map);
			}
			adapter = new SimpleAdapter(AttendaceList.this, listv,
					R.layout.attendace_sign_list_text, new String[] { "img",
							"signTime", "locationName", "name" }, new int[] {
							R.id.schedule_addImage1,
							R.id.attendace_sign_list_stime,
							R.id.attendace_sign_list_slocation,
							R.id.attendace_sign_list_suser, });
			if (cpage == 1)
			{
				att_list.setAdapter(adapter);
			}
			adapter.notifyDataSetChanged();
			moreView.setVisibility(View.VISIBLE);
			att_list.setOnItemClickListener(new AttendaceListListener());
			att_list.setOnScrollListener(new OnScrollListener()
			{

				public void onScrollStateChanged(AbsListView view,
						int scrollState)
				{
					// 当不滚动时
					// if(is_page&&scrollState ==
					// OnScrollListener.SCROLL_STATE_IDLE){
					//
					// //判断滚动到底部
					// //
					// if(view.getLastVisiblePosition()==(view.getCount()-1)){
					// // //然后 经行一些业务操作
					// // if(cpage<10)
					// // {
					// // att_loadmore.setVisibility(View.VISIBLE);
					// // }
					// // }
					// // else{
					// // att_loadmore.setVisibility(View.GONE);
					// // }
					// ////
					// if(view.getLastVisiblePosition()==(view.getCount()+1)){
					// //// att_loadmore.setVisibility(View.GONE);
					// //// }
					// att_loadmore.setVisibility(View.VISIBLE);
					// cpage += 1;
					// String date =
					// "queryCondition.listKeyValuePair[0].key=signUser&queryCondition.listKeyValuePair[0].pair==&queryCondition.listKeyValuePair[0].value="+user.getUserId()+"&signStatus=2&queryCondition.orderByFielName=s.signTime&queryCondition.sequence=desc&cpage="
					// + cpage;
					// new ListTaskList(AttendaceList.this).execute(date);
					// //Toast.makeText(PortDynmicNewsMain.this,
					// "相关数据...",Toast.LENGTH_SHORT).show();
					// }
					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
					{

						// // 判断滚动到底部
						if (view.getLastVisiblePosition() == (view.getCount() - 1))
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
								String date = "queryCondition.listKeyValuePair[0].key=signUser&queryCondition.listKeyValuePair[0].pair==&queryCondition.listKeyValuePair[0].value="
										+ user.getUserId()
										+ "&signStatus=2&queryCondition.orderByFielName=s.signTime&queryCondition.sequence=desc&cpage="
										+ cpage;
								new ListTaskList().execute(date);
							}
						}
					}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount)
				{
					// is_page=(firstVisibleItem+visibleItemCount==totalItemCount);
				}
			});
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String changeSignStatus(String value)
	{
		String sRet = "";
		if (null != value && value.equals("1"))
		{
			sRet = value.replace("1", "签退");

		} else if (null != value && value.equals("0"))
		{
			sRet = value.replace("0", "签到");
		}
		return sRet;
	}

	public static String changeYMDHMS(String value)
	{
		String sRet = "";
		if (null != value)
		{
			return value.replace("T", " ");
		}
		return sRet;
	}

	class AttendaceListListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			/* if(){ */
			Intent intent = new Intent(AttendaceList.this, AttendaceInSee.class);
			intent.putExtra("User", user);
			intent.putExtra("signID", listv.get(arg2).get("signID").toString());
			startActivityForResult(intent, 100);

			if(user!=null)
			{
			Log log = new Log(user.getName(), "查看签到签退", GlobalVar.LOGSEE, ""); // 日志
			log.execute();
			}

			/*
			 * }else{
			 * 
			 * }
			 */
		}
	}

	class SignBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(AttendaceList.this,
					AttendaceSignBackMap.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
		}
	}

	class SignIn implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// signStatusInfo=listv.get(0).get("signStatus")
			// .toString();
			// // if(signStatusInfo){
			// //
			// // }
			Intent intent = new Intent(AttendaceList.this,
					AttendaceSignIn.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
		}
	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// Intent intent=new
			// Intent(AttendaceList.this,MobileOfficeFragment.class);
			// startActivity(intent);

			finish();
		}
	}

	class AddMoreList implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			cpage += 1;
			String date = "queryCondition.listKeyValuePair[0].key=signUser&queryCondition.listKeyValuePair[0].pair==&queryCondition.listKeyValuePair[0].value="
					+ user.getUserId()
					+ "&signStatus=2&queryCondition.orderByFielName=s.signTime&queryCondition.sequence=desc&cpage="
					+ cpage;
			new ListTask(AttendaceList.this).execute(date);
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
			String result;
			result = query.ShowAttendaceList(params[0]);
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
				att_list.removeFooterView(moreView); // 移除底部视图
				Toast.makeText(AttendaceList.this, "已加载全部数据", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}
	}

	class ListTaskList extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		@Override
		protected String doInBackground(String... params)
		{
			String result;
			result = query.ShowAttendaceList(params[0]);
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
				att_list.removeFooterView(moreView); // 移除底部视图
				Toast.makeText(AttendaceList.this, "已加载全部数据", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}
	}

}
