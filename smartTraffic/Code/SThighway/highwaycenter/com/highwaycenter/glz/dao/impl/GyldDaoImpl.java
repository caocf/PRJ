package com.highwaycenter.glz.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.highwaycenter.glz.dao.GyldDao;
import com.highwaycenter.glz.model.HGlzGlzGyld;

@Repository("gylddao")
public class GyldDaoImpl extends BaseDaoDB<HGlzGlzGyld> implements GyldDao{

	@Override
	public BaseQueryRecords selectGyldgkList(Integer page, Integer rows,String stationId, String selectvalue) {
		StringBuffer hql = new StringBuffer("select new com.highwaycenter.bean.Gyldgk(a.id, a.code, "
			+ "a.type, a.stationId,a.districtId, a.sectionName, a.startStake,a.startStake2, a.startStakeName,"
			+" a.endStake,a.endStake2, a.endStakeName, a.manageLength) from HGlzGlzGyld a where 1=1 ");
		if(stationId!=null&&!stationId.equals("")){
			hql.append(" and a.stationId ='"+stationId+"' ");
		}
		return super.find(new HQL(hql.toString()),page,rows);
	}

	@Override
	public HGlzGlzGyld selectGyldxq(String id) {
		return super.findUnique(new HGlzGlzGyld(), "id", id);
	}

}
