package com.huzhouport.common;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * �����¼���������
 * @author Administrator zwc
 *
 */
public class CommonListenerWrapper
{
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
