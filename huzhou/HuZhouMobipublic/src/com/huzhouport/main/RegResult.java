package com.huzhouport.main;

import com.example.huzhouportpublic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class RegResult extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.activity_registerresult);
		
		TextView tx=(TextView) this.findViewById(R.id.register_result);
		
		Intent intent=this.getIntent();
		tx.setText(intent.getStringExtra("result"));
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}
}
