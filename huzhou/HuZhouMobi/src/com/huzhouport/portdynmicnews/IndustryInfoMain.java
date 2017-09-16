package com.huzhouport.portdynmicnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �ۺ���̬
 * 
 * @author Administrator
 * 
 */
public class IndustryInfoMain extends  Fragment
{
	private Query query = new Query();
	private View moreView;
	private ListView pml;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;
	private SimpleAdapter adapter;

	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.portdynmicnews_fragment,
				null);
		// ��ӵײ���ͼ
				moreView =getActivity(). getLayoutInflater().inflate(R.layout.dateload, null);
				moreView.setVisibility(View.GONE);
				pml = (ListView) v.findViewById(R.id.portdynmicnews_main_list);
				pml.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
				adapter = new SimpleAdapter(getActivity(), listv, R.layout.portdynmicnews_list,
						new String[] { "title", "date" }, new int[] {
								R.id.portdynmicnews_main_title,
								R.id.portdynmicnews_main_data });
				pml.setAdapter(adapter);

				// ���õ���¼��������¼�
				pml.setOnItemClickListener(new ProtDynmicNewsListener());
				pml.setOnScrollListener(new scrollNewsListener());

				// ���ҵ�һҳ����
				String data = "page=" + cpage;
				new queryIndustryInfo(getActivity(), true).execute(data);
		return v;
	}

	/**
	 * �б����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class ProtDynmicNewsListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (arg2 < listv.size())
			{
				Intent intent = new Intent(getActivity(),
						IndusrtyInfoSee.class);
				intent.putExtra("id",  listv.get(arg2).get("id").toString());
				startActivity(intent);
			}
		}
	}

	/**
	 * �б�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class scrollNewsListener implements OnScrollListener
	{

		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
			// ��������ʱ���������ײ�ʱ
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == (view.getCount() - 1))
			{
				// Ȼ�� ����һЩҵ�����
				if (cpage < maxpage)
				{
					moreView.findViewById(R.id.progressBar2).setVisibility(
							View.VISIBLE);
					((TextView) moreView.findViewById(R.id.loadmore_text))
							.setText(R.string.more);
					cpage += 1;
					String data = "cpage=" + cpage;
					new queryIndustryInfo(getActivity(), false)
							.execute(data);
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount)
		{

		}
	}

	/**
	 * ��ѯ�ۺ���̬����
	 * 
	 * @author Administrator
	 * 
	 */
	class queryIndustryInfo extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		boolean showDialog;

		public queryIndustryInfo(Context context, boolean showDialog)
		{

			this.showDialog = showDialog;

			if (showDialog)
			{

				pDialog = new WaitingDialog().createDefaultProgressDialog(
						getActivity(), queryIndustryInfo.this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params)
		{
			return query.showIndustryInfo(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
 
			if(result==null){
				Toast.makeText(getActivity(), "�����쳣", Toast.LENGTH_SHORT).show();
			}else{
			parseNewDynmic(result);
			updateListview();
			}
			super.onPostExecute(result);
		}

	}

	/**
	 * �����¶�̬
	 * 
	 * @param result
	 *            �ۺ���̬json����
	 */
	public void parseNewDynmic(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("pages");
			String errorInfo = data.getString("total");
			if (errorInfo.equals("0"))
			{
				Toast.makeText(getActivity(), "�����������...",
						Toast.LENGTH_SHORT).show();

			} else
			{
				// �������ľ���JSON����Ĳ�����
				JSONArray jsonArray = data.getJSONArray("list");

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", jsonObject.get("title"));
					map.put("date", jsonObject.get("updatetime"));
					map.put("id", jsonObject.get("id").toString());
					listv.add(map);
				}
			}
		} catch (Exception e)
		{

			e.printStackTrace();
		}
	}

	/**
	 * �����������б�
	 */
	public void updateListview()
	{
		moreView.setVisibility(View.VISIBLE);

		if (adapter.getCount() > 0)
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
	}

}
