package com.huzhouport.log.model;



import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import org.hibernate.Session;

import com.huzhouport.log.dao.Dao;









public class SaveImpl1 implements  Save1{
	//private Dao dao;
	//public  static final int LOG_LOGIN =1;
	private Dao dao;
	

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public boolean save1( String logUser,
			String logContent, int partOfStyle) {    //操作人  ， 日志内容 ， 日志类型         注 日志类型 ： 1代表 登录，  2 代表 删除 ， 3代表添加 ，  4代表修改 ， 5代表退出
		Log log=new Log();
		log.setLogUser(logUser);
		log.setLogContent(logContent);
		log.setPartOfStyle(partOfStyle);
		
		Calendar c=Calendar.getInstance();
		Date d=c.getTime();
		String a=d.toLocaleString();
		log.setLogTime(a);
		
		
		System.out.println(log.getLogContent());
		System.out.println(log.getLogTime());
		System.out.println(log.getLogUser());
		System.out.println(log.getPartOfStyle());
		
		//boolean abc=dao.sava(log);
		
		return true;
		
		
	}
	
	public  int   quanxian(int userid,int PermissionID ) {   //userId   PermissionID权限编号 
//		User user=new User();
//		RolePermission role=new RolePermission();
//		try {
//			user=dao.findByUserId( userid);
//			int partOfRole=user.getPartOfRole();
//			//System.out.println(partOfRole);
//			role=dao.findRolePermission(partOfRole,PermissionID);
//		int b= 	role.getStatus();
//		//System.out.println("b="+b);
//		if(b==1||b==0){
//			return b;
//			
//		}else {
//			return 0;
//		}
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		
//		
//		return PermissionID; 
//		
		return 1; 
}
	
	

}
