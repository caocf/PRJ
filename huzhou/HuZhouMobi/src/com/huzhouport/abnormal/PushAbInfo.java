package com.huzhouport.abnormal;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
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
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.car.CarMyApActivity;
import com.huzhouport.car.CarMyCheckActivity;
import com.huzhouport.common.util.Query;
import com.huzhouport.knowledge.KnowledgeNewView;
import com.huzhouport.main.User;
import com.huzhouport.schedule.ScheduleItemTime;
import com.hxkg.meeting.MeetingRoomMyApActivity;
import com.hxkg.meeting.MeetingRoomMyCheckActivity;
import com.hxkg.meeting.RemindReceive;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;

public class PushAbInfo extends Service
{
	public static String ACTION="GHPush";
	private static XMPPConnection connection;
	private User user;
	SQLiteDatabase db;
	int startId;
	XMPPConnector xConnector=null;
	Query query = new Query();
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Map<String,PendingIntent> pilist=new HashMap<String, PendingIntent>();
	
	@Override
	public void onCreate()
	{
		System.out.println("PushAbInfo_onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, final int startId) 
	{
		this.startId=startId;
		
		new TestThread(startId).start();

		System.out.println("PushAbInfo_onStartCommand_flags:"+flags+"_startId:"+startId);
		
		if(intent!=null)
		{
			 user=(User) intent.getSerializableExtra("User");	System.out.println(user);
			 
			 if(xConnector==null||!xConnector.isAlive())
			 {
				 xConnector=new XMPPConnector(user.getName(),"123456");
				 xConnector.start();
			 }		 
			 	
			 if(db!=null &&db.isOpen())return 3;
			 db=this.openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
		}
		 
		return 3;//Service.START_REDELIVER_INTENT,系统用上次的状态和数据重启
	}
	
	private class TestThread extends Thread
	{
		int startId;
		public  TestThread(int startId) {
			this.startId=startId;
		}
		
		@Override
		public void run() 
		{
			while (true){
				System.out.println("推送服务运行中 ID："+startId+"_"+this.getId());
				try 
				{
					Thread.sleep(5000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				}
			
		}
		
	}
	
	 
	 private class XMPPConnector extends Thread
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
			 ConnectionConfiguration configuration = new ConnectionConfiguration("120.55.100.184", 5222, "120.55.100.184");
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
				e.printStackTrace();
			} catch (IOException e) 
			{
				e.printStackTrace();
			} catch (XMPPException e) 
			{
				e.printStackTrace();
				
				AccountManager accountm=AccountManager.getInstance(connection);
				try 
				{
					accountm.createAccount(name, psw);
					connection.disconnect();//先断掉以后再重新登录
					loginORregister(name,psw);					
					action();
				} catch (NoResponseException e1) {
					
					e1.printStackTrace();
				} catch (XMPPErrorException e1) {
					
					e1.printStackTrace();
				} catch (NotConnectedException e1) {
				
					e1.printStackTrace();
				}
				catch (SmackException e1) 
				{
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
			 MultiUserChat muc=new MultiUserChat(connection,"hzgh@conference.120.55.100.184");
			 DiscussionHistory ds=new DiscussionHistory();
			 //ds.setSeconds(0);//最近xx秒内的信息
			 if(muc.isJoined())return;
			 ds.setMaxStanzas(0);
			 muc.join(user.getName(), "123456", ds,100000);
			 muc.addMessageListener(new PacketListener()
			 {
				@Override
				public void processPacket(Packet p)throws NotConnectedException
				{
					Message ms=(Message)p;
					String s=ms.getBody();
					if(s==null) return;
					
					SharedPreferences sp=PushAbInfo.this.getSharedPreferences("ABNORMAL", 4);
					
					String s3=sp.getString("potname", "请选择站点");	
					boolean offon=sp.getBoolean("offon", false);
					ActivityManager am = (ActivityManager) PushAbInfo.this.getSystemService(Context.ACTIVITY_SERVICE);
					List<RunningTaskInfo> list = am.getRunningTasks(1);
					
					if(s.equals(s3)&&offon)
					{							
						if (list != null && list.size() > 0)
						{
							String cpn = list.get(0).topActivity.getClassName();								
							if("com.huzhouport.abnormal.AbNormalList".equals(cpn))//在前台
							{
								//直接刷新
								//AbNormalList.fragmentOn.NewPotFresh(s);
								BroadCast();
								Intent intent=new Intent();
								Notify(1,intent,1,"您有异常报警","异常报警",s+"有新的异常情况");
							}
							else //已关闭
							{
								Intent intent=new Intent(PushAbInfo.this,AbNormalList.class);
								Notify(1,intent,1,"您有异常报警","异常报警",s+"有新的异常情况");
							}
						}							
					}
					else //消息推送
					{
						try 
						{
							JSONObject object=new JSONObject(s.trim());
							JSONArray array=object.getJSONArray("ids");
							for(int i=0;i<array.length();i++)
							{
								int id=array.getInt(i);									
								if(user.getUserId()==id)
								{
									String title=object.getString("title");
									String tip=object.getString("tip");																				
									int mstype=(int) object.get("mstype");
									
									switch (mstype)
									{
									case 1://会议申请
										Intent intentm=new Intent();
										int typem=(int) object.get("type");
										if(typem==1)//申请者消息
										{
											if(!isFront("com.hxkg.meeting.MeetingRoomMyApActivity"))
											{
												intentm=new Intent(PushAbInfo.this,MeetingRoomMyApActivity.class);
												intentm.putExtra("User", user);
											}
										}else //审核者消息
										{
											if(!isFront("com.hxkg.meeting.MeetingRoomMyCheckActivity"))
											{
												intentm=new Intent(PushAbInfo.this,MeetingRoomMyCheckActivity.class);
												intentm.putExtra("User", user);
											}
										}										
										
										Notify(1,intentm,1,title,title,tip);
										
										break;
									case 2://用车申请
										Intent intentc=new Intent();
										int typec=(int) object.get("type");
										if(typec==1)//申请者消息
										{
											if(!isFront("com.huzhouport.car.CarMyApActivity"))
											{
												intentm=new Intent(PushAbInfo.this,CarMyApActivity.class);
												intentm.putExtra("User", user);
											}
										}else //审核者消息
										{
											if(!isFront("com.huzhouport.car.CarMyCheckActivity"))
											{
												intentm=new Intent(PushAbInfo.this,CarMyCheckActivity.class);
												intentm.putExtra("User", user);
											}
										}										
										
										Notify(1,intentc,1,title,title,tip);
										/*Calendar calendar=Calendar.getInstance();
										calendar.set(Calendar.HOUR_OF_DAY, 0);
										calendar.set(Calendar.MINUTE, 0);
										calendar.set(Calendar.SECOND, 0);
										calendar.set(Calendar.MILLISECOND, 0);
										if(mstype==1)
										{
											db.execSQL("REPLACE INTO meetingrecord VALUES (?, ?,?,?,?)"
													,new Object[]{recordid,calendar.getTime(),status,type,id});
										}												
										else
										{
											db.execSQL("REPLACE INTO CarRecord VALUES (?, ?,?,?,?)"
													,new Object[]{recordid,calendar.getTime(),status,type,id});
										}*/
										break;
									case 3://通知公告
										Intent intent=new Intent();
										if(!isFront("com.huzhouport.knowledge.KnowledgeNewView"))
										{
											intent=new Intent(PushAbInfo.this,KnowledgeNewView.class);
											intent.putExtra("User", user);
											intent.putExtra("departmentId", "-1");
										}
										
										Notify(1,intent,1,title,title,tip);
										break;
									case 4://公务通知
										Intent intents=new Intent();
										if(!isFront("com.huzhouport.schedule.ScheduleItemTime"))
										{
											intents=new Intent(PushAbInfo.this,ScheduleItemTime.class);
											intents.putExtra("User", user);
											intents.putExtra("departmentId", "-1");
										}
										
										Notify(1,intents,1,title,title,tip);
										//设置提醒										
										JSONObject jsonObject1 = object.getJSONObject("scheduleEvent");
										JSONObject jsonObject2 = object.getJSONObject("scheduleEventUser");
										String eventTime=jsonObject1.getString("eventTime");
										int eventid=jsonObject1.getInt("eventId");
										String eventName=jsonObject1.getString("eventName");
										String eventRemind=jsonObject2.getString("eventRemind");
										int eventRemindType=jsonObject2.getInt("eventRemindType");	  						
										Date date=simpleDateFormat.parse(eventTime);
										long time=date.getTime();
										if(date.after(new Date())&&Integer.valueOf(eventRemind)>0)//满足提醒条件
										{										
											AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
								          Intent intent1 = new Intent(PushAbInfo.this, RemindReceive.class);
								          intent1.putExtra("alarmType", eventRemindType);
								          intent1.putExtra("alarmName", eventName);
								          intent1.putExtra("userid", user.getUserId());
								          intent1.putExtra("eventid", eventid);
								          PendingIntent pi = PendingIntent.getBroadcast(PushAbInfo.this, id, intent1, 0);									          
								          alarmManager.set(AlarmManager.RTC_WAKEUP, time-Integer.valueOf(eventRemind)*60*1000, pi);								         
																	          
										}
										break;

									default:
										break;
									}
																			
									break;
								}
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}							
						
					}					
				}					 
			 });			 
		 }
	 }
	 
	 private void setAlarm() 
	 {
		 String result1=query.queryScheduleByTimeInfo(simpleDateFormat.format(new Date()), user.getUserId());
			
		try
		{
			JSONTokener jsonParser = new JSONTokener(result1);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				String eventTime=jsonObject1.getString("eventTime");
				int id=jsonObject1.getInt("eventId");
				String eventName=jsonObject1.getString("eventName");
				String eventRemind=jsonObject2.getString("eventRemind");
				int eventRemindType=jsonObject2.getInt("eventRemindType");							
				Date date=simpleDateFormat.parse(eventTime);
				long time=date.getTime();
				if(date.after(new Date())&&Integer.valueOf(eventRemind)>0)//满足提醒条件
				{ 
					PendingIntent pp=pilist.get(eventTime+eventRemind+eventRemindType);
					if(pp==null)//从未设置过提醒
					{
						AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
				          Intent intent1 = new Intent(this, RemindReceive.class);
				          intent1.putExtra("alarmType", eventRemindType);
				          intent1.putExtra("alarmName", eventName);
				          intent1.putExtra("userid", user.getUserId());
				          intent1.putExtra("eventid", id);
				          PendingIntent pi = PendingIntent.getBroadcast(this, id, intent1, 0);
				          
				          am.set(AlarmManager.RTC_WAKEUP, time-Integer.valueOf(eventRemind)*60*1000, pi);
				          pilist.put(eventTime+eventRemind+eventRemindType, pi);
					}						          
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	 	
	 private boolean  isFront(String className) 
	 {
		 ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);  
	     List<RunningTaskInfo> list = am.getRunningTasks(15);
	     if(list==null||list.size()<=0)return false;
	     ComponentName cpn = list.get(0).topActivity; 
	     String string=cpn.getClassName();
	     System.out.println(cpn.getClassName()); 
         if (className.equals(string)) 
         {  
             return true;  
         }
	       
		return false;
		
	 }
	 
	 private void Notify(int ID,Intent intent,int requestcode,String smalltitle,String title,String content)
	 {
		 NotificationManager notifym=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		 Notification.Builder buider=new Notification.Builder(this);
		 buider.setSmallIcon(R.drawable.leave_img);
		 buider.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.drawer_logo));
		 buider.setTicker(smalltitle);
		 buider.setContentTitle(title);
		 buider.setContentText(content);
		 
		 PendingIntent pi=PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 buider.setContentIntent(pi);
		 buider.setDefaults(Notification.DEFAULT_SOUND);
		 Notification note=buider.getNotification();
		 notifym.notify(ID, note);
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
		System.out.println("PushAbInfo_onDestroy_startId:"+startId);
		new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				try 
				{
					connection.disconnect();
				} 
				catch (NotConnectedException e) 
				{
					e.printStackTrace();
				}
				
			}
		}).start(); 
		 
	 }
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}

}
