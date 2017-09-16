package com.huzhouport.main;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;

public class User  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userId; // �û�ID
	private String userName;// �ʺ�
	private String name;// ����
	private String password;// ����
	private int partOfDepartment;// ��������
	private int partOfRole;// ��ɫ
	private String tel;// ��ϵ��ʽ
	private String email;// ����
	private String lawId;// ִ��֤���
	private int partOfPost;// ְ��
	private int userStatus;// ״̬
	private List<RolePermission> rpList;

	
	public User()
	{
	}
	
	public User(Bundle oBundle)
	{
		oUserInfoBundle = oBundle;
	}

	Bundle	oUserInfoBundle	= null;

	public Bundle getUserInfoBundle()
	{
		return oUserInfoBundle;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPartOfDepartment() {
		return partOfDepartment;
	}

	public void setPartOfDepartment(int partOfDepartment) {
		this.partOfDepartment = partOfDepartment;
	}

	public int getPartOfRole() {
		return partOfRole;
	}

	public void setPartOfRole(int partOfRole) {
		this.partOfRole = partOfRole;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLawId() {
		return lawId;
	}

	public void setLawId(String lawId) {
		this.lawId = lawId;
	}

	public int getPartOfPost() {
		return partOfPost;
	}

	public void setPartOfPost(int partOfPost) {
		this.partOfPost = partOfPost;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public List<RolePermission> getRpList() {
		return rpList;
	}

	public void setRpList(List<RolePermission> rpList) {
		this.rpList = rpList;
	}

}