package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ChangePhoneNumActivity.TimeCount;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
/*
 * 换绑手机下一步界面
 */
public class ChangePhoneSecondActivity extends Activity implements OnClickListener{

	private RelativeLayout relative_title_final;
	private EditText ed_phone,get_code;
	private Button getmessage,btn_confirm;
	private TimeCount time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone_second);
		init();
	}
	private void init()
	{
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		ed_phone=(EditText)findViewById(R.id.ed_phone);
		get_code=(EditText)findViewById(R.id.get_code);
		
		getmessage=(Button)findViewById(R.id.getmessage);
		getmessage.setOnClickListener(this);
		
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//点击返回按钮
		case R.id.relative_title_final:
			finish();
			break;
		//点击发送验证码按钮	
		case R.id.getmessage:
			getMessage();
			break;
			
			//点击确认按钮
		case R.id.btn_confirm:
			ConfirmTask();
			break;

		default:
			break;
		}
		
	}
	
	
	public void getMessage(){
			String telphone=ed_phone.getText().toString();
			//telphone = get_tel.getText().toString();
			//访问网络
			KJHttp kj = new KJHttp();
			List<String> aa = new ArrayList<String>();
			
			if("".equals(telphone))
			{
				Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
				return;
			}else {
				aa.add("tel="+telphone);
			}

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
					//	Log.i(TAG+":kymjs---http", new String(t));
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
	
	// 计时器
		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) { // 参数依次为总时长,和计时的时间间隔
				super(millisInFuture, countDownInterval);
			}

			@Override
			public void onTick(long millisUntilFinished) { // 计时过程显示
				getmessage.setBackgroundResource(R.drawable.sendmenssage_agree);
				getmessage.setClickable(false);
				getmessage.setText("重发" + millisUntilFinished / 1000 + "秒");
			}

			@Override
			public void onFinish() { // 计时完毕时触发
				getmessage.setText("重新获取");
				getmessage.setClickable(true);
				getmessage.setBackgroundResource(R.drawable.ic_sendsms);
			}
		}
		
		
		
		
		//验证信息接口
		
		public void ConfirmTask(){

			//	telphone = get_tel.getText().toString();
				//访问网络
				KJHttp kj = new KJHttp();
				List<String> aa = new ArrayList<String>();

				
				String code=get_code.getText().toString();
				final String phone=ed_phone.getText().toString();
				if("".equals(phone))
				{
					Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
					return;
				}
				if("".equals(code))
				{
					Toast.makeText(getApplicationContext(), "请输入验证码信息", Toast.LENGTH_SHORT).show();
					return;
				}else
				{
					aa.add("username="+SystemStatic.userName);
					aa.add("tel="+phone);
					aa.add("code="+code);
				
				}

				//访问地址
				org.kymjs.kjframe.http.HttpParams params = null;
				try {
					params = Util.prepareKJparams(aa);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//访问地址
				String toUrl = contants.changetel;
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
						//	Log.i(TAG+":kymjs---http", new String(t));
							String result = new String(t).trim();

							try{
								JSONObject res = new JSONObject(result);
								int resultcode = res.getInt("resultcode");
								if(resultcode==1){
									
									SystemStatic.phoneNum=phone;
									Toast.makeText(getApplicationContext(), "换绑成功", Toast.LENGTH_SHORT).show();
									Intent intent_ok=new Intent(getApplicationContext(),HomeActivity.class);
									startActivity(intent_ok);
								}else if(resultcode==-1){
									Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
								}else if(resultcode==-2){
									Toast.makeText(getApplicationContext(), "手机号码错误", Toast.LENGTH_SHORT).show();
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					});
				}//else
			}
			
		

	

}
