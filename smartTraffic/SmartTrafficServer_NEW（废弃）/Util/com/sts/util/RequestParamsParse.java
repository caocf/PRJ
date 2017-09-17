package com.sts.util;

public class RequestParamsParse 
{
	public static boolean[] filterType(int type,int size)
	{
		boolean[] result=new boolean[size];
		
		for(int i=0;i<size;i++)
		{
			if(type%10==1)
			{
				result[size-1-i]=true;
			}
			else 
			{
				result[size-1-i]=false;
			}
			
			type/=10;
		}
		
		return result;
	}
}
