package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;

import net.hxkg.ghmanager.R;

import org.json.JSONObject;
import org.json.JSONTokener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class LeaveandovertimeNewListView extends Activity
{
	private Query query = new Query();
	private ListView lv;
	private String id;
	private ArrayList<HashMap<String, Object>> leaveview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_view);

		// ��ȡ��������
		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		// ���ݲ�ͬ���ͳ�ʼ��Textview˵������
		TextView lastdate = (TextView) findViewById(R.id.leaveandovertime_newlist_view_lastDate);
		TextView leaveOrOtReason = (TextView) findViewById(R.id.leaveandovertime_newlist_view_leaveOrOtReason);

		if (intent.getStringExtra("kind").equals("2"))
		{
			lastdate.setText("加班时长：");
			leaveOrOtReason.setText("�Ӱ����ɣ�");
		}
		if (intent.getStringExtra("kind").equals("3"))
		{
			lastdate.setText("����ʱ����");
			leaveOrOtReason.setText("�������ɣ�");
			findViewById(R.id.leaveandovertime_newlist_view_travl_tr)
					.setVisibility(View.VISIBLE);
		}

		lv = (ListView) findViewById(R.id.leaveandover_newlist_viewlist);
		ListTask task = new ListTask(this); 
		task.execute();

	}

	public void onBack(View view)
	{
		finish();
	}
	
	public void getContent(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject jsonArray = data.getJSONObject("leaveOrOtKindbean");
			
			String lastDate = jsonArray.getString("lastDate");
			int ld = Integer.valueOf(lastDate);
			if (ld >= 8)
			{
				int d = ld / 8; // ȥ��
				int l = ld % 8; // ȥ��
				if (l == 0)
				{
					lastDate = d + "��";
				} else
				{
					lastDate = d + "���";
				}
			} else
			{
				lastDate = "����";
			}
			
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_leaveOrOtUserNeirong, jsonArray.getString("leaveOrOtUser"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_beginDateNeirong, jsonArray.getString("beginDate"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_view_endDateNeirong, jsonArray.getString("endDate"));			
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_lastDateNeirong,lastDate);
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_addressNeirong, jsonArray.getString("address"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_leaveOrOtReasonNeirong, jsonArray.getString("leaveOrOtReason"));
			if(jsonArray.getString("approvalOpinion1").equals("null")){
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,"��" );
			}else{
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,jsonArray.getString("approvalOpinion1") );
			}

			String approvalResult = "";
			int color;
			if (jsonArray.getString("approvalResult").equals("������")
					|| jsonArray.getString("approvalResult").equals("��������"))
			{
				approvalResult = "������";
				color=R.color.leave_yellow;
			} else if (jsonArray.getString("approvalResult").equals("����"))
			{
				approvalResult = "����׼";
				color=R.color.leave_red;
			} else
			{
				approvalResult = "��׼";
				color=R.color.leave_green;
			}
			
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_approvalResultNeirong, approvalResult,color);
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_approvalID1Neirong, jsonArray.getString("approvalID1"));
			
			if (jsonArray.getString("approvalID2").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_view_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_view_tr2)
						.setVisibility(View.GONE);
			} else
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_approvalID2Neirong, jsonArray.getString("approvalID2"));
			
			
			if (jsonArray.getString("approvalID3").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_view_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_view_tr2)
						.setVisibility(View.GONE);
			} else
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_view_approvalID3Neirong, jsonArray.getString("approvalID3"));

			leaveview = new ArrayList<HashMap<String, Object>>();
			
			easyParse(1, jsonArray);
			easyParse(2, jsonArray);
			easyParse(3, jsonArray);
			
		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListView.this, "�����쳣",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	public void easyParse(int i,JSONObject jsonObject) throws Exception
	{
		String param1="approvalResult"+i;
		String param2="approvalID"+i;
		String param3="approvalTime"+i;
		String param4="approvalOpinion"+i;
		if(!jsonObject.getString(param1).equals("2") && !jsonObject.getString(param1).equals("1"))
			return;
		
		String text1 = "";
		String text2 = "";
		String text3 = "";

		text1 = jsonObject.getString(param2);
		text2 = jsonObject.getString(param3).substring(0,
				jsonObject.getString(param3).lastIndexOf(":"));
		if(jsonObject.getString(param1).equals("1"))
			text3 = "��׼";
		else if(jsonObject.getString(param1).equals("2"))
			text3="����׼";
		if(!jsonObject.getString(param4).equals("null"))
			text3+=":"+jsonObject.getString(param4);
		HashMap<String, Object> temp = new HashMap<String, Object>();
		
		temp.put("text1", text1);
		temp.put("text2", text2);
		temp.put("text3", text3);
		leaveview.add(temp);
	}

	/**
	 * ��ʾ�ظ�����
	 */
	public void showListview()
	{
		if (leaveview.size() == 0)
		{
			findViewById(R.id.leaveandover_newlist_viewbutton).setVisibility(
					View.GONE);
			findViewById(R.id.leaveandover_newlist_viewlist).setVisibility(
					View.GONE);
		} else
		{
			SimpleAdapter adapter = new SimpleAdapter(
					LeaveandovertimeNewListView.this, leaveview,
					R.layout.leaveandovertime_newlist_viewitem, new String[] {
							"text1", "text2", "text3" }, new int[] {
							R.id.leaveandovertime_newlist_viewitem_text1,
							R.id.leaveandovertime_newlist_viewitem_text2,
							R.id.leaveandovertime_newlist_viewitem_text3 });
			lv.setAdapter(adapter);
		}
	}

	/**
	 * ��ȡ��������߳�
	 * 
	 * @author Administrator
	 * 
	 */
	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					ListTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// ȡ���첽

			return query.Showneirong(id);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			getContent(result);// �������
			showListview();
			super.onPostExecute(result);
		}
	}
}
