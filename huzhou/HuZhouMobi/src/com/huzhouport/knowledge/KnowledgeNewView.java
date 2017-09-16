package com.huzhouport.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.main.User;
import com.huzhouport.pushmsg.NotifyPushMsg;
import com.huzhouport.pushmsg.NotifyPushMsgFolder;
import com.huzhouport.pushmsg.NotifyPushMsgManger;
import com.huzhouport.pushmsg.PushMsg;

public class KnowledgeNewView extends Activity 
{
	private String departmentId;
	private ArrayList<HashMap<String, Object>> folderlist;
	private SimpleAdapter folderAdapter = null;
	private ArrayList<HashMap<String, Object>> msglist = null;
	private SimpleAdapter msgAdapter = null;
	private ListView departmentlv, userlv;
	private EditText searchtext; // 搜索框
	private String content;
	private User user;
	private List<NotifyPushMsg> msgs = null;
	private List<NotifyPushMsgFolder> folders = null;
	private boolean mode_search = false;
	private ImageButton readall;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.knowledge_newlist);

		TextView title = (TextView) findViewById(R.id.knowledge_new_title);
		title.setFocusable(true);

		Intent intent = getIntent();
		departmentId = intent.getStringExtra("departmentId");

		departmentlv = (ListView) findViewById(R.id.knowledge_new_viewlist);
		userlv = (ListView) findViewById(R.id.knowledge_new_viewlist1);
		user = (User) intent.getSerializableExtra("User");

		// 返回
		ImageButton back = (ImageButton) findViewById(R.id.knowledge_new_back);
		back.setOnClickListener(new Back());

		// 搜索的图标
		ImageButton search = (ImageButton) findViewById(R.id.knowledge_new_searchbutton);
		search.setOnClickListener(new Search());

		searchtext = (EditText) findViewById(R.id.knowledge_new_searchtextname);
		searchtext.addTextChangedListener(new TextWatcher() 
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				content = searchtext.getText().toString();
				if (content.length() == 0) {
					// departmentId = "-1";
					mode_search = false;
					//ListTask task = new ListTask(KnowledgeNewView.this, true); // 异步
					//task.execute();
				} else {

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		readall = (ImageButton) findViewById(R.id.readall);
		if(Integer.valueOf(departmentId)==-1)
		{
			readall.setVisibility(View.GONE);
		}else {
			readall.setVisibility(View.VISIBLE);
		}
		//
		readall.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						KnowledgeNewView.this);
				builder.setMessage("将所有通知公告标为已读?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								KnowledgeNewView.this.readall();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});

		// 图标
		ImageButton zhuye = (ImageButton) findViewById(R.id.knowledge_new_zhuye);
		zhuye.setOnClickListener(new Zhuye());
		// 获取显示列表信息
		//ListTask task = new ListTask(this, true); // 异步
		//task.execute();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {
			setResult(20);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class Zhuye implements View.OnClickListener {
		public void onClick(View v) {

			setResult(20);
			finish();

		}
	}

	public class Departmenttiemgone implements View.OnClickListener {
		public void onClick(View v) {

			if (departmentlv.getVisibility() == 0) {
				departmentlv.setVisibility(View.GONE);
			} else {
				departmentlv.setVisibility(View.VISIBLE);
			}

		}
	}

	public class Useritemgone implements View.OnClickListener {
		public void onClick(View v) {

			if (userlv.getVisibility() == 0) {
				userlv.setVisibility(View.GONE);
			} else {
				userlv.setVisibility(View.VISIBLE);
			}
		}
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	public class Search implements View.OnClickListener 
	{
		public void onClick(View v) {
			content = searchtext.getText().toString();
			if (content.length() == 0) {
				Toast.makeText(KnowledgeNewView.this, "请先在搜索框里输入内容",
						Toast.LENGTH_SHORT).show();
			} else {
				mode_search = true;
				SearchListTask task = new SearchListTask(KnowledgeNewView.this); // 异步
				task.execute();
			}

		}
	}

	public class SearchButton implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(KnowledgeNewView.this,
					KnowledgeNewView.class);
			intent.putExtra("name", searchtext.getText().toString());
			startActivity(intent);

		}
	}

	// 非搜索,正常加载
	class ListTask extends AsyncTask<String, Integer, Object[]> 
	{
		ProgressDialog pDialog = null;

		@SuppressWarnings("deprecation")
		public ListTask(Context context, boolean showdialog) 
		{
			if (showdialog == true) {
				pDialog = new ProgressDialog(KnowledgeNewView.this);
				pDialog.setTitle("提示");
				pDialog.setMessage("正在加载中，请稍候。。。");
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
		}

		@Override
		protected Object[] doInBackground(String... params) 
		{
			if (isCancelled())
				return null;// 取消异步

			Object[] result = new Object[2];
			List<NotifyPushMsgFolder> folders = new NotifyPushMsgManger(getApplicationContext()).getFolders(user,
					Integer.parseInt(departmentId));
			List<NotifyPushMsg> msgs = new NotifyPushMsgManger(
					getApplicationContext()).getFolderNotify(user,
					Integer.parseInt(departmentId));
			result[0] = folders;
			result[1] = msgs;
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object[] result) 
		{
			if (pDialog != null)
				pDialog.dismiss();
			folders = (List<NotifyPushMsgFolder>) result[0];
			msgs = (List<NotifyPushMsg>) result[1];

			if (folders.size() == 0 && msgs.size() == 0)
				Toast.makeText(KnowledgeNewView.this, "暂无数据",
						Toast.LENGTH_SHORT).show();
			else {
				showfolders(folders);
				showmsgs(msgs);
			}
			super.onPostExecute(result);
		}

	}

	public void showfolders(List<NotifyPushMsgFolder> folders) 
	{
		if (folderlist == null)
			folderlist = new ArrayList<HashMap<String, Object>>();
		else
			folderlist.clear();

		if (folders.size() == 0) {
			findViewById(R.id.knowledge_new_viewlist).setVisibility(View.GONE);
		} else {
			findViewById(R.id.knowledge_new_viewlist).setVisibility(
					View.VISIBLE);
			for (int i = 0; i < folders.size(); i++) {
				NotifyPushMsgFolder folder = folders.get(i);

				HashMap<String, Object> departmentlistmap = new HashMap<String, Object>();
				String name = folder.getName();
				String id = "" + folder.getId();
				departmentlistmap.put("name", name);
				departmentlistmap.put("id", id);

				if (folder.isHasunreadmsg())
					departmentlistmap.put("ifnew", "*");
				else
					departmentlistmap.put("ifnew", " ");

				folderlist.add(departmentlistmap);
			}
		}

		if (folderlist != null) {
			if (folderAdapter == null) {
				folderAdapter = new FolderSimpleAdapter(KnowledgeNewView.this,
						folderlist, R.layout.knowledge_departmentitem,
						new String[] { "ifnew", "name", "id" }, new int[] {
								R.id.knowledge_ifnew,
								R.id.knowledge_departmentitem_name,
								R.id.knowledge_departmentitem_id });
				departmentlv.setAdapter(folderAdapter);
				departmentlv.setOnItemClickListener(new folderitemClick());
			} else {
				folderAdapter.notifyDataSetChanged();
			}
		}
	}

	class folderitemClick implements OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.knowledge_departmentitem_id);
			Intent intent = new Intent(KnowledgeNewView.this,
					KnowledgeNewView.class);
			intent.putExtra("departmentId", tv_id.getText().toString());
			intent.putExtra("User", user);
			if (departmentId.equals("-1")) 
			{
				startActivity(intent);
			} 
			else 
			{
				startActivityForResult(intent, 100);
			}

		}

	}

	public void readall() {
		new NotifyPushMsgManger(getApplicationContext()).readall(user);
		//if (mode_search == false)
			//new ListTask(KnowledgeNewView.this, false).execute();
	}

	class msgitemclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			TextView knowledgeIDtext = (TextView) arg1
					.findViewById(R.id.knowledge_knowledgeID);
			String knowledgeID = knowledgeIDtext.getText().toString();

			// 取得这条消息的详情
			NotifyPushMsg msg = msgs.get(arg2);
			if (knowledgeID.equals("" + msg.getMessageid())) {
				// 如果消息非面向用户
				new NotifyPushMsgManger(getApplicationContext()).readNotifyMsg(
						user, msg);
			}

			TextView link = (TextView) arg1.findViewById(R.id.knowledge_isLink);
			String islink = link.getText().toString();
			if (islink.equals("1")) {
				Intent intent = new Intent(KnowledgeNewView.this,
						KnowledgeShow.class);
				intent.putExtra("knowledgeID", knowledgeID);
				startActivity(intent);
				if (user != null) {
					new Log(user.getName(), "查看通知公告", GlobalVar.LOGSEE, "")
							.execute();
				}
			} else {
				Intent intent = new Intent(KnowledgeNewView.this,
						KnowledgetextShow.class);
				intent.putExtra("knowledgeID", knowledgeID);
				startActivity(intent);
				if (user != null) {
					new Log(user.getName(), "查看通知公告", GlobalVar.LOGSEE, "")
							.execute();
				}
			}
		}

	}

	public void showmsgs(List<NotifyPushMsg> msgs)
	{
		if (msglist == null)
			msglist = new ArrayList<HashMap<String, Object>>();
		else
			msglist.clear();
		if (msgs.size() == 0) 
		{
			findViewById(R.id.knowledge_new_viewlist1).setVisibility(View.GONE);
		}
		else 
		{
			findViewById(R.id.knowledge_new_viewlist1).setVisibility(View.VISIBLE);
			for (int i = 0; i < msgs.size(); i++) 
			{
				NotifyPushMsg msg = msgs.get(i);

				HashMap<String, Object> userlistmap = new HashMap<String, Object>();

				String name = msg.getKnowledgename();
				String name1 = "";
				if (msg.getKnowledgeindex() == null
						|| msg.getKnowledgeindex().equals("null")) {
					name1 = "无";
				} else {
					name1 = msg.getKnowledgeindex();
				}

				String id = "" + msg.getKnowledgeid();
				String id1 = "" + msg.getIslink();
				userlistmap.put("name", name);
				userlistmap.put("name1", name1);
				userlistmap.put("id", id);
				userlistmap.put("id1", id1);

				if (msg.getMsgstatus() == PushMsg.MSGSTATUS_PUSHED_NOTREAD
						|| msg.getMsgstatus() == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD)
					userlistmap.put("ifnew", "*");
				else
					userlistmap.put("ifnew", " ");
				msglist.add(userlistmap);
			}
		}

		if (msgAdapter == null) {
			msgAdapter = new ItemSimpleAdapter(KnowledgeNewView.this, msglist,
					R.layout.knowledge_listitem, new String[] { "ifnew",
							"name", "name1", "id", "id1", "time" }, new int[] {
							R.id.knowledge_ifnew, R.id.knowledge_knowledgeName,
							R.id.knowledge_knowledgeIndex,
							R.id.knowledge_knowledgeID, R.id.knowledge_isLink,
							R.id.knowledge_knowledgetime });
			userlv.setAdapter(msgAdapter);
			userlv.setOnItemClickListener(new msgitemclick());
		} else {
			msgAdapter.notifyDataSetChanged();
		}
	}

	public class KnowledgeBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//if (mode_search == false)
				//new ListTask(KnowledgeNewView.this, false).execute();
		}

	}

	private KnowledgeBroadcastReceiver knowledgeBroadcastReceiver = null;

	@Override
	protected void onResume() {

		IntentFilter filter = new IntentFilter();
		if (knowledgeBroadcastReceiver == null)
			knowledgeBroadcastReceiver = new KnowledgeBroadcastReceiver();

		filter.addAction(GlobalVar.PUSHMSGBROADCAST);
		registerReceiver(knowledgeBroadcastReceiver, filter);
		if (mode_search == false)
			new ListTask(this, true).execute();
		else
		{
			SearchListTask task = new SearchListTask(KnowledgeNewView.this); // 异步
			task.execute();
		}
		

		super.onResume();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		unregisterReceiver(knowledgeBroadcastReceiver);
	}

	class SearchListTask extends AsyncTask<String, Integer, Object> {
		ProgressDialog pDialog = null;

		public SearchListTask() {

		}

		@SuppressWarnings("deprecation")
		public SearchListTask(Context context) {
			pDialog = new ProgressDialog(KnowledgeNewView.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (SearchListTask.this != null
							&& SearchListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						SearchListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (SearchListTask.this != null
							&& SearchListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						SearchListTask.this.cancel(true);

				}
			});
			pDialog.show();

		}

		@Override
		protected Object doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步
			List<NotifyPushMsg> msgs = new NotifyPushMsgManger(
					KnowledgeNewView.this).searchPushMsgs(user, -1, content);
			return msgs;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			if (pDialog != null)
				pDialog.dismiss();
			if (msglist != null)
				msglist.clear();
			msgs = (List<NotifyPushMsg>) result;

			if (msgs.size() == 0) {
				Toast.makeText(KnowledgeNewView.this, "暂无数据",
						Toast.LENGTH_SHORT).show();
			} else {
				findViewById(R.id.knowledge_new_viewlist).setVisibility(
						View.GONE);
				showmsgs(msgs);
			}
			super.onPostExecute(result);
		}

	}

	public class FolderSimpleAdapter extends SimpleAdapter {

		public FolderSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			@SuppressWarnings("unchecked")
			Map<String, ?> map = (Map<String, ?>) getItem(position);

			View v = super.getView(position, convertView, parent);
			Drawable drawable = null;
			String ifnew = (String) map.get("ifnew");
			if (ifnew.equals("*")) {
				drawable = getResources().getDrawable(R.drawable.folderunread);
			} else {
				drawable = getResources().getDrawable(R.drawable.knowledge_img);
			}
			// / 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			TextView tv = (TextView) v
					.findViewById(R.id.knowledge_departmentitem_name);
			tv.setCompoundDrawables(drawable, null, null, null);

			TextView tvifnew = (TextView) v.findViewById(R.id.knowledge_ifnew);
			tvifnew.setText("");
			return v;
		}
	}

	public class ItemSimpleAdapter extends SimpleAdapter {

		public ItemSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			@SuppressWarnings("unchecked")
			Map<String, ?> map = (Map<String, ?>) getItem(position);

			View v = super.getView(position, convertView, parent);
			ImageButton tv = (ImageButton) v
					.findViewById(R.id.knowledge_itembutton);

			String ifnew = (String) map.get("ifnew");
			if (ifnew.equals("*")) {
				tv.setBackgroundResource(R.drawable.fileunread);
			} else {
				tv.setBackgroundResource(R.drawable.knowledge_text);
			}

			TextView tvifnew = (TextView) v.findViewById(R.id.knowledge_ifnew);
			tvifnew.setText("");
			return v;
		}
	}
}
