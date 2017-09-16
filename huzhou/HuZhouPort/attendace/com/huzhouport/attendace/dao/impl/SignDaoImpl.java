package com.huzhouport.attendace.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.dao.SignDao;
import com.huzhouport.attendace.model.Sign;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.common.util.ChangeType;

public class SignDaoImpl extends BaseDaoImpl<Sign> implements SignDao {

	public String addSignInfo(Sign sign) throws Exception {
		this.hibernateTemplate.save(sign);
		return null;
	}

	public int countPageSignInfo(Sign sign, String condition) throws Exception {
		String hql = "select count(*)  from Sign s, User u,Location l where s.signUser=u.userId and s.signLocation=l.locationID ";
		if(sign.getSignStatus()!=2){
			hql += "and s.signStatus="
				+ sign.getSignStatus();
		}
		if (null != condition && condition != "") {
			hql += " and (" + condition + ")";
		}
		return this.countEForeignKey(sign, hql);
	}

	public List<?> searchSignInfo(Sign sign, String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = " select s,u,l from Sign s, User u,Location l where s.signUser=u.userId and s.signLocation=l.locationID ";
		if(sign.getSignStatus()!=2){
			hql += "and s.signStatus="
				+ sign.getSignStatus();
		}
		if (null != condition && condition != "") {
			hql += " and (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(sign, hql, startSet, maxSet);
		return list;
	}

	public Map<String, Integer> findSignByCondition(Sign sign) throws Exception {
		String hql = " select count(s.signStatus) from Sign s where DATE_FORMAT(s.signTime,'%Y-%m-%d')='"
				+ ChangeType.changeYMD(sign.getSignTime())
				+ "' and s.signStatus=0 and s.signUser=" + sign.getSignUser();// 当前签到数
		String hql1 = " select count(s.signStatus) from Sign s where DATE_FORMAT(s.signTime,'%Y-%m-%d')='"
				+ ChangeType.changeYMD(sign.getSignTime())
				+ "' and s.signStatus=1 and s.signUser=" + sign.getSignUser();// 当前签退数
		int a = Integer.valueOf(this.hibernateTemplate.find(hql).get(0)
				.toString());
		int b = Integer.valueOf(this.hibernateTemplate.find(hql1).get(0)
				.toString());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("in", a);
		map.put("out", b);
		return map;
	}

	public List<?> seeSignInfoID(Sign sign) throws Exception {
		String hql = "select new map(l.locationName as locationName,l.longitude as longitude,l.latitude as latitude,u.name as name,"
				+ "s.signID as signID,s.signTime as signTime) from Sign s,Location l,User u where s.signLocation=l.locationID and s.signUser=u.userId and s.signID="
				+ sign.getSignID();
		return this.hibernateTemplate.find(hql);
	}
}
