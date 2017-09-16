package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import com.hxkg.ghpublic.R; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity; 
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPasswordActivity extends Activity{
	private final static String TAG = ForgetPasswordActivity.class.getSimpleName();
	private EditText forget_phone;
	private EditText forget_code;
	private TextView forget_password_Back;
	private Button forget_getcode;
	private EditText forget_password;
	private ImageView forget_password_eye;
	private Button forget_submit;

	private TimeCount time;//计时器

	private String telphone;
	private String code;
	private String newpsw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		forget_phone = (EditText) findViewById(R.id.forget_phone);
		forget_code = (EditText) findViewById(R.id.forget_code);
		forget_getcode = (Button) findViewById(R.id.forget_getcode);
		forget_password = (EditText) findViewById(R.id.forget_password);
		forget_password_eye = (ImageView) findViewById(R.id.forget_password_eye);
		forget_submit = (Button) findViewById(R.id.forget_submit);
		forget_password_Back = (TextView) findViewById(R.id.forget_password_Back);
		
		forget_password_Back.setOnClickListener(onclick);
		forget_getcode.setOnClickListener(onclick);
		forget_submit.setOnClickListener(onclick);
		forget_password_eye.setOnTouchListener(ontouch);
	}

	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.forget_getcode:
				getMessage();
				break;

			case R.id.forget_submit:
				forgetSubmit();
				break;
			case R.id.forget_password_Back:
				finish();
				break;
			default:
				break;
			}
		}
	};
	public OnTouchListener ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub


			switch (v.getId()) {
			case R.id.forget_password_eye:
				if (event.getAction() == MotionEvent.ACTION_DOWN)  
				{   
					forget_password_eye.setImageResource(R.drawable.icon_eye_open);
					forget_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

					return true;  
				}else if (event.getAction() == MotionEvent.ACTION_UP){  
					forget_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
					forget_password_eye.setImageResource(R.drawable.icon_eye_close);
					return true;  
				}
				break;
			default:
				break;
			}
			return false;
		}
	};

	public void getMessage(){

		telphone = forget_phone.getText().toString();
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
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){

				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);

					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();

					try{
						JSONObject res = new JSONObject(result);
						int resultcode = res.getInt("resultcode");
						if(resultcode==1){
							time = new TimeCount(60000, 1000);
							time.start();
						}else{
							Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}//else
	}

	//计时器
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) { //参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) { //计时过程显示
			forget_getcode.setBackgroundResource(R.drawable.sendmenssage_agree);
			forget_getcode.setClickable(false);
			forget_getcode.setText("重发"+millisUntilFinished/1000 + "S");
		}

		@Override
		public void onFinish() { //计时完毕时触发
			forget_getcode.setText("重新获取");
			forget_getcode.setClickable(true);
			forget_getcode.setBackgroundResource(R.drawable.ic_sendsms);
		}
	}


	public void forgetSubmit(){
		telphone = forget_phone.getText().toString();
		code = forget_code.getText().toString();
		newpsw = forget_password.getText().toString();
		if(forget_phone.equals("")){
			Toast.makeText(getApplicationContext(), "请输入手机号！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(forget_code.equals("")){
			Toast.makeText(getApplicationContext(), "请输入验证码！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(forget_password.equals("")){
			Toast.makeText(getApplicationContext(), "请输入新密码！", Toast.LENGTH_SHORT).show();
			return;
		}


		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();

		aa.add("tel="+telphone);
		aa.add("newpsw="+newpsw);
		aa.add("code="+code);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.baseUrl+"findpsw";
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
					//							Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();

					try{
						JSONObject res = new JSONObject(result);
						int resultcode = res.getInt("resultcode");
						if(resultcode==1){
							Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
							finish();
						}else if(resultcode==-1){
							Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
						}else if(resultcode==-2){
							Toast.makeText(getApplicationContext(), "此号码未注册", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
						}
					}catch(Exception e){
						//e.printStackTrace();
						Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}//else这里结束
	}
}
