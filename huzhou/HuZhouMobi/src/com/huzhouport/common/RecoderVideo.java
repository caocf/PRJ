package com.huzhouport.common;

import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.MovieRecorder;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecoderVideo extends Activity {
	MovieRecorder mRecorder;// 摄像
	private Button btnRecoder, btnRecoderFinish, btnRecoderReset;
	private SurfaceView sfv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recoder_video);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		// 设置横屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// 选择支持半透明模式,在有surfaceview的activity中使用。
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		btnRecoder = (Button) findViewById(R.id.recoder_btnRecoder);
		btnRecoderFinish = (Button) findViewById(R.id.recoder_btnRecoderFinish);
		btnRecoderReset = (Button) findViewById(R.id.recoder_btnRecoderReset);
		sfv = (SurfaceView) findViewById(R.id.recoder_surfaceView1);

		mRecorder = new MovieRecorder();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int nowWidth = dm.widthPixels; // 当前屏幕像素
		int nowHeigth = dm.heightPixels; // 当前屏幕像素
		mRecorder.setNowWidth(nowWidth);
		mRecorder.setNowHeigth(nowHeigth);

		btnRecoder.setOnClickListener(new mRecordingClick());
		btnRecoderFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRecorder.isRecording) {
					mRecorder.stopRecording();
					mRecorder.isRecording = false;
					btnRecoder.setText("录制录音，单击开始");
					refreshViewByRecordingState();
				}
				if (mRecorder.sFile != null) {
					String str = mRecorder.sFile;
					int index = str.lastIndexOf("/");
					String fileName = str.substring(index + 1);
					String currentDir = str.substring(0, index);
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("fileName", fileName);
					bundle.putString("currentDir", currentDir);
					intent.putExtras(bundle);
					setResult(GlobalVar.RECODER_ICON, intent);
					finish();
				}
			}
		});
		btnRecoderReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRecorder.isRecording) {
					mRecorder.stopRecording();
					mRecorder.isRecording = false;
					btnRecoder.setText("录制录音，单击开始");
					refreshViewByRecordingState();
				}
				finish();
			}
		});

	}

	class mRecordingClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!mRecorder.isRecording) {
				mRecorder.startRecording(sfv);

				mRecorder.isRecording = true;
				btnRecoder.setText("录制中，单击停止");
			} else {
				mRecorder.stopRecording();

				mRecorder.isRecording = false;
				btnRecoder.setText("录制录音，单击开始");

				refreshViewByRecordingState();
			}
		}
	};

	/* 刷新状态 */
	protected void refreshViewByRecordingState() {
		if (mRecorder.isRecording) {
			mRecorder.isRecording = true;
			btnRecoder.setText("录制中，单击停止");
		} else {
			mRecorder.isRecording = false;
			btnRecoder.setText("准备录制，单击开始");
		}

	}
}
