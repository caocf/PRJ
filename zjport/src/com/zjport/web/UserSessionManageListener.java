package com.zjport.web;

import com.zjport.model.TUser;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 用户会话管理HttpSessionListener
 * Project: Donut
 * @author TWQ
 * Description: 
 * Copyright: Copyright (c) 2014
 */
public class UserSessionManageListener implements HttpSessionListener {
	public static final Map<String, HttpSession> USER_ID_REF_SESSION_MAP = new HashMap<String, HttpSession>();
	//private static final String _MultiUserLoginAcceptable = Config.get("system.acl.multiUserLoginAcceptable");

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {}

	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		ServletContext application = session.getServletContext();
		//取得登录的用户名
		TUser user = (TUser)session.getAttribute("session_user");

		//System.out.println(user.stName2Html()+"已退出！！");
	}
	


	/**
	 * 在Session中注册用户信息（在用户登录初始化Session业务信息时调用）
	 * @param userId 用户ID
	 * @param session HttpSession
	 */
	private static void registerUserSession(String userId, HttpSession session) {
		session.setAttribute("DONUT_USER_ID", userId);
		USER_ID_REF_SESSION_MAP.put(userId, session);
	}
	
	
	/**
	 * 用户登录时的检查操作（防止重复登录等）
	 * @param userId 用户ID
	 * @param newSession 用户传入的待检查Session
	 */
	/*public static void checkSessionOnUserLogin(int userId, HttpSession newSession) {
		HttpSession mamagedSession = USER_ID_REF_SESSION_MAP.get(userId);
//		System.out.println("***UserSessionManageListener.checkSessionOnUserLogin"); // TODO System.out.println();
//		System.out.println("***userId=" + userId); // TODO System.out.println();
//		System.out.println("***newSessionId=" + newSession.getId()); // TODO System.out.println();
		if ( null != mamagedSession ) {
			String newSessionId = StringHelper.filterNull(newSession.getId());
			String managedSessionId = StringHelper.filterNull(mamagedSession.getId());
//			System.out.println("***newSessionId=" + newSessionId + ",managedSessionId=" + managedSessionId); // TODO System.out.println();
			if ( !newSessionId.equals(managedSessionId) ) {
				if ( "Y".equals(_MultiUserLoginAcceptable) ) {
					Log.debug("用户Session[" + userId + "]重复创建登录，操作已允许！");
				} else {
					mamagedSession.invalidate();
					Log.debug("用户Session[" + userId + "]重复创建登录，旧Session已注销！");
				}
			}
		}
		registerUserSession(userId, newSession);
	}*/
}
