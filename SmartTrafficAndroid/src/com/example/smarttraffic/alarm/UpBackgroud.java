package com.example.smarttraffic.alarm;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.network.HttpClient;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.smartBus.StationRealActivity;
import com.example.smarttraffic.smartBus.bean.BusArrive;
import com.example.smarttraffic.smartBus.bean.BusLocation;
import com.example.smarttraffic.util.DoString;

public class UpBackgroud
{
	private List<AlarmInfo> data;
	private Context context;
	
	public static final int MSG=1001;
	
//	public Map<Integer,Integer> count=new HashMap<Integer, Integer>();			//统计
	
	public void setData(List<AlarmInfo> data)
	{
		this.data = data;
	}
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case MSG:
					AlarmInfo alarmInfo=(AlarmInfo)msg.obj;
					parse(alarmInfo.getData(), alarmInfo);
					break;

			}
		}
		
		public void parse(String d,AlarmInfo alarmInfo)
		{
			try
			{			
				System.out.println(d);
				
				BusArrive busArrive=JSON.parseObject(DoString.parseThreeNetString(d),BusArrive.class);
				int i=busArrive.getBusLocationList().get(0).getStationIndex();
				int l=busArrive.getBusLocationList().get(0).getDistance();
				
				System.out.println("方式："+alarmInfo.getMethod());
				System.out.println("时间："+alarmInfo.getTime());
				System.out.println("站点距："+i);
				System.out.println("距离："+l);
				
				if(alarmInfo.getMethod()==0)
				{
					if(alarmInfo.getTime()>=i)
					{
						notifyUser(busArrive.getBusLocationList().get(0),alarmInfo);
						
						FavorDBOperate favorDBOperate=new FavorDBOperate(context);
						alarmInfo.setOpen(false);
						alarmInfo.setData("");
						favorDBOperate.update(alarmInfo.getId(), JSON.toJSONString(alarmInfo));
						favorDBOperate.CloseDB();
					}
				}
				else if (alarmInfo.getMethod()==1) 
				{
					
				}
				else 
				{
					if(alarmInfo.getTime()>l)
					{
						notifyUser(busArrive.getBusLocationList().get(0),alarmInfo);
						
						FavorDBOperate favorDBOperate=new FavorDBOperate(context);
						alarmInfo.setOpen(false);
						alarmInfo.setData("");
						favorDBOperate.update(alarmInfo.getId(), JSON.toJSONString(alarmInfo));
						favorDBOperate.CloseDB();
					}
				}
			} 
			catch (Exception e)
			{
			}
			
			
		}
		
		@SuppressWarnings("deprecation")
		public void notifyUser(BusLocation busLocation,AlarmInfo alarmInfo)
		{
			Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
			long [] pattern = {100,400,100,400}; 
	        vibrator.vibrate(pattern,-1);
	        
	        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);               
	        Notification n = new Notification(R.drawable.app_icon, "上车提醒", System.currentTimeMillis());             
	        n.flags = Notification.FLAG_AUTO_CANCEL;     
	        
	        Bundle bundle=new Bundle();
			bundle.putInt(StationRealActivity.STATION_REAL_LINE_ID,alarmInfo.getLineID());
			bundle.putInt(StationRealActivity.STATION_REAL_STARIONT_ID, alarmInfo.getStationID());
			bundle.putString(StationRealActivity.STATION_REAL_STARIONT_NAME, alarmInfo.getLineName());
			bundle.putString(StationRealActivity.STATION_REAL_LINE_NAME,alarmInfo.getStationName());

	        Intent i = new Intent(context, StationRealActivity.class);
	        i.putExtras(bundle);
	        
	        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
	        //PendingIntent
	        PendingIntent contentIntent = PendingIntent.getActivity(
	                context, 
	                R.string.app_name, 
	                i, 
	                PendingIntent.FLAG_UPDATE_CURRENT);
	             
	        String info="线路"+busLocation.getCarNumber()+" 当前站点:"+busLocation.getStationName()
	                +" 距站点:"+alarmInfo.getStationName()+"，还有"+busLocation.getStationIndex()+"站"+" 还有"+busLocation.getDistance()+"米"
	                +" 点击查看实时信息";
	        
	        n.setLatestEventInfo(
	                context,
	                "上车提醒", 
	                info, 
	                contentIntent);
	        nm.notify(R.string.app_name, n);
	        
	        System.out.println(info);
		}
		
	};
	
	public UpBackgroud(List<AlarmInfo> d,Context c)
	{
		this.data=d;
		this.context=c;
		
		System.out.println(d.size());
	}
	
	
	public void control(AlarmInfo alarmInfo)
	{
		new ThreadArrive(alarmInfo).start();
	}
	
	class ThreadArrive extends Thread
	{
		AlarmInfo alarmInfo;
		public ThreadArrive(AlarmInfo a)
		{
			this.alarmInfo=a;
		}
		
		@Override
		public void run()
		{		
			String d=HttpUrlRequestAddress.SMART_BUS_STATION_REAL_INFO_URL+"?stationID="+alarmInfo.getStationID()+"&lineID="+alarmInfo.getLineID()+"&direct="+1+"&count=3";
			
			System.out.println("reqest:"+d);
			
			d=HttpClient.queryStringForGet(d);
			
			System.out.println("response:"+d);
			
			alarmInfo.setData(d);
			Message message=new Message();
			message.what=MSG;
			message.obj=alarmInfo;
			
			handler.sendMessage(message);
		}
	}
	
	public void controlAll()
	{
		for(AlarmInfo a:data)
		{
			control(a);
		}
	}
	
	
}





