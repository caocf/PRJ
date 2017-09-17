package com.sts.favor.dao;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.common.dao.impl.BaseDaoDB;
import com.sts.favor.model.LineFavor;
import com.sts.favor.model.StationFavor;
import com.sts.favor.model.TransferFavor;
import com.sts.smartbus.model.BusLineForQueryByName;

public class FavorDao 
{
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void SaveStationFavor(StationFavor stationFavor)
	{
		this.hibernateTemplate.save(stationFavor);
	}
	
	public void SaveLineFavor(LineFavor lineFavor)
	{
		this.hibernateTemplate.save(lineFavor);
	}
	
	public void SaveTransferFavor(TransferFavor transferFavor)
	{
		this.hibernateTemplate.save(transferFavor);
	}
	
	public List<StationFavor> QueryAllStationFavor(int userID)
	{
		String hql="from StationFavor s where s.userID="+userID;
		return (List<StationFavor>) this.hibernateTemplate.find(hql);
	}
	
	public List<LineFavor> QueryAllLineFavor(int userID)
	{
		String hql="from LineFavor l where l.userID="+userID;
		return (List<LineFavor>) this.hibernateTemplate.find(hql);
	}
	
	public List<TransferFavor> QueryAllTransferFavor(int userID)
	{
		String hql="from TransferFavor t where t.userID="+userID;
		return (List<TransferFavor>) this.hibernateTemplate.find(hql);
	}
	
	public List<StationFavor> QueryHasStationFavor(StationFavor stationFavor)
	{
		String hql="from StationFavor s where s.userID="+stationFavor.getUserID()+" and s.stationID="+stationFavor.getStationID();
		return (List<StationFavor>) this.hibernateTemplate.find(hql);
	}
	
	
	public List<LineFavor> QueryHasLineFavor(LineFavor lineFavor)
	{
		String hql="from LineFavor l where l.userID="+lineFavor.getUserID()+" and l.lineID="+lineFavor.getLineID()+" and l.direct="+lineFavor.getDirect();
		return (List<LineFavor>) this.hibernateTemplate.find(hql);
	}
	
	public List<TransferFavor> QueryHasTransferFavor(TransferFavor transferFavor)
	{
		String hql="from TransferFavor t where t.userID="+transferFavor.getUserID()+" and t.startLantitude='"+transferFavor.getStartLantitude()+"' and t.startLongtitude='"+transferFavor.getStartLongtitude()+"' and t.endLantitude='"+transferFavor.getEndLantitude()+"' and t.endLongtitude='"+transferFavor.getEndLongtitude()+"' and t.startName='"+transferFavor.getStartName()+"' and t.endName='"+transferFavor.getEndName()+"'";
		return (List<TransferFavor>) this.hibernateTemplate.find(hql);
	}
	
	public LineFavor GetLineById(int id)
	{
		return this.hibernateTemplate.get(LineFavor.class, id);
	}
	
	public StationFavor GetStationById(int id)
	{
		return this.hibernateTemplate.get(StationFavor.class, id);
	}
	
	public TransferFavor GetTransferById(int id)
	{
		return this.hibernateTemplate.get(TransferFavor.class, id);
	}
	
	public void deleteLineByIDs(LineFavor line)
	{
		this.hibernateTemplate.delete(line);
	}
	
	public void deleteStationByIDs(StationFavor stationFavor)
	{
		this.hibernateTemplate.delete(stationFavor);
	}
	
	public void deleteTransferByIDs(TransferFavor transferFavor)
	{
		this.hibernateTemplate.delete(transferFavor);
	}
}
