package com.huzhouport.illegal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.common.util.SharedPreferencesClass;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class IlleageSelectUser extends Activity
{

	private Query								query		= new Query();
	private String								departmentId;
	private int									userid;						// �û�id
	private ArrayList<HashMap<String, Object>>	departmentlist= new ArrayList<HashMap<String, Object>>();;
	private ArrayList<HashMap<String, Object>>	userlist= new ArrayList<HashMap<String, Object>>();;
	private ListView							historylv,departmentlv , userlv;
	private int									de			= 0;				// �����ж��Ƿ�û������
																				// 0you
																				// 1mei
																				// you
	private int									us			= 0;				// �����ж��Ƿ�û������
	private EditText							searchtext;					// ������
	private String								content;
	private Button								historyTile,userTitle , departmentTile;
	private TextView							title;
	Map<String, String> parentId=new HashMap<String, String>();
	private SharedPreferencesClass sp ;
	private String userkind="1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_selectuserlist);

		title = (TextView) findViewById(R.id.illegalselectuser_new_title);
		ImageButton back = (ImageButton) findViewById(R.id.illegalselectuser_new_back);
		historyTile=(Button) findViewById(R.id.illegalselectuser_historybutton);
		departmentTile = (Button) findViewById(R.id.illegalselectuser_newbutton);
		userTitle = (Button) findViewById(R.id.illegalselectuser_newbutton1);
		ImageButton search = (ImageButton) findViewById(R.id.illegalselectuser_new_searchbutton);
		searchtext = (EditText) findViewById(R.id.illegalselectuser_new_searchtextname);
		ImageButton zhuye = (ImageButton) findViewById(R.id.illegalselectuser_new_zhuye);
		historylv = (ListView) findViewById(R.id.illegalselectuser_new_historylist);
		departmentlv = (ListView) findViewById(R.id.illegalselectuser_new_viewlist);
		userlv = (ListView) findViewById(R.id.illegalselectuser_new_viewlist1);

		userid = ((User) getIntent().getSerializableExtra("User")).getUserId();
		if(getIntent().getStringExtra("userkind")!=null){
			userkind="2";
		}
		
		departmentId = "-1";
		title.setText("��Աѡ��");
		sp=new SharedPreferencesClass(this);
		CreateHistoryList();//��ʾ5���ڸ���ʷ����
		// ��ȡ��ʾ�б���Ϣ
		new ListTask(this).execute(); // �첽

		back.setOnClickListener(new Back());
		historyTile.setOnClickListener(new Historygone());//��ʷ������ʾ
		departmentTile.setOnClickListener(new Departmenttiemgone());
		userTitle.setOnClickListener(new Useritemgone());
		search.setOnClickListener(new Search());
		zhuye.setOnClickListener(new Zhuye());

	}
	//����5���ڸ���ʷ����
	private void CreateHistoryList(){
		ArrayList<HashMap<String, Object>> historylist=new ArrayList<HashMap<String, Object>>();
		String[] hisUserid = null;
		String[] hisUserName = null;
		if(userkind.equals("1")){
			 hisUserid=sp.getValueByKey(sp.key_illegal_userid).split(",");
			 hisUserName=sp.getValueByKey(sp.key_illegal_username).split(",");
		}else if(userkind.equals("2")){
			 hisUserid=sp.getValueByKey(sp.key_illegal_check_userid).split(",");
			 hisUserName=sp.getValueByKey(sp.key_illegal_check_username).split(",");
		}
		
		for(int i=0;i<hisUserid.length;i++){
			if(hisUserid[i].length()!=0)
			{
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("id", hisUserid[i]);
			map.put("name", hisUserName[i]);
			historylist.add(map);
			}
		}
		SimpleAdapter adapter = new SimpleAdapter(IlleageSelectUser.this,
				historylist, R.layout.leaveandovertime_adduser, new String[] {
						"name", "id" }, new int[] {
						R.id.leaveandovertime_adduser_name,
						R.id.leaveandovertime_adduser_id });
		if(hisUserid.length==0){
			View moreView = getLayoutInflater().inflate(R.layout.listview_noitem, null);
			historylv.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		}
		historylv.setAdapter(adapter);
		
		historylv.setOnItemClickListener(new Userlistitem());
	}

	public class Zhuye implements View.OnClickListener
	{
		public void onClick(View v)
		{
			finish();
		}
	}
	
	public class Historygone implements View.OnClickListener
	{
		public void onClick(View v)
		{

			if (historylv.getVisibility() == 0)
			{
				historylv.setVisibility(View.GONE);
			}
			else
			{
				historylv.setVisibility(View.VISIBLE);
			}

		}
	}
	public class Departmenttiemgone implements View.OnClickListener
	{
		public void onClick(View v)
		{

			if (departmentlv.getVisibility() == 0)
			{
				departmentlv.setVisibility(View.GONE);
			}
			else
			{
				departmentlv.setVisibility(View.VISIBLE);
			}

		}
	}

	public class Useritemgone implements View.OnClickListener
	{
		public void onClick(View v)
		{

			if (userlv.getVisibility() == 0)
			{
				userlv.setVisibility(View.GONE);
			}
			else
			{
				userlv.setVisibility(View.VISIBLE);
			}
		}
	}

	// ����
	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			if (departmentId == "-1")
			{
				finish();
			}
			else
			{
				departmentId = parentId.get(departmentId);
				parentId.remove(parentId);
				new ListTask(IlleageSelectUser.this).execute();
			}

		}
	}

	public class Search implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content = searchtext.getText().toString();
			if (content.length() == 0)
			{
				Toast.makeText(IlleageSelectUser.this, "����������������������",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				new ListTask1(IlleageSelectUser.this).execute(); // ����
			}

		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;

		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			//pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���", true);
			pDialog = new ProgressDialog(IlleageSelectUser.this);
			pDialog.setTitle("���ݼ���");
			pDialog.setMessage("���ݼ����У����Ժ򡤡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);
					
				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);
					
				}
			});
			pDialog.show();	
		}

		@Override
		protected String doInBackground(String... params)
		{if(isCancelled()) return null;//ȡ���첽
			return query.newaddresslist(departmentId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			 if(result==null){
			      Toast.makeText(IlleageSelectUser.this, "�����쳣", Toast.LENGTH_SHORT).show();
			      }else{
			getdepartmentlist(result);
			showdepartmentlist();
			getuserlist(result);
			showuserlist();
			if (de == 1 && us == 1)
			{
				Toast.makeText(IlleageSelectUser.this, "û������",
						Toast.LENGTH_SHORT).show();
			}
			}
			super.onPostExecute(result);
		}

	}

	public void getdepartmentlist(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("listdepartment");
			departmentlist = new ArrayList<HashMap<String, Object>>();
			if (jsonArray.length() == 0)
			{
				departmentTile.setVisibility(View.GONE);
				departmentlv.setVisibility(View.GONE);
				de = 1;
			}
			else
			{
				de = 0;
				departmentTile.setVisibility(View.VISIBLE);
				departmentlv.setVisibility(View.VISIBLE);
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> departmentlistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String name = jsonObject.getString("departmentName");
					String id = jsonObject.getString("departmentId");
					departmentlistmap.put("name", name);
					departmentlistmap.put("id", id);
					departmentlist.add(departmentlistmap);
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(IlleageSelectUser.this, "�����쳣", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public void getuserlist(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("listuser");
			userlist = new ArrayList<HashMap<String, Object>>();
			if (jsonArray.length() == 0)
			{
				userTitle.setVisibility(View.GONE);
				userlv.setVisibility(View.GONE);
				us = 1;
			}
			else
			{
				us = 0;
				userTitle.setVisibility(View.VISIBLE);
				userlv.setVisibility(View.VISIBLE);
				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					if (userid != jsonObject.getInt("userId"))
					{
						HashMap<String, Object> userlistmap = new HashMap<String, Object>();

						String name = jsonObject.getString("name");
						int id = jsonObject.getInt("userId");
						userlistmap.put("name", name);
						userlistmap.put("id", id);
						userlist.add(userlistmap);
					}
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(IlleageSelectUser.this, "�����쳣", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public void showdepartmentlist()
	{

		SimpleAdapter adapter = new SimpleAdapter(IlleageSelectUser.this,
				departmentlist, R.layout.addresslist_departmentitem,
				new String[] { "name", "id" }, new int[] {
						R.id.address_departmentitem_name,
						R.id.address_departmentitem_id });
		departmentlv.setAdapter(adapter);
		departmentlv.setOnItemClickListener(new Departmentlistitem());

	}

	class Departmentlistitem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
		{
			parentId.put((String) departmentlist.get(arg2).get("id"),departmentId);
			departmentId = (String) departmentlist.get(arg2).get("id");
			title.setText((String) departmentlist.get(arg2).get("name"));
			new ListTask(IlleageSelectUser.this).execute(); // �첽
		}

	}
	
	class Userlistitem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
		{
			TextView usertv_id = (TextView) arg1
					.findViewById(R.id.leaveandovertime_adduser_id);
			TextView usertv_name = (TextView) arg1
					.findViewById(R.id.leaveandovertime_adduser_name);
			
			Intent intent = new Intent();
			intent.putExtra("user_id", usertv_id.getText().toString());// ���ûش�����ͼ
			intent.putExtra("user_name", usertv_name.getText().toString());// ���ûش�����ͼ
			if(userkind.equals("2")){
				sp.setValueByKey(usertv_id.getText().toString(), usertv_name.getText().toString(), sp.key_illegal_check_userid, sp.key_illegal_check_username);
			setResult(90, intent);
			}else{
				sp.setValueByKey(usertv_id.getText().toString(), usertv_name.getText().toString(), sp.key_illegal_userid, sp.key_illegal_username);
			setResult(80, intent);	
			}
			finish();
		}

	}

	public void showuserlist()
	{

		SimpleAdapter adapter = new SimpleAdapter(IlleageSelectUser.this,
				userlist, R.layout.leaveandovertime_adduser, new String[] {
						"name", "id" }, new int[] {
						R.id.leaveandovertime_adduser_name,
						R.id.leaveandovertime_adduser_id });
		userlv.setAdapter(adapter);
		userlv.setOnItemClickListener(new Userlistitem());

	}

	class ListTask1 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;

		public ListTask1()
		{

		}

		public ListTask1(Context context)
		{
			pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���", true);
		}

		@Override
		protected String doInBackground(String... params)
		{

			return query.selectnewaddresslist(content);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			 if(result==null){
			      Toast.makeText(IlleageSelectUser.this, "�����쳣", Toast.LENGTH_SHORT).show();
			      }else{
			getuserlist(result);
			showuserlist();
			departmentTile.setVisibility(View.GONE);
			departmentlv.setVisibility(View.GONE);
			userTitle.setVisibility(View.GONE);
			if (us == 1)
			{
				Toast.makeText(IlleageSelectUser.this, "û������",
						Toast.LENGTH_SHORT).show();
			}
			}
			super.onPostExecute(result);
		}

	}

}
