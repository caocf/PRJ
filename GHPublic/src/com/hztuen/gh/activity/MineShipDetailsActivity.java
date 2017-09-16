package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

public class MineShipDetailsActivity extends Activity implements OnClickListener{

	private RelativeLayout relative_title_final;
	private TextView tv_title_ship;
	private RelativeLayout relative_base_info,relative_cred_info,relative_break_rules,relative_deduct_points
	,relativie_baogang,relative_danger_record;
	
	private ImageView img_zhengshu,img_jiaofei,img_weizhang;
	private String jiaofei_reason;
	String shipid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_ship_details);
		init();
	}
	private void init()
	{
		Intent intent_state=getIntent();
		
		shipid=intent_state.getStringExtra("shipid");
		String zhengshu=intent_state.getStringExtra("zhengshu");
		String jiaofei=intent_state.getStringExtra("jiaofei");
		String weizhang=intent_state.getStringExtra("weizhang"); 		
		
		img_zhengshu=(ImageView)findViewById(R.id.img_zhengshu);
		img_jiaofei=(ImageView)findViewById(R.id.img_jiaofei);
		img_weizhang=(ImageView)findViewById(R.id.img_weizhang);
		
		if ("0".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.GONE);			
		} else if ("1".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.VISIBLE);	
		} else if ("2".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.VISIBLE);	
		}else{
			img_zhengshu.setVisibility(View.VISIBLE);	
		}
		
		if ("0".equals(jiaofei)) {
			img_jiaofei.setVisibility(View.GONE);			
		} else if ("1".equals(jiaofei)) {
			img_jiaofei.setVisibility(View.VISIBLE);	
		} else if ("2".equals(jiaofei)) {
			img_jiaofei.setVisibility(View.VISIBLE);	
		}else{
			img_jiaofei.setVisibility(View.VISIBLE);	
		}
		
		if ("0".equals(weizhang)) {
			img_weizhang.setVisibility(View.GONE);			
		} else if ("1".equals(weizhang)) {
			img_weizhang.setVisibility(View.VISIBLE);	
		} else if ("2".equals(weizhang)) {
			img_weizhang.setVisibility(View.VISIBLE);	
		}else{
			img_weizhang.setVisibility(View.VISIBLE);	
		}
		
		
		
		tv_title_ship=(TextView)findViewById(R.id.textView1);
		tv_title_ship.setText(SystemStatic.searchShipName);
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		

		
		relative_base_info=(RelativeLayout)findViewById(R.id.relative2);//基本信息		
		relative_base_info.setOnClickListener(this);
		
		relative_cred_info=(RelativeLayout)findViewById(R.id.relative3);//船舶证书		
		relative_cred_info.setOnClickListener(this);
		
		relative_break_rules=(RelativeLayout)findViewById(R.id.relative4);//违章证书		
		relative_break_rules.setOnClickListener(this);
		
		relative_deduct_points=(RelativeLayout)findViewById(R.id.relative5);//诚信扣分		
		relative_deduct_points.setOnClickListener(this);
		
		relative_deduct_points=(RelativeLayout)findViewById(R.id.relative5);//诚信扣分		
		relative_deduct_points.setOnClickListener(this);
		
		relativie_baogang=(RelativeLayout)findViewById(R.id.relative6);	//报港记录
		relativie_baogang.setOnClickListener(this);
		
		relative_danger_record=(RelativeLayout)findViewById(R.id.relative8);	//缴费信息
		relative_danger_record.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//基本信息
		case R.id.relative2:
			
			Intent intent_base_info=new Intent(getApplicationContext(),SearchShipBaseInfoActivity.class);
			startActivity(intent_base_info);
			break;
		//返回按钮
		case R.id.relative_title_final:
			finish();
			break;
		//船舶证书	
		case R.id.relative3:
			
			Intent intent_cred_info=new Intent();
			intent_cred_info.setClass(getApplicationContext(),CredCardInfoActivity.class);
			intent_cred_info.putExtra("type", "船舶证书");
			startActivity(intent_cred_info);
			break;
			
		//违章信息	
		case R.id.relative4:
			
			Intent intent_break_info=new Intent(getApplicationContext(),BreakRulesActivity.class);
			startActivity(intent_break_info);
			break;
			
		//诚信扣分	
		case R.id.relative5:
			Intent intent_deduct_info = new Intent();
			intent_deduct_info.setClass(getApplicationContext(), DeductPointsActivity.class);
			intent_deduct_info.putExtra("PointsType", "船舶扣分");
			startActivity(intent_deduct_info);
			break;
			
		//报港记录	
		case R.id.relative6:
			
			Intent intent_baogang=new Intent(getApplicationContext(),LetInRecordActivity.class);
			
			intent_baogang.putExtra("shipid", shipid);
			startActivity(intent_baogang);
			break;
			
		//缴费信息	
		case R.id.relative8:
			Intent intent_pay = new Intent();
			intent_pay.setClass(getApplicationContext(), PayInfoActivity.class);
//			if(jiaofei_reason==null||"".equals(jiaofei_reason))
//			{
//				
//			}else{
//				intent_pay.putExtra("jiaofei_reason", jiaofei_reason);
//			
//			}
			startActivity(intent_pay);
			break;
		
			
		default:
			break;
		}
		
	}
	
	
	
	// 获取证书状态信息
		private void GetShipCheckTask() {

			// TODO Auto-generated method stub

			// 访问网络

			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Username=" + SystemStatic.userName);

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.shipcheck;
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
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
