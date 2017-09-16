package net.hxkg.lawexcut;

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
public static String GLOVAL_FILENAME=null;
public static String GLOVAL_CURRENTDI=null;
public static File GLOVAL_FILE=null;
	public static String[] getImageFromPhone(Intent data) 
	{
		String[] oStrings = new String[2];
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) 
		{ 
			return oStrings;
		}
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");
		FileOutputStream b = null;
		String path = Environment.getExternalStorageDirectory() + "/GH/";
		File file = new File(path);
		file.mkdirs();
		String str = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		date = new Date();
		str = format.format(date);
		oStrings[0] = str;
		String fileName = path + str + ".png";
		oStrings[1] = fileName;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);

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
}
