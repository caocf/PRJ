package com.hztuen.gh.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.hxkg.ghpublic.R;

/**
 * @author zzq
 * @DateTime 2016年7月21日 下午4:04:28
 * 电话报警
 */

public class CallActivity extends AbActivity{
	@AbIocView(id = R.id.relative_title_final,click="click") RelativeLayout back;
	@AbIocView(id = R.id.phone,click="click") ImageView phone;
	@AbIocView(id = R.id.phone2,click="click") ImageView phone2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.relative_title_final:
			finish();
			break;
		case R.id.phone:
			Uri uri = Uri.parse("tel:"+12395);
			Intent dialntent = new Intent(Intent.ACTION_DIAL,uri);
			startActivity(dialntent);
			break;
		case R.id.phone2:
			Uri uri2 = Uri.parse("tel:"+110);
			Intent dialntent2 = new Intent(Intent.ACTION_DIAL,uri2);
			startActivity(dialntent2);
			break;
		default:
			break;
		}
	}
}
