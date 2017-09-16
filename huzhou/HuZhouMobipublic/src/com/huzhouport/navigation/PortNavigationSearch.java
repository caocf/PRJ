package com.huzhouport.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

public class PortNavigationSearch extends Activity{
	private Query query = new Query();
	private TextView pnttns, pnltns, pnldns;
	private ImageButton pnbackns;
	private ListView pnlns;
	private User user;
	private String userId, userName;
	private Button loadmoren;
	private int cpage = 1, maxpage;
	private String param;// ����
	private ClearEditText searchtext; // ������
	private String queryCondition = "";// ����������
	private boolean is_page;
	private SimpleAdapter adapter;
	private View moreView;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.portnavigation_search);
		pnltns = (TextView) findViewById(R.id.portnotice_main_title);
		pnldns = (TextView) findViewById(R.id.portnotice_main_data);
		pnbackns = (ImageButton) findViewById(R.id.portnavigation_search_back);
		pnlns = (ListView) findViewById(R.id.portnavigation_search_list);
		
		searchtext = (ClearEditText) findViewById(R.id.portnavigation_search_edit01);
		pnbackns.setOnClickListener(new MyBack());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		// ������ظ���
		//loadmoren.setOnClickListener(new AddMoreList());
		Intent intent = getIntent(); 
		user=(User) intent.getSerializableExtra("User");
		userId = intent.getStringExtra("userId");
		userName = intent.getStringExtra("userName");
		pnlns.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		String data = "cpage=" + cpage;
		// ������ť
		ImageButton pnsearch = (ImageButton) findViewById(R.id.portnavigation_search_bt);
		pnsearch.setOnClickListener(new searchOrBack());
		new ListTask(this).execute(data);//�첽
		searchtext.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = searchtext.getText().toString();

			}

		});
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
			Toast.makeText(PortNavigationSearch.this, "�����������...", Toast.LENGTH_SHORT).show();

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
		adapter = new SimpleAdapter(PortNavigationSearch.this,
				listv, R.layout.portnotice_list, new String[] { "titile",
						"date" }, new int[] { R.id.portnotice_main_title,
						R.id.portnotice_main_data });
		if(cpage==1){
			pnlns.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		pnlns.setOnItemClickListener(new PortNavigationMainListener());
		pnlns.setOnScrollListener(new OnScrollListener() {

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
							new ListTaskList(PortNavigationSearch.this).execute(data);
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
				Intent intent = new Intent(PortNavigationSearch.this,
						PortNavigationSearch.class);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);
			}
		}

	class PortNavigationMainListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(PortNavigationSearch.this,
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
				new ListTask(PortNavigationSearch.this).execute(data);
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
	    	  pDialog = new ProgressDialog(PortNavigationSearch.this);
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
				pnlns.removeFooterView(moreView); // �Ƴ��ײ���ͼ
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
	      @SuppressWarnings("deprecation")
		public ListTaskList(Context context){
		 // pDialog = ProgressDialog.show(context, "��ʾ", "���ڼ����У����Ժ򡣡���", true); 
//	    	  pDialog = new ProgressDialog(PortNoticeMain.this);
//				pDialog.setTitle("��ʾ");
//				pDialog.setMessage("�������ڼ����У����Ժ򡤡���");
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
//				pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
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
				pnlns.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				Toast.makeText(PortNavigationSearch.this, "�Ѽ���ȫ������", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
			super.onPostExecute(result);
		}
		  
	  }
	 class searchOrBack implements OnClickListener {

			@Override
			public void onClick(View v) {

				listv = new ArrayList<Map<String, Object>>();
				queryCondition = searchtext.getText().toString();				
				param = "&titile=" + queryCondition;
				new ListTask(PortNavigationSearch.this).execute(param);

			}

		}
}
