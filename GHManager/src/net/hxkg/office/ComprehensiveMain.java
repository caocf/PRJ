package net.hxkg.office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import org.json.JSONArray;
import org.json.JSONException; 
import com.huzhouport.leaveandovertime.Query;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView; 

public class ComprehensiveMain extends Activity implements onResult,OnItemClickListener,OnScrollListener
{
	private Query query = new Query();

	private ImageButton pmback;

	private View moreView;
	private ListView pml;

	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;

	private SimpleAdapter adapter;
	int page=1;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comprehensive_main);

		pmback = (ImageButton) findViewById(R.id.comprehensive_main_back);
		 
		pmback.setOnClickListener(new MyBack());		
		creatListView();
		ReFreshData(page);

	}
	
	public void ReFreshData(int page)
	{
		HttpRequest hRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("page", page);
		hRequest.post(Constants.BaseURL+"CodexListLocal", map);
	}

	public void creatListView()
	{
		pml = (ListView) findViewById(R.id.comprehensive_main_list);
		adapter = new SimpleAdapter(ComprehensiveMain.this, listv,
				R.layout.portdynmicnews_list, new String[] { "titile",
						"date" }, new int[] {
						R.id.portdynmicnews_main_title,
						R.id.portdynmicnews_main_data });
		pml.setAdapter(adapter);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		pml.addFooterView(moreView); 
		pml.setOnItemClickListener(this);
		pml.setOnScrollListener(this);
		
	}

	class ProtDynmicNewsListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			Intent intent = new Intent(ComprehensiveMain.this,ComprehensiveSee.class);
			intent.putExtra("url", (String) listv.get(arg2).get("url"));
		
			startActivity(intent);


		}
	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			finish();

		}

	}

	class AddMoreList implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			cpage += 1;
			String data = "page=" + cpage;
			new ListTask(ComprehensiveMain.this).execute(data);
		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			pDialog = new ProgressDialog(ComprehensiveMain.this);			
			pDialog.setMessage("数据加载中");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{

				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);
				}
			});
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			String result;

			result = query.showComprehensive(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			//creatListView(result);
			if (adapter != null)
			{
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage)
			{
				moreView.setVisibility(View.GONE);
				pml.removeFooterView(moreView); 
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}

	}

	@SuppressLint("ShowToast")
	class ListTaskList extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		@Override
		protected String doInBackground(String... params)
		{
			String result;
			result = query.showComprehensive(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			//creatListView(result);
			if (adapter != null)
			{
				adapter.notifyDataSetChanged();
			}
			if (cpage >= maxpage)
			{
				moreView.setVisibility(View.GONE);
				pml.removeFooterView(moreView); 
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			
			super.onPostExecute(result);
		}

	}

	@Override
	public void onSuccess(String result)
	{
		moreView.setVisibility(View.GONE);
		if(result==null||"".equals(result.trim()))
		{
			adapter.notifyDataSetChanged();
			return;
		}
		try 
		{
			JSONArray array=new JSONArray(result.trim());
			for(int i=0;i<array.length();i++)
			{
				JSONArray array2=array.getJSONArray(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("titile", array2.get(1));
				map.put("date", array2.getString(2).split(" ")[0]);
				map.put("content", array2.get(0));//ID
				listv.add(map);
				
			}
			adapter.notifyDataSetChanged();
			
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
	{
		int p=view.getLastVisiblePosition();
		if(p==totalItemCount-1)//已拉到底部
		{
			moreView.setVisibility(View.VISIBLE);
			ReFreshData(page++);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Intent intent=new Intent(this,ComprehensiveSee.class);
		intent.putExtra("content", (int)listv.get(position).get("content"));
		startActivity(intent);
		
	}

}
