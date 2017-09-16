package net.hxkg.ghmanager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cennavi.cenmapsdk.android.CNMKCommon;
import cennavi.cenmapsdk.android.control.CNMKAPImgr;
import cennavi.cenmapsdk.android.control.ICNMKGeneralListener;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class CenApplication extends Application
{
	public static CenApplication mApp;
	
	public static Map<String,Drawable> map_drawable=new HashMap<>();//纹理
	
	//MapAPI的管理类
	public static CNMKAPImgr mCNMKAPImgr = null;
	SharedPreferences listInfo;	
	public String mStrKey = "C05116B11D9CBBBD2A509CE703809E2023DC59D9";// 授权Key
	boolean m_bKeyRight = true;	// 授权Key正确，验证通过
	
	public static final String CREATE_tb = "CREATE TABLE IF NOT EXISTS GHM (id TEXT PRIMARY KEY,type text,region text)";
	public static final String CREATE_tbLaw = "CREATE TABLE IF NOT EXISTS " 
			+ "Lawbase"+ " (firstman  TEXT,secman TEXT,target TEXT,reason text,detail text,location text,type text," +
			"lon REAL,lat REAL,"+
					"commitdate text  PRIMARY KEY,status INTEGER)";
	public static final String CREATE_file = "CREATE TABLE IF NOT EXISTS " 
			+ "Lawfile"+ " (dir text, id INTEGER  PRIMARY KEY,lawid text)";
	public static SQLiteDatabase db;
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements ICNMKGeneralListener  
	{		
	  	public void onGetNetworkState(int error)
	  	{
	  		if ( error != CNMKCommon.RET_OK )
	  		{
				Toast.makeText(CenApplication.mApp.getApplicationContext(), "您的网络出错啦！",
							Toast.LENGTH_LONG).show();
		  	}
		}
		  
		public void onGetPermissionState(int error) 
		{
		 
			if (error != CNMKCommon.RET_OK ) 
			{
				// 授权Key错误：
				Toast.makeText(CenApplication.mApp.getApplicationContext(), 
							"请在CenMapApiDemoApp.java文件输入正确的授权Key！",
							Toast.LENGTH_LONG).show();
				CenApplication.mApp.m_bKeyRight = false;
			}
		 }
	}
	
	@Override
    public void onCreate()
	{
		super.onCreate();		
		
		mApp = this;
		
		db=this.openOrCreateDatabase("GD", Context.MODE_PRIVATE, null);
		//db.execSQL("DROP TABLE IF EXISTS Lawbase");
		//db.execSQL("DROP TABLE IF EXISTS Lawfile");
        db.execSQL(CREATE_tb); 
        db.execSQL(CREATE_tbLaw);
        db.execSQL(CREATE_file);
                
		mCNMKAPImgr = CNMKAPImgr.createMgr(this);
		mCNMKAPImgr.init(this.mStrKey, new MyGeneralListener());
		
		//加载文理
		AssetManager manager=this.getAssets();        
        //for(int j=1;j<=2;j++)
        	for(int i=0;i<=36;i++)
        	{
        		InputStream is=null;
				try 
				{
					is = manager.open(1+"/"+i*10+".png");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
        		Drawable drawable=Drawable.createFromStream(is, null); 
        		
        		drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*3, drawable
        				.getIntrinsicHeight()*3);
        		map_drawable.put(String.valueOf(1)+i, drawable);
        		try
        		{
					is.close() ;
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
        	}
        Drawable dot=this.getResources().getDrawable(R.drawable.iconmarka);
        dot.setBounds(0, 0, dot.getIntrinsicWidth(), dot .getIntrinsicHeight());
        map_drawable.put("target", dot);
	}

	@Override
    public void onTerminate() 
	{       
        if (mCNMKAPImgr != null)
        {
			mCNMKAPImgr.destroy();
			mCNMKAPImgr = null;
		}
        
        super.onTerminate();        
        System.exit(0);
    }
    @Override
    public void onLowMemory() 
    {
        super.onLowMemory();
    }
    
    public  Map<String,Drawable> getDrawableMap()
    {
    	return map_drawable;
    }
    public  SQLiteDatabase getData()
    {
    	return db;
    }
}
