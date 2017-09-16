package com.huzhouport.dangerousgoodsportln.model;

public class GoodsKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int goodsKindID;
	private String goodsKindName;
	public int getGoodsKindID() {
		return goodsKindID;
	}
	public void setGoodsKindID(int goodsKindID) {
		this.goodsKindID = goodsKindID;
	}
	public String getGoodsKindName() {
		return goodsKindName;
	}
	public void setGoodsKindName(String goodsKindName) {
		this.goodsKindName = goodsKindName;
	}
	
	
}
