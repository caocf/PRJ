package com.huzhouport.schedule;

import java.util.HashMap;
import java.util.Map;
import com.huzhouport.common.util.HttpUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;

/**
 * ��ʼ���� �򵤵�
 ***/
public class StartAlarm extends Activity {
	MediaPlayer alarmMusic;
	private Vibrator vibrator = null;
	
	int eventId,userId;
	int alarmType;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		String alarmName = getIntent().getStringExtra("alarmName");
		alarmType =getIntent().getIntExtra("alarmType",1);
		eventId =getIntent().getIntExtra("eventid",1);
		userId =getIntent().getIntExtra("userid",1);
		
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		//alarmMusic = MediaPlayer.create(this, R.raw.alarm);
		alarmMusic = MediaPlayer.create(this, RingtoneManager.getActualDefaultRingtoneUri(this,  
                RingtoneManager.TYPE_RINGTONE));
		alarmMusic.setLooping(true);
		 /* try {  
			  alarmMusic.prepare();  
	        } catch (IllegalStateException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } */
		
		// ��������
		switch (alarmType) {
		case 1:
			alarmMusic.start();

			break;
		case 2:
			vibrator.vibrate(new long[] { 500, 500, 500, 500, 500 }, 1);
			break;
		case 3:
			alarmMusic.start();
			vibrator.vibrate(new long[] { 500, 500, 500, 500, 50 }, 1);
			break;
		default:
			break;
		}

		// ����һ���Ի���
		new AlertDialog.Builder(StartAlarm.this).setTitle("��������")
				.setMessage(alarmName)
				.setPositiveButton("ȷ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ֹͣ����
						alarmMusic.stop();
						vibrator.cancel();
						// ������Activity
						StartAlarm.this.finish();
					}
				})
				.setNegativeButton("�ر�����", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						alarmMusic.stop();
						vibrator.cancel();
						new UploadDate().execute();
						
					}
				})
				.setCancelable(false)
				.show()
				;
	}
	class UploadDate extends AsyncTask<Void, Void, String> 
	{
		@Override
		protected String doInBackground(Void... params) 
		{
			Map<String, Object> paramsDtae=new HashMap<String, Object>();
			paramsDtae.put("scheduleEventUser.eventId", eventId);
			paramsDtae.put("scheduleEventUser.userId", userId);
		
			paramsDtae.put("scheduleEventUser.eventRemind", 0);
			
			//UpdateScheduleClock(Integer.parseInt(eventId),EventTime,valueOfRemind,EventName,toastNum);
			String actionUrl = HttpUtil.BASE_URL + 
					"upDateRemind.action?scheduleEventUser.eventId="+eventId+
					"&scheduleEventUser.userId="+userId+"&scheduleEventUser.eventRemind=0"+"&scheduleEventUser.eventRemindType="+alarmType;
			//http://192.168.1.135:6080/HuZhouPort/
			/*HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			String result = null;		
			try {
				result = hfu.post(actionUrl, paramsDtae);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;*/
			//UploadActivity.tool.addFile("��������",actionUrl, paramsDtae,null, null);
			HttpUtil.queryStringForPost(actionUrl);
			return null;
		}

		@Override
		protected void onPostExecute(String result) 
		{
			
			finish();
			super.onPostExecute(result);
		}

	}
}
