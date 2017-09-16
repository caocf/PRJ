package com.huzhouport.electricreport;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 电子报港添加港口
 * @author Administrator
 *
 */
public class ElectricReportNewAddPort extends Activity {

	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> portlist;
	private ListView listView;
	private String kind;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreportnew_addport);

		CommonListenerWrapper.commonBackWrapperListener(R.id.electricreportnew_addport_back,this);

		kind = getIntent().getStringExtra("kind");

		listView = (ListView) findViewById(R.id.electricreportnew_addport_viewlist);
		
		new queryPortTask(this).execute();
	}

	class queryPortTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public queryPortTask() {

		}

		public queryPortTask(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return query.showPortList();
		}

		@Override
		protected void onPostExecute(String result) {
            if(result==null){
            Toast.makeText(ElectricReportNewAddPort.this, "网络异常", Toast.LENGTH_SHORT).show();
            }else{
			parseContent(result);
			showContent();
            }
			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}

	public void parseContent(String result) {
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");

			portlist = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> portmap = new HashMap<String, Object>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

				portmap.put("ID", jsonObject.getString("portID"));
				portmap.put("Name", jsonObject.getString("portName"));
				portlist.add(portmap);
			}
		} catch (Exception e) {
			Toast.makeText(ElectricReportNewAddPort.this, "网络异常",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void showContent() {
		SimpleAdapter adapter = new SimpleAdapter(
				ElectricReportNewAddPort.this, portlist,
				R.layout.wharfwork_add_goods_item1,
				new String[] { "Name", "ID" }, new int[] {
						R.id.wharfwork_add_goods_item1_name,
						R.id.wharfwork_add_goods_item1_id });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new Submit());

	}

	class Submit implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_name = (TextView) arg1
					.findViewById(R.id.wharfwork_add_goods_item1_name);
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.wharfwork_add_goods_item1_id);
			Intent intent = new Intent();
			intent.putExtra("id", tv_id.getText().toString());// 设置回传的意图
			intent.putExtra("name", tv_name.getText().toString());// 设置回传的意图
			intent.putExtra("kind", kind);// 设置回传的意图
			setResult(50, intent);
			finish();
		}

	}

}
