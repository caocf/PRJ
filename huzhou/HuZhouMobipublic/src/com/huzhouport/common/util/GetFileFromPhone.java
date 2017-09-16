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
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return oStrings;
		}
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
		FileOutputStream b = null;
		String path = Environment.getExternalStorageDirectory() + "/download/";
		File file = new File(path);
		file.mkdirs();// 创建文件夹，名称为myimage

		// 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
		String str = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
		date = new Date();
		str = format.format(date);
		oStrings[0] = str;
		String fileName = path + str + ".jpg";
		oStrings[1] = fileName;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

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
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
                System.out.println("706-------------camera------"
                        + column_index);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();

                // 最后根据索引值获取图片路径
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                String uploadPhotPath = cursor.getString(column_index);
                System.out
                        .println("camera--------------------561----------- 相册图片的地址--------"
                                + uploadPhotPath);
               Bitmap map = scaleImg(bitMap, 250, 420);
                map.compress(Bitmap.CompressFormat.JPEG, 25, out);
                photo = out.toByteArray();
                intTobyte(photo.length); // 图片的长度
                System.out
                        .println("camera-----------------565----------相册图片的长度-----"
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
