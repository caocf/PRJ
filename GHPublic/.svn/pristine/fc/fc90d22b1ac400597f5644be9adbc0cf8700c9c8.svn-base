package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.CredCardModel;
import com.gh.modol.DeductPointsModel;
import com.gh.modol.ShipCircleListModel;
import com.hxkg.ghpublic.R;
import com.hxkg.zjsupervise.ship.ShipInfo;
import com.hxkg.zjsupervise.ship.ShipInfoListener;
import com.hxkg.zjsupervise.ship.XML2Json;
import com.hztuen.gh.activity.Adapter.CredCardAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CredCardInfoActivity extends Activity implements OnClickListener,
		ShipInfoListener {

	private RelativeLayout relative_title_final;
	private ListView list_cred_info;
	private List<CredCardModel> modellist = new ArrayList<CredCardModel>();
	private CredCardAdapter credAdapter,credAdapterDuck;
	private Intent intent_type,intent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cred_info);
		init();
	}

	private void init() {
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);

		list_cred_info = (ListView) findViewById(R.id.list_cred_info);

		credAdapter = new CredCardAdapter(getApplicationContext(), modellist,1);
		credAdapterDuck = new CredCardAdapter(getApplicationContext(), modellist,2);

		intent_type=getIntent();
		String type=intent_type.getStringExtra("type");
		
		if("船舶证书".equals(type))
		{
		modellist.clear();
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

						CredCardModel model = new CredCardModel();

						model.setFZRQ(map.get("FZRQ"));
						model.setYXRQ(map.get("YXRQ"));
						model.setZSMC(map.get("ZSMC"));

						modellist.add(model);

					}

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							list_cred_info.setAdapter(credAdapter);
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
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_ZH_CBCZXX",0);
		}else if("码头证书".equals(type)){
			GetDuckCardTask();
		}else if("公司证书".equals(type))
		{
			intent=getIntent();
			String name = intent.getStringExtra("companyName");
			GetCompCardTask(name);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_title_final:
			finish();
			break;

		default:
			break;
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
	
	
	
	// 按码头名称获取码头证书列表
		private void GetDuckCardTask() {

			// TODO Auto-generated method stub

			// 访问网络

			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Wharf=" + SystemStatic.Wharfname);
			

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.licencelist;
			if (!toUrl.equals("")) {
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
						
						modellist.clear();
						KJLoger.debug("===" + headers.get("Set-Cookie"));
						// Log.i(TAG+":kymjs---http", new String(t));
						String result = new String(t).trim();
						try {

							JSONObject resultJO = new JSONObject(result);
							// String resultMsg = resultJO.getString("resultMsg");
							// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
							JSONObject res = new JSONObject(result);
							JSONArray data = res.getJSONArray("data");
							Log.i("GH_TEXT", data.length() + "我是数据的size");
							if (data.length() == 0) {
								Toast.makeText(getApplicationContext(), "无证书信息",
										Toast.LENGTH_SHORT).show();
							} else {
								for (int i = 0; i < data.length(); i++) {
									JSONObject temp = data.getJSONObject(i);
									CredCardModel model = new CredCardModel();
									model.settitle(temp.getString("title"));
									model.setpost(temp.getString("post"));
									model.setvalid(temp.getString("valid"));

//									  "title": "危货作业证书",
//							            "post": "2015-9-6",
//							            "valid": "2015-11-25"
									modellist.add(model);

								}

								list_cred_info.setAdapter(credAdapterDuck);
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
		
		
		
		// 按公司名称获取公司证书列表
				private void GetCompCardTask(String name) {

					// TODO Auto-generated method stub

					// 访问网络

					KJHttp kjh = new KJHttp();
					List<String> aa = new ArrayList<String>();
					aa.add("Company=" + name);
					

					HttpParams params = null;
					try {
						params = Util.prepareKJparams(aa);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 访问地址
					String toUrl = contants.companylicence;
					if (!toUrl.equals("")) {
						kjh.post(toUrl, params, false, new HttpCallBack() {
							@Override
							public void onSuccess(Map<String, String> headers, byte[] t) {
								super.onSuccess(headers, t);
								// 获取cookie
								
								modellist.clear();
								KJLoger.debug("===" + headers.get("Set-Cookie"));
								// Log.i(TAG+":kymjs---http", new String(t));
								String result = new String(t).trim();
								try {

									JSONObject resultJO = new JSONObject(result);
									// String resultMsg = resultJO.getString("resultMsg");
									// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
									JSONObject res = new JSONObject(result);
									JSONArray obj = res.getJSONArray("obj");
									Log.i("GH_TEXT", obj.length() + "我是数据的size");
									if (obj.length() == 0) {
										Toast.makeText(getApplicationContext(), "无证书信息",
												Toast.LENGTH_SHORT).show();
									} else {
										for (int i = 0; i < obj.length(); i++) {
											JSONObject temp = obj.getJSONObject(i);
											CredCardModel model = new CredCardModel();
											model.settitle(temp.getString("title"));
											model.setpost(temp.getString("post"));
											model.setvalid(temp.getString("valid"));

//											  "title": "危货作业证书",
//									            "post": "2015-9-6",
//									            "valid": "2015-11-25"
											modellist.add(model);

										}

										list_cred_info.setAdapter(credAdapterDuck);
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
				
		
		

}
