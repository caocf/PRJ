package com.huzhouport.log.dao;

import java.util.List;


import com.huzhouport.log.model.Log;
import com.huzhouport.log.model.PageModel;





public interface Dao {
	
	
	
	
	

//	public List<Userbean> findAllUser(String hql);
	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage,String action);  //日志分页
	public void delete(String hql); //日志删除
	public boolean  logsave(Log log);
	
//	public void update(String hql);
//	public void delete(String hql);
//	public boolean  sava(Log log);
//	public User findByUserId(int userid)throws Exception;
//	public RolePermission findRolePermission(int  partOfRole,int PermissionID)throws Exception;
//	
	
}
