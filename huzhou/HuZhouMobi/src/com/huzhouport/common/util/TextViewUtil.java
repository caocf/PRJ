package com.huzhouport.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * 常用textview操作工具�?
 * 
 * @author Administrator zwc
 * 
 */
public class TextViewUtil
{
	/**
	 * 改变Drawable图片
	 * 
	 * @param c
	 * @param textView
	 * @param drawableID
	 */
	public static void changeDrawable(Context c, TextView textView,
			int drawableID)
	{
		try
		{
			Drawable drawable = c.getResources().getDrawable(drawableID);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());

			textView.setCompoundDrawables(drawable, null, null, null);
		} catch (Exception e)
		{

		}
	}

	/**
	 * 
	 * @param a
	 * @param id
	 * @param content
	 */
	public static void setText(Activity a, int id, String content)
	{
		if (content == null)
			return;
		try
		{
			((TextView) a.findViewById(id)).setText(content);
		} catch (Exception e)
		{

		}
	}
	
	/**
	 * 
	 * @param v
	 * @param id
	 * @param content
	 */
	public static void setText(View v,int id,String content)
	{
		if (content == null)
			return;
		try
		{
			((TextView) v.findViewById(id)).setText(content);
		} catch (Exception e)
		{

		}
	}
	
	/**
	 * 
	 * @param v
	 * @param id
	 * @param content
	 */
	public static void setText(View v,int id,double content)
	{
		setText(v, id, String.valueOf(content));
	}
	
	/**
	 * 
	 * @param v
	 * @param id
	 * @param content
	 */
	public static void setText(View v,int id,int content)
	{
		setText(v, id, String.valueOf(content));
	}
	
	/**
	 * 
	 * @param a
	 * @param id
	 * @param content
	 */
	public static void setText(Activity a, int id, double content)
	{
		setText(a, id, String.valueOf(content));
	}
	
	/**
	 * 
	 * @param a
	 * @param id
	 * @param content
	 */
	public static void setText(Activity a, int id, int content)
	{
		setText(a, id, String.valueOf(content));
	}
}
