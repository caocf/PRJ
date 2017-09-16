package com.huzhouport.cruiselog;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class TimeService extends Service implements Runnable {
	protected static final String ACTIONServiceTOActivity = "Service-Activity";// TimeService把时间变化告诉CruiselogAdd的广播
	protected static final String ACTIONActivityToService = "Activity-Service";// CruiselogAdd告知TimeService停止、开始、清零的广播
	private Handler handler;
	private Thread timecountThread; // 告诉handler时间递增的线程
	private boolean flagIfContinnute; // 接收timecountThread线程的标志
	// private boolean timeAdd; //handler执行时间递增的信息标志
	// 时间数据
	private int hour;
	private int min;
	private int sec;

	private Intent intentSendTime; // 传递时间数据的Intent @Override
	private ServiceReceiver serviceReceiver;
	 private IntentFilter filter;
	 private SharedPreferences oSharedPreferences;
	@Override
	public void onCreate() {
		super.onCreate();
		  serviceReceiver=new ServiceReceiver();   //初始化广播
		  filter=new IntentFilter();
		  filter.addAction(ACTIONActivityToService);
		  registerReceiver(serviceReceiver, filter);   //注册广播
		   
		// 初始化变量
		flagIfContinnute = false;
		
		 oSharedPreferences = getSharedPreferences(
				"TimeService", 0);
		hour = Integer.valueOf(oSharedPreferences.getString("hour", "0"));
		min =Integer.valueOf(oSharedPreferences.getString("min", "0"));
		sec = Integer.valueOf(oSharedPreferences.getString("sec", "0"));
		intentSendTime = new Intent();

		//GlobalVar.isexceptionexit=true; //异常结束	
		// 接收线程的信息实现时间递增
		handler = new Handler() {
			public void handleMessage(Message msg) {
				// timeAdd = msg.getData().getBoolean("timeadd");
				// 模拟时间变化规律 if(timeAdd)
				{
					if (sec + 1 == 60) {
						if (min + 1 == 60) {
							hour++;
							min = 0;
						} else {
							min++;
						}
						sec = 0;
					} else {
						sec++;
					}
				}

				intentSendTime.putExtra("hour", hour);
				intentSendTime.putExtra("min", min);
				intentSendTime.putExtra("sec", sec);

				
				Editor oEditor = getSharedPreferences(
						"TimeService", 0).edit();
				oEditor.putString("hour", hour+"");
				oEditor.putString("min", min+"");
				oEditor.putString("sec", sec+"");
				oEditor.commit();

				intentSendTime.setAction(ACTIONServiceTOActivity);
				sendBroadcast(intentSendTime);
			}
		};
		timecountThread = new Thread(this);
		timecountThread.start();
		
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(serviceReceiver);
			Editor oEditor = getSharedPreferences(
					"TimeService", 0).edit();
			oEditor.clear();
			oEditor.putBoolean("isexceptionexit", true);//异常结束
			oEditor.putString("hour", "0");
			oEditor.putString("min", "0");
			oEditor.putString("sec", "0");
			oEditor.commit();
		flagIfContinnute = false;
		super.onDestroy();
	}

	@Override
	public void run() {
		flagIfContinnute = true;
		while (flagIfContinnute) {
			Message msg = handler.obtainMessage();// 每一次都必须新建msg！
			Bundle data = new Bundle();
			data.putBoolean("timeadd", true);
			msg.setData(data);
			handler.sendMessage(msg);
			try {
				Thread.sleep(1000);// 每隔一秒钟告诉handler时间递增1
				System.out.println("秒=1秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 class ServiceReceiver extends BroadcastReceiver
	 {
	  @Override
	  public void onReceive(Context context, Intent intent)
	  {
	  
	  }
	 }
}
