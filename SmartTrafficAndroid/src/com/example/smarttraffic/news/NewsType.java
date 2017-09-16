package com.example.smarttraffic.news;

public class NewsType 
{
	public static final int CHENG_SHI_JIAO_TONG=0;
	public static final int GAO_SU_GONG_LU=1;
	public static final int PU_TONG_GONG_LU=2;
	public static final int GUO_DAO=3;
	public static final int SHENG_DAO=4;
	public static final int QI_TA_GONG_LU=5;
	public static final int GONG_JIAO=6;
	public static final int CHANG_TU=7;
	public static final int GONG_GONG_ZI_XING_CHENG=8;
	public static final int MIN_HANG=9;
	public static final int TIE_LU=10;
	public static final int CHU_ZHU_CHE=11;
	
	private int typeID;
	private String typeName;
	private boolean isCheck;
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	
}
