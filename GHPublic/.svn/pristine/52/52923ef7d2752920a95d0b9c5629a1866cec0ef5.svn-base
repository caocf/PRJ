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

import com.gh.modol.BreakRulesModel;
import com.gh.modol.CredCardModel;
import com.hxkg.ghpublic.R;
import com.hxkg.zjsupervise.ship.ShipInfo;
import com.hxkg.zjsupervise.ship.ShipInfoListener;
import com.hxkg.zjsupervise.ship.XML2Json;
import com.hztuen.gh.activity.Adapter.BreakRulesAdapter;
import com.hztuen.gh.activity.Adapter.CredCardAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BreakRulesActivity extends Activity implements OnClickListener,ShipInfoListener{

	private RelativeLayout relative_title_final;
	private ListView lv_break;
	private List<BreakRulesModel> modellist = new ArrayList<BreakRulesModel>();
	private BreakRulesAdapter breakAdapter;
	private ImageView img_no;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_break_rules);
		init();
	}
	private void init()
	{
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		relative_title_final.setOnClickListener(this);
		img_no=(ImageView)findViewById(R.id.img_no);
		lv_break=(ListView)findViewById(R.id.lv_break);
		
		breakAdapter=new BreakRulesAdapter(getApplicationContext(),modellist);
		
	//	GetCredCardTask();
		
		
		ShipInfo shipi=new ShipInfo(new ShipInfoListener() {
			
			@Override
			public void onShipInofo(String result) {
				// TODO Auto-generated method stub
				List<Map<String,String>> list=null;
				
				try {
					list = XML2Json.AnalysisOfXML(result,"recordset","record");

					
					for(int i=0;i<list.size();i++)
					{
						Map<String,String> map = list.get(i);
						
						BreakRulesModel model = new BreakRulesModel();

						model.setSLSJ(map.get("SLSJ"));
						model.setFADD(map.get("FADD"));
						model.setWFNR(map.get("WFNR"));
						model.setCFLB(map.get("CFLB"));
						
						model.setSFCF(map.get("SFCF"));//是否处罚
						
//						TextView SLSJ;//受理时间	
//						TextView FADD;//法案地点
//						TextView WFNR;//违法内容
//						
//						TextView CFLB;//处罚类别
						
						modellist.add(model);
						
					}
					
					
					
				    
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (modellist.size() == 0) {
								lv_break.setVisibility(View.GONE);
								img_no.setVisibility(View.VISIBLE);
							} else {
								lv_break.setVisibility(View.VISIBLE);
								img_no.setVisibility(View.GONE);
							}
							lv_break.setAdapter(breakAdapter);
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
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_CF_CBWZCFXX",0);
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
	
	
	
	//获取违章信息；列表
	private void GetCredCardTask() {

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
		String toUrl = contants.illegallist;
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

						JSONObject res = new JSONObject(result);
						JSONArray data = res.getJSONArray("recordset");

						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "无信息",
									Toast.LENGTH_SHORT).show();
						} else {

							for (int i = 0; i < data.length(); i++) {
								JSONObject temp = data.getJSONObject(i);
								BreakRulesModel model = new BreakRulesModel();

//								private String AJLB;    //	AJLB	案件类别
//								private String AY;		//	AY	案由
//								private String CFLB;    //处罚类别，每位0表示无此类，1表示有此类。从第一位到第10位的处罚类别顺序分别为：
//								private String CFYJ;    //	CFYJ	处罚意见		
//								private String CFTK;    //	CFTK	处罚条款
//								private String FADD;    //	FADD	法案地点	
//								private String FASJ;   //	FASJ	发案时间
//								private String JARQ;   //	JARQ	结案日期	
//								private String SFCF;   //	SFCF	是否处罚，0表示否，1表示是		
//								private String ID;		//	ID	ID
//								private String SFJA;	//	SFJA	是否结案，0表示否，1表示是
//								private String SLH;		//	SLH	受理号
//								private String SLSJ;	//	SLSJ	受理时间	
//								private String WFNR;	//	WFNR	违法内容
//								private String WFTK;    //	WFTK	违反条款
//								private String ZFSCBH;  //	ZFSCBH	执法手册编号
//								private String ZWCM;    //	ZWCM	中文船名
//								private String ZYSS;    //	ZYSS	主要事实
								model.setAJLB(temp.getString("AJLB"));
								model.setAY(temp.getString("AY"));
								model.setCFLB(temp.getString("CFLB"));
								model.setCFYJ(temp.getString("CFYJ"));
								model.setCFTK(temp.getString("CFTK"));
								model.setFADD(temp.getString("FADD"));
								model.setFASJ(temp.getString("FASJ"));
								model.setJARQ(temp.getString("JARQ"));
								model.setSFCF(temp.getString("SFCF"));
								model.setID(temp.getString("ID"));
								model.setSFJA(temp.getString("SFJA"));
								model.setSLH(temp.getString("SLH"));
								
								model.setSLSJ(temp.getString("SLSJ"));
								model.setWFNR(temp.getString("WFNR"));
								model.setZFSCBH(temp.getString("ZFSCBH"));
								model.setZWCM(temp.getString("ZWCM"));
								model.setZYSS(temp.getString("ZYSS"));

								modellist.add(model);

							}
							lv_break.setAdapter(breakAdapter);
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
