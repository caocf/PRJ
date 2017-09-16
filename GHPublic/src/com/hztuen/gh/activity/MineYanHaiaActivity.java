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
import com.hxkg.company.CompanyMolel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;  
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineYanHaiaActivity extends Activity implements OnClickListener
{	
	private RelativeLayout relative_baseinfo,relative_card,relative_point,relative_yingji,relative_gangzuo,relative_gangkou
	,relative_bowei,relative_guanzu,relative_zhanqiao,relative_duichang,relative_cangku,relative_maodi,relative_futong,
	relative_keyun,relative_guandao,relative_weihuo,relative_puhuo,relative_title_final;
	private TextView text_comp;
	private ImageButton btn_setting;
	
	CompanyMolel comModol=new CompanyMolel();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_yanhai);
		
		init();
		GetCompListTask();
	}
	private void init()
	{
		
		text_comp=(TextView)findViewById(R.id.text_comp);
		
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		relative_baseinfo=(RelativeLayout)findViewById(R.id.relative2);//基本信息
		relative_baseinfo.setOnClickListener(this);
		relative_card=(RelativeLayout)findViewById(R.id.relative3);//证书信息
		relative_card.setOnClickListener(this);
		relative_point=(RelativeLayout)findViewById(R.id.relative5);//诚信扣分
		relative_point.setOnClickListener(this);
		relative_yingji=(RelativeLayout)findViewById(R.id.relative6);//应急安全设备
		relative_yingji.setOnClickListener(this);
		relative_gangzuo=(RelativeLayout)findViewById(R.id.relative8);//港作船舶
		relative_gangzuo.setOnClickListener(this);
		relative_gangkou=(RelativeLayout)findViewById(R.id.relative9);//港口机械
		relative_gangkou.setOnClickListener(this);
		relative_bowei=(RelativeLayout)findViewById(R.id.relative12);//码头泊位
		relative_bowei.setOnClickListener(this);
		relative_guanzu=(RelativeLayout)findViewById(R.id.relative13);//罐组储罐
		relative_guanzu.setOnClickListener(this);
		relative_zhanqiao=(RelativeLayout)findViewById(R.id.relative14);//栈桥运输管道
		relative_zhanqiao.setOnClickListener(this);
		relative_duichang=(RelativeLayout)findViewById(R.id.relative15);//堆场
		relative_duichang.setOnClickListener(this);
		relative_cangku=(RelativeLayout)findViewById(R.id.relative16);//仓库
		relative_cangku.setOnClickListener(this);
		relative_maodi=(RelativeLayout)findViewById(R.id.relative17);//锚地
		relative_maodi.setOnClickListener(this);
		relative_futong=(RelativeLayout)findViewById(R.id.relative18);//浮筒
		relative_futong.setOnClickListener(this);
		relative_keyun=(RelativeLayout)findViewById(R.id.relative19);//客运站
		relative_keyun.setOnClickListener(this);
		
		relative_weihuo=(RelativeLayout)findViewById(R.id.relative21);//危险货物作业申报信息
		relative_weihuo.setOnClickListener(this);
		relative_puhuo=(RelativeLayout)findViewById(R.id.relative22);//普通货物作业报告查询
		relative_puhuo.setOnClickListener(this);
		
		btn_setting=(ImageButton)findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_setting:
			Intent intent_seting=new Intent(getApplicationContext(), MineSettingActivity.class);
			startActivity(intent_seting);
			break;
			//基本信息
		case R.id.relative2:
			if(comModol.statusid==1||comModol.statusid==3)//审核中或未通过
			{
				Toast.makeText(this, "暂无法查看", Toast.LENGTH_LONG).show();
				return;
			}
			String name=text_comp.getText().toString();
			Intent intent_baseInfo=new Intent();
			intent_baseInfo.setClass(getApplicationContext(), CompanyDetialActivity.class);
			intent_baseInfo.putExtra("companyName", name);
			intent_baseInfo.putExtra("CompanyMolel", comModol);
			startActivity(intent_baseInfo);
			
			break;
			//证书信息	
		case R.id.relative3:
			if(comModol.statusid==1||comModol.statusid==3)//审核中或未通过
			{
				Toast.makeText(this, "暂无法查看", Toast.LENGTH_LONG).show();
				return;
			}
			String name2=text_comp.getText().toString();
			Intent intent_cred_info=new Intent();
			intent_cred_info.setClass(getApplicationContext(),CredCardInfoActivity.class);
			intent_cred_info.putExtra("type", "公司证书");
			
			intent_cred_info.putExtra("companyName", name2);
			startActivity(intent_cred_info);
			
			break;
			//诚信扣分
		case R.id.relative5: 
			if(comModol.statusid==1||comModol.statusid==3)//审核中或未通过
			{
				Toast.makeText(this, "暂无法查看", Toast.LENGTH_LONG).show();
				return;
			}
			String name3=text_comp.getText().toString();
			Intent intent_deduct_info = new Intent();
					
			
			intent_deduct_info.setClass(getApplicationContext(), DeductPointsActivity.class);
			intent_deduct_info.putExtra("PointsType", "公司扣分");
			intent_deduct_info.putExtra("companyName", name3);
			startActivity(intent_deduct_info);
			break;
			//应急安全设备
		case R.id.relative6:
			if(comModol.statusid==1||comModol.statusid==3)//审核中或未通过
			{
				Toast.makeText(this, "暂无法查看", Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent_yingji=new Intent();
			intent_yingji.setClass(getApplicationContext(), EmergencyDeviceActivity.class);
			startActivity(intent_yingji);
			break;
			//港作船舶
		case R.id.relative8:
	
			break;
			//港口机械			
		case R.id.relative9:
			
			break;
			//码头泊位
		case R.id.relative12:
	
			break;
			//罐组储罐
		case R.id.relative13:
	
			break;
			//栈桥运输管道
		case R.id.relative14:
	
			break;
			//堆场
		case R.id.relative15:
	
			break;
			//仓库
		case R.id.relative16:
	
			break;
			//锚地
		case R.id.relative17:
	
			break;
			//浮筒
		case R.id.relative18:
			
			break;
			//客运站
		case R.id.relative19:
	
			break;
			
			//危险货物作业申报信息
		case R.id.relative21:
			if(comModol.statusid==1||comModol.statusid==3)//审核中或未通过
			{
				Toast.makeText(this, "暂无法查看", Toast.LENGTH_LONG).show();
				return;
			}
			SystemStatic.Wharfname=text_comp.getText().toString();
			Intent intent_weihuo=new Intent(getApplicationContext(),DuckDangersRecordActivity.class);
			intent_weihuo.putExtra("comid", comModol.name);
			startActivity(intent_weihuo);
	
			break;
			//普通货物作业报告查询
		case R.id.relative22:
	
			break;
			//返回按钮
		case R.id.relative_title_final:
			finish();
			break;
		default:
			break;
		}
		
	}
	
	
	// 按用户名获取公司列表
	
	private void GetCompListTask() {

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
		String toUrl = contants.baseUrl+"MyCompany";
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) 
				{
					super.onSuccess(headers, t);  				
					
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try { 
						JSONObject res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data"); 
						if (data.length() == 0) 
						{
							Toast.makeText(getApplicationContext(), "无公司信息", Toast.LENGTH_SHORT).show();
						} else 
						{
							JSONObject temp = data.getJSONObject(0);
							String comp_name=temp.getString("name");
							comModol.name=comp_name;	
							comModol.id=temp.getString("id");
							text_comp.setText(comp_name);
							JSONObject statusObject=temp.getJSONObject("statusEN");
							comModol.status=statusObject.getString("status");
							comModol.statusid=statusObject.getInt("id");
							if(comModol.statusid==1)//审核中
							{
								findViewById(R.id.text_comstatus).setVisibility(View.VISIBLE);
							}
							else if(comModol.statusid==3)//未通过
							{
								findViewById(R.id.text_comstatus).setVisibility(View.VISIBLE);
								ImageView im=(ImageView) findViewById(R.id.text_comstatus);
								im.setBackgroundResource(R.drawable.refer);
							}
								
							comModol.business=temp.getString("business");
							comModol.regnum=temp.getString("regnumber");
							comModol.regdate=temp.getString("binddate");
							comModol.linkman=temp.getString("contactname");
							
							JSONObject userObject=temp.getJSONObject("userEN");
							JSONObject regionObject=userObject.getJSONObject("region");
							JSONObject usertypeObject=userObject.getJSONObject("usertype");
							comModol.user_region=regionObject.getString("regionname");
							comModol.user_type=usertypeObject.getString("typename");
							
								

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
