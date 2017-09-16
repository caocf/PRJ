package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gh.modol.CredCardModel;
import com.gh.modol.PayInfoModel;
import com.hxkg.ghpublic.R;
import com.hxkg.zjsupervise.ship.ShipInfo;
import com.hxkg.zjsupervise.ship.ShipInfoListener;
import com.hxkg.zjsupervise.ship.XML2Json;
import com.hztuen.gh.activity.Adapter.PayInfoAdapter;
import com.hztuen.lvyou.utils.SystemStatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PayInfoActivity extends Activity implements ShipInfoListener {

	private RelativeLayout relative_title_final;
	private List<PayInfoModel> modellist = new ArrayList<PayInfoModel>();
	private PayInfoAdapter payAdapter;
	private ListView lv_pay;
	private Intent intent_before;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_info);
		init();
	}

	private void init() {
		lv_pay = (ListView) findViewById(R.id.list_pay_info);
		payAdapter = new PayInfoAdapter(getApplicationContext(), modellist);
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		intent_before=getIntent();
		
//		String jiaofei_reason=intent_before.getStringExtra("jiaofei_reason");
//		if(jiaofei_reason==null||"".equals(jiaofei_reason)){
//			
//		}else{
//			Toast.makeText(getApplicationContext(), jiaofei_reason, Toast.LENGTH_SHORT).show();
//		}
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		ShipInfo shipi = new ShipInfo(new ShipInfoListener() {

			@Override
			public void onShipInofo(String result) {
				// TODO Auto-generated method stub
				List<Map<String, String>> list = null;

				try {
					list = XML2Json
							.AnalysisOfXML(result, "recordset", "record");

					for (int i = 0; i < list.size(); i++) {
						Map<String, String> map = list.get(i);

						// YJZE 应缴金额
						// SJZE 实缴金额
						// YXQQ 有效期起
						// YXQZ 有效期止
						// JFXMMC 缴费项目名称
						PayInfoModel model = new PayInfoModel();

						model.setYJZE(map.get("YJZE"));
						model.setSJZE(map.get("SJZE"));
						model.setYXQQ(map.get("YXQQ"));
						model.setYXQZ(map.get("YXQZ"));
						model.setJFXMMC(map.get("JFXMMC"));

						modellist.add(model);

					}
					
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if(modellist.size()==0){
								Toast.makeText(getApplicationContext(), "未查到缴费信息", Toast.LENGTH_SHORT).show();
							}
							lv_pay.setAdapter(payAdapter);
						}
					});

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		});
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_JZ_CBJFXX",
				0);

	}

	@Override
	public void onShipInofo(String result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

}
