package com.huzhouport.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;

public class GetBitmap
{
	public static Bitmap GetPic(String path,float width,float height)
	{
		Bitmap bitmap_pic = null;
		String uri = HttpUtil.BASE_URL + "File/" + path;
		// ʹ�ÿ�Դ�������ͼƬ��Դ���û���
		URL url;
		try
		{
			url = new URL(uri);
			// ��ͼƬ��Դ
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() == 200)
			{
				// ʹ�ô˲������Ż���ȡ��ͼƬ��������ռ�ͻ�����Դ
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inSampleSize = 1;
				// ��ȡͼƬ��������
				InputStream is = conn.getInputStream();
				// ��ͼƬ������������ΪBitmap��ʽ
				Bitmap pic = BitmapFactory.decodeStream(is, null, opt);
				is.close();

				int bitmapWidth = pic.getWidth();
				int bitmapHeight = pic.getHeight();
				// ����ͼƬ�ĳߴ�
				float scaleWidth = (float) width / bitmapWidth;
				float scaleHeight = (float) height / bitmapHeight;
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				// �������ź��Bitmap����
				bitmap_pic = Bitmap.createBitmap(pic, 0, 0, bitmapWidth,
						bitmapHeight, matrix, false);
				// ���ù��Ĵ�ͼ����
				if (pic.isRecycled() == false)
				{ // ���û�л���
					pic.recycle();
				}
			}

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return bitmap_pic;
	}

	public static Bitmap GetThumbnailUtils(String path)
	{
		Bitmap bitmap_pic = null;
		String uri = HttpUtil.BASE_URL + "File/" + path;
		System.out.println("uri===="+uri);
		// ʹ�ÿ�Դ�������ͼƬ��Դ���û���
		URL url;
		try
		{
			url = new URL(uri);
			// ��ͼƬ��Դ
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() == 200)
			{
				// ʹ�ô˲������Ż���ȡ��ͼƬ��������ռ�ͻ�����Դ
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inSampleSize = 4;
				// ��ȡͼƬ��������
				InputStream is = conn.getInputStream();
				// ��ͼƬ������������ΪBitmap��ʽ
				Bitmap pic = BitmapFactory.decodeStream(is, null, opt);
				bitmap_pic= ThumbnailUtils.extractThumbnail(pic, 120, 120);
				is.close();
				// ���ù��Ĵ�ͼ����
				if (pic.isRecycled() == false)
				{ // ���û�л���
					pic.recycle();
				}
			}

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return bitmap_pic;
	}
}
