package com.huzhouport.wharfwork;

import com.example.huzhouportpublic.R;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.TextView;



public class WharfWorkAddPortMart extends Activity {
	private TextView tv1, tv2;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_add_portmart);

		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_portmart_back);
		back.setOnClickListener(new Back());

		tv1 = (TextView) findViewById(R.id.wharfwork_add_portmart_tv1);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("portmart", tv1.getText().toString());
				setResult(20, intent);
				finish();
			}
		});
		tv2 = (TextView) findViewById(R.id.wharfwork_add_portmart_tv2);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("portmart", tv2.getText().toString());
				setResult(20, intent);
				finish();
			}
		});

	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

}
