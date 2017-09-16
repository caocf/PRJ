package com.huzhouport.integratedquery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.example.huzhouport.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;

/*@author 沈丹丹
 * */
public class BS_CBQZXX extends Fragment
{
	private List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

	private String getResult, title;
	private ExpandableListView expandlistview;
	private int	cpage= 1,maxpage;
	private View moreView;
	private MyAdapter adapter ;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getResult=getArguments().getString("getResult");
		title=getArguments().getString("title");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.bs_cbzsxx, container, false);
		expandlistview = (ExpandableListView) view.findViewById(R.id.bs_cbzsxx_datalist);
		moreView = inflater.inflate(R.layout.dateload, null);
		ShowListView();
		if (getResult.length()==0) {
			new GetDate().execute();
		} else
			initData(getResult);
		return view;
	}
	class GetDate extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);
			paramsDate.put("method", 6);
			paramsDate.put("scape", cpage);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
			try
			{
				 getResult =hfu.post(actionUrl, paramsDate);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			initData(getResult);
			super.onPostExecute(result);
		}

	}
	public void initData(String getResult) {
		JSONTokener jsonParser = new JSONTokener(getResult);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();

			JSONArray jsonArray = data.getJSONArray("list");
			maxpage = data.getInt("totalPage");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put("parent_QZH", jsonObject2.getString("QZH"));
				map.put("parent_SFGMC", jsonObject2.getString("SFGMC")+"--"+jsonObject2.getString("MDGMC"));
				parentList.add(map);

				ArrayList<HashMap<String, Object>> childlist = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> childmap = new HashMap<String, Object>();

				childmap.put("child_ID", jsonObject2.getString("ID"));
				childmap.put("child_ZWCM", jsonObject2.getString("ZWCM"));
				childmap.put("child_QZSJ", CountTime.FormatTime(jsonObject2.getString("QZSJ")));
				childmap.put("child_QZDD", jsonObject2.getString("QZDD"));
				childmap.put("child_JCGSJ", CountTime.FormatTimeToDay(jsonObject2.getString("JCGSJ")));
				childmap.put("child_SFGMC", jsonObject2.getString("SFGMC"));
				childmap.put("child_MDGMC", jsonObject2.getString("MDGMC"));
				childmap.put("child_QZLXMC", jsonObject2.getString("QZLXMC"));
				childmap.put("child_QZH", jsonObject2.getString("QZH"));
				childmap.put("child_ZKRS", jsonObject2.getString("ZKRS"));
				childmap.put("child_ZHL", jsonObject2.getString("ZHL"));
				childmap.put("child_CYRS", jsonObject2.getString("CYRS"));
				childmap.put("child_QZRY", jsonObject2.getString("QZRY"));
				childmap.put("child_JLSJ", CountTime.FormatTimeToDay(jsonObject2.getString("JLSJ")));
				if(jsonObject2.getInt("JCGLX")==0){
					childmap.put("child_JCGLK","出港");
				}
				else if(jsonObject2.getInt("JCGLX")==1){
					childmap.put("child_JCGLK","进港");
				}
				childlist.add(childmap);
				allchildList.add(childlist);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 显示列表
		adapter.notifyDataSetChanged();
		
		if (cpage >= maxpage)
		{   moreView.setVisibility(View.GONE);
			expandlistview.removeFooterView(moreView); // 移除底部视图
		}
		moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
		((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.moreing);
	}

	public void ShowListView(){
		adapter=new MyAdapter(getActivity());
		expandlistview.addFooterView(moreView); 
		expandlistview.setAdapter(adapter);
		expandlistview.setGroupIndicator(null);
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		expandlistview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1))
					{
						moreView.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text)).setText(R.string.more);

						
						if (cpage < maxpage)
						{
							cpage += 1;
							new GetDate().execute();
						}
						
					}
				}				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	static BS_CBQZXX newInstance(String getResult,String title)
	{
		BS_CBQZXX newFragment = new BS_CBQZXX();
		Bundle obundle = new Bundle();
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

	}
	class MyAdapter extends BaseExpandableListAdapter {
		Context context;
		LayoutInflater mlayoutInflater;

		MyAdapter(Context context) {
			this.context = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {

			return allchildList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {

			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			convertView = mlayoutInflater.inflate(R.layout.cbqzxx_child, null);

			TextView tv_1 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt012);
			TextView tv_2 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt022);
			TextView tv_3 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt032);
			TextView tv_4 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt042);
			TextView tv_5 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt052);
			TextView tv_6 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt062);
			TextView tv_7 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt072);
			TextView tv_8 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt082);
			TextView tv_9 = (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt092);
			TextView tv_10= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt102);
			TextView tv_11= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt112);		
			TextView tv_12= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt122);
			TextView tv_13= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt132);
			TextView tv_14= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt142);		
			TextView tv_15= (TextView) convertView
					.findViewById(R.id.cbqzxx_child_txt152);
				
				
			tv_1.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_ID").toString());
			tv_2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZWCM").toString());
			tv_3.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_QZSJ").toString());
			tv_4.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_QZDD").toString());
			tv_5.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JCGSJ").toString());
			tv_6.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_SFGMC").toString());
			tv_7.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_MDGMC").toString());
			tv_8.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_QZLXMC").toString());
			tv_9.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_QZH").toString());
			tv_10.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_ZKRS").toString());
			tv_11.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZHL").toString());
			tv_12.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_CYRS").toString());
			tv_13.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_QZRY").toString());
			tv_14.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JLSJ").toString());
			tv_15.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JCGLK").toString());

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {

			return allchildList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {

			return allchildList.get(groupPosition);
		}

		@Override
		public int getGroupCount() {

			return allchildList.size();
		}

		@Override
		public long getGroupId(int groupPosition) {

			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			convertView = mlayoutInflater.inflate(R.layout.cbqzxx, null);

			TextView textParent = (TextView) convertView
					.findViewById(R.id.cbqzxx_grounptitle);
			TextView tv_ddTextView = (TextView) convertView
					.findViewById(R.id.cbqzxx_grounp_dd);

			textParent.setText(parentList.get(groupPosition).get("parent_QZH")
					.toString());
			tv_ddTextView.setText(parentList.get(groupPosition).get("parent_SFGMC")
					.toString());
			return convertView;
		}

		@Override
		public boolean hasStableIds() {

			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return false;
		}

	}
}
