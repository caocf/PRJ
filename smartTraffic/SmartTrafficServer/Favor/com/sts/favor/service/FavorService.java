package com.sts.favor.service;

import java.util.List;

import com.sts.favor.dao.FavorDao;
import com.sts.favor.model.LineFavor;
import com.sts.favor.model.StationFavor;
import com.sts.favor.model.TransferFavor;

public class FavorService 
{
	FavorDao favorDao;

	public FavorDao getFavorDao() {
		return favorDao;
	}

	public void setFavorDao(FavorDao favorDao) {
		this.favorDao = favorDao;
	}
	
	/**
	 * 保存
	 * @param stationFavor
	 */
	public int SaveStationFavor(int userid,int stationid,String stationName,String lan,String lon)
	{
		StationFavor stationFavor=new StationFavor();
		stationFavor.setUserID(userid);
		stationFavor.setStationID(stationid);
		stationFavor.setStationName(stationName);
		stationFavor.setLantitude(lan);
		stationFavor.setLongtitude(lon);
		
		if(HasStationFavor(stationFavor))
			return 0;
		
		this.favorDao.SaveStationFavor(stationFavor);
		return 1;
	}
	
	public int SaveLineFavor(int userid,int lineid,int direct)
	{
		LineFavor lineFavor=new LineFavor();
		lineFavor.setUserID(userid);
		lineFavor.setLineID(lineid);
		lineFavor.setDirect(direct);
		
		if(HasLineFavor(lineFavor))
			return 0;
		
		this.favorDao.SaveLineFavor(lineFavor);
		return 1;
	}
	
	public int SaveTransferFavor(int userid,String slantitude,String slongtitude,String elantitude,String elongtitude,String start,String end)
	{
		TransferFavor transferFavor=new TransferFavor();
		transferFavor.setStartLantitude(slantitude);
		transferFavor.setStartLongtitude(slongtitude);
		transferFavor.setEndLantitude(elantitude);
		transferFavor.setEndLongtitude(elongtitude);
		transferFavor.setUserID(userid);
		transferFavor.setStartName(start);
		transferFavor.setEndName(end);
		
		if(HasTransferFavor(transferFavor))
			return 0;
		
		this.favorDao.SaveTransferFavor(transferFavor);
		return 1;
	}
	
	/**
	 * 查找当前用户所有
	 * @param userID
	 * @return
	 */
	public List<StationFavor> QueryAllStationFavor(int userID)
	{
		return this.favorDao.QueryAllStationFavor(userID);
	}
	
	public List<LineFavor> QueryAllLineFavor(int userID)
	{
		return this.favorDao.QueryAllLineFavor(userID);
	}
	
	public List<TransferFavor> QueryAllTransferFavor(int userID)
	{
		return this.favorDao.QueryAllTransferFavor(userID);
	}
	
	/**
	 * 根据具体内容查找
	 * @param stationFavor
	 * @return
	 */
	public List<StationFavor> QueryHasStationFavor(StationFavor stationFavor)
	{
		return this.favorDao.QueryHasStationFavor(stationFavor);
	}
	
	
	public List<LineFavor> QueryHasLineFavor(LineFavor lineFavor)
	{
		return this.favorDao.QueryHasLineFavor(lineFavor);
	}
	
	public List<TransferFavor> QueryHasTransferFavor(TransferFavor transferFavor)
	{
		return this.favorDao.QueryHasTransferFavor(transferFavor);
	}
	
	/**
	 * 是否已有收藏
	 * @param stationFavor
	 * @return
	 */
	public boolean HasStationFavor(StationFavor stationFavor)
	{
		List<StationFavor> temp=this.QueryHasStationFavor(stationFavor);
		if(temp!=null && temp.size()>0)
			return true;
		return false;
	}
	
	public boolean HasLineFavor(LineFavor lineFavor)
	{
		List<LineFavor> temp=this.QueryHasLineFavor(lineFavor);
		if(temp!=null && temp.size()>0)
			return true;
		return false;
	}
	
	public boolean HasTransferFavor(TransferFavor transferFavor)
	{
		List<TransferFavor> temp=this.QueryHasTransferFavor(transferFavor);
		if(temp!=null && temp.size()>0)
			return true;
		return false;
	}
	
	public static final String SQLIT_CHAR=",";
	
	public boolean deleteLine(String ids,int userid)
	{
		String[] temps=ids.split(SQLIT_CHAR);
		
		try
		{
			for(int i=0;i<temps.length;i++)
			{
				int id=Integer.valueOf(temps[i]);
				LineFavor lineFavor=this.favorDao.GetLineById(id);
				
				if(lineFavor==null)
					continue;
				
				if(lineFavor.getUserID()==userid)
					this.favorDao.deleteLineByIDs(lineFavor);
			}
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean deleteStation(String ids,int userid)
	{
		String[] temps=ids.split(SQLIT_CHAR);
		
		try
		{
			for(int i=0;i<temps.length;i++)
			{
				int id=Integer.valueOf(temps[i]);
				StationFavor stationFavor=this.favorDao.GetStationById(id);
				
				if(stationFavor==null)
					continue;
				
				if(stationFavor.getUserID()==userid)
					this.favorDao.deleteStationByIDs(stationFavor);
			}
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean deleteTransfer(String ids,int userid)
	{
		String[] temps=ids.split(SQLIT_CHAR);
		
		try
		{
			for(int i=0;i<temps.length;i++)
			{
				int id=Integer.valueOf(temps[i]);
				TransferFavor transferFavor=this.favorDao.GetTransferById(id);
				
				if(transferFavor==null)
					continue;
				
				if(transferFavor.getUserID()==userid)
					this.favorDao.deleteTransferByIDs(transferFavor);
			}
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
