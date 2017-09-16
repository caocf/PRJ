package com.hztuen.gh.activity;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ghpublic.entity.TideDetail;
import com.hxkg.ghpublic.R;
import com.hxkg.ghpublic.R.layout;
import com.hztuen.gh.activity.Adapter.TideDetailAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.ParseUtil;
import com.hztuen.lvyou.utils.Util;

public class TideDetailActivity extends AbActivity{
	@AbIocView (id = R.id.relative_title , click = "click") RelativeLayout back;
	@AbIocView (id = R.id.title) TextView title;
	@AbIocView (id = R.id.tide_detail_list) ListView detail_lv;
	private String port = "";
	private String day = "";
	private List<TideDetail> mTideDetail_list = new ArrayList<TideDetail>();
	private TideDetailAdapter mAdapter;
	private View footview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tide_detail);
		footview = LinearLayout.inflate(getApplicationContext(), R.layout.tide_detail_bottom, null);
		detail_lv.addFooterView(footview);
		
		detail_lv.setDividerHeight(0);
		mAdapter = new TideDetailAdapter(abApplication, mTideDetail_list);
		detail_lv.setAdapter(mAdapter);
		Intent mIntent = getIntent();
		port = mIntent.getStringExtra("port");
		day = mIntent.getStringExtra("day");
		title.setText(port+day.substring(5, 7)+"月"+day.substring(8, day.length())+"日潮汐表");
		day = day+" 00:00:00";
		
		
		initDate();
	}
	public void initDate(){
		//访问网络
				KJHttp kj = new KJHttp();
				List<String> aa = new ArrayList<String>();

				aa.add("port="+port);
				aa.add("day="+day);
				//访问地址
				org.kymjs.kjframe.http.HttpParams params = null;
				try {
					params = Util.prepareKJparams(aa);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//访问地址
				String toUrl = contants.GetTide;
				if(params == null){
					//提示参数制造失败
					Util.getTip(getApplicationContext(), "构造参数失败");
				}else if(!toUrl.equals("")){
					kj.post(toUrl, params, false, new HttpCallBack(){

						@Override
						public void onSuccess(Map<String, String> headers, byte[] t) {
							// TODO Auto-generated method stub
							super.onSuccess(headers, t);
							
							// 获取cookie
							KJLoger.debug("===" + headers.get("Set-Cookie"));
//							Log.i(TAG+":kymjs---http", new String(t));
							String result = new String(t).trim();
							
							try{
								JSONArray jsor = new JSONArray(result);
								if(jsor.length()>0){
									for(int i = 0;i<jsor.length();i++){
										JSONObject obj = jsor.getJSONObject(i);
										TideDetail td = new TideDetail();
										td = ParseUtil.parseDataToEntity(obj, TideDetail.class);
										mTideDetail_list.add(td);
									}
									mAdapter.notifyDataSetChanged();
								}else{
									Toast.makeText(getApplicationContext(), "暂无数据...", Toast.LENGTH_SHORT).show();
								}
								
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						
						
						
					});
				}//else这里结束

	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.relative_title:
			finish();
			break;
		default:
			break;
		}
	}
}
