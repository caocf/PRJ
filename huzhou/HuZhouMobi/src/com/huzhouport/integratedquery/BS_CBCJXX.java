package com.huzhouport.integratedquery;

/*@author 沈丹丹
 * */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
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

public class BS_CBCJXX extends Fragment {
	private List<Map<String, Object>>						parentList		= new ArrayList<Map<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>>	allchildList	= new ArrayList<ArrayList<HashMap<String, Object>>>();
	ExpandableListView										expandlistview;
	TextView content01, content02, content03, content04, content05, content06,
			content07;
	private String getResult, title;
	private int	cpage= 1,maxpage;
	private View moreView;
	private MyAdapter adapter ;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getResult=getArguments().getString("getResult");
		title=getArguments().getString("title");
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.bs_cbcjxx, container, false);
		expandlistview=(ExpandableListView)view.findViewById(R.id.bs_cbcjxx_datalist);
		moreView = inflater.inflate(R.layout.dateload, null);
		ShowExpandableListView();
		if (getResult.length() == 0) {
			new GetDate().execute();
		} else
			iniDate();
		return view;
	}
	static BS_CBCJXX newInstance(String getResult,String title) {
		BS_CBCJXX newFragment = new BS_CBCJXX();
		Bundle obundle = new Bundle();   
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

	}
	public void ShowExpandableListView(){

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
	class GetDate extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);
			paramsDate.put("method", 4);
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
			iniDate();
			super.onPostExecute(result);
		}

	}

	private void iniDate() {
		try {
			JSONTokener jsonParser = new JSONTokener(getResult);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			maxpage = data.getInt("totalPage");
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put("parent_title", jsonObject2.getString("JYBH"));
				map.put("parent_xcjyrq", CountTime.FormatTimeToDay(jsonObject2.getString("XCJYRQ")));
				map.put("parent_jywcrq", CountTime.FormatTimeToDay(jsonObject2.getString("JYWCRQ")));
				map.put("parent_jyksrq", CountTime.FormatTimeToDay(jsonObject2.getString("JYKSRQ")));
				
				parentList.add(map);

				ArrayList<HashMap<String, Object>> childlist = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> childmap = new HashMap<String, Object>();

				childmap.put("child_JYBH", jsonObject2.getString("JYBH"));
				childmap.put("child_CJDJH", jsonObject2.getString("CJDJH"));
				childmap.put("child_ZWCM", jsonObject2.getString("ZWCM"));
				childmap.put("child_JYDD", jsonObject2.getString("JYDD"));
				childmap.put("child_JYDWMC", jsonObject2.getString("JYDWMC"));
				childmap.put("child_JYBM", jsonObject2.getString("JYBM"));
				childmap.put("child_SQR", jsonObject2.getString("SQR"));
				childmap.put("child_JYZL", jsonObject2.getString("JYZL"));
				childmap.put("child_XCJYRQ", CountTime.FormatTimeToDay(jsonObject2.getString("XCJYRQ")));		
				childmap.put("child_QTJY", jsonObject2.getString("QTJY"));
				childmap.put("child_SFWC", jsonObject2.getString("SFWC"));
				childmap.put("child_BZ", jsonObject2.getString("BZ"));
				childlist.add(childmap);
				allchildList.add(childlist);
			}

		} catch (Exception e) {
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

			convertView = mlayoutInflater.inflate(R.layout.cbcjxx_child, null);

			TextView tv_1 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt012);
			TextView tv_2 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt022);
			TextView tv_3 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt032);
			TextView tv_4 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt042);
			TextView tv_5 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt052);
			TextView tv_6 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt062);
			TextView tv_7 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt072);
			TextView tv_8 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt082);
			TextView tv_9 = (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt092);
			TextView tv_10= (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt102);
			TextView tv_11= (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt112);		
			TextView tv_12= (TextView) convertView
					.findViewById(R.id.cbcjxx_child_txt122);
		
			tv_1.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_JYBH").toString());
			tv_2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZWCM").toString());
			tv_3.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_CJDJH").toString());
			tv_4.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_JYDD").toString());
			tv_5.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JYDWMC").toString());
			tv_6.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JYBM").toString());
			tv_7.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_SQR").toString());
			tv_8.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JYZL").toString());
			tv_9.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_QTJY").toString());
			tv_10.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_SFWC").toString());
			tv_11.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_XCJYRQ").toString());
			tv_12.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_BZ").toString());

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

			convertView = mlayoutInflater.inflate(R.layout.cbcjxx_group, null);

			TextView textParent = (TextView) convertView
					.findViewById(R.id.cbcjxx_grounptitle);
			TextView tv_01 = (TextView) convertView
					.findViewById(R.id.cbcjxx_grounp12);
			TextView tv_02 = (TextView) convertView
					.findViewById(R.id.cbcjxx_grounp22);
			TextView tv_03 = (TextView) convertView
					.findViewById(R.id.cbcjxx_grounp32);

			textParent.setText(parentList.get(groupPosition).get("parent_title")
					.toString());
			tv_01.setText(parentList.get(groupPosition).get("parent_jyksrq")
					.toString());
			tv_02.setText(parentList.get(groupPosition).get("parent_jywcrq")
					.toString());
			tv_03.setText(parentList.get(groupPosition).get("parent_xcjyrq")
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
