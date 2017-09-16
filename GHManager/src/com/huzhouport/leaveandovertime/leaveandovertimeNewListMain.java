package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import net.hxkg.ghmanager.R;
import net.hxkg.user.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class leaveandovertimeNewListMain extends FragmentActivity
{
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private ImageButton img_add, img_delete, img_search;
	private ImageButton leave_approval, leave_apply, leave_all;
	private ImageButton img_add1, img_add2, img_add3;
	private String searchid = "0";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist);
		

		InitTextView();
		InitViewPager();

		img_add = (ImageButton) findViewById(R.id.leaveandovertime_newlist_add);
		img_delete = (ImageButton) findViewById(R.id.leaveandovertime_newlist_delete);
		img_search = (ImageButton) findViewById(R.id.leaveandovertime_newlist_search);
		img_add.setOnClickListener(new imgadd());
		img_delete.setOnClickListener(new imgdelete());
		img_search.setOnClickListener(new imgsearch());

		img_add1 = (ImageButton) findViewById(R.id.leaveandovertime_newlist_addleave);
		img_add2 = (ImageButton) findViewById(R.id.leaveandovertime_newlist_addovertime);
		img_add3 = (ImageButton) findViewById(R.id.leaveandovertime_newlist_addtravel);
		img_add1.setOnClickListener(new imgadd1());
		img_add2.setOnClickListener(new imgadd2());
		img_add3.setOnClickListener(new imgadd3());
	}
	
	public void onBack(View view)
	{
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (20 == resultCode)
		{
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	private void InitViewPager()
	{
		mPager = (ViewPager) findViewById(R.id.leaveandovertime_newlist_viewpager);
		fragmentsList = new ArrayList<Fragment>();
		Fragment fFamilyFragment = LeaveandovertimeNewListApproval.newInstance(
				User.uidString + "", User.name);
		Fragment fFamilyFragment1 = LeaveandovertimeNewListApply1.newInstance(
				User.uidString  + "", User.name);
		//Fragment fFamilyFragment2 = LeaveandovertimeNewListAll1.newInstance(
				//User.id  + "", User.name);

		fragmentsList.add(fFamilyFragment);
		fragmentsList.add(fFamilyFragment1);
		//fragmentsList.add(fFamilyFragment2);
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOffscreenPageLimit(3);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		//Log.e("fasdfdasf", "fdsfasdgaetgdfsbhshyrqeyh");
	}

	
	private void InitTextView()
	{
		leave_approval = (ImageButton) findViewById(R.id.leaveandovertime_newlist_approval);
		leave_apply = (ImageButton) findViewById(R.id.leaveandovertime_newlist_apply);
		leave_all = (ImageButton) findViewById(R.id.leaveandovertime_newlist_all);
		leave_approval.setOnClickListener(new MyOnClickListener(0));
		leave_apply.setOnClickListener(new MyOnClickListener(1));
		leave_all.setOnClickListener(new MyOnClickListener(2));
	}

	
	class MyOnClickListener implements View.OnClickListener
	{
		private int index = 0;

		public MyOnClickListener(int i)
		{
			index = i;
		}

		@Override
		public void onClick(View v)
		{
			searchid = index + "";
			mPager.setCurrentItem(index);
		}
	};

	
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int arg0)
		{

			searchid = arg0 + "";
			switch (arg0)
			{
			case 0:
				leave_approval
						.setImageResource(R.drawable.leave_approval_pressed);
				leave_apply.setImageResource(R.drawable.leave_apply_normal);
				leave_all.setImageResource(R.drawable.leave_all_normal);
				break;
			case 1:
				leave_approval
						.setImageResource(R.drawable.leave_approval_normal);
				leave_apply.setImageResource(R.drawable.leave_apply_pressed);
				leave_all.setImageResource(R.drawable.leave_all_normal);
				break;
			case 2:
				leave_approval
						.setImageResource(R.drawable.leave_approval_normal);
				leave_apply.setImageResource(R.drawable.leave_apply_normal);
				leave_all.setImageResource(R.drawable.leave_all_pressed);
				break;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	public class imgadd implements View.OnClickListener
	{
		public void onClick(View v)
		{
			img_add.setVisibility(View.INVISIBLE);
			img_delete.setVisibility(View.VISIBLE);
			findViewById(R.id.leaveandovertime_newlist_addL).setVisibility(
					View.VISIBLE);
		}
	}

	
	public class imgdelete implements View.OnClickListener
	{
		public void onClick(View v)
		{
			img_add.setVisibility(View.VISIBLE);
			img_delete.setVisibility(View.GONE);
			findViewById(R.id.leaveandovertime_newlist_addL).setVisibility(
					View.GONE);
		}
	}

	
	public class imgsearch implements View.OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(leaveandovertimeNewListMain.this,
					LeaveandovertimeNewListSearch.class);

			intent.putExtra("userid", User.id  + ""); 
			intent.putExtra("username", User.name + ""); 
			intent.putExtra("searchid", searchid);
			startActivityForResult(intent, 100);
		}
	}

	
	public class imgadd1 implements View.OnClickListener
	{
		public void onClick(View v)
		{
			img_add.setVisibility(View.VISIBLE);
			img_delete.setVisibility(View.GONE);
			findViewById(R.id.leaveandovertime_newlist_addL).setVisibility(
					View.GONE);

			Intent intent = new Intent(leaveandovertimeNewListMain.this,
					LeaveandovertimeNewListAdd.class);
			System.out.println("userid==" + User.id);
			intent.putExtra("userid", User.id + ""); 
			intent.putExtra("username", User.name); 
			intent.putExtra("kind", "1"); 
			startActivityForResult(intent, 100);

		}
	}

	
	public class imgadd2 implements View.OnClickListener
	{
		public void onClick(View v)
		{
			img_add.setVisibility(View.VISIBLE);
			img_delete.setVisibility(View.GONE);
			findViewById(R.id.leaveandovertime_newlist_addL).setVisibility(
					View.GONE);

			Intent intent = new Intent(leaveandovertimeNewListMain.this,
					LeaveandovertimeNewListAdd.class);
			System.out.println("userid==" + User.id);
			intent.putExtra("userid", User.id + ""); 
			intent.putExtra("username",User.name); 
			intent.putExtra("kind", "2"); 
			startActivityForResult(intent, 100);

		}
	}

	public class imgadd3 implements View.OnClickListener
	{
		public void onClick(View v)
		{
			img_add.setVisibility(View.VISIBLE);
			img_delete.setVisibility(View.GONE);
			findViewById(R.id.leaveandovertime_newlist_addL).setVisibility(
					View.GONE);

			Intent intent = new Intent(leaveandovertimeNewListMain.this,
					LeaveandovertimeNewListAdd.class);
			System.out.println("userid==" + User.id);
			intent.putExtra("userid",  User.id + "");
			intent.putExtra("username",  User.name);
			intent.putExtra("kind", "3");  
			startActivityForResult(intent, 100);

		}
	}

}
