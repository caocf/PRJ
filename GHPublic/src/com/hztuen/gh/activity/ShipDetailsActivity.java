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
 * 船货圈找船详情
 * @author hztuen7
 *
 */
public class ShipDetailsActivity extends Activity{

	
	
//	 "id": 3,
//     "title": "杭州货运",
//     "shiptype": "c",
//     "shipname": "浙杭州货001",
//     "tons": "15",
//     "emptydock": "a",
//     "targetdock": "b",
//     "route": "杭州水道",
//     "price": "1000",
//     "linkman": "李思",
//     "postdate": "2016-06-02 15:29:39",
//     "tel": "168",
//     "remark": "备注"
	
	private RelativeLayout relative_title_final;
	private TextView tv_title, tv_shiptype, tv_shipname, tv_tons, tv_rentprice,
			tv_linkman, tv_tel, tv_remark,tv_emptydock,tv_targetdock,tv_route;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_details);
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
		
		tv_emptydock = (TextView) findViewById(R.id.text11_context);
		tv_targetdock = (TextView) findViewById(R.id.text12_context);
		tv_route = (TextView) findViewById(R.id.text13_context);

		Intent in = getIntent();
		String title = in.getStringExtra("title");
		String shiptype = in.getStringExtra("shiptype");
		String shipname = in.getStringExtra("shipname");
		String tons = in.getStringExtra("load");
		String rentprice = in.getStringExtra("rentprice");
		String linkman = in.getStringExtra("linkman");
		String tel = in.getStringExtra("tel");
		String remark = in.getStringExtra("remark");
		String emptydock = in.getStringExtra("emptydock");
		String targetdock = in.getStringExtra("targetdock");
		String route = in.getStringExtra("route");

		tv_title.setText(title);
		tv_shiptype.setText(shiptype);
		tv_shipname.setText(shipname);
		tv_tons.setText(tons);
		tv_rentprice.setText(rentprice);
		tv_linkman.setText(linkman);
		tv_tel.setText(tel);
		tv_remark.setText(remark);
		
		tv_emptydock.setText(emptydock);
		tv_targetdock.setText(targetdock);
		tv_route.setText(route);
		

	}

	

}
