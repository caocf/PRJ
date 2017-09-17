package com.highwaycenter.bean;

public class selectListBean implements java.io.Serializable , Comparable<selectListBean>{

	private static final long serialVersionUID = 4715009859721284653L;
	private String id_string;
	private Integer id_int;
	private Object idObj;
	private String name;
	public selectListBean(){
		
	}
	public selectListBean(Integer id_int, String name){
		this.id_int = id_int;
		this.name = name;
		
	}
	public selectListBean(String id_string, String name){
		this.id_string = id_string;
		this.name = name;
		
	}
	public String getId_string() {
		return id_string;
	}
	public void setId_string(String id_string) {
		this.id_string = id_string;
	}
	public Integer getId_int() {
		return id_int;
	}
	public void setId_int(Integer id_int) {
		this.id_int = id_int;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int compareTo(selectListBean o) {
		return this.id_string.compareTo(o.getId_string());  
	}
	
	public Object getIdObj() {
		return idObj;
	}
	public void setIdObj(Object idObj) {
		this.idObj = idObj;
	}
	
	
	
	

}
