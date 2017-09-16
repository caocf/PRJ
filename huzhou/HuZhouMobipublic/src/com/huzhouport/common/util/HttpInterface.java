package com.huzhouport.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class HttpInterface
{
	public static final String	MESSAGE_CODE	= "phone";

	public static final String	MESSAGE_RESULT	= "resultContent";

	public static final int		NETWORD_ERROR	= 0;
	String						sURL;

	public void setURL(String sURL)
	{
		this.sURL = sURL;
	}

	HashMap<String, String>	hmParameter	= new HashMap<String, String>();

	public void setParameter(HashMap<String, String> parameter)
	{
		this.hmParameter = parameter;
	}

	public HttpInterface()
	{

	}

	public HttpInterface(String URL)
	{
		sURL = URL;
	}

	public HttpInterface(String URL, HashMap<String, String> Parameter)
	{
		sURL = URL;
		hmParameter = Parameter;
	}

	JsonInterface GetTempValue()
	{
		String sHttpURL = null;
		String sHttpParam = null;
		if (hmParameter != null && !hmParameter.isEmpty())
		{
			for (Iterator<Entry<String, String>> it = hmParameter.entrySet()
					.iterator(); it.hasNext();)
			{
				Map.Entry<String, String> e = (Entry<String, String>) it.next();
				if (sHttpParam == null)
				{
					sHttpParam = "?" + e.getKey().toString() + "="
							+ e.getValue();
				}
				else
				{
					sHttpParam += "&" + e.getKey().toString() + "="
							+ e.getValue();
				}

			}
		}
		if (sHttpParam == null)
		{
			sHttpURL = sURL;
		}
		else
			sHttpURL = sURL + sHttpParam;
		String sJsonNeedString = HttpUtil.queryStringForGet(sHttpURL);
		JsonInterface oJsonInterface = new JsonInterface(sJsonNeedString);
		return oJsonInterface;
		// String sHttpUrl = sURL +

	}

	public String GetValue(String Key)
	{
		String sRet = null;

		JsonInterface oJsonInterface = GetTempValue();
		if (oJsonInterface.getInString().equals("Õ¯¬Á“Ï≥££°"))
		{
			sRet = oJsonInterface.getInString();
		}
		else
			sRet = oJsonInterface.GetValue(Key);
		return sRet;
	}

	public int GetIntValue(String Key)
	{
		int iRet = 0;
		JsonInterface oJsonInterface = GetTempValue();
		if (oJsonInterface.getInString().equals("Õ¯¬Á“Ï≥££°"))
		{
			iRet = NETWORD_ERROR;
		}
		else
			iRet = oJsonInterface.GetIntValue(Key);
		return iRet;
	}

}
