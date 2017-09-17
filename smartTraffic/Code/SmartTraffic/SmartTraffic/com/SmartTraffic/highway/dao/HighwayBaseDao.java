package com.SmartTraffic.highway.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.SmartTraffic.highway.model.GczModel;
import com.SmartTraffic.highway.model.HJdTransportData;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Repository("highwayBaseDao")
public class HighwayBaseDao {
	private SessionFactory hwsessionFactory ;
	public SessionFactory getHwsessionFactory() {
		return hwsessionFactory;
	}

	@Autowired
	public void setHwsessionFactory(SessionFactory hwsessionFactory) {
		this.hwsessionFactory = hwsessionFactory;
	}
	
	/*public SessionFactory getSessionFactory() {
		return hwsessionFactory;
	}

	@Autowired
	// 注入sessionFacory Bean
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hwsessionFactory = sessionFactory;
	}*/

	/**
	 * 获得当前的hibernate session。
	 * 
	 * @return
	 */
	protected Session getCurrentSession() {
		return hwsessionFactory.getCurrentSession();
	}

	public List<?> selectJdInfo(){
		List<?> list = new ArrayList<>();
		String sql = "select  lxmc, tddm,tjsj  from h_jd_transdata where tjsj in ("
				+ "select max(tjsj) from h_jd_transdata where tjlx = 1)";
		Session session = getCurrentSession();
		////session.beginTransaction();
		try{
			Query q = session.createSQLQuery(sql);
		    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		    list = q.list();	
		 //   //session.getTransaction().commit();
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	 //   //session.close();
	}
	System.out.println("end.>>>>>>>"+new Date());
	return list;	
	}
	
	
	public HJdTransportData selectDayTransdata(String lxdm,String lxjc){
		List<HJdTransportData> list = new ArrayList<HJdTransportData>();
		Session session = getCurrentSession();
		String sql = String.format("select  *  from h_jd_transdata  where tjlx = 1 and lxdm = '%s'"
				+ " ",lxdm);
		if(!StringUtils.isBlank(lxjc)){
			sql = sql +" and lxmc like '%"+lxjc+"%'";
		}
		sql = sql +" order by tjsj desc";
		try {
			SQLQuery q = session.createSQLQuery(sql.toString());
			 q.addScalar("tddm",StandardBasicTypes.INTEGER);
			 q.addScalar("lxdm",StandardBasicTypes.STRING);
			 q.addScalar("lxmc", StandardBasicTypes.STRING);
			 q.addScalar("gclc",StandardBasicTypes.DOUBLE);
			 q.addScalar("jdcdlshj", StandardBasicTypes.INTEGER);
			 q.addScalar("jdczrshj", StandardBasicTypes.INTEGER);
			 q.addScalar("qcdlshj", StandardBasicTypes.INTEGER);
			 q.addScalar("qczrshj", StandardBasicTypes.INTEGER);
			 q.addScalar("xxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("zxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("dxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("tdxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("jzxc", StandardBasicTypes.INTEGER);			 
			 q.addScalar("zxkc", StandardBasicTypes.INTEGER);
			 q.addScalar("dkc", StandardBasicTypes.INTEGER);
			 q.addScalar("mtc", StandardBasicTypes.INTEGER);
			 q.addScalar("tlj", StandardBasicTypes.INTEGER);
			 q.addScalar("rlc", StandardBasicTypes.INTEGER);
			 q.addScalar("clc", StandardBasicTypes.INTEGER);
			 q.addScalar("zxc", StandardBasicTypes.INTEGER);
			 q.addScalar("xsl", StandardBasicTypes.INTEGER);
			 q.addScalar("syjtl", StandardBasicTypes.INTEGER);
			 q.addScalar("jtyjd",StandardBasicTypes.DOUBLE);
			 q.addScalar("tjsj", StandardBasicTypes.STRING);
			
			 q.setResultTransformer(Transformers.aliasToBean(HJdTransportData.class));
			  list = q.list();
			// //session.getTransaction().commit();
			 
		}catch(Exception e){
			e.printStackTrace();
		} finally {
		 //   //session.close();
		}
		if (list!=null&&list.size() > 0) {
			return list.get(0);
	 }else{
			return null;
	 }
	}
	
	
	
	
	
	
	public Integer selectDir(String lxdm,String fxm){
		List list = new ArrayList();
		String sql = String.format("select sxx from h_sp_sxx where lxdm='%s' and fxm ='%s'",lxdm,fxm);
		Session session = getCurrentSession();
		//session.beginTransaction();
		try{
			Query q = session.createSQLQuery(sql);
		  //  q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		    list = q.list();	
		    //session.getTransaction().commit();
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	    //session.close();
	}
	    Integer dir = 0;
	    if(list!=null&&list.size()>0){
	    	dir = (Integer)list.get(0);
	    }
	    return dir;	
	}
	
	
	public List<?> selectVideoInfo(){
		List<?> list = new ArrayList<>();
		String sql = "select indexCode,name from h_sp_cameradto";
		Session session = getCurrentSession();
		//session.beginTransaction();
		try{
			Query q = session.createSQLQuery(sql);
		    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		    list = q.list();	
		    //session.getTransaction().commit();
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	    //session.close();
	}
	return list;	
	}
	
	
	
	
	public Map  selectNumById(int jtldm){
		//List<?> list = new ArrayList<>();
		Map map = null;
		List<?> list;
		String sql = "select * from h_jd_transdata where tddm = "+jtldm;
		Session session = getCurrentSession();
		//session.beginTransaction();
		try{
			Query q = session.createSQLQuery(sql);
		    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		     list = q.list();	
		    
		    //session.getTransaction().commit();
		    if(list!=null&&list.size()>0){
				map = (Map) list.get(0);
			}
		    
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	    //session.close();
	}	
	System.out.println("end.>>>>>>>"+new Date());
	return map;	
	}//


   public List<GczModel> selectGczInfo(){
	   List<GczModel> list = new ArrayList();
	   String sql = " select dczbh,dczmc,jd,wd ,x.lx,l.lxjc,IFNULL(concat(x.lx, '(',l.lxjc,')'),x.lx) as lxmc from h_jd_jxdczxx x,  h_jd_jxzdjwd j, ("
	   		+ "select distinct lxdm, lxjc , ldqdzh, ldzdzh from h_jc_lxjbxx  ) l"
	   		+ "  where x.dczbh=j.gczbh and (x.dczbh like 's%' or x.dczbh like 'g%') and l.lxdm = x.lx   and l.ldqdzh<=x.zh   and l.ldzdzh>=x.zh ";
	   Session session = getCurrentSession();
		try{
			SQLQuery q = session.createSQLQuery(sql);
			 q.addScalar("dczbh",StandardBasicTypes.STRING);
			 q.addScalar("dczmc",StandardBasicTypes.STRING);
			 q.addScalar("jd", StandardBasicTypes.STRING);
			 q.addScalar("wd",StandardBasicTypes.STRING);
			 q.addScalar("lx",StandardBasicTypes.STRING);
			 q.addScalar("lxjc",StandardBasicTypes.STRING);
			 q.addScalar("lxmc",StandardBasicTypes.STRING);
			 q.setResultTransformer(Transformers.aliasToBean(GczModel.class));	
		     list = q.list();	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
		return list;
	}
		
	}
			
		
   
	public List<?> selectLxinfo(){
		  List<?> list = new ArrayList();
		String sql = "select distinct lxdm,lxmc as lxjc from h_jd_transdata where left(LXDM,1) = 'g' or left(LXDM,1) ='s' order by lxdm  ";
		 Session session = getCurrentSession();
			//session.beginTransaction();
			try{
				Query q = session.createSQLQuery(sql);
			    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
			    list = q.list();	
			    //session.getTransaction().commit();
		
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
		    //session.close();
		}
		return list;	
	}
	
	
	public HJdTransportData selectTransMon(String lxdm,String startTime,String endTime,String lxjc){
		List<HJdTransportData> list = new ArrayList<HJdTransportData>();
		Session session = getCurrentSession();
		String sql = String.format("select avg(gclc) as gclc,avg(jdcdlshj) as jdcdlshj ,avg(jdczrshj) as jdczrshj,"
				+ "avg(qcdlshj) as qcdlshj,avg(qczrshj) as qczrshj,avg(xxhc) as xxhc,"
				+ "avg(zxhc) as zxhc ,avg(dxhc) as dxhc,avg(tdxhc) as tdxhc,avg(jzxc) as jzxc,"
				+ "avg(zxkc) as zxkc,avg(dkc) as dkc,avg(mtc) as mtc,avg(tlj) as tlj,avg(rlc)"
				+ " as rlc,avg(clc) as clc,avg(zxc) as zxc ,avg(xsl) as xsl,"
				+ "avg(jtyjd) as jtyjd from h_jd_transdata where tjsj >= '%s' and "
				+ " tjsj<='%s'   and tjlx = 1 and lxdm ='%s'",
				startTime,endTime,lxdm);
		if(lxjc!=null&&!StringUtils.isBlank(lxjc)){
			sql = sql+" and lxmc like '%"+lxjc+"%'";
		}
		try {
			SQLQuery q = session.createSQLQuery(sql.toString());
			 q.addScalar("gclc",StandardBasicTypes.DOUBLE);
			 q.addScalar("jdcdlshj", StandardBasicTypes.INTEGER);
			 q.addScalar("jdczrshj", StandardBasicTypes.INTEGER);
			 q.addScalar("qcdlshj", StandardBasicTypes.INTEGER);
			 q.addScalar("qczrshj", StandardBasicTypes.INTEGER);
			 q.addScalar("xxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("zxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("dxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("tdxhc", StandardBasicTypes.INTEGER);
			 q.addScalar("jzxc", StandardBasicTypes.INTEGER);			 
			 q.addScalar("zxkc", StandardBasicTypes.INTEGER);
			 q.addScalar("dkc", StandardBasicTypes.INTEGER);
			 q.addScalar("mtc", StandardBasicTypes.INTEGER);
			 q.addScalar("tlj", StandardBasicTypes.INTEGER);
			 q.addScalar("rlc", StandardBasicTypes.INTEGER);
			 q.addScalar("clc", StandardBasicTypes.INTEGER);
			 q.addScalar("zxc", StandardBasicTypes.INTEGER);
			 q.addScalar("xsl", StandardBasicTypes.INTEGER);
			 q.addScalar("jtyjd",StandardBasicTypes.DOUBLE);
			 q.setResultTransformer(Transformers.aliasToBean(HJdTransportData.class));
			 list = q.list();
			 //session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//session.close();
		}
			 
		
		 if (list!=null&&list.size() > 0) {
				return list.get(0);
		 }else{
				return null;
		 }
	}
	
	
	public List selectTransSame(String lxdm,String lxjc){
		List<?> list = new ArrayList<>();
		Session session = getCurrentSession();
		String sql = "select distinct lxdm from h_jd_transdata where lxdm = '"+lxdm+"' and "
				+ " lxmc like '%"+lxjc+"%'  ";
		
		try{
			SQLQuery q = session.createSQLQuery(sql.toString());
		   list = q.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//session.close();
		}
	    return list;
	}
	
	
	
	
	
	
	

	
	

}
