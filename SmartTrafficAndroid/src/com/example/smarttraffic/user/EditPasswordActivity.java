package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
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
import android.widget.EditText;
import android.widget.Toast;

public class EditPasswordActivity extends FragmentActivity
{
	EditText pass1;
	EditText pass2;
	EditText pass3;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_password);

		pass1 = (EditText) findViewById(R.id.user_edit_pass1);
		pass2 = (EditText) findViewById(R.id.user_edit_pass2);
		pass3 = (EditText) findViewById(R.id.user_edit_pass3);

		initHead();
	}

	public void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("修改密码");
		ManagerFragment.replaceFragment(this, R.id.user_forget_password_head,
				fragment);
	}

	public void finish(View v)
	{
		final String p1 = pass1.getText().toString();
		final String p2 = pass2.getText().toString();
		final String p3 = pass3.getText().toString();
		if (p1.equals("") || p2.equals("") || p3.equals(""))
		{
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
		} else if (!p2.equals(p3))
		{
			Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
		} else
		{
			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{

					return JSON.parseObject(JSON.parseObject(data)
							.getJSONObject("message").toJSONString(),
							Message.class);
				}
			}, new BaseRequest()
			{
				@Override
				public PostParams CreatePostParams()
				{
					PostParams params=new PostParams();
					
					params.setUrl(HttpUrlRequestAddress.USER_EDIT_PASSWORD);
					
					Map<String, Object> p=new HashMap<String, Object>();
					
					p.put("user.phone", UserControl.getUser().getPhone());
					p.put("user.userpassword", p1);
					p.put("newPass",p2);
					
					params.setParams(p);
					
					return params;
				}
			}, new UpdateView()
			{

				@Override
				public void update(Object data)
				{
					Message message=(Message)data;
					
					if(message.getStatus()==1)
					{
						StartIntent.resultActivity(EditPasswordActivity.this, 1, "", "");
					}
					else 
					{
						Toast.makeText(EditPasswordActivity.this,message.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			}, this, R.string.error_user_forget_modify_pass).start();
			// StartIntent.startIntentAndFinish(this,UserMainActivity.class);
		}

	}
}
