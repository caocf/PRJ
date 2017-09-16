package com.example.smarttraffic.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class PackageInstaller {

	public final static String apkName = "icall.jpg";
	public final static String apkPackage = "com.tianze.itaxi";
	
	public final static String stopapkName = "drive.jpg";
	public final static String stopapkPackage = "rocket.trafficeye.android.hmi2";
	
	public static void install(Context ctx, String apkName) {
		Intent intentInstall = new Intent();
		String apkPath = Environment.getExternalStorageDirectory()+"/SmartTraffic/"+apkName;
		
		File filePath = new File(CheckVersion.UPDATE_SAVE_DIR);
		if (!filePath.exists())
		{
			filePath.mkdir();
		}
		
		File file = new File(apkPath);
		try {
			InputStream is = ctx.getAssets().open(apkName);

			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream  os = new FileOutputStream(file);
				byte[] bytes = new byte[512];
				while ((is.read(bytes)) > 0) {
					os.write(bytes);
				}

				os.close();
				is.close();
				Log.e("", "----------- has been copy to ");
			} else {
				Log.e("", "-----------cunzai ");
			}
			String permission = "666";
			try {
				String command = "chmod " + permission + " " + apkPath + "/"
						+ apkName;
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			Log.e("", e.toString());
		}
		Log.e("", "fl--" + file.getName() + "-dd---" + file.getAbsolutePath()
				+ "-pa-" + file.getPath());
		intentInstall.setAction(android.content.Intent.ACTION_VIEW);
		intentInstall.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intentInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intentInstall);
	}

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
	
	public static void openPackage(Context context,ApplicationInfo info){
		Intent mLaunchIntent = context.getPackageManager().getLaunchIntentForPackage( 
				info.packageName);
		context.startActivity(mLaunchIntent);
	}
	
    public boolean isStartService(Context ctx) {
            ActivityManager mActivityManager = (ActivityManager) ctx
                            .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> currentService = mActivityManager
                            .getRunningServices(100);
            final String igrsClassName = "com.iflytek.asr.AsrService"; //serviceName
            boolean b = igrsBaseServiceIsStart(currentService, igrsClassName);
            return b;
    }

    private boolean igrsBaseServiceIsStart(
                    List<ActivityManager.RunningServiceInfo> mServiceList,
                    String className) {
            for (int i = 0; i < mServiceList.size(); i++) {
                    if (className.equals(mServiceList.get(i).service.getClassName())) {
                            return true;
                    }
            }
            return false;
    }
    

	public static void getUninatllApkInfo(Context context, String archiveFilePath){
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
		if(info != null){
			ApplicationInfo appInfo = info.applicationInfo;
			String packageName = appInfo.packageName;
			System.out.println("packageName"+packageName);
//			String appName = pm.getApplicationLabel(appInfo).toString();
//			Drawable icon = pm.getApplicationIcon(appInfo);
		} else {
			System.out.println("info == null");
		}
	}

}