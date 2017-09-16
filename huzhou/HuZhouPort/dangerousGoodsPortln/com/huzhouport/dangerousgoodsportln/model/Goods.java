package com.huzhouport.dangerousgoodsportln.model;

public class Goods implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int goodsID;
	private String goodsName;
	private String goodsKindID;
	public int getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsKindID() {
		return goodsKindID;
	}
	public void setGoodsKindID(String goodsKindID) {
		this.goodsKindID = goodsKindID;
	}


	
	
}
