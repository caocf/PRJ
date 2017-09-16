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
import android.content.Intent;
import android.os.Bundle;  
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.EditText;
import android.widget.TextView; 
import android.widget.Toast;

public class ReportActivity extends Activity implements HttpRequest.onResult,AbOnWheelChangedListener
{  	 
	//时间
	SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private AbWheelView mWheelViewMD, mWheelViewMM, mWheelViewHH;
	private  String val;
	private List<String> textDMDateList;
	TextView time,time_in,timegas;
	private View contentView;
	//其他参数
	int reportid;
	String reportstring[]={"重船报告","空船报告"};
	TextView type;
	EditText goodscount,gascount;
	ShipSpinner shipsp;
	InOutSpinner inoutsp;
	PortSpinner fromsp,endsp;
	GoodsTypeSpinner goodstypesp;
	UnitSpinner unitsp;
	ProgressDialog loginDialog = null;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
		reportid=this.getIntent().getIntExtra("reportid", 0);//默认重船报告
		initView();
	} 
	
	public void onBack(View v)
	{
		finish();
	}
	
	private void initView()
	{
		type=(TextView) this.findViewById(R.id.type);
		type.setText(reportstring[reportid]);
		
		time=(TextView) this.findViewById(R.id.time);
		time.setText(sdDateFormat.format(new Date()));
		timegas=(TextView) this.findViewById(R.id.timegas);
		timegas.setText(sdDateFormat.format(new Date()));
		
		
		goodscount=(EditText) this.findViewById(R.id.goodscount);
		gascount=(EditText) this.findViewById(R.id.gascount);
		
		shipsp=(ShipSpinner) this.findViewById(R.id.shipspinner);
		shipsp.setID("");
		inoutsp=(InOutSpinner) this.findViewById(R.id.inoutpinner);
		fromsp=(PortSpinner) this.findViewById(R.id.fromspinner);
		endsp=(PortSpinner) this.findViewById(R.id.endpinner);
		goodstypesp=(GoodsTypeSpinner) this.findViewById(R.id.goodspinner);
		unitsp=(UnitSpinner) this.findViewById(R.id.unitspinner);
	}
	
	public void onTimePick(View v)
	{
		contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
		final Dialog dialog = new Dialog( this, R.style.dialog);
		dialog.setContentView(contentView);
		time_in = (TextView) contentView.findViewById(R.id.time_in);
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
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		 
		
    }
	 
	public void onSubmit(View v)
	{
		String s1=goodscount.getText().toString();
		String s2=gascount.getText().toString();
		
		if("".equals(s1))
		{
			Toast.makeText(this, "请输入载货数量", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(s2))
		{
			Toast.makeText(this, "请输入上次加油量", Toast.LENGTH_LONG).show();
			return;
		}
		
		
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		
		map.put("ReportTypeID", reportid+1);//System.out.println(reportid+1);
		map.put("Shipname", (String)shipsp.getSelectedItem());//System.out.println((String)shipsp.getSelectedItem());
		map.put("FromID", fromsp.getSelectedItemPosition()+1);//System.out.println(fromsp.getSelectedItemPosition()+1);
		map.put("ToID", endsp.getSelectedItemPosition()+1);//System.out.println(endsp.getSelectedItemPosition()+1);
		map.put("GoodsTypeID", goodstypesp.getSelectedItemPosition()+1);//System.out.println(goodstypesp.getSelectedItemPosition()+1);
		map.put("Tons", s1);//System.out.println(s1+1);
		map.put("UnitID", unitsp.getSelectedItemPosition()+1);//System.out.println(unitsp.getSelectedItemPosition()+1);
		map.put("Time", time.getText().toString().trim());//System.out.println(time.getText().toString().trim());
		map.put("GasMount", s2);//System.out.println(s2);
		map.put("GasTime", timegas.getText().toString().trim());//System.out.println(timegas.getText().toString().trim());
		map.put("InOutID", inoutsp.getSelectedItemPosition()+1);//System.out.println(inoutsp.getSelectedItemPosition()+1);
		hr.post(contants.baseUrl+"CommitEReport", map);
		loginDialog=new ProgressDialog(this);
		loginDialog.setMessage("数据上传中...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}
 

	@Override
	public void onSuccess(String result) 
	{		 
		if(loginDialog!=null)
			loginDialog.dismiss();
		Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	public void onError(int httpcode) 
	{
		if(loginDialog!=null)
			loginDialog.dismiss();
		
	}
	
	public void initWheelTime(View mTimeView, TextView tv, Dialog dialog) {
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
		// mWheelViewMD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));
		// mWheelViewMM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));
		// mWheelViewHH.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));

		mWheelViewMD.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewMM.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewHH.setCenterSelectGradientColors(centerSelectGradientColors);
		
		
		mWheelViewMD.addChangingListener(this);
		mWheelViewMM.addChangingListener(this);
		mWheelViewHH.addChangingListener(this);
//		
//		mWheelViewMD.setBackgroundColor(Color.parseColor("#F5F5F5"));
//		mWheelViewMM.setBackgroundColor(Color.parseColor("#F5F5F5"));
//		mWheelViewHH.setBackgroundColor(Color.parseColor("#F5F5F5"));
		
	
		
		
		initWheelTimePicker(this, dialog, tv, okBtn,
				cancelBtn, mWheelViewMD, mWheelViewMM, mWheelViewHH, 2013, 1,
				1, 10, 0, true);
	}

	public void initWheelTimePicker( Activity activity,
			final Dialog dialog, final TextView tv, Button okBtn,
			Button cancelBtn, final AbWheelView mWheelViewMD,
			final AbWheelView mWheelViewHH, final AbWheelView mWheelViewMM,
			int defaultYear, int defaultMonth, int defaultDay, int defaultHour,
			int defaultMinute, boolean initStart) {
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
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		
	}
}
