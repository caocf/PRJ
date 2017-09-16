package com.hztuen.gh.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.ab.activity.AbActivity;
import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.lvyou.utils.SystemStatic;

public class StartActivity extends AbActivity 
{	
	ProgressDialog loginDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	

		setContentView(R.layout.activity_start);		
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId())
		{
			case R.id.login:
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, LoginActivity.class);
				startActivity(intent);
				break;
			case R.id.regist:
				startActivity(new Intent(StartActivity.this,RegistActivity.class));
				break;
			case R.id.visitor:			
				SystemStatic.usertypeId = "0";
				SystemStatic.userName = "游客";
				Intent mIntent = new Intent();
				mIntent.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(mIntent);
				finish();
				break;
			default:
				break;
		}
		
	};
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{  
		if (keyCode == KeyEvent.KEYCODE_BACK )  
		{			  
			finish();
		}  

		return false;  

	}
}
