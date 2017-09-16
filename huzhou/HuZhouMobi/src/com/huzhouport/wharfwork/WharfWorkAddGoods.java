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


public class WharfWorkAddGoods extends Activity {

	private Query query	= new Query();
	private ArrayList<HashMap<String, Object>>	goodskindlist;
	private ArrayList<HashMap<String, Object>>	goodskindlist1;
	private ListView listView;
	private ListView listView1;
	private String name;
	protected void onCreate(Bundle savedInstanceState)
	{
		
	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_add_goods);

		
		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_goods_back);
		back.setOnClickListener(new Back());
		
		

		
		ListTask task = new ListTask(this); // 异步
		task.execute();
		
		
		listView=(ListView) findViewById(R.id.wharfwork_add_goods_viewlist);
		listView1=(ListView) findViewById(R.id.wharfwork_add_goods_viewlist1);
		

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
           
            if(40==resultCode)
            {
            	name = data.getStringExtra("name"); 
    		 
            	
    	    Intent intent = new Intent();
			intent.putExtra("name", name);// 设置回传的意图
			
			setResult(30, intent);
         	finish();
            }
            
            
            
            super.onActivityResult(requestCode, resultCode, data);
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
			pDialog = new ProgressDialog(WharfWorkAddGoods.this);
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

			String result = query.GoodsKindAll();

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			
			getNeirong(result);
			showNeirong();
            showNeirong1();
			if (pDialog != null)
				pDialog.dismiss();
			
			super.onPostExecute(result);
		}

	}
	
	
public void getNeirong(String result){
		
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray	jsonArray=data.getJSONArray("goodskindlist");
			JSONArray	jsonArray1=data.getJSONArray("goodskindlist1");
			
			goodskindlist = new ArrayList<HashMap<String, Object>>();
			goodskindlist1 = new ArrayList<HashMap<String, Object>>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> goodskindmap = new HashMap<String, Object>();
				JSONArray jsonArray2 = (JSONArray) jsonArray.opt(i);
				System.out.println("jsonArray23"+jsonArray2.opt(1));
				
				goodskindmap.put("goodsKindID", jsonArray2.opt(0));
				goodskindmap.put("goodsKindName", jsonArray2.opt(1));
				goodskindlist.add(goodskindmap);
				}
			
			for (int i = 0; i < jsonArray1.length(); i++) {
				HashMap<String, Object> goodskindmap1 = new HashMap<String, Object>();
				JSONArray jsonArray3 = (JSONArray) jsonArray1.opt(i);
				System.out.println("jsonArray23"+jsonArray3.opt(1));
				
				goodskindmap1.put("goodsKindID", jsonArray3.opt(0));
				goodskindmap1.put("goodsKindName", jsonArray3.opt(1));
				goodskindlist1.add(goodskindmap1);
				}
			
		    
		    
		} catch (Exception e) {
			Toast.makeText(WharfWorkAddGoods.this, "网络异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


public void showNeirong(){  	 
		SimpleAdapter adapter = new SimpleAdapter(WharfWorkAddGoods.this,goodskindlist, R.layout.wharfwork_add_goods_item, new String[] {"goodsKindName", "goodsKindID" }, new int[] { R.id.wharfwork_add_goods_item_name,R.id.wharfwork_add_goods_item_id});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new Next());

}	
public void showNeirong1(){  	 
	SimpleAdapter adapter = new SimpleAdapter(WharfWorkAddGoods.this,goodskindlist1, R.layout.wharfwork_add_goods_item1, new String[] {"goodsKindName", "goodsKindID" }, new int[] { R.id.wharfwork_add_goods_item1_name,R.id.wharfwork_add_goods_item1_id});
	listView1.setAdapter(adapter);
	listView1.setOnItemClickListener(new Submit());

}	
class Next implements OnItemClickListener
{

	@Override
	public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
	{
		TextView tv_id = (TextView) arg1
				.findViewById(R.id.wharfwork_add_goods_item_id);
		Intent intent = new Intent(WharfWorkAddGoods.this,
				WharfWorkAddGoodsSecend.class);
		intent.putExtra("goodsKindID", tv_id.getText().toString());
		startActivityForResult(intent, 100);
	}

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
		setResult(30, intent);
		finish();
	}

}

}
