package com.example.smarttraffic.common.about;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.system.CheckVersion;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.TextView;

/**
 * 关于界面
 * 各个子模块都有关于选项，都统一跳转到此界面
 * 每个子模块都有自己的ID，方便指定跳转来源
 * @author Administrator zhou
 *
 */
public class AboutActivity extends FragmentActivity
{
	//id参数名称
	public static final String ABOUT_ID="about_id";		
	
	TextView contentTextView;
	TextView noteTextView;
	TextView appversion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		
		contentTextView=(TextView)findViewById(R.id.about_content);
		noteTextView=(TextView)findViewById(R.id.about_note);
		appversion=(TextView)findViewById(R.id.app_version);
		
		int id=getIntent().getIntExtra(ABOUT_ID, 0);
		
		About about=FindAbout.findAbout(this, id);
		this.contentTextView.setText(about.getAboutContent());
		this.noteTextView.setText(about.getAboutNote());
		this.appversion.setText("版本:    "+new CheckVersion(this).getVersionName(CheckVersion.PACKAGE_NAME));
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("关于"+About.ABOUT_NAME[about.getAboutID()]);
		
		ManagerFragment.replaceFragment(this, R.id.about_head, fragment);
	}
}
