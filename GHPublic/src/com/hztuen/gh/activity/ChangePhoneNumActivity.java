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
 * 换绑手机界面
 */
public class ChangePhoneNumActivity extends Activity implements OnClickListener 
{
	private RelativeLayout relative_title;
	private Button regist_next, getmessage;
	private TimeCount time;
	private  EditText get_code;
	private EditText tv_12;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone_num);
		init();
	}
	private void init() 
	{
		relative_title = (RelativeLayout) findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);

		regist_next = (Button) findViewById(R.id.regist_next);
		regist_next.setOnClickListener(this);
		regist_next.setText("下一步");

		getmessage = (Button) findViewById(R.id.getmessage);
		getmessage.setOnClickListener(this);
		
		get_code=(EditText)findViewById(R.id.get_code);
		tv_12=(EditText)findViewById(R.id.tel);
		tv_12.setText(SystemStatic.phoneNum);
		tv_12.setEnabled(false);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		// 点击返回按钮
		case R.id.relative_title:
			finish();

			break;

		// 点击下一步
		case R.id.regist_next:
			
			YanzhengTask();
			break;
		// 点击发送验证码按钮
		case R.id.getmessage:
			getMessage(1);
			break;

		default:
			break;
		}

	}

	// 计时器
	class TimeCount extends CountDownTimer 
	{
		public TimeCount(long millisInFuture, long countDownInterval) 
		{ // 参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{ // 计时过程显示
			getmessage.setBackgroundResource(R.drawable.sendmenssage_agree);
			getmessage.setClickable(false);
			getmessage.setText("重发" + millisUntilFinished / 1000 + "秒");
		}

		@Override
		public void onFinish() 
		{ // 计时完毕时触发
			getmessage.setText("重新获取");
			getmessage.setClickable(true);
			getmessage.setBackgroundResource(R.drawable.ic_sendsms);
		}
	}
	
	
	public void getMessage(final int f)
	{ 
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();
			
		
		
		if("".equals(tv_12.getText().toString().trim()))
		{
				Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
				return;
		}
		aa.add("tel="+tv_12.getText().toString().trim());
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
	
	
	
	//验证信息接口	
	public void YanzhengTask()
	{ 
			KJHttp kj = new KJHttp();
			List<String> aa = new ArrayList<String>();

			
			String code=get_code.getText().toString();
			
			if("".equals(code))
			{
				Toast.makeText(getApplicationContext(), "请输入验证码信息", Toast.LENGTH_SHORT).show();
			}else
			{
				aa.add("username="+SystemStatic.userName); 
				aa.add("tel="+SystemStatic.phoneNum);
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
			String toUrl = contants.baseUrl+"validate";
			if(params == null){
				//提示参数制造失败
				Util.getTip(getApplicationContext(), "请输入手机号");
			}else if(!toUrl.equals(""))
			{
				kj.post(toUrl, params, false, new HttpCallBack()
				{
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) 
					{ 
						super.onSuccess(headers, t);

						// 获取cookie
						KJLoger.debug("===" + headers.get("Set-Cookie")); 
						String result = new String(t).trim();
						try
						{
							JSONObject res = new JSONObject(result);
							int resultcode = res.getInt("resultcode");
							if(resultcode==1||resultcode==-3)
							{ 
								Intent intent=new Intent(ChangePhoneNumActivity.this,ChangePhoneNum2Activity.class);
								ChangePhoneNumActivity.this.startActivity(intent);
								finish();
								
							}else  
							{
								Toast.makeText(getApplicationContext(), res.getString("resultdesc"), Toast.LENGTH_SHORT).show();
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			}//else
		}
		
	

}
