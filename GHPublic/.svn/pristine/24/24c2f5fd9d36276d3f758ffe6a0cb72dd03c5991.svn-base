package com.hztuen.lvyou.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hztuen.gh.activity.MineSettingActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DownloadFile
{


	private static Thread thread;
	private static Message message=new Message();
	private static HttpURLConnection conn = null;
	private static InputStream in = null;
	private static FileOutputStream out = null;
	public DownloadFile()
	{

	}


	public static void disThread()
	{
		if(thread != null && thread.isAlive()){
			//Log.e("readCacheThread", "thread interrupt_1");
			//thread.destroy();
			//Log.e("status", ""+readCacheThread.isInterrupted());
			MineSettingActivity.instance.handler.removeCallbacks(thread);
			//			thread.interrupt();
//			thread  = null;
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (in != null)
			{
//				try
//				{
//					//in.close();
//				}
//				catch (IOException e)
//				{
//					e.printStackTrace();
//				}
			}
			if (conn != null)
			{
			//	conn.disconnect();
			}
		}
	}

	/**
	 * 下载apk文件
	 * @param urlPath 下载地址
	 * @param savePath 保存地址
	 */
	public static void downloadAPK(final String urlPath,final String savePath)
	{
		thread=	new Thread(new Runnable()
		{
			@Override
			public void run()
			{
					URL url = null;

					try
					{
						url = new URL(urlPath);
						conn = (HttpURLConnection) url.openConnection();
						conn.connect();
						long fileLength = conn.getContentLength();
						in = conn.getInputStream();
						File filePath = new File(savePath);
						if (!filePath.exists())
						{
							filePath.createNewFile();
						}
						out = new FileOutputStream(new File(savePath));
						byte[] buffer = new byte[1024];
						int len = 0;
						long readedLength = 0l;
						
						message=MineSettingActivity.instance.handler.obtainMessage();
						message.what=CheckVersion.UPDATE_CREATE_PROGRESS;
						message.arg1=(int)fileLength;
						MineSettingActivity.instance.handler.sendMessage(message);

						while ((len = in.read(buffer)) != -1)
						{
							out.write(buffer, 0, len);
							readedLength += len;

							message=null;
							message=MineSettingActivity.instance.handler.obtainMessage();

							message.what=CheckVersion.UPDATE_PROGRESS;
							message.arg1=(int)readedLength;
							Log.i("GH_TEXT", String.valueOf(message.arg1));
							MineSettingActivity.instance.handler.sendMessage(message);
							//System.out.println((int)readedLength);

							if (readedLength >= fileLength)
							{
								break;
							}
						}
						out.flush();

						MineSettingActivity.instance.handler.sendEmptyMessage(CheckVersion.UPDATE_DOWN_VERSION);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						if (out != null)
						{
							try
							{
								out.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						if (in != null)
						{
							try
							{
								in.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						if (conn != null)
						{
							conn.disconnect();
						}

					}
			}
		});
		thread.start();

	}

	/**
	 * 安装apk
	 * @param APKPath apk�?在路�?
	 * @param context 上下�?
	 */
	public static void installAPK(String APKPath,Context context)
	{
		File appFile = new File(APKPath);
		if (!appFile.exists())
		{
			return;
		}
		// 跳转到新版本应用安装页面
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + appFile.toString()),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
