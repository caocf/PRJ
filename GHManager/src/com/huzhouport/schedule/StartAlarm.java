package com.huzhouport.schedule;


import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

/**
 * ��ʼ���� �򵤵�
 ***/
public class StartAlarm extends Activity {
	MediaPlayer alarmMusic;
	private Vibrator vibrator = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		// ����ָ�����֣���Ϊ֮����MediaPlayer����
		int alarmType = Integer.parseInt(getIntent()
				.getStringExtra("alarmType"));
		String alarmName = getIntent().getStringExtra("alarmName");
		alarmMusic = MediaPlayer.create(this, R.raw.alarm);
		alarmMusic.setLooping(true);
		// ��������
		switch (alarmType) {
		case 1:
			alarmMusic.start();

			break;
		case 2:
			vibrator.vibrate(new long[] { 1000, 50, 50, 100, 50 }, 1);
			break;
		case 3:
			alarmMusic.start();
			vibrator.vibrate(new long[] { 1000, 50, 50, 100, 50 }, 1);
			break;
		default:
			break;
		}

		// ����һ���Ի���
		new AlertDialog.Builder(StartAlarm.this).setTitle("����")
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
				}).show();
	}
}
