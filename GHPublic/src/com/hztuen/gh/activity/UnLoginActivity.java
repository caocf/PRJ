package com.hztuen.gh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.hxkg.ghpublic.R;

public class UnLoginActivity extends AbActivity{
	@AbIocView (id = R.id.relative_title_final , click="click") RelativeLayout relative_title_final;
	@AbIocView (id = R.id.btn_setting , click = "click") ImageButton btn_setting;
	@AbIocView (id = R.id.login , click = "click") Button login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlogin);
		
	}
	public void click(View v){
		Intent mIntent = new Intent();
		
		switch (v.getId()) {
		case R.id.relative_title_final:
			finish();
			break;
		case R.id.btn_setting:
			mIntent.setClass(getApplicationContext(), MineSettingActivity.class);
			mIntent.putExtra("visitormarker", "visitormarker");
			startActivity(mIntent);
			break;
		case R.id.login:
			mIntent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(mIntent);
			break;
		default:
			break;
		}
	}
}
