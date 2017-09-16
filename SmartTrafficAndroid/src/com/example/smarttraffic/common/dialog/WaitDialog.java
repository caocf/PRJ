package com.example.smarttraffic.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

public class WaitDialog
{

	private ProgressBar progressBar;
	private Dialog progressDialog;
	
	public void showWait(Context context)
	{
		if(progressDialog==null)
		{
			progressBar=new ProgressBar(context);
			progressDialog=GetDialog.editDialog(context, "", progressBar, "", null, "", null);
		}
		progressDialog.show();
	}
	
	public void cancelWait()
	{
		if(progressDialog!=null)
			progressDialog.cancel();
	}
}
