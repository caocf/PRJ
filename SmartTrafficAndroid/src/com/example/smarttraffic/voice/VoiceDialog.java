package com.example.smarttraffic.voice;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.ImageView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;

/**
 * 语音输入对话框
 * @author Administrator zhou
 *
 */
public class VoiceDialog
{
	Context context;
	ImageView imageView;
	public VoiceDialog(Context context)
	{
		this.context=context;
	}
	
	/**
	 * 获取语音输入对话框
	 * @param cancel 取消事件监听函数
	 * @return
	 */
	public Dialog getVoiceDialog(OnClickListener cancel)
	{
		if(imageView==null)
			imageView=new ImageView(context);
		imageView.setImageResource(R.drawable.voice_search);
		
		return GetDialog.editDialog(context, "语音输入", imageView,"", null, "取消", cancel);
	}
	
}
