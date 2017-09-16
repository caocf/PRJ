package com.huzhouport.abnormal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class AbNormalListDeal extends Activity implements OnScrollListener,OnItemClickListener
{
	String potnameString;
	
	private ListView lv;
	SimpleAdapter adapter;
	ArrayList<HashMap<String, Object>> showlist=new ArrayList<HashMap<String, Object>>();
	ArrayList<AbnormalInfo> abList=new ArrayList<AbnormalInfo>();
	private View moreView;
	public User user;
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		user=(User) this.getIntent().getSerializableExtra("User");
		
		this.setContentView(R.layout.abnormallistdeal_activity);	
		moreView = this.getLayoutInflater().inflate(R.layout.dateload, null);
		potnameString=this.getIntent().getStringExtra("potname");
		
		lv=(ListView) this.findViewById(R.id.lv_abnormals);
		lv.setOnScrollListener(this);
		lv.setOnItemClickListener(this);
		setAbNormalList();
		
	}	
	
	private void setAbNormalList()
	{
		new ListTask(this,true).execute("0");
				
		adapter=new SimpleAdapter(this,showlist,R.layout.abnormal_list_itemprocess
												,new String[]{"shipname","gatename","time"}
												,new int[]{R.id.tx_item_shipname,R.id.tx_item_gatename
															,R.id.tx_item_time});
		lv.addFooterView(moreView);
		lv.setAdapter(adapter);
		moreView.setVisibility(View.GONE);
	}
	
	public  class ListTask extends AsyncTask<String, Integer, String> 
	{
		ProgressDialog pDialog = null;
		boolean isnewp;
		
		public ListTask(Context context,boolean isnewPage) 
		{			
			isnewp=isnewPage;
			if(isnewPage)
			{
				pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
				pDialog.show();
			}	
			
		}

		@Override
		protected String doInBackground(String...totalPage )
		{
			if (isCancelled())
				return null;// 取消异步

			HttpFileUpTool hft = new HttpFileUpTool();
			Map<String, Object> map = new HashMap<String, Object>();
			
			String result = null;
			try
			{
				//URLEncoder.encode(potname,"utf-8")
				map.put("potname", new String(potnameString.getBytes(),"utf-8"));
				map.put("state", 1);
				
				
				map.put("totalPage", totalPage[0]);
				map.put("allTotal", 10);
				
				//HttpUtil.BASE_URL "http://192.168.1.100:8080/HuZhouPort/"
				result = hft.post(HttpUtil.BASE_URL+"AbNormalListByStatusAndPot", map);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) 
		{			
			if (pDialog != null)
				pDialog.dismiss();
			
			if (result == null) 
			{
				Toast.makeText(AbNormalListDeal.this, "网络异常",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				GetJson(result);
				adapter.notifyDataSetChanged();
				moreView.findViewById(R.id.progressBar2).setVisibility(
						View.GONE);
				((TextView) moreView.findViewById(R.id.loadmore_text))
						.setText(R.string.moreing);
				lv.invalidateViews();
			}
			
			super.onPostExecute(result);
		}
		
		private void GetJson(String result) 
		{
			// System.out.println("result="+result);
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try 
			{
				data = (JSONObject) jsonParser.nextValue();

				JSONArray jsonArray = data.getJSONArray("list");
				// System.out.println("jsonArray="+jsonArray);
				if (jsonArray.length() != 0) 
				{
					if(isnewp)
					{
						showlist.clear();
						abList.clear();
					}
					
					for (int i = 0; i < jsonArray.length(); i++) 
					{
						HashMap<String, Object> showlistmap = new HashMap<String, Object>();
						JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
						String aid = jsonObject.getString("aid");
						String shipname = jsonObject.getString("shipname");
						String gatename = jsonObject.getString("gatename");
						String abtime = jsonObject.getString("abdate");
						String route = jsonObject.getString("route");
						String AIS = jsonObject.getString("AIS");
						String blacklist = jsonObject.getString("blacklist");
						String arrearage = jsonObject.getString("arrearage");
						String report = jsonObject.getString("report");
						String illegal = jsonObject.getString("illegal");
						String cert = jsonObject.getString("cert");
						String process = jsonObject.getString("process");
						String pass = jsonObject.getString("pass");
						String remark= jsonObject.getString("remark");
						String status = jsonObject.getString("status");
						
						showlistmap.put("shipname", shipname);
						showlistmap.put("gatename", gatename);
						showlistmap.put("time", abtime);
						
						showlist.add(showlistmap);
						
						AbnormalInfo abnormalInfo=new AbnormalInfo();
						abnormalInfo.setAid(Integer.valueOf(aid));
						abnormalInfo.setShipname(shipname);
						abnormalInfo.setGatename(gatename);
						abnormalInfo.setRoute(route);
						abnormalInfo.setAIS(AIS);
						abnormalInfo.setBlacklist(blacklist);
						abnormalInfo.setArrearage(arrearage);
						abnormalInfo.setReport(report);
						abnormalInfo.setIllegal(illegal);
						abnormalInfo.setCert(cert);
						abnormalInfo.setProcess(process);
						abnormalInfo.setPass(pass);
						abnormalInfo.setRemark(remark);
						abnormalInfo.setStatus(status);
						abList.add(abnormalInfo);
					}
				}
			} 
			catch (Exception e) 
			{
				Toast.makeText(AbNormalListDeal.this, "暂无数据",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

		}
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent = new Intent(AbNormalListDeal.this,AbNormalViewProcessed.class);
		intent.putExtra("abinfo", abList.get(position));
		intent.putExtra("User",user);
		this.startActivityForResult(intent, 20);
		
	}

	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {
		new ListTask(this,true).execute("0");
    }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// 当不滚动时
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) 
		{
			// 判断滚动到底部
			if (view.getLastVisiblePosition() == (view.getCount() - 1)) 
			{				
				moreView.setVisibility(View.VISIBLE);
				moreView.findViewById(R.id.progressBar2).setVisibility(
						View.VISIBLE);
				((TextView) moreView.findViewById(R.id.loadmore_text))
						.setText(R.string.more);
				new ListTask(AbNormalListDeal.this,false).execute(String.valueOf(view.getCount()));
			}
		}		
	}



	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
}
