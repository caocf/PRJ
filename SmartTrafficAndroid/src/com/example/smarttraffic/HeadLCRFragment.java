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
 * 左中下拉框右布局
 * 
 * @author Administrator zhou
 * 
 */
public class HeadLCRFragment extends Fragment
{

	String headName;
	String rightName;
	TextView titleTextView;
	ImageView backTextView;
	TextView rightTextView;
	Spinner zoonSpinner;
	ArrayAdapter<String> adapter;
	OnClickListener rightListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.common_head_lcr_fragment,
				container, false);

		titleTextView = (TextView) rootView
				.findViewById(R.id.common_head_title);
		backTextView = (ImageView) rootView.findViewById(R.id.common_head_back);
		rightTextView = (TextView) rootView
				.findViewById(R.id.common_head_select);
		zoonSpinner = (Spinner) rootView.findViewById(R.id.common_head_zone);

		initSpinner();

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

		if (rightListener != null)
		{
			rightTextView.setOnClickListener(rightListener);
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
	 * 设置右侧选项监听器
	 * 
	 * @param listener
	 *            监听函数
	 */
	public void setRightListener(OnClickListener listener)
	{
		this.rightListener = listener;
	}

	/**
	 * 设置标题名称
	 * 
	 * @param name
	 *            标题名称
	 */
	public void setTitleName(String name)
	{
		headName = name;
	}

	/**
	 * 设置右侧选项名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setRightName(String name)
	{
		rightName = name;
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
