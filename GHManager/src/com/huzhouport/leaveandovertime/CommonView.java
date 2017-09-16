package com.huzhouport.leaveandovertime;

import android.app.Activity;
import android.widget.TextView;

/**
 * ������ͼ����
 * @author Administrator
 *
 */
public class CommonView
{
	/**
	 * ����ָ��textview������
	 * @param a ����activity
	 * @param id textview id
	 * @param content textiview ����
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
