package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SetMei extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_mei);

		ImageButton back = (ImageButton) findViewById(R.id.fragment_set_mei_back);
		back.setOnClickListener(new Back());

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);

		TextView tv2 = (TextView) findViewById(R.id.fragment_set_mei_tv2);
		tv2.setText(tm.getDeviceId()); // IMEIºÅ

	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

}
