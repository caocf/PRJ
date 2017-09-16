package com.huzhouport.common;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

/**
 * 加载等待对话框
 * @author Administrator zwc
 *
 */
public class WaitingDialog
{
	public ProgressDialog createDefaultProgressDialog(Context c,final AsyncTask<?, ?, ?> task)
	{
		return createDefaultProgressDialog(c, task, "数据正在加载中，请稍候···");
	}
	
	@SuppressWarnings("deprecation")
	public ProgressDialog createDefaultProgressDialog(Context c,final AsyncTask<?, ?, ?> task,String message)
	{
		final ProgressDialog pDialog = new ProgressDialog(c);
		pDialog.setTitle("提示");
		pDialog.setMessage(message);
		pDialog.setCancelable(true);
		pDialog.setOnCancelListener(new OnCancelListener()
		{
			
			@Override
			public void onCancel(DialogInterface dialog)
			{
				if (pDialog != null)
					pDialog.dismiss();
				if (task != null
						&& task.getStatus() == AsyncTask.Status.RUNNING)
					task.cancel(true);
				
			}
		});
		pDialog.setButton("取消", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog,int which)
			{
				if (pDialog != null)
					pDialog.dismiss();
				if (task != null
						&& task.getStatus() == AsyncTask.Status.RUNNING)
					task.cancel(true);
			}
		});
		
		return pDialog;
	}
}
