package com.huzhouport.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceView;

public class SoundRecorder {
	private MediaRecorder mediarecorder;
	public boolean isRecording;
	public String sFile;

	public void startRecording(SurfaceView surfaceView) {
		mediarecorder = new MediaRecorder();// 创建mediarecorder对象
		mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		// 设置录音文件输出的路径
		lastFileName = newFileName();
		mediarecorder.setOutputFile(lastFileName);
		try {
			// 准备录制
			mediarecorder.prepare();
			// 开始录制
			mediarecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isRecording = true;
		timeSize = 0;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(timeSize>60) release();
				else
				timeSize++;
			}
		}, 0,1000);
	}

	Timer timer;
	int timeSize = 0;

	private String lastFileName;

	public void stopRecording() {
		if (mediarecorder != null) {
			// 停止
			mediarecorder.stop();
			mediarecorder.release();
			mediarecorder = null;

			timer.cancel();
			if (null != lastFileName && !"".equals(lastFileName)) {
				File f = new File(lastFileName);
				String name = f.getName().substring(0,
						f.getName().lastIndexOf(".amr"));
				name += timeSize + "s.amr";
				String newPath = f.getParentFile().getAbsolutePath() + "/"
						+ name;
				sFile=newPath;
				if (f.renameTo(new File(newPath))) {
					int i = 0;
					i++;
				}
			}
		}
	}

	public String newFileName() {
		try {
			FileUtils fUtile=new FileUtils();
			fUtile.creatSDDir("HZMobi/");
			File file2 = new File(Environment.getExternalStorageDirectory()+"/HZMobi/");		
			return File.createTempFile("s", ".amr",file2).getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void release() {
		if (mediarecorder != null) {
			// 停止
			mediarecorder.stop();
			mediarecorder.release();
			mediarecorder = null;
		}
	}
}
