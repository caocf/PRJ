package com.huzhouport.pushmsg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User; 
import com.huzhouport.schedule.ScheduleItemTime;
import com.hxkg.meeting.RemindReceive;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ServerPushService extends Service 
{
	// ����鿴
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	// ֪ͨ����Ϣ
	private int messageNotificationID = 1000;

	private Notification messageNotification = null;
	private NotificationManager messageNotificationManager = null;
	private User user = null;
	Query query = new Query();
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Map<String,PendingIntent> pilist=new HashMap<String, PendingIntent>();	

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		if (intent != null) 
		{
			user = (User) intent.getSerializableExtra("User");
			// ��ʼ��
			messageNotification = new Notification();
			messageNotification.icon = R.drawable.ic_launcher; // ֪ͨͼƬ
			messageNotification.defaults = Notification.DEFAULT_SOUND;
			messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
			messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			// �����߳�
			MessageThread thread = new MessageThread();
			thread.isRunning = true;
			//thread.start();			
		}      
		 
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void onDestroy()
	{
		super.onDestroy();
	}

	class MessageThread extends Thread 
	{
		// ����״̬
		public boolean isRunning = true;

		public void sendBroadCast(int moduler) 
		{
			Intent intent = new Intent(GlobalVar.PUSHMSGBROADCAST);
			intent.putExtra("moduler", moduler);
			ServerPushService.this.sendBroadcast(intent);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() 
		{
			while (isRunning) 
			{
				try 
				{
					/*int notifymsgcnt = new NotifyPushMsgManger(
							ServerPushService.this)
							.getUserUnPushedUnreadNotifyCnt(user);
					// ֪ͨ����
					if (notifymsgcnt > 0) 
					{
						// ������Ϣ���ݺͱ���
						messageIntent = new Intent(getApplicationContext(),
								KnowledgeNewView.class);
						messageIntent.putExtra("departmentId", "-1");
						messageIntent.putExtra("User", user);

						messagePendingIntent = PendingIntent.getActivity(
								getApplicationContext(), 0, messageIntent,
								PendingIntent.FLAG_UPDATE_CURRENT);

						messageNotification.tickerText = "��֪ͨ������Ϣ"; // ֪ͨ����
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µ�֪ͨ����!", "��ע����ղ鿴",
								messagePendingIntent);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID, messageNotification);

						sendBroadCast(PushMsg.MODULERID_NOTIFY);
					}				
					
					int meetingmsgcnt =new MeetingPushMsgManager().getUnPushedMsg(user.getUserId());
					if (meetingmsgcnt > 0) 
					{
						Intent messageIntent4 = new Intent(ServerPushService.this, ScheduleItemTime.class);

						messageIntent4.putExtra("User", user);

						PendingIntent messagePendingIntent4 = PendingIntent
								.getActivity(getApplicationContext(), 0,
										messageIntent4,
										PendingIntent.FLAG_UPDATE_CURRENT);

						messageNotification.tickerText = "�¹�����Ϣ"; // ֪ͨ����
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µĹ�����Ϣ!", "��ע����ղ鿴",
								messagePendingIntent4);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID + 4, messageNotification);
						sendBroadCast(PushMsg.MODULERID_MEETING);
					}
					
					*/

					
					// ////////////////////////////////////////////////////////////////////
					/*int illegalcnt = 0;
					if (user != null)
						illegalcnt = new IllegalPushMsgManager()
								.getUnPushedMsg(user.getUserId());
					if (illegalcnt > 0) 
					{
						Intent messageIntent4 = new Intent(
								ServerPushService.this,
								IllegalCheckSearch.class);

						messageIntent4.putExtra("User", user);

						PendingIntent messagePendingIntent4 = PendingIntent
								.getActivity(getApplicationContext(), 0,
										messageIntent4,
										PendingIntent.FLAG_UPDATE_CURRENT);

						messageNotification.tickerText = "��Υ�������Ϣ"; // ֪ͨ����
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µ�Υ����Ϣ��Ҫ���!",
								"��ע����ղ鿴", messagePendingIntent4);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID + 4, messageNotification);
						sendBroadCast(PushMsg.MODULERID_ILLEGAL);
					}*/					
					
					// ////////////////////////////////////////////////////////////////////
					//��ҵ��Ѷ�����û���ȫΪ-1
					/*int industryinfocnt = 0;
					industryinfocnt = new IndustryInfoPushMsgManager(ServerPushService.this
								).getUnPushedMsg(-1);
					if (industryinfocnt > 0) 
					{
						Intent messageIntent4 = new Intent(
								ServerPushService.this,
								IndustryInfoActivity.class);

						messageIntent4.putExtra("User", user);

						PendingIntent messagePendingIntent4 = PendingIntent
								.getActivity(getApplicationContext(), 0,
										messageIntent4,
										PendingIntent.FLAG_UPDATE_CURRENT);

						messageNotification.tickerText = "����ҵ��Ѷ��Ϣ"; // ֪ͨ����
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µ���ҵ��Ѷ��Ϣ!",
								"��ע����ղ鿴", messagePendingIntent4);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID + 4, messageNotification);
						
						sendBroadCast(PushMsg.MODULERID_INDUSTRYINFO);
					}
					int laomsgcnt = 0;
					// ��ټӰ�,��Ҫ�ѵ�¼
					if (user != null)
						laomsgcnt = new LAOPushMsgManager().getUnPushedMsg(user
								.getUserId());

					if (laomsgcnt > 0) 
					{
						// ������Ϣ���ݺͱ���
						Intent messageIntent3 = new Intent(
								ServerPushService.this,
								leaveandovertimeNewListMain.class);

						messageIntent3.putExtra("User", user);

						PendingIntent messagePendingIntent3 = PendingIntent
								.getActivity(getApplicationContext(), 0,
										messageIntent3,
										PendingIntent.FLAG_UPDATE_CURRENT);

						messageNotification.tickerText = "�����������Ϣ"; // ֪ͨ����

						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µ���ټӰ�������Ϣ!",
								"��ע����ղ鿴", messagePendingIntent3);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID + 3, messageNotification);

						sendBroadCast(PushMsg.MODULERID_LEAVEANDOVERTIME);
					}*/

					Thread.sleep(GlobalVar.PUSHTIMER * 1000 * 10);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

}
