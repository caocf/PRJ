package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import org.json.JSONObject;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LeaveandovertimeNewListShow extends Activity
{
	private Query query = new Query();

	private String userid, id, username;
	private String actionUrl, param1;
	private String resultid;
	private ListView lv;
	private ArrayList<HashMap<String, Object>> leaveview=new ArrayList<>();
	
	RelativeLayout re;
	EditText edit;
	
	int mode=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.leaveandovertime_newlist_show);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");//System.out.println(id);
		mode=intent.getIntExtra("mode", 0);
				
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
		//noButton.setOnClickListener(new Nobutton());
		//yesButton.setOnClickListener(new Yesbutton());

		ImageButton sendButton = (ImageButton) findViewById(R.id.leaveandovertime_newlist_show_send);
		sendButton.setOnClickListener(new Sendbutton());

		lv = (ListView) findViewById(R.id.leaveandover_newlist_showlist);
		ListTask task = new ListTask(this); //		
		task.execute();
		
		re=(RelativeLayout) findViewById(R.id.leaveandovertime_newlist_show_footR);
		edit=(EditText) findViewById(R.id.edit);
	}

	public void onBack(View view)
	{
		finish();
	}
	
	public void getContent(String result)
	{
		//System.out.println(result);
		//JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data =new JSONObject(result);
			JSONObject user=data.getJSONObject("user"); 
			
			JSONObject approvalID1=null;
			Object object=data.get("approvalID1");
			if(object instanceof JSONObject)
				approvalID1=(JSONObject) object;
				
			
			String lastDate = data.getString("lastDate")+"H";
		
			
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_leaveOrOtUserNeirong, user.getString("xm"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_beginDateNeirong, data.getString("beginDate"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_show_endDateNeirong, data.getString("endDate"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_lastDateNeirong, lastDate);
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_addressNeirong, data.getString("address"));
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_leaveOrOtReasonNeirong, data.getString("leaveOrOtReason"));
			if(data.getString("approvalOpinion1").equals("null")){
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,"" );
			}else{
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalOpinion,data.getString("approvalOpinion1") );
			}
			String approvalResult = "";
			int color;
			if (data.getString("approvalResult").equals("待审批")|| data.getString("approvalResult").equals("正在审批"))
			{
				approvalResult = "待审批";
				color=R.color.leave_yellow;
				if(mode==0)
				{
					re.setVisibility(View.VISIBLE);
				}
				
			} 
			else if (data.getString("approvalResult").equals("驳回"))
			{
				approvalResult = "不批准";
				color=R.color.leave_red;
			} else
			{
				approvalResult = "批准";
				color=R.color.leave_green;
			}


			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalResultNeirong, approvalResult,color);
			if(approvalID1!=null)
			CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID1Neirong, approvalID1.getString("xm"));
			
			if (data.getString("approvalID2").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_show_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_show_tr2)
						.setVisibility(View.GONE);
			} else
			
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID2Neirong, data.getString("approvalID2"));

			
			if (data.getString("approvalID3").equals("0"))
			{
				findViewById(R.id.leaveandovertime_newlist_show_tr1)
						.setVisibility(View.GONE);
				findViewById(R.id.leaveandovertime_newlist_show_tr2)
						.setVisibility(View.GONE);
			} else
				CommonView.setTextviewValue(this, R.id.leaveandovertime_newlist_show_approvalID3Neirong, data.getString("approvalID3"));

			leaveview = new ArrayList<HashMap<String, Object>>();
			if(approvalID1!=null)
			easyParse(1, data,approvalID1);
			//easyParse(2, data);
			//easyParse(3, data);

		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListShow.this, "数据异常",
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
	
	public void easyParse(int i,JSONObject jsonObject,JSONObject jsonObject1) throws Exception
	{
		String param1="approvalResult"+i;
		String param2="name";
		String param3="approvalTime"+i;
		String param4="approvalOpinion"+i;

		if(!jsonObject.getString(param1).equals("2") && !jsonObject.getString(param1).equals("1"))
			return;
		
		String text1 = "";
		String text2 = "";
		String text3 = "";

		text1 = jsonObject1.getString(param2);
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
			et.requestFocus(); 
			// et.setFocusable(true);
			et.setFocusableInTouchMode(true);
		}
	}

	class Yesbutton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			/*resultid = "1"; 
			actionUrl = HttpUtil.BASE_URL + "LeaveAndOvertimeApprovalAgree";
			param1 = "leaveOrOt.leaveOrOtID=" + id + "&leaveOrOt.approvalID1="
					+ userid + "&leaveOrOt.approvalResult1=" + resultid
					+ "&leaveOrOt.approvalOpinion1=";
			ListTask1 task = new ListTask1(LeaveandovertimeNewListShow.this);
			task.execute();*/
			
			
		}
	}
	
	public void onButton(View v)
	{
		String resultString;
		if(v.getId()==R.id.leaveandovertime_newlist_show_yes)
		{
			resultString="3";//准许
		}else {
			resultString="4";
		}
		HttpRequest request=new HttpRequest(new HttpRequest.onResult()
		{
			@Override
			public void onSuccess(String result)
			{
				Toast.makeText(LeaveandovertimeNewListShow.this, "提交成功",
						Toast.LENGTH_LONG).show();
				LeaveandovertimeNewListShow.this.finish();
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		map.put("opinon", edit.getText().toString());
		map.put("result", resultString);
		request.post(Constants.BaseURL+"CheckLeave", map);
	}

	class Sendbutton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			EditText edit = (EditText) findViewById(R.id.leaveandover_newlist_show_input);
			if (edit.getText().toString().equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListShow.this, "",
						Toast.LENGTH_SHORT).show();
			} else
			{

				resultid = "2"; 
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
				return null;

			String idd=id;
			return query.Showneirong(id);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getContent(result);
			showListview();
			super.onPostExecute(result);
		}

	}

	class ListTask1 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask1(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(LeaveandovertimeNewListShow.this, this,"数据加载中...");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				//hfu.sendPost1(actionUrl, param1);
				result = "审批成功";
				Intent intent = new Intent(LeaveandovertimeNewListShow.this,leaveandovertimeNewListMain.class);
				//intent.putExtra("User", user);
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
			
			super.onPostExecute(result);
		}

	}

}
