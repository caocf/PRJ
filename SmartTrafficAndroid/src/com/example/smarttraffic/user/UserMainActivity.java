package com.example.smarttraffic.user;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户主界面
 * @author Administrator zhou
 *
 */
public class UserMainActivity extends FragmentActivity
{
	TextView nameTextView;
	TextView phoneTextView;
	TextView emailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_main);
		
		initHead();
		
		nameTextView=(TextView)findViewById(R.id.user_main_name);
		phoneTextView=(TextView)findViewById(R.id.user_main_phone);
		emailTextView=(TextView)findViewById(R.id.user_main_email);
		
		user=(User)getIntent().getSerializableExtra(UserLoginActivity.USER_DATA);
		
		if(user==null)
			user=(User)getIntent().getSerializableExtra(UserRegisterPasswordActivity.USER_INFO);
		
		if(user!=null)
		{
			nameTextView.setText(user.getUsername());
			phoneTextView.setText(user.getPhone());
			emailTextView.setText(user.getEmail());
		}
	}
	
	User user;
	
	public void initHead()
	{
		HeadLCRFragment fragment=new HeadLCRFragment();
		fragment.setTitleName("个人资料");
		fragment.setRightName("修改密码");
		fragment.setRightListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntentForResult(UserMainActivity.this, EditPasswordActivity.class,1);
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.user_main_head, fragment);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2)
	{
		if(arg0==1 && arg1==1)
		{
			Toast.makeText(this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
			UserControl.setUser(null);
			StartIntent.startIntentAndFinish(this, UserLoginActivity.class);
		}
		else if(arg0==2&& arg1==2)
		{
			nameTextView.setText(UserControl.getUser().getUsername());
			phoneTextView.setText(UserControl.getUser().getPhone());
			emailTextView.setText(UserControl.getUser().getEmail());
		}
	}
	
	public void finish(View v)
	{
		UserControl.setUser(null);
		this.finish();
	}
	
	public void edit(View v)
	{
		StartIntent.startIntentForResult(this, UserEditActivity.class,2);
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}
}
