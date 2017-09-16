package com.huzhouport.pushmsg;

/**
 * 与服务器KnowledgeKind对应
 * 
 * @author DongJun
 * 
 */
public class NotifyPushMsgFolder {
	private int id;//目录ID
	private String name;//目录名
	private int parentid;//父目录名
	private boolean hasunreadmsg;//未读信息数

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHasunreadmsg() {
		return hasunreadmsg;
	}

	public void setHasunreadmsg(boolean hasunreadmsg) {
		this.hasunreadmsg = hasunreadmsg;
	}
}
