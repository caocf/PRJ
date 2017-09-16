package com.huzhouport.cruiselog.action;

import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.huzhouport.cruiselog.model.CruiseTrack;
import com.huzhouport.user.model.User;

import ognl.DefaultTypeConverter;

public class RecordConverter  extends DefaultTypeConverter
{
	@Override
	public Object convertValue(Map context, Object target, Member member,String propertyName, Object value, Class toType)
	{
		if(propertyName.equals("id"))
		{
			return Integer.parseInt(((String[])value)[0]);
		}else if(propertyName.equals("tool"))
		{
			return ((String[])value)[0];
		}else if(propertyName.equals("meters"))
		{
			return Integer.parseInt(((String[])value)[0]);
		}else if(propertyName.equals("startTime"))
		{
			return ((String[])value)[0];
			
		}else if(propertyName.equals("endTime"))
		{
			return ((String[])value)[0];
		}else if(propertyName.equals("userids"))
		{
			Set<User> users =new HashSet();
			String params[]=(String[])value;
			for(String sid:params)
			{
				User user=new User();
				user.setUserId(Integer.valueOf(sid));
				users.add(user);
			}
			return users;
		}else if(propertyName.equals("lat"))
		{
			Set<CruiseTrack> track_set =new HashSet();
			String params[]=(String[])value;
			
		}
		return null;
		
	}

	@Override
	public Object convertValue(Map context, Object value, Class toType)
	{
		return super.convertValue(context, value, toType);
		
	}
}
