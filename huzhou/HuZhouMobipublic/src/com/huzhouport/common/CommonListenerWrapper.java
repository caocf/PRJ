package com.huzhouport.common;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * �����¼���������
 * 
 * @author Administrator zwc
 * 
 */
public class CommonListenerWrapper
{
	/**
	 * 
	 * @param id
	 * @param a
	 * @param listener
	 */
	public static void addListener(int id, Activity a, OnClickListener listener)
	{
		try
		{
			a.findViewById(id).setOnClickListener(listener);
		} catch (Exception e)
		{
		}

	}

	/**
	 * �����˳��¼�������
	 * 
	 * @param v
	 *            ������¼�����ͼ
	 * @param activity
	 *            ��ͼ����activity
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
	 * �����˳��¼�������
	 * 
	 * @param id
	 *            ������¼�����ͼid
	 * @param activity
	 *            ��ͼ����activity
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
