package com.huzhouport.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

@SuppressLint("SimpleDateFormat")
public class GetFileFromPhone {
public static String GLOVAL_FILENAME=null;
public static String GLOVAL_CURRENTDI=null;
public static File GLOVAL_FILE=null;
	public static String[] getImageFromPhone(Intent data) {
		String[] oStrings = new String[2];
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return oStrings;
		}
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
		FileOutputStream b = null;
		String path = Environment.getExternalStorageDirectory() + "/GH/";
		File file = new File(path);
		file.mkdirs();// �����ļ��У�����Ϊmyimage

		// ��Ƭ��������Ŀ���ļ����£��Ե�ǰʱ�����ִ�Ϊ���ƣ�����ȷ��ÿ����Ƭ���Ʋ���ͬ��
		String str = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// ��ȡ��ǰʱ�䣬��һ��ת��Ϊ�ַ���
		date = new Date();
		str = format.format(date);
		oStrings[0] = str;
		String fileName = path + str + ".jpg";
		oStrings[1] = fileName;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return oStrings;
	}
//ѡ���ֻ����� ���GLOVAL_FILENAME�ļ�����GLOVAL_CURRENTDI·�����ļ���GLOVAL_FILE
//	public static void getImageFromPhoneKu(Intent data,Application application) {
//
//		GLOVAL_FILENAME= data.getStringExtra("fileName");
//		GLOVAL_CURRENTDI= data.getStringExtra("currentDir");
//		
//		//�޷�ͨ���������ƻ�ȡ����ʱ
//		if(GLOVAL_FILENAME==null || GLOVAL_CURRENTDI ==null)
//		{
//			String temp=data.getData().getPath();
//			int i=temp.lastIndexOf("/");
//			GLOVAL_CURRENTDI=temp.substring(0,i-1);
//			GLOVAL_FILENAME=temp.substring(i+1);
//			 if(GLOVAL_FILENAME.indexOf(".")==-1){
//				 GLOVAL_FILENAME=null;
//				 GLOVAL_CURRENTDI=null;
//			 }
//			
//		}
//		
//		if (GLOVAL_FILENAME == null)
//		{
//			Uri selectedImage = data.getData();
//			String[] filePathColumn = {
//					MediaStore.Images.Media.DATA,
//					MediaStore.Audio.Media.DATA,
//					MediaStore.Video.Media.DATA };
//			Cursor cursor = application.getContentResolver()
//					.query(selectedImage, filePathColumn, null,
//							null, null);
//			if(cursor!=null){
//			cursor.moveToFirst();
//			int columnIndex = cursor
//					.getColumnIndex(filePathColumn[0]);
//			GLOVAL_CURRENTDI = cursor.getString(columnIndex);
//			cursor.close();
//			int index = GLOVAL_CURRENTDI.lastIndexOf("/");
//			GLOVAL_FILENAME = GLOVAL_CURRENTDI.substring(index + 1);
//			GLOVAL_CURRENTDI = GLOVAL_CURRENTDI.substring(0, index);
//			
//			}
//		}
//		
//		GLOVAL_FILE=new File(GLOVAL_CURRENTDI + "/"+ GLOVAL_FILENAME);
//	}
	
	public static void getImageFromPhoneKu(Intent data,Context application) {

		GLOVAL_FILENAME= data.getStringExtra("fileName");
		GLOVAL_CURRENTDI= data.getStringExtra("currentDir");
		
		//�޷�ͨ���������ƻ�ȡ����ʱ
		if(GLOVAL_FILENAME==null || GLOVAL_CURRENTDI ==null)
		{
			Uri uri = data.getData();
            String temp = StorageSelect.getPath(application, uri);
			
			int i=temp.lastIndexOf("/");
			GLOVAL_CURRENTDI=temp.substring(0,i-1);
			GLOVAL_FILENAME=temp.substring(i+1);
			 if(GLOVAL_FILENAME.indexOf(".")==-1){
				 GLOVAL_FILENAME=null;
				 GLOVAL_CURRENTDI=null;
			 }
			
		}
		
		if (GLOVAL_FILENAME == null)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = {
					MediaStore.Images.Media.DATA,
					MediaStore.Audio.Media.DATA,
					MediaStore.Video.Media.DATA };
			Cursor cursor = application.getContentResolver()
					.query(selectedImage, filePathColumn, null,
							null, null);
			if(cursor!=null){
			cursor.moveToFirst();
			int columnIndex = cursor
					.getColumnIndex(filePathColumn[0]);
			GLOVAL_CURRENTDI = cursor.getString(columnIndex);
			cursor.close();
			int index = GLOVAL_CURRENTDI.lastIndexOf("/");
			GLOVAL_FILENAME = GLOVAL_CURRENTDI.substring(index + 1);
			GLOVAL_CURRENTDI = GLOVAL_CURRENTDI.substring(0, index);
			
			}
		}
		
		
		GLOVAL_FILE=new File(StorageSelect.checkAndReplaceEmulatedPath(GLOVAL_CURRENTDI + "/"+ GLOVAL_FILENAME));
	}
}
