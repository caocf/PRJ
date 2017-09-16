package com.huzhouport.knowledge;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class KnowledgeView extends Activity {
	private ListView lv;
	private ArrayList<HashMap<String, Object>> knowledge;
	private Query query = new Query();
	private Context mContext;
	private String kindidtext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.konwledge_view);

		System.out.println("aaaa111");
		lv = (ListView) findViewById(R.id.knowledge_viewlist);

		// knowledge = new ArrayList<HashMap<String, Object>>();

		//获取显示列表信息
		ListTask task = new ListTask(this);  // 异步
        task.execute();
		
		


		// 返回
		ImageButton back = (ImageButton) findViewById(R.id.knowledge_back);
		back.setOnClickListener(new Back());

	}

	public void GetKnowledge(String result)  {

		JSONTokener jsonParser_User = new JSONTokener(result);
		// System.out.println("jsonParser_User:  " + jsonParser_User);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser_User.nextValue();
		// System.out.println("data:  " + data);
		JSONObject json1 = data.getJSONObject("pagemodel");
		JSONArray jsonArray = json1.getJSONArray("recordsDate");
		System.out.println("jsonArray:  " + jsonArray);
		System.out.println("jsonArray.length()" + jsonArray.length());
		// ArrayList<HashMap<String, Object>> childlist = null;
		knowledge = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < jsonArray.length(); i++) {
			HashMap<String, Object> knowmap = new HashMap<String, Object>();
			JSONObject jsonObject_konw = (JSONObject) jsonArray.opt(i);
			System.out.println("jsonObject_konw==" + jsonObject_konw);
			System.out.println("jsonObject_konw=="
					+ jsonObject_konw.getString("kindName")
					+ jsonObject_konw.getString("kindID")
					+ jsonObject_konw.getString("kindIndex"));
			knowmap.put("kindName", jsonObject_konw.getString("kindName"));
			knowmap.put("kindID", jsonObject_konw.getString("kindID"));
			knowmap.put("kindIndex", jsonObject_konw.getString("kindIndex"));
			knowmap.put("img", R.drawable.ic_action_call);
			knowledge.add(knowmap);

		}
		} catch (Exception e) {
			Toast.makeText(KnowledgeView.this, "请检查网络", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


	public void showListView() {	
		mContext = this;
		ImageAdapter adapter=new ImageAdapter(knowledge,mContext);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new Onitem());
		
		
	}
	
	

class Onitem implements OnItemClickListener {

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		TextView kindid=(TextView)arg1.findViewById(R.id.knowledge_kindid);
		kindidtext=kindid.getText().toString();
		Intent intent=new Intent(KnowledgeView.this,Knowledgelist.class);
		intent.putExtra("kindid", kindidtext);
		startActivity(intent);
		
		
	}
}
	class Back implements OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}


	class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      public ListTask(Context context){
		  pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
	
			return query.GetKnowledge();
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			GetKnowledge(result);
			showListView();
			super.onPostExecute(result);
		}
		  
	  }
	
	
	
}
