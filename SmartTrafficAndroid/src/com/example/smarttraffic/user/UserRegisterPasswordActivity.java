package com.example.smarttraffic.user;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.EmailVerify;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 用户注册步骤三
 * 
 * @author Administrator zhou
 * 
 */
public class UserRegisterPasswordActivity extends FragmentActivity implements
		UpdateView
{
	public static final String USER_INFO = "user_info";

	public void update(Object data)
	{
		if(data instanceof Message)
		{
			Message message = (Message) data;
			Toast.makeText(this, message.getMsg(), Toast.LENGTH_SHORT).show();
		}

		else if (data instanceof User)
		{
			Bundle bundle = new Bundle();
			bundle.putSerializable(USER_INFO, (User)data);
			UserControl.setUser((User)data);
			StartIntent.startIntentWithParam(this, UserMainActivity.class,
					bundle);
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			this.finish();
		} 

	}

	EditText usernameEditText;
	EditText passwordEditText;
	EditText passwordAgainEditText;
	EditText emailEditText;
	UserRegisterPasswordRequest request;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_register_password);

		initHead();
		initView();
		user = (User) getIntent().getSerializableExtra(
				UserRegisterVerifyActivity.USER_INFO);

	}

	private void initView()
	{
		usernameEditText = (EditText) findViewById(R.id.user_register3_username);
		passwordEditText = (EditText) findViewById(R.id.user_register3_password);
		passwordAgainEditText = (EditText) findViewById(R.id.user_register3_password_again);
		emailEditText = (EditText) findViewById(R.id.user_register3_email);
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("注册个人信息");
		fragment.setBackListener(new BackListener(this, "注册还未完成是否确定退出？"));
		ManagerFragment.replaceFragment(this, R.id.user_register_passwor_head,
				fragment);
	}

	public void finish(View v)
	{

		if (request == null)
			request = new UserRegisterPasswordRequest();

		if (usernameEditText.getText().toString().equals(""))
		{
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
		} else if (passwordEditText.getText().toString().equals("")
				|| passwordAgainEditText.getText().toString().equals(""))
		{
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
		} else if (!passwordAgainEditText.getText().toString()
				.equals(passwordEditText.getText().toString()))
		{
			Toast.makeText(this, "两次密码输入不相同", Toast.LENGTH_SHORT).show();
		} else if (emailEditText.getText().toString().equals(""))
		{
			Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(!EmailVerify.isEmail(emailEditText.getText().toString()))
		{
			Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
		}
		else
		{
			request.setUsername(usernameEditText.getText().toString());
			request.setPassword(passwordEditText.getText().toString());
			request.setPasswordAgain(passwordAgainEditText.getText().toString());
			request.setEmail(emailEditText.getText().toString());
			request.setPhone(user.getPhone());
			
			user.setEmail(emailEditText.getText().toString());
			user.setUserpassword(passwordEditText.getText().toString());
			user.setUsername(usernameEditText.getText().toString());

			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					System.out.println(data);
					Message message= JSON.parseObject(JSON.parseObject(data)
							.getJSONObject("message").toJSONString(),
							Message.class);
					
					if(message.getStatus()==1)
					{
						User u=JSON.parseObject(JSON.parseObject(data)
								.getJSONObject("result").toJSONString(),
								User.class);
						
						return u;
					}
					
					return message;
				}
			}, request, this, this, R.string.error_user_register_user_info)
					.start();
		}

	}

}
