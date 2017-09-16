package com.huzhouport.integratedquery;

/*@author 沈丹丹
 * */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class BS_CBJFXX extends Fragment {

	private List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

	TextView tishi, warntitle, nowarntitle;
	private ListView warnelv;
	ExpandableListView nowarnelv;
	private String getResult, title;
	private int cpage = 1, maxpage = 0;
	private View moreView;
	private ProgressBar pb;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getResult = getArguments().getString("getResult");
		title = getArguments().getString("title");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.bs_cbjfxx, container, false);
		pb = (ProgressBar) view.findViewById(R.id.bs_cbjfxx_pb2);
		warntitle = (TextView) view.findViewById(R.id.bs_cbjfxx_warntitle);
		nowarntitle = (TextView) view.findViewById(R.id.bs_cbjfxx_nowarntitle);
		tishi = (TextView) view.findViewById(R.id.bs_cbjfxx_result);
		warnelv = (ListView) view
				.findViewById(R.id.bs_cbjfxx_warnlist);
		nowarnelv = (ExpandableListView) view
				.findViewById(R.id.bs_cbjfxx_nowarnlist);
		moreView = inflater.inflate(R.layout.dateload, null);
		if (getResult.length() == 0) {
			new GetDate().execute();
		} else
			intData(getResult);
		return view;
	}

	static BS_CBJFXX newInstance(String getResult, String title) {
		BS_CBJFXX newFragment = new BS_CBJFXX();
		Bundle obundle = new Bundle();
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

	}

	public void showExpandableListView() {
		MyAdapter adapter = new MyAdapter(getActivity());
		if (cpage < maxpage) {
			nowarnelv.addFooterView(moreView);
			moreView.setVisibility(View.VISIBLE);
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
		}
		nowarnelv.setAdapter(adapter);
		nowarnelv.setGroupIndicator(null);
		nowarnelv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						moreView.findViewById(R.id.progressBar2).setVisibility(
								View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text))
								.setText(R.string.more);

						if (cpage < maxpage) {
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
			paramsDate.put("method", 3);
			paramsDate.put("scape", cpage);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
			try {
				getResult = hfu.post(actionUrl, paramsDate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			intData(getResult);
			super.onPostExecute(result);
		}

	}

	public void intData(String getResult) {
		JSONTokener jsonParser = new JSONTokener(getResult);
		JSONObject data;

		try {
			data = (JSONObject) jsonParser.nextValue();
			String listDateString = data.getString("allTotal");
			if (listDateString.equals("0"))  {
				nowarnelv.setVisibility(View.GONE);
				tishi.setVisibility(View.VISIBLE);
			} else {
				maxpage = data.getInt("totalPage");
				//-------------欠费信息 start----------------
				JSONArray jsonArray = data.getJSONArray("jf_warn");
				if(jsonArray.length()>0){
					warntitle.setVisibility(View.VISIBLE);
					List<Map<String, Object>> warnlist = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < jsonArray.length(); i++) {

							JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("name", jsonObject2.getString("JFXMMC"));
							map.put("money", jsonObject2.getString("YJZE"));
							map.put("money2", jsonObject2.getString("SJZE"));
							String gettime = jsonObject2.getString("YXQQ");
							String[] subString = gettime.split(" ");
							String endtime = jsonObject2.getString("YXQZ");
							String[] subString2 = endtime.split(" ");
							map.put("startday", subString[0]);
							map.put("endday", subString2[0]);
							warnlist.add(map);
					}
					SimpleAdapter adapterList=new SimpleAdapter(getActivity(), warnlist,
							R.layout.cbjfxx_listview_item, new String[] { "name", "money",
						"money2", "startday", "endday"}, new int[] {
						R.id.bs_cbjfxx_txt011, R.id.bs_cbjfxx_txt022,
						R.id.bs_cbjfxx_txt024, R.id.bs_cbjfxx_txt032,
						R.id.bs_cbjfxx_txt034 });
					warnelv.setAdapter(adapterList);
				}
				//-------------欠费信息 end----------------
				//-------------已缴费信息start-------------
				jsonArray = data.getJSONArray("jf_nowarn");
				if(jsonArray.length()>0){
					nowarntitle.setVisibility(View.VISIBLE);
					for (int i = 0; i < jsonArray.length(); i++) {
						//父节点内容
						HashMap<String, Object> map_mouth = new HashMap<String, Object>();// 定义Map集合,月
						JSONArray item = (JSONArray) jsonArray.opt(i);
						String info_time = item.getString(0);
						int groupMoney = 0, groupMoney2 = 0, groupOwe = 0;
						JSONArray info = (JSONArray) item.opt(1);
						map_mouth.put("year", info_time.split("年")[0]);
						map_mouth.put("mouth", info_time.split("年")[1]);
						ArrayList<HashMap<String, Object>> childlist = new ArrayList<HashMap<String, Object>>();
						for (int j = 0; j < info.length(); j++) {
							JSONObject info2 = (JSONObject) info.opt(j);
							groupMoney = groupMoney+ info2.getInt("YJZE");
							groupMoney2 = groupMoney2+info2.getInt("SJZE");
							//子节点内容
							HashMap<String, Object> childmap = new HashMap<String, Object>();
							childmap.put("child_name", info2.getString("JFXMMC"));
							childmap.put("child_money", info2.getString("YJZE"));
							childmap.put("child_money2", info2.getString("SJZE"));
							String gettime = info2.getString("YXQQ");
							String[] subString = gettime.split(" ");
							String endtime = info2.getString("YXQZ");
							String[] subString2 = endtime.split(" ");
							childmap.put("child_startday", subString[0]);
							childmap.put("child_endday", subString2[0]);
							childlist.add(childmap);
						}
						allchildList.add(childlist);
						groupOwe = groupMoney - groupMoney2;
						map_mouth.put("money", groupMoney);
						map_mouth.put("money2", groupMoney2);
						map_mouth.put("owe", groupOwe);
						parentList.add(map_mouth);
					}
				}
				
				//-------------已缴费信息 end----------------
				tishi.setVisibility(View.GONE);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showExpandableListView();
		pb.setVisibility(View.GONE);
	}


	public Boolean mouth(int getmouth, int[] list) {
		if (list.length > 0) {
			for (int i = 0; i < list.length; i++)
				if (getmouth == list[i])
					return false;
		}
		return true;
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

			convertView = mlayoutInflater.inflate(R.layout.cbjfxx_child, null);

			TextView textView = (TextView) convertView
					.findViewById(R.id.bs_cbjfxx_txt011);
			TextView textView1 = (TextView) convertView
					.findViewById(R.id.bs_cbjfxx_txt022);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.bs_cbjfxx_txt024);
			TextView textView3 = (TextView) convertView
					.findViewById(R.id.bs_cbjfxx_txt032);
			TextView textView4 = (TextView) convertView
					.findViewById(R.id.bs_cbjfxx_txt034);

			textView.setText(allchildList.get(groupPosition).get(childPosition)
					.get("child_name").toString());
			textView1.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_money").toString());
			textView2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_money2").toString());
			textView3.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_startday").toString());
			textView4.setText(allchildList.get(groupPosition)
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

			convertView = mlayoutInflater.inflate(R.layout.cbjfxx_group, null);
			TextView textParent1 = (TextView) convertView
					.findViewById(R.id.cbjfxx_groupyear);
			TextView textParent2 = (TextView) convertView
					.findViewById(R.id.cbjfxx_groupmouth);
			TextView textParent3 = (TextView) convertView
					.findViewById(R.id.cbjfxx_group_money);
			TextView textParent4 = (TextView) convertView
					.findViewById(R.id.cbjfxx_group_money2);
			TextView textParent5 = (TextView) convertView
					.findViewById(R.id.cbjfxx_group_owe);

			textParent1.setText(parentList.get(groupPosition).get("year")
					.toString());
			textParent2.setText(parentList.get(groupPosition).get("mouth")
					.toString());
			textParent3.setText(parentList.get(groupPosition).get("money")
					.toString());
			textParent4.setText(parentList.get(groupPosition).get("money2")
					.toString());
			textParent5.setText(parentList.get(groupPosition).get("owe")
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
