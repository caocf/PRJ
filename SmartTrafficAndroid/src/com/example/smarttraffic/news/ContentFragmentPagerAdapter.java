package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter
{
	private List<Fragment>	fragmentsList;
	private String[] title;

	public ContentFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	public ContentFragmentPagerAdapter(FragmentManager fm,
			List<Fragment> fragments)
	{
		super(fm);
		this.fragmentsList = fragments;
	}

	public ContentFragmentPagerAdapter(FragmentManager fm,Fragment[] fragments)
	{
		super(fm);
		this.fragmentsList=new ArrayList<Fragment>();
		for(int i=0;i<fragments.length;i++)
		{
			this.fragmentsList.add(fragments[i]);
		}
	}
	
	public ContentFragmentPagerAdapter(FragmentManager fm,Fragment[] fragments,String[] title)
	{
		super(fm);
		this.fragmentsList=new ArrayList<Fragment>();
		for(int i=0;i<fragments.length;i++)
		{
			this.fragmentsList.add(fragments[i]);
		}
		this.title=title;
	}
	
	public void update()
	{
		notifyDataSetChanged();
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
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		if(title==null)
			return "";
		return title[position];
	}

}
