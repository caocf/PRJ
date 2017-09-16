package com.example.smarttraffic.user;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 用户注册步骤一
 * 
 * @author Administrator zhou
 * 
 */
public class UserRegisterActivity extends FragmentActivity implements
		UpdateView
{

	public static final String USER_INFO = "user_info";

	@Override
	public void update(Object data)
	{

		Message msg = (Message) data;

		if (msg.getStatus() == 1)
		{
			Bundle bundle = new Bundle();
			User user = new User();
			user.setPhone(editText.getText().toString());

			bundle.putSerializable(USER_INFO, user);
			StartIntent.startIntentWithParam(this,
					UserRegisterVerifyActivity.class, bundle);
			this.finish();
		} else
		{
			Toast.makeText(this, msg.getMsg(), Toast.LENGTH_SHORT).show();
		}

	}

	EditText editText;
	UserRegisterRequest request;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_register);

		initHead();
		editText = (EditText) findViewById(R.id.user_register1_phones);
	}

	public void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("注册");
		fragment.setBackListener(new BackListener(this, "注册还未完成是否确定退出？"));
		ManagerFragment
				.replaceFragment(this, R.id.user_register_head, fragment);
	}

	public void next(View v)
	{
		if (request == null)
			request = new UserRegisterRequest();

		String phone = editText.getText().toString();
		if (phone.equals(""))
		{
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
		} else if (phone.length() != 11)
		{
			Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
		} else
		{
			request.setPhone(phone);
			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					return JSON.parseObject(JSON.parseObject(data)
							.getJSONObject("message").toJSONString(),
							Message.class);
				}
			}, request, this, this, R.string.error_user_register_phone).start();
		}

	}
}
