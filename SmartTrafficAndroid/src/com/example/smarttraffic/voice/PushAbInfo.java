package com.example.smarttraffic.voice;
import java.io.IOException;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PushAbInfo extends Service
{
	private static String ACTION="GHPush";
	private static XMPPConnection connection;
	
		 
	 @Override
	 public int onStartCommand(Intent intent, int flags, int startId) 
	 {	 
		 super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
		 TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		 String imei = mTm.getDeviceId();
			 new XMPPConnector(imei,"123456").start();
	

		 
		 
		
		 
		 //return 	 
		 return START_REDELIVER_INTENT;
	 }

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}	
	 
	 public class XMPPConnector extends Thread
	 {
		String nameString="";
		String psw=""; 
		 public XMPPConnector(String nameString,String psw)
		{
			 this.nameString=nameString;
			 this.psw=psw;
		}
		 @Override
		public void run()
		{
			 ConnectionConfiguration configuration = new ConnectionConfiguration("115.231.73.254", 5222, "115.231.73.254");
			 configuration.setReconnectionAllowed(true);
			 configuration.setSecurityMode(SecurityMode.disabled);
					
			 connection= new XMPPTCPConnection(configuration);
			 
			 loginORregister(nameString,psw);
		}
		 
		private void loginORregister(String name,String psw)
		{			 
			try 
			{
				connection.connect();
				connection.login(name, psw);
				action();
			} catch (SmackException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMPPException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				AccountManager accountm=AccountManager.getInstance(connection);
				 try 
				 {
					accountm.createAccount(name, psw);
					connection.disconnect();//�ȶϵ��Ժ������µ�¼
					loginORregister(name,psw);					
					action();
				} catch (NoResponseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (XMPPErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotConnectedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 catch (SmackException e1) 
				{
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (XMPPException e1)
				{
						
				} catch (IOException e4) 
				{
					
				}
			}
		}
		 
		private void action() throws SmackException,IOException,XMPPException ,NoResponseException,NotConnectedException
		 							  													,XMPPErrorException,SaslException
		 {			 
			 
			 /*
			 
			 ChatManager cm=ChatManager .getInstanceFor(connection);
			 cm.addChatListener(new ChatManagerListener()
			 {

				@Override
				public void chatCreated(Chat chat, boolean arg1) 
				{
					if(chat!=null)
					{
						Log.e("dd","mjiji");
						chat.addMessageListener(new MessageListener()
						{

							@Override
							public void processMessage(Chat arg0,Message msg) 
							{
								Log.e("dd", msg.getBody());								
							}							
						});
					}					
				}				 
			 });*/	
			 
			 MultiUserChat muc=new MultiUserChat(connection,"hxt@conference.115.231.73.254");
			 DiscussionHistory ds=new DiscussionHistory();
			 //ds.setSeconds(0);//���xx���ڵ���Ϣ
			 ds.setMaxStanzas(0);
			 muc.join(Build.MODEL, "123456", ds,100000);
			 muc.addMessageListener(new PacketListener()
			 {
				@Override
				public void processPacket(Packet p)throws NotConnectedException
				{
					Message ms=(Message)p;
					String s=ms.getBody();
					if(s!=null)
					{
						if(StringMatch.isStringReg(s, StringMatch.NUMBER_STRING))
						{
							int n=Integer.valueOf(s);
							if(n>5&&n<90)
							{
								SharedPreferences sp=PushAbInfo.this.getSharedPreferences("speed", 0);
								SharedPreferences.Editor editor=sp.edit();
								editor.putInt("s", Integer.valueOf(s));
								editor.commit();
							}						
						}
					}					
				}					 
			 });			 
		 }
	 }
	 
	 private void Notify(int ID,Intent intent,int requestcode,String smalltitle,String title,String content)
	 {
		 /*NotificationManager notifym=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		 Notification.Builder buider=new Notification.Builder(this);
		 //buider.setSmallIcon(R.drawable.leave_img);
		 //buider.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.drawer_logo));
		 buider.setTicker(smalltitle);
		 buider.setContentTitle(title);
		 buider.setContentText(content);
		 
		 PendingIntent pi=PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 buider.setContentIntent(pi);
		 buider.setDefaults(Notification.DEFAULT_SOUND);
		 Notification note=buider.getNotification();
		 notifym.notify(ID, note);*/
	 }
	 
	 private void BroadCast()
	 {
		 Intent intentBroadCast=new Intent();
		 intentBroadCast.setAction(ACTION);
		 intentBroadCast.putExtra("test", "test!!!!!");
		 this.sendBroadcast(intentBroadCast);
	 }
	 
	 @Override
	 public void onDestroy() 
	 {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					connection.disconnect();
				} catch (NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start(); 
		 
	 }
	

}
