package com.example.smarttraffic.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存、获取配置文件内容
 * @author Administrator zhou
 *
 */
public class SharePreference
{
	private Context context;
	private final String preferenceName="st_preference";
	private final int mode=0;
	public static final String defaultString="";
	public static final int defaultInt=0;
	public static final boolean defaultBool=false;
	
	
	public static final String AIR_SHARE_DEFAULT_DATE="air_share_default_date";
	public static final String AIR_SHARE_IS_SHOW="air_share_is_show";
	
	public static final String TRAIN_SHARE_DEFAULT_DATE="train_share_default_date";
	public static final String TRAIN_SHARE_IS_SHOW="train_share_is_show";
	
	public static final String BUS_SHARE_DEFAULT_DATE="bus_share_default_date";
	public static final String BUS_SHARE_IS_SHOW="bus_share_is_show";
	
	public static final String WEATHER_PIC_NUM="weather_pic_num";
	public static final String WEATHER_TEMPERATURE="weather_temperature";
	public static final String WEATHER_WEATHER="weather_weather";
	
	public static final String USER_NAME="user_name";
	public static final String USER_PASSWORD="user_password";
	public static final String USER_AUTO_SAVE="user_auto_save";
	
	public static final String READ_STATEMENT="read_statement";
	
	public SharePreference(Context context)
	{
		this.context=context;
	}
	
	public String getValueForString(String name)
	{
		return getValueForString(name, defaultString);
	}
	
	public String getValueForString(String name,String defaultName)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		return sharePreference.getString(name, defaultName);
	}
	
	public int getValueForInt(String name)
	{
		return getValueForInt(name, defaultInt);
	}
	
	public int getValueForInt(String name,int defaultValue)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		return sharePreference.getInt(name, defaultValue);
	}
	
	public boolean getValueForBool(String name)
	{
		return getValueForBool(name, defaultBool);
	}
	
	public boolean getValueForBool(String name,boolean defaultValue)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		return sharePreference.getBoolean(name, defaultValue);
	}
	
	public void saveValeForBool(String name,boolean value)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		sharePreference.edit().putBoolean(name, value).commit();
	}
	
	public void saveValeForInt(String name,int value)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		sharePreference.edit().putInt(name, value).commit();
	}
	
	public void saveValeForString(String name,String value)
	{
		SharedPreferences sharePreference= context.getSharedPreferences(preferenceName, mode);
		sharePreference.edit().putString(name, value).commit();
	}
}
