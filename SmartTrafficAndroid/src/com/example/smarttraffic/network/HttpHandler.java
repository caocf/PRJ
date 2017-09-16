package com.example.smarttraffic.network;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 统一的线程处理类 因为会初始化为静态对象，所有多线程访问时可能会造成顺序错误
 * 
 * @author Administrator zhou
 * 
 */
public class HttpHandler extends Handler
{
	public Context context;
	public Context activityContext;

	public void showWaitDialog(Context activityContext)
	{
		this.activityContext = activityContext;
	}

	private ProgressDialog progressDialog;

	public void createProgressBar()
	{
		try
		{
			if (activityContext == null)
				return;

			if (progressDialog != null && progressDialog.isShowing())
				return;

			if (progressDialog == null)
			{
				progressDialog = new ProgressDialog(activityContext);
				progressDialog.setMessage("正在请求中...");
			}
			progressDialog.show();
		} catch (Exception e)
		{
		}
	}

	public void cancelProgressBar()
	{
		if (activityContext == null)
			return;
		if (progressDialog == null)
			return;

		if (progressDialog.isShowing())
			progressDialog.dismiss();
		progressDialog = null;
		activityContext = null;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg)
	{

		try
		{
			if (msg.what == 100)
			{
				createProgressBar();
				return;
			} else
			{
				cancelProgressBar();
			}

			Data d = (Data) msg.obj;

			if (d == null
					|| d.getResult() == null
					|| (d.getResult() instanceof List<?> && ((List<?>) d
							.getResult()).size() < 0))
			{
				if (d.getPrompt() == null)
					Toast.makeText(context, "未查到相关数据", Toast.LENGTH_LONG)
							.show();
				else if(d.getPrompt().equals(""))
				{
				}
				else
					Toast.makeText(context, d.getPrompt(), Toast.LENGTH_LONG)
							.show();
				return;
			}

			if (d.getUpdateView() == null)
			{
				return;
			}

			switch (msg.what)
			{
			case HttpThread.DEFAULT:
				d.getUpdateView().update(d.getResult());
				break;

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
