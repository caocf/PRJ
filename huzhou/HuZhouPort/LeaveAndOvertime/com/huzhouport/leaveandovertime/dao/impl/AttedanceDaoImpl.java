package com.huzhouport.leaveandovertime.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.leaveandovertime.dao.AttedanceDao;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.role.model.RolePermission;


public class AttedanceDaoImpl extends BaseDaoImpl<LeaveOrOt> implements AttedanceDao{
	//通过用户Id查权限
	public List<RolePermission> FindPermissionByUserId(int userId,int permissionId) throws Exception{
		String hql="select rp from Role r,RolePermission rp,User u where rp.roleId=r.roleId" +
				" and r.roleId=u.partOfRole and u.userId="+userId+" and rp.permissionId="+permissionId;
		return this.hibernateTemplate.find(hql);
	}
	 private List<Integer> didlist;
		public void FindDepartment(List<Integer> did){
			String hql="select d.departmentId from Department d where d.partOfDepartmentId="+did.get(0);
			for(int i=1;i<did.size();i++){
				hql+=" or d.partOfDepartmentId="+did.get(i);
			}
			List<Integer> list=this.hibernateTemplate.find(hql);
			if(list!=null){
				didlist.addAll(list);
				if(list.size()>0){
					FindDepartment(list);
				}
			}
			
		}	
	//分页
	public int CountAttedanceByPermission(LeaveOrOt leaveOrOt,int roleStatus) throws Exception {
		String hql = "select count(*) from LeaveOrOt l,LeaveOrOtKind lk ,User u,User au where l.leaveOrOtKind=lk.kindID " +
 					"and l.leaveOrOtUser=u.userId and l.approvalID1=au.userId ";
		if(roleStatus==1){
			 hql += "and (au.userId="+leaveOrOt.getLeaveOrOtUser()+" or u.userId="+leaveOrOt.getLeaveOrOtUser()+")";
		}else if(roleStatus==2){
			didlist=new ArrayList<Integer>();
			String hql2="select u.partOfDepartment from User u where u.userId="+leaveOrOt.getLeaveOrOtUser();
			List<Integer> did=this.hibernateTemplate.find(hql2);
			FindDepartment(did);
			 hql += "and (au.userId="+leaveOrOt.getLeaveOrOtUser();
			for(int i=0;i<didlist.size();i++){
				hql += " or u.partOfDepartment="+didlist.get(i);
			}
			hql+=")";
		}
		if(leaveOrOt.getLeaveOrOtReason()!=null){
			hql+= "and (u.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or au.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or l.leaveOrOtReason like '%"+leaveOrOt.getLeaveOrOtReason()+"%')";
		}
		hql+="  order by l.leaveOrOtDate desc";
		return this.countEForeignKey(null, hql);
	}
	// 查询列表
	public List<?> SearchAttedanceByPermission(LeaveOrOt leaveOrOt,int roleStatus,int startSet, int maxSet) throws Exception {
		String hql = "select l,lk,u,au from LeaveOrOt l,LeaveOrOtKind lk ,User u,User au where l.leaveOrOtKind=lk.kindID " +
			"and l.leaveOrOtUser=u.userId and l.approvalID1=au.userId  ";
		if(roleStatus==1){
		 hql += " and (au.userId="+leaveOrOt.getLeaveOrOtUser()+" or u.userId="+leaveOrOt.getLeaveOrOtUser()+")";
		}else if(roleStatus==2){
			didlist=new ArrayList<Integer>();
			String hql2="select u.partOfDepartment from User u where u.userId="+leaveOrOt.getLeaveOrOtUser();
			List<Integer> did=this.hibernateTemplate.find(hql2);
			FindDepartment(did);
			 hql += " and (au.userId="+leaveOrOt.getLeaveOrOtUser();
			for(int i=0;i<didlist.size();i++){
				hql += " or u.partOfDepartment="+didlist.get(i);
			}
			 hql +=")";
		}
		if(leaveOrOt.getLeaveOrOtReason()!=null){
			hql+= "and (u.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or au.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or l.leaveOrOtReason like '%"+leaveOrOt.getLeaveOrOtReason()+"%')";
		}
		hql+="  order by l.leaveOrOtDate desc";
		return this.queryqueryEForeignKey(null, hql, startSet, maxSet);
	}
	//分页
	public int CountAttedanceByMy(LeaveOrOt leaveOrOt) throws Exception {
		String hql = "select count(*) from LeaveOrOt l,LeaveOrOtKind lk ,User u,User au where l.leaveOrOtKind=lk.kindID " +
 					"and l.leaveOrOtUser=u.userId and l.approvalID1=au.userId ";
			 hql += "and u.userId="+leaveOrOt.getLeaveOrOtUser();
			 hql+="  order by l.leaveOrOtDate desc";
		return this.countEForeignKey(null, hql);
	}
	// 查询列表
	public List<?> SearchAttedanceByMy(LeaveOrOt leaveOrOt,int startSet, int maxSet) throws Exception {
		String hql = "select l,lk,u,au from LeaveOrOt l,LeaveOrOtKind lk ,User u,User au where l.leaveOrOtKind=lk.kindID " +
			"and l.leaveOrOtUser=u.userId and l.approvalID1=au.userId  ";
		hql += "and u.userId="+leaveOrOt.getLeaveOrOtUser();
		hql+="  order by l.leaveOrOtDate desc";
		return this.queryqueryEForeignKey(null, hql, startSet, maxSet);
	}
	//APP
	public List<?> FindAttedanceByPermission_APP(LeaveOrOt leaveOrOt,int roleStatus) throws Exception{
		String hql = "select l,lk,u,au from LeaveOrOt l,LeaveOrOtKind lk ,User u,User au where l.leaveOrOtKind=lk.kindID " +
			"and l.leaveOrOtUser=u.userId and l.approvalID1=au.userId  ";
		if(roleStatus==1){
		 hql += " and (au.userId="+leaveOrOt.getLeaveOrOtUser()+" or u.userId="+leaveOrOt.getLeaveOrOtUser()+")";
		}else if(roleStatus==2){
			didlist=new ArrayList<Integer>();
			String hql2="select u.partOfDepartment from User u where u.userId="+leaveOrOt.getLeaveOrOtUser();
			List<Integer> did=this.hibernateTemplate.find(hql2);
			FindDepartment(did);
			 hql += " and (au.userId="+leaveOrOt.getLeaveOrOtUser();
			for(int i=0;i<didlist.size();i++){
				hql += " or u.partOfDepartment="+didlist.get(i);
			}
			 hql +=")";
		}
		if(leaveOrOt.getLeaveOrOtReason()!=null){
			hql+= "and (u.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or au.name like '%"+leaveOrOt.getLeaveOrOtReason()+"%' or l.leaveOrOtReason like '%"+leaveOrOt.getLeaveOrOtReason()+"%')";
		}
		hql+="  order by l.leaveOrOtDate desc";
		return this.hibernateTemplate.find(hql);
		
	}
}
