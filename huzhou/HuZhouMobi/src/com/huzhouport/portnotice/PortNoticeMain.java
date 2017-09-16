package com.huzhouport.portnotice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class PortNoticeMain extends Activity{
	private Query query = new Query();
	private TextView pntt, pnlt, pnld;
	private ImageButton pnback;
	private ListView pnl;
	private User user;
	private String userId, userName;
	private Button loadmoren;
	private int cpage = 1, maxpage;
	private boolean is_page;
	private SimpleAdapter adapter;
	private View moreView;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.portnotice_main);

		pntt = (TextView) findViewById(R.id.portnotice_main_titleText);
		pnlt = (TextView) findViewById(R.id.portnotice_main_title);
		pnld = (TextView) findViewById(R.id.portnotice_main_data);
		pnback = (ImageButton) findViewById(R.id.portnotice_main_back);
		pnl = (ListView) findViewById(R.id.portnotice_main_listview);
		//loadmoren = (Button) findViewById(R.id.portnotice_loadMoreButton);
		pnback.setOnClickListener(new MyBack());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		// 点击加载更多
		//loadmoren.setOnClickListener(new AddMoreList());
		Intent intent = getIntent(); 
		user=(User) intent.getSerializableExtra("User");
		userId = intent.getStringExtra("userId");
		userName = intent.getStringExtra("userName");
		pnl.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		String data = "cpage=" + cpage;
		new ListTask(this).execute(data);//异步
	}

	// 列表
	public void creatListView(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
		String errorInfo=data.getString("errorInfo");
		if(null!=errorInfo&&!errorInfo.equals("")&&!errorInfo.equals("null")){
			Toast.makeText(PortNoticeMain.this, "暂无相关数据...", Toast.LENGTH_SHORT).show();

		}else{
		// 接下来的就是JSON对象的操作了
		JSONArray jsonArray = data.getJSONArray("list");
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("titile", jsonObject.get("titile"));
			map.put("date", jsonObject.get("date"));
			map.put("url", jsonObject.get("url"));
			listv.add(map);
		}
		adapter = new SimpleAdapter(PortNoticeMain.this,
				listv, R.layout.portnotice_list, new String[] { "titile",
						"date" }, new int[] { R.id.portnotice_main_title,
						R.id.portnotice_main_data });
		if(cpage==1){
			pnl.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		pnl.setOnItemClickListener(new PortNoticeMainListener());
		pnl.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view,
					int scrollState) {
				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

//					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						// 然后 经行一些业务操作
						if (cpage < maxpage) {
							moreView.findViewById(R.id.progressBar2)
									.setVisibility(View.VISIBLE);
							((TextView) moreView
									.findViewById(R.id.loadmore_text))
									.setText(R.string.more);
							cpage += 1;
							String data = "cpage=" + cpage;
							new ListTaskList(PortNoticeMain.this).execute(data);
						}
					}
				}

			}

			@Override
			public void onScroll(AbsListView view,
					int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {
				//is_page=(firstVisibleItem+visibleItemCount==totalItemCount);
			}
		});
	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class PortNoticeMainListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(PortNoticeMain.this,
					PortNoticeSee.class);
			intent.putExtra("url", (String) listv.get(arg2).get("url"));
			intent.putExtra("User", user);
			startActivity(intent);
		}
	}
	/*
	 * intent.putString("",""); startActivity(intent);
	 */
	 class MyBack implements OnClickListener{

			@Override
			public void onClick(View v) {
				//Intent intent=new Intent(PortNoticeMain.this,OfficOA.class);
				//startActivity(intent);
				finish();
				
				
			}
			
		}
	 class AddMoreList implements OnClickListener {

			@Override
			public void onClick(View v) {
				// 加载更多方法
				cpage += 1;
				String data = "cpage=" + cpage;
				new ListTask(PortNoticeMain.this).execute(data);
				// creatListView(result);
			}

		}
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
		  //pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
	    	  pDialog = new ProgressDialog(PortNoticeMain.this);
				pDialog.setTitle("提示");
				pDialog.setMessage("数据正在加载中，请稍候···");
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
		protected String  doInBackground(String... params) {
		//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
		//showDangerousgoodsportln();
			
			String result;
				result=query.showPortNotice(params[0]);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			creatListView(result);
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			
			if (cpage >= maxpage) {
				moreView.setVisibility(View.GONE);
				pnl.removeFooterView(moreView); // 移除底部视图
				Toast.makeText(PortNoticeMain.this, "已加载全部数据", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
			super.onPostExecute(result);
		}
		  
	  }
	 @SuppressLint("ShowToast")
	class ListTaskList extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTaskList(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTaskList(Context context){
		 // pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true); 
//	    	  pDialog = new ProgressDialog(PortNoticeMain.this);
//				pDialog.setTitle("提示");
//				pDialog.setMessage("数据正在加载中，请稍候···");
//				pDialog.setCancelable(true);
//				pDialog.setOnCancelListener(new OnCancelListener()
//				{
//					
//					@Override
//					public void onCancel(DialogInterface dialog)
//					{
//						if (pDialog != null)
//							pDialog.dismiss();
//						if (ListTaskList.this != null && ListTaskList.this.getStatus() == AsyncTask.Status.RUNNING)
//							ListTaskList.this.cancel(true);
//						
//					}
//				});
//				pDialog.setButton("取消", new DialogInterface.OnClickListener()
//				{
//					
//					@Override
//					public void onClick(DialogInterface dialog,int which)
//					{
//						if (pDialog != null)
//							pDialog.dismiss();
//						if (ListTaskList.this != null && ListTaskList.this.getStatus() == AsyncTask.Status.RUNNING)
//							ListTaskList.this.cancel(true);
//						
//					}
//				});
//				pDialog.show();	
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
		//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
		//showDangerousgoodsportln();
			
			String result;
				result=query.showPortNotice(params[0]);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			creatListView(result);
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage) {
				moreView.setVisibility(View.GONE);
				pnl.removeFooterView(moreView); // 移除底部视图
				Toast.makeText(PortNoticeMain.this, "已加载全部数据", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
			super.onPostExecute(result);
		}
		  
	  }
}