package com.huzhouport.common.util;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

public class StorageSelect
{
	public static String queryAbsolutPath(String path)
	{
		if (path.startsWith("data") || path.startsWith("/data"))
			return path;
		else if (path.startsWith("storage") || path.startsWith("/storage")
				|| path.startsWith("sdcard") || path.startsWith("/sdcard"))
			return "/mnt" + path;

		return path;
	}

	public static String getPath(Context context, Uri uri)
	{

		if ("content".equalsIgnoreCase(uri.getScheme()))
		{
			String[] projection = { "_data" };
			Cursor cursor = null;

			try
			{
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst())
				{
					return cursor.getString(column_index);
				}
			} catch (Exception e)
			{
				// Eat it
			}
		}

		else if ("file".equalsIgnoreCase(uri.getScheme()))
		{
			return uri.getPath();
		}

		return "";
	}
	
	
	
	
	
	
	
	
	
	
	/*-------------------------------------------*/
	
	public static final String CT_S_Sdcard_Sign_Storage_emulated = "storage/emulated/";  
	public static final String CT_S_Sdcard_Sign_Storage_sdcard = "storage/sdcard";
	
	private static String CD_S_SdcardPath = "";  
    private static String CD_S_SdcardPathAbsolute = "";  
      
    public static String getSdcardPath(){  
        if (TextUtils.isEmpty(CD_S_SdcardPath))  
            CD_S_SdcardPath = Environment.getExternalStorageDirectory().getPath();  
  
        if (CD_S_SdcardPath.contains(CT_S_Sdcard_Sign_Storage_emulated))  
            CD_S_SdcardPath = CD_S_SdcardPath.replace(CT_S_Sdcard_Sign_Storage_emulated, CT_S_Sdcard_Sign_Storage_sdcard);  
  
        return CD_S_SdcardPath;  
    }  
  
    public static String getAbsoluteSdcardPath(){  
        if (TextUtils.isEmpty(CD_S_SdcardPathAbsolute))  
            CD_S_SdcardPathAbsolute = Environment.getExternalStorageDirectory().getAbsolutePath();  
  
        if (CD_S_SdcardPathAbsolute.contains(CT_S_Sdcard_Sign_Storage_emulated))  
            CD_S_SdcardPathAbsolute = CD_S_SdcardPathAbsolute.replace(CT_S_Sdcard_Sign_Storage_emulated, CT_S_Sdcard_Sign_Storage_sdcard);  
  
        return CD_S_SdcardPathAbsolute;  
    }  
      
    public static File getSdcardPathFile(){  
        return new File(getSdcardPath());  
    }  
      
    public static String checkAndReplaceEmulatedPath(String strSrc){  
        if (strSrc.contains(CT_S_Sdcard_Sign_Storage_emulated))  
            strSrc = strSrc.replace(CT_S_Sdcard_Sign_Storage_emulated,CT_S_Sdcard_Sign_Storage_sdcard);  
          
        return strSrc;  
    }  
	
	
	
}
