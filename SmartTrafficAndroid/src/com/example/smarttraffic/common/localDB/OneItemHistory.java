package com.example.smarttraffic.common.localDB;

public class OneItemHistory 
{
	public OneItemHistory()
	{
	}
	
	private String point;
	private int id;
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
	
}
