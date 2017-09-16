package com.huzhouport.main;

import com.example.huzhouportpublic.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Warn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warn);
		Button bt_110 = (Button) findViewById(R.id.warn_110);
		Button bt_12395 = (Button) findViewById(R.id.warn_12395);
		bt_110.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");

				intent.setData(Uri.parse("tel:110"));
				startActivity(intent);

			}
		});
		bt_12395.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");

				intent.setData(Uri.parse("tel:12395"));
				startActivity(intent);

			}
		});

	}
	public void Finish(View view){
		finish();
	}

}
