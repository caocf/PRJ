package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.CommonView;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 审核请假界面
 * @author Administrator
 *
 */
public class LeaveandovertimeNewListShow extends Activity
{
	private Query query = new Query();

	private String userid, id, username;
	private String actionUrl, param1;
	private String resultid;
	private User user = new User();
	private ListView lv;
	private ArrayList<HashMap<String, Object>> leaveview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_show);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		userid = intent.getStringExtra("userid");
		username = intent.getStringExtra("username");
		user.setUserId(Integer.parseInt(userid));
		user.setName(username);

		//返回事件
		CommonListenerWrapper.commonBackWrapperListener(R.id.leaveandovertime_newlist_show_back, this);
		
		TextView lastdate = (TextView) findViewById(R.id.leaveandovertime_newlist_show_lastDate);
		TextView leaveOrOtReason = (TextView) findViewById(R.id.leaveandovertime_newlist_show_leaveOrOtReason);
		
		if (intent.getStringExtra("kind").equals("2"))
		{
			lastdate.setText("加班时长：");
			leaveOrOtReason.setText("加班事由：");
		}
		if (intent.getStringExtra("kind").equals("3"))
		{
			lastdate.setText("出差时长：");
			leaveOrOtReason.setText("出差事由：");
			findViewById(R.id.leaveandovertime_newlist_show_travl_tr)
					.setVisibility(View.VISIBLE);
		}


		ImageButton noButton = (ImageButton) findViewById(R.id.leaveandovertime_newlist_show_no);
		ImageButton yesButton = (ImageButton) findViewById(R.id.leaveandovertime_newlist_show_yes);
		noButton.setOnClickListener(new Nobutton());
		yesButton.setOnClickListener(new Yesbutton());

		ImageButton sendButton = (ImageButton) findViewById(R.id.leaveandovertime_newlist_show_send);
		sendButton.setOnClickListener(new Sendbutton());

		lv = (ListView) findViewById(R.id.leaveandover_newlist_showlist);
		ListTask task = new ListTask(this); // 异步
		task.execute();

	}

	/**
	 * 解析服务器返回的请假信息内容
	 * @param result
	 */
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
				int d = ld / 8; // 去商
				int l = ld % 8; // 去余
				if (l == 0)
				{
					lastDate = d + "天";
				} else
				{
					lastDate = d + "天半";
				}
			} else
			{
				lastDate = "半天";
			}
			
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_leaveOrOtUserNeirong, jsonArray.getString("leaveOrOtUser"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_beginDateNeirong, jsonArray.getString("beginDate"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_show_endDateNeirong, jsonArray.getString("endDate"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_lastDateNeirong, lastDate);
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_addressNeirong, jsonArray.getString("address"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_leaveOrOtReasonNeirong, jsonArray.getString("leaveOrOtReason"));
			if(jsonArray.getString("approvalOpinion1").equals("null")){
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,"无" );
			}else{
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,jsonArray.getString("approvalOpinion1") );
			}
			String approvalResult = "";
			int color;
			if (jsonArray.getString("approvalResult").equals("待审批")
					|| jsonArray.getString("approvalResult").equals("正在审批"))
			{
				approvalResult = "待审批";
				color=R.color.leave_yellow;
			} else if (jsonArray.getString("approvalResult").equals("驳回"))
			{
				approvalResult = "不批准";
				color=R.color.leave_red;
			} else
			{
				approvalResult = "批准";
				color=R.color.leave_green;
			}


			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalResultNeirong, approvalResult,color);
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID1Neirong, jsonArray.getString("approvalID1"));
			
			if (jsonArray.getString("approvalID2").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_show_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_show_tr2)
						.setVisibility(View.GONE);
			} else
			
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID2Neirong, jsonArray.getString("approvalID2"));

			
			if (jsonArray.getString("approvalID3").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_show_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_show_tr2)
						.setVisibility(View.GONE);
			} else
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID3Neirong, jsonArray.getString("approvalID3"));

			leaveview = new ArrayList<HashMap<String, Object>>();
			
			easyParse(1, jsonArray);
			easyParse(2, jsonArray);
			easyParse(3, jsonArray);

		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListShow.this, "数据异常",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	/**
	 * 简单结果解析方法
	 * @param i
	 * @param jsonObject
	 * @throws Exception
	 */
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
			text3 = "批准";
		else if(jsonObject.getString(param1).equals("2"))
			text3="不批准";
		if(!jsonObject.getString(param4).equals("null"))
			text3+=":"+jsonObject.getString(param4);
		HashMap<String, Object> temp = new HashMap<String, Object>();
		
		temp.put("text1", text1);
		temp.put("text2", text2);
		temp.put("text3", text3);
		leaveview.add(temp);
	}

	public void showListview()
	{
		if (leaveview.size() == 0)
		{
			findViewById(R.id.leaveandover_newlist_showbutton).setVisibility(
					View.GONE);
			findViewById(R.id.leaveandover_newlist_showlist).setVisibility(
					View.GONE);
		} else
		{
			SimpleAdapter adapter = new SimpleAdapter(
					LeaveandovertimeNewListShow.this, leaveview,
					R.layout.leaveandovertime_newlist_viewitem, new String[] {
							"text1", "text2", "text3" }, new int[] {
							R.id.leaveandovertime_newlist_viewitem_text1,
							R.id.leaveandovertime_newlist_viewitem_text2,
							R.id.leaveandovertime_newlist_viewitem_text3 });
			lv.setAdapter(adapter);
		}
	}


	class Nobutton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			findViewById(R.id.leaveandovertime_newlist_show_footR)
					.setVisibility(View.GONE);
			findViewById(R.id.leaveandovertime_newlist_show_footR1)
					.setVisibility(View.VISIBLE);
			EditText et = (EditText) findViewById(R.id.leaveandover_newlist_show_input);
			et.requestFocus(); // 请求获取焦点
			// et.setFocusable(true);
			et.setFocusableInTouchMode(true);
		}
	}

	class Yesbutton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			resultid = "1"; // 准许
			actionUrl = HttpUtil.BASE_URL + "LeaveAndOvertimeApprovalAgree";
			param1 = "leaveOrOt.leaveOrOtID=" + id + "&leaveOrOt.approvalID1="
					+ userid + "&leaveOrOt.approvalResult1=" + resultid
					+ "&leaveOrOt.approvalOpinion1=";
			ListTask1 task = new ListTask1(LeaveandovertimeNewListShow.this);
			task.execute();
		}
	}

	class Sendbutton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			EditText edit = (EditText) findViewById(R.id.leaveandover_newlist_show_input);
			if (edit.getText().toString().equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListShow.this, "请填写审批原因",
						Toast.LENGTH_SHORT).show();
			} else
			{

				resultid = "2"; // 准许
				actionUrl = HttpUtil.BASE_URL + "LeaveAndOvertimeApprovalAgree";
				param1 = "leaveOrOt.leaveOrOtID=" + id
						+ "&leaveOrOt.approvalID1=" + userid
						+ "&leaveOrOt.approvalResult1=" + resultid
						+ "&leaveOrOt.approvalOpinion1="
						+ edit.getText().toString();
				ListTask1 task = new ListTask1(LeaveandovertimeNewListShow.this);
				task.execute();
			}
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, ListTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			return query.Showneirong(id);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getContent(result);// 获得数据
			showListview();
			super.onPostExecute(result);
		}

	}

	class ListTask1 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask1(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(LeaveandovertimeNewListShow.this, this,"正在提交中，请稍候。。。");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// 取消异步

			// 上传管理的提交
			// UploadActivity.tool.addFile("请假加班", actionUrl, param1);
			/*
			 * Intent intent = new Intent(LeaveandovertimeNewListShow.this,
			 * leaveandovertimeNewListMain.class); intent.putExtra("User",
			 * user); startActivity(intent); setResult(20); finish();
			 */

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				hfu.sendPost1(actionUrl, param1);
				result = "审批成功";
				Intent intent = new Intent(LeaveandovertimeNewListShow.this,
						leaveandovertimeNewListMain.class);
				intent.putExtra("User", user);
				startActivity(intent);
				setResult(20);
				finish();

			} catch (Exception e)
			{
				result = "审批失败";
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			Toast.makeText(LeaveandovertimeNewListShow.this, result,
					Toast.LENGTH_SHORT).show();
			if(user!=null)
			new Log(user.getName(), "审核请假加班", GlobalVar.LOGCHECK, "").execute();
			
			super.onPostExecute(result);
		}

	}

}
