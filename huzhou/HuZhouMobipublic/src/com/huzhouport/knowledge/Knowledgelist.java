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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Knowledgelist extends Activity {
	private ListView lv;
	private ArrayList<HashMap<String, Object>> knowledge;
	private Query query = new Query();
	private String kindidtext;
    private  String kindid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.konwledge_list);

		Intent intent=getIntent();
		kindid=intent.getStringExtra("kindid");
		 System.out.println("kindid="+kindid);
		
		lv = (ListView) findViewById(R.id.knowledge_list_viewlist);

		// knowledge = new ArrayList<HashMap<String, Object>>();

		//获取显示列表信息
		ListTask task = new ListTask(this);  // 异步
        task.execute();
		


		// 返回
		ImageButton back = (ImageButton) findViewById(R.id.knowledge_lsit_back);
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
			knowmap.put("knowledgeID", jsonObject_konw.getString("knowledgeID"));
			knowmap.put("knowledgeName", jsonObject_konw.getString("knowledgeName"));
			knowmap.put("knowledgeIndex", jsonObject_konw.getString("knowledgeIndex"));
			knowmap.put("isLink", jsonObject_konw.getString("isLink"));
			knowledge.add(knowmap);

		}
		System.out.println("knowledge12345="+knowledge);
		} catch (Exception e) {
			Toast.makeText(Knowledgelist.this, "请检查网络", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


	public void showListView() {	
		SimpleAdapter adapter = new SimpleAdapter(Knowledgelist.this,knowledge, R.layout.knowledge_listitem, new String[] {"knowledgeName", "knowledgeIndex" ,"knowledgeID","isLink"}, new int[] { R.id.knowledge_knowledgeName,R.id.knowledge_knowledgeIndex,R.id.knowledge_knowledgeID,R.id.knowledge_isLink});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new Onitem());
		
		
	}
	
	

class Onitem implements OnItemClickListener {

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		TextView knowledgeIDtext=(TextView)arg1.findViewById(R.id.knowledge_knowledgeID);
		String knowledgeID=knowledgeIDtext.getText().toString();
		
		TextView link=(TextView)arg1.findViewById(R.id.knowledge_isLink);
		String islink=link.getText().toString();
		if(islink.equals("1")){
			Intent intent = new Intent(Knowledgelist.this,
			KnowledgeShow.class);
		    intent.putExtra("knowledgeID", knowledgeID);
		    startActivity(intent);	
		}else{
			Intent intent = new Intent(Knowledgelist.this,
					KnowledgetextShow.class);
				    intent.putExtra("knowledgeID", knowledgeID);
				    startActivity(intent);	
		}

		
	}
}
	class Back implements OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}
	public void toshow(String result) {

		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
		  String id = data.get("islink")+"";
		  String link=data.getString("link");
			System.out.println("link===="+link);
			if(id.equals("0")){  //islink 为0 没连接就跳页面，，   为1 有连接 调用第三方浏览器
		Intent intent = new Intent(Knowledgelist.this,
		KnowledgeShow.class);
		intent.putExtra("kindID", kindidtext);
	    startActivity(intent);	
				
			}else{
				//Uri content = Uri.parse(HttpUtil.BASE_URL+"page/officoa/KnowledgeShow.jsp?kindID="+kindid.getText().toString());
				Uri content = Uri.parse(link);
				String action;
		        
		        
		                action = Intent.ACTION_DEFAULT;
		        
		        Intent intent = new Intent(action, content);
		        startActivity(intent);
			}

		
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
	
			return query.showKnowLedgedian(kindid);
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
