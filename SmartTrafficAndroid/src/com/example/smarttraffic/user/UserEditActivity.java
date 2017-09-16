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
import com.example.smarttraffic.util.EmailVerify;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Administrator zhou
 * 
 */
public class UserEditActivity extends FragmentActivity implements
		UpdateView
{
	public void update(Object data)
	{
		if(data instanceof Message)
		{
			Message message = (Message) data;
			Toast.makeText(this, message.getMsg(), Toast.LENGTH_SHORT).show();
		}

		else if (data instanceof User)
		{
			UserControl.setUser((User)data);
			Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
			StartIntent.resultActivity(this, 2, "", "");
		} 

	}

	EditText usernameEditText;
	EditText emailEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_edit);

		initHead();
		initView();

	}

	private void initView()
	{
		usernameEditText = (EditText) findViewById(R.id.user_register3_username);
		emailEditText = (EditText) findViewById(R.id.user_register3_email);
		
		usernameEditText.setText(UserControl.getUser().getUsername());
		emailEditText.setText(UserControl.getUser().getEmail());
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("修改个人信息");
		ManagerFragment.replaceFragment(this, R.id.user_register_passwor_head,
				fragment);
	}

	public void finish(View v)
	{
		if (usernameEditText.getText().toString().equals(""))
		{
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
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
			}, new BaseRequest()
			{
				@Override
				public PostParams CreatePostParams()
				{
					PostParams params=new PostParams();
					
					params.setUrl(HttpUrlRequestAddress.USER_EDIT_USER_INFO_URL);
					
					Map<String, Object> d=new HashMap<String, Object>();
					
					d.put("user.phone", UserControl.getUser().getPhone());
					d.put("user.email", emailEditText.getText().toString().trim());
					d.put("user.userid", UserControl.getUser().getUserid());
					d.put("user.username", usernameEditText.getText().toString().trim());
					d.put("user.userpassword", UserControl.getUser().getUserpassword());
					
					params.setParams(d);
					
					return params;	
				}
			}, this, this, "修改个人信息失败")
					.start();
		}

	}

}
