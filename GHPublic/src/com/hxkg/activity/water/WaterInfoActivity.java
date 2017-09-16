package com.hxkg.activity.water;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;
import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil; 
import com.gh.modol.WaterInfo;
import com.hxkg.ghpublic.R; 
import com.hztuen.gh.activity.ActivityNewsFrom;
import com.hztuen.gh.activity.Adapter.WaterListAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;  
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:04:22
 */
public class WaterInfoActivity extends AbActivity implements OnItemSelectedListener
{
	private TextView image_back;//返回
	private CitySpinner postion_button;//定位按钮
	private ListView water_info_list;//水文信息列表
	private List<WaterInfo> waterInfoList = new ArrayList<WaterInfo>();
	private TextView updatetime;//更新时间
	private String City = "嘉兴";
	private WaterListAdapter waterListAdapter;//列表的适配器
	private static final String TAG = WaterInfo.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_water_info);

		image_back = (TextView) findViewById(R.id.water_info_back); 
		water_info_list = (ListView) findViewById(R.id.water_info_list);
		updatetime = (TextView) findViewById(R.id.updatetime);
		updatetime.setText("当前水位信息采集与：");
		image_back.setOnClickListener(onclicklistener);
		postion_button=(CitySpinner) findViewById(R.id.channelspinner);
		postion_button.setOnItemSelectedListener(this);
		water_info_list.setDividerHeight(0);
		//
		waterListAdapter = new WaterListAdapter(getApplicationContext(), waterInfoList);
		water_info_list.setAdapter(waterListAdapter);
		water_info_list.setOnItemClickListener(onitemclicklistener);
		SharedPreferences preferences= getSharedPreferences("Data", 0);
		City=preferences.getString("cityname", "杭州"); 
		init();
	}
	public void init(){
		AbDialogUtil.showProgressDialog(WaterInfoActivity.this, 0,
				"网络加载中...");
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getWaterInfo(1);
				
			}
		}, 1000);
	}
	public OnItemClickListener onitemclicklistener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (parent.getId()){
			case R.id.water_info_list:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			}
		}
		private void expressItemClick(int position) {
			// TODO Auto-generated method stub
			String update = waterInfoList.get(position).getUpdatetime();
			updatetime.setText("当前水位信息采集与：" + update);
		}
	};
	public OnClickListener onclicklistener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.water_info_back:
				finish();
				break; 
			default:
				break;
			}
		}

	};
	
	public void getWaterInfo(int id)
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("CityID="+id);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.waterlist_url;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try { 
						JSONObject  res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data");
						String upst = "";
						if(data.length()==0){
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									AbDialogUtil.removeDialog(WaterInfoActivity.this);
									Toast.makeText(getApplicationContext(), "暂无数据。", Toast.LENGTH_SHORT).show();
								}
							}, 1000);

						}

						for(int i = 0;i<data.length();i++){
							JSONObject temp = data.getJSONObject(i);
							WaterInfo waterInfo = new WaterInfo();
							waterInfo.setId(temp.getLong("id"));
							upst=temp.getString("updatetime");
							if(upst.length()>15){
								upst = upst.substring(0, 16);
							}
							waterInfo.setUpdatetime(upst);
							waterInfo.setWhatchpoint(temp.getString("whatchpoint"));
							waterInfo.setSwsz(temp.getDouble("swsz"));
							waterInfo.setSyl(temp.getDouble("syl"));
							waterInfo.setRyl(temp.getInt("ryl"));
							waterInfo.setCjjx(temp.getInt("cjjx"));
							waterInfo.setCwj(temp.getInt("cwj"));
							waterInfo.setRegion(temp.getString("region"));
							waterInfoList.add(waterInfo);
						}
						waterListAdapter.pre(waterInfoList);
						waterListAdapter.notifyDataSetChanged();
						AbDialogUtil.removeDialog(WaterInfoActivity.this);
					}catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
					);
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
	{
		getWaterInfo(position+1);
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		// TODO Auto-generated method stub
		
	}
}
