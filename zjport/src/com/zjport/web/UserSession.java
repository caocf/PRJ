package com.zjport.web;

import com.zjport.model.TDepartment;
import com.zjport.model.TOrg;
import com.zjport.model.TUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Title: 用户Session基本信息定义类
 * 
 * @author TWQ
 */
public class UserSession {
	public static final String USER_ID = "session_userid";
	public static final String ACCOUNT = "session_account";
	public static final String USERNAME = "session_username";
	public static final String ORG_ID = "session_orgid";
	public static final String ORG_NAME = "session_orgname";
	public static final String USER = "session_user";
	public static final String ORG = "session_org";
	public static final String DEPT = "session_dept";
	
	//private static EPSPAclService _aclService = null;
	private HttpSession _session = null;
	private TUser _user = null;
	private TOrg _org = null;
	private TDepartment _dept = null;

	public static UserSession getInstance(HttpServletRequest request) {
		return new UserSession(request);
	}

	private UserSession(HttpServletRequest request) {
		this._session = request.getSession();
		/*if (null != _aclService) {
			this._user = _aclService.getUser(this.getStUserId());
			this._dept = _aclService.getDept(this._user.getStDeptId());
		}*/
	}

	public static HttpSession init(HttpServletRequest request, TUser daUser, TOrg daOrg, TDepartment daDept) {
		HttpSession session = request.getSession();
		session.setAttribute(USER_ID, daUser.getStUserId());
		session.setAttribute(ACCOUNT, daUser.getStAccount());
		session.setAttribute(USER, daUser);
		session.setAttribute(ORG, daOrg);
		session.setAttribute(DEPT, daDept);
		session.setAttribute(USERNAME, daUser.getStUserName());
		session.setAttribute(ORG_ID, daOrg.getStOrgId());
		session.setAttribute(ORG_NAME, daOrg.getStOrgName());

		// 根据需要检查重复用户登录
		//UserSessionManageListener.checkSessionOnUserLogin(daUser.getStUserId(), session);
		return session;
	}

	public static void clear(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(USER_ID);
		session.removeAttribute(ACCOUNT);
		session.removeAttribute(USER);
		session.removeAttribute(ORG);
		session.removeAttribute(DEPT);
		session.removeAttribute(USERNAME);
		session.removeAttribute(ORG_ID);
		session.removeAttribute(ORG_NAME);
	}

	public boolean isUserLogin() {
		if (null != this._user) {
			return true;
		} else {
			return false;
		}
	}

	public String getStUserId() {
		return String.valueOf(this._session.getAttribute(USER_ID)) ;
	}

	public String getStAccount() {
		return this._user.getStAccount();
	}

	public String getStUserName() {
		return this._user.getStUserName();
	}

	public String getStOrgId() {
		return this._org.getStOrgId();
	}

	public String getOrgName() {
		return this._org.getStOrgName();
	}
}
