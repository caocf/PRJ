package com.huzhouport.organization.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.organization.dao.DepartmentDao;
import com.huzhouport.organization.model.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public String addDepartmentInfo(Department department) throws Exception {
		this.hibernateTemplate.save(department);
		return null;
	}

	public String deleteDepartmentInfo(Department department) throws Exception {
		this.hibernateTemplate.delete(department);
		return null;
	}

	public String updateDepartmentInfo(Department department) throws Exception {
		this.hibernateTemplate.update(department);
		return null;
	}
	//根据下级部门查找上级部门
	@SuppressWarnings("unchecked")
	public List<Department> searchParentDepartmentName(Department department) throws Exception {
		String hql = "from Department d where d.departmentId="+department.getDepartmentId();
		return this.hibernateTemplate.find(hql);
	}
    //檢查同部門下的部門名
	@SuppressWarnings("unchecked")
	public List<Department> ckeckDepartmentName(Department department) throws Exception {
		String hql = "from Department d where d.partOfDepartmentId="+department.getPartOfDepartmentId()+
		"and d.departmentName='"+department.getDepartmentName()+"'";
		if(department.getDepartmentId()!=0)
			hql=hql+" and d.departmentId!="+department.getDepartmentId();
		return this.hibernateTemplate.find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Department> showDepartmentList(Department department)
			throws Exception {
		String hql = " from Department order by departmentId";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> findUserAndDepartment(Department department) throws Exception{
		String hql="select d,u from User as u,Department as d where u.partOfDepartment=d.departmentId and d.partOfDepartmentId="+department.getDepartmentId()+" order by d.departmentId asc";
		return this.hibernateTemplate.find(hql);
	}

}
