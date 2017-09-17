package com.SmartTraffic.video.dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.SmartTraffic.video.dao.VideoDao;
import com.SmartTraffic.video.model.CameraDto;
import com.SmartTraffic.video.model.STCamera;
import com.SmartTraffic.video.model.STControlUnit;
import com.SmartTraffic.video.model.STRegion;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;

@Repository("videoDao")
public class VideoDaoImpl extends BaseDaoDB implements VideoDao{

	private void saveList(List<?> list){
		 Session session = null;  
		 if(list!=null&&list.size()>0){
			 try{
				 session = super.getCurrentSession();
				// session.beginTransaction(); // 开启事物  
				 Object obj = null;
				 for(int i=0;i<list.size();i++){
					 obj = list.get(i);
					 session.save(obj);
					 if(i%50 == 0){
						 session.flush();
						 session.clear();
					 }
	
				 }
				 session.getTransaction().commit(); // 提交事物  
			 }catch(Exception  e){
				 e.printStackTrace();
				 session.getTransaction().rollback();
				 
			 }finally{
				 
				 session.flush();
				 session.clear();
				// session.close();
			 }
		 }
		 
	}
	
	
	protected  List<?> selectList(String sql){
		List<?> list = new ArrayList<>();
		Session session = super.getCurrentSession();
		try{
			Query q = session.createSQLQuery(sql);
		    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		    list = q.list();	
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	    
	}
	     return list;	
	}
	

	@Override
	public void saveCameras(List<STCamera> cameras) {
		saveList(cameras);		
	}

	@Override
	public void saveRegions(List<STRegion> regions) {
		saveList(regions);
		
	}

	@Override
	public void saveControlUnits(List<STControlUnit> units) {
		saveList(units);
		
	}

	@Override
	public void deleteAllTable(String tablename) {
		String sql = "delete  from  "+tablename;
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<STCamera> selectSTCameraByRegion(int regionId) {
		return (List<STCamera>) super.find(new STCamera(),"regionId",regionId).getData();
	}

	@Override
	public List<STRegion> selectRegionByUnit(int unitId) {

		String sql = "select {region.*} from z_st_region region start with region.region_high=0  and region.regionCell_lsh="+unitId+" connect by  "
				+ "region.region_high=prior region.region_id  ";
		  try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql).addEntity("region",STRegion.class);
				return q.list();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	}

	@Override
	public List<STControlUnit> selectControlUnit() {
		String sql = "select {control.*} from z_st_control control start with control.upcontrolunit_id=1 connect by  control.upcontrolunit_id=prior control.controlunit_id";
		  try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql).addEntity("control",STControlUnit.class);
				return q.list();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	}

	@Override
	public List<STCamera> selectSTCameraByUnit(int unitId) {
		return (List<STCamera>) super.find(new STCamera(),"controlunitId",unitId).getData();
	}
	
	@Override
	public void updateLonlat(int cameraId, float longitude, float latitude) {
		String sql = "update Z_AC_CAMERA_INFO set longitude ="+longitude+" , latitude ="+latitude +" where camera_id"
				+ "="+cameraId;
		super.update(new SQL(sql));
		
	}
	

	@Override
	public Object selectCameraItem(int cameraId) {
		String sql = "select * from Z_AC_CAMERA_INFO where CAMERA_ID = "+cameraId;
		final Query q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
	    List list = q.list();
	    if(list!=null&&list.size()>0){
	    	   return list.get(0);
	    	}else{
	    		return null;
	    	}
	}

	@Override
	public void saveOrUpdateLonLat(int cameraId, float longitute) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<?> selectRegionList(int unitId) {
		String sql = "select REGION_ID as regionId, NAME as regionName, PARENT_ID as regionHigh,"
				+ " REGION_LEVEL as regionLevel, CONTROL_UNIT_ID as regionCellLsh  "
				+ " from Z_AC_REGION_INFO where flag is not null start with PARENT_ID=0  and  CONTROL_UNIT_ID="+unitId+" connect by  "
				+ "  PARENT_ID=prior  REGION_ID";
		return  this.selectList(sql);
	}


	@Override
	public List<?> selectControlList() {
		String sql = "select  CONTROL_UNIT_ID as controlUnitID, NAME as controlUnitName, PARENT_ID as "
				+ " upControlUnitID "
				+ " from Z_AC_CONTROL_UNIT  "
				+ " start with  PARENT_ID=1 "
				+ " connect by   PARENT_ID=prior  CONTROL_UNIT_ID order siblings by  CONTROL_ORDER ";
		return  this.selectList(sql);
	}


	@Override
	public List<?> selectCamera(int regionId) {
		String sql = "select CAMERA_ID as cameraId, NAME as deviceName, "
				+ "  REGION_ID as regionId "
				+ " from Z_AC_CAMERA_INFO  camera where flag is not null and REGION_ID = "+regionId;
		return  this.selectList(sql);
	}

	@Override
	public void plUpdate(List<CameraDto> list) {
		 Session session = null;  
		 if(list!=null&&list.size()>0){
			 try{
				 session = super.getSessionFactory().openSession();
				 session.beginTransaction(); // 开启事物  
				 for(int i=0;i<list.size();i++){
					CameraDto obj = list.get(i);
					String sql = "update Z_AC_CAMERA_INFO set longitude ="+obj.getLon()+" , latitude ="+obj.getLan()+" where INDEX_CODE="+obj.getIndexCode();
					 session.createSQLQuery(sql).executeUpdate();  
					 if(i%50 == 0){
						 session.flush();
						 session.clear();
					 }
	
				 }
				 session.getTransaction().commit(); // 提交事物  
			 }catch(Exception  e){
				 e.printStackTrace();
				 session.getTransaction().rollback();
				 
			 }finally{
				 session.flush();
				 session.clear();
				 session.close();
				 
				
				// session.close();
			 }
		 }
	
		
	}


	@Override
	public List<?> selectAllCamera() {
		String sql = "select * "
				+ " from Z_AC_CAMERA_INFO  camera where flag is not null ";
		return  this.selectList(sql);
	}


	@Override
	public List<?> selectAllSameCamera() { 
		List list  = new ArrayList<>();
		String sql = "select * from Z_AC_CAMERA_INFO where LATITUDE is not null and LONGITUDE is not null";
		final Query q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
	    list = q.list();
	    if(list!=null&&list.size()>0){
	    	   return list;
	    }else{
	    		return null;
	    }
	}


	@Override
	public void updateSameCamera(List<Map<String,Object>> list) {
		 Session session = null;  
		
		  try{
				 session = super.getCurrentSession();
						
		   for(int i=0;i<list.size();i++){
			System.out.println(list.size());
			Map<String,Object> map = list.get(i);
			String name = (String) map.get("NAME");
			String  lat= (String) map.get("LATITUDE");
			String lon = (String) map.get("LONGITUDE");
			String  index= (String) map.get("INDEX_CODE");
			System.out.println(name);
			String hql = String.format("update Z_AC_CAMERA_INFO set LONGITUDE = '%s',LATITUDE = '%s' where NAME ='%s' ",lon,lat,name,index);
			Query q = session.createSQLQuery(hql);
			q.executeUpdate();
			if(i%50 == 0){
				 session.flush();
				 session.clear();
			 }
		 
		 
		 }

		 }catch(Exception  e){
			 e.printStackTrace();
			 session.getTransaction().rollback();
			 
		 }finally{
			 session.flush();
			 session.clear();
			// session.close();
		 }
	}


}
