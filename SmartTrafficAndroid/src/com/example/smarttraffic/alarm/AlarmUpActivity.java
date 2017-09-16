package com.example.smarttraffic.alarm;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmUpActivity extends FragmentActivity
{
	AlarmInfo alarmInfo;
	
	TextView upTextView;
	TextView downTextView;
	TextView methodTextView;
	TextView timeTextView;
	TextView contentTextView;
	
	public static final String ALARM_FROM_LINE="alarm_from_line";
	public static final String ALARM_STATITION_ID="alarm_station_id";
	public static final String ALARM_STATITION_NAME="alarm_station_name";
	public static final String ALARM_LINE_ID="alarm_line_id";
	public static final String ALARM_LINE_NAME="alarm_line_name";
	
	boolean isFromLine;
	String stationID;
	String stationName;
	String lineID;
	String lineName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_up);
		
		initHead();		
		alarmInfo=new AlarmInfo();
		alarmInfo.setUp(true);
		alarmInfo.setMethod(0);
		alarmInfo.setTime(1);
		alarmInfo.setWeeks(new boolean[7]);
		
		initParam();
		initView();
	}
	
	private void initParam()
	{
		isFromLine=getIntent().getBooleanExtra(ALARM_FROM_LINE, false);
		
		stationID=getIntent().getStringExtra(ALARM_STATITION_ID);
		lineID=getIntent().getStringExtra(ALARM_LINE_ID);
		stationName=getIntent().getStringExtra(ALARM_STATITION_NAME);
		lineName=getIntent().getStringExtra(ALARM_LINE_NAME);
		
//		alarmInfo.setStationID(stationID);
//		alarmInfo.setStationName(stationName);
//		alarmInfo.setLineID(lineID);
//		alarmInfo.setLineName(lineName);
	}
	private void initView()
	{
		upTextView=(TextView)findViewById(R.id.alarm_up_select_up);
		downTextView=(TextView)findViewById(R.id.alarm_up_select_down);
		methodTextView=(TextView)findViewById(R.id.alarm_up_method);
		timeTextView=(TextView)findViewById(R.id.alarm_up_time);
		contentTextView=(TextView)findViewById(R.id.alarm_up_content);
		
		contentTextView.setText("提醒站点："+stationName+" 提醒线路:"+lineName);
	}
	
	private void initHead()
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("详细设置");
		ManagerFragment.replaceFragment(this,R.id.alarm_up_head, fragment);
	}
	
	int[] resID=new int[]{R.array.array_alarm_station,R.array.array_alarm_time,R.array.array_alarm_length};
	int num;
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.alarm_up_advance:
				if(alarmInfo.isAdvance())
				{
					findViewById(R.id.alarm_up_advance_content).setVisibility(View.GONE);
					((ImageView)findViewById(R.id.alarm_up_advance)).setImageResource(R.drawable.switch_off_);
				}
				else 
				{
					findViewById(R.id.alarm_up_advance_content).setVisibility(View.VISIBLE);
					((ImageView)findViewById(R.id.alarm_up_advance)).setImageResource(R.drawable.switch_on_);
				}
				alarmInfo.setAdvance(!alarmInfo.isAdvance());
				break;
			
			case R.id.alarm_up_select_up:
				upTextView.setBackgroundResource(R.drawable.button_background_normal);
				upTextView.setTextColor(0xffffffff);
				downTextView.setBackgroundColor(getResources().getColor(R.color.back_groud_white));
				downTextView.setTextColor(0xff000000);
				
				alarmInfo.setUp(true);
				
				break;
				
			case R.id.alarm_up_select_down:
				downTextView.setBackgroundResource(R.drawable.button_background_normal);
				downTextView.setTextColor(0xffffffff);
				upTextView.setBackgroundColor(getResources().getColor(R.color.back_groud_white));
				upTextView.setTextColor(0xff000000);
				
				alarmInfo.setUp(false);
				break;
				
			case R.id.alarm_up_method:
				GetDialog.getRadioDialog(this, "", R.array.array_alarm_method,new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alarmInfo.setMethod(which);
						methodTextView.setText(getResources().getStringArray(R.array.array_alarm_method)[which]);
						dialog.cancel();
					}
				}, "", null,alarmInfo.getMethod()).show();
				
				break;
				
			case R.id.alarm_up_time:
				GetDialog.getRadioDialog(this, "",resID[alarmInfo.getMethod()] , new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						alarmInfo.setTime(Integer.valueOf(getResources().getStringArray(resID[alarmInfo.getMethod()])[which]));
						
						String[] str=new String[]{"站提醒","分提醒","米提醒"};
						timeTextView.setText("提前"+getResources().getStringArray(resID[alarmInfo.getMethod()])[which]+str[alarmInfo.getMethod()]);
						num=which;
						dialog.cancel();
					}
				}, "", null,alarmInfo.getTime()).show();
				
				break;
				
			case R.id.alarm_up_enter:				
				FavorDBOperate dbOperate=new FavorDBOperate(this);
				
				int[] contentType=new int[]{ContentType.SMART_BUS_ALARM_FAVOR,ContentType.SMART_BUS_ALARM_DOWN_FAVOR};
				
				String[] ls=lineID.split(",");
				String[] ln=lineName.split(",");
				String[] ss=stationID.split(",");
				String[] sn=stationName.split(",");
				
				if(isFromLine)
				{
					for(int z=0;z<ss.length;z++)
					{
						if(ss[z].equals(""))
							continue;
						
						AlarmInfo t=new AlarmInfo();
						t.setAdvance(alarmInfo.isAdvance());
						t.setCrowed(alarmInfo.isCrowed());
						t.setLineID(Integer.valueOf(lineID));
						t.setLineName(lineName);
						
						t.setMethod(alarmInfo.getMethod());
						t.setOpen(alarmInfo.isOpen());
						t.setPlanUpCar(alarmInfo.getPlanUpCar());
						
						t.setStationID(Integer.valueOf(ss[z]));
						if(sn[z]!=null)
							t.setStationName(sn[z]);
						
						t.setTime(alarmInfo.getTime());
						t.setUp(alarmInfo.isUp());
						t.setWeeks(alarmInfo.getWeeks());
						t.setWeekSelect(alarmInfo.getWeekSelect());
						
						dbOperate.insertAlarm(alarmInfo.isUp()?contentType[0]:contentType[1], t);
					}
				}
				else
				{
					for(int z=0;z<ls.length;z++)
					{
						if(ls[z].equals(""))
							continue;
						
						AlarmInfo t=new AlarmInfo();
						t.setAdvance(alarmInfo.isAdvance());
						t.setCrowed(alarmInfo.isCrowed());
						t.setLineID(Integer.valueOf(ls[z]));
						if(ln[z]==null)
							t.setLineName("");
						else
							t.setLineName(ln[z]);
						t.setMethod(alarmInfo.getMethod());
						t.setOpen(alarmInfo.isOpen());
						t.setPlanUpCar(alarmInfo.getPlanUpCar());
						t.setStationID(Integer.valueOf(stationID));
						t.setStationName(stationName);
						t.setTime(alarmInfo.getTime());
						t.setUp(alarmInfo.isUp());
						t.setWeeks(alarmInfo.getWeeks());
						t.setWeekSelect(alarmInfo.getWeekSelect());
						
						dbOperate.insertAlarm(alarmInfo.isUp()?contentType[0]:contentType[1], t);
					}
				}
				
