package com.huzhouport.wharfwork;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class WharfWorkSearch extends Activity
{
	private ListView							lv;
	private Query								query	= new Query();
	private ArrayList<HashMap<String, Object>>	wharfwork;
    private User user;
    private View						moreView;						// ���ظ���ҳ��
    private SimpleAdapter				adapter;
    private int							cpage			= 1 , maxpage;
    
    private String content;
    private EditText searchtext; // ������
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_search);

		lv = (ListView) findViewById(R.id.wharfwork_search_viewlist);

		//���ظ���
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);

		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_search_back);
		back.setOnClickListener(new Back());

		searchtext=(EditText) findViewById(R.id.wharfwork_search_searchtextname);
		
		ImageButton search = (ImageButton) findViewById(R.id.wharfwork_search_searchbutton);
		search.setOnClickListener(new Search());
	}

	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (20 == resultCode)
		{
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getWharfWork(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONObject data1=data.getJSONObject("pagemodel");
			maxpage=data1.getInt("totalPage");
			JSONArray jsonArray =data1.getJSONArray("recordsDate");
			//JSONArray jsonArray = data.getJSONArray("pagemodel");
			//JSONArray jsonArray1 = jsonArray.getJSONArray("recordsDate").opt(0);
			
			
			if (jsonArray.length() == 0)
			{
				Toast.makeText(WharfWorkSearch.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
			}
			{
				//wharfwork = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> wharfworkmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					
					if(jsonObject.getString("portMart").equals("1")){
						wharfworkmap.put("imgleft", R.drawable.wharf_in);
					}else{
						wharfworkmap.put("imgleft", R.drawable.wharf_out);
					}
					
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("declareTime").substring(0,jsonObject.getString("declareTime").lastIndexOf(":"));
					String text3 = jsonObject.getString("startingPortName")
							+ " -- " + jsonObject.getString("arrivalPortName");
					String text4 = jsonObject.getString("id");
				   String text5 = jsonObject.getString("cargoTypes")+jsonObject.getString("carrying")+"��";
				
					wharfworkmap.put("text1", text1);
					wharfworkmap.put("text2", text2);
					wharfworkmap.put("text3", text3);
					wharfworkmap.put("text4", text4);
					wharfworkmap.put("text5", text5);
					wharfwork.add(wharfworkmap);
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(WharfWorkSearch.this, "��������",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void showWharfWork()
	{
		wharfwork = new ArrayList<HashMap<String, Object>>();
		 adapter = new SimpleAdapter(
				WharfWorkSearch.this, wharfwork,
				R.layout.wharfwork_item, new String[] {"imgleft", "text1",
						"text2", "text3", "text4", "text5" }, new int[] {
						R.id.wharfwork_item_leftimg,
						R.id.wharfwork_item_name,
						R.id.wharfwork_item_time,
						R.id.wharfwork_item_port,
						R.id.wharfwork_item_id,
						R.id.wharfwork_item_co });
		 lv.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		lv.setAdapter(adapter);
		//adapter.notifyDataSetChanged(); //ˢ������
		moreView.setVisibility(View.GONE);
		lv.setOnItemClickListener(new WharfWorkItem());
		
		lv.setOnScrollListener(new OnScrollListener()
		{

			public void onScrollStateChanged(AbsListView view,int scrollState)
			{
				// ��������ʱ
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					// �жϹ������ײ�
					if (view.getLastVisiblePosition() == (view.getCount() - 1))
					{
						moreView.setVisibility(View.VISIBLE);
						moreView.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.more);
						LoadList();
					}
				}

			}

			@Override
			public void onScroll(AbsListView view,int firstVisibleItem,
					int visibleItemCount,int totalItemCount)
			{
			}
		});
	}

	
	private void LoadList()
	{

		System.out.println("page=="+cpage+" maxpage=="+maxpage);
		
		if (cpage < maxpage)
		{
			cpage += 1;
			new ListTask1().execute();
		}
		

	}
	
	class WharfWorkItem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
		{
			
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.wharfwork_item_id);
			Intent intent = new Intent(WharfWorkSearch.this,
					WharfWorkView.class);
			intent.putExtra("id", tv_id.getText().toString());
			startActivity(intent);
			if(user!=null){
			Log log = new Log(user.getName(),"�鿴��ͷ��ҵ����",GlobalVar.LOGSEE,"");  // ��־
			log.execute();
			}
		}

	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
      
			finish();
		}
	}

	public class Search implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content=searchtext.getText().toString();
		       if(content.length()==0){
		    		Toast.makeText(WharfWorkSearch.this, "����������������������", Toast.LENGTH_SHORT).show();
		       }else{
		    	  // wharfwork = new ArrayList<HashMap<String, Object>>();
		    	   showWharfWork();
		    	   ListTask task = new ListTask(WharfWorkSearch.this);  // �첽
				    task.execute();
		       }
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;

		
		public ListTask(Context context)
		{
			  pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
				pDialog.show();	
		}
		@Override
		protected String doInBackground(String... params)
		{
			
			if(isCancelled()) return null;//ȡ���첽


		        cpage=1;
		        String 	result = query.selectWharfWork1(content,cpage);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getWharfWork(result);
			adapter.notifyDataSetChanged();//ˢ��
			
			//showWharfWork();// ��ʾ�б���Ϣ
			super.onPostExecute(result);
		}

	}
	class ListTask1 extends AsyncTask<String, Integer, String>
	{

		protected String doInBackground(String... params)
		{
			String 	result = query.selectWharfWork1(content,cpage);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
		
			getWharfWork(result);
			
			//���ظ���
			adapter.notifyDataSetChanged();
			if (cpage >= maxpage)
			{   moreView.setVisibility(View.GONE);
				lv.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				//Toast.makeText(IllegalSearch.this, "�Ѽ���ȫ������", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
			
			//showWharfWork();// ��ʾ�б���Ϣ
			super.onPostExecute(result);
		}

	}
	
}
