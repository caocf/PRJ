package com.huzhouport.equipment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder; 

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.slidemenu.UserMod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class WebActivity extends Activity
{
	private WebView tex;
	
	String usernameString;
	String result="";
	
	ProgressDialog sumDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myequipment);
		
		tex = (WebView) findViewById(R.id.comprehensivesee_see_count);
		tex.getSettings().setJavaScriptEnabled(true);
		tex.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		tex.getSettings().setLoadWithOverviewMode(true);		
		tex.getSettings().setSupportZoom(true);
		tex.getSettings().setBuiltInZoomControls(true);
		pullData(); 
	}
	
	public void onBack(View v)
	{
		this.finish();
	}
	
	public void pullData()
	{
		sumDialog=new  ProgressDialog(this);
		sumDialog.setCancelable(true);
		sumDialog.setMessage("数据加载中");
		sumDialog.show();
		
		new Thread(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				HttpUtil hu=new HttpUtil();
				//usernameString=getSharedPreferences("SHARED_SAVE_USER_FILE_NAME", 0).getString("SHARED_SAVE_USER_USERNAME", "");
				String nString=UserMod.name;
				try {
					String name = URLEncoder.encode(nString, "UTF-8");
					result=hu.queryStringForGet("http://120.55.100.184:8080/sbgl/phoneweb/main.aspx?xm="+name);
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				runOnUiThread(new Runnable() 
				{
					
					@Override
					public void run() {
						tex.loadDataWithBaseURL("", result, "text/html", "UTF-8","");
						if(sumDialog!=null)
							sumDialog.dismiss();
					}
				});
				
			}
		}).start();
		
		
		
	}
}
