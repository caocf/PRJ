package com.sts.suggstion.model;

import java.io.Serializable;


public class Suggestion implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private int type;
	private String typeName;
	private String title;
	private String content;
	private String contact;
	private String suggestionPersion;
	
	public String getSuggestionPersion() {
		return suggestionPersion;
	}
	public void setSuggestionPersion(String suggestionPersion) {
		this.suggestionPersion = suggestionPersion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
