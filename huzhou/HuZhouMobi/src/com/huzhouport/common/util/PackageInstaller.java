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
 * apk安装工具
 * @author Administrator
 *
 */
public class PackageInstaller {


	/**
	 * 从assets中安装apk查询
	 * @param ctx 
	 * @param assetFile 在assets中的文件名
	 * @param savePath 暂存路径
	 * @param saveName 暂存文件名
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

			//创建文件
			file.createNewFile();

			//写入文件
			writeStreamToFile(stream, file);

			//安装apk
			installApk(ctx,apkPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 检查apk是否已安装
	 * @param context 所属上下文
	 * @param packageName 检查的apk包名
	 * @return 是否已安装
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
	 * 打开指定包
	 * @param context 所属上下文
	 * @param info 包信息
	 */
	public static void openPackage(Context context,ApplicationInfo info){
		Intent mLaunchIntent = context.getPackageManager().getLaunchIntentForPackage( 
				info.packageName);
		context.startActivity(mLaunchIntent);
	}
    

	/**
	 * 将数据流保存到指定文件中
	 * @param stream 数据流
	 * @param file 保存文件名
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
	 * 安装apk并打开
	 * @param context 上下文
	 * @param apkPath apk名称
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