package com.huzhouport.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;

/**
 * apk��װ����
 * @author Administrator
 *
 */
public class PackageInstaller {


	/**
	 * ��assets�а�װapk��ѯ
	 * @param ctx 
	 * @param assetFile ��assets�е��ļ���
	 * @param savePath �ݴ�·��
	 * @param saveName �ݴ��ļ���
	 */
	public static void installFromAsset(Context ctx, String assetFile,String savePath,String saveName) {
		AssetManager assets = ctx.getAssets();

		try
		{
			InputStream stream = assets.open(assetFile);
			if (stream == null)
			{
				return;
			}

			String folder = Environment
					.getExternalStorageDirectory().getPath()
					+ savePath;
			File f = new File(folder);
			if (!f.exists())
			{
				f.mkdir();
			}
			String apkPath = Environment
					.getExternalStorageDirectory().getPath()
					+ savePath+saveName;
			File file = new File(apkPath);

			//�����ļ�
			file.createNewFile();

			//д���ļ�
			writeStreamToFile(stream, file);

			//��װapk
			installApk(ctx,apkPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ���apk�Ƿ��Ѱ�װ
	 * @param context ����������
	 * @param packageName ����apk����
	 * @return �Ƿ��Ѱ�װ
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			openPackage(context,info);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * ��ָ����
	 * @param context ����������
	 * @param info ����Ϣ
	 */
	public static void openPackage(Context context,ApplicationInfo info){
		Intent mLaunchIntent = context.getPackageManager().getLaunchIntentForPackage( 
				info.packageName);
		context.startActivity(mLaunchIntent);
	}
    

	/**
	 * �����������浽ָ���ļ���
	 * @param stream ������
	 * @param file �����ļ���
	 */
	public static void writeStreamToFile(InputStream stream, File file)
	{
		try
		{
			OutputStream output = null;
			try
			{
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				try
				{
					final byte[] buffer = new byte[1024];
					int read;

					while ((read = stream.read(buffer)) != -1)
						output.write(buffer, 0, read);

					output.flush();
				} finally
				{
					output.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} finally
		{
			try
			{
				stream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��װapk����
	 * @param context ������
	 * @param apkPath apk����
	 */
	public static void installApk(Context context,String apkPath)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}