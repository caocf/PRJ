package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SetIp extends Activity {

	private String ip, port;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_ip);

		ip = HttpUtil.BASE_URL_IP;
		port = HttpUtil.BASE_URL_PORT;

		ImageButton back = (ImageButton) findViewById(R.id.fragment_set_ip_back);
		back.setOnClickListener(new Back());

		TextView tv2 = (TextView) findViewById(R.id.fragment_set_ip_tv2);
		TextView tv4 = (TextView) findViewById(R.id.fragment_set_ip_tv4);
		tv2.setText(ip);
		tv4.setText(port);

		tv2.setOnClickListener(new TV2());
		tv4.setOnClickListener(new TV4());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {

			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	public class TV2 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(SetIp.this, SetIpUpdate.class);
			intent.putExtra("ip", ip);
			intent.putExtra("port", port);
			startActivityForResult(intent, 100);

		}
	}

	public class TV4 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(SetIp.this, SetIpUpdate.class);
			intent.putExtra("ip", ip);
			intent.putExtra("port", port);
			startActivityForResult(intent, 100);
		}
	}

}
