package com.highwaycenter.glz.model;

import java.sql.Timestamp;

/**
 * HGlzJxDwry entity. @author MyEclipse Persistence Tools
 */

public class HGlzJxDwry implements java.io.Serializable {

	private static final long serialVersionUID = -1754371414041894116L;
	private String id;
	private String stationId;
	private String picId;
	private String workerName;
	private Short sex;
	private String workType;
	private Timestamp birthday;
	private String education;
	private String professional;
	private Short marriage;
	private String post;
	private String political;
	private String people;
	private String birthplace;
	private String cardId;
	private String telephone;
	private String mobilephone;
	private String address;
	private Timestamp entryTime;
	private Short workStatus;
	private Timestamp contractBegin;
	private Timestamp contractEnd;
	private Timestamp departureTime;
	private String resume;
	private String remarks;
	private String lsId;
	private Short attendanceMark;
	private Integer sequenceNo;

	// Constructors

	/** default constructor */
	public HGlzJxDwry() {
	}

	/** minimal constructor */
	public HGlzJxDwry(String id) {
		this.id = id;
	}

	public HGlzJxDwry(String id, String stationId,String workerName, Short sex,String workType,
			String post,Short workStatus,Timestamp birthday,Timestamp entryTime,String telephone, 
			String mobilephone, String professional){
		this.id = id;
		this.stationId = stationId;
		this.workerName = workerName;
		this.sex = sex;
		this.workType = workType;
		this.birthday = birthday;
		this.professional = professional;
		this.post = post;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.entryTime = entryTime;
		this.workStatus = workStatus;

		
	}
	/** full constructor */
	public HGlzJxDwry(String id, String stationId, String picId,
			String workerName, Short sex, String workType, Timestamp birthday,
			String education, String professional, Short marriage, String post,
			String political, String people, String birthplace, String cardId,
			String telephone, String mobilephone, String address,
			Timestamp entryTime, Short workStatus, Timestamp contractBegin,
			Timestamp contractEnd, Timestamp departureTime, String resume,
			String remarks, String lsId, Short attendanceMark,
			Integer sequenceNo) {
		this.id = id;
		this.stationId = stationId;
		this.picId = picId;
		this.workerName = workerName;
		this.sex = sex;
		this.workType = workType;
		this.birthday = birthday;
		this.education = education;
		this.professional = professional;
		this.marriage = marriage;
		this.post = post;
		this.political = political;
		this.people = people;
		this.birthplace = birthplace;
		this.cardId = cardId;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.address = address;
		this.entryTime = entryTime;
		this.workStatus = workStatus;
		this.contractBegin = contractBegin;
		this.contractEnd = contractEnd;
		this.departureTime = departureTime;
		this.resume = resume;
		this.remarks = remarks;
		this.lsId = lsId;
		this.attendanceMark = attendanceMark;
		this.sequenceNo = sequenceNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getPicId() {
		return this.picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getWorkerName() {
		return this.workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getWorkType() {
		return this.workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfessional() {
		return this.professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public Short getMarriage() {
		return this.marriage;
	}

	public void setMarriage(Short marriage) {
		this.marriage = marriage;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPolitical() {
		return this.political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getPeople() {
		return this.people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	public Short getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(Short workStatus) {
		this.workStatus = workStatus;
	}

	public Timestamp getContractBegin() {
		return this.contractBegin;
	}

	public void setContractBegin(Timestamp contractBegin) {
		this.contractBegin = contractBegin;
	}

	public Timestamp getContractEnd() {
		return this.contractEnd;
	}

	public void setContractEnd(Timestamp contractEnd) {
		this.contractEnd = contractEnd;
	}

	public Timestamp getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLsId() {
		return this.lsId;
	}

	public void setLsId(String lsId) {
		this.lsId = lsId;
	}

	public Short getAttendanceMark() {
		return this.attendanceMark;
	}

	public void setAttendanceMark(Short attendanceMark) {
		this.attendanceMark = attendanceMark;
	}

	public Integer getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}