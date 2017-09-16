package com.huzhouport.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

@SuppressLint("SimpleDateFormat")
public class GetFileFromPhone {

	public static String[] getImageFromPhone(Intent data) {
		String[] oStrings = new String[2];
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return oStrings;
		}
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
		FileOutputStream b = null;
		String path = Environment.getExternalStorageDirectory() + "/download/";
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

	public String[] getImageStrings(Intent data) {
		String[] oStrings=new String[2];
		/*Bundle bundle = data.getExtras();
       boolean paizhao = false;
       boolean xiangce = true;
        Uri originalUri = data.getData();
        if (originalUri != null) {
            Bitmap bitMap = null;
            try {
                if(bitMap!=null)bitMap.recycle();
                bitMap = null;
                ContentResolver resolver = null;
				bitMap = MediaStore.Images.Media.getBitmap(resolver,
                        originalUri);
                String[] proj = { MediaColumns.DATA };
                Cursor cursor = getContentResolver().query(originalUri, proj, null, null,
                        null);
                // ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
                int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
                System.out.println("706-------------camera------"
                        + column_index);
                // �����������ͷ ���������Ҫ����С�ĺ���������Խ��
                cursor.moveToFirst();

                // ����������ֵ��ȡͼƬ·��
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                String uploadPhotPath = cursor.getString(column_index);
                System.out
                        .println("camera--------------------561----------- ���ͼƬ�ĵ�ַ--------"
                                + uploadPhotPath);
               Bitmap map = scaleImg(bitMap, 250, 420);
                map.compress(Bitmap.CompressFormat.JPEG, 25, out);
                photo = out.toByteArray();
                intTobyte(photo.length); // ͼƬ�ĳ���
                System.out
                        .println("camera-----------------565----------���ͼƬ�ĳ���-----"
                                + photo.length);
                cam_photo.setImageBitmap(map);
            } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        return oStrings;
	}
}
