package com.example.smarttraffic.voice;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.content.Context;

/**
 * 语音输入管理器
 * 语音输入采用科大讯飞接口
 * @author Administrator zhou
 *
 */
public class VoiceManager 
{
	String voiceResult;								//语音识别结果
	Context context;
	
	RecognizerDialog recognizerDialog;
	SpeechRecognizer mIat;
	public VoiceManager(Context context)
	{
		this.context=context;
		initVoice();
	}
	
	public String getResult()
	{
		return voiceResult;
	}

	/**
	 * 初始化语音输入
	 */
	public void initVoice()
	{
		SpeechUtility.createUtility(context, SpeechConstant.APPID +"=53a7bba6");
		SpeechUtility.getUtility().checkServiceInstalled();
		
	}
	
	/**
	 * 取消语音输入
	 */
	public void cancel()
	{
		if(mIat.isListening())
			mIat.stopListening();
	}
	
	/**
	 * 打开语音输入对话框，使用默认监听器
	 */
	public void startDialog()
	{
		startDialog(mDialogListener);
	}
	
	/**
	 * 打开语音输入对话框
	 * @param listener 输入监听方法
	 */
	public void startDialog(RecognizerDialogListener listener)
	{
		recognizerDialog=new RecognizerDialog(context, mInitListener);
		recognizerDialog.setListener(listener);
		recognizerDialog.show();
	}
	
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
		}
	};
	
	/**
	 * 语音输入监听事件
	 */
	private RecognizerDialogListener mDialogListener=new RecognizerDialogListener()
	{
		
		@Override
		public void onResult(RecognizerResult results, boolean arg1)
		{
			voiceResult= VoiceJsonParser.parseIatResult(results.getResultString());
		}
		
		@Override
		public void onError(SpeechError arg0)
		{
			
		}
	};
	
	/**
	 * 开始语音输入（默认空监听方法）
	 */
	public void start()
	{
		start(mRecoListener);
	}
	
	/**
	 * 开始语音输入
	 * @param listener 设置监听方法
	 */
	public void start(RecognizerListener listener)
	{
		mIat= SpeechRecognizer.createRecognizer(context, mInitListener);
		mIat.setParameter(SpeechConstant.DOMAIN, "iat"); 
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn"); 
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		mIat.setParameter(SpeechConstant.ASR_PTT, "0");
		
		mIat.startListening(listener); 
	}
	private RecognizerListener mRecoListener = new RecognizerListener()
	{
		@Override
		public void onBeginOfSpeech() 
		{			
		}

		@Override
		public void onEndOfSpeech() 
		{			
		}

		@Override
		public void onError(SpeechError arg0) 
		{			
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, String arg3) 
		{		
		}

		@Override
		public void onResult(RecognizerResult result, boolean isLast) 
		{
			
		}

		@Override
		public void onVolumeChanged(int arg0) 
		{

		}
		
		
	};
	
}
