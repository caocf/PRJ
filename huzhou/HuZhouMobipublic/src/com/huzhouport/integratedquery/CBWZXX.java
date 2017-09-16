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

import com.example.huzhouportpublic.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.model.User;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class CBWZXX extends Activity
{
	private List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

	TextView tishi;
	ExpandableListView expandlistview;
	private User user;
	private String title;
	private RelativeLayout rl;
	private int cpage = 1, maxpage;
	private View moreView;
	private MyAdapter adapter;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_cbwzxx);

		ImageButton back = (ImageButton) findViewById(R.id.bs_cbwzxx_back);
		TextView tv_title = (TextView) findViewById(R.id.bs_cbwzxx_title);
		rl = (RelativeLayout) findViewById(R.id.bs_cbwzxx_tishi);
		expandlistview = (ExpandableListView) findViewById(R.id.bs_cbwzxx_datalist);
		tishi = (TextView) findViewById(R.id.bs_cbwzxx_noresult);

		user = (User) getIntent().getSerializableExtra("User");
		title = user.getShipBindingList().get(user.getBindnum()).getShipNum();
		tv_title.setText(title);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		showExpandableListView();
		back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();

			}
		});
		new GetDate().execute();

		new Log(user, "违章记录查询", GlobalVar.LOGSEE, "").execute();
	}

	public void showExpandableListView()
	{
		adapter = new MyAdapter(CBWZXX.this);
		expandlistview.addFooterView(moreView);
		expandlistview.setAdapter(adapter);
		expandlistview.setGroupIndicator(null);
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		expandlistview.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1))
					{
						moreView.findViewById(R.id.progressBar2).setVisibility(
								View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text))
								.setText(R.string.more);

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
					int visibleItemCount, int totalItemCount)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	class GetDate extends AsyncTask<Void, Void, String>
	{
		@Override
		protected String doInBackground(Void... params)
		{
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);
			paramsDate.put("method", 5);
			paramsDate.put("scape", cpage);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
			String result = null;
			try
			{
				result = hfu.post(actionUrl, paramsDate);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (result != null)
			{
				intData(result);
				// 显示列表
				adapter.notifyDataSetChanged();

				if (cpage >= maxpage)
				{
					moreView.setVisibility(View.GONE);
					expandlistview.removeFooterView(moreView); // 移除底部视图
				}
				moreView.findViewById(R.id.progressBar2).setVisibility(
						View.GONE);
				((TextView) moreView.findViewById(R.id.loadmore_text))
						.setText(R.string.moreing);
			} else
			{
				moreView.setVisibility(View.GONE);
				expandlistview.removeFooterView(moreView); // 移除底部视图
				tishi.setVisibility(View.VISIBLE);
			}
			super.onPostExecute(result);
		}

	}

	public void intData(String getResult)
	{
		JSONTokener jsonParser = new JSONTokener(getResult);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			String listDateString = data.getString("list");
			if (listDateString.equals("null"))
			{
				moreView.setVisibility(View.GONE);
				tishi.setVisibility(View.VISIBLE);
			} else
			{
				JSONArray jsonArray = data.getJSONArray("list");
				maxpage = data.getInt("totalPage");
				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					HashMap<String, Object> map = new HashMap<String, Object>();

					map.put("parent_title", jsonObject2.getString("FADD"));// group的标题
					map.put("parent_title2", jsonObject2.getString("AY"));// group的标题2
					map.put("parent_time", CountTime
							.FormatTimeToDay(jsonObject2.getString("SLSJ")));// group的受理时间
					map.put("parent_result", jsonObject2.getString("CFYJ"));// group的处罚结果
					map.put("parent_end", jsonObject2.getString("SFJA"));// group的处罚结果
					parentList.add(map);

					ArrayList<HashMap<String, Object>> childlist = new ArrayList<HashMap<String, Object>>();

					HashMap<String, Object> childmap = new HashMap<String, Object>();

					childmap.put("child_SLH", jsonObject2.getString("SLH"));
					childmap.put("child_SLSJ", CountTime
							.FormatTimeToDay(jsonObject2.getString("SLSJ")));
					childmap.put("child_ZWCM", jsonObject2.getString("ZWCM"));
					childmap.put("child_AY", jsonObject2.getString("AY"));
					childmap.put("child_FASJ", CountTime
							.FormatTimeToDay(jsonObject2.getString("FASJ")));
					childmap.put("child_FADD", jsonObject2.getString("FADD"));
					childmap.put("child_AJLB", jsonObject2.getString("AJLB"));
					childmap.put("child_ZYSS", jsonObject2.getString("ZYSS"));
					childmap.put("child_ZFSCBH",
							jsonObject2.getString("ZFSCBH"));
					childmap.put("child_WFNR", jsonObject2.getString("WFNR"));
					childmap.put("child_WFTK", jsonObject2.getString("WFTK"));
					childmap.put("child_CFTK", jsonObject2.getString("CFTK"));
					childmap.put("child_CFYJ", jsonObject2.getString("CFYJ"));
					childmap.put("child_CFLB", jsonObject2.getString("CFLB"));
					childmap.put("child_SFCF", jsonObject2.getString("SFCF"));
					childmap.put("child_SFJA", jsonObject2.getString("SFJA"));
					childmap.put("child_JARQ", CountTime
							.FormatTimeToDay(jsonObject2.getString("JARQ")));
					childlist.add(childmap);

					allchildList.add(childlist);
				}
				if (jsonArray.length() == 0)
				{
					rl.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class MyAdapter extends BaseExpandableListAdapter
	{
		Context context;
		LayoutInflater mlayoutInflater;

		MyAdapter(Context context)
		{
			this.context = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition)
		{

			return allchildList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition)
		{

			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent)
		{

			convertView = mlayoutInflater.inflate(R.layout.cbwzxx_child, null);

			TextView textView1 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt012);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt022);
			TextView textView3 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt032);
			TextView textView4 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt042);
			TextView textView5 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt052);
			TextView textView6 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt062);
			TextView textView7 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt072);
			TextView textView8 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt082);
			TextView textView9 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt092);
			TextView textView10 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt102);
			TextView textView11 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt112);
			TextView textView12 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt122);
			TextView textView13 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt132);
			TextView textView14 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt142);
			TextView textView15 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt152);
			TextView textView16 = (TextView) convertView
					.findViewById(R.id.bs_cbwzxx_txt162);

			textView1.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_AY").toString());
			textView2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_SLH").toString());
			textView3.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZWCM").toString());
			textView4.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_SLSJ").toString());

			textView5.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_FASJ").toString());
			textView6.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_FADD").toString());
			textView7.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_AJLB").toString());

			textView8.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_WFNR").toString());
			textView9.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_WFTK").toString());
			textView10.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_CFTK").toString());
			textView11.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_CFYJ").toString());

			String sType = allchildList.get(groupPosition).get(childPosition)
					.get("child_CFLB").toString();
			String[] sCFType = sType.split("");
			sType = "";
			if (sCFType[1].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType01);
			if (sCFType[1].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType01);
			if (sCFType[2].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType02);
			if (sCFType[2].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType02);
			if (sCFType[3].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType03);
			if (sCFType[3].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType03);
			if (sCFType[4].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType04);
			if (sCFType[4].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType04);
			if (sCFType[5].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType05);
			if (sCFType[5].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType05);
			if (sCFType[6].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType06);
			if (sCFType[6].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType06);
			if (sCFType[7].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType07);
			if (sCFType[7].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType07);
			if (sCFType[8].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType08);
			if (sCFType[8].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType08);
			if (sCFType[9].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType09);
			if (sCFType[9].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType09);
			if (sCFType[10].equalsIgnoreCase("1") && sType.length() != 0)
				sType = sType
						+ ","
						+ getResources().getString(
								R.string.bs_cbwzxx_PunishmentType10);
			if (sCFType[10].equalsIgnoreCase("1") && sType.length() == 0)
				sType = getResources().getString(
						R.string.bs_cbwzxx_PunishmentType10);

			System.out.println("sType  " + sType);
			textView12.setText(sType);

			if (Integer.parseInt((String) allchildList.get(groupPosition)
					.get(childPosition).get("child_SFCF")) == 1)
				textView13.setText(getResources().getString(
						R.string.bs_cbwzxx_yes));
			else
				textView13.setText(getResources().getString(
						R.string.bs_cbwzxx_no));
			textView14.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_JARQ").toString());
			textView15.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZFSCBH").toString());

			textView16.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_ZYSS").toString());

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition)
		{

			return allchildList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition)
		{

			return allchildList.get(groupPosition);
		}

		@Override
		public int getGroupCount()
		{

			return allchildList.size();
		}

		@Override
		public long getGroupId(int groupPosition)
		{

			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent)
		{

			convertView = mlayoutInflater.inflate(R.layout.cbwzxx_group, null);

			TextView textParent1 = (TextView) convertView
					.findViewById(R.id.cbwzxx_grounptitle);
			TextView textParent2 = (TextView) convertView
					.findViewById(R.id.cbwzxx_grounp12);
			TextView textParent3 = (TextView) convertView
					.findViewById(R.id.cbwzxx_grounp22);
			TextView textParent4 = (TextView) convertView
					.findViewById(R.id.cbwzxx_grounptitle2);

			ImageView image = (ImageView) convertView
					.findViewById(R.id.cbwzxx_time);

			textParent1.setText(parentList.get(groupPosition)
					.get("parent_title").toString());
			textParent2.setText(parentList.get(groupPosition)
					.get("parent_result").toString());
			textParent3.setText(parentList.get(groupPosition)
					.get("parent_time").toString());
			textParent4.setText(parentList.get(groupPosition)
					.get("parent_title2").toString());
			if (Integer.parseInt((String) parentList.get(groupPosition).get(
					"parent_end")) == 1)
				image.setImageResource(R.drawable.treated);// untreated
			else
				image.setImageResource(R.drawable.untreated);

			return convertView;
		}

		@Override
		public boolean hasStableIds()
		{

			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{

			return false;
		}

	}

}
