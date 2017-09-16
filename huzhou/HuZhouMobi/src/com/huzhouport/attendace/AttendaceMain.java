package com.huzhouport.attendace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AttendaceMain extends Activity {
	private ImageButton amb;
	@SuppressWarnings("unused")
	private TextView amtt, amlsb,amlsi;
	private ListView aml;
	//private Button ;
	@SuppressWarnings("unused")
	private Query query = new Query();
	private List<Map<String, Object>> listv;
	private User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.attendace_main);

		amb = (ImageButton) findViewById(R.id.attendace_main_back);
		amtt = (TextView) findViewById(R.id.attendace_main_titleText);
		amlsi = (TextView) findViewById(R.id.attendace_main_list_sign);
		// amlsb = (TextView) findViewById(R.id.attendace_main_list_sign_back);
		aml = (ListView) findViewById(R.id.attendace_main_list);
		try {
			creatListView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = getIntent();
		user=(User) intent.getSerializableExtra("User");
		/*userId = intent.getStringExtra("userId");
		userName = intent.getStringExtra("userName");*/
		/*userId = "1";
		userName = "林华1";*/
		
		amb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent intent=new Intent(AttendaceMain.this,OfficOA.class);
				//startActivity(intent);
				finish();
			}
		});

		// amlsb.setOnClickListener(new MySignIn());

	}

	// 列表
	public void creatListView() throws Exception {
		listv = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("sign", "签到");
		listv.add(map);
		map = new HashMap<String, Object>();
		map.put("sign", "签退");
		listv.add(map);
		SimpleAdapter adapter = new SimpleAdapter(AttendaceMain.this, listv,
				R.layout.attendace_main_list, new String[] { "sign" },
				new int[] { R.id.attendace_main_list_sign });
		aml.setAdapter(adapter);
		aml.setOnItemClickListener(new MySignBack());
	}

	class MySignBack implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0) {
				Intent intent = new Intent(AttendaceMain.this,
						AttendaceSignIn.class);
				intent.putExtra("User", user);
				startActivity(intent);

			} else {
				Intent intent = new Intent(AttendaceMain.this,
						AttendaceSignBackMap.class);
				intent.putExtra("User", user);
				startActivity(intent);
			}
		}

	}
}
