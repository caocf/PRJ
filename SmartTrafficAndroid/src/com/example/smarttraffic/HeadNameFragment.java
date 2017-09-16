package com.example.smarttraffic;

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
 * 左中布局
 * 
 * @author Administrator zhou
 * 
 */
public class HeadNameFragment extends Fragment
{

	String headName;
	TextView titleTextView;
	ImageView backTextView;
	OnClickListener backListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.common_head_name_fragment,
				container, false);

		titleTextView = (TextView) rootView
				.findViewById(R.id.common_head_title);
		backTextView = (ImageView) rootView.findViewById(R.id.common_head_back);

		titleTextView.setText(headName);

		if (backListener == null)
		{
			backTextView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

					getActivity().finish();
				}
			});
		} else
		{
			backTextView.setOnClickListener(backListener);
		}
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

	public void setBackListener(OnClickListener backListener)
	{
		this.backListener = backListener;
	}
}
