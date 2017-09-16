package com.huzhouport.setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.MainPage;
import com.huzhouport.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeBinding extends Activity {
	private ListView list_Binding;
	private ArrayList<HashMap<String, Object>> list;
	private TextView tv_title;
	private User user;
	private int bindnum;
	private SimpleAdapter adapter ;
	private String bindName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_binding);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_binding_back);
		ImageButton img_submit = (ImageButton) findViewById(R.id.setup_binding_submit);
		 tv_title=(TextView)findViewById(R.id.setup_binding_title);
		list_Binding = (ListView) findViewById(R.id.setup_binding_listview);
		
		user = (User) getIntent().getSerializableExtra("User");
		bindnum=user.getBindnum();
		CreatListView();
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		img_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new UpdateBindName().execute();
			}
		});
	}
	class UpdateBindName extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String result=null;
			try {
				HttpFileUpTool hft=new HttpFileUpTool();
				Map<String, Object> param1=new HashMap<String, Object>();
				param1.put("publicUser.bindName", bindName);
				param1.put("publicUser.userId", user.getUserId());
				result=hft.post(HttpUtil.BASE_URL+"UpdateBindName", param1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			user.setBindnum(bindnum);
			Toast.makeText(ChangeBinding.this, "设置成功！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(ChangeBinding.this, MainPage.class);
			intent.putExtra("User", user);
			intent.putExtra("checkboxvalue1", getIntent().getStringExtra("checkboxvalue1"));
			intent.putExtra("checkboxvalue2", getIntent().getStringExtra("checkboxvalue2"));
			startActivity(intent);
			super.onPostExecute(result);
		}
		
	}
	private void CreatListView(){
		if (user != null) {
			if (user.getWharfBindingList() != null) {
				tv_title.setText("码头绑定信息");
				list = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < user.getWharfBindingList().size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("name", user.getWharfBindingList().get(i).getWharfNum());
					if(i==bindnum){
						map.put("img",R.drawable.item_select);	
					}else{
						map.put("img","");
					}
					list.add(map);	
				}
				
			}else if (user.getShipBindingList() != null){
				tv_title.setText("船舶绑定信息");
				list = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < user.getShipBindingList().size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("name", user.getShipBindingList().get(i).getShipNum());
					if(i==bindnum){
						map.put("img",R.drawable.item_select);	
					}else{
						map.put("img","");
					}
					
					list.add(map);	
				}
			}
			 adapter = new SimpleAdapter(
					ChangeBinding.this, list,
					R.layout.setup_changebinding_item, new String[] {"name","img" }, new int[] {
							R.id.changebinding_name,R.id.changebinding_img });
			list_Binding.setAdapter(adapter);
			list_Binding
					.setOnItemClickListener(new SelectOnlyOne());
		}
	}
	
	class SelectOnlyOne implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			for (int i = 0; i < list.size(); i++) {
				if(i!=arg2){
					list.get(i).put("img","");
				}else{
					list.get(i).put("img",R.drawable.item_select);
				}
			}
			bindName=((TextView)arg1.findViewById(R.id.changebinding_name)).getText().toString();
			bindnum=arg2;
			adapter.notifyDataSetChanged();
		}
		
	}
}