//				dbOperate.insertAlarm(alarmInfo.isUp()?contentType[0]:contentType[1], alarmInfo);
				dbOperate.CloseDB();
				StartIntent.startIntent(this, AlarmFavorActivity.class);
				this.finish();
				break;
				
			case R.id.alarm_up_cancel:
				this.finish();
				break;
				
			case R.id.alarm_up_advance_date_0:
				select(R.id.alarm_up_advance_date_0, 0);
				break;
				
			case R.id.alarm_up_advance_date_1:
				select(R.id.alarm_up_advance_date_1, 1);
				break;
				
			case R.id.alarm_up_advance_date_2:
				select(R.id.alarm_up_advance_date_2, 2);
				break;
				
			case R.id.alarm_up_advance_date_3:
				select(R.id.alarm_up_advance_date_3, 3);
				break;
				
			case R.id.alarm_up_advance_date_4:
				select(R.id.alarm_up_advance_date_4, 4);
				break;
				
			case R.id.alarm_up_advance_date_5:
				select(R.id.alarm_up_advance_date_5, 5);
				break;
				
			case R.id.alarm_up_advance_date_6:
				select(R.id.alarm_up_advance_date_6, 6);
				break;
		}
	}
	
	private void select(int id,int num)
	{
		TextView textView=(TextView)findViewById(id);
		
		boolean[] temp=alarmInfo.getWeeks();
		temp[num]=!temp[num];
		
		alarmInfo.setWeeks(temp);
		
		if(alarmInfo.getWeeks()[num])
			textView.setBackgroundColor(getResources().getColor(R.color.history_color));
		else
			textView.setBackgroundColor(0xffa3a3a3);
			
	}
}
