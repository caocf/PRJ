package com.huzhouport.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;

public class ScheduleSelectUserInfo extends Activity
{

	private Query						query			= new Query();
	private String						departmentId,eventId;
	private String[]					userListid;											// 参与人
	private List<Map<String, Object>>	departmentlist;
	private List<Map<String, Object>>	userlist;
	private ListView					departmentlv , userlv;
	private int							de				= 0;								// 用来判断是否没有数据
																							// 0you
																							// 1mei
																							// you
	private int							us				= 0;								// 用来判断是否没有数据
	private EditText					searchtext;										// 搜索框
	private String						content;
	private Button						userTitle , departmentTile;
	private TextView					title;
	Map<String, String>					parentId		= new HashMap<String, String>();
	private ArrayList<HashMap<String, Object>>		userMap;
	private HashMap<String, Boolean>		departmentMap= new HashMap<String, Boolean>();
	private ArrayList<HashMap<String, Boolean>> dMap;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectuser);

		title = (TextView) findViewById(R.id.selectuser_title);
		ImageButton back = (ImageButton) findViewById(R.id.selectuser_back);
		departmentTile = (Button) findViewById(R.id.selectuser_dep_title);
		userTitle = (Button) findViewById(R.id.selectuser_user_title);
		ImageButton img_search = (ImageButton) findViewById(R.id.selectuser_searchbutton);
		ImageButton img_finish = (ImageButton) findViewById(R.id.selectuser_finish);
		searchtext = (EditText) findViewById(R.id.selectuser_searchcontent);
		ImageButton zhuye = (ImageButton) findViewById(R.id.selectuser_home);
		departmentlv = (ListView) findViewById(R.id.selectuser_dep_lv);
		userlv = (ListView) findViewById(R.id.selectuser_user_lv);
		Intent intent = getIntent();
		eventId=intent.getStringExtra("eventId");
		userListid = intent.getStringExtra("agereeUserIdlist").split(",");
		System.out.println("userListid:"+userListid);
		userMap= (ArrayList<HashMap<String, Object>>) getIntent().getSerializableExtra("userMap");
		dMap=(ArrayList<HashMap<String, Boolean>>) getIntent().getSerializableExtra("dMap");
		if(userMap.size()==0){
			userMap= new ArrayList<HashMap<String, Object>>();
		}
		if(dMap.size()!=0){
			departmentMap	= dMap.get(0);
		}
			
		departmentId = "-1";
		title.setText("人员选择");

		// 获取显示列表信息
		new ListTask(this).execute(); // 异步

		back.setOnClickListener(new Back());
		departmentTile.setOnClickListener(new Departmenttiemgone());
		userTitle.setOnClickListener(new Useritemgone());
		img_search.setOnClickListener(new Search());
		zhuye.setOnClickListener(new Zhuye());
		img_finish.setOnClickListener(new SelectFinish());
	}

	class SelectFinish implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.putExtra("userMap", userMap);
			dMap.add(departmentMap);
			intent.putExtra("dMap", dMap);
			setResult(80, intent);
			finish();

		}

	}

	public class Zhuye implements View.OnClickListener
	{
		public void onClick(View v)
		{
			finish();
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

	// 返回
	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			if (departmentId == "-1")
			{
				finish();
			}
			else
			{	ChooseAllDepartment();
				departmentId = parentId.get(departmentId);
				parentId.remove(parentId);
				new ListTask(ScheduleSelectUserInfo.this).execute();
			}

		}
	}
