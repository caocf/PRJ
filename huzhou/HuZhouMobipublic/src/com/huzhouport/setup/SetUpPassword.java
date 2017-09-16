package com.huzhouport.setup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.model.User;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetUpPassword extends Activity {
	private TextView tv_oldpassword, tv_newPassword, tv_checkPassword;
	private User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_password);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_password_back);
		ImageButton img_submit = (ImageButton) findViewById(R.id.setup_password_submit);
		tv_oldpassword = (TextView) findViewById(R.id.setup_password_old);
		tv_newPassword = (TextView) findViewById(R.id.setup_password_new);
		tv_checkPassword = (TextView) findViewById(R.id.setup_password_check);
		
		user = (User) getIntent().getSerializableExtra("User");
		
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		img_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(validate()){
				new SubmitDate().execute();
				}
			}
		});
	}
	private Boolean  validate()
	{
		if (tv_oldpassword.getText().length() == 0)
		{
			Toast.makeText(SetUpPassword.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		else if ( tv_newPassword.getText().length() == 0)
		{
			Toast.makeText(SetUpPassword.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
			return false;

		}else if (tv_checkPassword.getText().length() == 0)
		{
			Toast.makeText(SetUpPassword.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
			return false;

		}else if (!(isNumAndEng(tv_oldpassword.getText().toString()) ))
		{
			Toast.makeText(SetUpPassword.this, "原密码只能是数字和英文", Toast.LENGTH_SHORT).show();
			return false;

		}
		else if (!(isNumAndEng(tv_newPassword.getText().toString()) ))
		{
			Toast.makeText(SetUpPassword.this, "新密码只能是数字和英文", Toast.LENGTH_SHORT).show();
			return false;

		}
		else if (!(isNumAndEng(tv_checkPassword.getText().toString()) ))
		{
			Toast.makeText(SetUpPassword.this, "确认密码只能是数字和英文", Toast.LENGTH_SHORT).show();
			return false;

		}
		else if ( tv_newPassword.getText().length() <6)
		{
			Toast.makeText(SetUpPassword.this, "新密码不能少于6位", Toast.LENGTH_SHORT).show();
			return false;

		}else if (tv_checkPassword.getText().length()<6)
		{
			Toast.makeText(SetUpPassword.this, "确认密码不能少于6位", Toast.LENGTH_SHORT).show();
			return false;

		}
		else if (!tv_newPassword.getText().toString().equals(tv_checkPassword.getText().toString()) )
		{
			Toast.makeText(SetUpPassword.this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
			return false;

		}
		return true;
	}
	public static boolean isNumAndEng(String str){

	    Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");

	    return pattern.matcher(str).matches();   

	 }
	class SubmitDate extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			Map<String, Object>	paramsDate	= new HashMap<String, Object>();
			
			paramsDate.put("publicUser.userId", user.getUserId());
			paramsDate.put("publicUser.userName", tv_oldpassword.getText());
			paramsDate.put("publicUser.psd", tv_newPassword.getText());
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "ChangePsdPublicMobi";
			String result = null;
			try
			{
				result = hfu.post(actionUrl, paramsDate);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null)
			{try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				
					data = (JSONObject) jsonParser.nextValue();
				
				int jsonObject1 = data.getInt("allTotal");
				if (jsonObject1 == 1)
				{
					Toast.makeText(SetUpPassword.this, "修改成功！", Toast.LENGTH_LONG).show();
					finish();
				}else if(jsonObject1 == 2){
					Toast.makeText(SetUpPassword.this, "原密码错误", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(SetUpPassword.this, "修改失败！请重新提交", Toast.LENGTH_LONG).show();
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
		
	}
}
