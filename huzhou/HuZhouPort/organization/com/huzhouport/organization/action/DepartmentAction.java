package com.huzhouport.organization.action;

import java.util.List;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.organization.model.Department;
import com.huzhouport.organization.service.DepartmentService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DepartmentAction extends BaseAction implements ModelDriven<Department>{
	private Department department=new Department();
	private DepartmentService departmentService;	
	private List<?> list;
	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Department getModel() {
	
		return department;
	}
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	//查询全部部门
	public String showDepartmentList() throws Exception{
		list=this.departmentService.showDepartmentList(department);
		return SUCCESS;
	}

	//增加
	public String addDepartmentInfo() throws Exception {
		try{
			msg=this.departmentService.addDepartmentInfo(department);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//查询 上级部门
	public String searchParentDepartmentName() throws Exception{
		try{
		list=departmentService.searchParentDepartmentName(department);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//更新	
	public String updateDepartmentInfo() throws Exception{
		msg=departmentService.updateDepartmentInfo(department);
		return SUCCESS;
	}
	//删除
	public String deleteDepartmentInfo() throws Exception{
		try{
		departmentService.deleteDepartmentInfo(department);
		}catch (Exception e) {
			throw new Exception("该部门被引用或已删除，导致无法删除！");
		}	
		return SUCCESS;
	}
	//手机端 分级查找
	public String findUserAndDepartment() throws Exception{
		try{
		list=this.departmentService.findUserAndDepartment(department);
		}catch (Exception e) {
			
		}	
		return SUCCESS;
	}
}
