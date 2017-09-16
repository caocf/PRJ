package com.example.smarttraffic.user;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends FragmentActivity implements
		UpdateView
{

	@Override
	public void update(Object data)
	{
		Message msg = (Message) data;

		if (msg.getStatus() == 1)
		{
			Toast.makeText(this,"密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
			this.finish();
		} else
		{
			Toast.makeText(this, msg.getMsg(), Toast.LENGTH_SHORT).show();
		}
		

	}

	EditText pass1;
	EditText pass2;

	ForgetPasswordModifyRequest request;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_password);

		pass1 = (EditText) findViewById(R.id.user_forget_pass1);
		pass2 = (EditText) findViewById(R.id.user_forget_pass2);
		user = (User) getIntent().getSerializableExtra(
				ForgetVerigyActivity.USER_INFO);

		initHead();
	}

	public void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("找回密码");
		ManagerFragment.replaceFragment(this, R.id.user_forget_password_head,
				fragment);
	}

	public void finish(View v)
	{
		String p1 = pass1.getText().toString();
		String p2 = pass2.getText().toString();
		if (p1.equals("") || p2.equals(""))
		{
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
		} else if (!p1.equals(p2))
		{
			Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
		} else
		{
			if (request == null)
				request = new ForgetPasswordModifyRequest();
			request.setPhone(user.getPhone());
			request.setNewPassword(p1);

			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					System.out.println(data);
					
					return JSON.parseObject(JSON.parseObject(data)
							.getJSONObject("message").toJSONString(),
							Message.class);
				}
			}, request, this, this, R.string.error_user_forget_modify_pass)
					.start();
			// StartIntent.startIntentAndFinish(this,UserMainActivity.class);
		}

	}
}
