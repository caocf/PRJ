package com.huzhouport.common.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * 图片查看器
 * @author Administrator zwc
 *
 */
public class ShowImage
{
	//调用系统图库app查看图片
	public static void ShowImageBySystemApp(String path,Activity activity)
	{
		File file=new File(Environment.getExternalStorageDirectory()+"/"+path);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "image/*");
		activity.startActivity(intent);
	}
}
