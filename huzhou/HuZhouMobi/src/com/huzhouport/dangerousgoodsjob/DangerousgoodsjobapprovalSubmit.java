package com.huzhouport.dangerousgoodsjob;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DangerousgoodsjobapprovalSubmit extends Activity
{

	private String userid; // 用户id
	private String id; // 请假加班id
	private Spinner spinner_kind; // 下拉框
	private String actionUrl, param1;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_approval);
		user = (User) getIntent().getSerializableExtra("User");
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		userid = intent.getStringExtra("userid");

		// 下拉框
		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsjob_approval_ResultNeirong);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.illegal_check_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsjob_approvalback);
		back.setOnClickListener(new Back());

		// 提交按钮
		ImageButton sumbit = (ImageButton) findViewById(R.id.dangerousgoodsjob_approval_submit);
		sumbit.setOnClickListener(new Sumbit());

	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			finish();
		}
	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// 获得要传的值

			int resultid;
			String result = spinner_kind.getSelectedItem().toString();
			if (result.equals("通过"))
			{
				resultid = 4;
			} else
			{
				resultid = 3;
			}

			TextView reviewOpinionText = (TextView) findViewById(R.id.dangerousgoodsjob_approval_OpinionNeirong);
			String reviewOpinion = reviewOpinionText.getText().toString();

			actionUrl = HttpUtil.BASE_URL + "DangerousGoodsJobApproval";
			// String param1
			// ="leaveOrOtKindbean.approvalID1="+id1+"&leaveOrOtKindbean.approvalID2="+id2+"&leaveOrOtKindbean.approvalID3="+id3+"&leaveOrOtKindbean.leaveOrOtUser="+userid+"&leaveOrOtKindbean.kindName="+kindname+"&leaveOrOtKindbean.leaveOrOtReason="+Reason+"&leaveOrOtKindbean.beginDate="+begintime+"&leaveOrOtKindbean.endDate="+endtime;
			param1 = "declareID=" + id + "&userid=" + userid + "&reviewResult="
					+ resultid + "&reviewOpinion=" + reviewOpinion;

			ListTask task = new ListTask(DangerousgoodsjobapprovalSubmit.this); // 异步
			task.execute();

		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this, "正在提交中，请稍候。。。");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				hfu.sendPost1(actionUrl, param1);
				result = "审批成功";
				Intent intent = new Intent(
						DangerousgoodsjobapprovalSubmit.this,
						DangerousgoodsjobList.class);
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

		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			Toast.makeText(DangerousgoodsjobapprovalSubmit.this, result,
					Toast.LENGTH_SHORT).show();
			if(user!=null)
			new Log(user.getName(), "审核危险货物码头作业", GlobalVar.LOGCHECK, "")
					.execute();
			super.onPostExecute(result);
		}

	}
}
