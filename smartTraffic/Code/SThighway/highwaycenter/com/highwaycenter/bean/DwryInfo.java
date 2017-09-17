package com.highwaycenter.bean;

import java.util.Date;
public class DwryInfo implements java.io.Serializable {

	private static final long serialVersionUID = 3332550622288499057L;
	private String id;
	private String stationId;
	private String workerName;
	private short sex;
	private String workType;
	private String post;
	private Date birthday;
	private String telephone;
	private String mobilephone;
	private String professional;
	private Date entryTime;
	private short workStatus;
	private String stationName;

	public DwryInfo(){
		
	}
	
	public DwryInfo(String id, String stationId, String workerName, short sex,
			String workType, String post, Date birthday, String telephone,
			String mobilephone, String professional, Date entryTime,
			short workStatus) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.workerName = workerName;
		this.sex = sex;
		this.workType = workType;
		this.post = post;
		this.birthday = birthday;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.professional = professional;
		this.entryTime = entryTime;
		this.workStatus = workStatus;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public short getSex() {
		return sex;
	}
	public void setSex(short sex) {
		this.sex = sex;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public short getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(short workStatus) {
		this.workStatus = workStatus;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	
}
