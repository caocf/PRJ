package com.example.smarttraffic.common.debug;

import java.util.Date;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DebugActivity extends FragmentActivity implements UpdateView
{
	public static final String DEBUG_NAME="debug_name";
	int id;
	
	ArrayAdapter<String> adapter;
	Spinner spinner;
	DebugRequest debug;
	TextView titleTextView;
	TextView contentTextView;
	TextView contacTextView;
	
	@Override
	public void update(Object data)
	{
		String result=(String)data;
		
		if(result.equals("true"))
			Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_debug);
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("纠错");
		ManagerFragment.replaceFragment(this, R.id.debug_head, fragment);
		
		titleTextView=(TextView)findViewById(R.id.debug_title);
		contentTextView=(TextView)findViewById(R.id.debug_content);
		contacTextView=(TextView)findViewById(R.id.debug_contact);
		
		id=getIntent().getIntExtra(DEBUG_NAME, 0);
		initSpinner();
		debug=new DebugRequest();
	}
	
	private void initSpinner()
    {	
    	adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,DebugRequest.SUGGESTION_NAME);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    	spinner=(Spinner)findViewById(R.id.debug_type);
    	spinner.setAdapter(adapter);

    	spinner.setSelection(id);
    }
	
	public void submit(View v)
	{
		switch (v.getId())
		{
			case R.id.debug_submit:
				check();
				
				break;
		}
	}
	
	public void check()
	{		
		if(titleTextView.getText().toString().equals(""))
		{
			Toast.makeText(this, "标题不要为空", Toast.LENGTH_SHORT).show();
		}
		else if(contentTextView.getText().toString().equals(""))
		{
			Toast.makeText(this, "内容不要为空", Toast.LENGTH_SHORT).show();
		}
		else 
		{	
			debug.setTitle(titleTextView.getText().toString());
			debug.setContent(contentTextView.getText().toString());
			debug.setContact(contacTextView.getText().toString());
			debug.setType((int)spinner.getSelectedItemId());
			debug.setDate(new Date());
			
			new HttpThread(new DebugResult(), debug, this,this,R.string.error_debug_info).start();
		}
	}
}
