package com.hztuen.gh.activity;

import com.hxkg.ghpublic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 租船详情界面
 * 
 * @author hztuen7
 *
 */
public class ShipRentDetailsActivity extends Activity {

	// "id": 0,
	// "title": "货船出租",
	// "shiptype": "货船",
	// "shipname": "浙湖州货1670",
	// "tons": "10",
	// "rentprice": "1000",
	// "linkman": "焦明",
	// "tel": "159",
	// "remark": "备注",
	// "postdate": "2016-06-02 15:32:06"
	private RelativeLayout relative_title_final;
	private TextView tv_title, tv_shiptype, tv_shipname, tv_tons, tv_rentprice,
			tv_linkman, tv_tel, tv_remark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shiprent_details);
		init();
	}

	private void init() {

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		tv_title = (TextView) findViewById(R.id.text1_context);
		tv_shiptype = (TextView) findViewById(R.id.text2_context);
		tv_shipname = (TextView) findViewById(R.id.text3_context);
		tv_tons = (TextView) findViewById(R.id.text4_context);
		tv_rentprice = (TextView) findViewById(R.id.text5_context);
		tv_linkman = (TextView) findViewById(R.id.text6_context);
		tv_tel = (TextView) findViewById(R.id.text8_context);
		tv_remark = (TextView) findViewById(R.id.text9_context);

		Intent in = getIntent();
		String title = in.getStringExtra("title");
		String shiptype = in.getStringExtra("shiptype");
		String shipname = in.getStringExtra("shipname");
		String tons = in.getStringExtra("load");
		String rentprice = in.getStringExtra("rentprice");
		String linkman = in.getStringExtra("linkman");
		String tel = in.getStringExtra("tel");
		String remark = in.getStringExtra("remark");

		tv_title.setText(title);
		tv_shiptype.setText(shiptype);
		tv_shipname.setText(shipname);
		tv_tons.setText(tons);
		tv_rentprice.setText(rentprice);
		tv_linkman.setText(linkman);
		tv_tel.setText(tel);
		tv_remark.setText(remark);

	}

}
