package com.sts.parkingdb.action;

import java.util.List;

import com.sts.common.model.Message;
import com.sts.common.util.MessageFactory;
import com.sts.parkingdb.service.ParkingDBService;

/**
 * 停车场信息(数据库中获取)
 * 
 * @author Administrator zwc
 * 
 */
public class ParkingDBAction {

	// 停车场id
	String parkID;

	// 停车次名次
	String parkName;

	// 停车次类型
	int parkType;

	// 停车经纬度
	double lan;
	double lon;
	int radius;

	// 分页
	int page;
	int num;

	// 是否包含实时信息
	int isReal;

	public void setIsReal(int isReal) {
		this.isReal = isReal;
	}

	public void setParkID(String parkID) {
		this.parkID = parkID;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public void setParkType(int parkType) {
		this.parkType = parkType;
	}

	public void setLan(double lan) {
		this.lan = lan;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setNum(int num) {
		this.num = num;
	}

	// 返回结果
	List<?> result;

	public List<?> getResult() {
		return result;
	}

	// 结果消息
	Message message;

	public Message getMessage() {
		return message;
	}

	ParkingDBService parkingDBService;

	public void setParkingDBService(ParkingDBService parkingDBService) {
		this.parkingDBService = parkingDBService;
	}

	// id搜索
	//http://localhost:8080/SmartTrafficServer/searchParkingByID?parkID=018301&isReal=1
	public String searchParkingByID() {
		try {
			result = this.parkingDBService.searchParkingByID(parkID,
					isReal == 1 ? true : false);

			message = MessageFactory.createMessage(result);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}
		return "success";
	}

	// 名字类型搜索
	//http://localhost:8080/SmartTrafficServer/searchParkingByNameAndType?parkType=1&page=1&num=10&isReal=1
	public String searchParkingByNameAndType() {

		try {
			if (parkType == 0)
				parkType = -1;

			parkName = parkName == null ? "" : parkName;

			result = this.parkingDBService.searchParkingByNameAndType(parkType,
					parkName, page, num, isReal == 1 ? true : false);

			message = MessageFactory.createMessage(result);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}
		return "success";
	}

	// 位置搜索
	//http://localhost:8080/SmartTrafficServer/searchParkingByLoacation?radius=9000&lan=30.7000&lon=120.700&page=1&num=15&isReal=1
	public String searchParkingByLoacation() {

		try {
			result = this.parkingDBService.searchParkingByLoacation(radius,
					lan, lon, page, num, isReal == 1 ? true : false);

			message = MessageFactory.createMessage(result);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}
		return "success";
	}
}
