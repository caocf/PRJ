package com.hztuen.lvyou.utils;
import android.os.Environment;

/**
 * �汾����
 * 
 * @author Administrator zhou
 * 
 */
public class CheckVersion extends Thread
{
	public CheckVersion(String url, String save)
	{
		this.url = url;
		this.save = save;
	}

	public static final String PACKAGE_NAME = "com.example.huzhouport";

	public static final int UPDATE_HAS_NO_VERSION = 0;
	public static final int UPDATE_HAS_NEW_VERSION = 1;
	public static final int UPDATE_DOWN_VERSION = 2;
	public static final int UPDATE_CREATE_PROGRESS = 3;
	public static final int UPDATE_PROGRESS = 4;

	public static final String UPDATE_SAVE_PATH = Environment
			.getExternalStorageDirectory()
			+ "/huzhoupublicupdate.apk";
	public static final String UPDATE_SAVE_DIR = Environment
			.getExternalStorageDirectory() + "/autoupdate";
	
	String url;
	String save;

	@Override
	public void run()
	{
		DownloadFile.downloadAPK(url, save);

	}
	
	public void dissThread()
	{
		DownloadFile.disThread();
	}


	
}
