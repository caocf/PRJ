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
	protected static final String ACTIONServiceTOActivity = "Service-Activity";// TimeService��ʱ��仯����CruiselogAdd�Ĺ㲥
	protected static final String ACTIONActivityToService = "Activity-Service";// CruiselogAdd��֪TimeServiceֹͣ����ʼ������Ĺ㲥
	private Handler handler;
	private Thread timecountThread; // ����handlerʱ��������߳�
	private boolean flagIfContinnute; // ����timecountThread�̵߳ı�־
	// private boolean timeAdd; //handlerִ��ʱ���������Ϣ��־
	// ʱ������
	private int hour;
	private int min;
	private int sec;

	private Intent intentSendTime; // ����ʱ�����ݵ�Intent @Override
	private ServiceReceiver serviceReceiver;
	 private IntentFilter filter;
	 private SharedPreferences oSharedPreferences;
	@Override
	public void onCreate() {
		super.onCreate();
		  serviceReceiver=new ServiceReceiver();   //��ʼ���㲥
		  filter=new IntentFilter();
		  filter.addAction(ACTIONActivityToService);
		  registerReceiver(serviceReceiver, filter);   //ע��㲥
		   
		// ��ʼ������
		flagIfContinnute = false;
		
		 oSharedPreferences = getSharedPreferences(
				"TimeService", 0);
		hour = Integer.valueOf(oSharedPreferences.getString("hour", "0"));
		min =Integer.valueOf(oSharedPreferences.getString("min", "0"));
		sec = Integer.valueOf(oSharedPreferences.getString("sec", "0"));
		intentSendTime = new Intent();

		//GlobalVar.isexceptionexit=true; //�쳣����	
		// �����̵߳���Ϣʵ��ʱ�����
		handler = new Handler() {
			public void handleMessage(Message msg) {
				// timeAdd = msg.getData().getBoolean("timeadd");
				// ģ��ʱ��仯���� if(timeAdd)
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
			oEditor.putBoolean("isexceptionexit", true);//�쳣����
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
			Message msg = handler.obtainMessage();// ÿһ�ζ������½�msg��
			Bundle data = new Bundle();
			data.putBoolean("timeadd", true);
			msg.setData(data);
			handler.sendMessage(msg);
			try {
				Thread.sleep(1000);// ÿ��һ���Ӹ���handlerʱ�����1
				System.out.println("��=1��");
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
