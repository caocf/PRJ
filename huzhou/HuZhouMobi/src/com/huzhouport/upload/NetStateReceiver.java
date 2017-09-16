package com.huzhouport.upload;

import com.huzhouport.common.util.NetStateManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 网络状态监听器
 * @author Administrator zwc
 *
 */
public class NetStateReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context,Intent intent)
	{
		if(UploadActivity.tool==null || UploadActivity.tool.getFaileUpFile()==null)
			return;
		
		if(NetStateManager.isNetConnect(context))
		{
			if(UploadActivity.tool.getFaileUpFile().size()>0)
			{
				Toast.makeText(context, "网络连接，重新上传失败文件", Toast.LENGTH_LONG).show();
				
				UploadActivity.tool.reUpload(true);
			}
			
			System.out.println("网络连接");
		}
		else
		{
			if(UploadActivity.tool.getFaileUpFile().size()>0)
			{
				Toast.makeText(context, "网络连接失败，还有未上传文件", Toast.LENGTH_LONG).show();
			}
			
			System.out.println("网络失败");
		}
	}

}
