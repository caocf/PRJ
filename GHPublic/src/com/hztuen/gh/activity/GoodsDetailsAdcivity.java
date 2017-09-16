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
 * 船货圈找船找货详情界面
 * @author hztuen7
 *
 */
public class GoodsDetailsAdcivity extends Activity {

	
//	 "id": 2,
//     "titile": "嘉兴拉货",
//     "type": "c",
//     "name": "沙子",
//     "tons": "5",
//     "packaging": "散装",
//     "startport": "a",
//     "unloadport": "b",
//     "price": "1000",
//     "linkman": "王三",
//     "tel": "139",
//     "remark": "备注",
//     "postdate": "2016-06-02 15:25:28"
	private RelativeLayout relative_title_final;
	private TextView tv_title, tv_type, tv_name, tv_tons, tv_packaging,tv_price,
			tv_linkman, tv_tel, tv_remark,tv_startport,tv_unloadport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
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
		tv_type = (TextView) findViewById(R.id.text2_context);
		tv_name = (TextView) findViewById(R.id.text3_context);
		tv_tons = (TextView) findViewById(R.id.text4_context);
		tv_price = (TextView) findViewById(R.id.text5_context);
		tv_linkman = (TextView) findViewById(R.id.text6_context);
		tv_tel = (TextView) findViewById(R.id.text8_context);
		tv_remark = (TextView) findViewById(R.id.text9_context);
		
		tv_startport = (TextView) findViewById(R.id.text11_context);
		tv_unloadport = (TextView) findViewById(R.id.text12_context);
		tv_packaging = (TextView) findViewById(R.id.text13_context);

		Intent in = getIntent();
		String title = in.getStringExtra("title");
		String shiptype = in.getStringExtra("type");
		String shipname = in.getStringExtra("name");
		String tons = in.getStringExtra("tons");
		String price = in.getStringExtra("price");
		String linkman = in.getStringExtra("linkman");
		String tel = in.getStringExtra("tel");
		String remark = in.getStringExtra("remark");
		String startport = in.getStringExtra("startport");
		String unloadport = in.getStringExtra("unloadport");
	

		tv_title.setText(title);
		tv_type.setText(shiptype);
		tv_name.setText(shipname);
		tv_tons.setText(tons);
		tv_price.setText(price);
		tv_linkman.setText(linkman);
		tv_tel.setText(tel);
		tv_remark.setText(remark);
		
		tv_startport.setText(startport);
		tv_unloadport.setText(unloadport);
		

	}

	
	

}
