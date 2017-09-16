package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:22:50
 * 注册界面
 */
public class RegistActivity extends Activity
{
	private TextView regist_back;
	
	private Button getmessage;//获取验证码
	private EditText get_code; 
	
	private EditText get_tel;
	private EditText get_username;
	UserTypeSpinner userTypeSpinner;
	CitySpinner citySpinner;
	private EditText get_password;
	private ImageView regist_eye;	
	
	private TimeCount time;	
	private boolean flag = false;
	
	private Button regist_next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
 
		getmessage = (Button) findViewById(R.id.getmessage);
		get_code = (EditText) findViewById(R.id.get_code);
		
		userTypeSpinner=(UserTypeSpinner) findViewById(R.id.usertype);
		citySpinner=(CitySpinner) findViewById(R.id.city);
		get_tel = (EditText) findViewById(R.id.get_tel);
		get_username = (EditText) findViewById(R.id.get_username);		
		get_password = (EditText) findViewById(R.id.get_password);
		regist_eye = (ImageView) findViewById(R.id.regist_eye);
		
		regist_next = (Button) findViewById(R.id.regist_next);
		regist_back = (TextView) findViewById(R.id.regist_back);
	 
		getmessage.setOnClickListener(onclicklistener);
		regist_next.setOnClickListener(onclicklistener);
		regist_back.setOnClickListener(onclicklistener);
	
		regist_eye.setOnClickListener(onclicklistener);
	}
	
	public OnClickListener onclicklistener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			 
			case R.id.getmessage:
				if(null!=get_tel.getText().toString().trim() && !get_tel.getText().toString().trim().equals("")&&get_tel.getText().toString().trim().length()==11){
					getMessage(get_tel.getText().toString().trim());
				}else{
					Toast.makeText(getApplicationContext(), "手机号格式错误", Toast.LENGTH_SHORT).show();
					return;
				}
				break;
			case R.id.regist_next:
				/*Intent intent=new Intent(getApplicationContext(), RegistSecondPageActivity.class);
				intent.putExtra("area", citySpinner.getSelectedItemPosition()+1);
				intent.putExtra("telphone", get_tel.getText().toString().trim());
				intent.putExtra("username", get_username.getText().toString().trim());							
				intent.putExtra("password", get_password.getText().toString().trim());
				intent.putExtra("type", userTypeSpinner.getSelectedItemPosition()+1);*/
				//startActivity(intent);
				nextCheck();//注册下一步
				break;	
			case R.id.regist_back:
				RegistActivity.this.finish();
				break;
			case R.id.regist_eye:
				if(Switch()){
					regist_eye.setImageResource(R.drawable.icon_eye_open);
					get_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else{
					get_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
					regist_eye.setImageResource(R.drawable.icon_eye_close);
				}
				break;
			default:
				break;
			}
		}
	};
	 
	public void nextCheck()
	{
		String telphone = get_tel.getText().toString().trim();
		String username = get_username.getText().toString().trim();
		String code = get_code.getText().toString();
		String password = get_password.getText().toString().trim();
		int cityid=citySpinner.getSelectedItemPosition()+1;
		int usertypeid=userTypeSpinner.getSelectedItemPosition()+1;
		
		if(username.equals("")){
			Toast.makeText(getApplicationContext(), "请输入用户名！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(telphone.equals("")){
			Toast.makeText(getApplicationContext(), "请输入手机号！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(code.equals("")){
			Toast.makeText(getApplicationContext(), "请输入验证码！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(password.equals("")){
			Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
			return;
		}
		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();

		aa.add("tel="+telphone);
		aa.add("username="+username);
		aa.add("code="+code);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.nextcheck;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){

				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try{
						JSONObject res = new JSONObject(result);
						int resultcode = res.getInt("resultcode");
						if(resultcode==1){
							Intent intent = new Intent();
							intent.putExtra("area", citySpinner.getSelectedItemPosition()+1);
							intent.putExtra("telphone", get_tel.getText().toString().trim());
							intent.putExtra("username", get_username.getText().toString().trim());							
							intent.putExtra("password", get_password.getText().toString().trim());
							intent.putExtra("type", userTypeSpinner.getSelectedItemPosition()+1);
							if(userTypeSpinner.getSelectedItemPosition()+1==1)
							{
								intent.setClass(getApplicationContext(), RegistSecondPageActivity.class);
							}else{
								intent.setClass(getApplicationContext(), RegistSecondCoastal.class);
							}
							startActivity(intent);	
							RegistActivity.this.finish();
						}else if(resultcode==-1){
							Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
						}else if(resultcode==-2){
							Toast.makeText(getApplicationContext(), "手机号码错误", Toast.LENGTH_SHORT).show();
						}else if(resultcode==-3){
							Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
						}
					}catch(Exception e){
						//e.printStackTrace();
						Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}//else这里结束
	}

	/**
	 * 获取验证码
	 * */
	public void getMessage(String telphone){
		telphone = get_tel.getText().toString();
		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("tel="+telphone);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.getmessage;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "请输入手机号");
		}else if(!toUrl.equals(""))
		{
			time = new TimeCount(60000, 1000);
			time.start();
			
			kj.post(toUrl, params, false, new HttpCallBack()
			{
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try{
						JSONObject res = new JSONObject(result);
						int resultcode = res.getInt("resultcode");
						if(resultcode==1){
							
						}else{
							
						}
					}catch(Exception e){
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}//else
	}
	/**
	 * 计时器
	 * */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) { //参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onTick(long millisUntilFinished) { //计时过程显示
			getmessage.setBackgroundResource(R.drawable.sendmenssage_agree);
			getmessage.setClickable(false);
			getmessage.setText("重发"+millisUntilFinished/1000 + "秒");
		}
		@Override
		public void onFinish() { //计时完毕时触发
			getmessage.setText("重新获取");
			getmessage.setClickable(true);
			getmessage.setBackgroundResource(R.drawable.ic_sendsms);
		}
	}
	/**
	 * @return
	 * 眼睛开关
	 */
	public boolean Switch(){

		if(flag){
			flag = false;
		}else{
			flag = true;
		}
		return flag;

	}
}
