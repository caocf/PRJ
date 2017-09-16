package com.huzhouport.common.model;

import java.io.Serializable;

/**
 * 键值对封装类，如，按照userName='zhangsan'查询，其中key为“userName”；value为“=”；pair为“zhangsan”
 * @author 王翠
 *
 */
public class KeyValuePair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;//键
	
	private String value;//值
	
	private String pair;//对
	
	private String connector;//连接符  or 或者 and

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}
	
	
}
