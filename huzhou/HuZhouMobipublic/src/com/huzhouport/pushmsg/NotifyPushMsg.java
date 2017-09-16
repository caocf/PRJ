package com.huzhouport.pushmsg;

public class NotifyPushMsg extends PushMsg {
	private int knowledgeid;
	private String knowledgename;
	private String knowledgeindex;
	private int partofkind;
	private int islink;
	private String knowledgecontent;
	private int islinkinfo;

	public int getKnowledgeid() {
		return knowledgeid;
	}

	public void setKnowledgeid(int knowledgeid) {
		this.knowledgeid = knowledgeid;
	}

	public String getKnowledgename() {
		return knowledgename;
	}

	public void setKnowledgename(String knowledgename) {
		this.knowledgename = knowledgename;
	}

	public String getKnowledgeindex() {
		return knowledgeindex;
	}

	public void setKnowledgeindex(String knowledgeindex) {
		this.knowledgeindex = knowledgeindex;
	}

	public int getPartofkind() {
		return partofkind;
	}

	public void setPartofkind(int partofkind) {
		this.partofkind = partofkind;
	}

	public int getIslink() {
		return islink;
	}

	public void setIslink(int islink) {
		this.islink = islink;
	}

	public String getKnowledgecontent() {
		return knowledgecontent;
	}

	public void setKnowledgecontent(String knowledgecontent) {
		this.knowledgecontent = knowledgecontent;
	}

	public int getIslinkinfo() {
		return islinkinfo;
	}

	public void setIslinkinfo(int islinkinfo) {
		this.islinkinfo = islinkinfo;
	}

	public NotifyPushMsg() {
		setModulerid(MODULERID_NOTIFY);
	}

	@Override
	public void setPushmsgtime(String pushmsgtime) {
		pushmsgtime.replaceAll("T", " ");
		super.setPushmsgtime(pushmsgtime);
	}
}
