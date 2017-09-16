package com.example.smarttraffic.news;

import android.graphics.Bitmap;

public class News 
{
	private int id;
	private int subType;
	private int type;
	private Bitmap image;
	private String title;
	private String content;
	private String date;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getSubType()
	{
		return subType;
	}
	public void setSubType(int subType)
	{
		this.subType = subType;
	}
	
}
