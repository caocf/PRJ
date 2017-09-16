package com.example.smarttraffic;

import com.example.smarttraffic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 左中下拉框布局
 * 
 * @author Administrator zhou
 * 
 */
public class HeadFragment extends Fragment
{
	String headName; // 标题
	TextView titleTextView; // 标题
	ImageView backImageView; // 左侧图片
	Spinner zoonSpinner; // 下拉框
	ArrayAdapter<String> adapter; // 下拉框内容适配器
	OnClickListener clickListener; // 左侧图片点击事件
	int imageDrawableID; // 左侧图片ID

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.common_head_fragment,
				container, false);

		titleTextView = (TextView) rootView
				.findViewById(R.id.common_head_title);
		backImageView = (ImageView) rootView
				.findViewById(R.id.common_head_back);
		zoonSpinner = (Spinner) rootView.findViewById(R.id.common_head_zone);

		initSpinner();

		titleTextView.setText(headName);

		// 默认退出事件
		if (clickListener == null)
		{
			backImageView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					getActivity().finish();
				}
			});
		} else
		{
			backImageView.setOnClickListener(clickListener);
		}

		// 默认退出图片，如有加载自定义图片
		if (imageDrawableID != 0)
		{
			backImageView.setImageResource(imageDrawableID);
		}

		return rootView;
	}

	/**
	 * 初始化下拉框
	 */
	private void initSpinner()
	{
		String[] zoonCity = getResources().getStringArray(
				R.array.array_region_city);

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, zoonCity);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		zoonSpinner.setAdapter(adapter);

		zoonSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		zoonSpinner.setSelection(0);
	}

	/**
	 * 设置左侧图片
	 * 
	 * @param imageDrawableID
	 *            图片id
	 */
	public void setImageDrawableID(int imageDrawableID)
	{
		this.imageDrawableID = imageDrawableID;
	}

	/**
	 * 设置图片点击事件
	 * 
	 * @param clickListener
	 *            点击事件
	 */
	public void setClickListener(OnClickListener clickListener)
	{
		this.clickListener = clickListener;
	}

	/**
	 * 设置标题内容
	 * 
	 * @param name
	 *            标题内容
	 */
	public void setTitleName(String name)
	{
		headName = name;
	}

	/**
	 * 下拉框选择事件
	 * 
	 * @author Administrator
	 * 
	 */
	class SpinnerSelectedListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id)
		{

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{

		}

	}
}
