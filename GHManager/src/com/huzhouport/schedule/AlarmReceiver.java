package com.huzhouport.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * ƒ÷÷”œ‡”¶
 * …Úµ§µ§
***/
public class AlarmReceiver extends BroadcastReceiver{ 
	  @Override 
	    public void onReceive(Context arg0, Intent arg1) {
	        Intent intent=new Intent(arg0,StartAlarm.class);
	        intent.putExtra("alarmType", arg1.getStringExtra("alarmType"));
	        intent.putExtra("alarmName", arg1.getStringExtra("alarmName"));
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        arg0.startActivity(intent);
	        
	    } 
	 
}
