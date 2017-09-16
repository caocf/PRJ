package com.huzhouport.wharfwork;





import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.knowledge.KnowledgeNewView;





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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class WharfWorkAddGoodsSecend extends Activity {

	private Query query	= new Query();
	private ArrayList<HashMap<String, Object>>	goodskindlist;
	private ListView listView;
	private ListView listView1;
	private String goodsKindID;
	protected void onCreate(Bundle savedInstanceState)
	{
		
	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_add_goods);

		
		goodsKindID=	getIntent().getStringExtra("goodsKindID");
		
		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_goods_back);
		back.setOnClickListener(new Back());
		
		

		
		ListTask task = new ListTask(this); // 异步
		task.execute();
		
		
		listView=(ListView) findViewById(R.id.wharfwork_add_goods_viewlist);
		listView.setVisibility(View.GONE);
		findViewById(R.id.wharfwork_add_goods_tx).setVisibility(View.GONE);
		
		
		listView1=(ListView) findViewById(R.id.wharfwork_add_goods_viewlist1);
		

	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			finish();
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
			pDialog = new ProgressDialog(WharfWorkAddGoodsSecend.this);
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
		protected String doInBackground(String... params)
		{
			if(isCancelled()) return null;//取消异步

			String result = query.GoodsAll(goodsKindID);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			
			getNeirong(result);
            showNeirong();
			if (pDialog != null)
				pDialog.dismiss();
			
			super.onPostExecute(result);
		}

	}
	
	
public void getNeirong(String result){
		
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray	jsonArray=data.getJSONArray("goodslist");
			
			goodskindlist = new ArrayList<HashMap<String, Object>>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> goodskindmap = new HashMap<String, Object>();
				JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
				
				
				goodskindmap.put("goodsKindID", jsonObject.getString("goodsID"));
				goodskindmap.put("goodsKindName",jsonObject.getString("goodsName"));
				goodskindlist.add(goodskindmap);
				}
			
	
		    
		    
		} catch (Exception e) {
			Toast.makeText(WharfWorkAddGoodsSecend.this, "网络异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


public void showNeirong(){  	 
		SimpleAdapter adapter = new SimpleAdapter(WharfWorkAddGoodsSecend.this,goodskindlist, R.layout.wharfwork_add_goods_item1, new String[] {"goodsKindName", "goodsKindID" }, new int[] { R.id.wharfwork_add_goods_item1_name,R.id.wharfwork_add_goods_item1_id});
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(new Submit());

}	



class Submit implements OnItemClickListener
{

	@Override
	public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
	{
		TextView tv_name = (TextView) arg1
				.findViewById(R.id.wharfwork_add_goods_item1_name);
		Intent intent = new Intent();
		intent.putExtra("name", tv_name.getText().toString());// 设置回传的意图
		setResult(40, intent);
		finish();
	}

}

}
