package com.example.smarttraffic.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

public class DownloadFile
{
	public static int downloadAPK(final String url, final String savePath,
			Map<String, Object> params)
	{
		HttpClient httpClient = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try
		{
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);// 连接时间
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 15 * 1000);// 数据传输时间
			HttpPost httpRequest = new HttpPost(url);// 请求地址
			List<NameValuePair> urlparams = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : params.entrySet())
			{
				String key = entry.getKey();
				Object val = entry.getValue();
				urlparams.add(new BasicNameValuePair(key, val.toString()));
			}
			httpRequest.setEntity(new UrlEncodedFormEntity(urlparams));
			HttpResponse response = httpClient.execute(httpRequest);
			Header[] headers = response.getAllHeaders();
			long size = 0;// 文件大小
			for (Header h : headers)
			{
				if ("Content-Disposition".equals(h.getName()))
				{
					String fileString = h.getValue();
					fileString = fileString.substring(fileString
							.indexOf("filename=") + "filename=".length());
					fileString = fileString.replaceAll("\"", "");
					fileString = URLDecoder.decode(fileString, "UTF-8");
					Log.d("Content-Disposition", fileString);
				} else if ("Content-Length".equals(h.getName()))
				{
					size = Long.valueOf(h.getValue());
				}
			}
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				return -1;
			}
			if (size < 0)
			{
				return -1;
			}
			HttpEntity resEntity = response.getEntity();
			is = resEntity.getContent();// 获得文件的输入流
			long fileLength = resEntity.getContentLength();
			bis = new BufferedInputStream(is);
			File newFile = new File(savePath);
			fos = new FileOutputStream(newFile);
			bos = new BufferedOutputStream(fos);

			byte[] bytes = new byte[4096];
			int len = 0;// 最后一次的长度可能不足4096

			long readedLength = 0l;

			Message message = new Message();
			message.what = CheckVersion.UPDATE_CREATE_PROGRESS;
			message.arg1 = (int) fileLength;
			CheckVersion.handler.sendMessage(message);

			while ((len = bis.read(bytes)) > 0)
			{
				bos.write(bytes, 0, len);

				readedLength += len;

				message = null;
				message = new Message();

				message.what = CheckVersion.UPDATE_PROGRESS;
				message.arg1 = (int) readedLength;
				CheckVersion.handler.sendMessage(message);

				if (readedLength >= fileLength)
				{
					break;
				}
			}
			bos.flush();
			CheckVersion.handler.sendEmptyMessage(CheckVersion.UPDATE_DOWN_VERSION);
		} catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (bis != null)
					bis.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (bos != null)
					bos.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (fos != null)
					fos.close();
			} catch (IOException e)
			{
			}
			httpClient.getConnectionManager().shutdown();
		}
		return 0;
	}

	/**
	 * 下载apk文件
	 * 
	 * @param urlPath
	 *            下载地址
	 * @param savePath
	 *            保存地址
	 */
	// public static void downloadAPK(final String urlPath,final String
	// savePath)
	// {
	// new Thread(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// URL url = null;
	// InputStream in = null;
	// FileOutputStream out = null;
	// HttpURLConnection conn = null;
	// try
	// {
	// url = new URL(urlPath);
	// conn = (HttpURLConnection) url.openConnection();
	// conn.connect();
	// long fileLength = conn.getContentLength();
	// in = conn.getInputStream();
	// File filePath = new File(savePath);
	// if (!filePath.exists())
	// {
	// filePath.createNewFile();
	// }
	// out = new FileOutputStream(new File(savePath));
	// byte[] buffer = new byte[1024];
	// int len = 0;
	// long readedLength = 0l;
	//
	// Message message=new Message();
	// message.what=CheckVersion.UPDATE_CREATE_PROGRESS;
	// message.arg1=(int)fileLength;
	// CheckVersion.handler.sendMessage(message);
	//
	// while ((len = in.read(buffer)) != -1)
	// {
	// out.write(buffer, 0, len);
	// readedLength += len;
	//
	// message=null;
	// message=new Message();
	//
	// message.what=CheckVersion.UPDATE_PROGRESS;
	// message.arg1=(int)readedLength;
	// CheckVersion.handler.sendMessage(message);
	//
	// if (readedLength >= fileLength)
	// {
	// break;
	// }
	// }
	// out.flush();
	//
	// CheckVersion.handler.sendEmptyMessage(CheckVersion.UPDATE_DOWN_VERSION);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// finally
	// {
	// if (out != null)
	// {
	// try
	// {
	// out.close();
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// if (in != null)
	// {
	// try
	// {
	// in.close();
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// if (conn != null)
	// {
	// conn.disconnect();
	// }
	//
	// }
	// }
	// }).start();
	// }

	/**
	 * 安装apk
	 * 
	 * @param APKPath
	 *            apk所在路径
	 * @param context
	 *            上下文
	 */
	public static void installAPK(String APKPath, Context context)
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
