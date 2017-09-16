package com.huzhouport.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.schedule.AarmAlertService;
import com.huzhouport.slidemenu.MainActivity;
import com.huzhouport.slidemenu.UserMod;
import com.hxkg.meeting.KeyboardChangeListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements KeyboardChangeListener.KeyBoardListener
{
	private EditText et01, et02;
	private User user = new User();
	private int num = 0;
	Map<String, Object> params = new HashMap<String, Object>();
    private int checkboxvalue1=0,checkboxvalue2=0;
    private CheckBox cb1,cb2;
    ScrollView scrollView;
    KeyboardChangeListener keyboardChangeListener;
    TextView t1,t2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.login);	
		et01 = (EditText) findViewById(R.id.EditText01);
		et02 = (EditText) findViewById(R.id.EditText02);
		onKeyDown(KeyEvent.KEYCODE_BACK, null);// 返回按钮
		num++;
		 cb1 = (CheckBox  )findViewById(R.id.login_checkbox1);
		 cb2 = (CheckBox  )findViewById(R.id.login_checkbox2);
		 cb1.setChecked(true);
		 cb2.setChecked(true);
		
		//保存密码
		SharedPreferences oSharedPreferences = this.getSharedPreferences(
				"UserData", 0);
		if(oSharedPreferences.getString("checkboxvalue1", "0").equals("1")){
			//CheckBox  cb1 = (CheckBox  )findViewById(R.id.login_checkbox1);
			cb1.setChecked(true);
			cb2.setChecked(false);
			et01.setText(oSharedPreferences.getString("Username", ""));
			et02.setText(oSharedPreferences.getString("Password", ""));
		}else{
			cb1.setChecked(false);
			cb2.setChecked(false);
			et01.setText(oSharedPreferences.getString("Username", ""));
		}
		
		scrollView=(ScrollView) findViewById(R.id.login_ScrollView);
		keyboardChangeListener=new KeyboardChangeListener(this);
		keyboardChangeListener.setKeyBoardListener(this);
		t1=(TextView) findViewById(R.id.textcc1);
		t2=(TextView) findViewById(R.id.textcc2);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			if (num == 1) {
				finish();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class loginButton implements OnClickListener {
		public void onClick(View v) {
			if (!validate()) {
				
			}
		}
	}
	public void loginButton(View view){
		if (validate()) {
			new Logining(this).execute();
		}
		
	}

	class Logining extends AsyncTask<Void, Void, Integer> {
		ProgressDialog pDialog = null;

		public Logining() {
		}

		@SuppressWarnings("deprecation")
		public Logining(Context content) {
			pDialog = new ProgressDialog(Login.this);
			pDialog.setTitle("登陆");
			pDialog.setMessage("登陆中·····");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					 if (Logining.this != null && Logining.this.getStatus() == AsyncTask.Status.RUNNING) 
						 	Logining.this.cancel(true);
					
				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					 if (Logining.this != null && Logining.this.getStatus() == AsyncTask.Status.RUNNING) 
						 	Logining.this.cancel(true);
					
				}
			});
			pDialog.show();	
		}

		protected Integer doInBackground(Void... params) {
			if(isCancelled()) return null;
			return getLoginInfo();
		}

		protected void onPostExecute(Integer result) {
			CheckBox  cb1 = (CheckBox  )findViewById(R.id.login_checkbox1);
			if(cb1.isChecked()){
				checkboxvalue1=1;
			}
			CheckBox  cb2 = (CheckBox  )findViewById(R.id.login_checkbox2);
			if(cb2.isChecked()){
				checkboxvalue2=1;
			}
			
			if (pDialog != null)
				pDialog.dismiss();
			switch (result) {
			case 1:
				openSevice();//打开闹钟服务
				Intent intent = new Intent(Login.this, MainActivity.class);
				intent.putExtra("User", user);
				intent.putExtra("username", et01.getText().toString());
				intent.putExtra("password", et02.getText().toString());
				intent.putExtra("checkboxvalue1", checkboxvalue1+"");
				intent.putExtra("checkboxvalue2", checkboxvalue2+"");
				UserMod.name= et01.getText().toString();
				startActivity(intent);
				finish();
				break;
			case 2:
				showDialog("用户名不存在，请重新输入！");
				et01.setText("");
				et02.setText("");
				et01.setFocusable(true);
				break;
			case 3:
				showDialog("用户名或密码错误，请重新输入！");
				et02.setText("");
				et02.setFocusable(true);
				break;
			case 4:
				showDialog("没有权限，请重新输入！");
				et01.setText("");
				et01.setFocusable(true);
				break;
			default:
				showDialog("网络异常！");
				break;
			}
			super.onPostExecute(result);
		}

	}
	// 打开闹钟服务
		public void openSevice() {
			Intent intent=new Intent(Login.this, AarmAlertService.class);
			intent.putExtra("User", user);
			startService(intent);
		}

	// 登陆，取的服务器返回值
	private int getLoginInfo() {
		String username = et01.getText().toString();
		String pwd = et02.getText().toString();
		int jsonObject1 = 0;
		String result = query(username, pwd);
		try {
			if (result != null) {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				data = (JSONObject) jsonParser.nextValue();
				jsonObject1 = data.getInt("loginRes");
				if (jsonObject1 == 1) {
					JSONObject jsonObject2 = data.getJSONObject("model");
					user.setUserId(jsonObject2.getInt("userId"));
					user.setName(jsonObject2.getString("name"));
					user.setPartOfDepartment(jsonObject2.getInt("partOfDepartment"));
					user.setPartOfPost(jsonObject2.getInt("partOfPost"));
					// 权限
					JSONArray jsonObject3 = data.getJSONArray("list");
					List<RolePermission> list_rpList = new ArrayList<RolePermission>();
					for (int i = 0; i < jsonObject3.length(); i++) {
						JSONArray jsonObject4 = (JSONArray) jsonObject3.opt(i);
						JSONObject jObject1 = (JSONObject) jsonObject4.opt(0);
						RolePermission rp = new RolePermission();
						rp.setPermissionId(jObject1.getInt("permissionId"));
						rp.setStatus(jObject1.getInt("status"));
						list_rpList.add(rp);
					}
					user.setRpList(list_rpList);
				}
			} 
		} catch (Exception e) {
			jsonObject1=0;
		}
		return jsonObject1;
	}

	// 验证输入
	private boolean validate() {
		if (et01.getText().toString() == null || et01.getText().length() == 0) {
			Toast.makeText(Login.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (et02.getText().toString() == null
				|| et02.getText().length() == 0) {
			Toast.makeText(Login.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;

		}
		return true;
	}

	private void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", null);
		AlertDialog alert = builder.create();
		alert.show();
	}

	private String query(String username, String password) {
		params.put("user.userName", username);
		params.put("user.password", password);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "login_mobi";
		String result = null;
		try {
			result = hfu.post(actionUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void onKeyboardChange(boolean isShow, int keyboardHeight) 
	{
		if(isShow)
		{
			scrollView.scrollBy(0, 60);
			t1.setTextColor(Color.parseColor("#333333"));
			t2.setTextColor(Color.parseColor("#333333"));
		}else {
			scrollView.scrollBy(0, -60);
			t1.setTextColor(Color.parseColor("#333333"));
			t2.setTextColor(Color.parseColor("#333333"));
		}
		
		//System.out.print("bbbbbbbbbbbbbbbbbbbb");
	}

}
