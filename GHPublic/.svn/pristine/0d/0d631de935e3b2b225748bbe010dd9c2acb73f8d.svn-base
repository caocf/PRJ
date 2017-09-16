package com.hztuen.gh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.hxkg.ghpublic.R;

/**
 * @author zzq
 * @DateTime 2016年7月21日 下午6:02:17
 * 安全应急设备
 * 布局文件为activity_emergency_device
 * activity_emergency_device里面的listview的子控件布局文件名为emergency_device_item
 * 列表替换 emergency_device_item暂时不用 后续补充
 */
public class EmergencyDeviceActivity extends AbActivity{
	@AbIocView(id = R.id.back,click = "click") TextView back;
	/**
	 * 列表相关
	 * */
	@AbIocView(id = R.id.shoes,click = "click") TextView shoes;//防静电服
	@AbIocView(id = R.id.duijiangji,click = "click") TextView duijiangji;//对讲机
	@AbIocView(id = R.id.maodeng,click = "click") TextView maodeng;//帽灯
	@AbIocView(id = R.id.gas,click = "click") TextView gas;//可燃气体检测证书
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_device);
	}
	public void click(View v){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), EmergencyDeviceDetailActivity.class);
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.shoes:
			intent.putExtra("", "");
			startActivity(intent);
			break;
		case R.id.duijiangji:
			intent.putExtra("", "");
			startActivity(intent);
			break;
		case R.id.maodeng:
			intent.putExtra("", "");
			startActivity(intent);
			break;
		case R.id.gas:
			intent.putExtra("", "");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
