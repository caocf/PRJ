package com.huzhouport.version;

import java.io.File;
import java.util.List;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.ManagerDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 版本更新
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
		this(false, showInfo, context);
	}

	public CheckVersion(boolean isForce, boolean showInfo, Context context)
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

	public static final String PACKAGE_NAME = "com.example.huzhouportpublic";

	public static final int UPDATE_HAS_NO_VERSION = 0;
	public static final int UPDATE_HAS_NEW_VERSION = 1;
	public static final int UPDATE_DOWN_VERSION = 2;
	public static final int UPDATE_CREATE_PROGRESS = 3;
	public static final int UPDATE_PROGRESS = 4;

	public static final String UPDATE_SAVE_PATH = Environment
			.getExternalStorageDirectory()
			+ "/autoupdate/huzhoupublicupdate.apk";
	public static final String UPDATE_SAVE_DIR = Environment
			.getExternalStorageDirectory() + "/autoupdate";

	public static final String UPADTE_INFO_URL = HttpUtil.BASE_URL
			+ "queryLastVersion?appid=2";
	public static final String UPADTE_DOWN_URL = HttpUtil.BASE_URL
			+ "downLastApp?appid=2";

	class updateThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				HttpGetVersion getVersion = new HttpGetVersion();

				VersionInfo versions = getVersion
						.getVersionInfo(UPADTE_INFO_URL);

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

			ManagerDialog.editDialog(context, "新版本内容", initView(versionInfo),
					"下载", click, "取消", cancel, !isForce).show();
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
			dialog = ManagerDialog.editDialog(context, "更新", progressBar, "",
					null, "", null);
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
			new downThread(CheckVersion.UPADTE_DOWN_URL,
					CheckVersion.UPDATE_SAVE_PATH).start();
			;
		}
	};

	OnClickListener cancel = new OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			System.out.println("-----"+isForce);
			
			if (isForce)
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

	public downThread(String url, String save)
	{
		this.url = url;
		this.save = save;
	}

	@Override
	public void run()
	{
		DownloadFile.downloadAPK(url, save);

	}
}