//class alarmUpUpdate implements UpdateView
//{
//	public static int count_alarm=0;
//	
//	AlarmInfo alarmInfo;
//	Context context;
//	
//	public alarmUpUpdate(AlarmInfo a,Context c)
//	{
//		this.alarmInfo=a;
//		this.context=c;
//	}
//	
//	public void notifyUser(StationForReal stationForReal)
//	{
//		Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//		long [] pattern = {100,400,100,400}; 
//        vibrator.vibrate(pattern,-1);
//        
//        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);               
//        Notification n = new Notification(R.drawable.app_icon, "上车提醒", System.currentTimeMillis());             
//        n.flags = Notification.FLAG_AUTO_CANCEL;     
//        
//        Intent i = new Intent(context, StationRealActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
//        //PendingIntent
//        PendingIntent contentIntent = PendingIntent.getActivity(
//                context, 
//                R.string.app_name, 
//                i, 
//                PendingIntent.FLAG_UPDATE_CURRENT);
//                         
//        n.setLatestEventInfo(
//                context,
//                "上车提醒", 
//                "线路"+stationForReal.getLineName()+" 距站点"+stationForReal.getStationName()
//                +"，还有"+stationForReal.getRealInfos().get(0).getInterStationNum()+"站"
//                +" 点击查看实时信息", 
//                contentIntent);
//        nm.notify(R.string.app_name, n);
//        
//        
//        
//        System.out.println(count_alarm++);
//	}
//	
//	@Override
//	public void update(Object data)
//	{
//		StationForReal stationForReal=(StationForReal)data;
//		
//		if(alarmInfo.getMethod()==0)
//		{
//			if(stationForReal.getRealInfos().get(0).getInterStationNum()<alarmInfo.getTime())
//			{
//				notifyUser(stationForReal);
//			}
//		}
//		else if(alarmInfo.getMethod()==1)
//		{
//			
//		}
//		else if (alarmInfo.getMethod()==2) 
//		{
//			if(stationForReal.getRealInfos().get(0).getDistance()<alarmInfo.getTime())
//			{
//				notifyUser(stationForReal);
//			}
//		}
//	}
//}
//
//class alarmUpSearch extends BaseSearch
//{
//	@Override
//	public Object parse(String data)
//	{
//		StationForReal result=new StationForReal();
//		
//		try
//		{
//			JSONObject jsonObject=new JSONObject(data);
//			jsonObject=jsonObject.getJSONObject("realStation");
//			
//			result.setLineName(jsonObject.getString("lineName"));
//			result.setStationName(jsonObject.getString("stationName"));
//			
//			JSONArray array=jsonObject.getJSONArray("realInfos");
//			
//			List<RealInfo> list=new ArrayList<RealInfo>();
//			
//			for(int i=0;i<array.length();i++)
//			{
//				jsonObject=array.getJSONObject(i);
//				
//				RealInfo temInfo=new RealInfo();
//				temInfo.setCrowed(jsonObject.getInt("crowed"));
//				temInfo.setDistance(jsonObject.getDouble("distance"));
//				temInfo.setInterStationNum(jsonObject.getInt("interStationNum"));
//				temInfo.setNum(jsonObject.getInt("num"));
//				temInfo.setSpeed(jsonObject.getInt("speed"));
//				
//				list.add(temInfo);
//			}
//			
//			result.setRealInfos(list);
//			
//		} catch (JSONException e)
//		{
//			e.printStackTrace();
//		}			
//		
//		return result;
//	}
//}
//
//class alarmUpRequest extends BaseRequest
//{
//	private int stationID;
//	private int lineID;
//	private int orient;
//	
//	public alarmUpRequest(int s,int l)
//	{
//		this.stationID=s;
//		this.lineID=l;
//	}
//	
//	@Override
//	public String CreateUrl()
//	{
//		return HttpUrlRequestAddress.SMART_BUS_STATION_REAL_INFO_URL+"?stationID"+stationID+"&lineID="+lineID+"&direct="+orient;
//	}
//}
