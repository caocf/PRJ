package com.huzhouport.wharfSelect.model;

/**
 * 片区
 * 
 * @author DongJun
 * 
 */
public class PieceArea {
	private int id;// 主键
	private String name;// 片区名
	private int portareaid;// 所属港区id

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPortareaid() {
		return portareaid;
	}

	public void setPortareaid(int portareaid) {
		this.portareaid = portareaid;
	}
}
