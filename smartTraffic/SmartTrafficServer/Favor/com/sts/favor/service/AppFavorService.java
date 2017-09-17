package com.sts.favor.service;

import java.util.List;

import com.sts.favor.dao.AppFavorDao;
import com.sts.favor.model.AppBikeStationFavor;
import com.sts.favor.model.AppLineFavor;
import com.sts.favor.model.AppStationFavor;
import com.sts.favor.model.AppTransferFavor;

public class AppFavorService {
	AppFavorDao appFavorDao;

	public void setAppFavorDao(AppFavorDao appFavorDao) {
		this.appFavorDao = appFavorDao;
	}

	/*----------------------------------------------------------------------------------------*/

	public int SaveStationFavor(int userid, int stationid,String stationname,String lines) {
		AppStationFavor stationFavor = new AppStationFavor();
		stationFavor.setUserID(userid);
		stationFavor.setStationID(stationid);
		stationFavor.setStationName(stationname);
		stationFavor.setLines(lines);

		if (HasStationFavor(stationFavor))
			return 0;

		this.appFavorDao.SaveStationFavor(stationFavor);
		return 1;
	}
	
	public int SaveBikeStationFavor(int userid, int stationid,String stationname,String address) {
		AppBikeStationFavor stationFavor = new AppBikeStationFavor();
		stationFavor.setUserID(userid);
		stationFavor.setStationID(stationid);
		stationFavor.setStationName(stationname);
		stationFavor.setAddress(address);

		if (HasBikeStationFavor(stationFavor))
			return 0;

		this.appFavorDao.SaveBikeStationFavor(stationFavor);
		return 1;
	}

	public int SaveLineFavor(int userid, int lineid, int direct,String linename,String startname,String endname) {
		AppLineFavor lineFavor = new AppLineFavor();
		lineFavor.setUserID(userid);
		lineFavor.setLineID(lineid);
		lineFavor.setDirect(direct);
		lineFavor.setLinename(linename);
		lineFavor.setStartname(startname);
		lineFavor.setEndname(endname);

		if (HasLineFavor(lineFavor))
			return 0;

		this.appFavorDao.SaveLineFavor(lineFavor);
		return 1;
	}

	public int SaveTransferFavor(int userid, double slantitude,
			double slongtitude, double elantitude, double elongtitude,
			String start, String end) {
		AppTransferFavor transferFavor = new AppTransferFavor();
		transferFavor.setStartLantitude(slantitude);
		transferFavor.setStartLongtitude(slongtitude);
		transferFavor.setEndLantitude(elantitude);
		transferFavor.setEndLongtitude(elongtitude);
		transferFavor.setUserID(userid);
		transferFavor.setStartName(start);
		transferFavor.setEndName(end);

		if (HasTransferFavor(transferFavor))
			return 0;

		this.appFavorDao.SaveTransferFavor(transferFavor);
		return 1;
	}

	/*----------------------------------------------------------------------------------------*/

	public List<?> QueryAllStationFavor(int userID) {
		return this.appFavorDao.QueryAllStationFavor(userID);
	}
	
	public List<?> QueryAllBikeStationFavor(int userID) {
		return this.appFavorDao.QueryAllBikeStationFavor(userID);
	}

	public List<?> QueryAllLineFavor(int userID) {
		return this.appFavorDao.QueryAllLineFavor(userID);
	}

	public List<?> QueryAllTransferFavor(int userID) {
		return this.appFavorDao.QueryAllTransferFavor(userID);
	}

	/*------------------------------------------------------------------------*/

	public boolean HasStationFavor(AppStationFavor stationFavor) {
		List<AppStationFavor> temp = this.appFavorDao
				.QueryHasStationFavor(stationFavor);
		if (temp != null && temp.size() > 0)
			return true;
		return false;
	}
	
	public boolean HasBikeStationFavor(AppBikeStationFavor stationFavor) {
		List<AppBikeStationFavor> temp = this.appFavorDao
				.QueryHasBikeStationFavor(stationFavor);
		if (temp != null && temp.size() > 0)
			return true;
		return false;
	}

	public boolean HasLineFavor(AppLineFavor lineFavor) {
		List<AppLineFavor> temp = this.appFavorDao.QueryHasLineFavor(lineFavor);
		if (temp != null && temp.size() > 0)
			return true;
		return false;
	}

	public boolean HasTransferFavor(AppTransferFavor transferFavor) {
		List<AppTransferFavor> temp = this.appFavorDao
				.QueryHasTransferFavor(transferFavor);
		if (temp != null && temp.size() > 0)
			return true;
		return false;
	}

	/*---------------------------------------------------------------------*/

	public static final String SQLIT_CHAR = ",";

	public int deleteOneLine(int id, int userid) {
		AppLineFavor lineFavor = this.appFavorDao.GetLineById(id);

		if (lineFavor == null)
			return -2;
		else if (lineFavor.getUserID() != userid)
			return -1;

		this.appFavorDao.deleteLineByIDs(lineFavor);
		return 1;

	}

	public int deleteLine(String ids, int userid) {
		String[] temps = ids.split(SQLIT_CHAR);

		try {
			for (int i = 0; i < temps.length; i++) {
				int id = Integer.valueOf(temps[i]);

				int resultcode = deleteOneLine(id, userid);

				if (resultcode < 0)
					return -(i + 1);
			}

			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int deleteOneStation(int id, int userid) {
		AppStationFavor stationFavor = this.appFavorDao.GetStationById(id);

		if (stationFavor == null)
			return -2;
		else if (stationFavor.getUserID() != userid)
			return -1;

		this.appFavorDao.deleteStationByIDs(stationFavor);
		return 1;

	}

	public int deleteStation(String ids, int userid) {
		String[] temps = ids.split(SQLIT_CHAR);

		try {
			for (int i = 0; i < temps.length; i++) {
				int id = Integer.valueOf(temps[i]);

				int resultcode = deleteOneStation(id, userid);

				if (resultcode < 0)
					return -(i + 1);
			}

			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	public int deleteOneBikeStation(int id, int userid) {
		AppBikeStationFavor stationFavor = this.appFavorDao.GetBikeStationById(id);

		if (stationFavor == null)
			return -2;
		else if (stationFavor.getUserID() != userid)
			return -1;

		this.appFavorDao.deleteBikeStationByIDs(stationFavor);
		return 1;

	}

	public int deleteBikeStation(String ids, int userid) {
		String[] temps = ids.split(SQLIT_CHAR);

		try {
			for (int i = 0; i < temps.length; i++) {
				int id = Integer.valueOf(temps[i]);

				int resultcode = deleteOneBikeStation(id, userid);

				if (resultcode < 0)
					return -(i + 1);
			}

			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int deleteOneTransfer(int id, int userid) {
		AppTransferFavor stationFavor = this.appFavorDao.GetTransferById(id);

		if (stationFavor == null)
			return -2;
		else if (stationFavor.getUserID() != userid)
			return -1;

		this.appFavorDao.deleteTransferByIDs(stationFavor);
		return 1;

	}

	public int deleteTransfer(String ids, int userid) {
		String[] temps = ids.split(SQLIT_CHAR);

		try {
			for (int i = 0; i < temps.length; i++) {
				int id = Integer.valueOf(temps[i]);

				int resultcode = deleteOneTransfer(id, userid);

				if (resultcode < 0)
					return -(i + 1);
			}

			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
}
