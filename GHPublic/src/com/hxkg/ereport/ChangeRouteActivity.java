package com.hxkg.ereport;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ab.util.AbDateUtil;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.view.wheel.AbNumericWheelAdapter;
import com.ab.view.wheel.AbStringWheelAdapter;
import com.ab.view.wheel.AbWheelView;
import com.ab.view.wheel.AbWheelView.AbOnWheelChangedListener;
import com.hxkg.ghpublic.R; 
import com.hztuen.gh.contact.contants;

import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;  
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView; 
import android.widget.Toast;

public class ChangeRouteActivity extends Activity implements HttpRequest.onResult,AbOnWheelChangedListener
{	
	ProgressDialog loginDialog = null;
	
	EReportModol modol;
	View contentView;
	List<String> textDMDateList;
	AbWheelView mWheelViewMD, mWheelViewMM, mWheelViewHH;
	String val;	
	
	PortSpinner portSpinner;
	TextView time;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changeroute); 
		modol=(EReportModol) this.getIntent().getSerializableExtra("EReportModol"); 
		
		initView();
	}
	
	private void initView()
	{
		portSpinner=(PortSpinner) findViewById(R.id.portspinner);
		time=(TextView) findViewById(R.id.time);
	}
	
	public void onCommit(View v)
	{
		String timeString=time.getText().toString().trim();
		long portidString=portSpinner.getSelectedItemId()+1;
		Map<String, Object> map=new HashMap<>();
		map.put("ToID", portidString);
		map.put("Time", timeString);
		String idString=modol.id;
		map.put("id", idString);
		HttpRequest hr=new HttpRequest(this);
		hr.post(contants.baseUrl+"UpDateERePortByID", map);
		
	}
	
	public void onTimePick(View v)
	{
		contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
		final Dialog dialog = new Dialog( this, R.style.dialog);
		dialog.setContentView(contentView); 
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp9 = dialog.getWindow().getAttributes();
		lp9.width = WindowManager.LayoutParams.FILL_PARENT;
		// 设置透明度为0.3
		lp9.alpha = 1f;
		// lp.dimAmount=0.7f;
		dialog.getWindow().setAttributes(lp9);
		dialog.getWindow().setBackgroundDrawableResource(
				R.drawable.pop_shape);
		dialog.show();
		initWheelTime(contentView,(TextView) v,dialog);
	}
	
	public void initWheelTime(View mTimeView, TextView tv, Dialog dialog) 
	{
		 mWheelViewMD = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView1);
		 mWheelViewMM = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView2);
		 mWheelViewHH = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView3);

		int[] centerSelectGradientColors = new int[] { 0x00F8F8FF, 0x00F8F8FF,
				0x00F8F8FF };

		Button okBtn = (Button) mTimeView.findViewById(R.id.btn_ok);
		Button cancelBtn = (Button) mTimeView.findViewById(R.id.btn_cancel);

		mWheelViewMD.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewMM.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewHH.setCenterSelectGradientColors(centerSelectGradientColors);
		
		
		mWheelViewMD.addChangingListener(this);
		mWheelViewMM.addChangingListener(this);
		mWheelViewHH.addChangingListener(this); 		
		
		initWheelTimePicker(this, dialog, tv, okBtn,
				cancelBtn, mWheelViewMD, mWheelViewMM, mWheelViewHH, 2013, 1,
				1, 10, 0, true);
	}
	public void initWheelTimePicker( Activity activity,
			final Dialog dialog, final TextView tv, Button okBtn,
			Button cancelBtn, final AbWheelView mWheelViewMD,
			final AbWheelView mWheelViewHH, final AbWheelView mWheelViewMM,
			int defaultYear, int defaultMonth, int defaultDay, int defaultHour,
			int defaultMinute, boolean initStart) 
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(1);
		int month = calendar.get(2) + 1;
		int day = calendar.get(5);
		int hour = calendar.get(11);
		int minute = calendar.get(12);
		int second = calendar.get(13);

		if (initStart) {
			defaultYear = year;
			defaultMonth = month;
			defaultDay = day;
			defaultHour = hour;
			defaultMinute = minute;
		}

		 val = AbStrUtil.dateTimeFormat(defaultYear + "-" + defaultMonth
				+ "-" + defaultDay + " " + defaultHour + ":" + defaultMinute
				+ ":" + second);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		List<String> list_big = Arrays.asList(months_big);
		List<String> list_little = Arrays.asList(months_little);

		List<String> textDMList = new ArrayList();
		textDMDateList = new ArrayList();
		for (int i = 1; i < 13; i++) {
			if (list_big.contains(String.valueOf(i))) {
				for (int j = 1; j < 32; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(defaultYear + "-" + i + "-" + j);
				}

			} else if (i == 2) {
				if (AbDateUtil.isLeapYear(defaultYear)) {
					for (int j = 1; j < 28; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(defaultYear + "-" + i + "-" + j);
					}
				} else {
					for (int j = 1; j < 29; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(defaultYear + "-" + i + "-" + j);
					}
				}
			} else {
				for (int j = 1; j < 29; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(defaultYear + "-" + i + "-" + j);
				}
			}
		}
		
		
		
		
		int next_year=defaultYear+1;
		for (int i = 1; i < 13; i++) {
			if (list_big.contains(String.valueOf(i))) {
				for (int j = 1; j < 32; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(next_year + "-" + i + "-" + j);
				}

			} else if (i == 2) {
				if (AbDateUtil.isLeapYear(next_year)) {
					for (int j = 1; j < 28; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(next_year + "-" + i + "-" + j);
					}
				} else {
					for (int j = 1; j < 29; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(next_year + "-" + i + "-" + j);
					}
				}
			} else {
				for (int j = 1; j < 29; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(next_year + "-" + i + "-" + j);
				}
			}
		}

		String currentDay = defaultMonth + "月" + " " + defaultDay + "日";
		int currentDayIndex = textDMList.indexOf(currentDay);

		mWheelViewMD.setAdapter(new AbStringWheelAdapter(textDMList, AbStrUtil
				.strLength("12月 12日")));
		mWheelViewMD.setCyclic(true);
		mWheelViewMD.setLabel("");
		mWheelViewMD.setCurrentItem(currentDayIndex);

		mWheelViewMD.setLabelTextSize(35);
		mWheelViewMD.setLabelTextColor(Integer.MIN_VALUE);
		mWheelViewMD.setAdditionalItemHeight(82);
		mWheelViewMD.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMD.setValueTextSize(38);

		
		
		
		mWheelViewHH.setAdapter(new AbNumericWheelAdapter(0, 23));
		mWheelViewHH.setCyclic(true);
		mWheelViewHH.setLabel("点");
		mWheelViewHH.setCurrentItem(defaultHour);
		mWheelViewHH.setLabelTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewHH.setLabelTextSize(35);
		// mWheelViewHH.setLabelTextColor(Integer.MIN_VALUE);
		mWheelViewHH.setAdditionalItemHeight(82);
		mWheelViewHH.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewHH.setValueTextSize(38);

		
		
		
		
		
		mWheelViewMM.setAdapter(new AbNumericWheelAdapter(0, 59));
		mWheelViewMM.setCyclic(true);
		mWheelViewMM.setLabel("分");

		mWheelViewMM.setCurrentItem(defaultMinute);

		mWheelViewMM.setLabelTextSize(35);
		mWheelViewMM.setLabelTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMM.setAdditionalItemHeight(82);
		mWheelViewMM.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMM.setValueTextSize(38);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
				int index1 = mWheelViewMD.getCurrentItem();
				int index2 = mWheelViewHH.getCurrentItem();
				int index3 = mWheelViewMM.getCurrentItem();

				String dmStr = textDMDateList.get(index1);
				Calendar calendar = Calendar.getInstance();
				int second = calendar.get(Calendar.SECOND);
				val = AbStrUtil.dateTimeFormat(dmStr + " " + index2
						+ ":" + index3);
				
				
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");       
				String date_now=sDateFormat.format(new java.util.Date());    //当前时间
				
				
				try {

		            Date beginTime=sDateFormat.parse(date_now);//当前时间
		            Date endTime=sDateFormat.parse(val);//所选择的时间
		            //判断是否大于
		            tv.setText(val);
					dialog.dismiss();

		        } catch (ParseException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
				
			}

		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});

	}
	 
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		finish();
	}
	  
 

	@Override
	public void onSuccess(String result) 
	{		 
		if(loginDialog!=null)
			loginDialog.dismiss();
		Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	public void onError(int httpcode) 
	{
		if(loginDialog!=null)
			loginDialog.dismiss();
		
	}

	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue)
	{
		// TODO Auto-generated method stub
		
	}
	 
}
