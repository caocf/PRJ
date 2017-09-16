package com.huzhouport.common;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 常用事件监听方法
 * @author Administrator zwc
 *
 */
public class CommonListenerWrapper
{
	/**
	 * 常用退出事件监听器
	 * 
	 * @param v
	 *            欲添加事件的视图
	 * @param activity
	 *            视图所在activity
	 */
	public static void commonBackWrapperListener(View v, final Activity activity)
	{
		try
		{
			v.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					activity.finish();
				}
			});
		} catch (Exception e)
		{
		}
	}

	/**
	 * 常用退出事件监听器
	 * 
	 * @param id
	 *            欲添加事件的视图id
	 * @param activity
	 *            视图所在activity
	 */
	public static void commonBackWrapperListener(int id, final Activity activity)
	{
		try
		{
			View v = activity.findViewById(id);

			commonBackWrapperListener(v, activity);
		} catch (Exception e)
		{
		}
	}
}
