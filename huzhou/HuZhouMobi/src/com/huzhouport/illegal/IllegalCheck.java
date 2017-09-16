package com.huzhouport.illegal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class IllegalCheck extends Activity {
	private ImageButton img_back;
	private Spinner sp;
	private EditText et_content;
	private Button bt_finish;
	private String userId, illegalId;
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
		userId = String.valueOf(user.getUserId());
		illegalId = getIntent().getStringExtra("illegalId");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.illegal_check_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		new GetDate().execute();
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		bt_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new UploadDate(IllegalCheck.this).execute();
			}
		});
	}

	class UploadDate extends AsyncTask<Void, Void, String> {
		ProgressDialog pDialog = null;

		public UploadDate(Context context) {
		
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this,"数据提交中，请稍等······");
			pDialog.show();	
		}

		@Override
		protected String doInBackground(Void... params) {
			Query query=new Query();
			String time =query.getAndAnServiceTime();
			paramsDate.put("illegal.reviewUser", userId);
			paramsDate.put("illegal.reviewResult",
					sp.getSelectedItemPosition() + 1);
			paramsDate.put("illegal.reviewComment", et_content.getText());
			paramsDate.put("illegal.reviewWether", 1);
			paramsDate.put("illegal.reviewTime", time);
			paramsDate.put("illegal.illegalId", illegalId);
			if(isCancelled()) return null;//取消异步
			HttpFileUpTool hfu = new HttpFileUpTool();
			String url = HttpUtil.BASE_URL + "checkIllegalByMobi";
			String result = null;			
			try {
				result = hfu.post(url, paramsDate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
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
					if (jsonObject0.equals("1")) {
						Toast.makeText(IllegalCheck.this, "保存成功",
								Toast.LENGTH_SHORT).show();
						if(user!=null)
						new Log(user.getName(), "审核违章取证", GlobalVar.LOGCHECK, "").execute();
						
						Intent intent=new Intent(IllegalCheck.this,IllegalCheckSearch.class);
						intent.putExtra("User", user);
						startActivity(intent);
						setResult(20);
						finish();		
					}

					else
						Toast.makeText(IllegalCheck.this, "保存失败",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(IllegalCheck.this, "发生错误",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			} else
				Toast.makeText(IllegalCheck.this, "发生错误,请重新保存",
						Toast.LENGTH_SHORT).show();

			super.onPostExecute(result);
		}

	}
	class GetDate extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			Query query=new Query();
			return query.showIllegalInfo(illegalId);
		}

		@Override
		protected void onPostExecute(String result) {
			initDate(result);
			super.onPostExecute(result);
		}
		
	}
	public void initDate(String result){
        JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();			
		// 接下来的就是JSON对象的操作了
		JSONArray jsonArray = data.getJSONArray("list");
		for(int i=0;i<jsonArray.length();i++)
		{
			JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
			JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
			int reviewResult=jsonObject1.getInt("reviewResult"); //1通过2未通过
				if(reviewResult==1) 
				{	
					Toast.makeText(IllegalCheck.this, "已审核", Toast.LENGTH_SHORT).show();
					finish();
				
				}
				else
				{
					sp.setSelection(1);
				}
				if(jsonObject1.getString("reviewComment").equals("null"))
					et_content.setText("无");
				else
					et_content.setText(jsonObject1.getString("reviewComment"));
				
			}
		} catch (Exception e) {
			Toast.makeText(IllegalCheck.this, "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}
