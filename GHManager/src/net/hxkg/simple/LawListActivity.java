package net.hxkg.simple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
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
import android.widget.Toast;

public class LawListActivity extends Activity implements OnItemClickListener
{
	private Query query = new Query();

	private ImageButton pmback;

	private View moreView;
	private ListView pml;

	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;

	private SimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comprehensive_main);

		pmback = (ImageButton) findViewById(R.id.comprehensive_main_back);
		pml = (ListView) findViewById(R.id.comprehensive_main_list);
		adapter = new SimpleAdapter(this, listv,
				R.layout.portdynmicnews_list, new String[] { "titile",
						"date" }, new int[] {
						R.id.portdynmicnews_main_title,
						R.id.portdynmicnews_main_data });
		pml.setAdapter(adapter);
		pml.setOnItemClickListener(this);
		pmback.setOnClickListener(new MyBack());
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);

		pml.addFooterView(moreView); 
		String data = "page=" + cpage;
		new ListTask(this).execute(data);

	}

	public void creatListView(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
			String errorInfo = data.getString("errorInfo");
			if (null != errorInfo && !errorInfo.equals("")
					&& !errorInfo.equals("null"))
			{
				Toast.makeText(LawListActivity.this, "数据加载失败",Toast.LENGTH_SHORT).show();

			} else
			{
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("titile", jsonObject.get("titile"));
					map.put("date", jsonObject.get("date"));
					map.put("url", jsonObject.get("url"));
					listv.add(map);
				}
				adapter = new SimpleAdapter(LawListActivity.this, listv,
						R.layout.portdynmicnews_list, new String[] { "titile",
								"date" }, new int[] {
								R.id.portdynmicnews_main_title,
								R.id.portdynmicnews_main_data });

				if (cpage == 1)
				{
					pml.setAdapter(adapter);
				}
				adapter.notifyDataSetChanged();
				moreView.setVisibility(View.VISIBLE);

				pml.setOnItemClickListener(new ProtDynmicNewsListener());
				pml.setOnScrollListener(new OnScrollListener()
				{

					public void onScrollStateChanged(AbsListView view,
							int scrollState)
					{
						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
						{
							if (view.getLastVisiblePosition() == (view.getCount() - 1))
							{
								if (cpage < maxpage)
								{
									moreView.findViewById(R.id.progressBar2)
											.setVisibility(View.VISIBLE);
									((TextView) moreView
											.findViewById(R.id.loadmore_text))
											.setText(R.string.more);
									cpage += 1;
									String data = "page=" + cpage;
									new ListTaskList().execute(data);
								}
							}
						}
						

					}

					@Override
					public void onScroll(AbsListView view,int firstVisibleItem, int visibleItemCount,int totalItemCount)
					{
					}
				});

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	class ProtDynmicNewsListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			
			Map<String, Object> map = listv.get(arg2);
			String string= (String) map.get("titile");
			
			Intent intent = new Intent();
			intent.putExtra("name", string);
			setResult(1041,intent);
			finish();
		}
	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.putExtra("name", "");
			setResult(1041,intent);
			finish();

		}

	}

	class AddMoreList implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			cpage += 1;
			String data = "cpage=" + cpage;
			new ListTask(LawListActivity.this).execute(data);
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
			pDialog = new ProgressDialog(LawListActivity.this);			
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
			creatListView(result);
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
			creatListView(result);
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
	public void onBackPressed()
	{
		Intent intent = new Intent();
		intent.putExtra("name", "");
		setResult(1041,intent);
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent intent = new Intent();
		intent.putExtra("name", (String) listv.get(position).get("titile"));
		setResult(1041,intent);
		finish();
		
	}

}
