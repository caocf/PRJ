package com.sts.favor.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.favor.model.AppBikeStationFavor;
import com.sts.favor.model.AppLineFavor;
import com.sts.favor.model.AppStationFavor;
import com.sts.favor.model.AppTransferFavor;


public class AppFavorDao 
{
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void SaveStationFavor(AppStationFavor stationFavor)
	{
		this.hibernateTemplate.save(stationFavor);
	}
	
	public void SaveBikeStationFavor(AppBikeStationFavor stationFavor)
	{
		this.hibernateTemplate.save(stationFavor);
	}
	
	public void SaveLineFavor(AppLineFavor lineFavor)
	{
		this.hibernateTemplate.save(lineFavor);
	}
	
	public void SaveTransferFavor(AppTransferFavor transferFavor)
	{
		this.hibernateTemplate.save(transferFavor);
	}

	public List<?> QueryAllBikeStationFavor(int userID)
	{
		String hql="from AppBikeStationFavor s where s.userID="+userID;
		return this.hibernateTemplate.find(hql);
	}
	
	public List<?> QueryAllStationFavor(int userID)
	{
		String hql="from AppStationFavor s where s.userID="+userID;
		return this.hibernateTemplate.find(hql);
	}
	
	public List<?> QueryAllLineFavor(int userID)
	{
		String hql="from AppLineFavor l where l.userID="+userID;
		return this.hibernateTemplate.find(hql);
	}
	
	public List<?> QueryAllTransferFavor(int userID)
	{
		String hql="from AppTransferFavor t where t.userID="+userID;
		return this.hibernateTemplate.find(hql);
	}
	
	
	public void deleteLineByIDs(AppLineFavor line)
	{
		this.hibernateTemplate.delete(line);
	}
	
	public void deleteStationByIDs(AppStationFavor stationFavor)
	{
		this.hibernateTemplate.delete(stationFavor);
	}
	
	public void deleteBikeStationByIDs(AppBikeStationFavor stationFavor)
	{
		this.hibernateTemplate.delete(stationFavor);
	}
	
	public void deleteTransferByIDs(AppTransferFavor transferFavor)
	{
		this.hibernateTemplate.delete(transferFavor);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AppStationFavor> QueryHasStationFavor(AppStationFavor stationFavor)
	{
		String hql="from AppStationFavor s where s.userID="+stationFavor.getUserID()+" and s.stationID="+stationFavor.getStationID();
		return (List<AppStationFavor>) this.hibernateTemplate.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AppBikeStationFavor> QueryHasBikeStationFavor(AppBikeStationFavor stationFavor)
	{
		String hql="from AppBikeStationFavor s where s.userID="+stationFavor.getUserID()+" and s.stationID="+stationFavor.getStationID();
		return (List<AppBikeStationFavor>) this.hibernateTemplate.find(hql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AppLineFavor> QueryHasLineFavor(AppLineFavor lineFavor)
	{
		String hql="from AppLineFavor l where l.userID="+lineFavor.getUserID()+" and l.lineID="+lineFavor.getLineID()+" and l.direct="+lineFavor.getDirect();
		return (List<AppLineFavor>) this.hibernateTemplate.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AppTransferFavor> QueryHasTransferFavor(AppTransferFavor transferFavor)
	{
		String hql="from AppTransferFavor t where t.userID="+transferFavor.getUserID()+" and t.startLantitude='"+transferFavor.getStartLantitude()+"' and t.startLongtitude='"+transferFavor.getStartLongtitude()+"' and t.endLantitude='"+transferFavor.getEndLantitude()+"' and t.endLongtitude='"+transferFavor.getEndLongtitude()+"' and t.startName='"+transferFavor.getStartName()+"' and t.endName='"+transferFavor.getEndName()+"'";
		return (List<AppTransferFavor>) this.hibernateTemplate.find(hql);
	}
	
	
	public AppLineFavor GetLineById(int id)
	{
		return this.hibernateTemplate.get(AppLineFavor.class, id);
	}
	
	public AppStationFavor GetStationById(int id)
	{
		return this.hibernateTemplate.get(AppStationFavor.class, id);
	}
	
	public AppBikeStationFavor GetBikeStationById(int id)
	{
		return this.hibernateTemplate.get(AppBikeStationFavor.class, id);
	}
	
	public AppTransferFavor GetTransferById(int id)
	{
		return this.hibernateTemplate.get(AppTransferFavor.class, id);
	}
}
