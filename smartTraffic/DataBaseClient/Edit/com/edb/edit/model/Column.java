package com.edb.edit.model;

public class Column 
{
	private String tableName;
	private String columnName;
	private String columnComment;
	private String columnType;
	private int columnLength;
	private String owner;

	public Column()
	{
	}
	
	public Column(String tableName, String columnName, String columnComment,String columnType, int columnLength, String owner) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnComment = columnComment;
		this.columnType = columnType;
		this.columnLength = columnLength;
		this.owner = owner;
	}
	public int getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnType() {
		return columnType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	
	
}
