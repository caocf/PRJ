package com.huzhouport.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.user.model.User;
import com.huzhouport.user.service.UserService;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<User> {
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private UserService userService;

	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	private int t;
	private int tc;
	
	private List<?> pu;
	private List<?> nu;
	
	public List<?> getPu() {
		return pu;
	}

	public void setPu(List<?> pu) {
		this.pu = pu;
	}
	public List<?> getNu() {
		return nu;
	}

	public void setNu(List<?> nu) {
		this.nu = nu;
	}
	
	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}
	
	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}


	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public final int getTotalPage() {
		return totalPage;
	}

	public final void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public User getModel() {

		return user;
	}

	// 分页排序查询
	public String showUserList() throws Exception
	{
		String nowpage=this.getCpage();
		
		try 
		{
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.userService.countPageUserInfo(user, GlobalVar.PAGESIZE);//String sp=user.getQueryCondition().getOrderByFielName();
			
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) 
			{
				list = this.userService.searchUserInfo(user, Integer
						.valueOf(nowpage), GlobalVar.PAGESIZE);
				
				if(totalPage==1)
				{
					pu=null;
					nu=null;
				}
				else
				{
					if(Integer.valueOf(nowpage)>1&&Integer.valueOf(nowpage)<allTotal)//第二页到倒数第二页
					{
						pu = this.userService.searchUserInfo(user, Integer
								.valueOf(nowpage)-1, GlobalVar.PAGESIZE);//前一页
						
						nu = this.userService.searchUserInfo(user, Integer//后一页第一个
								.valueOf(nowpage)+1, GlobalVar.PAGESIZE);
						//nid=((User) u2.get(0)).getUserId();
					}
					else if(Integer.valueOf(nowpage)==allTotal)//最后一页
					{
						pu = this.userService.searchUserInfo(user, Integer
								.valueOf(nowpage)-1, GlobalVar.PAGESIZE);
						
						nu=null;
					}
					else if(Integer.valueOf(nowpage)==1)//第一页
					{						
						pu=null;
						
						nu = this.userService.searchUserInfo(user, Integer
								.valueOf(nowpage)+1, GlobalVar.PAGESIZE);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		//List<User> ol=(List<User>) list;
		return SUCCESS;
	}
	
	public String SetUserOrder() throws Exception
	{
		User beuser=userService.showUserByNumId(t).get(0);
		User userc=userService.showUserByNumId(tc).get(0);
		int ordernum=beuser.getOrder();
		int ordernumc=userc.getOrder();
		
		beuser.setOrder(ordernumc);
		userService.updateUserInfo(beuser);
			
		userc.setOrder(ordernum);
		userService.updateUserInfo(userc);	
		
		
		return SUCCESS;
	}

	// 用户通过ID
	public String showUserById() throws Exception {
		try {
			list = this.userService.showUserById(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String AllUser() throws Exception {
		list = this.userService.AllUser();
		return SUCCESS;
	}

	// 查询全部职务
	public String showPostInfo() throws Exception {
		try {
			list = this.userService.showPostInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 查询全部状态
	public String showStatusInfo() throws Exception {
		try {
			list = this.userService.showStatusInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 增加
	public String addUserInfo() throws Exception {
		try {
			user.setTel(user.getTel().replace("，", ","));
			
			this.userService.addUserInfo(user);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return SUCCESS;

	}

	// 根据ListID修改用户信息
	public String deleteUserByListId() throws Exception {
		try {
			userService.deleteUserInfo(user);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 根据ID修改用户信息
	public String deleteUserById() throws Exception {
		try {
			userService.deleteUserById(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}

	// 更新用户信息
	public String updateUserInfo() throws Exception {
		try {
			user.setTel(user.getTel().replace("，", ","));
			
			
			userService.updateUserInfo(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}

	// 修改密码
	public String ChangePassword() throws Exception {
		int t = this.userService.CheckPassword(user);
		if (t > 0) {
			this.userService.ChangePassword(user);
			
			allTotal=1;
			return SUCCESS;
		}

		if (t == 0) {
			allTotal=2;
		}

		return SUCCESS;
	}

	// 全部用户
	public String AllUserAndDepartment() throws Exception {
		list = this.userService.AllUserAndDepartment();
		return SUCCESS;
	}

	// 通过用户模糊搜索姓名
	public String FindByName() throws Exception {
		list = this.userService.FindByName(user);
		return SUCCESS;
	}

	// 通过用户模糊搜索姓名
	public String FindByName_mobi() throws Exception {
		if (user.getName() != null) {
			user.setName(new String(user.getName().getBytes("ISO8859-1"),
					"UTF-8"));

		}
		list = this.userService.FindByName(user);
		return SUCCESS;
	}

	// 用户查找 通过tel
	public String AllUserAndDepartmentTel() throws Exception {

		list = this.userService.AllUserAndDepartmentTel(user);
		return SUCCESS;
	}

	// 用户查找 通过username
	public String AllUserAndDepartmentUsername() throws Exception {

		list = this.userService.AllUserAndDepartmentTel(user);
		return SUCCESS;
	}

	// 通过id找用户
	public String AllUserAndDepartmentById() throws Exception {

		list = this.userService.AllUserAndDepartmentById(user);
		return SUCCESS;
	}

	// 通过部门
	public String UserListByDepartment() throws Exception {
		list = this.userService.UserListByDepartment(user);
		return SUCCESS;
	}
}
