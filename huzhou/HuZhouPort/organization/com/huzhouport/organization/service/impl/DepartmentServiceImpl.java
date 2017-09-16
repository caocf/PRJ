package com.huzhouport.organization.service.impl;

import java.util.List;

import com.huzhouport.organization.dao.DepartmentDao;
import com.huzhouport.organization.model.Department;
import com.huzhouport.organization.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService  {

	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public String addDepartmentInfo(Department department) throws Exception {
		String msg="";
		if(departmentDao.ckeckDepartmentName(department).size()>0)
		{
			msg="新增失败！与同个上级部门下的子部门命名相同。";
			return msg;
		}
		else
			{
			this.departmentDao.addDepartmentInfo(department);
			return null;
			}
		
	}

	public String deleteDepartmentInfo(Department department) throws Exception {
		
		return this.departmentDao.deleteDepartmentInfo(department);
	}

	public String updateDepartmentInfo(Department department) throws Exception {
		String msg="";
		if(departmentDao.ckeckDepartmentName(department).size()>0)
		{
			msg="修改失败！与同个上级部门下的子部门命名相同。";
			return msg;
		}
		else 
			{
			this.departmentDao.updateDepartmentInfo(department);
			return null;
			}
	}

	public List<Department> searchParentDepartmentName(
			Department department) throws Exception {
		return this.departmentDao.searchParentDepartmentName(department);
	}

	//查询全部部门
	public List<Department> showDepartmentList(Department department) throws Exception{
		return this.departmentDao.showDepartmentList(department);
	}
	//手机端 分级查找
	public List<?> findUserAndDepartment(Department department) throws Exception{
		return this.departmentDao.findUserAndDepartment(department);
	}
}
