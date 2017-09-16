package com.huzhouport.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class PortNavigationMain extends Activity{
	private Query query = new Query();
	//private TextView pnttn, pnltn, pnldn;
	private ImageButton pnbackn,pnsearch;
	private ListView pnln;
	private User user;
	//private String userId, userName;
	//private Button loadmoren;
	private int cpage = 1, maxpage;
	//private boolean is_page;
	private SimpleAdapter adapter;
	private View moreView;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.portnavigation_main);
		/*TextView pnttn = (TextView) findViewById(R.id.portnavigation_main_titleText);
		TextView pnltn = (TextView) findViewById(R.id.portnotice_main_title);
		TextView pnldn = (TextView) findViewById(R.id.portnotice_main_data);*/
		pnbackn = (ImageButton) findViewById(R.id.portnavigation_main_back);
		pnsearch = (ImageButton) findViewById(R.id.portnavigation_main_search);
		pnln = (ListView) findViewById(R.id.portnavigation_main_listview);
		//loadmoren = (Button) findViewById(R.id.portnotice_loadMoreButton);
		pnbackn.setOnClickListener(new MyBack());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		// ������ظ���
		//loadmoren.setOnClickListener(new AddMoreList());
		Intent intent = getIntent(); 
		user=(User) intent.getSerializableExtra("User");
		//userId = intent.getStringExtra("userId");
		//userName = intent.getStringExtra("userName");
		pnln.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		String data = "cpage=" + cpage;
		pnsearch.setOnClickListener(new Search());
		new ListTask(this).execute(data);//�첽
	}

	// �б�
	public void creatListView(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
		String errorInfo=data.getString("errorInfo");
		if(null!=errorInfo&&!errorInfo.equals("")&&!errorInfo.equals("null")){
			Toast.makeText(PortNavigationMain.this, "�����������...", Toast.LENGTH_SHORT).show();

		}else{
		// �������ľ���JSON����Ĳ�����
		JSONArray jsonArray = data.getJSONArray("list");
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("titile", jsonObject.get("titile"));
			map.put("date", jsonObject.get("date"));
			map.put("url", jsonObject.get("url"));
			listv.add(map);
		}
		adapter = new SimpleAdapter(PortNavigationMain.this,
				listv, R.layout.portnotice_list, new String[] { "titile",
						"date" }, new int[] { R.id.portnotice_main_title,
						R.id.portnotice_main_data });
		if(cpage==1){
			pnln.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		pnln.setOnItemClickListener(new PortNavigationMainListener());
		pnln.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view,
					int scrollState) {
				// ��������ʱ
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

//					// �жϹ������ײ�
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						// Ȼ�� ����һЩҵ�����
						if (cpage < maxpage) {
							moreView.findViewById(R.id.progressBar2)
									.setVisibility(View.VISIBLE);
							((TextView) moreView
									.findViewById(R.id.loadmore_text))
									.setText(R.string.more);
							cpage += 1;
							String data = "cpage=" + cpage;
							new ListTaskList(PortNavigationMain.this).execute(data);
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
	// ����
		public class Search implements View.OnClickListener {
			public void onClick(View v) {
				// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
//				findViewById(R.id.electricreport_table2)
//						.setVisibility(View.VISIBLE);
				Intent intent = new Intent(PortNavigationMain.this,
						PortNavigationSearch.class);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);
			}
		}

	class PortNavigationMainListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(PortNavigationMain.this,
					PortNavigationSee.class);
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
				// ���ظ��෽��
				cpage += 1;
				String data = "cpage=" + cpage;
				new ListTask(PortNavigationMain.this).execute(data);
				// creatListView(result);
			}

		}
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
		  //pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���", true); 
	    	  pDialog = new ProgressDialog(PortNavigationMain.this);
				pDialog.setTitle("��ʾ");
				pDialog.setMessage("�������ڼ����У����Ժ򡤡���");
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
				pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
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
			
			String result = null;
				try {
					result=query.showPortnavigation("",params[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				pnln.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				//Toast.makeText(PortNoticeMain.this, "�Ѽ���ȫ������", 3000).show();
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
	      public ListTaskList(Context context){}
		  
		@Override
		protected String  doInBackground(String... params) {
		//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
		//showDangerousgoodsportln();
			
			String result = null;
				try {
					result=query.showPortnavigation("",params[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				pnln.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				Toast.makeText(PortNavigationMain.this, "�Ѽ���ȫ������", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
			super.onPostExecute(result);
		}
		  
	  }
}