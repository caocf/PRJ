package com.example.smarttraffic;

import java.util.Date;

import com.example.smarttraffic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目中一般收藏夹的头部fragment,为左中右格局
 * 
 * @author Administrator zhou
 * 
 */
public class HeadFavorFragment extends Fragment
{

	String headName; // 布局名称
	String rightName; // 右边选项名称
	TextView titleTextView; // 标题文字
	ImageView backTextView; // 返回图片
	TextView rightTextView; // 右侧文字
	OnClickListener rightClickListener; // 右侧文字点击事件
	

	/**
	 * 默认返回图片事件，添加右侧文字点击事件
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_favor_head,
				container, false);

		titleTextView = (TextView) rootView
				.findViewById(R.id.common_head_title);
		backTextView = (ImageView) rootView.findViewById(R.id.common_head_back);
		rightTextView = (TextView) rootView
				.findViewById(R.id.common_head_right);

		titleTextView.setText(headName);
		rightTextView.setText(rightName);

		backTextView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				getActivity().finish();
			}
		});

		if (rightClickListener != null)
			rightTextView.setOnClickListener(rightClickListener);

		return rootView;
	}

	/**
	 * 设置标题名称（在fragment加载之前）
	 * 
	 * @param name
	 *            标题名称
	 */
	public void setTitleName(String name)
	{
		headName = name;
	}

	/**
	 * 设置右侧文本名称（在fragment加载之前）
	 * 
	 * @param name
	 *            右侧名称
	 */
	public void setRightName(String name)
	{
		rightName = name;
	}

	/**
	 * 设置右侧文本点击事件（在fragment加载之前）
	 * 
	 * @param listener
	 *            点击事件
	 */
	public void setRightListen(OnClickListener listener)
	{
		this.rightClickListener = listener;
	}

}
