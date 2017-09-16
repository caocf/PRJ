package com.zjport.dataquery.model;

public class Condition
{
	private String name;
	private String condition;
	private String value;
	private int isNum; // 0 yes 1 no

	public Condition()
	{
	}

	public Condition(String name, String condition, String value, int isNum)
	{
		super();
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.isNum = isNum;
	}

	public void setIsNum(int isNum)
	{
		this.isNum = isNum;
	}

	public int getIsNum()
	{
		return isNum;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
