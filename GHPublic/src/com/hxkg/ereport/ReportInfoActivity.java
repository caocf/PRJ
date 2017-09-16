package com.hxkg.ereport;
 
import com.hxkg.ghpublic.R; 

import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;  
import android.view.View;
import android.widget.TextView; 

public class ReportInfoActivity extends Activity implements HttpRequest.onResult
{
	TextView type,ship,inout,from,to,goodstype,goodscount,time,gascount,gastime,goodsunit;
	
	ProgressDialog loginDialog = null;
	
	EReportModol modol;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportinfo); 
		modol=(EReportModol) this.getIntent().getSerializableExtra("EReportModol");
		initView();
	} 
	
	private void initView()
	{
		type=(TextView) this.findViewById(R.id.reporttype);
		type.setText(modol.reportid);
		
		ship=(TextView) this.findViewById(R.id.ship);
		ship.setText(modol.shipname);
		inout=(TextView) this.findViewById(R.id.inout);
		inout.setText(modol.inout);
		from=(TextView) this.findViewById(R.id.from);
		from.setText(modol.from);
		to=(TextView) this.findViewById(R.id.to);
		to.setText(modol.to);
		goodstype=(TextView) this.findViewById(R.id.goodstype);
		goodstype.setText(modol.goods);
		goodscount=(TextView) this.findViewById(R.id.goodscount);
		goodscount.setText(modol.goodscount);
		time=(TextView) this.findViewById(R.id.time);
		time.setText(modol.time);
		gascount=(TextView) this.findViewById(R.id.gascount);
		gascount.setText(modol.gascount);
		
		gastime=(TextView) this.findViewById(R.id.gastime);
		gastime.setText(modol.gastime); 
		
		goodsunit=(TextView) this.findViewById(R.id.goodsunit);
		goodsunit.setText(modol.unit); 
	}
	 
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		finish();
	}
	  
 

	@Override
	public void onSuccess(String result) 
	{		 
		if(loginDialog!=null)
			loginDialog.dismiss();
		finish();
	}

	@Override
	public void onError(int httpcode) 
	{
		if(loginDialog!=null)
			loginDialog.dismiss();
		
	}
	
	public void onChange(View v)
	{
		Intent intent=new Intent(this,ChangeRouteActivity.class);
		intent.putExtra("EReportModol", modol);
		startActivity(intent);
		finish();
		
	}
	 
}
