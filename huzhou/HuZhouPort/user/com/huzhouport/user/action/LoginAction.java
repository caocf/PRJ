package com.huzhouport.user.action;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.role.service.RoleService;
import com.huzhouport.user.model.User;
import com.huzhouport.user.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends BaseAction implements ModelDriven<User> {
	private static final long serialVersionUID = 1L;
	private User user = new User();// 用户
	private UserService userService;
	private List<?> list_P = null;
	private int loginRes;// 1:成功，2：用户名不存在；3：密码错误；4：没有权限
	private LogsaveServer logsaveserver;
	private RoleService roleService;
	public int getLoginRes() {
		return loginRes;
	}

	public void setLoginRes(int loginRes) {
		this.loginRes = loginRes;
	}

	public User getModel() {
		return user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<?> getList() {
		return list_P;
	}

	public void setList(List<?> list_P) {
		this.list_P = list_P;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String login_mobi() {
		try {
			List<User> list = this.userService.searchDataByConditions(user);
			if (list.size() == 0) {
				loginRes = 2;
				return SUCCESS;
			}
			if (!list.get(0).getPassword().equals(this.user.getPassword())) {
				loginRes = 3;
				return SUCCESS;
			}
			if (list.get(0).getUserStatus() != 1
					&& list.get(0).getUserStatus() != 2
					&& list.get(0).getUserStatus() != 3) {
				loginRes = 4;
				return SUCCESS;
			}
			user.setUserId(list.get(0).getUserId());
			user.setName(list.get(0).getName());
			int p=list.get(0).getPartOfDepartment();
			int postid=list.get(0).getPartOfPost();
			user.setPartOfDepartment(p);
			user.setPartOfPost(postid);
			list_P=this.roleService.searchRolePermissionByRoleId(list.get(0).getPartOfRole());
			
			logsaveserver.logsave(list.get(0).getName(), "登录内部app系统", GlobalVar.LOGLOGIN,GlobalVar.LOGAPP,"");
			loginRes = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public String login_pc() throws Exception {
	
			List<User> list = this.userService.queryByLoginName(user);
			if (list.size() <1) {
				throw new Exception("不存在该用户!");
			}
			if (!list.get(0).getPassword().equals(this.user.getPassword())) {
				throw new Exception("密码错误!");
			}
			if (list.get(0).getUserStatus() != 1) {
				throw new Exception("没有登陆权限!");
			}
			Map session = ActionContext.getContext().getSession(); // 获得session
			session.put("username", list.get(0).getUserName());
			session.put("name", list.get(0).getName());
			session.put("userId", list.get(0).getUserId());
			session.put("roleId",list.get(0).getPartOfRole());
			session.put("depid",list.get(0).getPartOfDepartment());
			String a, b;
			a = this.user.getEmail().toLowerCase();
			b = (String) session.get("validateString");
			if (!a.equals(b.toLowerCase())) {
				throw new Exception("验证码不正确!");
			}else
				logsaveserver.logsave(list.get(0).getName(), "登录系统", GlobalVar.LOGLOGIN,GlobalVar.LOGPC,"");
			
		return SUCCESS;

	}

}
