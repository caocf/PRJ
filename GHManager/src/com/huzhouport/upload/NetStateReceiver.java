package com.huzhouport.upload;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ����״̬������
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
				Toast.makeText(context, "�������ӣ������ϴ�ʧ���ļ�", Toast.LENGTH_LONG).show();
				
				UploadActivity.tool.reUpload(true);
			}
			
			System.out.println("��������");
		}
		else
		{
			if(UploadActivity.tool.getFaileUpFile().size()>0)
			{
				Toast.makeText(context, "��������ʧ�ܣ�����δ�ϴ��ļ�", Toast.LENGTH_LONG).show();
			}
			
			System.out.println("����ʧ��");
		}
	}

}
