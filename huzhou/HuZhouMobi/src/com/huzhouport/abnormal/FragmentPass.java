package com.huzhouport.abnormal;

import com.example.huzhouport.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class FragmentPass extends Fragment implements RadioGroup.OnCheckedChangeListener
{	
	AbnormalInfo abnormalInfo;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.ab_option_prass, container,false);	
		
		RadioGroup options=(RadioGroup) rootView.findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		
		
		return rootView;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		int num=-1;
		switch (checkedId)
		{
		case R.id.btn_0:num=0;break;	
			
		case R.id.btn_1:num=1;break;	
		case R.id.btn_2:num=2;break;	
		case R.id.btn_3:num=3;break;

		}
		
		((AbNormalProcess)this.getActivity()).GetOptions(1, num);		
	}	
	
}
