package com.example.smarttraffic.common.introduce;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;

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
public class IntroduceActivity extends FragmentActivity
{
	//id参数名称
	public static final String INTRODUCE_ID="introduce_id";		
	
	TextView contentTextView;
	TextView noteTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		
		contentTextView=(TextView)findViewById(R.id.about_content);
		noteTextView=(TextView)findViewById(R.id.about_note);
		
		int id=getIntent().getIntExtra(INTRODUCE_ID, 0);
		
		Introduce about=FindIntroduce.findAbout(this, id);
		this.contentTextView.setText(about.getIntroduceContent());
		this.noteTextView.setText(about.getIntroduceNote());
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName(Introduce.INTRODUCE_NAME[about.getIntroduceID()]+"概况");
		
		ManagerFragment.replaceFragment(this, R.id.about_head, fragment);
		
	}
}
