package com.sts.user.service;

import java.util.List;
import java.util.Random;

import com.sts.user.dao.UserDao;
import com.sts.user.model.User;

/**
 * 用户操作service 类
 * 
 * @author Administrator
 * 
 */
public class UserService {
	UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 登录
	 * 
	 * @param phone
	 *            手机号
	 * @param password
	 *            密码
	 * @return
	 */
	public User login(String phone, String password) {
		List<User> users = userDao.verifyLogin(phone, password);

		if (users == null || users.size() <= 0)
			return null;
		else
			return users.get(0);
	}

	/**
	 * 用户是否已存在
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public boolean userIsExist(String phone) 
	{
		List<User> users = userDao.queryUserByName(phone);
		if (users == null || users.size() == 0)
			return false;
		else
			return true;
	}

	/**
	 * 用户是否已存在
	 * 
	 * @param phone
	 * @param verify
	 * @return
	 */
	public boolean userIsExist(String phone, boolean verify) {
		List<User> users = userDao.queryUserByName(phone, verify);

		if (users == null || users.size() <= 0)
			return false;
		else
			return true;

	}

	/**
	 * 注册验证
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @return
	 */
	public boolean registerVerifyResult(String phone, String code) {
		List<User> us = userDao.queryUserByName(phone, false);
		User u;

		if (us != null && us.size() > 0) {
			u = us.get(0);

			if (u.getVerifyCode().equals(code)) {
				u.setVerifyStatus(true);
				userDao.updateUser(u);
				return true;
			}
		}

		return false;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param phone
	 * @param name
	 * @param password
	 * @param email
	 * @return
	 */
	public User saveUserInfo(String phone, String name, String password,
			String email) {

		List<User> us = userDao.queryUserByName(phone);
		User u = null;

		if (us != null && us.size() > 0) {
			u = us.get(0);

			u.setUsername(name);
			u.setPassword(password);
			u.setEmail(email);

			userDao.updateUser(u);
		}

		return u;
	}
	
	/**
	 * 更新用户
	 * @param u
	 */
	public void updateUser(User u)
	{
		this.userDao.updateUser(u);
	}

	/**
	 * 修改密码
	 * 
	 * @param phone
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public boolean modifyPassword(String phone, String oldPass, String newPass) {
		List<User> users = userDao.queryUserByName(phone, oldPass, false);

		if (users == null || users.size() == 0)
			return false;
		User user = users.get(0);

		user.setVerifyStatus(true);
		user.setPassword(newPass);
		userDao.updateUser(user);
		return true;
	}

	private final int length = 6;

	/**
	 * 根据手机号创建用户
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public User registerVerify(String phone) {
		User user = new User();

		user.setPhone(phone);
		user.setVerifyCode(randomString(length));
		user.setVerifyStatus(false);
		userDao.registerUser(user);

		return user;
	}

	/**
	 * 获取用户（包括未验证的用户）
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public User getUser(String phone) {
		List<User> us = userDao.queryUserByName(phone, false);
		User u = null;

		if (us != null && us.size() > 0) {
			u = us.get(0);
		}

		return u;
	}

	/**
	 * 忘记密码
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public boolean forgetPassword(String phone) {
		User user;
		List<User> users = userDao.queryUserByName(phone, false);

		if (users == null || users.size() == 0)
			return false;

		user = users.get(0);

		user.setVerifyStatus(false);

		userDao.updateUser(user);

		return true;
	}

	/**
	 * 创建验证码
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public String CreateVerifyCodeForWeb(String phone) 
	{
		if (phone.length() > 0)
			return randomString(length);
		else
			return "";
	}

	/**
	 * 注册用户
	 * 
	 * @param phone
	 * @param password
	 * @param email
	 * @return
	 */
	public boolean RegisterUserForWeb(String phone, String password,
			String email) {
		boolean result = false;

		try {
			User user = new User();
			user.setPassword(password);
			user.setPhone(phone);
			user.setEmail(email);
			user.setVerifyStatus(true);

			userDao.registerUser(user);

			return true;
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * 注册用户
	 * @param u
	 * @return
	 */
	public boolean RegisterUser(User u) {
		boolean result = false;

		try {
			
			u.setVerifyStatus(true);

			userDao.registerUser(u);

			return true;
		} catch (Exception e) {
			
			System.out.println(e);
		}

		return result;
	}

	/**
	 * 通过id查询用户
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	public User QueryByUserID(int id) {
		User user = this.userDao.queryUserByID(id);

		if (user != null) {
			return user;
		}

		return null;
	}

	/**
	 * 随机验证码
	 * 
	 * @param length
	 * @return
	 */
	public static final String randomString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;

		if (length < 1) {
			return "";
		}
		if (randGen == null) {
			randGen = new Random();
			// numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			// +"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
			numbersAndLetters = ("0123456789")
					.toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @param address
	 * @param birthday
	 * @param sex
	 * @return
	 */
	public User modifyUserInfo(User user, String address, String birthday,
			int sex) {
		if (address != null && !address.equals("")) {
			user.setAddress(address);
		}

		if (birthday != null && !birthday.equals("")) {
			user.setBirthday(birthday);
		}

		if (sex == 1 || sex == 2 || sex == 0) {
			user.setSex(sex);
		}

		this.userDao.updateUser(user);

		return user;
	}
	/**
	 * 修改密码根据手机
	 * 
	 * @param phone
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public boolean changePasswordByPhone(String phone,String newPass) {
		List<User> users = userDao.queryUserByName(phone, false);

		if (users == null || users.size() == 0)
			return false;
		User user = users.get(0);

		user.setVerifyStatus(true);
		user.setPassword(newPass);
		userDao.updateUser(user);
		return true;
	}

}
