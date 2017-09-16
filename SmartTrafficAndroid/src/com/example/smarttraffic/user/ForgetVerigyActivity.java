package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetVerigyActivity extends FragmentActivity
{
	public static final String USER_INFO="user_infp";
	
	User user;

	TextView codeTextView;
	TextView verifyTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_register_verify);
		
		user=(User)getIntent().getSerializableExtra(UserRegisterActivity.USER_INFO);
		codeTextView=(TextView)findViewById(R.id.user_register2_verify_test_code);
//		codeTextView.setText("您的验证码已发送到手机，请查收");
//		codeTextView.setText(codeTextView.getText().toString()+user.getVerifyCode());
		verifyTextView=(TextView)findViewById(R.id.user_register2_verify_code);
				
		initHead();
		requestCode();
	}
	
	public void initHead()
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("手机验证");
		fragment.setBackListener(new BackListener(this, "找回密码还未完成是否确定退出？"));
		ManagerFragment.replaceFragment(this, R.id.user_register_verify_head, fragment);
	}
	
	String code = "";

	private void requestCode()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				JSONObject object = JSON.parseObject(data);

				Message message = JSON.parseObject(
						object.getJSONObject("message").toJSONString(),
						Message.class);

				if (message.getStatus() == 1)
					code = object.getString("result");

				System.out.println(data);

				return message;
			}
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams postParams = new PostParams();
				postParams
						.setUrl(HttpUrlRequestAddress.USER_REGISTER_VERIFY_URL);
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("user.phone", user.getPhone());
				
				postParams.setParams(p);

				return postParams;

			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				Message message = (Message) data;

				if (message.getStatus() == 1)
					codeTextView.setText("您的验证码已发送到手机，请注意查收");
				Toast.makeText(ForgetVerigyActivity.this,
						message.getMsg(), Toast.LENGTH_SHORT).show();

			}
		}, "请求验证码失败").start();
	}

	
	public void next(View v)
	{
		
		if (!code.equals(verifyTextView.getText().toString()))
			Toast.makeText(ForgetVerigyActivity.this, "验证码错误",
					Toast.LENGTH_SHORT).show();
		else
		{
			Bundle bundle = new Bundle();
			bundle.putSerializable(USER_INFO, user);
			StartIntent.startIntentWithParam(this,
					ForgetPasswordActivity.class, bundle);
			this.finish();
		}
	}
}
