package com.huzhouport.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.model.ShipBinding;
import com.huzhouport.model.User;
import com.huzhouport.model.WharfBinding;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {
	private EditText et01, et02;
	private User user = new User();
	private int num = 0;
	Map<String, Object> params = new HashMap<String, Object>();
    private int checkboxvalue1=0,checkboxvalue2=0;
    private CheckBox cb1,cb2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.login);

		et01 = (EditText) findViewById(R.id.login_username);
		et02 = (EditText) findViewById(R.id.login_psd);
		onKeyDown(KeyEvent.KEYCODE_BACK, null);// 返回按钮
		num++;
		ImageButton img_exit = (ImageButton) findViewById(R.id.login_back);
		img_exit.setOnClickListener(new Exit());
		
		 cb1 = (CheckBox  )findViewById(R.id.login_checkbox1);
		 cb2 = (CheckBox  )findViewById(R.id.login_checkbox2);
		 cb1.setChecked(true);
		 cb2.setChecked(true);
		
		SharedPreferences oSharedPreferences = this.getSharedPreferences(
				"UserDataPublic", 0);
		if(oSharedPreferences.getString("checkboxvalue1", "0").equals("1")){
			//cb1 = (CheckBox  )findViewById(R.id.login_checkbox1);
			cb1.setChecked(true);
			cb2.setChecked(false);
			et01.setText(oSharedPreferences.getString("Username", ""));
			et02.setText(oSharedPreferences.getString("Password", ""));
		}else{
			cb1.setChecked(false);
			cb2.setChecked(false);
			et01.setText(oSharedPreferences.getString("Username", ""));
		}
	}

	class Exit implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (num == 1) {
				finish();
			}

		}

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

	public void loginButton(View view) {
		if (validate()) {
			new Logining(this).execute();
		}

	}
	public void WarnButton(View view) {
		Intent intent=new Intent(this,Warn.class);
		startActivity(intent);

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
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (Logining.this != null
							&& Logining.this.getStatus() == AsyncTask.Status.RUNNING)
						Logining.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (Logining.this != null
							&& Logining.this.getStatus() == AsyncTask.Status.RUNNING)
						Logining.this.cancel(true);

				}
			});
			pDialog.show();
		}

		protected Integer doInBackground(Void... params) {
			if (isCancelled())
				return null;
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
			case 0:
				showDialog("网络异常！");
				et01.setText("");
				et01.setFocusable(true);
				et02.setText("");
				break;
			case 1:
				Intent intent = new Intent(Login.this, MainPage.class);
				intent.putExtra("User", user);
				intent.putExtra("username", et01.getText().toString());
				intent.putExtra("password", et02.getText().toString());
				intent.putExtra("checkboxvalue1", checkboxvalue1+"");
				intent.putExtra("checkboxvalue2", checkboxvalue2+"");
				startActivity(intent);
				break;
			case 2:
				showDialog("用户名不存在，请重新输入！");
				et01.setText("");
				et02.setText("");
				et01.setFocusable(true);
				break;
			case 3:
				showDialog("密码错误，请重新输入！");
				et02.setText("");
				et02.setFocusable(true);
				break;
			case 5:
				showDialog("您的账号未绑定该手机，请联系管理员");//imei号或iccid号不正确！
				et02.setText("");
				et02.setFocusable(true);
				break;
			default:
				showDialog("网络异常！");
				break;
			}
			super.onPostExecute(result);
		}

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
				jsonObject1 = data.getInt("allTotal");
				if (jsonObject1 == 1) {
					GetModelDate(data);
				}

			}
		} catch (Exception e) {
			jsonObject1 = 0;
		}
		return jsonObject1;
	}

	private void GetModelDate(JSONObject data) {
		try {
			JSONObject sUser = data.getJSONObject("publicUser");
			JSONArray sBindingList = data.getJSONArray("shipBindingList");
			JSONArray wBindingList = data.getJSONArray("wharfBindingList");
			user.setUserId(sUser.getInt("userId"));
			user.setUserName(sUser.getString("userName"));
			user.setPsd(sUser.getString("psd"));
			user.setPhoneNumber(sUser.getString("phoneNumber"));
			user.setImei(sUser.getString("imei"));
			user.setIccid(sUser.getString("iccid"));
			
			
			if (sBindingList.length()!=0) {
				List<ShipBinding> sBindings = new ArrayList<ShipBinding>();
				for(int j=0;j<sBindingList.length();j++){
					JSONObject sBinding=(JSONObject) sBindingList.opt(j);
					ShipBinding sBinding2 = new ShipBinding();
					sBinding2.setShipId(sBinding.getInt("shipId"));
					sBinding2.setShipNum(sBinding.getString("shipNum"));
					sBinding2.setShipUser(sBinding.getInt("shipUser"));
					sBinding2.setBindingTime(CountTime.FormatTimeToDay(sBinding.getString("bindingTime")));	
					sBindings.add(sBinding2);
					if(sUser.getString("bindName").equals(sBinding.getString("shipNum"))){
						user.setBindnum(j);
					}
				}
				
				user.setShipBindingList(sBindings);
			} else {
				user.setShipBindingList(null);
			}
			if (wBindingList.length() != 0) {
				List<WharfBinding> wBindings = new ArrayList<WharfBinding>();
				for (int j = 0; j < wBindingList.length(); j++) {
					JSONObject wBinding = (JSONObject) wBindingList.opt(j);
					WharfBinding wBinding2 = new WharfBinding();
					wBinding2.setWharfId(wBinding.getInt("wharfId"));
					wBinding2.setWharfNum(wBinding.getString("wharfNum"));
					wBinding2.setWharfUser(wBinding.getInt("wharfUser"));
					wBinding2.setBindingTime(CountTime.FormatTimeToDay(wBinding
							.getString("bindingTime")));
					wBinding2.setWharfNumber(wBinding.getString("wharfNumber"));
					wBinding2.setWharfWorkSurplus(wBinding
							.getString("wharfWorkSurplus"));
					wBindings.add(wBinding2);
					if(sUser.getString("bindName").equals(wBinding.getString("wharfNum"))){
						user.setBindnum(j);
					}
				}
				user.setWharfBindingList(wBindings);
			} else {
				user.setWharfBindingList(null);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

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
		//获取imei号
		 TelephonyManager tm = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
		params.put("publicUser.imei", tm.getDeviceId());
		params.put("publicUser.iccid", tm.getSimSerialNumber());
		params.put("publicUser.userName", username);
		params.put("publicUser.psd", password);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "LoginPublicMobi";
		
		System.out.println("登录地址 "+actionUrl);
		
		String result = null;
		try {
			result = hfu.post(actionUrl, params);
			
			System.out.println(result);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
	}

	

	
}
