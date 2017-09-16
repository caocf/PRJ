package com.example.smarttraffic.user;

public class UserControl
{
	public static User user;
	
	public static User getUser()
	{
		return user;
	}
	public static void setUser(User user)
	{
		UserControl.user = user;
	}
}
