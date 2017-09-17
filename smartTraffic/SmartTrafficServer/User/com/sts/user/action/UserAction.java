package com.sts.user.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sts.sms.action.SendMessage;
import com.sts.user.model.User;
import com.sts.user.service.UserService;

public class UserAction 
{
	public static final String USER_SESSION_NAME = "user_session_name";
	public static final String USER_SESSION_PHONE = "user_session_phone";
	public static final String USER_SESSION_ID = "user_session_id";

	User user;
	UserService userService;

	// 个人相关信息
	String phone;
	String code;
	String username;
	String password;
	String oldPassword;
	String newPassword;
	String email;

	int status;			//1 成功 -1 无此用户 -2 操作失败
	
	private String address;
	private String birthday;
	private int sex; // 0:不知1:男2:女

	
	int source;				//0、APP端  1、WEB端
	
	// 返回结果
	boolean userIsExist;
	boolean isSuccess;

	public int getStatus() {
		return status;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isUserIsExist() {
		return userIsExist;
	}

	public void setUserIsExist(boolean userIsExist) {
		this.userIsExist = userIsExist;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 用户登录（手机号、密码），适用APP/WEB
	 * 
	 * @return
	 */
	public String login() {
		user = userService.login(phone, password);

		if (user == null) {
			userIsExist = false;
			isSuccess = false;
		} else {
			userIsExist = true;
			isSuccess = true;

			// 保存session
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			session.put(USER_SESSION_NAME, user.getUsername());
			session.put(USER_SESSION_ID, user.getUserid());
			session.put(USER_SESSION_PHONE, user.getPhone());
		}

		return "success";
	}

	/**
	 * 退出 适用APP/WEB
	 * 
	 * @return
	 */
	public String UserExit() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		// 删除session
		if (session.containsKey(USER_SESSION_ID)) {
			session.remove(USER_SESSION_ID);
		}
		if (session.containsKey(USER_SESSION_NAME)) {
			session.remove(USER_SESSION_NAME);
		}
		if (session.containsKey(USER_SESSION_PHONE)) {
			session.remove(USER_SESSION_PHONE);
		}

		return "success";
	}
	
	/**
	 * 获取验证码
	 * @return 
	 */
	public String reSendVerifyCode()
	{
		if (userService.userIsExist(phone, false)) {
			userIsExist = true;
			isSuccess = true;
			user = userService.getUser(phone);
			user.setVerifyCode(userService.CreateVerifyCodeForWeb(phone));

			this.userService.updateUser(user);
			
			SendMessage.SendSMS(new String[] { user.getPhone() },
					(source==0?"【禾行通】":"【智慧出行】")+"你好，你的验证码是：" + user.getVerifyCode(), 5);
		}
		
		return "success";
	}

	/**
	 * 手机注册（第一步） 适用APP
	 * 
	 * @return
	 */
	public String registerPhone() {
		// 无用户
		if (userService.userIsExist(phone)) {
			userIsExist = true;
			isSuccess = false;
		}
		// 数据库有用户残留信息（如忘记密码的用户信息或只注册了一半的用户信息）
		else if (userService.userIsExist(phone, false)) {
			userIsExist = true;
			isSuccess = true;
			user = userService.getUser(phone);
			user.setVerifyCode(userService.CreateVerifyCodeForWeb(phone));

			this.userService.updateUser(user);
			
			//发送短信
			SendMessage.SendSMS(new String[] { user.getPhone() },
					(source==0?"【禾行通】":"【智慧出行】")+"你好，你的验证码是：" + user.getVerifyCode(), 5);
		}
		// 第一步注册成功，同时发生验证码
		else {
			user = userService.registerVerify(phone);
			userIsExist = false;
			isSuccess = true;

			SendMessage.SendSMS(new String[] { user.getPhone() },
					"【禾行通】你好，你的验证码是：" + user.getVerifyCode(), 5);
		}

		return "success";
	}

	/**
	 * 手机注册验证（第二步） 适用APP
	 * 
	 * @return
	 */
	public String registerPhoneVerify() {
		isSuccess = userService.registerVerifyResult(phone, code);

		return "success";
	}

	/**
	 * 手机注册保存用户信息（第三步） 适用APP
	 * 
	 * @return
	 */
	public String SaveUserInfo() {
		if (userService.userIsExist(phone)) {
			user = userService.saveUserInfo(phone, username, password, email);
			isSuccess = true;
		}

		return "success";
	}

	/**
	 * 修改密码,同时登录 适用APP/ WEB
	 * 
	 * @return
	 */
	public String EditPassword() {
		isSuccess = userService.modifyPassword(phone, oldPassword, newPassword);
		user = userService.login(phone, newPassword);

		return "success";
	}

	/**
	 * 手机端忘记密码（第一步），将验证状态设置为未验证,然后调用注册第一步，第二步与注册第二步相同 适用APP
	 * 
	 * @return
	 */
	public String forgetPassword() {
		isSuccess = userService.forgetPassword(phone);

		if (isSuccess)
			return registerPhone();
		else
			return "success";
	}
	
	/**
	 * 获取验证码
	 * @return
	 */
	public String modifyVerifyCode()
	{
		user=this.userService.getUser(phone);
		user.setVerifyCode(userService.CreateVerifyCodeForWeb(phone));

		this.userService.updateUser(user);
		
		//发送短信
		SendMessage.SendSMS(new String[] { user.getPhone() },
				(source==0?"【禾行通】":"【智慧出行】")+"你好，你的验证码是：" + user.getVerifyCode(), 5);
		
		return "success";
	}
	
	/**
	 * 手机号是否已存在（第一步） 适用于WEB
	 * 
	 * @return
	 */
	public String PhoneIsRegisterForWeb() {
		if (phone == "")
			userIsExist = false;
		else
			userIsExist = userService.userIsExist(phone);
		return "success";
	}
	
	// 存放验证码，提交注册后校验
	public static final Map<String, String> codeMap = new HashMap<String, String>();
	
	public static final String REGISTER_TEMP_INFO_PHONE = "register_temp_info_phone";

	/**
	 * 获取验证码（第二步） 适用于WEB
	 * 
	 * @return
	 */
	public String GetVerifyCodeForWeb() 
	{
		// 手机号未注册
		if (!(userService.userIsExist(phone))) 
		{
			// 生成验证码
			code = userService.CreateVerifyCodeForWeb(phone);
			if (!"".equals(code)) 
			{
				// 放入缓存，提高验证效率
				codeMap.put(phone, code);

				// 发送短信
				int a=SendMessage.SendSMS(new String[] { phone },"【智慧出行】您的验证码是：" + code, 5);
				if(a==0)
				{
					isSuccess = true;	
				}
				else
				{
					isSuccess = false;
				}
				
			} 
			else 
			{
				isSuccess = false;
			}
		} 
		else 
		{
			isSuccess = false;
		}

		return "success";
	}

	// 0 成功 1 手机号发生更改或者过期  2 验证码错误 3 密码为空 
	int registerStatusCode;

	public int getRegisterStatusCode() {
		return registerStatusCode;
	}

	/**
	 * 注册用户信息（第三步） 适用WEB
	 * 
	 * @return
	 */
	public String RegisterUserForWeb() 
	{
		if (codeMap.containsKey(phone)) 
		{
			// 验证码错误
			if (!code.equals(codeMap.get(phone))) 
			{
				registerStatusCode = 2;
				return "success";
			}
			
			// 密码为空
			else if ("".equals(password)) 
			{
				registerStatusCode = 3;
				return "success";
			}
			
			// 注册
			else 
			{
				User t = new User();
				t.setPhone(phone);
				t.setPassword(password);
				t.setEmail(email);
				t.setAddress(address);
				t.setBirthday(birthday);
				t.setSex(sex);
				t.setUsername(username);
				t.setVerifyCode(code);
				t.setAddress(address);
				userService.RegisterUser(t);
				
				codeMap.remove(phone);
				
				ActionContext actionContext = ActionContext.getContext();
				Map<String, Object> session = actionContext.getSession();
				session.put(REGISTER_TEMP_INFO_PHONE, phone);
				
				registerStatusCode = 0;
			}
		} 
		else 
		{
			registerStatusCode = 1;
		}

		return "success";
	}

	/**
	 * 获取用户信息 通过SESSION获取，无需传入信息 适用于WEB
	 * 
	 * @return
	 */
	public String GetUserInfoForWeb() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(USER_SESSION_ID)) {
			int id = Integer.valueOf(session.get(USER_SESSION_ID).toString());

			user = this.userService.QueryByUserID(id);
			if (user == null)
				isSuccess = false;
			else
				isSuccess = true;
		} else {
			isSuccess = false;
		}

