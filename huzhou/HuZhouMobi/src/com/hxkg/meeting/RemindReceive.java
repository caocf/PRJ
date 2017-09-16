package com.hxkg.meeting;

import com.example.huzhouport.R;
import com.huzhouport.schedule.StartAlarm;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RemindReceive extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 /*Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);   
         vib.vibrate(new long[]{0,1000,500,1000},-1); */         
         
         Intent intent1=new Intent(context,StartAlarm.class);
         intent1.putExtra("alarmType", intent.getIntExtra("alarmType",1));
         intent1.putExtra("alarmName", intent.getStringExtra("alarmName"));
         intent1.putExtra("userid", intent.getIntExtra("userid",0));
         intent1.putExtra("eventid", intent.getIntExtra("eventid",0));
         intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(intent1);         
         
         /*Intent messageIntent4 = new Intent();
         //messageIntent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         PendingIntent messagePendingIntent4 = PendingIntent.getActivity(context.getApplicationContext(), 0,
							messageIntent4,
							PendingIntent.FLAG_UPDATE_CURRENT);
			Notification messageNotification= new Notification();			
			messageNotification.icon = R.drawable.ic_launcher; // 通知图片
			messageNotification.defaults = Notification.DEFAULT_SOUND;
			messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
			NotificationManager messageNotificationManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);			
			messageNotification.tickerText = "新会议安排消息"; // 通知标题
			messageNotification.setLatestEventInfo(
					context, "您有新的会议安排!", "请注意查收查看",
					messagePendingIntent4);
			// 发布消息
			messageNotificationManager.notify(1005, messageNotification);*/
	}

}
