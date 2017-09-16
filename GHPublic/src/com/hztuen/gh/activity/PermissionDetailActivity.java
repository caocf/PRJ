package com.hztuen.gh.activity;

import com.hxkg.ghpublic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PermissionDetailActivity extends Activity implements OnClickListener{

	private TextView text1_context,text2_context,text3_context,text4_context,text5_context,text6_context,
	text7_context,text8_context,text9_context,text10_context,text11_context;
	private ImageView image_button_back;
	private RelativeLayout relative_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission_detail);
		
		init();
	}
	public void init()
	{
		   text1_context=(TextView)findViewById(R.id.text1_context);
		   text2_context=(TextView)findViewById(R.id.text2_context);
		   text3_context=(TextView)findViewById(R.id.text3_context);
		   text4_context=(TextView)findViewById(R.id.text4_context);
		   text5_context=(TextView)findViewById(R.id.text5_context);
		   text6_context=(TextView)findViewById(R.id.text6_context);
		   text7_context=(TextView)findViewById(R.id.text7_context);
		   text8_context=(TextView)findViewById(R.id.text8_context);
		   text9_context=(TextView)findViewById(R.id.text9_context);
		   text10_context=(TextView)findViewById(R.id.text10_context);
		   text11_context=(TextView)findViewById(R.id.text11_context);
		   image_button_back=(ImageView)findViewById(R.id.permission_list_back);
		   relative_title=(RelativeLayout)findViewById(R.id.relative_title);
		   
		   image_button_back.setOnClickListener(this);
		   relative_title.setOnClickListener(this);
		   
		   
			
		   Intent intent = getIntent();  
	        
	       String projectname = intent.getStringExtra("projectname");  
	       String location = intent.getStringExtra("location");  
	       String buildproperty = intent.getStringExtra("buildproperty");  
	       String unit = intent.getStringExtra("unit");  
	       String operator = intent.getStringExtra("operator");  
	       String tel = intent.getStringExtra("tel");  
	       String designer = intent.getStringExtra("designer");  
	       String accept = intent.getStringExtra("accept");  
	       String permmitnum = intent.getStringExtra("permmitnum"); 
	       String applytime = intent.getStringExtra("applytime");  
	       String state = intent.getStringExtra("state");
	       
	       text1_context.setText(projectname);
	       text2_context.setText(location);
	       text3_context.setText(buildproperty);
	       text4_context.setText(unit);
	       text5_context.setText(operator);
	       text6_context.setText(tel);
	       text7_context.setText(designer);
	       text8_context.setText(accept);
	       text9_context.setText(permmitnum);
	       text10_context.setText(applytime);	       
	       text11_context.setText(state);
	       
	       
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.permission_list_back:
			finish();
			break;
			
		case R.id.relative_title:
			finish();
			break;
		}
	}
	
	

}
