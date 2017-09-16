package com.example.smarttraffic.user;

import com.example.smarttraffic.common.dialog.GetDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

public class BackListener implements OnClickListener
{
	Activity activity;
	String message;
	public BackListener(Activity context,String message)
	{
		this.activity=context;
		this.message=message;
	}
	@Override
	public void onClick(View v)
	{
		GetDialog.messageDialog(activity,"提示",message, "确定", ensure, "取消", cancel).show();
	}
	
	android.content.DialogInterface.OnClickListener ensure =new android.content.DialogInterface.OnClickListener()
	{		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			dialog.cancel();		
			activity.finish();
		}
	};

	android.content.DialogInterface.OnClickListener cancel=new android.content.DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which) 
		{
			dialog.cancel();
		}
	};
}
