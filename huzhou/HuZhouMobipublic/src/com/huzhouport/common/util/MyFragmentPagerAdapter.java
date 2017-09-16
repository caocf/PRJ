package com.huzhouport.common.util;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * viewpager内容适配器
 * 用于viewpager的切换
 * @author Administrator
 *
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
	private ArrayList<Fragment>	fragmentsList;

	public MyFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	public MyFragmentPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments)
	{
		super(fm);
		this.fragmentsList = fragments;
	}

	@Override
	public int getCount()
	{
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0)
	{
		return fragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object)
	{
		return super.getItemPosition(object);
	}

}
