package com.huzhouport.slidemenu;


import com.example.huzhouport.R;
import com.huzhouport.main.Login;
import com.huzhouport.main.User;
import com.huzhouport.pushmsg.ServerPushService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTopRightDialog extends Activity
{
	// private MyDialog dialog;
	private LinearLayout	layout;
	private User		user=new User();
	private  TextView tv_user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_top_right_dialog);
		user = (User) getIntent().getSerializableExtra("User");
		// btnUserPhoneNum.setText(oUser.getsUserPhoneNum());
        tv_user=(TextView) findViewById(R.id.main_top_right_user);
        if (user != null)
		{
        	tv_user.setText(user.getName());
		}
        
        
		// dialog=new MyDialog(this);
		layout = (LinearLayout) findViewById(R.id.llUserMenu);
		layout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		finish();
		return true;
	}

	public void login(View view)
	{
		if (user != null)
		{
			//Toast.makeText(MainTopRightDialog.this, "您已登录，无需重新登录",Toast.LENGTH_SHORT).show();
		}
		else
		{
			/*Intent intent = null;
			intent = new Intent(MainTopRightDialog.this, Login.class);
			startActivity(intent);*/
			setResult(170);
			finish();
		}
	}

	public void UpdateUser(View view)
	{
		
		
		
		
		Editor oEditor = getSharedPreferences("UserData", 0).edit();
		oEditor.clear();
		oEditor.commit();
	/*	Intent intent = new Intent(MainTopRightDialog.this, Login.class);
		startActivity(intent);*/
		setResult(170);
		finish();
	}
}
