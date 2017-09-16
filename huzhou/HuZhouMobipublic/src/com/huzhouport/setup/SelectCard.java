package com.huzhouport.setup;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;

import com.huzhouport.common.util.Query;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SelectCard extends Activity {
	private Query query = new Query();
	private ListView oldList;
	private ArrayList<HashMap<String, String>> oldDat=new ArrayList<HashMap<String, String>>();
	private SimpleAdapter adapter;
	private String DateKind;
	private String position;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_card);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_boatman_back);
		ImageButton img_submit = (ImageButton) findViewById(R.id.setup_boatman_add);
				
		oldList= (ListView) findViewById(R.id.setup_boatman_listview);
		
		DateKind= getIntent().getStringExtra("DateKind");
		position= getIntent().getStringExtra("position");

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		
		img_submit.setVisibility(View.GONE);

		new ListTask(this).execute();// 异步
	}

	


	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			pDialog = new ProgressDialog(SelectCard.this);
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

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return query.ShowBoatcardList();
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);// 获得数据

			super.onPostExecute(result);
		}

		public void getNeirong(String result) {

			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray=data.getJSONArray("list");
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jb=(JSONObject) jsonArray.opt(i);
					HashMap<String, String> map=new HashMap<String, String>();
					map.put("cardID", jb.getString("cardID"));
					map.put("cardName", jb.getString("cardName"));
					oldDat.add(map);
				}
				
				adapter=new SimpleAdapter(SelectCard.this, oldDat, R.layout.dangerousgoodsjob_add_additem,
						new String[] { "cardID", "cardName" }, new int[] {
						R.id.dangerousgoodsjob_wharfID,
						R.id.dangerousgoodsjob_addwharfName });
				
				oldList.setAdapter(adapter);
				oldList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent();
						intent.setClass(SelectCard.this, SetBoatman.class);
						intent.putExtra("cardId", oldDat.get(arg2).get("cardID"));
						intent.putExtra("cardName", oldDat.get(arg2).get("cardName"));
						intent.putExtra("DateKind", DateKind);
						intent.putExtra("position", position);
						setResult(40, intent);
						finish();
					}
				});
				
			} catch (Exception e) {
				Toast.makeText(SelectCard.this, "无法获取证书类型列表", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
		}
	}

}
