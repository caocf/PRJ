package com.huzhouport.wharfwork;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huzhouportpublic.R;

public class WharfWorkAddUniti extends Activity {

	private ImageButton eltau_back;
	//private List<Map<String, Object>> listv;
	private TextView uniti1, uniti2,uniti3,uniti4,uniti5,uniti6,uniti7,uniti8;
	
	String unit,unit2,unit3,unit4,unit5,unit6,unit7,unit8;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.electricreport_adduniti);
	
	
	
	eltau_back = (ImageButton) findViewById(R.id.electricreport_adduniti_back);
	//elt_unitilist=(ListView) findViewById(R.id.electricreport_adduniti_main);
	uniti1=(TextView) findViewById(R.id.electricreport_main_uniti1);
	uniti2=(TextView) findViewById(R.id.electricreport_main_uniti2);
	uniti3=(TextView) findViewById(R.id.electricreport_main_uniti3);
	uniti4=(TextView) findViewById(R.id.electricreport_main_uniti4);
	uniti5=(TextView) findViewById(R.id.electricreport_main_uniti5);
	uniti6=(TextView) findViewById(R.id.electricreport_main_uniti6);
	uniti7=(TextView) findViewById(R.id.electricreport_main_uniti7);
	uniti8=(TextView) findViewById(R.id.electricreport_main_uniti8);

	//userName = intent.getStringExtra("userName");
	
	
	uniti1.setOnClickListener(new ElectricReportUnitiListener1());
	uniti2.setOnClickListener(new ElectricReportUnitiListener2());
	uniti3.setOnClickListener(new ElectricReportUnitiListener3());
	uniti4.setOnClickListener(new ElectricReportUnitiListener4());
	uniti5.setOnClickListener(new ElectricReportUnitiListener5());
	uniti6.setOnClickListener(new ElectricReportUnitiListener6());
	uniti7.setOnClickListener(new ElectricReportUnitiListener7());
	uniti8.setOnClickListener(new ElectricReportUnitiListener8());
	eltau_back.setOnClickListener(new MyBack());
	}
	
	
	class MyBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			// Intent intent=new Intent(ElectricReportList.this,OfficOA.class);
			// startActivity(intent);
			finish();
		}
	}
	class ElectricReportUnitiListener1 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit = uniti1.getText().toString();
			intent.putExtra("unit", unit);
			setResult(40,intent);
			finish();
		}
	}
	class ElectricReportUnitiListener2 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit2 = uniti2.getText().toString();
			intent.putExtra("unit", unit2);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener3 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit3 = uniti3.getText().toString();

			intent.putExtra("unit", unit3);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener4 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit4 = uniti4.getText().toString();

			intent.putExtra("unit", unit4);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener5 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit5 = uniti5.getText().toString();
		
			intent.putExtra("unit", unit5);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener6 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit6 = uniti6.getText().toString();

			intent.putExtra("unit", unit6);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener7 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit7 = uniti7.getText().toString();
	
			intent.putExtra("unit", unit7);
			setResult(40,intent );
			finish();
		}
	}
	class ElectricReportUnitiListener8 implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			unit8 = uniti8.getText().toString();
			intent.putExtra("unit", unit8);
			setResult(40,intent );
			finish();
		}
	}
}


