package com.huzhouport.equipment.model;

public class EquipmentKind implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int equipmentKindID;
	private String equipmentKindName;
	
	public int getEquipmentKindID() {
		return equipmentKindID;
	}
	public void setEquipmentKindID(int equipmentKindID) {
		this.equipmentKindID = equipmentKindID;
	}
	public String getEquipmentKindName() {
		return equipmentKindName;
	}
	public void setEquipmentKindName(String equipmentKindName) {
		this.equipmentKindName = equipmentKindName;
	}
	
}
