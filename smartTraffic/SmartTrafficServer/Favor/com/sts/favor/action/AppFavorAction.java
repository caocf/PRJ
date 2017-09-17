package com.sts.favor.action;

import java.util.List;

import com.sts.common.model.Message;
import com.sts.favor.service.AppFavorService;

public class AppFavorAction {

	/*--------------------------------------------------------*/
	AppFavorService appFavorService;

	public void setAppFavorService(AppFavorService appFavorService) {
		this.appFavorService = appFavorService;
	}

	/*--------------------------------------------------------*/

	Message message;
	List<?> result;

	public Message getMessage() {
		return message;
	}

	public List<?> getResult() {
		return result;
	}

	/*------------------------------------------------------------*/
	int userid;

	int lineID;
	int direct;
	int stationID;

	String lineIds;
	String stationIds;
	String transferIds;
	
	String name;
	
	String stationLines;

	double startLantitude;
	double startLongtitude;
	double endLantitude;
	double endLongtitude;
	String startName;
	String endName;

	String address;
	
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStationLines(String stationLines) {
		this.stationLines = stationLines;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public void setLineIds(String lineIds) {
		this.lineIds = lineIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
	}

	public void setTransferIds(String transferIds) {
		this.transferIds = transferIds;
	}

	public void setStartLantitude(double startLantitude) {
		this.startLantitude = startLantitude;
	}

	public void setStartLongtitude(double startLongtitude) {
		this.startLongtitude = startLongtitude;
	}

	public void setEndLantitude(double endLantitude) {
		this.endLantitude = endLantitude;
	}

	public void setEndLongtitude(double endLongtitude) {
		this.endLongtitude = endLongtitude;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	/*--------------------------------------------------------*/

	/**
	 * 收藏站点
	 */
	public String APPSaveStationFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if (stationID <= 0)
			message = new Message(-2, "站点id不可能小于等于0");
		else if(name==null ||name.equals(""))
			message=new Message(-4, "站点名不能为空");
		else {
			int i = this.appFavorService.SaveStationFavor(userid, stationID,name,stationLines);
			if (i == 0)
				message = new Message(-3, "该用户已收藏了该站点");
			else if (i == 1)
				message = new Message(1, "收藏站点成功");
		}

		return "success";
	}
	
	/**
	 * 收藏公共自行车站点
	 */
	public String APPSaveBikeStationFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if (stationID <= 0)
			message = new Message(-2, "站点id不可能小于等于0");
		else if(name==null ||name.equals(""))
			message=new Message(-4, "站点名不能为空");
		else {
			int i = this.appFavorService.SaveBikeStationFavor(userid, stationID,name,address);
			if (i == 0)
				message = new Message(-3, "该用户已收藏了该站点");
			else if (i == 1)
				message = new Message(1, "收藏站点成功");
		}

		return "success";
	}
	

	/**
	 * 收藏线路
	 * @return
	 */
	public String APPSaveLineFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if (lineID <= 0)
			message = new Message(-2, "线路id不可能小于等于0");
		else if(name==null ||name.equals(""))
			message = new Message(-4, "线路名不能为空");
		else if(startName==null ||  startName.equals("") )
			message = new Message(-5, "线路起点不能为空");
		else if(endName==null || endName.equals(""))
			message = new Message(-6, "线路终点不能为空");
		else {
			int i = this.appFavorService.SaveLineFavor(userid, lineID, direct,name,startName,endName);
			if (i == 0)
				message = new Message(-3, "该用户已收藏了该线路");
			else if (i == 1)
				message = new Message(1, "收藏线路成功");
		}

