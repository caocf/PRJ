package com.huzhouport.upload;
import android.os.Handler;
import android.os.Message;

public class ResultHandler extends Handler
{
	ResultListener listener;
	
	public static final int DEFAULT=9;
	public static final int SEND_FALSE=-1;
	public static final int SEND_SUCCESS=0;
	public static final int SEND_WAITING=1;
	public static final int RELOAD=2;
	public static final int DELETE=3;
	
	public void setListener(ResultListener listener) {
		this.listener = listener;
	}

	@Override
	public void handleMessage(Message msg)
	{
		if(listener==null)
			return;
		switch (msg.what) 
		{
			case -1:
			case 0:
			case 1:
				listener.sendListener(msg.what);
				break;
			case 2:
				listener.reloadListener();
				break;
			case 3:
				listener.deleteListener();
				break;
		}
		
		listener.defaultListener();
	}
}
