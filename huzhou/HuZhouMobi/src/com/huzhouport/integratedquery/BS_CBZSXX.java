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
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BS_CBZSXX extends Fragment {
	private List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

	private String getResult, title;
	private ExpandableListView expandlistview;
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
	static BS_CBZSXX newInstance(String getResult,String title) {
		BS_CBZSXX newFragment = new BS_CBZSXX();
		Bundle obundle = new Bundle();   
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

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
	class GetDate extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);
			paramsDate.put("method", 2);
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

	class MyListViewDemo implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TableRow tr01 = (TableRow) view.findViewById(R.id.cbzsxx_tr01);
			TableRow tr02 = (TableRow) view.findViewById(R.id.cbzsxx_tr02);
			TableRow tr03 = (TableRow) view.findViewById(R.id.cbzsxx_tr03);
			tr01.setVisibility(View.GONE);
			tr02.setVisibility(View.GONE);
			tr03.setVisibility(View.GONE);
			Toast.makeText(getActivity(), (int) (position + id),
					Toast.LENGTH_SHORT).show();
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
				map.put("time", jsonObject2.getString("outtime"));// 过期
				map.put("parent", jsonObject2.getString("ZSMC"));
				parentList.add(map);

				ArrayList<HashMap<String, Object>> childlist = new ArrayList<HashMap<String, Object>>();

				HashMap<String, Object> childmap = new HashMap<String, Object>();

				childmap.put("child_id", jsonObject2.getString("ZSBH"));
				childmap.put("child_startday", CountTime.FormatTime(jsonObject2.getString("FZRQ")));
				childmap.put("child_endday", CountTime.FormatTime(jsonObject2.getString("YXRQ")));
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

			convertView = mlayoutInflater.inflate(R.layout.cbzsxx_child, null);

			TextView textView = (TextView) convertView
					.findViewById(R.id.bs_cbzsxx_txt012);
			TextView textView1 = (TextView) convertView
					.findViewById(R.id.bs_cbzsxx_txt022);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.bs_cbzsxx_txt032);

			textView.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_id").toString());
			textView1.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_startday").toString());
			textView2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_endday").toString());

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

			convertView = mlayoutInflater.inflate(R.layout.cbzsxx_group, null);

			TextView textParent = (TextView) convertView
					.findViewById(R.id.cbzsxx_grounptitle);
			ImageView image = (ImageView) convertView
					.findViewById(R.id.cbzsxx_time);

			textParent.setText(parentList.get(groupPosition).get("parent")
					.toString());
			if ((String) parentList.get(groupPosition).get("time") != null) {
				if (Integer.parseInt((String) parentList.get(groupPosition)
						.get("time")) == 1)
					image.setImageResource(R.drawable.time_normal);
				else
					image.setImageResource(R.drawable.time_out);
			} else
				image.setImageResource(R.drawable.time_normal);
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
