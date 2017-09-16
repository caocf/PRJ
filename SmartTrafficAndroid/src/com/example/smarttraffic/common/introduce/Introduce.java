package com.example.smarttraffic.common.introduce;


/**
 * 简介信息
 * 包括关于ID，关于内容，其他注释
 * @author Administrator zhou
 *
 */
public class Introduce
{
	//关于模块名称
	public static final String[] INTRODUCE_NAME=new String[]{"智慧公交","公共自行车","驾培服务"};
	
	private int introduceID;
	private String introduceContent;
	private String introduceNote;
	public int getIntroduceID()
	{
		return introduceID;
	}
	public void setIntroduceID(int introduceID)
	{
		this.introduceID = introduceID;
	}
	public String getIntroduceContent()
	{
		return introduceContent;
	}
	public void setIntroduceContent(String introduceContent)
	{
		this.introduceContent = introduceContent;
	}
	public String getIntroduceNote()
	{
		return introduceNote;
	}
	public void setIntroduceNote(String introduceNote)
	{
		this.introduceNote = introduceNote;
	}
	
	
}
