package com.ghpublic.entity;

import java.io.Serializable;

/**
 * @author  zzq
 * 2016年8月10日 上午10:15:23
 * 港口列表的实体类
 */
public class PortName implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String portName;
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}


}
