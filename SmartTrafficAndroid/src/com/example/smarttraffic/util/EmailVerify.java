package com.example.smarttraffic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Administrator
 * 
 */
public class EmailVerify
{
	public static boolean isEmail(String email)
	{
		Pattern emailPattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = emailPattern.matcher(email);
		if (matcher.find())
		{
			return true;
		}
		return false;
	}

}
