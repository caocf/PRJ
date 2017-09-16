package com.huzhouport.leaveandovertime;

import java.util.ArrayList;

import com.example.huzhouport.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.main.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;

/**
 * ��ټӰ�
 * 
 * @author Administrator
 * 
 */
public class leaveandovertimeNewListMain extends FragmentActivity
{
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private ImageButton img_add, img_delete, img_search;
	private ImageButton leave_approval, leave_apply, leave_all;
	private User user;

	private ImageButton img_add1, img_add2, img_add3;
	private String searchid = "0";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist);

		// �˳��¼�
		CommonListenerWrapper.commonBackWrapperListener(
				R.id.leaveandovertime_newlist_back, this);

		user = (User) getIntent().getSerializableExtra("User");
		// viewpager���ݳ�ʼ��
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
		if(user!=null)
		new Log(user.getName(), "�鿴��ټӰ�", GlobalVar.LOGSEE, "").execute();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (20 == resultCode)
		{
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ��ʼ��viewpager
	 */
	private void InitViewPager()
	{
		mPager = (ViewPager) findViewById(R.id.leaveandovertime_newlist_viewpager);
		fragmentsList = new ArrayList<Fragment>();
		Fragment fFamilyFragment = LeaveandovertimeNewListApproval.newInstance(
				user.getUserId() + "", user.getName());
		Fragment fFamilyFragment1 = LeaveandovertimeNewListApply.newInstance(
				user.getUserId() + "", user.getName());
		Fragment fFamilyFragment2 = LeaveandovertimeNewListAll.newInstance(
				user.getUserId() + "", user.getName());

		fragmentsList.add(fFamilyFragment);
		fragmentsList.add(fFamilyFragment1);
		fragmentsList.add(fFamilyFragment2);
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOffscreenPageLimit(3);// ���û���ҳ�棬��ǰҳ�������N��ҳ�涼�ᱻ����
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ��ʼ��ѡ�����
	 */
	private void InitTextView()
	{
		leave_approval = (ImageButton) findViewById(R.id.leaveandovertime_newlist_approval);
		leave_apply = (ImageButton) findViewById(R.id.leaveandovertime_newlist_apply);
		leave_all = (ImageButton) findViewById(R.id.leaveandovertime_newlist_all);
		leave_approval.setOnClickListener(new MyOnClickListener(0));
		leave_apply.setOnClickListener(new MyOnClickListener(1));
		leave_all.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * ѡ�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
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

	/**
	 * ҳ�����ݸı������
	 * 
	 * @author Administrator
	 * 
	 */
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

	/**
	 * ��������
	 * @author Administrator
	 *
	 */
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

	/**
	 * ȡ����������
	 * @author Administrator
	 *
	 */
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

	/**
	 * ��ת������ҳ�棬������ټӰ�����
	 * @author Administrator
	 *
	 */
	public class imgsearch implements View.OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(leaveandovertimeNewListMain.this,
					LeaveandovertimeNewListSearch.class);

			intent.putExtra("userid", user.getUserId() + ""); // �û�id
			intent.putExtra("username", user.getName() + ""); // �û�name
			intent.putExtra("searchid", searchid);
			startActivityForResult(intent, 100);
		}
	}

	/**
	 * ��Ҫ���
	 * @author Administrator
	 *
	 */
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
			System.out.println("userid==" + user.getUserId());
			intent.putExtra("userid", user.getUserId() + ""); // �û�id
			intent.putExtra("username", user.getName()); // �û�����
			intent.putExtra("kind", "1"); // 1��� 2�Ӱ�
			startActivityForResult(intent, 100);

		}
	}

	/**
	 * ��Ҫ�Ӱ�
	 * @author Administrator
	 *
	 */
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
			System.out.println("userid==" + user.getUserId());
			intent.putExtra("userid", user.getUserId() + ""); // �û�id
			intent.putExtra("username", user.getName()); // �û�����
			intent.putExtra("kind", "2"); // 1��� 2�Ӱ�
			startActivityForResult(intent, 100);

		}
	}

	/**
	 * ��Ҫ����
	 * @author Administrator
	 *
	 */
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
			System.out.println("userid==" + user.getUserId());
			intent.putExtra("userid", user.getUserId() + ""); // �û�id
			intent.putExtra("username", user.getName()); // �û�����
			intent.putExtra("kind", "3"); // 1��� 2�Ӱ� 3����
			startActivityForResult(intent, 100);

		}
	}

}