		return "success";
	}

	/**
	 * 收藏换乘
	 * @return
	 */
	public String APPSaveTransferFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if (startLantitude == 0 || startLongtitude == 0
				|| endLantitude == 0 || endLongtitude == 0 || startName == null
				|| startName.equals("") || endName == null
				|| endName.equals(""))
			message = new Message(-2, "传入参数都不能为空");
		else {
			int i = this.appFavorService.SaveTransferFavor(userid,
					startLantitude, startLongtitude, endLantitude,
					endLongtitude, startName, endName);
			if (i == 0)
				message = new Message(-3, "该用户已收藏了该换乘方案");
			else if (i == 1)
				message = new Message(1, "收藏换乘方案成功");
		}

		return "success";
	}

	/**
	 * 查询所有站点
	 * @return
	 */
	public String APPQueryAllStationFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else {
			result = this.appFavorService.QueryAllStationFavor(userid);
			message = new Message(1, "查询成功");
		}

		return "success";
	}
	
	/**
	 * 查询所有自行车站点
	 * @return
	 */
	public String APPQueryAllBikeStationFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else {
			result = this.appFavorService.QueryAllBikeStationFavor(userid);
			message = new Message(1, "查询成功");
		}

		return "success";
	}

	/**
	 * 查询所有线路
	 * @return
	 */
	public String APPQueryAllLineFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else {
			result = this.appFavorService.QueryAllLineFavor(userid);
			message = new Message(1, "查询成功");
		}

		return "success";
	}

	/**
	 * 查询所有换乘
	 * @return
	 */
	public String APPQueryAllTransferFavor() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else {
			result = this.appFavorService.QueryAllTransferFavor(userid);
			message = new Message(1, "查询成功");
		}
		return "success";
	}

	/**
	 * 删除站点
	 * stationIds已","区分
	 * @return
	 */
	public String APPDeleteForStation() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if(stationIds==null ||stationIds.equals(""))
			message = new Message(-2, "删除内容id不能为空");
		else {
			int resultcode = this.appFavorService.deleteStation(stationIds,
					userid);

			if (resultcode == 1)
				message = new Message(1, "删除成功");
			else if (resultcode == 0)
				message = new Message(-3, "操作失败，未知错误");
			else if (resultcode < 0)
				message = new Message(-4, "第" + (-resultcode)
						+ "条数据操作失败，该收藏记录不存在或不属于该用户");
		}
		return "success";
	}
	
	/**
	 * 删除自行车站点
	 * stationIds已","区分
	 * @return
	 */
	public String APPDeleteBikeForStation() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if(stationIds==null ||stationIds.equals(""))
			message = new Message(-2, "删除内容id不能为空");
		else {
			int resultcode = this.appFavorService.deleteBikeStation(stationIds,
					userid);

			if (resultcode == 1)
				message = new Message(1, "删除成功");
			else if (resultcode == 0)
				message = new Message(-3, "操作失败，未知错误");
			else if (resultcode < 0)
				message = new Message(-4, "第" + (-resultcode)
						+ "条数据操作失败，该收藏记录不存在或不属于该用户");
		}
		return "success";
	}

	/**
	 * 删除线路
	 * lineIds已","区分
	 * @return
	 */
	public String APPDeleteForLine() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if(lineIds==null ||lineIds.equals(""))
			message = new Message(-2, "删除内容id不能为空");
		else {
			int resultcode = this.appFavorService.deleteLine(lineIds, userid);

			if (resultcode == 1)
				message = new Message(1, "删除成功");
			else if (resultcode == 0)
				message = new Message(-3, "操作失败，未知错误");
			else if (resultcode < 0)
				message = new Message(-4, "第" + (-resultcode)
						+ "条数据操作失败，该收藏记录不存在或不属于该用户");
		}
		return "success";
	}

	/**
	 * 删除换乘
	 * transferIds已","区分
	 * @return
	 */
	public String APPDeleteForTransfer() {
		if (userid == 0)
			message = new Message(-1, "传入用户id不能为空");
		else if(transferIds==null ||transferIds.equals(""))
			message = new Message(-2, "删除内容id不能为空");
		else {
			int resultcode = this.appFavorService.deleteTransfer(transferIds,
					userid);

			if (resultcode == 1)
				message = new Message(1, "删除成功");
			else if (resultcode == 0)
				message = new Message(-3, "操作失败，未知错误");
			else if (resultcode < 0)
				message = new Message(-4, "第" + (-resultcode)
						+ "条数据操作失败，该收藏记录不存在或不属于该用户");
		}
		return "success";
	}
}
