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
	// 获取消息线程
	// private MessageThread messageThread = null;
	// 点击查看
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	// 通知栏消息
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
		// 初始化
		messageNotification = new Notification();
		messageNotification.icon = R.drawable.ic_launcher; // 通知图片
		messageNotification.tickerText = "新通知公告消息"; // 通知标题
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
		messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 点击查看
		// messageIntent = new Intent(this,KnowledgeNewView.class);
		// messagePendingIntent = PendingIntent.getActivity(this, 0,
		// messageIntent, 0);

		// 开启线程
		MessageThread thread = new MessageThread();
		thread.isRunning = true;
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/***
	 * 从服务端获取消息
	 * 
	 * @author zhanglei
	 * 
	 */
	class MessageThread extends Thread {
		// 运行状态
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
						// 设置消息内容和标题
						messageIntent = new Intent(getApplicationContext(),
								KnowledgeNewView.class);
						messageIntent.putExtra("departmentId", "-1");

						messagePendingIntent = PendingIntent.getActivity(
								getApplicationContext(), 0, messageIntent, 0);

						messageNotification.tickerText = "新通知公告消息"; // 通知标题
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "您有新的通知公告!", "请注意查收查看",
								messagePendingIntent);
						// 发布消息
						messageNotificationManager.notify(
								messageNotificationID, messageNotification);

						sendBroadCast(PushMsg.MODULERID_NOTIFY);
					}
					
					// ////////////////////////////////////////////////////////////////////
					//行业资讯不会用户，全为-1
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

						messageNotification.tickerText = "新行业资讯消息"; // 通知标题
						messageNotification.setLatestEventInfo(
								ServerPushService.this, "您有新的行业资讯消息!",
								"请注意查收查看", messagePendingIntent4);
						// 发布消息
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
