package com.huzhouport.electricreport;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.huzhouportpublic.R;
import com.huzhouport.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ElectricReportNewAddShipName extends Activity {

	private ArrayList<HashMap<String, Object>> portlist;
	private ListView listView;
	private User user;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreportnew_addport);

		ImageButton back = (ImageButton) findViewById(R.id.electricreportnew_addport_back);
		back.setOnClickListener(new Back());

		user = (User) getIntent().getSerializableExtra("user");

		TextView title = (TextView) findViewById(R.id.electricreportnew_addport_title);
		title.setText("船名号选择");

		listView = (ListView) findViewById(R.id.electricreportnew_addport_viewlist);
		
		showNeirong();
		

	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}


	public void showNeirong() {
		portlist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < user.getShipBindingList().size(); i++) {
			HashMap<String, Object> portmap = new HashMap<String, Object>();

			portmap.put("ID", user.getShipBindingList().get(i).getShipId());
			portmap.put("Name", user.getShipBindingList().get(i).getShipNum());
			portlist.add(portmap);
		}
		SimpleAdapter adapter = new SimpleAdapter(
				ElectricReportNewAddShipName.this, portlist,
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
			setResult(125, intent);
			finish();
		}

	}

}
