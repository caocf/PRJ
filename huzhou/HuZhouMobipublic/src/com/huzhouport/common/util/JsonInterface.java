package com.huzhouport.common.util;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonInterface
{
	public JsonInterface()
	{

	}

	public JsonInterface(String InString)
	{
		sInString = InString;

	}

	String	sInString;

	public String getInString()
	{
		return sInString;
	}

	JSONObject	oJsonObject	= null;

	public void setInString(String InString)
	{
		this.sInString = InString;
	}

	JSONObject FormatInString(String InString)
	{
		JSONTokener jsonTokener = new JSONTokener(sInString);
		JSONObject oJsonObjectRet = null;
		try
		{
			oJsonObjectRet = (JSONObject) jsonTokener.nextValue();
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return oJsonObjectRet;
	}

	public String GetValue(String Key)
	{
		if (oJsonObject == null)
			oJsonObject = FormatInString(sInString);
		String sRet = null;
		try
		{
			sRet = oJsonObject.getString(Key);
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return sRet;
	}

	public int GetIntValue(String Key)
	{
		if (oJsonObject == null)
			oJsonObject = FormatInString(sInString);
		int iRet = 0;
		try
		{
			iRet = oJsonObject.getInt(Key);
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return iRet;
	}

}
