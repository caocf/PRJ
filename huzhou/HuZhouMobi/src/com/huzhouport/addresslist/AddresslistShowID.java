package com.huzhouport.addresslist;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AddresslistShowID extends Activity {
	private Context mContext;
	private Query query = new Query();
	private String id;

	private ListView tellv;
	private ArrayList<HashMap<String, Object>> tellist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresslist_showid);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		String name = intent.getStringExtra("name");
		System.out.println("addressname=" + id + name);

		TextView textView1 = (TextView) findViewById(R.id.addresslist_textView);
		textView1.setText(name);

		// 获取显示列表信息
		ListTask task = new ListTask(this); // 异步
		task.execute();

		tellv = (ListView) findViewById(R.id.addresslist_show_viewlist);

		// 返回
		ImageButton back = (ImageButton) findViewById(R.id.addresslist_back1);
		back.setOnClickListener(new Back());

	}

	public void GetUserListByDepartment(String result) {

		tellist = new ArrayList<HashMap<String, Object>>();
		JSONTokener jsonParser_User = new JSONTokener(result);

		JSONObject data;
		try {
			data = (JSONObject) jsonParser_User.nextValue();

			JSONArray jsonArray = data.getJSONArray("list");
			JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(0);
			JSONObject jsonObject_Deaprtment = (JSONObject) jsonArray2.opt(0);
			JSONObject jsonObject_User = (JSONObject) jsonArray2.opt(1);
			String telnumber = jsonObject_User.getString("tel");
			String delnumber = jsonObject_Deaprtment
					.getString("departmentName");
			TextView addresslist_del = (TextView) findViewById(R.id.addresslist_del);
			addresslist_del.setText(delnumber);

			String[] telString = telnumber.split(",");

			for (int i = 0; i < telString.length; i++) {
				HashMap<String, Object> tellistmap = new HashMap<String, Object>();
				tellistmap.put("tel", telString[i]);
				tellist.add(tellistmap);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void showdepartmentlist() {

		SimpleAdapter adapter = new SimpleAdapter(AddresslistShowID.this,
				tellist, R.layout.addresslist_showitem, new String[] { "tel" },
				new int[] { R.id.address_show_name });
		tellv.setAdapter(adapter);
		mContext = this;
		ImageAdapterShow imgadapter = new ImageAdapterShow(tellist, mContext);
		tellv.setAdapter(imgadapter);
		tellv.setOnItemClickListener(new Userlistitem());

	}

	// 打电话
	public class TelButton implements View.OnClickListener {
		public void onClick(View v) {
			TextView telnumber = (TextView) findViewById(R.id.address_show_name);
			String number = telnumber.getText().toString();
			Intent intent = new Intent(); // 创建一个意图
			intent.setAction("android.intent.action.CALL");
			// intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("tel:" + number));
			startActivity(intent); // 方法内部会自动为intent添加类别
									// ：android.intent.category.DEFAULT".

		}
	}

	// 发短信
	public class smsButton implements View.OnClickListener {
		public void onClick(View v) {

			/**
			 * 调用系统界面，给指定的号码发送短信
			 */
			TextView telnumber = (TextView) findViewById(R.id.address_show_name);
			String number = telnumber.getText().toString();
			Intent sendIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("smsto:" + number));
			// sendIntent.putExtra("sms_body", body); //用于附带短信内容，可不加
			startActivity(sendIntent);
		}
	}

	class Userlistitem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView telnumber = (TextView) arg1
					.findViewById(R.id.address_show_name);
			String number = telnumber.getText().toString();
			Intent intent = new Intent(); // 创建一个意图
			intent.setAction("android.intent.action.CALL");
			// intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("tel:" + number));
			startActivity(intent); // 方法内部会自动为intent添加类别
									// ：android.intent.category.DEFAULT".
		}

	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			// Intent intent=new Intent(AddresslistShowID.this,
			// AddresslistView.class);
			// startActivity(intent);
			finish();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		 @SuppressWarnings("deprecation")
			public ListTask(Context context){
		    	  pDialog = new ProgressDialog(AddresslistShowID.this);
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
							if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
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
							if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
								ListTask.this.cancel(true);
							
						}
					});
					pDialog.show();	
				  
			  }

		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()) return null;//取消异步
			
			return query.AllUserAndDepartmentById(id);
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			GetUserListByDepartment(result);
			showdepartmentlist();
			super.onPostExecute(result);
		}

	}

}