//判断部门里是否全选
	public void ChooseAllDepartment(){
		int chooseIcon=0;
		for(int i=0;i<userMap.size();i++){
			if(userMap.get(i).get("dId").equals(departmentId)&&(Boolean)userMap.get(i).get("oboolen")==false){
				chooseIcon=1;
			}
		}
		if(chooseIcon==1)
		{
			departmentMap.put(departmentId, false);
		}
		else{
			departmentMap.put(departmentId, true);
		}
	}
	public class Search implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content = searchtext.getText().toString();
			if (content.length() == 0)
			{
				Toast.makeText(ScheduleSelectUserInfo.this, "请先在搜索框里输入内容",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				new ListTask1(ScheduleSelectUserInfo.this).execute(); // 搜索
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
			pDialog = new ProgressDialog(ScheduleSelectUserInfo.this);
			pDialog.setTitle("数据加载");
			pDialog.setMessage("数据加载中，请稍候···");
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
				public void onClick(DialogInterface dialog,int which)
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
			return query.newaddresslistInfo(departmentId,eventId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getdepartmentlist(result);
			showdepartmentlist();
			getuserlist(result);
			showuserlist();
			if (de == 1 && us == 1)
			{
				Toast.makeText(ScheduleSelectUserInfo.this, "没有数据",
						Toast.LENGTH_SHORT).show();
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
			departmentlist = new ArrayList<Map<String, Object>>();
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
					if (departmentMap.get(id) == null)
						departmentMap.put(id, false);
					departmentlistmap.put("departmentName", name);
					departmentlistmap.put("departmentId", id);
					departmentlist.add(departmentlistmap);
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(ScheduleSelectUserInfo.this, "网络异常", Toast.LENGTH_SHORT)
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
			userlist = new ArrayList<Map<String, Object>>();
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
					int numcheckuser=0;
					for(int j=0;j<userListid.length;j++){
						if(userListid[j].equals(jsonObject.getString("userId"))){
							numcheckuser=1;
							break;
						}
					}
					if (numcheckuser==0)
					{
						HashMap<String, Object> userlistmap = new HashMap<String, Object>();

						String name = jsonObject.getString("name");
						String id = jsonObject.getString("userId");
						String did=jsonObject.getString("partOfDepartment");
						int haveUser=0;
						for(int j=0;j<userMap.size();j++){
							if(id.equals(userMap.get(j).get("uId"))){
								haveUser=1;
								 if ((Boolean) userMap.get(j).get("oboolen"))
								{
									userlistmap.put("userImg", R.drawable.checkbox_yes);
								}
								else
								{
									userlistmap.put("userImg", R.drawable.checkbox_no);
								}	
							}
						}
						if (haveUser==0)
						{
							HashMap<String, Object> ouserMap = new HashMap<String, Object>();
							ouserMap.put("uId", id);
							ouserMap.put("dId", did);
							ouserMap.put("uName", name);
							ouserMap.put("oboolen", false);
							userMap.add(ouserMap);
							userlistmap.put("userImg", R.drawable.checkbox_no);
						}
						userlistmap.put("dId", did);
						userlistmap.put("userName", name);
						userlistmap.put("userId", id);
						userlist.add(userlistmap);
					}
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(ScheduleSelectUserInfo.this, "网络异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public void showdepartmentlist()
	{
		AdapterListViewCheckboxDepartment adapter = new AdapterListViewCheckboxDepartment();
		departmentlv.setAdapter(adapter);
		departmentlv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent,View view,
					int position,long id)
			{

				parentId.put(
						(String) departmentlist.get(position).get(
								"departmentId"), departmentId);
				departmentId = (String) departmentlist.get(position).get(
						"departmentId");
				title.setText((String) departmentlist.get(position).get(
						"departmentName"));
				new ListTask(ScheduleSelectUserInfo.this).execute(); // 异步

			}

		});

	}

	public void showuserlist()
	{

		SimpleAdapter adapter = new SimpleAdapter(ScheduleSelectUserInfo.this,
				userlist, R.layout.selectuser_user, new String[] { "userImg",
						"userName", "userId" }, new int[] {
						R.id.selectuser_user_checkbox,
						R.id.selectuser_user_name, R.id.selectuser_user_id });
		userlv.setAdapter(adapter);
		userlv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent,View view,
					int position,long id)
			{
				String id1 = (String) userlist.get(position).get("userId");
				ImageView imgImageView = (ImageView) view
						.findViewById(R.id.selectuser_user_checkbox);				
				for(int j=0;j<userMap.size();j++){
					if(userMap.get(j).get("uId").equals(id1)){
						if ((Boolean) userMap.get(j).get("oboolen"))
						{
							userMap.get(j).put("oboolen", false);
							imgImageView.setImageResource(R.drawable.checkbox_no);
						}

						else
						{
							userMap.get(j).put("oboolen",true);
							imgImageView.setImageResource(R.drawable.checkbox_yes);
						}
					}
				}
				
			}
		});

	}

	class ListTask1 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;

		public ListTask1()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask1(Context context)
		{
			pDialog = new ProgressDialog(ScheduleSelectUserInfo.this);
			pDialog.setTitle("数据加载");
			pDialog.setMessage("数据加载中，请稍候···");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{

				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask1.this != null
							&& ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask1.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask1.this != null
							&& ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask1.this.cancel(true);

				}
			});
			pDialog.show();
		}


		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步
			return query.selectnewaddresslist(content);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getuserlist(result);
			showuserlist();
			departmentTile.setVisibility(View.GONE);
			departmentlv.setVisibility(View.GONE);
			userTitle.setVisibility(View.GONE);
			if (us == 1)
			{
				Toast.makeText(ScheduleSelectUserInfo.this, "没有数据",
						Toast.LENGTH_SHORT).show();
			}

			super.onPostExecute(result);
		}

	}

	class AdapterListViewCheckboxDepartment extends BaseAdapter
	{
		private LayoutInflater mInflater;    
    
	    public AdapterListViewCheckboxDepartment() {
			 mInflater = LayoutInflater.from(ScheduleSelectUserInfo.this);
	    }    
	    
	  
	    
	    public int getCount() {    
	        return departmentlist.size();    
	    }    
	    
	    public Object getItem(int position) {    
	        return null;    
	    }    
	    
	    public long getItemId(int position) {    
	        return 0;    
	    }    
	    
	    public View getView(int position, View convertView, ViewGroup parent) {    
	        D_ViewHolder holder = null;    
	        if (convertView == null) {    
	            holder = new D_ViewHolder();    
	            convertView = mInflater.inflate(R.layout.selectuser_department, null);  
	            holder.cBox = (ImageView) convertView.findViewById(R.id.selectuser_department_checkbox); 
	            holder.name = (TextView) convertView.findViewById(R.id.selectuser_department_name);  
	            holder.id = (TextView) convertView.findViewById(R.id.selectuser_department_id);  
	             
	            holder.name.setText(departmentlist.get(position).get("departmentName").toString());  
	            holder.id.setText(departmentlist.get(position).get("departmentId").toString());  
	            if(departmentMap.get(departmentlist.get(position).get("departmentId").toString()))
	                holder.cBox.setImageResource(R.drawable.checkbox_yes); 
	                else  holder.cBox.setImageResource(R.drawable.checkbox_no); 
	            final String id=holder.id.getText().toString() ;
	            final ImageView img=holder.cBox ;
	            holder.cBox.setOnClickListener(new OnClickListener()
	    		{
	    			
	    			@Override
	    			public void onClick(View v)
	    			{
	    				if (departmentMap.get(id))
	    				{
	    					departmentMap.put(id, false);
	    					for(int i=0;i<userMap.size();i++){
	    						if(id.equals(userMap.get(i).get("dId"))){
	    							userMap.get(i).put("oboolen", false);
	    						}
	    					}    					
	    					img.setImageResource(R.drawable.checkbox_no);
	    				}
	    			
	    			else{
	    				departmentMap.put(id, true);
	    				int chooseUser=0;
	    				for(int i=0;i<userMap.size();i++){
    						if(id.equals(userMap.get(i).get("dId"))){
    							chooseUser=1;
    							userMap.get(i).put("oboolen", true);
    						}
    							
    					}   
	    				if(chooseUser==0){
	    					new GetUserByDepartment().execute(id);
	    				}
	    				img.setImageResource(R.drawable.checkbox_yes);
	    			}
	    				
	    			}
	    		});
	            
	            convertView.setTag(holder);    
	        } else {    
	            holder = (D_ViewHolder) convertView.getTag();    
	        }    
	        
	        return convertView;    
	    }    
	    
	    public final class D_ViewHolder {     
	        public TextView name;    
	        public TextView id;    
	        public ImageView cBox;    
	    }    
	}
	class GetUserByDepartment extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			return query.newaddresslistInfo(params[0], eventId);
		}

		@Override
		protected void onPostExecute(String result)
		{

			JSONTokener jsonParser_User = new JSONTokener(result);
			try
			{
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray = data.getJSONArray("listuser");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					int numcheckuser=0;
					for(int j=0;j<userListid.length;j++){
						if(userListid[j].equals(jsonObject.getString("userId"))){
							numcheckuser=1;
							break;
						}
					}
					if (numcheckuser==0)
					{
						String name = jsonObject.getString("name");
						String id = jsonObject.getString("userId");
						String did = jsonObject.getString("partOfDepartment");

						HashMap<String, Object> ouserMap = new HashMap<String, Object>();
						ouserMap.put("uId", id);
						ouserMap.put("dId", did);
						ouserMap.put("uName", name);
						ouserMap.put("oboolen", true);
						userMap.add(ouserMap);

					}
				}
			}
			catch (Exception e)
			{
				Toast.makeText(ScheduleSelectUserInfo.this, "选择失败",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}

	}
}
