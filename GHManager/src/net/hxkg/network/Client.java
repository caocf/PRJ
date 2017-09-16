package net.hxkg.network;

import java.net.URI;
import net.hxkg.ghmanager.R;
import net.hxkg.news.MainFramentNewsActivity;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class Client extends WebSocketClient 
{
	Context context;
	
	public Client(URI serverUri, Draft draft,Context context) 
	{
		super(serverUri, draft);
		this.context=context;
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) 
	{
		System.out.println("onClose");
		connect();
	}

	@Override
	public void onError(Exception arg0) 
	{
		System.out.println("onError");
		arg0.printStackTrace();
		
	}

	@Override 
	public void onMessage(String msg) 
	{
		System.out.println(msg);
		String[] ms=msg.split(",");
		
		String titleString=ms[1];
		
		Intent intent=new Intent(context,MainFramentNewsActivity.class);
		intent.putExtra("model", 1);
		Notify(1,intent,ms[0],"您有新的"+ms[0],titleString);
	}

	@Override
	public void onOpen(ServerHandshake arg0) 
	{
		System.out.println("onOpen");
		String userid=context.getSharedPreferences("User", 0).getString("userid", "");
		send("USER_"+String.valueOf(userid));
	}
	
	private void Notify(int ID,Intent intent,String smalltitle,String title,String content)
	 {
		 NotificationManager notifym=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		 Notification.Builder buider=new Notification.Builder(context);
		 buider.setSmallIcon(R.drawable.leave_img);
		 buider.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_logo));
		 buider.setTicker(smalltitle);
		 buider.setContentTitle(title);
		 buider.setContentText(content);
		 
		 PendingIntent pi=PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 buider.setContentIntent(pi);
		 buider.setDefaults(Notification.DEFAULT_SOUND);
		 Notification note=buider.getNotification();
		 notifym.notify(ID, note);
	 }

}
