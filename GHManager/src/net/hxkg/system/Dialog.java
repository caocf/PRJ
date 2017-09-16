package net.hxkg.system;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

public class Dialog implements DialogInterface.OnClickListener
{
	Context context;
	VersionEN versionEN;
	AlertDialog  alertDialog;
	public Dialog(Context context,VersionEN versionEN)
	{
		this.context=context;
		this.versionEN=versionEN;
	}
	
	public void show()
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("发现新版本");
		builder.setMessage(versionEN.getDesc());
		builder.setPositiveButton("更新", this);
		builder.setNegativeButton("取消",this);
		alertDialog= builder.create();
		alertDialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) 
	{
		switch (which)
		{
		case DialogInterface.BUTTON_POSITIVE:
			DownloadFile.downloadAPK(versionEN.getAddress(), Environment.getExternalStorageDirectory()
																			+ "/GHManager.apk");
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			alertDialog.dismiss();
				
		default:
			break;
		}
		
	}
}
