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
		// 使用开源代码加载图片资源设置缓存
		URL url;
		try
		{
			url = new URL(uri);
			// 打开图片资源
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() == 200)
			{
				// 使用此参数，优化读取的图片，尽量少占客户端资源
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inSampleSize = 1;
				// 获取图片的流对象
				InputStream is = conn.getInputStream();
				// 将图片的流对象设置为Bitmap格式
				Bitmap pic = BitmapFactory.decodeStream(is, null, opt);
				is.close();

				int bitmapWidth = pic.getWidth();
				int bitmapHeight = pic.getHeight();
				// 缩放图片的尺寸
				float scaleWidth = (float) width / bitmapWidth;
				float scaleHeight = (float) height / bitmapHeight;
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				// 产生缩放后的Bitmap对象
				bitmap_pic = Bitmap.createBitmap(pic, 0, 0, bitmapWidth,
						bitmapHeight, matrix, false);
				// 将用过的大图回收
				if (pic.isRecycled() == false)
				{ // 如果没有回收
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
		// 使用开源代码加载图片资源设置缓存
		URL url;
		try
		{
			url = new URL(uri);
			// 打开图片资源
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() == 200)
			{
				// 使用此参数，优化读取的图片，尽量少占客户端资源
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inSampleSize = 4;
				// 获取图片的流对象
				InputStream is = conn.getInputStream();
				// 将图片的流对象设置为Bitmap格式
				Bitmap pic = BitmapFactory.decodeStream(is, null, opt);
				bitmap_pic= ThumbnailUtils.extractThumbnail(pic, 120, 120);
				is.close();
				// 将用过的大图回收
				if (pic.isRecycled() == false)
				{ // 如果没有回收
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
