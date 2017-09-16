package com.example.smarttraffic.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * fragment管理工具
 * @author Administrator zwc
 *
 */
public class ManagerFragment 
{
	
	public static void switchContent(FragmentActivity fragmentActivity,int id,Fragment mContent,Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(id, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
	
	
	/**
	 * 添加fragment 如已添加，显示
	 * @param fragmentActivity 宿主
	 * @param id fragment布局的id
	 * @param fragment 添加的fragment
	 */
	public static void replaceFragment(FragmentActivity fragmentActivity,int id,Fragment fragment)
	{
		FragmentTransaction trasection =fragmentActivity.getSupportFragmentManager().beginTransaction();
		if(!fragment.isAdded())
		{
	        try
	        {
	        	trasection.replace(id, fragment);
	        	trasection.commit();
	        }
	        catch (Exception e) 
	        {
	        	 e.printStackTrace();
	        }
		}else
		{
	        trasection.show(fragment);			 
		}
	}
	public static void replaceFragment(FragmentActivity fragmentActivity,int id,Fragment fragment,boolean addToStack)
	{
		FragmentTransaction trasection =fragmentActivity.getSupportFragmentManager().beginTransaction();
		if(!fragment.isAdded())
		{
	        try
	        {
	        	trasection.replace(id, fragment);
	        	if(addToStack)
	        		trasection.addToBackStack(null);
	        	trasection.commit();
	        }
	        catch (Exception e) 
	        {
	        	 e.printStackTrace();
	        }
		}else
		{
	        trasection.show(fragment);			 
		}
	}
	
	
	/**
	 * 移除fragment
	 * @param fragmentActivity 宿主fragment
	 * @param fragment 移除的fragment
	 */
	public static void removeFragment(FragmentActivity fragmentActivity,Fragment fragment)
	{
		FragmentTransaction trasection =fragmentActivity.getSupportFragmentManager().beginTransaction();
		
        try
        {
        	trasection.remove(fragment);
        	trasection.commit();
        }
        catch (Exception e) 
        {
        	 e.printStackTrace();
        }

        trasection.show(fragment);			 
	}
}
