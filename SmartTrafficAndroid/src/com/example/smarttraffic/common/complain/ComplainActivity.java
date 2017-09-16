package com.example.smarttraffic.common.complain;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ComplainActivity extends FragmentActivity implements UpdateView
{
	public static final String COMPLAIN_NAME="complain_name";
	int id;
	
	ArrayAdapter<String> adapter;
	Spinner spinner;
	ComplainRequest complain;
	TextView titleTextView;
	TextView contentTextView;
	TextView contacTextView;
	
	TextView contactPersionTextView;
	TextView leaveContactTextView;
	
	@Override
	public void update(Object data)
	{
		String result=(String)data;
		
		if(result.equals("true"))
		{
			Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
			
			BaseHistoryDBoperation db=new BaseHistoryDBoperation(ComplainActivity.this);
			
			Complain s=new Complain();
			s.setContact(complain.getContact());
			s.setContent(complain.getContent());
			s.setDate(new Date());
			s.setPersion(complain.getPersion());
			s.setTitle(complain.getTitle());
			db.insert(ContentType.COMPLAIN_HISTORY, JSON.toJSONString(s));
			
			db.CloseDB();
			
			StartIntent.startIntent(this, ComplainHistoryActivity.class);
			this.finish();
			
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_suggestion);
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("我要投诉");
		fragment.setRightListen(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(ComplainActivity.this, ComplainHistoryActivity.class);
			}
		});
		fragment.setRightName("历史记录");
		((TextView)findViewById(R.id.suggestion_type_name)).setText("投诉类别");
		ManagerFragment.replaceFragment(this, R.id.suggestion_head, fragment);
		
		titleTextView=(TextView)findViewById(R.id.suggestion_title);
		contentTextView=(TextView)findViewById(R.id.suggestion_content);
		contacTextView=(TextView)findViewById(R.id.suggestion_contact);
		contactPersionTextView=(TextView)findViewById(R.id.suggestion_contact_person);
		
		leaveContactTextView=(TextView)findViewById(R.id.suggestion_leave_contact);
		
		id=getIntent().getIntExtra(COMPLAIN_NAME, 0);
		initSpinner();
		complain=new ComplainRequest();
	}
	
	private void initSpinner()
    {	
    	adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ComplainRequest.COMPLAIN_NAME);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    	spinner=(Spinner)findViewById(R.id.suggestion_type);
    	spinner.setAdapter(adapter);

    	spinner.setSelection(id);
    }
	
	public void submit(View v)
	{
		switch (v.getId())
		{
			case R.id.suggestion_submit:
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
			complain.setTitle(titleTextView.getText().toString());
			complain.setContent(contentTextView.getText().toString());
			complain.setContact(contacTextView.getText().toString());
			complain.setPersion(contactPersionTextView.getText().toString());
			complain.setType((int)spinner.getSelectedItemId());
			complain.setDate(new Date());
			
			new HttpThread(new ComplainResult(), complain,this,this,R.string.error_complain_info).start();
		}
	}
	
	boolean leave_contact;
	public void click(View v)
	{
		switch (v.getId())
		{
			case R.id.suggestion_leave_contact:
				if(leave_contact)
				{
					ViewSetter.setDrawableImage(this, leaveContactTextView, R.drawable.item_uncheck);
					contacTextView.setVisibility(View.VISIBLE);
					contactPersionTextView.setVisibility(View.VISIBLE);
				}
				else
				{
					ViewSetter.setDrawableImage(this, leaveContactTextView, R.drawable.item_check);
					contacTextView.setVisibility(View.GONE);
					contactPersionTextView.setVisibility(View.GONE);
				}
				
				leave_contact=!leave_contact;
				
				break;
		}
	}
}
