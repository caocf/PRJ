package com.huzhouport.patrol;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

@SuppressLint("ShowToast")
public class PatrolSearch extends Activity
{
	private EditText et_content;
	private ImageButton bt_saerch;
	private ListView lv;
	private int cpage = 1, maxpage;
	private String queryCondition = "";

	private Query query = new Query();
	private List<Map<String, Object>> list;
	private User user;
	private View moreView; // ���ظ���ҳ��
	private SimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_search);

		et_content = (EditText) findViewById(R.id.illegal_search_edit01);
		bt_saerch = (ImageButton) findViewById(R.id.illegal_search_bt);
		lv = (ListView) findViewById(R.id.illegal_search_listView);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);

		user = (User) getIntent().getSerializableExtra("User");

		et_content.setHint(R.string.patrol_search_hint);
		showListView();
		// ��ȡ�б�
		new GetPatrolList().execute();
		bt_saerch.setOnClickListener(new searchOrBack());
	}

	public void Back(View view)
	{
		finish();
	}

	class searchOrBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			moreView.findViewById(R.id.progressBar2)
					.setVisibility(View.VISIBLE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.more);
			lv.removeFooterView(moreView); // �Ƴ��ײ���ͼ
			cpage = 1;
			showListView();
			queryCondition = et_content.getText().toString();
			new GetPatrolList().execute();
		}

	}

	public void showListView()
	{
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(PatrolSearch.this, list,
				R.layout.illegal_check_item, new String[] { "object",
						"content", "time", "patrolId" }, new int[] {
						R.id.illegal_check_item_kind,
						R.id.illegal_check_item_name,
						R.id.illegal_check_item_time,
						R.id.illegal_check_item_eventId });
		lv.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		moreView.setVisibility(View.VISIBLE);
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				if (arg2 < list.size())
				{
					TextView tv_patrolId = (TextView) arg1
							.findViewById(R.id.illegal_check_item_eventId);
					Intent intent = new Intent(PatrolSearch.this,
							PatrolSee.class);
					intent.putExtra("User", user);
					intent.putExtra("patrolId", tv_patrolId.getText());
					startActivityForResult(intent, 100); // ��÷���ֵ �õ� Ȼ�� �жϷ���

					if (user != null)
					{
						new Log(user.getName(), "�鿴��ͷѲ��", GlobalVar.LOGSEE, "")
								.execute();
					}
				}
			}

		});
		lv.setOnScrollListener(new OnScrollListener()
		{

			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// ��������ʱ
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{

					// �жϹ������ײ�
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
							new GetPatrolList().execute();
						}
					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
			}
		});
	}

	class GetPatrolList extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			String date = "queryCondition.orderByFielName=p.patrolTime&queryCondition.sequence=desc&cpage="
					+ cpage;
			if (queryCondition.length() != 0)
			{
				try
				{
					queryCondition = URLEncoder.encode(queryCondition, "UTF-8");
				} catch (UnsupportedEncodingException e)
				{
					Toast.makeText(PatrolSearch.this, "��������ʧ��",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				date = "patrol.patrolObject=" + queryCondition + "&" + date;
			}
			if (isCancelled())
				return null;// ȡ���첽
			return query.showPatrolList(date);
		}

		@Override
		protected void onPostExecute(String result)
		{

			showPatrolList(result);
			// ��ʾ�б�
			adapter.notifyDataSetChanged();

			if (cpage >= maxpage)
			{
				moreView.setVisibility(View.GONE);
				lv.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				// Toast.makeText(PatrolSearch.this, "�Ѽ���ȫ������", 3000).show();
			}
			moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
			((TextView) moreView.findViewById(R.id.loadmore_text))
					.setText(R.string.moreing);
			super.onPostExecute(result);
		}

	}

	public void showPatrolList(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");

			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject3 = (JSONObject) jsonArray2.opt(0);
				map.put("patrolId", jsonObject3.getString("patrolId"));
				int nodeKind = jsonObject3.getInt("patCategoriese");// 1:����2������3������4������
				switch (nodeKind)
				{
				case 1:
					map.put("object", jsonObject3.getString("patrolObject")
							+ "(����)");
					break;
				case 2:
					map.put("object", jsonObject3.getString("patrolObject")
							+ "(����)");
					break;
				case 3:
					map.put("object", jsonObject3.getString("patrolObject")
							+ "(����)");
					break;
				case 4:
					map.put("object", jsonObject3.getString("patrolObject")
							+ "(����)");
					break;
				}
				String time = jsonObject3.getString("patrolTime");
				String[] sub = time.split(" ");
				map.put("time", sub[0]);
				map.put("content", jsonObject3.getString("patrolContent"));
				list.add(map);
			}
		} catch (Exception e)
		{
			Toast.makeText(PatrolSearch.this, "û���������������", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (20 == resultCode)
		{

			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
