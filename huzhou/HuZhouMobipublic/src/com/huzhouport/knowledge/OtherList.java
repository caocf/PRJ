package com.huzhouport.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ShowToast")
public class OtherList extends Activity {
	private Query query = new Query();
	private User user;
	//private String userId, userName;
	private ImageButton o_back;
//	private TextView  o_shipname,o_description,o_alarmLevel,o_title;
	private ListView o_list;
	private List<Map<String, Object>> listv;
	public String shipName;
	//private Button o_loadmore;
	private int cpage = 1, maxpage;
	private String param;// ����
	//private ClearEditText searchtext; // ������
	//private String queryCondition = "";// ����������
	private View moreView; // ���ظ���ҳ��
	private SimpleAdapter adapter;
	private String reviewWether;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_list);

		//o_title = (TextView) findViewById(R.id.other_lsit_title);
		o_back = (ImageButton) findViewById(R.id.other_lsit_back);
		o_list = (ListView) findViewById(R.id.other_list_listview);
		//o_shipname = (TextView) findViewById(R.id.other_list_text_shipname);
		//o_description = (TextView) findViewById(R.id.other_list_text_description);
		//o_alarmLevel = (TextView) findViewById(R.id.other_list_text_alarmLevel);
		// elt_list_pier = (TextView)
		// findViewById(R.id.electricreport_list_text_pier);
		// ele_loadmore = (Button) findViewById(R.id.elereportloadMoreButton);
		
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		
	
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		o_list.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ���
		param = "shipName="+user.getShipBindingList().get(user.getBindnum()).getShipNum();
		System.out.println("vv"+param);
		
		o_back.setOnClickListener(new MyBack());
		
		// elt_searchadd.setOnClickListener(new MyAdd());
		// ele_loadmore.setOnClickListener(new AddMoreList());

		ListTask task = new ListTask(this); // �첽
		task.execute();
	
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //���Ը��ݶ���������������Ӧ�Ĳ���
            if(18==resultCode)
            {
         	   finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
    }
	// �б�
	public void creatListView(String result) {
		System.out.println("ssss"+result);
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			listv = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("description", jsonObject.get("description"));
				map.put("filterName", jsonObject.get("filterName"));
				//map.put("alarmLevel", jsonObject.get("alarmLevel"));
				reviewWether = jsonObject.getString("alarmLevel");
				if (reviewWether.equals("0"))
					map.put("alarmLevel", "����");
				else if (reviewWether.equals("1"))
					map.put("alarmLevel", "����");
				else if (reviewWether.equals("2"))
					map.put("alarmLevel", "�澯");
				listv.add(map);
			}
			adapter = new SimpleAdapter(OtherList.this, listv,
					R.layout.other_list_text,
					new String[] { "shipName", "reportTime",
							"estimatedTimeOfArrival" }, new int[] {
							R.id.electricreport_list_text_name,
							R.id.electricreport_list_text_type,
							R.id.electricreport_list_text_arrival });

			o_list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			moreView.setVisibility(View.VISIBLE);
			o_list.setOnItemClickListener(new ElectricReportListener());
			// if (cpage < maxpage) {
			// ele_loadmore.setVisibility(View.VISIBLE);
			// }
			// else ele_loadmore.setVisibility(View.GONE);
//			o_list.setOnScrollListener(new OnScrollListener() {
//
//				public void onScrollStateChanged(AbsListView view,
//						int scrollState) {
//					// ��������ʱ
//					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//
//						// �жϹ������ײ�
//						if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//							// Ȼ�� ����һЩҵ�����
//							if (cpage < maxpage) {
//								moreView.findViewById(R.id.progressBar2)
//										.setVisibility(View.VISIBLE);
//								((TextView) moreView
//										.findViewById(R.id.loadmore_text))
//										.setText(R.string.more);
//								cpage += 1;
//								String date = "queryCondition.orderByFielName=e.reportID&queryCondition.sequence=desc&cpage="
//										+ cpage;
//								new ListTask(OtherList.this)
//										.execute(date);
//							}
//						}
//					}
//
//				}
//
//				@Override
//				public void onScroll(AbsListView view, int firstVisibleItem,
//						int visibleItemCount, int totalItemCount) {
//				}
//			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String changeYMDHMS(String value) {
		String sRet = "";
		if (null != value) {
			return value.replace("T", " ");
		}
		return sRet;
	}

	class ElectricReportListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			Intent intent = new Intent(OtherList.this,
//					ElectricReportSee.class);
//			intent.putExtra("User", user);
//			intent.putExtra("reportID", listv.get(arg2).get("reportID")
//					.toString());
//			startActivityForResult(intent, 100);
		}
	}
	

	// ����
	class MyBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			finish();
		}
	}

	// ����
	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
//			findViewById(R.id.electricreport_table2)
//					.setVisibility(View.VISIBLE);
//			Intent intent = new Intent(OtherList.this,
//					ElectricReportSearch.class);
//			intent.putExtra("User", user);
//			startActivityForResult(intent, 100);
		}
	}

	

	

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			// pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���",
			// true);
			pDialog = new ProgressDialog(OtherList.this);
			pDialog.setTitle("��ʾ");
			pDialog.setMessage("�������ڼ����У����Ժ򡤡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
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
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = query.ShowOther(param);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			creatListView(result);
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage) {
				moreView.setVisibility(View.GONE);
				o_list.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				//Toast.makeText(ElectricReportList.this, "�Ѽ���ȫ������", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}
	}

//	class ListTaskList extends AsyncTask<String, Integer, String> {
//		ProgressDialog pDialog = null;
//
//		public ListTaskList() {
//
//		}
//
//		public ListTaskList(Context context) {
//			// pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���",
//			// true);
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			String result = null;
//			try {
//				result = query.ShowOther(param);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			if (pDialog != null)
//				pDialog.dismiss();
//			creatListView(result);
//			if (adapter != null) {
//				adapter.notifyDataSetChanged();
//			}
//			if (cpage >= maxpage) {
//				moreView.setVisibility(View.GONE);
//				o_list.removeFooterView(moreView); // �Ƴ��ײ���ͼ
//				Toast.makeText(OtherList.this, "�Ѽ���ȫ������", 3000).show();
//			}
//			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
//			((TextView) moreView.findViewById(R.id.loadmore_text))
//					.setText(R.string.moreing);
//			super.onPostExecute(result);
//		}
//	}

	
}
