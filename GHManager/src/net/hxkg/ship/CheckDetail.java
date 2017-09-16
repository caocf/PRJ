package net.hxkg.ship;
import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CheckDetail extends Activity
{
	CheckInfo model;
	
	TextView SLH,SLSJ,ZWCM,AY,FASJ,FADD,AJLB,ZYSS,WFNR,WFTK,CFTK,CFLB,SFCF,SFJA,JARQ,CFYJ;
	
	String statusString[]={"否","是"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkdetail);
		
		model=(CheckInfo) getIntent().getSerializableExtra("CheckInfo");
		initView();
	}
	
	private void initView()
	{
		SLH=(TextView) findViewById(R.id.SLH);
		SLH.setText(model.getJYBH());
		SLSJ=(TextView) findViewById(R.id.SLSJ);
		SLSJ.setText(model.getCJDJH());
		ZWCM=(TextView) findViewById(R.id.ZWCM);
		ZWCM.setText(model.getZWCM());
		AY=(TextView) findViewById(R.id.AY);
		AY.setText(model.getJYDD());
		FASJ=(TextView) findViewById(R.id.FASJ);
		FASJ.setText(model.getJYDWMC());
		FADD=(TextView) findViewById(R.id.FADD);
		FADD.setText(model.getJYBM());
		AJLB=(TextView) findViewById(R.id.AJLB);
		AJLB.setText(model.getSQR());
		ZYSS=(TextView) findViewById(R.id.ZYSS);
		ZYSS.setText(model.getJYZL());
		WFNR=(TextView) findViewById(R.id.WFNR);
		String s1=model.getJYKSRQ();
		WFNR.setText(s1);
		WFTK=(TextView) findViewById(R.id.WFTK);
		String string=model.getJYWCRQ();
		WFTK.setText(string);
		CFTK=(TextView) findViewById(R.id.CFTK);
		CFTK.setText(model.getQTJY());
		CFYJ=(TextView) findViewById(R.id.CFYJ);
		CFYJ.setText(model.getSFWC());
		CFLB=(TextView) findViewById(R.id.CFLB);
		CFLB.setText(model.getXCJYRQ());
		SFCF=(TextView) findViewById(R.id.FASJ);
		
		SFCF.setText(model.getBZ());
		/*
		
		FADD=(TextView) findViewById(R.id.FADD);
		
		FADD.setText(string);
		if(Integer.valueOf(string.trim())<Integer.valueOf(s1))
			FADD.setTextColor(Color.parseColor("#ff2323"));*/		
		
	}
	public void onBack(View view)
	{
		this.finish();
	}
}
