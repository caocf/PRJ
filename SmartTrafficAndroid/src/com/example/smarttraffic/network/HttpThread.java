package com.example.smarttraffic.network;

import com.example.smarttraffic.EnterActivity;
import android.content.Context;
import android.os.Message;

/**
 * 统一的请求线程 多线程同时启动可能造成一定干扰 （以后可做成单例模式）
 * 
 * @author Administrator zhou
 * 
 */
public class HttpThread extends Thread
{
	Search search;
	Request request;
	UpdateView updateView;

	public static final int DEFAULT = -1;
	public static int count = 0; // 避免线程间通信错误

	String prompt;

	public HttpThread(Search search, Request request, UpdateView updateView)
	{
		this(search, request, updateView, "");
	}

	public HttpThread(Search search, Request request, UpdateView updateView,
			String promt)
	{
		this.search = search;
		this.request = request;
		this.updateView = updateView;
		this.prompt = promt;
	}

	public HttpThread(Search search, Request request, UpdateView updateView,
			Context context)
	{
		this(search, request, updateView, context, 0);
	}

	public HttpThread(Search search, Request request, UpdateView updateView,
			Context context, String hint)
	{
		this.search = search;
		this.request = request;
		this.updateView = updateView;

		prompt = hint;

		EnterActivity.handler.showWaitDialog(context);
	}

	public HttpThread(Search search, Request request, UpdateView updateView,
			Context context, int id)
	{
		try
		{
			this.search = search;
			this.request = request;
			this.updateView = updateView;
			if (id == 0)
				prompt = "";
			else
				this.prompt = context.getResources().getString(id);
			EnterActivity.handler.showWaitDialog(context);
		} catch (Exception e)
		{
		}
	}

	@Override
	public void run()
	{
		try
		{
			Data data = new Data();

			EnterActivity.handler.sendEmptyMessage(100);

			count++;

			data.setCount(count);
			data.setResult(search.search(request));
			data.setPrompt(prompt);
			data.setUpdateView(updateView);

			Message message = EnterActivity.handler.obtainMessage();
			message.obj = data;

			message.what = search.getSearchKind();

			switch (search.getSearchKind())
			{
			case DEFAULT:

				EnterActivity.handler.sendMessage(message);
				break;

			}
			
		} catch (Exception e)
		{
		}

	}
}

class Data
{
	UpdateView updateView;
	Object result;
	int count;
	String prompt;

	public String getPrompt()
	{
		return prompt;
	}

	public void setPrompt(String prompt)
	{
		this.prompt = prompt;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

	public UpdateView getUpdateView()
	{
		return updateView;
	}

	public void setUpdateView(UpdateView updateView)
	{
		this.updateView = updateView;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
