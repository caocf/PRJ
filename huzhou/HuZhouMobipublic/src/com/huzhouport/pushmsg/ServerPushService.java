package com.huzhouport.pushmsg;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.knowledge.KnowledgeNewView;
import com.huzhouport.model.User;
import com.huzhouport.portdynmicnews.IndustryInfoActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ServerPushService extends Service {

	public static String PUSHMSGBROADCAST = "com.huzhouport.pushmsg.broadcast";
	// ��ȡ��Ϣ�߳�
	// private MessageThread messageThread = null;
	// ����鿴
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	// ֪ͨ����Ϣ
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificationManager = null;
	private User user = new User();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			user = null;
		} else {
			user = (User) intent.getSerializableExtra("User");
			if (user == null) {
				System.out.println("Grond user==null");
				user = null;
			} else {
				System.out.println("Grond user is not null : "
						+ user.getUserId());
			}
		}
		// ��ʼ��
		messageNotification = new Notification();
		messageNotification.icon = R.drawable.ic_launcher; // ֪ͨͼƬ
		messageNotification.tickerText = "��֪ͨ������Ϣ"; // ֪ͨ����
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
		messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// ����鿴
		// messageIntent = new Intent(this,KnowledgeNewView.class);
		// messagePendingIntent = PendingIntent.getActivity(this, 0,
		// messageIntent, 0);

		// �����߳�
		MessageThread thread = new MessageThread();
		thread.isRunning = true;
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/***
	 * �ӷ���˻�ȡ��Ϣ
	 * 
	 * @author zhanglei
	 * 
	 */
	class MessageThread extends Thread {
		// ����״̬
		public boolean isRunning = true;

		public void sendBroadCast(int moduler) {
			Intent intent = new Intent(GlobalVar.PUSHMSGBROADCAST);
			intent.putExtra("moduler", moduler);
			ServerPushService.this.sendBroadcast(intent);
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			while (isRunning) {
				try {
					int notifymsgcnt = new NotifyPushMsgManger(
							getApplicationContext()).getUserUnPushedUnreadNotifyCnt(user);
					if (notifymsgcnt > 0) {
						// ������Ϣ���ݺͱ���
						messageIntent = new Intent(getApplicationContext(),
								KnowledgeNewView.class);
						messageIntent.putExtra("departmentId", "-1");

						messagePendingIntent = PendingIntent.getActivity(
								getApplicationContext(), 0, messageIntent, 0);

						messageNotification.tickerText = "��֪ͨ������Ϣ"; // ֪ͨ����
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "�����µ�֪ͨ����!", "��ע����ղ鿴",
								messagePendingIntent);
						// ������Ϣ
						messageNotificationManager.notify(
								messageNotificationID, messageNotification);

						sendBroadCast(PushMsg.MODULERID_NOTIFY);
					}
					
					// ////////////////////////////////////////////////////////////////////
					//��ҵ��Ѷ�����û���ȫΪ-1
					int industryinfocnt = 0;
					industryinfocnt = new IndustryInfoPushMsgManager(
								getApplicationContext()).getUnPushedMsg(-1);
					if (industryinfocnt > 0) {
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
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000 * 60 * 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
