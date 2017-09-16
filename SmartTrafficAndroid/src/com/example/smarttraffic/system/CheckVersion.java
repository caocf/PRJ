package com.example.smarttraffic.system;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 版本更新类
 * 
 * @author Administrator zhou
 * 
 */
public class CheckVersion
{
	public CheckVersion(Context context)
	{
		this(context, true);
	}

	public CheckVersion(Context context, boolean showInfo)
	{
		this(context, showInfo, false);
	}

	public CheckVersion(Context context, boolean showInfo, boolean isForce)
	{
		this.isForce = isForce;
		this.showInfo = showInfo;
		this.context = context;
	}

	boolean isForce; // 是否强制更新
	boolean showInfo;
	Context context;

	public int getCurrentVersion(String packageName)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
		} catch (Exception e)
		{
			return -1;
		}
	}

	public static int getCurrentVersion(Context context)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionCode;
		} catch (Exception e)
		{
			return -1;
		}
	}

	public static int getCurrentEquipment(Context context)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionCode;
		} catch (Exception e)
		{
			return -1;
		}
	}

	public String getVersionName(String packageName)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (Exception e)
		{
			return "";
		}
	}

	public int getLastVersion(String url)
	{
		return new HttpGetVersion().getLastVersion(url);
	}

	public boolean hasNewVersion(String packageName, VersionInfo url)
	{
		return (getCurrentVersion(packageName) < url.getVersionID()) ? true
				: false;
	}

	public boolean hasNewVersion(String packageName, List<Version> list)
	{
		return (getCurrentVersion(packageName) < new HttpGetVersion()
				.getLastVersion(list)) ? true : false;
	}

	public void check()
	{
		CheckVersion.handler.setContext(context);
		CheckVersion.handler.setShowInfo(showInfo);
		CheckVersion.handler.setForce(isForce);
		new updateThread().start();
	}

	public static final String PACKAGE_NAME = "com.example.smarttraffic";

	public static final int UPDATE_HAS_NO_VERSION = 0;
	public static final int UPDATE_HAS_NEW_VERSION = 1;
	public static final int UPDATE_DOWN_VERSION = 2;
	public static final int UPDATE_CREATE_PROGRESS = 3;
	public static final int UPDATE_PROGRESS = 4;

	public static final String UPDATE_SAVE_PATH = Environment
			.getExternalStorageDirectory() + "/SmartTraffic/autoUpdate.apk";
	public static final String UPDATE_SAVE_DIR = Environment
			.getExternalStorageDirectory() + "/SmartTraffic";

	class updateThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				HttpGetVersion getVersion = new HttpGetVersion();
				VersionInfo versions = getVersion
						.getVersionInfo(HttpUrlRequestAddress.UPDATE_GET_VERSION);

				if (versions == null)
				{
					Toast.makeText(context, "您的网络出问题了", Toast.LENGTH_SHORT)
							.show();
				} else
				{
					boolean hasUpdate = hasNewVersion(PACKAGE_NAME, versions);

					Message message = new Message();

					if (hasUpdate)
					{
						VersionInfo info = versions;
						message.obj = info;
						message.what = UPDATE_HAS_NEW_VERSION;

						// VersionInfo
						// info=getVersion.getVersionInfo(versions.getDetailUrl());
						// message.obj=info;
						// message.what=UPDATE_HAS_NEW_VERSION;
					} else
					{
						message.what = UPDATE_HAS_NO_VERSION;
					}
					handler.sendMessage(message);
				}
			} catch (Exception e)
			{
			}
		}
	}

	public static updateHandler handler = new updateHandler();
}

class updateHandler extends Handler
{
	Context context;
	VersionInfo versionInfo;
	boolean showInfo;
	boolean isForce;

	Dialog dialog;
	ProgressBar progressBar;

	public void setForce(boolean isForce)
	{
		this.isForce = isForce;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public void setShowInfo(boolean showInfo)
	{
		this.showInfo = showInfo;
	}

	public View initView(VersionInfo v)
	{
		View view = LayoutInflater.from(context).inflate(
				R.layout.update_layout, null);

		TextView temp = (TextView) view.findViewById(R.id.version_id);
		temp.setText("版本号：" + v.getVersionName());

		temp = (TextView) view.findViewById(R.id.version_describe);
		temp.setText("新版本特性：" + v.getVersionDescribe());

		temp = (TextView) view.findViewById(R.id.version_debug);
		temp.setText("错误修改：" + v.getVersionDebugLog());

		temp = (TextView) view.findViewById(R.id.version_update);
		temp.setText("新增功能：" + v.getVersionUpdateLog());

		temp = (TextView) view.findViewById(R.id.version_update_date);
		temp.setText("版本更新时间：" + v.getVersionUpdateTime());

		return view;
	}

	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
		case CheckVersion.UPDATE_HAS_NO_VERSION:
			if (showInfo)
				Toast.makeText(context, "已是最新版本", Toast.LENGTH_LONG).show();
			break;

		case CheckVersion.UPDATE_HAS_NEW_VERSION:
			versionInfo = (VersionInfo) msg.obj;
			File filePath = new File(CheckVersion.UPDATE_SAVE_DIR);
			if (!filePath.exists())
			{
				filePath.mkdir();
			}
			GetDialog.editDialog(context, "新版本内容", initView(versionInfo), "下载",
					click, "取消", cancel).show();
			break;
		case CheckVersion.UPDATE_DOWN_VERSION:
			dialog.cancel();
			DownloadFile.installAPK(CheckVersion.UPDATE_SAVE_PATH, context);
			break;

		case CheckVersion.UPDATE_CREATE_PROGRESS:
			View view = LayoutInflater.from(context).inflate(R.layout.progress,
					null);
			progressBar = (ProgressBar) view
					.findViewById(R.id.progressbar_updown);

			progressBar.setMax(msg.arg1);
			dialog = GetDialog.editDialog(context, "更新", progressBar, "", null,
					"", null, !isForce, new OnKeyListener()
					{

						@Override
						public boolean onKey(DialogInterface dialog,
								int keyCode, KeyEvent event)
						{
							System.out.println("cancel");
							
							if (keyCode == KeyEvent.KEYCODE_BACK
									&& event.getRepeatCount() == 0)
								return true;
							
							return false;
						}
					});

			dialog.show();
			break;

		case CheckVersion.UPDATE_PROGRESS:
			if (progressBar != null)
				progressBar.setProgress(msg.arg1);

			break;
		}
	}

	OnClickListener click = new OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			new downThread(HttpUrlRequestAddress.UPDATE_DOWNLOAD_VERSION,
					CheckVersion.UPDATE_SAVE_PATH,
					CheckVersion.getCurrentVersion(context), Build.MODEL,
					((TelephonyManager) context
							.getSystemService(Activity.TELEPHONY_SERVICE))
							.getDeviceId()).start();
			
		}
	};

	OnClickListener cancel = new OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			System.out.println("-----" + isForce);

			if (isForce && versionInfo.getVersionKind() == 2)
				System.exit(0);
			else
				dialog.cancel();
		}
	};
}

class downThread extends Thread
{
	String url;
	String save;
	int version;
	String equitment;
	String imei;

	public downThread(String url, String save, int version, String equitment,
			String imei)
	{
		this.url = url;
		this.save = save;

		this.version = version;
		this.equitment = equitment;
		this.imei = imei;
	}

	@Override
	public void run()
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oldversion", version);
		params.put("equitment", equitment);
		params.put("imei", imei);

		DownloadFile.downloadAPK(url, save, params);
	}
}
