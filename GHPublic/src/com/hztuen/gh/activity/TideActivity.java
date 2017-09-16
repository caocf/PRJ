package com.hztuen.gh.activity;

import java.util.Calendar;
import java.util.Date;

import com.ab.activity.AbActivity;
import com.ab.util.AbDateUtil;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.view.ioc.AbIocView;
import com.ab.view.wheel.AbWheelUtil;
import com.ab.view.wheel.AbWheelView;
import com.hxkg.ghpublic.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author  zzq
 * 2016年8月9日 下午6:08:49
 * 潮汐信息
 */
public class TideActivity extends AbActivity{

	@AbIocView (id = R.id.relative_title_final ,click = "click") RelativeLayout back;
	@AbIocView (id = R.id.gangkou,click = "click") TextView gangkou_tv;
	@AbIocView (id = R.id.riqi,click = "click") TextView riqi_tv;
	@AbIocView (id = R.id.search,click = "click") Button search_btn;
	private String port = "";
	private View mTimeView1 = null;
	private String ymd = "";
	private int marker = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tide);
	}
	public void click(View v){
		
		switch (v.getId()) {
		case R.id.relative_title_final:
			finish();
			break;
		case R.id.gangkou:
			Intent intent = new Intent();
			intent.setClass(TideActivity.this, TidePortActivity.class);
			startActivityForResult(intent, 10001);
			break;
		case R.id.riqi:
			marker = 1;
			mTimeView1 = getLayoutInflater().inflate(R.layout.pop_date_choose,
					null);
			initWheelDate(mTimeView1,riqi_tv);
			AbDialogUtil.showDialog(mTimeView1,Gravity.BOTTOM);
			break;
		case R.id.search:
			if(marker==0){
				Toast.makeText(getApplicationContext(), "请选择日期...", Toast.LENGTH_SHORT).show();
				return;
			}else{
				if(port.equals("")){
					Toast.makeText(getApplicationContext(), "请选择港口...", Toast.LENGTH_SHORT).show();
				}else{
					Intent mIntent = new Intent().setClass(TideActivity.this,TideDetailActivity.class);
					ymd = riqi_tv.getText().toString().trim();
//					mIntent.setClass(TideActivity.this, TideDetailActivity.class);
					mIntent.putExtra("day", ymd);
					mIntent.putExtra("port", port);
					startActivity(mIntent);
				}
			}
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, mIntent);
		if (mIntent != null) {
			switch (requestCode) {
			case 10001:
				port = mIntent.getStringExtra("port");
				gangkou_tv.setText(port);
				break;
			default:
				break;
			}
		}
	}
	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	public void initWheelDate(View mDateView,TextView mText){
		int[] centerSelectGradientColors = new int[] { 0x00F8F8FF, 0x00F8F8FF,
				0x00F8F8FF };
		//年月日时间选择器
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		String date =  mText.getText().toString().trim();
		if(!AbStrUtil.isEmpty(date)){
			Date dateNew = AbDateUtil.getDateByFormat(date, AbDateUtil.dateFormatYMD);
			if(dateNew!=null){
				year = 1900+dateNew.getYear();
				month = dateNew.getMonth()+1;
				day = dateNew.getDate();
			}
		}
		final AbWheelView mWheelViewY = (AbWheelView)mDateView.findViewById(R.id.wheelView1);
		final AbWheelView mWheelViewM = (AbWheelView)mDateView.findViewById(R.id.wheelView2);
		final AbWheelView mWheelViewD = (AbWheelView)mDateView.findViewById(R.id.wheelView3);
		Button okBtn = (Button)mDateView.findViewById(R.id.btn_ok);
		Button cancelBtn = (Button)mDateView.findViewById(R.id.btn_cancel);
		mWheelViewY.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewM.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewD.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewY.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewY.setValueTextSize(38);
		mWheelViewY.setAdditionalItemHeight(82);

		mWheelViewM.setValueTextSize(38);
		mWheelViewM.setAdditionalItemHeight(82);
		mWheelViewM.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));


		mWheelViewD.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewD.setValueTextSize(38);
		mWheelViewD.setAdditionalItemHeight(82);
		AbWheelUtil.initWheelDatePicker(this, mText, mWheelViewY, mWheelViewM, mWheelViewD,okBtn,cancelBtn, year,month,day, year, 120, false);


	}
}
