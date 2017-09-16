package com.huzhouport.portdynmicnews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.example.huzhouport.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.Query;
import com.huzhouport.portdynmicnews.PortdynmicnewsActivity.ProtDynmicNewsListener;
import com.huzhouport.portdynmicnews.PortdynmicnewsActivity.queryDynmicNews;
import com.huzhouport.portdynmicnews.PortdynmicnewsActivity.scrollNewsListener;

/**
 * 港航动态详情
 * @author Administrator
 *
 */
public class DynmicNewsSee1 extends Activity
{
	private Query query = new Query();
	private WebView tex;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private ListView comment;
	private View moreView;
	private SimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portdynmicnews_see1);
		tex = (WebView) findViewById(R.id.portdynmicnews_see_count);
		comment=(ListView) this.findViewById(R.id.portdynmicnews_main_list);
		moreView =getLayoutInflater().inflate(R.layout.dateload, null);
		moreView.setVisibility(View.GONE);
		comment.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		adapter = new SimpleAdapter(this, listv, R.layout.portdynmicnews_list,
				new String[] { "titile", "date" }, new int[] {
						R.id.portdynmicnews_main_title,
						R.id.portdynmicnews_main_data });
		comment.setAdapter(adapter);
		// 设置滚动事件
		comment.setOnScrollListener(new scrollNewsListener());
		//设置返回按钮
		CommonListenerWrapper.commonBackWrapperListener(
				R.id.portdynmicnews_see_back, this);

		//查找新闻
		String url = getIntent().getStringExtra("url");
		new queryNewsTask(this).execute(url); 
		
		//评论列表
		new queryComments(this, false).execute("0","1");

	}
	
	

	/**
	 * 列表滚动事件
	 * 
	 * @author Administrator
	 * 
	 */
	class scrollNewsListener implements OnScrollListener
	{

		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
			// 当不滚动时并滚动到底部时
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == (view.getCount() - 1))
			{
				// 然后 经行一些业务操作
				
					moreView.findViewById(R.id.progressBar2).setVisibility(
							View.VISIBLE);
					((TextView) moreView.findViewById(R.id.loadmore_text))
							.setText(R.string.more);
					
					new queryComments(DynmicNewsSee1.this, false).execute("1","0");
				
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount)
		{

		}
	}

	/**
	 * 查找新闻异步线程
	 * @author Administrator
	 *
	 */
	class queryNewsTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		
		public queryNewsTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(DynmicNewsSee1.this, queryNewsTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			return query.seePortDynmicNews(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			showNews(result);
			super.onPostExecute(result);
		}

	}
	//评论列表
	class queryComments extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		boolean showDialog;
		Context context;

		public queryComments(Context context, boolean showDialog)
		{
			this.context=context;
			this.showDialog = showDialog;

			if (showDialog)
			{
				pDialog = new WaitingDialog().createDefaultProgressDialog(context, queryComments.this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params)
		{
			String result=null;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("Page", params[0]);
			map.put("Newsid", params[1]);
			HttpFileUpTool postFileUpTool=new HttpFileUpTool();
			try
			{
				result=postFileUpTool.post("http://192.168.1.116/commentlist", map);
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
			if(result==null){
				Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
			}else{
				parseNewDynmic(result);
				updateListview();
			}
			super.onPostExecute(result);
		}

	}
	
	public void parseNewDynmic(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			if (data!=null)
			
			{
				// 接下来的就是JSON对象的操作了
				JSONArray jsonArray = data.getJSONArray("data");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("titile", jsonObject.get("username"));
					map.put("date", jsonObject.get("content"));
					listv.add(map);
				}
			}
		} catch (Exception e)
		{

			e.printStackTrace();
		}
	}
	/**
	 * 更新评论列表
	 */
	public void updateListview()
	{
		moreView.setVisibility(View.VISIBLE);

		if (adapter.getCount() > 0)
		{
			adapter.notifyDataSetChanged();
		}
		moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
		((TextView) moreView.findViewById(R.id.loadmore_text))
				.setText(R.string.moreing);
	}
	
	@SuppressLint("SetJavaScriptEnabled") 
	public void showNews(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONObject jo = data.getJSONObject("modelPortDynmicNews");
			WebSettings webSettings = tex.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			tex.loadDataWithBaseURL("",
					"<html><body>" + jo.getString("contenct")
							+ "</body></html>", "text/html", "utf-8", "");

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
