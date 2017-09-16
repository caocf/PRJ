package com.example.smarttraffic.airport;

/**
 * 航班经过城市
 * 用于输入建议，获取航班支持城市信息
 * @author Administrator zhou
 *
 */
public class AirCity
{
	private String abbreviation;	//简写
	private String englishName;		//城市英文名
	private String name;			//城市中文名
	public String getAbbreviation()
	{
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}
	public String getEnglishName()
	{
		return englishName;
	}
	public void setEnglishName(String englishName)
	{
		this.englishName = englishName;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
