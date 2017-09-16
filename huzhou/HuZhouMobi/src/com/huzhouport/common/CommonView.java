package com.huzhouport.common;

import android.app.Activity;
import android.widget.TextView;

/**
 * 常用视图操作
 * @author Administrator
 *
 */
public class CommonView
{
	/**
	 * 设置指定textview的内容
	 * @param a 所在activity
	 * @param id textview id
	 * @param content textiview 内容
	 */
	public static void setTextviewValue(Activity a,int id,String content)
	{
		TextView textView =(TextView)a.findViewById(id);
		textView.setText(content);
	}
	
	/**
	 * 
	 * @param a
	 * @param id
	 * @param content
	 * @param colorID
	 */
	public static void setTextviewValue(Activity a,int id,String content,int colorID)
	{
		TextView textView =(TextView)a.findViewById(id);
		textView.setText(content);
		textView.setTextColor(a.getResources().getColor(
				colorID));
	}
}
