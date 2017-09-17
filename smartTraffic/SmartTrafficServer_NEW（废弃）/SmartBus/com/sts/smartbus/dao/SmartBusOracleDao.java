package com.sts.smartbus.dao;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.smartbus.model.BusStation;
import com.sts.smartbus.model.LineOnStation;

public class SmartBusOracleDao {

	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**
	 * 通过id查询站点
	 * @param id 站点id
	 * @return 站点列表数据
	 */
	public List<BusStation> queryStationByStationID(int id)
	{
		String hql="from BusStation b where b.Id="+id;
		
		return (List<BusStation>) this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 站名模糊查询
	 * @param name 站名
	 * @param containBM 是否模糊匹配别名
	 * @return
	 */
	public List<BusStation> queryStationByStationName(String name,boolean contaionBM,int limit)
	{
		String hql="from BusStation b where b.Name like '%"+name+"%'";
		
		if(contaionBM)
			hql+=" or b.Rename like '%"+name+"%'";
		
		return (List<BusStation>) this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 站点默认查询(不匹配别名)
	 * @param name 站名
	 * @return
	 */
	public List<BusStation> queryStationByStationName(String name,int limit)
	{
		return queryStationByStationName(name, false,limit);
	}
	

}
