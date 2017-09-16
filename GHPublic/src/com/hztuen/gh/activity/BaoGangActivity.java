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

import com.gh.modol.DeductPointsModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BaoGangActivity extends Activity implements OnClickListener{

	private RelativeLayout relative_title_final;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baogang);
		init();
	}
	private void init()
	{
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
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
	
	// 获取报港记录列表
		private void GetBaoGangTask() {

			// TODO Auto-generated method stub

			// 访问网络

			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Shipname=" + SystemStatic.searchShipName);
			aa.add("Page=" + "0");

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.creditlist;
			if (!toUrl.equals("")) {
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
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
								Toast.makeText(getApplicationContext(), "无扣分记录",
										Toast.LENGTH_SHORT).show();
							} else {
								for (int i = 0; i < data.length(); i++) {
									JSONObject temp = data.getJSONObject(i);
									DeductPointsModel model = new DeductPointsModel();
									// {"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
									model.setpenalty(temp.getString("penalty"));
									model.setreason(temp.getString("reason"));
									model.settime(temp.getString("time"));

									//
									// "id": 14,
									// "title": "嘉兴货源",
									// "content": "备注",
									// "price": "1500元",
									// "tradetype": "货源",
									// "postime": "2016-05-19 10:19:02"
									//

									//
									//
									//
								//	modellist.add(model);

								}

								//list_deduct_points.setAdapter(deductAdapter);
							}
							// newsAdapter.pre(newslist);
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
