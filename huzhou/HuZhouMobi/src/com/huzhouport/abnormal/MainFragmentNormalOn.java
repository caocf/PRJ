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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainFragmentNormalOn extends Fragment implements OnScrollListener,OnItemClickListener
{	
	private SharedPreferences sp;
	SharedPreferences.Editor editor;
	private Activity activity;
	
	private TextView tx_pot ;
	private ListView lv;	
	SimpleAdapter adapter;
	
	public static String potname="";
	ArrayList<HashMap<String, Object>> showlist=new ArrayList<HashMap<String, Object>>();
	ArrayList<AbnormalInfo> abList=new ArrayList<AbnormalInfo>();
	
	private View moreView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		activity=this.getActivity();
		sp=activity.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);
		moreView = activity.getLayoutInflater().inflate(R.layout.dateload, null);
		potname=sp.getString("potname", "请选择站点");
		
		View rootView = inflater.inflate(R.layout.abnormallist_fragment_on, container,false);		
		tx_pot=(TextView) rootView.findViewById(R.id.tx_pot);
		tx_pot.setText(potname);
		lv=(ListView) rootView.findViewById(R.id.lv_abnormals);
		lv.setOnScrollListener(this);
		lv.setOnItemClickListener(this);
		setAbNormalList();		
		
		
		return rootView;
	}
	
	public void ChoosePot(View v)
	{
		Intent intent=new Intent(this.getActivity(),Pots.class);
		activity.startActivityForResult(intent, AbNormalList.REQUEST_CODE); 
	}
	
	public void NewPotFresh(String potname)
	{
		//if("".equals(potname))
		tx_pot.setText(potname);
		this.potname=potname;
		editor=sp.edit();
		editor.putString("potname", potname);
		editor.commit();
		showlist.clear();
		new ListTask(activity,true).execute("0");
		
	}
	
	private void setAbNormalList()
	{
		new ListTask(activity,true).execute("0");
				
		adapter=new SimpleAdapter(this.getActivity(),showlist,R.layout.abnormal_list_item
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
				map.put("potname", new String(potname.getBytes(),"utf-8"));
				map.put("state", 0);
				
				
				map.put("totalPage", totalPage[0]);
				map.put("allTotal", 10);
				
				//"http://192.168.1.100:8080/HuZhouPort/"
				result = hft.post(HttpUtil.BASE_URL+"AbNormalListByStatusAndPot", map);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block.printStackTrace(); 
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
				Toast.makeText(activity, "网络异常",Toast.LENGTH_SHORT).show();
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
						String shipid = jsonObject.getString("shipid");
						
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
						abnormalInfo.setShipid(shipid);
						abList.add(abnormalInfo);
					}
				}
			} 
			catch (Exception e) 
			{
				Toast.makeText(activity, "暂无数据",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

		}
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
				new ListTask(activity,false).execute(String.valueOf(view.getCount()));
			}
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) 
	{
				
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent = new Intent(activity,AbNormalView.class);
		intent.putExtra("abinfo", abList.get(position));
		intent.putExtra("User", ((AbNormalList)activity).user);
		activity.startActivityForResult(intent, 12);
	}
	
}
