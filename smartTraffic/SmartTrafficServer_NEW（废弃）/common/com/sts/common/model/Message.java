package com.sts.common.model;

/**
 * 返回消息状态
 * 
 * @author Administrator
 * 
 */
public class Message {

	public Message() {
		status = -1;
		msg = "";
	}

	public Message(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	int status;
	String msg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
