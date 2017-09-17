package com.highwaycenter.glz.dao.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.QljcxjcBean;
import com.highwaycenter.bean.QljcxjcmxBean;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.dao.XcQljcxjcDao;
import com.highwaycenter.glz.model.HGlzXcQljcxjc;
@Repository("qljcxjcdao")
public class XcQljcxjcDaoImpl extends BaseDaoDB<HGlzXcQljcxjc> implements  XcQljcxjcDao{

	@Override
	public BaseQueryRecords selectQljcxjcList(Integer page, Integer rows,
			String stationId, String bridgeId, String selectvalue) {
		StringBuffer sql = new StringBuffer("select a.id,b.sectionName, a.bridgeId, c.name,"+
			"a.bridgePey,a.checkDate,a.maintenanceOrg,a.recorder,a.officer,a.nextCheckDate,a.judgeType,"
			+ "a.weather,a.facadeFileId,a.lateralFileId)  from h_glz_xc_qljcxjc as a left join h_glz_glz_gyld as b on "
			+" a.partId = b.id left join h_glz_qlgk as c on a.bridgeId =c.id where 1=1 ");
        if(stationId!=null&&!stationId.trim().equals("")){
        	sql.append(" and a.stationId ='"+stationId+"' ");
        }
        if(bridgeId!=null&&!bridgeId.trim().equals("")){
        	sql.append(" and a.bridgeId ='"+bridgeId+"' ");
        }
		List list = new ArrayList();
        BaseQueryRecords records = super.find(new SQL(sql.toString()),page,rows);
        List<Object[]> objlist = (List<Object[]>) records.getData();
        if(objlist!=null&&objlist.size()>0){
        	Iterator<Object[]> it = objlist.iterator();
        	while(it.hasNext()){
        		Object[] obj = it.next();
        		QljcxjcBean  qljc = new QljcxjcBean();
        		qljc.setId((String)obj[0]);
        		qljc.setSectionName((String)obj[1]);
        		qljc.setBridgeId((String)obj[2]);
        		qljc.setBridgeName((String)obj[3]);
        		qljc.setBridgePey((String)obj[4]);
        		qljc.setCheckDate((Timestamp)obj[5]);
        		qljc.setMaintenanceOrg((String)obj[6]);
        		qljc.setRecorder((String)obj[7]);
        		qljc.setOfficer((String)obj[8]);
        		qljc.setNextCheckDate((Timestamp)obj[9]);
        		qljc.setJudgeType((String)obj[10]);
        		qljc.setWeather((String)obj[11]);
        		qljc.setFacadeFileId((String)obj[12]);
        		qljc.setLateralFileId((String)obj[13]);
        		list.add(qljc);	
          }
        }
        	records.setData(list);
        
		return records;
	}

	@Override
	public QljcxjcBean selectQljcxjcXq(String id) {
		String sql = "select a.id,b.sectionName, a.bridgeId, c.name,"+
				"a.bridgePey,a.checkDate,a.maintenanceOrg,a.recorder,a.officer,a.nextCheckDate,a.judgeType,"
				+ "a.weather,a.facadeFileId,a.lateralFileId)  from h_glz_xc_qljcxjc as a left join h_glz_glz_gyld as b on "
				+" a.partId = b.id left join h_glz_qlgk as c on a.bridgeId =c.id where id='"+id+"'";
		
		Object[] obj = (Object[]) super.findUnique(new SQL(sql));
		QljcxjcBean  qljc = new QljcxjcBean();
		if(obj!=null){
		qljc.setId((String)obj[0]);
		qljc.setSectionName((String)obj[1]);
		qljc.setBridgeId((String)obj[2]);
		qljc.setBridgeName((String)obj[3]);
		qljc.setBridgePey((String)obj[4]);
		qljc.setCheckDate((Timestamp)obj[5]);
		qljc.setMaintenanceOrg((String)obj[6]);
		qljc.setRecorder((String)obj[7]);
		qljc.setOfficer((String)obj[8]);
		qljc.setNextCheckDate((Timestamp)obj[9]);
		qljc.setJudgeType((String)obj[10]);
		qljc.setWeather((String)obj[11]);
		qljc.setFacadeFileId((String)obj[12]);
		qljc.setLateralFileId((String)obj[13]);
		}
		return qljc;
		
	}

	@Override
	public List<QljcxjcmxBean> selecQljcxjcmxXq(String checkId) {
		String sql = "select a.id,b.code,b.name,a.type, a.area,a.opinion,a.judgeType from h_glz_xc_qljcxjcmx as a ,"
				+ "h_glz_xc_jcbj as b where a.componentId = b.id and a.checkId = '"+checkId+"'";
		List<QljcxjcmxBean> list = new ArrayList<QljcxjcmxBean>();
		
		
		List<Object[]> objlist =  (List<Object[]>) super.find(new SQL(sql)).getData();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				QljcxjcmxBean  qljcmx = new QljcxjcmxBean();
				
				qljcmx.setId((String)obj[0]);
				qljcmx.setComponentCode((String)obj[1]);
				qljcmx.setComponentName((String)obj[2]);
				qljcmx.setType((String)obj[3]);
				qljcmx.setArea((String)obj[4]);
				qljcmx.setOpinion((String)obj[5]);
				qljcmx.setJudgeType((String)obj[6]);
				list.add(qljcmx);
			}
		}
		
		return list;
	}

	@Override
	public List<Object[]> selectPropertyList(String key1, String key2) {
		String sql = "select distinct a."+key1 +",a."+key2+" from h_glz_xc_qljcxjcmx a";
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public List<selectListBean> selectQlNameList() {
		String sql = "select distinct a.bridgeId ,b.name from h_glz_xc_qljcxjc as a,h_glz_qlgk as b where a.bridgeId =b.id";
		List list = new ArrayList();
		List<Object[]> objlist = (List<Object[]>) super.find(new SQL(sql)).getData();
        if(objlist!=null&&objlist.size()>0){
        	Iterator<Object[]> it = objlist.iterator();
        	while(it.hasNext()){
        		Object[] obj = it.next();
        		selectListBean qlmc = new selectListBean();
        		qlmc.setId_string((String)obj[0]);
        		qlmc.setName((String)obj[1]);
        		list.add(qlmc);
        	}
        }
		return list;
	}


}
