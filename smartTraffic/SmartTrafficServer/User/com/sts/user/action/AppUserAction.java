package com.sts.user.action;

import com.sts.common.model.Message;
import com.sts.sms.action.SendMessage;
import com.sts.user.model.AppUser;
import com.sts.user.service.AppUserService;
import com.sts.user.service.UserService;

public class AppUserAction {

	AppUser user;
	Message message;
	Object result;
	AppUserService appUserService;

	String newPass;

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
	public AppUser getUser() {
		return user;
	}
	

	public Message getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	/**
	 * 手机号是否存在
	 * 
	 * @return
	 */
	public String appPhoneIsExisted() {
		try {
			if (user == null || user.getPhone() == null
					|| user.getPhone().equals(""))
				message = new Message(-1, "参数传入错误");
			else {
				if (this.appUserService.appPhoneIsExisted(user.getPhone()))
					message = new Message(0, "手机号已注册");
				else
					message = new Message(1, "手机号尚未被注册");
			}
		} catch (Exception e) {
			message = new Message(-2, "其他错误");
		}
		return "success";
	}

	/**
	 * 发送验证码
	 * 
	 * @return
	 */
	public String appSendMessage() {
		if (user == null || user.getPhone() == null
				|| user.getPhone().equals(""))
			message = new Message(-1, "参数传入错误");
		else {
			String code = UserService.randomString(6);

			result = code;

			int resultcode = SendMessage.SendSMS(
					new String[] { user.getPhone() },
					"【嘉兴智慧交通】您的智慧交通禾行通的验证码是：" + code, 5);

			if (resultcode == 0)
				message = new Message(1, "操作成功，验证码已发送");
			else
				message = new Message(-2, "验证码发送失败");
		}

		return "success";
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	public String appAddUser() {
		
		if (user == null)
			message = new Message(-1, "参数传入错误");
		else if (user.getPhone() == null || user.getPhone().equals(""))
			message = new Message(-2, "手机号不能为空");
		else if (user.getUsername() == null || user.getUsername().equals(""))
			message = new Message(-3, "姓名不能为空");
		else if (user.getUserpassword() == null
				|| user.getUserpassword().equals(""))
			message = new Message(-4, "密码不能为空");
		else {
			boolean r = this.appUserService.appAddUser(user);

			if (r)
			{
				message = new Message(1, "保存用户成功");
				result = this.appUserService.appLogin(user);
			}
			else
				message = new Message(0, "保存用户失败");
		}

		return "success";
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String appLogin() {

		if (user == null)
			message = new Message(-1, "参数传入错误");
		else if (user.getPhone() == null || user.getPhone().equals(""))
			message = new Message(-2, "手机号不能为空");
		else if (user.getUserpassword() == null
				|| user.getUserpassword().equals(""))
			message = new Message(-3, "密码不能为空");
		else {
			result = this.appUserService.appLogin(user);

			if (result == null)
				message = new Message(0, "登录失败，用户名或密码错误");
			else
				message = new Message(1, "登录成功");
		}

		return "success";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String appEditPassword() {

		if (user == null)
			message = new Message(-1, "参数传入错误");
		else if (user.getPhone() == null || user.getPhone().equals(""))
			message = new Message(-2, "手机号不能为空");
		else if (user.getUserpassword() == null
				|| user.getUserpassword().equals(""))
			message = new Message(-3, "旧密码不能为空");
		else if (newPass == null || newPass.equals(""))
			message = new Message(-4, "新密码不能为空");
		else {
			if (!this.appUserService.appPhoneIsExisted(user.getPhone()))
				message = new Message(-5, "该手机号尚未注册");
			else {
				AppUser appUser = this.appUserService.appLogin(this.user);

				if (appUser == null)
					message = new Message(-6, "原密码错误");
				else {
					appUser.setUserpassword(newPass);
					this.appUserService.updateUser(appUser);
					message = new Message(1, "密码修改成功");
				}
			}
		}

		return "success";
	}

	/**
	 * 修改用户信息
	 * @return
	 */
	public String appEditUserInfo() {
		
		if (user == null)
			message = new Message(-1, "参数传入错误");
		else if (user.getPhone() == null || user.getPhone().equals(""))
			message = new Message(-2, "手机号不能为空");
		else {
			AppUser appUser = this.appUserService.queryAppUser(this.user.getPhone());
			if (appUser == null)
				message = new Message(-3, "该手机号尚未注册");
			else {

				appUser.setEmail(user.getEmail());
				appUser.setUsername(user.getUsername());

				result = appUser;
				this.appUserService.updateUser(appUser);
				message = new Message(1, "更新用户成功");
			}
		}

		return "success";
		
	}

	/**
	 * 重置密码
	 * @return
	 */
	public String appResetPass() {
		if (user == null)
			message = new Message(-1, "参数传入错误");
		else if (user.getPhone() == null || user.getPhone().equals(""))
			message = new Message(-2, "手机号不能为空");
		else if (user.getUserpassword() == null
				|| user.getUserpassword().equals(""))
			message = new Message(-3, "密码不能为空");
		else {
			AppUser appUser = this.appUserService.queryAppUser(this.user.getPhone());
			if (appUser == null)
				message = new Message(-5, "该手机号尚未注册");
			else {

				appUser.setUserpassword(user.getUserpassword());

				result = appUser;
				this.appUserService.updateUser(appUser);
				message = new Message(1, "重置密码修改成功");
			}
		}

		return "success";

	}

}
