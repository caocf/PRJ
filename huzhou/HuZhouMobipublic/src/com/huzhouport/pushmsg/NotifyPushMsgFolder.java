package com.huzhouport.pushmsg;

/**
 * �������KnowledgeKind��Ӧ
 * 
 * @author DongJun
 * 
 */
public class NotifyPushMsgFolder {
	private int id;//Ŀ¼ID
	private String name;//Ŀ¼��
	private int parentid;//��Ŀ¼��
	private boolean hasunreadmsg;//δ����Ϣ��

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
