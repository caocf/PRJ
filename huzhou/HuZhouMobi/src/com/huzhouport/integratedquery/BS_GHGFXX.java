package com.huzhouport.integratedquery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.huzhouport.R;

/*@author �򵤵�
 * */
public class BS_GHGFXX extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.no_search_ltem);

		TextView tv = (TextView) findViewById(R.id.schedule_add_noresult);
		tv.setText("վ��ӿڿ�ͨ");
	}
}
