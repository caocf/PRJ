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
 * 找回密码步骤一
 * 
 * @author Administrator zhou
 * 
 */
public class ForgetFindActivity extends FragmentActivity implements UpdateView
{

	@Override
	public void update(Object data)
	{
		Message msg = (Message) data;

		if (msg.getStatus() == 0)
		{
			Bundle bundle = new Bundle();
			User user = new User();
			user.setPhone(editText.getText().toString());

			bundle.putSerializable(UserRegisterActivity.USER_INFO, user);
			StartIntent.startIntentWithParam(this,
					ForgetVerigyActivity.class, bundle);
			this.finish();
		} else
		{
			Toast.makeText(this, msg.getMsg(), Toast.LENGTH_SHORT).show();
		}
	}

	EditText editText;
	ForgetPasswordRequest request;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_find);

		editText = (EditText) findViewById(R.id.user_forget_one_phones);

		initHead();
	}

	public void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("找回密码");
		fragment.setBackListener(new BackListener(this, "找回密码还未完成是否确定退出？"));
		ManagerFragment.replaceFragment(this, R.id.user_forget_head, fragment);
	}

	public void next(View v)
	{
		if (request == null)
			request = new ForgetPasswordRequest();

		String phone = editText.getText().toString();
		if (!phone.equals(""))
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
			}, request, this, this, R.string.error_user_forget_phone).start();
		}

	}
}
