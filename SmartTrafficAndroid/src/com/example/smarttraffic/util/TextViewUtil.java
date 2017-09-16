package com.example.smarttraffic.util;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * 常用textview操作工具类
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
	public static void setText(View v, int id, String content)
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

	public static Map<String, Integer> color = new HashMap<String, Integer>();

	static
	{
		color.put("畅通", 0xff1E9B01);
		color.put("基本畅通",0xff7fbe05 );
		color.put("轻度拥堵",0xffffae00);
		color.put("中度拥堵",0xffff6c00 );
		color.put("严重拥堵", 0xffff0000);
	}

	public static void setTextForIndex(View v,int id1,int id2,String data1,double data2)
	{
		try
		{
			TextView textView1=((TextView) v.findViewById(id1));
			textView1.setText(data1);
			
			TextView textView2=(TextView)v.findViewById(id2);
			textView2.setText(String.valueOf(data2));
			
			textView1.setTextColor(color.get(data1));
			textView2.setTextColor(color.get(data1));
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * @param v
	 * @param id
	 * @param content
	 */
	public static void setText(View v, int id, double content)
	{
		setText(v, id, String.valueOf(content));
	}

	/**
	 * 
	 * @param v
	 * @param id
	 * @param content
	 */
	public static void setText(View v, int id, int content)
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
