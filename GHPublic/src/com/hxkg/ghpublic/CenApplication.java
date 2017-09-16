package com.hxkg.ghpublic;
import java.util.HashMap;
import java.util.Map;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

public class CenApplication extends Application
{
	public static CenApplication mApp;
	
	Map<String,Drawable> map_drawable=new HashMap<>();//纹理
	SharedPreferences listInfo;	
	public String mStrKey = "C05116B11D9CBBBD2A509CE703809E2023DC59D9";// 授权Key
	boolean m_bKeyRight = true;	// 授权Key正确，验证通过
	
	public static final String CREATE_tb = "CREATE TABLE IF NOT EXISTS GPUB (id TEXT PRIMARY KEY,type text,region text)";
	public static final String CREATE_tb1 = "CREATE TABLE IF NOT EXISTS " 
			+ "GHM1"+ " (" + "type"
			+ " TEXT,region TEXT,status TEXT,PRIMARY KEY(type,region))";
	public static SQLiteDatabase db;
	
	
	@Override
    public void onCreate()
	{
		super.onCreate();		
		
		mApp = this;
		
		db=this.openOrCreateDatabase("GD", Context.MODE_PRIVATE, null);
		//db.execSQL("DROP TABLE IF EXISTS GPUB");
        db.execSQL(CREATE_tb);        
	}

	@Override
    public void onTerminate() 
	{ 
        
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
