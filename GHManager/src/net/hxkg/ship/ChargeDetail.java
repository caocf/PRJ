package net.hxkg.ship;
import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ChargeDetail extends Activity
{
	ChargeModol model;
	
	TextView SLH,SLSJ,ZWCM,AY,FASJ,FADD,AJLB,ZYSS,WFNR,WFTK,CFTK,CFLB,SFCF,SFJA,JARQ,CFYJ;
	
	String statusString[]={"否","是"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargedetail);
		
		model=(ChargeModol) getIntent().getSerializableExtra("ChargeModol");
		initView();
	}
	
	private void initView()
	{
		SLH=(TextView) findViewById(R.id.SLH);
		SLH.setText(model.getXLH());
		SLSJ=(TextView) findViewById(R.id.SLSJ);
		SLSJ.setText(model.getJFXMDM());
		ZWCM=(TextView) findViewById(R.id.ZWCM);
		ZWCM.setText(model.JFXMMC);
		AY=(TextView) findViewById(R.id.AY);
		AY.setText(model.getSFFSMC());
		FASJ=(TextView) findViewById(R.id.FASJ);
		String s1=model.getYJZE();
		FASJ.setText(s1);
		FADD=(TextView) findViewById(R.id.FADD);
		String string=model.getSJZE();
		FADD.setText(string);
		if(Integer.valueOf(string.trim())<Integer.valueOf(s1))
			FADD.setTextColor(Color.parseColor("#ff2323"));
		AJLB=(TextView) findViewById(R.id.AJLB);
		AJLB.setText(model.getYXQQ());		
		ZYSS=(TextView) findViewById(R.id.ZYSS);
		ZYSS.setText(model.getYXQZ());
		WFNR=(TextView) findViewById(R.id.WFNR);
		WFNR.setText(model.getKPRQ());		
		
	}
	
	public void onBack(View view)
	{
		this.finish();
	}
}
