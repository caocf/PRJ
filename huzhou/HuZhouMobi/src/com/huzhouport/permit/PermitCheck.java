package com.huzhouport.permit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class PermitCheck extends Activity {
	private ImageButton img_back;
	private Spinner sp;
	private EditText et_content;
	private Button bt_finish;
	private String inspectionId;
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_check);

		img_back = (ImageButton) findViewById(R.id.illegal_check_back);
		sp = (Spinner) findViewById(R.id.illegal_check_spinner);
		et_content = (EditText) findViewById(R.id.illegal_check_result);
		bt_finish = (Button) findViewById(R.id.illegal_check_finish);

		user = (User) getIntent().getSerializableExtra("User");
		inspectionId = getIntent().getStringExtra("inspectionId");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.illegal_check_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		bt_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new UploadDate(PermitCheck.this).execute();

			}
		});
	}

	class UploadDate extends AsyncTask<Void, Void, String> {
		ProgressDialog pDialog = null;

		public UploadDate() {

		}

		@SuppressWarnings("deprecation")
		public UploadDate(Context context) {
			//pDialog = ProgressDialog.show(context, "提交", "数据提交中，请稍等······",
				//	true);
			pDialog = new ProgressDialog(PermitCheck.this);
			pDialog.setTitle("提交");
			pDialog.setMessage("数据提交中，请稍候···");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (UploadDate.this != null && UploadDate.this.getStatus() == AsyncTask.Status.RUNNING)
					UploadDate.this.cancel(true);
					
				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (UploadDate.this != null && UploadDate.this.getStatus() == AsyncTask.Status.RUNNING)
					UploadDate.this.cancel(true);
					
				}
			});
			pDialog.show();	
		}

		@Override
		protected String doInBackground(Void... params) {
			Query query=new Query();
			String time =query.getAndAnServiceTime();
			paramsDate.put("inspection.reviewUser", user.getUserId());
			paramsDate.put("inspection.reviewResult",
					sp.getSelectedItemPosition() + 1);
			paramsDate.put("inspection.reviewComment", et_content.getText());
			paramsDate.put("inspection.reviewWether", 1);
			paramsDate.put("inspection.reviewTime", time);
			paramsDate.put("inspection.inspectionId", inspectionId);
			if(isCancelled()) return null;//取消异步
			HttpFileUpTool hfu = new HttpFileUpTool();
			String url = HttpUtil.BASE_URL + "checkInspectionByInspectionId";
			String result = null;
			
			try {
				result = hfu.post(url, paramsDate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
			/*String url = HttpUtil.BASE_URL + "checkInspectionByInspectionId";
			UploadActivity.tool.addFile("许可踏勘审核",url, paramsDate,null, null);
			return null;*/
		}

		@Override
		protected void onPostExecute(String result) {

			if (pDialog != null)
				pDialog.dismiss();
			if (result != null) {
				try {
					JSONTokener jsonParser = new JSONTokener(result);
					JSONObject data;

					data = (JSONObject) jsonParser.nextValue();
					String jsonObject0 = data.getString("result");
					if (jsonObject0.equals("1"))
						{
						Toast.makeText(PermitCheck.this, "保存成功",
								Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(PermitCheck.this,PermitCheckSearch.class);
						intent.putExtra("User", user);
						startActivity(intent);
						setResult(20);
						finish();
						}
					else
						Toast.makeText(PermitCheck.this, "保存失败",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(PermitCheck.this, "发生错误", Toast.LENGTH_SHORT)
							.show();
					e.printStackTrace();
				}
			} else
				Toast.makeText(PermitCheck.this, "发生错误", Toast.LENGTH_SHORT)
						.show();
			/*Intent intent=new Intent(PermitCheck.this,PermitCheckSearch.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(20);
			finish();*/
			super.onPostExecute(result);
		}

	}
}