		return "success";
	}

	/**
	 * 修改用户信息 适用于WEB
	 * 
	 * @return
	 */
	public String ModifyUserInfoForWeb() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(USER_SESSION_ID)) {
			int id = Integer.valueOf(session.get(USER_SESSION_ID).toString());
			user = this.userService.QueryByUserID(id);
			user = this.userService
					.modifyUserInfo(user, address, birthday, sex);
			isSuccess = true;
		}

		return "success";
	}

	/**
	 * 修改密码 适用于WEB端
	 * 
	 * @return
	 */
	public String EditPasswordForWeb() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(USER_SESSION_PHONE)) {
			String sessionPhone = session.get(USER_SESSION_PHONE).toString();
			isSuccess = userService.modifyPassword(sessionPhone, oldPassword,
					newPassword);
			user = userService.login(phone, newPassword);
		}

		return "success";
	}

	/**
	 * 获取用户名 和用户id
	 * 
	 * @return
	 */
	public String GetNameFromSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(USER_SESSION_NAME)) {
			username = session.get(USER_SESSION_NAME).toString();
			code = session.get(USER_SESSION_ID).toString();
		} else {
			username = "";
			code = "";
		}

		return "success";
	}
	//沈丹丹
	/**
	 * Web端忘记密码第一步 和重发忘记密码的验证码
	 * 
	 * @return
	 */
	public String forgetPasswordForWeb() 
	{
		isSuccess = false;
		if (userService.userIsExist(phone, false))
		{
			code = userService.CreateVerifyCodeForWeb(phone);
			codeMap.put(phone, code);
			
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			session.put(REGISTER_TEMP_INFO_PHONE, phone);
			session.put(USER_SESSION_PHONE, phone);

			// 发送短信
			int a = SendMessage.SendSMS(new String[]{ phone }, "【智慧出行】,您正在进行忘记密码操作，验证码是：" + code, 5);
			if (a == 0)
				isSuccess = true;
		}

		return "success";
	}
	
	/**
	 * Web端忘记密码（第二步） 适用WEB
	 * 
	 * @return
	 */
	public String ChackViricodeForWeb() 
	{
		if (codeMap.containsKey(phone)) 
		{
			// 验证码与session中生成的不一致
			if (!code.equals(codeMap.get(phone))) 
			{
				registerStatusCode = 2;
				return "success";
			}
		} 
		else 
		{
			registerStatusCode = 1;
		}

		codeMap.remove(phone);
		return "success";
	}
	/**
	 * 忘记密码 适用于WEB端 第三部
	 * 
	 * @return
	 */
	public String ChangePasswordForWeb() 
	{
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(USER_SESSION_PHONE)) {
			String sessionPhone = session.get(USER_SESSION_PHONE).toString();
			isSuccess = userService.changePasswordByPhone(sessionPhone,newPassword);
			user = userService.login(sessionPhone, newPassword);
			if(user != null)
			{
				session.put(USER_SESSION_NAME, user.getUsername());
				session.put(USER_SESSION_ID, user.getUserid());
			}
		}

		return "success";
	}
}
