package com.huzhouport.time;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.hxkg.ghmanager.R;


import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;



public class WheelMain {

	private PopupWindow popupWindow;
	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	private static int START_YEAR = 1990, END_YEAR = 2100;

	public WheelMain() {

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

	public WheelMain(PopupWindow popupWinddow, View view) {
		super();
		this.popupWindow = popupWinddow;
		this.view = view;
	}

	/**
	 * @Description: TODO ��������ʱ��ѡ����
	 */
	public void showDateTimePicker(final View v) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// ��Ӵ�С���·ݲ�����ת��Ϊlist,����֮����ж�
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// ��
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// ����"��"����ʾ����
		wv_year.setCyclic(true);// ��ѭ������
		wv_year.setLabel("��");// �������
		wv_year.setCurrentItem(year - START_YEAR);// ��ʼ��ʱ��ʾ������

		// ��
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("��");
		wv_month.setCurrentItem(month);

		// ��
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// ����
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("��");
		wv_day.setCurrentItem(day - 1);

		// ʱ
		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// ��
		wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		// ���"��"����
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
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
		// ���"��"����
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
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

		// ������Ļ�ܶ���ָ��ѡ��������Ĵ�С
		int textSize = 0;

		textSize = 32;

		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		// ����dialog�Ĳ���,����ʾ
		popupWindow.setContentView(view);
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
	}

	public String getTime() {
		
		System.out.println("wv1="+wv_month.getCurrentItem());
		System.out.println("wv2="+wv_day.getCurrentItem());
		System.out.println("wv3="+wv_hours.getCurrentItem());
		System.out.println("wv4="+wv_mins.getCurrentItem());
	    
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
