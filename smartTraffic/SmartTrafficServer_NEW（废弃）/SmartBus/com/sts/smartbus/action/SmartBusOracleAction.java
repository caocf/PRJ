package com.sts.smartbus.action;

import java.util.List;

import com.sts.common.model.Message;
import com.sts.common.util.MessageFactory;
import com.sts.smartbus.service.SmartBusOracleService;

/**
 * 从oracle中获取数据
 * 
 * @author Administrator zwc
 * 
 */
public class SmartBusOracleAction {

	SmartBusOracleService smartBusOracleService;

	public void setSmartBusOracleService(
			SmartBusOracleService smartBusOracleService) {
		this.smartBusOracleService = smartBusOracleService;
	}

	int stationID;
	String stationName;
	int page;
	int num;

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public void setStationName(String staionName) {
		this.stationName = staionName;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	// 返回结果列表
	List<?> result;

	public List<?> getResult() {
		return result;
	}

	// 返回结果消息
	Message message;

	public Message getMessage() {
		return message;
	}

	/**
	 * 站点id查询接口
	 * 
	 * @return
	 */
	public String queryStationByStationID() {
		try {
			if (stationID > 0) {
				result = this.smartBusOracleService
						.queryStationByStationID(stationID);

				message = MessageFactory.createMessage(result);
			} else {
				message = new Message(-1, "站点号不能小于0");
			}
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}

		return "success";
	}

	/**
	 * 站点名称输入提示接口
	 * 
	 * @return
	 */
	public String querySimilarStationByName() {
		try {
			if (stationName == null || stationName.equals("")) {
				message = new Message(-1, "传入站名为空");
			} else {
				result = this.smartBusOracleService.queryStationByStationName(
						stationName, false, 10);

				message = MessageFactory.createMessage(result);
			}
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}

		return "success";
	}

	public String queryLineThroughStation() {
		return "success";
	}
}
