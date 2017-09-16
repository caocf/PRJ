package com.example.smarttraffic.util;

import android.app.Activity;
import android.content.Context;

public class GetArray 
{
	public static String[] getStringArrayByID(int id,Activity activity)
	{
		return activity.getResources().getStringArray(id);
	}
	
	public static String[] getStringArrayByID(int id,Context context)
	{
		return context.getResources().getStringArray(id);
	}
}
