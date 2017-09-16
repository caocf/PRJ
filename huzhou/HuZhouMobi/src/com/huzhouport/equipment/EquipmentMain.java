package com.huzhouport.equipment;

import java.util.ArrayList;

import com.example.huzhouport.R;
import com.huzhouport.leaveandovertime.LeaveandovertimeNewListAdd;
import com.huzhouport.leaveandovertime.LeaveandovertimeNewListSearch;
import com.huzhouport.leaveandovertime.MyFragmentPagerAdapter;
import com.huzhouport.main.User;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageButton;


public class EquipmentMain extends FragmentActivity
{
	private ViewPager	mPager;
	private ArrayList<Fragment>	fragmentsList;
	
	private ImageButton	img_back,img_add,img_delete ,img_search;
	private ImageButton leave_approval,leave_apply,leave_all;
	private User user;
	
	private ImageButton img_add1,img_add2;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipment_main);
		
		img_back = (ImageButton) findViewById(R.id.equipment_main_back);
		img_search=(ImageButton) findViewById(R.id.equipment_main_search);


		
		Intent intent = getIntent();
		user=(User) intent.getSerializableExtra("User");
		System.out.println("userid=="+user.getUserId());
		InitTextView();
		InitViewPager();
	
		img_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();

			}
		});
		
		img_add=(ImageButton) findViewById(R.id.equipment_main_add);
		img_add.setOnClickListener(new imgadd());
		img_search.setOnClickListener(new imgsearch());

		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
            if(20==resultCode)
            {
         	   
         	   finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	private void InitViewPager()
	{
		mPager = (ViewPager) findViewById(R.id.equipment_main_viewpager);
		fragmentsList = new ArrayList<Fragment>();
		Fragment fFamilyFragment = EquipmentApproval
				.newInstance(user.getUserId()+"",user.getName());
		Fragment fFamilyFragment1 = EquipmentApply
				.newInstance(user.getUserId()+"",user.getName());
		Fragment fFamilyFragment2 = EquipmentAll.newInstance(user.getUserId()+"",user.getName());
				//.newInstance(user.getUserInfoBundle());
		fragmentsList.add(fFamilyFragment);
		fragmentsList.add(fFamilyFragment1);
		fragmentsList.add(fFamilyFragment2);
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOffscreenPageLimit(3);// 设置缓存页面，当前页面的相邻N各页面都会被缓存
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void InitTextView()
	{
		leave_approval=(ImageButton) findViewById(R.id.equipment_main_approval);
		leave_apply=(ImageButton) findViewById(R.id.equipment_main_apply);		
		leave_all=(ImageButton) findViewById(R.id.equipment_main_all);
		leave_approval.setOnClickListener(new MyOnClickListener(0));
		leave_apply.setOnClickListener(new MyOnClickListener(1));
		leave_all.setOnClickListener(new MyOnClickListener(2));

	}
	public class MyOnClickListener implements View.OnClickListener
	{
		private int	index	= 0;

		public MyOnClickListener(int i)
		{
			index = i;
		}

		@Override
		public void onClick(View v)
		{
			mPager.setCurrentItem(index);
		}
	};
	
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int arg0)
		{

			switch (arg0)
			{
				case 0:
					leave_approval.setImageResource(R.drawable.leave_approval_pressed);
				    leave_apply.setImageResource(R.drawable.leave_apply_normal);
				    leave_all.setImageResource(R.drawable.leave_all_normal);
					break;
				case 1:
					leave_approval.setImageResource(R.drawable.leave_approval_normal);
				    leave_apply.setImageResource(R.drawable.leave_apply_pressed);
				    leave_all.setImageResource(R.drawable.leave_all_normal);
				    break;
				case 2:
					leave_approval.setImageResource(R.drawable.leave_approval_normal);
				    leave_apply.setImageResource(R.drawable.leave_apply_normal);
				    leave_all.setImageResource(R.drawable.leave_all_pressed);
                   break;
			}

		}

		@Override
		public void onPageScrolled(int arg0,float arg1,int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}
	
	public class imgadd implements View.OnClickListener{
		public void onClick(View v){
			Intent intent=new Intent(EquipmentMain.this,EquipmentAdd.class);
			intent.putExtra("userid", user.getUserId()+""); //用户id
			intent.putExtra("username", user.getName()); //用户名字
			startActivityForResult(intent,100);
		}
	}

	public class imgsearch implements View.OnClickListener{
		public void onClick(View v){
			Intent intent=new Intent(EquipmentMain.this,EquipmentSearch.class);
			intent.putExtra("userid", user.getUserId()+""); //用户id
			intent.putExtra("username", user.getName()); //用户名字
			startActivityForResult(intent,100);
		}
	}
	
	
	

}
