package com.huzhouport.main;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;

public class User  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userId; // 用户ID
	private String userName;// 帐号
	private String name;// 姓名
	private String password;// 密码
	private int partOfDepartment;// 所属部门
	private int partOfRole;// 角色
	private String tel;// 联系方式
	private String email;// 邮箱
	private String lawId;// 执法证编号
	private int partOfPost;// 职务
	private int userStatus;// 状态
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