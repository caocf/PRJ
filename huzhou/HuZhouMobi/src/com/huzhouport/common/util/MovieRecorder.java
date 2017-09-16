package com.huzhouport.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaRecorder;
import android.view.SurfaceView;

public class MovieRecorder {
	private MediaRecorder mediarecorder;
	public boolean isRecording;
	public String sFile;
	public int nowWidth, nowHeigth;

	public int getNowWidth() {
		return nowWidth;
	}

	public void setNowWidth(int nowWidth) {
		this.nowWidth = nowWidth;
	}

	public int getNowHeigth() {
		return nowHeigth;
	}

	public void setNowHeigth(int nowHeigth) {
		this.nowHeigth = nowHeigth;
	}

	public void startRecording(SurfaceView surfaceView) {
		mediarecorder = new MediaRecorder();// ����mediarecorder����
		// ����¼����ƵԴΪCamera(���)
		mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		// ����¼����ɺ���Ƶ�ķ�װ��ʽTHREE_GPPΪ3gp.MPEG_4Ϊmp4
		mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		// ����¼�Ƶ���Ƶ����h263 h264
		mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		// ������Ƶ¼�Ƶķֱ��ʡ�����������ñ���͸�ʽ�ĺ��棬���򱨴�
		if (nowWidth < 720 || nowHeigth < 576) {
			mediarecorder.setVideoSize(320, 240);
		} else
			mediarecorder.setVideoSize(320, 240);
			//mediarecorder.setVideoSize(720, 576);
		// ����¼�Ƶ���Ƶ֡�ʡ�����������ñ���͸�ʽ�ĺ��棬���򱨴�
		mediarecorder.setVideoFrameRate(25);
		mediarecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
		// ������Ƶ�ļ������·��
		lastFileName = newFileName();
		mediarecorder.setOutputFile(lastFileName);
		try {
			// ׼��¼��
			mediarecorder.prepare();
			// ��ʼ¼��
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
				// TODO Auto-generated method stub
				if (timeSize > 60)
					release();
				else
					timeSize++;
			}
		}, 0, 1000);
	}

	Timer timer;
	int timeSize = 0;

	private String lastFileName;

	public void stopRecording() {
		if (mediarecorder != null) {
			// ֹͣ
			mediarecorder.stop();
			mediarecorder.release();
			mediarecorder = null;

			timer.cancel();
			if (null != lastFileName && !"".equals(lastFileName)) {
				File f = new File(lastFileName);
				String name = f.getName().substring(0,
						f.getName().lastIndexOf(".mp4"));
				name += timeSize + "s.mp4";
				String newPath = f.getParentFile().getAbsolutePath() + "/"
						+ name;
				sFile = newPath;
				if (f.renameTo(new File(newPath))) {
				}
			}
		}
	}

	public String newFileName() {
		try {
			return File.createTempFile("/mov", ".mp4").getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void release() {
		if (mediarecorder != null) {
			// ֹͣ
			mediarecorder.stop();
			mediarecorder.release();
			mediarecorder = null;
		}
	}
}
