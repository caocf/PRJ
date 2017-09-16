package net.hxkg.channel;

import java.io.Serializable;

public class IssueEntity implements Serializable
{
	String typename;
	String ptypename;	
	String channelname;
	int leftorright;
	String issuetime;
	String kzString;
	String markString;
	int issueid;
	int channelid;
	int typeid;
	
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public int getIssueid() {
		return issueid;
	}
	public void setIssueid(int issueid) {
		this.issueid = issueid;
	}
	public String getMarkString() {
		return markString;
	}
	public void setMarkString(String markString) {
		this.markString = markString;
	}
	public String getKzString() {
		return kzString;
	}
	public void setKzString(String kzString) {
		this.kzString = kzString;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getPtypename() {
		return ptypename;
	}
	public void setPtypename(String ptypename) {
		this.ptypename = ptypename;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public int getLeftorright() {
		return leftorright;
	}
	public void setLeftorright(int leftorright) {
		this.leftorright = leftorright;
	}
	public String getIssuetime() {
		return issuetime;
	}
	public void setIssuetime(String issuetime) {
		this.issuetime = issuetime;
	}
	
	
}
