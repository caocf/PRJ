package com.huzhouport.dangerousgoodsportln;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;

import com.huzhouport.model.User;

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

public class DangerousgoodsportlnapprovalSubmit extends Activity
{

	private String	userid;		// �û�id
	private String	id;			// ��ټӰ�id
	private Spinner	spinner_kind;	// ������
	private String	actionUrl , param1;
	private User	user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsportln_approval);
		user = (User) getIntent().getSerializableExtra("User");
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		userid = intent.getStringExtra("userid");

		// ������
		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsportln_approval_ResultNeirong);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.illegal_check_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsportln_approvalback);
		back.setOnClickListener(new Back());

		// �ύ��ť
		ImageButton sumbit = (ImageButton) findViewById(R.id.dangerousgoodsportln_approval_submit);
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
			// ���Ҫ����ֵ

			int resultid;
			String result = spinner_kind.getSelectedItem().toString();
			if (result.equals("ͨ��"))
			{
				resultid = 4;
			}
			else
			{
				resultid = 3;
			}

			TextView reviewOpinionText = (TextView) findViewById(R.id.dangerousgoodsportln_approval_OpinionNeirong);
			String reviewOpinion = reviewOpinionText.getText().toString();
			// �ύ

			actionUrl = HttpUtil.BASE_URL + "DangerousGoodsPortlnApproval";
			// String param1
			// ="leaveOrOtKindbean.approvalID1="+id1+"&leaveOrOtKindbean.approvalID2="+id2+"&leaveOrOtKindbean.approvalID3="+id3+"&leaveOrOtKindbean.leaveOrOtUser="+userid+"&leaveOrOtKindbean.kindName="+kindname+"&leaveOrOtKindbean.leaveOrOtReason="+Reason+"&leaveOrOtKindbean.beginDate="+begintime+"&leaveOrOtKindbean.endDate="+endtime;
			param1 = "declareID=" + id + "&userid=" + userid + "&reviewResult="
					+ resultid + "&reviewOpinion=" + reviewOpinion;
			System.out.println("param====" + param1);

			ListTask task = new ListTask(
					DangerousgoodsportlnapprovalSubmit.this); // �첽
			task.execute();

			/*
			 * //��ӽ���̨ try { hfu.sendPost1(actionUrl, param1);
			 * Toast.makeText(DangerousgoodsportlnapprovalSubmit.this, "����ɹ�",
			 * Toast.LENGTH_SHORT).show();
			 * 
			 * 
			 * Intent intent=new Intent(DangerousgoodsportlnapprovalSubmit.this,
			 * DangerousgoodsportlnList.class); //intent.putExtra("userid",
			 * userid); startActivity(intent);
			 * 
			 * setResult(20); finish();
			 * 
			 * } catch (Exception e) {
			 * Toast.makeText(DangerousgoodsportlnapprovalSubmit.this, "����ʧ��",
			 * Toast.LENGTH_SHORT).show(); e.printStackTrace(); }
			 */
		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;

		public ListTask()
		{

		}

		public ListTask(Context context)
		{
			pDialog = ProgressDialog.show(context, "��ʾ", "�����ύ�У����Ժ򡣡���", true);
		}

		@Override
		protected String doInBackground(String... params)
		{
			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";
			try
			{
				hfu.sendPost1(actionUrl, param1);
				// Toast.makeText(DangerousgoodsportlnapprovalSubmit.this,
				// "����ɹ�", Toast.LENGTH_SHORT).show();
				result = "����ɹ�";

				Intent intent = new Intent(
						DangerousgoodsportlnapprovalSubmit.this,
						DangerousgoodsportlnList.class);
				intent.putExtra("User", user);
				startActivity(intent);
				setResult(20);
				finish();

			}
			catch (Exception e)
			{
				result = "����ʧ��";
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			Toast.makeText(DangerousgoodsportlnapprovalSubmit.this, result,
					Toast.LENGTH_SHORT).show();

			super.onPostExecute(result);
		}

	}
}
