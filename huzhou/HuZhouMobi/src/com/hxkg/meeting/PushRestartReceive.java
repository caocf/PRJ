package com.hxkg.meeting;
import com.huzhouport.main.User;
import com.huzhouport.pushmsg.ServerPushService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PushRestartReceive extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 User user=(User) intent.getSerializableExtra("User");
	     Intent intent1=new Intent(context,ServerPushService.class);
	     //intent1.putExtra("User",user);
	     //intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     //context.startService(intent1);         
         
	}

}
