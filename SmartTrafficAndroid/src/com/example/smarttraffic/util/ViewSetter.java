package com.example.smarttraffic.util;

import com.example.smarttraffic.view.InputListView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 视图控件操作
 * @author Administrator zhou
 *
 */
public class ViewSetter 
{
	/**
	 * 交换两个edittext控件内容
	 * @param editText1
	 * @param editText2
	 */
	public static void exchangeEditValue(EditText editText1,EditText editText2)
	{
		Editable temp=editText1.getText();
		editText1.setText(editText2.getText());
		editText2.setText(temp);
	}
	
	/**
	 * 交换两个textview控件的内容
	 * @param editText1
	 * @param editText2
	 */
	public static void exchangeTextValue(TextView editText1,TextView editText2)
	{
		String temp=editText1.getText().toString();
		editText1.setText(editText2.getText());
		editText2.setText(temp);
	}
	
	/**
	 * 设置textView控件内容
	 * @param textView
	 * @param content
	 */
	public static void setViewValue(TextView textView,String content)
	{
		textView.setText(content);
	}
	
	/**
	 * 交换自定义控件inputlistvalue控件内容
	 * @param inputListView1
	 * @param inputListView2
	 */
	public static void exchangeInputlistValue(InputListView inputListView1,InputListView inputListView2)
	{
		String temp=inputListView1.getText();
		inputListView1.setText(inputListView2.getText());
		inputListView2.setText(temp);
	}
	
	public static void setTextColor(TextView textView,int[] limint,int[] color,int data)
	{
		for(int i=0;i<limint.length;i++)
		{
			if(data<limint[i])
			{
				textView.setTextColor(color[i]);
				return;
			}
		}
	}
	
	public static void setTextColor(TextView textView,int[] color,int num)
	{
		if(color==null || color.length<num+1)
			return;
		textView.setTextColor(color[num]);
	}
	
	public static void setTextColor(TextView textView,int data)
	{
		int[] limit=new int[]{5,20};
		int[] color=new int[]{0xffff0000,0xffffff00};
		setTextColor(textView, limit, color, data);
	}
	
	public static void setDrawableImage(Context c,TextView t,int imageID)
	{
		Drawable drawable=c.getResources().getDrawable(imageID);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		
		t.setCompoundDrawables(drawable,null,null,null);
	}
	
	public static void setDrawableImage(Context c,TextView t,int imageID,int orient,int padding)
	{
		Drawable drawable=c.getResources().getDrawable(imageID);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		
		switch (orient)
		{
			case 1:
				t.setCompoundDrawables(drawable,null,null,null);
				break;
				
			case 2:
				t.setCompoundDrawables(null,drawable,null,null);
				break;
				
			case 3:
				t.setCompoundDrawables(null,null,drawable,null);
				break;
				
			case 4:
				t.setCompoundDrawables(null,null,null,drawable);
				break;
		
		}
		
		t.setCompoundDrawablePadding(padding);
	}
	
	public static void setTextviewColor(TextView textView,String[] str,String[] color)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			
			for(int i=0;i<str.length;i++)
			{
				sb.append("<font color='"+color[i]+"'>"+str[i]+"</font>");
			}
	
	        textView.setText(Html.fromHtml(sb.toString()));
		}
		catch(Exception e)
		{
		}
	}
}
