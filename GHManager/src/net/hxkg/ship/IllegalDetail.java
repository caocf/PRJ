package net.hxkg.ship;

import java.util.ArrayList;
import java.util.List;

import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class IllegalDetail extends Activity
{
	BreakRulesModel model;
	
	TextView SLH,SLSJ,ZWCM,AY,FASJ,FADD,AJLB,ZYSS,WFNR,WFTK,CFTK,CFLB,SFCF,SFJA,JARQ;
	
	String statusString[]={"否","是"};
	final String items[]={"警告","罚款","暂扣证照","吊销证照","没收船舶","没收非法财物","没收违法所得",
			"责令停产停业","行政拘留","其他"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_illegaldetail);
		
		model=(BreakRulesModel) getIntent().getSerializableExtra("BreakRulesModel");
		initView();
	}
	
	private void initView()
	{
		SLH=(TextView) findViewById(R.id.SLH);
		SLH.setText(model.getSLH());
		SLSJ=(TextView) findViewById(R.id.SLSJ);
		SLSJ.setText(model.getSLSJ());
		ZWCM=(TextView) findViewById(R.id.ZWCM);
		ZWCM.setText(model.getZWCM());
		AY=(TextView) findViewById(R.id.AY);
		AY.setText(model.getAY());
		FASJ=(TextView) findViewById(R.id.FASJ);
		FASJ.setText(model.getFASJ());
		FADD=(TextView) findViewById(R.id.FADD);
		FADD.setText(model.getFADD());
		ZYSS=(TextView) findViewById(R.id.ZYSS);
		ZYSS.setText(model.getZYSS());
		WFNR=(TextView) findViewById(R.id.WFNR);
		WFNR.setText(model.getWFNR());
		WFTK=(TextView) findViewById(R.id.WFTK);
		WFTK.setText(model.getWFTK());
		CFTK=(TextView) findViewById(R.id.CFTK);
		CFTK.setText(model.getCFTK());
		SFCF=(TextView) findViewById(R.id.SFCF);
		String s1="".equals(model.getSFCF())?"0":model.getSFCF();
		SFCF.setText(statusString[Integer.valueOf(s1.trim())]);
		
		SFJA=(TextView) findViewById(R.id.SFJA);
		String s2="".equals(model.getSFJA())?"0":model.getSFJA();
		SFJA.setText(statusString[Integer.valueOf(s2.trim())]);
		JARQ=(TextView) findViewById(R.id.JARQ);
		JARQ.setText(model.getJARQ());
		AJLB=(TextView) findViewById(R.id.AJLB);
		AJLB.setText(model.getAJLB());
		CFLB=(TextView) findViewById(R.id.CFLB);
		List<String> itemsList=new ArrayList<>();
	        String lbString="";
	        String string=model.getCFLB();        
	        for(int i=0;i<string.length();i++)
	        {        	
	        	if(string.charAt(i)=='1')
	        	{
	        		itemsList.add(items[i]);
	        	}
	        		
	        }
	        for(String item:itemsList)
	        {
	        	lbString+=item;
	        	lbString+=",";
	        }
	       CFLB.setText(lbString.substring(0,lbString.length()-1));
	}
	public void onBack(View view)
	{
		this.finish();
	}
}
