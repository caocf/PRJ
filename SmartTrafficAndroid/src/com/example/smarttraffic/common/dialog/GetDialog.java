package com.example.smarttraffic.common.dialog;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class GetDialog 
{	
	public GetDialog(TextView textView)
	{
		Calendar c=Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH);
		hour=c.get(Calendar.HOUR_OF_DAY);
		minute=c.get(Calendar.MINUTE);
		second=c.get(Calendar.SECOND);
		week=c.get(Calendar.DAY_OF_WEEK)-1;
		this.timeTextView=textView;
	}
	
	int year;
	int month;
	int day;
	int hour;
	int minute;
	int second;
	int week;
	
	TextView timeTextView;
	
	public int getWeek() {
		return week;
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	public Dialog getDateDialog(Activity activity)
	{
		return getDateDialog(activity,1);
	}
	
	int type;
	public static final String[] weeks={"日","一","二","三","四","五","六"};
	
	public Dialog getDateDialog(Activity activity,int showType)
	{
		
		Calendar c=Calendar.getInstance();
		this.type=showType;
		
		Dialog dialog;
		dialog=new DatePickerDialog(activity,
			new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int y, int m,int d) 
			{
				year=y;
				month=m+1;
				day=d;
				if(type==1)
				{
					timeTextView.setText(getMonth()+"月"+getDay()+"日  "+getHour()+"："+getMinute()+"");
				}
				else if(type==2)
				{
					Calendar tempCalendar=Calendar.getInstance();
					tempCalendar.set(y, m, d);
					
					timeTextView.setText(getMonth()+"月"+getDay()+"日  "+"星期"+weeks[tempCalendar.get(Calendar.DAY_OF_WEEK)-1]);
				}
			}
		} ,
		c.get(Calendar.YEAR),
		c.get(Calendar.MONTH),
		c.get(Calendar.DAY_OF_MONTH));
		
		return dialog;
	}
	
	public Dialog getTimeDialog(Activity activity)
	{
		Calendar c=Calendar.getInstance();
		
		Dialog dialog;
		dialog=new TimePickerDialog(
				activity, 
                new TimePickerDialog.OnTimeSetListener(){
                    public void onTimeSet(TimePicker view, int h, int m) {
                        hour=h;
                        minute=m;
                        
                        timeTextView.setText(getMonth()+"月"+getDay()+"日  "+getHour()+"："+getMinute()+"");
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            );
		
		return dialog;
	}
	
	public static Dialog getDateDialog(Context context,OnDateSetListener listener,Calendar c)
	{
		return getDateDialog(context, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}
	
	public static Dialog getDateDialog(Context context,OnDateSetListener listener,int year,int month,int day)
	{		
		Dialog dialog;
		dialog=new DatePickerDialog(context,listener,year,month,day);
		
		return dialog;
	}
	
	public static Dialog getTimeDialog(Context c,OnTimeSetListener listener,int hour,int minute,boolean is24hour)
	{		
		Dialog dialog;
		dialog=new TimePickerDialog(c,listener,hour,minute,is24hour);
		
		return dialog;
	}
	
	public static Dialog getRadioDialog(Context ctx,String title,int itemsId,
			OnClickListener listener,String btnName,OnClickListener listener2)
	{
		return getRadioDialog(ctx,title,itemsId,listener,btnName,listener2,0);
	}
	
	 public static Dialog getRadioDialog(Context ctx,String title,int itemsId, 
			 OnClickListener listener,String btnName,OnClickListener listener2,int selectID) {
		  
		 Dialog dialog=null;
		 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);

		 builder.setTitle(title);
		 builder.setSingleChoiceItems(itemsId, selectID, listener);
		 builder.setPositiveButton(btnName, listener2) ;
		 dialog = builder.create();
		 return dialog;
	 }
	 
	 public static Dialog getListDialog(Context ctx,String title,String[] data, OnClickListener listener) 
	 {  
		 Dialog dialog=null;
		 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);

		 builder.setTitle(title);
		 builder.setItems(data, listener);
		 builder.setNegativeButton("", null) ;
		 dialog = builder.create();
		 return dialog;
	 }
	 public static Dialog editDialog(Context ctx,String title,View v,String name1,
			 OnClickListener listener1,String name2,OnClickListener listener2)
	 {
		 Dialog dialog=null;
		 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);

		 builder.setTitle(title);
		 builder.setView(v);
		 builder.setPositiveButton(name1, listener1) ;
		 builder.setNegativeButton(name2, listener2) ;
		 dialog = builder.create();
		 return dialog;
	 }
	 
	 public static Dialog editDialog(Context ctx, String title, View v,
				String name1, OnClickListener listener1, String name2,
				OnClickListener listener2, boolean canCancel,OnKeyListener onKeyListener)
		{
			Dialog dialog = null;
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
					ctx);

			builder.setTitle(title);
			builder.setView(v);
			builder.setPositiveButton(name1, listener1);
			builder.setNegativeButton(name2, listener2);
			builder.setOnKeyListener(onKeyListener);

			dialog = builder.create();
			
			dialog.setCancelable(canCancel);
			
			return dialog;
		}
	 
	 public static Dialog messageDialog(Context ctx,String title,String content,String name1,
			 OnClickListener listener1,String name2,OnClickListener listener2)
	 {
		 Dialog dialog=null;
		 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);

		 TextView textView=new TextView(ctx);
		 textView.setTextSize(20);
		 textView.setPadding(20, 20, 20, 20);
		 textView.setText(content);
		 
		 builder.setTitle(title);
		 builder.setView(textView);
		 builder.setPositiveButton(name1, listener1) ;
		 builder.setNegativeButton(name2, listener2) ;
		 dialog = builder.create();
		 return dialog;
	 }
}
