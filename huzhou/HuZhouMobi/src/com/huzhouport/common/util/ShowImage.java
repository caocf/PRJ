package com.huzhouport.common.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * ͼƬ�鿴��
 * @author Administrator zwc
 *
 */
public class ShowImage
{
	//����ϵͳͼ��app�鿴ͼƬ
	public static void ShowImageBySystemApp(String path,Activity activity)
	{
		File file=new File(Environment.getExternalStorageDirectory()+"/"+path);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "image/*");
		activity.startActivity(intent);
	}
}
