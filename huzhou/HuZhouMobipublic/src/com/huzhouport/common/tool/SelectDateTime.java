package com.huzhouport.common.tool;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;


import com.example.huzhouportpublic.R;
import com.huzhouport.time.NumericWheelAdapter;
import com.huzhouport.time.OnWheelChangedListener;
import com.huzhouport.time.WheelView;

public class SelectDateTime {

	private PopupWindow popupWindow;
	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	private static int START_YEAR = 1990, END_YEAR = 2100;

	public SelectDateTime() {

	}

	public PopupWindow getPopupWinddow() {
		return popupWindow;
	}

	public void setPopupWinddow(PopupWindow popupWinddow) {
		this.popupWindow = popupWinddow;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public SelectDateTime(PopupWindow popupWinddow, View view) {
		super();
		this.popupWindow = popupWinddow;
		this.view = view;
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void showDateTimePicker(final View v) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		ShowViewByTime(v,year,month,day,hour ,minute);
	}
	public void showDateTimePicker(final View v,String stime) {
		if (stime == null || stime.equals(""))
		{
			Date dt = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm");
			stime = format.format(dt);
		}
		String[] subString1=stime.split(" ");
		String[] substring2 =subString1[0].split("-");
		String[] substring3 =subString1[1].split(":");
		int year= Integer.parseInt(substring2[0]);
		int month = Integer.parseInt(substring2[1])-1;
		int day = Integer.parseInt(substring2[2]);
		int hour = Integer.parseInt(substring3[0]);
		int minute = Integer.parseInt(substring3[1]);
		ShowViewByTime(v,year,month,day,hour ,minute);
	}
	public void ShowViewByTime(final View v,int year,int month,int day,int hour,int minute){

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 时
		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;

		textSize = 32;

		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		// 设置dialog的布局,并显示
		popupWindow.setContentView(view);
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);	
	}
	public String getTime() {
		String month="";
		String day="";
		String hours="";
		String mins="";
		if(wv_month.getCurrentItem()+1<10){
			month="0"+(wv_month.getCurrentItem()+1);
		}else{
			month=wv_month.getCurrentItem()+1+"";
		}
		
		if(wv_day.getCurrentItem()+1<10){
			day="0"+(wv_day.getCurrentItem()+1);
		}else{
			day=wv_day.getCurrentItem()+1+"";
		}
		
		if(wv_hours.getCurrentItem()<10){
			hours="0"+wv_hours.getCurrentItem();
		}else{
			hours=wv_hours.getCurrentItem()+"";
		}
		if(wv_mins.getCurrentItem()<10){
			mins="0"+wv_mins.getCurrentItem();
		}else{
			mins=wv_mins.getCurrentItem()+"";
		}
		
		
		StringBuffer sb = new StringBuffer();
		/*sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
				.append((wv_month.getCurrentItem() + 1)).append("-")
				.append((wv_day.getCurrentItem() + 1)).append("-")
				.append(wv_hours.getCurrentItem()).append(":")
				.append(wv_mins.getCurrentItem());*/
		sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
		.append(month).append("-")
		.append(day).append(" ")
		.append(hours).append(":")
		.append(mins);
		return sb.toString();
	}
}
