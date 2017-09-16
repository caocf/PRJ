package net.hxkg.user;

import java.util.ArrayList;

public class User 
{
	public static  Integer id;
	public static String uidString; 
	public static String name;
	public static String psw;
	public static String dep;
	public static String tel;
	public static String sjzgString; 
	public static String dw;
	public static String sjhm;
	public static ArrayList<String> permitsArrayList=new ArrayList();//登录后得到
	
	public static boolean checkpermit(String classname)
	{
		int size=permitsArrayList.size();
		for(String s:permitsArrayList)
		{
			if(classname.equals((s)))
				return true;
		}
		return false;
	}
}
