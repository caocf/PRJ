package com.example.smarttraffic.drivingSchool;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DrivingStudentGradeActivity extends FragmentActivity 
{
	TextView phoneTextView;
	Button queryButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_driving_student_grade);
		
		initHead(savedInstanceState);
		initView();
	}
	
	public void initView()
	{
		phoneTextView=(TextView)findViewById(R.id.driving_student_grade_identify);
		queryButton=(Button)findViewById(R.id.driving_student_grade_query);
		
	}
	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadNameFragment headNameFragment=new HeadNameFragment();
    		
    		String nameString;
    		nameString="成绩查询";
    		headNameFragment.setTitleName(nameString);
    		
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.driving_student_grade_head, headNameFragment)
                    .commit();
        }
	}
	
	public void search(View v)
	{
		Toast.makeText(this,"抱歉，成绩查询服务尚未启动", Toast.LENGTH_SHORT).show();
	}
}
