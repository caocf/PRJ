package com.sts.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.sts.sms.action.SendMessage;

public class StartUp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// 项目启动项
		System.out.println("StartUp");
		// 激活短信软件
	//	SendMessage.RegistSMS();

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
