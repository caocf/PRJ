package com.SmartTraffic.video.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("videoAccessDao")
public class VideoAccessDao {
	private SessionFactory videosessionFactory ;

	public List<?> selectList(String sql){
		List<?> list = new ArrayList<>();
		Session session = videosessionFactory.openSession();
		session.beginTransaction();
		try{
			Query q = session.createSQLQuery(sql);
		    q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		    list = q.list();	
		    session.getTransaction().commit();
	
	} catch (Exception e1) {
		e1.printStackTrace();
		System.out.println(e1.getMessage());
	} finally {
	    session.close();
	}
	return list;	
	}
	
	
	public Object selectPlayInfo(int cameraid){
		String sql = "select * from v_playview where CAMERAID ="+cameraid;
		List<?> list = this.selectList(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
	    }else{
	    	return null;
	    }
		
	}
	
	
	public List<?> selectRegionList(int unitId){

		String sql = "select "
				+ "{region.REGION_ID as regionId,region.NAME as regionName, region.PARENT_ID as regionHigh,"
				+ " region. REGION_LEVEL as regionLevel,region.CONTROL_UNIT_ID as regionCellLsh } "
				+ " from REGION_INFO region start with region.PARENT_ID=0  and region.CONTROL_UNIT_ID="+unitId+" connect by  "
				+ " region.PARENT_ID=prior region.REGION_ID  ";
		return  this.selectList(sql);
	}
	
	public List<?> selectControlList(){
		String sql = "select {control.CONTROL_UNIT_ID as controlUnitID,control.NAME as controlUnitName,control.PARENT_ID as "
				+ " upControlUnitID} "
				+ " from CONTROL_UNIT control "
				+ " start with control.PARENT_ID=1 "
				+ " connect by  control.PARENT_ID=prior control.CONTROL_UNIT_ID";
		return  this.selectList(sql);
	}
	
	
	public List<?> selectCamera(int regionId){
		String sql = "select {camera.CAMERAID as cameraId,camera.CAMERANAME as deviceName, camera.CONTROLUNITID as controlunitId,"
				+ " camera.REGIONID as regionId } "
				+ " from V_PLAYVIEW  camera where camera.REGIONID = "+regionId;
		return  this.selectList(sql);
	}
	
	public SessionFactory getVideosessionFactory() {
		return videosessionFactory;
	}

	public void setVideosessionFactory(SessionFactory videosessionFactory) {
		this.videosessionFactory = videosessionFactory;
	}

	
	
	
}
