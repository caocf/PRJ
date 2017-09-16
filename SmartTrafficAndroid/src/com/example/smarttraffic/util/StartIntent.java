package com.example.smarttraffic.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * intent跳转
 * @author Administrator zhou
 *
 */
public class StartIntent 
{
	public static void startIntent(Activity from,Class<?> to)
	{
		Intent intent=new Intent();
		intent.setClass(from, to);
		from.startActivity(intent);
	}
	
	public static void startIntentWithParam(Activity from,Class<?> to,Bundle data)
	{
		Intent intent=new Intent();
		intent.setClass(from, to);
		intent.putExtras(data);
		
		from.startActivity(intent);
	}
	
	public static void startIntentForResult(Activity from,Class<?> to)
	{
		startIntentForResult(from, to, 0);
	}
	
	public static void startIntentForResult(Activity from,Class<?> to,int RequestCode)
	{
		Intent intent=new Intent();
		intent.setClass(from, to);
		from.startActivityForResult(intent, RequestCode);
	}
	
	public static void startIntentForResultWithParams(Activity from,Class<?> to,int RequestCode,Bundle bundle)
	{
		Intent intent=new Intent();
		intent.setClass(from, to);
		intent.putExtras(bundle);
		from.startActivityForResult(intent, RequestCode);
	}
	
	public static void startIntentAndFinish(Activity from,Class<?> to)
	{
		Intent intent=new Intent();
		intent.setClass(from, to);
		from.startActivity(intent);
		from.finish();
	}
	
	public static void resultActivity(Activity result,int resultCode,String name,String value)
	{
		Intent intent = new Intent();
        intent.putExtra(name, value);
        result.setResult(resultCode, intent);
        result.finish();
	}
	
	public static void resultActivity(Activity result,int resultCode,String name,int value)
	{
		Intent intent = new Intent();
        intent.putExtra(name, value);
        result.setResult(resultCode, intent);
        result.finish();
	}
	
	public static void resultActivity(Activity result,int resultCode)
	{
        result.setResult(resultCode);
        result.finish();
	}
}
