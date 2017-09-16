package net.hxkg.cruise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List; 

public class LogModel implements Serializable
{
	public String  title;
	public String  content;
	public String  time;
	public String location;
	
	public List<String> fileArray=new ArrayList<>();

}
