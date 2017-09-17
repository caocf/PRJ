package com.highwaycenter.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.highwaycenter.bean.AuthoritySession;

public class SessionMap {//存取权限工具类
	//权限map对应   key:au+rybh; value :AuthoritySession
	public static Map<String,AuthoritySession> sessionMap ;
	
	/**
	 * 查询内存里面存在的权限用户
	 */
	public static List<Integer> selectExitRybh(){
		List<Integer> list = new ArrayList<Integer>();
		if(sessionMap!=null){
			Set<?> set = sessionMap.entrySet() ;
			Iterator<?> it = sessionMap.entrySet().iterator();
			while(it.hasNext()){
			Entry<?, ?> entry = (Entry)it.next();
			   String rystr = (String) entry.getKey();
			   String rybh = rystr.substring(2);
			   System.out.println("内存里面存在的人员编号"+rybh);
			   list.add(Integer.valueOf(rybh));
			}
		}
		return list;
	}
	
	
	
	/**
	 * 查询该用户是否有对应权限
	 * @param rybh
	 * @return
	 */
	public static String checkAuSession(Integer rybh){
		System.out.println("检查用户是否有权限内存信息"+rybh);
		if(sessionMap==null){
			return "checknull";   //说明内存里面没有权限信息，应该重新登陆
		}
		if(sessionMap!=null){
			System.out.println("权限内存对象的大小"+sessionMap.size());
			//Map<String,AuthoritySession> temp = new HashMap<String,AuthoritySession>();
			//temp.putAll(sessionMap);
		Set<?> set = sessionMap.entrySet() ;
		Iterator<?> it = sessionMap.entrySet().iterator();
		while(it.hasNext()){
		Entry<?, ?> entry = (Entry)it.next();
		String rykey = "au"+rybh;
		/*System.out.println("内存信息里面每一个人的权限信息"+entry.getKey()+"--"+entry.getValue());*/
		if(rykey.equals(entry.getKey())){
			AuthoritySession ausession = (AuthoritySession) entry.getValue();
			String austr = ausession.getAutoStr();
			System.out.println("----返回用户权限内存信息----ry:"+
	    		    ausession.getRybh()+" qx:"+ausession.getAutoStr());
			return austr;
		}
	}
	
			System.out.println("检查用户是否有权限内存信息结果：没有权限信息");
			return "false";
	}else{
		return "false";
	}
	}
	
	/**
	 * 权限信息保存(用户登录--保存)
	 * @param ausession
	 */
     public  static void  saveAuSession(AuthoritySession ausession){
    
    	 try{
    		 initSession();
    		 String check = checkAuSession(ausession.getRybh());
    		 
    		    String key = "au"+ausession.getRybh();
    		    sessionMap.put(key, ausession);
    		    System.out.println("----保存用户权限内存信息----ry:"+
    		    ausession.getRybh()+" qx:"+ausession.getAutoStr());
    		 
    		 System.out.println("用户已存在权限信息");
    	 }catch(Exception e){
    		 e.printStackTrace();
    		 System.out.println("权限保存失败");
    	 }
     }
     
     /**
 	 * 单权限信息更新
 	 * @param ausession
 	 */
      public  static void  updateAuSession(AuthoritySession ausession){
     	 try{
     		 initSession();
     		 String check = checkAuSession(ausession.getRybh());
     		 if(!check.equals("false")){
     		    String key = "au"+ausession.getRybh();
     		   if(ausession.getAutoStr()==null||ausession.getAutoStr().equals("'null'")||ausession.getAutoStr().equals("")){
	                sessionMap.remove(key);
	           }else{
		           sessionMap.put(key, ausession);
	           }
     		   
     		   System.out.println("----更新用户权限内存信息----ry:"+
   	    		    ausession.getRybh()+" qx:"+ausession.getAutoStr());
     		 }
     	 }catch(Exception e){
     		 e.printStackTrace();
     		 System.out.println("权限保存失败");
     	 }
      }
	
    	 /**
    	  * 权限信息删除（人员删除）
    	  * @param rybh
    	  */
    	 public  static void  deleteAuSession(Integer rybh){
        	 try{
        		 initSession();
        		 String key = "au"+rybh;
        		 String check = checkAuSession(rybh);
        		 if(!check.equals("false")){
        		    sessionMap.remove(key);
        		    System.out.println("----删除用户权限内存信息----"+rybh);
        		 }
        	 }catch(Exception e){
        		 e.printStackTrace();
        		 System.out.println("权限删除失败");
         }
    	}
    	 
    	 /**
    		 * 批量权限信息新(用户登录--保存,用户角色修改,角色权限修改时候更新)
    		 * @param ausession
    		 */
    	     public  static void  updateAuSessionList(List<AuthoritySession> ausesslist){
    	    	 try{
    	    		 initSession();
    	    		 System.out.println("----批量修改用户权限内存信息----");
    	    		 if(ausesslist!=null&&ausesslist.size()>0){
    	    		   for(AuthoritySession ausession:ausesslist){
    	    		      updateAuSession(ausession);
    	    		   }
    	    		 }
    	    	 }catch(Exception e){
    	    		 e.printStackTrace();
    	    		 System.out.println("权限保存失败");
    	    	 }
    	     }
    	     
    	     public static void initSession(){
    	    	 if(sessionMap==null){
    	    		 sessionMap = new HashMap<String,AuthoritySession>() ;
    	    	 }
    	     }


}