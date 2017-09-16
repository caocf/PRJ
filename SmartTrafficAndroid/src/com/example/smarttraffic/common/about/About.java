package com.example.smarttraffic.common.about;


/**
 * 关于我们
 * 包括关于ID，关于内容，其他注释
 * @author Administrator zhou
 *
 */
public class About
{
	//关于模块名称
	public static final String[] ABOUT_NAME=new String[]{"我们","出行规划","长途客运","铁路信息","民航信息","汽车维修","驾培服务","新闻快讯","公共自行车","智慧公交"};
	
	private int aboutID;
	private String aboutContent;
	private String aboutNote;
	
	public int getAboutID()
	{
		return aboutID;
	}
	public void setAboutID(int aboutID)
	{
		this.aboutID = aboutID;
	}
	public String getAboutName()
	{
		return ABOUT_NAME[aboutID];
	}

	public String getAboutContent()
	{
		return aboutContent;
	}
	public void setAboutContent(String aboutContent)
	{
		this.aboutContent = aboutContent;
	}
	public String getAboutNote()
	{
		return aboutNote;
	}
	public void setAboutNote(String aboutNote)
	{
		this.aboutNote = aboutNote;
	}
	
}
