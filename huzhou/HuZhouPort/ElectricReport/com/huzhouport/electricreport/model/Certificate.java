package com.huzhouport.electricreport.model;

public class Certificate implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cardID;//证书编号
	private String cardName;//证书名字
	private String isDelete;//是否删除Y删除N没删除
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getIsDelete() {
		return isDelete;
	}
	
}
