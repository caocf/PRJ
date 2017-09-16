package com.hztuen.gh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.hxkg.ghpublic.R;

/**
 * @author zzq
 * @DateTime 2016年7月21日 下午6:01:09
 * 安全应急设备详情
 * 布局文件名称为activity_emergency_device_detail
 */
public class EmergencyDeviceDetailActivity extends AbActivity{
	@AbIocView(id = R.id.back,click = "onclick") TextView back;
	/**
	 * 设备相关信息
	 * */
	@AbIocView(id = R.id.device_type) TextView device_type;
	@AbIocView(id = R.id.device_regu) TextView device_regu;
	@AbIocView(id = R.id.device_amount) TextView device_amount;
	@AbIocView(id = R.id.device_time) TextView device_time;
	@AbIocView(id = R.id.device_note) TextView device_note;
	@AbIocView(id = R.id.device_name) TextView device_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_device_detail);
	}
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}
