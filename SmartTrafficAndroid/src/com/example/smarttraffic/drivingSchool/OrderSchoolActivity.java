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

public class OrderSchoolActivity extends FragmentActivity {

	TextView nameTextView;
	TextView birthTextView;
	TextView addressTextView;
	TextView teleTextView;
	TextView schoolTextView;
	TextView identifyTextView;
	
	Button enterButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_school);
		
		initView();
		
		initHead(savedInstanceState);
	}
	
	private void initView()
	{
		nameTextView=(TextView)findViewById(R.id.driving_student_name);
		birthTextView=(TextView)findViewById(R.id.driving_student_birth);
		addressTextView=(TextView)findViewById(R.id.driving_student_address);
		teleTextView=(TextView)findViewById(R.id.driving_student_tele);
		schoolTextView=(TextView)findViewById(R.id.driving_student_school);
		identifyTextView=(TextView)findViewById(R.id.driving_student_identyfy);
		
		enterButton=(Button)findViewById(R.id.driving_student_enter);
	}
	
	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadNameFragment headNameFragment=new HeadNameFragment();
    		
    		String nameString;
    		nameString="预约报名";
    		headNameFragment.setTitleName(nameString);
    		
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.driving_student_head, headNameFragment)
                    .commit();
        }
	}
	
	public void search(View v)
	{
		Toast.makeText(this,"抱歉，报名服务正在开发中", Toast.LENGTH_SHORT).show();
	}
	
//	private SchoolOrderInfo getOrderInfo()
//	{
//		SchoolOrderInfo result=new SchoolOrderInfo();
//		
//		return result;
//	}
}
