package com.sts.parkingdb.service;

import java.util.List;

import com.sts.common.model.Degree;
import com.sts.common.util.GPSToMeter;
import com.sts.parkingdb.dao.ParkingDBDao;

public class ParkingDBService {
	ParkingDBDao parkingDBDao;

	public void setParkingDBDao(ParkingDBDao parkingDBDao) {
		this.parkingDBDao = parkingDBDao;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @param isReal
	 * @return
	 */
	public List<?> searchParkingByID(String id, boolean isReal) {
		return this.parkingDBDao.searchParkingByID(id, isReal);
	}

	/**
	 * 根据类型和名称查询
	 * 
	 * @param type
	 * @param name
	 * @param page
	 * @param num
	 * @param isReal
	 * @return
	 */
	public List<?> searchParkingByNameAndType(int type, String name, int page,
			int num, boolean isReal) {
		return this.parkingDBDao.searchParkingByNameAndType(type, name, page,
				num, isReal);
	}

	public List<?> searchParkingByNameAndTypes(int[] type, String name,
			int page, int num) {
		return this.parkingDBDao.searchParkingByNameAndTypes(type, name, page,
				num);
	}

	/**
	 * 搜索半径
	 * 
	 * @param radius
	 * @param lan1
	 * @param lon1
	 * @return
	 */
	public List<?> searchParkingByLoacation(int radius, double lan1,
			double lon1, int page, int num, boolean isReal) {
		try {
			Degree[] degrees = GPSToMeter.GetDegreeCoordinates(new Degree(lan1,
					lon1), 1.0 * radius / 1000);

			List<?> result = this.parkingDBDao.searchParkingByCenterAndRadius(
					degrees[3].getX(), degrees[0].getY(), degrees[0].getX(),
					degrees[3].getY(), page, num, isReal);

			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public List<?> searchParkingByLocationCircle(int radius, double lan,
			double lon, int page, int num, boolean isReal) {
		return this.parkingDBDao.searchParkingByLocationCircle(radius, lan,
				lon, page, num, isReal);
	}
}
