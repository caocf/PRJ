package com.hxkg.ereport;
 
import com.gh.modol.RecordLetIn;
import com.hxkg.ghpublic.R; 

import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;  
import android.view.View;
import android.widget.TextView; 

public class ShipReportInfoActivity extends Activity implements HttpRequest.onResult
{
	TextView ship,from,to,goodstype,goodscount,gastime,goodsunit,number,uptime,cheker,status;
	
	ProgressDialog loginDialog = null;
	
	RecordLetIn modol;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shipreportinfo); 
		modol=(RecordLetIn) this.getIntent().getSerializableExtra("RecordLetIn");
		initView();
	} 
	
	private void initView()
	{ 		
		ship=(TextView) this.findViewById(R.id.ship);
		ship.setText(modol.shipnameString); 
		from=(TextView) this.findViewById(R.id.from);
		from.setText(modol.getstart());
		to=(TextView) this.findViewById(R.id.to);
		to.setText(modol.gettarget());
		goodstype=(TextView) this.findViewById(R.id.goodstype);
		goodstype.setText(modol.getgoods());
		goodscount=(TextView) this.findViewById(R.id.goodscount);
		goodscount.setText(modol.gettons()); 
		
		gastime=(TextView) this.findViewById(R.id.gastime);
		gastime.setText(modol.getberthtime()); 
		
		goodsunit=(TextView) this.findViewById(R.id.goodsunit);
		goodsunit.setText(modol.getunit()); 
		
		number=(TextView) this.findViewById(R.id.shipnum);
		number.setText(modol.getnumber());
		
		uptime=(TextView) this.findViewById(R.id.uptime);
		uptime.setText(modol.uptime);
		
		//cheker=(TextView) this.findViewById(R.id.cheker);
		//cheker.setText(modol.cheker);
		status=(TextView) this.findViewById(R.id.status);
		status.setText(modol.statusString);
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
	 
}
