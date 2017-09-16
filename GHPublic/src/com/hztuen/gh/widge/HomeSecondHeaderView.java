package com.hztuen.gh.widge;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.hxkg.ghpublic.R;

public class HomeSecondHeaderView extends LinearLayout
{
	private CheckedTextView textView;
	private View underLine_view;

	private String title ="";  //title
	private Boolean isClick = false; //是否选中

	final String labels[]={"港航要闻","行业动态","媒体聚焦","港航通知","行政通告","航行警告"}; 
	//决定类别
	int mode,position,type;
	
	public Boolean getIsClick() 
	{
		return isClick;
	}

	public String getTitle() 
	{
		return title;
	}
	
	public int getType() 
	{
		return type;
	}

	public HomeSecondHeaderView(Context context) 
	{
		super(context);
		if (title != null && !"".equals(title)) 
		{
			textView.setText(title);
		}
		if(isClick)
		{
			textView.setChecked(true);
			textView.setTextColor(Color.parseColor("#1766b1"));
		}else 
		{
			textView.setChecked(false);
			textView.setTextColor(Color.parseColor("#901766b1"));
		}
	}
	public HomeSecondHeaderView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);

		View view = LayoutInflater.from(context).inflate(R.layout.home_second_view, this, true);
		textView=(CheckedTextView) view.findViewById(R.id.title);
		underLine_view=(View)view.findViewById(R.id.underLine_view);
		
		
		int resouceId = attrs.getAttributeResourceValue(null, "title", 0);
		if (resouceId != 0) {
			title = context.getResources().getString(resouceId);
		} else {
			title = attrs.getAttributeValue(null, "title");
		}
		resouceId = attrs.getAttributeResourceValue(null, "isClick", 0);
		if (resouceId != 0) {
			isClick = context.getResources().getBoolean(resouceId);
		} else {
			isClick = attrs.getAttributeBooleanValue(null, "isClick", false);
		}
		if(isClick){
			textView.setChecked(true);
			textView.setTextColor(Color.parseColor("#1766b1"));
		}else {
			textView.setChecked(false);
			textView.setTextColor(Color.parseColor("#901766b1"));
		}
		
		
		if(textView.isChecked()){
			underLine_view.setVisibility(View.VISIBLE);
		}else {
			underLine_view.setVisibility(View.GONE);
		}

		if (title != null && !"".equals(title)) {
			textView.setText(title);
		}
		
	}

	public void setIsClick(Boolean isClick) 
	{
		this.isClick = isClick;
		if(isClick){
			textView.setChecked(true);
			textView.setTextColor(Color.parseColor("#1766b1"));
		}else {
			textView.setChecked(false);
			textView.setTextColor(Color.parseColor("#901766b1"));
		}
		
		if(textView.isChecked()){
			underLine_view.setVisibility(View.VISIBLE);
		}else {
			underLine_view.setVisibility(View.GONE);
		}
	}
	
	public void setTitle(String title)
	{
		this.title = title;
		if (title != null && !"".equals(title)) 
		{
			textView.setText(title);
		}
	}
	
	public void setType(int mode,int position)
	{
		this.mode=mode;
		this.position=position;
		type=mode*3+position;
		setTitle(labels[type]);
	}
}
