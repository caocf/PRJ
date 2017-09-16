package com.example.smarttraffic.view;

import android.widget.TextView;

public class AnimListener
{
	TextView t1;
	TextView t2;
	
	public AnimListener(TextView t1,TextView t2)
	{
		this.t1=t1;
		this.t2=t2;
	}
	
	public void exchange()
	{
		TextView temp=t1;
		t1=t2;
		t2=temp;
	}
}
