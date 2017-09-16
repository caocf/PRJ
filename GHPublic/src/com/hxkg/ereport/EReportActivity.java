package com.hxkg.ereport;
 
import com.ab.view.wheel.AbWheelView;
import com.ab.view.wheel.AbWheelView.AbOnWheelChangedListener;
import com.hxkg.ghpublic.R; 
import net.hxkg.network.HttpRequest;
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;  
import android.view.View; 

public class EReportActivity extends Activity implements HttpRequest.onResult,AbOnWheelChangedListener
{ 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ereport);  
	} 
	
	public void onBack(View v)
	{
		finish();
	}
	
	public void  onViewClick(View v)
	{
		Intent intent=new Intent(this,ReportActivity.class);
		int reportid=0;
	
		switch(v.getId())
		{
		case R.id.t1:
			reportid=0;
			intent.putExtra("reportid", reportid);
			startActivity(intent);
			break;
		case R.id.t2://空船
			reportid=1;
			intent.putExtra("reportid", reportid);
			startActivity(intent);
			break;
		case R.id.t3://记录列表
			Intent intent1=new Intent(this,EReportListActivity.class);
			startActivity(intent1);
			break;
		case R.id.back:finish();
			
		}
	}
	 
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		 
		
    }
	  
 

	@Override
	public void onSuccess(String result) 
	{ 
	}

	@Override
	public void onError(int httpcode) 
	{ 
		
	}
	  

	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) 
	{
		
	}
}
