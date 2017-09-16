package com.example.smarttraffic.drivingSchool;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class DrivingStudentInfoFragment extends Fragment
{

	Button gradeButton;
	Button orderButton;
	Button examButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView=inflater.inflate(R.layout.fragment_driving_student, container,false);
		
		HeadFragment fragment=new HeadFragment();
		fragment.setTitleName("驾培服务");
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_student_fragment_head, fragment);
		
		initView(rootView);
		
		return rootView;
	}
	
	public void initView(View rootView)
	{
		orderButton=(Button)rootView.findViewById(R.id.driving_student_order);
		gradeButton=(Button)rootView.findViewById(R.id.driving_student_grade);
		examButton=(Button)rootView.findViewById(R.id.driving_student_exam);
		
		studentOnClick onClick=new studentOnClick();
		
		orderButton.setOnClickListener(onClick);
		gradeButton.setOnClickListener(onClick);
		examButton.setOnClickListener(onClick);
	}
	
	class studentOnClick implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) {
			case R.id.driving_student_order:
				StartIntent.startIntent(getActivity(), OrderSchoolActivity.class);
				break;

			case R.id.driving_student_grade:
				StartIntent.startIntent(getActivity(), DrivingStudentGradeActivity.class);
				break;
			case R.id.driving_student_exam:
				StartIntent.startIntent(getActivity(), DrivingStudentExamActivity.class);
				break;
			}
			
		}
		
	}
}
