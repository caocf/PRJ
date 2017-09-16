package net.hxkg.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.hxkg.simple.OfPreview1Activity;
import net.hxkg.user.UserActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;

public class DownloadFile
{
	public DownloadFile()
	{
		
	}
	
	public static void downloadPDF(final String urlPath,final String savePath)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				URL url = null;
				InputStream in = null;
				FileOutputStream out = null;
				HttpURLConnection conn = null;
				try
				{
					url = new URL(urlPath);
					conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					long fileLength = conn.getContentLength();
					if(fileLength<=0)
					{
						Message message1=new Message();
						message1.what=OfPreview1Activity.sec;
						OfPreview1Activity.handle.sendMessage(message1);
						//conn.disconnect();
						return;
					}
						
					in = conn.getInputStream();
					File filePath = new File(savePath);
					filePath.getParentFile().mkdirs();
					if (!filePath.exists())
					{
						filePath.createNewFile();
					}
					out = new FileOutputStream(new File(savePath));
					byte[] buffer = new byte[1024];
					int len = 0;
					long readedLength = 0;
					
					/*Message message=new Message();
					message.what=UserActivity.TOTALSIZE;
					message.arg1=(int)fileLength;
					UserActivity.handler.sendMessage(message);*/
					
					while ((len = in.read(buffer)) != -1)
					{
						out.write(buffer, 0, len);
						readedLength += len;
						
						/*message=null;
						message=new Message();
						
						message.what=UserActivity.PROGRESS;
						message.arg1=(int)readedLength;
						UserActivity.handler.sendMessage(message);*/
						
						if (readedLength >= fileLength)
						{
							break;
						}
					}
					out.flush();
					Message message1=new Message();
					message1.what=OfPreview1Activity.DONE;
					OfPreview1Activity.handle.sendMessage(message1);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Message message1=new Message();
					message1.what=OfPreview1Activity.sec;
					OfPreview1Activity.handle.sendMessage(message1);
					//conn.disconnect();
					return;
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
		}).start();
	}

	/**
	 * 下载apk文件
	 * @param urlPath 下载地址
	 * @param savePath 保存地址
	 */
	public static void downloadAPK(final String urlPath,final String savePath)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				URL url = null;
				InputStream in = null;
				FileOutputStream out = null;
				HttpURLConnection conn = null;
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
					
					Message message=new Message();
					message.what=UserActivity.TOTALSIZE;
					message.arg1=(int)fileLength;
					UserActivity.handler.sendMessage(message);
					
					while ((len = in.read(buffer)) != -1)
					{
						out.write(buffer, 0, len);
						readedLength += len;
						
						message=null;
						message=new Message();
						
						message.what=UserActivity.PROGRESS;
						message.arg1=(int)readedLength;
						UserActivity.handler.sendMessage(message);
						
						if (readedLength >= fileLength)
						{
							break;
						}
					}
					out.flush();
					Message message1=new Message();
					message1.what=UserActivity.DONE;
					UserActivity.handler.sendMessage(message1);
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
		}).start();
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
		
		context.startActivity(intent);
	}
}
