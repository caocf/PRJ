package com.huzhouport.portdynmicnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;
import com.huzhouport.pushmsg.IndustryInfoPushMsgManager;
import com.huzhouport.pushmsg.PushMsg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class IndustryInfoActivity extends Activity
{

	private Query query = new Query();
	private View moreView;
	private ListView pml;
	private List<Map<String, Object>> listv = new ArrayList<Map<String, Object>>();
	private int cpage = 1, maxpage;
	private SimpleAdapter adapter;

	User user;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.portdynmicnews_main);

		// ��ӷ��ذ�ť�¼�
		CommonListenerWrapper.commonBackWrapperListener(
				R.id.portdynmicnews_main_back, this);

		user = (User) getIntent().getSerializableExtra("User");

		TextView title = (TextView) findViewById(R.id.portdynmicnews_main_titleText);
		title.setText("��ҵ��Ѷ");
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		moreView.setVisibility(View.GONE);
		pml = (ListView) findViewById(R.id.portdynmicnews_main_list);
		pml.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		adapter = new ExtSimpleAdapter(this, listv,
				R.layout.portdynmicnews_list, new String[] { "title", "date" },
				new int[] { R.id.portdynmicnews_main_title,
						R.id.portdynmicnews_main_data });
		pml.setAdapter(adapter);

		// ���õ���¼��������¼�
		pml.setOnItemClickListener(new ProtDynmicNewsListener());
		pml.setOnScrollListener(new scrollNewsListener());

	}

	@Override
	protected void onResume()
	{
		// ���ҵ�һҳ����
		String data = "cpage=" + cpage;
		new queryIndustryInfo(this, true).execute(data);

		super.onResume();
	}

	class ExtSimpleAdapter extends SimpleAdapter
	{

		public ExtSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to)
		{
			super(context, data, resource, from, to);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = super.getView(position, convertView, parent);

			TextView tv = (TextView) v
					.findViewById(R.id.portdynmicnews_main_title);
			Map<String, Object> data = listv.get(position);

			if (tv == null || data == null)
				return v;

			String unread = (String) data.get("unread");

			if (unread != null && unread.equals("yes"))
			{
				tv.setTextColor(Color.parseColor("#333333"));
			} else
			{
				tv.setTextColor(Color.parseColor("#999999"));
			}

			return v;
		}
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
				int messageid = Integer.parseInt(listv.get(arg2).get("id")
						.toString());
				new IndustryInfoPushMsgManager(IndustryInfoActivity.this)
						.readPushMsg(-1, messageid);

				new Log(user, "�鿴��ҵ��Ѷ", GlobalVar.LOGSEE, "").execute();

				Intent intent = new Intent(IndustryInfoActivity.this,
						IndusrtyInfoSee.class);
				intent.putExtra("id", listv.get(arg2).get("id").toString());
				startActivity(intent);
				finish();
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
					new queryIndustryInfo(IndustryInfoActivity.this, false)
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
						IndustryInfoActivity.this, queryIndustryInfo.this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params)
		{
			new IndustryInfoPushMsgManager(IndustryInfoActivity.this)
					.pullUnPushedMsg(-1);

			return query.showIndustryInfo(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			if (result == null)
			{
				Toast.makeText(IndustryInfoActivity.this, "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else
			{
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
				Toast.makeText(IndustryInfoActivity.this, "�����������...",
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
					map.put("date",
							jsonObject
									.get("updatetime")
									.toString()
									.substring(
											0,
											jsonObject.get("updatetime")
													.toString()
													.lastIndexOf(":"))
									.replace("T", " "));
					map.put("id", jsonObject.get("id").toString());

					// //////////
					List<PushMsg> pushMsgs = new IndustryInfoPushMsgManager(
							this).findPushedMsg(-1, jsonObject.getInt("id"));
					if (pushMsgs != null && pushMsgs.size() > 0)
					{
						PushMsg msg = pushMsgs.get(0);

						if (msg.getMsgstatus() == PushMsg.MSGSTATUS_PUSHED_READ
								|| msg.getMsgstatus() == PushMsg.MSGSTATUS_NOTPUSH_READ)
						{
							map.put("unread", "no");
						} else
						{
							map.put("unread", "yes");
						}
					} else
						map.put("unread", "no");
					// ////////

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
