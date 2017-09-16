package com.hztuen.gh.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.hxkg.ghpublic.R;
import com.hxkg.zjsupervise.ship.ShipInfo;
import com.hxkg.zjsupervise.ship.ShipInfoListener;
import com.hxkg.zjsupervise.ship.XML2Json;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 搜索船舶基本信息
 */
public class SearchShipBaseInfoActivity extends Activity implements OnClickListener,ShipInfoListener{

	private RelativeLayout relative_title_final;
	private TextView tv_CBDJH,tv_CJDJH,tv_ZWCM,tv_CJG,tv_CBLX,tv_ZDW,tv_JDW;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_ship_base_info);
		init();
		
	}
	private void init()
	{
		ShipInfo shipi=new ShipInfo(new ShipInfoListener() {
			
			@Override
			public void onShipInofo(String result) {
				// TODO Auto-generated method stub
				List<Map<String,String>> list=null;
				
				try {
					list=XML2Json.AnalysisOfXML(result, "record");
					Map<String,String> map = list.get(0);
					final String CBDJH=map.get("CBDJH");
					final String CJDJH=map.get("CJDJH");
					final String ZWCM=map.get("ZWCM");
					final String CJG=map.get("CJG");
					final String ZDW=map.get("ZDW");
					final String JDW=map.get("JDW");
					final String CBLX=map.get("CBLX");
				    
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							tv_CBDJH.setText(CBDJH);
							tv_CJDJH.setText(CJDJH);
							tv_ZWCM.setText(ZWCM);
							tv_CJG.setText(CJG);
							tv_CBLX.setText(CBLX);
							tv_ZDW.setText(ZDW);
							tv_JDW.setText(JDW);
							
						}
					});
					
					
					//tv_CJDJH,tv_ZWCM,tv_CJG,tv_CBLX,tv_ZDW,tv_JDW;
					
					
					Log.i("GH_TEXT", list.toString());
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
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_JC_CBJBXX",0);
		
		tv_CBDJH=(TextView)findViewById(R.id.text1_context);
		tv_CJDJH=(TextView)findViewById(R.id.text3_context);
		tv_ZWCM=(TextView)findViewById(R.id.text4_context);
		tv_CJG=(TextView)findViewById(R.id.text6_context);
		tv_CBLX=(TextView)findViewById(R.id.text9_context);
		tv_ZDW=(TextView)findViewById(R.id.text10_context);
		tv_JDW=(TextView)findViewById(R.id.text11_context);
		
		
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		GetBaseInfoTask();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	//获取详细信息
	private void GetBaseInfoTask() {

		// TODO Auto-generated method stub

		// 访问网络

		
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Shipname=" + SystemStatic.searchShipName);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.baseinfo;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					onShipInofo(result);
					
					
					
					try {

						JSONObject res = new JSONObject(result);
						JSONObject data = res.getJSONObject("record");
//						
//						 "CBDJH": "300311000431",
//					        "CBLX": "?????",
//					        "CBLXDM": "2030100021",
//					        "CC": 42.8,
//					        "CJDJH": "2011F3100541",
//					        "CJG": "??",
//					        "CJGDM": "3332",
//					        "CKZHL": 694,
//					        "CSKZ": 0.521,
//					        "CSMZ": 2.5,
//					        "JDW": 223,
//					        "JYR": "???",
//					        "JYRDH": "13957278579",
//					        "LDJBC": 44.5,
//					        "SYR": "???",
//					        "XK": 9,
//					        "XS": 3.1,
//					        "ZC": 44.8,
//					        "ZDW": 399,
//					        "ZJZGL": 282.2,
//					        "ZWCM": "????1931"
						String CBDJH=data.getString("CBDJH");//船舶登记号
				//		String CBDJH=data.getString("CBDJH");//船舶登记号
						String CJDJH=data.getString("CJDJH");//船检登记号
						String ZWCM=data.getString("ZWCM");//中文船名
						String CJG=data.getString("CJG");//船籍港代码
						String CBLX=data.getString("CBLX");//船舶类型
						String ZDW=data.getString("ZDW");//总吨位
						String JDW=data.getString("JDW");//净吨位
//						String CBDJH=data.getString("CBDJH");//船舶登记号
//						String CBDJH=data.getString("CBDJH");//船舶登记号
						
						//tv_CBDJH,tv_CJDJH,tv_ZWCM,tv_CJG,tv_CBLX,tv_ZDW,tv_JDW;
						
						tv_CBDJH.setText(CBDJH);
						tv_CJDJH.setText(CJDJH);
						tv_ZWCM.setText(ZWCM);
						tv_CJG.setText(CJG);
						tv_CBLX.setText(CBLX);
						tv_ZDW.setText(ZDW);
						tv_JDW.setText(JDW);
						

						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "无信息",
									Toast.LENGTH_SHORT).show();
						} else {
							

						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
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
