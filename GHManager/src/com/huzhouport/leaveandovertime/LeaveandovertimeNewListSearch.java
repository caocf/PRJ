package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;

import net.hxkg.ghmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LeaveandovertimeNewListSearch extends Activity {
	private ListView lv, lv1;
	private Button but, but1;
	private String userid, username;
	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> leave;
	private ArrayList<HashMap<String, Object>> leave1;
	private EditText searchtext; // ������
	private ImageButton img_searchbutton, img_searchback;
	private String content;
	private int l1, l2;
	private String searchid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.leaveandovertime_newlist_search);

		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		username = intent.getStringExtra("username");
		searchid = intent.getStringExtra("searchid");

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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (20 == resultCode) {
			setResult(20);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			content = searchtext.getText().toString();
			if (content.length() == 0) {
				Toast.makeText(LeaveandovertimeNewListSearch.this,
						"����������������������", Toast.LENGTH_SHORT).show();
			} else {
				if (searchid.equals("0")) {
					ListTask task = new ListTask(
							LeaveandovertimeNewListSearch.this); // �첽
					task.execute();
				} else if (searchid.equals("1")) {
					ListTaskApply task = new ListTaskApply(
							LeaveandovertimeNewListSearch.this);
					task.execute();
				} else {
					ListTaskAll task = new ListTaskAll(
							LeaveandovertimeNewListSearch.this);
					task.execute();
				}

			}

		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;



		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			pDialog = new ProgressDialog(LeaveandovertimeNewListSearch.this);
			//pDialog.setTitle("��ʾ");
			pDialog.setMessage("搜索中");
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
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

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
			if (isCancelled())
				return null;

			String result = query
					.FindAttedanceByPermission_APP(userid, content);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			System.out.println(result);
			if (result == null || result.equals("null")) {
				Toast.makeText(LeaveandovertimeNewListSearch.this, "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else {
				getLeave(result);
				showLeave();
				showLeave1();
				super.onPostExecute(result);
			}
		}

	}

	public void getLeave(String result) {

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			leave = new ArrayList<HashMap<String, Object>>();
			leave1 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONArray ja = (JSONArray) jsonArray.opt(i);
				JSONObject jo0 = (JSONObject) ja.opt(0);
				JSONObject jo1 = (JSONObject) ja.opt(1);
				JSONObject jo2 = (JSONObject) ja.opt(2);

				if (jo1.getInt("kindType") == 1) {
					leavemap.put("imgleft", R.drawable.leave_leave);
					leavemap.put("kind", jo1.getInt("kindType"));
				} else if (jo1.getInt("kindType") == 2) {
					leavemap.put("imgleft", R.drawable.leave_overtime);
					leavemap.put("kind", jo1.getInt("kindType"));
				} else {
					leavemap.put("imgleft", R.drawable.leave_travel);
					leavemap.put("kind", jo1.getInt("kindType"));
				}

				String text1 = jo0.getString("leaveOrOtReason");
				String text2 = jo2.getString("name")
						+ " "
						+ jo0.getString("leaveOrOtDate").substring(0,
								jo0.getString("beginDate").lastIndexOf(":"));
				String text3 = jo0.getString("leaveOrOtID");
				String text4 = jo0.getString("approvalID1");
				if (jo0.getInt("approvalResult") == 1) {
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jo0.getInt("approvalResult") == 2) {
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jo0.getInt("approvalResult") == 3) {
					leavemap.put("img", R.drawable.leave_status3);
				} else {
					leavemap.put("img", R.drawable.leave_status4);
				}

				if (jo0.getInt("approvalResult") == 1
						|| jo0.getInt("approvalResult") == 2) {
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leavemap.put("text4", text4);
					leave.add(leavemap);
				} else {
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leavemap.put("text4", text4);
					leave1.add(leavemap);
				}

			}

		} catch (Exception e) {
			Toast.makeText(LeaveandovertimeNewListSearch.this, "暂无数据",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void showLeave() {
		if (leave.size() == 0) {
			l1 = 0;
			but.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
		} else {
			l1 = 1;
			but.setVisibility(View.VISIBLE);
			lv.setVisibility(View.VISIBLE);
			SimpleAdapter adapter = new SimpleAdapter(
					LeaveandovertimeNewListSearch.this, leave,
					R.layout.leaveandovertime_newlist_approvalitem,
					new String[] { "imgleft", "kind", "img", "text1", "text2",
							"text3", "text4" }, new int[] {
							R.id.leaveandovertime_newlist_approvalitem_leftimg,
							R.id.leaveandovertime_newlist_approvalitem_kind,
							R.id.leaveandovertime_newlist_approvalitem_img,
							R.id.leaveandovertime_newlist_approvalitem_name,
							R.id.leaveandovertime_newlist_approvalitem_name1,
							R.id.leaveandovertime_newlist_approvalitem_id,
							R.id.leaveandovertime_newlist_approvalitem_userid });
			lv.setAdapter(adapter);
			if (searchid.equals("0")) {
				lv.setOnItemClickListener(new leaveOnitem());
			} else if (searchid.equals("1")) {
				lv.setOnItemClickListener(new leaveOnitem1());
			} else {
				lv.setOnItemClickListener(new leaveOnitemAll());
			}
		}
	}

	public void showLeave1() {
		if (leave1.size() == 0) {
			l2 = 0;
			but1.setVisibility(View.GONE);
			lv1.setVisibility(View.GONE);
		} else {
			l2 = 1;
			but1.setVisibility(View.VISIBLE);
			lv1.setVisibility(View.VISIBLE);

			SimpleAdapter adapter = new SimpleAdapter(
					LeaveandovertimeNewListSearch.this, leave1,
					R.layout.leaveandovertime_newlist_approvalitem,
					new String[] { "imgleft", "kind", "img", "text1", "text2",
							"text3", "text4" }, new int[] {
							R.id.leaveandovertime_newlist_approvalitem_leftimg,
							R.id.leaveandovertime_newlist_approvalitem_kind,
							R.id.leaveandovertime_newlist_approvalitem_img,
							R.id.leaveandovertime_newlist_approvalitem_name,
							R.id.leaveandovertime_newlist_approvalitem_name1,
							R.id.leaveandovertime_newlist_approvalitem_id,
							R.id.leaveandovertime_newlist_approvalitem_userid });
			lv1.setAdapter(adapter);
			lv1.setOnItemClickListener(new leaveOnitem1());
		}
		int s = l1 + l2;
		if (s == 0) {
			Toast.makeText(LeaveandovertimeNewListSearch.this, "û���ҵ��������",
					Toast.LENGTH_SHORT).show();
		}
	}

	class leaveOnitem1 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);
			Intent intent = new Intent(LeaveandovertimeNewListSearch.this,
					LeaveandovertimeNewListView.class);
			intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
			intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ� 1���
																	// 2�Ӱ�
			intent.putExtra("userid", userid); // �û�id
			startActivity(intent);


		}

	}

	class leaveOnitem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);
			Intent intent = new Intent(LeaveandovertimeNewListSearch.this,
					LeaveandovertimeNewListShow.class);
			intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
			intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ� 1���
																	// 2�Ӱ�
			intent.putExtra("userid", userid); // �û�id
			intent.putExtra("username", username); // �û�id
			startActivityForResult(intent, 100);

		}

	}



	class ListTaskApply extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTaskApply() {

		}

		@SuppressWarnings("deprecation")
		public ListTaskApply(Context context) {
			pDialog = new ProgressDialog(LeaveandovertimeNewListSearch.this);
			pDialog.setTitle("��ʾ");
			pDialog.setMessage("���ڼ����У����Ժ򡣡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTaskApply.this != null
							&& ListTaskApply.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTaskApply.this.cancel(true);

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTaskApply.this != null
							&& ListTaskApply.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTaskApply.this.cancel(true);

				}
			});
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			if (isCancelled())
				return null;// ȡ���첽

			return query.selectLeaveAndOvertiemALLbymy(userid, content);
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if (result == null || result.equals("null")) {
				Toast.makeText(LeaveandovertimeNewListSearch.this, "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else {
				getLeaveApply(result);// �������
				showLeave();
				showLeave1();
			}
			super.onPostExecute(result);
		}

	}

	public void getLeaveApply(String result) {
		System.out.println("result===" + result);

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("leavelist");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			leave = new ArrayList<HashMap<String, Object>>();
			leave1 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> leavemap = new HashMap<String, Object>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				if (jsonObject.getString("kindType").equals("1")) {
					leavemap.put("imgleft", R.drawable.leave_leave);
					leavemap.put("kind", jsonObject.getString("kindType"));
				} else if (jsonObject.getString("kindType").equals("2")) {
					leavemap.put("imgleft", R.drawable.leave_overtime);
					leavemap.put("kind", jsonObject.getString("kindType"));
				} else {
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
				String text4 = jsonObject.getString("approvalID");
				if (jsonObject.getString("approvalResult").equals("������")) {
					System.out.println("jsonArray===1");
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult")
						.equals("��������")) {
					leavemap.put("img", R.drawable.leave_status1);
				} else if (jsonObject.getString("approvalResult").equals("����")) {
					leavemap.put("img", R.drawable.leave_status3);
				} else {
					leavemap.put("img", R.drawable.leave_status4);
				}

				if (jsonObject.getString("approvalResult").equals("������")
						|| jsonObject.getString("approvalResult")
								.equals("��������")) {
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leavemap.put("text4", text4);
					leave.add(leavemap);
				} else {
					leavemap.put("text1", text1);
					leavemap.put("text2", text2);
					leavemap.put("text3", text3);
					leavemap.put("text4", text4);
					leave1.add(leavemap);
				}

			}

		} catch (Exception e) {
			Toast.makeText(LeaveandovertimeNewListSearch.this, "�����쳣",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	class ListTaskAll extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTaskAll() {

		}

		@SuppressWarnings("deprecation")
		public ListTaskAll(Context context) {
			pDialog = new ProgressDialog(LeaveandovertimeNewListSearch.this);
			pDialog.setTitle("��ʾ");
			pDialog.setMessage("���ڼ����У����Ժ򡣡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTaskAll.this != null
							&& ListTaskAll.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTaskAll.this.cancel(true);

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTaskAll.this != null
							&& ListTaskAll.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTaskAll.this.cancel(true);

				}
			});
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			if (isCancelled())
				return null;// ȡ���첽

			return query.selectShowleaveALL(content);
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if (result == null || result.equals("null")) {
				Toast.makeText(LeaveandovertimeNewListSearch.this, "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else {
				getLeaveApply(result);// �������
				showLeave();
				showLeave1();
			}
			super.onPostExecute(result);
		}

	}

	class leaveOnitemAll implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_id);
			TextView tv_userid = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_userid);
			TextView tv_kind = (TextView) arg1
					.findViewById(R.id.leaveandovertime_newlist_approvalitem_kind);

			System.out.println("tv_userid==" + tv_userid.getText().toString());
			if (tv_userid.getText().toString().equals(userid)) {
				Intent intent = new Intent(LeaveandovertimeNewListSearch.this,
						LeaveandovertimeNewListShow.class);
				intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
				intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ�
																		// 1���
																		// 2�Ӱ�
				intent.putExtra("userid", userid); // �û�id
				intent.putExtra("username", username); // �û�id
				startActivityForResult(intent, 100);


			} else {
				Intent intent = new Intent(LeaveandovertimeNewListSearch.this,
						LeaveandovertimeNewListView.class);
				intent.putExtra("id", tv_id.getText().toString()); // ��ټ�¼���
				intent.putExtra("kind", tv_kind.getText().toString()); // ��ٻ��߼Ӱ�
																		// 1���
																		// 2�Ӱ�
				intent.putExtra("userid", userid); // �û�id
				startActivity(intent);

			}

		}

	}
}
