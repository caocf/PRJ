package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.main.UpdateAppManager;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class SetMy extends Activity {
	
	UpdateAppManager oUpdateAppManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_my);
		
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_my_back);
	    back.setOnClickListener(new Back());
	    
	    
	    oUpdateAppManager = new UpdateAppManager(this);
	    TextView tv1=(TextView) findViewById(R.id.fragment_set_my_tv1);
		  tv1.setText("°æ±¾  £º"+oUpdateAppManager.getVerName());  //°æ±¾ºÅ
	 
}
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	
	
	
	
}
