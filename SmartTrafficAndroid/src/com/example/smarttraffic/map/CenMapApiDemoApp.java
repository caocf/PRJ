package com.example.smarttraffic.map;


import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;
import cennavi.cenmapsdk.android.CNMKCommon;
import cennavi.cenmapsdk.android.control.CNMKAPImgr;
import cennavi.cenmapsdk.android.control.ICNMKGeneralListener;

public class CenMapApiDemoApp extends Application { 
	public static CenMapApiDemoApp mDemoApp;
	
	public CNMKAPImgr mCNMKAPImgr = null;
	SharedPreferences listInfo;

	public String mStrKey = "C05116B11D9CBBBD2A509CE703809E2023DC59D9";
	boolean m_bKeyRight = true;	
	
	public static class MyGeneralListener implements ICNMKGeneralListener {
	
	  	public void onGetNetworkState(int error)
	  	{
	  		if ( error != CNMKCommon.RET_OK )
	  		{
				Toast.makeText(CenMapApiDemoApp.mDemoApp.getApplicationContext(), "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
	  		}
	  	}
	  
	  	public void onGetPermissionState(int error)
	  	{
	 
			if (error != CNMKCommon.RET_OK ) {

				Toast.makeText(CenMapApiDemoApp.mDemoApp.getApplicationContext(), 
						"请在CenMapApiDemoApp.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				CenMapApiDemoApp.mDemoApp.m_bKeyRight = false;
			}
	  	}
	}
	
	@Override
    public void onCreate() {
		
		mDemoApp = this;
		mCNMKAPImgr = CNMKAPImgr.createMgr(this);
		mCNMKAPImgr.init(this.mStrKey, new MyGeneralListener());
		super.onCreate();
	}
	
	@Override
	public void onTerminate() {
		if (mCNMKAPImgr != null) {
			mCNMKAPImgr.destroy();
			mCNMKAPImgr = null;
		}
		super.onTerminate();
		System.exit(0);
	}

}
